package com.AL.ie.service.strategy;

import org.apache.log4j.Logger;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.support.MessageBuilder;

import com.AL.ie.service.validation.impl.RTSValidationService;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

public enum ProcessStrategy {

	INSTANCE;

	private static final Logger logger = Logger
			.getLogger(ProcessStrategy.class);

	public Message<?> onMessage(Message<?> m) {

		Object obj = m.getPayload();

		if (obj instanceof OrderManagementRequestResponseDocument) {
			return onOrderMessage(m);
		}

		return null;

	}

	public Message<?> onOrderMessage(final Message<?> message) {

		logger.debug("Process Strategy#"
				+ message.getHeaders().getSequenceNumber()
				+ " Strategy onMessage:" + message.getHeaders().toString());
		
		

		final MessageHeaders headers = message.getHeaders();

		try {

			OrderManagementRequestResponseDocument returnPayLoad = (OrderManagementRequestResponseDocument) message
					.getPayload();
			
			//System.out.println(returnPayLoad.toString());
			if (!RTSValidationService.validate(returnPayLoad)) {
				return null;
			}

			if (returnPayLoad != null) {
				return MessageBuilder.withPayload(returnPayLoad)
						.copyHeadersIfAbsent(headers).build();
			}
		} catch (final Exception e) {
			 logger.error(e);
		}

		return null;
	}
	
	

}
