package com.AL.factory;

import java.util.Calendar;

import com.AL.ie.service.strategy.ArbiterFlowKey;
import com.AL.util.NotificationEvents;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.NotificationEventType;

public class NotificationEventFactory {

	private static final String PROCESS_BY_PROVIDER = "process line item to provider";
	private static final String OME_SOURCE = "ome";

	public static final NotificationEventType createProcessByProviderEvent(
			LineItemType lineItemType) {

		NotificationEventType notificationEvent = lineItemType
				.getNotificationEvents().addNewEvent();
		notificationEvent.setCode(NotificationEvents.processByProvider.getId());
		notificationEvent.setComment(PROCESS_BY_PROVIDER);
		notificationEvent.setUniqueId(ArbiterFlowKey.nextVal());
		notificationEvent.setAcknowledged(false);
		notificationEvent.setDateTimeStamp(Calendar.getInstance());
		notificationEvent.setSource(OME_SOURCE);
		notificationEvent.addReason(1);

		return notificationEvent;

	}
}
