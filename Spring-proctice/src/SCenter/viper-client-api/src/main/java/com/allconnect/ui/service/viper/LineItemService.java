package com.A.ui.service.V;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.A.thread.JobUpdateThread;
import com.A.ui.builder.LineItemStatusBuilder;
import com.A.ui.constants.Constants;
import com.A.ui.service.V.impl.CustomerInteractionCacheService;
import com.A.ui.service.V.impl.OrderCacheService;
import com.A.ui.transport.TransportConfig;
import com.A.ui.vo.ErrorList;
import com.A.V.factory.CustomerFactory;
import com.A.V.factory.OrderFactory;
import com.A.V.factory.SalesContextFactory;
import com.A.xml.v4.AccountHolderType;
import com.A.xml.v4.JobType;
import com.A.xml.v4.LineItemCollectionType;
import com.A.xml.v4.LineItemPriceInfoType;
import com.A.xml.v4.LineItemStatusType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.NotificationEventCollectionType;
import com.A.xml.v4.ObjectFactory;
import com.A.xml.v4.OrderManagementRequestResponse;
import com.A.xml.v4.OrderType;
import com.A.xml.v4.SalesContextType;
import com.A.xml.v4.SchedulingInfoType;

public enum LineItemService {

	INSTANCE;

	public static final Logger logger = Logger.getLogger(LineItemService.class);

	/**
	 * Update LineItem ScheduleInfo
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @param transactionType
	 * @param sit
	 * @return OrderType
	 *
	 * Deprecated: Use updateLineItem(final String agentId, final String orderId,final String transactionType,
			LineItemCollectionType liCollection, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType submitScheduleInfoUpdate(String agentId,
			final String orderId, final long lineItemId,
			final String transactionType, final SchedulingInfoType sit) {
		return submitScheduleInfoUpdate(agentId, orderId, lineItemId, transactionType, sit, null);
	}

	/**
	 * Update LineItem ScheduleInfo
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @param transactionType
	 * @param sit
	 * @param salesContext
	 * @return OrderType
	 *
	 * Deprecated: Use updateLineItem(final String agentId, final String orderId,final String transactionType,
			LineItemCollectionType liCollection, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType submitScheduleInfoUpdate(String agentId,
			final String orderId, final long lineItemId,
			final String transactionType, final SchedulingInfoType sit, SalesContextType salesContext) {

		ObjectFactory oFactory = new ObjectFactory();
		LineItemType lit = oFactory.createLineItemType();

		lit.setExternalId(lineItemId);
		lit.setSchedulingInfo(sit);

		return updateLineItem(agentId, orderId, lineItemId, transactionType, lit, salesContext);
	}

	/**
	 * Update LineItem Pricing Info
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @param transactionType
	 * @param priceInfo
	 * @return OrderType
	 *
	 * Deprecated: Use updateLineItem(final String agentId, final String orderId,final String transactionType,
			LineItemCollectionType liCollection, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType priceInfoUpdate(String agentId, final String orderId,
			final long lineItemId, final String transactionType,
			final LineItemPriceInfoType priceInfo) {
		return priceInfoUpdate(agentId, orderId, lineItemId, transactionType, priceInfo, null);
	}

	/**
	 * Update LineItem Pricing Info
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @param transactionType
	 * @param priceInfo
	 * @param salesContext
	 * @return OrderType
	 *
	 * Deprecated: Use updateLineItem(final String agentId, final String orderId,final String transactionType,
			LineItemCollectionType liCollection, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType priceInfoUpdate(String agentId, final String orderId,
			final long lineItemId, final String transactionType,
			final LineItemPriceInfoType priceInfo, SalesContextType salesContext) {

		ObjectFactory oFactory = new ObjectFactory();
		LineItemType lit = oFactory.createLineItemType();

		lit.setExternalId(lineItemId);
		lit.setLineItemPriceInfo(priceInfo);

		return updateLineItem(agentId, orderId, lineItemId, transactionType,
				lit);
	}

	/**
	 * Update LineItem status to schedule pending
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @param reasons
	 * @return OrderType
	 *
	 * Deprecated: Use updateLineItemStatus(final String agentId, final String orderId, final List<String> lineItemIdList,
			final String statusCode, final String description, final List<Integer> reasons, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType schedulePending(final String agentId,
			final String orderId, final String lineItemId,
			final List<Integer> reasons) {

		return updateLineItemStatus(agentId, orderId, lineItemId,
				"processing_schedule_pending", "processing_schedule_pending",
				reasons);
	}

	/**
	 * Update LineItem status to schedule confirmed
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @param reasons
	 * @return OrderType
	 *
	 * Deprecated: Use updateLineItemStatus(final String agentId, final String orderId, final List<String> lineItemIdList,
			final String statusCode, final String description, final List<Integer> reasons, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType scheduleConfirmed(final String agentId,
			final String orderId, final String lineItemId,
			final List<Integer> reasons) {

		return updateLineItemStatus(agentId, orderId, lineItemId,
				"processing_schedule_confirmed",
				"processing_schedule_confirmed", reasons);
	}

	/**
	 * Update LineItem status to cancelled
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @param reasons
	 * @return OrderType
	 *
	 * Deprecated: Use updateLineItemStatus(final String agentId, final String orderId, final List<String> lineItemIdList,
			final String statusCode, final String description, final List<Integer> reasons, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType cancel(final String agentId, final String orderId,
			final String lineItemId, final List<Integer> reasons) {

		return updateLineItemStatus(agentId, orderId, lineItemId,
				"processing_cancelled", "processing_cancelled", reasons);
	}

	/**
	 * Update LineItem status to sales new
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @param reasons
	 * @return OrderType
	 *
	 * Deprecated: Use updateLineItemStatus(final String agentId, final String orderId, final List<String> lineItemIdList,
			final String statusCode, final String description, final List<Integer> reasons, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType sales(final String agentId, final String orderId,
			final String lineItemId, final List<Integer> reasons) {

		return updateLineItemStatus(agentId, orderId, lineItemId,
				"sales_new_order", "sales_new_order", reasons);
	}

	/**
	 * Update LineItem status to provision
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @param reasons
	 * @return OrderType
	 *
	 * Deprecated: Use updateLineItemStatus(final String agentId, final String orderId, final List<String> lineItemIdList,
			final String statusCode, final String description, final List<Integer> reasons, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType provision(final String agentId, final String orderId,
			final String lineItemId, final List<Integer> reasons) {

		return updateLineItemStatus(agentId, orderId, lineItemId,
				"provision_ready", "provision_ready", reasons);
	}

	/**
	 * Update LineItem status to delete
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @param reasons
	 * @return OrderType
	 *
	 * Deprecated: Use updateLineItemStatus(final String agentId, final String orderId, final List<String> lineItemIdList,
			final String statusCode, final String description, final List<Integer> reasons, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType delete(final String agentId, final String orderId,
			final String lineItemId, final List<Integer> reasons) {

		return updateLineItemStatus(agentId, orderId, lineItemId,
				"cancelled_removed", "deleted order", reasons);
	}

	/**
	 * Update Multiple LineItem status to delete
	 *
	 * @param activeProvider
	 * @param agentId
	 * @param orderId
	 * @param roles
	 * @param lineitem_id_arr
	 * @return OrderType
	 */
	public OrderType deleteMultiple(final String activeProvider,
			final String agentId, final String orderId,
			Map<String, Object> roles, final String[] lineitem_id_arr) {

		ConcurrentExecution exec = new ConcurrentExecution();
		exec.execute(agentId, orderId, lineitem_id_arr);

		return OrderService.INSTANCE.getOrderByOrderNumber(orderId, roles,
				activeProvider);
	}

