package com.A.V.mo.dialog;

import java.util.HashMap;
import java.util.Map;

public enum DialogMotherObject {

	INSTANCE;
	
	public Map<String, Map<String, String>> getData() {

		Map<String, Map<String, String>> context = new HashMap<String, Map<String, String>>();

		Map<String, String> salesFlow = new HashMap<String, String>();
		salesFlow.put("salesFlow.dialogueType", "Provisioning");

		Map<String, String> orderSource = new HashMap<String, String>();
		orderSource.put("orderSource.channel", "CallCenter");

		Map<String, String> provisioning = new HashMap<String, String>();
		provisioning.put("provisioning.marketItem", "TWC-WCAR-CI");

		context.put("salesFlow", salesFlow);
		context.put("orderSource", orderSource);
		context.put("provisioning", provisioning);
		return context;
	}
}
