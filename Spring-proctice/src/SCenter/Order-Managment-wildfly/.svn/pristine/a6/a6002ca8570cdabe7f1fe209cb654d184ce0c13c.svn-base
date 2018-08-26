package com.AL.ie.ws.impl;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import com.AL.ie.beans.IeMessageHandlerLocal;
import com.AL.ie.ws.IeWSHandler;
import com.AL.util.OmeSpringUtil;
import com.AL.xml.v4.AcMessageDocument;
import com.AL.xml.v4.AcMessageType;
import com.AL.xml.v4.AcMessageType.Payload;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.StatusType;

public class IeAbstractHandler implements IeMessageHandlerLocal {

	private static Logger logger = Logger.getLogger(IeAbstractHandler.class);
	private static final String EMPTY_STRING = "";
	private static final OmeSpringUtil omeSpringUtil =   OmeSpringUtil.INSTANCE;
	/**
	 * @param inputXml
	 *            xml to be extracted.
	 * @return Order Management Data
	 */
	private static String extractPayload(final String inputXml) {
		if (inputXml == null) {
			logger.debug("input.xml.is.empty");
			return EMPTY_STRING;
		}

		String payloadData;

		try {
			logger.debug("parsing.input.to.extract.payload");
			AcMessageDocument ac = AcMessageDocument.Factory.parse(inputXml);
			AcMessageType ty = ac.getAcMessage();
			logger.debug("getting.payload");
			Payload payload = ty.getPayload();
			payloadData = payload.xmlText();
		} catch (Exception e) {
			logger.error(e);
			payloadData = inputXml;
		}

		logger.debug("returning.extracted.payload");
		return payloadData;
	}

	/**
	 * {@inheritDoc}
	 */

	public static String baseProcessRequest(final String inputXml) {
		logger.debug("Enter  : processRequest() method...");
		logger.debug("attempting.to.extract.payload");
		String payloadData = extractPayload(inputXml);
		logger.debug("extracted.payload");
		OrderManagementRequestResponseDocument doc = null;

		try {
			doc = getOrderManagementRequestResponseDocument(payloadData);

			logger.debug("Retrieving taskHandler from spring context...");

			@SuppressWarnings("unchecked")
			IeWSHandler<OrderManagementRequestResponseDocument> taskHandler = (IeWSHandler<OrderManagementRequestResponseDocument>) omeSpringUtil
					.getBean("ieWSHandler");

			if (taskHandler == null) {
				logger.debug("unable.to.create.task.handler.ieWSHandler:");
				throw new IllegalArgumentException(
						"unable.to.create.task.handler.ieWSHandler:");
			} else {
				logger.debug("created."
						+ taskHandler.getClass().getCanonicalName());
			}
			logger.debug("Retrieved taskHandler..."
					+ taskHandler.getClass().getName());
			OrderManagementRequestResponseDocument response =  taskHandler.execute(doc);
			AcMessageDocument finalResponse = AcMessageDocument.Factory.newInstance();
            finalResponse.addNewAcMessage();
            AcMessageType newMessage = finalResponse.getAcMessage();

            newMessage.setMsgType( AcMessageType.MsgType.RESPONSE );
            newMessage.setActionType( AcMessageType.ActionType.QUERY );
            newMessage.setPayloadType( "OrderResponseDocument" );
            newMessage.addNewPayload();
            
            AcMessageType.Payload newMsgPayload = newMessage.getPayload();
            newMsgPayload.set( response );
			return finalResponse.toString();
		} catch (Exception e) {
			logger.error("unable.to.process.request:" + e.getMessage());
			StatusType status = doc.addNewOrderManagementRequestResponse()
					.addNewStatus();
			status.setStatusCode(1);
			status.setStatusMsg(e.getMessage());

			return doc.xmlText();
		}
	}

	/**
	 * @param inputXml
	 *            input xml received from the request
	 * @return RequestResponseDocument for OrderManagement
	 */
	public static OrderManagementRequestResponseDocument getOrderManagementRequestResponseDocument(
			final String inputXml) {
		OrderManagementRequestResponseDocument doc = null;

		try {
			doc = OrderManagementRequestResponseDocument.Factory
					.parse(inputXml);
		} catch (XmlException e) {
			logger.error("unable.to.parse.input.request:" + inputXml);
			doc = OrderManagementRequestResponseDocument.Factory.newInstance();

			StatusType status = doc.addNewOrderManagementRequestResponse()
					.addNewStatus();
			status.setStatusCode(1);
			status.setStatusMsg("unable.to.parse.input.request."
					+ e.getMessage());
		}
		return doc;
	}
}
