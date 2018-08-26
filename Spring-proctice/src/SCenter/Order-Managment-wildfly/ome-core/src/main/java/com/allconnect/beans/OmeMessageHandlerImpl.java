package com.AL.beans;

import java.util.Map;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.AL.comm.manager.jms.receiver.Executable;
import com.AL.task.util.OMERequestLoggerUtil;
import com.AL.ws.impl.AbstractOmeHandler;

/**
 * Session Bean implementation class MessageHandlerImpl
 */
@Stateless
public class OmeMessageHandlerImpl implements Executable<String, String, String> {
    private static final Logger logger = Logger.getLogger(OmeMessageHandlerImpl.class);

    /**
     * Default constructor.
     */
    public OmeMessageHandlerImpl() {
	super();
    }

    public String execute(String msg, Map<String, String> arg1) {
	long currentTime = System.currentTimeMillis();
	String message = msg;
	OMERequestLoggerUtil.printRequest(message);

	String response = AbstractOmeHandler.baseProcessRequest(msg);
	logger.info("OME Request Completed [GUID=" + OMERequestLoggerUtil.INSTANCE.getGUID(message) + ", TransactionId="+ OMERequestLoggerUtil.INSTANCE.getTransactionId(message)+", TransactionType="+ OMERequestLoggerUtil.INSTANCE.getTaskName(message)+", Status=" +  OMERequestLoggerUtil.INSTANCE.getRequestStatus(response)+ ", Time = " + (System.currentTimeMillis() - currentTime) + " ms]");
	return response;
    }

}
