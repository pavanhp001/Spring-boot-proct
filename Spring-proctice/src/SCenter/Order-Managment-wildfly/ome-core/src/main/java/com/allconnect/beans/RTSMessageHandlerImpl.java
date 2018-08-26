package com.AL.beans;

import java.util.Map;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.AL.comm.manager.jms.receiver.Executable;
import com.AL.ie.service.strategy.MotherObjectArbiter;
import com.AL.util.file.FileReadUtil;

/**
 * Session Bean implementation class MessageHandlerImpl
 */
@Stateless
public class RTSMessageHandlerImpl  implements
		Executable<String, String, String> {
	private static final Logger logger = Logger
			.getLogger(RTSMessageHandlerImpl.class);

	/**
	 * Default constructor.
	 */
	public RTSMessageHandlerImpl() {
		super();
	}

	public String execute(String msg,Map<String, String> arg1) {
	    logger.debug("Processing Cleint Message request..." + msg);
	    String path = MotherObjectArbiter.path;
		String arbiterResponse = FileReadUtil.getXMLFromFile(path
				+ "src//main//resources//xml//arbiter//arbiter-0-response.xml");
		logger.debug("Process completed... :"  );

		return arbiterResponse;
	}

	
}
