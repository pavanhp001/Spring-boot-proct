package com.A.ui.vo;

import java.io.Serializable;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

public class LineItemTypeVO implements Serializable{
	
	private long lineItemExternalId;
	private int lineItemNumber;
	
	private String accountHolderFirstName;
	private String accountHolderLastName;
	private String accountHolderMiddleName;
	private String accountHolderNameSuffix;
	private String productName;
	private String providerName;
	private String lineItemDetailType;
	private String providerExtId;
	private String displayLineItem;
	private String oneTimePriceValue;
	private String monthlyPriceAfterPromoEnds;
	private String lineItemStatusAttribute;
	private String baseMonthlyNonPromotionPrice;
	private String lineItemName;
	private String lineItemProviderName;
	private String parentExternalId;
	private String productExternalId;
	private String firstDesiredTime;
	private String secondDesiredDate;
	private String secondDesiredTime;
	private String fallOutOrder;
	private String lineItemStatus;
	private String productSorce;
	private String productCategoryType;
	private String thermRate;
	private String guaranteeAmount;
	private String prepaidAmount;
	private String baseRecurringPriceForNaturalGas;
	private String baseNonRecurringPriceForNaturalGas;
	private String energyUnitName;
	private Double monthlyTotal;
	private Double instalationTotal;
	
	private List<String> previousStatus;
	private List<FeaturesAndSelecetionsVO> featuresAndSelecetionsVO;
	private String imageID;
	private String category;

	
	private XMLGregorianCalendar schedulingStartDate;
	private XMLGregorianCalendar schedulingStartTime;
	private XMLGregorianCalendar schedulingStartDateStartTime;
	private XMLGregorianCalendar schedulingStartDateEndTime;
	
	private XMLGregorianCalendar scheduledStartDate;
	private XMLGregorianCalendar scheduledStartTime;
	private XMLGregorianCalendar scheduledStartDateStartTime;
	private XMLGregorianCalendar scheduledStartDateEndTime;
	
	private XMLGregorianCalendar disconnectStartDate;
	private XMLGregorianCalendar disconnectStartTime;
	private XMLGregorianCalendar disconnectStartDateStartTime;
	private XMLGregorianCalendar disconnectStartDateEndTime;
	
	private XMLGregorianCalendar actualStartDate;
	private XMLGregorianCalendar actualStartTime;
	private XMLGregorianCalendar actualStartDateStartTime;
	private XMLGregorianCalendar actualStartDateEndTime;
	
