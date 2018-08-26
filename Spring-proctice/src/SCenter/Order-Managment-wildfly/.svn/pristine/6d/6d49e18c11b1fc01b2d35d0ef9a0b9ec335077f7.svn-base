package com.AL.ws.impl;

import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;

import com.AL.beans.OmeMessageHandlerLocal;
import com.AL.util.OmeSpringUtil;
import com.AL.ws.WSHandler;
import com.AL.xml.v4.AcMessageDocument;
import com.AL.xml.v4.AcMessageType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.StatusType;
import com.AL.xml.v4.AcMessageType.Payload;

public class AbstractOmeHandler implements OmeMessageHandlerLocal {

	private static Logger logger = Logger.getLogger(AbstractOmeHandler.class);
	private static final String EMPTY_STRING = "";
	private static final OmeSpringUtil omeSpringUtil = OmeSpringUtil.INSTANCE;
	private static final String NAMESPACE = "http://xml.AL.com/v4";
	private static final String NAMESPACE_PREFIX = "ac";
	private static XmlOptions xmlOptions = null;
	private static final int XML_INDENTION = 2;

	private static final XmlOptions getOptions() {

		if (xmlOptions == null) {
			xmlOptions = new XmlOptions();
			Map<String, String> namespaceMap = new java.util.HashMap<String, String>();
			namespaceMap.put(NAMESPACE, NAMESPACE_PREFIX);
			xmlOptions.setSaveSuggestedPrefixes(namespaceMap);
			xmlOptions.setSavePrettyPrint();
			xmlOptions.setSavePrettyPrintIndent(XML_INDENTION);
			//xmlOptions.setLoadUseDefaultResolver();
		}

		return xmlOptions;

	}

	/**
	 * @param inputXml
	 *            xml to be extracted.
	 * @return Order Management Data
	 */
	private static String extractPayload(final String inputXml) {
		if (inputXml == null) {
			logger.debug("input xml is empty");
			return EMPTY_STRING;
		}

		String payloadData = null;;

		try {
			logger.debug("parsing input to extract payload");
			if(inputXml.contains("payload>")){
				AcMessageDocument ac = AcMessageDocument.Factory.parse(inputXml);
				AcMessageType ty = ac.getAcMessage();
				logger.debug("getting payload");
				Payload payload = ty.getPayload();
				payloadData = payload.xmlText(); //call from SOAPUI
			}
			else{
				payloadData = inputXml; // there is no payload as this is from application
			}
		} catch (Exception e) {
			logger.debug("Exception while parsing input request : " + e);
			payloadData = inputXml;
		}

		logger.debug("returning extracted payload");
		return payloadData;
	}

	/**
	 * {@inheritDoc}
	 */

	public static String baseProcessRequest(final String inputXml) {
		logger.debug("Enter  : processRequest() method...");
		String payloadData = extractPayload(inputXml);
		OrderManagementRequestResponseDocument doc = null;

		try {
			doc = getOrderManagementRequestResponseDocument(payloadData);

			logger.debug("Retrieving taskHandler from spring context...");

			WSHandler<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> taskHandler = (WSHandler<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument>) omeSpringUtil
			.getBean("orderManagementWSHandler");

			if (taskHandler == null) {
				logger.debug("unable to create task handler orderManagementWSHandler:");
				throw new IllegalArgumentException(
						"unable to create task handler orderManagementWSHandler:");
			} else {
				logger.debug("created."
						+ taskHandler.getClass().getCanonicalName());
			}
			logger.debug("Retrieved taskHandler..."
					+ taskHandler.getClass().getName());

			OrderManagementRequestResponseDocument response = taskHandler
					.execute(doc);

			AcMessageDocument finalResponse = AcMessageDocument.Factory
					.newInstance();
			finalResponse.addNewAcMessage();
			AcMessageType newMessage = finalResponse.getAcMessage();

			newMessage.setMsgType(AcMessageType.MsgType.RESPONSE);
			newMessage.setActionType(AcMessageType.ActionType.QUERY);
			newMessage.setPayloadType("OrderResponseDocument");
			newMessage.addNewPayload();

			AcMessageType.Payload newMsgPayload = newMessage.getPayload();
			newMsgPayload.set(response);

			XmlOptions printOptions = getOptions();
			return finalResponse.xmlText(printOptions);

		} catch (Exception e) {

			logger.error("unable to process request:", e);
			StatusType status = doc.addNewOrderManagementRequestResponse()
					.addNewStatus();
			status.setStatusCode(1);
			status.addNewProcessingMessages().addNewMessage().setText(ExceptionUtils.getRootCauseMessage(e));
			status.setStatusMsg("Error");

			XmlOptions printOptions = getOptions();
			return doc.xmlText(printOptions);
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
			logger.debug("unable to parse input request:" + inputXml);
			doc = OrderManagementRequestResponseDocument.Factory.newInstance();

			StatusType status = doc.addNewOrderManagementRequestResponse()
					.addNewStatus();
			status.setStatusCode(1);
			status.setStatusMsg("unable to parse input request."
					+ e.getMessage());
		}
		return doc;
	}
}
