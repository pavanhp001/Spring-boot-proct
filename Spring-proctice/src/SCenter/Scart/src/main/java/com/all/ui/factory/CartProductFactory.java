package com.AL.ui.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.util.Utils;
import com.AL.productResults.vo.ProductSummaryVO;
import com.AL.ui.builder.CartLineItemBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.service.V.VOperation;
import com.AL.ui.util.CartLineItemUtil;
import com.AL.ui.util.CartUtil;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.util.PriceUtil;
import com.AL.ui.vo.ErrorList;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.DialogValueType;
import com.AL.xml.v4.FeatureValueType;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemDetailType;
import com.AL.xml.v4.LineItemPriceInfoType;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderLineItemDetailTypeType;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.SelectedDialogsType;
import com.AL.xml.v4.SelectedFeaturesType;
import com.AL.xml.v4.SelectedDialogsType.Dialogs;

/**
 * @author Preetam
 *
 */
public enum CartProductFactory {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(CartProductFactory.class);

	/**
	 * 
	 */
	private com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();

	/**
	 * @param order
	 * @param request
	 * @return
	 */
	public LineItemCollectionType getProductDetails(OrderType order, HttpServletRequest request)
	{
		logger.info("getProductDetails_begin_in_CartProductFactory");
		Double startTime = Double.valueOf(System.currentTimeMillis());

		LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
		/*	
		 * Building the Promotion checker Map if we pass lineItemNumber as key the Map returns true if promotion exists
		 * for that LineItem or else returns false 				 
		 */
		Map<Integer,Boolean> promoMap = CartLineItemFactory.INSTANCE.hasLineItemPromtions(order);
		List<LineItemType> lineItems = order.getLineItems().getLineItem();
		/*	
		 * Getting the ProductResultmanager from session contains all the products in it			 
		 */
		ProductResultsManager productResultManager = (ProductResultsManager) request.getSession().getAttribute("productResultManager");
		if(lineItems != null )
		{
			for(LineItemType lineItem : lineItems)
			{
				/*	
				 * Allows only products		 
				 */
				if(CartLineItemUtil.isProductIncludingRemoved(lineItem))
				{

					LineItemType newLineItemType = oFactory.createLineItemType();
					newLineItemType.setLineItemNumber(lineItem.getLineItemNumber());
					newLineItemType.setExternalId(lineItem.getExternalId());
					/*	
					 * LineItem Prices	from Order 
					 */
					Double baseNonRecurringPrice = lineItem.getLineItemPriceInfo().getBaseNonRecurringPrice();
					Double baseRecurringPrice = lineItem.getLineItemPriceInfo().getBaseRecurringPrice();
					/*	
					 * If the product is real-time it brings the prices from productResultManager.
					 * Else sends the Prices from Order
					 */
					if(CartLineItemUtil.isRealtimeProduct(lineItem))
					{
						/*	
						 * Checking whether the real-time product has prices from order .Else sets the prices from 
						 * Product result Manager
						 * 
						 */
						if(baseRecurringPrice > 0 || baseNonRecurringPrice > 0)
						{
							newLineItemType.setLineItemPriceInfo(
									CartLineItemBuilder.INSTANCE.createLineItemPriceInfoType(baseRecurringPrice, baseNonRecurringPrice));
							liCollection.getLineItem().add(newLineItemType);
						}
						else{
							LineItemDetailType lineItemDetailtype = lineItem.getLineItemDetail();
							if(lineItemDetailtype != null )
							{
								OrderLineItemDetailTypeType detail = lineItemDetailtype.getDetail();
								if(detail != null && detail.getProductLineItem()!= null)
								{
									String providerExernalId = detail.getProductLineItem().getProvider().getExternalId();
									String productExernalId = detail.getProductLineItem().getExternalId();
									if(productResultManager != null )
									{
										for (ProductSummaryVO newMdo : productResultManager.getProdSearchVO().getAllProductList())
										{
											if (newMdo != null && newMdo.getProviderExternalId().equalsIgnoreCase(providerExernalId)
													&& newMdo.getExternalId().equalsIgnoreCase(productExernalId))
											{
												int lineItemNumber = lineItem.getLineItemNumber();
												/*	
												 * Checking whether the real-time product has Promotion.If there 
												 * reduces the promotion price
												 * 
												 */
												if(promoMap.get(lineItemNumber))
												{
													newLineItemType.setLineItemPriceInfo(
															PriceUtil.INSTANCE.calculatePromotionalPrice(order,
																	newMdo.getBaseRecurringPrice(), newMdo.getBaseNonRecurringPrice(),lineItemNumber));
												}else{
													newLineItemType.setLineItemPriceInfo(
															CartLineItemBuilder.INSTANCE.createLineItemPriceInfoType(
																	newMdo.getBaseRecurringPrice(), newMdo.getBaseNonRecurringPrice()));
												}
												liCollection.getLineItem().add(newLineItemType);
											}
										}
									}
								}
							}
						}
					}
					else
					{
						int lineItemNumber = lineItem.getLineItemNumber();

						if(promoMap.get(lineItemNumber))
						{

							LineItemPriceInfoType lineItemPricInfoType  = oFactory.createLineItemPriceInfoType();

							Double selectedBaseRecurringFeaturesPrice = PriceUtil.INSTANCE.getSelectedFeaturesPrice(lineItem);
							Double selectedBaseNonRecurringFeaturesPrice = PriceUtil.INSTANCE.getSelectedNonRecurringFeaturesPrice(lineItem);


							LineItemDetailType lineItemDetailtype = lineItem.getLineItemDetail();
							if(lineItemDetailtype != null )
							{
								OrderLineItemDetailTypeType detail = lineItemDetailtype.getDetail();
								if(detail != null && detail.getProductLineItem()!= null)
								{
									String providerExernalId = detail.getProductLineItem().getProvider().getExternalId();
									String productExernalId = detail.getProductLineItem().getExternalId();
									if(productResultManager != null )
									{
										for (ProductSummaryVO newMdo : productResultManager.getProdSearchVO().getAllProductList())
										{
											if (newMdo != null && newMdo.getProviderExternalId().equalsIgnoreCase(providerExernalId)
													&& newMdo.getExternalId().equalsIgnoreCase(productExernalId))
											{
												baseRecurringPrice = newMdo.getBaseRecurringPrice();
												baseNonRecurringPrice = newMdo.getBaseNonRecurringPrice();
											}
										}
									}
								}
							}

							lineItemPricInfoType = PriceUtil.INSTANCE.calculatePromotionalPrice(order, baseRecurringPrice, baseNonRecurringPrice, lineItemNumber);

							if((selectedBaseRecurringFeaturesPrice > 0) || (selectedBaseNonRecurringFeaturesPrice > 0))
							{

								if((selectedBaseRecurringFeaturesPrice > 0))
								{
									baseRecurringPrice = lineItemPricInfoType.getBaseRecurringPrice();
									baseRecurringPrice += selectedBaseRecurringFeaturesPrice;
									baseRecurringPrice = Math.round(baseRecurringPrice * 100.0) / 100.0;
									lineItemPricInfoType.setBaseRecurringPrice(baseRecurringPrice);
								}

								if((selectedBaseNonRecurringFeaturesPrice > 0))
								{
									baseNonRecurringPrice = lineItemPricInfoType.getBaseNonRecurringPrice();
									baseNonRecurringPrice += selectedBaseNonRecurringFeaturesPrice;
									baseNonRecurringPrice = Math.round(baseNonRecurringPrice * 100.0) / 100.0;
									lineItemPricInfoType.setBaseNonRecurringPrice(baseNonRecurringPrice);
								}
							}

							LineItemType lineItemType = oFactory.createLineItemType();
							lineItemType.setLineItemNumber(lineItemNumber);
							lineItemType.setExternalId(lineItem.getExternalId());
							lineItemType.setLineItemPriceInfo(lineItemPricInfoType);

							liCollection.getLineItem().add(lineItemType);
						}
						else
						{

							newLineItemType.setLineItemPriceInfo(CartLineItemBuilder.INSTANCE.createLineItemPriceInfoType
									(baseRecurringPrice, baseNonRecurringPrice));

							liCollection.getLineItem().add(newLineItemType);
						}
					}
				}
				else
				{
					LineItemType lineItemType = oFactory.createLineItemType();
					lineItemType.setLineItemNumber(lineItem.getLineItemNumber());
					lineItemType.setExternalId(lineItem.getExternalId());
					liCollection.getLineItem().add(lineItemType);
				}
			}
		}
		logger.info("getProductDetails_end_in_CartProductFactory");
		return liCollection;
	}

