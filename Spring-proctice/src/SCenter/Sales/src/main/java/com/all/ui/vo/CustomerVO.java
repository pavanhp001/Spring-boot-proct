package com.AL.ui.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CustomerVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sequenceID="";	
	private String customerMatch="";
	private String dtAgentId="";
	private String dtConsumerSsn="";
	private String dtCreated="";
	private String dtNameFirst="";
	private String dtNameLast="";    
	private String dtPartner="";
	private String dtPartnerAccountId="";	
	private String dtSaStreet1="";
	private String dtSaStreet2="";
	private String state="";
	private String zipCode="";
	private String city="";	
	private String externalId="";
	private String dtConfirmedEmailAddress="";
	private String dtReqStartTimeBegin="";
	private String dtRequestedStartDate="";
	private String dtSequenceNum="";
	private String dtSaState="";
	private String dtSaZip="";
	private String dtSaCity="";
	private String dtNameMiddle;
	private String dtNameSuffix;
	private String dtConsumerDlnum;
	private String dtSpouseRoommateName;
	private String dtSpouseRoommateSsn;
	private String dtEmail;
	private String dtPremiseNumber;
	private String dtGasRequestedStartDate;
	private Integer dtSearchId;
	private String dtReqStartTimeEnd;
	private String dtGasReqStartTimeBegin;
	private String dtGasReqStartTimeEnd;
	private String dtTelephoneNum;
	private String dtNamePrefix;
	
	public String getDtNamePrefix() {
		return dtNamePrefix;
	}

	public void setDtNamePrefix(String dtNamePrefix) {
		this.dtNamePrefix = dtNamePrefix;
	}
	private Map<String, String> partnerSpecificDataMap = new HashMap<String, String>();


	public Map<String, String> getPartnerSpecificDataMap() {
		return partnerSpecificDataMap;
	}

	public void setPartnerSpecificDataMap(Map<String, String> partnerSpecificDataMap) {
		this.partnerSpecificDataMap = partnerSpecificDataMap;
	}
	
	public String getDtTelephoneNum() {
		return dtTelephoneNum;
	}
	public void setDtTelephoneNum(String dtTelephoneNum) {
		this.dtTelephoneNum = dtTelephoneNum;
	}
	public String getDtSaCity() {
		return dtSaCity;
	}
	public void setDtSaCity(String dtSaCity) {
		this.dtSaCity = dtSaCity;
	}
	public String getDtSaState() {
		return dtSaState;
	}
	public void setDtSaState(String dtSaState) {
		this.dtSaState = dtSaState;
	}
	public String getDtSaZip() {
		return dtSaZip;
	}
	public void setDtSaZip(String dtSaZip) {
		this.dtSaZip = dtSaZip;
	}
	public String getDtSequenceNum() {
		return dtSequenceNum;
	}
	public void setDtSequenceNum(String dtSequenceNum) {
		this.dtSequenceNum = dtSequenceNum;
	}
	public String getDtReqStartTimeBegin() {
		return dtReqStartTimeBegin;
	}
	public void setDtReqStartTimeBegin(String dtReqStartTimeBegin) {
		this.dtReqStartTimeBegin = dtReqStartTimeBegin;
	}
	public String getDtRequestedStartDate() {
		return dtRequestedStartDate;
	}
	public void setDtRequestedStartDate(String dtRequestedStartDate) {
		this.dtRequestedStartDate = dtRequestedStartDate;
	}
	public String getDtConfirmedEmailAddress() {
		return dtConfirmedEmailAddress;
	}
	public void setDtConfirmedEmailAddress(String dtConfirmedEmailAddress) {
		this.dtConfirmedEmailAddress = dtConfirmedEmailAddress;
	}
	public String getDtSaStreet1() {
		return dtSaStreet1;
	}
	public void setDtSaStreet1(String dtSaStreet1) {
		this.dtSaStreet1 = dtSaStreet1;
	}
	public String getDtSaStreet2() {
		return dtSaStreet2;
	}
	public void setDtSaStreet2(String dtSaStreet2) {
		this.dtSaStreet2 = dtSaStreet2;
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSequenceID() {
		return sequenceID;
	}
	public void setSequenceID(String sequenceID) {
		this.sequenceID = sequenceID;
	}

	public String getCustomerMatch() {
		return customerMatch;
	}
	public void setCustomerMatch(String customerMatch) {
		this.customerMatch = customerMatch;
	}
	public String getDtAgentId() {
		return dtAgentId;
	}
	public void setDtAgentId(String dtAgentId) {
		this.dtAgentId = dtAgentId;
	}
	public String getDtConsumerSsn() {
		return dtConsumerSsn;
	}
	public void setDtConsumerSsn(String dtConsumerSsn) {
		this.dtConsumerSsn = dtConsumerSsn;
	}
	public String getDtCreated() {
		return dtCreated;
	}
	public void setDtCreated(String dtCreated) {
		this.dtCreated = dtCreated;
	}
	public String getDtNameFirst() {
		return dtNameFirst;
	}
	public void setDtNameFirst(String dtNameFirst) {
		this.dtNameFirst = dtNameFirst;
	}
	public String getDtNameLast() {
		return dtNameLast;
	}
	public void setDtNameLast(String dtNameLast) {
		this.dtNameLast = dtNameLast;
	}
	public String getDtPartner() {
		return dtPartner;
	}
	public void setDtPartner(String dtPartner) {
		this.dtPartner = dtPartner;
	}
	public String getDtPartnerAccountId() {
		return dtPartnerAccountId;
	}
	public void setDtPartnerAccountId(String dtPartnerAccountId) {
		this.dtPartnerAccountId = dtPartnerAccountId;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getDtNameMiddle() {
		return dtNameMiddle;
	}
	public void setDtNameMiddle(String dtNameMiddle) {
		this.dtNameMiddle = dtNameMiddle;
	}
	public String getDtNameSuffix() {
		return dtNameSuffix;
	}
	public void setDtNameSuffix(String dtNameSuffix) {
		this.dtNameSuffix = dtNameSuffix;
	}
	public String getDtConsumerDlnum() {
		return dtConsumerDlnum;
	}
	public void setDtConsumerDlnum(String dtConsumerDlnum) {
		this.dtConsumerDlnum = dtConsumerDlnum;
	}
	public String getDtSpouseRoommateName() {
		return dtSpouseRoommateName;
	}
	public void setDtSpouseRoommateName(String dtSpouseRoommateName) {
		this.dtSpouseRoommateName = dtSpouseRoommateName;
	}
	public String getDtSpouseRoommateSsn() {
		return dtSpouseRoommateSsn;
	}
	public void setDtSpouseRoommateSsn(String dtSpouseRoommateSsn) {
		this.dtSpouseRoommateSsn = dtSpouseRoommateSsn;
	}
	public String getDtEmail() {
		return dtEmail;
	}
	public void setDtEmail(String dtEmail) {
		this.dtEmail = dtEmail;
	}
	public String getDtPremiseNumber() {
		return dtPremiseNumber;
	}
	public void setDtPremiseNumber(String dtPremiseNumber) {
		this.dtPremiseNumber = dtPremiseNumber;
	}
	public String getDtGasRequestedStartDate() {
		return dtGasRequestedStartDate;
	}
	public void setDtGasRequestedStartDate(String dtGasRequestedStartDate) {
		this.dtGasRequestedStartDate = dtGasRequestedStartDate;
	}
	public Integer getDtSearchId() {
		return dtSearchId;
	}
	public void setDtSearchId(Integer dtSearchId) {
		this.dtSearchId = dtSearchId;
	}
	public String getDtReqStartTimeEnd() {
		return dtReqStartTimeEnd;
	}
	public void setDtReqStartTimeEnd(String dtReqStartTimeEnd) {
		this.dtReqStartTimeEnd = dtReqStartTimeEnd;
	}
	public String getDtGasReqStartTimeBegin() {
		return dtGasReqStartTimeBegin;
	}
	public void setDtGasReqStartTimeBegin(String dtGasReqStartTimeBegin) {
		this.dtGasReqStartTimeBegin = dtGasReqStartTimeBegin;
	}
	public String getDtGasReqStartTimeEnd() {
		return dtGasReqStartTimeEnd;
	}
	public void setDtGasReqStartTimeEnd(String dtGasReqStartTimeEnd) {
		this.dtGasReqStartTimeEnd = dtGasReqStartTimeEnd;
	}
	@Override
	public String toString() {
		return "CustomerVO [city=" + city + ", customerMatch=" + customerMatch
				+ ", dtAgentId=" + dtAgentId + ", dtConfirmedEmailAddress="
				+ dtConfirmedEmailAddress + ", dtConsumerSsn=" + dtConsumerSsn
				+ ", dtCreated=" + dtCreated + ", dtNameFirst=" + dtNameFirst
				+ ", dtNameLast=" + dtNameLast + ", dtPartner=" + dtPartner
				+ ", dtPartnerAccountId=" + dtPartnerAccountId
				+ ", dtReqStartTimeBegin=" + dtReqStartTimeBegin
				+ ", dtRequestedStartDate=" + dtRequestedStartDate
				+ ", dtSaStreet1=" + dtSaStreet1 + ", dtSaStreet2="
				+ dtSaStreet2 + ", externalId=" + externalId + ", sequenceID="
				+ sequenceID + ", state=" + state + ", zipCode=" + zipCode+ ", dtSequenceNum="
				+ dtSequenceNum + ", dtSaState=" + dtSaState + ", dtSaZip=" + dtSaZip
				+ ", dtSaCity=" + dtSaCity
				+ ", dtEmail=" + dtEmail
				+ "]";
	}
	
	
	



}
