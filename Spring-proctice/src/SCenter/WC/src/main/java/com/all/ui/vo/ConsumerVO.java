/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.A.ui.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 *
 * @author Rajasekhar.N
 */
public class ConsumerVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer dtSequenceNum;
	private String dtPartnerAccountId;
	private String dtPartner;
	private String dtNamePrefix;
	private String dtNameFirst;
	private String dtNameMiddle;
	private String dtNameLast;
	private String dtNameSuffix;
	private String dtConfirmedEmailAddress;
	private String dtConsumerDlnum;
	private String dtConsumerSsn;
	private String dtSpouseRoommateName;
	private String dtSpouseRoommateSsn;
	private String dtAgentId;
	private String dtEmail;
	private Date dtCreated;
	private String dtPremiseNumber;
	private String dtRequestedStartDate;
	private String dtGasRequestedStartDate;
	private String dtSearchId;
	private String dtReqStartTimeBegin;
	private String dtReqStartTimeEnd;
	private String dtGasReqStartTimeBegin;
	private String dtGasReqStartTimeEnd;
	private String externalId;
	private Integer customerMatch;
	private String dtSaCity;
	private String dtSaState;
	private String dtSaZip;
	private String dtSaStreet1;
	private String dtSaStreet2;
	private String dtTelephoneNum;

	/**
	 * @return the dtTelephoneNum
	 */
	public String getDtTelephoneNum() {
		return dtTelephoneNum;
	}

	/**
	 * @param dtTelephoneNum the dtTelephoneNum to set
	 */
	public void setDtTelephoneNum(String dtTelephoneNum) {
		this.dtTelephoneNum = dtTelephoneNum;
	}
	private String dtExt;

	private String dataName;

	private String dataValue;

	private Map<String, String> partnerSpecificDataMap = new HashMap<String, String>();

	private List<DtAddr> dt_address = new ArrayList<DtAddr>();

	/**
	 * @return the dt_address
	 */
	public List<DtAddr> getDt_address() {
		return dt_address;
	}

	/**
	 * @param dtAddress the dt_address to set
	 */
	public void setDt_address(List<DtAddr> dtAddress) {
		dt_address = dtAddress;
	}

	private List<DtPhoneType> dt_phone =  new ArrayList<DtPhoneType>();


	public Integer getCustomerMatch() {
		return customerMatch;
	}

	public void setCustomerMatch(Integer customerMatch) {
		this.customerMatch = customerMatch;
	}


	public Integer getDtSequenceNum() {
		return dtSequenceNum;
	}

	public void setDtSequenceNum(Integer dtSequenceNum) {
		this.dtSequenceNum = dtSequenceNum;
	}

	public String getDtPartnerAccountId() {
		return dtPartnerAccountId;
	}

	public void setDtPartnerAccountId(String dtPartnerAccountId) {
		this.dtPartnerAccountId = dtPartnerAccountId;
	}

	public String getDtPartner() {
		return dtPartner;
	}

	public void setDtPartner(String dtPartner) {
		this.dtPartner = dtPartner;
	}

	public String getDtNamePrefix() {
		return dtNamePrefix;
	}

	public void setDtNamePrefix(String dtNamePrefix) {
		this.dtNamePrefix = dtNamePrefix;
	}

	public String getDtNameFirst() {
		return dtNameFirst;
	}

	public void setDtNameFirst(String dtNameFirst) {
		this.dtNameFirst = dtNameFirst;
	}

	public String getDtNameMiddle() {
		return dtNameMiddle;
	}

	public void setDtNameMiddle(String dtNameMiddle) {
		this.dtNameMiddle = dtNameMiddle;
	}

	public String getDtNameLast() {
		return dtNameLast;
	}

	public void setDtNameLast(String dtNameLast) {
		this.dtNameLast = dtNameLast;
	}

	public String getDtNameSuffix() {
		return dtNameSuffix;
	}

	public void setDtNameSuffix(String dtNameSuffix) {
		this.dtNameSuffix = dtNameSuffix;
	}

	public String getDtConfirmedEmailAddress() {
		return dtConfirmedEmailAddress;
	}

	public void setDtConfirmedEmailAddress(String dtConfirmedEmailAddress) {
		this.dtConfirmedEmailAddress = dtConfirmedEmailAddress;
	}

	public String getDtConsumerDlnum() {
		return dtConsumerDlnum;
	}

	public void setDtConsumerDlnum(String dtConsumerDlnum) {
		this.dtConsumerDlnum = dtConsumerDlnum;
	}

	public String getDtConsumerSsn() {
		return dtConsumerSsn;
	}

	public void setDtConsumerSsn(String dtConsumerSsn) {
		this.dtConsumerSsn = dtConsumerSsn;
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

	public String getDtAgentId() {
		return dtAgentId;
	}

	public void setDtAgentId(String dtAgentId) {
		this.dtAgentId = dtAgentId;
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

	public String getDtRequestedStartDate() {
		return dtRequestedStartDate;
	}

	public void setDtRequestedStartDate(String dtRequestedStartDate) {
		this.dtRequestedStartDate = dtRequestedStartDate;
	}

	public String getDtGasRequestedStartDate() {
		return dtGasRequestedStartDate;
	}

	public void setDtGasRequestedStartDate(String dtGasRequestedStartDate) {
		this.dtGasRequestedStartDate = dtGasRequestedStartDate;
	}

	public String getDtSearchId() {
		return dtSearchId;
	}

	public void setDtSearchId(String dtSearchId) {
		this.dtSearchId = dtSearchId;
	}

	public String getDtReqStartTimeBegin() {
		return dtReqStartTimeBegin;
	}

	public void setDtReqStartTimeBegin(String dtReqStartTimeBegin) {
		this.dtReqStartTimeBegin = dtReqStartTimeBegin;
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

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Date getDtCreated() {
		return dtCreated;
	}

	public void setDtCreated(Date dtCreated) {
		this.dtCreated = dtCreated;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}


	/**
	 * @return the dtExt
	 */
	public String getDtExt() {
		return dtExt;
	}


	/**
	 * @param dtExt the dtExt to set
	 */
	public void setDtExt(String dtExt) {
		this.dtExt = dtExt;
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

	public Map<String, String> getPartnerSpecificDataMap() {
		return partnerSpecificDataMap;
	}

	public void setPartnerSpecificDataMap(Map<String, String> partnerSpecificDataMap) {
		this.partnerSpecificDataMap = partnerSpecificDataMap;
	}

	/**
	 * @return the dt_phone
	 */
	public List<DtPhoneType> getDt_phone() {
		return dt_phone;
	}

	/**
	 * @param dtPhone the dt_phone to set
	 */
	public void setDt_phone(List<DtPhoneType> dtPhone) {
		dt_phone = dtPhone;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (dtSequenceNum != null ? dtSequenceNum.hashCode() : 0);
		return hash;
	}



	public static class DtPhoneType
	{
		private String dtTelephoneNum;

		private String dtTelephoneType;

		/**
		 * @return the dtTelephoneNum
		 */
		public String getDtTelephoneNum() {
			return dtTelephoneNum;
		}

		/**
		 * @param dtTelephoneNum the dtTelephoneNum to set
		 */
		public void setDtTelephoneNum(String dtTelephoneNum) {
			this.dtTelephoneNum = dtTelephoneNum;
		}

		/**
		 * @return the dtTelephoneType
		 */
		public String getDtTelephoneType() {
			return dtTelephoneType;
		}

		/**
		 * @param dtTelephoneType the dtTelephoneType to set
		 */
		public void setDtTelephoneType(String dtTelephoneType) {
			this.dtTelephoneType = dtTelephoneType;
		}
	}

	public static class DtAddr
	{
		private String dtAddressStreet1;

		private String dtAddressStreet2;

		private String dtAddressCity;

		private String dtAddressState;

		private String dtAddressZip;

		private String dtAddressType;

		/**
		 * @return the dtAddressType
		 */
		public String getDtAddressType() {
			return dtAddressType;
		}

		/**
		 * @param dtAddressType the dtAddressType to set
		 */
		public void setDtAddressType(String dtAddressType) {
			this.dtAddressType = dtAddressType;
		}


		/**
		 * @return the dtAddressStreet1
		 */
		public String getDtAddressStreet1() {
			return dtAddressStreet1;
		}

		/**
		 * @param dtAddressStreet1 the dtAddressStreet1 to set
		 */
		public void setDtAddressStreet1(String dtAddressStreet1) {
			this.dtAddressStreet1 = dtAddressStreet1;
		}

		/**
		 * @return the dtAddressStreet2
		 */
		public String getDtAddressStreet2() {
			return dtAddressStreet2;
		}

		/**
		 * @param dtAddressStreet2 the dtAddressStreet2 to set
		 */
		public void setDtAddressStreet2(String dtAddressStreet2) {
			this.dtAddressStreet2 = dtAddressStreet2;
		}

		/**
		 * @return the dtAddressCity
		 */
		public String getDtAddressCity() {
			return dtAddressCity;
		}

		/**
		 * @param dtAddressCity the dtAddressCity to set
		 */
		public void setDtAddressCity(String dtAddressCity) {
			this.dtAddressCity = dtAddressCity;
		}

		/**
		 * @return the dtAddressState
		 */
		public String getDtAddressState() {
			return dtAddressState;
		}

		/**
		 * @param dtAddressState the dtAddressState to set
		 */
		public void setDtAddressState(String dtAddressState) {
			this.dtAddressState = dtAddressState;
		}

		/**
		 * @return the dtAddressZip
		 */
		public String getDtAddressZip() {
			return dtAddressZip;
		}

		/**
		 * @param dtAddressZip the dtAddressZip to set
		 */
		public void setDtAddressZip(String dtAddressZip) {
			this.dtAddressZip = dtAddressZip;
		}

	}

}