package com.AL.ui.factory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import com.AL.ui.constants.Constants;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.service.V.impl.OrderCacheService;
import com.AL.ui.vo.OrderQualVO;
import com.AL.ui.vo.PromotionVO;
import com.AL.xml.pr.v4.ProductInfoType;
import com.AL.xml.v4.ApplicableType;
import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.Customer;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemDetailType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderLineItemDetailTypeType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.ProductPromotionType;
import com.AL.xml.v4.ProviderSourceBaseType;
import com.AL.xml.v4.ProviderSourceType;
import com.AL.xml.v4.ServiceType;

public enum PromotionHandlerFactory {
	INSTANCE;
	static com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();
	private static final Logger logger = Logger.getLogger(PromotionHandlerFactory.class);
	
	
	/*public LineItemType addPromotions(JSONObject updatePromotionJsonObject, HttpServletRequest request, String orderId, LineItemType item,
			OrderType orderType, LineItemType promoLineItemType){

		try{
			OrderType order = oFactory.createOrderType();
			LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();

			if(updatePromotionJsonObject != null){

				logger.info("UPDATE PROMOTION JSON OBJECT :::::::::::: "+updatePromotionJsonObject);

				String productExernalId = (String) updatePromotionJsonObject.get("productExternalId");
				logger.info("productExernalId :::::::::::: "+productExernalId);

				boolean isValidPromotion = validatePromotions(orderType, productExernalId);

				if(isValidPromotion){

					com.AL.xml.v4.CustAddress cust = getAddress(orderType.getCustomerInformation().getCustomer(),
							com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());
					updatePromotionJsonObject.put("svcAddressExtId", String.valueOf(cust.getAddress().getExternalId()));
					updatePromotionJsonObject.put("billingInfoExtId", String.valueOf(orderType.getCustomerInformation().getCustomer()
							.getBillingInfoList().getBillingInfo().get(0).getExternalId()));

					promoLineItemType = createPromotion(updatePromotionJsonObject, item);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return promoLineItemType;
	}*/

