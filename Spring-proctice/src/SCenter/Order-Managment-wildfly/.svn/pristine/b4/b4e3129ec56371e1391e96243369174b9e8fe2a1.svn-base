package com.AL.activity.impl;

import java.util.List;
import com.AL.activity.lineitem.impl.ActivityLocateLineItem;
import com.AL.enums.LineItemStatus;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.vm.vo.OrderChangeValueObject;

public enum ActivityAllowFromStatusToStatus {

	INSTANCE;

	private static final String SUBMITTED_STATUS = "submitted";

	public boolean execute(final OrchestrationContext params,
			final SalesOrder salesOrder,
			final OrderChangeValueObject orderChangeVO) {
		boolean response = Boolean.TRUE;

		@SuppressWarnings("unchecked")
		List<String> lineItemIds = (List<String>) params
				.get(TaskContextParamEnum.lineItemList.name());

		if ((salesOrder != null) && (orderChangeVO != null)
				&& (orderChangeVO.getStatus() != null) && (lineItemIds != null)
				&& (lineItemIds.size() > 0)) {

			String newStatus = orderChangeVO.getStatus();

			boolean attemptToSubmitDeleted = (isSubmitDeleted(params, lineItemIds, newStatus, salesOrder) && response);
			response = !attemptToSubmitDeleted;
		}

		return response;
	}

	public boolean isSubmitDeleted(final OrchestrationContext params,List<String> lineItemIds, String newStatus,
			SalesOrder salesOrder) {

		boolean response = Boolean.FALSE;

		for (String lineItemId : lineItemIds) {

			LineItem lineItem = ActivityLocateLineItem.INSTANCE.execute(
					lineItemId, salesOrder);

			if ((lineItem.getCurrentStatus() != null)&&(SUBMITTED_STATUS.equalsIgnoreCase(newStatus))
					&& (LineItemStatus.cancelled_removed.name().equals(lineItem
							.getCurrentStatus().getStatus()))) {

				params.getValidationReport().addErrorMessage(
						7001L,
						"attempt to submit deleted lineitem:"+lineItemId );
				
				return Boolean.TRUE;
			}
		}

		return response;
	}

}