	/**
	 * Updating the prices of RealTime Product
	 * 
	 * 
	 * @param agentId
	 * @param order
	 * @param productUniqueIdMap
	 * @param updateSalesContext
	 * @return
	 */
	public OrderType updateRealTimePricing(String agentId, OrderType order,
			Map<String, String> productUniqueIdMap,
			SalesContextType updateSalesContext)
	{
		long startTimer = 0;
		StopWatch timer = new StopWatch();
		timer.start();
		try{
			logger.info("updateRealTimePricing_begin_in_CartProductFactory");
			if(order !=null && order.getExternalId() != 0)
			{
	
				String orderId = String.valueOf(order.getExternalId());
				LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
	
				Map<Integer,Boolean> promoMap = CartLineItemFactory.INSTANCE.hasLineItemPromtions(order);
	
				for(LineItemType lineItem:order.getLineItems().getLineItem())
				{
					if(CartLineItemUtil.isProduct(lineItem) && CartLineItemUtil.isRealtimeProduct(lineItem))
					{
						String status = CartLineItemFactory.INSTANCE.getLineItemAttr(lineItem, Constants.CKO, Constants.STATUS);
						if(!Utils.isBlank(status) && status.equalsIgnoreCase(Constants.CKO_READY) &&
								!Utils.isBlank(productUniqueIdMap.get(lineItem.getPartnerExternalId()+"_"+
										lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId())))
						{
							LineItemType lineItemType = oFactory.createLineItemType();
	
							String[] prices = productUniqueIdMap.get(lineItem.getPartnerExternalId()+"_"+lineItem.getLineItemDetail().getDetail().getProductLineItem().getExternalId()).split("\\|");
							Double baseRecurringPrice =Double.parseDouble(prices[1].replace("$", ""));
							Double baseNonRecurringPrice =Double.parseDouble(prices[0].replace("$", ""));
							long lineItemExternalId = lineItem.getExternalId();
							int lineItemNumber = lineItem.getLineItemNumber();
							if(promoMap.get(lineItemNumber))
							{
								lineItemType.setLineItemPriceInfo(
										PriceUtil.INSTANCE.calculatePromotionalPrice(order,
												baseRecurringPrice, baseNonRecurringPrice,lineItemNumber));
							}
							else
							{
								lineItemType.setLineItemPriceInfo(CartLineItemBuilder.INSTANCE.createLineItemPriceInfoType
										(baseRecurringPrice, baseNonRecurringPrice));
							}
							lineItemType.setLineItemNumber(lineItemNumber);
							lineItemType.setExternalId(lineItemExternalId);
	
							liCollection.getLineItem().add(lineItemType);
						}
					}
				}
				if(liCollection.getLineItem().size() > 0)
				{
					startTimer = timer.getTime();
					order = LineItemService.INSTANCE.updateLineItem(agentId, orderId, VOperation.updateLineItem.toString(),liCollection,updateSalesContext);
					logger.info("TimeTakenforUpdateLineItemServiceCall="+(timer.getTime()-startTimer));
					logger.info("updateRealTimePricing_end_in_CartProductFactory");
					return order;
				}
				else
				{
					logger.info("updateRealTimePricing_end_in_CartProductFactory");
					return order;
				}
			}
			logger.info("updateRealTimePricing_end_in_CartProductFactory");
			return order;
		}finally{
			timer.stop();
		}
	}

