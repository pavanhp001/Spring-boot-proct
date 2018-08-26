package com.A.productResults.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.A.ui.util.Utils;
import com.A.xml.pr.v4.CapabilityType;
import com.A.xml.pr.v4.EnergyPriceInfoType.Rate;
import com.A.xml.pr.v4.DescriptiveInfoType;
import com.A.xml.pr.v4.FeatureGroupType;
import com.A.xml.pr.v4.FeatureType;
import com.A.xml.pr.v4.ItemCategory;
import com.A.xml.pr.v4.ProductInfoType;
import com.A.xml.pr.v4.ProductPromotionType;
import com.A.xml.pr.v4.ProductType;

public class ProductSummaryVO {

	
	private static final Logger logger = Logger.getLogger(ProductSummaryVO.class);
	private String externalId;
	private String itemExternalId;
	private String name;
	
	private JSONObject prodJson;
	
	private String productCategory;
	private List<String> channelList;
	private String source;
	
	private String parentExternalId;
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
	private String promotionDescription = "";
	private String promotionCode;
	private String promotionExternalId;
	private List<String> promotionMetaDataList;
	private Float  promotionPrice;
	private String providerName;
	private String productType;
	private String PromotionType;
	private String PromotionPriceValueType;
	private String utilityPromotionMetaData;
	private Boolean status = false;
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
	private String parentName;
	private Double effectivePrice;
	private Boolean isPromotion = false;
	private List<ProductPromotionType> promotions = new ArrayList<ProductPromotionType>();
	private List<String> productCategories = new ArrayList<String>();
	private String priceUnitType;
	private boolean internetFeatures = false;
	private Map<String, String> metadata = new HashMap<String, String>();
	private boolean isTPVProduct = false;
	private boolean isWarmTransferProduct = false;
	private float points;
	private int revCommissions;
	private List<FeatureType> featureTypeList; 
	private List<FeatureGroupType> featureGroupTypeList;
	private List<DescriptiveInfoType> descriptiveInfoTypelist;
	private Map<String, Double> energyTierMap;
	private String energyUnitName;
	private boolean isSyntheticBundle = false;
	private ProductSummaryVO pairedProduct = null; 
	private boolean isConsumersInteractionsProduct = false;
	private Double totalPoints;
	private Double displayBasePrice;	
	private Double displayPromotionPrice;
	private String displayPricingGrid;
	private boolean isBonusPointsAdded = false;
	private double connectionSpeedVal;
	
	
	public double getConnectionSpeedVal() {
		return connectionSpeedVal;
	}

	public void setConnectionSpeedVal(double connectionSpeedVal) {
		this.connectionSpeedVal = connectionSpeedVal;
	}

	public boolean isBonusPointsAdded() {
		return isBonusPointsAdded;
	}

	public void setBonusPointsAdded(boolean isBonusPointsAdded) {
		this.isBonusPointsAdded = isBonusPointsAdded;
	}

	
	
	public String getDisplayPricingGrid() {
		return displayPricingGrid;
	}

	public void setDisplayPricingGrid(String displayPricingGrid) {
		this.displayPricingGrid = displayPricingGrid;
	}

