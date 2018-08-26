package com.AL.activity.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.AL.factory.StatusFactory;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.StatusRecordBean;
import com.AL.V.beans.entity.User;

public enum ActivityCreateInitialStatus {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(ActivityCreateInitialStatus.class);

	private static final String INITIAL_STATUS_REASON_CODE = "5001";

	public void execute(
			ActivitySubmitToProviderAfterValidation orderStatusChange,
			User user, SalesOrder salesOrder, OrchestrationContext params) {

		logger.info("Creating initial order status");
		salesOrder.setCurrentStatus(null);
		salesOrder.setHistoricStatus(new ArrayList<StatusRecordBean>());

		int numOfLineItemsInSalesOrder = salesOrder.getLineItems().size();
		for (int assignedIndex = 0; assignedIndex < numOfLineItemsInSalesOrder; assignedIndex++) {
			LineItem lineItem = salesOrder.getLineItems().get(assignedIndex);
			lineItem.setLineItemNumber(assignedIndex);
			lineItem.setCurrentStatus(null);
			lineItem.setHistoricStatus(new ArrayList<StatusRecordBean>());

			StatusFactory.INSTANCE.createInitialStatus(user, lineItem);

		}

		StatusRecordBean initialStatus = orderStatusChange
				.calcPostOperationOrderStatus(salesOrder, params, user);

		initializeReasons(initialStatus);
		initializeHistoricStatus(salesOrder, initialStatus);
		logger.debug("Initial order status : " + initialStatus.getStatus());
		salesOrder.setCurrentStatus(initialStatus);

	}

	public void initializeReasons(StatusRecordBean initialStatus) {

		if (initialStatus.getReasons() == null) {
			initialStatus.setReasons(new ArrayList<String>());
		}
		initialStatus.getReasons().add(INITIAL_STATUS_REASON_CODE);
	}

	public void initializeHistoricStatus(LineItem lineitem,
			StatusRecordBean initialStatus) {

		if (lineitem.getHistoricStatus() == null) {
			lineitem.setHistoricStatus(new ArrayList<StatusRecordBean>());
		}
		lineitem.getHistoricStatus().add(initialStatus);
	}

	public void initializeHistoricStatus(SalesOrder salesOrder,
			StatusRecordBean initialStatus) {

		if (salesOrder.getHistoricStatus() == null) {
			salesOrder.setHistoricStatus(new ArrayList<StatusRecordBean>());
		}
		salesOrder.getHistoricStatus().add(initialStatus);
	}
}
