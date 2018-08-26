package com.AL.task.impl;

import static com.AL.task.util.SalesContextUtil.SESSION_STATUS_KEY;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.activity.ActivitySalesContextEval;
import com.AL.activity.impl.ActivityPersistSalesContext;
import com.AL.customer.OmeCustomerService;
import com.AL.service.impl.LineItemServiceImpl;
import com.AL.task.LocalTaskUpdateLineItem;
import com.AL.task.TaskBase;
import com.AL.task.broadcast.AddProviderIdToContextForBroadcast;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.ResponseItemOme;
import com.AL.task.response.ResponseBuilder;
import com.AL.task.util.DomainObjectLocator;
import com.AL.task.util.SalesContextUtil;
import com.AL.util.XmlUtil;
import com.AL.util.messaging.Broadcastable;
import com.AL.validation.OMEValidator;
import com.AL.V.beans.NotificationEvent;
import com.AL.V.beans.entity.Broadcast;
import com.AL.V.beans.entity.CoachingBean;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.ReasonBean;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.Vdao.broadcast.BroadcastMessage;
import com.AL.Vdao.broadcast.BroadcastService;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.vm.service.BroadcastManager;
import com.AL.vm.service.OrderAgentService;
import com.AL.vm.service.OrderManagementService;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.NotificationEventType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.Reasons;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;

/**
 * @author ebthomas
 *
 */

@Component("taskUpdateLineItem")
public class TaskUpdateLineItem extends TaskBase<OrderManagementRequestResponseDocument> implements LocalTaskUpdateLineItem<OrderManagementRequestResponseDocument>, Broadcastable {

    private static final String TRANS_UPDATE_LINE_ITEM = "updateLineItem";

    private static final String UPDATE_LINEITEM_ACTION = "Lineitem Updated";

    private Logger logger = Logger.getLogger(TaskUpdateLineItem.class);

    @Autowired
    private OrderManagementDao orderManagementDao;

    @Autowired
    private MarshallOrder<SalesOrder> marshallOrder;

    @Autowired
    private OrderManagementService omeService;

    @Autowired
    private OmeCustomerService omeCustomerService;

    @Autowired
    private ResponseBuilder responseBuilder;

    @Autowired
    private LineItemServiceImpl lineItemService;

    @Autowired
    ActivityPersistSalesContext activityPersistSalesContext;

    @Autowired
    private OMEValidator omeValidator;

    @Autowired
    private OrderAgentService agentService;

    @Autowired
    private BroadcastManager broadcastManager;

