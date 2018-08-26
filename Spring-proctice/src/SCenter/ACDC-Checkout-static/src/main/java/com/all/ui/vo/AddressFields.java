package com.AL.ui.vo;

public class AddressFields {
	//previous address details
	private String prevAddrPrefixDirectional;
	private  String prevAddrStreetName;
	private  String prevAddrStreetNumber;
	private  String prevAddrStreetType;
	private  String prevAddrLine2;
	private  String prevAddrLine2Info;
	private  String prevAddrCity;
	private  String prevAddrStateOrPrivince;
	private  String prevAddrPostalCode;
	
	//service address details
	
	private  String svcAddrPrefixDirectional;
	private  String svcAddrStreetName;
	private  String svcAddrStreetNumber;
	private  String svcAddrStreetType;
	private  String svcAddrLine2;
	private  String svcAddrLine2Info;
	private  String svcAddrCity;
	private  String svcAddrStateOrPrivince;
	private  String svcAddrPostalCode;
	
	//billing address details
	private  String billingAddrPrefixDirectional;
	private  String billingAddrStreetName;
	private  String billingAddrStreetNumber;
	private  String billingAddrStreetType;
	private  String billingAddrLine2;
	private  String billingAddrLine2Info;
	private  String billingAddrCity;
	private  String billingAddrStateOrPrivince;
	private  String billingAddrPostalCode;
	
	//shipping address details
	private  String shippingAddrPrefixDirectional;
	private  String shippingAddrStreetName;
	private  String shippingAddrStreetNumber;
	private  String shippingAddrStreetType;
	private  String shippingAddrLine2;
	private  String shippingAddrLine2Info;
	private  String shippingAddrCity;
	private  String shippingAddrStateOrPrivince;
	private  String shippingAddrPostalCode;
	private  boolean isBillingAddressPresent = false;
	
