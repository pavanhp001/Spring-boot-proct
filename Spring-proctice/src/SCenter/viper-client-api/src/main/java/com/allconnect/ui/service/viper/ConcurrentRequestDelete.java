package com.A.ui.service.V;

import java.util.List;

public class ConcurrentRequestDelete implements Runnable {

	final String orderId;
	final String lineItemId;
	final String statusCode;
	final String description;
	final List<Integer> reasons;
	final String agentId;

	public ConcurrentRequestDelete(final String agentId, final String orderId,
			final String lineItemId, final String statusCode,
			final String description, final List<Integer> reasons) {

		this.orderId = orderId;
		this.lineItemId = lineItemId;
		this.statusCode = statusCode;
		this.description = description;
		this.reasons = reasons;
		this.agentId = agentId;
	}

	public void run() {
		LineItemService.INSTANCE.updateLineItemStatus(agentId, orderId,
				lineItemId, "cancelled_removed", "deleted order", reasons);

	}

}
