package com.AL.task.impl;

import static com.AL.task.util.SalesContextUtil.SESSION_STATUS_KEY;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.activity.ActivitySalesContextEval;
import com.AL.activity.impl.ActivityCheckLineItemForJob;
import com.AL.activity.impl.ActivityPersistSalesContext;
import com.AL.activity.impl.ActivitySalesContextEvaluate;
import com.AL.activity.impl.ActivitySyncJobStatusReason;
import com.AL.customer.OmeCustomerService;
import com.AL.enums.LineItemStatus;
import com.AL.task.LocalTaskUpdateLineItemStatus;
import com.AL.task.TaskBase;
import com.AL.task.broadcast.AddProviderIdToContextForBroadcast;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.response.ResponseBuilder;
import com.AL.task.util.DomainObjectLocator;
import com.AL.task.util.SalesContextUtil;
import com.AL.util.messaging.Broadcastable;
import com.AL.V.beans.entity.Broadcast;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.Vdao.broadcast.BroadcastMessage;
import com.AL.Vdao.broadcast.BroadcastService;
import com.AL.Vdao.dao.JobDao;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.Vdao.dao.UserDao;
import com.AL.vm.service.BroadcastManager;
import com.AL.vm.service.OrderManagementService;
import com.AL.vm.service.OrderStatusService;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.vm.util.converter.unmarshall.UnmarshallLineItemOrderStatus;
import com.AL.vm.vo.AgentUtil;
import com.AL.vm.vo.OrderChangeValueObject;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;

/**
 * @author ebthomas
 *
 */

