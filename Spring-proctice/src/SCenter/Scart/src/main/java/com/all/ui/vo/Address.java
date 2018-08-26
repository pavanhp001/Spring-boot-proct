package com.AL.ui.vo;

import java.io.Serializable;

public class Address implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String streetNumber;
	private String streetName;
	private String streetType;
	private String prefixDirectional;
	private String line2;
	private String city;
	private String stateOrProvince;
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStateOrProvince() {
		return stateOrProvince;
	}
	public void setStateOrProvince(String stateOrProvince) {
		this.stateOrProvince = stateOrProvince;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	private String postalCode;
	/**
	 * @return the streetNumber
	 */
	public String getStreetNumber() {
		return streetNumber;
	}
	/**
	 * @param streetNumber the streetNumber to set
	 */
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	/**
	 * @return the streetName
	 */
	public String getStreetName() {
		return streetName;
	}
	/**
	 * @param streetName the streetName to set
	 */
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	/**
	 * @return the streetType
	 */
	public String getStreetType() {
		return streetType;
	}
	/**
	 * @param streetType the streetType to set
	 */
	public void setStreetType(String streetType) {
		this.streetType = streetType;
	}
	/**
	 * @return the prefixDirectional
	 */
	public String getPrefixDirectional() {
		return prefixDirectional;
	}
	/**
	 * @param prefixDirectional the prefixDirectional to set
	 */
	public void setPrefixDirectional(String prefixDirectional) {
		this.prefixDirectional = prefixDirectional;
	}
	/**
	 * @return the postfixDirectional
	 */
	public String getPostfixDirectional() {
		return postfixDirectional;
	}
	/**
	 * @param postfixDirectional the postfixDirectional to set
	 */
	public void setPostfixDirectional(String postfixDirectional) {
		this.postfixDirectional = postfixDirectional;
	}
	private String postfixDirectional;

}
