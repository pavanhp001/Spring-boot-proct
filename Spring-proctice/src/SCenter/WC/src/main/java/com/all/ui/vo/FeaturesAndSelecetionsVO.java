package com.A.ui.vo;

import java.io.Serializable;

public class FeaturesAndSelecetionsVO implements Serializable,Comparable<FeaturesAndSelecetionsVO> {
	
	private String featureExtrnalId;
	private String featureValue;
	private Double baseRecurringPrice;
	private Double baseNonRecurringPrice;
	
	/**
	 * @return the featureExtrnalId
	 */
	public String getFeatureExtrnalId() {
		return featureExtrnalId;
	}

	/**
	 * @param featureExtrnalId the featureExtrnalId to set
	 */
	public void setFeatureExtrnalId(String featureExtrnalId) {
		this.featureExtrnalId = featureExtrnalId;
	}

	/**
	 * @return the featureValue
	 */
	public String getFeatureValue() {
		return featureValue;
	}

	/**
	 * @param featureValue the featureValue to set
	 */
	public void setFeatureValue(String featureValue) {
		this.featureValue = featureValue;
	}

	/**
	 * @return the baseRecurringPrice
	 */
	public Double getBaseRecurringPrice() {
		return baseRecurringPrice;
	}

	/**
	 * @param baseRecurringPrice the baseRecurringPrice to set
	 */
	public void setBaseRecurringPrice(Double baseRecurringPrice) {
		this.baseRecurringPrice = baseRecurringPrice;
	}

	/**
	 * @return the baseNonRecurringPrice
	 */
	public Double getBaseNonRecurringPrice() {
		return baseNonRecurringPrice;
	}

	/**
	 * @param baseNonRecurringPrice the baseNonRecurringPrice to set
	 */
	public void setBaseNonRecurringPrice(Double baseNonRecurringPrice) {
		this.baseNonRecurringPrice = baseNonRecurringPrice;
	}

	public int compareTo(FeaturesAndSelecetionsVO featuresAndSelections) {
		
		if(featuresAndSelections.getFeatureExtrnalId().equalsIgnoreCase("Taxes")) {
			 return -1;
		 } else if (this.getFeatureExtrnalId().equalsIgnoreCase("Taxes")){
			 return 1;
		 }
		 else if(this.baseRecurringPrice > featuresAndSelections.getBaseRecurringPrice())
		 {
			 return -1;
		 }
		 else if(this.baseRecurringPrice < featuresAndSelections.getBaseRecurringPrice())
		 {
			 return 1;
		 }
		return 0;
	}	

}