@Component("taskUpdateLineItemStatus")
public class TaskUpdateLineItemStatus extends
		TaskBase<OrderManagementRequestResponseDocument> implements
		LocalTaskUpdateLineItemStatus<OrderManagementRequestResponseDocument>,
		Broadcastable {

	private static final String TRANS_UPDATE_LINE_ITEM_STATUS = "updateLineItemStatus";

	private static final String SOURCE = "source";

	private static final String HARMONY = "harmony";

	@Autowired
	private ActivityPersistSalesContext activityPersistSalesContext;

	@Autowired
	private OrderManagementService omeService;

	@Autowired
	private OrderManagementDao orderManagementDao;

	@Autowired
	private MarshallOrder<SalesOrder> marshallOrder;

	@Autowired
	private UnmarshallLineItemOrderStatus unmarshallLineItemOrderStatus;

	@Autowired
	private OrderStatusService orderStatusService;

	@Autowired
	private ResponseBuilder responseBuilder;

	@Autowired
	private OmeCustomerService omeCustomerService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private JobDao jobDao;

	@Autowired
	private BroadcastManager broadcastManager;
	
	@Autowired
	private ActivityCheckLineItemForJob checkJob;

	private Logger logger = Logger.getLogger(TaskUpdateLineItemStatus.class);

	/**
	 * {@inheritDoc}
	 */
	public OrchestrationContext processRequest(
			final OrderManagementRequestResponseDocument orderDocument) {

		logger.info("Processing updateLineItemStatus request");
		Request request = orderDocument.getOrderManagementRequestResponse().getRequest();
 
		logger.debug("BEFORE:OME request: " + request.xmlText().toString());

		List<String> lineItemIdList = request.getLineitemIdList();
		logger.info("BEFORE:request.getLineitemIdList(): "+ lineItemIdList.size());

		OrderChangeValueObject orderChangeValueObject = unmarshallLineItemOrderStatus.build(request);
		logger.info("AFTER:request.getLineitemIdList(): "+ lineItemIdList.size());

		OrchestrationContext params = createLoadContext(orderDocument);
		boolean isSessionStatusCompleted = SalesContextUtil.INSTANCE.isSessionStatusCompleted(request.getSalesContext());
		if(isSessionStatusCompleted) {
			logger.debug("sessionStatus completed");
			request.setIncludeAccountHolders(true);
		}
		
		params.add(SESSION_STATUS_KEY, isSessionStatusCompleted);
		
		Boolean isEncore = SalesContextUtil.INSTANCE.isEncore(orderDocument);
		logger.info("Is Encore req : " + isEncore);
		
		params.add(TaskContextParamEnum.isEncore.name(), isEncore);
		params.add(TaskContextParamEnum.orderStatus.name(),orderChangeValueObject);
		params.add(TaskContextParamEnum.request.name(), request);
		params.add(TaskContextParamEnum.orderId.name(), request.getOrderId());
		params.add(TaskContextParamEnum.agentId.name(), AgentUtil.getAgentId(request));
		params.add(TaskContextParamEnum.lineItemList.name(), lineItemIdList);
		params.add(TaskContextParamEnum.source.name(), ActivitySalesContextEval.INSTANCE.getSource(orderDocument));

		return params;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrchestrationContext processAgenda(final OrchestrationContext params) {

		String orderId = (String) params.get(TaskContextParamEnum.orderId.name());
		SalesOrder salesOrder = (SalesOrder) params.get(TaskContextParamEnum.salesOrder.name());
		if (salesOrder == null) {
			
			salesOrder = DomainObjectLocator.INSTANCE.findBasicSalesOrder(params, orderManagementDao);
			params.add(TaskContextParamEnum.salesOrder.name(), salesOrder);
		}
		orderStatusService.updateOrderAndLineitemStatus(params);
		params.add("DONT_SAVE_SALES_ORDER", Boolean.TRUE); 
		activityPersistSalesContext.process(params);
		OrderChangeValueObject orderChangeValueObject = (OrderChangeValueObject) params.get(TaskContextParamEnum.orderStatus.name());
		ActivitySyncJobStatusReason.INSTANCE.execute(jobDao, orderChangeValueObject);
		salesOrder = (SalesOrder) params.get(TaskContextParamEnum.salesOrder.name());
		if (salesOrder != null) {
			AddProviderIdToContextForBroadcast.INSTANCE.execute(params, salesOrder);
			addConsumerToContext(params, salesOrder);
		} 
		else {
			throw new IllegalArgumentException(MessageFormat.format("missing order:%s", orderId));
		}
		return params;
	}

	public void addConsumerToContext(final OrchestrationContext params,	SalesOrder salesOrder) {
		
		logger.debug("get customer info from db:"+ salesOrder.getConsumerExternalId());
		Consumer customer = omeCustomerService.getCustomer(salesOrder.getConsumerExternalId());
		if (customer != null) {
			params.add(TaskContextParamEnum.customer.name(), customer);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public OrderManagementRequestResponseDocument processResponse(final OrchestrationContext params) {
		
		OrderManagementRequestResponseDocument omrrd = responseBuilder.buildResponse(params);
		
		OrderType order = null;
		Request omeRequest = null;
		OrderChangeValueObject orderChangeValueObject = (OrderChangeValueObject) params.get(TaskContextParamEnum.orderStatus.name());
		Boolean isHoldOrder = orderChangeValueObject.getStatus().equalsIgnoreCase(LineItemStatus.hold_order.name());
		logger.info("TaskUpdateLineItemStatus:Process Response():Hold Order"+isHoldOrder);
		
		/**
		 * This condition is used to insert a record into om_job table 
		 * when the status is updated from processing_cancelled to hold_order
		 * 
		 *  This scenario is for Encore. 
		 *  The hold_order need to visible in Hold Queue of Encore Application.
		 */		 
		if (isHoldOrder && (omrrd != null)
				&& (omrrd.getOrderManagementRequestResponse() != null)
				&& (omrrd.getOrderManagementRequestResponse().getResponse() != null)
				&& (omrrd.getOrderManagementRequestResponse().getResponse().getOrderInfoList() != null)
				&& (omrrd.getOrderManagementRequestResponse().getResponse().getOrderInfoList().size() > 0)) {
			
			order = omrrd.getOrderManagementRequestResponse().getResponse().getOrderInfoList().get(0);
			omeRequest = (Request) params.get(TaskContextParamEnum.request.name());

			if ((!params.getValidationReport().hasErrors()) && (!params.getValidationReport().hasFatal())) {
				checkJob.checkValidJob(omeRequest.getAgentId(), orderChangeValueObject, false, order);
			}
		}
		return omrrd;
	}

	/**
	 * {@inheritDoc}
	 */
	public OrderManagementRequestResponseDocument handleFault(
			final Exception e, final OrchestrationContext params,
			final OrderManagementRequestResponseDocument orderDocument) {

		logger.debug(e.getMessage());
		return orderDocument;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void broadcast(String strToBroadcastOriginal, Map<String, String> map) {
		
		Broadcast brd = broadcastManager.buildOrderBroadcast(strToBroadcastOriginal, map);
		String extId = String.valueOf(broadcastManager.saveBroadcastMessage(brd));
		map.put("broadcast_id", extId);
		
		BroadcastMessage broadcast = new BroadcastMessage(extId, strToBroadcastOriginal, map, TRANS_UPDATE_LINE_ITEM_STATUS);
		BroadcastService.sendBroadcast(broadcast, Thread.currentThread().getName());
	}

	public OrderManagementService getOmeService() {
		return omeService;
	}

	public void setOmeService(OrderManagementService omeService) {
		this.omeService = omeService;
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

}