	/**
	 * Temporary check for StaticPrices when showing on
	 * @param order
	 * @param session
	 * @return
	 */
	public  OrderType validateStaticPrices(OrderType order, HttpSession session){
		long startTimer = 0;
		StopWatch timer = new StopWatch();
		timer.start();
		try{
			logger.info("validateStaticPrices_begin_in_CartProductFactory");
			LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
			String agentId = CartUtil.INSTANCE.getAgentId(session);
			String orderId = String.valueOf(order.getExternalId());
			/*	
			 * Getting SalesContext from the session				 
			 */
			SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
	
			/*	
			 * Getting the ProductResultmanager from session contains all the products in it			 
			 */
			ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
	
			Map<Integer,Boolean> promoMap = CartLineItemFactory.INSTANCE.hasLineItemPromtions(order);
	
			List<LineItemType> lineItems = order.getLineItems().getLineItem();
	
			if(lineItems != null)
			{
				for(LineItemType lineItem : lineItems)
				{
	
					if(CartLineItemUtil.isProduct(lineItem) && CartLineItemUtil.isStaticProduct(lineItem))
					{
	
						LineItemPriceInfoType lineItemPricInfoType  = oFactory.createLineItemPriceInfoType();
	
						Double selectedBaseRecurringFeaturesPrice = PriceUtil.INSTANCE.getSelectedFeaturesPrice(lineItem);
						Double selectedBaseNonRecurringFeaturesPrice = PriceUtil.INSTANCE.getSelectedNonRecurringFeaturesPrice(lineItem);
	
						Double baseRecurringPrice = 0.0;
						Double baseNonRecurringPrice = 0.0;
	
						int lineItemNumber = lineItem.getLineItemNumber();
	
						LineItemDetailType lineItemDetailtype = lineItem.getLineItemDetail();
	
						if(lineItemDetailtype != null )
						{
							OrderLineItemDetailTypeType detail = lineItemDetailtype.getDetail();
							if(detail != null && detail.getProductLineItem()!= null)
							{
								String providerExernalId = detail.getProductLineItem().getProvider().getExternalId();
								String productExernalId = detail.getProductLineItem().getExternalId();
								if(productResultManager != null )
								{
									for (ProductSummaryVO newMdo : productResultManager.getProdSearchVO().getAllProductList())
									{
										if (newMdo != null && newMdo.getProviderExternalId().equalsIgnoreCase(providerExernalId)
												&& newMdo.getExternalId().equalsIgnoreCase(productExernalId))
										{
											baseRecurringPrice = newMdo.getBaseRecurringPrice();
											baseNonRecurringPrice = newMdo.getBaseNonRecurringPrice();
										}
									}
								}
							}
						}
	
						if(promoMap.get(lineItemNumber))
						{
	
							lineItemPricInfoType = PriceUtil.INSTANCE.calculatePromotionalPrice(order, baseRecurringPrice, 
									baseNonRecurringPrice, lineItemNumber);
	
							if((selectedBaseRecurringFeaturesPrice > 0) || 
									(selectedBaseNonRecurringFeaturesPrice > 0))
							{
	
								if((selectedBaseRecurringFeaturesPrice > 0))
								{
									baseRecurringPrice = lineItemPricInfoType.getBaseRecurringPrice();
									baseRecurringPrice += selectedBaseRecurringFeaturesPrice;
									baseRecurringPrice = Math.round(baseRecurringPrice * 100.0) / 100.0;
									lineItemPricInfoType.setBaseRecurringPrice(baseRecurringPrice);
								}
	
								if((selectedBaseNonRecurringFeaturesPrice > 0))
								{
									baseNonRecurringPrice = lineItemPricInfoType.getBaseNonRecurringPrice();
									baseNonRecurringPrice += selectedBaseNonRecurringFeaturesPrice;
									baseNonRecurringPrice = Math.round(baseNonRecurringPrice * 100.0) / 100.0;
									lineItemPricInfoType.setBaseNonRecurringPrice(baseNonRecurringPrice);
								}
							}
	
							LineItemType lineItemType = oFactory.createLineItemType();
							lineItemType.setLineItemNumber(lineItemNumber);
							lineItemType.setExternalId(lineItem.getExternalId());
							lineItemType.setLineItemPriceInfo(lineItemPricInfoType);
	
							liCollection.getLineItem().add(lineItemType);
						}
	
					}
				}
			}
	
			if(liCollection.getLineItem().size() > 0)
			{
				startTimer = timer.getTime();
				order = LineItemService.INSTANCE.updateLineItem(agentId, orderId, 
						VOperation.updateLineItem.toString(), liCollection, salesContext);
				logger.info("TimeTakenforUpdateLineItemServiceCall="+(timer.getTime()-startTimer));
				
			}
			logger.info("validateStaticPrices_end_in_CartProductFactory");
			return order;
		}finally
		{
			timer.stop();
		}
	}

