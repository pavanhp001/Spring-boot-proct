package com.AL.ui.util;

public enum FlowType {
	WEBMICRO("Web Micro"), WEBREFRRER("Web Referrer"), WEBCCP("Web CCP"), 
	CONFIRM("Confirm"),NONCONFIRM("Non-Confirm"),AGENTTRANSFER("Agent Transfer");
	private String refferName;
 
	private FlowType(String refferName) {
		this.refferName = refferName;
	}
 
	public String getRefferName() {
		return refferName;
	}
 
}