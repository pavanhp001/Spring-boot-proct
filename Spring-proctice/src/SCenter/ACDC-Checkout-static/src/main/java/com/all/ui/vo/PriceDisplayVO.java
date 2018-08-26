package com.AL.ui.vo;

/**
 * @author prabhu sekhar tripuraneni
 * 
 * @desc this class contains the all fields that goes into the price related JSONObject 
 *
 */
public class PriceDisplayVO {
	
	private String featureGroupExternalID;
	private String featureExternalID;
	private String description;
	private String quantity;
	private String recurringPrice;
	private String nonRecurringPrice;
	private String type;
	private String dataConstraint;
	private boolean includedFlag;
	private boolean selected;
	private String priceValueType;
	 
	 
	 public String getPriceValueType() {
	  return priceValueType;
	 }
	 public void setPriceValueType(String priceValueType) {
	  this.priceValueType = priceValueType;
	 }
	
	/**
	 * @return the featureGroupExternalID
	 */
	public String getFeatureGroupExternalID() {
		return featureGroupExternalID;
	}
	/**
	 * @param featureGroupExternalID the featureGroupExternalID to set
	 */
	public void setFeatureGroupExternalID(String featureGroupExternalID) {
		this.featureGroupExternalID = featureGroupExternalID;
	}
	/**
	 * @return the featureExternalID
	 */
	public String getFeatureExternalID() {
		return featureExternalID;
	}
	/**
	 * @param featureExternalID the featureExternalID to set
	 */
	public void setFeatureExternalID(String featureExternalID) {
		this.featureExternalID = featureExternalID;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * @return the recurringPrice
	 */
	public String getRecurringPrice() {
		return recurringPrice;
	}
	/**
	 * @param recurringPrice the recurringPrice to set
	 */
	public void setRecurringPrice(String recurringPrice) {
		this.recurringPrice = recurringPrice;
	}
	/**
	 * @return the nonRecurringPrice
	 */
	public String getNonRecurringPrice() {
		return nonRecurringPrice;
	}
	/**
	 * @param nonRecurringPrice the nonRecurringPrice to set
	 */
	public void setNonRecurringPrice(String nonRecurringPrice) {
		this.nonRecurringPrice = nonRecurringPrice;
	}
	/**
	 * @return the includedFlag
	 */
	public boolean isIncludedFlag() {
		return includedFlag;
	}
	/**
	 * @param includedFlag the includedFlag to set
	 */
	public void setIncludedFlag(boolean includedFlag) {
		this.includedFlag = includedFlag;
	}
	/**
	 * @return the dataConstraint
	 */
	public String getDataConstraint() {
		return dataConstraint;
	}
	/**
	 * @param dataConstraint the dataConstraint to set
	 */
	public void setDataConstraint(String dataConstraint) {
		this.dataConstraint = dataConstraint;
	}
	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}
	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("PriceDisplayVO [");
		buff.append("featureGroupExternalID = "+featureGroupExternalID).append(",");
		buff.append("featureExternalID = "+featureExternalID).append(",");
		buff.append("description = "+description).append(",");
		buff.append("quantity = "+quantity).append(",");
		buff.append("dataConstraint = "+dataConstraint).append(",");
		buff.append("baseRecurringPrice = "+recurringPrice).append(",");
		buff.append("baseNonRecurringPrice = "+nonRecurringPrice).append(",");
		buff.append("type = "+type).append(",");
		buff.append("included = "+includedFlag);
		buff.append("]");
		return buff.toString();
	}
	
}
