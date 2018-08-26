package com.A.ui.vo;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.A.xml.pr.v4.ProductPromotionType;

public class ProductVOJSON {
	private String providerName;
	private String productName;
	private String productType;
	private String providerExtId;
	private String parentProviderExtId;
	private String productExID;
	private Double baseRecurringPrice;
	private Double baseNonRecurringPrice;
	private Double promoPrice;
	private boolean isValidIconsForProducts;
	private boolean isChannelLineupProvider;
	private boolean isVideoCapable;
	private boolean isExistingCustomer;
	private String hiddenProductJSON;
	private List<String> productIconList;
	private String productDescription;
	private String productPointDisplay;
	private Float productPoints;
	private Map<String,String> filterMetaDatMap;
	private Map<String,String> metaDataMap;
	private Double sortPrice;
	private Double productScore;
	private String imageID;
	private String unitName;
	private Double usageRate;
	private Map<String,Double> energyTierMap;
	private String baseRecNA;
	private Map<String,String> capabilitMap;
	private String condition;
	private List<ProductPromotionType> promotions ;//= product.getProductDetails().getPromotion();
	private String promotionDescription;
	private String pairedProduct;
	private String salesTipexternalId;
	private Float totalPoints;
	private Double totalPrice;
	private Double totalScore;
	private Double totalPromotionPrice;
	private Double  displayPromotionPrice;
	private Double totalDisplayBasePrice;
	private Double  totalDisplayPromotionPrice;
	private Double displayBasePrice;
	private Float channels;
	private Float connSpeed;
	private String displayPricingGrid;
	private String bundlePriceJson;
	private String pricingGridJson;
	private Float contractTerm;
	private String connectionSpeedCount;
	private Float channelsCount;
	private String latinoProduct;
	private String pricingGridLink;
	private String verizonPricingGridContent;
	private String verizonBasePriceContent;
	private ProductVOJSON pairedProductVOJSON = null;
	
	public String getPricingGridLink() {
		return pricingGridLink;
	}
	
	public void setPricingGridLink(String pricingGridLink) {
		this.pricingGridLink = pricingGridLink;
	}
	
