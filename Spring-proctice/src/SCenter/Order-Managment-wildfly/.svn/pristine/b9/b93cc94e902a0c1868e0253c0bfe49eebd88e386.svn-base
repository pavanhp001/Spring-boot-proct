package com.AL.activity.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.AL.factory.StatusFactory;
import com.AL.order.status.LineItemStatusFactory;
import com.AL.rules.core.RuleService;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.StatusRecordBean;
import com.AL.V.beans.entity.User;
import com.AL.vm.service.OrderAgentService;
import com.AL.vm.vo.OrderChangeValueObject;
public enum ActivityRulesValidation {

	INSTANCE;

	private static final Logger logger = Logger
			.getLogger(ActivityRulesValidation.class);

	public Boolean execute(final RuleService ruleService,
			final OrderAgentService agentService, final OrchestrationContext params,
			SalesOrder salesOrder, final User agent) {

		logger.debug("starting process to submit sales order to provider:"
				+ salesOrder.getExternalId());
		OrderChangeValueObject orderChangeVO = (OrderChangeValueObject) params
				.get(TaskContextParamEnum.orderStatus.name());

		logger.debug("validating sales order before status change:"
				+ salesOrder.getExternalId());
		
		

		StatusRecordBean newStatus = LineItemStatusFactory.INSTANCE
				.createNewStatus(agentService, salesOrder, StatusFactory.INSTANCE.convert(orderChangeVO.getStatus()) ,orderChangeVO.getReasonList(), agent);

		invokeRulesValidation(ruleService, params, salesOrder, newStatus);

		if (params.getValidationReport().hasErrors()) {
			logger.debug("failed rules validation:"
					+ salesOrder.getExternalId());

			params.getValidationReport().addErrorMessage(
					2L,
					"unable to continue submit process. validation errors"
							+ salesOrder.getExternalId());
			return Boolean.FALSE;
		}

		return Boolean.TRUE;

	}

	public void invokeRulesValidation(final RuleService ruleService,
			final OrchestrationContext params, SalesOrder salesOrder,
			StatusRecordBean newStatus) {

		Set<Object> factList = new HashSet<Object>();
		factList.add(salesOrder);
		factList.add(newStatus);
		params.add("facts", factList);
		ruleService.execute(params);
	}

}
