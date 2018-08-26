package com.AL.activity.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.activity.Activity;
import com.AL.enums.OrderStatus;
import com.AL.ie.activity.impl.ActivityArbiterResponse;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.OrchestrationParamName;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.StatusRecordBean;
import com.AL.V.beans.entity.User;
import com.AL.Vdao.dao.BusinessPartyDao;
import com.AL.vm.service.OrderAgentService;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.vm.vo.OrderChangeValueObject;

@Component("activitySubmitOrder")
public class ActivitySubmitOrder implements Activity {

	private static final Logger logger = Logger
			.getLogger(ActivitySubmitOrder.class);

	final boolean ENABLE_RTS_INTEGRATION = Boolean.TRUE;

	@Autowired
	private ActivityUpdateLineItemWithNotificationEvent activityUpdateLineItemWithNotificationEvent;

	@Autowired
	BusinessPartyDao businessPartyDao;



	@Autowired
	ActivityArbiterResponse arbiterResponse;

	@Autowired
	private OrderAgentService agentService;

	@Autowired
	private MarshallOrder<SalesOrder> marshallOrder;



	private static final String TOKEN = "TOKEN_FOR_MAP";



	public void updateOrderStatus(final OrchestrationContext params) {

		SalesOrder salesOrder = (SalesOrder) params
				.get(TaskContextParamEnum.salesOrder.name());

		@SuppressWarnings("unchecked")
		Map<String, String> lineItemStatus = (Map<String, String>) params
				.get(OrchestrationParamName.lineItemStatusMap.name());

		if (lineItemStatus == null) {
			lineItemStatus = new HashMap<String, String>();
		}

		if (lineItemStatus.size() == 0) {

			List<LineItem> liList = salesOrder.getLineItems();
			for (LineItem lineItem : liList) {
				if ((lineItem != null) && (lineItem.getCurrentStatus() != null)
						&& (lineItem.getCurrentStatus().getStatus() != null)) {
					lineItemStatus.put(lineItem.getCurrentStatus().getStatus(),
							TOKEN);
				}
			}

		}


	}

	public void updateRequestWithTransientResponse(
			final OrchestrationContext params) {

		if (params.getValidationReport().hasErrors()) {
			return;
		}

		SalesOrder salesOrder = (SalesOrder) params
				.get(TaskContextParamEnum.salesOrder.name());

		logger.info("evaluating sales order response :" + salesOrder.getId());
		arbiterResponse.process(params);

	}

	public StatusRecordBean createNewStatus(final SalesOrder orderBean,
			final OrderChangeValueObject orderChangeValueObject) {
		Calendar calNow = Calendar.getInstance();

		logger.debug("creating new order status entry");
		StatusRecordBean statusRecord = new StatusRecordBean();
		orderBean.setCurrentStatus(statusRecord);

		String currentStatus = orderChangeValueObject.getStatus();

		statusRecord.setStatus(OrderStatus.resolveStatus(currentStatus));
		statusRecord.setDateTimeStamp(calNow);

		logger.debug("validate agent id:" + orderChangeValueObject.getAgentId());
		User agent = agentService.getAgent(orderChangeValueObject.getAgentId());
		statusRecord.setAgent(agent);

		if (orderChangeValueObject.getReasonListAsString() == null) {
			statusRecord.setReasons(Collections.EMPTY_LIST);
		} else {
			statusRecord.setReasons(orderChangeValueObject
					.getReasonListAsString());
		}

		return statusRecord;

	}

	public ActivityUpdateLineItemWithNotificationEvent getActivityUpdateLineItemWithNotificationEvent() {
		return activityUpdateLineItemWithNotificationEvent;
	}

	public void setActivityUpdateLineItemWithNotificationEvent(
			ActivityUpdateLineItemWithNotificationEvent activityUpdateLineItemWithNotificationEvent) {
		this.activityUpdateLineItemWithNotificationEvent = activityUpdateLineItemWithNotificationEvent;
	}

	public BusinessPartyDao getBusinessPartyDao() {
		return businessPartyDao;
	}

	public void setBusinessPartyDao(BusinessPartyDao businessPartyDao) {
		this.businessPartyDao = businessPartyDao;
	}



	public ActivityArbiterResponse getArbiterResponse() {
		return arbiterResponse;
	}

	public void setArbiterResponse(ActivityArbiterResponse arbiterResponse) {
		this.arbiterResponse = arbiterResponse;
	}

	public OrderAgentService getAgentService() {
		return agentService;
	}

	public void setAgentService(OrderAgentService agentService) {
		this.agentService = agentService;
	}

	public MarshallOrder<SalesOrder> getMarshallOrder() {
		return marshallOrder;
	}

	public void setMarshallOrder(MarshallOrder<SalesOrder> marshallOrder) {
		this.marshallOrder = marshallOrder;
	}

	public void process(OrchestrationContext params) {
		// TODO Auto-generated method stub

	}

}
