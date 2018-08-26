package com.A.ui.service.V;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.A.ui.repository.SessionCache;
import com.A.ui.service.V.impl.OrderCacheService;
import com.A.ui.transport.TransportConfig;
import com.A.ui.vo.ErrorList;
import com.A.ui.vo.OrderVO;
import com.A.util.DateUtil;
import com.A.V.factory.CustomerFactory;
import com.A.V.factory.OrderFactory;
import com.A.V.factory.SalesContextFactory;
import com.A.V.service.auth.AuthorizationFilter;
import com.A.xml.v4.DateTimeOrTimeRangeType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.ObjectFactory;
import com.A.xml.v4.OrderManagementRequestResponse;
import com.A.xml.v4.OrderManagementRequestResponse.PagingDetail;
import com.A.xml.v4.OrderManagementRequestResponse.Response;
import com.A.xml.v4.OrderSearch;
import com.A.xml.v4.OrderStatusWithTypeType;
import com.A.xml.v4.OrderType;
import com.A.xml.v4.SalesContextType;

public enum OrderService {

	INSTANCE;

	private static final String PROCESSING_SCHEDULE_PENDING = "processing_schedule_pending";
	private static final String HOLD_ORDER_PARAM = "hold_order";
	private static final String REASON_PARAM = "reason";
	private static final String TRANSACTION_TYPE_GET_ORDER_BY_STATUS = "getOrderByStatus";

	private static final String SALES_CONTEXT = "SalesContext";
	private static final String ORDER_TYPE = "OrderType";

	private static final Map<String, Object> DEFAULT_ROLES = new HashMap<String, Object>();
	private static final String DEFAULT_ACTIVE_PROVIDER = "";

	private static final String COMPLETE_ORDER = "WHOLE_ORDER_BY_LINE_ITEM";

	public static final Logger logger = Logger.getLogger(OrderService.class);

	/**
	 * @param orderType
	 * @param roles
	 * @param ACTIVE_PROVIDER
	 */
	public void filterOrder(final OrderType orderType, Map<String, Object> roles, final String ACTIVE_PROVIDER) {

		if (orderType != null) {
			AuthorizationFilter.INSTANCE.removeDeleted(orderType);
			AuthorizationFilter.INSTANCE.filterShowAuthorizedView(orderType, roles, ACTIVE_PROVIDER);
		}
	}

	/**
	 * @param agentId
	 * @param orderId
	 * @param lineItemIdList
	 * @return OrderType
	 */
	public OrderType submitLineItem(final String agentId, final String orderId,
			final List<String> lineItemIdList) {

		OrderType orderType = null;

		orderType = submitLineItem(agentId, orderId, lineItemIdList, UUID.randomUUID().toString());

		return orderType;

	}

	/**
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @return OrderType
	 */
	public OrderType submitLineItem(final String agentId, final String orderId, final String lineItemId) {

		List<String> ids = new ArrayList<String>();
		ids.add(lineItemId);

		return submitLineItem(agentId, orderId, ids, UUID.randomUUID().toString());

	}

