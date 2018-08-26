package com.AL.ie.activity;

import java.util.List;

import org.apache.log4j.Logger;

import com.AL.ie.domain.TransientLineItemContainer;
import com.AL.ie.service.strategy.ArbiterFlow;
import com.AL.ie.service.strategy.ArbiterFlowManagerDefault;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.OrchestrationParamName;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.TransientResponseContainerType;

public class ActivityAddTransientResponseContainer implements Activity {
	
	private static final Logger logger = Logger
			.getLogger(ActivityAddTransientResponseContainer.class);
	
	private OrderType ot = null;

	public void process(OrchestrationContext params) {

		ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> flow = (ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument>) params
				.get(TaskContextParamEnum.arbiterFlow.name());
		
		logger.debug("flow Request: " + flow.getRequest().toString());
		logger.debug("flow Response: " + flow.getResponse().toString());
		
		ot = (OrderType) params.get(OrchestrationParamName.salesOrderType
				.name());
		logger.debug("ActivityAddTransientResponseContainer: " + ot.toString());
		
		List<LineItemCollectionType> lineItemCollectionTypeList = flow
				.getLineItemCollectionTypeList();

		TransientLineItemContainer container = TransientLineItemContainer
				.create();
		for (LineItemCollectionType liCollectionType : lineItemCollectionTypeList) {
			logger.info("lineItemCollectionTypeList Count: " + lineItemCollectionTypeList.size());
			List<LineItemType> lineItemTypeList = liCollectionType
					.getLineItemList();
			logger.info("lineItemTypeList Count: " + lineItemTypeList.size());
			for (LineItemType lineItemType : lineItemTypeList) {
				container.add(lineItemType);
			}
		}

		List<LineItemType> responseLineItemTypeList = ot.getLineItems()
				.getLineItemList();
		for (LineItemType responseLineItemType : responseLineItemTypeList) {

			if ((responseLineItemType != null)) {
				LineItemType transientLineItem = container
						.get((int)responseLineItemType.getExternalId());
				if (transientLineItem != null) {
					logger.info("transientLineItem NOT NULL for: " + responseLineItemType.getExternalId());
					TransientResponseContainerType trct = transientLineItem
							.getTransientResponseContainer();

					responseLineItemType.setTransientResponseContainer(trct);
				} else {
					logger.info("transientLineItem NULL for: " + responseLineItemType.getExternalId());
				}
			}
		}

		params.add(OrchestrationParamName.salesOrderType.name(), ot);

	}

	public OrderType getOt() {
		return ot;
	}

}
