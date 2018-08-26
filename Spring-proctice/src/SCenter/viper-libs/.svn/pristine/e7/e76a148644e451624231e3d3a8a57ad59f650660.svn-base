package com.ac.V.dao.broadcast;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.log4j.Logger;

import com.A.comm.manager.CommunicationManager;
import com.A.comm.manager.jms.util.JMSConfigManager;

public class BroadcastThread implements Runnable {

    private static final Logger logger = Logger.getLogger(BroadcastThread.class);

    private static final String BROADCAST_NAMESPACE = "broadcast";
    private static final String BROADCAST_LOGICAL_NAME = "endpoint.ome.out";
    private static final CommunicationManager<Message, MessageListener> broadcastCommMngr = JMSConfigManager.INSTANCE.createCommunicationManager(BROADCAST_NAMESPACE);

    private BroadcastMessage broadcastMessage;
    private String tName;

    public BroadcastThread(BroadcastMessage msg,String name) {
	this.broadcastMessage = msg;
	this.tName = name;
    }



    public void run() {
	logger.info("Executing broadcast from thread = " + tName +" Broadcast ExtId = " + broadcastMessage.getId());
	if (broadcastMessage != null) {
		try {
		    long start = System.currentTimeMillis();
			if(broadcastMessage.getMessageHeaders() != null){
				logger.info("Broadcast Message header :" + broadcastMessage.getMessageHeaders().toString());
			}
			logger.debug("Broadcasted Message : " + broadcastMessage.getBroadcastMsg());
			broadcastCommMngr.send(BROADCAST_LOGICAL_NAME, broadcastMessage.getBroadcastMsg(),broadcastMessage.getMessageHeaders());
			logger.info("Broadcast message took : " + (System.currentTimeMillis() - start) + "ms");
		} catch (Exception e) {
			logger.error("exception while broadcasting customer message to queue : ",e);
		}

	}
	logger.info("Completed executing broadcast from thread = " + tName);
    }



    public BroadcastMessage getBroadcastMessage() {
        return broadcastMessage;
    }

}