	/**
	 * Update LineItem status to Hold
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @param reasons
	 * @return OrderType
	 *
	 * Deprecated: Use updateLineItemStatus(final String agentId, final String orderId, final List<String> lineItemIdList,
			final String statusCode, final String description, final List<Integer> reasons, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType hold(final String agentId, final String orderId,
			final String lineItemId, final List<Integer> reasons) {

		return updateLineItemStatus(agentId, orderId, lineItemId, "hold_order",
				"hold_order", reasons);
	}

	/**
	 * Update LineItem status to Aged
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @param reasons
	 * @return OrderType
	 *
	 * Deprecated: Use updateLineItemStatus(final String agentId, final String orderId, final List<String> lineItemIdList,
			final String statusCode, final String description, final List<Integer> reasons, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType aged(final String agentId, final String orderId,
			final String lineItemId, final List<Integer> reasons) {

		return updateLineItemStatus(agentId, orderId, lineItemId,
				"processing_aged", "processing_aged", reasons);
	}

	/**
	 * Update LineItem status
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @param statusCode
	 * @param description
	 * @param reasons
	 * @return OrderType
	 *
	 * Deprecated: Use updateLineItemStatus(final String agentId, final String orderId, final List<String> lineItemIdList,
			final String statusCode, final String description, final List<Integer> reasons, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType updateLineItemStatus(final String agentId,
			final String orderId, final String lineItemId,
			final String statusCode, final String description,
			final List<Integer> reasons) {
		return updateLineItemStatus(agentId, orderId, lineItemId, statusCode,
				description, reasons, null);
	}

	/**
	 * Update LineItem status for single item
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @param statusCode
	 * @param description
	 * @param reasons
	 * @param salesContext
	 * @return OrderType
	 *
	 * Deprecated: Use updateLineItemStatus(final String agentId, final String orderId, final List<String> lineItemIdList,
			final String statusCode, final String description, final List<Integer> reasons, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType updateLineItemStatus(final String agentId,
			final String orderId, final String lineItemId,
			final String statusCode, final String description,
			final List<Integer> reasons, SalesContextType salesContext) {

		List<String> lineItemIdList = new ArrayList<String>();
		lineItemIdList.add(lineItemId);

		return updateLineItemStatus(agentId, orderId, lineItemIdList, statusCode, description, reasons, salesContext);
	}

	/**
	 * Update LineItem status for multiple items
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @param statusCode
	 * @param description
	 * @param reasons
	 * @return OrderType
	 */
	@Deprecated
	public OrderType updateLineItemStatus(final String agentId,
			final String orderId, final List<String> lineItemIdList,
			final String statusCode, final String description,
			final List<Integer> reasons, SalesContextType salesContext) {

		return updateLineItemStatus(agentId,
				orderId,  lineItemIdList,
			 statusCode, description,
				reasons,  salesContext, new ErrorList());
	}


	/**
	 * @param agentId
	 * @param orderId
	 * @param lineItemIdList
	 * @param statusCode
	 * @param description
	 * @param reasons
	 * @param salesContext
	 * @return Map<String,Object>
	 */
	public OrderType updateLineItemStatus(final String agentId,
			final String orderId, final List<String> lineItemIdList,
			final String statusCode, final String description,
			final List<Integer> reasons, SalesContextType salesContext, ErrorList errorList)
	{
		logger.debug("updateLineItemStatus: lineItemIdList : "+lineItemIdList);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = oFactory
		.createOrderManagementRequestResponse();
		omrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		String correlationId = SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID");
		if(StringUtils.isBlank(correlationId)){
			correlationId = UUID.randomUUID().toString();
		}
		logger.info("updateLineItemStatus ------ [GUID: " +correlationId +"]");
		omrr.setCorrelationId(correlationId);
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.setTransactionType("updateLineItemStatus");

		omrr.getRequest().setOrderId(orderId);
		for(String lineItemId : lineItemIdList){
			omrr.getRequest().getLineitemId().add(lineItemId);
		}
		omrr.getRequest().setAgentId(agentId);
		if(salesContext != null){
			omrr.getRequest().setSalesContext(salesContext);
		}

		LineItemStatusType liStatusType = LineItemStatusBuilder.INSTANCE.build(
				agentId, statusCode, description, reasons);

		omrr.getRequest().setNewLineItemStatus(liStatusType);

		logger.info("OME request : [TransactionType=updateLineItemStatus, GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId+"]");
		long start = System.currentTimeMillis();
		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE
		.getOrderClient().send(omrr);
		OrderFactory.INSTANCE.validateOrder(cmrrR, errorList);

		OrderType orderR = null;

		if (cmrrR != null && cmrrR.getResponse() != null
				&& cmrrR.getResponse().getOrderInfo() != null) {
			List<OrderType> orderTypeList = cmrrR.getResponse().getOrderInfo();

			if (orderTypeList.size() > 0) {
				orderR = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(orderTypeList.get(0));

				if (orderR != null && orderR.getExternalId() != 0) {
					//Clearing CI cache since updateLineItem will add new CI to the list
					String customerId = orderR.getCustomerInformation().getCustomer().getExternalId().toString();
					CustomerInteractionCacheService.INSTANCE.clearCachedCustomerInteraction(customerId);

					OrderCacheService.INSTANCE.clear(orderId);
					OrderCacheService.INSTANCE.store(orderR, orderId);
					logger.info("OME request completed: [TransactionType=addLineItem, GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId +", Time="+(System.currentTimeMillis()-start)+"]");
				}
			}
		}

		return orderR;
	}

	/**
	 * Submit LineItem
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @return OrderType
	 *
	 * Deprecated: Use submitLineItem(final String agentId, final String orderId,
			final String lineItemId, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType submitLineItem(final String agentId, final String orderId,
			final String lineItemId) {
		return submitLineItem(agentId, orderId, lineItemId, null);
	}

	/**
	 * Submit LineItem
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @return OrderType
	 */
	public OrderType submitLineItem(final String agentId, final String orderId,
			final String lineItemId, SalesContextType salesContext) {

		final List<String> liList = new ArrayList<String>();
		liList.add(lineItemId);

		return submitMultipleLineItem(agentId, orderId, liList, salesContext);
	}

