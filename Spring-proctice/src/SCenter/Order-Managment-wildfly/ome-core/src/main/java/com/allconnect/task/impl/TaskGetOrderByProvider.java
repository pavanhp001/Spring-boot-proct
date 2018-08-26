package com.AL.task.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.task.LocalGetOrderByProvider;
import com.AL.task.TaskBase;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.ResponseItemOme;
import com.AL.task.response.ResponseBuilder;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.Vdao.dao.CustomerDao;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.vm.util.converter.unmarshall.UnmarshallOrder;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
@Component("getOrderByProvider")
public class TaskGetOrderByProvider extends TaskBase<OrderManagementRequestResponseDocument> implements
		LocalGetOrderByProvider<OrderManagementRequestResponseDocument> {

	private static final Logger logger = Logger
			.getLogger(TaskGetOrderByProvider.class);

	@Autowired
	private UnmarshallOrder unmarshallOrder;

	@Autowired
	private OrderManagementDao orderManagementDao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private ResponseBuilder responseBuilder;

	private static final Long DEFAULT_ORDER_EXTERNAL_ID = -1L;
	private static final Long DEFAULT_PROVIDER_EXTERNAL_ID = -1L;

	@Override
	public OrchestrationContext processRequest(
			OrderManagementRequestResponseDocument orderDocument) {

		logger.info("Processing getOrderByProvider request");
		Request request = orderDocument.getOrderManagementRequestResponse()
				.getRequest();

		OrchestrationContext params = createLoadContext(orderDocument);
		Long orderExtId = getOrderExternalId(request);
		Long providerExtId = getProviderExtId(request);

		logger.info("Requested order ext. id [" + orderExtId + "] and provider ext. id [" + providerExtId +"]");
		try {
			params.add(TaskContextParamEnum.salesOrderId.name(), orderExtId);
			params.add(TaskContextParamEnum.request.name(), request);
			params.add(TaskContextParamEnum.providerExtId.name(), providerExtId);
		} catch (Exception e) {
			logger.error("Exception thrown while processing GetOrderByProvider processRequest : " , e);
			e.printStackTrace();
			return OrchestrationContext.Factory.createOme(e);
		}
		return params;
	}

	@Override
	public OrchestrationContext processAgenda(OrchestrationContext params) {
		Long salesOrderId = (Long) params.get(TaskContextParamEnum.salesOrderId
				.name());
		Long providerExtId =  (Long) params.get(TaskContextParamEnum.providerExtId.name());

		SalesOrder salesOrderBean = orderManagementDao.findOrderByProviderExtId(salesOrderId, providerExtId);

		if (salesOrderBean != null) {
				Consumer consumer = customerDao
						.findCustomerByExternalId(salesOrderBean
								.getConsumerExternalId());
				if (consumer != null) {
					params.add(TaskContextParamEnum.customer.name(), consumer);
				}
			params.addSuccessful(salesOrderBean);
		} else {
			((ResponseItemOme) params).addSalesOrderNotFound(String
					.valueOf(salesOrderId));
		}

		return params;
	}

	@Override
	public OrderManagementRequestResponseDocument processResponse(
			OrchestrationContext params) {
		return responseBuilder.buildResponse(params);
	}

	@Override
	public OrderManagementRequestResponseDocument handleFault(Exception e,
			OrchestrationContext taskResponse,
			OrderManagementRequestResponseDocument orderDocument) {
		logger.debug(e);
		return orderDocument;
	}

	@Override
	public void broadcast(String strToBroadcastOriginal,
			Map<String, String> header) {
		//Gets api do not need to broadcast
	}

	/**
	 * Retrieves order external id from request document
	 * @param request
	 * @return
	 */
	private Long getOrderExternalId(Request request) {
		String orderId = unmarshallOrder.buildOrderId(request);
		Long orderExtId = DEFAULT_ORDER_EXTERNAL_ID;
		if (orderId != null && orderId.trim().length() > 0) {
			orderExtId = Long.valueOf(orderId);
		}

		return orderExtId;
	}

	/**
	 * Retrieves lineitem external id from request document
	 * @param request
	 * @return
	 */
	private Long getProviderExtId(Request request) {
		String sProvExtId = unmarshallOrder.buildProviderExternalId(request);
		Long providerExtId = DEFAULT_PROVIDER_EXTERNAL_ID;
		if (sProvExtId != null && sProvExtId.trim().length() > 0) {
			providerExtId = Long.valueOf(sProvExtId);
		}

		return providerExtId;
	}


}
