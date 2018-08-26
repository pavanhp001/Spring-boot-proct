package com.AL.activity.impl;

import org.apache.log4j.Logger;

import com.AL.enums.OrderStatus;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.validate.ValidateSubmit;
import com.AL.vm.vo.OrderChangeValueObject;

public enum ActivityAddSubmitRequestToParam {
 INSTANCE;

	private static final Logger logger = Logger
	.getLogger(ActivityAddSubmitRequestToParam.class);

 public void execute(final OrchestrationContext params) {
		OrderChangeValueObject orderChangeValueObject = (OrderChangeValueObject) params
				.get(TaskContextParamEnum.orderStatus.name());

		ValidateSubmit.INSTANCE.validate(orderChangeValueObject);

		logger.info("Submitting order [" + orderChangeValueObject.getOrderId() + "] to provider");

		logger.debug("ensuring transition-to status is:" + OrderStatus.submitted.name());
		orderChangeValueObject.setStatus(OrderStatus.submitted.name());


	}
}
