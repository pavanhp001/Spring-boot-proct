package com.A.V.beans.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.interfaces.CommonBeanInterface;
@Entity
@Table(name = "OM_BROADCAST")
public class Broadcast implements CommonBeanInterface {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7978515602686748993L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "broadcastSequence")
	@SequenceGenerator(name = "broadcastSequence", sequenceName = "OM_BROADCAST_SEQ",allocationSize = 1)
	@Column(name = "id")
	private long id;

	@Column(name = "BROADCAST_TYPE")
	private String broadcastType;

	@Column(name = "MESSAGE_HEADERS")
	private String messageHeaders;

	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;

	@Column(name = "BROADCAST_MESSAGE", columnDefinition = "TEXT")
	private String brodcastMessage;

	@Column(name = "CCP_STATUS")
	private String ccpStatus;

	@Column(name = "DWME_STATUS")
	private String dwmeStatus;

	@Column(name = "DMADAPTER_STATUS")
	private String dmAdapterStatus;

	@Column(name = "GUID")
	private String guid;

	@Column(name = "EXTERNAL_ID")
	private long externalId;

	@Column(name = "BROADCAST_DATE")
	private Calendar broadcastDate;

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public String getBroadcastType() {
		return broadcastType;
	}

	public void setBroadcastType(String broadcastType) {
		this.broadcastType = broadcastType;
	}



	public String getMessageHeaders() {
		return messageHeaders;
	}

	public void setMessageHeaders(String messageHeaders) {
		this.messageHeaders = messageHeaders;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getBrodcastMessage() {
		return brodcastMessage;
	}

	public void setBrodcastMessage(String brodcastMessage) {
		this.brodcastMessage = brodcastMessage;
	}

	public String getCcpStatus() {
		return ccpStatus;
	}

	public void setCcpStatus(String ccpStatus) {
		this.ccpStatus = ccpStatus;
	}

	public String getDwmeStatus() {
		return dwmeStatus;
	}

	public void setDwmeStatus(String dwmeStatus) {
		this.dwmeStatus = dwmeStatus;
	}

	public String getDmAdapterStatus() {
		return dmAdapterStatus;
	}

	public void setDmAdapterStatus(String dmAdapterStatus) {
		this.dmAdapterStatus = dmAdapterStatus;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public long getExternalId() {
		return externalId;
	}

	public void setExternalId(long externalId) {
		this.externalId = externalId;
	}

	public Calendar getBroadcastDate() {
		return broadcastDate;
	}

	public void setBroadcastDate(Calendar broadcastDate) {
		this.broadcastDate = broadcastDate;
	}

	@Override
	public String toString() {
		return "Broadcast [id=" + id + ", broadcastType=" + broadcastType
				+ ", messageHeaders=" + messageHeaders + ", transactionType="
				+ transactionType + ", ccpStatus=" + ccpStatus
				+ ", dwmeStatus=" + dwmeStatus + ", dmAdapterStatus="
				+ dmAdapterStatus + ", guid=" + guid + ", externalId="
				+ externalId + ", broadcastDate=" + broadcastDate + "]";
	}



}