	public LineItemCollectionType addPromotions(String updatePromotionJson, HttpServletRequest request, String orderId, LineItemType item){

		LineItemCollectionType liCollection = null;
		OrderType orderType = oFactory.createOrderType();
		try{
			if(updatePromotionJson != null){
				liCollection = oFactory.createLineItemCollectionType();
				JSONObject updatePromotionJsonObject = new JSONObject(updatePromotionJson);

				logger.info("UPDATE PROMOTION JSON OBJECT :::::::::::: "+updatePromotionJsonObject);

				//JSONObject order = updatePromotionJsonObject.getJSONObject("order");
				//String orderId = order.getString("externalId");
				String productExernalId = (String) updatePromotionJsonObject.get("productExternalId");
				logger.info("productExernalId :::::::::::: "+productExernalId);
				//JSONObject lineItemJSON = order.getJSONObject("lineItem");
				//String productExernalId = lineItemJSON.getString("productExternalId");
				//JSONObject lineItemJSON = new JSONObject(); 
				orderType = getOrder(orderId);

				boolean isValidPromotion = validatePromotions(orderType, productExernalId);

				if(isValidPromotion){

					com.AL.xml.v4.CustAddress cust = getAddress(orderType.getCustomerInformation().getCustomer(),
							com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());
					updatePromotionJsonObject.put("svcAddressExtId", String.valueOf(cust.getAddress().getExternalId()));
					updatePromotionJsonObject.put("billingInfoExtId", String.valueOf(orderType.getCustomerInformation().getCustomer()
							.getBillingInfoList().getBillingInfo().get(0).getExternalId()));

					liCollection.getLineItem().add(createPromotion(updatePromotionJsonObject, item));
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return liCollection;
	}


	public com.AL.xml.v4.CustAddress getAddress(Customer customer, final String key) {
		boolean isRole = Boolean.FALSE;
		if ((customer != null) && (customer.getAddressList() != null)) {
			List<com.AL.xml.v4.CustAddress> custAddressList = customer.getAddressList().getCustomerAddress();
			if (custAddressList != null) {
				for (com.AL.xml.v4.CustAddress custAddress : custAddressList) {
					if ((custAddress != null)
							&& (custAddress.getAddressRoles() != null)) {
						List<com.AL.xml.v4.RoleType> roleTypeList = custAddress.getAddressRoles().getRole();
						for (com.AL.xml.v4.RoleType roleType : roleTypeList) {
							if (key.equals(roleType.name())) {
								isRole = Boolean.TRUE;
								break;
							}
						}
						if (isRole) {
							return custAddress;
						}
					}
				}
			}
		}
		return new com.AL.xml.v4.CustAddress();
	}

	private boolean validatePromotions(OrderType orderType, String selectedPromotion) {

		List<String> orderPromtions = new ArrayList<String>();
		if(orderType.getLineItems() != null){
			for(LineItemType lineItem : orderType.getLineItems().getLineItem()){
				if(lineItem.getLineItemDetail().getDetailType().equals("productPromotion")){
					logger.info("orderPromtion ::-->> "+lineItem.getLineItemDetail().getDetail().getPromotionLineItem().getPromotion().getExternalId()
							+"_"+lineItem.getLineItemDetail().getDetail().getPromotionLineItem().getAppliesTo().get(0));
					orderPromtions.add(lineItem.getLineItemDetail().getDetail().getPromotionLineItem().getPromotion().getExternalId()
							+"_"+lineItem.getLineItemDetail().getDetail().getPromotionLineItem().getAppliesTo().get(0));
				}
			}
		}
		logger.info("selectedPromotion ::-->> "+selectedPromotion);
		if(orderPromtions.contains(selectedPromotion)){
			return false;
		}else{
			return true;
		}

	}

	public OrderType getOrder(String orderId) {
		OrderType order = OrderCacheService.INSTANCE.get(orderId);
		boolean isFound = Boolean.FALSE;
		retry: for (int i = 0; i < 3; i++) {
			if (order == null) {
				order = OrderService.INSTANCE.getOrderByOrderNumber(orderId,Boolean.FALSE);
				isFound = (order != null);
				if (isFound) {
					OrderCacheService.INSTANCE.store(order, orderId);
					break retry;
				}
			}
		}
		return order;
	}

	/**
	 * @param selectedLineItem
	 * @return
	 * @throws JSONException
	 */
	public LineItemType createPromotion(JSONObject selectedLineItem, LineItemType item) throws JSONException{

		String partnerExternalId = item.getPartnerExternalId();
		ProviderSourceType providerSourceType = item.getProductDatasource();
		String providerSourceBaseType = "";
		String providerName = "";
		if(providerSourceType.getValue().value().equalsIgnoreCase("INTERNAL")){
			providerSourceBaseType = "internal";
		}

		for(AttributeEntityType entity : item.getLineItemAttributes().getEntity()){
			if(entity.getSource().equalsIgnoreCase("PROVIDER_NAME")){
				providerName = entity.getAttribute().get(0).getValue(); 
			}
		}

		String productExernalId = (String) selectedLineItem.get("productExternalId");
		String svcAddressExtId = selectedLineItem.getString("svcAddressExtId");
		String billingInfoExtId = selectedLineItem.getString("billingInfoExtId");
		//item.getLineItemDetail().getDetail().getPromotionLineItem().getAppliesTo().get(0)
		//logger.info("APPLIES TO ::::::::::::: "+item.getLineItemDetail().getDetail().getPromotionLineItem().getAppliesTo().get(0));

		String conditions = selectedLineItem.getString("conditions");
		String description = selectedLineItem.getString("description");
		String priceValue = selectedLineItem.getString("priceValue");
		String priceValueType = selectedLineItem.getString("priceValueType");
		String promoCode = selectedLineItem.getString("promoCode");
		String qualification = selectedLineItem.getString("qualification");
		String shortDescription = selectedLineItem.getString("shortDescription");
		String type = selectedLineItem.getString("type");

		LineItemType lineItemType = oFactory.createLineItemType();
		lineItemType.setExternalId(0L);
		lineItemType.setService(ServiceType.BUSINESS);
		lineItemType.setState(1);
		lineItemType.setSvcAddressExtId(svcAddressExtId);
		lineItemType.setBillingInfoExtId(billingInfoExtId);
		lineItemType.setPartnerExternalId(partnerExternalId);

		ProviderSourceType pstValue = oFactory.createProviderSourceType();
		pstValue.setDatasource(providerName);
		pstValue.setValue(getProviderSourceBaseType(providerSourceBaseType));
		lineItemType.setProductDatasource(pstValue);

		LineItemDetailType lineItemDetailType = oFactory.createLineItemDetailType();
		lineItemDetailType.setDetailType("productPromotion");

		OrderLineItemDetailTypeType orderLineItemDetailTypeType = oFactory.createOrderLineItemDetailTypeType();
		ApplicableType applies = new ApplicableType();

		logger.info("Line Item Number - >> "+item.getLineItemNumber());
		applies.getAppliesTo().add(item.getLineItemNumber());

		ProductPromotionType productPromotion = oFactory.createProductPromotionType();
		productPromotion.setConditions(conditions);
		productPromotion.setDescription(description);
		productPromotion.setExternalId(productExernalId);
		productPromotion.setPriceValue(Float.parseFloat(priceValue));
		productPromotion.setPriceValueType(priceValueType);
		productPromotion.setPromoCode(promoCode);
		productPromotion.setQualification(qualification);
		productPromotion.setShortDescription(shortDescription);
		productPromotion.setType(type);

		applies.setPromotion(productPromotion);
		orderLineItemDetailTypeType.setPromotionLineItem(applies);

		lineItemDetailType.setDetail(orderLineItemDetailTypeType);
		lineItemType.setLineItemDetail(lineItemDetailType);

		LineItemAttributeType lineItemAttributeType=oFactory.createLineItemAttributeType();
		Map<String, String> lineItemAttributesMap = new HashMap<String, String>();
		if(conditions.length() > 500 ){
			conditions = conditions.substring(0,500);
		}
		if(qualification.length() > 500 ){
			qualification = qualification.substring(0,500);
		}
		if(description.length() > 500 ){
			description = description.substring(0,500);
		}
		lineItemAttributesMap.put("conditions", conditions);
		lineItemAttributesMap.put("description", description);
		lineItemAttributesMap.put("priceValue", priceValue);
		lineItemAttributesMap.put("priceValueType", priceValueType);
		lineItemAttributesMap.put("promoCode", promoCode);
		lineItemAttributesMap.put("qualification", qualification);
		lineItemAttributesMap.put("shortDescription", shortDescription);
		lineItemAttributeType.getEntity().add(setAttributeEntityType(lineItemAttributesMap,Constants.PROMOTION_DETAILS));
		lineItemType.setLineItemAttributes(lineItemAttributeType);

		return lineItemType;
	}
	public ProviderSourceBaseType getProviderSourceBaseType(String value){
		for (ProviderSourceBaseType c: ProviderSourceBaseType.values()) {
			if (c.value().toString().equalsIgnoreCase(value)) {
				return c;
			}
		}
		return null;
	}

	public AttributeEntityType setAttributeEntityType(Map<String, String> map, String source){

		AttributeEntityType entity =oFactory.createAttributeEntityType();
		entity.setSource(source);
		for (Entry<String, String> entry : map.entrySet()) {
			logger.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			AttributeDetailType attribute = oFactory.createAttributeDetailType();
			attribute.setName(entry.getKey());
			attribute.setValue(entry.getValue());
			entity.getAttribute().add(attribute);
		}

		return entity;
	}

	public LineItemCollectionType updatePromotions(String selectedPromotionJson, HttpServletRequest request, String orderId, LineItemType item){

		LineItemCollectionType liCoType = oFactory.createLineItemCollectionType();

		LineItemType lineItemType = oFactory.createLineItemType();
		String description = "";
		String priceValue = "";
		String priceValueType = "";
		String promoCode = "";
		String qualification = "";
		String shortDescription = "";
		String type = "";
		String conditions = "";
		String productDetailType = "";
		String productDataSource = "";
		JSONObject selectedLineItemJSONObject = null;
		try{
			selectedLineItemJSONObject = new JSONObject(selectedPromotionJson);
		}catch (JSONException e) {
			e.printStackTrace();
		}
		String productExernalId = "";

		int appliesTo = item.getLineItemNumber();

		try {
			conditions = selectedLineItemJSONObject.getString("conditions");
			description = selectedLineItemJSONObject.getString("description");
			priceValue = selectedLineItemJSONObject.getString("priceValue");
			priceValueType = selectedLineItemJSONObject.getString("priceValueType");
			promoCode = selectedLineItemJSONObject.getString("promoCode");
			qualification = selectedLineItemJSONObject.getString("qualification");
			shortDescription = selectedLineItemJSONObject.getString("shortDescription");
			type = selectedLineItemJSONObject.getString("type");
			productExernalId = (String) selectedLineItemJSONObject.get("productExternalId");
			productDetailType = selectedLineItemJSONObject.getString("productDetailType");
			productDataSource = selectedLineItemJSONObject.getString("productDataSource");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		lineItemType.setExternalId(item.getExternalId());
		lineItemType.setService(ServiceType.BUSINESS);
		lineItemType.setState(1);

		ProviderSourceType pstValue = oFactory.createProviderSourceType();
		pstValue.setDatasource(productDetailType);
		pstValue.setValue(getProviderSourceBaseType(productDataSource));
		lineItemType.setProductDatasource(pstValue);

		LineItemDetailType lineItemDetailType = oFactory.createLineItemDetailType();
		lineItemDetailType.setDetailType("productPromotion");

		OrderLineItemDetailTypeType orderLineItemDetailTypeType = oFactory.createOrderLineItemDetailTypeType();
		ApplicableType applies = new ApplicableType();
		applies.getAppliesTo().add(appliesTo);
		applies.setIsAppliesToInternal(Boolean.FALSE);

		ProductPromotionType productPromotion = oFactory.createProductPromotionType();
		productPromotion.setConditions(conditions);
		productPromotion.setDescription(description);
		productPromotion.setExternalId(productExernalId);
		productPromotion.setPriceValue(Float.parseFloat(priceValue));
		productPromotion.setPriceValueType(priceValueType);
		productPromotion.setPromoCode(promoCode);
		productPromotion.setQualification(qualification);
		productPromotion.setShortDescription(shortDescription);
		productPromotion.setType(type);

		applies.setPromotion(productPromotion);
		orderLineItemDetailTypeType.setPromotionLineItem(applies);

		lineItemDetailType.setDetail(orderLineItemDetailTypeType);
		lineItemType.setLineItemDetail(lineItemDetailType);

		//LineItemAttributeType lineItemAttributeType=oFactory.createLineItemAttributeType();
		if(conditions.length() > 500 ){
			conditions = conditions.substring(0,500);
		}
		if(qualification.length() > 500 ){
			qualification = qualification.substring(0,500);
		}
		if(description.length() > 500 ){
			description = description.substring(0,500);
		}
		//lineItemType.setLineItemAttributes(lineItemAttributeType);
		//removing line item features since it is causing errors in price service
		lineItemType.setSelectedFeatures(null);
		//removing line item features since it is causing errors in price service
		lineItemType.setActiveDialogs(null);
		liCoType.getLineItem().add(lineItemType);
		return liCoType;
	}

	public LineItemCollectionType buildPromotionLineItemCollectionType(PromotionVO promotionVO,OrderQualVO orderQualVO){

		LineItemCollectionType liCoType = oFactory.createLineItemCollectionType();

		LineItemType lineItemType = oFactory.createLineItemType();

		int appliesTo = orderQualVO.getLineItemNumber();
		com.AL.xml.pr.v4.ProductPromotionType promotionOBJ = promotionVO.getPromotionObj();
		lineItemType.setExternalId(orderQualVO.getLineItemExternalId());
		lineItemType.setService(ServiceType.BUSINESS);
		lineItemType.setState(1);

		ProviderSourceType pstValue = oFactory.createProviderSourceType();
		pstValue.setDatasource(orderQualVO.getProductDetailType());
		pstValue.setValue(getProviderSourceBaseType("internal"));
		lineItemType.setProductDatasource(pstValue);

		LineItemDetailType lineItemDetailType = oFactory.createLineItemDetailType();
		lineItemDetailType.setDetailType("productPromotion");

		OrderLineItemDetailTypeType orderLineItemDetailTypeType = oFactory.createOrderLineItemDetailTypeType();
		ApplicableType applies = new ApplicableType();
		applies.getAppliesTo().add(appliesTo);
		applies.setIsAppliesToInternal(Boolean.FALSE);

		ProductPromotionType productPromotion = oFactory.createProductPromotionType();
		productPromotion.setConditions(promotionOBJ.getConditions());
		productPromotion.setDescription(promotionOBJ.getDescription());
		productPromotion.setExternalId(promotionOBJ.getExternalId());
		productPromotion.setPriceValue(promotionOBJ.getPriceValue());
		productPromotion.setPriceValueType(promotionOBJ.getPriceValueType());
		productPromotion.setPromoCode(promotionOBJ.getPromoCode());
		productPromotion.setQualification(promotionOBJ.getQualification());
		productPromotion.setShortDescription(promotionOBJ.getShortDescription());
		productPromotion.setType(promotionOBJ.getType());

		applies.setPromotion(productPromotion);
		orderLineItemDetailTypeType.setPromotionLineItem(applies);

		lineItemDetailType.setDetail(orderLineItemDetailTypeType);
		lineItemType.setLineItemDetail(lineItemDetailType);

		//removing line item features since it is causing errors in price service
		lineItemType.setSelectedFeatures(null);
		//removing line item features since it is causing errors in price service
		lineItemType.setActiveDialogs(null);
		liCoType.getLineItem().add(lineItemType);
		return liCoType;
	}

	/**
	 * @param productInfo
	 * @return
	 */
	public List<PromotionVO> buildPromotioVOList(ProductInfoType productInfo,Map<String,Long>  lineItemTypePromotionMap) {
		List<PromotionVO> promotionVOList = new ArrayList<PromotionVO>();
		try{
			if(productInfo!= null &&  productInfo.getProductDetails()!= null 
					&& productInfo.getProductDetails().getPromotion() != null){
				List<String> conflictsExtIdList = new ArrayList<String>();
				List<String> baseMonthlyPromotions = new ArrayList<String>();
				if (productInfo.getProductDetails().getPromotion().size() == 1){
					com.AL.xml.pr.v4.ProductPromotionType productPromotionType = productInfo.getProductDetails().getPromotion().get(0);
					PromotionVO promotionVO = buildPromotionData(productPromotionType,productInfo);
					if (productPromotionType.getType() != null && productPromotionType.getType().equalsIgnoreCase(Constants.BASE_MONTHLY_DISCOUNT)){
						promotionVO.setBaseMonthlyPromo(true);
					}
					if(lineItemTypePromotionMap.get(promotionVO.getPromotionObj().getExternalId()) != null){
						promotionVO.setChecked(true);
					}
					promotionVO.setDisplayInputType("checkbox");
					promotionVOList.add(promotionVO);
				}else{
					for(com.AL.xml.pr.v4.ProductPromotionType productPromotion:productInfo.getProductDetails().getPromotion()){
						if (productPromotion.getPreconditions() != null && productPromotion.getPreconditions().getConflicts() != null){
							for (String promotionExternalId : productPromotion.getPreconditions().getConflicts().getPromotionExternalId()){
								conflictsExtIdList.add(promotionExternalId);
							}	
						}
						if (productPromotion.getType() != null && productPromotion.getType().equalsIgnoreCase(Constants.BASE_MONTHLY_DISCOUNT)){
							baseMonthlyPromotions.add(productPromotion.getExternalId());
						}
					}
					for(com.AL.xml.pr.v4.ProductPromotionType productPromotion:productInfo.getProductDetails().getPromotion()){
						PromotionVO promotionVO = buildPromotionData(productPromotion,productInfo);
						if(conflictsExtIdList.contains(productPromotion.getExternalId()) || (baseMonthlyPromotions.contains(productPromotion.getExternalId()))){
							promotionVO.setBaseMonthlyPromo(true);
							if(baseMonthlyPromotions.size() == 1){
								promotionVO.setDisplayInputType("checkbox");
							}else {
								promotionVO.setDisplayInputType("radio");
							}
						}else{
							promotionVO.setBaseMonthlyPromo(false);
							promotionVO.setDisplayInputType("checkbox");
						}
						if(lineItemTypePromotionMap.get(promotionVO.getPromotionObj().getExternalId()) != null){
							promotionVO.setChecked(true);
						}
						promotionVOList.add(promotionVO);	
					}

				}
				logger.info("promotionVOJSONList="+promotionVOList);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return promotionVOList;
	}

	/**
	 * @param productPromotionType
	 * @param productInfo
	 * @return
	 * @throws Exception
	 */
	private PromotionVO buildPromotionData(com.AL.xml.pr.v4.ProductPromotionType productPromotionType,ProductInfoType productInfo) throws Exception {
		PromotionVO promotionVO = new PromotionVO();
		Map<String,String> displayInputMap = buildDiaplayPromotionInputValue(productPromotionType,productInfo);
		productPromotionType.setDescription(Jsoup.parse(productPromotionType.getDescription(), "UTF-8").text());
		productPromotionType.setShortDescription(Jsoup.parse(productPromotionType.getShortDescription(),"UTF-8").text());
		productPromotionType.setConditions(Jsoup.parse(productPromotionType.getConditions(),"UTF-8").text());
		productPromotionType.setQualification(Jsoup.parse(productPromotionType.getQualification(),"UTF-8").text());
		promotionVO .setPromotionObj(productPromotionType);
		promotionVO.setDisplayInputValue(displayInputMap);
		return promotionVO;
	}

	/**
	 * @param productPromotionType
	 * @param productInfo
	 * @return
	 * @throws Exception
	 */
	private Map<String,String> buildDiaplayPromotionInputValue(com.AL.xml.pr.v4.ProductPromotionType productPromotionType,ProductInfoType productInfo) throws Exception {
		Map<String,String> displayInputMap = new HashMap<String,String>();
		displayInputMap.put("priceValueType", productPromotionType.getPriceValueType());
		displayInputMap.put("featureExternalID", productPromotionType.getExternalId());
		displayInputMap.put("includedFlag", "true");
		displayInputMap.put("shortDescription",productPromotionType.getShortDescription());
		if (productPromotionType.getType() != null && Constants.BASE_MONTHLY_DISCOUNT.equalsIgnoreCase(productPromotionType.getType())){
			if(Constants.RELATIVE.equalsIgnoreCase(productPromotionType.getPriceValueType())){
				displayInputMap.put(Constants.RECURING_PRICE, String.format(Constants._2F, productInfo.getProduct().getPriceInfo().getBaseRecurringPrice()-productPromotionType.getPriceValue()));
				displayInputMap.put(Constants.PROMO_SIGN, "-$"+String.format(Constants._2F, productPromotionType.getPriceValue()));
				displayInputMap.put("promotionPrice", String.format(Constants._2F, productPromotionType.getPriceValue()));
			} else if (Constants.ABSOLUTE.equalsIgnoreCase(productPromotionType.getPriceValueType())){
				displayInputMap.put(Constants.RECURING_PRICE,String.format(Constants._2F, productPromotionType.getPriceValue()));
				displayInputMap.put("promotionPrice", String.format(Constants._2F, productPromotionType.getPriceValue()));
			}
			
		} else if (productPromotionType.getType() != null && Constants.ONE_TIME_FEE_DISCOUNT.equalsIgnoreCase(productPromotionType.getType())) {

			if (productPromotionType.getPriceValueType() != null) {
				if (Constants.RELATIVE.equalsIgnoreCase(productPromotionType.getPriceValueType())) {
					displayInputMap.put(Constants.NON_RECURING_PRICE, String.format(Constants._2F, productInfo.getProduct().getPriceInfo().getBaseNonRecurringPrice() - productPromotionType.getPriceValue()));
					displayInputMap.put(Constants.PROMO_SIGN, "-$"+String.format(Constants._2F, productPromotionType.getPriceValue()));
					displayInputMap.put("promotionPrice", String.format(Constants._2F, productPromotionType.getPriceValue()));
				} else if (Constants.ABSOLUTE.equalsIgnoreCase(productPromotionType.getPriceValueType())) {
					displayInputMap.put(Constants.NON_RECURING_PRICE, String.format(Constants._2F, productPromotionType.getPriceValue()));
					displayInputMap.put(Constants.PROMO_SIGN, "-$"+String.format(Constants._2F, productInfo.getProduct().getPriceInfo().getBaseNonRecurringPrice() - productPromotionType.getPriceValue()));
					displayInputMap.put("promotionPrice", String.format(Constants._2F, productPromotionType.getPriceValue()));
				} 
			}
		} else {
			displayInputMap.put(Constants.NON_RECURING_PRICE, String.format(Constants._2F, productPromotionType.getPriceValue()));
		}
		displayInputMap.put("type", "promotion");
		displayInputMap.put("promotionType", productPromotionType.getType());
		return displayInputMap;
	}
	
	/**
	 * @param order
	 * @param orderQualVO
	 * @return
	 */
	public Map<String,Long> getLineItemTypePromotionMap(OrderType order, OrderQualVO orderQualVO) {
		Map<String,Long> promoLineItemMap =  new HashMap<String, Long>();
		if(order.getLineItems() != null){
			int lineItemNumber = orderQualVO.getLineItemNumber();
			for(LineItemType lineItemTypes : order.getLineItems().getLineItem()){
				if(lineItemTypes.getLineItemDetail().getDetailType().equals("productPromotion") && 
						!lineItemTypes.getLineItemStatus().getStatusCode().value().equalsIgnoreCase("CANCELLED_REMOVED")){
					OrderLineItemDetailTypeType detailType = lineItemTypes.getLineItemDetail().getDetail();
					boolean appliesToSameLineItem = false;
					if(lineItemNumber == lineItemTypes.getLineItemDetail().getDetail().getPromotionLineItem().getAppliesTo().get(0)){
						appliesToSameLineItem = true;
					}
					if(appliesToSameLineItem){
						promoLineItemMap.put(detailType.getPromotionLineItem().getPromotion().getExternalId(), lineItemTypes.getExternalId());
					}
				}
			}
		}
		return  promoLineItemMap; 
	}
}
