package com.A.enums;

import java.util.Map;

public enum OrderStatus {

	sales("sales"), submitted("submitted"), cancelled("cancelled"), processing(
			"processing"), order_aged("order_aged"), scheduled_confirmed(
			"scheduled_confirmed"), provision_ready("provision_ready"), provision_problem(
			"provision_problem"), test_order("test_order"), undefined(
			"undefined");

	private String description;

	public static OrderStatus precedence(final Map<String, String> map) {

		if (map.get(LineItemStatus.processing_schedule_pending.name()) != null) {
			return OrderStatus.processing;
		}

		if (map.get(LineItemStatus.processing_problem_ordered_vru_failed.name()) != null) {
			return OrderStatus.processing;
		}
		if (map.get(LineItemStatus.processing_connected.name()) != null) {
			return OrderStatus.processing;
		}
		if (map.get(LineItemStatus.processing_disconnected.name()) != null) {
			return OrderStatus.processing;
		}

		if (map.get(LineItemStatus.processing_schedule_confirmed.name()) != null) {
			return OrderStatus.scheduled_confirmed;
		}

		if (map.get(LineItemStatus.sales_pre_order.name()) != null) {
			return OrderStatus.sales;
		}

		if (map.get(LineItemStatus.sales_new_order.name()) != null) {
			return OrderStatus.sales;
		}
		if (map.get(LineItemStatus.sales_submitted.name()) != null) {
			return OrderStatus.provision_ready;
		}

		if (map.get(LineItemStatus.hold_order.name()) != null) {
			return OrderStatus.provision_problem;
		}

		if (map.get(LineItemStatus.hold_order_pending_problem.name()) != null) {
			return OrderStatus.provision_problem;
		}

		if (map.get(LineItemStatus.hold_customer_action.name()) != null) {
			return OrderStatus.provision_problem;
		}

		if (map.get(LineItemStatus.hold_authorization_pending.name()) != null) {
			return OrderStatus.provision_problem;
		}

		if (map.get(LineItemStatus.hold_provider.name()) != null) {
			return OrderStatus.provision_problem;
		}

		if (map.get(LineItemStatus.provision_ready.name()) != null) {
			return OrderStatus.provision_ready;
		}

		if (map.get(LineItemStatus.provision_problem.name()) != null) {
			return OrderStatus.provision_problem;
		}

		if (map.get(LineItemStatus.submit_failed.name()) != null) {
			return OrderStatus.sales;
		}

		if (map.get(LineItemStatus.failed.name()) != null) {
			return OrderStatus.sales;
		}

		if (map.get(LineItemStatus.failed_test.name()) != null) {
			return OrderStatus.sales;
		}

		if (map.get(LineItemStatus.processing_aged.name()) != null) {
			return OrderStatus.order_aged;
		}

		if (map.get(LineItemStatus.cancelled_removed.name()) != null) {
			return OrderStatus.cancelled;
		}

		if (map.get(LineItemStatus.processing_cancelled.name()) != null) {
			return OrderStatus.cancelled;
		}

		if (map.get(LineItemStatus.sales_defer.name()) != null) {
			return OrderStatus.sales;
		}

		if (map.get(LineItemStatus.test_order.name()) != null) {
			return OrderStatus.test_order;
		}

		return OrderStatus.sales;
	}

	public static String resolveStatus(final String status) {

		if (status == null) {
			return sales.name();
		}

		try {

			OrderStatus resolvedStatus = OrderStatus.valueOf(status
					.toLowerCase());
			return resolvedStatus.name();
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage()
					+ "--order status invalid:" + status);

		}
	}

	private OrderStatus(final String value) {
		this.description = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		this.description = value;
	}

}
