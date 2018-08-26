package com.A.ui.vo;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class SalesProductDetailsVO {

	
	private String externalId;
	private String itemExternalId;
	private String name;
	
	private JSONObject prodJson;
	
	private String productCategory;
	private List<String> channelList;
	private String source;
	
	private String parentExternalId;
	private String parentName;
	private Double baseNonRecurringPrice;
	private Double baseRecurringPrice;
	private Double score;
	private Map<String,String> capabilityMap;
	private boolean isDishProduct = false;
	private List<String> marketingHighlightsList;
	private String descriptiveInfoDescription;
	private String descriptiveInfoType;
	private String descriptiveInfoValue;
	private String metaData;
	private String promotionDescription;
	private String promotionCode;
	private String promotionExternalId;
	private List<String> promotionMetaDataList;
	private Float  promotionPrice;
	private String providerName;
	private String productType;
	private String PromotionType;
	private String PromotionPriceValueType;
	private String longDescription;
	private String conditions;
	private String qualification ;
	//private String utilityPromotionMetaData;
	private String noOfChannels;
	private boolean isSellable = true;
	private String shortDescription;
	private String internetSpeed;
	private String modemOptions;
	private String longDistance;

	public String getNoOfChannels() {
		return noOfChannels;
	}
	public void setNoOfChannels(String noOfChannels) {
		this.noOfChannels = noOfChannels;
	}
	public String getInternetSpeed() {
		return internetSpeed;
	}
	public void setInternetSpeed(String internetSpeed) {
		this.internetSpeed = internetSpeed;
	}
	public String getModemOptions() {
		return modemOptions;
	}
	public void setModemOptions(String modemOptions) {
		this.modemOptions = modemOptions;
	}
	public String getLongDistance() {
		return longDistance;
	}
	public void setLongDistance(String longDistance) {
		this.longDistance = longDistance;
	}
	public List<String> getMarketingHighlightsList() {
		return marketingHighlightsList;
	}
	public void setMarketingHighlightsList(List<String> marketingHighlightsList) {
		this.marketingHighlightsList = marketingHighlightsList;
	}
	
	public String getDescriptiveInfoValue() {
		return descriptiveInfoValue;
	}
	public void setDescriptiveInfoValue(String descriptiveInfoValue) {
		this.descriptiveInfoValue = descriptiveInfoValue;
	}
	public String getMetaData() {
		return metaData;
	}
	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}
	public String getPromotionDescription() {
		return promotionDescription;
	}
	public void setPromotionDescription(String promotionDescription) {
		this.promotionDescription = promotionDescription;
	}
	public String getPromotionCode() {
		return promotionCode;
	}
	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}
	public String getPromotionExternalId() {
		return promotionExternalId;
	}
	public void setPromotionExternalId(String promotionExternalId) {
		this.promotionExternalId = promotionExternalId;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	private String providerExternalId;	
	
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getItemExternalId() {
		return itemExternalId;
	}
	public void setItemExternalId(String itemExternalId) {
		this.itemExternalId = itemExternalId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getParentExternalId() {
		return parentExternalId;
	}
	public void setParentExternalId(String parentExternalId) {
		this.parentExternalId = parentExternalId;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Double getBaseNonRecurringPrice() {
		return baseNonRecurringPrice;
	}
	public void setBaseNonRecurringPrice(Double baseNonRecurringPrice) {
		this.baseNonRecurringPrice = baseNonRecurringPrice;
	}
	public Double getBaseRecurringPrice() {
		return baseRecurringPrice;
	}
	public void setBaseRecurringPrice(Double baseRecurringPrice) {
		this.baseRecurringPrice = baseRecurringPrice;
	}
	
	public List<String> getChannelList() {
		return channelList;
	}
	public void setChannelList(List<String> channelList) {
		this.channelList = channelList;
	}

	@Override
	public String toString() {
		return "SalesProductDetailsVO [PromotionPriceValueType="
				+ PromotionPriceValueType + ", PromotionType=" + PromotionType
				+ ", baseNonRecurringPrice=" + baseNonRecurringPrice
				+ ", baseRecurringPrice=" + baseRecurringPrice
				+ ", capabilityMap=" + capabilityMap + ", channelList="
				+ channelList + ", conditions=" + conditions
				+ ", descriptiveInfoDescription=" + descriptiveInfoDescription
				+ ", descriptiveInfoType=" + descriptiveInfoType
				+ ", descriptiveInfoValue=" + descriptiveInfoValue
				+ ", externalId=" + externalId + ", internetSpeed="
				+ internetSpeed + ", isDishProduct=" + isDishProduct
				+ ", isSellable=" + isSellable + ", itemExternalId="
				+ itemExternalId + ", longDescription=" + longDescription
				+ ", longDistance=" + longDistance
				+ ", marketingHighlightsList=" + marketingHighlightsList
				+ ", metaData=" + metaData + ", modemOptions=" + modemOptions
				+ ", name=" + name + ", noOfChannels=" + noOfChannels
				+ ", parentExternalId=" + parentExternalId + ", parentName="
				+ parentName + ", prodJson=" + prodJson + ", productCategory="
				+ productCategory + ", productType=" + productType
				+ ", promotionCode=" + promotionCode
				+ ", promotionDescription=" + promotionDescription
				+ ", promotionExternalId=" + promotionExternalId
				+ ", promotionMetaDataList=" + promotionMetaDataList
				+ ", promotionPrice=" + promotionPrice
				+ ", providerExternalId=" + providerExternalId
				+ ", providerName=" + providerName + ", qualification="
				+ qualification + ", score=" + score + ", shortDescription="
				+ shortDescription + ", source=" + source + "]";
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public Map<String, String> getCapabilityMap() {
		return capabilityMap;
	}
	public void setCapabilityMap(Map<String, String> capabilityMap) {
		this.capabilityMap = capabilityMap;
	}
	public void setProviderExternalId(String providerExternalId) {
		this.providerExternalId = providerExternalId;
	}
	public String getProviderExternalId() {
		return providerExternalId;
	}
	public boolean isDishProduct() {
		return isDishProduct;
	}
	public void setDishProduct(boolean isDishProduct) {
		this.isDishProduct = isDishProduct;
	}
	public JSONObject getProdJson() {
		return prodJson;
	}
	public void setProdJson(JSONObject prodJson) {
		this.prodJson = prodJson;
	}
	public void setDescriptiveInfoDescription(String descriptiveInfoDescription) {
		this.descriptiveInfoDescription = descriptiveInfoDescription;
	}
	public String getDescriptiveInfoDescription() {
		return descriptiveInfoDescription;
	}
	public void setDescriptiveInfoType(String descriptiveInfoType) {
		this.descriptiveInfoType = descriptiveInfoType;
	}
	public String getDescriptiveInfoType() {
		return descriptiveInfoType;
	}
	public void setPromotionPrice(Float promotionPrice) {
		this.promotionPrice = promotionPrice;
	}
	public String getPromotionType() {
		return PromotionType;
	}
	public void setPromotionType(String promotionType) {
		PromotionType = promotionType;
	}
	public String getPromotionPriceValueType() {
		return PromotionPriceValueType;
	}
	public void setPromotionPriceValueType(String promotionPriceValueType) {
		PromotionPriceValueType = promotionPriceValueType;
	}
	public Float getPromotionPrice() {
		return promotionPrice;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getParentName() {
		return parentName;
	}
	public List<String> getPromotionMetaDataList() {
		return promotionMetaDataList;
	}
	public String getLongDescription() {
		return longDescription;
	}
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public void setPromotionMetaDataList(List<String> promotionMetaDataList) {
		this.promotionMetaDataList = promotionMetaDataList;
	}
	public boolean isSellable() {
		return isSellable;
	}
	public void setSellable(boolean isSellable) {
		this.isSellable = isSellable;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
}
