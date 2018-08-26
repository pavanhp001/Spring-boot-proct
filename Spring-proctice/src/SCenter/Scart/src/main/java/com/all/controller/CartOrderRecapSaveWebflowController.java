package com.AL.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.dao.CustomerTrackerDao;
import com.AL.ui.dao.SalesCallDao;
import com.AL.ui.dao.SalesSessionDao;
import com.AL.ui.domain.SalesSession;
import com.AL.ui.factory.CartEventFactory;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.factory.CartSalesContextFactory;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.AccountHolderType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

/**
 * @author 
 *
 */
@Controller("CartOrderRecapSaveWebflowController")
public class CartOrderRecapSaveWebflowController implements Action  {

	/**
	 * Logger Initialization
	 * 
	 */
	private static final Logger logger = Logger.getLogger(CartOrderRecapSaveWebflowController.class);

	@Autowired
	private SalesSessionDao salesSessionDao;
	
	@Autowired
	private SalesCallDao salesCallDao;
	
	@Autowired
	private CustomerTrackerDao customerTrackerDao;
	
	
	/**
	 * Based on the orderId in the URL builds the view for Order summary screen.
	 * 
	 * 
	 * @param
	 * @param request
	 * @return
	 */
	public Event execute(RequestContext req) throws Exception {
		HttpServletRequest request =(HttpServletRequest)req.getExternalContext().getNativeRequest();
		long startTimer=0;
		StopWatch timer=new StopWatch();
	    timer.start();
		try{
			logger.info("Preparing_CloseCallSaleView_in_CartOrderRecapSaveWebflowController");
			HttpSession session = request.getSession();
			
			OrderType order = (OrderType)request.getSession().getAttribute(ControllerConstants.ORDER);
			OrderManagementRequestResponse orderManagementRequestResponse = OrderService.INSTANCE.getOrderManagementRequestResponseByOrderNumber(String.valueOf(order.getExternalId()), Boolean.TRUE);
			
			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)request.getSession().getAttribute("dynamicFlowContextMap");
			if(contextMap!=null && !contextMap.isEmpty() 
					&& contextMap.get("dynamicFlow")!=null 
					&& !contextMap.get("dynamicFlow").isEmpty())
			{
				Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
				if(dynamicFlow.get("dynamicFlow.flowType").contains("simpleChoice")){
					/*
					 * Do submit call for savers offer when "isMarketingOptIn" flag  is 'true' on customer.			 
					 */
					/*if(order!=null 
							&& order.getCustomerInformation()!=null 
							&& order.getCustomerInformation().getCustomer()!=null 
							&& order.getCustomerInformation().getCustomer().isMarketingOptIn())
					{
						
						 * Submitting the savers offer only.			 
						 
						order = CartPlaceOrderFactory.INSTANCE.submitOrderForSaversOffer(request);
					}*/
					//else
					//{	
					updateDispositionForSale(order, request);

						SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
						SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
						int zipOnlySearch = 0;
						if(salesCenterVo!= null && salesCenterVo.getValueByName("isZipOnlySearch") != null && salesCenterVo.getValueByName("isZipOnlySearch").equalsIgnoreCase("YES")){
							zipOnlySearch = 1;
						}

						if(salesContext != null)
						{
							startTimer=timer.getTime();
							OrderService.INSTANCE.updateSalesContext(order.getAgentId(), String.valueOf(order.getExternalId()), 
									CartSalesContextFactory.INSTANCE.getStatusCompleteSalesContext(salesContext), 
									SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID"), zipOnlySearch);
							logger.info("TimetakenforUpdateOrderServicecall="+(timer.getTime()-startTimer));
							try{
								Utils.insertAndUpdateCustomerTrackerData(orderManagementRequestResponse.getResponse().getOrderInfo().get(0), session, customerTrackerDao);
								}catch(Exception e){
									logger.info("error occured while inserting the data "+e.getMessage());
							}
							try{
								Utils.postVZSaveSmartCartProduct(orderManagementRequestResponse.getResponse().getOrderInfo().get(0), session);
							}catch(Exception e){
								logger.info("error occured while post VZ SaveSmartCartProduct "+e.getMessage());
							}

						}
						
					//}
				}
				
			}
			order = orderManagementRequestResponse.getResponse().getOrderInfo().get(0);
			boolean isTPVEnabled = false;
			List<LineItemType>  lineItemList = CartLineItemFactory.INSTANCE.getTPVLineItemsWithSortingOrder(order);
			if(lineItemList != null && lineItemList.size() > 0){
				isTPVEnabled = true;
				StringBuffer accountHolderName = new StringBuffer();
				if (lineItemList.get(0).getAccountHolderExternalId() != null){
					for(AccountHolderType accountHolder : order.getAccountHolder())
					{
						logger.info("accountHolderExtID="+ accountHolder.getExternalId()+"***********LineAcountHolderExtID="+lineItemList.get(0).getAccountHolderExternalId());
						if ( accountHolder.getExternalId() == lineItemList.get(0).getAccountHolderExternalId().longValue() )
						{
							accountHolderName.append(accountHolder.getFirstName());
							accountHolderName.append(" ");
							accountHolderName.append(accountHolder.getLastName());
							break;
						}
					}
				}
				logger.info("accountHolderNameLength="+ accountHolderName.length());
				if(accountHolderName.length()==0)
				{
					accountHolderName.append(order.getCustomerInformation().getCustomer().getFirstName());
					accountHolderName.append(" ");
					accountHolderName.append(order.getCustomerInformation().getCustomer().getLastName());
				}
				req.getFlowScope().put("tpvAccountHolderName", accountHolderName.toString());
				logger.info("tpvAccountHolderName="+ accountHolderName.toString());
			}
			SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute("salescontext");
			String refferId = salesCenterVo.getValueByName("referrer.businessParty.referrerId");
			req.getFlowScope().put("refferIdValue", refferId);
			req.getFlowScope().put("isTPVEnabled",isTPVEnabled);
			Event event = CartEventFactory.INSTANCE.createCloseCallSaleView(session, order, req);
			logger.info("end_of_CloseCallSaleView_in_CartOrderRecapSaveWebflowController");
			logger.info("TimetakenforCartOrderRecapSaveWebflowController="+timer.getTime());
			return event;
		}finally
	    {
	    	timer.stop();
	    }
	}
	
