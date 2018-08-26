package com.AL.task.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.task.LocalTaskGetOrder;
import com.AL.task.TaskBase;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.ResponseItemOme;
import com.AL.task.response.ResponseBuilder;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.Vdao.dao.CustomerDao;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.Vdao.util.SystemPropertiesRepo;
import com.AL.vm.util.converter.marshall.MarshallConsumers;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.vm.util.converter.unmarshall.UnmarshallOrder;
import com.AL.xml.v4.CustomerInformationDocument.CustomerInformation;
import com.AL.xml.v4.CustomerType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderType;

/**
 * @author ebthomas
 *
 */

@Component("taskGetOrder")
public class TaskGetOrder extends
		TaskBase<OrderManagementRequestResponseDocument> implements
		LocalTaskGetOrder<OrderManagementRequestResponseDocument> {

	private Logger logger = Logger.getLogger(TaskGetOrder.class);

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

	@Autowired
	private MarshallConsumers marshallConsumer;

	/**
	 * {@inheritDoc}
	 */
	public OrchestrationContext processRequest(
			final OrderManagementRequestResponseDocument orderDocument) {

		logger.info("Processing getOrder request");
		Request request = orderDocument.getOrderManagementRequestResponse()
				.getRequest();

		OrchestrationContext params = createLoadContext(orderDocument);
		// Long orderExtId = getOrderExternalId(request);
		String orderExtIds = getOrderExternalId(request);
		logger.info("Requested order external id : " + orderExtIds);
		try {
			params.add(TaskContextParamEnum.salesOrderId.name(), orderExtIds);
			params.add(TaskContextParamEnum.request.name(), request);
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
		String salesOrderId = (String) params
				.get(TaskContextParamEnum.salesOrderId.name());
		List<SalesOrder> soList = new ArrayList<SalesOrder>();
		Request request = (Request) params.get(TaskContextParamEnum.request
				.name());
		if (salesOrderId != null && StringUtils.contains(salesOrderId, ",")) {
			soList = orderManagementDao.findByIds(salesOrderId, request.getIncludeAccountHolders());
		} else {
			SalesOrder salesOrderBean = orderManagementDao.findById(Long
					.valueOf(salesOrderId), request.getIncludeAccountHolders());

			if (salesOrderBean == null) {
				((ResponseItemOme) params).addSalesOrderNotFound(String
						.valueOf(salesOrderId));
			}
			if (salesOrderBean != null) {
				soList.add(salesOrderBean);
			}
		}

		Map<String, Consumer> orderConsumerMap = new HashMap<String, Consumer>();

		if ((soList != null) && (soList.size() > 0)) {
			for (SalesOrder salesOrderBean : soList) {

				if (salesOrderBean == null) {
					continue;
				}

				Consumer consumer = customerDao
						.findCustomerByExternalId(salesOrderBean
								.getConsumerExternalId());
				orderConsumerMap.put(salesOrderBean.getExternalId().toString(),
						consumer);
			}
			if (orderConsumerMap != null) {
				params.add(TaskContextParamEnum.consumerMap.name(),
						orderConsumerMap);
			}

			params.addSuccessful(soList);
		} else {
			((ResponseItemOme) params).addSalesOrderNotFound(String
					.valueOf(salesOrderId));
		}

		return params;
	}

	/**
	 * {@inheritDoc}
	 */
	public OrderManagementRequestResponseDocument processResponse(
			final OrchestrationContext params) {

		logger.info("Preparing order response");

		OrderManagementRequestResponseDocument container = OrderManagementRequestResponseDocument.Factory
				.newInstance();
		OrderManagementRequestResponse requestResponse = container
				.addNewOrderManagementRequestResponse();

		// Boolean includeCustDetails =
		// (Boolean)params.get(TaskContextParamEnum.includeCustomerDetails.name());
		List<SalesOrder> salesOrderBeanList = getSalesOrderBeanList(params
				.get(TaskContextParamEnum.salesOrder.name()));
		Request request = (Request) params.get(TaskContextParamEnum.request
				.name());
		// Load response
		// loadSalesOrderToResponse( requestResponse, salesOrderBeanList);
		if (salesOrderBeanList != null) {
			Response response = requestResponse.addNewResponse();
			for (SalesOrder salesOrderBean : salesOrderBeanList) {
				responseBuilder.populateSalesContext(params, response,
						salesOrderBean);
				OrderType orderType = response.addNewOrderInfo();
				CustomerInformation custInfoType = orderType
						.addNewCustomerInformation();
				CustomerType custType = custInfoType.addNewCustomer();
				if (salesOrderBean != null && orderType != null) {
					marshallOrder.build(salesOrderBean, orderType, request.getIncludeAccountHolders());
					Consumer consumerBean = null;
					if(null != params.get(TaskContextParamEnum.consumerMap.name())){
						Map<String, Consumer> orderConsumerMap = (Map<String, Consumer>)params.get(TaskContextParamEnum.consumerMap.name());
						consumerBean = orderConsumerMap.get(salesOrderBean.getExternalId().toString());
						if(null == consumerBean){
							consumerBean = customerDao
									.findCustomerByExternalId(salesOrderBean
											.getConsumerExternalId());							
						}
					}
					if (consumerBean != null) {
						custType = orderType.getCustomerInformation()
								.getCustomer();
						marshallConsumer.build(consumerBean, custType, request.getIncludeAccountHolders());
					}
				}

			}
		} else {
			logger.debug("Sales order not found for provided id.");
		}

		//If properties is set in db to true then only add req to response xml
		Boolean addRequestToRes = SystemPropertiesRepo.getSystemPropertyValueAsBoolean("OME.add.req.to.response");
		if(addRequestToRes){
			Request req = (Request) params.get(TaskContextParamEnum.request.name());
			container.getOrderManagementRequestResponse().setRequest(req);
		}
		return container;

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
	@Override
	public void broadcast(String strToBroadcastOriginal, Map<String, String> map) {

		// Gets api do not need to broadcast
		// Broadcastable broadcast = HttpBroadcastable.createDefault();
		// broadcast.broadcast(strToBroadcastOriginal,map);
	}

	// private Long getOrderExternalId(Request request) {
	// String orderId = unmarshallOrder.buildOrderId(request);
	// Long orderExtId = DEFAULT_ORDER_EXTERNAL_ID;
	// if (orderId != null && orderId.trim().length() > 0) {
	// orderExtId = Long.valueOf(orderId);
	// }
	//
	// return orderExtId;
	// }

	private String getOrderExternalId(Request request) {
		return unmarshallOrder.buildOrderId(request);

	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
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
