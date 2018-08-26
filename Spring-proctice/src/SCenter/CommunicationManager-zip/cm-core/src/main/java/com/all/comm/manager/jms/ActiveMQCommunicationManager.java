/**
 *
 */
package com.AL.comm.manager.jms;

import java.util.Map;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;
import org.apache.activemq.broker.BrokerService;
import org.apache.log4j.Logger;
import com.AL.cm.util.UUIDGenerator;
import com.AL.comm.manager.jms.factory.JmsMessageFactory;
import com.AL.comm.manager.jms.util.JMSConfigManager;
import com.AL.comm.manager.jms.util.TargetDestination;

/**
 * @author ebthomas
 * 
 */
public class ActiveMQCommunicationManager extends JMSCommunicationManagerBase {

	private static Logger logger = Logger
			.getLogger(ActiveMQCommunicationManager.class);

	public final String ACTIVEMQ_CONNECTION_FACTORY_CLASS = "org.apache.activemq.ActiveMQConnectionFactory";
	private String namespace = "default";
	private String MSG_SELECTOR = "designation";
	private static final String RTS_MSG_SELECTOR_PROPERTY_KEY = "designation";

	public ActiveMQCommunicationManager(String namespace) {
		super();
		this.setNamespace(namespace);
		// setupActiveMQBroker();
		cacheConnectionFactory();
	}

	public void cacheConnectionFactory() {
		boolean isStandalone = !JMSConfigManager.INSTANCE
				.isRunningInContainer();
		if (isStandalone) {
			getConnectionFactory();
		} else {
			JMSConfigManager.INSTANCE.getConnectionFactory(this.getNamespace());
		}
	}

	public void setupActiveMQBroker() {
		try {
			// This message broker is embedded
			BrokerService broker = new BrokerService();
			broker.setPersistent(false);
			broker.setUseJmx(false);
			broker.addConnector(JMSConfigManager.INSTANCE.getNamespace(
					this.getNamespace()).getMessageBrokerUrl());

			broker.start();
		} catch (Exception e) {
			// Handle the exception appropriately
		}
	}

	public ConnectionFactory getConnectionFactory() {

		ConnectionFactory jmsConnectionFactory = JMSConfigManager.INSTANCE
				.getConnectionFactory(this.getNamespace());
		String messageBrokerUrl = JMSConfigManager.INSTANCE.getNamespace(
				this.getNamespace()).getMessageBrokerUrl();

		if (jmsConnectionFactory == null) {

			throw new IllegalArgumentException(
					"unable to create connection factory:" + messageBrokerUrl);

		}
		return jmsConnectionFactory;
	}

	public Message sendSync(String targetQueue, String originalMsg, int timeout)
			throws Exception {

		return sendSync(targetQueue, originalMsg, timeout, null);

	}
	
	public Message sendSync(String targetQueue, String originalMsg, 
			int timeout, boolean useTargetQueueName)
			throws Exception {
		return sendSync(targetQueue, originalMsg, timeout, null, useTargetQueueName);
	}

	public Message sendSync(String targetQueue, String originalMsg,
			int timeout, Map<String, String> map) throws Exception {
		return sendSync(targetQueue, originalMsg, timeout, map, false);
	}
	
	public Message sendSync(String targetQueue, String originalMsg,
			int timeout, Map<String, String> map, boolean useTargetQueueName) throws Exception {

		TargetDestination targetDestination = null;
		TemporaryQueue tempQueue = null;
		String destName = null;
		if(useTargetQueueName) {
			destName = targetQueue;
		} else {
			destName = JMSConfigManager.INSTANCE.getPhysicalDestination(
				getNamespace(), targetQueue);
			logger.debug(targetQueue + " translated to " + destName);
		}
		Session session = null;
		MessageProducer producer = null;
		Connection connection = null;
		Destination sendQueue = null;

		try {

			connection = this.getConnection();

			targetDestination = JmsMessageFactory.createJMSDestination(
					JMSQueueType.queue, connection, destName);

			sendQueue = targetDestination.getDestination();
			producer = targetDestination.getSession().createProducer(sendQueue);
			targetDestination.setProducer(producer);

			TextMessage message = targetDestination.getSession()
					.createTextMessage();
			message.setText(originalMsg);

			// Temporary Queue
			tempQueue = JMSConfigManager.INSTANCE
					.createTemporaryQueue(targetDestination.getSession());
			targetDestination.setTempQueue(tempQueue);

			MessageConsumer consumer = targetDestination.getSession()
					.createConsumer(tempQueue);
			targetDestination.setConsumer(consumer);
			message.setJMSReplyTo(tempQueue);
			message.setJMSCorrelationID(UUIDGenerator.generate());
			populateJMSHeader(map, message);
			// Determine if message selector should be added to message header
			String qSelectorFilter = resolveQSelectorFilter(targetQueue);
			if (qSelectorFilter != NO_SELECTOR_CONFIGURED) {
				message.setStringProperty(RTS_MSG_SELECTOR_PROPERTY_KEY,
						qSelectorFilter);
				logger.debug(targetQueue + " selector filter added:"
						+ qSelectorFilter);
			}

			logger.debug("sending message");
			Long now = System.currentTimeMillis();
			producer.send(message);

			logger.info("message sent, elapsed time: "	+ (System.currentTimeMillis() - now));

			now = System.currentTimeMillis();
			logger.debug("waiting for message reply");
			Message result = consumer.receive(timeout);
			logger.info("reply completed: elapsed time:" + (System.currentTimeMillis() - now));

			return result;

		} catch (Exception e) {

			logger.error(e);
			return null;
		}

		finally {
			tempQueue = null;

			if (targetDestination != null) {
				targetDestination.stop();
			}

			targetDestination = null;

			logger.debug("cleaning up mdb receiver sender producer");
			try {

				if (producer != null) {
					producer.close();
				}
			} catch (Exception e) {
				logger.error("unable to properly close producer");
			}

			logger.debug("cleaning up mdb receiver sender session");
			try {
				if (session != null) {

					session.close();
				}
			} catch (Exception e) {
				logger.error("unable to properly close consumer:" + e);
			}

			disconnect(connection);
		}

	}