	public ProductVOJSON getPairedProductVOJSON() {
		return pairedProductVOJSON;
	}
	public void setPairedProductVOJSON(ProductVOJSON pairedProductVOJSON) {
		this.pairedProductVOJSON = pairedProductVOJSON;
	}
	public String getPricingGridJson() {
		return pricingGridJson;
	}
	public void setPricingGridJson(String pricingGridJson) {
		this.pricingGridJson = pricingGridJson;
	}
	public String getDisplayPricingGrid() {
		return displayPricingGrid;
	}
	public void setDisplayPricingGrid(String displayPricingGrid) {
		this.displayPricingGrid = displayPricingGrid;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getProviderExtId() {
		return providerExtId;
	}
	public void setProviderExtId(String providerExtId) {
		this.providerExtId = providerExtId;
	}
	public String getProductExID() {
		return productExID;
	}
	public void setProductExID(String productExID) {
		this.productExID = productExID;
	}
	public Double getBaseRecurringPrice() {
		return baseRecurringPrice;
	}
	public void setBaseRecurringPrice(Double baseRecurringPrice) {
		this.baseRecurringPrice = baseRecurringPrice;
	}
	public Double getPromoPrice() {
		return promoPrice;
	}
	public void setPromoPrice(Double promoPrice) {
		this.promoPrice = promoPrice;
	}
	public boolean isValidIconsForProducts() {
		return isValidIconsForProducts;
	}
	public void setValidIconsForProducts(boolean isValidIconsForProducts) {
		this.isValidIconsForProducts = isValidIconsForProducts;
	}
	public boolean isChannelLineupProvider() {
		return isChannelLineupProvider;
	}
	public void setChannelLineupProvider(boolean isChannelLineupProvider) {
		this.isChannelLineupProvider = isChannelLineupProvider;
	}
	public boolean isVideoCapable() {
		return isVideoCapable;
	}
	public void setVideoCapable(boolean isVideoCapable) {
		this.isVideoCapable = isVideoCapable;
	}
	public boolean isExistingCustomer() {
		return isExistingCustomer;
	}
	public void setExistingCustomer(boolean isExistingCustomer) {
		this.isExistingCustomer = isExistingCustomer;
	}
	public List<String> getProductIconList() {
		return productIconList;
	}
	public void setProductIconList(List<String> productIconList) {
		this.productIconList = productIconList;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public Float getProductPoints() {
		return productPoints;
	}
	public void setProductPoints(Float productPoints) {
		this.productPoints = productPoints;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getBaseNonRecurringPrice() {
		return baseNonRecurringPrice;
	}
	public void setBaseNonRecurringPrice(Double baseNonRecurringPrice) {
		this.baseNonRecurringPrice = baseNonRecurringPrice;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getPromotionDescription() {
		return promotionDescription;
	}
	public void setPromotionDescription(String promotionDescription) {
		this.promotionDescription = promotionDescription;
	}
	public Map<String, String> getFilterMetaDatMap() {
		return filterMetaDatMap;
	}
	public void setFilterMetaDatMap(Map<String, String> filterMetaDatMap) {
		this.filterMetaDatMap = filterMetaDatMap;
	}
	public Map<String, String> getMetaDataMap() {
		return metaDataMap;
	}
	public void setMetaDataMap(Map<String, String> metaDataMap) {
		this.metaDataMap = metaDataMap;
	}
	public Double getSortPrice() {
		return sortPrice;
	}
	public void setSortPrice(Double sortPrice) {
		this.sortPrice = sortPrice;
	}
	public Double getProductScore() {
		return productScore;
	}
	public void setProductScore(Double productScore) {
		this.productScore = productScore;
	}
	public String getProductPointDisplay() {
		return productPointDisplay;
	}
	public void setProductPointDisplay(String productPointDisplay) {
		this.productPointDisplay = productPointDisplay;
	}
	public String getHiddenProductJSON() {
		return hiddenProductJSON;
	}
	public void setHiddenProductJSON(String hiddenProductJSON) {
		this.hiddenProductJSON = hiddenProductJSON;
	}
	public String getParentProviderExtId() {
		return parentProviderExtId;
	}
	public void setParentProviderExtId(String parentProviderExtId) {
		this.parentProviderExtId = parentProviderExtId;
	}
	public String getImageID() {
		return imageID;
	}
	public void setImageID(String imageID) {
		this.imageID = imageID;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUnitName() {
		return unitName;
	}
	public Double getUsageRate() {
		return usageRate;
	}
	public void setUsageRate(Double usageRate) {
		this.usageRate = usageRate;
	}
	public Map<String, Double> getEnergyTierMap() {
		return energyTierMap;
	}
	public void setEnergyTierMap(Map<String, Double> energyTierMap) {
		this.energyTierMap = energyTierMap;
	}

public String getBaseRecNA() {
	return baseRecNA;
}
public void setBaseRecNA(String baseRecNA) {
	this.baseRecNA = baseRecNA;
}
public Map<String, String> getCapabilitMap() {
	return capabilitMap;
}
public void setCapabilitMap(Map<String, String> capabilitMap) {
	this.capabilitMap = capabilitMap;
}
public List<ProductPromotionType> getPromotions() {
	return promotions;
}
public void setPromotions(List<ProductPromotionType> promotions) {
	this.promotions = promotions;
}
public String getCondition() {
	return condition;
}
public void setCondition(String condition) {
	this.condition = condition;
}
public String getPairedProduct() {
	return pairedProduct;
}
public void setPairedProduct(String pairedProduct) {
	this.pairedProduct = pairedProduct;
}
public String getSalesTipexternalId() {
	return salesTipexternalId;
}
public void setSalesTipexternalId(String salesTipexternalId) {
	this.salesTipexternalId = salesTipexternalId;
}
public Float getTotalPoints() {
	return totalPoints;
}
public void setTotalPoints(Float totalPoints) {
	this.totalPoints = totalPoints;
}
public Double getTotalPrice() {
	return totalPrice;
}
public void setTotalPrice(Double totalPrice) {
	this.totalPrice = totalPrice;
}
public Double getTotalPromotionPrice() {
	return totalPromotionPrice;
}
public void setTotalPromotionPrice(Double totalPromotionPrice) {
	this.totalPromotionPrice = totalPromotionPrice;
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
public Double getTotalScore() {
	return totalScore;
}
public void setTotalScore(Double totalScore) {
	this.totalScore = totalScore;
}
public Float getChannels() {
	return channels;
}
public void setChannels(Float channels) {
	this.channels = channels;
}
public Float getConnSpeed() {
	return connSpeed;
}
public void setConnSpeed(Float connSpeed) {
	this.connSpeed = connSpeed;
}
public Double getTotalDisplayBasePrice() {
	return totalDisplayBasePrice;
}
public void setTotalDisplayBasePrice(Double totalDisplayBasePrice) {
	this.totalDisplayBasePrice = totalDisplayBasePrice;
}
public Double getTotalDisplayPromotionPrice() {
	return totalDisplayPromotionPrice;
}
public void setTotalDisplayPromotionPrice(Double totalDisplayPromotionPrice) {
	this.totalDisplayPromotionPrice = totalDisplayPromotionPrice;
}
public void setBundlePriceJson(String string) {
	this.bundlePriceJson = string;
}
public String getBundlePriceJson() {
	return bundlePriceJson;
}
public Float getContractTerm() {
	return contractTerm;
}
public void setContractTerm(Float contractTerm) {
	this.contractTerm = contractTerm;
}
public String getConnectionSpeedCount() {
	return connectionSpeedCount;
}
public void setConnectionSpeedCount(String connectionSpeedCount) {
	this.connectionSpeedCount = connectionSpeedCount;
}
public Float getChannelsCount() {
	return channelsCount;
}
public void setChannelsCount(Float channelsCount) {
	this.channelsCount = channelsCount;
}
public String getLatinoProduct() {
	return latinoProduct;
}
public void setLatinoProduct(String latinoProduct) {
	this.latinoProduct = latinoProduct;
}

public String getVerizonPricingGridContent() {
	return verizonPricingGridContent;
}

public void setVerizonPricingGridContent(String verizonPricingGridContent) {
	this.verizonPricingGridContent = verizonPricingGridContent;
}

public String getVerizonBasePriceContent() {
	return verizonBasePriceContent;
}

public void setVerizonBasePriceContent(String verizonBasePriceContent) {
	this.verizonBasePriceContent = verizonBasePriceContent;
}

}
