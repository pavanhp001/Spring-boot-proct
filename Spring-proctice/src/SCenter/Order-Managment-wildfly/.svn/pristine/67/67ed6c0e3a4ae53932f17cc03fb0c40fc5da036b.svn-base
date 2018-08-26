package com.AL.ie.service.strategy;

import java.util.Map;

import org.apache.log4j.Logger;

public enum TemplateStrategy {

	INSTANCE;

	private static final Logger logger = Logger
			.getLogger(TemplateStrategy.class);

	public String getATTOrderQualificationTemplate(String methodName,
			String providerId, String correlationId, String sessionId,
			String orderNumber, String region, String orderRRDocument,
			String salesCode, String affiliateName) {
		StringBuilder sb = new StringBuilder();

		sb.append("<tns:rtimRequestResponse ");
		sb.append("xsi:schemaLocation=\"http://xml.AL.com/v4/rtimRequestResponse/rtim.xsd\" ");
		sb.append("xmlns:tns=\"http://xml.AL.com/v4/rtimRequestResponse/\"  ");
		sb.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> ");
		sb.append("<request xsi:type=\"tns:OrderFulfillmentRequest\"  ");
		sb.append("xsi:schemaLocation=\"http://xml.AL.com/v4/orderFulfillment/orderFulfillment.xsd\"  ");
		sb.append("xmlns:ac=\"http://xml.AL.com/v4\"  ");
		sb.append("xmlns:tns=\"http://xml.AL.com/v4/orderFulfillment\"  ");
		sb.append("xmlns:tns1=\"http://xml.AL.com/common\"  ");
		sb.append("xmlns:tns2=\"http://xml.AL.com/v4/VendorRequestResponse/\"> ");
		sb.append("<context> ");
		sb.append("<tns1:correlationId>" + correlationId
				+ "</tns1:correlationId> ");
		sb.append("<tns1:providerId>" + providerId + "</tns1:providerId> ");
		sb.append("<tns1:sessionId>" + sessionId + "</tns1:sessionId> ");
		sb.append("<tns1:salesCode>" + salesCode + " </tns1:salesCode> ");
		sb.append("<tns1:affiliateName>" + affiliateName
				+ "</tns1:affiliateName> ");
		sb.append("<tns1:orderNumber>" + orderNumber + "</tns1:orderNumber> ");
		sb.append("<tns1:region>" + region + "</tns1:region> ");
		sb.append("<tns1:transactionType>" + methodName
				+ "</tns1:transactionType> ");
		sb.append("</context> ");
		sb.append(orderRRDocument);
		sb.append("</request> ");
		sb.append("</tns:rtimRequestResponse> ");

		logger.debug("**************SENDING TO QUEUE *****************************");
		logger.debug(sb.toString());
		logger.debug("*******************************************");
		return sb.toString();

	}

	public String getATTOrderQualificationTemplate(
			final Map<String, String> data) {
		String methodName = data.get("methodName");
		String providerId = data.get("providerId");
		String correlationId = data.get("correlationId");
		String sessionId = data.get("sessionId");
		String orderNumber = data.get("orderNumber");
		String region = data.get("region");
		String orderRRDocument = data.get("orderRRDocument");
		String salesCode = data.get("salesCode");
		String affiliateName = data.get("affiliateName");

		return getATTOrderQualificationTemplate(methodName, providerId,
				correlationId, sessionId, orderNumber, region, orderRRDocument,
				salesCode, affiliateName);
	}
}
