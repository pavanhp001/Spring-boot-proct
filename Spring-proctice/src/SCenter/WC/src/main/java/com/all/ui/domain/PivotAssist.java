package com.A.ui.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class PivotAssist implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String providerId="";	
	private String providerName="";
	private String pivotAsstProviderId="";
	private String pivotAsstProviderName="";
	private String pivotAsstOtherProviderId="";
	private String pivotAsstOtherProviderName="";
	private String pivotAsstName="";
	
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getPivotAsstProviderId() {
		return pivotAsstProviderId;
	}
	public void setPivotAsstProviderId(String pivotAsstProviderId) {
		this.pivotAsstProviderId = pivotAsstProviderId;
	}
	public String getPivotAsstProviderName() {
		return pivotAsstProviderName;
	}
	public void setPivotAsstProviderName(String pivotAsstProviderName) {
		this.pivotAsstProviderName = pivotAsstProviderName;
	}
	public String getPivotAsstName() {
		return pivotAsstName;
	}
	public void setPivotAsstName(String pivotAsstName) {
		this.pivotAsstName = pivotAsstName;
	}
	public String getPivotAsstOtherProviderId() {
		return pivotAsstOtherProviderId;
	}
	public void setPivotAsstOtherProviderId(String pivotAsstOtherProviderId) {
		this.pivotAsstOtherProviderId = pivotAsstOtherProviderId;
	}
	public String getPivotAsstOtherProviderName() {
		return pivotAsstOtherProviderName;
	}
	public void setPivotAsstOtherProviderName(String pivotAsstOtherProviderName) {
		this.pivotAsstOtherProviderName = pivotAsstOtherProviderName;
	}
	public String getPivotAssistJson() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("providerId", providerId);
			obj.put("providerName", providerName);
			obj.put("pivotAsstProviderId", pivotAsstProviderId);
			obj.put("pivotAsstProviderName", pivotAsstProviderName);
			obj.put("pivotAsstOtherProviderId", pivotAsstOtherProviderId);
			obj.put("pivotAsstOtherProviderName", pivotAsstOtherProviderName);
			obj.put("pivotAsstName", pivotAsstName);
		} catch (JSONException e) {
		}
	return obj.toString();
	}

}
