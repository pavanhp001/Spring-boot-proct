package com.AL.task.strategy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.AL.enums.OrderStatus;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.StatusRecordBean;

public enum StatusChangeStrategy {

	INSTANCE;

	private static final Logger logger = Logger
			.getLogger(StatusChangeStrategy.class);

	public void resolveNewStatusAndReason(final SalesOrder orderBean,
			final StatusRecordBean newStatus) {

		Calendar calNow = Calendar.getInstance();

		logger.debug("resolving order status:" + newStatus.getStatus());

		String resolvedStatus = OrderStatus
				.resolveStatus(newStatus.getStatus());

		newStatus.setStatus(resolvedStatus);
		newStatus.setReasons(getDefaultReasons());
		newStatus.setDateTimeStamp(calNow);
		//newStatus.setAgentExternalId(orderBean.getAgentId());
		logger.debug(resolvedStatus + " <-- resolving order status for order : "
				+ orderBean.getExternalId());

	}

	public void validateStatus(final StatusRecordBean currentStatus) {

		if ((currentStatus.getStatus() != null)
				&& (currentStatus.getStatus().equalsIgnoreCase("undefined"))) {
			currentStatus.setStatus(OrderStatus.sales.name());
		}

		if ((currentStatus.getReasons() == null)
				|| (currentStatus.getReasons().size() == 0)) {

			currentStatus.setReasons(getDefaultReasons());
		}
	}

	public void addCurrentStatusToHistory(final SalesOrder salesOrder,
			final StatusRecordBean current) {

		if (salesOrder.getHistoricStatus() == null) {
			salesOrder.setHistoricStatus(new ArrayList<StatusRecordBean>());
		}

		// Ensure current is captured in history
		Boolean isAlreadyExist = Boolean.FALSE;
		alreadyExistTest: for (StatusRecordBean srb : salesOrder
				.getHistoricStatus()) {
			if (srb.getId() == current.getId()) {
				isAlreadyExist = Boolean.TRUE;
				break alreadyExistTest;
			}
		}

		if (!isAlreadyExist) {
			salesOrder.getHistoricStatus().add(current);
		}

		if (salesOrder.getCurrentStatus() == null) {
			return;
		}

		isAlreadyExist = Boolean.FALSE;
		alreadyExistTest: for (StatusRecordBean srb : salesOrder
				.getHistoricStatus()) {
			if (srb.getId() == salesOrder.getCurrentStatus().getId()) {
				isAlreadyExist = Boolean.TRUE;
				break alreadyExistTest;
			}
		}

		if (!isAlreadyExist) {
			salesOrder.getHistoricStatus().add(salesOrder.getCurrentStatus());
		}

	}

	public List<String> getDefaultReasons() {
		List<String> reasons = new ArrayList<String>();
		reasons.add("0");// Default System Generate

		return reasons;
	}
}
