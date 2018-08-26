package com.AL.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.activity.ActivityUpdateJobSchedule;
import com.AL.activity.impl.ActivityAllowFromStatusToStatus;
import com.AL.activity.impl.ActivityCalculateOrderPrice;
import com.AL.activity.impl.ActivityDispatchOrderToExtProvider;
import com.AL.activity.impl.ActivityRulesValidation;
import com.AL.activity.impl.ActivitySubmitOrder;
import com.AL.activity.impl.ActivitySubmitToProviderAfterValidation;
import com.AL.customer.OmeCustomerService;
import com.AL.enums.OrderStatus;
import com.AL.factory.ArbiterRequestFactory;
import com.AL.factory.StatusFactory;
import com.AL.ie.service.strategy.ArbiterFlowManagerDefault;
import com.AL.rules.core.RuleService;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.util.DomainObjectLocator;
import com.AL.util.audit.AuditService;
import com.AL.util.convert.SafeConvert;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.OrderAudit;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.StatusRecordBean;
import com.AL.V.beans.entity.User;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.vm.service.OrderAgentService;
import com.AL.vm.service.OrderManagementService;
import com.AL.vm.service.OrderStatusService;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.vm.vo.OrderChangeValueObject;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderType;

@Component
public class OrderStatusServiceImpl implements OrderStatusService {

	private static final String LINEITEM_SUBMITTED = "Lineitem Submitted";

	private static final String FACTS = "facts";

	private static final String ORDER_STAUS_UPDATE_ACTION = "Order Staus Updated";

	private static final String LINEITEM_STATUS_UPDATE_ACTION = "Lineitem Status Updated";

	private static final Logger logger = Logger
			.getLogger(OrderStatusServiceImpl.class);

	@Autowired
	ActivitySubmitToProviderAfterValidation activitySubmit;

	@Autowired
	private ActivityCalculateOrderPrice activityCalculateOrderPrice;

	@Autowired
	private OrderAgentService agentService;

	@Autowired
	private RuleService ruleService;

	@Autowired
	ActivitySubmitOrder activitySubmitOrder;

	@Autowired
	private OrderManagementDao orderManagementDao;

	@Autowired
	private OrderManagementService omeService;

	@Autowired
	protected AuditService<OrderAudit> auditService;

	@Autowired
	private OmeCustomerService omeCustomerService;

	@Autowired
	private MarshallOrder marshallOrder;

	@Autowired
	private ActivityUpdateJobSchedule activityUpdateJobSchedule;

	@Autowired
	ArbiterFlowManagerDefault arbiter;

	@Autowired
	private ArbiterRequestFactory arbiterRequestFactory;

	public void updateOrderToSubmitStatus(final OrchestrationContext params,
			final String agentId, SalesOrder salesOrder) {

		List<String> reasons = new ArrayList<String>();
		reasons.add("submitting order");
		Calendar calNow = Calendar.getInstance();

		StatusRecordBean statusRecord = new StatusRecordBean();
		statusRecord.setStatus(OrderStatus.submitted.name());

		statusRecord.setDateTimeStamp(calNow);

		logger.debug("validate agent id:" + agentId);
		User agent = agentService.getAgent(agentId);
		statusRecord.setAgent(agent);

		statusRecord.setReasons(reasons);

		salesOrder.setCurrentStatus(statusRecord);

	}

	public void submitOrderToProvider(StatusRecordBean newStatus,
			SalesOrder salesOrder, final OrchestrationContext params) {
		try {

			logger.debug("attempting submit order to provider:"
					+ salesOrder.getExternalId());

			ActivityDispatchOrderToExtProvider.INSTANCE
					.dispatchOrderToExternalProvider(arbiter,
							arbiterRequestFactory, params);
			activitySubmitOrder.updateRequestWithTransientResponse(params);
			activitySubmitOrder.updateOrderStatus(params);

		} catch (Exception e) {
			logger.warn(e.getMessage());
			params.getValidationReport().addErrorMessage(
					3L,
					"error during order submission to provider:"
							+ e.getMessage());
		} finally {
			// UPDATE ORDER STATUS BASED ON SUBMISSION RESPONSE
		}
	}

	/*
	 *
	 * (non-Javadoc)
	 *
	 * @see
	 * com.AL.vm.service.OrderStatusService#submitOrder(com.AL
	 * .task.context.impl.OrchestrationContext)
	 */

	public SalesOrder getSalesOrder(OrderChangeValueObject orderChangeVO,
			final OrchestrationContext params) {

		Long orderId = SafeConvert.convertLong(orderChangeVO.getOrderId());

		SalesOrder salesOrder = (SalesOrder) params
				.get(TaskContextParamEnum.salesOrder.name());

		if (salesOrder == null) {
			salesOrder = DomainObjectLocator.INSTANCE.findSalesOrder(params,
					orderId, orderManagementDao);
		}

		return salesOrder;

	}
	
