package com.AL.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.AL.productResults.managers.ProductResultsManager;
import com.AL.ui.builder.CartLineItemBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.factory.CartProductFactory;
import com.AL.ui.factory.CartPromotionFactory;
import com.AL.ui.factory.CartRulesFactory;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.CartLineItemUtil;
import com.AL.ui.util.CartUtil;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

/**
 * @author Preetam
 *
 */
@Controller
public class CartLineItemController extends BaseController  {

	/**
	 * 	Logger Initialization
	 * 
	 */
	private static final Logger logger = Logger.getLogger(CartLineItemController.class);
	

	/**
	 * Adding LineItem to Order from Recommendations page
	 * @param request
	 * @return List<String>
	 */
	@RequestMapping(value="/recommendations/AddtoOrder")
	protected @ResponseBody List<String> addToOrder(HttpServletRequest request)throws Exception{


		Double startTime = Double.valueOf(System.currentTimeMillis());
		logger.info("addToOrder_begin_in_CartLineItemController");
		String productData = request.getParameter("productData");
		String orderId = request.getParameter("orderId");
		String offerType = request.getParameter("offerType");
		HttpSession session = request.getSession();
		String agentId = CartUtil.INSTANCE.getAgentId(session);

		com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();
		LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
		Map<String,String> productUniqueIdMap = new HashMap<String, String>();

		Map<Integer,String> errorReasonMap = new HashMap<Integer,String>();
		try
		{
			if(!productData.isEmpty())
			{
				JSONObject index = new JSONObject(productData);
				JSONArray selectedArray = index.getJSONArray("lineItems");

				/*	
				 * Changing the Cart Rules
				 * 
				 * JSONArray success = CartRulesFactory.INSTANCE.cartRulesValidator(selectedArray,orderId,session);			 
				 */

				/*				
				 * Validates whether the selected LineItem satisfies the CartRules or Not
				 */				
				Double startTimeforCartRules = Double.valueOf(System.currentTimeMillis());
				JSONArray success = CartRulesFactory.INSTANCE.cartRules(selectedArray,orderId,session);
				logger.info("Cart_Rules_completed[orderId="+orderId+"][Time="+CartUtil.INSTANCE.getTimeDiff(startTimeforCartRules) + "sec]");
			
				String parentExternalId = null;
				logger.info("successArray="+success);
				for(int i=0;i < success.length(); i++)
				{
					JSONObject feedback = (JSONObject) success.get(i);

					if(!feedback.has("error"+i))
					{
						/*	
						 * Selected LineItem satisfies CartRules					 
						 */

						boolean isPromotion = feedback.getBoolean(Constants.IS_Promotion);
						if(isPromotion)
						{
							/*	
							 * Adding Promotion/productPromotion 						 
							 */	
							liCollection.getLineItem().add(CartLineItemBuilder.INSTANCE.
									createPromotion(feedback));
						}
						else
						{
							/*	
							 * Adding LinItem/product					 
							 */	
							parentExternalId = feedback.getString(Constants.PARTNER_ID);
							logger.info("LineItem Json "+feedback);
							LineItemType lineItemType = CartLineItemBuilder.INSTANCE.createLineItem(feedback, productUniqueIdMap);
							
							ProductResultsManager productResultManager = (ProductResultsManager)request.getSession().getAttribute("productResultManager");
							if (productResultManager.getSelectedExistingCustomerStatusMap().get(parentExternalId) != null &&
									productResultManager.getSelectedExistingCustomerStatusMap().get(parentExternalId).equalsIgnoreCase("Disable")
									&& "Transfer".equalsIgnoreCase(offerType) ){
								lineItemType.setIsTransfer(1);
							}
							//CartLineItemUtil.getTotalPoints(lineItemType, session, Constants.ADD);
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
								logger.info("hybris lineitemattribute added"+hybrisAttributes);
							}
							if(!Utils.isBlank(feedback.getString("noEmail")) && feedback.getString("noEmail").equalsIgnoreCase("true")) {
								lineItemType.getLineItemAttributes().getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("suppress_utility_email", "true", "", "UTILITY_EMAIL"));
								logger.info("noEmail_lineitemattribute_added");
							}
							if(!Utils.isBlank(feedback.getString("noOrderStatus")) && feedback.getString("noOrderStatus").equalsIgnoreCase("true")) {
								lineItemType.getLineItemAttributes().getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("suppress_utility_order_status", "true", "", "UTILITY_ORDER_STATUS"));
								logger.info("noOrderStatus_lineitemattribute_added");
							}													
							liCollection.getLineItem().add(lineItemType);
						}
					}
					else
					{
						/*	
						 * Selected LineItem failed CartRules					 
						 */
						String error = feedback.getString("error"+i);
						errorReasonMap.put(i, error);
					}
				}
				