	/**
	 * @param order
	 * @param activeDialogueData
	 * @param lineItemExternalId
	 * @param isUtilityOffer 
	 * @return
	 */
	public OrderType getLineItemsFromOrder(OrderManagementRequestResponse orderManagementRequestResponse, JSONObject activeDialogueData, String lineItemExternalId, boolean isUtilityOffer, String productExtID){
		logger.info("getLineItemsFromOrder_begin_in_CartProductFactory");
		
		long startTimer = 0;
		StopWatch timer = new StopWatch();
		timer.start();
		try{
			OrderType order = orderManagementRequestResponse.getResponse().getOrderInfo().get(0);
			
			long orderId = order.getExternalId();
			String agentId = order.getAgentId();
			
			LineItemType itemType = getLineItemBySelectedDialogues(activeDialogueData);
			LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
			
			List<LineItemType> collectionType = order.getLineItems().getLineItem();
			int lineItemNumber = -1;
			for(LineItemType lineItemType : collectionType){
				if(lineItemType.getExternalId() == Long.valueOf(lineItemExternalId)){
					lineItemNumber = lineItemType.getLineItemNumber();
				}
			}
			itemType.setExternalId(Long.valueOf(lineItemExternalId));
			
			itemType.setLineItemNumber(lineItemNumber);
			
			LineItemAttributeType lineItemAttribute = oFactory.createLineItemAttributeType();
			AttributeEntityType entity = oFactory.createAttributeEntityType();
			entity.setSource("CKO");
			
			SalesContextType context = orderManagementRequestResponse.getResponse().getSalesContext();
			
			if(!isUtilityOffer){
				AttributeDetailType attr = oFactory.createAttributeDetailType();
				attr.setName("STATUS");
				attr.setValue(Constants.REMOVED);
				entity.getAttribute().add(attr);
				lineItemAttribute.getEntity().add(entity);
				itemType.setLineItemAttributes(lineItemAttribute);
				
				liCollection.getLineItem().add(itemType);
				List<String> lineItemIdList = new ArrayList<String>();
				lineItemIdList.add(lineItemExternalId);
				
				List<Integer> reasons = new ArrayList<Integer>(); 
				reasons.add(ControllerConstants.REASON_CODE);
				
				startTimer = timer.getTime();
				LineItemService.INSTANCE.updateLineItem(agentId, String.valueOf(orderId), VOperation.updateLineItem.toString(), liCollection, context, new ErrorList());
				logger.info("TimeTakenforUpdateLineItemServicecall="+(timer.getTime()-startTimer));
				
				startTimer = timer.getTime();
				order = LineItemService.INSTANCE.updateLineItemStatus(agentId, String.valueOf(orderId), lineItemIdList, 
						LineItemStatusCodesType.CANCELLED_REMOVED.value(), LineItemStatusCodesType.CANCELLED_REMOVED.value(), reasons, context, new ErrorList());
				logger.info("TimeTakenforUpdateLineItemStatusServicecall="+(timer.getTime()-startTimer));
			}
			else{
				logger.info("productExtID="+productExtID);
				LineItemType utilityOfferlineItem = LineItemUtil.getUtilityOfferLineItem(order, productExtID);

				if (utilityOfferlineItem != null) {
					itemType.setExternalId(utilityOfferlineItem.getExternalId());
					itemType.setLineItemNumber(utilityOfferlineItem.getLineItemNumber());
				}
				
				AttributeDetailType attr = oFactory.createAttributeDetailType();
				attr.setName("STATUS");
				attr.setValue(Constants.CKO_INCOMPLETE);
				attr.setDescription("CKO InCompleted");
				entity.getAttribute().add(attr);
				lineItemAttribute.getEntity().add(entity);
				itemType.setLineItemAttributes(lineItemAttribute);
				
				liCollection.getLineItem().add(itemType);
				startTimer = timer.getTime();
				order = LineItemService.INSTANCE.updateLineItem(agentId, String.valueOf(orderId), VOperation.updateLineItem.toString(), liCollection, context, new ErrorList());
				logger.info("TimeTakenforUpdateLineItemServicecall="+(timer.getTime()-startTimer));
			}
			logger.info("getLineItemsFromOrder_end_in_CartProductFactory");
			return order;
		}finally{
			timer.stop();
		}
	}

