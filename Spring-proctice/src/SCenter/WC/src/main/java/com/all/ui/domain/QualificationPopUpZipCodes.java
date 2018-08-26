package com.A.ui.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;

import java.util.Date;

@Entity
@Table(name = "qualification_popup_zipcodes")
public class QualificationPopUpZipCodes implements Serializable{
	
	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "zip_code")
	private String zipcode;
	
	@Column(name = "referrers")
	private String referrers;
	
	@Column(name = "provider_name")
	private String providerName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * @param zipcode the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getReferrers() {
		return referrers;
	}

	public void setReferrers(String referrers) {
		this.referrers = referrers;
	}
	
}