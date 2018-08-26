package com.AL.task.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.task.LocalTaskGetLineItemStatus;
import com.AL.task.TaskBase;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.ResponseItemOme;
import com.AL.Vdao.dao.LineItemDao;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.LineItemStatusType;
import com.AL.xml.v4.OrderData;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderSearch;


@Component("taskGetLineItemStatus")
public class TaskGetLineItemStatus extends TaskBase<OrderManagementRequestResponseDocument> implements LocalTaskGetLineItemStatus<OrderManagementRequestResponseDocument>{

	private Logger logger = Logger.getLogger(TaskGetLineItemStatus.class);
	
	@Autowired
	private LineItemDao lineItemDao;
	
	@Override
	public OrchestrationContext processRequest(OrderManagementRequestResponseDocument orderDocument) {

		logger.info("Processing getLineItemStatus request");
		
		OrchestrationContext params = OrchestrationContext.Factory.createOme();
		Request request = orderDocument.getOrderManagementRequestResponse().getRequest();
		saveRequestContext(orderDocument.getOrderManagementRequestResponse(), params);
		
		if(null != request && null != request.getConfirmationNumber()){
			params.add(TaskContextParamEnum.request.name(), request);
		}
		else{
			throw new IllegalArgumentException("Invalid null request.");
		}
		logger.info("exit getLineItemStatus request");
		return params;
	}

	@Override
	public OrchestrationContext processAgenda(OrchestrationContext requestResult) {
		logger.info("Processing getLineItemStatus agenda");
		try{
			Request request = (Request)requestResult.get(TaskContextParamEnum.request.name());
			Map<String, String> resultMap = lineItemDao.getLineItemByProviderConfirmationNumber(request.getConfirmationNumber());
			requestResult.add(TaskContextParamEnum.lineItemInfo.name(), resultMap);
		}catch(Exception e){
			logger.error("Exception while processing agenda :"+e);
		}
		logger.info("exit getLineItemStatus agenda");
		return requestResult;
	}

	@Override
	public OrderManagementRequestResponseDocument processResponse(OrchestrationContext requestResult) {
		logger.info("Processing getLineItemStatus response");
		
		OrderManagementRequestResponseDocument container = OrderManagementRequestResponseDocument.Factory.newInstance();
		OrderManagementRequestResponse requestResponse = container.addNewOrderManagementRequestResponse();
		
		try{
			Response response = requestResponse.addNewResponse();
			loadRequestToResponse(container, requestResult);
			loadResponseContext(requestResult, requestResponse);
			
			Map<String, String> resultMap = (Map<String, String>)requestResult.get(TaskContextParamEnum.lineItemInfo.name());
			OrderSearch search = response.addNewOrderSearchResult();
			
			if(null != resultMap){
				search.setTotalRows(1);
				OrderData data = search.addNewSearchResult();
				data.setLineItemId(resultMap.get("lineItemExtId"));
				data.setOrderId(resultMap.get("orderExtId"));
				LineItemStatusType itemStatusType = data.addNewLineItemStatus();
				itemStatusType.setStatusCode(LineItemStatusCodesType.Enum.forString(resultMap.get("lineItemStatus")));
			}
			else{
				search.setTotalRows(0);
				Request request = (Request)requestResult.get(TaskContextParamEnum.request.name());
				((ResponseItemOme) requestResult).addOrderNotFound("No RTIM mapping found for confirmationNumber="+request.getConfirmationNumber());				
			}
		}
		catch(Exception e){
			logger.error("Exception while processing response :"+e);
		}
		return container;
	}

	@Override
	public OrderManagementRequestResponseDocument handleFault(Exception e, OrchestrationContext taskResponse, OrderManagementRequestResponseDocument orderDocument) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void broadcast(String strToBroadcastOriginal, Map<String, String> header) {
		// TODO Auto-generated method stub
	}
}
