package com.AL.task.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.customer.OmeCustomerService;
import com.AL.task.LocalTaskDelete;
import com.AL.task.TaskBase;
import com.AL.task.broadcast.AddProviderIdToContextForBroadcast;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.ResponseItemOme;
import com.AL.task.response.ResponseBuilder;
import com.AL.util.messaging.Broadcastable;
import com.AL.util.messaging.impl.HttpBroadcastable;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.vm.util.converter.unmarshall.UnmarshallOrder;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
 * @author ebthomas
 * 
 */

@Component("taskDelete")
public class TaskDelete extends
		TaskBase<OrderManagementRequestResponseDocument> implements
		LocalTaskDelete<OrderManagementRequestResponseDocument>, Broadcastable {

	private Logger logger = Logger.getLogger(TaskDelete.class);

	@Autowired
	private UnmarshallOrder unmarshallOrder;

	@Autowired
	private OrderManagementDao orderManagementDao;

	@Autowired
	private OmeCustomerService omeCustomerService;

	@Autowired
	private MarshallOrder<SalesOrder> marshallOrder;
	
	@Autowired
	private ResponseBuilder responseBuilder;

	/**
	 * {@inheritDoc}
	 */
	public OrchestrationContext processRequest(
			final OrderManagementRequestResponseDocument orderDocument) {

		Request request = orderDocument.getOrderManagementRequestResponse()
				.getRequest();
		String orderId = unmarshallOrder.buildOrderId(request);

		OrchestrationContext params =  createLoadContext(orderDocument);

	 
		try {
			params.add(TaskContextParamEnum.request.name(), request);
			params.add(TaskContextParamEnum.salesOrderId.name(), orderId);
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
		
		

		Long salesOrderId = Long.valueOf((String) params.get(TaskContextParamEnum.salesOrderId
				.name()));
		logger.debug("removing:" + salesOrderId);

		SalesOrder removedSalesOrder = null;

		ResponseItemOme taskResponse = OrchestrationContext.Factory
				.createOme(params);

		if (salesOrderId != null) {
			removedSalesOrder = orderManagementDao.findById(salesOrderId);

			logger.debug("removing sales order:" + removedSalesOrder);
			logger.debug("check dao not null." + orderManagementDao);
			
			String agentId = (String)params.get(TaskContextParamEnum.agent.name() );

			if (removedSalesOrder != null) {
				 
				for (LineItem li : removedSalesOrder.getLineItems()) {

					if (li != null) {
						omeCustomerService.addConsumerInteraction(agentId, removedSalesOrder, li,
								"lineitem updated:" + li.getExternalId());
					}

				}
				
				orderManagementDao.removeSalesOrderById(String.valueOf(salesOrderId));
				logger.debug("removed sales order:" + salesOrderId);
			}
		}

		if (removedSalesOrder != null) {
			logger.debug("removing sales order:" + salesOrderId);
			taskResponse.addSalesOrderRemoved(removedSalesOrder);
			logger.debug("removed sales order:" + salesOrderId);
		} else {
			logger.debug("sales order not found:" + salesOrderId);
			taskResponse.addSalesOrderNotFound(String.valueOf(salesOrderId));
		}
		
		AddProviderIdToContextForBroadcast.INSTANCE.execute(   params, removedSalesOrder);

		return taskResponse;
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

	/*public AuditService<OrderAudit> getAuditService() {
		return auditService;
	}

	public void setAuditService(AuditService<OrderAudit> auditService) {
		this.auditService = auditService;
	}*/
	
	

	public MarshallOrder<SalesOrder> getMarshallOrder() {
		return marshallOrder;
	}

	public void setMarshallOrder(MarshallOrder<SalesOrder> marshallOrder) {
		this.marshallOrder = marshallOrder;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void broadcast(String strToBroadcastOriginal, Map<String, String> map) {

		Broadcastable broadcast = HttpBroadcastable.createDefault();
		broadcast.broadcast(strToBroadcastOriginal,map);
	}
	
	
}
