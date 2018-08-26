package com.AL.task.util;

import org.apache.log4j.Logger;

import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.User;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.vm.service.OrderAgentService;
import com.AL.vm.vo.OrderChangeValueObject;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;

public enum DomainObjectLocator {

	INSTANCE;

	private static final Logger logger = Logger
			.getLogger(DomainObjectLocator.class);

	
	public SalesOrder findBasicSalesOrder(final OrchestrationContext params,
			final OrderManagementDao orderManagementDao) {

		SalesOrder salesOrder = null;

		logger.debug("get basic sales order from database or context");
		Request request = (Request) params.get(TaskContextParamEnum.request
				.name());

		if (request != null) {
			String orderId = request.getOrderId();

			if (orderId != null) {
				logger.debug("getting order:" + orderId + " from the database");
				salesOrder = orderManagementDao.findBasicSalesOrderById(Long.valueOf(orderId), request.getIncludeAccountHolders());
				params.add(TaskContextParamEnum.salesOrder.name(), salesOrder);
			}
		}

		return salesOrder;
	}
	
	
	public SalesOrder findSalesOrder(final OrchestrationContext params,
			final OrderManagementDao orderManagementDao) {

		SalesOrder salesOrder = null;

		logger.debug("get sales order from database or context");
		Request request = (Request) params.get(TaskContextParamEnum.request
				.name());
		if (request != null) {
			String orderId = request.getOrderId();

			if (orderId != null) {
				logger.debug("getting order:" + orderId + " from the database");
				salesOrder = orderManagementDao.findById(Long.valueOf(orderId), request.getIncludeAccountHolders());
				params.add(TaskContextParamEnum.salesOrder.name(), salesOrder);
			}
		}

		return salesOrder;
	}

	public User findAgent(final OrderChangeValueObject orderChangeVO,
			final OrderAgentService agentService) {

			User agent = agentService.getAgent(orderChangeVO);
			if (agent == null) {
				throw new IllegalArgumentException("missing agent:"	+ orderChangeVO.getAgentId());
			}
		return agent;

	}

	public Boolean validateAgent(String agentId, OrderAgentService agentService){
		Boolean isValid = Boolean.TRUE;
		User agent = agentService.findAgentById(agentId);
		if (agent == null) {
			isValid = Boolean.FALSE;
		}
		return isValid;
	}

	public SalesOrder findSalesOrder(final OrchestrationContext params,
			final Long orderId, final OrderManagementDao orderManagementDao) {
		SalesOrder salesOrder = (SalesOrder) params
				.get(TaskContextParamEnum.salesOrder.name());

		if (salesOrder == null) {
			salesOrder = addSalesOrderFromDatabaseToContext(orderId, params,
					orderManagementDao);
		}

		if (salesOrder == null) {
			throw new IllegalArgumentException("Unable to locate Sales Order:"
					+ orderId);
		}

		return salesOrder;
	}

	public SalesOrder addSalesOrderFromDatabaseToContext(Long orderId,
			final OrchestrationContext params,
			final OrderManagementDao orderManagementDao) {

		logger.debug("retrieve sales order from the database:" + orderId);
		SalesOrder salesOrder = orderManagementDao.findById(orderId);

		logger.debug("adding sales order:" + orderId
				+ " to the orchestration context");
		params.add(TaskContextParamEnum.salesOrder.name(), salesOrder);

		return salesOrder;

	}

}
