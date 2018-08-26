package com.A.V.beans.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.interfaces.CommonBeanInterface;

@Entity
@Table(name = "OM_CUST_SELECTION")
public class CustomSelection implements CommonBeanInterface
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9051443972639890815L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "custSelectionSequence")
	@SequenceGenerator(name = "custSelectionSequence", sequenceName = "OM_CUST_SELECTION_SEQ", allocationSize = 1)
	private long id;
	
	@Column(name = "SELECTION_EXT_ID")
	private String selectionExtId;
	
	@Column(name = "PARENT_CHOICE_EXT_ID")
	private String parentChoiceExtId;
	
	@Column(name = "CHOICE_EXT_ID")
	private String choiceExtId;
	
	@Column(name = "CHOICE_DETAIL")
	private String choiceDetail;
	
	@Column(name = "BASE_NONRECURRING_PRICE")
	private double baseNonRecurringPrice;
	
	@Column(name = "BASE_RECURRING_PRICE")
	private double baseRecurringPrice;
	
	@Column(name = "BASE_NONRECURRING_PRICE_UNITS")
	private String baseNonRecurringPriceUnits;
	
	@Column(name = "BASE_RECURRING_PRICE_UNITS")
	private String baseRecurringPriceUnits;
	
	@Column(name = "INCLUDEIN_TOTAL_PRICE")
	private boolean includeInTotalPrice;
		
	@Column(name = "SELECTION_DISPLAY_ORDER")		  
	private BigInteger selectionDisplayOrder;
	
	@Column(name = "SELECTION_TYPE")
	private String selectionType;
	
	@Column(name = "SELECTION_SHORT_DESC")
	private String selectionShortDesc;
	
	@Column(name = "SELECTION_NAME")
	private String selectionName;

	@Column(name = "CHIOCE_DISPLAY_ORDER")
	private BigInteger choiceDisplayOrder;
	
	@Column(name = "CHIOCE_SHORT_DESC")
	private String choiceShortDes;
	
	@Column(name = "CHIOCE_NAME")
	private String choiceName;
	
	
	@Override
	public long getId()
	{
		return id;
	}

	@Override
	public void setId( long id )
	{
		this.id = id;
	}

	public String getSelectionExtId()
	{
		return selectionExtId;
	}

	public void setSelectionExtId( String selectionExtId )
	{
		this.selectionExtId = selectionExtId;
	}

	public String getParentChoiceExtId()
	{
		return parentChoiceExtId;
	}

	public void setParentChoiceExtId( String parentChoiceExtId )
	{
		this.parentChoiceExtId = parentChoiceExtId;
	}

	public String getChoiceExtId()
	{
		return choiceExtId;
	}

	public void setChoiceExtId( String choiceExtId )
	{
		this.choiceExtId = choiceExtId;
	}

	public String getChoiceDetail()
	{
		return choiceDetail;
	}

	public void setChoiceDetail( String choiceDetail )
	{
		this.choiceDetail = choiceDetail;
	}

	public double getBaseNonRecurringPrice() {
		return baseNonRecurringPrice;
	}

	public void setBaseNonRecurringPrice(double baseNonRecurringPrice) {
		this.baseNonRecurringPrice = baseNonRecurringPrice;
	}

	public double getBaseRecurringPrice() {
		return baseRecurringPrice;
	}

	public void setBaseRecurringPrice(double baseRecurringPrice) {
		this.baseRecurringPrice = baseRecurringPrice;
	}

	
	public String getBaseNonRecurringPriceUnits() {
		return baseNonRecurringPriceUnits;
	}

	public void setBaseNonRecurringPriceUnits(String baseNonRecurringPriceUnits) {
		this.baseNonRecurringPriceUnits = baseNonRecurringPriceUnits;
	}

	public String getBaseRecurringPriceUnits() {
		return baseRecurringPriceUnits;
	}

	public void setBaseRecurringPriceUnits(String baseRecurringPriceUnits) {
		this.baseRecurringPriceUnits = baseRecurringPriceUnits;
	}

	public boolean isIncludeInTotalPrice() {
		return includeInTotalPrice;
	}

	public void setIncludeInTotalPrice(boolean includeInTotalPrice) {
		this.includeInTotalPrice = includeInTotalPrice;
	}

	
	public String getSelectionType() {
		return selectionType;
	}

	public void setSelectionType(String selectionType) {
		this.selectionType = selectionType;
	}

	public String getSelectionShortDesc() {
		return selectionShortDesc;
	}

	public void setSelectionShortDesc(String selectionShortDesc) {
		this.selectionShortDesc = selectionShortDesc;
	}

	public String getSelectionName() {
		return selectionName;
	}

	public void setSelectionName(String selectionName) {
		this.selectionName = selectionName;
	}

	public BigInteger getSelectionDisplayOrder() {
		return selectionDisplayOrder;
	}

	public void setSelectionDisplayOrder(BigInteger selectionDisplayOrder) {
		this.selectionDisplayOrder = selectionDisplayOrder;
	}

	public BigInteger getChoiceDisplayOrder() {
		return choiceDisplayOrder;
	}

	public void setChoiceDisplayOrder(BigInteger choiceDisplayOrder) {
		this.choiceDisplayOrder = choiceDisplayOrder;
	}

	public String getChoiceShortDes() {
		return choiceShortDes;
	}

	public void setChoiceShortDes(String choiceShortDes) {
		this.choiceShortDes = choiceShortDes;
	}

	public String getChoiceName() {
		return choiceName;
	}

	public void setChoiceName(String choiceName) {
		this.choiceName = choiceName;
	}
	
	

}
