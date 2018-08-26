package com.AL.controller;

import java.util.HashMap;
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
import com.AL.ui.builder.CartDialogueBuilder;
import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.dao.CustomerTrackerDao;
import com.AL.ui.factory.CartDialogueFactory;
import com.AL.ui.factory.CartEventFactory;
import com.AL.ui.factory.CartPlaceOrderFactory;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.CartLineItemUtil;
import com.AL.ui.util.CartUtil;
import com.AL.ui.vo.DataFieldVO;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.di.v4.DialogueResponseType;
import com.AL.xml.di.v4.DataGroupType.DataFieldList;
import com.AL.xml.v4.CustAddress;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

/**
 * @author 
 *
 */
@Controller("CartOrderRecapWebflowController")
public class CartOrderRecapWebflowController implements Action  {

	/**
	 * Logger Initialization
	 * 
	 */
	private static final Logger logger = Logger.getLogger(CartOrderRecapWebflowController.class);
	
	@Autowired
	private CustomerTrackerDao customerTrackerDao;

	/**
	 * Based on the orderId in the URL builds the view for Order summary screen.
	 * 
	 * 
	 * @param id(OrderType externalId)
	 * @param request
	 * @return ModelAndView
	 */
	public Event execute(RequestContext req) throws Exception {
		HttpServletRequest request =(HttpServletRequest)req.getExternalContext().getNativeRequest();
		StopWatch timer=new StopWatch();
		timer.start();
		long startTimer=0;
		try{
			logger.info("preparing_orderRecapView_in_CartOrderRecapWebflowController");
	
			HttpSession session = request.getSession();
			String agentId = CartUtil.INSTANCE.getAgentId(session);
			String id = request.getParameter("orderId");
			
			SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute("salescontext");
			Map<String, Map<String, String>> context = (Map<String, Map<String, String>>)request.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, String> dynamicFlow = context.get("dynamicFlow");
			
			dynamicFlow.put("dynamicFlow.page", "OrderRecap");
			dynamicFlow.put("GUID", salesCenterVo.getValueByName("GUID"));
			/*
			 * Making service call to get dialogues from DialogService..
			 */
			logger.info("about_to_make_DialogService_call_in_CartOrderRecapWebflowController");
			
			DialogueResponseType dialogueResponseType = CartDialogueFactory.INSTANCE.getDialoguesByContext(context);
			
			if(dialogueResponseType == null || dialogueResponseType.getResults() == null 
					|| dialogueResponseType.getResults().getDialogueList() == null 
					|| dialogueResponseType.getResults().getDialogueList().getDialogue() == null 
					|| dialogueResponseType.getResults().getDialogueList().getDialogue().isEmpty())
			{
				
				throw new UnRecoverableException("Dialogues Were not found.");
			}
			DataFieldList dataFieldList = CartDialogueBuilder.INSTANCE.buildDataFieldList(dialogueResponseType);
			
			List<DataFieldVO> dataFieldVOs =  CartDialogueBuilder.INSTANCE.prepareDataFiledVOList(dataFieldList.getDataField());
			
			logger.info("dataFieldVOs="+dataFieldVOs);
			req.getFlowScope().put(ControllerConstants.DATAFIELDLIST, dataFieldVOs);
			/*	
			 * Getting SalesContext from the session				 
			 */
			SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
			/*	
			 *	Order excluding lineItems which are in CANCELLED_REMOVED status				 
			 */
			startTimer=timer.getTime();
			OrderType order = OrderService.INSTANCE.getOrderByOrderNumber(id,agentId,new HashMap<String,Object>(),"*",Boolean.TRUE,salesContext);
			logger.info("TimetakenforGetOrderServiceCall="+(timer.getTime()-startTimer));
			CustAddress custAddress  = CartLineItemUtil.getAddress(order.getCustomerInformation().getCustomer(),com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());
			req.getFlowScope().put("address", CartEventFactory.INSTANCE.getAddress(custAddress));
			Map<String, String> notePadHeaders = new LinkedHashMap<String, String>();
			notePadHeaders = (Map<String, String>)session.getAttribute("notePadMap");	
			if (notePadHeaders != null) {
			req.getFlowScope().put("notePadMap", notePadHeaders);
			}		
			Event event = CartEventFactory.INSTANCE.createOrderView(agentId, order, ControllerConstants.ORDER_RECAP_VIEW, ControllerConstants.ORDER_RECAP_TITLE, req);
			
			logger.info("end_of_orderRecapView_in_CartOrderRecapWebflowController");
			logger.info("TimetakenforPreparingorderrecapPage="+timer.getTime());
			return event;
		}finally
		{
			timer.stop();
		}
	}
	
	
	

	/**
	 * Submitting the order and confirming next flow.
	 * 
	 * @param RequestContext
	 * @return Event
	 */
	public Event submitOrder(RequestContext req) throws UnRecoverableException, Exception {
		HttpServletRequest request =(HttpServletRequest)req.getExternalContext().getNativeRequest();
		try{
			logger.info("submitOrder_begin_CartOrderRecapWebflowController");
			
			if( request.getSession().getAttribute("isFromRecommendation")!= null )
			{
			    request.getSession().removeAttribute("isFromRecommendation");
			}
			
			boolean isSimpleChoiceFlow = false;
			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)request.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			if(dynamicFlow.get("dynamicFlow.flowType").contains("simpleChoice")){
				isSimpleChoiceFlow = true;
			}
			if(request.getParameter("endCall")==null)
			{
				/*
				 * Placing the Order	
				 * 2nd parameter in below placeOrder method is true when we coming from simple choice flow otherwise it is false.			 
				 */
				OrderType order = CartPlaceOrderFactory.INSTANCE.placeOrder(req,isSimpleChoiceFlow, customerTrackerDao);
				request.getSession().setAttribute(ControllerConstants.ORDER, order);
				request.getSession().setAttribute("isAllProductsSubmitted", "Yes");
			}
			
			ProductResultsManager productResultManager = (ProductResultsManager) request.getSession().getAttribute("productResultManager");
			logger.info("submitOrder_end_CartOrderRecapWebflowController");
			if(productResultManager.getSaversOfferMap()!=null && !productResultManager.getSaversOfferMap().isEmpty()){
				if(request.getParameter("endCall")!=null)
				{
					req.getFlowScope().put("event", "closeCallNoSaleEvent");
				}
				else
				{
					req.getFlowScope().put("event", "closeCallSaleEvent");
				}
				return new Event(this, "offerEvent");
			}
			else
			{
				if(request.getParameter("endCall")!=null)
				{
					return new Event(this, "closeCallNoSaleEvent");
				}
				else{
					return new Event(this, "closeCallSaleEvent");
				}
			}
			
		}catch (Exception e) {
			req.getFlowScope().put("message", e.getMessage());
			req.getFlowScope().put("pageTitle",request.getParameter("pageTitle")!=null?request.getParameter("pageTitle"):"");
			logger.error("error_while_placing_order_before_saversOffer",e);
			throw new UnRecoverableException(e.getMessage());
		}
	}
}
