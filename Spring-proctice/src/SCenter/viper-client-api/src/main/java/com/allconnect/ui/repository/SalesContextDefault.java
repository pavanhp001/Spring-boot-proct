package com.A.ui.repository;

import java.util.HashMap;
import java.util.Map;

import com.A.V.domain.SalesContext;
import com.A.V.factory.SalesContextFactory;

public enum SalesContextDefault {

	INSTANCE;

	public SalesContext create(String dwellingType) {

		SalesContext sc = null;

		Map<String, Map<String, String>> map = createSalesContext(dwellingType);

		sc = SalesContextFactory.INSTANCE.getSalesContext(map);

		return sc;
	}

	public Map<String, Map<String, String>> createSalesContext(
			String dwellingType) {
		Map<String, Map<String, String>> salesContextData = new HashMap<String, Map<String, String>>();

		Map<String, String> context = new HashMap<String, String>();
		context.put("context.mode", "production");
		salesContextData.put("context", context);

		Map<String, String> orderSource = new HashMap<String, String>();
		orderSource.put("orderSource.source", "123");
		orderSource.put("orderSource.referrer", "utility");
		orderSource.put("orderSource.channel", "1");

		salesContextData.put("orderSource", orderSource);

		Map<String, String> consumer = new HashMap<String, String>();
		consumer.put("consumer.creditScore", "650");
		salesContextData.put("consumer", consumer);

		if (dwellingType != null && dwellingType.equalsIgnoreCase("Apartment")) {
			dwellingType = "apartment";
		} else {
			dwellingType = "house";
		}
		Map<String, String> dwelling = new HashMap<String, String>();
		dwelling.put("dwelling.dwellingType", dwellingType);
		salesContextData.put("dwelling", dwelling);

		Map<String, String> salesFlow = new HashMap<String, String>();
		salesFlow.put("salesFlow.dialogueType", "core");
		salesContextData.put("salesFlow", salesFlow);

		Map<String, String> agent = new HashMap<String, String>();
		agent.put("agent.capability", "advanced");
		salesContextData.put("agent", agent);

		return salesContextData;
	}
}