	/* returns a lineItem with active dialogues and selected features set
	 * @param monthlyCost
	 * @param parameterMap
	 * @param featureMap*/
	public LineItemType getLineItemBySelectedDialogues(JSONObject activeDialogueData){
		logger.info("getLineItemBySelectedDialogues_begin_in_CartProductFactory");
		LineItemType itemType = oFactory.createLineItemType();

		SelectedDialogsType selectedDialogueType = oFactory.createSelectedDialogsType();

		SelectedDialogsType.Dialogs selectedDialogueTypeDialogue = oFactory.createSelectedDialogsTypeDialogs();
		SelectedFeaturesType featureType = oFactory.createSelectedFeaturesType();
		String dialogueJSON = "";
		try {
			dialogueJSON = (String) activeDialogueData.get("dialgoues");
			logger.debug("dialogueJSON="+dialogueJSON);
			JSONArray jArray = new JSONArray(dialogueJSON);
			logger.debug("JSON_ARRAY_LENGTH="+jArray.length());
			if(jArray != null && jArray.length() > 0){
				for(int i = 0; i < jArray.length(); i++){
					String individualObject = (String)jArray.get(i);
					JSONObject individualJSONObject = new JSONObject(individualObject);
					returnGeneratedValues(individualJSONObject, selectedDialogueTypeDialogue, featureType);
				}
			}
		} catch (JSONException e1) {
			logger.warn("error_while_preparing_activeDialogues_for_lineItem",e1);
		}
		selectedDialogueType.setDialogs(selectedDialogueTypeDialogue);
		itemType.setActiveDialogs(selectedDialogueType);
		itemType.setSelectedFeatures(featureType);
		logger.info("getLineItemBySelectedDialogues_end_in_CartProductFactory");
		return itemType;
	}

