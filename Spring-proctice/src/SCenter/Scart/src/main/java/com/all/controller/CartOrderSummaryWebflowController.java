package com.AL.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.vo.ProductSummaryVO;
import com.AL.service.ClosingOfferService;
import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.factory.CartEventFactory;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.CartLineItemUtil;
import com.AL.ui.util.CartUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.CustAddress;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

/**
 * @author 
 *
 */
@Controller("CartOrderSummaryWebflowController")
public class CartOrderSummaryWebflowController implements Action  {
	
	@Autowired
	ClosingOfferService closingOfferService;
	
	private long startTimer=0;
	/**
	 * Logger Initialization
	 * 
	 */
	private static final Logger logger = Logger.getLogger(CartOrderSummaryWebflowController.class);

	/**
	 * Based on the orderId in the URL builds the view for Order summary screen.
	 * 
	 * 
	 * @param id(OrderType externalId)
	 * @param request
	 * @return ModelAndView
	 */
	public Event execute(RequestContext req) throws UnRecoverableException {
		logger.info("execute_begin_in_CartOrderSummaryWebflowController");
		StopWatch timer = new StopWatch();
		timer.start();
		HttpServletRequest request =(HttpServletRequest)req.getExternalContext().getNativeRequest();
		try{
			HttpSession session = request.getSession();
			String agentId = CartUtil.INSTANCE.getAgentId(session);
			String id = request.getParameter("orderId");
			ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
			Map<String, String> notePadHeaders = new LinkedHashMap<String, String>();
			notePadHeaders = (Map<String, String>)session.getAttribute("notePadMap");	
			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)session.getAttribute("dynamicFlowContextMap");
			SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute("salescontext");
			if (contextMap != null){
				Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
				if (dynamicFlow != null){
					dynamicFlow.put("dynamicFlow.page", "OrderSummary");
				}
			}

			/*	
			 * Service Call for Sales Context 		 
			 */
			startTimer=timer.getTime();
			OrderManagementRequestResponse orderManagementRequestResponse = OrderService.INSTANCE.getOrderManagementRequestResponseByOrderNumber(id, Boolean.TRUE);
			logger.info("TimeTakenforOrderServicecall="+(timer.getTime()-startTimer));

			SalesContextType context = orderManagementRequestResponse.getResponse().getSalesContext();
			SalesContextFactory.INSTANCE.setContextInSession(session, context);

			OrderType order = orderManagementRequestResponse.getResponse().getOrderInfo().get(0);
			//To display feature description for COX RTS
			CartLineItemUtil.coxOrderSummaryFeatureDisplayNames(order,request);
			logger.info("setting_order_in_session_order_summary");
			request.getSession().setAttribute("order", order);

			CustAddress custAddress  = CartLineItemUtil.getAddress(order.getCustomerInformation().getCustomer(),com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());
			req.getFlowScope().put("address", CartEventFactory.INSTANCE.getAddress(custAddress));
			if (notePadHeaders != null) {
				req.getFlowScope().put("notePadMap", notePadHeaders);
			}

			startTimer=timer.getTime();
			//TODO:Remove this CALL after OME fix(Temporary Fix for Calculating Static Prices)
			//order = CartProductFactory.INSTANCE.validateStaticPrices(order, session);
			logger.info("TimeTakenforOrderServiceCalls[orderId="+id+"][Time="+ (timer.getTime()-startTimer) + "]");

			logger.info("Building_Order_Summary_View");
			Event event = CartEventFactory.INSTANCE.createOrderView(agentId, order, ControllerConstants.ORDER_SUMMARY_VIEW, ControllerConstants.ORDER_SUMMARY_TITLE, req);

			req.getFlowScope().put("totalPoints", Utils.getTotalPoints(order,session));

			logger.info("execute_end_in_CartOrderSummaryWebflowController");
			logger.info("TimetakenforCartOrderSummaryWebflowController="+timer.getTime());
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			List<ProductSummaryVO>	closingOfferList  = closingOfferService.buildClosingOfferList(order, productResultManager, request);
			if(closingOfferList != null 
			    && !closingOfferList.isEmpty() 
				 && dynamicFlow != null 
					&& dynamicFlow.get("dynamicFlow.flowType") != null 
					     && !dynamicFlow.get("dynamicFlow.flowType").contains("simpleChoice")){
				req.getFlowScope().put("closingOfferFlagInOrderSummary" , "Next Page ClosingOffer - "+closingOfferList.get(0).getName());
			}else{
				req.getFlowScope().put("closingOfferFlagInOrderSummary" , null);
			}
			return event;
		}catch(Exception e){
			logger.error("Exception_in_CartOrderSummaryWebflowController",e);
			req.getFlowScope().put("message", e.getMessage());
			req.getFlowScope().put("pageTitle",request.getParameter("pageTitle")!=null?request.getParameter("pageTitle"):"");
			throw new UnRecoverableException(e.getMessage());
		}finally {
			timer.stop();
		}
	}


}