    /**
     * {@inheritDoc}
     */
    public OrchestrationContext processRequest(final OrderManagementRequestResponseDocument orderDocument) {

		logger.info("Processing updateLineItem request");
		boolean isEncoreReq = SalesContextUtil.INSTANCE.isEncore(orderDocument);	
		boolean isUpdateRequest = Boolean.TRUE;
		Request request = orderDocument.getOrderManagementRequestResponse().getRequest();
		// Set the values in OrchestrationContext
		OrchestrationContext params = createLoadContext(orderDocument);
		ResponseItemOme rio = (ResponseItemOme) params;
	
		String agentId = "";
		if (request.getAgentId() != null) {
		    agentId = request.getAgentId();
		}
	
		boolean isSessionStatusCompleted = SalesContextUtil.INSTANCE.isSessionStatusCompleted(request.getSalesContext());
		if(isSessionStatusCompleted) {
			logger.debug("sessionStatus completed");
			request.setIncludeAccountHolders(true);
		}
		params.add(SESSION_STATUS_KEY, isSessionStatusCompleted);
		
		if (!DomainObjectLocator.INSTANCE.validateAgent(agentId, agentService)) {
		    throw new IllegalArgumentException("Invalid agent id : " + agentId);
		}
		params.add(TaskContextParamEnum.agentId.name(), agentId);
		
		// Retrieve transactionType to check request is for updateLineItem or not.
		String transactionType = orderDocument.getOrderManagementRequestResponse().getTransactionType().toString();
		isUpdateRequest = (TRANS_UPDATE_LINE_ITEM.equalsIgnoreCase(transactionType));
	
		SalesOrder salesOrder;
		Long orderId = Long.valueOf(request.getOrderId());
		
		salesOrder = orderManagementDao.findById(orderId, request.getIncludeAccountHolders());
		if (salesOrder == null) {
		    throw new IllegalArgumentException("SalesOrder with external id [" + orderId + "] does not exist!!!");
		}
		params.add(TaskContextParamEnum.orderExternalId.name(), orderId);
		
		Consumer consumer = omeCustomerService.getCustomer(salesOrder.getConsumerExternalId());
		params.add(TaskContextParamEnum.customer.name(), consumer);	
		
		List<LineItem> updateLineItemList;
		params.add(TaskContextParamEnum.isEncore.name(), isEncoreReq);
	
		if ((request != null) && (request.getUpdatedLineItemInfo() != null) && (request.getUpdatedLineItemInfo().getLineItemList() != null)) {
		    List<LineItemType> lineItemTypeList = request.getUpdatedLineItemInfo().getLineItemList();
	
		    if (salesOrder != null) {
				// Retrieve each lineItem in updateRequest and update it's information
				updateLineItemList = new ArrayList<LineItem>();
				
				// If request is for Add/Remove SelectedFeature then first we have to
				// gather all info about line item and detail and then prepare PricingRequestResponse
				if (!XmlUtil.isElementNull(request.getUpdatedLineItemInfo().newCursor(), XmlUtil.SELECTED_FEATURE)) {
					
				    logger.info("Processing add/remove selected features");
		
				    LineItemCollectionType updatedLIColl = LineItemCollectionType.Factory.newInstance();
				    // Validate selected features for lineitem are valid or not
				    Map<String, String> validationMsgs = omeService.validateSelectedFeatures(lineItemTypeList);
		
				    // Validate service address reference and billing info provided
				    // at lineitem level exist for this order's customer
				    if (validationMsgs.isEmpty()) {
				    	validationMsgs = omeService.validateAddressAndBillingRef(salesOrder, lineItemTypeList, consumer);
				    }
		
				    if (validationMsgs.isEmpty()) {
		
				    	// Get the lineitem bean from db and marshall its information so that we can prepare Pricing request
				    	lineItemService.generateLineItemInfo(salesOrder, lineItemTypeList, updatedLIColl);
				    	
				    	// Replace Scheduling information from client request so that we can update it to db
				    	lineItemService.updateOtherLineItemInfo(lineItemTypeList, updatedLIColl);
				    	
				    	// process the Request for Pricing and then update priced lineitem info in to the LineItem
				    	lineItemService.processFeatureUpdateRequest(isUpdateRequest, salesOrder, updateLineItemList, updatedLIColl, rio);
				    }
				    else {
				    	rio.addInvalidLineItems(validationMsgs);
				    }
				}
				// Else we will process it as normal update request to update basic info
				else if (salesOrder != null) {
				    if (lineItemTypeList != null) {
				    	if(request.getNotificationEvents()!= null && request.getNotificationEvents().getEventList()!= null){
				    		List<NotificationEventType> notificationEventTypeList = request.getNotificationEvents().getEventList();
				    		for(NotificationEventType notificationEventType: notificationEventTypeList){
				    			if(notificationEventType.getCode()==600){
				    				NotificationEvent notificationEvent = new NotificationEvent();
						    		notificationEvent.setCode(notificationEventType.getCode());
						    		params.add(TaskContextParamEnum.acesReasonsNotificationEvents.name(), notificationEvent);
				    			}
				    			else if(notificationEventType.getCode()==700){
				    				NotificationEvent notificationEvent = new NotificationEvent();
						    		notificationEvent.setCode(notificationEventType.getCode());
						    		params.add(TaskContextParamEnum.acesCoachingsNotificationEvents.name(), notificationEvent);
				    			}
				    		}
				    	}
				    	updateLineItems(isUpdateRequest, rio, salesOrder, updateLineItemList, lineItemTypeList, consumer);
				    	lineItemService.updateOrderPrice(salesOrder, updateLineItemList);
				    }
				}
				params.add(TaskContextParamEnum.lineItemList.name(), updateLineItemList);
		    }
		    else {
		    	rio.addSalesOrderNotFound(String.valueOf(orderId));
		    }
		}
		params.add(TaskContextParamEnum.request.name(), request);
		params.add(TaskContextParamEnum.salesOrder.name(), salesOrder);
		params.add(TaskContextParamEnum.source.name(), ActivitySalesContextEval.INSTANCE.getSource(orderDocument));
		return params;
    }

