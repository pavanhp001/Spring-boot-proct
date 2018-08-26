package com.A.V.jms;

import java.util.UUID;

import com.A.xml.cm.v4.CustomerManagementRequestResponse;
import com.A.xml.v4.ObjectFactory;
import com.A.xml.v4.OrderManagementRequestResponse;

public enum JMSTestHelper {

	INSTANCE;
	
	public OrderManagementRequestResponse getOrderByOrderNumber(final String id) {

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse cmrr = oFactory
				.createOrderManagementRequestResponse();
		cmrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("getOrder");

		if (id != null) {
			cmrr.getRequest().setOrderId(id);
		}

		return cmrr;
	}

	public CustomerManagementRequestResponse getCustomerType(final String id) {

		com.A.xml.cm.v4.ObjectFactory oFactory = new com.A.xml.cm.v4.ObjectFactory();
		CustomerManagementRequestResponse cmrr = oFactory
				.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory
				.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("getCustomerById");

		if (id != null) {
			cmrr.getRequest().setCustomerId(id);
		}

		return cmrr;

	}
}
