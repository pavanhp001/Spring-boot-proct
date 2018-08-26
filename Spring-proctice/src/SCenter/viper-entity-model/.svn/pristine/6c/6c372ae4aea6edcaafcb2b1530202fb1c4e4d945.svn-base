package com.A.V.beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.interfaces.CommonBeanInterface;
@Entity
@Table( name = "telephony" )
public class Telephony implements CommonBeanInterface{


    /**
	 * 
	 */
	private static final long serialVersionUID = 2968922772338858434L;

	@Id
    @GeneratedValue( generator = "telephonySequence" )
    @SequenceGenerator( name = "telephonySequence", sequenceName = "TELEPHONY_SEQ" )
	private long id;
	
	private String phoneNumber;
	private String vdn;
	private String language;
	private String callType;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getVdn() {
		return vdn;
	}
	public void setVdn(String vdn) {
		this.vdn = vdn;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = callType;
	}
	
	
	
}
