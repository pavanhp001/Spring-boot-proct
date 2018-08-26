package com.AL.op.beans;

import java.util.Map;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.AL.comm.manager.jms.receiver.Executable;
import com.AL.op.AbstractOpHandler;


/**
 * Session Bean implementation class MessageHandlerImpl
 */
@Stateless
public class OpMessageHandlerImpl implements Executable<String, String, String> {
	
	private static final Logger logger = Logger.getLogger(OpMessageHandlerImpl.class);

	public String execute(String message, Map<String, String> map) {
		logger.info("OrderProvisioning request received");
		logger.debug(message);
		String response = AbstractOpHandler.baseProcessRequest(message);
		logger.info("OrderProvisioning response");
		logger.debug(response);
		return response;
	}
}
