package com.AL.ie.task.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.ie.activity.ActivityBuildProviderResponse;
import com.AL.ie.activity.impl.ActivityArbiterResponse;
import com.AL.ie.service.strategy.ArbiterFlow;
import com.AL.ie.service.strategy.ArbiterFlowManagerDefault;
import com.AL.ie.task.IeTaskBase;
import com.AL.ie.task.LocalTaskOrderSubmission;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
 
 

/**
 * @author ebthomas
 * 
 */

@Component("taskOrderSubmission")
public class TaskOrderSubmission extends
		IeTaskBase<OrderManagementRequestResponseDocument> implements
		LocalTaskOrderSubmission<OrderManagementRequestResponseDocument> {

	private Logger logger = Logger.getLogger(TaskOrderSubmission.class);

	@Autowired
	ArbiterFlowManagerDefault arbiter;

	@Autowired
	ActivityArbiterResponse arbiterResponse;

	/**
	 * {@inheritDoc}
	 */
	public OrchestrationContext processRequest(
			final OrderManagementRequestResponseDocument orderDocument) {

		OrchestrationContext params =  createLoadContext(orderDocument);
		params.addResponseItem("request", orderDocument);

		return params;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrchestrationContext processAgenda(final OrchestrationContext params) {
		final OrderManagementRequestResponseDocument orderDocument = (OrderManagementRequestResponseDocument) params
				.get("request");
		ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> flow = arbiter
				.dispatch(orderDocument);

		params.add(TaskContextParamEnum.arbiterFlow.name(), flow);

		arbiterResponse.process(params);
		return params;
	}

	/**
	 * {@inheritDoc}
	 */
	public OrderManagementRequestResponseDocument processResponse(
			final OrchestrationContext params) {

		ActivityBuildProviderResponse activity = new ActivityBuildProviderResponse();
		activity.process(params);

		OrderManagementRequestResponseDocument doc =   activity.getDoc();

		return doc;

	}

	/**
	 * {@inheritDoc}
	 */
	public OrderManagementRequestResponseDocument handleFault(
			final Exception e, final OrchestrationContext params,
			final OrderManagementRequestResponseDocument orderDocument) {

		logger.debug(e.getMessage());

		return orderDocument;

	}

	/**
	 * {@inheritDoc}
	 */
	public void broadcast(final String objectToBroadcast) {
		logger.debug("broadcast override:Order Submission");
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

}
