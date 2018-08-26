package com.A.ui.service.V;

import java.util.UUID;

import com.A.ui.service.event.NotificationEventIdentifier;
import com.A.util.DateUtil;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.NotificationEventType;
import com.A.xml.v4.ObjectFactory;

public enum EventService {

	INSTANCE;

	//TODO: Add to properties lookup in DB
	private static final String DEFAULT_SOURCE = "V-client-api";
	 

	private ObjectFactory oFactory = new ObjectFactory();

	public boolean isProcessByProviderEvtNotification(final LineItemType lit) {

		if (lit.getNotificationEvents() == null) {
			lit.setNotificationEvents(oFactory
					.createNotificationEventCollectionType());
		}

		for (NotificationEventType net : lit.getNotificationEvents().getEvent()) {

			if (net.getCode()==NotificationEventIdentifier.processByProvider.getCode()) {
				return Boolean.TRUE;
			}
		}

		return Boolean.FALSE;
	}

	public void addProcessByProvider(final LineItemType lit) {

		if (!isProcessByProviderEvtNotification(lit)) {

			NotificationEventType net = oFactory.createNotificationEventType();
			net.setAcknowledged(false);
			net.setComment(NotificationEventIdentifier.processByProvider.getDescription());
			net.setUniqueId(UUID.randomUUID().toString());
			net.setDateTimeStamp(DateUtil.getCurrentXMLDate());
			net.setSource(DEFAULT_SOURCE);

			net.setCode(NotificationEventIdentifier.processByProvider.getCode());
			net.getReason().add(NotificationEventIdentifier.processByProvider.getCode());

			lit.getNotificationEvents().getEvent().add(net);
		}
	}
	// <ac:notificationEvents>
	// <ac:event dateTimeStamp="2001-12-31T12:00:00" source="" uniqueId="">
	// <ac:code>1</ac:code>
	// <ac:reason>processByProvider</ac:reason>
	// <ac:comment>processByProvider</ac:comment>
	// <ac:acknowledged>false</ac:acknowledged>
	// </ac:event>
	// </ac:notificationEvents>

}