	public Boolean updateOrderAndLineitemStatus(final OrchestrationContext params) {
		
		logger.info("Updating order and lineitem status");
		OrderChangeValueObject orderChangeVO = (OrderChangeValueObject) params.get(TaskContextParamEnum.orderStatus.name());
		SalesOrder salesOrder = getSalesOrder(orderChangeVO, params);
		if (salesOrder != null) {
			
			User agent = DomainObjectLocator.INSTANCE.findAgent(orderChangeVO,agentService);
			boolean isSuccessfulRulesValidation = ActivityRulesValidation.INSTANCE.execute(ruleService, agentService, params, salesOrder, agent);
			boolean isAllowStatusTransition = ActivityAllowFromStatusToStatus.INSTANCE.execute(params, salesOrder, orderChangeVO);
			boolean isSubmitRequest = (OrderStatus.submitted.name().equals(orderChangeVO.getStatus()));
			
			if ((isSuccessfulRulesValidation) && (isAllowStatusTransition)) {
				
				logger.debug("audit logging and backup for rollback.  order:" + salesOrder.getExternalId());
				if (isSubmitRequest) {
					StatusFactory.INSTANCE.backupCurrentStatusForRollback(salesOrder, params);
				}
				
				//update the lineItem current status and historic status and attributes
				StatusFactory.INSTANCE.changeLineItemToRequestedStatus(orderManagementDao, agentService, salesOrder,orderChangeVO.getReasonList(),params, agent, orderChangeVO.getStatus(), orderChangeVO.getChangedAt());
				
				if (isSubmitRequest) {
				    params.add(TaskContextParamEnum.interaction.name(), LINEITEM_SUBMITTED);
				    addCustomerInteraction(agent.getUserLogin(), salesOrder, params);
					activitySubmit.processSubmitByProvider(agent, salesOrder,params, orderChangeVO);
				} 
				else {
				    params.add(TaskContextParamEnum.interaction.name(), LINEITEM_STATUS_UPDATE_ACTION);
				    addCustomerInteraction(agent.getUserLogin(), salesOrder, params);
					// If lineitem status has changed ensure scheduled job is updated.
					for (LineItem li : salesOrder.getLineItems()) {
						activityUpdateJobSchedule.updateStatus(agent.getUserLogin(), li.getCurrentStatus(),li.getProviderExternalId(), salesOrder, li);
					}
				}
				
				// NEW SALES ORDER STATUS
				StatusRecordBean newSalesOrderStatus = activitySubmit.calcPostOperationOrderStatus(salesOrder, params, agent);
				
				logger.debug("New order status after submit : " + newSalesOrderStatus.getStatus());
				activitySubmit.persistUpdatedOrderStatusAndPricing(params,salesOrder, newSalesOrderStatus, isSubmitRequest);
				
				//Added below condition to handle LineItem status update for updateLineItemStatus api call 
				if (isSubmitRequest) {
					orderManagementDao.updateOrderStatus(salesOrder, false);
				}else{
					orderManagementDao.updateOrderStatus(salesOrder, true);
				}
				
				params.getValidationReport().addInfoMessage(101L, "update order:" + salesOrder.getExternalId() + " status:"+ salesOrder.getCurrentStatus().getStatus());
				
				return Boolean.TRUE;
			} 
			else {
				Long orderId = SafeConvert.convertLong(orderChangeVO.getOrderId());
				params.getValidationReport().addErrorMessage(101L,"unable to update order status:" + orderId.longValue());
				if (isSubmitRequest) {
					params.getValidationReport().addErrorMessage(102L, "unable to submit lineitem(s) for order:"+ orderId.longValue());
				}
			}
		}
		return Boolean.FALSE;
	}

	@SuppressWarnings("unchecked")
	private void addCustomerInteraction(String userLogin,
			SalesOrder salesOrder, OrchestrationContext params) {
		String source = (String)params.get(TaskContextParamEnum.source.name() );
		if(source == null || source.trim().equals("")){
			source = salesOrder.getSource();
		}
		List<String> lineItemIds = (List<String>) params.get(TaskContextParamEnum.lineItemList.name());

		String transType = (String)params.get(TaskContextParamEnum.interaction.name());

		for (String lineItemId : lineItemIds) {
			omeCustomerService.addConsumerInteraction(userLogin, salesOrder, Long.valueOf(lineItemId),
					source, transType + " [lineitem : "+lineItemId+"]");
		}
	}

