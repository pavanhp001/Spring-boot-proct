package com.AL.ui.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
 


@XStreamAlias("CKO")
public class FeedbackVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6587302314454837175L;
	private String customerId;
	private String orderId;
	private String status;
	private String securityKey;
	private Map<String, String> feedbackMap;

	@XStreamImplicit(itemFieldName = "lineitem")
	private List<Long> lineItems = new ArrayList<Long>();

	@XStreamImplicit(itemFieldName = "param")
	private List<String> params = new ArrayList<String>();

	public Long getFirstLineItemId() {

		Long lid = 0L;

		if (lineItems.size() > 0) {
			lid = lineItems.get(0);
		}

		return lid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Long> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<Long> lineItems) {
		this.lineItems = lineItems;
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSecurityKey() {
		return securityKey;
	}

	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}
	
	

	/**
	 * @return the feedbackMap
	 */
	public Map<String, String> getFeedbackMap() {
		return feedbackMap;
	}

	/**
	 * @param feedbackMap the feedbackMap to set
	 */
	public void setFeedbackMap(Map<String, String> feedbackMap) {
		this.feedbackMap = feedbackMap;
	}

}