	/**
	 * Submit LineItem
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @return OrderType
	 */
	public OrderType submitLineItem(final String agentId, final String orderId,
			final String lineItemId, SalesContextType salesContext , boolean includeAH) {

		final List<String> liList = new ArrayList<String>();
		liList.add(lineItemId);

		return submitMultipleLineItem(agentId, orderId, liList, salesContext,new ErrorList() , includeAH);
	}

	
	/**
	 * Submit Multiple LineItem
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineitem_id_list
	 * @return OrderType
	 *
	 * Deprecated: Use submitMultipleLineItem(final String agentId,
			final String orderId, final List<String> lineitem_id_list, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType submitMultipleLineItem(final String agentId,
			final String orderId, final List<String> lineitem_id_list) {

		return submitMultipleLineItem(agentId, orderId, lineitem_id_list, null);
	}

	/**
	 * Submit Multiple LineItem
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineitem_id_list
	 * @param salesContext
	 * @return OrderType
	 */
	public OrderType submitMultipleLineItem(final String agentId,
			final String orderId, final List<String> lineitem_id_list, SalesContextType salesContext) {

		return submitMultipleLineItem(agentId, orderId, lineitem_id_list, salesContext,new ErrorList());
	}


	/**
	 * Submit Multiple LineItem
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineitem_id_list
	 * @param salesContext
	 * @param errorList
	 * @return OrderType
	 */
	public OrderType submitMultipleLineItem(final String agentId,
			final String orderId, final List<String> lineitem_id_list, SalesContextType salesContext,ErrorList errorList) {

		logger.info("submitMultipleLineItem -- orderId="+orderId+", lineitem list="+lineitem_id_list);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = oFactory
		.createOrderManagementRequestResponse();
		omrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		String correlationId = SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID");
		if(StringUtils.isBlank(correlationId)){
			correlationId = UUID.randomUUID().toString();
		}
		omrr.setCorrelationId(correlationId);
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.setTransactionType("submit");

		omrr.getRequest().setOrderId(orderId);

		for (String lineItemId : lineitem_id_list) {
			omrr.getRequest().getLineitemId().add(lineItemId);
		}

		omrr.getRequest().setAgentId(agentId);

		if(salesContext != null){
			omrr.getRequest().setSalesContext(salesContext);
		}

		logger.info("OME request : [TransactionType=submit, GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId+"]");
		long start = System.currentTimeMillis();
		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE
		.getOrderClient().send(omrr);

		OrderFactory.INSTANCE.validateOrder(cmrrR, errorList);

		OrderType orderR = null;

		if (cmrrR != null && cmrrR.getResponse() != null
				&& cmrrR.getResponse().getOrderInfo() != null) {
			List<OrderType> orderTypeList = cmrrR.getResponse().getOrderInfo();

			if (orderTypeList.size() > 0) {
				orderR = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(orderTypeList.get(0));

				if (orderR != null && orderR.getExternalId() != 0) {
					//Clearing CI cache since updateLineItem will add new CI to the list
					String customerId = orderR.getCustomerInformation().getCustomer().getExternalId().toString();
					CustomerInteractionCacheService.INSTANCE.clearCachedCustomerInteraction(customerId);

					OrderCacheService.INSTANCE.clear(orderId);
					OrderCacheService.INSTANCE.store(orderR, orderId);
					logger.info("OME request completed: [TransactionType=submit, GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId +", Time="+(System.currentTimeMillis()-start)+"]");
				}
			}
		}

		//return OrderService.INSTANCE.getOrderByOrderNumber(orderId);
		return orderR;
	}


	

	/**
	 * Submit Multiple LineItem
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineitem_id_list
	 * @param salesContext
	 * @param errorList
	 * @return OrderType
	 */
	public OrderType submitMultipleLineItem(final String agentId,
			final String orderId, final List<String> lineitem_id_list, SalesContextType salesContext,ErrorList errorList, boolean includeAH) {

		logger.info("submitMultipleLineItem -- orderId="+orderId+", lineitem list="+lineitem_id_list);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = oFactory
		.createOrderManagementRequestResponse();
		omrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		String correlationId = SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID");
		if(StringUtils.isBlank(correlationId)){
			correlationId = UUID.randomUUID().toString();
		}
		omrr.setCorrelationId(correlationId);
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.setTransactionType("submit");

		omrr.getRequest().setOrderId(orderId);
		omrr.getRequest().setIncludeAccountHolders(includeAH);

		for (String lineItemId : lineitem_id_list) {
			omrr.getRequest().getLineitemId().add(lineItemId);
		}

		omrr.getRequest().setAgentId(agentId);

		if(salesContext != null){
			omrr.getRequest().setSalesContext(salesContext);
		}

		logger.info("OME request : [TransactionType=submit, GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId+"]");
		long start = System.currentTimeMillis();
		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE
		.getOrderClient().send(omrr);

		OrderFactory.INSTANCE.validateOrder(cmrrR, errorList);

		OrderType orderR = null;

		if (cmrrR != null && cmrrR.getResponse() != null
				&& cmrrR.getResponse().getOrderInfo() != null) {
			List<OrderType> orderTypeList = cmrrR.getResponse().getOrderInfo();

			if (orderTypeList.size() > 0) {
				orderR = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(orderTypeList.get(0));

				if (orderR != null && orderR.getExternalId() != 0) {
					//Clearing CI cache since updateLineItem will add new CI to the list
					String customerId = orderR.getCustomerInformation().getCustomer().getExternalId().toString();
					CustomerInteractionCacheService.INSTANCE.clearCachedCustomerInteraction(customerId);

					OrderCacheService.INSTANCE.clear(orderId);
					OrderCacheService.INSTANCE.store(orderR, orderId);
					logger.info("OME request completed: [TransactionType=submit, GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId +", Time="+(System.currentTimeMillis()-start)+"]");
				}
			}
		}

		//return OrderService.INSTANCE.getOrderByOrderNumber(orderId);
		return orderR;
	}


	
	/**
	 * Get LineItem from Order with LineItemId
	 *
	 * @param agentId
	 * @param orderType
	 * @param lineItemId
	 * @param roles
	 * @return LineItemType
	 */
	public LineItemType getLineItem(final String agentId, OrderType orderType,
			long lineItemId, Map<String, Object> roles) {

		logger.debug("getLineItem from order, LineItemId: "+lineItemId);

		LineItemType targetLineItem = null;

		if (orderType != null && orderType.getLineItems() != null
				&& orderType.getLineItems().getLineItem() != null) {

			List<LineItemType> lineItemList = orderType.getLineItems()
			.getLineItem();

			for (LineItemType lit : lineItemList) {
				if (lineItemId == lit.getExternalId()) {
					targetLineItem = lit;
					break;
				}
			}
		}

		if (targetLineItem != null) {
			// Sorting Status History
			Collections.sort(targetLineItem.getLineItemStatusHistory()
					.getPreviousStatus(), new Comparator<LineItemStatusType>() {
				@Override
				public int compare(LineItemStatusType l1, LineItemStatusType l2) {
					return l2.getDateTimeStamp().compare(l1.getDateTimeStamp());
				}
			});
		}

		return targetLineItem;
	}