	public void auditOrder(final OrchestrationContext params,
			final SalesOrder salesOrder) {
//
//		OrderType orderType = getMarshalledOrder(salesOrder);
//		String agentId = (String) params.get(TaskContextParamEnum.agentId
//				.name());
//		auditService.audit(AuditBuilder.createOrderAudit(salesOrder,
//				orderType.toString(), ORDER_STAUS_UPDATE_ACTION, agentId));

	}

	/**
	 * This method will retrieve the order from db and prepare an xml to be
	 * audited
	 *
	 * @param salesOrder
	 * @return
	 */
	private OrderType getMarshalledOrder(SalesOrder salesOrder) {
		OrderManagementRequestResponseDocument orderDoc = OrderManagementRequestResponseDocument.Factory
				.newInstance();
		Response response = orderDoc.addNewOrderManagementRequestResponse()
				.addNewResponse();
		SalesOrder oldOrder = orderManagementDao.findById(salesOrder
				.getExternalId());
		OrderType orderType = response.addNewOrderInfo();
		marshallOrder.build(oldOrder, orderType);
		return orderType;
	}

	public void calcAndUpdateOrderStatusAndPricing(
			final OrchestrationContext params, final SalesOrder salesOrder,
			final User agent) {

		logger.debug("calculate new order status" + salesOrder.getExternalId());
		StatusRecordBean newSalesOrderStatus = activitySubmit
				.calcPostOperationOrderStatus(salesOrder, params, agent);

		logger.debug("persist updated order status and calculate pricing"
				+ salesOrder.getExternalId());
		activitySubmit.persistUpdatedOrderStatusAndPricing(params, salesOrder,
				newSalesOrderStatus, Boolean.TRUE);
	}
	
	public void saveAllLineItemStatus(final SalesOrder salesOrder) {

		// possible inter-lineitem update in the rules engine. save all lineitem
		// status
		logger.debug("persisting updated lineitem status to database");
		List<LineItem> liList = salesOrder.getLineItems();
		for (LineItem lineItem : liList) {
			omeService.saveLineItemStatus(lineItem);
		}
	}

	public void addFactsToContextForRulesProcessing(
			final OrchestrationContext params, final Object... objs) {

		Set<Object> factList = new HashSet<Object>();

		for (Object obj : objs)
			factList.add(obj);

		params.add(FACTS, factList);
	}

	public StatusRecordBean createNewStatus(final SalesOrder orderBean,
			final OrderChangeValueObject orderChangeValueObject,
			final String reason) {
		StatusRecordBean srb = createNewStatus(orderBean,
				orderChangeValueObject);

		List<String> reasons = new ArrayList<String>();
		reasons.add(reason);

		return srb;
	}

	public StatusRecordBean createNewStatus(final SalesOrder orderBean,
			final OrderChangeValueObject orderChangeValueObject) {
		Calendar calNow = Calendar.getInstance();

		logger.debug("creating new order status entry");
		StatusRecordBean statusRecord = new StatusRecordBean();
		orderBean.setCurrentStatus(statusRecord);

		String currentStatus = orderChangeValueObject.getStatus();

		statusRecord.setStatus(OrderStatus.resolveStatus(currentStatus));
		statusRecord.setDateTimeStamp(calNow);

		logger.debug("validate agent id:" + orderChangeValueObject.getAgentId());

		if (orderChangeValueObject.getAgentId() == null) {
			orderChangeValueObject.setAgentId("1");
			logger.debug("updated agent id:"
					+ orderChangeValueObject.getAgentId());
		}
		User agent = null;

		try {
			agent = agentService.getAgent(orderChangeValueObject.getAgentId());
		} catch (Exception e) {
			logger.error(e.getMessage());

			throw new IllegalArgumentException("Invalid Agent Id");
		}

		statusRecord.setAgent(agent);

		if (orderChangeValueObject.getReasonListAsString() == null) {
			statusRecord.setReasons(Collections.EMPTY_LIST);
		} else {
			statusRecord.setReasons(orderChangeValueObject
					.getReasonListAsString());
		}

		return statusRecord;

	}

	public ActivityCalculateOrderPrice getActivityCalculateOrderPrice() {
		return activityCalculateOrderPrice;
	}

	public void setActivityCalculateOrderPrice(
			ActivityCalculateOrderPrice activityCalculateOrderPrice) {
		this.activityCalculateOrderPrice = activityCalculateOrderPrice;
	}

	public ActivitySubmitOrder getActivitySubmitOrder() {
		return activitySubmitOrder;
	}

	public void setActivitySubmitOrder(ActivitySubmitOrder activitySubmitOrder) {
		this.activitySubmitOrder = activitySubmitOrder;
	}

}