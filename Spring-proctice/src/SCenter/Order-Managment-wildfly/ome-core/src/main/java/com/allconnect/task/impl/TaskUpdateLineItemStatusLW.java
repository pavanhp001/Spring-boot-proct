package com.AL.task.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.activity.ActivitySalesContextEval;
import com.AL.customer.OmeCustomerService;
import com.AL.service.OrderManagementRedefinedService;
import com.AL.task.LocalTaskUpdateLineItemStatusLW;
import com.AL.task.TaskBase;
import com.AL.task.TaskEnum;
import com.AL.task.broadcast.AddProviderIdToContextForBroadcast;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.response.ResponseBuilder;
import com.AL.task.util.SalesContextUtil;
import com.AL.V.beans.entity.Broadcast;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.User;
import com.AL.Vdao.broadcast.BroadcastMessage;
import com.AL.Vdao.broadcast.BroadcastService;
import com.AL.vm.service.BroadcastManager;
import com.AL.vm.service.OrderAgentService;
import com.AL.vm.vo.AgentUtil;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import static com.AL.task.util.SalesContextUtil.SESSION_STATUS_KEY;

@Component("taskUpdateLineItemStatusLW")
public class TaskUpdateLineItemStatusLW extends TaskBase<OrderManagementRequestResponseDocument> 
										implements LocalTaskUpdateLineItemStatusLW<OrderManagementRequestResponseDocument>{

	private Logger logger = Logger.getLogger(TaskUpdateLineItemStatusLW.class);

	private static final String TRANS_UPDATE_LINE_ITEM_STATUS = "updateLineItemStatusLW";
	
	@Autowired
	private OrderAgentService agentService;
	
	@Autowired
	private OrderManagementRedefinedService orderManagementRedefinedService;
	
	@Autowired
	private OmeCustomerService omeCustomerService;
	
	@Autowired
	private ResponseBuilder responseBuilder;
	
	@Autowired
	private BroadcastManager broadcastManager;
	
	@Override
	public OrchestrationContext processRequest(OrderManagementRequestResponseDocument orderDocument) {

		logger.info("Processing updateLineItemStatusLW request");
		Request request = orderDocument.getOrderManagementRequestResponse().getRequest();
		
		logger.debug("BEFORE:OME request: " + request.xmlText().toString());
		
		OrchestrationContext params = createLoadContext(orderDocument);
		boolean isSessionStatusCompleted = SalesContextUtil.INSTANCE.isSessionStatusCompleted(request.getSalesContext());
        if(isSessionStatusCompleted) {
	         logger.debug("sessionStatus completed");
	         request.setIncludeAccountHolders(true);
        }
        params.add(SESSION_STATUS_KEY, isSessionStatusCompleted);
		
		logger.debug("context loader created...");
		
		params.add(TaskContextParamEnum.request.name(), request);
		
		Boolean isEncore = SalesContextUtil.INSTANCE.isEncore(orderDocument);
		params.add(TaskContextParamEnum.isEncore.name(), isEncore);
		logger.info("Is Encore req : " + isEncore);
		
		params.add(TaskContextParamEnum.request.name(), request);
		params.add(TaskContextParamEnum.orderId.name(), request.getOrderId()); // order external id
		if(null != request.getLineitemIdList()){
			params.add(TaskContextParamEnum.lineItemExtId.name(), request.getLineitemIdList().get(0));
		}
		else{
			logger.error("No line item id provided in the request to update the status !!! ");
		}
		params.add(TaskContextParamEnum.agentId.name(),AgentUtil.getAgentId(request));
		params.add(TaskContextParamEnum.source.name(), ActivitySalesContextEval.INSTANCE.getSource(orderDocument));
		params.add(TaskContextParamEnum.transactionId.name(), orderDocument.getOrderManagementRequestResponse().getTransactionId());
		params.add(TaskContextParamEnum.status.name(), request.getNewLineItemStatus().getStatusCode().toString());
		if(null != request.getNewLineItemStatus().getReasonList() && request.getNewLineItemStatus().getReasonList().size() > 0){
			params.add(TaskContextParamEnum.reason.name(), String.valueOf(request.getNewLineItemStatus().getReasonList().get(0)));
		}
		
		// Added new flag to prevent OME to build the response and to avoid lazy initialization issue
		if(SalesContextUtil.INSTANCE.isExternal(orderDocument)){
			params.add(TaskContextParamEnum.isACHResponseNeeded.name(), new Boolean(true));
		}
		return params;
	}

	@Override
	public OrchestrationContext processAgenda(OrchestrationContext params) {

		logger.info("TaskUpdateLineItemStatusLW processAgenda started");
		long startTime = System.currentTimeMillis();
		
		// Get User object
		if(null != params.get(TaskContextParamEnum.agentId.name())){
			
			User user = agentService.findAgentById((String)params.get(TaskContextParamEnum.agentId.name()));
			params.add(TaskContextParamEnum.agent.name(), user);
			
			orderManagementRedefinedService.updateLineitemStatus(params);
			
			SalesOrder salesOrder = (SalesOrder)params.get(TaskContextParamEnum.salesOrder.name());
			if(null != salesOrder){
				AddProviderIdToContextForBroadcast.INSTANCE.execute(params,salesOrder);
				
				params.add(TaskContextParamEnum.transactionType.name(), TaskEnum.updateLineItemStatusLW.name());
				addConsumerToContext(params, salesOrder);
			}			
		}
		else{
			logger.error("Agent id is not in the request !! ");
		}
		
		logger.info("TaskUpdateLineItemStatusLW processAgenda completed in "+(startTime - System.currentTimeMillis())+" ms");		
		return params;
	}

	@Override
	public OrderManagementRequestResponseDocument processResponse(OrchestrationContext requestResult) {
		return responseBuilder.buildResponse(requestResult);
	}

	@Override
	public OrderManagementRequestResponseDocument handleFault(Exception e,
			OrchestrationContext taskResponse,
			OrderManagementRequestResponseDocument orderDocument) {
		
		logger.debug(e.getMessage());
		return orderDocument;
	}

	@Override
	public void broadcast(String strToBroadcastOriginal, Map<String, String> header) {
		logger.debug(strToBroadcastOriginal);
		if(null == header){
			header = new HashMap<String, String>();
		}
		header.put(TaskContextParamEnum.transactionType.name(), TaskEnum.updateLineItemStatusLW.name());
		
		Broadcast brd = broadcastManager.buildOrderBroadcast(strToBroadcastOriginal, header);
		String extId = String.valueOf(broadcastManager.saveBroadcastMessage(brd));
		header.put("broadcast_id", extId);
		BroadcastMessage broadcast = new BroadcastMessage(extId, strToBroadcastOriginal, header, TRANS_UPDATE_LINE_ITEM_STATUS);
		BroadcastService.sendBroadcast(broadcast, Thread.currentThread().getName());
	}

	private void addConsumerToContext(final OrchestrationContext params,
			SalesOrder salesOrder) {
		logger.debug("get customer info from db:"
				+ salesOrder.getConsumerExternalId());
		Consumer customer = omeCustomerService.getCustomer(salesOrder
				.getConsumerExternalId());

		if (customer != null) {
			params.add(TaskContextParamEnum.customer.name(), customer);
		}

	}
}
