package com.A.Vdao.dao.impl;

import java.util.Calendar;

public class SearchCriteria {

    private String firstName;
    private String lastName;
    private String lastSSN;
    private String city;
    private String state;
    private String zipCode;
    private String streetAddress;
    private String customerNo;
    private String phoneNo;
    private String ssn;
    private String emailId;
    private String lineItemStatus;
    private String providerId;
    private Calendar scheduledInstallDate;
    private Calendar orderDate;
    private Calendar lineItemCreateDate;
    private String dtPartnerId;
    private Calendar orderStarDate;
    private Calendar orderEndDate;
    private String agentId;
    private String isAccountHolderSearch;
    private int channelType;

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastSSN() {
        return lastSSN;
    }
    public void setLastSSN(String lastSSN) {
        this.lastSSN = lastSSN;
    }
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
	 * @return the providerId
	 */
	public String getProviderId() {
		return providerId;
	}
	/**
	 * @param providerId the providerId to set
	 */
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	/**
	 * @return the scheduledInstallDate
	 */
	public Calendar getScheduledInstallDate() {
		return scheduledInstallDate;
	}
	/**
	 * @param scheduledInstallDate the scheduledInstallDate to set
	 */
	public void setScheduledInstallDate(Calendar scheduledInstallDate) {
		this.scheduledInstallDate = scheduledInstallDate;
	}
	/**
	 * @return the orderDate
	 */
	public Calendar getOrderDate() {
		return orderDate;
	}
	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(Calendar orderDate) {
		this.orderDate = orderDate;
	}
	/**
	 * @return the lineItemCreateDate
	 */
	public Calendar getLineItemCreateDate() {
		return lineItemCreateDate;
	}
	/**
	 * @param lineItemCreateDate the lineItemCreateDate to set
	 */
	public void setLineItemCreateDate(Calendar lineItemCreateDate) {
		this.lineItemCreateDate = lineItemCreateDate;
	}
	public String getDtPartnerId() {
	    return dtPartnerId;
	}
	public void setDtPartnerId(String dtPartnerId) {
	    this.dtPartnerId = dtPartnerId;
	}
	public Calendar getOrderStarDate() {
	    return orderStarDate;
	}
	public void setOrderStarDate(Calendar orderStarDate) {
	    this.orderStarDate = orderStarDate;
	}
	public Calendar getOrderEndDate() {
	    return orderEndDate;
	}
	public void setOrderEndDate(Calendar orderEndDate) {
	    this.orderEndDate = orderEndDate;
	}
	public String getAgentId() {
	    return agentId;
	}
	public void setAgentId(String agentId) {
	    this.agentId = agentId;
	}
	
	public int getChannelType() {
		return channelType;
	}
	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}
	
	/**
	 * @return the isAccountHolderSearch
	 */
	public String getIsAccountHolderSearch() {
		return isAccountHolderSearch;
	}
	/**
	 * @param isAccountHolderSearch the isAccountHolderSearch to set
	 */
	public void setIsAccountHolderSearch(String isAccountHolderSearch) {
		this.isAccountHolderSearch = isAccountHolderSearch;
	}
	@Override
	public String toString() {
		return "SearchCriteria [firstName=" + firstName + ", lastName=" + lastName + ", lastSSN=" + lastSSN + ", city="
				+ city + ", state=" + state + ", zipCode=" + zipCode + ", streetAddress=" + streetAddress
				+ ", customerNo=" + customerNo + ", phoneNo=" + phoneNo + ", ssn=" + ssn + ", emailId=" + emailId
				+ ", lineItemStatus=" + lineItemStatus + ", providerId=" + providerId + ", scheduledInstallDate="
				+ scheduledInstallDate + ", orderDate=" + orderDate + ", lineItemCreateDate=" + lineItemCreateDate
				+ ", dtPartnerId=" + dtPartnerId + ", orderStarDate=" + orderStarDate + ", orderEndDate="
				+ orderEndDate + ", agentId=" + agentId + ", isAccountHolderSearch=" + isAccountHolderSearch + channelType + "]";
	}
}
