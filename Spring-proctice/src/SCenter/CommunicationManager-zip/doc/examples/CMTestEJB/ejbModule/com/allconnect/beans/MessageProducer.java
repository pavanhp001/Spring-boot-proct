package com.AL.beans;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

/**
 * Session Bean implementation class MessageProducer
 */
@Stateless
public class MessageProducer implements MessageProducerLocal {

	@Resource(mappedName = "java:/activemq/QueueConnectionFactory")
	private static QueueConnectionFactory factory;

	@Resource(mappedName = "activemq/queue/inbound")
	private static Queue queue;
    /**
     * Default constructor. 
     */
    public MessageProducer() {
    }

	@Override
	public void sendMessage() {
		try {
			QueueConnection qcon = factory.createQueueConnection();
			QueueSession qsession = qcon.createQueueSession(true, 0);
			QueueSender qsender = qsession.createSender(queue);
			TextMessage message = qsession.createTextMessage();
			message.setText("!!!!!!!New TestMessage processed by MessageHandlerImpl.java!!!!!");
			qsender.send(message);
			qsender.close();
			qsession.close();
			qcon.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

}
