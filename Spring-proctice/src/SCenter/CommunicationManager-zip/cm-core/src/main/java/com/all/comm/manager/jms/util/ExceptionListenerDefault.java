package com.AL.comm.manager.jms.util;

import javax.jms.Connection;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.apache.log4j.Logger;

public class ExceptionListenerDefault implements ExceptionListener {

	private static final Logger logger = Logger
			.getLogger(ExceptionListenerDefault.class);
	private Connection connection;

	public ExceptionListenerDefault(final Connection connection)
	{
		this.connection = connection;
	}
	
	public void onException(JMSException ex) {
		logger.info("connection error occurred " + ex.getMessage() + ex);
		handleException(ex);

	}

	public JMSException handleException(JMSException ex) {

		if (ex instanceof javax.jms.IllegalStateException) {
		}
		if (ex instanceof javax.jms.InvalidClientIDException) {
		}
		if (ex instanceof javax.jms.InvalidDestinationException) {
		}
		if (ex instanceof javax.jms.InvalidSelectorException) {
		}
		if (ex instanceof javax.jms.JMSSecurityException) {
		}
		if (ex instanceof javax.jms.MessageEOFException) {
		}
		if (ex instanceof javax.jms.MessageFormatException) {
		}
		if (ex instanceof javax.jms.MessageNotReadableException) {
		}
		if (ex instanceof javax.jms.MessageNotWriteableException) {
		}
		if (ex instanceof javax.jms.ResourceAllocationException) {
		}
		if (ex instanceof javax.jms.TransactionInProgressException) {
		}
		if (ex instanceof javax.jms.TransactionRolledBackException) {
		}
		
		restartConnection();

		return null;
	}
	
	private void restartConnection()
	{
		if (connection != null)
		{
			JMSConfigManager.INSTANCE.restartConnection(connection);
		}
	}
	
	public void clearConnection()
	{
		this.connection = null;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	
}
