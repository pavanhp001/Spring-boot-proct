package com.AL.task.response;

import com.AL.activity.impl.ActivityUpdateLineItemWithNotificationEvent;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.util.SalesContextUtil;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;

public enum ResponseBuilderSubmit {
 
	INSTANCE ;
	
	/**
	 * {@inheritDoc}
	 */
	public OrderManagementRequestResponseDocument processResponse(
			final OrchestrationContext params, ResponseBuilder responseBuilder, ActivityUpdateLineItemWithNotificationEvent activityUpdateLineItemWithNotificationEvent) {

		Boolean isBuildResponseIncludeEventNotification = Boolean.TRUE;
		OrderManagementRequestResponseDocument omrrd = responseBuilder
				.buildResponse(params);
		OrderType order = null;
		Request omeRequest = null;

		if ((omrrd != null)
				&& (omrrd.getOrderManagementRequestResponse() != null)
				&& (omrrd.getOrderManagementRequestResponse().getResponse() != null)
				&& (omrrd.getOrderManagementRequestResponse().getResponse()
						.getOrderInfoList() != null)
				&& (omrrd.getOrderManagementRequestResponse().getResponse()
						.getOrderInfoList().size() > 0)) {
			order = omrrd.getOrderManagementRequestResponse().getResponse()
					.getOrderInfoList().get(0);

			omeRequest = (Request) params.get(TaskContextParamEnum.request.name());

			if ((!params.getValidationReport().hasErrors())
					&& (!params.getValidationReport().hasFatal())) {
				boolean processScheduler = Boolean.FALSE;
				activityUpdateLineItemWithNotificationEvent
						.addProviderCommunicationEvent(omeRequest.getAgentId(),omeRequest, order, isBuildResponseIncludeEventNotification,processScheduler);
			}
		}

		return omrrd;

	}
}
