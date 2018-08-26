package com.A.ui.service.V;
import com.A.ui.service.V.*;
public class ConcurrentRequestSubmit implements Runnable {

	final String orderId;
	final String lineItemId;
	final String agentId;

	public ConcurrentRequestSubmit(final String agentId, final String orderId,	final String lineItemId) {
		this.orderId = orderId;
		this.lineItemId = lineItemId;
		this.agentId = agentId;
	}

	public void run() {
		LineItemService.INSTANCE.submitLineItem(agentId, orderId, lineItemId);
	}
}