	private static final String NO_SELECTOR_CONFIGURED = null;

	private String resolveQSelectorFilter(String logicalName) {

		// TODO: Move this to external file
		if ((logicalName != null) && (logicalName.length() != 0)) {

			if (logicalName.indexOf(".rts.") != -1) {
				return "attsti";// DEFAULT
			} else if (".att.".indexOf(logicalName) != -1) {
				return "attsti";
			} else if (".dish.".indexOf(logicalName) != -1) {
				return "dish";
			} else if (".verizon.".indexOf(logicalName) != -1) {
				return "verizon";
			}
		}

		return NO_SELECTOR_CONFIGURED;
	}

	public Message sendSync(Object msg) throws Exception {
		throw new IllegalArgumentException("unsupported operation exception");
	}

	/**
	 * {@inheritDoc}
	 */
	public Message sendSync(Object msg, Map<String, String> map)
			throws Exception {
		throw new IllegalArgumentException("unsupported operation exception");
	}

	/**
	 * {@inheritDoc}
	 */
	public void reply(String destName, String messageText) throws Exception {
		// this.send(destName, messageText);

	}

	/**
	 * {@inheritDoc}
	 */
	public Message sendSync(String destName, String responseName, String msg,
			int timeout, Map<String, String> map) throws Exception {

		throw new IllegalArgumentException("unsupported operation exception");
	}

	/**
	 * {@inheritDoc}
	 */
	public Message sendSync(String destName, String responseName, String msg,
			int timeout) throws Exception {

		throw new IllegalArgumentException("unsupported operation exception");
	}

	public Message sendSync(String targetQueue, TextMessage msg, int timeout)
			throws Exception {

		return sendSync(targetQueue, msg, timeout, null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.AL.comm.manager.CommunicationManager#sendSync(java.lang.String
	 * , javax.jms.Message, int)
	 */
	public Message sendSync(String targetQueue, TextMessage msg, int timeout,
			Map<String, String> map) throws Exception {

		TargetDestination targetDestination = null;
		TemporaryQueue tempQueue = null;
		String destName = JMSConfigManager.INSTANCE.getPhysicalDestination(
				getNamespace(), targetQueue);

		Session session = null;
		MessageProducer producer = null;
		Connection connection = null;
		Destination sendQueue = null;

		try {

			connection = this.getConnection();

			targetDestination = JmsMessageFactory.createJMSDestination(
					JMSQueueType.queue, connection, destName);

			sendQueue = targetDestination.getDestination();
			producer = targetDestination.getSession().createProducer(sendQueue);
			targetDestination.setProducer(producer);

			TextMessage message = targetDestination.getSession()
					.createTextMessage();
			message.setText(msg.getText());

			String msgSelector = msg.getStringProperty(MSG_SELECTOR);
			if (msgSelector != null && !msgSelector.equals("")) {
				message.setStringProperty(MSG_SELECTOR, msgSelector);
			} else {
				logger.error("message selector not set JMS property key = designation expected");
			}

			// Temporary Queue
			tempQueue = JMSConfigManager.INSTANCE
					.createTemporaryQueue(targetDestination.getSession());
			targetDestination.setTempQueue(tempQueue);

			MessageConsumer consumer = targetDestination.getSession()
					.createConsumer(tempQueue);
			targetDestination.setConsumer(consumer);
			message.setJMSReplyTo(tempQueue);
			message.setJMSCorrelationID(UUIDGenerator.generate());
			populateJMSHeader(map, msg);
			logger.debug("sending message");
			producer.send(message);

			logger.debug("message sent");

			Long now = System.currentTimeMillis();
			logger.debug("waiting for message reply");
			Message result = consumer.receive(timeout);
			logger.debug("reply completed: elapsed time:"
					+ (System.currentTimeMillis() - now));

			return result;

		} catch (Exception e) {

			logger.error(e);
			return null;
		}

		finally {
			tempQueue = null;

			if (targetDestination != null) {
				targetDestination.stop();
			}

			targetDestination = null;

			logger.debug("cleaning up mdb receiver sender producer");
			try {

				if (producer != null) {
					producer.close();
				}
			} catch (Exception e) {
				logger.error("unable to properly close producer");
			}

			logger.debug("cleaning up mdb receiver sender session");
			try {
				if (session != null) {

					session.close();
				}
			} catch (Exception e) {
				logger.error("unable to properly close consumer:" + e);
			}

			disconnect(connection);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Message createMessage(Object obj) throws Exception {
		Connection connection = this.getConnection();
		return JmsMessageFactory.createMessage(connection, obj);
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void listen(String target, MessageListener callback)
			throws Exception {
		// TODO Auto-generated method stub

	}

	

	

}
