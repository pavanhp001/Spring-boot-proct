package com.AL.bean;

public class WebOrderBean {

	String customerId;
	String orderId;
	String lineItemId;
	String pageType;
	String agentId;
	String page;
	String htmlContent;
	String ucid;
	String guid;
	String providerId;
	String productExtId;
	
	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the lineItemId
	 */
	public String getLineItemId() {
		return lineItemId;
	}
	/**
	 * @param lineItemId the lineItemId to set
	 */
	public void setLineItemId(String lineItemId) {
		this.lineItemId = lineItemId;
	}
	/**
	 * @return the providerId
	 */
	public String getPageType() {
		return pageType;
	}
	/**
	 * @param providerId the providerId to set
	 */
	public void setpageType(String pageType) {
		this.pageType = pageType;
	}
	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}
	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}
	/**
	 * @return the htmlContent
	 */
	public String getHtmlContent() {
		return htmlContent;
	}
	/**
	 * @param htmlContent the htmlContent to set
	 */
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	/**
	 * @return the ucid
	 */
	public String getUcid() {
		return ucid;
	}
	/**
	 * @param ucid the ucid to set
	 */
	public void setUcid(String ucid) {
		this.ucid = ucid;
	}
	
	/**
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}
	
	/**
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
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
	
	/**
	 * @param customerId
	 * @param orderId
	 * @param lineItemId
	 * @param providerId
	 * @param agentId
	 * @param page
	 * @param htmlContent
	 * @param ucid
	 */
	public WebOrderBean(String customerId, String orderId, String lineItemId,
			String pageType, String agentId, String page, String htmlContent,
			String ucid, String guid, String providerId, String productExtId) {
		this.customerId = customerId;
		this.orderId = orderId;
		this.lineItemId = lineItemId;
		this.pageType = pageType;
		this.agentId = agentId;
		this.page = page;
		this.htmlContent = htmlContent;
		this.ucid = ucid;
		this.guid = guid;
		this.providerId = providerId;
		this.productExtId = productExtId;
	}
	
	
	
	
	
}
