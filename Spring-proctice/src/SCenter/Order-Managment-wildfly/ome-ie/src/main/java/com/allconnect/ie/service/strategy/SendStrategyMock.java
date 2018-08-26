package com.AL.ie.service.strategy;

import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;
import com.AL.comm.manager.CommunicationManager;
import com.AL.util.file.FileReadUtil;

import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.orderFulfillment.OrderFulfillmentRequest;
import com.AL.xml.v4.orderFulfillment.OrderFulfillmentRequestDocument;

import static org.mockito.Mockito.*;

public enum SendStrategyMock {

	INSTANCE;

	private static final Logger logger = Logger
			.getLogger(SendStrategyMock.class);
	private static final int TIMEOUT = 20000;

	String namespace = "integration";
	// CommunicationManager<javax.jms.Message, MessageListener> commManager =
	// JMSConfigManager.INSTANCE
	// .createCommunicationManager(namespace);

	CommunicationManager<javax.jms.Message, MessageListener> commManager = null;

	public void setMock1(int liNum) {

		String path = MotherObjectArbiter.path;
		String arbiterResponse = FileReadUtil.getXMLFromFile(path
				+ "src//main//resources//xml//arbiter//arbiter-0-response.xml");

		try {
			commManager = mock(CommunicationManager.class);
			TextMessage mockTextMessage = mock(TextMessage.class);
			when(mockTextMessage.getText()).thenReturn(arbiterResponse);
			when(commManager.sendSync(anyString(), anyString(), anyInt()))
					.thenReturn(mockTextMessage);
		} catch (Exception e) {
			logger.error("set mock ERROR!!!!");
		}
	}

	public Message<?> onMessage(Message<?> m) {

		Object obj = m.getPayload();

		if (obj == null) {
			return m;
		}

		if (obj instanceof String) {
			return onStringMessage(m);
		}

		else if (obj instanceof OrderFulfillmentRequest) {
			return onOrderMessage(m);
		}

		else
			throw new IllegalArgumentException("invalid data type:");

	}

	public Message<?> onOrderMessage(final Message<?> message) {

		long start = System.currentTimeMillis();
		Object obj = message.getPayload();
		OrderFulfillmentRequestDocument payload = (OrderFulfillmentRequestDocument) obj;
		OrderFulfillmentRequest doc = payload.getOrderFulfillmentRequest();

		// int liNum = doc.getOrderManagementRequestResponse().getRequest()
		// .getOrderInfo().getLineItems().getLineItemList().get(0)
		// .getLineItemNumber();
		// setMock1(liNum);

		org.springframework.integration.MessageHeaders headers = message
				.getHeaders();
		Message<?> resultMessage = null;
		TextMessage textMsgReply = null;
		try {

			logger.info("send strategy:" + message.getHeaders().toString());

			// if (doc != null && commManager != null)
			// textMsgReply = (TextMessage) commManager.sendSync(
			// "endpoint.rts.in", doc.xmlText(), 20000);
			// if (textMsgReply != null) {
			String path = MotherObjectArbiter.path;
			String arbiterResponse = FileReadUtil
					.getXMLFromFile(path
							+ "src//main//resources//xml//arbiter//arbiter-0-response.xml");
			OrderManagementRequestResponse oMRRD = convert(arbiterResponse);// convert(textMsgReply.getText());

			logger.debug(oMRRD.toString());
			logger.debug(oMRRD.getCorrelationId());
			logger.debug(oMRRD.getResponse().getOrderInfoList().get(0)
					.getExternalId());
			logger.debug(oMRRD.getResponse().getOrderInfoList().get(0)
					.getLineItems().getLineItemList().get(0).getExternalId());

			resultMessage = MessageBuilder.withPayload(oMRRD)
					.copyHeaders(headers).build();

			MessageBuilder.withPayload(oMRRD)
					.copyHeadersIfAbsent(message.getHeaders()).build();

			// } else {
			// resultMessage = message;
			// }
			return resultMessage;
		} catch (Exception e) {
			logger.warn(e);
		}
		logger.info((new StringBuilder())
				.append("send strategy processing time:")
				.append(System.currentTimeMillis() - start).toString());
		return resultMessage;

	}

	public OrderManagementRequestResponse convert(final String inputXml) {
		OrderManagementRequestResponseDocument result = null;

		try {
			result = OrderManagementRequestResponseDocument.Factory
					.parse(inputXml);// OrderFulfillmentResponse.Factory.parse(inputXml);

			// RtimRequestResponseDocument doc =
			// RtimRequestResponseDocument.Factory
			// .parse(inputXml);
			//
			// result = (OrderFulfillmentResponse) doc.getRtimRequestResponse()
			// .getResponse();

		} catch (Exception e) {

			// if (result == null) {
			// result = OrderFulfillmentResponse.Factory.newInstance();
			// }
			// StatusType status = result.addNewOrderManagementRequestResponse()
			// .addNewStatus();
			// status.setStatusCode(1);
			// status.setStatusMsg(e.getMessage());
			//
			// return result;
		}

		if (result != null) {
			return result.getOrderManagementRequestResponse();
		}

		return null;
	}

	public Message<?> onStringMessage(final Message<?> message) {

		return message;
	}

}
