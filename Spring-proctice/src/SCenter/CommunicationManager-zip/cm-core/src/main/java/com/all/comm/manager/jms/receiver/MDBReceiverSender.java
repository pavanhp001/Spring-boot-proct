package com.AL.comm.manager.jms.receiver;

import java.util.HashMap;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.apache.log4j.Logger;

public class MDBReceiverSender extends MDBReceiverAbstract implements
		MessageListener {
	private static Logger logger = Logger.getLogger(MDBReceiverSender.class); 
	

	public MDBReceiverSender() {
		super();

		assert (getNamespace() != null);
		assert (getJndiName() != null);
		
		
	}

	public MDBReceiverSender(final Class<?> clazz, final String method) {
		super(clazz, method);
	}

	 

	public boolean isJNDIConfigured() {
		return ((getJndiName() != null) && (getJndiName().length() > 0));
	}

	public boolean isDynamicClassConfigured() {
		return ((getClazzname() != null) && (getClazzname().length() > 0)
				&& (getMethodName() != null) && (getMethodName().length() > 0));
	}
	
	public String processJNDIDefinedBean(final Message message) {
		String result = null;

		logger.debug("processing.mdb.receiver.sender.on.message.class."
				+ this.getJndiName());

		if (message instanceof TextMessage) {
			try {
				logger.debug("processing.mdb.receiver.sender.on.message.instance.invocation");
				String payload = ((TextMessage) message).getText();
				
				Map<String, String> mapHeaderProps = extractHeaderInfo( message );
				result = processInstanceInvocation(payload, mapHeaderProps);
				
			} catch (JMSException e) {
				logger.error(e);
			}
		}

		return result;
	}
	
	public String processDynamicDefinedBean(final Message message) {
		
		if (message == null)
		{
			logger.info("unable.to.process.dynamic.defined.bean.illegal.null.argument");
			return "unable.to.process.dynamic.defined.bean.illegal.null.argument";
		}
		
		String result = null;

		logger.debug("processing.mdb.receiver.sender.on.message.class."
				+ this.getClazzname());
		logger.debug("processing.mdb.receiver.sender.on.message.method."
				+ this.getMethodName());

		if (message instanceof TextMessage) {
			try {
				String payload = ((TextMessage) message).getText();
				result = processDynamicInvocation(payload);
			} catch (JMSException e) {
				logger.error(e);
			}
		}

		return result;
	}

	public void onMessage(final Message message) {

		try {
			logger.debug("cm.processing.mdb.receiver.sender.onmessage.jndi=["
					+ this.getJndiName() + "]");

			String result = null;

			if (isJNDIConfigured()) {
				result = processJNDIDefinedBean(message);
			} else

			if (isDynamicClassConfigured()) {
				result = processDynamicDefinedBean(message);
			}  
			
			if (result == null)
			{
				result = "UNABLE.TO.PROCESS.MESSAGE."
					+ message.getJMSCorrelationID();
				
				logger.info("failure:"+result);
			}
 
			assertCommunicationManagerInitialized();
			this.getCommunicationManager().send(result, message);

		} catch (Exception e) { 
			logger.error(e);
		}

	}

}
