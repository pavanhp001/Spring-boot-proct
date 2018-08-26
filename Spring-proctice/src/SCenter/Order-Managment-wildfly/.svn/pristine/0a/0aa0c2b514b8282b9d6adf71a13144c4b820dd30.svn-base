package com.AL.task.impl;

import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.AL.customer.OmeCustomerService;
import com.AL.task.LocalTaskProcessOrderEvent;
import com.AL.task.TaskBase;
import com.AL.task.broadcast.AddProviderIdToContextForBroadcast;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.response.ResponseBuilder;
import com.AL.util.messaging.Broadcastable;
import com.AL.util.messaging.impl.HttpBroadcastable;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.vm.util.converter.unmarshall.UnmarshallOrder;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;

/**
 * @author ebthomas
 *
 */

@Component("taskProcessOrderEvent")
public class TaskProcessOrderEvent extends
		TaskBase<OrderManagementRequestResponseDocument> implements
		LocalTaskProcessOrderEvent<OrderManagementRequestResponseDocument> {

	private Logger logger = Logger.getLogger(TaskProcessOrderEvent.class);

	@Autowired
	private OrderManagementDao orderManagementDao;

	@Autowired
	private ResponseBuilder responseBuilder;

	@Autowired
	private UnmarshallOrder unmarshallOrder;

	@Autowired
	private OmeCustomerService omeCustomerService;

	/**
	 * {@inheritDoc}
	 */
	public OrchestrationContext processRequest(
			final OrderManagementRequestResponseDocument orderDocument) {
		Request request = orderDocument.getOrderManagementRequestResponse()
				.getRequest();

		logger.debug("add input values to context");
		OrchestrationContext params = createLoadContext(orderDocument);
		params.add(TaskContextParamEnum.request.name(), request);

		Long orderExternalId = getOrderExternalId(request);
		params.add(TaskContextParamEnum.salesOrderId.name(), orderExternalId);

		return params;
	}

	private Long getOrderExternalId(Request request) {
		String orderId = unmarshallOrder.buildOrderId(request);
		Long orderExtId = -1L;
		if (orderId != null && orderId.trim().length() > 0) {
			orderExtId = Long.valueOf(orderId);
		}

		return orderExtId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrchestrationContext processAgenda(final OrchestrationContext params) {

		Long salesOrderId = (Long) params.get(TaskContextParamEnum.salesOrderId
				.name());

		SalesOrder salesOrderBean = orderManagementDao.findById(salesOrderId);

		if (salesOrderBean != null) {

			 params.add(TaskContextParamEnum.salesOrder.name(),salesOrderBean);


			logger.debug("add consumer info to context. order:"
					+ salesOrderBean.getExternalId());
			addConsumerToContext(params, salesOrderBean);

			AddProviderIdToContextForBroadcast.INSTANCE.execute(params,
					salesOrderBean);

		}

		return params;
	}



	public void addConsumerToContext(final OrchestrationContext params,
			SalesOrder salesOrder) {
		logger.debug("get customer info from db:"
				+ salesOrder.getConsumerExternalId());
		Consumer customer = omeCustomerService.getCustomer(salesOrder
				.getConsumerExternalId());

		if (customer != null) {
			params.add(TaskContextParamEnum.customer.name(), customer);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public OrderManagementRequestResponseDocument processResponse(
			final OrchestrationContext params) {

		return responseBuilder.buildResponse(params);
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

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public OrderManagementDao getOrderManagementDao() {
		return orderManagementDao;
	}

	public void setOrderManagementDao(OrderManagementDao orderManagementDao) {
		this.orderManagementDao = orderManagementDao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void broadcast(String strToBroadcastOriginal, Map<String, String> map) {

//		Broadcastable broadcast = HttpBroadcastable.createDefault();
//		broadcast.broadcast(strToBroadcastOriginal, map);
	}

}
