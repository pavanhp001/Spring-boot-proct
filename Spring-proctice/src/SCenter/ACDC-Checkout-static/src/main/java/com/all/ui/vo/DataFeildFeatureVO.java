package com.AL.ui.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class DataFeildFeatureVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String featureExID;
	private boolean isIncluded = false;
	private String baseNonRecurringPrice;
	private String baseRecurringPrice;
	private String comparableValue;
	private String dataConstraint;
	private List<String> dataConstraintValueList;
	private String type ;
	private String description ;
	private Map<String,String> dispalyInputValue;
	private String dispalyInput;
	private String featureDescription;
	private String dataFieldName;
	private String dataFieldText;
	private boolean isEnabled;
	private String dataFieldExID;
	private String validation;
	private String valueTarget;
	private String enteredValue;
	private List<DataFeildFeatureVO> featureVOList;
	private Map<Integer,PriceTierVO> priceTierMap;
	private String tagSectionName;
	public String getFeatureExID() {
		return featureExID;
	}
	public void setFeatureExID(String featureExID) {
		this.featureExID = featureExID;
	}
	public boolean isIncluded() {
		return isIncluded;
	}
	public void setIncluded(boolean isIncluded) {
		this.isIncluded = isIncluded;
	}
	public String getBaseNonRecurringPrice() {
		return baseNonRecurringPrice;
	}
	public void setBaseNonRecurringPrice(String baseNonRecurringPrice) {
		this.baseNonRecurringPrice = baseNonRecurringPrice;
	}
	public String getBaseRecurringPrice() {
		return baseRecurringPrice;
	}
	public void setBaseRecurringPrice(String baseRecurringPrice) {
		this.baseRecurringPrice = baseRecurringPrice;
	}
	public String getComparableValue() {
		return comparableValue;
	}
	public void setComparableValue(String comparableValue) {
		this.comparableValue = comparableValue;
	}
	public String getDataConstraint() {
		return dataConstraint;
	}
	public void setDataConstraint(String dataConstraint) {
		this.dataConstraint = dataConstraint;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDataFieldName() {
		return dataFieldName;
	}
	public void setDataFieldName(String dataFieldName) {
		this.dataFieldName = dataFieldName;
	}
	public String getDataFieldText() {
		return dataFieldText;
	}
	public void setDataFieldText(String dataFieldText) {
		this.dataFieldText = dataFieldText;
	}
	public String getDispalyInput() {
		return dispalyInput;
	}
	public void setDispalyInput(String dispalyInput) {
		this.dispalyInput = dispalyInput;
	}
	public String getFeatureDescription() {
		return featureDescription;
	}
	public void setFeatureDescription(String featureDescription) {
		this.featureDescription = featureDescription;
	}
	public List<DataFeildFeatureVO> getFeatureVOList() {
		return featureVOList;
	}
	public void setFeatureVOList(List<DataFeildFeatureVO> featureVOList) {
		this.featureVOList = featureVOList;
	}
	public List<String> getDataConstraintValueList() {
		return dataConstraintValueList;
	}
	public void setDataConstraintValueList(List<String> dataConstraintValueList) {
		this.dataConstraintValueList = dataConstraintValueList;
	}
	public Map<String, String> getDispalyInputValue() {
		return dispalyInputValue;
	}
	public void setDispalyInputValue(Map<String, String> dispalyInputValue) {
		this.dispalyInputValue = dispalyInputValue;
	}
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getDataFieldExID() {
		return dataFieldExID;
	}
	public void setDataFieldExID(String dataFieldExID) {
		this.dataFieldExID = dataFieldExID;
	}
	public String getValidation() {
		return validation;
	}
	public void setValidation(String validation) {
		this.validation = validation;
	}
	public String getValueTarget() {
		return valueTarget;
	}
	public void setValueTarget(String valueTarget) {
		this.valueTarget = valueTarget;
	}
	public String getEnteredValue() {
		return enteredValue;
	}
	public void setEnteredValue(String enteredValue) {
		this.enteredValue = enteredValue;
	}
	
	public Map<Integer, PriceTierVO> getPriceTierMap() {
		return priceTierMap;
	}
	public void setPriceTierMap(Map<Integer, PriceTierVO> priceTierMap) {
		this.priceTierMap = priceTierMap;
	}
	public String getTagSectionName() {
		return tagSectionName;
	}
	public void setTagSectionName(String tagSectionName) {
		this.tagSectionName = tagSectionName;
	}
	@Override
	public String toString() {
		return "DataFeildFeatureVO [baseNonRecurringPrice="
		+ baseNonRecurringPrice + ", baseRecurringPrice="
		+ baseRecurringPrice + ", comparableValue=" + comparableValue
		+ ", dataConstraint=" + dataConstraint
		+ ", dataConstraintValueList=" + dataConstraintValueList
		+ ", dataFieldExID=" + dataFieldExID + ", dataFieldName="
		+ dataFieldName + ", dataFieldText=" + dataFieldText
		+ ", description=" + description + ", dispalyInput="
		+ dispalyInput + ", dispalyInputValue=" + dispalyInputValue
		+ ", enteredValue=" + enteredValue + ", featureDescription="
		+ featureDescription + ", featureExID=" + featureExID
		+ ", featureVOList=" + featureVOList + ", isEnabled="
		+ isEnabled + ", isIncluded=" + isIncluded + ", type=" + type
		+ ", validation=" + validation + ", valueTarget=" + valueTarget
		+ "]";
	}


}