    private void updateLineItems(boolean isUpdateRequest, ResponseItemOme rio, SalesOrder orderBean, 
    		List<LineItem> updateLineItemList, List<LineItemType> lineItemTypeList, Consumer consumer) {
		
    	Long lineitemExtId;
		LineItem lineItemBean;
		for (LineItemType ucLineItemType : lineItemTypeList) {
		    logger.info("Processing regular lineitem update");
		    // Retrieve LineItemNumber from request
		    lineitemExtId = ucLineItemType.getExternalId();
	
		    Boolean isLineItemExist = lineItemService.isExist(orderBean.getLineItems(), lineitemExtId);
		    if (lineitemExtId != null && lineitemExtId > 0 && isLineItemExist) {
				
		    	Map<String, String> validationMsgs = omeService.validateAddressAndBillingRef(orderBean, lineItemTypeList, consumer);
				
				// User wants to update lineitem
				if (validationMsgs.isEmpty()) {
				    lineItemBean = lineItemService.updateLineItem(orderBean, lineitemExtId, ucLineItemType, isUpdateRequest);
				    updateLineItemList.add(lineItemBean);
				}
				else {
				    rio.addInvalidLineItems(validationMsgs);
				}
		    }
		    else {
		    	throw new IllegalArgumentException("Invalid lineItem :  LineItem [" + lineitemExtId + "] does not exist for SalesOrder [" + orderBean.getExternalId() + "]");
		    }
		}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrchestrationContext processAgenda(final OrchestrationContext params) {
		List<LineItem> updatedLineItemList = (List<LineItem>) params.get(TaskContextParamEnum.lineItemList.name());
		SalesOrder salesOrder = (SalesOrder) params.get(TaskContextParamEnum.salesOrder.name());
		
		if (updatedLineItemList != null && !updatedLineItemList.isEmpty()) {
		    boolean isReasonsUpdated = false;
		    boolean isCoachingsUpdated = false;
			lineItemService.saveFeatures(updatedLineItemList);
		    lineItemService.saveLineItemAttributes(updatedLineItemList);
		    lineItemService.saveDialogues(updatedLineItemList);
		    lineItemService.saveSelections(updatedLineItemList);
		    if(TaskContextParamEnum.acesReasonsNotificationEvents != null){
		    	NotificationEvent notificationEvent = (NotificationEvent) params.get(TaskContextParamEnum.acesReasonsNotificationEvents.name());
		    	if(notificationEvent!= null && notificationEvent.getCode()!= null && notificationEvent.getCode()==600){
		    		lineItemService.saveReasons(updatedLineItemList);
		    		isReasonsUpdated = true;
		    	}
		    }
		    if(TaskContextParamEnum.acesCoachingsNotificationEvents != null){
		    	NotificationEvent notificationEvent = (NotificationEvent) params.get(TaskContextParamEnum.acesCoachingsNotificationEvents.name());
		    	if(notificationEvent!= null && notificationEvent.getCode()!= null && notificationEvent.getCode()==700){
		    		lineItemService.saveCoachings(updatedLineItemList);
		    		isCoachingsUpdated = true;
		    	}
		    }
		    params.add("DONT_SAVE_SALES_ORDER", new Boolean(true)); // Add this new flag to prevent the sales order object to persist again
		    activityPersistSalesContext.process(params);
		    lineItemService.updateOrder(salesOrder);
		    if (salesOrder != null) {
				String agentId = "";
				if (params.get(TaskContextParamEnum.agentId.name()) != null) {
				    agentId = (String) params.get(TaskContextParamEnum.agentId.name());
				}
		
				String source = (String) params.get(TaskContextParamEnum.source.name());
				if (source == null || source.trim().equals("")) {
				    source = salesOrder.getSource();
				}
		
				for (LineItem li : updatedLineItemList) {
				    if (li != null) {
				    	omeCustomerService.addConsumerInteraction(agentId, salesOrder, source, li, "lineitem updated:" + li.getExternalId());
				    	if(isReasonsUpdated){
				    		StringBuffer reasonNotes = new StringBuffer();
				    		Set<ReasonBean> reasonSet = li.getReasons();
				        	for(ReasonBean reason: reasonSet){
				        		reasonNotes.append(reason.getReasonCategory()).append("|").append(reason.getReasonDesc()).append(",");
				        	} 
				        	String notes = reasonNotes.toString();
				        	if(notes.length()>1){
				        	    notes = notes.substring(0,notes.length()-1);
				        	    omeCustomerService.addConsumerInteraction(agentId, salesOrder, source, li, notes);
				        	}
				    		
				    	}
				    	if(isCoachingsUpdated){
				    		StringBuffer coachingNotes = new StringBuffer();
				    		Set<CoachingBean> coachingSet = li.getCoachings();
				        	for(CoachingBean coaching: coachingSet){
				        		coachingNotes.append(coaching.getCoachingType()).append("|").append(coaching.getCoachingDesc()).append("|").append(coaching.getAgentId()).append(",");
				        	} 
				        	String notes = coachingNotes.toString();
				        	if(notes.length()>1){
				        	    notes = notes.substring(0,notes.length()-1);
				        	    omeCustomerService.addConsumerInteraction(agentId, salesOrder, source, li, notes);
				        	}
				    	}
				    }
				}
		    }
		}
		AddProviderIdToContextForBroadcast.INSTANCE.execute(params, salesOrder);	
		return params;
    }

    /**
     * {@inheritDoc}
     */
    public OrderManagementRequestResponseDocument processResponse(final OrchestrationContext params) {
    	return responseBuilder.buildResponse(params);
    }

    /**
     * {@inheritDoc}
     */
    public OrderManagementRequestResponseDocument handleFault(final Exception e, final OrchestrationContext params, final OrderManagementRequestResponseDocument orderDocument) {

		logger.debug(e.getMessage());
		return orderDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void broadcast(String strToBroadcastOriginal, Map<String, String> map) {

    	logger.info("Broadcasting message");
		
    	Broadcast brd = broadcastManager.buildOrderBroadcast(strToBroadcastOriginal, map);
		String extId = String.valueOf(broadcastManager.saveBroadcastMessage(brd));
		map.put("broadcast_id", extId);
		
		BroadcastMessage broadcast = new BroadcastMessage(extId,strToBroadcastOriginal,map,TRANS_UPDATE_LINE_ITEM);
		BroadcastService.sendBroadcast(broadcast, Thread.currentThread().getName());
    }

    public OrderManagementDao getOrderManagementDao() {
    	return orderManagementDao;
    }

    public void setOrderManagementDao(OrderManagementDao orderManagementDao) {
    	this.orderManagementDao = orderManagementDao;
    }

    public MarshallOrder<SalesOrder> getMarshallOrder() {
    	return marshallOrder;
    }

    public void setMarshallOrder(MarshallOrder<SalesOrder> marshallOrder) {
    	this.marshallOrder = marshallOrder;
    }

    public OrderAgentService getAgentService() {
    	return agentService;
    }

    public void setAgentService(OrderAgentService agentService) {
    	this.agentService = agentService;
    }

}
