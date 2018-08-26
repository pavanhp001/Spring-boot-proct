package com.AL.task.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import com.AL.task.TaskBase;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

public class TaskGetAllAccountHoldersByOrder extends TaskBase<OrderManagementRequestResponseDocument> {

	private Logger logger = Logger.getLogger(TaskGetAllAccountHoldersByOrder.class);

	@Override
	public OrchestrationContext processRequest(	OrderManagementRequestResponseDocument orderDocument) {
		logger.info("Processing getAllAccountHoldersByOrder request");

		OrchestrationContext params = createLoadContext(orderDocument);
		Long orderExtId = -1L;
		if(orderDocument.getOrderManagementRequestResponse().getRequest().getOrderId() != null &&
				!orderDocument.getOrderManagementRequestResponse().getRequest().getOrderId().trim().isEmpty()){
			orderExtId = Long.valueOf(orderDocument.getOrderManagementRequestResponse().getRequest().getOrderId().trim());
			params.add(TaskContextParamEnum.salesOrderId.name(), orderExtId);
			params.add(TaskContextParamEnum.request.name(), orderDocument.getOrderManagementRequestResponse().getRequest());
		}else{
			params.getValidationReport().addErrorMessage(0L,
					"Order external id is required.");
			throw new IllegalArgumentException("Order external id is required.");
		}
		return params;
	}

	@Override
	public OrchestrationContext processAgenda(OrchestrationContext params) {
		String salesOrderId = (String) params.get(TaskContextParamEnum.salesOrderId.name());
		return null;
	}

	@Override
	public OrderManagementRequestResponseDocument processResponse(
			OrchestrationContext requestResult) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderManagementRequestResponseDocument handleFault(Exception e,
			OrchestrationContext taskResponse,
			OrderManagementRequestResponseDocument orderDocument) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void broadcast(String strToBroadcastOriginal,
			Map<String, String> header) {
		// TODO Auto-generated method stub

	}

}
