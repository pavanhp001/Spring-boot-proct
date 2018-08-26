package com.AL.ui.vo;

/**
 * @author kgarimella
 *
 */
public class ReferrerVO {
	
	 /**
	 * Referral Name
	 */
	String name = "";
	
	 /**
	 * Referral ExternalId
	 */
	String externalId = "";
	
	 /**
	 * Unique VDN
	 */
	String vdn = "";
	 
	 /**
	 * @param name
	 * @param externalId
	 * @param vdn
	 */
	public ReferrerVO(String name,String externalId, String vdn){
		 setName(name);
		 setExternalId(externalId);
		 setVdn(vdn);
	 }
	 
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return
	 */
	public String getExternalId() {
		return externalId;
	}
	/**
	 * @param externalId
	 */
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	/**
	 * @return
	 */
	public String getVdn() {
		return vdn;
	}
	/**
	 * @param vdn
	 */
	public void setVdn(String vdn) {
		this.vdn = vdn;
	}

}
