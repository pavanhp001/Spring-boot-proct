/**
 *
 */
package com.AL.comm.manager.jms;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ConnectionFailedException;
import org.apache.log4j.Logger;

import com.AL.comm.manager.CommunicationManager;
import com.AL.comm.manager.jms.factory.JmsMessageFactory;
import com.AL.comm.manager.jms.strategy.ReceiveStrategy;
import com.AL.comm.manager.jms.strategy.SendStrategy;
import com.AL.comm.manager.jms.util.JMSConfigManager;
import com.AL.comm.manager.jms.util.TargetDestination;

/**
 *
 * @author ebthomas
 *
 */
public abstract class JMSCommunicationManagerBase implements
		CommunicationManager<Message, MessageListener> {


	private static final Map<String, String> EMPTY_MAP = new HashMap<String, String>();

	private static Logger logger = Logger
			.getLogger(JMSCommunicationManagerBase.class);

	public JMSCommunicationManagerBase() {
		super();
	}

	public Connection getConnection() {
		try {
			return JMSConfigManager.INSTANCE
					.getIndependentConnection(getNamespace());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void disconnect(Connection connection) {

		logger.debug("closing...");
		JMSConfigManager.INSTANCE.doDisconnect(connection);
		connection = null;
		logger.debug("closed");

	}

	public ConnectionFactory getJmsConnectionFactory(
			String jmsConnectionFactoryJndiName) throws Exception {
		ConnectionFactory jmsConnectionFactory = JMSConfigManager.INSTANCE
				.getConnectionFactory(jmsConnectionFactoryJndiName);

		return jmsConnectionFactory;
	}

	public void stop(TargetDestination jmsDest) {
		try {

			if (jmsDest != null) {
				jmsDest.stop();

				jmsDest = null;
			}
		} catch (Exception e) {
		}
	}

	// /////////////////////////////////////////////////////////////////////////
	// Internal worker methods

	public void listenAsync(TargetDestination jmsDest, MessageListener callback)
			throws Exception {
		ReceiveStrategy.setupAsynchConsumer(jmsDest, callback);

		log("listen() - Asynchronous listen on destination "
				+ jmsDest.toString());
	}

	public Message listen(String destName) throws Exception {
		return listenSync(destName);
	}

	//
	// This is a synchronous listen. The caller will block until
	// a message is received for the given destination
	//
	public Message listenSync(String destName) throws Exception {
		System.out.println("listen() - Synchronous listen on destination "
				+ destName);

		Connection connection = this.getConnection();
		Message message = null;
		try {

			TargetDestination jmsDest = JmsMessageFactory.createJMSDestination(
					JMSQueueType.queue, connection, destName);

			// Setup the consumer and block until a
			// message arrives for this destination

			System.out.println("listen() -  destination:" + jmsDest);
			//
			message = ReceiveStrategy.setupSynchConsumer(jmsDest, 0);
		} finally {
			disconnect(connection);
		}

		return message;
	}

	//
	// This is a synchronous listen. The caller will block until
	// a message is received for the given destination OR the
	// timeout value (in milliseconds) has been reached
	//
	public Message listenSync(String destName, int timeout) throws Exception {
		log("listen() - Synchronous listen on destination " + destName);

		Connection connection = getConnection();
		try {
			TargetDestination jmsDest = JmsMessageFactory.createJMSDestination(
					JMSQueueType.queue, connection, destName);

			// Setup the consumer and block until a
			// message arrives for this destination
			//
			return ReceiveStrategy.setupSynchConsumer(jmsDest, timeout);
		} finally {
			disconnect(connection);
		}
	}

	public void listenAsync(Destination dest, MessageListener callback)
			throws Exception {
		Connection connection = JMSConfigManager.INSTANCE
				.getIndependentConnection(this.getNamespace());
		Session s = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageConsumer c = s.createConsumer(dest);
		c.setMessageListener(callback);
	}

	public Message listenSync(Destination dest) throws Exception {
		Connection connection = JMSConfigManager.INSTANCE
				.getIndependentConnection(this.getNamespace());

		Message msg = null;
		Session s = null;
		try {
			s = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageConsumer c = s.createConsumer(dest);
			msg = c.receive();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return msg;
	}

	/**
	 * {@inheritDoc}
	 */
	public Message listenSync(TargetDestination dest) throws Exception {
		return null;
	}

	protected static void log(String s) {
		// if ( fLog )
		System.out.println(s);
	}

	public Message sendSync(String destName, String msg, int timeout)
			throws Exception {
		String responseName = null;

		return sendSync(destName, responseName, msg, timeout);
	}

	public static String getNameForDestination(Destination dest)
			throws JMSException {
		if (dest instanceof Queue) {
			return ((Queue) dest).getQueueName();
		} else if (dest instanceof Topic) {
			return ((Topic) dest).getTopicName();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void send(TargetDestination targetDestination, Message msg)
			throws Exception {
		// Make sure we have a message producer created for this destination
		SendStrategy.setupProducer(targetDestination);

		// Send the message for this destination
		SendStrategy.addMuleCorrelationId(msg);
		targetDestination.getProducer().send(msg);
		log("send() - message sent");

	}

	public void sendString(String targetQueue, String originalMsg, Map<String, String> map) {

		sendString(targetQueue, originalMsg, map, false);
	}

	private void sendString(String targetQueue, String originalMsg, Map<String, String> map, boolean useTargetQueueName) {

		Session session = null;
		MessageProducer producer = null;
		TargetDestination targetDestination = null;
		String namespace = getNamespace();
		String destName = null;
		
		if(!useTargetQueueName)
		{
			destName = JMSConfigManager.INSTANCE.getPhysicalDestination(
					getNamespace(), targetQueue);
			logger.debug(targetQueue + " translated to " + destName);
			
		}
		else
		{
			destName = targetQueue;
		}

		Connection connection = this.getConnection();
		logger.debug("Received connection");
		try {

			targetDestination = JmsMessageFactory.createJMSDestination(
					JMSQueueType.queue, connection, destName);
			session = targetDestination.getSession();

			Destination sendQueue = targetDestination.getDestination();
			producer = session.createProducer(sendQueue);
			targetDestination.setProducer(producer);

			TextMessage msg = session.createTextMessage();
			SendStrategy.addMuleCorrelationId(msg);
			msg.setText(originalMsg);

			populateJMSHeader(map, msg);

			producer.send(msg);
			logger.debug("Message sent complete");
		} catch (ConnectionFailedException ex) {

			logger.error("connection failed attempting restart");
			connection = JMSConfigManager.INSTANCE.restartConnection(namespace);
		} catch (Exception e) {

			logger.error(e);
		} finally {
			try {
				targetDestination.stop();
				disconnect(connection);

			} catch (Exception e) {
				logger.error(e);
			}
		}

	}

	/**
	 * Send a String message to a JMS TOPIC
	 *
	 * @param targetQueue
	 * @param originalMsg
	 */
	public void sendBroadcastMap(String targetQueue, Map<String,Object> originalMsg, Map<String, String> map) {
		logger.debug("Sending MapMessage");
		Session session = null;
		MessageProducer producer = null;
		TargetDestination targetDestination = null;
		String namespace = getNamespace();

		String destName = JMSConfigManager.INSTANCE.getPhysicalDestination(
				getNamespace(), targetQueue);
		logger.debug(targetQueue + " translated to " + destName);

		Connection connection = this.getConnection();
		try {

			targetDestination = JmsMessageFactory.createJMSDestination(
					JMSQueueType.queue, connection, destName);
			session = targetDestination.getSession();

			Destination sendQueue = targetDestination.getDestination();
			producer = session.createProducer(sendQueue);
			targetDestination.setProducer(producer);

			MapMessage mapMsg = session.createMapMessage();
			populateMsg(originalMsg,mapMsg);
			SendStrategy.addMuleCorrelationId(mapMsg);
			populateJMSHeader(map, mapMsg);
			/*TextMessage msg = session.createTextMessage();
			SendStrategy.addMuleCorrelationId(msg);
			populateJMSHeader(map, msg);
			msg.setText(originalMsg);*/
			producer.send(mapMsg);

		} catch (ConnectionFailedException ex) {

			logger.error("connection failed attempting restart");
			connection = JMSConfigManager.INSTANCE.restartConnection(namespace);
		} catch (Exception e) {

			logger.error(e);
		} finally {
			try {
				targetDestination.stop();
				disconnect(connection);

			} catch (Exception e) {
				logger.error(e);
			}
		}

	}

	/**
	 * Helper method to populate MapMessage from provided map
	 * @param originalMsg
	 * @param mapMsg
	 * @throws JMSException
	 */
	private void populateMsg(Map<String, Object> originalMsg, MapMessage mapMsg) throws JMSException {
		for(String key : originalMsg.keySet()){
			if(originalMsg.get(key) instanceof String){
				mapMsg.setString(key, (String)originalMsg.get(key));
			}else if(originalMsg.get(key) instanceof Integer){
				mapMsg.setInt(key, (Integer)originalMsg.get(key));
			}else if(originalMsg.get(key) instanceof Long){
				mapMsg.setLong(key, (Long)originalMsg.get(key));
			}else if(originalMsg.get(key) instanceof Double){
				mapMsg.setDouble(key, (Double)originalMsg.get(key));
			}else if(originalMsg.get(key) instanceof Byte){
				mapMsg.setByte(key, (Byte)originalMsg.get(key));
			}else if(originalMsg.get(key) instanceof Short){
				mapMsg.setShort(key, (Short)originalMsg.get(key));
			}else if(originalMsg.get(key) instanceof Boolean){
				mapMsg.setBoolean(key, (Boolean)originalMsg.get(key));
			}else if(originalMsg.get(key) instanceof Float){
				mapMsg.setFloat(key, (Float)originalMsg.get(key));
			}else if(originalMsg.get(key) instanceof Character){
				mapMsg.setChar(key, (Character)originalMsg.get(key));
			}else{
				mapMsg.setObject(key, originalMsg.get(key));
			}
		}
	}

	/**
	 * Send a String message to a JMS TOPIC
	 *
	 * @param targetQueue
	 * @param originalMsg
	 */
	public void sendBroadcastString(String targetQueue, String originalMsg, Map<String, String> map) {

		Session session = null;
		MessageProducer producer = null;
		TargetDestination targetDestination = null;
		String namespace = getNamespace();

		String destName = JMSConfigManager.INSTANCE.getPhysicalDestination(
				getNamespace(), targetQueue);
		logger.debug(targetQueue + " translated to " + destName);

		Connection connection = this.getConnection();
		try {

			targetDestination = JmsMessageFactory.createJMSDestination(
					JMSQueueType.topic, connection, destName);
			session = targetDestination.getSession();

			Destination sendQueue = targetDestination.getDestination();
			producer = session.createProducer(sendQueue);
			targetDestination.setProducer(producer);

			TextMessage msg = session.createTextMessage();
			SendStrategy.addMuleCorrelationId(msg);
			populateJMSHeader(map, msg);
			msg.setText(originalMsg);
			producer.send(msg);

		} catch (ConnectionFailedException ex) {

			logger.error("connection failed attempting restart");
			connection = JMSConfigManager.INSTANCE.restartConnection(namespace);
		} catch (Exception e) {

			logger.error(e);
		} finally {
			try {
				targetDestination.stop();
				disconnect(connection);

			} catch (Exception e) {
				logger.error(e);
			}
		}

	}

	public void sendBroadcast(String result, Object obj, Map<String, String> map) {

		if (obj instanceof String) {
			sendBroadcastString(result, (String) obj, map);
		}
	}

	public void send(String result, Object obj) {

		send(  result,   obj,  EMPTY_MAP);
	}

	public void send(String result, Object obj, boolean useTargetQueueName) {

		send(  result,   obj,  EMPTY_MAP, useTargetQueueName);
	}
	

	public void send(String result, Object obj, Map<String, String> map) {
		send(result, obj, map, false );
	}

		
	private void send(String result, Object obj, Map<String, String> map,  boolean useTargetQueueName) {

		if (obj instanceof String) {
			sendString(result, (String) obj, map, useTargetQueueName);
			return;
		}

		Message message = null;

		if (obj instanceof Message) {
			logger.debug("sending instance of message");
			message = (Message) obj;
		}

		if (message == null) {
			logger.debug("unable to send message");

			return;
		}
		Session session = null;
		MessageProducer producer = null;
		Connection connection = null;
		Destination jmsReplyTo = null;
		Queue forwardTo = null;
		boolean connectionFoundInCache = false;
		String namespace = "";
		String correlationId = "";

		try {
			jmsReplyTo = message.getJMSReplyTo();
			forwardTo = (Queue) jmsReplyTo;
			correlationId = message.getJMSCorrelationID();

			if (jmsReplyTo != null) {
				String destination = forwardTo.getQueueName();

				logger.debug("jms reply to queue name" + destination);

				// ********************************************
				// session = communicationManager.getSession();
				// ********************************************

				logger.debug("getting namespace");

				namespace = getNamespace();

				logger.debug("getting connection on namespace:" + namespace);

				connection = this.getConnection();

				if (connection != null) {
					logger.debug("connection found");
					connectionFoundInCache = true;

				} else {
					for (int i = 0; i < 10; i++)
						logger.error("+++++++++ ERROR CREATING JMS CONNECTION FACTORY ++++++++++++++++++++");
					throw new IllegalArgumentException(
							"unable to create connection factory for namespace "
									+ namespace);
				}

				sendReplyMessage(result, correlationId, jmsReplyTo, forwardTo,
						connection, session, producer);
			} else {
				logger.warn("using receiver sender without reply configured:"
						+ correlationId);
			}
		} catch (ConnectionFailedException ex) {
			try {
				logger.error("connection failed attempting restart and resend");
				connection = JMSConfigManager.INSTANCE
						.restartConnection(namespace);

				sendReplyMessage(result, correlationId, jmsReplyTo, forwardTo,
						connection, session, producer);

			} catch (JMSException e) {
				logger.error("unable to restart connection and send message"
						+ jmsReplyTo);
			}
		} catch (Exception e) {
			logger.error("unable to setup reply " + e);
		} finally {

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

					if ((connection != null) && (!connectionFoundInCache)) {
						logger.debug("cleaning up mdb receiver sender connection");
						connection.stop();
						connection.close();

					}
				}
			} catch (Exception e) {
				logger.error("unable to properly close consumer:" + e);
			}

			disconnect(connection);

		}
	}

	private void sendReplyMessage(String result, String correlationId,
			Destination jmsReplyTo, Queue forwardTo, Connection connection,
			Session session, MessageProducer producer) throws JMSException {
		logger.debug("creating session");

		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// ********************************************
		// forwardTo = session.createQueue(destination);
		logger.debug("creating producer");
		producer = session.createProducer(forwardTo);
		logger.debug("creating text message");
		TextMessage forward = session.createTextMessage(result);

		logger.debug("setting jms correlation Id");
		forward.setJMSCorrelationID(correlationId);
		logger.debug("sending message");
		logger.debug("message sent");
		producer.send(forward);

		logger.debug("producer sent message:" + jmsReplyTo);
	}

	public abstract String getNamespace();

	public void populateJMSHeader(final Map<String, String> map,
			final MapMessage message) {
		if ((map == null) || (map.size() == 0)) {
			return;
		}

		Collection<String> keySet = map.keySet();

		for (String key : keySet) {

			String value = map.get(key);

			try {

				message.setStringProperty(key, value);
				logger.debug("added key:" + key + " value:" + value
						+ " to the message header ");

			} catch (Exception e) {
				StringBuilder sb = new StringBuilder();
				sb.append(message.toString())
						.append(" unable to set property key:").append(key)
						.append(" value:").append(value).append(" ")
						.append(e.getMessage());
				logger.warn(sb.toString());
			}
		}

	}


	public void populateJMSHeader(final Map<String, String> map,
			final TextMessage message) {
		logger.debug("Populate JMS headers");
		if ((map == null) || (map.size() == 0)) {
			return;
		}

		Collection<String> keySet = map.keySet();

		for (String key : keySet) {

			String value = map.get(key);

			try {

				message.setStringProperty(key, value);
				logger.debug("added key:" + key + " value:" + value
						+ " to the message header ");

			} catch (Exception e) {
				StringBuilder sb = new StringBuilder();
				sb.append(message.toString())
						.append(" unable to set property key:").append(key)
						.append(" value:").append(value).append(" ")
						.append(e.getMessage());
				logger.warn(sb.toString());
			}
		}

	}

	public void send(String target, Map<String, Object> msg,
			Map<String, String> map) {
		if(msg != null){
			sendBroadcastMap(target, msg, map);
		}
	}

}
