
package com.AL.activity.impl;

import java.util.List;

import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.CustomerPaymentEvent;
import com.AL.xml.v4.CustomerDocument.Customer;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.PayEventRequestType;
import com.AL.xml.v4.PayeventRequestList;
import com.AL.xml.v4.PaymentEventType;

public enum ActivitySecureFieldsClean {

	INSTANCE;

	public void addCVV(final OrchestrationContext params,
			final OrderType salesOrder) {

		if ((salesOrder == null) || (salesOrder.getLineItems() == null)) {
			return;
		}

		List<LineItemType> liList = salesOrder.getLineItems().getLineItemList();

		if ((liList == null) || (liList.size() == 0)) {
			return;
		}

		for (LineItemType li : liList) {
			Customer customer = li.getCustomer();
			if (customer != null) {
				addCVV(params, customer);
			}

		}

	}

	public void addCVV(final OrchestrationContext params, Customer consumerBean) {

		if ((consumerBean == null)
				|| (consumerBean.getPayments() == null)
				|| (consumerBean.getPayments().getPaymentEventList() == null)
				|| (consumerBean.getPayments().getPaymentEventList().size() == 0)) {
			return;
		}

		List<PaymentEventType> petList = consumerBean.getPayments()
				.getPaymentEventList();

		PayeventRequestList payeventRequestList = (PayeventRequestList) params
				.get(TaskContextParamEnum.payEventData.name());

		if ((payeventRequestList != null)
				&& (payeventRequestList.getPayeventRequestList() != null)
				&& (payeventRequestList.getPayeventRequestList().size() > 0)) {
			for (PayEventRequestType perType : payeventRequestList
					.getPayeventRequestList()) {

				if ((perType.getPayEventExtId() != null)) {

					for (PaymentEventType payevent : petList) {

						if ((payevent.getExternalId() != null)
								&& (perType.getPayEventExtId().equals(payevent
										.getExternalId()))) {
							payevent.setCVV(perType.getCvv());
						}
					}

				}

			}
		}

	}

	public void addCVV(final OrchestrationContext params, Consumer consumerBean) {

		if ((consumerBean == null) || (consumerBean.getPaymentEvents() == null)
				|| (consumerBean.getPaymentEvents().size() == 0)) {
			return;
		}

		PayeventRequestList payeventRequestList = (PayeventRequestList) params
				.get(TaskContextParamEnum.payEventData.name());

		if ((payeventRequestList != null)
				&& (payeventRequestList.getPayeventRequestList() != null)
				&& (payeventRequestList.getPayeventRequestList().size() > 0)) {
			for (PayEventRequestType perType : payeventRequestList
					.getPayeventRequestList()) {

				if ((perType.getPayEventExtId() != null)) {

					for (CustomerPaymentEvent payevent : consumerBean
							.getPaymentEvents()) {

						if ((payevent.getExternalId() != null)
								&& (perType.getPayEventExtId().equals(payevent
										.getExternalId()))) {
							payevent.setCvv(perType.getCvv());
						}
					}

				}

			}
		}

	}
}
 
