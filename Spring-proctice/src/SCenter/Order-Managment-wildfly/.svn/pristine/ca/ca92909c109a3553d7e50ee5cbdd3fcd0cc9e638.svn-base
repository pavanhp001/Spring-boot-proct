package com.AL.task.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.AL.rules.core.RuleService;
import com.AL.task.LocalTaskRules;
import com.AL.task.TaskBase;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.ResponseItemOme;
import com.AL.task.response.ResponseBuilder;
import com.AL.util.messaging.Broadcastable;
import com.AL.util.messaging.impl.HttpBroadcastable;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.Vdao.dao.CustomerDao;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.vm.service.OrderManagementService;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.vm.util.converter.unmarshall.UnmarshallOrder;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
 * @author ebthomas
 *
 */

@Component("taskRules")
public class TaskRules extends TaskBase<OrderManagementRequestResponseDocument>
		implements LocalTaskRules<OrderManagementRequestResponseDocument>,
		Broadcastable {

	private Logger logger = Logger.getLogger(TaskRules.class);

	@Autowired
	private OrderManagementService omeService;

	@Autowired
	private UnmarshallOrder unmarshallOrder;

	@Autowired
	private MarshallOrder<SalesOrder> marshallOrder;

	@Autowired
	private OrderManagementDao orderManagementDao;

	@Autowired
	private ResponseBuilder responseBuilder;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	public RuleService ruleService;

	private static final Long DEFAULT_ORDER_EXTERNAL_ID = -1L;

	/**
	 * Constructor.
	 */
	public TaskRules() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	public OrchestrationContext processRequest(
			final OrderManagementRequestResponseDocument orderDocument) {

		Request request = orderDocument.getOrderManagementRequestResponse()
				.getRequest();

		orderDocument.getOrderManagementRequestResponse().getRequest();

		OrchestrationContext params = createLoadContext(orderDocument);
		Long orderExtId = getOrderExternalId(request);

		try {
			params.add(TaskContextParamEnum.salesOrderId.name(), orderExtId);
			params.add(TaskContextParamEnum.request.name(), request);
		} catch (Exception e) {
			return OrchestrationContext.Factory.createOme(e);
		}
		return params;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrchestrationContext processAgenda(final OrchestrationContext params) {


		Request request = (Request) params.get(TaskContextParamEnum.request
				.name());



		//System.out.println(request.getRulesAction());


		if ((request != null) && (request.getRulesAction() != null)) {

			logger.debug("rules action:"+request.getRulesAction().toString());
			logger.debug(Request.RulesAction.VALIDATE.toString());
			logger.debug(Request.RulesAction.CLEAR_MEMORY_CACHE.toString());
			logger.debug(Request.RulesAction.CLEAR_STORAGE_CACHE.toString());

			//rulesAction


			if ((Request.RulesAction.VALIDATE.toString().equalsIgnoreCase(
					request.getRulesAction().toString())) || ("validate".equals(request.getRulesAction().toString()))) {
				validate(params);

			}

			else if ((Request.RulesAction.CLEAR_MEMORY_CACHE.toString()
					.equalsIgnoreCase(request.getRulesAction().toString()))  || ("clearMemoryCache".equals(request.getRulesAction().toString()))) {

				clearMemoryCache(params);

			}

			else if ((Request.RulesAction.CLEAR_STORAGE_CACHE.toString()
					.equalsIgnoreCase(request.getRulesAction().toString())) || ("clearStorageCache".equals(request.getRulesAction().toString()))) {

				clearStorageCache(params);

			}
		} else {
			validate(params);
		}

		return params;
	}

	public void clearMemoryCache(final OrchestrationContext params) {
		ruleService.removeMemoryCache();
	}

	public void clearStorageCache(final OrchestrationContext params) {

		ruleService.removeStorageCache();
		clearMemoryCache(params);
	}

	public void validate(final OrchestrationContext params) {

		Set<Object> facts = new HashSet<Object>();

		Long salesOrderId = (Long) params.get(TaskContextParamEnum.salesOrderId
				.name());

		SalesOrder salesOrderBean = orderManagementDao.findById(salesOrderId);

		if (salesOrderBean != null) {

			Consumer consumer = customerDao
					.findCustomerByExternalId(salesOrderBean
							.getConsumerExternalId());
			if (consumer != null) {
				params.add(TaskContextParamEnum.customer.name(), consumer);
				facts.add(consumer);
			}

			params.addSuccessful(salesOrderBean);

		} else {
			((ResponseItemOme) params).addSalesOrderNotFound(String
					.valueOf(salesOrderId));
		}

		Map<String, String> rules = new HashMap<String, String>();
		params.add("rulesSelectionProperties", rules);

		StringBuilder sbProvider = new StringBuilder();
		StringBuilder sbDetailExId = new StringBuilder();

		// Add Provider and Product Information
		for (LineItem li : salesOrderBean.getLineItems()) {
			if (li.getProviderExternalId() != null) {

				if (sbProvider.length() > 0) {
					sbProvider.append(",");
				}
				sbProvider.append(li.getProviderExternalId());
			}

			if ((li.getLineItemDetailBean() != null)
					&& (li.getLineItemDetailBean()
							.getLineItemDetailExternalId() != null)) {

				if (sbDetailExId.length() > 0) {
					sbDetailExId.append(",");
				}

				sbDetailExId.append(li.getLineItemDetailBean()
						.getLineItemDetailExternalId());
			}

		}

		String PROVIDER = "provider";
		String LINE_ITEM_DETAIL_EX_ID = "lineItemDetailExternalId";

		rules.put(PROVIDER, sbProvider.toString());
		rules.put(LINE_ITEM_DETAIL_EX_ID, sbDetailExId.toString());

		facts.add(salesOrderBean);

		params.add("facts", facts);
		ruleService.execute(params);

	}

	private Long getOrderExternalId(Request request) {
		String orderId = unmarshallOrder.buildOrderId(request);
		Long orderExtId = DEFAULT_ORDER_EXTERNAL_ID;
		if (orderId != null && orderId.trim().length() > 0) {
			orderExtId = Long.valueOf(orderId);
		}

		return orderExtId;
	}

	/**
	 * {@inheritDoc}
	 */
	public OrderManagementRequestResponseDocument processResponse(
			final OrchestrationContext params) {
		return responseBuilder.buildResponse(params);
	}

	public OrderManagementDao getOrderManagementDao() {
		return orderManagementDao;
	}

	public void setOrderManagementDao(OrderManagementDao orderManagementDao) {
		this.orderManagementDao = orderManagementDao;
	}

	/**
	 * {@inheritDoc}
	 */
	public OrderManagementRequestResponseDocument handleFault(
			final Exception e, final OrchestrationContext params,
			final OrderManagementRequestResponseDocument orderDocument) {

		logger.debug(e);
		return orderDocument;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void broadcast(String strToBroadcastOriginal, Map<String, String> map) {

//		Broadcastable broadcast = HttpBroadcastable.createDefault();
//		broadcast.broadcast(strToBroadcastOriginal, map);
	}

	public OrderManagementService getOmeService() {
		return omeService;
	}

	public void setOmeService(OrderManagementService omeService) {
		this.omeService = omeService;
	}

	public UnmarshallOrder getUnmarshallOrder() {
		return unmarshallOrder;
	}

	public void setUnmarshallOrder(UnmarshallOrder unmarshallOrder) {
		this.unmarshallOrder = unmarshallOrder;
	}

	public MarshallOrder<SalesOrder> getMarshallOrder() {
		return marshallOrder;
	}

	public void setMarshallOrder(MarshallOrder<SalesOrder> marshallOrder) {
		this.marshallOrder = marshallOrder;
	}

}
