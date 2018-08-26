package com.AL.ui.domain.sales;

import java.util.ArrayList;
import java.util.List;

public class ProductFeature {

    private Boolean available;
    private BooleanConstraint booleanConstraint;
    private List<FeatureCapability> capabilities;
    private List<FeatureDependency> dependencies;
    private String description;
    private Object doNotDisplay;
    private String externalId;
    private Object included;
    private IntegerConstraint integerConstraint;
    private PriceInformation priceInformation;
    private List<PriceTier> priceTierList;
    private boolean required;
    private StringConstraint stringConstraint;
    private String tags;
    private String type;
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	public BooleanConstraint getBooleanConstraint() {
		return booleanConstraint;
	}
	public void setBooleanConstraint(BooleanConstraint booleanConstraint) {
		this.booleanConstraint = booleanConstraint;
	}
	public List<FeatureCapability> getCapabilities() {
		if (capabilities == null) {
			capabilities = new ArrayList<FeatureCapability>();
		}
		return capabilities;
	}
	public void setCapabilities(List<FeatureCapability> capabilities) {
		this.capabilities = capabilities;
	}
	public void addCapability(FeatureCapability capability) {
		getCapabilities().add(capability);
	}

	public List<FeatureDependency> getDependencies() {
		if (dependencies == null) {
			dependencies = new ArrayList<FeatureDependency>();
		}
		return dependencies;
	}
	public void setDependencies(List<FeatureDependency> dependencies) {
		this.dependencies = dependencies;
	}
	public void addDependency(FeatureDependency arg) {
		getDependencies().add(arg);
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Object getDoNotDisplay() {
		return doNotDisplay;
	}
	public void setDoNotDisplay(Object doNotDisplay) {
		this.doNotDisplay = doNotDisplay;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public Object getIncluded() {
		return included;
	}
	public void setIncluded(Object included) {
		this.included = included;
	}
	public IntegerConstraint getIntegerConstraint() {
		return integerConstraint;
	}
	public void setIntegerConstraint(IntegerConstraint integerConstraint) {
		this.integerConstraint = integerConstraint;
	}
	public PriceInformation getPriceInformation() {
		return priceInformation;
	}
	public void setPriceInformation(PriceInformation priceInformation) {
		this.priceInformation = priceInformation;
	}
	public List<PriceTier> getPriceTierList() {
		if (priceTierList == null) {
			priceTierList = new ArrayList<PriceTier>();
		}
		return priceTierList;
	}
	public void setPriceTierList(List<PriceTier> priceTierList) {
		this.priceTierList = priceTierList;
	}
	public void addPriceTier(PriceTier arg) {
		getPriceTierList().add(arg);
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public StringConstraint getStringConstraint() {
		return stringConstraint;
	}
	public void setStringConstraint(StringConstraint stringConstraint) {
		this.stringConstraint = stringConstraint;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