	/**
	 * Update LineItem
	 *
	 * @param agentId
	 * @param orderId
	 * @param lit
	 * @param lineItemId
	 * @return OrderType
	 *
	 * Deprecated: Use updateLineItem(final String agentId, final String orderId,final String transactionType,
			LineItemCollectionType liCollection, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType lineItemUpdate(final String agentId, final String orderId,
			final LineItemType lit, final long lineItemId) {

		return updateLineItem(agentId, orderId, lineItemId, "updateLineItem", lit);
	}

	/**
	 * Update LineItem
	 *
	 * @param agentId
	 * @param orderId
	 * @param lit
	 * @param lineItemId
	 * @param guid
	 * @return OrderType
	 *
	 * Deprecated: Use updateLineItem(final String agentId, final String orderId,final String transactionType,
			LineItemCollectionType liCollection, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType lineItemUpdate(final String agentId, final String orderId,
			final LineItemType lit, final long lineItemId, String guid) {

		SalesContextType salesContext = SalesContextFactory.INSTANCE.getSalesContext("CKO", "GUID", guid);
		return updateLineItem(agentId, orderId, lineItemId, "updateLineItem", lit, salesContext);
	}

	/**
	 * Update LineItem
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @param transactionType
	 * @param lit
	 * @return OrderType
	 *
	 * Deprecated: Use updateLineItem(final String agentId, final String orderId,final String transactionType,
			LineItemCollectionType liCollection, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType updateLineItem(final String agentId, final String orderId,
			final long lineItemId, final String transactionType,
			final LineItemType lit) {

		return updateLineItem(agentId, orderId, lineItemId, transactionType, lit, null);
	}

	/**
	 * Update LineItem
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @param transactionType
	 * @param lit
	 * @param salesContext
	 * @return OrderType
	 *
	 * Deprecated: Use updateLineItem(final String agentId, final String orderId,final String transactionType,
			LineItemCollectionType liCollection, SalesContextType salesContext)
	 */
	@Deprecated
	public OrderType updateLineItem(final String agentId, final String orderId,
			final long lineItemId, final String transactionType,
			final LineItemType lit, SalesContextType salesContext) {

		ObjectFactory oFactory = new ObjectFactory();

		if (lit != null) {
			lit.setExternalId(lineItemId);
		}

		OrderType orderInfo = OrderCacheService.INSTANCE.get(orderId);
		if(orderInfo == null){
			orderInfo = OrderService.INSTANCE.getOrderByOrderNumber(orderId);
		}

		int lineNumber = getLineItemNumber(orderInfo,lineItemId);
		lit.setLineItemNumber(lineNumber);
		logger.info("Lineitem External Id : "+lit.getExternalId());
		logger.info("LineItem Number: "+lit.getLineItemNumber());
		LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
		liCollection.getLineItem().add(lit);

		return updateLineItem(agentId, orderId, transactionType, liCollection, salesContext);
	}

	private int getLineItemNumber(OrderType orderInfo, long lineItemId) {
		int number = 0;
		boolean isLineNumberFound = false;
		for(LineItemType item : orderInfo.getLineItems().getLineItem()){
			if(item.getExternalId() == lineItemId){
				number = item.getLineItemNumber();
				isLineNumberFound = true;
				break;
			}
		}
		
		//if the Line-number is not found then retreive the order from database
		if(isLineNumberFound == false){
			logger.info(" LineItem not found in the order which is retreived from cache : "+lineItemId);
			logger.info(" Get the order from database : "+orderInfo.getExternalId());
			orderInfo = OrderService.INSTANCE.getOrderByOrderNumber(String.valueOf(orderInfo.getExternalId()));
			for(LineItemType item : orderInfo.getLineItems().getLineItem()){
				if(item.getExternalId() == lineItemId){
					number = item.getLineItemNumber();
					logger.info(" Lineitem found : "+lineItemId);
					isLineNumberFound = true;
					break;
				}
			}
			//store the latest order in cache
			OrderCacheService.INSTANCE.store(orderInfo, String.valueOf(orderInfo.getExternalId()));
		}
		return number;
	}

	/**
	 * Update LineItem
	 *
	 * @param AgentId
	 * @param OrderType ExtId
	 * @param LineItemType ExtId
	 * @param transactionType
	 * @param LineItemType
	 * @param SalesContextType
	 * @return OrderType
	 */
	public OrderType updateLineItem(final String agentId, final String orderId,final String transactionType,
			LineItemCollectionType liCollection, SalesContextType salesContext) {

		return updateLineItem(agentId, orderId, transactionType, liCollection, salesContext, new ErrorList());
	}
	
	/**
	 * Update LineItem
	 *
	 * @param AgentId
	 * @param OrderType ExtId
	 * @param LineItemType ExtId
	 * @param transactionType
	 * @param LineItemType
	 * @param SalesContextType
	 * @param isIncludeAccountHolders
	 * @return OrderType
	 */
	public OrderType updateLineItem(final String agentId, final String orderId,final String transactionType,
			LineItemCollectionType liCollection, SalesContextType salesContext, boolean isIncludeAccountHolders) {

		return updateLineItem(agentId, orderId, transactionType, liCollection, salesContext, new ErrorList(),isIncludeAccountHolders);
	}
	
