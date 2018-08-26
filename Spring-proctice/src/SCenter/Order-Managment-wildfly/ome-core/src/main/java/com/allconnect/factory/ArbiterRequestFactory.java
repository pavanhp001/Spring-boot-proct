package com.AL.factory;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.activity.impl.ActivitySecureFieldsClean;
import com.AL.activity.impl.ActivityUpdateLineItemWithNotificationEvent;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.Vdao.dao.BusinessPartyDao;
import com.AL.Vdao.dao.CustomerDao;
import com.AL.vm.util.converter.marshall.MarshallConsumers;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.xml.v4.CustomerInformationDocument.CustomerInformation;
import com.AL.xml.v4.CustomerType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.TransactionType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.ProcessingMessage;
import com.AL.xml.v4.StatusType;
import com.AL.xml.v4.StatusType.ProcessingMessages;


@Component
public class ArbiterRequestFactory {

	private static final Logger logger = Logger
			.getLogger(ArbiterRequestFactory.class);

	@Autowired
	private MarshallOrder<SalesOrder> marshallOrder;

	@Autowired
	private MarshallConsumers marshallConsumers;

	@Autowired
	private ActivityUpdateLineItemWithNotificationEvent activityUpdateLineItemWithNotificationEvent;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	BusinessPartyDao businessPartyDao;

	public OrderManagementRequestResponseDocument createOrderXMLWithEventNotificationAndCustomer(
			final OrchestrationContext params) {

		SalesOrder salesOrder = (SalesOrder) params
				.get(TaskContextParamEnum.salesOrder.name());

		Request requestFromClient = (Request) params
				.get(TaskContextParamEnum.request.name());

		OrderManagementRequestResponseDocument container = OrderManagementRequestResponseDocument.Factory
				.newInstance();

		logger.debug("building request response that will be sent to RTIM");
		OrderManagementRequestResponse requestResponse = buildRequestResponse(
				container, params);

		logger.debug("adding request that will be sent to RTIM");
		Request request = requestResponse.addNewRequest();

		logger.debug("adding client info, request info, and sales order to request Order.");
		buildOrderType(params, salesOrder, requestFromClient, request);

		logger.debug("ArbiterRequestFactory:createOrderXMLWithEventNotificationAndCustomer: " + container.toString());
		return container;
	}

	private void buildOrderType(final OrchestrationContext params,
			final SalesOrder salesOrder, final Request requestFromClient,
			final Request request) {

		OrderType newOrderInfoResponse = request.addNewOrderInfo();
		Boolean isBuildEventNotificationAllowAll = Boolean.FALSE;
		Request clientRequest = (Request) params.get(TaskContextParamEnum.request
				.name());
		boolean includeAccountHolders = false;
		if(clientRequest != null) {
			includeAccountHolders = clientRequest.getIncludeAccountHolders();
		}
		
		if (salesOrder != null) {
			marshallOrder.build(salesOrder, newOrderInfoResponse, includeAccountHolders);
			Long consumerExtId = salesOrder.getConsumerExternalId();
			if (consumerExtId != null) {
				Consumer consumerBean = customerDao
						.findCustomerByExternalId(consumerExtId);
				if (consumerBean != null) {

					//update main customer with cvv and other secure info
					ActivitySecureFieldsClean.INSTANCE.addCVV(params,
							consumerBean);
					//update lineitem customer with cvv and other secure info
					ActivitySecureFieldsClean.INSTANCE.addCVV(params,
							newOrderInfoResponse);


					CustomerInformation custInfoType = newOrderInfoResponse
							.addNewCustomerInformation();
					CustomerType custType = custInfoType.addNewCustomer();
					// custType = custInfoType.getCustomer();
					marshallConsumers.build(consumerBean, custType);

					populateExtraInfoForCustomer(custType, params);
				}
			}
			request.setOrderId(String.valueOf(salesOrder.getExternalId()));

			boolean processScheduler = Boolean.TRUE;
			activityUpdateLineItemWithNotificationEvent
					.addProviderCommunicationEvent(
							requestFromClient.getAgentId(), requestFromClient,
							newOrderInfoResponse,
							isBuildEventNotificationAllowAll, processScheduler);

		}

	}



	private OrderManagementRequestResponse buildRequestResponse(
			final OrderManagementRequestResponseDocument container,
			final OrchestrationContext params) {

		OrderManagementRequestResponse requestResponse = container
				.addNewOrderManagementRequestResponse();
		// TODO : this code is added to pass status element to RTIM ATT,
		// otherwise it will failed to process it
		StatusType statusType = requestResponse.addNewStatus();
		statusType.setStatusCode(0);
		statusType.setStatusMsg("SUCCESS");
		ProcessingMessages procMsgs = statusType.addNewProcessingMessages();
		ProcessingMessage msg = procMsgs.addNewMessage();
		msg.setText("Process Order");

		requestResponse.setRegion(params.getRegion());
		requestResponse.setAffiliateName(params.getAffiliateName());
		requestResponse.setSalesCode(params.getSalesCode());
		requestResponse.setCorrelationId(params.getCorrelationId());
		requestResponse.setTransactionId(params.getTransactionId());
		requestResponse.setSessionId(params.getSessionId());
		requestResponse.setTransactionTimeStamp(Calendar.getInstance());
		requestResponse.setTransactionType(TransactionType.ORDER_SUBMISSION);

		return requestResponse;
	}

	// TODO this method needs to removed once accounttype, paymentstype and
	// customer type has been implemtented
	private void populateExtraInfoForCustomer(CustomerType custType,
			OrchestrationContext params) {
		//logger.debug("***********Retrieving extra customer info from context and adding it to RTIM request************");

	}
}
