package com.AL.task.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.activity.ActivitySalesContextEval;
import com.AL.activity.impl.ActivityAddSubmitRequestToParam;
import com.AL.activity.impl.ActivityPersistSalesContext;
import com.AL.activity.impl.ActivityUpdateLineItemWithNotificationEvent;
import com.AL.customer.OmeCustomerService;
import com.AL.service.VOrderMappingService;
import com.AL.task.LocalTaskSubmit;
import com.AL.task.TaskBase;
import com.AL.task.broadcast.AddProviderIdToContextForBroadcast;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.response.ResponseBuilder;
import com.AL.task.response.ResponseBuilderSubmit;
import com.AL.task.util.DomainObjectLocator;
import com.AL.task.util.OrchestrationContextUtil;
import com.AL.task.util.SalesContextUtil;
import com.AL.task.validate.ValidateSubmit;
import com.AL.util.audit.AuditService;
import com.AL.util.messaging.Broadcastable;
import com.AL.V.beans.entity.Broadcast;
import com.AL.V.beans.entity.OrderAudit;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.Vdao.broadcast.BroadcastMessage;
import com.AL.Vdao.broadcast.BroadcastService;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.vm.service.BroadcastManager;
import com.AL.vm.service.OrderAgentService;
import com.AL.vm.service.OrderManagementService;
import com.AL.vm.service.OrderStatusService;
import com.AL.vm.util.converter.unmarshall.UnmarshallOrder;
import com.AL.vm.util.converter.unmarshall.UnmarshallOrderStatus;
import com.AL.vm.vo.AgentUtil;
import com.AL.vm.vo.OrderChangeValueObject;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import static com.AL.task.util.SalesContextUtil.SESSION_STATUS_KEY;

/**
 * @author ebthomas
 *
 *
 *
 *         DELETE FROM V_INTEGRATION_0.OM_SALES_ORDER_OM_STAT_REC WHERE OM_SALES_ORDER_ID = 75;
 *
 */

@Component("taskSubmit")
public class TaskSubmit extends TaskBase<OrderManagementRequestResponseDocument> implements LocalTaskSubmit<OrderManagementRequestResponseDocument>, Broadcastable {

    private static final String TRANS_SUBMIT = "submit";

    @Autowired
    private ActivityPersistSalesContext activityPersistSalesContext;

    @Autowired
    private OrderAgentService agentService;

    @Autowired
    private OrderManagementDao orderManagementDao;

    @Autowired
    protected AuditService<OrderAudit> auditService;

    @Autowired
    private UnmarshallOrder unmarshallOrder;

    @Autowired
    private UnmarshallOrderStatus unmarshallOrderStatus;

    @Autowired
    private OmeCustomerService omeCustomerService;

    @Autowired
    private OrderStatusService updateStatusService;

    @Autowired
    private OrderManagementService omeService;

    @Autowired
    private ResponseBuilder responseBuilder;

    @Autowired
    private VOrderMappingService orderMappingService;

    @Autowired
    private ActivityUpdateLineItemWithNotificationEvent activityUpdateLineItemWithNotificationEvent;

    @Autowired
    private BroadcastManager broadcastManager;

    private Logger logger = Logger.getLogger(TaskSubmit.class);