	private void returnGeneratedValues(JSONObject activeDialogueJSON, Dialogs selectedDialogueTypeDialogue, SelectedFeaturesType featureType ){
		logger.info("returnGeneratedValues_begin_in_CartProductFactory");
		try {
			DialogValueType dialogueValueType = oFactory.createDialogValueType();
			DialogValueType.Value dialogueValueTypeValue = oFactory.createDialogValueTypeValue();
			SelectedFeaturesType.FeatureGroup selFeatureGroup = oFactory.createSelectedFeaturesTypeFeatureGroup();
			if(activeDialogueJSON.getString("type").equalsIgnoreCase("dialogue")){
				dialogueValueType.setExternalId(activeDialogueJSON.getString("name"));
				dialogueValueTypeValue.setSelected(true);

				dialogueValueTypeValue.setType(activeDialogueJSON.getString("dialogue_type"));

				dialogueValueTypeValue.setValue(activeDialogueJSON.getString("selected_value"));

				dialogueValueType.getValue().add(dialogueValueTypeValue);
				selectedDialogueTypeDialogue.getDialog().add(dialogueValueType);
			}
			else if(activeDialogueJSON.getString("type").equalsIgnoreCase("feature")){
				
				SelectedFeaturesType.Features selFeatures = oFactory.createSelectedFeaturesTypeFeatures();
				FeatureValueType fVal = oFactory.createFeatureValueType();
				
				fVal.setExternalId(activeDialogueJSON.getString("name"));
				fVal.setType(activeDialogueJSON.getString("dialogue_type"));				
				fVal.setValue(activeDialogueJSON.getString("selected_value"));
				selFeatures.getFeatureValue().add(fVal);
				featureType.setFeatures(selFeatures);
				
				selectedDialogueTypeDialogue.getDialog().add(dialogueValueType);
			}
			else if(activeDialogueJSON.getString("type").equalsIgnoreCase("featureGroup")){

				FeatureValueType fVal = oFactory.createFeatureValueType();
				selFeatureGroup = oFactory.createSelectedFeaturesTypeFeatureGroup();
				selFeatureGroup.setExternalId(activeDialogueJSON.getString("featuregroup"));
				selFeatureGroup.setGroupType(1);
				fVal.setExternalId(activeDialogueJSON.getString("name"));
				fVal.setType(activeDialogueJSON.getString("dialogue_type"));
				fVal.setValue(activeDialogueJSON.getString("selected_value"));
				selFeatureGroup.getFeatureValue().add(fVal);
				featureType.getFeatureGroup().add(selFeatureGroup);	
			}
		} catch (JSONException e) {
			logger.warn("error_in_returnGeneratedValues_method",e);
		}
		logger.info("returnGeneratedValues_end_in_CartProductFactory");
	}
}
