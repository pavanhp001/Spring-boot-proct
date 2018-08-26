package com.AL.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.AL.productResults.managers.ProductResultsManager;
import com.AL.ui.util.Utils;
import com.AL.productResults.vo.ProductSummaryVO;
import com.AL.ui.builder.CartLineItemBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.dao.ProviderDao;
import com.AL.ui.dao.ReceiverMatchFeatureDao;
import com.AL.ui.domain.Provider;
import com.AL.ui.domain.ReceiverMatchFeature;
import com.AL.ui.factory.CartCKOFactory;
import com.AL.ui.factory.CartEventFactory;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.factory.CartProductFactory;
import com.AL.ui.factory.CartRulesFactory;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.service.V.ProductService;
import com.AL.ui.service.V.VOperation;
import com.AL.ui.util.CartLineItemUtil;
import com.AL.ui.util.CartUtil;
import com.AL.ui.util.JsonUtil;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.vo.Address;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.me.v4.MerchandisingRequestType.ProductList;
import com.AL.xml.pr.v4.EnterpriseResponseDocumentType;
import com.AL.xml.pr.v4.OfferTypeType;
import com.AL.xml.pr.v4.ProductInfoType;
import com.AL.xml.pr.v4.ProductRequestType.ProviderList;
import com.AL.xml.pr.v4.ProductResponseType;
import com.AL.xml.pr.v4.ProviderResults;
import com.AL.xml.pr.v4.ProviderType;
import com.AL.xml.se.v4.ServiceabilityResponse2;
import com.AL.xml.v4.ApplicableType;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;
import com.google.gson.Gson;
import com.AL.productResults.managers.RESTClientForDrupal;



@Controller("CartCKOWebFlowController")
public class CartCKOWebFlowController  implements Action {
	@Autowired
	ProviderDao providerDao;
	
	@Autowired
	ReceiverMatchFeatureDao receiverMatchFeatureDao;
	
	private static final Logger logger = Logger.getLogger(CartCKOWebFlowController.class);
	
	private static final String PROVIDER_EXTERNAL_ID = "providerExternalId";
	private static final String STATUS = "status";
	private static final String CUSTOMER_INFO = "customerInfo";
	private static final String EXISTING_CUSTOMER_DETAILS = "existingCustomerDetails";
	private static final String DISABLE = "disable";
	private static final String CUST_TYPE = "custType";
	private static final String RESOLVED = "resolved";
	private static final String NEW = "new";
	private static final String EXISTING_CUSTOMER = "existingCustomer";
	
