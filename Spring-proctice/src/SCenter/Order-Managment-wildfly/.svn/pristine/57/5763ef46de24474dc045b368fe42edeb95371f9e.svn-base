package com.AL.util.messaging.impl;

import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import org.apache.log4j.Logger;
import com.AL.comm.manager.CommunicationManager;
import com.AL.comm.manager.jms.util.JMSConfigManager;

public class JMSBroadcastUtil {
	private static Logger logger = Logger.getLogger(JMSBroadcastUtil.class);

	private static String BROADCAST_NAMESPACE = "ome/broadcast";
	private static String BROADCAST_LOGICAL_NAME = "endpoint.ome.out";
	private static CommunicationManager<Message, MessageListener> broadcastCommMngr = JMSConfigManager.INSTANCE
			.createCommunicationManager(BROADCAST_NAMESPACE);

	/**
	 * Utility method which is using Communication Manager to send message to
	 * JMS queue based on configuration in "broadcast.properties" file.
	 *
	 * @param message
	 */
	public static void sendMessage(String message, final Map<String, String> map) {
		if (message != null) {
			try {
			    	long start = System.currentTimeMillis();
				logger.debug("sending broadcast..");
				if(map != null){
					logger.debug("Broadcast Message header :" + map.toString());
				}
				logger.debug("Broadcasted Message : " + message) ;
				//broadcastCommMngr.sendBroadcast(BROADCAST_LOGICAL_NAME, message, map);
				broadcastCommMngr.send(BROADCAST_LOGICAL_NAME, message, map);
				logger.info("Broadcast message took : " + (System.currentTimeMillis() - start) + "ms");
			} catch (Exception e) {
				logger.error(
						"Got exception while broadcasting message to OME outbound queue : ",
						e);
			}
		}
	}
}