	/**
	 * @return the secondDesiredDate
	 */
	public String getSecondDesiredDate() {
		return secondDesiredDate;
	}
	/**
	 * @param secondDesiredDate the secondDesiredDate to set
	 */
	public void setSecondDesiredDate(String secondDesiredDate) {
		this.secondDesiredDate = secondDesiredDate;
	}
	/**
	 * @return the secondDesiredTime
	 */
	public String getSecondDesiredTime() {
		return secondDesiredTime;
	}
	/**
	 * @param secondDesiredTime the secondDesiredTime to set
	 */
	public void setSecondDesiredTime(String secondDesiredTime) {
		this.secondDesiredTime = secondDesiredTime;
	}
	/**
	 * @return the firstDesiredTime
	 */
	public String getFirstDesiredTime() {
		return firstDesiredTime;
	}
	/**
	 * @param firstDesiredTime the firstDesiredTime to set
	 */
	public void setFirstDesiredTime(String firstDesiredTime) {
		this.firstDesiredTime = firstDesiredTime;
	}
	/**
	 * @return the lineItemExternalId
	 */
	public long getLineItemExternalId() {
		return lineItemExternalId;
	}
	/**
	 * @param lineItemExternalId the lineItemExternalId to set
	 */
	public void setLineItemExternalId(long lineItemExternalId) {
		this.lineItemExternalId = lineItemExternalId;
	}
	/**
	 * @return the accountHolderFirstName
	 */
	public String getAccountHolderFirstName() {
		return accountHolderFirstName;
	}
	/**
	 * @param accountHolderFirstName the accountHolderFirstName to set
	 */
	public void setAccountHolderFirstName(String accountHolderFirstName) {
		this.accountHolderFirstName = accountHolderFirstName;
	}
	/**
	 * @return the accountHolderLastName
	 */
	public String getAccountHolderLastName() {
		return accountHolderLastName;
	}
	/**
	 * @param accountHolderLastName the accountHolderLastName to set
	 */
	public void setAccountHolderLastName(String accountHolderLastName) {
		this.accountHolderLastName = accountHolderLastName;
	}
	/**
	 * @return the accountHolderMiddleName
	 */
	public String getAccountHolderMiddleName() {
		return accountHolderMiddleName;
	}
	/**
	 * @param accountHolderMiddleName the accountHolderMiddleName to set
	 */
	public void setAccountHolderMiddleName(String accountHolderMiddleName) {
		this.accountHolderMiddleName = accountHolderMiddleName;
	}
	/**
	 * @return the accountHolderNameSuffix
	 */
	public String getAccountHolderNameSuffix() {
		return accountHolderNameSuffix;
	}
	/**
	 * @param accountHolderNameSuffix the accountHolderNameSuffix to set
	 */
	public void setAccountHolderNameSuffix(String accountHolderNameSuffix) {
		this.accountHolderNameSuffix = accountHolderNameSuffix;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the providerName
	 */
	public String getProviderName() {
		return providerName;
	}
	/**
	 * @param providerName the providerName to set
	 */
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	/**
	 * @return the lineItemDetailType
	 */
	public String getLineItemDetailType() {
		return lineItemDetailType;
	}
	/**
	 * @param lineItemDetailType the lineItemDetailType to set
	 */
	public void setLineItemDetailType(String lineItemDetailType) {
		this.lineItemDetailType = lineItemDetailType;
	}
	/**
	 * @return the providerExtId
	 */
	public String getProviderExtId() {
		return providerExtId;
	}
	/**
	 * @param providerExtId the providerExtId to set
	 */
	public void setProviderExtId(String providerExtId) {
		this.providerExtId = providerExtId;
	}
	/**
	 * @return the displayLineItem
	 */
	public String getDisplayLineItem() {
		return displayLineItem;
	}
	/**
	 * @param displayLineItem the displayLineItem to set
	 */
	public void setDisplayLineItem(String displayLineItem) {
		this.displayLineItem = displayLineItem;
	}
	/**
	 * @return the oneTimePriceValue
	 */
	public String getOneTimePriceValue() {
		return oneTimePriceValue;
	}
	/**
	 * @param oneTimePriceValue the oneTimePriceValue to set
	 */
	public void setOneTimePriceValue(String oneTimePriceValue) {
		this.oneTimePriceValue = oneTimePriceValue;
	}
	/**
	 * @return the monthlyPriceAfterPromoEnds
	 */
	public String getMonthlyPriceAfterPromoEnds() {
		return monthlyPriceAfterPromoEnds;
	}
	/**
	 * @param monthlyPriceAfterPromoEnds the monthlyPriceAfterPromoEnds to set
	 */
	public void setMonthlyPriceAfterPromoEnds(String monthlyPriceAfterPromoEnds) {
		this.monthlyPriceAfterPromoEnds = monthlyPriceAfterPromoEnds;
	}
	/**
	 * @return the lineItemStatusAttribute
	 */
	public String getLineItemStatusAttribute() {
		return lineItemStatusAttribute;
	}
	/**
	 * @param lineItemStatusAttribute the lineItemStatusAttribute to set
	 */
	public void setLineItemStatusAttribute(String lineItemStatusAttribute) {
		this.lineItemStatusAttribute = lineItemStatusAttribute;
	}
	/**
	 * @return the baseMonthlyNonPromotionPrice
	 */
	public String getBaseMonthlyNonPromotionPrice() {
		return baseMonthlyNonPromotionPrice;
	}
	/**
	 * @param baseMonthlyNonPromotionPrice the baseMonthlyNonPromotionPrice to set
	 */
	public void setBaseMonthlyNonPromotionPrice(String baseMonthlyNonPromotionPrice) {
		this.baseMonthlyNonPromotionPrice = baseMonthlyNonPromotionPrice;
	}
	/**
	 * @return the lineItemName
	 */
	public String getLineItemName() {
		return lineItemName;
	}
	/**
	 * @param lineItemName the lineItemName to set
	 */
	public void setLineItemName(String lineItemName) {
		this.lineItemName = lineItemName;
	}
	/**
	 * @return the lineItemProviderName
	 */
	public String getLineItemProviderName() {
		return lineItemProviderName;
	}
	/**
	 * @param lineItemProviderName the lineItemProviderName to set
	 */
	public void setLineItemProviderName(String lineItemProviderName) {
		this.lineItemProviderName = lineItemProviderName;
	}
	/**
	 * @return the parentExternalId
	 */
	public String getParentExternalId() {
		return parentExternalId;
	}
	/**
	 * @param parentExternalId the parentExternalId to set
	 */
	public void setParentExternalId(String parentExternalId) {
		this.parentExternalId = parentExternalId;
	}
	/**
	 * @return the productExternalId
	 */
	public String getProductExternalId() {
		return productExternalId;
	}
	/**
	 * @param productExternalId the productExternalId to set
	 */
	public void setProductExternalId(String productExternalId) {
		this.productExternalId = productExternalId;
	}
	
	/**
	 * @return the fallOutOrder
	 */
	public String getFallOutOrder() {
		return fallOutOrder;
	}
	/**
	 * @param fallOutOrder the fallOutOrder to set
	 */
	public void setFallOutOrder(String fallOutOrder) {
		this.fallOutOrder = fallOutOrder;
	}
	/**
	 * @return the productCategoryType
	 */
	public String getProductCategoryType() {
		return productCategoryType;
	}
	/**
	 * @param productCategoryType the productCategoryType to set
	 */
	public void setProductCategoryType(String productCategoryType) {
		this.productCategoryType = productCategoryType;
	}
	/**
	 * @return the thermRate
	 */
	public String getThermRate() {
		return thermRate;
	}
	/**
	 * @param thermRate the thermRate to set
	 */
	public void setThermRate(String thermRate) {
		this.thermRate = thermRate;
	}
	/**
	 * @return the guaranteeAmount
	 */
	public String getGuaranteeAmount() {
		return guaranteeAmount;
	}
	/**
	 * @param guaranteeAmount the guaranteeAmount to set
	 */
	public void setGuaranteeAmount(String guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}
	/**
	 * @return the prepaidAmount
	 */
	public String getPrepaidAmount() {
		return prepaidAmount;
	}
	/**
	 * @param prepaidAmount the prepaidAmount to set
	 */
	public void setPrepaidAmount(String prepaidAmount) {
		this.prepaidAmount = prepaidAmount;
	}
	/**
	 * @return the baseRecurringPriceForNaturalGas
	 */
	public String getBaseRecurringPriceForNaturalGas() {
		return baseRecurringPriceForNaturalGas;
	}
	/**
	 * @param baseRecurringPriceForNaturalGas the baseRecurringPriceForNaturalGas to set
	 */
	public void setBaseRecurringPriceForNaturalGas(
			String baseRecurringPriceForNaturalGas) {
		this.baseRecurringPriceForNaturalGas = baseRecurringPriceForNaturalGas;
	}
	/**
	 * @return the baseNonRecurringPriceForNaturalGas
	 */
	public String getBaseNonRecurringPriceForNaturalGas() {
		return baseNonRecurringPriceForNaturalGas;
	}
	/**
	 * @param baseNonRecurringPriceForNaturalGas the baseNonRecurringPriceForNaturalGas to set
	 */
	public void setBaseNonRecurringPriceForNaturalGas(
			String baseNonRecurringPriceForNaturalGas) {
		this.baseNonRecurringPriceForNaturalGas = baseNonRecurringPriceForNaturalGas;
	}
	/**
	 * @return the monthlyTotal
	 */
	public Double getMonthlyTotal() {
		return monthlyTotal;
	}
	/**
	 * @param monthlyTotal the monthlyTotal to set
	 */
	public void setMonthlyTotal(Double monthlyTotal) {
		this.monthlyTotal = monthlyTotal;
	}
	/**
	 * @return the instalationTotal
	 */
	public Double getInstalationTotal() {
		return instalationTotal;
	}
	/**
	 * @param instalationTotal the instalationTotal to set
	 */
	public void setInstalationTotal(Double instalationTotal) {
		this.instalationTotal = instalationTotal;
	}
	/**
	 * @return the lineItemNumber
	 */
	public int getLineItemNumber() {
		return lineItemNumber;
	}
	/**
	 * @param lineItemNumber the lineItemNumber to set
	 */
	public void setLineItemNumber(int lineItemNumber) {
		this.lineItemNumber = lineItemNumber;
	}
	/**
	 * @return the lineItemStatus
	 */
	public String getLineItemStatus() {
		return lineItemStatus;
	}
	/**
	 * @param lineItemStatus the lineItemStatus to set
	 */
	public void setLineItemStatus(String lineItemStatus) {
		this.lineItemStatus = lineItemStatus;
	}
	/**
	 * @return the productSorce
	 */
	public String getProductSorce() {
		return productSorce;
	}
	/**
	 * @param productSorce the productSorce to set
	 */
	public void setProductSorce(String productSorce) {
		this.productSorce = productSorce;
	}
	/**
	 * @return the previousStatus
	 */
	public List<String> getPreviousStatus() {
		return previousStatus;
	}
	/**
	 * @param previousStatus the previousStatus to set
	 */
	public void setPreviousStatus(List<String> previousStatus) {
		this.previousStatus = previousStatus;
	}
	/**
	 * @return the featuresAndSelecetionsVO
	 */
	public List<FeaturesAndSelecetionsVO> getFeaturesAndSelecetionsVO() {
		return featuresAndSelecetionsVO;
	}
	/**
	 * @param featuresAndSelecetionsVO the featuresAndSelecetionsVO to set
	 */
	public void setFeaturesAndSelecetionsVO(
			List<FeaturesAndSelecetionsVO> featuresAndSelecetionsVO) {
		this.featuresAndSelecetionsVO = featuresAndSelecetionsVO;
	}
	
	/**
	 * @param schedulingStartDate the schedulingStartDate to set
	 */
	public void setSchedulingStartDate(XMLGregorianCalendar schedulingStartDate) {
		this.schedulingStartDate = schedulingStartDate;
	}
	
	/**
	 * @return the schedulingStartDate
	 */
	public XMLGregorianCalendar getSchedulingStartDate() {
		return schedulingStartDate;
	}
	/**
	 * @param schedulingStartTime the schedulingStartTime to set
	 */
	public void setSchedulingStartTime(XMLGregorianCalendar schedulingStartTime) {
		this.schedulingStartTime = schedulingStartTime;
	}
	/**
	 * @return the schedulingStartTime
	 */
	public XMLGregorianCalendar getSchedulingStartTime() {
		return schedulingStartTime;
	}
	/**
	 * @param schedulingStartDateStartTime the schedulingStartDateStartTime to set
	 */
	public void setSchedulingStartDateStartTime(XMLGregorianCalendar schedulingStartDateStartTime) {
		this.schedulingStartDateStartTime = schedulingStartDateStartTime;
	}
	/**
	 * @return the schedulingStartDateStartTime
	 */
	public XMLGregorianCalendar getSchedulingStartDateStartTime() {
		return schedulingStartDateStartTime;
	}
	/**
	 * @param schedulingStartDateEndTime the schedulingStartDateEndTime to set
	 */
	public void setSchedulingStartDateEndTime(XMLGregorianCalendar schedulingStartDateEndTime) {
		this.schedulingStartDateEndTime = schedulingStartDateEndTime;
	}
	/**
	 * @return the schedulingStartDateEndTime
	 */
	public XMLGregorianCalendar getSchedulingStartDateEndTime() {
		return schedulingStartDateEndTime;
	}
	/**
	 * @return the scheduledStartDate
	 */
	public XMLGregorianCalendar getScheduledStartDate() {
		return scheduledStartDate;
	}
	/**
	 * @param scheduledStartDate the scheduledStartDate to set
	 */
	public void setScheduledStartDate(XMLGregorianCalendar scheduledStartDate) {
		this.scheduledStartDate = scheduledStartDate;
	}
	/**
	 * @return the scheduledStartTime
	 */
	public XMLGregorianCalendar getScheduledStartTime() {
		return scheduledStartTime;
	}
	/**
	 * @param scheduledStartTime the scheduledStartTime to set
	 */
	public void setScheduledStartTime(XMLGregorianCalendar scheduledStartTime) {
		this.scheduledStartTime = scheduledStartTime;
	}
	/**
	 * @return the scheduledStartDateStartTime
	 */
	public XMLGregorianCalendar getScheduledStartDateStartTime() {
		return scheduledStartDateStartTime;
	}
	/**
	 * @param scheduledStartDateStartTime the scheduledStartDateStartTime to set
	 */
	public void setScheduledStartDateStartTime(
			XMLGregorianCalendar scheduledStartDateStartTime) {
		this.scheduledStartDateStartTime = scheduledStartDateStartTime;
	}
	/**
	 * @return the scheduledStartDateEndTime
	 */
	public XMLGregorianCalendar getScheduledStartDateEndTime() {
		return scheduledStartDateEndTime;
	}
	/**
	 * @param scheduledStartDateEndTime the scheduledStartDateEndTime to set
	 */
	public void setScheduledStartDateEndTime(
			XMLGregorianCalendar scheduledStartDateEndTime) {
		this.scheduledStartDateEndTime = scheduledStartDateEndTime;
	}
	/**
	 * @return the disconnectStartDate
	 */
	public XMLGregorianCalendar getDisconnectStartDate() {
		return disconnectStartDate;
	}
	/**
	 * @param disconnectStartDate the disconnectStartDate to set
	 */
	public void setDisconnectStartDate(XMLGregorianCalendar disconnectStartDate) {
		this.disconnectStartDate = disconnectStartDate;
	}
	/**
	 * @return the disconnectStartTime
	 */
	public XMLGregorianCalendar getDisconnectStartTime() {
		return disconnectStartTime;
	}
	/**
	 * @param disconnectStartTime the disconnectStartTime to set
	 */
	public void setDisconnectStartTime(XMLGregorianCalendar disconnectStartTime) {
		this.disconnectStartTime = disconnectStartTime;
	}
	/**
	 * @return the disconnectStartDateStartTime
	 */
	public XMLGregorianCalendar getDisconnectStartDateStartTime() {
		return disconnectStartDateStartTime;
	}
	/**
	 * @param disconnectStartDateStartTime the disconnectStartDateStartTime to set
	 */
	public void setDisconnectStartDateStartTime(
			XMLGregorianCalendar disconnectStartDateStartTime) {
		this.disconnectStartDateStartTime = disconnectStartDateStartTime;
	}
	/**
	 * @return the disconnectStartDateEndTime
	 */
	public XMLGregorianCalendar getDisconnectStartDateEndTime() {
		return disconnectStartDateEndTime;
	}
	/**
	 * @param disconnectStartDateEndTime the disconnectStartDateEndTime to set
	 */
	public void setDisconnectStartDateEndTime(
			XMLGregorianCalendar disconnectStartDateEndTime) {
		this.disconnectStartDateEndTime = disconnectStartDateEndTime;
	}
	/**
	 * @return the actualStartDate
	 */
	public XMLGregorianCalendar getActualStartDate() {
		return actualStartDate;
	}
	/**
	 * @param actualStartDate the actualStartDate to set
	 */
	public void setActualStartDate(XMLGregorianCalendar actualStartDate) {
		this.actualStartDate = actualStartDate;
	}
	/**
	 * @return the actualStartTime
	 */
	public XMLGregorianCalendar getActualStartTime() {
		return actualStartTime;
	}
	/**
	 * @param actualStartTime the actualStartTime to set
	 */
	public void setActualStartTime(XMLGregorianCalendar actualStartTime) {
		this.actualStartTime = actualStartTime;
	}
	/**
	 * @return the actualStartDateStartTime
	 */
	public XMLGregorianCalendar getActualStartDateStartTime() {
		return actualStartDateStartTime;
	}
	/**
	 * @param actualStartDateStartTime the actualStartDateStartTime to set
	 */
	public void setActualStartDateStartTime(
			XMLGregorianCalendar actualStartDateStartTime) {
		this.actualStartDateStartTime = actualStartDateStartTime;
	}
	/**
	 * @return the actualStartDateEndTime
	 */
	public XMLGregorianCalendar getActualStartDateEndTime() {
		return actualStartDateEndTime;
	}
	/**
	 * @param actualStartDateEndTime the actualStartDateEndTime to set
	 */
	public void setActualStartDateEndTime(
			XMLGregorianCalendar actualStartDateEndTime) {
		this.actualStartDateEndTime = actualStartDateEndTime;
	}
	public void setEnergyUnitName(String energyUnitName) {
		this.energyUnitName = energyUnitName;
	}

	public String getEnergyUnitName() {
		return energyUnitName;
	}
	public void setImageID(String imageID) {
		this.imageID = imageID;
	}
	public String getImageID() {
		return imageID;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategory() {
		return category;
	}

}
