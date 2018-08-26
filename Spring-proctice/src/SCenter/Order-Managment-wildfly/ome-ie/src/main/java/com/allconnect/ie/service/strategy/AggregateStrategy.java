package com.AL.ie.service.strategy;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.message.GenericMessage;
import org.springframework.integration.support.MessageBuilder;
import com.AL.ie.domain.TransientLineItemContainer;
import com.AL.vm.arbiter.converter.ArbiterMarshaller;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.TransientResponseContainerType;
import com.AL.xml.v4.rtimRequestResponse.RtimRequestResponseDocument;

public enum AggregateStrategy {

	INSTANCE;

	private static final Logger logger = Logger
			.getLogger(AggregateStrategy.class);

	public Message<?> onMessage(List<Message<?>> messages) {

		if ((messages != null) && (messages.size() > 0)) {
			logger.debug("number of aggregation elements -->" + messages.size());
			Message<?> message = messages.iterator().next();

			if ((message != null) && (message.getHeaders() != null)) {
				String key = (String) message.getHeaders().get("FLOW_KEY");

				if (key != null) {

					logger.debug("extracted message count:" + messages.size()
							+ "flow key:" + key);
					Object obj = message.getPayload();

					if (obj instanceof String) {
						logger.debug("resolving String message");
						return onStringMessage(messages);
					}

					else if (obj instanceof RtimRequestResponseDocument) {
						logger.debug("resolving RtimRequestResponseDocument message");

						return onOrderMessage(messages);
					}   else {

						logger.debug(obj.getClass().getCanonicalName()
								+ " invalid data type for flow key:" + key);

						final MessageHeaders headers = messages.get(0)
								.getHeaders();

						return onInvalidOrderMessage(key, headers);
					}

				}

			}
		}

		logger.debug("unable to resolve message returning null:");
		return null;

	}

	public Message<?> onInvalidOrderMessage(final String key,
			final MessageHeaders headers) {

		List<LineItemCollectionType> list = new ArrayList<LineItemCollectionType>();

		ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> flow = ArbiterFlowManagerDefault.activeSessionsList
				.get(key);

		flow.setLineItemCollectionTypeList(list);

		TransientLineItemContainer transientLineItemContainer = TransientLineItemContainer
				.create();
		logger.debug("completed aggregating..returning empty transient container. REMOVE THIS.result:");

		return MessageBuilder.withPayload(transientLineItemContainer)
				.copyHeadersIfAbsent(headers).build();

	}






	public Message<?> onOrderMessage(final List<Message<?>> messages) {

		List<LineItemCollectionType> list = new ArrayList<LineItemCollectionType>();

		final MessageHeaders headers = messages.get(0).getHeaders();
		String key = (String) messages.get(0).getHeaders().get("FLOW_KEY");

		for (Message<?> message : messages) {
			logger.info("Retrieveing payload from RTIM response message : " + message.toString());
			RtimRequestResponseDocument rtimRequestResponseDocument = (RtimRequestResponseDocument) message
					.getPayload();
			TransientResponseContainerType tResponseContainer = ArbiterMarshaller.INSTANCE
					.createResponseTransient(rtimRequestResponseDocument);

			LineItemCollectionType lict = ArbiterMarshaller.INSTANCE
					.getLineItemCollectionType(rtimRequestResponseDocument,
							tResponseContainer);

			logger.debug("Retrieveing LineItemCollectionType : " +lict.xmlText());

			list.add(lict);
		}

		ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> flow = ArbiterFlowManagerDefault.activeSessionsList
				.get(key);

		flow.setLineItemCollectionTypeList(list);

		TransientLineItemContainer transientLineItemContainer = TransientLineItemContainer
				.create();
		logger.debug("completed aggregating..returning empty transient container. REMOVE THIS.result:");

		return MessageBuilder.withPayload(transientLineItemContainer)
				.copyHeadersIfAbsent(headers).build();

	}

	public Message<?> onStringMessage(final List<Message<?>> messages) {

		logger.debug("onStringMessage doAggregate");
		StringBuffer buf = new StringBuffer();
		for (Message<?> message : messages) {
			buf.append(message.getPayload() + " ");
		}
		logger.debug("completed doAggregate");
		return new GenericMessage<String>(buf.toString());

	}

}