    /**
     * {@inheritDoc}
     */
    public OrchestrationContext processRequest(final OrderManagementRequestResponseDocument orderDocument) {
		Request request = orderDocument.getOrderManagementRequestResponse().getRequest();
		logger.info("Processing submit request");
		OrderChangeValueObject orderStatusChangeVO = OrderChangeValueObject.createSubmit(request);
		OrchestrationContext params = OrchestrationContextUtil.populateContextWithOrderParams(orderDocument);
		OrchestrationContextUtil.populateContextWithExtraCustInfo(request, params);
		boolean isSessionStatusCompleted = SalesContextUtil.INSTANCE.isSessionStatusCompleted(request.getSalesContext());
		if(isSessionStatusCompleted) {
			logger.debug("sessionStatus completed");
			request.setIncludeAccountHolders(true);
		}
		params.add(SESSION_STATUS_KEY, isSessionStatusCompleted);
		params.add(TaskContextParamEnum.orderStatus.name(), orderStatusChangeVO);
		params.add(TaskContextParamEnum.request.name(), request);
		params.add(TaskContextParamEnum.agentId.name(), AgentUtil.getAgentId(request));
		List<String> lineItemIdList = request.getLineitemIdList();
		// Tweaking the code related to lineItemId or lineitemId list
		if (request.getLineItemId() != null && !request.getLineItemId().equals("")) {
		    if (lineItemIdList != null) {
		    	lineItemIdList.add(request.getLineItemId());
		    }
		    else {
		    	lineItemIdList = new ArrayList<String>();
		    	lineItemIdList.add(request.getLineItemId());
		    }
		}
		params.add(TaskContextParamEnum.lineItemList.name(), lineItemIdList);
		params.add(TaskContextParamEnum.source.name(), ActivitySalesContextEval.INSTANCE.getSource(orderDocument));
		return params;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrchestrationContext processAgenda(final OrchestrationContext params) {
		OrderChangeValueObject orderChangeValueObject = (OrderChangeValueObject) params.get(TaskContextParamEnum.orderStatus.name());
		SalesOrder salesOrder = (SalesOrder) params.get(TaskContextParamEnum.salesOrder.name());
		String agentId = (String) params.get(TaskContextParamEnum.agentId.name());
		logger.debug("Agent id in submit req: " + agentId);
		if (salesOrder == null) {
		    salesOrder = DomainObjectLocator.INSTANCE.findSalesOrder(params, orderManagementDao);
		    params.add(TaskContextParamEnum.salesOrder.name(), salesOrder);
		}
		ValidateSubmit.INSTANCE.validate(orderManagementDao, orderChangeValueObject, salesOrder, agentId);
		ActivityAddSubmitRequestToParam.INSTANCE.execute(params);
		
		params.add("DONT_SAVE_SALES_ORDER", new Boolean(true)); // Add this new flag to prevent the sales order object to persist again
		activityPersistSalesContext.process(params);
		
		updateStatusService.updateOrderAndLineitemStatus(params);
		OrchestrationContextUtil.addConsumerToContext(params, salesOrder, omeCustomerService);
		AddProviderIdToContextForBroadcast.INSTANCE.execute(params, salesOrder);
		
		return params;
    }

    /**
     * {@inheritDoc}
     */
    public OrderManagementRequestResponseDocument processResponse(final OrchestrationContext params) {
    	OrderManagementRequestResponseDocument omrrd = ResponseBuilderSubmit.INSTANCE.processResponse(
    			params, responseBuilder, activityUpdateLineItemWithNotificationEvent);
    	return omrrd;
    }

    /**
     * {@inheritDoc}
     */
    public OrderManagementRequestResponseDocument handleFault(final Exception e, final OrchestrationContext params, 
    		final OrderManagementRequestResponseDocument orderDocument) {
    	return orderDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void broadcast(String strToBroadcastOriginal, Map<String, String> map) {
		// Code for mapping V order no. with provider order no.
		orderMappingService.processOrderMapping(strToBroadcastOriginal);
		//Broadcastable broadcast = HttpBroadcastable.createDefault();
		if (map != null) {
		    logger.debug("Broadcasting order info with headers :" + map.toString());
		}
		Broadcast brd = broadcastManager.buildOrderBroadcast(strToBroadcastOriginal, map);
		String extId = String.valueOf(broadcastManager.saveBroadcastMessage(brd));
		map.put("broadcast_id", extId);
		//broadcast.broadcast(strToBroadcastOriginal, map);
		BroadcastMessage broadcast = new BroadcastMessage(extId,strToBroadcastOriginal,map,TRANS_SUBMIT);
		BroadcastService.sendBroadcast(broadcast, Thread.currentThread().getName());
    }

    public OrderAgentService getAgentService() {
    	return agentService;
    }

    public void setAgentService(OrderAgentService agentService) {
    	this.agentService = agentService;
    }

    public OrderManagementDao getOrderManagementDao() {
    	return orderManagementDao;
    }

    public void setOrderManagementDao(OrderManagementDao orderManagementDao) {
    	this.orderManagementDao = orderManagementDao;
    }

    public Logger getLogger() {
    	return logger;
    }

    public void setLogger(Logger logger) {
    	this.logger = logger;
    }

    public UnmarshallOrder getUnmarshallOrder() {
    	return unmarshallOrder;
    }

    public void setUnmarshallOrder(UnmarshallOrder unmarshallOrder) {
    	this.unmarshallOrder = unmarshallOrder;
    }

    public AuditService<OrderAudit> getAuditService() {
    	return auditService;
    }

    public void setAuditService(AuditService<OrderAudit> auditService) {
    	this.auditService = auditService;
    }

    public UnmarshallOrderStatus getUnmarshallOrderStatus() {
    	return unmarshallOrderStatus;
    }

    public void setUnmarshallOrderStatus(UnmarshallOrderStatus unmarshallOrderStatus) {
    	this.unmarshallOrderStatus = unmarshallOrderStatus;
    }

    public OmeCustomerService getOmeCustomerService() {
    	return omeCustomerService;
    }

    public void setOmeCustomerService(OmeCustomerService omeCustomerService) {
    	this.omeCustomerService = omeCustomerService;
    }

    public OrderStatusService getOrderStatusService() {
    	return updateStatusService;
    }

    public void setOrderStatusService(OrderStatusService orderStatusService) {
    	this.updateStatusService = orderStatusService;
    }

    public ResponseBuilder getResponseBuilder() {
    	return responseBuilder;
    }

    public void setResponseBuilder(ResponseBuilder responseBuilder) {
    	this.responseBuilder = responseBuilder;
    }

    public OrderManagementService getOmeService() {
    	return omeService;
    }

    public void setOmeService(OrderManagementService omeService) {
    	this.omeService = omeService;
    }

    public ActivityUpdateLineItemWithNotificationEvent getActivityUpdateLineItemWithNotificationEvent() {
    	return activityUpdateLineItemWithNotificationEvent;
    }

    public void setActivityUpdateLineItemWithNotificationEvent(ActivityUpdateLineItemWithNotificationEvent 
    		activityUpdateLineItemWithNotificationEvent) {
    	this.activityUpdateLineItemWithNotificationEvent = activityUpdateLineItemWithNotificationEvent;
    }

}
