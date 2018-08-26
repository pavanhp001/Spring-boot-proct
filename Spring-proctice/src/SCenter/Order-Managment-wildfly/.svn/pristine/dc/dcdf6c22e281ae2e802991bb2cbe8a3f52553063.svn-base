package com.AL.task.util;

import org.apache.log4j.Logger;
import com.AL.customer.OmeCustomerService;
import com.AL.task.TaskBase;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.xml.v4.CustomerInformationDocument.CustomerInformation;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.PayeventRequestList;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;

public class OrchestrationContextUtil {

	private static final Logger logger = Logger
			.getLogger(OrchestrationContextUtil.class);

	public static OrchestrationContext populateContextWithOrderParams(
			final OrderManagementRequestResponseDocument orderDocument) {

		OrchestrationContext params = TaskBase.createLoadContext(orderDocument);

		params.add(TaskContextParamEnum.region.name(), orderDocument
				.getOrderManagementRequestResponse().getRegion());
		params.add(TaskContextParamEnum.salesCode.name(), orderDocument
				.getOrderManagementRequestResponse().getSalesCode());
		params.add(TaskContextParamEnum.affiliateName.name(), orderDocument
				.getOrderManagementRequestResponse().getAffiliateName());
		params.add(TaskContextParamEnum.transactionId.name(), orderDocument
				.getOrderManagementRequestResponse().getTransactionId());
		params.add(TaskContextParamEnum.transactionTimeStamp.name(),
				orderDocument.getOrderManagementRequestResponse()
						.getTransactionTimeStamp());

		// ensure sessionId is present.
		if ((orderDocument.getOrderManagementRequestResponse().getSessionId() == null)
				|| (orderDocument.getOrderManagementRequestResponse()
						.getSessionId().length() == 0)) {
			orderDocument.getOrderManagementRequestResponse().setSessionId(
					String.valueOf(System.currentTimeMillis()));
		}

		PayeventRequestList payeventRequestList = extractPayeventRequest(orderDocument);

		if ((payeventRequestList != null)
				&& (payeventRequestList.getPayeventRequestList() != null)
				&& (payeventRequestList.getPayeventRequestList().size() != 0)) {
			params.add(TaskContextParamEnum.payEventData.name(),
					payeventRequestList);

		}

		params.add(TaskContextParamEnum.sessionId.name(), orderDocument
				.getOrderManagementRequestResponse().getSessionId());
		params.add(TaskContextParamEnum.correlationId.name(), orderDocument
				.getOrderManagementRequestResponse().getCorrelationId());
		return params;
	}

	private static PayeventRequestList extractPayeventRequest(
			final OrderManagementRequestResponseDocument orderDocument) {

		PayeventRequestList payeventRequestList = null;

		if ((orderDocument != null)
				&& (orderDocument.getOrderManagementRequestResponse() != null)
				&& (orderDocument.getOrderManagementRequestResponse()
						.getRequest() != null)
				&& (orderDocument.getOrderManagementRequestResponse()
						.getRequest().getPayeventRequests() != null)) {
			payeventRequestList = orderDocument
					.getOrderManagementRequestResponse().getRequest()
					.getPayeventRequests();

		}

		return payeventRequestList;
	}

	// TODO this method needs to removed once accounttype, paymentstype and
	// customer type has been implemtented
	public static void populateContextWithExtraCustInfo(Request request,
			final OrchestrationContext params) {
		if (request.getOrderInfo() != null
				&& request.getOrderInfo().getCustomerInformation() != null) {
			CustomerInformation custInfoType = request.getOrderInfo()
					.getCustomerInformation();
			if (custInfoType.getCustomer() != null) {
				logger.debug("*********Populating context with client supplied account payments and customer type**********");

				if (custInfoType.getCustomer().getProviderCustomerType() != null) {
					String custType = custInfoType.getCustomer()
							.getProviderCustomerType().toString();
					params.add(TaskContextParamEnum.custType.name(), custType);
				}

			}
		}

	}

	public static void addConsumerToContext(final OrchestrationContext params,
			SalesOrder salesOrder, final OmeCustomerService omeCustomerService) {
		logger.debug("get customer info from db:"
				+ salesOrder.getConsumerExternalId());
		Consumer customer = omeCustomerService.getCustomer(salesOrder
				.getConsumerExternalId());

		if (customer != null) {
			params.add(TaskContextParamEnum.customer.name(), customer);
		}

	}
}
