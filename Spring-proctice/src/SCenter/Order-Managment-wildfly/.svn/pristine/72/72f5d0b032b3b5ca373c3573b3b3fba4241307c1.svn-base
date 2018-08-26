package com.AL.activity.impl;

import com.AL.factory.ArbiterRequestFactory;
import com.AL.factory.StatusFactory;
import com.AL.ie.service.strategy.ArbiterFlowManagerDefault;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.enums.OrderStatus;
import com.AL.rules.core.RuleService;
import com.AL.service.impl.OrderStatusServiceImpl;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.OrchestrationParamName;
import com.AL.task.strategy.StatusChangeStrategy;
import com.AL.util.audit.AuditService;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.OrderAudit;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.StatusRecordBean;
import com.AL.V.beans.entity.User;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.vm.service.OrderAgentService;
import com.AL.vm.vo.OrderChangeValueObject;

@Component
public class ActivitySubmitToProviderAfterValidation {

	private static final String LI_TYPE_PRODUCT = "product";

	private static final Logger logger = Logger
			.getLogger(OrderStatusServiceImpl.class);

	@Autowired
	private ActivityCalculateOrderPrice activityCalculateOrderPrice;

	@Autowired
	private OrderAgentService agentService;

	@Autowired
	private RuleService ruleService;

	@Autowired
	ActivitySubmitOrder activitySubmitOrder;

	@Autowired
	private OrderManagementDao orderManagementDao;

	@Autowired
	protected AuditService<OrderAudit> auditService;

	@Autowired
	ArbiterFlowManagerDefault arbiter;

	@Autowired
	private ArbiterRequestFactory arbiterRequestFactory;

	private static final String TOKEN = "TOKEN_FOR_MAP";

	public Boolean persistUpdatedOrderStatusAndPricing(
			final OrchestrationContext params, SalesOrder salesOrder,
			StatusRecordBean newSalesOrderStatus, boolean isSubmit) {
		logger.debug("order status change is "
				+ newSalesOrderStatus.getStatus() + "  [Order:"
				+ salesOrder.getExternalId() +"]");
		logger.debug("updating order pricing. Sales Order:"
				+ salesOrder.getExternalId());
		params.add(TaskContextParamEnum.salesOrder.name(), salesOrder);
		// Fix - PR-30: When there is a submit call from Encore and do the following pricing calculation
		if ((!isSubmit) || 
				(ActivitySalesContextEvaluate.INSTANCE.isCallFromEncore(salesOrder) && (isSubmit))) {
		    logger.debug("Recalculating price after status change");
			activityCalculateOrderPrice.process(params);
		}
		updateAndPersistAggregatedOrderStatus(salesOrder, newSalesOrderStatus,
				Boolean.FALSE);
		return Boolean.TRUE;
	}

	public StatusRecordBean calcPostOperationOrderStatus(SalesOrder salesOrder,
			final OrchestrationContext params, final User agent) {
		if (agent == null) {
			throw new IllegalArgumentException("invalid user agent");
		}
		StatusRecordBean newSalesOrderStatus = new StatusRecordBean();
		try {
			@SuppressWarnings("unchecked")
			Map<String, String> lineItemStatus = (Map<String, String>) params
					.get(OrchestrationParamName.lineItemStatusMap.name());
			if (lineItemStatus == null) {
				lineItemStatus = new HashMap<String, String>();
			}
			logger.debug("get status of all line items");
			if (lineItemStatus.size() == 0) {
				List<LineItem> liList = salesOrder.getLineItems();
				for (LineItem lineItem : liList) {
					if ((lineItem != null)
							&& (lineItem.getCurrentStatus() != null)
							&& (lineItem.getCurrentStatus().getStatus() != null)) {
						if(lineItem.getLineItemDetail().getType().equalsIgnoreCase(LI_TYPE_PRODUCT)){
							lineItemStatus.put(lineItem.getCurrentStatus().getStatus(), TOKEN);
						}
					}
				}
			}
			logger.debug("default status is sales");
			OrderStatus calculatedOrderStatus = OrderStatus.sales;
			logger.debug("determine order status based on the precedence rules");
			if ((lineItemStatus != null) && (lineItemStatus.size() > 0)) {
				calculatedOrderStatus = OrderStatus.precedence(lineItemStatus);
			}
			logger.debug("Calculated order status : " + calculatedOrderStatus.name().toString());
			newSalesOrderStatus = StatusFactory.INSTANCE
					.create(agent,calculatedOrderStatus);
			params.getValidationReport().addInfoMessage(1L,
					"order status updated:");
		} catch (Exception e) {
			logger.debug(e.getMessage());
			params.getValidationReport().addErrorMessage(
					3L,
					"error during order submission to provider:"
							+ e.getMessage());
		} finally {
			newSalesOrderStatus.setAgent(agent);
			newSalesOrderStatus.setAgentExternalId(agent.getUserLogin());
		}
		return newSalesOrderStatus;
	}

	public void processSubmitByProvider(User agent, SalesOrder salesOrder,
			OrchestrationContext params, OrderChangeValueObject orderChangeVO) {
		logger.debug("status change is submitted.  delegate status change to provider:"
				+ salesOrder.getExternalId());
		ActivityDispatchOrderToExtProvider.INSTANCE
				.dispatchOrderToExternalProvider(arbiter,
						arbiterRequestFactory, params);
		activitySubmitOrder.updateRequestWithTransientResponse(params);
	}

	public void updateAndPersistAggregatedOrderStatus(
			final SalesOrder orderBean, final StatusRecordBean newStatus,
			final boolean doPersist) {
		StatusRecordBean currentStatus = orderBean.getCurrentStatus();
		StatusChangeStrategy.INSTANCE.validateStatus(currentStatus);
		StatusChangeStrategy.INSTANCE.resolveNewStatusAndReason(orderBean,
				newStatus);
		StatusChangeStrategy.INSTANCE.addCurrentStatusToHistory(orderBean,
				currentStatus);
		logger.debug("update current order status:" + orderBean.getExternalId());
		orderBean.setCurrentStatus(newStatus);
		if (doPersist) {
			orderManagementDao.updateOrderStatus(orderBean);
		}
	}

	public ActivityCalculateOrderPrice getActivityCalculateOrderPrice() {
		return activityCalculateOrderPrice;
	}

	public void setActivityCalculateOrderPrice(
			ActivityCalculateOrderPrice activityCalculateOrderPrice) {
		this.activityCalculateOrderPrice = activityCalculateOrderPrice;
	}

	public ActivitySubmitOrder getActivitySubmitOrder() {
		return activitySubmitOrder;
	}

	public void setActivitySubmitOrder(ActivitySubmitOrder activitySubmitOrder) {
		this.activitySubmitOrder = activitySubmitOrder;
	}

}