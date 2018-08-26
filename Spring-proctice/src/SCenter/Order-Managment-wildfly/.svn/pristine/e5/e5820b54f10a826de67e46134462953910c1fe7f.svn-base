package com.AL.task.impl;

import static com.AL.task.util.AccountHolderUtil.inputAccountHoldersKey;
import static com.AL.task.util.SalesContextUtil.SESSION_STATUS_KEY;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.activity.ActivitySalesContextEval;
import com.AL.activity.impl.ActivityPersistSalesContext;
import com.AL.customer.OmeCustomerService;
import com.AL.service.impl.LineItemServiceImpl;
import com.AL.task.LocalTaskUpdateLineItemLW;
import com.AL.task.TaskBase;
import com.AL.task.broadcast.AddProviderIdToContextForBroadcast;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.ResponseItemOme;
import com.AL.task.response.ResponseBuilder;
import com.AL.task.util.AccountHolderUtil;
import com.AL.task.util.DomainObjectLocator;
import com.AL.task.util.SalesContextUtil;
import com.AL.util.messaging.Broadcastable;
import com.AL.V.beans.entity.AccountHolder;
import com.AL.V.beans.entity.Broadcast;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.LineItem;
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
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;

/**
 * @author pdhar
 * 
 */

@Component("taskUpdateLineItemLW")
public class TaskUpdateLineItemLW extends
		TaskBase<OrderManagementRequestResponseDocument> implements
		LocalTaskUpdateLineItemLW<OrderManagementRequestResponseDocument>,
		Broadcastable {
	
	private static final String TRANS_UPDATE_LINE_ITEM = "updateLineItem";

	private static final String EMPTY_STRING = "";

	private static final Logger logger = Logger.getLogger(TaskUpdateLineItemLW.class);

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
	private OrderAgentService agentService;

	@Autowired
	private BroadcastManager broadcastManager;

	/**
	 * {@inheritDoc}
	 */
	public OrchestrationContext processRequest(
			final OrderManagementRequestResponseDocument orderDocument) {
		logger.info("Processing updateLineItemLW request");
		boolean isEncoreReq = SalesContextUtil.INSTANCE.isEncore(orderDocument);
		boolean isUpdateRequest = true;
		Request request = orderDocument.getOrderManagementRequestResponse()
				.getRequest();
		// Set the values in OrchestrationContext
		OrchestrationContext params = createLoadContext(orderDocument);
		boolean isSessionStatusCompleted = SalesContextUtil.INSTANCE.isSessionStatusCompleted(request.getSalesContext());
		if(isSessionStatusCompleted) {
			logger.debug("sessionStatus completed");
			request.setIncludeAccountHolders(true);
		}
		params.add(SESSION_STATUS_KEY, isSessionStatusCompleted);
		ResponseItemOme rio = (ResponseItemOme) params;
		String agentId = (request.getAgentId() != null) ? request.getAgentId() : EMPTY_STRING;
		if (!DomainObjectLocator.INSTANCE.validateAgent(agentId, agentService)) {
			throw new IllegalArgumentException("Invalid agent id : " + agentId);
		}
		params.add(TaskContextParamEnum.agentId.name(), agentId);
		Long orderId = Long.valueOf(request.getOrderId());
		SalesOrder salesOrder = orderManagementDao.findById(orderId, true);
		if (salesOrder == null) {
			throw new IllegalArgumentException("SalesOrder with external id ["+ orderId + "] does not exist!!!");
		}
		Consumer consumer = omeCustomerService.getCustomer(salesOrder.getConsumerExternalId());
		params.add(TaskContextParamEnum.customer.name(), consumer);	
		params.add(TaskContextParamEnum.orderExternalId.name(), orderId);
		params.add(TaskContextParamEnum.isEncore.name(), isEncoreReq);
		if ((request != null) && (request.getUpdatedLineItemInfo() != null)
				&& (request.getUpdatedLineItemInfo().getLineItemList() != null)) {
			List<LineItemType> lineItemTypeList = request.getUpdatedLineItemInfo().getLineItemList();
			List<LineItem> updateLineItemList = new ArrayList<LineItem>();
			if(lineItemService.isPricingRequestNeeded(salesOrder, lineItemTypeList, isUpdateRequest)) {
				logger.info("Processing static provider lineItem with selected features");
				LineItemCollectionType updatedLIColl = LineItemCollectionType.Factory
						.newInstance();
				// Validate selected features for lineItem are valid or not
				Map<String, String> validationMsgs = omeService.validateSelectedFeatures(lineItemTypeList);
				// Validate service address reference and billing info provided
				// at lineItem level exist for this order's customer
				if (validationMsgs.isEmpty()) {
					validationMsgs = omeService.validateAddressAndBillingRef(salesOrder, lineItemTypeList, consumer);
				}
				if (validationMsgs.isEmpty()) {
					/* Get the lineItem bean from db and marshall its
					information so that we can prepare Pricing request*/ 
					lineItemService.generateLineItemInfo(salesOrder, lineItemTypeList, updatedLIColl);
					/*Replace Scheduling information from client
					request so that we can update it to db*/ 
					lineItemService.updateOtherLineItemInfo(lineItemTypeList, updatedLIColl);
					/*process the Request for Pricing and then update
					 priced lineItem info in to the LineItem*/ 
					lineItemService.processFeatureUpdateRequest(
							isUpdateRequest, salesOrder,
							updateLineItemList, updatedLIColl, rio);
				} else {
					rio.addInvalidLineItems(validationMsgs);
				}
			} else {
				updateLineItems(isUpdateRequest, rio, salesOrder, updateLineItemList, lineItemTypeList, consumer);
				lineItemService.updateOrderPrice(salesOrder, updateLineItemList);
			}
			params.add(TaskContextParamEnum.lineItemList.name(), updateLineItemList);
			if (request.getAccountHolderList().size() != 0) {
				List<AccountHolder> inputAccountHolders = AccountHolderUtil.setAccountHolderInfo(
						salesOrder.getAccountHolders(), request.getAccountHolderList(), params, orderId);
				params.add(inputAccountHoldersKey, inputAccountHolders);
				//Add only new account holders from input accountHolders list to saved account holders
				for(AccountHolder accountHolder : inputAccountHolders) {
					if(accountHolder.getId() == 0) {
						salesOrder.getAccountHolders().add(accountHolder);
					}
				}
			}	
		}
		params.add(TaskContextParamEnum.request.name(), request);
		params.add(TaskContextParamEnum.salesOrder.name(), salesOrder);
		params.add(TaskContextParamEnum.source.name(),
				ActivitySalesContextEval.INSTANCE.getSource(orderDocument));
		return params;
	}

	private void updateLineItems(boolean isUpdateRequest, ResponseItemOme rio,
			SalesOrder orderBean, List<LineItem> updateLineItemList,
			List<LineItemType> lineItemTypeList, Consumer consumer) {
		Long lineitemExtId;
		LineItem lineItemBean;
		for (LineItemType ucLineItemType : lineItemTypeList) {
			logger.info("Processing regular lineitem update");
			// Retrieve LineItemNumber from request
			lineitemExtId = ucLineItemType.getExternalId();
			Boolean isLineItemExist = lineItemService.isExist(
					orderBean.getLineItems(), lineitemExtId);
			if (lineitemExtId != null && lineitemExtId > 0 && isLineItemExist) {
				Map<String, String> validationMsgs = omeService
						.validateAddressAndBillingRef(orderBean, lineItemTypeList, consumer);
				if (validationMsgs.isEmpty()) {
					lineItemBean = lineItemService.updateLineItem(orderBean,
							lineitemExtId, ucLineItemType, isUpdateRequest);
					updateLineItemList.add(lineItemBean);
				} else {
					rio.addInvalidLineItems(validationMsgs);
				}
			} else {
				throw new IllegalArgumentException(
						"Invalid lineItem :  LineItem [" + lineitemExtId
								+ "] does not exist for SalesOrder ["
								+ orderBean.getExternalId() + "]");
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrchestrationContext processValidation(OrchestrationContext params,
			SalesOrder salesOrder) {

		logger.info("Validating constraint violation for lineitems");
		return params;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OrchestrationContext processAgenda(final OrchestrationContext params) {
		List<LineItem> updatedLineItemList = (List<LineItem>) params
				.get(TaskContextParamEnum.lineItemList.name());
		SalesOrder salesOrder = (SalesOrder) params
				.get(TaskContextParamEnum.salesOrder.name());
		if (updatedLineItemList != null && !updatedLineItemList.isEmpty()) {
			lineItemService.saveFeatures(updatedLineItemList);
			lineItemService.saveLineItemAttributes(updatedLineItemList);
			lineItemService.saveDialogues(updatedLineItemList);
			lineItemService.saveSelections(updatedLineItemList);
			// Save all accountHolder
			List<AccountHolder> inputAccountHolders = (List<AccountHolder>)params.get(inputAccountHoldersKey);
			if(inputAccountHolders != null) {
				lineItemService.saveAccountHolders(updatedLineItemList, params, inputAccountHolders);
			}
			//save payment events
			lineItemService.savePaymentEvents(updatedLineItemList);
			//lineItemService.updateLineItems(updatedLineItemList, Boolean.FALSE);
			params.add("DONT_SAVE_SALES_ORDER", new Boolean(true)); // Add this new flag to prevent the sales order object to persist again
			activityPersistSalesContext.process(params);
			lineItemService.updateOrder(salesOrder);
			String agentId = "";
			if (params.get(TaskContextParamEnum.agentId.name()) != null) {
				agentId = (String) params.get(TaskContextParamEnum.agentId.name());
			}

			String source = (String) params.get(TaskContextParamEnum.source
					.name());
			if (source == null || source.trim().equals("")) {
				source = salesOrder.getSource();
			}
			for (LineItem li : updatedLineItemList) {
				if (li != null) {
					omeCustomerService.addConsumerInteraction(agentId,
							salesOrder, source, li, "lineitem updated:"
									+ li.getExternalId());
				}
			}
		}
		AddProviderIdToContextForBroadcast.INSTANCE.execute(params, salesOrder);
		return params;
	}

	/**
	 * {@inheritDoc}
	 */
	public OrderManagementRequestResponseDocument processResponse(
			final OrchestrationContext params) {
		return responseBuilder.buildResponse(params);
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
		logger.info("Broadcasting message");
		Broadcast brd = broadcastManager.buildOrderBroadcast(
				strToBroadcastOriginal, map);
		String extId = String.valueOf(broadcastManager
				.saveBroadcastMessage(brd));
		map.put("broadcast_id", extId);
		BroadcastMessage broadcast = new BroadcastMessage(extId,
				strToBroadcastOriginal, map, TRANS_UPDATE_LINE_ITEM);
		BroadcastService.sendBroadcast(broadcast, Thread.currentThread()
				.getName());
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
