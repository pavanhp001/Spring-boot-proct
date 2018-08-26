package com.AL.ws.impl;

import org.apache.log4j.Logger;

import com.AL.util.ExceptionUtil;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.ProcessingMessage;
import com.AL.xml.v4.StatusType;
import com.AL.xml.v4.StatusType.ProcessingMessages;

public enum WSExceptionHandler {

	INSTANCE;
	
	private static Logger logger = Logger
	.getLogger(WSExceptionHandler.class);
	
public OrderManagementRequestResponseDocument handleException(final OrderManagementRequestResponseDocument doc,Exception e ) {
		
		StatusType status = null;
		Throwable t = ExceptionUtil.unwindException(e);
		if (doc.getOrderManagementRequestResponse() != null) {
			if (doc.getOrderManagementRequestResponse().getStatus() != null) {
				status = doc.getOrderManagementRequestResponse()
						.getStatus();
			} else {
				status = doc.addNewOrderManagementRequestResponse()
						.addNewStatus();
			}
			ProcessingMessages procMsgs = status.getProcessingMessages();

			if (procMsgs == null) {
				procMsgs = status.addNewProcessingMessages();
			}
			ProcessingMessage procMsg = procMsgs.addNewMessage();
			procMsg.setCode(0);
			procMsg.setText(t.getMessage());
		} else {
			status = doc.addNewOrderManagementRequestResponse()
					.addNewStatus();
			ProcessingMessages procMsgs = status.addNewProcessingMessages();
			ProcessingMessage procMsg = procMsgs.addNewMessage();
			procMsg.setCode(0);
			procMsg.setText(t.getMessage());
		}
		status.setStatusCode(1);
		status.setStatusMsg("FATAL");

		logger.error("Exception thrown", e);
		e.printStackTrace();
		return doc;
	}
}
