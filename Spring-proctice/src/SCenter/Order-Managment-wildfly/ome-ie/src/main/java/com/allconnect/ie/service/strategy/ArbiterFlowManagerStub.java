package com.AL.ie.service.strategy;

import java.util.UUID;
import com.AL.ie.service.ArbiterFlowManager;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

public class ArbiterFlowManagerStub implements ArbiterFlowManager {

	public static long TIME_OUT = 500000;

	public String getUniqueId() {
		UUID idOne = UUID.randomUUID();

		return idOne.toString();
	}

	public ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> createFlow(
			OrderManagementRequestResponseDocument payload) {
		 throw new IllegalArgumentException("not implemented in stub");
	}

	public ArbiterFlow<String, String> createFlow(String payload) {
		throw new IllegalArgumentException("not implemented in stub");
	}

	public ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> dispatch(
			OrderManagementRequestResponseDocument payload) {

		ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> aFlow = new ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument>(
				payload, TIME_OUT);

		return aFlow;
	}

}
