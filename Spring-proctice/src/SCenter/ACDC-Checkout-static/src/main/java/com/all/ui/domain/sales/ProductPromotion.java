package com.AL.ui.domain.sales;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.Duration;

public class ProductPromotion {
    private String externalId;
    private String promoCode;
    private Date inEffect;
    private Date expiration;
    private String shortDescription;
    private String description;
    private String qualification;
    private String conditions;
    private List<String> externalFeatureIds;  // list of preconditioned features
    private List<String> promotionConflictExternalIds;
    private String type;
    private String priceValueType;
    private Float priceValue;
    private String promotionDuration;
    private List<String> metaData;
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	public Date getInEffect() {
		return inEffect;
	}
	public void setInEffect(Date inEffect) {
		this.inEffect = inEffect;
	}
	public Date getExpiration() {
		return expiration;
	}
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public List<String> getExternalFeatureIds() {
		if (externalFeatureIds == null) {
			externalFeatureIds = new ArrayList<String>();
		}
		return externalFeatureIds;
	}
	public void setExternalFeatureIds(List<String> externalFeatureIds) {
		this.externalFeatureIds = externalFeatureIds;
	}
	public void addPrerequisite(String featureId) {
		getExternalFeatureIds().add(featureId);
	}

	public List<String> getPromotionConflictExternalIds() {
		if (promotionConflictExternalIds == null) {
			promotionConflictExternalIds = new ArrayList<String>();
		}
		return promotionConflictExternalIds;
	}
	public void setPromotionConflictExternalIds(
			List<String> promotionConflictExternalIds) {
		this.promotionConflictExternalIds = promotionConflictExternalIds;
	}
	
	public void addPromotionConflict(String arg) {
		getPromotionConflictExternalIds().add(arg);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPriceValueType() {
		return priceValueType;
	}
	public void setPriceValueType(String priceValueType) {
		this.priceValueType = priceValueType;
	}
	public Float getPriceValue() {
		return priceValue;
	}
	public void setPriceValue(Float priceValue) {
		this.priceValue = priceValue;
	}
	public String getPromotionDuration() {
		return promotionDuration;
	}
	public void setPromotionDuration(String promotionDuration) {
		this.promotionDuration = promotionDuration;
	}
	public List<String> getMetaData() {
		if (metaData == null) {
			metaData = new ArrayList<String>();
		}
		return metaData;
	}
	public void setMetaData(List<String> metaData) {
		this.metaData = metaData;
	}
	
	public void addMetaData(String arg) {
		getMetaData().add(arg);
	}
}
