package com.A.ui.builder;

import com.A.xml.v4.LineItemType;
import com.A.xml.v4.OrderType;
import com.A.xml.v4.ProviderLineItemStatusType;
import com.A.xml.v4.TransientResponseContainerType;
import com.A.xml.v4.TransientResponseType;

public enum TransientResponseBuilder {

	INSTANCE;

	public TransientResponseContainerType getTransientResponse(OrderType order,
			Long lineItemExternalId) {

		TransientResponseContainerType trc = order.getLineItems().getLineItem()
				.get(0).getTransientResponseContainer();

		LineItemType selectedLineItem = null;
		selectionLoop: for (LineItemType lit : order.getLineItems()
				.getLineItem()) {

			if (lit.getExternalId() == lineItemExternalId) {
				selectedLineItem = lit;
				break selectionLoop;

			}
		}

		if (selectedLineItem != null) {
			trc = selectedLineItem.getTransientResponseContainer();
		}

		return trc;

	}

	public ProviderLineItemStatusType getTransientResponseStatus(
			OrderType order, Long lineItemExternalId) {

		TransientResponseContainerType tContainer = getTransientResponse(order,
				lineItemExternalId);

		TransientResponseType tr = tContainer.getTransientResponse();
		ProviderLineItemStatusType plis = tr.getProviderLineItemStatus();

		return plis;

	}

	public boolean isError(OrderType order, Long lineItemExternalId) {

		ProviderLineItemStatusType pStatus = getTransientResponseStatus(order,
				lineItemExternalId);

		return "ERROR".equals(pStatus.getProcessingStatusCode());
	}

	public boolean isSuccess(OrderType order, Long lineItemExternalId) {

		ProviderLineItemStatusType pStatus = getTransientResponseStatus(order,
				lineItemExternalId);

		return "INFO".equals(pStatus.getProcessingStatusCode());
	}

}