	/**
	 * 
	 * @param orderManagementRequestResponse
	 */
	public void updateDispositionForSale(OrderType orderType, HttpServletRequest request)
	{
		logger.info("Updating_Disposition_data_into_SalesSession_table");
		try {
			String ambDisconnectDateTime = (String) request.getSession().getAttribute("ambDisconnectdatetime"); 
	        long salesCallId = (Long) request.getSession().getAttribute("salesCallId");
		    logger.info("ambDisconnectDateTime"+ambDisconnectDateTime);
		    logger.info("salesCallIdVal"+salesCallId);
			Long orderId = orderType.getExternalId();
			Long customerId = orderType.getCustomerInformation().getCustomer().getExternalId();
			SalesSession salesSession = new SalesSession();
			if (customerId != null && orderId != null) 
			{
				salesSession = salesSessionDao.getSalesSession(orderId,	customerId);
			}

			Long dispositionId = 11041L;
			salesSession.setDispositionId(dispositionId);
			logger.info("DispositionId="+dispositionId);
			salesSession.setEndTime(Calendar.getInstance());
			salesSession.setNormalClose("true");
			salesSessionDao.updateSalesSession(salesSession);

			logger.info("ambDisconnectDateTime="+ambDisconnectDateTime);

			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z (Z)");
			if (!Utils.isBlank(ambDisconnectDateTime)) 
			{
				Calendar disconnectCal = Calendar.getInstance();
				String ambDisconnectDateTime2 = ambDisconnectDateTime.substring(0, 31);
				String ambDisconnectDateTime3 = ambDisconnectDateTime.substring(31, ambDisconnectDateTime.length());
				ambDisconnectDateTime = ambDisconnectDateTime2 + ":"+ ambDisconnectDateTime3;
				disconnectCal.setTime(sdf.parse(ambDisconnectDateTime));
				salesCallDao.updateSalesCallWithAmbDisconnectDateTime(salesCallId, disconnectCal);
			}
			logger.info("Saved_DispositionDetails");
		} 
		catch (Exception e) {
			logger.warn("Error_in_SaveDispositionDetails"+e.getMessage());
		}
	}

}
