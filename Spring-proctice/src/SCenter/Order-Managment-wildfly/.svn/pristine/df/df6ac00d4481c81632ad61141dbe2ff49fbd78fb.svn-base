package com.AL.task.impl;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.AL.task.LocalTaskGetOrderByConfirmationNumber;
import com.AL.task.TaskBase;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.ResponseItemOme;
import com.AL.task.response.ResponseBuilder;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.Vdao.dao.CustomerDao;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.vm.util.converter.unmarshall.UnmarshallOrder;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
 * @author ebthomas
 * 
 */

@Component("taskGetOrderByConfirmationNumber")
public class TaskGetOrderByConfirmationNumber extends
		TaskBase<OrderManagementRequestResponseDocument>
		implements
		LocalTaskGetOrderByConfirmationNumber<OrderManagementRequestResponseDocument> {

	@Autowired
	private UnmarshallOrder unmarshallOrder;

	@Autowired
	private OrderManagementDao orderManagementDao;

	@Autowired
	private MarshallOrder<SalesOrder> marshallOrder;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private ResponseBuilder responseBuilder;

	/**
	 * {@inheritDoc}
	 */
	public OrchestrationContext processRequest(
			final OrderManagementRequestResponseDocument orderDocument) {
		Request request = orderDocument.getOrderManagementRequestResponse()
				.getRequest();
		 
		String confirmationId = unmarshallOrder.buildConfirmationId(request);

		OrchestrationContext params = createLoadContext(orderDocument);

		try {
			params.add(TaskContextParamEnum.confirmationNumber.name(),
					confirmationId);
			params.add(TaskContextParamEnum.request.name(), request);
			//params.add(TaskContextParamEnum.includeCustomerDetails.name(),request.getIncludeCustomerDetailsInResponse());
		} catch (Exception e) {
			return OrchestrationContext.Factory.createOme(e);
		}

		return params;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrchestrationContext processAgenda(final OrchestrationContext params) {
		String confirmationNumber = (String) params
				.get(TaskContextParamEnum.confirmationNumber.name());

		//Boolean includeCustDetails = (Boolean) params.get(TaskContextParamEnum.includeCustomerDetails.name());

		SalesOrder salesOrderBean = orderManagementDao
				.findByConfirmationNumber(confirmationNumber);

		if (salesOrderBean != null) {
			//if (includeCustDetails) {
				Consumer consumer = customerDao
						.findCustomerByExternalId(salesOrderBean
								.getConsumerExternalId());
				if (consumer != null) {
					params.add(TaskContextParamEnum.customer.name(), consumer);
				}
			//}
			params.addSuccessful(salesOrderBean);
		} else {
			((ResponseItemOme) params)
					.addSalesOrderNotFound(confirmationNumber);
		}

		return params;
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

		return orderDocument;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void broadcast(String strToBroadcastOriginal, Map<String, String> map) {

		//Broadcast is not required fro all the gets
		//Broadcastable broadcast = HttpBroadcastable.createDefault();
		//broadcast.broadcast(strToBroadcastOriginal,map);
	}

	public UnmarshallOrder getUnmarshallOrder() {
		return unmarshallOrder;
	}

	public void setUnmarshallOrder(UnmarshallOrder unmarshallOrder) {
		this.unmarshallOrder = unmarshallOrder;
	}

	public OrderManagementDao getOrderManagementDao() {
		return orderManagementDao;
	}

	public void setOrderManagementDao(OrderManagementDao orderManagementDao) {
		this.orderManagementDao = orderManagementDao;
	}

	public MarshallOrder<SalesOrder> getMarshallOrder() {
		return marshallOrder;
	}

	public void setMarshallOrder(MarshallOrder<SalesOrder> marshallOrder) {
		this.marshallOrder = marshallOrder;
	}

}
