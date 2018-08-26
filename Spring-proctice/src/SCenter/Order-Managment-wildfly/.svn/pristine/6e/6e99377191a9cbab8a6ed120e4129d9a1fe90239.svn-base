package com.AL.task.builder;

import java.util.List;

import org.apache.log4j.Logger;

import com.AL.activity.impl.ActivitySalesContextEvaluate;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.ResponseItemOme;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.vm.service.OrderManagementService;
import com.AL.vm.vo.OrderChangeValueObject;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;

public enum OrderChangeVOBuilder {
	
	INSTANCE;
	
	private static Logger logger = Logger.getLogger(OrderChangeVOBuilder.class);

	public OrderChangeValueObject build(final OrderManagementService orderManagementService,
			final OrchestrationContext params,
			final OrderManagementRequestResponseDocument orderDocument) {

		Request request = orderDocument.getOrderManagementRequestResponse()
				.getRequest();

		//TODO: Entire request has option and each new product has Item.  Reconcil so that this option is at the Product/Promotion level.
		//You can then control how each Item is applied
		//TODO: Design and think thru implications.
		//TODO: Validate invalid configured applies to...ie..promotion to promotion... promotion to deleted...etc...
		Boolean isAppliesToLineItemIncluded = request
				.getIsAppliesToLineItemIncluded();

		Long orderId = null;
		try {
			orderId = Long.valueOf(request.getOrderId());
			logger.debug("order id:" + orderId);
		} catch (Exception e) {
			logger.error("unable to extract order id");
			params.getValidationReport().addErrorMessage(
					23L,
					"unable to extract orderId for AddLineItem:"
							+ orderDocument.getOrderManagementRequestResponse()
									.getTransactionId());
		}
	    int afterLineItemNumber =  request.getAfterLineItemNumber();//99999
		logger.debug("after lineitem number:" + afterLineItemNumber);

		SalesOrder salesOrder = orderManagementService.findOrderById(orderId, request.getIncludeAccountHolders());

		if (salesOrder == null) {
			((ResponseItemOme) params).addSalesOrderNotFound(String
					.valueOf(orderId));
			throw new IllegalArgumentException("sales order not found:"
					+ orderId);
		}

		params.add(TaskContextParamEnum.salesOrder.name(), salesOrder);
		logger.debug("extracting line items from request:"
				+ request.getNewLineItems().sizeOfLineItemArray());
		LineItemCollectionType newLineItemContainer = request.getNewLineItems();
		List<LineItem> existingLineItems = salesOrder.getLineItems();

		OrderChangeValueObject orderChangeValueObject = new OrderChangeValueObject(
				existingLineItems, newLineItemContainer,
				String.valueOf(afterLineItemNumber));
		orderChangeValueObject.setOrderId(String.valueOf(orderId));
		orderChangeValueObject
				.setAppliesToLineItemIncluded(isAppliesToLineItemIncluded);
		orderChangeValueObject.setAgentId(request.getAgentId());
		String guid = ActivitySalesContextEvaluate.INSTANCE.getGUID(orderDocument);
		orderChangeValueObject.setCorrelationId(guid);
		return orderChangeValueObject;
	}
}