	public Double getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Double totalPoints) {
		this.totalPoints = totalPoints;
	}

	public boolean isConsumersInteractionsProduct() {
		return isConsumersInteractionsProduct;
	}

	public void setConsumersInteractionsProduct(
			boolean isConsumersInteractionsProduct) {
		this.isConsumersInteractionsProduct = isConsumersInteractionsProduct;
	}

	/**
	 * @return the internetFeatures
	 */
	public boolean hasInternetFeatures() {
		return internetFeatures;
	}

	/**
	 * @param internetFeatures the internetFeatures to set
	 */
	public void setInternetFeatures(boolean internetFeatures) {
		this.internetFeatures = internetFeatures;
	}
	
	/**
	 * @return the isPromotion
	 */
	public Boolean getIsPromotion() {
		return isPromotion;
	}
	/**
	 * @param isPromotion the isPromotion to set
	 */
	public void setIsPromotion(Boolean isPromotion) {
		this.isPromotion = isPromotion;
	}
	/**
	 * @return the effectivePrice
	 */
	public Double getEffectivePrice() {
		return effectivePrice;
	}
	/**
	 * @param effectivePrice the effectivePrice to set
	 */
	public void setEffectivePrice(Double effectivePrice) {
		this.effectivePrice = effectivePrice;
	}
	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public List<String> getMarketingHighlightsList() {
		if(marketingHighlightsList == null){
			marketingHighlightsList = new ArrayList<String>();
		}
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

	public Double getDisplayPromotionPrice() {
		return displayPromotionPrice;
	}

	public void setDisplayPromotionPrice(Double displayPromotionPrice) {
		this.displayPromotionPrice = displayPromotionPrice;
	}

	public Double getDisplayBasePrice() {
		return displayBasePrice;
	}

	public void setDisplayBasePrice(Double displayBasePrice) {
		this.displayBasePrice = displayBasePrice;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductSummaryVO [PromotionPriceValueType="
				+ PromotionPriceValueType + ", PromotionType=" + PromotionType
				+ ", baseNonRecurringPrice=" + baseNonRecurringPrice
				+ ", baseRecurringPrice=" + baseRecurringPrice
				+ ", capabilityMap=" + capabilityMap + ", channelList="
				+ channelList + ", conditions=" + conditions
				+ ", descriptiveInfoDescription=" + descriptiveInfoDescription
				+ ", descriptiveInfoType=" + descriptiveInfoType
				+ ", descriptiveInfoValue=" + descriptiveInfoValue
				+ ", effectivePrice=" + effectivePrice + ", externalId="
				+ externalId + ", internetFeatures=" + internetFeatures
				+ ", internetSpeed=" + internetSpeed + ", isDishProduct="
				+ isDishProduct + ", isPromotion=" + isPromotion
				+ ", isSellable=" + isSellable + ", isTPVProduct="
				+ isTPVProduct + ", isWarmTransferProduct="
				+ isWarmTransferProduct + ", itemExternalId=" + itemExternalId
				+ ", longDescription=" + longDescription + ", longDistance="
				+ longDistance + ", marketingHighlightsList="
				+ marketingHighlightsList + ", metaData=" + metaData
				+ ", metadata=" + metadata + ", modemOptions=" + modemOptions
				+ ", name=" + name + ", noOfChannels=" + noOfChannels
				+ ", parentExternalId=" + parentExternalId + ", parentName="
				+ parentName + ", priceUnitType=" + priceUnitType
				+ ", prodJson=" + prodJson + ", productCategories="
				+ productCategories + ", productCategory=" + productCategory
				+ ", productType=" + productType + ", promotionCode="
				+ promotionCode + ", promotionDescription="
				+ promotionDescription + ", promotionExternalId="
				+ promotionExternalId + ", promotionMetaDataList="
				+ connectionSpeedVal + ", connectionSpeedVal="
				+ promotionMetaDataList + ", promotionPrice=" + promotionPrice
				+ ", promotions=" + promotions + ", providerExternalId="
				+ providerExternalId + ", providerName=" + providerName
				+ ", qualification=" + qualification + ", score=" + score
				+ ", displayBasePrice=" + displayBasePrice + ", displayPromotionPrice=" + displayPromotionPrice
				+ ", displayPricingGrid=" + displayPricingGrid + ", shortDescription=" + shortDescription + ", source="
				+ source + ", status=" + status + ", utilityPromotionMetaData="
				+ utilityPromotionMetaData + ", totalPoints=" + totalPoints + "]";
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
	public void setUtilityPromotionMetaData(String utilityPromotionMetaData) {
		this.utilityPromotionMetaData = utilityPromotionMetaData;
	}
	public String getUtilityPromotionMetaData() {
		return utilityPromotionMetaData;
	}
	public List<String> getPromotionMetaDataList() {
		return promotionMetaDataList;
	}
	public void setPromotionMetaDataList(List<String> promotionMetaDataList) {
		this.promotionMetaDataList = promotionMetaDataList;
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
	public String getNoOfChannels() {
		return noOfChannels;
	}
	public void setNoOfChannels(String noOfChannels) {
		this.noOfChannels = noOfChannels;
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
	public boolean isSyntheticBundle() {
		return isSyntheticBundle;
	}

	public void setSyntheticBundle(boolean isSyntheticBundle) {
		this.isSyntheticBundle = isSyntheticBundle;
	}

	public ProductSummaryVO getPairedProduct() {
		return pairedProduct;
	}

	public void setPairedProduct(ProductSummaryVO pairedProduct) {
		this.pairedProduct = pairedProduct;
	}
	public  void populatePromotions(ProductInfoType product) {
		if (product.getProductDetails().getMetaData() != null 
				&& product.getProductDetails().getMetaData().getMetaData() != null 
					&& product.getProductDetails().getMetaData().getMetaData().size() > 0){
			for (int i=0;i<product.getProductDetails().getMetaData().getMetaData().size(); i ++){
				if (getPromotionMetaDataList() != null && getPromotionMetaDataList().size() > 0){
					getPromotionMetaDataList().add(product.getProductDetails().getMetaData().getMetaData().get(i));
				}else{
					List<String> metaList = new ArrayList<String>();
					metaList.add(product.getProductDetails().getMetaData().getMetaData().get(i));
					setPromotionMetaDataList(metaList);
				}
			}
			List<String> mDataList = product.getProductDetails().getMetaData().getMetaData();
			for (String mdata : mDataList) {
				if(mdata.indexOf('=') > -1){
					String[] nameValuePair = mdata.split("=");
					if (nameValuePair.length == 2){
						metadata.put(nameValuePair[0], nameValuePair[1]);
						try{
							if (nameValuePair[0].equalsIgnoreCase("DISPLAY_BASE_PRICE")){
								setDisplayBasePrice(Double.valueOf(nameValuePair[1]));
							}
							else if (nameValuePair[0].equalsIgnoreCase("DISPLAY_PROMO_PRICE")){
								setDisplayPromotionPrice(Double.valueOf(nameValuePair[1]));
							}
							else if (nameValuePair[0].equalsIgnoreCase("DISPLAY_PRICING_GRID")){
								setDisplayPricingGrid(nameValuePair[1]);
							}
						}
						catch(Exception e){
                            logger.warn("Invalid display price value"+e.getMessage());
						}
					}
				}else if(!Utils.isBlank(mdata)){
					metadata.put(mdata, "");		
				}
			}
		}
		
		if (product.getProductDetails().getPromotion() != null && product.getProductDetails().getPromotion().size() > 0){
			int i=0;
			for ( ProductPromotionType promotion: product.getProductDetails().getPromotion()){
				if (promotion != null){
					if(i == 0) {
						setPromotionCode(promotion.getPromoCode());
						setPromotionDescription(promotion.getDescription());
						setPromotionExternalId(promotion.getExternalId());
						setPromotionType(promotion.getType());
						setPromotionPrice(promotion.getPriceValue());
						setPromotionPriceValueType(promotion.getPriceValueType());
						setQualification(promotion.getQualification());
						setConditions(promotion.getConditions());
						setShortDescription(promotion.getShortDescription());
					}
					promotions.add(promotion);
				}
				if (promotion.getMetaData() != null){
					if (promotion.getMetaData().getMetaData() != null &&
							promotion.getMetaData().getMetaData().size() > 0) {
						String strMetaData = promotion.getMetaData().getMetaData().get(0);
						if(strMetaData != null && strMetaData.equals("ADVERTISE")) {
							setPromotionCode(promotion.getPromoCode());
							setPromotionDescription(promotion.getDescription());
							setPromotionExternalId(promotion.getExternalId());
							setPromotionType(promotion.getType());
							setPromotionPrice(promotion.getPriceValue());
							setPromotionPriceValueType(promotion.getPriceValueType());
							setQualification(promotion.getQualification());
							setConditions(promotion.getConditions());
							setShortDescription(promotion.getShortDescription());
						}
					}
				}
				i++;
			}
		}
	}
	
	public void populateProductSummary(ProductType product) {
		if(product.getProductCategoryList().getProductCategory()!=null && !product.getProductCategoryList().getProductCategory().isEmpty()){
			setProductCategory(product.getProductCategoryList().getProductCategory().get(0).getDisplayName());
			prepareProductCategories(product.getProductCategoryList().getProductCategory());
		}else{
			logger.info("Product category not found for :: "+product.getExternalId());
		}
		setName(product.getName());
		setChannelList(product.getChannels().getChannel());
		setProviderName(product.getProvider().getName());
		setProviderExternalId(product.getProvider().getExternalId());
		if (product.getProvider().getSource() != null){
			setSource(product.getProvider().getSource().getValue().toString());	
		}
		if (product.getProvider().getParent() != null){
			setParentExternalId(product.getProvider().getParent().getExternalId());	
			setParentName(product.getProvider().getParent().getName());
		}
		setBaseNonRecurringPrice(product.getPriceInfo().getBaseNonRecurringPrice());
		setBaseRecurringPrice(product.getPriceInfo().getBaseRecurringPrice());
		if(product.getPriceInfo().getEnergyPriceInfo() != null 
				&& product.getPriceInfo().getEnergyPriceInfo().getRate() != null
				&& !product.getPriceInfo().getEnergyPriceInfo().getRate().isEmpty()) {
			Map<String, Double> energyTier = new HashMap<String, Double>();
			List<Rate> rateList =  product.getPriceInfo().getEnergyPriceInfo().getRate();
			for (Rate rate : rateList) {
				energyTier.put(rate.getTier(), rate.getValue());
			}
			setEnergyTierMap(energyTier);
		}
		if(product.getPriceInfo().getEnergyPriceInfo() != null 
				&& product.getPriceInfo().getEnergyPriceInfo().getEnergyUnit() != null){
			setEnergyUnitName(product.getPriceInfo().getEnergyPriceInfo().getEnergyUnit().value());
		}
	}
	private void prepareProductCategories(List<ItemCategory> list) {
		for (ItemCategory category : list) {
			productCategories.add(category.getDisplayName());
		
		}		
	}
	public void populateCapabilities(ProductInfoType product) {
		Map<String,String> capabilityMap = new HashMap<String, String>();
		for (CapabilityType capability : product.getProduct().getCapabilityList().getCapability()){
			capabilityMap.put(capability.getName(), capability.getName());
		}
		setCapabilityMap(capabilityMap);
	}
	public void populateMarketingHighlights(ProductInfoType prodInfo) {
		if (prodInfo.getProductDetails().getMarketingHighlights() != null){
			setMarketingHighlightsList(prodInfo.getProductDetails().getMarketingHighlights().getMarketingHighlight());
		}
	}
	public void populateDescriptiveInfo(ProductInfoType prodInfo) {
		if (prodInfo.getProductDetails().getDescriptiveInfo() != null && prodInfo.getProductDetails().getDescriptiveInfo().size() > 0){
			if (prodInfo.getProductDetails().getDescriptiveInfo().get(0) != null){
				setDescriptiveInfoValue(prodInfo.getProductDetails().getDescriptiveInfo().get(0).getValue());	
			}
		}
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	/**
	 * @return the promotions
	 */
	public List<ProductPromotionType> getPromotions() {
		return promotions;
	}
	/**
	 * @param promotions the promotions to set
	 */
	public void setPromotions(List<ProductPromotionType> promotions) {
		this.promotions = promotions;
	}
	/**
	 * @return the productCategories
	 */
	public List<String> getProductCategories() {
		return productCategories;
	}
	/**
	 * @param productCategories the productCategories to set
	 */
	public void setProductCategories(List<String> productCategories) {
		this.productCategories = productCategories;
	}
	/**
	 * @return the priceUnitType
	 */
	public String getPriceUnitType() {
		return priceUnitType;
	}
	/**
	 * @param priceUnitType the priceUnitType to set
	 */
	public void setPriceUnitType(String priceUnitType) {
		this.priceUnitType = priceUnitType;
	}

	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	public boolean isTPVProduct() {
		return isTPVProduct;
	}

	public void setTPVProduct(boolean isTPVProduct) {
		this.isTPVProduct = isTPVProduct;
	}

	public boolean isWarmTransferProduct() {
		return isWarmTransferProduct;
	}

	public void setWarmTransferProduct(boolean isWarmTransferProduct) {
		this.isWarmTransferProduct = isWarmTransferProduct;
	}

	public float getPoints() {
		return points;
	}

	public void setPoints(float points) {
		this.points = points;
	}

	public int getRevCommissions() {
		return revCommissions;
	}

	public void setRevCommissions(int revCommissions) {
		this.revCommissions = revCommissions;
	}

	public List<FeatureType> getFeatureTypeList() {
		return featureTypeList;
	}

	public void setFeatureTypeList(List<FeatureType> featureTypeList) {
		this.featureTypeList = featureTypeList;
	}

	public List<FeatureGroupType> getFeatureGroupTypeList() {
		return featureGroupTypeList;
	}

	public void setFeatureGroupTypeList(List<FeatureGroupType> featureGroupTypeList) {
		this.featureGroupTypeList = featureGroupTypeList;
	}

	public List<DescriptiveInfoType> getDescriptiveInfoTypelist() {
		return descriptiveInfoTypelist;
	}

	public void setDescriptiveInfoTypelist(
			List<DescriptiveInfoType> descriptiveInfoTypelist) {
		this.descriptiveInfoTypelist = descriptiveInfoTypelist;
	}

	public void setEnergyUnitName(String energyUnitName) {
		this.energyUnitName = energyUnitName;
	}

	public String getEnergyUnitName() {
		return energyUnitName;
	}

	public Map<String, Double> getEnergyTierMap() {
		return energyTierMap;
	}

	public void setEnergyTierMap(Map<String, Double> energyTierMap) {
		this.energyTierMap = energyTierMap;
	}

	public ProductSummaryVO(ProductSummaryVO ps, ProductSummaryVO pairedProduct) {
		this.externalId = ps.externalId;
		this.itemExternalId = ps.itemExternalId;
		this.name = ps.name;
		this.prodJson = ps.prodJson;
		this.productCategory = ps.productCategory;
		this.channelList = ps.channelList;
		this.source = ps.source;
		this.parentExternalId = ps.parentExternalId;
		this.baseNonRecurringPrice = ps.baseNonRecurringPrice;
		this.baseRecurringPrice = ps.baseRecurringPrice;
		this.score = ps.score;
		this.capabilityMap = ps.capabilityMap;
		this.isDishProduct = ps.isDishProduct;
		this.marketingHighlightsList = ps.marketingHighlightsList;
		this.descriptiveInfoDescription = ps.descriptiveInfoDescription;
		this.descriptiveInfoType = ps.descriptiveInfoType;
		this.descriptiveInfoValue = ps.descriptiveInfoValue;
		this.metaData = ps.metaData;
		this.promotionDescription = ps.promotionDescription;
		this.promotionCode = ps.promotionCode;
		this.promotionExternalId = ps.promotionExternalId;
		this.promotionMetaDataList = ps.promotionMetaDataList;
		this.promotionPrice = ps.promotionPrice;
		this.providerName = ps.providerName;
		this.productType = ps.productType;
		this.PromotionType = ps.PromotionType;
		this.PromotionPriceValueType = ps.PromotionPriceValueType;
		this.utilityPromotionMetaData = ps.utilityPromotionMetaData;
		this.status = ps.status;
		this.longDescription = ps.longDescription;
		this.conditions = ps.conditions;
		this.qualification = ps.qualification;
		this.noOfChannels = ps.noOfChannels;
		this.isSellable = ps.isSellable;
		this.shortDescription = ps.shortDescription;
		this.internetSpeed = ps.internetSpeed;
		this.modemOptions = ps.modemOptions;
		this.longDistance = ps.longDistance;
		this.parentName = ps.parentName;
		this.effectivePrice = ps.effectivePrice;
		this.isPromotion = ps.isPromotion;
		this.promotions = ps.promotions;
		this.productCategories = ps.productCategories;
		this.priceUnitType = ps.priceUnitType;
		this.internetFeatures = ps.internetFeatures;
		this.metadata = ps.metadata;
		this.isTPVProduct = ps.isTPVProduct;
		this.isWarmTransferProduct = ps.isWarmTransferProduct;
		this.points = ps.points;
		this.revCommissions = ps.revCommissions;
		this.featureTypeList = ps.featureTypeList;
		this.featureGroupTypeList = ps.featureGroupTypeList;
		this.descriptiveInfoTypelist = ps.descriptiveInfoTypelist;
		this.energyTierMap = ps.energyTierMap;
		this.energyUnitName = ps.energyUnitName;
		this.isSyntheticBundle = ps.isSyntheticBundle;
		this.pairedProduct = ps.pairedProduct;
		this.providerExternalId = ps.providerExternalId;
		this.totalPoints = ps.totalPoints;
		this.displayBasePrice = ps.displayBasePrice;
		this.displayPromotionPrice = ps.displayPromotionPrice;
		this.displayPricingGrid = ps.displayPricingGrid;
		this.pairedProduct = pairedProduct;
	}

	public ProductSummaryVO() {
	}
}
