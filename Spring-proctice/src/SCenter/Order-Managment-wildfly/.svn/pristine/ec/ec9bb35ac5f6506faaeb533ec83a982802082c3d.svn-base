package com.AL.ie.service.strategy;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.support.MessageBuilder;
import com.AL.vm.arbiter.converter.ArbiterMarshaller;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderType;

public enum SplitStrategy {

	INSTANCE;

	private static Logger logger = Logger.getLogger(SplitStrategy.class);

	public List<Message<?>> onMessage(Message<?> m) {

		Object obj = m.getPayload();
		MessageHeaders headers = m.getHeaders();

		if (obj instanceof OrderManagementRequestResponseDocument) {
			return onMessage((OrderManagementRequestResponseDocument) obj,
					headers);
		}

		return null;

	}

	public List<Message<?>> onMessage(
			OrderManagementRequestResponseDocument oRRD, MessageHeaders headers) {

		logger.info("Split Strategy onMessage:Correlation Id:"
				+ headers.getCorrelationId());

		if ((oRRD != null) && (oRRD instanceof OrderManagementRequestResponseDocument)) {

			OrderManagementRequestResponseDocument omrrd = (OrderManagementRequestResponseDocument) oRRD ;
			//logger.info(omrrd.toString());
		}



		OrderType originalOrder = ArbiterMarshaller.INSTANCE.getOrder(oRRD);

		List<OrderType> ordersSplit = ArbiterMarshaller.INSTANCE
				.createSplitTemplate(originalOrder);
		ArrayList<Message<?>> list = new ArrayList<Message<?>>();
		for (OrderType ot : ordersSplit) {


			OrderManagementRequestResponseDocument newORRD = (OrderManagementRequestResponseDocument) oRRD
					.copy();
			OrderManagementRequestResponse oMRR = newORRD
					.addNewOrderManagementRequestResponse();
			Request req = oMRR.addNewRequest();
			req.setOrderInfo(ot);

			// Splitter will set correlation key, the sequence number and the
			// sequence size by default
			Message<OrderManagementRequestResponseDocument> message = MessageBuilder
					.withPayload(newORRD).copyHeadersIfAbsent(headers).build();

			list.add(message);

			logger.info("splitting#" + list.size() + " header:"
					+ message.getHeaders().toString());
		}

		return list;

	}



}
