package com.AL.ie.util;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;

import com.AL.util.converter.mask.SecurityMask;
import com.AL.xml.v4.AcMessageDocument;
import com.AL.xml.v4.AcMessageType;
import com.AL.xml.v4.AcMessageType.Payload;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderType;

public enum IERequestLoggerUtil {
	INSTANCE;

	private static final String EMPTY_STRING="";

	private static final Logger logger = Logger.getLogger(IERequestLoggerUtil.class);

	public static void printRequest(String message){
		String payload = extractPayload(message);

		OrderManagementRequestResponseDocument doc = getOrderManagementRequestResponseDocument(payload);

		if(doc != null){
			OrderManagementRequestResponseDocument doc1 = (OrderManagementRequestResponseDocument)doc.copy();
			if(doc1 != null && doc1.getOrderManagementRequestResponse() != null && doc1.getOrderManagementRequestResponse().getRequest() != null ){
				logger.info("Processing OME Proxy request GUID : " + doc1.getOrderManagementRequestResponse().getCorrelationId());
				Request req = doc1.getOrderManagementRequestResponse().getRequest();
				if(req.getOrderInfo() != null){
					doMask(req.getOrderInfo());
				}
				logger.debug("Request to OME Proxy : " + doc1.xmlText());
			}
		}


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

		String payloadData;

		try {
			AcMessageDocument ac = AcMessageDocument.Factory.parse(inputXml);
			AcMessageType ty = ac.getAcMessage();
			Payload payload = ty.getPayload();
			payloadData = payload.xmlText();
		} catch (Exception e) {
			//logger.warn("Exception while parsing input request : " + e.getMessage());
			payloadData = inputXml;
		}
		return payloadData;
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
			logger.error("Unable to parse input request:" + inputXml,e);
			//doc = OrderManagementRequestResponseDocument.Factory.newInstance();
		}
		return doc;
	}

	/**
	 * Mask sensitive info
	 * @param order
	 */
	private static void doMask(OrderType order) {
		if ((order != null) && (order.getCustomerInformation() != null)
				&& (order.getCustomerInformation().getCustomer() != null)) {
			SecurityMask.INSTANCE.execute(order.getCustomerInformation()
					.getCustomer());
		}
	}

	public String getGUID(String msg){
		String guid = "";
		 String payload = extractPayload(msg);
		 if(payload != null && payload.trim().length() > 0){
			 OrderManagementRequestResponseDocument doc = getOrderManagementRequestResponseDocument(payload);
				if(doc != null && doc.getOrderManagementRequestResponse() != null ){
					guid = doc.getOrderManagementRequestResponse().getCorrelationId() != null ? doc.getOrderManagementRequestResponse().getCorrelationId() : "";
				}
		 }

		return guid;
	}
}
