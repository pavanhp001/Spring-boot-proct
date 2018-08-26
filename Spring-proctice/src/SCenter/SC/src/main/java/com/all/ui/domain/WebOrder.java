package com.AL.ui.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author mnagineni
 * 
 * Hibernate mapping class of SCORE_WEB_ORDER table
 */
@Entity
@Table(name = "SCORE_WEB_ORDER")
public class WebOrder implements Serializable {
	
	private static final long serialVersionUID = 553127845394L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "SAVED_ON")
	private Calendar savedOn;
	
	@Column(name = "CUSTOMER_ID")
	private String customerId;
	
	@Column(name = "ORDER_ID")
	private String orderId;
	
	@Column(name = "LINEITEM_ID")
	private String lineItemId;
	
	@Column(name = "PAGE_TYPE")
	private String pageType;
	
	@Column(name = "AGENT_ID")
	private String agentId;
	
	@Column(name = "PAGE")
	private String page;
	
	@Column(name = "KEY_NAME")
	private String keyName;
	
	@Column(name = "UCID")
	private String ucid;
	
	@Column(name = "PROVIDER_ID")
	private String providerId;
	
	@Column(name = "PRODUCT_EXT_ID")
	private String productExtId;
	
	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProductExtId() {
		return productExtId;
	}

	public void setProductExtId(String productExtId) {
		this.productExtId = productExtId;
	}


	
	/*@Column(name = "GUID")
	private String guid;*/
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Calendar getSavedOn() {
		return savedOn;
	}

	public void setSavedOn(Calendar savedOn) {
		this.savedOn = savedOn;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(String lineItemId) {
		this.lineItemId = lineItemId;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getUcid() {
		return ucid;
	}

	public void setUcid(String ucid) {
		this.ucid = ucid;
	}

	/*public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}*/
	
	
}
