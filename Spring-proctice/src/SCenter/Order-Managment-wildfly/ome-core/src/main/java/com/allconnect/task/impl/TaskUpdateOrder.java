package com.AL.task.impl;

import static com.AL.task.util.SalesContextUtil.SESSION_STATUS_KEY;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.activity.ActivitySalesContextEval;
import com.AL.activity.impl.ActivityPersistSalesContext;
import com.AL.customer.OmeCustomerService;
import com.AL.enums.CustomerActionEnum;
import com.AL.task.LocalTaskUpdateOrder;
import com.AL.task.TaskBase;
import com.AL.task.broadcast.AddProviderIdToContextForBroadcast;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.ResponseItemOme;
import com.AL.task.response.ResponseBuilder;
import com.AL.task.util.DomainObjectLocator;
import com.AL.task.util.SalesContextUtil;
import com.AL.util.messaging.Broadcastable;
import com.AL.validation.OMEValidator;
import com.AL.validation.impl.CustomerValidationHelper;
import com.AL.V.beans.entity.Broadcast;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.Vdao.broadcast.BroadcastMessage;
import com.AL.Vdao.broadcast.BroadcastService;
import com.AL.Vdao.dao.CustomerDao;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.Vdao.util.SystemPropertiesRepo;
import com.AL.vm.service.BroadcastManager;
import com.AL.vm.service.OrderAgentService;
import com.AL.vm.util.converter.marshall.MarshallConsumers;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.vm.util.converter.unmarshall.UnmarshallOrder;
import com.AL.vm.util.converter.unmarshall.UnmarshallOrderStatus;
import com.AL.xml.v4.CustomerInformationDocument.CustomerInformation;
import com.AL.xml.v4.CustomerManagementRequestResponseDocument;
import com.AL.xml.v4.CustomerType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderType;

/**
 * @author ebthomas
 *
 */

