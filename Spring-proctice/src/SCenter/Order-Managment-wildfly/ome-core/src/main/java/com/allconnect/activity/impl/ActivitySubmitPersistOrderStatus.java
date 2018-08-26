package com.AL.activity.impl;


import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.enums.OrderStatus;
import com.AL.factory.ArbiterRequestFactory;
import com.AL.ie.service.strategy.ArbiterFlowManagerDefault;
import com.AL.rules.core.RuleService;
import com.AL.service.impl.OrderStatusServiceImpl;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.OrchestrationParamName;
import com.AL.util.audit.AuditService;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.OrderAudit;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.StatusRecordBean;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.vm.service.OrderAgentService;

@Component
public class ActivitySubmitPersistOrderStatus {

	private static final Logger logger = Logger
			.getLogger(OrderStatusServiceImpl.class);

	@Autowired
	ArbiterFlowManagerDefault arbiter;

	@Autowired
	private ArbiterRequestFactory arbiterRequestFactory;

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

	private static final String LI_TYPE_PRODUCT = "product";

	private static final String TOKEN = "TOKEN_FOR_MAP";

	public void invokeRulesValidation(final OrchestrationContext params,
			SalesOrder salesOrder, StatusRecordBean newStatus) {

		Set<Object> factList = new HashSet<Object>();
		factList.add(salesOrder);
		factList.add(newStatus);
		params.add("facts", factList);
		ruleService.execute(params);
	}

	public void submitOrderToProvider(StatusRecordBean newStatus,
			SalesOrder salesOrder, final OrchestrationContext params) {
		try {

			logger.debug("status change is submitted.  delegate status change to provider:"
					+ salesOrder.getExternalId());

			ActivityDispatchOrderToExtProvider.INSTANCE
					.dispatchOrderToExternalProvider(arbiter,
							arbiterRequestFactory, params);
			activitySubmitOrder.updateRequestWithTransientResponse(params);

			Map<String, String> lineItemStatus = (Map<String, String>) params
					.get(OrchestrationParamName.lineItemStatusMap.name());

			if (lineItemStatus == null) {

				lineItemStatus = new HashMap<String, String>();
			}

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

			if ((lineItemStatus != null) && (lineItemStatus.size() > 0)) {
				OrderStatus orderStatus = OrderStatus
						.precedence(lineItemStatus);

				newStatus.setStatus(orderStatus.name());
				newStatus.setDateTimeStamp(Calendar.getInstance());
			}
			params.getValidationReport().addInfoMessage(1L,
					"order status updated:" + salesOrder.getExternalId());
		} catch (Exception e) {
			logger.debug(e.getMessage());
			params.getValidationReport().addErrorMessage(
					3L,
					"error during order submission to provider:"
							+ e.getMessage());
		} finally {
			// UPDATE ORDER STATUS BASED ON SUBMISSION RESPONSE
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