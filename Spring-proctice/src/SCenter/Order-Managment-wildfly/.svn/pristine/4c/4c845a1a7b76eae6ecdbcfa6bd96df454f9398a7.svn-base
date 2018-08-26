package com.AL.ie.service.strategy;

import org.apache.log4j.Logger;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import com.AL.vm.arbiter.converter.ArbiterMarshaller;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.orderFulfillment.OrderFulfillmentRequestDocument;

public enum FormatStrategy {
	INSTANCE;

	private static final Logger logger = Logger.getLogger(FormatStrategy.class);

	public Message<?> onMessage(Message<?> m) {

		logger.debug("Format#"+m.getHeaders().getSequenceNumber()+" Strategy onMessage:" + m.getHeaders().toString());
		
		Object obj = m.getPayload();

		if (obj instanceof String) {
			return onStringMessage(m);
		}

		else if (obj instanceof OrderManagementRequestResponseDocument) {
			OrderFulfillmentRequestDocument returnPayLoad = ArbiterMarshaller.INSTANCE.onOrderMessage((OrderManagementRequestResponseDocument) obj);

			
			return MessageBuilder.withPayload(returnPayLoad)
					.copyHeadersIfAbsent(m.getHeaders()).build();
		}

		return null;

	}
	
	 
	

	public Message<?> onStringMessage(final Message<?> message) {

		return message;
	}

}
