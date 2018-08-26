package com.AL.ie.service.strategy;

import java.util.List;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.springframework.integration.Message;

import com.AL.factory.ProviderLineItemStatusFactory;
import com.AL.ie.domain.TransientLineItemContainer;
import com.AL.vm.arbiter.converter.ArbiterMarshaller;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.TransientResponseContainerType;
import com.AL.xml.v4.TransientResponseType;

public enum ResolveStrategy {
	INSTANCE;

	private Logger logger = Logger.getLogger(ResolveStrategy.class);

	public Message<?> onMessage(final Message<?> message) {

		if (message != null) {
			logger.debug("Resolve Strategy.release processing lock:"
					+ message.getHeaders().toString());
		}

		releaseProcessingLock(message);

		return message;
	}

	private void releaseProcessingLock(final Message<?> message) {

		logger.debug("ResolveStrategy onMessage:"
				+ message.getHeaders().getCorrelationId());

		String key = (String) message.getHeaders().get("FLOW_KEY");

		try {
			logger.debug("resolve about to wake waiting object:" + key
					+ " corelation id:" + message.getHeaders());
			ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> flow = ArbiterFlowManagerDefault.activeSessionsList
					.remove(key);

			if (flow == null) {
				logger.debug("unable to locate key:" + key);

				throw new IllegalArgumentException(
						"missing integration flow manager");
			}

			logger.debug("resolve locking flow for notify release:"
					+ flow.getGuid());
			synchronized (flow.getLock()) {
				logger.debug("setting flow response:" + flow.getGuid());

				logger.debug("setting response in flow:" + flow.getGuid());
				logger.debug("waking flow object:" + flow.getGuid());
				logger.debug("COMPLETED RESOLVE - Will Notify:" + flow.getGuid());
				flow.getLock().notify();
				logger.debug("Notify Complete:");

			}

		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void updateLineItemsWithTransientInfo(
			TransientLineItemContainer transientLineItemContainer,
			OrderManagementRequestResponseDocument copyOfOriginal) {

		List<LineItemType> liCollection = copyOfOriginal
				.getOrderManagementRequestResponse().getRequest()
				.getOrderInfo().getLineItems().getLineItemList();

		for (LineItemType origLineItem : liCollection) {
			int activeIndex = origLineItem.getLineItemNumber();
			logger.debug("processing line item " + activeIndex
					+ " with transient response");
			LineItemType updatedLineItem = transientLineItemContainer
					.get(activeIndex);

			if (ArbiterMarshaller.INSTANCE
					.isAvailableForProviderProcessing(updatedLineItem)) {

				TransientResponseContainerType integrationTransientResponse = null;
				if (updatedLineItem != null) {
					logger.debug("line item " + activeIndex
							+ " using transient container from provider");
					integrationTransientResponse = updatedLineItem
							.getTransientResponseContainer();

				} else {

					logger.debug("line item " + activeIndex
							+ " generating default transient container");

					integrationTransientResponse = TransientResponseContainerType.Factory
							.newInstance();

					TransientResponseType trt = integrationTransientResponse
							.addNewTransientResponse();

					ProviderLineItemStatusFactory.INSTANCE.createSalesNewOrder(  trt);
				}

				origLineItem
						.setTransientResponseContainer(integrationTransientResponse);
			} else {
				logger.debug("line item " + activeIndex
						+ " not eligible for update with transient response");
			}

		}

	}

	public OrderManagementRequestResponseDocument cloneByXMLCopy(
			final OrderManagementRequestResponseDocument doc) {

		XmlObject obj = doc.copy();

		return (OrderManagementRequestResponseDocument) obj;
	}

}
