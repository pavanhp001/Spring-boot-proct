package com.A.ui.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;

import java.util.Date;

@Entity
@Table(name = "cox_zip_codes")
public class CoxZipcodes implements Serializable{

	@Id
	@Column(name = "zip_code")
	private Integer zipcode;

	/**
	 * @return the zipcode
	 */
	public Integer getZipcode() {
		return zipcode;
	}

	/**
	 * @param zipcode the zipcode to set
	 */
	public void setZipcode(Integer zipcode) {
		this.zipcode = zipcode;
	}
	
}