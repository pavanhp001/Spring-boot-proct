package com.A.V.jms;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.A.V.gateway.CustomerClient;
import com.A.V.gateway.OrderClient;
import com.A.V.gateway.util.JaxbUtil;
import com.A.V.gateway.jms.CustomerClientJMS;
import com.A.V.gateway.jms.OrderClientJMS;
import com.A.xml.cm.v4.CustomerManagementRequestResponse;
import com.A.xml.v4.ObjectFactory;
import com.A.xml.v4.OrderManagementRequestResponse;

/**
 *
 */

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/V-client-api-app-context.xml" })
public class JMSClientTest {

	private static final JaxbUtil<OrderManagementRequestResponse> util = new JaxbUtil<OrderManagementRequestResponse>();

	@Test
	public void testOrderSendViaJMS() throws Exception {

		OrderManagementRequestResponse omrr = getOrderByOrderNumber("7103");

		OrderClient jmsClient = new OrderClientJMS();
		OrderManagementRequestResponse omrrFromQueue = jmsClient.send(omrr);

		assertNotNull("RESPONSE---->>>>>>"
				+ util.toString(omrrFromQueue,
						OrderManagementRequestResponse.class));

	}

	@Test
	public void testCustomerSendViaJMS() throws Exception {

		CustomerManagementRequestResponse cmrr = getCustomerType("7033");

		CustomerClient jmsClient = new CustomerClientJMS();
		CustomerManagementRequestResponse omrrFromQueue = jmsClient.send(cmrr);

		JaxbUtil<CustomerManagementRequestResponse> utilCust = new JaxbUtil<CustomerManagementRequestResponse>();

		assertNotNull("RESPONSE---->>>>>>"
				+ utilCust.toString(omrrFromQueue,
						CustomerManagementRequestResponse.class));

	}

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
