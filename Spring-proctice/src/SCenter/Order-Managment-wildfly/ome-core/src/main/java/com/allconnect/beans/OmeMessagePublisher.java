package com.AL.beans;

import javax.jms.Message;
import javax.jms.MessageListener;
import org.apache.log4j.Logger;
import com.AL.comm.manager.CommunicationManager;
import com.AL.comm.manager.jms.util.JMSConfigManager;
import com.AL.ome.OmePricingMessageSender;

public class OmeMessagePublisher {
	private static Logger LOGGER = Logger
			.getLogger(OmePricingMessageSender.class);
	private static String namespace = "ome/ome";
	private static CommunicationManager<Message, MessageListener> communicationManager = JMSConfigManager.INSTANCE
			.createCommunicationManager(namespace);
	// private static final int TIME_OUT = 15000;

	private static String logicalQueueName = "endpoint.ome.out";

	public static void publishMessage(String response) {
		LOGGER.debug("Publishing message to outbound queue(Logical Queue Name in ome.properties) : "
				+ logicalQueueName);
		// Message message;
		try {
			// message = communicationManager.sendSync( logicalQueueName,
			// response, TIME_OUT );
			communicationManager.send(logicalQueueName, response);
		} catch (Exception e) {
			LOGGER.debug("Exception thrown while publishing message :"
					+ e.getMessage());
			LOGGER.error(e.getStackTrace());
		}
	}
}