	/**
	 * @return the prevAddrPrefixDirectional
	 */
	public String getPrevAddrPrefixDirectional() {
		return prevAddrPrefixDirectional;
	}
	/**
	 * @param prevAddrPrefixDirectional the prevAddrPrefixDirectional to set
	 */
	public void setPrevAddrPrefixDirectional(String prevAddrPrefixDirectional) {
		this.prevAddrPrefixDirectional = prevAddrPrefixDirectional;
	}
	/**
	 * @return the prevAddrStreetName
	 */
	public String getPrevAddrStreetName() {
		return prevAddrStreetName;
	}
	/**
	 * @param prevAddrStreetName the prevAddrStreetName to set
	 */
	public void setPrevAddrStreetName(String prevAddrStreetName) {
		this.prevAddrStreetName = prevAddrStreetName;
	}
	/**
	 * @return the prevAddrStreetNumber
	 */
	public String getPrevAddrStreetNumber() {
		return prevAddrStreetNumber;
	}
	/**
	 * @param prevAddrStreetNumber the prevAddrStreetNumber to set
	 */
	public void setPrevAddrStreetNumber(String prevAddrStreetNumber) {
		this.prevAddrStreetNumber = prevAddrStreetNumber;
	}
	/**
	 * @return the prevAddrStreetType
	 */
	public String getPrevAddrStreetType() {
		return prevAddrStreetType;
	}
	/**
	 * @param prevAddrStreetType the prevAddrStreetType to set
	 */
	public void setPrevAddrStreetType(String prevAddrStreetType) {
		this.prevAddrStreetType = prevAddrStreetType;
	}
	/**
	 * @return the prevAddrLine2
	 */
	public String getPrevAddrLine2() {
		return prevAddrLine2;
	}
	/**
	 * @param prevAddrLine2 the prevAddrLine2 to set
	 */
	public void setPrevAddrLine2(String prevAddrLine2) {
		this.prevAddrLine2 = prevAddrLine2;
	}
	/**
	 * @return the prevAddrLine2Info
	 */
	public String getPrevAddrLine2Info() {
		return prevAddrLine2Info;
	}
	/**
	 * @param prevAddrLine2Info the prevAddrLine2Info to set
	 */
	public void setPrevAddrLine2Info(String prevAddrLine2Info) {
		this.prevAddrLine2Info = prevAddrLine2Info;
	}
	/**
	 * @return the prevAddrCity
	 */
	public String getPrevAddrCity() {
		return prevAddrCity;
	}
	/**
	 * @param prevAddrCity the prevAddrCity to set
	 */
	public void setPrevAddrCity(String prevAddrCity) {
		this.prevAddrCity = prevAddrCity;
	}
	/**
	 * @return the prevAddrStateOrPrivince
	 */
	public String getPrevAddrStateOrPrivince() {
		return prevAddrStateOrPrivince;
	}
	/**
	 * @param prevAddrStateOrPrivince the prevAddrStateOrPrivince to set
	 */
	public void setPrevAddrStateOrPrivince(String prevAddrStateOrPrivince) {
		this.prevAddrStateOrPrivince = prevAddrStateOrPrivince;
	}
	/**
	 * @return the prevAddrPostalCode
	 */
	public String getPrevAddrPostalCode() {
		return prevAddrPostalCode;
	}
	/**
	 * @param prevAddrPostalCode the prevAddrPostalCode to set
	 */
	public void setPrevAddrPostalCode(String prevAddrPostalCode) {
		this.prevAddrPostalCode = prevAddrPostalCode;
	}
	/**
	 * @return the svcAddrPrefixDirectional
	 */
	public String getSvcAddrPrefixDirectional() {
		return svcAddrPrefixDirectional;
	}
	/**
	 * @param svcAddrPrefixDirectional the svcAddrPrefixDirectional to set
	 */
	public void setSvcAddrPrefixDirectional(String svcAddrPrefixDirectional) {
		this.svcAddrPrefixDirectional = svcAddrPrefixDirectional;
	}
	/**
	 * @return the svcAddrStreetName
	 */
	public String getSvcAddrStreetName() {
		return svcAddrStreetName;
	}
	/**
	 * @param svcAddrStreetName the svcAddrStreetName to set
	 */
	public void setSvcAddrStreetName(String svcAddrStreetName) {
		this.svcAddrStreetName = svcAddrStreetName;
	}
	/**
	 * @return the svcAddrStreetNumber
	 */
	public String getSvcAddrStreetNumber() {
		return svcAddrStreetNumber;
	}
	/**
	 * @param svcAddrStreetNumber the svcAddrStreetNumber to set
	 */
	public void setSvcAddrStreetNumber(String svcAddrStreetNumber) {
		this.svcAddrStreetNumber = svcAddrStreetNumber;
	}
	/**
	 * @return the svcAddrStreetType
	 */
	public String getSvcAddrStreetType() {
		return svcAddrStreetType;
	}
	/**
	 * @param svcAddrStreetType the svcAddrStreetType to set
	 */
	public void setSvcAddrStreetType(String svcAddrStreetType) {
		this.svcAddrStreetType = svcAddrStreetType;
	}
	/**
	 * @return the svcAddrLine2
	 */
	public String getSvcAddrLine2() {
		return svcAddrLine2;
	}
	/**
	 * @param svcAddrLine2 the svcAddrLine2 to set
	 */
	public void setSvcAddrLine2(String svcAddrLine2) {
		this.svcAddrLine2 = svcAddrLine2;
	}
	/**
	 * @return the svcAddrLine2Info
	 */
	public String getSvcAddrLine2Info() {
		return svcAddrLine2Info;
	}
	/**
	 * @param svcAddrLine2Info the svcAddrLine2Info to set
	 */
	public void setSvcAddrLine2Info(String svcAddrLine2Info) {
		this.svcAddrLine2Info = svcAddrLine2Info;
	}
	/**
	 * @return the svcAddrCity
	 */
	public String getSvcAddrCity() {
		return svcAddrCity;
	}
	/**
	 * @param svcAddrCity the svcAddrCity to set
	 */
	public void setSvcAddrCity(String svcAddrCity) {
		this.svcAddrCity = svcAddrCity;
	}
	/**
	 * @return the svcAddrStateOrPrivince
	 */
	public String getSvcAddrStateOrPrivince() {
		return svcAddrStateOrPrivince;
	}
	/**
	 * @param svcAddrStateOrPrivince the svcAddrStateOrPrivince to set
	 */
	public void setSvcAddrStateOrPrivince(String svcAddrStateOrPrivince) {
		this.svcAddrStateOrPrivince = svcAddrStateOrPrivince;
	}
	/**
	 * @return the svcAddrPostalCode
	 */
	public String getSvcAddrPostalCode() {
		return svcAddrPostalCode;
	}
	/**
	 * @param svcAddrPostalCode the svcAddrPostalCode to set
	 */
	public void setSvcAddrPostalCode(String svcAddrPostalCode) {
		this.svcAddrPostalCode = svcAddrPostalCode;
	}
	/**
	 * @return the billingAddrPrefixDirectional
	 */
	public String getBillingAddrPrefixDirectional() {
		return billingAddrPrefixDirectional;
	}
	/**
	 * @param billingAddrPrefixDirectional the billingAddrPrefixDirectional to set
	 */
	public void setBillingAddrPrefixDirectional(String billingAddrPrefixDirectional) {
		this.billingAddrPrefixDirectional = billingAddrPrefixDirectional;
	}
	/**
	 * @return the billingAddrStreetName
	 */
	public String getBillingAddrStreetName() {
		return billingAddrStreetName;
	}
	/**
	 * @param billingAddrStreetName the billingAddrStreetName to set
	 */
	public void setBillingAddrStreetName(String billingAddrStreetName) {
		this.billingAddrStreetName = billingAddrStreetName;
	}
	/**
	 * @return the billingAddrStreetNumber
	 */
	public String getBillingAddrStreetNumber() {
		return billingAddrStreetNumber;
	}
	/**
	 * @param billingAddrStreetNumber the billingAddrStreetNumber to set
	 */
	public void setBillingAddrStreetNumber(String billingAddrStreetNumber) {
		this.billingAddrStreetNumber = billingAddrStreetNumber;
	}
	/**
	 * @return the billingAddrStreetType
	 */
	public String getBillingAddrStreetType() {
		return billingAddrStreetType;
	}
	/**
	 * @param billingAddrStreetType the billingAddrStreetType to set
	 */
	public void setBillingAddrStreetType(String billingAddrStreetType) {
		this.billingAddrStreetType = billingAddrStreetType;
	}
	/**
	 * @return the billingAddrLine2
	 */
	public String getBillingAddrLine2() {
		return billingAddrLine2;
	}
	/**
	 * @param billingAddrLine2 the billingAddrLine2 to set
	 */
	public void setBillingAddrLine2(String billingAddrLine2) {
		this.billingAddrLine2 = billingAddrLine2;
	}
	/**
	 * @return the billingAddrLine2Info
	 */
	public String getBillingAddrLine2Info() {
		return billingAddrLine2Info;
	}
	/**
	 * @param billingAddrLine2Info the billingAddrLine2Info to set
	 */
	public void setBillingAddrLine2Info(String billingAddrLine2Info) {
		this.billingAddrLine2Info = billingAddrLine2Info;
	}
	/**
	 * @return the billingAddrCity
	 */
	public String getBillingAddrCity() {
		return billingAddrCity;
	}
	/**
	 * @param billingAddrCity the billingAddrCity to set
	 */
	public void setBillingAddrCity(String billingAddrCity) {
		this.billingAddrCity = billingAddrCity;
	}
	/**
	 * @return the billingAddrStateOrPrivince
	 */
	public String getBillingAddrStateOrPrivince() {
		return billingAddrStateOrPrivince;
	}
	/**
	 * @param billingAddrStateOrPrivince the billingAddrStateOrPrivince to set
	 */
	public void setBillingAddrStateOrPrivince(String billingAddrStateOrPrivince) {
		this.billingAddrStateOrPrivince = billingAddrStateOrPrivince;
	}
	/**
	 * @return the billingAddrPostalCode
	 */
	public String getBillingAddrPostalCode() {
		return billingAddrPostalCode;
	}
	/**
	 * @param billingAddrPostalCode the billingAddrPostalCode to set
	 */
	public void setBillingAddrPostalCode(String billingAddrPostalCode) {
		this.billingAddrPostalCode = billingAddrPostalCode;
	}
	/**
	 * @return the shippingAddrPrefixDirectional
	 */
	public String getShippingAddrPrefixDirectional() {
		return shippingAddrPrefixDirectional;
	}
	/**
	 * @param shippingAddrPrefixDirectional the shippingAddrPrefixDirectional to set
	 */
	public void setShippingAddrPrefixDirectional(
			String shippingAddrPrefixDirectional) {
		this.shippingAddrPrefixDirectional = shippingAddrPrefixDirectional;
	}
	/**
	 * @return the shippingAddrStreetName
	 */
	public String getShippingAddrStreetName() {
		return shippingAddrStreetName;
	}
	/**
	 * @param shippingAddrStreetName the shippingAddrStreetName to set
	 */
	public void setShippingAddrStreetName(String shippingAddrStreetName) {
		this.shippingAddrStreetName = shippingAddrStreetName;
	}
	/**
	 * @return the shippingAddrStreetNumber
	 */
	public String getShippingAddrStreetNumber() {
		return shippingAddrStreetNumber;
	}
	/**
	 * @param shippingAddrStreetNumber the shippingAddrStreetNumber to set
	 */
	public void setShippingAddrStreetNumber(String shippingAddrStreetNumber) {
		this.shippingAddrStreetNumber = shippingAddrStreetNumber;
	}
	/**
	 * @return the shippingAddrStreetType
	 */
	public String getShippingAddrStreetType() {
		return shippingAddrStreetType;
	}
	/**
	 * @param shippingAddrStreetType the shippingAddrStreetType to set
	 */
	public void setShippingAddrStreetType(String shippingAddrStreetType) {
		this.shippingAddrStreetType = shippingAddrStreetType;
	}
	/**
	 * @return the shippingAddrLine2
	 */
	public String getShippingAddrLine2() {
		return shippingAddrLine2;
	}
	/**
	 * @param shippingAddrLine2 the shippingAddrLine2 to set
	 */
	public void setShippingAddrLine2(String shippingAddrLine2) {
		this.shippingAddrLine2 = shippingAddrLine2;
	}
	/**
	 * @return the shippingAddrLine2Info
	 */
	public String getShippingAddrLine2Info() {
		return shippingAddrLine2Info;
	}
	/**
	 * @param shippingAddrLine2Info the shippingAddrLine2Info to set
	 */
	public void setShippingAddrLine2Info(String shippingAddrLine2Info) {
		this.shippingAddrLine2Info = shippingAddrLine2Info;
	}
	/**
	 * @return the shippingAddrCity
	 */
	public String getShippingAddrCity() {
		return shippingAddrCity;
	}
	/**
	 * @param shippingAddrCity the shippingAddrCity to set
	 */
	public void setShippingAddrCity(String shippingAddrCity) {
		this.shippingAddrCity = shippingAddrCity;
	}
	/**
	 * @return the shippingAddrStateOrPrivince
	 */
	public String getShippingAddrStateOrPrivince() {
		return shippingAddrStateOrPrivince;
	}
	/**
	 * @param shippingAddrStateOrPrivince the shippingAddrStateOrPrivince to set
	 */
	public void setShippingAddrStateOrPrivince(String shippingAddrStateOrPrivince) {
		this.shippingAddrStateOrPrivince = shippingAddrStateOrPrivince;
	}
	/**
	 * @return the shippingAddrPostalCode
	 */
	public String getShippingAddrPostalCode() {
		return shippingAddrPostalCode;
	}
	/**
	 * @param shippingAddrPostalCode the shippingAddrPostalCode to set
	 */
	public void setShippingAddrPostalCode(String shippingAddrPostalCode) {
		this.shippingAddrPostalCode = shippingAddrPostalCode;
	}
	
	
	
	
	public boolean isBillingAddressPresent() {
		return isBillingAddressPresent;
	}
	public void setBillingAddressPresent(boolean isBillingAddressPresent) {
		this.isBillingAddressPresent = isBillingAddressPresent;
	}
	public  String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("prevAddrPrefixDirectional: "    +this.prevAddrPrefixDirectional);
		sb.append("prevAddrStreetName       : "    +this.prevAddrStreetName);
		sb.append("prevAddrStreetNumber     : "   +this.prevAddrStreetNumber);
		sb.append("prevAddrStreetType       : "   +this. prevAddrStreetType);
		sb.append("prevAddrLine2            : "   +this. prevAddrLine2);
		sb.append("prevAddrLine2Info        : "   +this. prevAddrLine2Info);
		sb.append("prevAddrCity             : "   +this. prevAddrCity);
		sb.append("prevAddrStateOrPrivince  : "   +this. prevAddrStateOrPrivince);
		sb.append("prevAddrPostalCode       : "   +this. prevAddrPostalCode);
		sb.append("svcAddrPrefixDirectional : "   +this. svcAddrPrefixDirectional);
		sb.append("svcAddrStreetName        : "   +this. svcAddrStreetName);
		sb.append("svcAddrStreetNumber      : "   +this. svcAddrStreetNumber);
		sb.append("svcAddrStreetType        : "   +this. svcAddrStreetType);
		sb.append("svcAddrLine2             : "   +this. svcAddrLine2);
		sb.append("svcAddrLine2Info         : "   +this. svcAddrLine2Info);
		sb.append("svcAddrCity              : "   +this. svcAddrCity);
		sb.append("svcAddrStateOrPrivince   : "   +this. svcAddrStateOrPrivince);
		sb.append("svcAddrPostalCode        : "   +this. svcAddrPostalCode);
		sb.append("billingAddrPrefixDirectional: " +this.billingAddrPrefixDirectional);
		sb.append("billingAddrStreetName       : " +this.billingAddrStreetName);
		sb.append("billingAddrStreetNumber     : " +this.billingAddrStreetNumber);
		sb.append("billingAddrStreetType       : " +this.billingAddrStreetType);
		sb.append("billingAddrLine2            : " +this.billingAddrLine2);
		sb.append("billingAddrLine2Info        : " +this.billingAddrLine2Info);
		sb.append("billingAddrCity             : " +this.billingAddrCity);
		sb.append("billingAddrStateOrPrivince  : " +this.billingAddrStateOrPrivince);
		sb.append("billingAddrPostalCode       : " +this.billingAddrPostalCode);
		sb.append("shippingAddrPrefixDirectional: "+this.shippingAddrPrefixDirectional);
		sb.append("shippingAddrStreetName       : "+this.shippingAddrStreetName);
		sb.append("shippingAddrStreetNumber     : "+this.shippingAddrStreetNumber);
		sb.append("shippingAddrStreetType       : "+this.shippingAddrStreetType);
		sb.append("shippingAddrLine2            : "+this.shippingAddrLine2);
		sb.append("shippingAddrLine2Info        : "+this.shippingAddrLine2Info);
		sb.append("shippingAddrCity             : "+this.shippingAddrCity);
		sb.append("shippingAddrStateOrPrivince  : "+this.shippingAddrStateOrPrivince);
		sb.append("shippingAddrPostalCode       : "+this.shippingAddrPostalCode);
		
		return sb.toString();
	}
}
