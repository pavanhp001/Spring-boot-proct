package com.A.ui.domain;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import javax.persistence.GenerationType;

/**
 * @author ethomas
 * 
 */

@Entity
@Table(name = "SALES_CALL")
public class SalesCall implements Serializable {

	@Id
	@Column(name = "SALES_CALL_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long salesCallId;

	@Column(name = "SALES_SESSION_ID")
	private Long salesSessionId;

	@Column(name = "AGENT_PHONEID")
	private String agentPhoneId;

	@Column(name = "AGENT")
	private String agent;

	@Column(name = "CALLED_ADDRESS")
	private String calledAddress;

	@Column(name = "CALLING_ADDRESS")
	private String callingAddress;

	@Column(name = "UEC")
	private Long uec;

	@Column(name = "UCID")
	private String ucid;

	@Column(name = "ALERTING_TIME")
	private Calendar alertingTime;

	@Column(name = "ESTABLISHED_TIME")
	private Calendar establishedTime;

	@Column(name = "DISCONNECT_TIME")
	private Calendar disconnectTime;


	@Override
	public String toString() {
		return "Metric [salesCallId=" + salesCallId + ", salesSessionId=" + salesSessionId + ", agentPhoneId=" + agentPhoneId
		+ ", agent=" + agent + ", calledAddress=" + calledAddress + ", calledAddress="
		+ calledAddress + ", uec=" + uec + ", ucid=" + ucid+ ", alertingTime=" + alertingTime + ", establishedTime=" 
		+ establishedTime+", disconnectTime=" + disconnectTime +"]";
	}

	public long getSalesCallId() {
		return salesCallId;
	}


	public void setSalesCallId(long salesCallId) {
		this.salesCallId = salesCallId;
	}


	public Long getSalesSessionId() {
		return salesSessionId;
	}


	public void setSalesSessionId(Long salesSessionId) {
		this.salesSessionId = salesSessionId;
	}


	public String getAgentPhoneId() {
		return agentPhoneId;
	}


	public void setAgentPhoneId(String agentPhoneId) {
		this.agentPhoneId = agentPhoneId;
	}


	public String getAgent() {
		return agent;
	}


	public void setAgent(String agent) {
		this.agent = agent;
	}


	public String getCalledAddress() {
		return calledAddress;
	}


	public void setCalledAddress(String calledAddress) {
		this.calledAddress = calledAddress;
	}


	public String getCallingAddress() {
		return callingAddress;
	}


	public void setCallingAddress(String callingAddress) {
		this.callingAddress = callingAddress;
	}


	public Long getUec() {
		return uec;
	}


	public void setUec(Long uec) {
		this.uec = uec;
	}


	public String getUcid() {
		return ucid;
	}


	public void setUcid(String ucid) {
		this.ucid = ucid;
	}


	public Calendar getAlertingTime() {
		return alertingTime;
	}


	public void setAlertingTime(Calendar alertingTime) {
		this.alertingTime = alertingTime;
	}


	public Calendar getEstablishedTime() {
		return establishedTime;
	}


	public void setEstablishedTime(Calendar establishedTime) {
		this.establishedTime = establishedTime;
	}


	public Calendar getDisconnectTime() {
		return disconnectTime;
	}


	public void setDisconnectTime(Calendar disconnectTime) {
		this.disconnectTime = disconnectTime;
	}

}
