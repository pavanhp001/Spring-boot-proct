package com.AL.ie.activity;

import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.OrchestrationParamName;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;

public class ActivityBuildProviderResponse implements Activity {

	private OrderManagementRequestResponseDocument doc = null;

	public void process(OrchestrationContext params) {
        doc = (OrderManagementRequestResponseDocument) params
				.get(OrchestrationParamName.arbiterResponse.name());

        if (doc == null) {
        	doc = OrderManagementRequestResponseDocument.Factory.newInstance();
        	doc.addNewOrderManagementRequestResponse();
        	
        }
		OrderManagementRequestResponse requestResponse = doc
				.getOrderManagementRequestResponse();

		Response response = requestResponse.addNewResponse();

		OrderType ot = null;

		if ((doc != null)
				&& (doc.getOrderManagementRequestResponse() != null)
				&& (doc.getOrderManagementRequestResponse().getRequest() != null)
				&& (doc.getOrderManagementRequestResponse().getRequest()
						.getOrderInfo() != null)) {

			ot = (OrderType) doc.getOrderManagementRequestResponse()
					.getRequest().getOrderInfo().copy();
			OrderType[] otArray = { ot };
			response.setOrderInfoArray(otArray);
		}

		params.add(OrchestrationParamName.salesOrderType.name(), ot);

		ActivityAddTransientResponseContainer addTransientResponseContainerActivity = new ActivityAddTransientResponseContainer();
		addTransientResponseContainerActivity.process(params);
		ot = addTransientResponseContainerActivity.getOt();

		params.add(OrchestrationParamName.arbiterResponse.name(), doc);
		params.add(OrchestrationParamName.salesOrderType.name(), ot);
 
		OrderType[] otArray = { ot };
		response.setOrderInfoArray(otArray);
		
		doc.getOrderManagementRequestResponse().getResponse().setOrderInfoArray(otArray);
	}

	public OrderManagementRequestResponseDocument getDoc() {
		return doc;
	}

}
