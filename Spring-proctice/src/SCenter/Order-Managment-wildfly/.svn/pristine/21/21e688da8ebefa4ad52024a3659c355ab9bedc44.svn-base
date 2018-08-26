package com.AL.activity.impl;

import org.apache.log4j.Logger;

import com.AL.factory.ArbiterRequestFactory;
import com.AL.ie.service.strategy.ArbiterFlow;
import com.AL.ie.service.strategy.ArbiterFlowManagerDefault;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.validate.ValidateSubmit;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

public enum ActivityDispatchOrderToExtProvider {

	INSTANCE;

	private static final Logger logger = Logger
			.getLogger(ActivityDispatchOrderToExtProvider.class);

	final boolean ENABLE_RTS_INTEGRATION = Boolean.TRUE;

	public void dispatchOrderToExternalProvider(
			ArbiterFlowManagerDefault arbiter,
			ArbiterRequestFactory arbiterRequestFactory,
			final OrchestrationContext params) {

		final SalesOrder salesOrder = (SalesOrder) params
				.get(TaskContextParamEnum.salesOrder.name());

		ValidateSubmit.INSTANCE.validateCurrentStatusIsSubmitted(params,
				salesOrder);

		logger.info("generating XML as transport payload.  sales order:"
				+ salesOrder.getExternalId());

		final OrderManagementRequestResponseDocument payload = arbiterRequestFactory
				.createOrderXMLWithEventNotificationAndCustomer(params);
		processRealTimeRequest(params, arbiter, payload, salesOrder);

	}

	public void processRealTimeRequest(final OrchestrationContext params,
			ArbiterFlowManagerDefault arbiter,
			OrderManagementRequestResponseDocument payload,
			final SalesOrder salesOrder) {

		ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> flow = null;

		if (ENABLE_RTS_INTEGRATION) {
			logger.debug("dispatching sales order to external provider system :"
					+ salesOrder.getId());

			//logger.info("arbiter.dispatch-->"+payload.xmlText());
			flow = arbiter.dispatch(payload);
			logger.info("external provider system dispatch completed.  sales order:"
					+ salesOrder.getId());
		}

		if (flow != null) {
			params.add(TaskContextParamEnum.arbiterFlow.name(), flow);

		}
	}
}
