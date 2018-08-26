package com.AL.ie.service;

import com.AL.ie.service.strategy.ArbiterFlow;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

public interface ArbiterFlowManager {

	public ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> createFlow(
			final OrderManagementRequestResponseDocument payload);

	public ArbiterFlow<String, String> createFlow(final String payload);

	public ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> dispatch(
			final OrderManagementRequestResponseDocument payload);

	public String getUniqueId();

}
