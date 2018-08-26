package com.A.ui.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 
 * 
 */

@Entity
@Table(name = "SALES_SESSION")
public class SalesSession implements Serializable{

	@Id	
	@Column(name = "SALES_SESSION_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long salesSessionId;

	
	@Column(name = "ORDERID")
	private Long orderId;

	@Column(name = "CUSTOMERID")
	private Long customerId;
	
	@Column(name = "AGENT")
	private String agent;

	@Column(name = "DISPOSITION_ID")
	private Long dispositionId;


	@Column(name = "START_TIME")
	private Calendar startTime;
	
	@Column(name = "NORMAL_CLOSE")
	private String normalClose = "false";


	/**
	 * @return the normalClose
	 */
	public String getNormalClose() {
		return normalClose;
	}

	/**
	 * @param normalClose the normalClose to set
	 */
	public void setNormalClose(String normalClose) {
		this.normalClose = normalClose;
	}


	public Calendar getStartTime() {
		return startTime;
	}




	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}




	public Calendar getEndTime() {
		return endTime;
	}




	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	@Column(name = "END_TIME")
	private Calendar endTime;
 
	@Column(name = "SALES_CALL_ID")
	private Long salesCallId;

	@Column(name = "ID")
	private Long id;


	public static SalesSession create(Long salesSessionId, Long orderId, Long customerId, String agent, Long dispositionId,
			Calendar startTime, Calendar endTime, Long salesCallId, Long id) {

		SalesSession salesSession = new SalesSession();
		salesSession.setSalesSessionId(salesSessionId);
		salesSession.setOrderId(orderId);
		salesSession.setCustomerId(customerId);
		salesSession.setAgent(agent);
		salesSession.setDispositionId(dispositionId);
		salesSession.setStartTime(startTime);
		salesSession.setEndTime(endTime);
		salesSession.setSalesCallId(salesCallId);
		salesSession.setId(id);
		return salesSession;
	}


	

	@Override
	public String toString() {
		
		return "SalesSession [salesSessionId=" + salesSessionId + ", orderId=" + orderId + ", customerId="
				+ customerId + ", agent=" + agent + ", dispositionId=" + dispositionId + ", startTime="
				+ startTime	+", endTime=" + endTime + ", salesCallId="
				+ salesCallId + ", id=" + id+  "]";
	}

	public Long getSalesSessionId() {
		return salesSessionId;
	}

	public void setSalesSessionId(Long salesSessionId) {
		this.salesSessionId = salesSessionId;
	}
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getDispositionId() {
		return dispositionId;
	}

	public void setDispositionId(Long dispositionId) {
		this.dispositionId = dispositionId;
	}
	
	public Long getSalesCallId() {
		return salesCallId;
	}

	public void setSalesCallId(Long salesCallId) {
		this.salesCallId = salesCallId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}
}
