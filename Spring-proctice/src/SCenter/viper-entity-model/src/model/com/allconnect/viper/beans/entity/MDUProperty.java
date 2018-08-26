package com.A.V.beans.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table( name = "mdu_property" )
public class MDUProperty implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9036289441621199813L;

	@Column(name="MDU_PROPERTY_ID")
	private String mduPropertyId;

	@Id
    @GeneratedValue( generator = "mduPropertySequence" )
    @SequenceGenerator( name = "mduPropertySequence", sequenceName = "mdu_property_seq" )
    @Column(name="EXTERNAL_ID")
    private long mduExternalId;

	@Column(name="ORDERSOURCE_EXTERNAL_ID")
	private int orderSourceExternalId;
	
	@Column(name="NAME")
	private String propertyName;
	
	@Column(name = "ADDRESS1")
    private String address1;

	@Column(name = "ADDRESS2")
    private String address2;
	
	@Column(name = "APT")
    private String apartment;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;
    
    @Column(name = "ZIP")
    private String zip;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMduPropertyId() {
		return mduPropertyId;
	}

	public void setMduPropertyId(String mduPropertyId) {
		this.mduPropertyId = mduPropertyId;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getOrderSourceExternalId() {
		return orderSourceExternalId;
	}

	public void setOrderSourceExternalId(int orderSourceExternalId) {
		this.orderSourceExternalId = orderSourceExternalId;
	}

	public void setMduExternalId(long mduExternalId) {
		this.mduExternalId = mduExternalId;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public long getMduExternalId() {
		return mduExternalId;
	}

	public String getZip() {
		return zip;
	}

}
