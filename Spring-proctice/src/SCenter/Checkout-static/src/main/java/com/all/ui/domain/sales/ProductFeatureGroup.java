package com.AL.ui.domain.sales;

import java.util.ArrayList;
import java.util.List;

public class ProductFeatureGroup {
	private String externalId;
	private String description;
	private PriceInformation priceInformation;
	private Boolean required;
	private Boolean available;
    private BooleanConstraint booleanConstraint;
    private IntegerConstraint integerConstraint;
    private StringConstraint stringConstraint;
    private List<ProductFeature> productFeatures;
    private PickListMode pickListMode;

	public BooleanConstraint getBooleanConstraint() {
		return booleanConstraint;
	}

	public void setBooleanConstraint(BooleanConstraint booleanConstraint) {
		this.booleanConstraint = booleanConstraint;
	}

	public IntegerConstraint getIntegerConstraint() {
		return integerConstraint;
	}

	public void setIntegerConstraint(IntegerConstraint integerConstraint) {
		this.integerConstraint = integerConstraint;
	}

	public StringConstraint getStringConstraint() {
		return stringConstraint;
	}

	public void setStringConstraint(StringConstraint stringConstraint) {
		this.stringConstraint = stringConstraint;
	}
   
	public List<ProductFeature> getProductFeatures() {
		if (productFeatures == null) {
			productFeatures = new ArrayList<ProductFeature>();
		}
		return productFeatures;
	}

	public void setProductFeatures(List<ProductFeature> arg) {
		productFeatures = arg;
	}
 
	public void addProductFeature(ProductFeature arg) {
		getProductFeatures().add(arg);
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PriceInformation getPriceInformation() {
		return priceInformation;
	}

	public void setPriceInformation(PriceInformation priceInformation) {
		this.priceInformation = priceInformation;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public PickListMode getPickListMode() {
		return pickListMode;
	}

	public void setPickListMode(PickListMode pickListMode) {
		this.pickListMode = pickListMode;
	}
}