	/**
	 * @param agentId
	 * @param orderId
	 * @param salesContext
	 * @param order
	 * @param guid
	 * @return
	 */
	public OrderManagementRequestResponse updateOrder(final String agentId, final String orderId,
			final SalesContextType salesContext, final OrderType order, String guid) {

		OrderCacheService.INSTANCE.clear(orderId);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse.Request request = oFactory
				.createOrderManagementRequestResponseRequest();
		request.setSalesContext(salesContext);
		request.setOrderId(orderId);
		request.setUpdateOrderInfo(order);

		order.setAgentId(agentId);
		Long orderIdAsLong = Long.valueOf(orderId);
		order.setExternalId(orderIdAsLong);

		OrderManagementRequestResponse omrr = updateOMRR(request, oFactory, VOperation.updateOrder.name());

		omrr.setCorrelationId(guid);
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));

		omrr.getRequest().setUpdateOrderInfo(order);
		omrr.getRequest().setAgentId(agentId);

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);
		// This is for camel case...
		// Start
		if (omrrR != null && omrrR.getResponse() != null && omrrR.getResponse().getOrderInfo() != null
				&& omrrR.getResponse().getOrderInfo().size() > 0) {
			if (omrrR.getResponse().getOrderInfo().size() > 0) {
				OrderType orderType = omrrR.getResponse().getOrderInfo().get(0);
				orderType = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(orderType);
				omrrR.getResponse().getOrderInfo().set(0, orderType);
			}
		}
		// end
		return omrrR;

	}

	/**
	 * @param agentId
	 * @param orderId
	 * @param order
	 * @param guid
	 * @return OrderType
	 * 
	 *         Deprecated: Use OrderType updateOrder(final String agentId, final
	 *         String orderId, final OrderType order, final SalesContextType
	 *         salesContext, String guid); instead of this method
	 */
	@Deprecated
	public OrderType updateOrder(final String agentId, final String orderId, final OrderType order,
			String guid) {
		return updateOrder(agentId, orderId, order, null, guid);
	}

	/**
	 * @param agentId
	 * @param orderId
	 * @param order
	 * @param salesContext
	 * @param guid
	 * @return OrderType
	 */
	public OrderType updateOrder(final String agentId, final String orderId, final OrderType order,
			final SalesContextType salesContext, String guid) {
		OrderCacheService.INSTANCE.clear(orderId);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, VOperation.updateOrder.name());

		if (StringUtils.isBlank(guid)) {
			guid = UUID.randomUUID().toString();
		}
		omrr.setCorrelationId(guid);
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));

		order.setAgentId(agentId);
		omrr.getRequest().setUpdateOrderInfo(order);
		omrr.getRequest().setAgentId(agentId);
		if (salesContext != null) {
			omrr.getRequest().setSalesContext(salesContext);
		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		OrderType orderR = null;

		if ((omrrR != null) && (omrrR.getResponse() != null) && (omrrR.getResponse().getOrderInfo() != null)) {
			List<OrderType> orderTypeList = omrrR.getResponse().getOrderInfo();

			if (orderTypeList.size() > 0) {
				orderR = orderTypeList.get(0);
			}
		}

		orderR = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(orderR);
		OrderCacheService.INSTANCE.store(orderR, orderId);

		return orderR;
	}

	/**
	 * @param agentId
	 * @param orderId
	 * @param lineItemIds
	 * @param guid
	 * @return OrderType
	 * 
	 *         Deprecated: Use OrderType submitLineItem(final String agentId,
	 *         final String orderId, final List<String> lineItemIds, final
	 *         SalesContextType salesContext, String guid) instead of this
	 *         method
	 */
	@Deprecated
	public OrderType submitLineItem(final String agentId, final String orderId,
			final List<String> lineItemIds, String guid) {

		return submitLineItem(agentId, orderId, lineItemIds, null, guid);
	}

	/**
	 * @param agentId
	 * @param orderId
	 * @param lineItemIds
	 * @param salesContext
	 * @param guid
	 * @return OrderType
	 */
	public OrderType submitLineItem(final String agentId, final String orderId,
			final List<String> lineItemIds, final SalesContextType salesContext, String guid) {
		OrderCacheService.INSTANCE.clear(orderId);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, VOperation.submit.name());

		omrr.setCorrelationId(guid);
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));

		omrr.getRequest().setOrderId(orderId);
		omrr.getRequest().getLineitemId().addAll(lineItemIds);
		omrr.getRequest().setAgentId(agentId);
		if (salesContext != null) {
			omrr.getRequest().setSalesContext(salesContext);
		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		OrderType orderR = null;

		if ((omrrR != null) && (omrrR.getResponse() != null) && (omrrR.getResponse().getOrderInfo() != null)) {
			List<OrderType> orderTypeList = omrrR.getResponse().getOrderInfo();

			if (orderTypeList.size() > 0) {
				orderR = orderTypeList.get(0);
			}
		}
		orderR = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(orderR);
		OrderCacheService.INSTANCE.store(orderR, orderId);

		return orderR;
	}

	/**
	 * To create new order
	 * 
	 * @param salesContextType
	 * @param order
	 * @return OrderType
	 * 
	 *         Use OrderType createOrder(String agentId, SalesContextType
	 *         salesContextType, OrderType order) instead of this method
	 */
	@Deprecated
	public OrderType createOrder(SalesContextType salesContextType, OrderType order) {
		return createOrder("default", salesContextType, order);
	}

	/**
	 * To create new order
	 * 
	 * @param agentId
	 * @param salesContextType
	 * @param order
	 * @return OrderType
	 */
	public OrderType createOrder(String agentId, SalesContextType salesContextType, OrderType order) {

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, VOperation.createOrder.name());

		if (agentId != null) {
			omrr.getRequest().setAgentId(agentId);
			order.setAgentId(agentId);
		}
		omrr.getRequest().setOrderInfo(order);
		omrr.getRequest().setSalesContext(salesContextType);

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		OrderType orderR = null;

		if ((omrrR != null) && (omrrR.getResponse() != null) && (omrrR.getResponse().getOrderInfo() != null)) {
			List<OrderType> orderTypeList = omrrR.getResponse().getOrderInfo();

			if (orderTypeList.size() > 0) {
				orderR = orderTypeList.get(0);
			}
		}

		logger.info("[createOrder] OrderId :::::: " + orderR.getExternalId());
		orderR = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(orderR);
		OrderCacheService.INSTANCE.store(orderR, orderR.getExternalId() + "");

		return orderR;
	}

	/**
	 * @param confirmationNumber
	 * @param roles
	 * @param ACTIVE_PROVIDER
	 * @return
	 */
	public OrderType getOrderByConfirmationNumber(final String confirmationNumber, Map<String, Object> roles,
			final String ACTIVE_PROVIDER) {
		return getOrderByConfirmationNumber("default", confirmationNumber, roles, ACTIVE_PROVIDER);
	}

	/**
	 * @param agentId
	 * @param confirmationNumber
	 * @param roles
	 * @param ACTIVE_PROVIDER
	 * @return
	 */
	public OrderType getOrderByConfirmationNumber(String agentId, final String confirmationNumber,
			Map<String, Object> roles, final String ACTIVE_PROVIDER) {

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory,
				VOperation.getOrderByConfirmationNumber.name());

		if (agentId != null) {
			omrr.getRequest().setAgentId(agentId);
		}
		if (confirmationNumber != null) {
			omrr.getRequest().setConfirmationNumber(confirmationNumber);
		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		OrderType order = null;
		if ((omrrR != null) && (omrrR.getResponse() != null) && (omrrR.getResponse().getOrderInfo() != null)) {
			List<OrderType> orderTypeList = omrrR.getResponse().getOrderInfo();
			if (orderTypeList.size() > 0) {
				order = orderTypeList.get(0);
			}
		}

		if (order != null && order.getExternalId() != 0) {
			AuthorizationFilter.INSTANCE.removeDeleted(order);
			AuthorizationFilter.INSTANCE.filterShowAuthorizedView(order, roles, ACTIVE_PROVIDER);

			// Sorting Status History
			Collections.sort(order.getOrderStatusHistory().getPreviousStatus(),
					new Comparator<OrderStatusWithTypeType>() {
						public int compare(OrderStatusWithTypeType o1, OrderStatusWithTypeType o2) {
							return o2.getDateTimeStamp().compare(o1.getDateTimeStamp());
						}
					});
		}

		return CustomerFactory.INSTANCE.checkTitleCaseForCustomer(order);
	}

	/**
	 * @param id
	 * @param roles
	 * @param ACTIVE_PROVIDER
	 * @return
	 */
	public OrderVO getOrderVOByOrderNumber(final String id, Map<String, Object> roles,
			final String ACTIVE_PROVIDER)

	{
		logger.debug("getOrderVOByOrderNumber, id: " + id + ", Provider: " + ACTIVE_PROVIDER);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, VOperation.getOrder.name());

		if (id != null) {
			omrr.getRequest().setOrderId(id);
		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		boolean isAccordOrder = SessionCache.INSTANCE.isAccord();

		OrderType order = null;

		if ((omrrR != null) && (omrrR.getResponse() != null)) {

			List<OrderType> orderTypeList = omrrR.getResponse().getOrderInfo();

			if ((orderTypeList != null) && (orderTypeList.size() > 0)) {
				order = orderTypeList.get(0);
			}
		}

		if (order != null && order.getExternalId() != 0) {
			if (omrrR.getResponse().getSalesContext() != null) {
				OrderFactory.INSTANCE.cacheGUID(order.getExternalId(), omrrR.getResponse().getSalesContext());
			}

			AuthorizationFilter.INSTANCE.removeDeleted(order);
			AuthorizationFilter.INSTANCE.filterShowAuthorizedView(order, roles, ACTIVE_PROVIDER);

			// Sorting Status History
			Collections.sort(order.getOrderStatusHistory().getPreviousStatus(),
					new Comparator<OrderStatusWithTypeType>() {
						public int compare(OrderStatusWithTypeType o1, OrderStatusWithTypeType o2) {
							return o2.getDateTimeStamp().compare(o1.getDateTimeStamp());
						}
					});
			order = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(order);
		}

		OrderVO orderVO = new OrderVO(order, isAccordOrder);

		return orderVO;
	}

	/**
	 * @param orderType
	 * @param lineItemExtId
	 */
	public void addEventNotificationProcessByProvider(final OrderType orderType, Long lineItemExtId) {

		for (LineItemType lit : orderType.getLineItems().getLineItem()) {

			if (lit.getExternalId() == lineItemExtId) {
				EventService.INSTANCE.addProcessByProvider(lit);

			}
		}
	}

	/**
	 * @param agentId
	 * @param orderType
	 * @param lineItemExtIdList
	 * @param GUID
	 * @return OrderType
	 */
	public OrderType orderQualification(final String agentId, final OrderType orderType,
			final List<Long> lineItemExtIdList, String GUID) {

		return orderQualification(agentId, orderType, lineItemExtIdList, DEFAULT_ROLES,
				DEFAULT_ACTIVE_PROVIDER, GUID);

	}

	/**
	 * @param agentId
	 * @param orderType
	 * @param lineItemExtIdList
	 * @return OrderType
	 */
	public OrderType orderQualification(final String agentId, final OrderType orderType,
			final List<Long> lineItemExtIdList) {

		return orderQualification(agentId, orderType, lineItemExtIdList, DEFAULT_ROLES,
				DEFAULT_ACTIVE_PROVIDER, null);

	}

	/**
	 * @param agentId
	 * @param orderType
	 * @param lineItemExtIdList
	 * @param roles
	 * @param ACTIVE_PROVIDER
	 * @return OrderType
	 */
	public OrderType orderQualification(final String agentId, final OrderType orderType,
			final List<Long> lineItemExtIdList, Map<String, Object> roles, final String ACTIVE_PROVIDER) {
		return orderQualification(agentId, orderType, lineItemExtIdList, roles, ACTIVE_PROVIDER, null);
	}

	/**
	 * @param agentId
	 * @param operation
	 * @param orderType
	 * @param lineItemExtIdList
	 * @param roles
	 * @param ACTIVE_PROVIDER
	 * @param GUID
	 * @return OrderType
	 */
	public OrderType realTimeService(final String agentId, VOperation operation,
			final OrderType orderType, final List<Long> lineItemExtIdList, Map<String, Object> roles,
			final String ACTIVE_PROVIDER, String GUID) {
		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, operation.name());
		if (GUID != null) {
			omrr.setCorrelationId(GUID);
		}
		if (orderType != null) {
			omrr.getRequest().setOrderInfo(orderType);
			omrr.getRequest().setOrderId(String.valueOf(orderType.getExternalId()));
			omrr.getRequest().setAgentId(agentId);
		}

		for (Long lineItemExtId : lineItemExtIdList) {

			addEventNotificationProcessByProvider(orderType, lineItemExtId);

		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		OrderType order = null;

		if ((omrrR != null) && (omrrR.getResponse() != null)) {

			List<OrderType> orderTypeList = omrrR.getResponse().getOrderInfo();

			if ((orderTypeList != null) && (orderTypeList.size() > 0)) {
				order = orderTypeList.get(0);
			}
		}

		if (order != null && order.getExternalId() != 0) {
			AuthorizationFilter.INSTANCE.removeDeleted(order);
		}

		return CustomerFactory.INSTANCE.checkTitleCaseForCustomer(order);

	}

	/**
	 * @param agentId
	 * @param orderType
	 * @param lineItemExtIdList
	 * @param roles
	 * @param ACTIVE_PROVIDER
	 * @param GUID
	 * @return OrderType
	 */
	public OrderType orderQualification(final String agentId, final OrderType orderType,
			final List<Long> lineItemExtIdList, Map<String, Object> roles, final String ACTIVE_PROVIDER,
			String GUID) {

		return realTimeService(agentId, VOperation.orderQualification, orderType, lineItemExtIdList,
				roles, ACTIVE_PROVIDER, GUID);

	}

	// TODO: have to do the actual implementations
	/**
	 * @param id
	 * @param filtered
	 * @return
	 * 
	 *         Use OrderType getOrderByOrderNumber(final String id, final String
	 *         agentId, Map<String, Object> roles, final String ACTIVE_PROVIDER,
	 *         Boolean filtered, SalesContextType salesContext) instead of this
	 *         method
	 */
	@Deprecated
	public OrderType getOrderByOrderNumber(final String id, boolean filtered) {
		return getOrderByOrderNumber(id);
	}

	/**
	 * @param id
	 * @return
	 * 
	 *         Use OrderType getOrderByOrderNumber(final String id, final String
	 *         agentId, Map<String, Object> roles, final String ACTIVE_PROVIDER,
	 *         Boolean filtered, SalesContextType salesContext) instead of this
	 *         method
	 */
	@Deprecated
	public OrderType getOrderByOrderNumber(final String id) {

		return getOrderByOrderNumber(id, Boolean.TRUE);
	}

	/**
	 * @param id
	 * @return
	 * 
	 *         Use OrderType getOrderByOrderNumber(final String id, final String
	 *         agentId, Map<String, Object> roles, final String ACTIVE_PROVIDER,
	 *         Boolean filtered, SalesContextType salesContext) instead of this
	 *         method
	 */
	@Deprecated
	public OrderType getOrderByOrderNumberNoFilter(final String id) {

		return getOrderByOrderNumber(id, Boolean.FALSE);
	}

	/**
	 * @param agentId
	 * @param orderId
	 * @param salesContext
	 * @param guid
	 * @return
	 */
	public Response updateSalesContext(final String agentId, final String orderId,
			final SalesContextType salesContext, String guid) {

		OrderCacheService.INSTANCE.clear(orderId);

		ObjectFactory oFactory = new ObjectFactory();
		Long orderIdAsLong = Long.valueOf(orderId);

		OrderManagementRequestResponse.Request request = oFactory
				.createOrderManagementRequestResponseRequest();
		request.setSalesContext(salesContext);
		request.setOrderId(orderId);
		request.setUpdateOrderInfo(oFactory.createOrderType());
		request.getUpdateOrderInfo().setExternalId(orderIdAsLong);
		request.getUpdateOrderInfo().setAgentId(agentId);

		OrderManagementRequestResponse omrr = updateOMRR(request, oFactory, VOperation.updateOrder.name());
		omrr.setCorrelationId(guid);
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.getRequest().setAgentId(agentId);

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		return omrrR.getResponse();

	}

	/**
	 * @param id
	 * @return
	 */
	@Deprecated
	public SalesContextType getSalesContext(final String id) {
		return getSalesContext(id, "default");
	}

	/**
	 * @param id
	 * @param agentId
	 * @return
	 */
	public SalesContextType getSalesContext(final String id, final String agentId) {

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, VOperation.getOrder.name());

		if (id != null) {
			omrr.getRequest().setOrderId(id);
		}

		if (agentId != null) {
			omrr.getRequest().setAgentId(agentId);
		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		OrderType order = null;

		if ((omrrR != null) && (omrrR.getResponse() != null)) {

			List<OrderType> orderTypeList = omrrR.getResponse().getOrderInfo();

			if ((orderTypeList != null) && (orderTypeList.size() > 0)) {
				order = orderTypeList.get(0);
			}
		}

		if (order != null && order.getExternalId() != 0) {
			OrderCacheService.INSTANCE.clear(id);

			// Sorting Status History
			Collections.sort(order.getOrderStatusHistory().getPreviousStatus(),
					new Comparator<OrderStatusWithTypeType>() {
						public int compare(OrderStatusWithTypeType o1, OrderStatusWithTypeType o2) {
							return o2.getDateTimeStamp().compare(o1.getDateTimeStamp());
						}
					});
			order = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(order);
			OrderCacheService.INSTANCE.store(order, id);
		}

		return omrrR.getResponse().getSalesContext();

	}

	/**
	 * @param id
	 * @param filtered
	 * @return
	 * 
	 *         Use OrderType getOrderByOrderNumber(final String id, final String
	 *         agentId, Map<String, Object> roles, final String ACTIVE_PROVIDER,
	 *         Boolean filtered, SalesContextType salesContext) instead of this
	 *         method
	 */
	@Deprecated
	public OrderType getOrderByOrderNumber(final String id, final Boolean filtered) {
		return getOrderByOrderNumber(id, "default", filtered);
	}

	/**
	 * @param id
	 * @param agentId
	 * @param filtered
	 * @return
	 * 
	 *         Use OrderType getOrderByOrderNumber(final String id, final String
	 *         agentId, Map<String, Object> roles, final String ACTIVE_PROVIDER,
	 *         Boolean filtered, SalesContextType salesContext) instead of this
	 *         method
	 */
	@Deprecated
	public OrderType getOrderByOrderNumber(final String id, final String agentId, final Boolean filtered) {
		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, VOperation.getOrder.name());

		if (id != null) {
			omrr.getRequest().setOrderId(id);
		}

		if (agentId != null) {
			omrr.getRequest().setAgentId(agentId);
		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		OrderType order = null;

		if ((omrrR != null) && (omrrR.getResponse() != null)) {

			List<OrderType> orderTypeList = omrrR.getResponse().getOrderInfo();

			if ((orderTypeList != null) && (orderTypeList.size() > 0)) {
				order = orderTypeList.get(0);
			}
		}

		if (order != null && order.getExternalId() != 0) {

			if (filtered) {
				AuthorizationFilter.INSTANCE.removeDeleted(order);
			}
			// Sorting Status History
			Collections.sort(order.getOrderStatusHistory().getPreviousStatus(),
					new Comparator<OrderStatusWithTypeType>() {
						public int compare(OrderStatusWithTypeType o1, OrderStatusWithTypeType o2) {
							return o2.getDateTimeStamp().compare(o1.getDateTimeStamp());
						}
					});
		}

		return CustomerFactory.INSTANCE.checkTitleCaseForCustomer(order);
	}

	/**
	 * This method is used to return all account holders.
	 * @param id
	 * @param agentId
	 * @param filtered
	 * @param includeAccountHolders
	 * @return
	 */
	
	public OrderType getOrderWithAccountHoldersByOrderNumber(final String id, final String agentId, final Boolean filtered, final Boolean includeAccountHolders) {
		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, VOperation.getOrder.name());

		if (id != null) {
			omrr.getRequest().setOrderId(id);
			omrr.getRequest().setIncludeAccountHolders(includeAccountHolders);
		}

		if (agentId != null) {
			omrr.getRequest().setAgentId(agentId);
		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		OrderType order = null;

		if ((omrrR != null) && (omrrR.getResponse() != null)) {

			List<OrderType> orderTypeList = omrrR.getResponse().getOrderInfo();

			if ((orderTypeList != null) && (orderTypeList.size() > 0)) {
				order = orderTypeList.get(0);
			}
		}

		if (order != null && order.getExternalId() != 0) {

			if (filtered) {
				AuthorizationFilter.INSTANCE.removeDeleted(order);
			}
			// Sorting Status History
			Collections.sort(order.getOrderStatusHistory().getPreviousStatus(),
					new Comparator<OrderStatusWithTypeType>() {
						public int compare(OrderStatusWithTypeType o1, OrderStatusWithTypeType o2) {
							return o2.getDateTimeStamp().compare(o1.getDateTimeStamp());
						}
					});
		}

		return CustomerFactory.INSTANCE.checkTitleCaseForCustomer(order);
	}

	/**
	 * @param id
	 * @return
	 */
	public OrderManagementRequestResponse getOrderManagementRequestResponseByOrderNumber(final String id) {

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, VOperation.getOrder.name());

		if (id != null) {
			omrr.getRequest().setOrderId(id);
		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		// This is for camel case...
		// Start
		if (omrrR != null && omrrR.getResponse() != null && omrrR.getResponse().getOrderInfo() != null
				&& omrrR.getResponse().getOrderInfo().size() > 0) {
			if (omrrR.getResponse().getOrderInfo().size() > 0) {
				OrderType orderType = omrrR.getResponse().getOrderInfo().get(0);
				orderType = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(orderType);
				omrrR.getResponse().getOrderInfo().set(0, orderType);
			}
		}
		// end

		return omrrR;
	}
	
	/**
	 * This method is used to return the order object along with account holders if 'includeAccountHolders' flag is true.
	 * @param id
	 * @return
	 */
	public OrderManagementRequestResponse getOrderManagementRequestResponseByOrderNumber(final String id,boolean includeAccountHolders) {

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, VOperation.getOrder.name());

		if (id != null) {
			omrr.getRequest().setOrderId(id);
		}
		
		omrr.getRequest().setIncludeAccountHolders(includeAccountHolders);

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		// This is for camel case...
		// Start
		if (omrrR != null && omrrR.getResponse() != null && omrrR.getResponse().getOrderInfo() != null
				&& omrrR.getResponse().getOrderInfo().size() > 0) {
			if (omrrR.getResponse().getOrderInfo().size() > 0) {
				OrderType orderType = omrrR.getResponse().getOrderInfo().get(0);
				orderType = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(orderType);
				omrrR.getResponse().getOrderInfo().set(0, orderType);
			}
		}
		// end

		return omrrR;
	}	

	/**
	 * This method is used to return all account holders.
	 * @param id
	 * @return
	 */
	public OrderManagementRequestResponse getAllAccountHoldersByOrderNumber(final String id) {

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, VOperation.getAllAccountHolders.name());

		if (id != null) {
			omrr.getRequest().setOrderId(id);
		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		// This is for camel case...
		// Start
		if (omrrR != null && omrrR.getResponse() != null && omrrR.getResponse().getOrderInfo() != null
				&& omrrR.getResponse().getOrderInfo().size() > 0) {
			if (omrrR.getResponse().getOrderInfo().size() > 0) {
				OrderType orderType = omrrR.getResponse().getOrderInfo().get(0);
				orderType = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(orderType);
				omrrR.getResponse().getOrderInfo().set(0, orderType);
			}
		}
		// end

		return omrrR;
	}	

	
	
	/**
	 * @param id
	 * @param roles
	 * @param ACTIVE_PROVIDER
	 * @return
	 * 
	 *         Use OrderType getOrderByOrderNumber(final String id, final String
	 *         agentId, Map<String, Object> roles, final String ACTIVE_PROVIDER,
	 *         Boolean filtered, SalesContextType salesContext) instead of this
	 *         method
	 */
	@Deprecated
	public OrderType getOrderByOrderNumber(final String id, Map<String, Object> roles,
			final String ACTIVE_PROVIDER) {
		return getOrderByOrderNumber(id, "default", roles, ACTIVE_PROVIDER);
	}

	/**
	 * Get order with Order ExternalId
	 * 
	 * @param id
	 * @param agentId
	 * @param roles
	 * @param ACTIVE_PROVIDER
	 * @return OrderType
	 * 
	 *         Use OrderType getOrderByOrderNumber(final String id, final String
	 *         agentId, Map<String, Object> roles, final String ACTIVE_PROVIDER,
	 *         Boolean filtered, SalesContextType salesContext) instead of this
	 *         method
	 */
	@Deprecated
	public OrderType getOrderByOrderNumber(final String id, final String agentId, Map<String, Object> roles,
			final String ACTIVE_PROVIDER) {

		return getOrderByOrderNumber(id, agentId, roles, ACTIVE_PROVIDER, Boolean.TRUE, null);
	}

	/**
	 * Get order with Order ExternalId
	 * 
	 * @param Order
	 *            id
	 * @param agentId
	 * @param roles
	 *            , Use empty Map
	 * @param ACTIVE_PROVIDER
	 *            , Use "*" if you don't want to filter results using provider
	 * @param filtered
	 *            , Passing Boolean.TRUE this will filter deleted lineitems and
	 *            remove lineitems that doesn't belong to ACTIVE_PROVIDER
	 * @param salesContext
	 * @return OrderType
	 */
	public OrderType getOrderByOrderNumber(final String id, final String agentId, Map<String, Object> roles,
			final String ACTIVE_PROVIDER, Boolean filtered, SalesContextType salesContext) {
		logger.debug("getOrderByOrderNumber: Id: " + id + ", Provider: " + ACTIVE_PROVIDER + ", filtered: "
				+ filtered);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, VOperation.getOrder.name());

		if (id != null) {
			omrr.getRequest().setOrderId(id);
		}

		if (agentId != null) {
			omrr.getRequest().setAgentId(agentId);
		}

		if (salesContext != null) {
			omrr.getRequest().setSalesContext(salesContext);
		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		OrderType order = null;

		if ((omrrR != null) && (omrrR.getResponse() != null)) {

			List<OrderType> orderTypeList = omrrR.getResponse().getOrderInfo();

			if ((orderTypeList != null) && (orderTypeList.size() > 0)) {
				order = orderTypeList.get(0);
			}
		}

		if (order != null && order.getExternalId() != 0) {
			if (omrrR.getResponse().getSalesContext() != null) {
				OrderFactory.INSTANCE.cacheGUID(order.getExternalId(), omrrR.getResponse().getSalesContext());
			}
			OrderCacheService.INSTANCE.clear(id);
			if (filtered) {
				AuthorizationFilter.INSTANCE.removeDeleted(order);
				AuthorizationFilter.INSTANCE.filterShowAuthorizedView(order, roles, ACTIVE_PROVIDER);
			}

			// Sorting Status History
			Collections.sort(order.getOrderStatusHistory().getPreviousStatus(),
					new Comparator<OrderStatusWithTypeType>() {
						public int compare(OrderStatusWithTypeType o1, OrderStatusWithTypeType o2) {
							return o2.getDateTimeStamp().compare(o1.getDateTimeStamp());
						}
					});

			order = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(order);
			OrderCacheService.INSTANCE.store(order, id);
		}

		return order;

	}

	/**
	 * Getting OrderType based on LineItem External Id
	 * 
	 * @param lineItemId
	 * @param agentId
	 * @return OrderType
	 */
	@Deprecated
	public OrderType getOrderByLineItemNumber(final String lineItemId, final String agentId) {
		return getOrderByLineItemNumber(lineItemId, agentId, null, "*", Boolean.TRUE);
	}

	/**
	 * Getting OrderType based on LineItem External Id This method is also used
	 * to search Order based on it's Accord Id, Set Accord Order Id as
	 * lineItemId and set the below salesContext to search with Accord Order Id
	 * <v4:salesContext> <v4:entity name="source"> <v4:attribute name="searchBy"
	 * value="SRVC_SLCTN_ID"/> </v4:entity> </v4:salesContext>
	 * 
	 * @param lineItemId
	 * @param agentId
	 * @param salesContext
	 * @return
	 */
	@Deprecated
	public OrderType getOrderByLineItemNumber(final String lineItemId, final String agentId,
			SalesContextType salesContext, String providerId, Boolean filtered) {

		return getOrderByLineItemNumber(lineItemId, agentId, salesContext, providerId, filtered,
				new ErrorList());
	}

	/**
	 * Getting OrderType based on LineItem External Id This method is also used
	 * to search Order based on it's Accord Id, Set Accord Order Id as
	 * lineItemId and set the below salesContext to search with Accord Order Id
	 * <v4:salesContext> <v4:entity name="source"> <v4:attribute name="searchBy"
	 * value="SRVC_SLCTN_ID"/> </v4:entity> </v4:salesContext>
	 * 
	 * @param lineItemId
	 * @param agentId
	 * @param salesContext
	 * @return
	 */
	public OrderType getOrderByLineItemNumber(final String lineItemId, final String agentId,
			SalesContextType salesContext, String providerId, Boolean filtered, ErrorList errorList) {

		return (OrderType) getOrderByLineItemNumberForGIOU(lineItemId, agentId, salesContext, providerId,
				filtered, errorList).get(ORDER_TYPE);
	}

	/**
	 * Getting OrderType based on LineItem External Id This method is also used
	 * to search Order based on it's Accord Id, Set Accord Order Id as
	 * lineItemId and set the below salesContext to search with Accord Order Id
	 * <v4:salesContext> <v4:entity name="source"> <v4:attribute name="searchBy"
	 * value="SRVC_SLCTN_ID"/> </v4:entity> </v4:salesContext>
	 * 
	 * @param lineItemId
	 * @param agentId
	 * @param salesContext
	 * @return
	 */
	public Map<String, Object> getOrderByLineItemNumberForGIOU(final String lineItemId, final String agentId,
			SalesContextType salesContext, String providerId, Boolean filtered, ErrorList errorList) {

		return getOrderByLineItemNumber(lineItemId, agentId, salesContext, providerId, filtered, errorList,
				new HashMap<String, Object>(), Boolean.FALSE);
	}

	/**
	 * @param lineItemId
	 * @param agentId
	 * @param salesContext
	 * @param providerId
	 * @param filtered
	 * @param errorList
	 * @param inputMap
	 *            - Used to send any input params
	 * @param complete
	 *            - Boolean.TRUE will get all the lineitems
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getOrderByLineItemNumber(final String lineItemId, final String agentId,
			SalesContextType salesContext, String providerId, Boolean filtered, ErrorList errorList,
			Map<String, Object> inputMap, Boolean complete) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, VOperation.getOrderByLineItem.name());

		if (lineItemId != null) {
			omrr.getRequest().setLineItemId(lineItemId);
		}

		if (agentId != null) {
			omrr.getRequest().setAgentId(agentId);
		}

		if (salesContext != null) {
			omrr.getRequest().setSalesContext(salesContext);
		}

		if (complete) {
			omrr.getRequest().setCriteria(COMPLETE_ORDER);
		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		OrderFactory.INSTANCE.validateOrder(omrrR, errorList);

		OrderType order = null;

		if ((omrrR != null) && (omrrR.getResponse() != null)) {
			if (omrrR.getResponse().getSalesContext() != null) {
				resultMap.put(SALES_CONTEXT, omrrR.getResponse().getSalesContext());
			}

			List<OrderType> orderTypeList = omrrR.getResponse().getOrderInfo();
			if ((orderTypeList != null) && (orderTypeList.size() > 0)) {
				order = orderTypeList.get(0);
			}
		}

		if (order != null && order.getExternalId() != 0) {

			if (omrrR.getResponse().getSalesContext() != null) {
				OrderFactory.INSTANCE.cacheGUID(order.getExternalId(), omrrR.getResponse().getSalesContext());
			}

			OrderCacheService.INSTANCE.clear("" + order.getExternalId());

			if (filtered) {
				AuthorizationFilter.INSTANCE.removeDeleted(order);
				AuthorizationFilter.INSTANCE.filterShowAuthorizedView(order, null, providerId);
			}

			// Sorting Status History
			Collections.sort(order.getOrderStatusHistory().getPreviousStatus(),
					new Comparator<OrderStatusWithTypeType>() {
						public int compare(OrderStatusWithTypeType o1, OrderStatusWithTypeType o2) {
							return o2.getDateTimeStamp().compare(o1.getDateTimeStamp());
						}
					});

			OrderCacheService.INSTANCE.store(order, "" + order.getExternalId());
		}

		resultMap.put(ORDER_TYPE, CustomerFactory.INSTANCE.checkTitleCaseForCustomer(order));

		return resultMap;
	}

	/**
	 * Search Order with OrderId and ProviderId, this will give the results by
	 * filtering the lineitems based on ProviderId
	 * 
	 * @param agentId
	 * @param orderId
	 * @param providerId
	 * @param salesContext
	 * @return OrderType
	 */
	public OrderType getOrderByProvider(final String agentId, final String orderId, final String providerId,
			SalesContextType salesContext, Boolean filtered) {
		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, VOperation.getOrderByProvider.name());

		if (orderId != null) {
			omrr.getRequest().setOrderId(orderId);
			omrr.getRequest().setProviderId(providerId);
		}

		if (agentId != null) {
			omrr.getRequest().setAgentId(agentId);
		}

		if (salesContext != null) {
			omrr.getRequest().setSalesContext(salesContext);
		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		OrderType order = null;

		if ((omrrR != null) && (omrrR.getResponse() != null)) {
			List<OrderType> orderTypeList = omrrR.getResponse().getOrderInfo();
			if ((orderTypeList != null) && (orderTypeList.size() > 0)) {
				order = orderTypeList.get(0);
				if (filtered) {
					AuthorizationFilter.INSTANCE.removeDeleted(order);
				}
			}
		}

		if (order != null && order.getExternalId() != 0) {
			// Sorting Status History
			Collections.sort(order.getOrderStatusHistory().getPreviousStatus(),
					new Comparator<OrderStatusWithTypeType>() {
						public int compare(OrderStatusWithTypeType o1, OrderStatusWithTypeType o2) {
							return o2.getDateTimeStamp().compare(o1.getDateTimeStamp());
						}
					});
		}

		return CustomerFactory.INSTANCE.checkTitleCaseForCustomer(order);
	}

	/**
	 * @param oFactory
	 * @param transactionType
	 * @return
	 */
	public OrderManagementRequestResponse createOMRR(final ObjectFactory oFactory,
			final String transactionType) {

		OrderManagementRequestResponse omrr = oFactory.createOrderManagementRequestResponse();
		omrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		omrr.setCorrelationId(UUID.randomUUID().toString());
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.setTransactionType(transactionType);

		return omrr;

	}

	/**
	 * @param request
	 * @param oFactory
	 * @param transactionType
	 * @return
	 */
	public OrderManagementRequestResponse updateOMRR(OrderManagementRequestResponse.Request request,
			final ObjectFactory oFactory, final String transactionType) {

		OrderManagementRequestResponse omrr = oFactory.createOrderManagementRequestResponse();
		omrr.setRequest(request);
		omrr.setCorrelationId(UUID.randomUUID().toString());
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.setTransactionType(transactionType);

		return omrr;

	}

	/**
	 * @param statusList
	 * @param reasonList
	 * @param offset
	 * @param totalRows
	 * @param roles
	 * @param ACTIVE_PROVIDER
	 * @return
	 */
	public List<OrderType> getOrderByStatusReason(final List<String> statusList,
			final List<String> reasonList, int offset, int totalRows, Map<String, Object> roles,
			final String ACTIVE_PROVIDER) {

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, VOperation.getOrderByStatus.name());

		if ((omrr != null) && (omrr.getPagingDetail() == null)) {

			PagingDetail pagingDetail = oFactory.createOrderManagementRequestResponsePagingDetail();
			omrr.setPagingDetail(pagingDetail);
		}
		omrr.getPagingDetail().setOffSet(offset);
		omrr.getPagingDetail().setTotalRows(totalRows);

		if (reasonList != null) {
			omrr.getRequest().getReason().addAll(reasonList);
		}

		if (statusList != null) {
			omrr.getRequest().getStatus().addAll(statusList);
		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		List<OrderType> orderTypeList = omrrR.getResponse().getOrderInfo();

		if (orderTypeList != null) {
			AuthorizationFilter.INSTANCE.removeDeleted(orderTypeList);
			AuthorizationFilter.INSTANCE.filterShowAuthorizedView(orderTypeList, roles, ACTIVE_PROVIDER);

			// Sorting Date
			Collections.sort(orderTypeList, new Comparator<OrderType>() {
				public int compare(OrderType o1, OrderType o2) {

					long d1 = 0;
					long d2 = 0;

					if ((o1 != null) && (o1.getOrderDate() != null)) {
						d1 = DateUtil.toDate(o1.getOrderDate()).getTime();
					}

					if ((o2 != null) && (o2.getOrderDate() != null)) {
						d2 = DateUtil.toDate(o2.getOrderDate()).getTime();
					}

					if (d1 < d2)
						return -1;
					else if (d1 > d2)
						return 1;
					else
						return 0;
				}
			});
		}

		return orderTypeList;
	}

	/**
	 * @param id
	 * @param roles
	 * @param ACTIVE_PROVIDER
	 * @return OrderType
	 * 
	 *         Use OrderType getOrderByOrderNumber(final String id, final String
	 *         agentId, Map<String, Object> roles, final String ACTIVE_PROVIDER,
	 *         Boolean filtered, SalesContextType salesContext) instead of this
	 *         method
	 */
	public OrderType getOrderType(final String id, Map<String, Object> roles, final String ACTIVE_PROVIDER) {
		return getOrderByOrderNumber(id, "default", roles, ACTIVE_PROVIDER);
	}

	/**
	 * @param map
	 * @return
	 */
	private boolean isOnHoldStatusReasonLookup(Map<String, String> map) {

		return (map.get("onhold") != null);
	}

	@Deprecated
	public List<OrderType> getByScheduleDate(final String orderDate, Map<String, Object> roles,
			final String ACTIVE_PROVIDER) {
		return getByScheduleDate(orderDate, roles, ACTIVE_PROVIDER, Boolean.TRUE);
	}

	/**
	 * @param orderDate
	 * @param roles
	 * @param ACTIVE_PROVIDER
	 * @return
	 */
	public List<OrderType> getByScheduleDate(final String orderDate, Map<String, Object> roles,
			final String ACTIVE_PROVIDER, Boolean filtered) {

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory,
				VOperation.getOrderByScheduleDate.name());

		DateTimeOrTimeRangeType dt = oFactory.createDateTimeOrTimeRangeType();

		if (dt != null) {
			omrr.getRequest().setDateFilter(dt);
		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		List<OrderType> orderTypeList = omrrR.getResponse().getOrderInfo();

		if (orderTypeList != null) {
			if (filtered) {
				AuthorizationFilter.INSTANCE.removeDeleted(orderTypeList);
			}
			AuthorizationFilter.INSTANCE.buildContextView(orderTypeList, roles, ACTIVE_PROVIDER);
		}

		return orderTypeList;

	}

	@Deprecated
	public List<OrderType> getByOrderDate(final String orderDate, Map<String, Object> roles,
			final String ACTIVE_PROVIDER) {

		return getByOrderDate(orderDate, roles, ACTIVE_PROVIDER, Boolean.TRUE);

	}

	/**
	 * @param orderDate
	 * @param roles
	 * @param ACTIVE_PROVIDER
	 * @return
	 */
	public List<OrderType> getByOrderDate(final String orderDate, Map<String, Object> roles,
			final String ACTIVE_PROVIDER, boolean filterDeleted) {

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, VOperation.getOrderByDate.name());

		DateTimeOrTimeRangeType dt = oFactory.createDateTimeOrTimeRangeType();
		if (orderDate != null && !orderDate.trim().equals("")) {
			XMLGregorianCalendar value = DateUtil.fromStringToXMLDate(orderDate);
			dt.setDate(value);
			dt.setTime(value);
		} else {
			// If order date is empty, using today's date
			XMLGregorianCalendar value = DateUtil.fromDateToXML(new Date());
			dt.setDate(value);
			dt.setTime(value);
		}

		if (dt != null) {
			omrr.getRequest().setDateFilter(dt);
		}

		logger.debug("OMRR --> " + omrr.toString());
		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		List<OrderType> orderTypeList = null;

		if ((omrrR != null) && (omrrR.getResponse() != null)) {
			orderTypeList = omrrR.getResponse().getOrderInfo();
		}

		if (orderTypeList != null) {

			if (filterDeleted) {

				AuthorizationFilter.INSTANCE.removeDeleted(orderTypeList);
			}
			AuthorizationFilter.INSTANCE.buildContextView(orderTypeList, roles, ACTIVE_PROVIDER);

		}

		return orderTypeList;

	}

	/**
	 * @param map
	 * @param roles
	 * @param ACTIVE_PROVIDER
	 * @return
	 */
	public List<OrderType> addOnHoldOrderByStatusReason(Map<String, String> map, Map<String, Object> roles,
			final String ACTIVE_PROVIDER) {

		return addStatusReason(HOLD_ORDER_PARAM, map, roles, ACTIVE_PROVIDER);

	}

	/**
	 * @param map
	 * @param roles
	 * @param ACTIVE_PROVIDER
	 * @return
	 */
	public List<OrderType> addSchedulePendingByStatusReason(Map<String, String> map,
			Map<String, Object> roles, final String ACTIVE_PROVIDER) {

		return addStatusReason(PROCESSING_SCHEDULE_PENDING, map, roles, ACTIVE_PROVIDER);

	}

	/**
	 * @param status
	 * @param map
	 * @param roles
	 * @param ACTIVE_PROVIDER
	 * @return
	 */
	public List<OrderType> addStatusReason(String status, Map<String, String> map, Map<String, Object> roles,
			final String ACTIVE_PROVIDER) {

		List<OrderType> orderTypeList = null;

		if (isOnHoldStatusReasonLookup(map)) {
			orderTypeList = OrderService.INSTANCE.addOnHoldOrderByStatusReason(map, roles, ACTIVE_PROVIDER);
		}

		if (orderTypeList != null) {

			OrderManagementRequestResponse omRR = new OrderManagementRequestResponse();
			OrderManagementRequestResponse.Request r = new OrderManagementRequestResponse.Request();

			r.getStatus().add(status);
			r.getReason().add(map.get(REASON_PARAM));

			omRR.setRequest(r);

			omRR.setCorrelationId(UUID.randomUUID().toString());
			omRR.setTransactionType(TRANSACTION_TYPE_GET_ORDER_BY_STATUS);

			OrderManagementRequestResponse orderRR = TransportConfig.INSTANCE.getOrderClient().send(omRR);

			if ((orderRR != null) && (orderRR.getResponse() != null)
					&& (orderRR.getResponse().getOrderInfo() != null)) {
				orderTypeList = orderRR.getResponse().getOrderInfo();
			}

			if (orderTypeList != null) {
				AuthorizationFilter.INSTANCE.removeDeleted(orderTypeList);
				AuthorizationFilter.INSTANCE.buildContextView(orderTypeList, roles, ACTIVE_PROVIDER);
			}

		}

		return orderTypeList;

	}

	/**
	 * @param agentId
	 * @param customerExtId
	 * @return
	 */
	public List<OrderType> getOrderByCustomer(String agentId, String customerExtId) {

		ObjectFactory oFactory = new ObjectFactory();

		OrderManagementRequestResponse omrr = createOMRR(oFactory, "getOrderByCustomer");

		omrr.getRequest().setAgentId(agentId);
		omrr.getRequest().setCustomerId(customerExtId);

		OrderManagementRequestResponse omRR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		if (omRR != null && omRR.getResponse() != null) {
			List<OrderType> orderTypeList = omRR.getResponse().getOrderInfo();
			if (orderTypeList != null && orderTypeList.size() > 0) {
				if (orderTypeList.size() > 0) {
					return orderTypeList;
				}
			}
		}

		return null;
	}

	/**
	 * To search orders using status, provider, LI create/Sch Start/order dates
	 * 
	 * @param Agent
	 *            Login Id - agentId
	 * @param LineItem
	 *            current status - status
	 * @param providerId
	 * @param Scheduled
	 *            Start Date - schdStDt
	 * @param Order
	 *            Date - orderDate
	 * @param LineItem
	 *            create date - liCreateDt
	 * @param salesContext
	 * @param channel_type 
	 * @return OrderSearch
	 */
	public OrderSearch searchOrders(final String agentId, final String status, final String providerId,
			final XMLGregorianCalendar schdStDt, final XMLGregorianCalendar orderDate,
			final XMLGregorianCalendar liCreateDt, final SalesContextType salesContext, Integer channel_type) {

		logger.info("searchOrders: providerId: " + providerId + ", schdStartDate : " + schdStDt);

		ObjectFactory oFactory = new ObjectFactory();

		String correlationId = SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID");
		if (StringUtils.isBlank(correlationId)) {
			correlationId = UUID.randomUUID().toString();
		}

		OrderManagementRequestResponse omrr = oFactory.createOrderManagementRequestResponse();
		omrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		omrr.setCorrelationId(correlationId);
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.setTransactionType(VOperation.orderSearch.name());

		omrr.getRequest().setAgentId(agentId);
		if (schdStDt != null) {
			omrr.getRequest().setScheduledStartDate(schdStDt);
		}
		if (orderDate != null) {
			omrr.getRequest().setOrderDate(orderDate);
		}
		if (liCreateDt != null) {
			omrr.getRequest().setLineItemCreateDate(liCreateDt);
		}
		omrr.getRequest().setProviderId(providerId);
		omrr.getRequest().getStatus().add(status);
		omrr.getRequest().setSalesContext(salesContext);
		omrr.getRequest().setChannelType(channel_type);

		OrderManagementRequestResponse omrr_response = TransportConfig.INSTANCE.getOrderClient().send(omrr);
		if (omrr_response != null && omrr_response.getResponse() != null) {
			return omrr_response.getResponse().getOrderSearchResult();
		}

		return null;
	}
	
	public OrderManagementRequestResponse getOrderManagementRequestResponseByLineItemNumber(final String lineItemId,final String agentId,  String orderId, SalesContextType salesContext, boolean includeAccountHolders) {

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, VOperation.getOrderByLineItem.name());
		
		omrr.getRequest().setIncludeAccountHolders(includeAccountHolders);

		if (lineItemId != null) {
			omrr.getRequest().setLineItemId(lineItemId);
		}
		
		if (agentId != null) {
			omrr.getRequest().setAgentId(agentId);
		}
		
		if (orderId != null) {
			omrr.getRequest().setOrderId(orderId);
		}

		if (salesContext != null) {
			omrr.getRequest().setSalesContext(salesContext);
		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);
		return omrrR;
	}
	
	public OrderManagementRequestResponse getOrderManagementRequestResponseByLineItemNumber(final String lineItemId,final String agentId, SalesContextType salesContext) {

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, VOperation.getOrderByLineItem.name());

		if (lineItemId != null) {
			omrr.getRequest().setLineItemId(lineItemId);
		}

		if (agentId != null) {
			omrr.getRequest().setAgentId(agentId);
		}

		if (salesContext != null) {
			omrr.getRequest().setSalesContext(salesContext);
		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);
		return omrrR;
	}
	
	public void removeOtherLineItems(final OrderType orderType, long... includeLineItemExternalIds) {
		if ((orderType != null) && (orderType.getExternalId() != 0) && (includeLineItemExternalIds != null)) {
			if (orderType.getLineItems() != null) {
				List<LineItemType> listLineItemList = orderType.getLineItems()
						.getLineItem();
				if (listLineItemList != null) {
					Iterator<LineItemType> it = listLineItemList.iterator();
					while (it.hasNext()) {
						LineItemType lineItem = (LineItemType) it.next();
						boolean isFound = false;
						for(long id : includeLineItemExternalIds) {
							if(id == lineItem.getExternalId()) {
								isFound = true;
								break;
							}
						}
						if(!isFound) {
							it.remove();
						}
					}
				}
			}
		}
	}

	public OrderType getOrderByOrderNumber(final String id,final String agentId, Map<String, Object> roles,
					final String ACTIVE_PROVIDER, Boolean filtered,
					SalesContextType salesContext, Boolean isAccntHolder) {
		logger.debug("getOrderByOrderNumber: Id: " + id + ", Provider: " + ACTIVE_PROVIDER + ", filtered: " + filtered);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory,
				VOperation.getOrder.name());

		if (id != null) {
			omrr.getRequest().setOrderId(id);
		}

		if (agentId != null) {
			omrr.getRequest().setAgentId(agentId);
		}

		if (salesContext != null) {
			omrr.getRequest().setSalesContext(salesContext);
		}

		if (isAccntHolder != null) {
			omrr.getRequest().setIncludeAccountHolders(isAccntHolder);
		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE
				.getOrderClient().send(omrr);

		OrderType order = null;

		if ((omrrR != null) && (omrrR.getResponse() != null)) {

			List<OrderType> orderTypeList = omrrR.getResponse().getOrderInfo();

			if ((orderTypeList != null) && (orderTypeList.size() > 0)) {
				order = orderTypeList.get(0);
			}
		}

		if (order != null && order.getExternalId() != 0) {
			if (omrrR.getResponse().getSalesContext() != null) {
				OrderFactory.INSTANCE.cacheGUID(order.getExternalId(), omrrR
						.getResponse().getSalesContext());
			}
			OrderCacheService.INSTANCE.clear(id);
			if (filtered) {
				AuthorizationFilter.INSTANCE.removeDeleted(order);
				AuthorizationFilter.INSTANCE.filterShowAuthorizedView(order,
						roles, ACTIVE_PROVIDER);
			}

			// Sorting Status History
			Collections.sort(order.getOrderStatusHistory().getPreviousStatus(),
					new Comparator<OrderStatusWithTypeType>() {
						public int compare(OrderStatusWithTypeType o1,
								OrderStatusWithTypeType o2) {
							return o2.getDateTimeStamp().compare(
									o1.getDateTimeStamp());
						}
					});

			order = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(order);
			OrderCacheService.INSTANCE.store(order, id);
		}

		return order;

	}


	/**
	 * @param lineItemId
	 * @param agentId
	 * @param salesContext
	 * @param providerId
	 * @param filtered
	 * @param errorList
	 * @param inputMap
	 *            - Used to send any input params
	 * @param complete
	 *            - Boolean.TRUE will get all the lineitems
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getOrderByLineItemNumber(final String lineItemId, final String agentId,
			SalesContextType salesContext, String providerId, Boolean filtered, ErrorList errorList,
			Map<String, Object> inputMap, Boolean complete, Boolean isAccntHolder) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, VOperation.getOrderByLineItem.name());

		if (lineItemId != null) {
			omrr.getRequest().setLineItemId(lineItemId);
		}

		if (agentId != null) {
			omrr.getRequest().setAgentId(agentId);
		}

		if (salesContext != null) {
			omrr.getRequest().setSalesContext(salesContext);
		}

		if (isAccntHolder != null) {
			omrr.getRequest().setIncludeAccountHolders(isAccntHolder);
		}

		if (complete) {
			omrr.getRequest().setCriteria(COMPLETE_ORDER);
		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		OrderFactory.INSTANCE.validateOrder(omrrR, errorList);

		OrderType order = null;

		if ((omrrR != null) && (omrrR.getResponse() != null)) {
			if (omrrR.getResponse().getSalesContext() != null) {
				resultMap.put(SALES_CONTEXT, omrrR.getResponse().getSalesContext());
			}

			List<OrderType> orderTypeList = omrrR.getResponse().getOrderInfo();
			if ((orderTypeList != null) && (orderTypeList.size() > 0)) {
				order = orderTypeList.get(0);
			}
		}

		if (order != null && order.getExternalId() != 0) {

			if (omrrR.getResponse().getSalesContext() != null) {
				OrderFactory.INSTANCE.cacheGUID(order.getExternalId(), omrrR.getResponse().getSalesContext());
			}

			OrderCacheService.INSTANCE.clear("" + order.getExternalId());

			if (filtered) {
				AuthorizationFilter.INSTANCE.removeDeleted(order);
				AuthorizationFilter.INSTANCE.filterShowAuthorizedView(order, null, providerId);
			}

			// Sorting Status History
			Collections.sort(order.getOrderStatusHistory().getPreviousStatus(),
					new Comparator<OrderStatusWithTypeType>() {
						public int compare(OrderStatusWithTypeType o1, OrderStatusWithTypeType o2) {
							return o2.getDateTimeStamp().compare(o1.getDateTimeStamp());
						}
					});

			OrderCacheService.INSTANCE.store(order, "" + order.getExternalId());
		}

		resultMap.put(ORDER_TYPE, CustomerFactory.INSTANCE.checkTitleCaseForCustomer(order));

		return resultMap;
	}


	/**
	 * @param id
	 * @param roles
	 * @param ACTIVE_PROVIDER
	 * @return
	 */
	public OrderVO getOrderVOByOrderNumberWithoutFilter(final String id, Map<String, Object> roles,
			final String ACTIVE_PROVIDER)

	{
		logger.debug("getOrderVOByOrderNumber, id: " + id + ", Provider: " + ACTIVE_PROVIDER);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, VOperation.getOrder.name());

		if (id != null) {
			omrr.getRequest().setOrderId(id);
		}

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		boolean isAccordOrder = SessionCache.INSTANCE.isAccord();

		OrderType order = null;

		if ((omrrR != null) && (omrrR.getResponse() != null)) {

			List<OrderType> orderTypeList = omrrR.getResponse().getOrderInfo();

			if ((orderTypeList != null) && (orderTypeList.size() > 0)) {
				order = orderTypeList.get(0);
			}
		}

		if (order != null && order.getExternalId() != 0) {
			if (omrrR.getResponse().getSalesContext() != null) {
				OrderFactory.INSTANCE.cacheGUID(order.getExternalId(), omrrR.getResponse().getSalesContext());
			}

			//AuthorizationFilter.INSTANCE.removeDeleted(order);
			AuthorizationFilter.INSTANCE.filterShowAuthorizedView(order, roles, ACTIVE_PROVIDER);

			// Sorting Status History
			Collections.sort(order.getOrderStatusHistory().getPreviousStatus(),
					new Comparator<OrderStatusWithTypeType>() {
						public int compare(OrderStatusWithTypeType o1, OrderStatusWithTypeType o2) {
							return o2.getDateTimeStamp().compare(o1.getDateTimeStamp());
						}
					});
			order = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(order);
		}

		OrderVO orderVO = new OrderVO(order, isAccordOrder);

		return orderVO;
	}

	public OrderSearch searchOrders(final String agentId, final String status, final String providerId,
			final XMLGregorianCalendar schdStDt, final XMLGregorianCalendar orderDate,
			final XMLGregorianCalendar liCreateDt, final SalesContextType salesContext, String channel_type) {

		logger.info("searchOrders: providerId: " + providerId + ", schdStartDate : " + schdStDt);

		ObjectFactory oFactory = new ObjectFactory();

		String correlationId = SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID");
		if (StringUtils.isBlank(correlationId)) {
			correlationId = UUID.randomUUID().toString();
		}

		OrderManagementRequestResponse omrr = oFactory.createOrderManagementRequestResponse();
		omrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		omrr.setCorrelationId(correlationId);
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.setTransactionType(VOperation.orderSearch.name());

		omrr.getRequest().setAgentId(agentId);
		if (schdStDt != null) {
			omrr.getRequest().setScheduledStartDate(schdStDt);
		}
		if (orderDate != null) {
			omrr.getRequest().setOrderDate(orderDate);
		}
		if (liCreateDt != null) {
			omrr.getRequest().setLineItemCreateDate(liCreateDt);
		}
		omrr.getRequest().setProviderId(providerId);
		omrr.getRequest().getStatus().add(status);
		omrr.getRequest().setSalesContext(salesContext);

		OrderManagementRequestResponse omrr_response = TransportConfig.INSTANCE.getOrderClient().send(omrr);
		if (omrr_response != null && omrr_response.getResponse() != null) {
			return omrr_response.getResponse().getOrderSearchResult();
		}

		return null;
	}
	
	/**
	 * @param agentId
	 * @param orderId
	 * @param salesContext
	 * @param guid
	 * @param zipOnlySearch
	 * @return
	 */
	public Response updateSalesContext(final String agentId, final String orderId,
			final SalesContextType salesContext, String guid, int zipOnlySearch) {

		OrderCacheService.INSTANCE.clear(orderId);

		ObjectFactory oFactory = new ObjectFactory();
		Long orderIdAsLong = Long.valueOf(orderId);

		OrderManagementRequestResponse.Request request = oFactory
				.createOrderManagementRequestResponseRequest();
		request.setSalesContext(salesContext);
		request.setOrderId(orderId);
		request.setUpdateOrderInfo(oFactory.createOrderType());
		request.getUpdateOrderInfo().setExternalId(orderIdAsLong);
		request.getUpdateOrderInfo().setAgentId(agentId);
		if(zipOnlySearch == 1){
		    request.getUpdateOrderInfo().setIsZipOnlySearch(zipOnlySearch);
		}

		OrderManagementRequestResponse omrr = updateOMRR(request, oFactory, VOperation.updateOrder.name());
		omrr.setCorrelationId(guid);
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.getRequest().setAgentId(agentId);

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		return omrrR.getResponse();

	}
	
	/**
	 * @param agentId
	 * @param orderId
	 * @param salesContext
	 * @param guid
	 * @param zipOnlySearch
	 * @return
	 */
	public Response updateSalesContext(final String agentId, final String orderId,
			final SalesContextType salesContext, String guid, int zipOnlySearch, String orderSourceExternalId) {

		OrderCacheService.INSTANCE.clear(orderId);

		ObjectFactory oFactory = new ObjectFactory();
		Long orderIdAsLong = Long.valueOf(orderId);

		OrderManagementRequestResponse.Request request = oFactory
				.createOrderManagementRequestResponseRequest();
		request.setSalesContext(salesContext);
		request.setOrderId(orderId);
		request.setUpdateOrderInfo(oFactory.createOrderType());
		request.getUpdateOrderInfo().setExternalId(orderIdAsLong);
		request.getUpdateOrderInfo().setAgentId(agentId);
		if(zipOnlySearch == 1){
		    request.getUpdateOrderInfo().setIsZipOnlySearch(zipOnlySearch);
		}
		request.getUpdateOrderInfo().setOrdersourceExternalId(orderSourceExternalId);
		OrderManagementRequestResponse omrr = updateOMRR(request, oFactory, VOperation.updateOrder.name());
		omrr.setCorrelationId(guid);
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.getRequest().setAgentId(agentId);

		OrderManagementRequestResponse omrrR = TransportConfig.INSTANCE.getOrderClient().send(omrr);

		return omrrR.getResponse();

	}
}
