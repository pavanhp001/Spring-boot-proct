package com.AL.ui.builder;

import java.util.HashMap;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.AL.ui.constants.Constants;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.factory.CartPromotionFactory;
import com.AL.ui.factory.CartRulesFactory;
import com.AL.ui.util.CartLineItemUtil;
import com.AL.xml.v4.ApplicableType;
import com.AL.xml.v4.FeatureValueType;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemDetailType;
import com.AL.xml.v4.LineItemPriceInfoType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.LinkableType;
import com.AL.xml.v4.OrderLineItemDetailTypeType;
import com.AL.xml.v4.PriceInfoType;
import com.AL.xml.v4.ProductPromotionType;
import com.AL.xml.v4.ProviderSourceType;
import com.AL.xml.v4.ProviderType;
import com.AL.xml.v4.SelectedFeaturesType;
import com.AL.xml.v4.ServiceType;

/**
 * @author Preetam
 *
 */
public enum CartLineItemBuilder {

	INSTANCE;

	private com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();

	private static final Logger logger = Logger.getLogger(CartLineItemBuilder.class);
	
	/**
	 * @param selectedLineItem
	 * @return
	 * @throws JSONException
	 */
	public LineItemType createPromotion(JSONObject selectedLineItem) throws JSONException{
		logger.info("createPromotion_begin_in_CartLineItemBuilder");
		String productExernalId = selectedLineItem.getString(Constants.PROMO_PRODUCT_ID);
		String partnerExternalId = selectedLineItem.getString(Constants.PARTNER_ID);
		String svcAddressExtId = selectedLineItem.getString(Constants.ADDRESS_ID);
		String billingInfoExtId = selectedLineItem.getString(Constants.BILLINGINFO_ID);
		String providerSourceBaseType = selectedLineItem.getString(Constants.PROVIDER_SOURCE_TYPE);
		String providerName = CartLineItemUtil.validateProviderName( selectedLineItem.getString(Constants.PROVIDER_NAME_VALUE));
		Integer appliesTo = selectedLineItem.getInt(Constants.APPLIES_TO);
		String conditions = selectedLineItem.getString(Constants.CONDITIONS);
		String description = selectedLineItem.getString(Constants.DESCRIPTION);
		String priceValue = selectedLineItem.getString(Constants.PRICE);
		String priceValueType = selectedLineItem.getString(Constants.PRICETYPE);
		String promoCode = selectedLineItem.getString(Constants.PROMOCODE);
		String qualification = selectedLineItem.getString(Constants.QUALIFICATION);
		String shortDescription = selectedLineItem.getString(Constants.SHORTDESCRIPTION);
		String type = selectedLineItem.getString(Constants.TYPE);
		boolean isAppliesToInternal = selectedLineItem.getBoolean(Constants.APPLIES_TO_INTERNAL);

		LineItemType lineItemType = oFactory.createLineItemType();
		lineItemType.setExternalId(0L);
		lineItemType.setService(ServiceType.BUSINESS);
		lineItemType.setState(1);
		lineItemType.setSvcAddressExtId(svcAddressExtId);
		lineItemType.setBillingInfoExtId(billingInfoExtId);
		lineItemType.setPartnerExternalId(partnerExternalId);

		lineItemType.setProductDatasource(createProviderSourceType(providerName,providerSourceBaseType));

		LineItemDetailType lineItemDetailType = oFactory.createLineItemDetailType();
		lineItemDetailType.setDetailType("productPromotion");

		OrderLineItemDetailTypeType orderLineItemDetailTypeType = oFactory.createOrderLineItemDetailTypeType();
		ApplicableType applies = new ApplicableType();
		applies.getAppliesTo().add(appliesTo);
		applies.setIsAppliesToInternal(isAppliesToInternal);

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
		
		if(selectedLineItem.has(Constants.PROMO_DURATION)){
			String promoDuration = selectedLineItem.getString(Constants.PROMO_DURATION);
			try {
				Duration dura = DatatypeFactory.newInstance().newDuration(promoDuration);
				productPromotion.setPromotionDuration(dura);
			} catch (DatatypeConfigurationException e) {
				logger.error("error_in_CartLineItemBuilder",e);
			}

		}

		applies.setPromotion(productPromotion);
		orderLineItemDetailTypeType.setPromotionLineItem(applies);

		lineItemDetailType.setDetail(orderLineItemDetailTypeType);
		lineItemType.setLineItemDetail(lineItemDetailType);
		
		//lineItemType.setLineItemAttributes(createPromtionLineItemAttributes(productPromotion));
		logger.info("createPromotion_end_in_CartLineItemBuilder");
		return lineItemType;
	}
	/**
	 * @param selectedLineItem
	 * @return
	 * @throws JSONException
	 */
	public LineItemType createPromotion(LineItemType lineItem){
		logger.info("createPromotion_begin_in_CartLineItemBuilder_with_line_item_type");
		ApplicableType lineItemApplies = CartPromotionFactory.INSTANCE.getApplicableType(lineItem);
		ProductPromotionType lineItemProductPromotion = lineItemApplies.getPromotion();

		String productExernalId = lineItemProductPromotion.getExternalId();
		String conditions = lineItemProductPromotion.getConditions();
		String description = lineItemProductPromotion.getDescription();
		Float priceValue = lineItemProductPromotion.getPriceValue();
		String priceValueType = lineItemProductPromotion.getPriceValueType();
		String promoCode = lineItemProductPromotion.getPromoCode();
		String qualification = lineItemProductPromotion.getQualification();
		String shortDescription =lineItemProductPromotion.getShortDescription();
		String type = lineItemProductPromotion.getType();
		String dataSource = lineItem.getProductDatasource().getValue().toString();
		boolean isAppliesToInternal = false;

		LineItemType lineItemType = oFactory.createLineItemType();
		lineItemType.setExternalId(0L);
		lineItemType.setService(ServiceType.BUSINESS);
		lineItemType.setState(1);
		lineItemType.setSvcAddressExtId(lineItem.getSvcAddressExtId());
		lineItemType.setBillingInfoExtId(lineItem.getBillingInfoExtId());
		
		lineItemType.setProductDatasource(createProviderSourceType(dataSource,dataSource));

		LineItemDetailType lineItemDetailType = oFactory.createLineItemDetailType();
		lineItemDetailType.setDetailType("productPromotion");

		OrderLineItemDetailTypeType orderLineItemDetailTypeType = oFactory.createOrderLineItemDetailTypeType();
		ApplicableType applies = new ApplicableType();
		applies.getAppliesTo().addAll(lineItem.getLineItemDetail().getDetail().getPromotionLineItem().getAppliesTo());
		applies.setIsAppliesToInternal(isAppliesToInternal);

		ProductPromotionType productPromotion = oFactory.createProductPromotionType();
		productPromotion.setConditions(conditions);
		productPromotion.setDescription(description);
		productPromotion.setExternalId(productExernalId);
		productPromotion.setPriceValue(priceValue);
		productPromotion.setPriceValueType(priceValueType);
		productPromotion.setPromoCode(promoCode);
		productPromotion.setQualification(qualification);
		productPromotion.setShortDescription(shortDescription);
		productPromotion.setType(type);

		if(lineItemProductPromotion.getPromotionDuration() != null){
			productPromotion.setPromotionDuration(lineItemProductPromotion.getPromotionDuration());
		}

		applies.setPromotion(productPromotion);
		orderLineItemDetailTypeType.setPromotionLineItem(applies);

		lineItemDetailType.setDetail(orderLineItemDetailTypeType);
		lineItemType.setLineItemDetail(lineItemDetailType);

		//lineItemType.setLineItemAttributes(createPromtionLineItemAttributes(productPromotion));
		logger.info("createPromotion_end_in_CartLineItemBuilder_with_line_item_type");
		return lineItemType;
	}
	/**
	 * @param selectedLineItem
	 * @param productUniqueIdMap
	 * @return
	 * @throws JSONException
	 */
	public LineItemType createLineItem(JSONObject selectedLineItem , Map<String,String> productUniqueIdMap) throws JSONException{
		logger.info("createLineItem_begin_in_CartLineItemBuilder");
		String productExernalId = selectedLineItem.getString(Constants.PRODUCT_ID);
		String partnerExternalId = selectedLineItem.getString(Constants.PARTNER_ID);
		String svcAddressExtId = selectedLineItem.getString(Constants.ADDRESS_ID);
		String billingInfoExtId = selectedLineItem.getString(Constants.BILLINGINFO_ID);
		String img_id = selectedLineItem.getString(Constants.IMAGE_VALUE);
		String providerName = CartLineItemUtil.validateProviderName( selectedLineItem.getString(Constants.PROVIDER_NAME_VALUE));
		String providerSourceBaseType = selectedLineItem.getString(Constants.PROVIDER_SOURCE_TYPE);
		String productname = selectedLineItem.getString(Constants.PRODUCT_NAME_VALUE);
		String nonrecuring = selectedLineItem.getString(Constants.BASE_NON_RECURRING_PRICE);
		String recuring = selectedLineItem.getString(Constants.BASE_RECURRING_PRICE);
		JSONObject capabilityMap =  selectedLineItem.getJSONObject(Constants.CAPABILITY_MAP);
		String productTypeCategory = selectedLineItem.getString(Constants.PRODUCT_TYPE);
		
		productUniqueIdMap.put(partnerExternalId+"_"+productExernalId,nonrecuring+"|"+recuring);

		LineItemType lineItemType = oFactory.createLineItemType();

		lineItemType.setPartnerName(providerName);
		lineItemType.setExternalId(0L);
		lineItemType.setService(ServiceType.BUSINESS);
		lineItemType.setState(1);
		lineItemType.setSvcAddressExtId(svcAddressExtId);
		lineItemType.setBillingInfoExtId(billingInfoExtId);
		lineItemType.setPartnerExternalId(partnerExternalId);

		lineItemType.setProductDatasource(createProviderSourceType(providerName,providerSourceBaseType));

		lineItemType.setLineItemPriceInfo(createLineItemPriceInfoType(Double.parseDouble(recuring.replace("$", "")),Double.parseDouble(nonrecuring.replace("$", ""))));
		
		if(selectedLineItem.has(Constants.TPV_PRODUCT) && selectedLineItem.getBoolean(Constants.TPV_PRODUCT)){
			lineItemType.setEventType(1);
		}
		else if(selectedLineItem.has(Constants.WARM_TRANSFER_PRODUCT) && selectedLineItem.getBoolean(Constants.WARM_TRANSFER_PRODUCT)){
			lineItemType.setEventType(2);
		}
		
		LineItemDetailType lineItemDetailType = oFactory.createLineItemDetailType();
		lineItemDetailType.setDetailType("product");
		lineItemDetailType.setProductUniqueId(null);


		OrderLineItemDetailTypeType orderLineItemDetailTypeType = oFactory.createOrderLineItemDetailTypeType();
		LinkableType productType = oFactory.createLinkableType();
		productType.setExternalId(productExernalId);
		productType.setName(productname);
		ProviderType providerType = oFactory.createProviderType();
		providerType.setExternalId(partnerExternalId);
		providerType.setName(providerName);
		productType.setProvider(providerType);
		orderLineItemDetailTypeType.setProductLineItem(productType);

		lineItemType = CartLineItemBuilder.INSTANCE.createLineItemFeatures(lineItemType,selectedLineItem);

		LineItemAttributeType lineItemAttributeType=oFactory.createLineItemAttributeType();
		//Change for the productName Issue
		lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType( Constants.NAME, 
				providerName, "", Constants.PROVIDER_NAME));
		lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType( Constants.NAME, 
				productname, "", Constants.PRODUCT_NAME));
		lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType( Constants.ID, 
				img_id, "", Constants.IMAGE_ID));

		if(productTypeCategory.equals(Constants.DOUBLE_PLAY)){
			lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType( Constants.PRODUCT_TYPE, 
					CartRulesFactory.INSTANCE.getDoublePlay(capabilityMap),"", Constants.PRODUCT_CATEGORY));
		}else{
			lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType( Constants.PRODUCT_TYPE, 
					productTypeCategory, "", Constants.PRODUCT_CATEGORY));
		}
		lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType( Constants.STATUS, Constants.CKO_READY,
				Constants.DESC_CKO_READY, Constants.CKO));	
		lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType( Constants.BASEMONTHLYFEE, recuring.replace("$", ""),
				"", Constants.CKO));
		lineItemType.setLineItemAttributes(lineItemAttributeType);

		lineItemDetailType.setDetail(orderLineItemDetailTypeType);
		lineItemType.setLineItemDetail(lineItemDetailType);
		logger.info("createLineItem_end_in_CartLineItemBuilder");
		return lineItemType;
	}

	/**
	 * sets the baseRecurringPrice & baseNonRecurringPrice in LineItemPriceInfoType and returns it.
	 * 
	 * 
	 * @param baseRecurringPrice
	 * @param baseNonRecurringPrice
	 * @return LineItemPriceInfoType
	 */
	public LineItemPriceInfoType createLineItemPriceInfoType(Double baseRecurringPrice,Double baseNonRecurringPrice){
		logger.info("createLineItemPriceInfoType_begin_in_CartLineItemBuilder");
		LineItemPriceInfoType lineItemPriceInfoType = oFactory.createLineItemPriceInfoType();
		
		lineItemPriceInfoType.setBaseRecurringPrice(baseRecurringPrice);
		lineItemPriceInfoType.setBaseRecurringPriceUnits(Constants.PRICE_UNITS);
		lineItemPriceInfoType.setBaseNonRecurringPrice(baseNonRecurringPrice);
		lineItemPriceInfoType.setBaseNonRecurringPriceUnits(Constants.PRICE_UNITS);
		logger.info("createLineItemPriceInfoType_end_in_CartLineItemBuilder");
		return lineItemPriceInfoType;
	}

	/**
	 * creates the ProviderSourceType and sets the data source value
	 * @param providerName
	 * @param providerSourceBaseType
	 * @return ProviderSourceType
	 */
	public ProviderSourceType createProviderSourceType(String providerName,String providerSourceBaseType){

		ProviderSourceType pstValue = oFactory.createProviderSourceType();
		pstValue.setDatasource(providerName);
		pstValue.setValue(CartLineItemFactory.INSTANCE.getProviderSourceBaseType(providerSourceBaseType));

		return pstValue;
	}
	
	/**
	 * Creates Promotion LineItemAttributes
	 * 
	 * @param productPromotion
	 * @return LineItemAttributeType
	 */
	public LineItemAttributeType createPromtionLineItemAttributes(ProductPromotionType productPromotion){
		
		//Building LineItemAttribute Map
		Map<String, String> lineItemAttributesMap = new HashMap<String, String>();
		
		lineItemAttributesMap.put(Constants.SHORTDESCRIPTION, CartLineItemUtil.lineItemAttributeMaxLength(productPromotion.getShortDescription()));
		lineItemAttributesMap.put(Constants.DESCRIPTION, CartLineItemUtil.lineItemAttributeMaxLength(productPromotion.getDescription()));
		lineItemAttributesMap.put(Constants.CONDITIONS, CartLineItemUtil.lineItemAttributeMaxLength(productPromotion.getConditions()));
		lineItemAttributesMap.put(Constants.PRICE, productPromotion.getPriceValue().toString());
		lineItemAttributesMap.put(Constants.PRICETYPE, productPromotion.getPriceValueType());
		lineItemAttributesMap.put(Constants.PROMOCODE, productPromotion.getPromoCode());
		lineItemAttributesMap.put(Constants.QUALIFICATION, CartLineItemUtil.lineItemAttributeMaxLength(productPromotion.getQualification()));
		
		//Adding the Attributes to Source
		LineItemAttributeType lineItemAttributeType=oFactory.createLineItemAttributeType();
		lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType(lineItemAttributesMap,Constants.PROMOTION_DETAILS));
		
		return lineItemAttributeType;
	}
	
	/**
	 * @param lineItemType
	 * @param lineItem
	 * @return
	 * @throws JSONException
	 */
	public LineItemType createLineItemFeatures(LineItemType lineItemType, JSONObject lineItem) throws JSONException {
		logger.info("createLineItemFeatures_begin_in_CartLineItemBuilder");
		SelectedFeaturesType features = oFactory.createSelectedFeaturesType();
		if(lineItem.has(Constants.FEATURE_VALUE)){
			SelectedFeaturesType.Features selFeatures = oFactory.createSelectedFeaturesTypeFeatures();
			JSONObject featureValue =  lineItem.getJSONObject(Constants.FEATURE_VALUE);
			JSONArray featureType = featureValue.getJSONArray(Constants.FEATURE_TYPE);
			for(int j=0;j < featureType.length(); j++){
				FeatureValueType fVal = oFactory.createFeatureValueType();
				JSONObject feature = (JSONObject) featureType.get(j);
				logger.debug(feature);
				fVal.setExternalId(feature.getString(Constants.EXTERNAL_ID));
				fVal.setValue(feature.getString(Constants.VALUE));
				fVal.setType(feature.getString(Constants.TYPE));
				String recurringPrice = feature.getString(Constants.FEATURE_REC_PRICE);
				String nonRecuringPrice = feature.getString(Constants.FEATURE_NONREC_PRICE);
				if(!recurringPrice.equalsIgnoreCase("Included")){
					PriceInfoType price = oFactory.createPriceInfoType();
					if(recurringPrice.contains("/")){
						recurringPrice = "0.00";
					}
					price.setBaseRecurringPrice(Double.parseDouble(recurringPrice.replace("$", "")));
					price.setBaseNonRecurringPrice(Double.parseDouble(nonRecuringPrice.replace("$", "")));
					fVal.setPrice(price);
				}
				selFeatures.getFeatureValue().add(fVal);
				features.setFeatures(selFeatures);
			}
			lineItemType.setSelectedFeatures(features);
		}
		if(lineItem.has(Constants.FEATURE_GROUP)){
			SelectedFeaturesType.FeatureGroup selFeatureGroup = oFactory.createSelectedFeaturesTypeFeatureGroup();
			JSONArray featureGroups = lineItem.getJSONArray(Constants.FEATURE_GROUP);	
			for(int j=0;j < featureGroups.length(); j++){
				selFeatureGroup = oFactory.createSelectedFeaturesTypeFeatureGroup();
				JSONObject featureGroup = (JSONObject) featureGroups.get(j);
				if(!featureGroup.isNull(Constants.FEATURE_VALUE)){
					JSONObject featureVal = featureGroup.getJSONObject(Constants.FEATURE_VALUE);
					FeatureValueType fVal = oFactory.createFeatureValueType();
					fVal.setExternalId(featureVal.getString(Constants.EXTERNAL_ID));
					fVal.setValue(featureVal.getString(Constants.VALUE));
					fVal.setType(featureVal.getString(Constants.TYPE));
					String recurringPrice = featureVal.getString(Constants.FEATURE_REC_PRICE);
					if(!recurringPrice.equalsIgnoreCase("INCLUDED")){
						PriceInfoType price = oFactory.createPriceInfoType();
						price.setBaseRecurringPrice(Double.parseDouble(recurringPrice.replace("$", "")));
						fVal.setPrice(price);
					}

					selFeatureGroup.getFeatureValue().add(fVal);
				}
				selFeatureGroup.setExternalId(featureGroup.getString(Constants.EXTERNAL_ID));
				selFeatureGroup.setGroupType(featureGroup.getInt(Constants.GROUP_TYPE));
				features.getFeatureGroup().add(selFeatureGroup);
			}
			lineItemType.setSelectedFeatures(features);
		}
		logger.info("createLineItemFeatures_end_in_CartLineItemBuilder");
		return lineItemType;
	}
}