	/**
	 * Update LineItem
	 *
	 *
	 * @param agentId
	 * @param orderId
	 * @param transactionType
	 * @param liCollection
	 * @param salesContext
	 * @param errorList
	 * @return OrderType
	 */
	public OrderType updateLineItem(final String agentId, final String orderId,final String transactionType,
			LineItemCollectionType liCollection, SalesContextType salesContext, ErrorList errorList, boolean isIncludeAccountHolders) {
		logger.info("updateLineItem, orderId : "+orderId);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = oFactory
		.createOrderManagementRequestResponse();
		omrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		String correlationId = SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID");
		if(StringUtils.isBlank(correlationId)){
			correlationId = UUID.randomUUID().toString();
		}
		omrr.setCorrelationId(correlationId);
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.setTransactionType(transactionType);

		if (orderId != null) {
			omrr.getRequest().setOrderId(orderId);
			omrr.getRequest().setIncludeAccountHolders(isIncludeAccountHolders);
		}

		omrr.getRequest().setAgentId(agentId);
		
		if (liCollection != null) {
			OrderType orderInfo = OrderCacheService.INSTANCE.get(orderId);
			if(orderInfo == null){
				orderInfo = OrderService.INSTANCE.getOrderWithAccountHoldersByOrderNumber(orderId, agentId, Boolean.FALSE,Boolean.TRUE);
			}
			for (LineItemType item : liCollection.getLineItem()) {
				int lineNumber = getLineItemNumber(orderInfo,
						item.getExternalId());
				item.setLineItemNumber(lineNumber);
				if (item.getNotificationEvents() != null) {
					omrr.getRequest().setNotificationEvents(
							item.getNotificationEvents());
					item.setNotificationEvents(null);
				}
			}
			omrr.getRequest().setUpdatedLineItemInfo(liCollection);
		}

		if(salesContext != null){
			omrr.getRequest().setSalesContext(salesContext);
		}

		logger.info("OME request : [TransactionType="+ transactionType+", GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId +"]");
		long start = System.currentTimeMillis();
		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);
		OrderFactory.INSTANCE.validateOrder(cmrrR, errorList);

		OrderType orderR = OrderService.INSTANCE.getOrderWithAccountHoldersByOrderNumber(orderId, agentId, Boolean.FALSE,Boolean.TRUE);

/*		if (cmrrR != null && cmrrR.getResponse() != null
				&& cmrrR.getResponse().getOrderInfo() != null) {
			List<OrderType> orderTypeList = cmrrR.getResponse().getOrderInfo();

			if (orderTypeList.size() > 0) {
				orderR = orderTypeList.get(0);
				orderR = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(orderTypeList.get(0));
				if (orderR != null && orderR.getExternalId() != 0) {
					//Clearing CI cache since updateLineItem will add new CI to the list
					String customerId = orderR.getCustomerInformation().getCustomer().getExternalId().toString();
					CustomerInteractionCacheService.INSTANCE.clearCachedCustomerInteraction(customerId);

					OrderCacheService.INSTANCE.clear(orderId);
					OrderCacheService.INSTANCE.store(orderR, orderId);
					logger.info("OME request completed: [TransactionType"+ transactionType+", GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId +", Time="+(System.currentTimeMillis()-start)+"]");
				}
			}
		}*/

