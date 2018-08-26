package com.AL.ui.factory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.webflow.execution.RequestContext;

import com.AL.managers.ApplicationContextProvider;
import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.vo.ProductSummaryVO;
import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.dao.CustomerTrackerDao;
import com.AL.ui.dao.SalesCallDao;
import com.AL.ui.dao.SalesSessionDao;
import com.AL.ui.domain.CustomerTracker;
import com.AL.ui.domain.SalesSession;
import com.AL.ui.service.V.CustomerService;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.CartLineItemUtil;
import com.AL.ui.util.CartUtil;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.CartError;
import com.AL.ui.vo.ErrorList;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.CustomerFactory;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.cm.v4.Attributes;
import com.AL.xml.cm.v4.CustomerAttributeEntityType;
import com.AL.xml.cm.v4.CustomerAttributeType;
import com.AL.xml.cm.v4.CustomerContextType;
import com.AL.xml.cm.v4.CustomerType;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

public enum CartPlaceOrderFactory {

	INSTANCE;

	/**
	 * Logger Initialization
	 * 
	 */
	private static final Logger logger = Logger.getLogger(CartPlaceOrderFactory.class);

	/**
	 * Places the Order
	 * 
	 * 
	 * @param orderId
	 * @param agentId
	 * @param salesContext
	 * @param request
	 * @return OrderType
	 * @throws UnRecoverableException 
	 */
	public OrderType placeOrder(RequestContext req, boolean isFromSimpleChoice, CustomerTrackerDao customerTrackerDao) throws UnRecoverableException
	{
		StopWatch timer=new StopWatch();
		timer.start();
		long startTimer=0;
		try{
			logger.info("placeOrder_begin_in_CartPlaceOrderFactory");
			HttpServletRequest request =(HttpServletRequest)req.getExternalContext().getNativeRequest();
			String orderId = request.getParameter("orderId");
			HttpSession session = request.getSession();
			String agentId = CartUtil.INSTANCE.getAgentId(session);
			/**	
			 * Getting SalesContext from the session				 
			 */
			SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
	
			/*	
			 * Getting the Order from Caching Service				 
			 */
			startTimer=timer.getTime();
			OrderType order = CartUtil.INSTANCE.getOrder(orderId, agentId, salesContext);
			logger.info("TimeTakenforOrderServiceCall="+(timer.getTime()-startTimer));
			if(CartLineItemFactory.INSTANCE.allowPlaceOrder(order))
			{
				Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
				Map<String, String> sourceEntity = new HashMap<String, String>();
				sourceEntity.put("source", "salescenter");
				sourceEntity.put( "GUID", (String)session.getAttribute("GUID") );
				data.put( "source", sourceEntity );
				CustomerContextType customerContext = CustomerFactory.INSTANCE.buildCustomerContext(data);

				com.AL.xml.cm.v4.ObjectFactory oFactory = new com.AL.xml.cm.v4.ObjectFactory();
				CustomerType updateCustomer = oFactory.createCustomerType();
				CustomerAttributeType customerAttributeType = new com.AL.xml.cm.v4.ObjectFactory().createCustomerAttributeType();
				Attributes attributes = updateCustomer.getAttributes();
				if(attributes == null)
				{
					attributes = new com.AL.xml.cm.v4.ObjectFactory().createAttributes();
				}
				List<CustomerAttributeEntityType> customerAttributeEntityTypes = customerAttributeType.getEntity();
				
				String typeOfService = (String)request.getSession().getAttribute("typeOfService");
				logger.info("typeOfService="+typeOfService);
				if( request.getSession().getAttribute("isMoveInDelta")!= null && !Utils.isBlank(typeOfService) && !"moveInDeltaService".equalsIgnoreCase(typeOfService) )
				{
					customerAttributeEntityTypes.add(CartLineItemFactory.INSTANCE.setCustomerAttributeEntityType("VVD", "true", "", "VVD"));
				}
				else
				{
					customerAttributeEntityTypes.add(CartLineItemFactory.INSTANCE.setCustomerAttributeEntityType("VVD", "false", "", "VVD"));
				}
				attributes.getAttribute().add(customerAttributeType);
				updateCustomer.setAttributes(attributes);

				
				com.AL.xml.v4.Customer orderCustomer = order.getCustomerInformation().getCustomer();
				
				if( !Utils.isBlank ( orderCustomer.getBestEmailContact() ) )
				{
					updateCustomer.setExternalId( orderCustomer.getExternalId() );
					updateCustomer.setEMailOptIn( orderCustomer.isEMailOptIn() );
					updateCustomer.setEMailProductUpdatesOptIn(true);
					updateCustomer.setMarketingOptIn( orderCustomer.isMarketingOptIn() );
					updateCustomer.setNonBuyerWebOptIn( orderCustomer.isNonBuyerWebOptIn() );
					updateCustomer.setDirectMailOptIn( orderCustomer.isDirectMailOptIn() );
					updateCustomer.setPhoneContactOptIn( orderCustomer.isPhoneContactOptIn() );

				}
				
				startTimer = timer.getTime();
				updateCustomer = CustomerService.INSTANCE.submitCustomerType(agentId, String.valueOf(orderCustomer.getExternalId()),"updateCustomer", updateCustomer, customerContext);
				logger.info("TimeTakenforUpdateCustomerServiceCall="+(timer.getTime()-startTimer));
				
				ErrorList errorList = new ErrorList();
				List<String> listOflineItemIds = LineItemUtil.containsUtilityOffers(order);
				logger.info("CKO_in_complete_utility_offers_list=" + listOflineItemIds);
				if (!listOflineItemIds.isEmpty())
				{
					List<Integer> reasons = new ArrayList<Integer>();
					reasons.add(ControllerConstants.REASON_CODE);
	
					LineItemService.INSTANCE.updateLineItemStatus(agentId, String.valueOf(order.getExternalId()),
							listOflineItemIds, LineItemStatusCodesType.CANCELLED_REMOVED.value(),
							LineItemStatusCodesType.CANCELLED_REMOVED.value(), reasons, salesContext,errorList);
					logger.info("UpdateUtilityOrderLineItemStatus_completed");
					if (errorList != null && errorList.size() > 0) 
					{
						for (CartError cartError : errorList) 
						{
							logger.info("updateLineItemStatus_Error_Code:" + cartError.getCode());
							logger.info("updateLineItemStatus_Error_Message:"	+ cartError.getMessage());
							logger.info("updateLineItemStatus_Error_Description:" + cartError.getDescription());
						}
						throw new UnRecoverableException(errorList.get(0).getMessage());
					}
				}
	
				/*	
				 * updating the sales Context to get the CCP Mail for RTIM products which are already submitted in RTIM CKOs.		 
				 */
				if( !isFromSimpleChoice )
				{
				    updateDispositionForSale(order, request);					
					SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
					int zipOnlySearch = 0;
					if(salesCenterVo != null && salesCenterVo.getValueByName("isZipOnlySearch") != null && salesCenterVo.getValueByName("isZipOnlySearch").equalsIgnoreCase("YES")){
						zipOnlySearch = 1;
					}

					startTimer = timer.getTime();
					OrderService.INSTANCE.updateSalesContext( agentId, orderId, 
							CartSalesContextFactory.INSTANCE.getStatusCompleteSalesContext(salesContext), 
							SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID"), zipOnlySearch );
					logger.info("TimetakenforUpdateOrderSeviceCall="+(timer.getTime()-startTimer));
					try{
						Utils.insertAndUpdateCustomerTrackerData(order, session, customerTrackerDao);
					}catch(Exception e){
						logger.info("error occured while inserting the data "+e.getMessage());
					}
					try{
						Utils.postVZSaveSmartCartProduct(order, session);
					}catch(Exception e){
						logger.info("error occured while post VZ SaveSmartCartProduct "+e.getMessage());
					}
				}
				CartLineItemFactory.INSTANCE.setRTIMOrderNumberInSession(order, request);
				SalesContextFactory.INSTANCE.setContextInSession(session, salesContext);
				
			}
			
			logger.info("placeOrder_end_in_CartPlaceOrderFactory");
			logger.info("TimetakenforPlaceOrder="+(timer.getTime()-startTimer));
			return order;
		}finally
		{
			timer.stop();
		}
	}
	
	
	/**
	 * Order submit for savers offer in simple choice flow.
	 * 
	 * @param request
	 * @return order
	 * @throws UnRecoverableException
	 */
	public OrderType submitOrderForSaversOffer(HttpServletRequest request) throws UnRecoverableException
	{
		logger.info("submitOrderForSaversOffer_begin_in_CartPlaceOrderFactory");
		String orderId = request.getParameter("orderId");
		HttpSession session = request.getSession();
		String agentId = CartUtil.INSTANCE.getAgentId(session);
		/*
		 * Getting SalesContext from the session				 
		 */
		SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);

		/*	
		 * Getting the Order from Caching Service				 
		 */
		OrderType order = CartUtil.INSTANCE.getOrder(orderId, agentId, salesContext);

		List<String> lineitem_id_list =  LineItemUtil.containsSaversOffers(order);

		if(lineitem_id_list.size() > 0)
		{
			Double serviceStartTime = Double.valueOf(System.currentTimeMillis());
			
			ErrorList errorList = new ErrorList();
			/*	
			 * ServiceCall for submitting the Order	to place the savers offer.		 
			 */
			order = LineItemService.INSTANCE.submitMultipleLineItem( agentId, orderId, lineitem_id_list,
					CartSalesContextFactory.INSTANCE.getStatusCompleteSalesContext(salesContext),errorList );
			
			if(errorList != null && errorList.size() > 0)
			{
				for(CartError cartError: errorList)
				{
				    logger.info("Submit Multiple LineItem Error Code:"+cartError.getCode());
				    logger.info("Submit Multiple LineItem Error Message:"+cartError.getMessage());
				    logger.info("Submit Multiple LineItem Error Description:"+cartError.getDescription());
				}
				throw new UnRecoverableException(errorList.get(0).getMessage());
			}
			logger.info("Order_submittion_is_Completed[orderId="+orderId+"][Time="+ CartUtil.INSTANCE.getTimeDiff(serviceStartTime) + " ms]");
		}
		
		SalesContextFactory.INSTANCE.setContextInSession(session, salesContext);
		logger.info("submitOrderForSaversOffer_end_in_CartPlaceOrderFactory");
		return order;
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
			if(ApplicationContextProvider.getApplicationContext() != null 
					&& ApplicationContextProvider.getApplicationContext().getBean(SalesSessionDao.class) != null
					&& ApplicationContextProvider.getApplicationContext().getBean(SalesCallDao.class) != null){

				SalesSessionDao salesSessionDao = ApplicationContextProvider.getApplicationContext().getBean(SalesSessionDao.class);
				SalesCallDao salesCallDao = ApplicationContextProvider.getApplicationContext().getBean(SalesCallDao.class);
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
		} 
		catch (Exception e) {
			logger.warn("Error_in_SaveDispositionDetails"+e.getMessage());
		}		
	}
	
