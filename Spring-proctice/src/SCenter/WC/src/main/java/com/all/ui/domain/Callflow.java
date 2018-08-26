package com.A.ui.domain;

 


import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author hestacio
 * 
 */

@Entity
@Table(name = "DF_CALLFLOW_WEBFLOW_MAPPING")
public class Callflow implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;

	@Column(name = "REFERRER_ID")
	private Long referrerId;
	
	@Column(name = "REFERRER_NAME")
	private String referrerName;
	
	@Column(name = "REFERRER_TYPE")
	private String referrerType;
	
	@Column(name = "SEGMENT")
	private String segment;
	
	@Column(name = "SUB_SEGMENT")
	private String subSegment;
	
	@Column(name = "REFERRER_DATA")
	private int referrerData;
	
	@Column(name = "WEBFLOW_ID")
	private Long webflowId;
	
	@Column(name = "DIALOG_ID")
	private Long dialogId;
	
	@Column(name = "ORDER_SOURCE")
	private String orderSource;


	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getReferrerId() {
		return referrerId;
	}

	public void setReferrerId(Long referrerId) {
		this.referrerId = referrerId;
	}

	public String getReferrerName() {
		return referrerName;
	}

	public void setReferrerName(String referrerName) {
		this.referrerName = referrerName;
	}
	
	public String getReferrerType() {
		return referrerType;
	}

	public void setReferrerType(String referrerType) {
		this.referrerType = referrerType;
	}	
	
	public String getSegment() {
		return segment;
	}
	
	public void setSegment(String segment) {
		this.segment = segment;
	}
	
	public String getSubSegment() {
		return subSegment;
	}
	
	public void setSubSegment(String subSegment) {
		this.subSegment = subSegment;
	}	
	
	public int getReferrerData() {
		return referrerData;
	}
	
	public void setReferrerData() {
		this.referrerData = referrerData;
	}
	
	public Long getWebflowId() {
		return webflowId;
	}

	public void setWebflowId(Long webflowId) {
		this.webflowId = webflowId;
	}
	
	public Long getDialogId() {
		return dialogId;
	}

	public void setDialogId(Long dialogId) {
		this.dialogId = dialogId;
	}
	
	@Override
	public String toString() {
		return "CallFlowParameter [id=" + id + "]";
	}

}
