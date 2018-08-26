package com.AL.ie.beans;

import java.util.Map;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.AL.comm.manager.jms.receiver.Executable;
import com.AL.ie.util.IERequestLoggerUtil;
import com.AL.ie.ws.impl.IeAbstractHandler;

/**
 * Session Bean implementation class MessageHandlerImpl
 */
@Stateless
public class IeMessageHandlerImpl  implements
		Executable<String, String, String> {
	private static final Logger logger = Logger
			.getLogger(IeMessageHandlerImpl.class);

	/**
	 * Default constructor.
	 */
	public IeMessageHandlerImpl() {
		super();
	}

	public String execute(String msg, Map<String, String> arg1) {
		long currentTime = System.currentTimeMillis();
		String message = msg;
	    IERequestLoggerUtil.printRequest(message);

		String response = IeAbstractHandler.baseProcessRequest(msg);
		logger.info("OME Request[GUID : "+ IERequestLoggerUtil.INSTANCE.getGUID(message)+"] completed in : " + (System.currentTimeMillis() - currentTime) + "ms");

		return response;
	}

}