	@SuppressWarnings("unchecked")
	public Event execute(RequestContext request) throws Exception {
		
		try{
		HttpServletRequest httpRequest =(HttpServletRequest)request.getExternalContext().getNativeRequest();
		HttpServletResponse httpResponse =(HttpServletResponse)request.getExternalContext().getNativeResponse();
		HttpSession session = httpRequest.getSession();
		String isClosingOfferFlow = request.getFlowScope().getString("isClosingOfferFlow");
		SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute("salescontext");
		Double startTime = Double.valueOf(System.currentTimeMillis());
		String orderId = httpRequest.getParameter(ControllerConstants.ORDERID);
		String customerId = httpRequest.getParameter("customerIdVal");
		/**	
		 * Building the ModelandView map for product CKO				 
		 */
		if(Utils.isBlank(orderId)) {
			orderId = salesCenterVo.getValueByName("orderId");
		}
		if(Utils.isBlank(customerId)) {
			customerId = String.valueOf(session.getAttribute("customerID"));
		}
		Map<String,Object> viewMap = CartCKOFactory.INSTANCE.getCKOMavMap(orderId, customerId, httpRequest, providerDao);
		Map<String, String> notePadHeaders = new LinkedHashMap<String, String>();
		notePadHeaders = (Map<String, String>) httpRequest.getSession().getAttribute("notePadMap");	
		if (notePadHeaders != null) {
			request.getFlowScope().put("notePadMap", notePadHeaders);
		}
		/**	
		 * Building the ModelandView Object for product CKO				 
		 */
		if(!Utils.isBlank(isClosingOfferFlow)){
			request.getFlowScope().put("isClosingOfferFromCKO", "true");
			viewMap.put(ControllerConstants.UTILITYOFFER, "true");
		}else{
			request.getFlowScope().put("isClosingOfferFromCKO", "");
		}
		craeteCKOView(request,viewMap, "cart.CKO_start",receiverMatchFeatureDao);
		commonOptions(httpResponse);
		logger.info("Completed_Building_CKO_View[orderId="+orderId+"][Time="+ CartUtil.INSTANCE.getTimeDiff(startTime) + " ms]");

		}catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception_in_CartCKOWebFlowController",e);
			throw new UnRecoverableException(e.getMessage());
		}
		return new Event(this, "addressValidationEvent");
	}
	
	
	public Address getAddress(com.AL.xml.v4.CustAddress custAdr){
		Address add = new Address();

		add.setPostfixDirectional(custAdr.getAddress().getPostfixDirectional());
		add.setPrefixDirectional(custAdr.getAddress().getPrefixDirectional());
		add.setStreetName(custAdr.getAddress().getStreetName());
		add.setStreetNumber(custAdr.getAddress().getStreetNumber());
		add.setStreetType(custAdr.getAddress().getStreetType());
		add.setLine2(custAdr.getAddress().getLine2());
		add.setCity(custAdr.getAddress().getCity());
		add.setStateOrProvince(custAdr.getAddress().getStateOrProvince());
		add.setPostalCode(custAdr.getAddress().getPostalCode());


		return add;

	}
	
	public Event addProductToOrderAndCKO(RequestContext request) throws UnRecoverableException,Exception 
	{
		logger.info("addProductToOrderAndCKO_begin_in_CartCKOWebFlowController");
		HttpServletRequest httpRequest =(HttpServletRequest)request.getExternalContext().getNativeRequest();
		HttpSession session = httpRequest.getSession();
		try{
			
		addToOrder(httpRequest);
		
		OrderType order = (OrderType)httpRequest.getSession().getAttribute("order");
		String productExternalId = (String)httpRequest.getAttribute(ControllerConstants.PRODUCTIDS);
		if(!Utils.isBlank(httpRequest.getParameter("mixedBundleTime"))){
			logger.info("addProductToOrderAndCKOForMixedBundles productExternalId="+productExternalId);
		}
		logger.info("productExternalId="+productExternalId);
		String lineItemExternalId = getLineItemExternalIdByProductId(productExternalId,order);
		logger.info("lineItemExternalId="+lineItemExternalId);
 		if(lineItemExternalId!=null)
 		{
 			httpRequest.setAttribute(ControllerConstants.LINEITEMIDS,lineItemExternalId);
 			execute(request);
 			//httpRequest.setAttribute("totalPoints",Utils.getTotalPoints(order, session));;
 			logger.info("addProductToOrderAndCKO_end_in_CartCKOWebFlowController");
 			logger.info("total pt :: "+Utils.getTotalPoints(order, session));
 			logger.info("total pointst :: "+httpRequest.getAttribute("totalPoints"));
 			request.getFlowScope().put("totalPoints",httpRequest.getAttribute("totalPoints"));
 			return new Event(this, "CKOViewEvent");
 		}
		}catch (Exception e)
		{
			request.getFlowScope().put("message", e.getMessage());
			request.getFlowScope().put("pageTitle",httpRequest.getParameter("pageTitle")!=null?httpRequest.getParameter("pageTitle"):"");
			logger.error("Error_occured_while_adding_product_to_order_and_moving_to_CKO_in_addProductToOrderAndCKO_method", e);
			throw new UnRecoverableException(e.getMessage());
		}
		logger.info("addProductToOrderAndCKO_end_in_CartCKOWebFlowController");
 		return new Event(this, "recommendationsEvent");
	}
	
	
	private String getLineItemExternalIdByProductId( String productExternalId, OrderType order ) 
	{
		if( order.getLineItems() != null && !order.getLineItems().getLineItem().isEmpty() )
		{
			for(LineItemType lineItemType :order.getLineItems().getLineItem())
			{
				if(lineItemType.getLineItemDetail() != null 
					&& lineItemType.getLineItemDetail().getDetail() !=null 
					&& lineItemType.getLineItemDetail().getDetail().getProductLineItem() != null )
				{
					if( lineItemType.getLineItemDetail().getDetail().getProductLineItem().getExternalId().equals(productExternalId)){
						if(lineItemType.getLineItemStatus()!=null 
							&& lineItemType.getLineItemStatus().getStatusCode()!=null 
							&& LineItemStatusCodesType.SALES_NEW_ORDER.value()
							.equalsIgnoreCase( lineItemType.getLineItemStatus().getStatusCode().value()))
						{
							return String.valueOf(lineItemType.getExternalId());
						}
					}
				}
			}
		}
		return null;
	}


	private void addToOrder(HttpServletRequest request)throws Exception{

		logger.info("addToOrder_begin_in_CartCKOWebFlowController");
		String productData = request.getParameter("productDataToAddOrder");
		String orderId = request.getParameter("orderId");
		String offerType = request.getParameter("productOfferTypeValue");
		HttpSession session = request.getSession();
		String agentId = CartUtil.INSTANCE.getAgentId(session);
		String mixedBundleTime = request.getParameter("mixedBundleTime");

		com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();
		LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
		Map<String,String> productUniqueIdMap = new HashMap<String, String>();

		try
		{
			if(!productData.isEmpty())
			{
				JSONObject index = new JSONObject(productData);
				JSONArray selectedArray = index.getJSONArray("lineItems");
				
				logger.info("selectedArray="+selectedArray.toString());
				logger.info("selectedArray="+selectedArray.toString());

				/*				
				 * Validates whether the selected LineItem satisfies the CartRules or Not
				 */				
				JSONArray success = CartRulesFactory.INSTANCE.cartRules(selectedArray,orderId,session);
				
				boolean isProductOnOrder = true;
				int errorCount = -1;
				Integer isAppliesTo = null;
				
				String parentExternalId = null;
				logger.info("successArray="+success);
				for(int i=0;i < success.length(); i++)
				{
					JSONObject feedback = (JSONObject) success.get(i);

					if(!feedback.has("error"+i))
					{
						boolean isPromotion = feedback.getBoolean(Constants.IS_Promotion);
						if(isPromotion)
						{
							//Adding Promotion/productPromotion 						 
							liCollection.getLineItem().add(CartLineItemBuilder.INSTANCE.
									createPromotion(feedback));
						}
						else
						{
							//Adding LinItem/product					 
							isProductOnOrder = false;
							parentExternalId = feedback.getString(Constants.PARTNER_ID);
							logger.info("LineItem Json "+feedback);
							LineItemType lineItemType = CartLineItemBuilder.INSTANCE.
							createLineItem(feedback, productUniqueIdMap);
							
							String providerName = CartLineItemUtil.validateProviderName( feedback.getString(Constants.PROVIDER_NAME_VALUE));
							
							request.setAttribute(ControllerConstants.PROVIDERNAMES, providerName);
							request.setAttribute(ControllerConstants.PROVIDERIDS, feedback.getString(Constants.PARTNER_ID));
							request.setAttribute(ControllerConstants.PRODUCTSRCS, feedback.getString(Constants.PROVIDER_SOURCE_TYPE));
							request.setAttribute(ControllerConstants.UTILITYOFFER , Constants.FALSE);
							request.setAttribute(ControllerConstants.PRODUCTIDS, feedback.getString(Constants.PRODUCT_ID));
							
							ProductResultsManager productResultManager = (ProductResultsManager)request.getSession().getAttribute("productResultManager");
							if (productResultManager.getSelectedExistingCustomerStatusMap().get(parentExternalId) != null &&
									productResultManager.getSelectedExistingCustomerStatusMap().get(parentExternalId).equalsIgnoreCase("Disable")
									&& "Transfer".equalsIgnoreCase(offerType) )
							{
								lineItemType.setIsTransfer(1);
							}
							SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute("salescontext");
							if(!Utils.isBlank(feedback.getString("hybrisShell")) && feedback.getString("hybrisShell").equalsIgnoreCase("true")) {
								String hybrisAttributes = "";
								hybrisAttributes = salesCenterVo.getValueByName("referrer.program.url");
								if(!Utils.isBlank(hybrisAttributes) && (hybrisAttributes.contains(Constants.CHARTER) || hybrisAttributes.contains(Constants.SPECTRUM))){
									String atgAffiliateId = "none";
									if (!Utils.isBlank(salesCenterVo.getValueByName("atg.affiliate.id"))) {
										atgAffiliateId = salesCenterVo.getValueByName("atg.affiliate.id");
									}			
									hybrisAttributes = hybrisAttributes + "|" + atgAffiliateId;
									String atgPhoneNumber = "none";
									if (!Utils.isBlank(salesCenterVo.getValueByName("atg.phone.number"))) {
										atgPhoneNumber = salesCenterVo.getValueByName("atg.phone.number");
									}
									hybrisAttributes = hybrisAttributes + "|" + atgPhoneNumber;
								}
								else{
									String usaaId = "none";
									if (!Utils.isBlank(salesCenterVo.getValueByName("usaa.id"))) {
										usaaId = salesCenterVo.getValueByName("usaa.id");
									}			
									hybrisAttributes = hybrisAttributes + "|" + usaaId;
									String programName = "none";
									if (!Utils.isBlank(salesCenterVo.getValueByName("referrer.program.name"))) {
										programName = salesCenterVo.getValueByName("referrer.program.name");
									}
									hybrisAttributes = hybrisAttributes + "|" + programName;
									String agentExtId = "none";
									if (!Utils.isBlank(salesCenterVo.getValueByName("agentExtId"))) {
										agentExtId = salesCenterVo.getValueByName("agentExtId");
									}	
									hybrisAttributes = hybrisAttributes + "|" + agentExtId;
								}
								lineItemType.getLineItemAttributes().getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("Hybris", hybrisAttributes, "", "HYBRIS_ATTRIBUTES"));
								logger.info("hybris lineitemattribute added");
							}
							if(!Utils.isBlank(feedback.getString("noEmail")) && feedback.getString("noEmail").equalsIgnoreCase("true")) {
								lineItemType.getLineItemAttributes().getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("suppress_utility_email", "true", "", "UTILITY_EMAIL"));
								logger.info("noEmail_lineitemattribute_added");
							}
							if(!Utils.isBlank(feedback.getString("noOrderStatus")) && feedback.getString("noOrderStatus").equalsIgnoreCase("true")) {
								lineItemType.getLineItemAttributes().getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("suppress_utility_order_status", "true", "", "UTILITY_ORDER_STATUS"));
								logger.info("noOrderStatus_lineitemattribute_added");
							}	
							if(!Utils.isBlank(mixedBundleTime)) {
								logger.info("mixedBundleTime = "+mixedBundleTime);
						        lineItemType.getLineItemAttributes().getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("mixed_bundle", mixedBundleTime, "", "MIXED_BUNDLE"));
						       }
							//CartLineItemUtil.getTotalPoints(lineItemType, session, Constants.ADD);
							
							liCollection.getLineItem().add(lineItemType);
						}
					}
					else
					{
						errorCount = i;
						isAppliesTo = feedback.getInt("isAppliesTo");
					}
				}
				
				if(isProductOnOrder && errorCount>-1)
				{
					String jsonString=(String)selectedArray.get(errorCount);

					JSONObject feedback =new JSONObject(jsonString);
					
					String providerName = CartLineItemUtil.validateProviderName( feedback.getString(Constants.PROVIDER_NAME_VALUE) );
					
					request.setAttribute(ControllerConstants.PROVIDERNAMES, providerName);
					request.setAttribute(ControllerConstants.PROVIDERIDS, feedback.getString(Constants.PARTNER_ID));
					request.setAttribute(ControllerConstants.PRODUCTSRCS, feedback.getString(Constants.PROVIDER_SOURCE_TYPE));
					request.setAttribute(ControllerConstants.UTILITYOFFER, Constants.FALSE);
					request.setAttribute(ControllerConstants.PRODUCTIDS, feedback.getString(Constants.PRODUCT_ID));
				}
				
				logger.info("AddLineItem_request_building_completed");

				if(liCollection.getLineItem().size()>0) 
				{
					if( isProductOnOrder && isAppliesTo != null )
					{
						for(LineItemType itemType : liCollection.getLineItem())
						{
							ApplicableType applies = itemType.getLineItemDetail().getDetail().getPromotionLineItem();
							applies.getAppliesTo().clear();
							applies.getAppliesTo().add(isAppliesTo);
							applies.setIsAppliesToInternal(Boolean.FALSE);
						}
					}
					/*	
					 * Getting SalesContext from the Session.				 
					 */
					SalesContextType updateSalesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);

					Double startTimeforAddLineItem = Double.valueOf(System.currentTimeMillis());
					/*	
					 * ServiceCall for adding LineItem to Order					 
					 */
					OrderType order = LineItemService.INSTANCE.addLineItem(agentId, orderId, liCollection,updateSalesContext,true);
					logger.info("TimetakenForAddLineItem[Time="+ CartUtil.INSTANCE.getTimeDiff(startTimeforAddLineItem) + "sec]");
					
					if( Constants.CENTURY_LINK.equals(parentExternalId) || Constants.COX_RTS_PROVIDER_ID.equals(parentExternalId) || Constants.TXU_RTS_PROVIDER_ID.equals(parentExternalId))
					{
						/*	
						 * ServiceCall for updating Realtime LineItemPrice					 
						 */
						order = CartProductFactory.INSTANCE.updateRealTimePricing(agentId,order,productUniqueIdMap,updateSalesContext);
					}
					if(order!=null && order.getExternalId()!=0)
					{
						request.getSession().setAttribute("order",order);
						//CartLineItemUtil.getTotalPoints(order, session);
						Utils.getTotalPoints(order, session);
					}
				}
			}
		}
		catch (Exception e) 
		{  
			logger.warn("error_while_adding_product_to_order",e);
		}
		logger.info("addToOrder_end_in_CartCKOWebFlowController");
	}
	
	
	public void craeteCKOView(RequestContext request,Map<String,Object> viewMap, String viewName,ReceiverMatchFeatureDao receiverMatchFeatureDao) throws Exception
	{
		logger.info("craeteCKOView_begin_in_CartCKOWebFlowController");
		HttpServletRequest httpRequest =(HttpServletRequest)request.getExternalContext().getNativeRequest();
		HttpSession session = httpRequest.getSession();
		
		request.getFlowScope().put(ControllerConstants.ORDERID, viewMap.get(ControllerConstants.ORDERID));
		request.getFlowScope().put(ControllerConstants.CUSTOMERID, viewMap.get(ControllerConstants.CUSTOMERID));
		request.getFlowScope().put(ControllerConstants.LINEITEMID, viewMap.get(ControllerConstants.LINEITEMID));
		request.getFlowScope().put(ControllerConstants.PROVIDERID, viewMap.get(ControllerConstants.PROVIDERID));
		request.getFlowScope().put(ControllerConstants.PRODUCTIDS, viewMap.get(ControllerConstants.PRODUCTIDS));
		request.getFlowScope().put(ControllerConstants.RECEIVER_MATCHES, viewMap.get(ControllerConstants.RECEIVER_MATCHES));
		request.getFlowScope().put(ControllerConstants.PROVIDERNAMES, viewMap.get(ControllerConstants.PROVIDERNAMES));
		request.getFlowScope().put(ControllerConstants.UTILITYOFFER, viewMap.get(ControllerConstants.UTILITYOFFER));
		request.getFlowScope().put(ControllerConstants.KEY, viewMap.get(ControllerConstants.KEY).toString());
		request.getFlowScope().put(ControllerConstants.URL, viewMap.get(ControllerConstants.URL).toString());
		request.getFlowScope().put(ControllerConstants.PLAN_POINTS, viewMap.get(ControllerConstants.PLAN_POINTS).toString());
		session.setAttribute(ControllerConstants.ORDER, viewMap.get(ControllerConstants.ORDER));
		session.setAttribute(ControllerConstants.LINEITEMLIST, viewMap.get(ControllerConstants.LINEITEMLIST));
		session.setAttribute(ControllerConstants.ADDRESS,getAddress((com.AL.xml.v4.CustAddress)viewMap.get(ControllerConstants.ADDRESS)));
		if(session.getAttribute("newAttv6LineItemExtID")!= null){
			session.setAttribute("newAttv6LineItemExtID", null);
		}
		String isClosingOfferFlow = request.getFlowScope().getString("isClosingOfferFlow");
		Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)session.getAttribute("dynamicFlowContextMap");
		ObjectMapper mapper = new ObjectMapper();
		//converting map to JSON string
		String dynamicFlowContextMap = mapper.writeValueAsString(contextMap);
		request.getFlowScope().put("dynamicFlowContextMap", dynamicFlowContextMap);
		
		request.getFlowScope().put(EXISTING_CUSTOMER, Constants.FALSE);
		
		request.getFlashScope().put("CKOCompletedLineItems" , LineItemUtil.prepareCKOCompletedLinItemsListFromOrder( 
																	(OrderType)viewMap.get(ControllerConstants.ORDER)) );
		SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute("salescontext");
		Map<String, String> dynamicFlow = null;
		if (contextMap != null){
			dynamicFlow = contextMap.get("dynamicFlow");
		}
		if( viewMap.get(ControllerConstants.UTILITYOFFER).equals(Constants.TRUE))
		{
			if (dynamicFlow != null){
				dynamicFlow.put("dynamicFlow.page", "Utility");
			}
			String feebBackJson = "\"feedbackMap\":\"\"";
			Map<String, String> partnerSpecificDataMap = (Map<String, String>)httpRequest.getSession().getAttribute("partnerSpecificDataMap");
			logger.info("PartnerSpecificDataMap="+partnerSpecificDataMap);
			
			
            String refferID  = salesCenterVo.getValueByName("referrer.businessParty.referrerId");
			if("33933".equalsIgnoreCase(refferID) && partnerSpecificDataMap != null && partnerSpecificDataMap.size() > 0){
				final JsonUtil<Map> util = new JsonUtil<Map>();
				feebBackJson =  util.convert(partnerSpecificDataMap, "feedbackMap", Map.class);
				if(!Utils.isBlank(feebBackJson)){
					feebBackJson = feebBackJson.substring(feebBackJson.indexOf("{")+1, feebBackJson.lastIndexOf("}"));
				}
			}else if(partnerSpecificDataMap != null && partnerSpecificDataMap.get("Prior_Enroll_Surge") != null){
				JsonUtil<Map> util = new JsonUtil<Map>();
				Map<String,String> partnerMap = new HashMap<String,String>();
				partnerMap.put("PriorEnrollSurge", partnerSpecificDataMap.get("Prior_Enroll_Surge"));
				feebBackJson =  util.convert(partnerMap, "feedbackMap", Map.class);
				if(!Utils.isBlank(feebBackJson)){
					feebBackJson = feebBackJson.substring(feebBackJson.indexOf("{")+1, feebBackJson.lastIndexOf("}"));
				}
			}
			logger.info("RefferID="+refferID+"_FeebBackJson="+feebBackJson);
			request.getFlowScope().put("feedbackMap",feebBackJson);
			request.getFlowScope().put(ControllerConstants.TITLE, ControllerConstants.UTILITY_TITLE);
			
			if(!Utils.isBlank(isClosingOfferFlow)){
				request.getFlowScope().put(ControllerConstants.TITLE, ControllerConstants.CLOSING_OFFER_TITLE);
			}
			
			String isDominionOffer = (String)session.getAttribute("isDominionOffer");
			
			if(!Utils.isBlank(isClosingOfferFlow) && !Utils.isBlank(isDominionOffer)&& isDominionOffer.equalsIgnoreCase("true")){
				request.getFlowScope().put(ControllerConstants.TITLE, ControllerConstants.DOMINION_OFFER_TITLE);
			}
		}
		else
		{
			List<ReceiverMatchFeature> receiversList = receiverMatchFeatureDao.getAllReceiverMatchFeature();
			logger.info("receiversList="+receiversList);
			if(receiversList!= null && !receiversList.isEmpty() ){
				List<String> list = new ArrayList<String>();
				for(ReceiverMatchFeature  rcvMatch :receiversList){
					list.add(rcvMatch.getFeatureExternalId());
				}
				request.getFlowScope().put("receiversList", new Gson().toJson(list).replaceAll("\"", "'"));
		    }else{
		    	request.getFlowScope().put("receiversList", "");
		    }
			if (dynamicFlow != null){
				dynamicFlow.put("dynamicFlow.page", "CKO");
			}
			request.getFlowScope().put(ControllerConstants.TITLE, ControllerConstants.CKO_TITLE);
		}
		
		if(viewMap.get(ControllerConstants.ISRECOMMENDATION) != null)
		{
			request.getFlowScope().put(ControllerConstants.ISRECOMMENDATION, viewMap.get(ControllerConstants.ISRECOMMENDATION));
		}
		else
		{
			request.getFlowScope().put(ControllerConstants.ISRECOMMENDATION, Constants.FALSE);
		}
		ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");	
		if(productResultManager != null){
			String points =(String)productResultManager.getProductPoints((String)viewMap.get(ControllerConstants.PRODUCTIDS));
			if(!Utils.isBlank(points)){
				request.getFlowScope().put("productPoints", points);
			}else{
				request.getFlowScope().put("productPoints", "NA");
			}
		}
		
		logger.info("craeteCKOView_end_in_CartCKOWebFlowController");
	}
	
	
	public void commonOptions(HttpServletResponse theHttpServletResponse) {
		theHttpServletResponse.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with, my-cool-header");
		theHttpServletResponse.addHeader("Access-Control-Max-Age", "60");  
		theHttpServletResponse.addHeader("Access-Control-Allow-Methods","GET, POST, OPTIONS");
		theHttpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
	}
	
	
	/**
	 * Intermediate termination of CKO. Updating Line Item attribute status as CKOIncomplete.
	 * 
	 * 
	 * @param RequestContext
	 * @return Event
	 */
	public Event cancelCKO(RequestContext req) throws Exception{
		StopWatch timer=new StopWatch();
		timer.start();
		try{
			long startTimer=0;
			logger.info("cancelCKO_begin_in_CartCKOWebFlowController");
			
			HttpServletRequest request =(HttpServletRequest)req.getExternalContext().getNativeRequest();
			Double startTime = Double.valueOf(System.currentTimeMillis());
			
			String lineItemId = request.getParameter("linItemInCKO");
			String isUtilityOffer = request.getParameter("isUtilityOffer");
			String isRecommendation = request.getParameter("isRecommendation");
			HttpSession session = request.getSession();
			SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute("salescontext");
			String agentId = salesCenterVo.getValueByName("agent.id");
			String orderId = salesCenterVo.getValueByName("order.id");
	        String callView = req.getFlowScope().getString("callView");
	        String isClosingOfferFlow = req.getFlowScope().getString("isClosingOfferFlow");
	        
	        if (session.getAttribute("pauseAndResumeURL")!= null && session.getAttribute("phoneId")!= null){
	        	RESTClientForDrupal.INSTANCE.resumeCall(String.valueOf(session.getAttribute("phoneId")), String.valueOf(session.getAttribute("pauseAndResumeURL")));
			}
	        
	        if(request.getSession().getAttribute("newAttv6LineItemExtID")!= null){
	        	String activeLineItemId = (String) request.getSession().getAttribute("newAttv6LineItemExtID");
	        	if(! activeLineItemId.equals(lineItemId)){
	        		lineItemId = activeLineItemId;
	        	}
	        	request.getSession().setAttribute("newAttv6LineItemExtID", null);
	        }
			com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();
			/**	
			 * Getting SalesContext from the session				 
			 */
			SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
			
			startTimer=timer.getTime();
	
			OrderType order = OrderService.INSTANCE.getOrderByOrderNumber(orderId,agentId,new HashMap<String, Object>(),"*", Boolean.FALSE, salesContext);
			logger.info("TimeTakenForOrderServiceCall="+(timer.getTime()-startTimer));
			
			if(isUtilityOffer.equalsIgnoreCase(Constants.TRUE))
			{
				String productExtID = request.getParameter("productExtIds");

				logger.info("productExtID="+productExtID);
				LineItemType utilityOfferlineItem = LineItemUtil.getUtilityOfferLineItem(order, productExtID);

				logger.info("utilityOfferlineItem="+utilityOfferlineItem);
				if (utilityOfferlineItem != null) {
					
					LineItemType lineItemType = oFactory.createLineItemType();
					LineItemAttributeType lineItemAttributeType = oFactory.createLineItemAttributeType();
					
					lineItemType.setExternalId(utilityOfferlineItem.getExternalId());
					lineItemType.setLineItemNumber(utilityOfferlineItem.getLineItemNumber());
					
					// Adding the LineItem Attribute CKO_INCOMPLETE
					lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType(Constants.STATUS,Constants.CKO_INCOMPLETE,"CKO TERMINATED", Constants.CKO));

					lineItemType.setLineItemAttributes(lineItemAttributeType);
					LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
					liCollection.getLineItem().add(lineItemType);

					// Updating the LineItemAttribute
					startTimer = timer.getTime();
					order = LineItemService.INSTANCE.updateLineItem(agentId,orderId, VOperation.updateLineItem.toString(),liCollection, salesContext);
					logger.info("TimeTakenForUpdateLineItemServiceCall="+ (timer.getTime() - startTimer));
				}
			}
			else
			{
				/**	
				 * Builds the response for UpdateLineItem 			 
				 */
				LineItemCollectionType liCollection = CartProductFactory.INSTANCE.getProductDetails(order, request);
	
				/**	
				 * Adds the LineItemAttribute for which the LineItemStatus has been changed.			 
				 */
				for(LineItemType linItem : liCollection.getLineItem())
				{
					if(linItem.getExternalId() == Long.valueOf(lineItemId))
					{
						LineItemAttributeType lineItemAttributeType=oFactory.createLineItemAttributeType();
						lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType(Constants.STATUS, Constants.CKO_INCOMPLETE,
								"CKO TERMINATED", Constants.CKO));	
						linItem.setLineItemAttributes(lineItemAttributeType);
					}
				}
	
				/*	
				 * ServiceCall for updating LineItemAttributes to CKOReady				 
				 */
				startTimer=timer.getTime();
				order = LineItemService.INSTANCE.updateLineItem(agentId, orderId, VOperation.updateLineItem.toString(), liCollection, salesContext);
				logger.info("TimetakenforUpdateLineItemServiceCall="+(timer.getTime()-startTime));
			}
			if(order != null){
				session.setAttribute("order",order);
			}
			if(isUtilityOffer.equalsIgnoreCase(Constants.TRUE) && !Utils.isBlank(isClosingOfferFlow) && !Utils.isBlank(callView)){
				logger.info("Cancel_in_CloseUtilityOffer_redirecting_to_qualification[orderId="+orderId+"][Time="+ CartUtil.INSTANCE.getTimeDiff(startTime) + " ms]");
				return new Event(this ,callView);
			}else if(isUtilityOffer.equalsIgnoreCase(Constants.TRUE) && isRecommendation.equalsIgnoreCase(Constants.FALSE))
			{
				//Redirecting to qualification
				logger.info("Cancel_in_UtilityOffer_redirecting_to_qualification[orderId="+orderId+"][Time="+ CartUtil.INSTANCE.getTimeDiff(startTime) + " ms]");
				return new Event(this ,"qualificationEvent");
			}
			else if(isUtilityOffer.equalsIgnoreCase(Constants.TRUE) && isRecommendation.equalsIgnoreCase(Constants.TRUE))
			{
				//Redirecting to Recommendations
				logger.info("Cancel_in_UtilityOffer_redirecting_to_Recommendations[orderId="+orderId+"][Time="+ CartUtil.INSTANCE.getTimeDiff(startTime) + " ms]");
				return new Event(this ,"recommendationsEvent");
			}
			else
			{
				//Redirecting to OrderSummary
				logger.info("Cancel_in_CKO_redirecting_to_OrderSummary[orderId="+orderId+"][Time="+ CartUtil.INSTANCE.getTimeDiff(startTime) + " ms]");
				return  CartEventFactory.INSTANCE.createOrderView(agentId, order, ControllerConstants.ORDER_SUMMARY_VIEW, ControllerConstants.ORDER_SUMMARY_TITLE, req);
			}
		}finally
		{
			timer.stop();
		}
	}
	
	
	
	/**
	 * Prepare existing customer CKO view.
	 *  
	 * @param request
	 * @throws UnRecoverableException
	 */
	public void showExistingCustomerCKOView(RequestContext request) throws UnRecoverableException 
	{
		StopWatch timer = new StopWatch();
		timer.start();
		logger.info("showExistingCustomerCKOView_begin");
		HttpServletRequest httpRequest = (HttpServletRequest) request.getExternalContext().getNativeRequest();
		HttpSession session = httpRequest.getSession();
		try
		{
			String providerExternalId = httpRequest.getParameter(PROVIDER_EXTERNAL_ID);
			String orderId = httpRequest.getParameter(ControllerConstants.ORDERID);
			String customerId = httpRequest.getParameter(ControllerConstants.CUSTOMERID);
			String mamAddress = httpRequest.getParameter("mamAddress");
			String pageTitleName =  httpRequest.getParameter("pageTitleName");
			String isexistingCustomer = httpRequest.getParameter("isexistingCustomer");
			OrderType order = (OrderType)session.getAttribute(ControllerConstants.ORDER);
			String keys = null;
			String urls = null;
			try{
				Provider provider = providerDao.get(Long.valueOf(providerExternalId));
				keys = provider.getCertificate() ;
				urls = provider.getUrl();
			}catch(Exception e)
			{
				e.printStackTrace();
				logger.error("error_while_getting_provider_CKO_url", e);
				keys = "ABC123";
				urls = "http://CKO-url-not-found";
			}
			
			ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");	
			String providerName = getProviderNameByProviderId(providerExternalId, productResultManager.getSelectedExistingProvidersMap());
			request.getFlowScope().put(ControllerConstants.ORDERID, orderId);
			request.getFlowScope().put(ControllerConstants.CUSTOMERID, customerId);
			request.getFlowScope().put(ControllerConstants.LINEITEMID, "");
			request.getFlowScope().put(ControllerConstants.PROVIDERID, providerExternalId);
			request.getFlowScope().put(ControllerConstants.PROVIDERNAMES, providerName);
			request.getFlowScope().put(ControllerConstants.KEY, keys );
			request.getFlowScope().put(ControllerConstants.URL, urls );
			request.getFlowScope().put(ControllerConstants.TITLE, ControllerConstants.CKO_TITLE);
			request.getFlowScope().put(ControllerConstants.ISRECOMMENDATION, Constants.FALSE);
			request.getFlowScope().put(ControllerConstants.UTILITYOFFER, Constants.FALSE);
			if(mamAddress != null && !Utils.isBlank(mamAddress)){
				request.getFlowScope().put(EXISTING_CUSTOMER, Constants.FALSE);
				request.getFlowScope().put("mamAddress", mamAddress);
			}else{
				request.getFlowScope().put(EXISTING_CUSTOMER, Constants.TRUE);
			}
			if(isexistingCustomer != null && !Utils.isBlank(isexistingCustomer) ){
				request.getFlowScope().put(EXISTING_CUSTOMER, Constants.TRUE);
			}
			request.getFlashScope().put("CKOCompletedLineItems", LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(order) );
			request.getFlowScope().put("pageTitleName", pageTitleName );
			session.setAttribute("pageTitleName", pageTitleName);
			session.setAttribute(ControllerConstants.LINEITEMLIST, CartLineItemFactory.INSTANCE.sortLineItemBasedOnStatus(order));
			session.setAttribute(ControllerConstants.ADDRESS, getAddress( CartLineItemUtil.getAddress(order.getCustomerInformation().getCustomer(),
														com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString())) );
			logger.info("time_taken_to_execute_showExistingCustomerCKOView_method:"+timer.getTime());
		}
		catch(Exception e)
		{
			request.getFlowScope().put("message", e.getMessage());
			request.getFlowScope().put("pageTitle",httpRequest.getParameter("pageTitle")!=null?httpRequest.getParameter("pageTitle"):"");
			logger.error("error_in_showExistingCustomerCKOView_method",e);
			throw new UnRecoverableException(e.getMessage());
		}
		finally
		{
			timer.stop();
		}
	}
	
	
	
	/**
	 * We are doing product service call depending on status which is coming from CKOs.
	 * 
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/authenticateExistingCustomer")
	public @ResponseBody void authenticateExistingCustomer(HttpServletRequest request)throws Exception
	{
		try{
			logger.info("authenticatExistingCustomer_begin_in_CartCKOWebFlowController");
			String authenticateJSON = request.getParameter("authenticateJSON");
			HttpSession session = request.getSession();
			ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");	
			logger.info("authenticateJSON="+authenticateJSON);
			JSONObject authenticateECJSON = new JSONObject(authenticateJSON);
			
			logger.info("authenticateJSON="+authenticateJSON);
			
			//the below conditional statements will executes when status as resolved/new.  
			if( authenticateECJSON.length() > 0 && RESOLVED.equalsIgnoreCase( authenticateECJSON.getString(STATUS) ))
			{
				String providerExternalId = authenticateECJSON.getString(PROVIDER_EXTERNAL_ID);
				if( !productResultManager.getSelectedExistingProvidersMap().isEmpty() )
				{
					removeProviderDetailsFromExistingProviders(providerExternalId,productResultManager.getSelectedExistingProvidersMap());
					 session.setAttribute("removedExistingProvidersMapAfterAuth",providerExternalId);
					productResultManager.getSelectedExistingCustomerStatusMap().put(providerExternalId,DISABLE);
					if( authenticateECJSON.get(CUSTOMER_INFO)!=null )
					{
						JSONObject customerInfo = authenticateECJSON.getJSONObject(CUSTOMER_INFO);
						if( customerInfo.length() > 0 )
						{
							session.setAttribute(EXISTING_CUSTOMER_DETAILS, customerInfo);
						}
					}
					String customerType = authenticateECJSON.getString(CUST_TYPE);
					doProductServiceCallForExistingProvider( providerExternalId, customerType, productResultManager );
				}
			}
			else if( authenticateECJSON.length() > 0 && NEW.equalsIgnoreCase( authenticateECJSON.getString(STATUS) ))
			{
				String providerExternalId = authenticateECJSON.getString(PROVIDER_EXTERNAL_ID);
				removeProviderDetailsFromExistingProviders(providerExternalId,productResultManager.getSelectedExistingProvidersMap());
				
				if(productResultManager.getSelectedExistingCustomerStatusMap().containsKey(providerExternalId))
				{
					productResultManager.getSelectedExistingCustomerStatusMap().remove(providerExternalId);
				}

				if( authenticateECJSON.get(CUSTOMER_INFO)!=null )
				{
					JSONObject customerInfo = authenticateECJSON.getJSONObject(CUSTOMER_INFO);
					if( customerInfo.length() > 0 )
					{
						session.setAttribute(EXISTING_CUSTOMER_DETAILS, customerInfo);
					}
				}
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		logger.info("authenticatExistingCustomer_end_in_CartCKOWebFlowController");
	}
	
	@RequestMapping(value = "/getProductsFromSE2Response")
	public @ResponseBody String getProductsSE2Response(HttpServletRequest request)throws Exception
	{
		String pageTitleName = "";
		try{
			logger.info("authenticatExistingCustomer_begin_in_CartCKOWebFlowController");
			String mamStatus = request.getParameter("mamStatus");
			HttpSession session = request.getSession();
			ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");	
			JSONObject mamStatusJSON = new JSONObject(mamStatus);
			logger.info("mamStatusJSON="+mamStatusJSON);
			 pageTitleName = (String)session.getAttribute("pageTitleName");
			 SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute("salescontext");
			//the below conditional statements will executes when status as resolved/new.  
		if( mamStatus.length() > 0 && "SUCCESS".equalsIgnoreCase(mamStatusJSON.getString(STATUS))){
				String providerExternalId = mamStatusJSON.getString(PROVIDER_EXTERNAL_ID);
				productResultManager.getSelectedExistingCustomerStatusMap().put(providerExternalId,DISABLE);
				if(providerExternalId != null){
					List<String> providerList = new ArrayList<String>();
					if(session.getAttribute("ListOfProviderIds") != null){
						providerList = (List<String>)session.getAttribute("ListOfProviderIds");
						providerList.add(providerExternalId);
						session.setAttribute("ListOfProviderIds",providerList);
					}else{
						providerList.add(providerExternalId);
						session.setAttribute("ListOfProviderIds",providerList);
					}
				}
				request.getSession().setAttribute("isMAMSuccess", request.getParameter("isMAMSuccess"));
				
				
				getProductsFromSE2Response( providerExternalId, productResultManager, mamStatusJSON.getString("addressBlock"),mamStatusJSON.getString("guid"),mamStatusJSON.getString(STATUS), salesCenterVo);
				request.getSession().setAttribute("MAMproductsUpdated", true);
			}else if(mamStatus.length() > 0 &&  !"SUCCESS".equalsIgnoreCase(mamStatusJSON.getString(STATUS))){
				String providerExternalId = mamStatusJSON.getString(PROVIDER_EXTERNAL_ID);
			if(mamStatusJSON.getString(STATUS).equalsIgnoreCase("none")){
				getProductsFromResultsManager( providerExternalId,  productResultManager , mamStatusJSON.getString(STATUS),  salesCenterVo); 
			}else{
				productResultManager.updateRealTimeStatusForMAMToDispalyATTandDirecTV(productResultManager.getRealTimeStatusMap(), productResultManager.context.getAllProductList(),providerExternalId,mamStatusJSON.getString(STATUS));
			}
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		logger.info("authenticatExistingCustomer_end_in_CartCKOWebFlowController");
		
		return pageTitleName;
	}
	
	/**
	 * Doing product service call based on customer type which is coming from CKOs.
	 * @param providerExternalId
	 * @param customerType
	 * @param productResultManager
	 */
	private void doProductServiceCallForExistingProvider( String providerExternalId, String customerType, ProductResultsManager productResultManager ) 
	{
		long startTimer=0;
		StopWatch timer=new StopWatch();
		timer.start();
		try{
			logger.info("doProductServiceCallForExistingProvider_begin.");
			ProviderType providerType = new ProviderType();
			boolean isReqProductServiceCall = false;
			if(!OfferTypeType.NEW.value().equalsIgnoreCase(customerType))
			{
				for (OfferTypeType offerType :OfferTypeType.values())
				{
					if(offerType.value().equalsIgnoreCase(customerType))
					{
						providerType.setOfferType(offerType);
						isReqProductServiceCall = true;
						break;
					}
				}
			}
			if(!isReqProductServiceCall)
			{
				return ;
			}
			logger.info("customerType="+customerType);
			ProviderList providerList = new ProviderList();
			logger.info("preparing_provider_list_to_send_along_with_product_service_call.");
			String providerName = productResultManager.getProviderNamesAndExtIdsMap().get(providerExternalId);
			providerType = productResultManager.addRtimProvidersToProvidersList(providerExternalId, providerType, providerName);
			providerType.getParent().setName(providerName);
			providerType.getParent().setExternalId(providerExternalId);
			
			providerList.getProvider().add(providerType);
			
			ServiceabilityResponse2 sre = (ServiceabilityResponse2)productResultManager.getSarRes().getResponse();
			logger.info("product_service_call_for_existing_customer.");
			startTimer = timer.getTime();
			EnterpriseResponseDocumentType response = ProductService.INSTANCE.getProducts( providerList, 
					productResultManager.getAddress( sre.getCorrectedAddress()), productResultManager.getSarRes().getGUID(),
					productResultManager.getSalesContext(), productResultManager.getServiceabilityTransactionType() );
			logger.info("TimeTakenforGetProductsServiceCall="+(timer.getTime() - startTimer));
			
			ProductList productList = new ProductList();
			List<ProductSummaryVO> productVOList = new ArrayList<ProductSummaryVO>();
			if (response != null) 
			{
				ProductResponseType productResponseType = (ProductResponseType) response.getResponse();
				List<ProviderResults> results = productResponseType.getProviderResults();
				logger.info("preparing_existing_customer_products.");
				for (ProviderResults rs : results)
				{
					for(ProductInfoType prodInfo : rs.getProductInfo())
					{
						ProductSummaryVO productVo = productResultManager.getProduct(prodInfo);
						if (productResultManager.isMetaDataOwnOrRent(productVo,productResultManager.context.getRentOrOwn()))
						{
							productResultManager.addMerchandisedProduct(productList, prodInfo);
							productResultManager.checkTPVandWarmTransferOnProduct(productVo);
							productVOList.add(productVo);
						}
					}
				}
			}
			
			if( !productList.getProduct().isEmpty() )
			{
				productVOList = productResultManager.getMerchandising(productList, productVOList, 
						productResultManager.getSarRes().getGUID(), productResultManager.getSalesContext());	
				
				List<ProductSummaryVO> allProductVOList = productResultManager.context.getAllProductList();
				
				List<ProductSummaryVO> updatedProductVOList = new ArrayList<ProductSummaryVO>();
				logger.info("preparing_new_productVOList_for_existing_customer.");
				for(ProductSummaryVO productSummaryVO : allProductVOList)
				{
					if( !productSummaryVO.getProviderExternalId().equalsIgnoreCase(providerExternalId) )
					{
						updatedProductVOList.add(productSummaryVO);
					}
				}
				updatedProductVOList.addAll(addSellableProductsToProductVOList(productVOList));
				productResultManager.context.setAllProductList(updatedProductVOList);
				
				logger.info("updated_new_productVOList_on_productResultManager_for_existing_customer.");
				productResultManager.productByIconMapData(productResultManager.context.getAllProductList());
				//productResultManager.populateProductsData();
				if(productResultManager.getHideHughesNet() != null && productResultManager.getHideHughesNet().equalsIgnoreCase("true")){
					boolean isHughesNetServiceable = productResultManager.isHughesNetServiceable();
					productResultManager.setHNProductShow(isHughesNetServiceable);
				}
				List<ProductSummaryVO> powerPitchSyntheticBundlesList = productResultManager.populateSyntheticBundlesData();
				productResultManager.populatePowerPitchData(powerPitchSyntheticBundlesList); //Need to call this method based on flow
				productResultManager.productByIconMapData(productResultManager.context.getAllProductList());
				productResultManager.populateProductsData();
				productResultManager.populatePivotAssistData();
				logger.info("doProductServiceCallForExistingProvider_end.");
			}
		}catch (Exception e) 
		{
			logger.warn("error_in_doProductServiceCallForExistingProvider_method", e);
		}
		finally
		{
			timer.stop();
		}
	}
	
	
	private List<ProductSummaryVO> addSellableProductsToProductVOList( List<ProductSummaryVO> productVOList ) {
		List<ProductSummaryVO> sellableProductVOList = new ArrayList<ProductSummaryVO>();
		for (ProductSummaryVO productVO : productVOList){
			if (productVO != null && productVO.isSellable()){
				sellableProductVOList.add(productVO);
			}
		}
		return sellableProductVOList;
	}
	

	/**
	 * Removing provider name and externalId from "selectedExistingProvidersMap". 
	 * @param providerExternalId
	 * @param selectedExistingProviders
	 */
	private void removeProviderDetailsFromExistingProviders(String providerExternalId, HashMap<String, String> selectedExistingProviders)
	{
		String providerName = getProviderNameByProviderId(providerExternalId,selectedExistingProviders);
		if( !Utils.isBlank(providerName) )
		{
			selectedExistingProviders.remove(providerName);
		}
	}

	

	/**
	 * Getting provider name by using provider External Id from "selectedExistingProviders" map
	 * @param selectedExistingProviders 
	 * @param providerExternalId 
	 * @return
	 */
	private String getProviderNameByProviderId(String providerExternalId, HashMap<String, String> selectedExistingProviders) 
	{
		String providerName = null;
		for(Entry<String, String> existingProviders: selectedExistingProviders.entrySet())
		{
			if(existingProviders.getValue().equals(providerExternalId))
			{
				providerName = existingProviders.getKey();
				break;
			}
		}
		
		return providerName;
	}
	private void getProductsFromSE2Response( String providerExternalId, ProductResultsManager productResultManager ,String addressBlock,String guid,String status, SalesCenterVO salesCenterVo) 
	{
		long startTimer=0;
		StopWatch timer=new StopWatch();
		timer.start();
		try{
			logger.info("getProductsFromSE2Response_begin.");
			ProviderType providerType = new ProviderType();
			providerType.setOfferType(OfferTypeType.NEW);
			ProviderList providerList = new ProviderList();
			logger.info("preparing_provider_list_to_send_along_with_product_service_call_for_MAM.");
			String providerName = productResultManager.getProviderNamesAndExtIdsMap().get(providerExternalId);
			providerType = productResultManager.addRtimProvidersToProvidersList(providerExternalId, providerType, providerName);
			providerType.getParent().setName(providerName);
			providerType.getParent().setExternalId(providerExternalId);
			
			providerList.getProvider().add(providerType);
			
			ServiceabilityResponse2 sre = (ServiceabilityResponse2)productResultManager.getSarRes().getResponse();
			logger.info("product_service_call_for_mam.");
			startTimer = timer.getTime();
			// sre.getCorrectedAddress()
			EnterpriseResponseDocumentType response = ProductService.INSTANCE.getProducts( providerList, 
					productResultManager.getAddress( sre.getCorrectedAddress()), productResultManager.getSarRes().getGUID(),
					productResultManager.getSalesContext(), productResultManager.getServiceabilityTransactionType() );
			logger.info("TimeTakenforGetProductsServiceCall="+(timer.getTime() - startTimer));
			
			ProductList productList = new ProductList();
			List<ProductSummaryVO> productVOList = new ArrayList<ProductSummaryVO>();
			if (response != null) 
			{
				ProductResponseType productResponseType = (ProductResponseType) response.getResponse();
				List<ProviderResults> results = productResponseType.getProviderResults();
				logger.info("preparing_products_for_MAM.");
				for (ProviderResults rs : results)
				{
					for(ProductInfoType prodInfo : rs.getProductInfo())
					{
						ProductSummaryVO productVo = productResultManager.getProduct(prodInfo);
							productResultManager.addMerchandisedProduct(productList, prodInfo);
							productResultManager.checkTPVandWarmTransferOnProduct(productVo);
							productVOList.add(productVo);
							
						if(productResultManager.getMultiAddrProviderList() != null && !productResultManager.getMultiAddrProviderList().isEmpty()){
							if(productResultManager.getMultiAddrProviderList().contains(productVo.getProviderExternalId())){
								productResultManager.getMultiAddrProviderList().remove(productVo.getProviderExternalId());
					}
				}
			}
				}
			}
			logger.info("MultiAddrProviderList="+productResultManager.getMultiAddrProviderList());
			
			if( !productList.getProduct().isEmpty() )
			{
				productVOList = productResultManager.getMerchandising(productList, productVOList, 
						productResultManager.getSarRes().getGUID(), productResultManager.getSalesContext());	
				
				List<ProductSummaryVO> allProductVOList = productResultManager.context.getAllProductList();
				
				List<ProductSummaryVO> updatedProductVOList = new ArrayList<ProductSummaryVO>();
				logger.info("preparing_new_productVOList_for_MAM.");
				for(ProductSummaryVO productSummaryVO : allProductVOList)
				{
					if( !productSummaryVO.getProviderExternalId().equalsIgnoreCase(providerExternalId) )
					{
						updatedProductVOList.add(productSummaryVO);
					}
				}
				updatedProductVOList.addAll(addSellableProductsToProductVOList(productVOList));
				productResultManager.context.setAllProductList(updatedProductVOList);
				
				logger.info("updated_new_productVOList_on_productResultManager_for_MAM.");
				
				List<ProductSummaryVO> powerPitchSyntheticBundlesList = productResultManager.populateSyntheticBundlesData();
				productResultManager.populatePowerPitchData(powerPitchSyntheticBundlesList);
				productResultManager.updateRealTimeStatusForMAMToDispalyATTandDirecTV(productResultManager.getRealTimeStatusMap(), productResultManager.context.getAllProductList(),providerExternalId,status);
				productResultManager.productByIconMapData(productResultManager.context.getAllProductList());
				productResultManager.populateProductsData();
				try{
					String unitType = null;
					String unitNumber = null;
					if(!Utils.isBlank(salesCenterVo.getValueByName("unit.type")) || !Utils.isBlank(salesCenterVo.getValueByName("unitType"))){
						if(!Utils.isBlank(salesCenterVo.getValueByName("unit.type"))){
							unitType = salesCenterVo.getValueByName("unit.type");
						}else{
							unitType = salesCenterVo.getValueByName("unitType");
						}
					}
					if(!Utils.isBlank(salesCenterVo.getValueByName("unit.number")) || !Utils.isBlank(salesCenterVo.getValueByName("unitNum"))){
						if(!Utils.isBlank(salesCenterVo.getValueByName("unit.number"))){
							unitNumber = salesCenterVo.getValueByName("unit.number");
						}else{
							unitNumber = salesCenterVo.getValueByName("unitNum");
						}
					}
					if(!Utils.isBlank(unitType) && !Utils.isBlank(unitNumber)){
						productResultManager.populateMDURoadMapPowerPitchData(); //Need to call this method based on flow 
					}
					else{
						productResultManager.populateSDURoadMapPowerPitchData(); //Need to call this method based on flow 
					}
				}
				catch(Exception e){
					logger.warn("Exception while populating RoadMap PowerPitch Data in CartCKOWebFlowController "+e.getMessage());
				}
				logger.info("doProductServiceCallForExistingProvider_end.");
			}
		}catch (Exception e) 
		{
			logger.warn("error_in_getProductsFromSE2Response_method", e);
		}
		finally
		{
			timer.stop();
		}
	}
	
	private void getProductsFromResultsManager( String providerExternalId, ProductResultsManager productResultManager ,String status, SalesCenterVO salesCenterVo) 
	{
		try{ 
				List<ProductSummaryVO> allProductVOList = productResultManager.context.getAllProductList();
				List<ProductSummaryVO> updatedProductVOList = new ArrayList<ProductSummaryVO>();
				logger.info("preparing_products_in_getProductsFromResultsManager");
				for(ProductSummaryVO productSummaryVO : allProductVOList)
				{
					if( productSummaryVO.getProviderExternalId().equalsIgnoreCase(providerExternalId) )
					{
						productSummaryVO.setSellable(true);
						updatedProductVOList.add(productSummaryVO);
					}
					else{
						updatedProductVOList.add(productSummaryVO);
					}
				}
				productResultManager.context.setAllProductList(updatedProductVOList);
				List<ProductSummaryVO> powerPitchSyntheticBundlesList = productResultManager.populateSyntheticBundlesData();
				productResultManager.populatePowerPitchData(powerPitchSyntheticBundlesList);
				productResultManager.updateRealTimeStatusForMAMToDispalyATTandDirecTV(productResultManager.getRealTimeStatusMap(), productResultManager.context.getAllProductList(),providerExternalId,status);
				productResultManager.productByIconMapData(productResultManager.context.getAllProductList());
				productResultManager.populateProductsData();
				try{
					String unitType = null;
					String unitNumber = null;
					if(!Utils.isBlank(salesCenterVo.getValueByName("unit.type")) || !Utils.isBlank(salesCenterVo.getValueByName("unitType"))){
						if(!Utils.isBlank(salesCenterVo.getValueByName("unit.type"))){
							unitType = salesCenterVo.getValueByName("unit.type");
						}else{
							unitType = salesCenterVo.getValueByName("unitType");
						}
					}
					if(!Utils.isBlank(salesCenterVo.getValueByName("unit.number")) || !Utils.isBlank(salesCenterVo.getValueByName("unitNum"))){
						if(!Utils.isBlank(salesCenterVo.getValueByName("unit.number"))){
							unitNumber = salesCenterVo.getValueByName("unit.number");
						}else{
							unitNumber = salesCenterVo.getValueByName("unitNum");
						}
					}
					if(!Utils.isBlank(unitType) && !Utils.isBlank(unitNumber)){
						productResultManager.populateMDURoadMapPowerPitchData(); //Need to call this method based on flow 
					}
					else{
						productResultManager.populateSDURoadMapPowerPitchData(); //Need to call this method based on flow 
					}
				}
				catch(Exception e){
					logger.warn("Exception while populating RoadMap PowerPitch Data in CartCKOWebFlowController= "+e.getMessage());
				}
				logger.info("getProductsFromResultsManager_end.");
		}catch (Exception e) 
		{
			logger.warn("error_in_getProductsFromResultsManager_method", e);
		}
	}
}