@Component("taskUpdateOrder")
public class TaskUpdateOrder extends
		TaskBase<OrderManagementRequestResponseDocument> implements
		LocalTaskUpdateOrder<OrderManagementRequestResponseDocument>,
		Broadcastable {

	private static final String TRANS_UPDATE_ORDER = "updateOrder";

	private static final String UPDATE_ORDER_ACTION = "Order Updated";

	private static Logger logger = Logger.getLogger(TaskUpdateOrder.class);

	@Autowired
	private UnmarshallOrder unmarshallOrder;

	@Autowired
	private OrderManagementDao orderManagementDao;

	@Autowired
	private MarshallOrder<SalesOrder> marshallOrder;

	@Autowired
	private UnmarshallOrderStatus unmarshallOrderStatus;

	@Autowired
	private OmeCustomerService omeCustomerService;

	@Autowired
	private ResponseBuilder responseBuilder;

	@Autowired
	private OMEValidator omeValidator;

	@Autowired
	ActivityPersistSalesContext activityPersistSalesContext;

	@Autowired
	private OrderAgentService agentService;

	@Autowired
	private MarshallConsumers marshallConsumer;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private BroadcastManager broadcastManager;

	/**
	 * {@inheritDoc}
	 */
	public OrchestrationContext processRequest(final OrderManagementRequestResponseDocument orderDocument) {
		
		logger.info("Processing update order request");
		Request request = orderDocument.getOrderManagementRequestResponse().getRequest();
		String correlationId = orderDocument.getOrderManagementRequestResponse().getCorrelationId();

		OrchestrationContext params = createLoadContext(orderDocument);

		boolean isSessionStatusCompleted = SalesContextUtil.INSTANCE.isSessionStatusCompleted(request.getSalesContext());
		if (isSessionStatusCompleted) {
			logger.debug("sessionStatus completed");
			request.setIncludeAccountHolders(true);
		}
		params.add(SESSION_STATUS_KEY, isSessionStatusCompleted);

		ResponseItemOme rio = (ResponseItemOme) params;
		String agentId = "";
		if (request.getAgentId() != null) {
			agentId = request.getAgentId();
		}
		
		if (!DomainObjectLocator.INSTANCE.validateAgent(agentId, agentService)) {
			throw new IllegalArgumentException("Invalid agent id : " + agentId);
		}
		params.add(TaskContextParamEnum.agentId.name(), agentId);
		
		// Updating customer info if customer info is present and action is "UpdateCustomer"
		if (request.getUpdateOrderInfo() != null && request.getUpdateOrderInfo().getCustomerInformation() != null) {
			
			String customerAction = request.getUpdateOrderInfo().getCustomerInformation().getAction().toString();
			logger.debug("Customer Action..." + customerAction);
			
			CustomerType customerSrc = request.getUpdateOrderInfo().getCustomerInformation().getCustomer();
			CustomerManagementRequestResponseDocument customerResponse = null;

			// If customer action is UpdateCustomer then update it
			if (customerAction.equalsIgnoreCase(CustomerActionEnum.UPDATECUSTOMER.name())) {
				
				customerResponse = omeCustomerService.processCustomer(params, customerSrc, customerAction);
				// Check for fatal error in customer response
				Boolean fatalResponse = CustomerValidationHelper.isFatalResponse(customerResponse, rio);
				if (fatalResponse || customerResponse == null) {
					rio.addInvalidCustomerResponse(CUSTOMERSERVICE_FATAL_ERROR);
				}
			}
		}

		params.add(TaskContextParamEnum.correlationId.name(), correlationId);
		params.add(TaskContextParamEnum.request.name(), request);
		params.add(TaskContextParamEnum.source.name(), ActivitySalesContextEval.INSTANCE.getSource(orderDocument));

		return params;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrchestrationContext processAgenda(final OrchestrationContext params) {

		Request request = (Request) params.get(TaskContextParamEnum.request.name());
		if (request == null) {
			throw new IllegalArgumentException("missing Request");
		}

		OrderType ucOrderInfo = request.getUpdateOrderInfo();
		if (ucOrderInfo == null) {
			throw new IllegalArgumentException("missing Order to be updated");
		}

		Long orderExternalId = ucOrderInfo.getExternalId();
		if ((orderExternalId == null) || (orderExternalId == 0)) {

			String orderId = request.getOrderId();
			if (orderId != null) {
				try {
					orderExternalId = Long.valueOf(orderId);
				} catch (Exception e) {
					params.getValidationReport().addErrorMessage(987L,
							"unable to locate sales order external Id");
				}
			}

			throw new IllegalArgumentException("missing external Id");
		}

		SalesOrder orderBean = orderManagementDao.findById(Long.valueOf(orderExternalId), request.getIncludeAccountHolders());
		if (orderBean == null) {
			params.getValidationReport().addErrorMessage(987L, "unable to locate sales order:" + orderExternalId);
		}
		String guid = (String) params.get(TaskContextParamEnum.correlationId.name());
		orderBean.setGuid(guid);
		
		unmarshallOrder.updateOrder(orderBean, ucOrderInfo, Boolean.TRUE);
		params.addResponseItem(TaskContextParamEnum.salesOrder.name(), orderBean);

		logger.debug("updating");
		if (orderBean != null) {
			
			activityPersistSalesContext.process(params);
			
			//orderManagementDao.update(orderBean);
			Consumer consumer = omeCustomerService.getCustomer(orderBean.getConsumerExternalId());

			String agentId = (String) params.get(TaskContextParamEnum.agentId.name());
			String source = (String) params.get(TaskContextParamEnum.source.name());
			if (source == null || source.trim().equals("")) {
				source = orderBean.getSource();
			}

			omeCustomerService.addConsumerInteraction(agentId, orderBean, source, "Order Information updated.");
			params.add(TaskContextParamEnum.customer.name(), consumer);

			params.addResponseItem(TaskContextParamEnum.salesOrder.name(),orderBean);
		}

		AddProviderIdToContextForBroadcast.INSTANCE.execute(params, orderBean);

		return params;
	}

	/**
	 * {@inheritDoc}
	 */
	public OrderManagementRequestResponseDocument processResponse(
			final OrchestrationContext params) {

		Request request = (Request) params.get(TaskContextParamEnum.request
				.name());

		OrderManagementRequestResponseDocument container = OrderManagementRequestResponseDocument.Factory
				.newInstance();
		OrderManagementRequestResponse requestResponse = container
				.addNewOrderManagementRequestResponse();

		// Boolean includeCustDetails = (Boolean)params.get(TaskContextParamEnum.includeCustomerDetails.name());
		List<SalesOrder> salesOrderBeanList = getSalesOrderBeanList(params.get(TaskContextParamEnum.salesOrder.name()));

		Object ttAsObject = params.get(TaskContextParamEnum.transactionType.name());

		if (ttAsObject != null) {
			
			String transType = (String) ttAsObject;
			logger.debug("TaskUpdateOrder:processResponse(): transType: "+ transType);
			container.getOrderManagementRequestResponse().setTransactionType(OrderManagementRequestResponse.TransactionType.Enum.forString(transType));
		} 
		else {
			logger.debug("TaskUpdateOrder:processResponse(): transType: NULL");
		}

		// Attach CorrelationId
		String correlationId = (String) params.get(TaskContextParamEnum.correlationId.name());
		logger.debug("TaskUpdateOrder:processResponse(): correlationId: "+ correlationId);
		if (correlationId != null) {
			container.getOrderManagementRequestResponse().setCorrelationId(correlationId);
		}

		// Load response
		// loadSalesOrderToResponse( requestResponse, salesOrderBeanList);
		if (salesOrderBeanList != null) {
			Response response = requestResponse.addNewResponse();
			for (SalesOrder salesOrderBean : salesOrderBeanList) {
				
				responseBuilder.populateSalesContext(params, response, salesOrderBean);
				OrderType orderType = response.addNewOrderInfo();
				CustomerInformation custInfoType = orderType.addNewCustomerInformation();
				CustomerType custType = custInfoType.addNewCustomer();
				if (salesOrderBean != null && orderType != null) {
					
					marshallOrder.build(salesOrderBean, orderType,request.getIncludeAccountHolders());
					Consumer consumerBean = customerDao.findCustomerByExternalId(salesOrderBean.getConsumerExternalId());
					if (consumerBean != null) {
						custType = orderType.getCustomerInformation().getCustomer();
						marshallConsumer.build(consumerBean, custType, request.getIncludeAccountHolders());
					}
				}
			}
		} 
		else {
			logger.debug("Sales order not found for provided date.");
		}

		// Ensure that request is in the response container
		// loadRequestToResponse(container, params);
		Request req = (Request) params.get(TaskContextParamEnum.request.name());

		// If properties is set in db to true then only add req to response xml
		Boolean addRequestToRes = SystemPropertiesRepo.getSystemPropertyValueAsBoolean("OME.add.req.to.response");
		if (addRequestToRes) {
			container.getOrderManagementRequestResponse().setRequest(req);
		}
		return container;
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
		Broadcast brd = broadcastManager.buildOrderBroadcast(
				strToBroadcastOriginal, map);
		String extId = String.valueOf(broadcastManager
				.saveBroadcastMessage(brd));
		map.put("broadcast_id", extId);
		// Broadcastable broadcast = HttpBroadcastable.createDefault();
		// broadcast.broadcast(strToBroadcastOriginal, map);
		BroadcastMessage broadcast = new BroadcastMessage(extId,
				strToBroadcastOriginal, map, TRANS_UPDATE_ORDER);
		BroadcastService.sendBroadcast(broadcast, Thread.currentThread()
				.getName());
	}

	public UnmarshallOrder getUnmarshallOrder() {
		return unmarshallOrder;
	}

	public void setUnmarshallOrder(UnmarshallOrder unmarshallOrder) {
		this.unmarshallOrder = unmarshallOrder;
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

	public UnmarshallOrderStatus getUnmarshallOrderStatus() {
		return unmarshallOrderStatus;
	}

	public void setUnmarshallOrderStatus(
			UnmarshallOrderStatus unmarshallOrderStatus) {
		this.unmarshallOrderStatus = unmarshallOrderStatus;
	}

}