		return orderR;
	}	

	/**
	 * Update LineItem
	 *
	 *
	 * @param agentId
	 * @param orderId
	 * @param transactionType
	 * @param liCollection
	 * @param salesContext
	 * @param errorList
	 * @return OrderType
	 */
	public OrderType updateLineItem(final String agentId, final String orderId,final String transactionType,
			LineItemCollectionType liCollection, SalesContextType salesContext, ErrorList errorList) {
		logger.info("updateLineItem, orderId : "+orderId);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = oFactory
		.createOrderManagementRequestResponse();
		omrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		String correlationId = SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID");
		if(StringUtils.isBlank(correlationId)){
			correlationId = UUID.randomUUID().toString();
		}
		omrr.setCorrelationId(correlationId);
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.setTransactionType(transactionType);

		if (orderId != null) {
			omrr.getRequest().setOrderId(orderId);
		}

		omrr.getRequest().setAgentId(agentId);

		if (liCollection != null) {
			OrderType orderInfo = OrderCacheService.INSTANCE.get(orderId);
			if(orderInfo == null){
				orderInfo = OrderService.INSTANCE.getOrderByOrderNumber(orderId,agentId,Boolean.FALSE);
			}
			for(LineItemType item : liCollection.getLineItem()){
				int lineNumber = getLineItemNumber(orderInfo,item.getExternalId());
				item.setLineItemNumber(lineNumber);
				if(item.getNotificationEvents() != null){
				omrr.getRequest().setNotificationEvents(item.getNotificationEvents());
				item.setNotificationEvents(null);
				}
			}
			omrr.getRequest().setUpdatedLineItemInfo(liCollection);
		}

		if(salesContext != null){
			omrr.getRequest().setSalesContext(salesContext);
		}

		logger.info("OME request : [TransactionType="+ transactionType+", GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId +"]");
		long start = System.currentTimeMillis();
		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE
		.getOrderClient().send(omrr);
		OrderFactory.INSTANCE.validateOrder(cmrrR, errorList);

		OrderType orderR = null;

		if (cmrrR != null && cmrrR.getResponse() != null
				&& cmrrR.getResponse().getOrderInfo() != null) {
			List<OrderType> orderTypeList = cmrrR.getResponse().getOrderInfo();

			if (orderTypeList.size() > 0) {
				orderR = orderTypeList.get(0);
				orderR = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(orderTypeList.get(0));
				if (orderR != null && orderR.getExternalId() != 0) {
					//Clearing CI cache since updateLineItem will add new CI to the list
					String customerId = orderR.getCustomerInformation().getCustomer().getExternalId().toString();
					CustomerInteractionCacheService.INSTANCE.clearCachedCustomerInteraction(customerId);

					OrderCacheService.INSTANCE.clear(orderId);
					OrderCacheService.INSTANCE.store(orderR, orderId);
					logger.info("OME request completed: [TransactionType"+ transactionType+", GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId +", Time="+(System.currentTimeMillis()-start)+"]");
				}
			}
		}

		return orderR;
	}
	
	/**
	 * Update LineItemLW
	 * This method is implemented to Handle the new TransactionType 'updateLineItemLW'
	 * This transacionType handles the below operations.
	 * Add AccountHolder
	 * Update AccountHolder
	 * Add PaymentEvets to the LineItem
	 *
	 * @param AgentId
	 * @param OrderType ExtId
	 * @param LineItemType ExtId
	 * @param transactionType
	 * @param LineItemType
	 * @param SalesContextType
	 * @return OrderType
	 */
	public OrderType updateLineItemLW(final String agentId, final String orderId,final String transactionType,
			LineItemCollectionType liCollection, SalesContextType salesContext, AccountHolderType accountHolder) {

		return updateLineItemLW(agentId, orderId, transactionType, liCollection, salesContext, new ErrorList(),accountHolder);
	}
	
	/**
	 * Update LineItem
	 *
	 *
	 * @param agentId
	 * @param orderId
	 * @param transactionType
	 * @param liCollection
	 * @param salesContext
	 * @param errorList
	 * @return OrderType
	 */
	public OrderType updateLineItemLW(final String agentId, final String orderId,final String transactionType,
			LineItemCollectionType liCollection, SalesContextType salesContext, ErrorList errorList, AccountHolderType accountHolder) {
		logger.info("updateLineItem, orderId : "+orderId);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = oFactory.createOrderManagementRequestResponse();
		omrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		String correlationId = SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID");
		if(StringUtils.isBlank(correlationId)){
			correlationId = UUID.randomUUID().toString();
		}
		omrr.setCorrelationId(correlationId);
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.setTransactionType(transactionType);

		if (orderId != null) {
			omrr.getRequest().setOrderId(orderId);
			omrr.getRequest().setIncludeAccountHolders(true);
		}

		omrr.getRequest().setAgentId(agentId);

		if (liCollection != null) {
			OrderType orderInfo = OrderCacheService.INSTANCE.get(orderId);
			if(orderInfo == null){
				orderInfo = OrderService.INSTANCE.getOrderWithAccountHoldersByOrderNumber(orderId, agentId, Boolean.FALSE,Boolean.TRUE);
			}
			for(LineItemType item : liCollection.getLineItem()){
				int lineNumber = getLineItemNumber(orderInfo,item.getExternalId());
				item.setLineItemNumber(lineNumber);
				if(item.getNotificationEvents() != null){
				omrr.getRequest().setNotificationEvents(item.getNotificationEvents());
				item.setNotificationEvents(null);
				}
			}
			omrr.getRequest().setUpdatedLineItemInfo(liCollection);
		}

		if(salesContext != null){
			omrr.getRequest().setSalesContext(salesContext);
		}
		
		//Account Holders
		if(accountHolder != null){
			omrr.getRequest().getAccountHolder().add(accountHolder);
		}

		logger.info("OME request : [TransactionType="+ transactionType+", GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId +"]");
		
		long start = System.currentTimeMillis();
		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);
		OrderFactory.INSTANCE.validateOrder(cmrrR, errorList);

		OrderType orderR = null;

		if (cmrrR != null && cmrrR.getResponse() != null
				&& cmrrR.getResponse().getOrderInfo() != null) {
			List<OrderType> orderTypeList = cmrrR.getResponse().getOrderInfo();

			if (orderTypeList.size() > 0) {
				orderR = orderTypeList.get(0);
				orderR = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(orderTypeList.get(0));
				if (orderR != null && orderR.getExternalId() != 0) {
					//Clearing CI cache since updateLineItem will add new CI to the list
					String customerId = orderR.getCustomerInformation().getCustomer().getExternalId().toString();
					CustomerInteractionCacheService.INSTANCE.clearCachedCustomerInteraction(customerId);

					OrderCacheService.INSTANCE.clear(orderId);
					OrderCacheService.INSTANCE.store(orderR, orderId);
					logger.info("OME request completed: [TransactionType"+ transactionType+", GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId +", Time="+(System.currentTimeMillis()-start)+"]");
				}
			}
		}

		return orderR;
	}	
	
	/**
	 * Add LineItem to order
	 *
	 * @param agentId
	 * @param orderId
	 * @param liCollection
	 * @return OrderType
	 *
	 * Deprecated: Use addLineItem(final String agentId, final String orderId,
			final LineItemCollectionType liCollection, SalesContextType salesContext, boolean isLiIncluded)
	 */
	@Deprecated
	public OrderType addLineItem(final String agentId, final String orderId,
			final LineItemCollectionType liCollection) {

		SalesContextType salesContext = null;
		return addLineItem(agentId, orderId, liCollection, salesContext);
	}

	/**
	 * Add LineItem to order
	 *
	 * @param agentId
	 * @param orderId
	 * @param liCollection
	 * @param GUID
	 * @return OrderType
	 *
	 * Deprecated: Use addLineItem(final String agentId, final String orderId,
			final LineItemCollectionType liCollection, SalesContextType salesContext, boolean isLiIncluded)
	 */
	@Deprecated
	public OrderType addLineItem(final String agentId, final String orderId,
			final LineItemCollectionType liCollection, String GUID) {

		SalesContextType salesContext = SalesContextFactory.INSTANCE.getSalesContext("CKO", "GUID", GUID);
		return addLineItem(agentId, orderId, liCollection, salesContext);
	}

	/**
	 * Add LineItem product to order
	 *
	 * @param agentId
	 * @param orderId
	 * @param liCollection
	 * @param salesContext
	 * @return OrderType
	 *
	 * Deprecated: Use addLineItem(final String agentId, final String orderId,
			final LineItemCollectionType liCollection, SalesContextType salesContext, boolean isLiIncluded)
	 */
	@Deprecated
	public OrderType addLineItem(final String agentId, final String orderId,
			final LineItemCollectionType liCollection, SalesContextType salesContext) {
		return addLineItem(agentId, orderId, liCollection, salesContext, true);
	}

	/**
	 * Add LineItem product/promotion to order
	 *
	 * @param agentId
	 * @param orderId
	 * @param liCollection
	 * @param salesContext
	 * @return OrderType
	 */
	public OrderType addLineItem(final String agentId, final String orderId,
			final LineItemCollectionType liCollection, SalesContextType salesContext, boolean isLiIncluded) {
		logger.info("Adding lineitem synchronously");
		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = oFactory.createOrderManagementRequestResponse();
		omrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		String correlationId = SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID");
		if(StringUtils.isBlank(correlationId)){
			correlationId = UUID.randomUUID().toString();
		}
		omrr.setCorrelationId(correlationId);
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.setTransactionType("addLineItem");

		if(salesContext == null){
			salesContext = SalesContextFactory.INSTANCE.getSalesContext("source", "notes_source", "accord");
		}
		omrr.getRequest().setSalesContext(salesContext);

		if (orderId != null) {
			omrr.getRequest().setOrderId(orderId);
		}
		omrr.getRequest().setAgentId(agentId);
		omrr.getRequest().setAfterLineItemNumber(9999);
		omrr.getRequest().setIsAppliesToLineItemIncluded(isLiIncluded);
		omrr.getRequest().setNewLineItems(liCollection);
		logger.info("OME request : [TransactionType=addLineItem, GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId+"]");
		long start = System.currentTimeMillis();
		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE
		.getOrderClient().send(omrr);

		OrderType orderR = null;

		if (cmrrR != null && cmrrR.getResponse() != null
				&& cmrrR.getResponse().getOrderInfo() != null) {
			List<OrderType> orderTypeList = cmrrR.getResponse().getOrderInfo();

			if (orderTypeList.size() > 0) {
				orderR = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(orderTypeList.get(0));
				if (orderR != null && orderR.getExternalId() != 0) {
					OrderCacheService.INSTANCE.clear(orderId);
					OrderCacheService.INSTANCE.store(orderR, orderId);
				}else{
					OrderCacheService.INSTANCE.clear(orderId);
				}

				logger.info("OME request completed: [TransactionType=addLineItem, GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId +", Time="+(System.currentTimeMillis()-start)+"]");
			}
		}

		return orderR;
	}
	
	
	/**
     * Asynchronous addLineItem call. Be careful about using this service call as it does not return
     * response.
     *
     * @param agentId
     * @param orderId
     * @param liCollection
     * @param salesContext
     * @param isLiIncluded
     * @return Nothing.
     */
     public void asyncAddLineItem(final String agentId, final String orderId,
                final LineItemCollectionType liCollection, SalesContextType salesContext, boolean isLiIncluded) {
           logger.info("Adding lineitem asynchronously");
           ObjectFactory oFactory = new ObjectFactory();
           OrderManagementRequestResponse omrr = oFactory.createOrderManagementRequestResponse();
           omrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
           String correlationId = SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID");
           if(StringUtils.isBlank(correlationId)){
   				correlationId = UUID.randomUUID().toString();
   			}
           omrr.setCorrelationId(correlationId);
           omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
           omrr.setTransactionType("addLineItem");
           omrr.getRequest().setSalesContext(salesContext);

           if (orderId != null) {
                omrr.getRequest().setOrderId(orderId);
           }
           omrr.getRequest().setAgentId(agentId);
           omrr.getRequest().setAfterLineItemNumber(9999);
           omrr.getRequest().setIsAppliesToLineItemIncluded(isLiIncluded);
           omrr.getRequest().setNewLineItems(liCollection);
           logger.info("OME Async request : [TransactionType=addLineItem, GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId+"]");
           long start = System.currentTimeMillis();
           TransportConfig.INSTANCE.getOrderClient().sendAsync(omrr);
           logger.info("OME Async request completed: [TransactionType=addLineItem, GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId +", Time="+(System.currentTimeMillis()-start)+"]");
     }
     

	/**
	 * This is to lock the lineitem of this customer
	 *
	 * @param agentId
	 * @param lineitemId
	 * @return
	 */
	public boolean lockLineitem(String agentId, String jobId){
		logger.info("lockLineitem, jobId: "+jobId);

		JobType job = JobService.INSTANCE.getJobByExternalId(jobId, agentId);
		if(job == null){
			logger.debug("There is no job with ExtId: "+jobId);
			return true;
		} else if(job.getTtl() > 0L){
			return false;
		} else {
			//Lock order in separate process
			JobUpdateThread jut = new JobUpdateThread(agentId, jobId, null, null, Boolean.TRUE,
					Constants.IND_LOCK_TYPE, null);
			new Thread(jut).start();
			return true;
		}
	}
	
	/**
	 * @param orderId
	 * @param lineItemId
	 * @param statuscode
	 * @param reasonCode
	 * @return
	 */
	public String extUpdateLineItemStatus(String orderId, String lineItemId, String statuscode, Integer reasonCode){
		
		logger.debug("External Process updateLineItemStatus: lineItemId : "+lineItemId+", Order ID : "+orderId);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = oFactory
		.createOrderManagementRequestResponse();
		omrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.setTransactionType("updateLineItemStatusLW");
		String agentId = "external";
		
		String entityName = "SYP";
		String[] names = {"source", "updatedBy", "sessionStatus"};
		String[] values = {"V", "ext_process", "completed"};
		
		SalesContextType salesContextType = new SalesContextType();

		com.A.xml.v4.SalesContextEntityType salesContextEntityType = new com.A.xml.v4.SalesContextEntityType();
		salesContextEntityType.setName(entityName);
		for(int i = 0; i<names.length; i++){
			com.A.xml.v4.NameValuePairType nameValuePairType = new com.A.xml.v4.NameValuePairType();
			nameValuePairType.setName(names[i]);
			nameValuePairType.setValue(values[i]);
			salesContextEntityType.getAttribute().add(nameValuePairType);
		}
		
		salesContextType.getEntity().add(salesContextEntityType);
				
		omrr.getRequest().setOrderId(orderId);
		omrr.getRequest().getLineitemId().add(lineItemId);
		omrr.getRequest().setAgentId(agentId);
		if(salesContextType != null){
			omrr.getRequest().setSalesContext(salesContextType);
		}
		
		List<Integer> reasonCodes = new ArrayList<Integer>();
		reasonCodes.add(reasonCode);

		LineItemStatusType liStatusType = LineItemStatusBuilder.INSTANCE.build(
				agentId, statuscode, statuscode, reasonCodes);

		omrr.getRequest().setNewLineItemStatus(liStatusType);

		logger.info("OME request : [TransactionType=updateLineItemStatusLW, AgentId="+agentId+",OrderId="+orderId+"]");
		long start = System.currentTimeMillis();
		ErrorList errorList = new ErrorList();
		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE
		.getOrderClient().send(omrr);
		
		String responseStatus = "success";
		if (cmrrR != null && cmrrR.getResponse() != null
				&& cmrrR.getResponse().getOrderInfo() != null) {
			OrderFactory.INSTANCE.validateOrder(cmrrR, errorList);
			if(errorList != null && !errorList.isEmpty() && errorList.size() != 0){
				responseStatus = "failed";
				logger.error(" Error OME Response : [TransactionType=updateLineItemStatusLW, AgentId="+agentId+",OrderId="+orderId+"], Error Message"+
						errorList.get(0).getMessage());
			}
		} else {
			responseStatus = "serviceUnavailable";
			logger.info(" EMPTY OME Response : [TransactionType=updateLineItemStatusLW, AgentId="+agentId+",OrderId="+orderId+"]");
		}

		return responseStatus;
		
	}
	
	/**
	 * Add LineItem product/promotion to order and get order with AccountHolders 
	 *
	 * @param agentId
	 * @param orderId
	 * @param liCollection
	 * @param salesContext
	 * @param isIncludeAccountHolders
	 * @return OrderType
	 */
	public OrderType addLineItem(final String agentId, final String orderId,
			final LineItemCollectionType liCollection, SalesContextType salesContext, boolean isLiIncluded,boolean isIncludeAccountHolders) {
		logger.info("Adding lineitem synchronously");
		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = oFactory.createOrderManagementRequestResponse();
		omrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		String correlationId = SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID");
		if(StringUtils.isBlank(correlationId)){
			correlationId = UUID.randomUUID().toString();
		}
		omrr.setCorrelationId(correlationId);
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.setTransactionType("addLineItem");

		if(salesContext == null){
			salesContext = SalesContextFactory.INSTANCE.getSalesContext("source", "notes_source", "accord");
		}
		omrr.getRequest().setSalesContext(salesContext);

		if (orderId != null) {
			omrr.getRequest().setOrderId(orderId);
		}
		omrr.getRequest().setAgentId(agentId);
		omrr.getRequest().setIncludeAccountHolders(isIncludeAccountHolders);
		omrr.getRequest().setAfterLineItemNumber(9999);
		omrr.getRequest().setIsAppliesToLineItemIncluded(isLiIncluded);
		omrr.getRequest().setNewLineItems(liCollection);
		logger.info("OME request : [TransactionType=addLineItem, GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId+"]");
		long start = System.currentTimeMillis();
		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE
		.getOrderClient().send(omrr);

		OrderType orderR = null;

		if (cmrrR != null && cmrrR.getResponse() != null
				&& cmrrR.getResponse().getOrderInfo() != null) {
			List<OrderType> orderTypeList = cmrrR.getResponse().getOrderInfo();

			if (orderTypeList.size() > 0) {
				orderR = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(orderTypeList.get(0));
				if (orderR != null && orderR.getExternalId() != 0) {
					OrderCacheService.INSTANCE.clear(orderId);
					OrderCacheService.INSTANCE.store(orderR, orderId);
				}else{
					OrderCacheService.INSTANCE.clear(orderId);
				}

				logger.info("OME request completed: [TransactionType=addLineItem, GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId +", Time="+(System.currentTimeMillis()-start)+"]");
			}
		}

		return orderR;
	}

	public OrderType updateLineItemStatus(final String agentId,
			final String orderId, final List<String> lineItemIdList,
			final String statusCode, final String description,
			final List<Integer> reasons, SalesContextType salesContext, ErrorList errorList,
			NotificationEventCollectionType notificationEventCollectionType) {
		logger.debug("updateLineItemStatus: lineItemIdList : "+lineItemIdList);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = oFactory
		.createOrderManagementRequestResponse();
		omrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		String correlationId = SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID");
		if(StringUtils.isBlank(correlationId)){
			correlationId = UUID.randomUUID().toString();
		}
		logger.info("updateLineItemStatus ------ [GUID: " +correlationId +"]");
		omrr.setCorrelationId(correlationId);
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.setTransactionType("updateLineItemStatus");

		omrr.getRequest().setOrderId(orderId);
		for(String lineItemId : lineItemIdList){
			omrr.getRequest().getLineitemId().add(lineItemId);
		}
		omrr.getRequest().setAgentId(agentId);
		if(salesContext != null){
			omrr.getRequest().setSalesContext(salesContext);
		}
		
		if(notificationEventCollectionType != null && notificationEventCollectionType.getEvent().size() > 0){
			omrr.getRequest().setNotificationEvents(notificationEventCollectionType);
		}

		LineItemStatusType liStatusType = LineItemStatusBuilder.INSTANCE.build(
				agentId, statusCode, description, reasons);

		omrr.getRequest().setNewLineItemStatus(liStatusType);

		logger.info("OME request : [TransactionType=updateLineItemStatus, GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId+"]");
		long start = System.currentTimeMillis();
		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE
		.getOrderClient().send(omrr);
		OrderFactory.INSTANCE.validateOrder(cmrrR, errorList);

		OrderType orderR = null;

		if (cmrrR != null && cmrrR.getResponse() != null
				&& cmrrR.getResponse().getOrderInfo() != null) {
			List<OrderType> orderTypeList = cmrrR.getResponse().getOrderInfo();

			if (orderTypeList.size() > 0) {
				orderR = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(orderTypeList.get(0));

				if (orderR != null && orderR.getExternalId() != 0) {
					//Clearing CI cache since updateLineItem will add new CI to the list
					String customerId = orderR.getCustomerInformation().getCustomer().getExternalId().toString();
					CustomerInteractionCacheService.INSTANCE.clearCachedCustomerInteraction(customerId);

					OrderCacheService.INSTANCE.clear(orderId);
					OrderCacheService.INSTANCE.store(orderR, orderId);
					logger.info("OME request completed: [TransactionType=addLineItem, GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId +", Time="+(System.currentTimeMillis()-start)+"]");
				}
			}
		}

		return orderR;
	}
	
	/**
	 * Submit Multiple LineItem
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineitem_id_list
	 * @param salesContext
	 * @return OrderType
	 * @return zipOnlySearch
	 */
	public OrderType submitMultipleLineItem(final String agentId,
			final String orderId, final List<String> lineitem_id_list, SalesContextType salesContext, int zipOnlySearch) {

		return submitMultipleLineItem(agentId, orderId, lineitem_id_list, salesContext,new ErrorList(), zipOnlySearch);
	}

	/**
	 * Submit Multiple LineItem
	 *
	 * @param agentId
	 * @param orderId
	 * @param lineitem_id_list
	 * @param salesContext
	 * @param errorList
	 * @return OrderType
	 * @return zipOnlySearch
	 */
	public OrderType submitMultipleLineItem(final String agentId,
			final String orderId, final List<String> lineitem_id_list, SalesContextType salesContext,ErrorList errorList, int zipOnlySearch) {

		logger.info("submitMultipleLineItem -- orderId="+orderId+", lineitem list="+lineitem_id_list);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = oFactory
		.createOrderManagementRequestResponse();
		omrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		String correlationId = SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID");
		if(StringUtils.isBlank(correlationId)){
			correlationId = UUID.randomUUID().toString();
		}
		omrr.setCorrelationId(correlationId);
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.setTransactionType("submit");

		omrr.getRequest().setOrderId(orderId);

		for (String lineItemId : lineitem_id_list) {
			omrr.getRequest().getLineitemId().add(lineItemId);
		}

		omrr.getRequest().setAgentId(agentId);

		if(salesContext != null){
			omrr.getRequest().setSalesContext(salesContext);
		}
		if(zipOnlySearch == 1){
			omrr.getRequest().getUpdateOrderInfo().setIsZipOnlySearch(zipOnlySearch);
		}
		logger.info("OME request : [TransactionType=submit, GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId+"]");
		long start = System.currentTimeMillis();
		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE
		.getOrderClient().send(omrr);

		OrderFactory.INSTANCE.validateOrder(cmrrR, errorList);

		OrderType orderR = null;

		if (cmrrR != null && cmrrR.getResponse() != null
				&& cmrrR.getResponse().getOrderInfo() != null) {
			List<OrderType> orderTypeList = cmrrR.getResponse().getOrderInfo();

			if (orderTypeList.size() > 0) {
				orderR = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(orderTypeList.get(0));

				if (orderR != null && orderR.getExternalId() != 0) {
					//Clearing CI cache since updateLineItem will add new CI to the list
					String customerId = orderR.getCustomerInformation().getCustomer().getExternalId().toString();
					CustomerInteractionCacheService.INSTANCE.clearCachedCustomerInteraction(customerId);

					OrderCacheService.INSTANCE.clear(orderId);
					OrderCacheService.INSTANCE.store(orderR, orderId);
					logger.info("OME request completed: [TransactionType=submit, GUID="+correlationId+", AgentId="+agentId+",OrderId="+orderId +", Time="+(System.currentTimeMillis()-start)+"]");
				}
			}
		}

		//return OrderService.INSTANCE.getOrderByOrderNumber(orderId);
		return orderR;
	}

}
