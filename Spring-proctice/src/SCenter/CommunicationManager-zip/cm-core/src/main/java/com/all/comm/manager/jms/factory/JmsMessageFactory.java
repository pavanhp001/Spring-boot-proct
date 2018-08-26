/**
 *
 */
package com.AL.comm.manager.jms.factory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageFormatException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
//import org.mule.api.transport.OutputHandler;
//import org.mule.transport.jms.JmsConstants;
import com.AL.cm.util.ClassUtil;
import com.AL.comm.manager.jms.JMSQueueType;
import com.AL.comm.manager.jms.util.TargetDestination;

/**
 * @author ebthomas
 *
 */
public class JmsMessageFactory {

	private static final Logger logger = Logger.getLogger(JmsMessageFactory.class);
	public static Message createMessage(final Connection connection,
			final Object object) throws Exception {
		boolean fTransacted = true;
		int ackMode = Session.AUTO_ACKNOWLEDGE;

		// Create the new destination and store it
		//
		Session session = connection.createSession(fTransacted, ackMode);

		if (object instanceof Message) {
			return (Message) object;
		} else if (object instanceof String) {
			return session.createTextMessage((String) object);
		} else if (object instanceof Map) {
			MapMessage mMsg = session.createMapMessage();
			Map src = (Map) object;

			for (Iterator i = src.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				mMsg.setObject(entry.getKey().toString(), entry.getValue());
			}

			return mMsg;
		} else if (object instanceof InputStream) {
			StreamMessage sMsg = session.createStreamMessage();
			InputStream temp = (InputStream) object;

			byte[] buffer = new byte[4096];
			int len;

			try {
				while ((len = temp.read(buffer)) != -1) {
					sMsg.writeBytes(buffer, 0, len);
				}
			} catch (IOException e) {
				throw new JMSException(
						"Failed to read input stream to create a stream message: "
								+ e);
			}

			return sMsg;
		} else if (object instanceof List) {
			StreamMessage sMsg = session.createStreamMessage();
			List list = (List) object;
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Object o = iter.next();
				validateStreamMessageType(o);
				sMsg.writeObject(o);
			}
			return sMsg;
		} else if (object instanceof byte[]) {
			BytesMessage bMsg = session.createBytesMessage();
			bMsg.writeBytes((byte[]) object);
			return bMsg;
		} else if (object instanceof Serializable) {
			ObjectMessage oMsg = session.createObjectMessage();
			oMsg.setObject((Serializable) object);
			return oMsg;
		}
		// else if ( object instanceof OutputHandler )
		// {
		// ByteArrayOutputStream output = new ByteArrayOutputStream();
		// try
		// {
		// ( (OutputHandler) object ).write( null, output );
		// }
		// catch ( IOException e )
		// {
		// JMSException j = new JMSException(
		// "Could not serialize OutputHandler." );
		// j.initCause( e );
		// throw j;
		// }
		//
		// BytesMessage bMsg = session.createBytesMessage();
		// bMsg.writeBytes( (byte[]) output.toByteArray() );
		// return bMsg;
		// }
		else {
			throw new JMSException(
					"Source was not of a supported type, data must be Serializable, String, byte[], Map or InputStream, "
							+ "but was "
							+ ClassUtil.getShortClassName(object, "<null>"));
		}

	}

	public static Message createJMSMessage(Serializable obj, Session session)
			throws Exception {
		if (obj instanceof String) {
			TextMessage textMsg = session.createTextMessage();
			textMsg.setText((String) obj);
			return textMsg;
		} else {
			ObjectMessage objMsg = session.createObjectMessage();
			objMsg.setObject(obj);
			return objMsg;
		}
	}

	public static Destination createDestination(Class<?> type, String name,
			Session session) {
		// Create a topic or queue as specified
		//

		if (type.getName().equals("javax.jms.Queue")) {
			try {
				logger.warn("creating queue=" + name);
				return session.createQueue(name);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else {
			try {
				logger.debug("Creating topic="+name);
				return session.createTopic(name);
			} catch (JMSException e) {
				e.printStackTrace();
				return null;
			}
		}

	}

	protected static boolean fLog = false;

	// /////////////////////////////////////////////////////////////////////////
	// Public API methods

	public MapMessage createMapMessage(TargetDestination jmsDest,
			String destName) throws Exception {
		return jmsDest.getSession().createMapMessage();
	}

	protected static void validateStreamMessageType(Object candidate)
			throws MessageFormatException {
		if (candidate instanceof Boolean || candidate instanceof Byte
				|| candidate instanceof Short || candidate instanceof Character
				|| candidate instanceof Integer || candidate instanceof Long
				|| candidate instanceof Float || candidate instanceof Double
				|| candidate instanceof String || candidate instanceof byte[]) {
			return;
		}

		throw new MessageFormatException(
				String.format(
						"Invalid type passed to StreamMessage: %s . Allowed types are: "
								+ "Boolean, Byte, Short, Character, Integer, Long, Float, Double,"
								+ "String and byte[]",
						ClassUtil.getShortClassName(candidate, "null")));
	}

	public static byte[] toByteArray(Message message, String jmsSpec,
			String encoding) throws JMSException, IOException {
		if (message instanceof BytesMessage) {
			BytesMessage bMsg = (BytesMessage) message;
			bMsg.reset();

			// if ( JmsConstants.JMS_SPECIFICATION_11.equals( jmsSpec ) )
			// {
			// long bmBodyLength = bMsg.getBodyLength();
			// if ( bmBodyLength > Integer.MAX_VALUE )
			// {
			// throw new JMSException(
			// "Size of BytesMessage exceeds Integer.MAX_VALUE; "
			// + "please consider using JMS StreamMessage instead" );
			// }
			//
			// if ( bmBodyLength > 0 )
			// {
			// byte[] bytes = new byte[(int) bmBodyLength];
			// bMsg.readBytes( bytes );
			// return bytes;
			// }
			// else
			// {
			// return ArrayUtils.EMPTY_BYTE_ARRAY;
			// }
			// }
			// else
			{
				ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
				byte[] buffer = new byte[4096];
				int len;

				while ((len = bMsg.readBytes(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}

				if (baos.size() > 0) {
					return baos.toByteArray();
				} else {
					return ArrayUtils.EMPTY_BYTE_ARRAY;
				}
			}
		} else if (message instanceof StreamMessage) {
			StreamMessage sMsg = (StreamMessage) message;
			sMsg.reset();

			ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
			byte[] buffer = new byte[4096];
			int len;

			while ((len = sMsg.readBytes(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}

			return baos.toByteArray();
		} else if (message instanceof ObjectMessage) {
			ObjectMessage oMsg = (ObjectMessage) message;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(baos);
			os.writeObject(oMsg.getObject());
			os.flush();
			os.close();
			return baos.toByteArray();
		} else if (message instanceof TextMessage) {
			TextMessage tMsg = (TextMessage) message;
			String tMsgText = tMsg.getText();

			if (null == tMsgText) {
				// Avoid creating new instances of byte arrays, even empty ones.
				// The
				// load on this part of the code can be high.
				return ArrayUtils.EMPTY_BYTE_ARRAY;
			} else {
				return tMsgText.getBytes(encoding);
			}
		} else {
			throw new JMSException("Cannot get bytes from Map Message");
		}
	}

	public static TargetDestination createJMSDestination(Connection connection,
			String name, Class<?> type, boolean fTransacted, int ackMode)
			throws Exception {

		Session session = connection.createSession(fTransacted, ackMode);
		Destination destination = JmsMessageFactory.createDestination(type,
				name, session);
		TargetDestination targetDestination = new TargetDestination(
				destination, session, null, null);
		targetDestination.setSendTo(name);

		return targetDestination;

	}

	public static TargetDestination createJMSDestination(
			JMSQueueType queueType, Connection connection, String name)
			throws Exception {
		logger.debug("Creating jms destination : type="+queueType.name()+", Name=" + name);
		boolean fTransacted = false;
		int ackMode = Session.AUTO_ACKNOWLEDGE;
		Destination destination = null;
		TargetDestination targetDestination = null;

		Session session = connection.createSession(fTransacted, ackMode);
		logger.debug("Created session");
		if (JMSQueueType.queue.name().equals(queueType.name())) {
			destination = JmsMessageFactory.createDestination(Queue.class,
					name, session);
		} else {
			destination = JmsMessageFactory.createDestination(Topic.class,
					name, session);
		}

		targetDestination = new TargetDestination(destination, session, null,
				null);
		targetDestination.setSendTo(name);

		return targetDestination;

	}

}
