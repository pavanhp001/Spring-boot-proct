package com.AL.ie.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.support.MessageBuilder;
import com.AL.ie.domain.TransientLineItemContainer;
import com.AL.ie.service.ArbiterService;
import com.AL.ie.service.strategy.AggregateStrategy;
import com.AL.ie.service.strategy.FormatStrategy;
import com.AL.ie.service.strategy.ProcessStrategy;
import com.AL.ie.service.strategy.ResolveStrategy;
import com.AL.ie.service.strategy.SendStrategy;
import com.AL.ie.service.strategy.SplitStrategy;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

public class ArbiterServiceImpl implements ArbiterService {

	private static final Logger logger = Logger
			.getLogger(ArbiterServiceImpl.class);

	static {
		logger.info("Arbiter Service Impl 1.1.0");

	}

	public ArbiterServiceImpl() {
		super();

	}

	public List<Message<?>> doSplit(Message<?> message) {

		if ((message != null) && (message.getHeaders() != null)) {
			logger.info(" start split correlation:"
					+ message.getHeaders().toString());
			
			
			Object obj = message.getPayload();

			if ((obj != null) && (obj instanceof OrderManagementRequestResponseDocument)) {
				
				OrderManagementRequestResponseDocument omrrd = (OrderManagementRequestResponseDocument) obj ;
				logger.debug(omrrd.toString());
			}
			 
			List<Message<?>> resultMsg = SplitStrategy.INSTANCE
					.onMessage(message);
			logger.info(" end split correlation:"
					+ message.getHeaders().getCorrelationId());

			if (resultMsg.size() == 0) {

				Message<?> noProcessing = MessageBuilder
						.withPayload(TransientLineItemContainer.create())
						.copyHeadersIfAbsent(message.getHeaders()).build();

				doResolve(noProcessing);

				return null;
			} else {
				return resultMsg;
			}

		}

		logger.debug("returning empty list from splitter doSplit");
		return new ArrayList<Message<?>>();
	}

	private static final String TERMINATE_MESSAGE_EVENT = "TERMINATE";

	public Message<?> doAggregate(List<Message<?>> messages) {
		logger.info("start aggregate message size:" + messages.size()
				+ " correlation:"
				+ messages.get(0).getHeaders().getCorrelationId());
		Message<?> m = AggregateStrategy.INSTANCE.onMessage(messages);

		if (m != null) {
			logger.info("do aggregate completed:"
					+ m.getHeaders().getCorrelationId());

			doResolve(m);

			String key = (String) m.getHeaders().get("FLOW_KEY");
			UUID correlationUUID = (UUID) m.getHeaders().get(
					MessageHeaders.CORRELATION_ID);

			String correlationId = correlationUUID.toString();

			Map<String, String> map = new HashMap<String, String>();
			map.put(MessageHeaders.CORRELATION_ID, correlationId);
			map.put("FLOW_KEY", key);
			Message<String> msg = MessageBuilder
					.withPayload(TERMINATE_MESSAGE_EVENT).copyHeaders(map)
					.build();

			return msg;
		}

		logger.info(messages.toArray());

		throw new IllegalArgumentException("invalid message type:"
				+ messages.toString());

	}

	public Message<?> doSend(final Message<?> message) {

		logger.info("start send correlation:" + message.getHeaders().toString());
		Message<?> m = SendStrategy.INSTANCE.onMessage(message);
		
		if ((m != null) && (m.getHeaders() != null)) {
		logger.info("send Completed:" + m.getHeaders().getCorrelationId());
		}
		return m;
	}

	public Message<?> doResolve(final Message<?> message) {

		if ((message != null) && (message.getHeaders() != null)) {
			logger.info("start resolve correlation:"
					+ message.getHeaders().toString());
		} else {
			logger.info("null RESOLVE");
		}
		Message<?> m = ResolveStrategy.INSTANCE.onMessage(message);
		if ((m != null) && (m.getHeaders() != null)
				&& (m.getHeaders().getCorrelationId() != null)) {
			logger.info("resolve Completed:"
					+ m.getHeaders().getCorrelationId());
		} else {
			logger.info("resolve returned null RESULT");
		}
		return m;

	}

	public Message<?> doFormat(final Message<?> message) {

		logger.info("start format correlation:"
				+ message.getHeaders().toString());
		Message<?> m = FormatStrategy.INSTANCE.onMessage(message);
		logger.info("format Completed:" + m.getHeaders().getCorrelationId());
		return m;
	}

	public Message<?> doProcess(final Message<?> message) {

		logger.info("start process correlation:"
				+ message.getHeaders().toString());
		Message<?> m = ProcessStrategy.INSTANCE.onMessage(message);
		logger.info("process Completed:" + m.getHeaders().getCorrelationId());
		return m;
	}

	protected void handleErrorMessage(final String cause,
			final Exception exception, final Message<String> message) {
		logger.info("handle error correlation:"
				+ message.getHeaders().toString());
		logger.info("Handle Error Message");
	}

}