				logger.info("AddLineItem_request_building_completed");

				List<String> result = null;
				if(liCollection.getLineItem().size()>0) 
				{

					/*	
					 * ServiceCall for getting SalesContext of the Order				 
					 */
					SalesContextType updateSalesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);

					/*	
					 * Validating GUID in SalesContext				 
					 */
					updateSalesContext = CartUtil.INSTANCE.setGUIDInSalesContext(orderId,agentId,updateSalesContext,session);

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

					/*	
					 * Building the Result 				 
					 */

					if(order!=null && order.getExternalId()>0){
						request.getSession().setAttribute("order", order);
						request.getSession().setAttribute("lineItemList", CartLineItemFactory.INSTANCE.sortLineItemBasedOnStatus(order));
					}
					
					result = CartLineItemFactory.INSTANCE.addToOrderResult(order, productUniqueIdMap, errorReasonMap,Float.toString(Utils.getTotalPoints(order, session)));
					logger.info("Add_LineItem_completed[orderId="+orderId+"][Time="+ CartUtil.INSTANCE.getTimeDiff(startTime) + "sec]");
					logger.info("cart_addorder_result="+result);
					return result;
				}
				else if(errorReasonMap.size()!=0)
				{
					result= new ArrayList<String>();
					/*	
					 * Building the Error Result 				 
					 */
					result = CartLineItemFactory.INSTANCE.addToOrderErrorResult(errorReasonMap, result);
					logger.info("Add_LineItem_completed[orderId="+orderId+"][Time="+ CartUtil.INSTANCE.getTimeDiff(startTime) + "sec]");
					return result;
				}
			}
			
		}
		catch (Exception e) 
		{  
			logger.warn("error_while_adding_product",e);
			return null;
		}
		return null;
	}

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/add/lineItemPromotion")
	protected @ResponseBody String addPromtions(HttpServletRequest request)throws Exception
	{   long startTimer=0;
		StopWatch timer = new StopWatch();
		timer.start();
		logger.info("addPromtions_begin_in_CartLineItemController");
		String updatePromotionJson = request.getParameter("updatePromotionJson");
		HttpSession session = request.getSession();
		String agentId = CartUtil.INSTANCE.getAgentId(session);
		com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();
		OrderType orderType = oFactory.createOrderType();

		/*	
		 * ServiceCall for getting SalesContext of the Order				 
		 */
		SalesContextType updateSalesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);

		try 
		{
			if(!updatePromotionJson.isEmpty())
			{
				LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
				JSONObject updatePromotionJsonObject = new JSONObject(updatePromotionJson);
				JSONObject order = updatePromotionJsonObject.getJSONObject("order");
				String orderId = order.getString("externalId"); 
				JSONArray selectedArray = order.getJSONArray("lineItems");
				int appliesTo = 0;

				for(int i=0;i < selectedArray.length(); i++)
				{
					String jsonString=(String)selectedArray.get(i);
					JSONObject feedback =new JSONObject(jsonString);
					feedback.put("isAppliesToInternal", false);
					appliesTo = feedback.getInt("appliesTo");

					String productExernalId = feedback.getString("productExternalId");
					String selectedPromotion = productExernalId+"_"+appliesTo;
					startTimer = timer.getTime();
					orderType = CartUtil.INSTANCE.getOrder(orderId,agentId,updateSalesContext);
					logger.info("TimeTakenforGetOrderServiceCall="+(timer.getTime() - startTimer));
					boolean isValidPromotion = CartPromotionFactory.INSTANCE.ValidatePromotions(orderType, selectedPromotion);
					if(isValidPromotion)
					{
						com.AL.xml.v4.CustAddress cust = CartLineItemUtil.getAddress(orderType.getCustomerInformation().getCustomer(),
								com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());
						feedback.put("svcAddressExtId", String.valueOf(cust.getAddress().getExternalId()));
						feedback.put("billingInfoExtId", String.valueOf(session.getAttribute("billingInfoExternalId")));
						liCollection.getLineItem().add(CartLineItemBuilder.INSTANCE.createPromotion(feedback));
					}
				}

				JSONObject result = new JSONObject();

				if(liCollection.getLineItem().size()>0)
				{
					/*	
					 * ServiceCall for adding LineItem to Order					 
					 */
					startTimer=timer.getTime();
					orderType = LineItemService.INSTANCE.addLineItem(agentId, orderId, liCollection, updateSalesContext, false);
					logger.info("TimeTakenforAddLineItemServiceCall="+(timer.getTime()-startTimer));
					
					if(orderType != null)
					{
						for(LineItemType lineItem :orderType.getLineItems().getLineItem())
						{
							if(lineItem.getLineItemNumber() == appliesTo)
							{
								result.put("liId", lineItem.getExternalId());
								result.put("stat", true);
								result.put("baseRecurringPrice", lineItem.getLineItemPriceInfo().getBaseRecurringPrice());
								result.put("baseNonRecurringPrice", lineItem.getLineItemPriceInfo().getBaseNonRecurringPrice());
								result.put("hasPromotion", true);
							}
						}
						request.getSession().setAttribute("order", orderType);
						request.getSession().setAttribute("lineItemList", CartLineItemFactory.INSTANCE.sortLineItemBasedOnStatus(orderType));
					}
					logger.info("cart_addPromtions_result="+result);
					return result.toString();
				}
				else
				{
					if(orderType != null)
					{
						for(LineItemType lineItem :orderType.getLineItems().getLineItem())
						{
							if(lineItem.getLineItemNumber() == appliesTo)
							{
								result.put("liId", lineItem.getExternalId());
								result.put("stat", false);
							}
						}
					}
					logger.info("cart_addPromtions_result="+result);
					return result.toString();
				}
			}
		}
		catch (JSONException e) 
		{
			logger.warn("Error_while_adding_promotion",e);
		}
		finally
		{
			timer.stop();
		}
		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception
			{
		return new ModelAndView();
			}
	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateShoppingCartCKOLineItems")
	protected @ResponseBody Map<String,String> updateShoppingCartCKOLineItems(HttpServletRequest request)
	{
		String orderID  = request.getParameter("orderID");
		String newLineItemExtID  = request.getParameter("newLineItemExternalID");
		String removeLineItemExID  = request.getParameter("removeLineItemExternalID");
		Map<String,String> lineItemDataMap = new HashMap<String,String>();
		request.getSession().setAttribute("newAttv6LineItemExtID", newLineItemExtID);
		logger.info(String.format("orderID=%s newLineItemExternalID=%s removeLineItemExternalID=%s", orderID , newLineItemExtID , removeLineItemExID));
		try{
			if(!Utils.isBlank(orderID) && !Utils.isBlank(newLineItemExtID) && !Utils.isBlank(removeLineItemExID)){
				OrderManagementRequestResponse orderManagementRequestResponse = OrderService.INSTANCE.getOrderManagementRequestResponseByOrderNumber(orderID, Boolean.TRUE);
				if(orderManagementRequestResponse != null 
						&& orderManagementRequestResponse.getResponse() != null 
						&& orderManagementRequestResponse.getResponse().getOrderInfo() != null 
						&& !orderManagementRequestResponse.getResponse().getOrderInfo().isEmpty()){
					OrderType order = orderManagementRequestResponse.getResponse().getOrderInfo().get(0);
					request.getSession().setAttribute("order",order);
					lineItemDataMap.put("totalPoints",Float.toString(Utils.getTotalPoints(order, request.getSession())));
					LineItemType lineItemType = LineItemUtil.getLineItemBasedOnLineItemExID(order, Long.valueOf(newLineItemExtID.trim()));
					LineItemType removeLineItemType = LineItemUtil.getLineItemBasedOnLineItemExID(order, Long.valueOf(removeLineItemExID.trim()));
					if(lineItemType != null){
						if(lineItemType.getLineItemDetail() != null 
								&& lineItemType.getLineItemDetail().getDetail() != null
								&& lineItemType.getLineItemDetail().getDetail().getProductLineItem() != null){
							lineItemDataMap.put("productExID",lineItemType.getLineItemDetail().getDetail().getProductLineItem().getExternalId());
							if(lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProvider() != null){
								lineItemDataMap.put("providerID",lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId());	
							}
						}
						lineItemDataMap.put("productName",LineItemUtil.getLineItemAttr(lineItemType, Constants.PRODUCT_NAME, Constants.NAME));
						lineItemDataMap.put("recPrice",String.format (Constants.PERCENT_2F, lineItemType.getLineItemPriceInfo().getBaseRecurringPrice()));
						lineItemDataMap.put("recNonPrice",String.format (Constants.PERCENT_2F , lineItemType.getLineItemPriceInfo().getBaseNonRecurringPrice()));
						lineItemDataMap.put("lineItemExID",String.valueOf(lineItemType.getExternalId()));
						lineItemDataMap.put("lineItemNumber",String.valueOf(lineItemType.getLineItemNumber()));
						if(lineItemType.getLineItemStatus() != null && lineItemType.getLineItemStatus().getStatusCode() != null){
							lineItemDataMap.put("lineItemStatus",lineItemType.getLineItemStatus().getStatusCode().name());
						}
					}
					if(removeLineItemType != null){
						lineItemDataMap.put("removeLineItemExID",String.valueOf(removeLineItemType.getExternalId()));
						lineItemDataMap.put("removeLineItemRecPrice",String.format (Constants.PERCENT_2F, removeLineItemType.getLineItemPriceInfo().getBaseRecurringPrice()));
						lineItemDataMap.put("removeLineItemNonRecPrice",String.format (Constants.PERCENT_2F, removeLineItemType.getLineItemPriceInfo().getBaseNonRecurringPrice()));
						if(removeLineItemType.getLineItemStatus() != null && removeLineItemType.getLineItemStatus().getStatusCode() != null){
							lineItemDataMap.put("removeLineItemStatus",removeLineItemType.getLineItemStatus().getStatusCode().name());
						}
						if(removeLineItemType.getLineItemDetail() != null 
								&&  removeLineItemType.getLineItemDetail().getDetail() != null
								&&  removeLineItemType.getLineItemDetail().getDetail().getProductLineItem() != null){
							lineItemDataMap.put("removeProductName",removeLineItemType.getLineItemDetail().getDetail().getProductLineItem().getName());
							if(removeLineItemType.getLineItemDetail().getDetail().getProductLineItem().getProvider() != null){
								lineItemDataMap.put("removeProviderID",removeLineItemType.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId());	
							}
							lineItemDataMap.put("removeProductExID",removeLineItemType.getLineItemDetail().getDetail().getProductLineItem().getExternalId());
						}
					}
				}
			}
		}catch(Exception e){
			logger.error("Exception_occured_while_update_shoppingCart_for_CKO_lineItem="+e.getMessage());
		}
		if(lineItemDataMap.isEmpty()){
			lineItemDataMap.put("status", "error");
		}else{
			lineItemDataMap.put("status", "success"); 
		}
		logger.info("lineItemDataMap="+lineItemDataMap);
		return lineItemDataMap;
	}
	@RequestMapping(value="/recommendations/AddMixedBundleToOrder")
	protected @ResponseBody List<List<String>> addMixedBundleToOrder(HttpServletRequest request)throws Exception{


		
		logger.info("addMixedBundleToOrder_begin_in_CartLineItemController");
		String productData1 = request.getParameter("productData1");
		String productData2 = request.getParameter("productData2");
		logger.info("addMixedBundleToOrder productData1= "+productData1+ " productData2"+productData2);
		List<List<String>> resultsList = new ArrayList<List<String>>();
		
		List<String> productsArray = new ArrayList<String>();
		productsArray.add(productData1);
		if(productData2 != null){
			productsArray.add(productData2);
		}
		Double startTime = Double.valueOf(System.currentTimeMillis());
		for(String productData :productsArray){
			resultsList.add(addMixedBundleProducts(productData, request,startTime));
		}
		logger.info("MixedBundle_resultsList = "+resultsList);
		return resultsList;
	}
	
	private List<String> addMixedBundleProducts(String productData,HttpServletRequest request,Double startTime){

		try
		{
			HttpSession session = request.getSession();
			String agentId = CartUtil.INSTANCE.getAgentId(session);
			
			String orderId = request.getParameter("orderId");
			String offerType = request.getParameter("offerType");
			com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();
			LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
			Map<String,String> productUniqueIdMap = new HashMap<String, String>();

			Map<Integer,String> errorReasonMap = new HashMap<Integer,String>();
			if(!productData.isEmpty())
			{
				JSONObject index = new JSONObject(productData);
				JSONArray selectedArray = index.getJSONArray("lineItems");

				/*	
				 * Changing the Cart Rules
				 * 
				 * JSONArray success = CartRulesFactory.INSTANCE.cartRulesValidator(selectedArray,orderId,session);			 
				 */

				/*				
				 * Validates whether the selected LineItem satisfies the CartRules or Not
				 */				
				Double startTimeforCartRules = Double.valueOf(System.currentTimeMillis());
				JSONArray success = CartRulesFactory.INSTANCE.cartRules(selectedArray,orderId,session);
				logger.info("MixedBundle_Cart_Rules_completed[orderId="+orderId+"][Time="+CartUtil.INSTANCE.getTimeDiff(startTimeforCartRules) + "sec]");
			
				String parentExternalId = null;
				logger.info("successArray="+success);
				for(int i=0;i < success.length(); i++)
				{
					JSONObject feedback = (JSONObject) success.get(i);

					if(!feedback.has("error"+i))
					{
						/*	
						 * Selected LineItem satisfies CartRules					 
						 */

						boolean isPromotion = feedback.getBoolean(Constants.IS_Promotion);
						if(isPromotion)
						{
							/*	
							 * Adding Promotion/productPromotion 						 
							 */	
							liCollection.getLineItem().add(CartLineItemBuilder.INSTANCE.
									createPromotion(feedback));
						}
						else
						{
							/*	
							 * Adding LinItem/product					 
							 */	
							parentExternalId = feedback.getString(Constants.PARTNER_ID);
							logger.info("MixedBundle LineItem Json "+feedback);							
							
							LineItemType lineItemType = CartLineItemBuilder.INSTANCE.createLineItem(feedback, productUniqueIdMap);
							String mixedBundleTime = request.getParameter("mixedBundleTime");
							if(!Utils.isBlank(mixedBundleTime)) {
								logger.info("mixedBundleTime="+mixedBundleTime);	
						        lineItemType.getLineItemAttributes().getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("mixed_bundle", mixedBundleTime, "", "MIXED_BUNDLE"));
						       }
							ProductResultsManager productResultManager = (ProductResultsManager)request.getSession().getAttribute("productResultManager");
							if (productResultManager.getSelectedExistingCustomerStatusMap().get(parentExternalId) != null &&
									productResultManager.getSelectedExistingCustomerStatusMap().get(parentExternalId).equalsIgnoreCase("Disable")
									&& "Transfer".equalsIgnoreCase(offerType) ){
								lineItemType.setIsTransfer(1);
							}
							//CartLineItemUtil.getTotalPoints(lineItemType, session, Constants.ADD);
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
								logger.info("MixedBundle hybris lineitemattribute added"+hybrisAttributes);
							}
							if(!Utils.isBlank(feedback.getString("noEmail")) && feedback.getString("noEmail").equalsIgnoreCase("true")) {
								lineItemType.getLineItemAttributes().getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("suppress_utility_email", "true", "", "UTILITY_EMAIL"));
								logger.info("MixedBundle_noEmail_lineitemattribute_added");
							}
							if(!Utils.isBlank(feedback.getString("noOrderStatus")) && feedback.getString("noOrderStatus").equalsIgnoreCase("true")) {
								lineItemType.getLineItemAttributes().getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("suppress_utility_order_status", "true", "", "UTILITY_ORDER_STATUS"));
								logger.info("MixedBundle_noOrderStatus_lineitemattribute_added");
							}													
							liCollection.getLineItem().add(lineItemType);
						}
					}
					else
					{
						/*	
						 * Selected LineItem failed CartRules					 
						 */
						String error = feedback.getString("error"+i);
						errorReasonMap.put(i, error);
					}
				}
				
				logger.info("MixedBundle_AddLineItem_request_building_completed");

				List<String> result = null;
				if(liCollection.getLineItem().size()>0) 
				{

					/*	
					 * ServiceCall for getting SalesContext of the Order				 
					 */
					SalesContextType updateSalesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);

					/*	
					 * Validating GUID in SalesContext				 
					 */
					updateSalesContext = CartUtil.INSTANCE.setGUIDInSalesContext(orderId,agentId,updateSalesContext,session);

					Double startTimeforAddLineItem = Double.valueOf(System.currentTimeMillis());

					/*	
					 * ServiceCall for adding LineItem to Order					 
					 */
					OrderType order = LineItemService.INSTANCE.addLineItem(agentId, orderId, liCollection,updateSalesContext,true);

					logger.info("MixedBundle_TimetakenForAddLineItem[Time="+ CartUtil.INSTANCE.getTimeDiff(startTimeforAddLineItem) + "sec]");
					if( Constants.CENTURY_LINK.equals(parentExternalId) || Constants.COX_RTS_PROVIDER_ID.equals(parentExternalId) || Constants.TXU_RTS_PROVIDER_ID.equals(parentExternalId))
					{
						/*	
						 * ServiceCall for updating Realtime LineItemPrice					 
						 */
						order = CartProductFactory.INSTANCE.updateRealTimePricing(agentId,order,productUniqueIdMap,updateSalesContext);
					}

					/*	
					 * Building the Result 				 
					 */

					if(order!=null && order.getExternalId()>0){
						request.getSession().setAttribute("order", order);
						request.getSession().setAttribute("lineItemList", CartLineItemFactory.INSTANCE.sortLineItemBasedOnStatus(order));
					}
					
					result = CartLineItemFactory.INSTANCE.addToOrderResult(order, productUniqueIdMap, errorReasonMap,Float.toString(Utils.getTotalPoints(order, session)));
					logger.info("MixedBundle_Add_LineItem_completed[orderId="+orderId+"][Time="+ CartUtil.INSTANCE.getTimeDiff(startTime) + "sec]");
					logger.info("cart_addMixedBundleProductsToorder_result="+result);
					request.getSession().setAttribute("totalPoints",Utils.getTotalPoints(order, session));
					return result;
				}
				else if(errorReasonMap.size()!=0)
				{
					result= new ArrayList<String>();
					result.add("totalPoints|"+request.getSession().getAttribute("totalPoints"));
					/*	
					 * Building the Error Result 				 
					 */
					result = CartLineItemFactory.INSTANCE.addToOrderErrorResult(errorReasonMap, result);
					logger.info("MixedBundle_Add_LineItem_completed[orderId="+orderId+"][Time="+ CartUtil.INSTANCE.getTimeDiff(startTime) + "sec]");
					logger.info("totalPoints "+request.getSession().getAttribute("totalPoints"));
					return result;
				}
			}
			
		}catch (Exception e) 
		{  
			logger.warn("MixedBundle_error_while_adding_product",e);
			return null;
		}
		return new ArrayList<String>();
	}
}