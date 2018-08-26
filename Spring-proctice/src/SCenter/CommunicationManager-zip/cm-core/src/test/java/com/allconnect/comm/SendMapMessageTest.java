package com.AL.comm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.comm.manager.CommunicationManager;
import com.AL.comm.manager.jms.util.JMSConfigManager;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class SendMapMessageTest {
	private static final Map<String, String> EMPTY_MAP = new HashMap<String, String>();

	@Test
	public void testMessageDeliverytest() throws Exception {
		// Start sending messages
		sendMessage();
		System.out.println("completed sending all messages");
	}

	public void sendMessage() throws JMSException, Exception {
		final String namespace = "leadProvisioningQueue";

		EMPTY_MAP.put("providers", "2314635");
		final String logicalQueueName = "endpoint.leadprovisioning.q";

		CommunicationManager<Message, MessageListener> communicationManager = JMSConfigManager.INSTANCE
				.createCommunicationManager(namespace);
		System.out.println("sending..." + System.currentTimeMillis());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("OrderId", 123);
		map.put("LineItemId", 234);
		map.put("ProviderId", "222");
		communicationManager.send(logicalQueueName, map, EMPTY_MAP);
	}

}