	public void getThoughtSpotTrackerData(OrderType order, HttpSession session) {
		try {
			ProductResultsManager productResultManager = (ProductResultsManager)session.getAttribute("productResultManager");
			Map<Integer, List<CustomerTracker>> tsOrderTrackerMap = null;
			List<CustomerTracker> tsLineItemTrackerDataList = new ArrayList<CustomerTracker>();
			
			SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute("salescontext");
			String referrerName = salesCenterVo.getValueByName("referrer.businessParty.referrerName");
			String customerName = getCustomerName(order);
			String dwellingType = salesCenterVo.getValueByName("dwellingType"); 
			String rentown = salesCenterVo.getValueByName("rentown"); 
			if(productResultManager != null && productResultManager.context.getAllProductList() != null && order.getLineItems()!= null){
				List<LineItemType> lineItems = order.getLineItems().getLineItem();
				if(lineItems != null){
					logger.info("totalPoints="+lineItems.size());
					for(LineItemType lineItem : lineItems){
						//if(isProduct(lineItem)){
						CustomerTracker customerTracker = new CustomerTracker();
						float points = 0.0f;
							if(lineItem != null && lineItem.getLineItemDetail() != null && lineItem.getLineItemDetail().getDetail() != null 
									&& lineItem.getLineItemDetail().getDetail().getProductLineItem() != null && lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId() != null){
								logger.info("lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId()="+lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId());
								for(ProductSummaryVO product : productResultManager.context.getAllProductList()){
									if(product.getExternalId().equalsIgnoreCase(lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId())){
										if(product != null && !Float.isNaN(product.getPoints())  && product.getPoints() >= 0.0){
											points = product.getPoints();
										}
										break;
									}
								}
							}
							//tsAgentTrackerVO.put("Call Number", agentObect.getAgentId());
							customerTracker.setReferrer(referrerName);
							customerTracker.setCustomerName(customerName);
							customerTracker.setOrderId(order.getExternalId());
							customerTracker.setDwellingType(dwellingType);
							customerTracker.setOwn(rentown);
							customerTracker.setProductName(lineItem.getLineItemDetail().getDetail().getProductLineItem().getName());
								customerTracker.setConcertPoints(points);
								customerTracker.setActualPoints(points);
							tsLineItemTrackerDataList.add(customerTracker);
							
							logger.info("tsLineItemTrackerDataList"+tsLineItemTrackerDataList);
							logger.info("tsOrderTrackerMap"+tsOrderTrackerMap);
						//}
					}
					tsOrderTrackerMap = (Map<Integer, List<CustomerTracker>>)session.getAttribute("tsOrderTrackerMap");
					logger.info("tsOrderTrackerMap="+tsOrderTrackerMap);
					if(tsOrderTrackerMap != null && tsOrderTrackerMap.size()>0){
						tsOrderTrackerMap.put(tsOrderTrackerMap.size()+1, tsLineItemTrackerDataList);
					}
					else{
						tsOrderTrackerMap = new HashMap<Integer, List<CustomerTracker>>();
						tsOrderTrackerMap.put(1, tsLineItemTrackerDataList);
					}
				}
			}
			session.setAttribute("tsOrderTrackerMap", tsOrderTrackerMap);
			//int val = getSessionSize(session);
			logger.info("tsOrderTrackerMap1="+tsOrderTrackerMap);
		}  
		catch (NumberFormatException e) {
			logger.error("Error_while_getting_product_points"+e.getMessage());
		}
		session.setAttribute("totalPoints","");
	}


	private String getCustomerName(OrderType order) {
		StringBuilder customerName = new StringBuilder();
		customerName.append(order.getCustomerInformation().getCustomer().getFirstName());
		customerName.append(" ");
		customerName.append(order.getCustomerInformation().getCustomer().getLastName());
		return customerName.toString();
	}
}
