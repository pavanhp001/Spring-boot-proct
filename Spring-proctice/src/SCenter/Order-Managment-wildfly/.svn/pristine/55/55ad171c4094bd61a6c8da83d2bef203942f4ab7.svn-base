package com.AL.task.response;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.util.AccountHolderUtil;
import com.AL.validation.Message;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.SalesOrderContext;
import com.AL.V.beans.job.Job;
import com.AL.Vdao.util.SystemPropertiesRepo;
import com.AL.vm.util.converter.marshall.MarshallConsumers;
import com.AL.vm.util.converter.marshall.MarshallJob;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.vm.util.converter.marshall.MarshallSalesOrderContext;
import com.AL.xml.v4.CustomerInformationDocument.CustomerInformation;
import com.AL.xml.v4.CustomerType;
import com.AL.xml.v4.JobCollectionType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.NotificationEventCollectionType;
import com.AL.xml.v4.NotificationEventType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

@Component
public class ResponseBuilder {

	private static final Logger logger = Logger
			.getLogger(ResponseBuilder.class);
	@Autowired
	private MarshallOrder<SalesOrder> marshallOrder;

	@Autowired
	private MarshallSalesOrderContext marshallContext;

	@Autowired
	private MarshallConsumers marshallConsumer;

	@Autowired
	private MarshallJob marshallJob;

	public OrderManagementRequestResponseDocument buildResponse(
			final OrchestrationContext params) {

		logger.info("Preparing order response");
		OrderManagementRequestResponseDocument doc = (OrderManagementRequestResponseDocument) params.get(TaskContextParamEnum.arbiterResponse.name());

		// create response container
		OrderManagementRequestResponseDocument container = OrderManagementRequestResponseDocument.Factory.newInstance();
		OrderManagementRequestResponse requestResponse = container.addNewOrderManagementRequestResponse();
		Response response = requestResponse.addNewResponse();
		loadResponseContext(params, requestResponse);

		List<Job> jobList = (List<Job>) params.get(TaskContextParamEnum.jobList.name());
		if (jobList != null) {
			JobCollectionType jobCollection = response.addNewJobs();
			marshallJob.build(jobList, jobCollection);
		}

		// add Response Information to response container
		SalesOrder salesOrderBean = getSalesOrder(params.get(TaskContextParamEnum.salesOrder.name()));
		OrderType newOrderInfoResponse = response.addNewOrderInfo();

		try {
			if ((salesOrderBean != null)
					&& (salesOrderBean.getSalesOrderContexts() != null)
					&& (salesOrderBean.getSalesOrderContexts().size() > 0)) {
				
				List<SalesOrderContext> socList = new ArrayList<SalesOrderContext>(salesOrderBean.getSalesOrderContexts());
				SalesContextType scType = marshallContext.build(socList);
				response.setSalesContext(scType);
			}

			populateSalesContext(params, response, salesOrderBean);

		} catch (LazyInitializationException lazyException) {
			params.getValidationReport().addErrorMessage(34L, lazyException.getMessage());
			logger.error(lazyException.getMessage()+ " sales context marshalling error");
		} catch (NullPointerException ne) {
			params.getValidationReport().addErrorMessage(35L, ne.getMessage());
			logger.error(ne.getMessage() + "  Missing/Invalid Information");
		}

		Request request = (Request) params.get(TaskContextParamEnum.request.name());
		boolean includeAccountHolders = false;
		if(request != null) {
			
			includeAccountHolders = request.getIncludeAccountHolders();
		} else if(params.get(AccountHolderUtil.includeAccountHoldersKey) != null) {
			includeAccountHolders = (Boolean)params.get(AccountHolderUtil.includeAccountHoldersKey); 
		}
		
		// Reverting the account holder flag if the request is from ext_process
		if(null != params.get(TaskContextParamEnum.isACHResponseNeeded.name())){
			includeAccountHolders = false;
		}
		
		Long orderId = (Long) params.get(TaskContextParamEnum.salesOrderId.name());

		// Ensure that request is in the response container
		Map<String, NotificationEventCollectionType> notifEventsMap = loadRequestToResponse(container, request, orderId);
		if (!params.getValidationReport().hasMessages(Message.Type.FATAL)) {
			
			marshallOrderAndCustomerDetail(response, newOrderInfoResponse, salesOrderBean, params, includeAccountHolders);
		}

		if (doc != null) {
			updateResponseWithTransientContainer(doc, container);
		}

		addNotificationEventsFromRequest(notifEventsMap, response);

		addTransactionType(params, container);
		return container;
	}

	public void addTransactionType(final OrchestrationContext params,
			final OrderManagementRequestResponseDocument container) {

		Object ttAsObject = params.get(TaskContextParamEnum.transactionType
				.name());

		if (ttAsObject != null) {
			String transType = (String) ttAsObject;
			container.getOrderManagementRequestResponse().setTransactionType(
					OrderManagementRequestResponse.TransactionType.Enum
							.forString(transType));
		}
	}

	public void populateSalesContext(final OrchestrationContext params,
			Response response, SalesOrder salesOrderBean) {
		try {
			if ((salesOrderBean != null)
					&& (salesOrderBean.getSalesOrderContexts() != null)
					&& (salesOrderBean.getSalesOrderContexts().size() > 0)) {
				List<SalesOrderContext> socList = new ArrayList<SalesOrderContext>(
						salesOrderBean.getSalesOrderContexts());
				SalesContextType scType = marshallContext.build(socList);
				response.setSalesContext(scType);
			}

		} catch (LazyInitializationException lazyException) {
			params.getValidationReport().addErrorMessage(34L,
					lazyException.getMessage());
			logger.error(lazyException.getMessage()
					+ " sales context marshalling error");
		} catch (NullPointerException ne) {
			params.getValidationReport().addErrorMessage(35L, ne.getMessage());
			logger.error(ne.getMessage() + "  Missing/Invalid Information");
		}
	}

	public void doX() {

	}

	public void addNotificationEventsFromRequest(
			final Map<String, NotificationEventCollectionType> notifEventsMap,
			final Response response) {

		logger.debug("adding notification events from request to response");
		if ((notifEventsMap != null) && (!notifEventsMap.isEmpty())
				&& (response != null) && (response.getOrderInfoList() != null)
				&& (response.getOrderInfoList().size() > 0)) {

			LineItemCollectionType litCollection = response.getOrderInfoList()
					.get(0).getLineItems();
			if ((litCollection != null)
					&& (litCollection.getLineItemList() != null)) {
				List<LineItemType> listOfLineItems = litCollection
						.getLineItemList();

				for (LineItemType lit : listOfLineItems) {

					String responseLineItemNumber = null;

					if (lit.getExternalId() == 0) {
						responseLineItemNumber = String.valueOf(lit
								.getExternalId());
					} else {
						responseLineItemNumber = String.valueOf(lit
								.getLineItemNumber());
					}

					NotificationEventCollectionType existingFromRequestNotifEventCollType = notifEventsMap
							.get(responseLineItemNumber);
					NotificationEventCollectionType newNotifEventCollType = lit
							.getNotificationEvents();

					if ((newNotifEventCollType == null)
							&& (existingFromRequestNotifEventCollType != null)
							&& (existingFromRequestNotifEventCollType
									.getEventList() != null)
							&& (existingFromRequestNotifEventCollType
									.getEventList().size() > 0)) {
						newNotifEventCollType = lit.addNewNotificationEvents();
					}

					if ((existingFromRequestNotifEventCollType != null)
							&& (existingFromRequestNotifEventCollType
									.getEventList() != null)
							&& (existingFromRequestNotifEventCollType
									.getEventList().size() > 0)) {

						for (NotificationEventType eventFromRequest : existingFromRequestNotifEventCollType
								.getEventList()) {
							newNotifEventCollType.getEventList().add(
									eventFromRequest);
						}

					}
				}

			}
		}

	}

	/**
	 * Helper method to populate client supplied context informatoin back in to
	 * the response
	 *
	 * @param params
	 * @param requestResponse
	 */
	public void loadResponseContext(final OrchestrationContext params,
			OrderManagementRequestResponse requestResponse) {

		if (params.get(TaskContextParamEnum.correlationId.name()) != null) {
			requestResponse.setCorrelationId((String) params
					.get(TaskContextParamEnum.correlationId.name()));
		}

		if (params.get(TaskContextParamEnum.transactionId.name()) != null) {
			requestResponse.setTransactionId((Integer) params
					.get(TaskContextParamEnum.transactionId.name()));
		}

		if (params.get(TaskContextParamEnum.timeStamp.name()) != null) {
			requestResponse.setTransactionTimeStamp((Calendar) params
					.get(TaskContextParamEnum.timeStamp.name()));
		} else {
			requestResponse.setTransactionTimeStamp(Calendar.getInstance());
		}

		if (params.get(TaskContextParamEnum.affiliateName.name()) != null) {
			requestResponse
					.setAffiliateName((String) TaskContextParamEnum.affiliateName
							.name());
		}

		if (params.get(TaskContextParamEnum.salesCode.name()) != null) {
			requestResponse
					.setSalesCode((String) TaskContextParamEnum.salesCode
							.name());
		}

		if (params.get(TaskContextParamEnum.sessionId.name()) != null) {
			requestResponse
					.setSessionId((String) TaskContextParamEnum.sessionId
							.name());
		}

		if (params.get(TaskContextParamEnum.region.name()) != null) {
			requestResponse.setRegion((String) TaskContextParamEnum.region
					.name());
		}

		if (params.get(TaskContextParamEnum.transactionType.name()) != null) {
			requestResponse.setTransactionType(requestResponse
					.getTransactionType().forString(
							(String) TaskContextParamEnum.transactionType
									.name()));
		}

		if(params.get(TaskContextParamEnum.notificationEvents.name()) != null) {
		    NotificationEventCollectionType notif =(NotificationEventCollectionType)params.get(TaskContextParamEnum.notificationEvents.name());
		    requestResponse.getResponse().setNotificationEvents(notif);
		}
	}

	public void updateResponseWithTransientContainer(
			OrderManagementRequestResponseDocument docInput,
			OrderManagementRequestResponseDocument docResponse) {

		logger.debug("updateResponseWithTransientContainer");

		if ((docInput != null) && (docResponse != null)) {

			OrderManagementRequestResponse omRRInput = docInput
					.getOrderManagementRequestResponse();

			OrderManagementRequestResponse omRRResponse = docResponse
					.getOrderManagementRequestResponse();

			if ((omRRInput != null) && (omRRResponse != null)) {

				Request requestWithTransient = omRRInput.getRequest();
				Response responseWithTransient = omRRResponse.getResponse();

				if ((requestWithTransient != null)
						&& (responseWithTransient != null)
						&& (responseWithTransient.getOrderInfoList() != null)
						&& (responseWithTransient.getOrderInfoList().size() > 0)) {

					Map<String, LineItemType> requestMap = getMapOfLineItemTransientResponse(requestWithTransient
							.getOrderInfo());

					Map<String, LineItemType> responseMap = getMapOfLineItemTransientResponse(responseWithTransient
							.getOrderInfoList().get(0));

					Set<String> requestKeys = requestMap.keySet();

					for (String requestKey : requestKeys) {
						LineItemType requestLIT = requestMap.get(requestKey);
						LineItemType responseLIT = responseMap.get(requestKey);

						if ((requestLIT != null)
								&& (responseLIT != null)
								&& (requestLIT.getTransientResponseContainer() != null)) {
							responseLIT
									.setTransientResponseContainer(requestLIT
											.getTransientResponseContainer());
						}
					}

				}

			}

		}
	}

	public Map<String, LineItemType> getMapOfLineItemTransientResponse(
			final OrderType ot) {

		Map<String, LineItemType> mapOfLineItemTypes = new HashMap<String, LineItemType>();

		if ((ot != null) && (ot.getLineItems() != null)
				&& (ot.getLineItems().getLineItemList() != null)) {

			List<LineItemType> lits = ot.getLineItems().getLineItemList();

			for (LineItemType lit : lits) {
				String lineItemNumber = String.valueOf(lit.getLineItemNumber());
				mapOfLineItemTypes.put(lineItemNumber, lit);
			}
		}

		return mapOfLineItemTypes;
	}

	public void marshallOrderAndCustomerDetail(Response response,
			OrderType newOrderInfoResponse, SalesOrder salesOrderBean,
			final OrchestrationContext params) {
		marshallOrderAndCustomerDetail(response, newOrderInfoResponse,
				salesOrderBean, params, false);
	}
	
	public void marshallOrderAndCustomerDetail(Response response,
			OrderType newOrderInfoResponse, SalesOrder salesOrderBean,
			final OrchestrationContext params, boolean includeAccountHolders) {

		if (salesOrderBean != null) {
			logger.debug("adding Order info in response");
			marshallOrder.build(salesOrderBean, newOrderInfoResponse, includeAccountHolders);
			response.setOrderId(String.valueOf(salesOrderBean.getExternalId()));
		}

		Consumer consumerBean = (Consumer) params
				.get(TaskContextParamEnum.customer.name());
		if (consumerBean != null) {
			logger.debug("adding Customer info in response");
			CustomerInformation custInfoType = newOrderInfoResponse
					.addNewCustomerInformation();
			CustomerType custType = custInfoType.addNewCustomer();
			custType = custInfoType.getCustomer();
			marshallConsumer.build(consumerBean, custType, includeAccountHolders);
		}
	}

	public OrderManagementRequestResponseDocument buildResponseList(
			final OrchestrationContext params) {

		OrderManagementRequestResponseDocument doc = (OrderManagementRequestResponseDocument) params
				.get(TaskContextParamEnum.arbiterResponse.name());

		if (doc != null) {
			return doc;
		}

		// create response container
		OrderManagementRequestResponseDocument container = OrderManagementRequestResponseDocument.Factory
				.newInstance();
		OrderManagementRequestResponse requestResponse = container
				.addNewOrderManagementRequestResponse();
		Response response = requestResponse.addNewResponse();

		loadResponseContext(params, requestResponse);

		// add Response Information to response container
		SalesOrder salesOrderBean = getSalesOrder(params
				.get(TaskContextParamEnum.salesOrder.name()));

		OrderType newOrderInfoResponse = response.addNewOrderInfo();

		if (salesOrderBean != null) {
			marshallOrder.build(salesOrderBean, newOrderInfoResponse);
			response.setOrderId(String.valueOf(salesOrderBean.getExternalId()));
		}

		Boolean includeCustDetails = (Boolean) params
				.get(TaskContextParamEnum.includeCustomerDetails.name());

		if ((includeCustDetails != null) && (includeCustDetails)) {
			Consumer consumerBean = (Consumer) params
					.get(TaskContextParamEnum.customer.name());

			if (consumerBean != null) {
				CustomerInformation custInfoType = newOrderInfoResponse
						.addNewCustomerInformation();

				CustomerType custType = custInfoType.addNewCustomer();
				marshallConsumer.build(consumerBean, custType);
			}
		}

		Request request = (Request) params.get(TaskContextParamEnum.request
				.name());

		Long orderId = (Long) params.get(TaskContextParamEnum.salesOrderId
				.name());

		// Ensure that request is in the response container
		Map<String, NotificationEventCollectionType> notifEventsMap = loadRequestToResponse(
				container, request, orderId);

		addNotificationEventsFromRequest(notifEventsMap, response);

		return container;
	}

	/**
	 * @param taskResponse
	 *            Load Request information to response
	 * @param container
	 *            Response Object
	 */
	public Map<String, NotificationEventCollectionType> loadRequestToResponse(
			final OrderManagementRequestResponseDocument container,
			final Request request, Long orderId) {

		Map<String, NotificationEventCollectionType> events = new HashMap<String, NotificationEventCollectionType>();

		if (request != null) {
			if (orderId != null) {
				request.setOrderId(String.valueOf(orderId.longValue()));
			}

			// If properties is set in db to true then only add req to response
			// xml
			Boolean addRequestToRes = SystemPropertiesRepo
					.getSystemPropertyValueAsBoolean("OME.add.req.to.response");
			if (addRequestToRes) {
				container.getOrderManagementRequestResponse().setRequest(
						request);
			}

			if ((request.getOrderInfo() != null)
					&& (request.getOrderInfo().getLineItems() != null)
					&& (request.getOrderInfo().getLineItems().getLineItemList() != null)) {
				List<LineItemType> litList = request.getOrderInfo()
						.getLineItems().getLineItemList();
				for (LineItemType lit : litList) {

					try {

						String externalId = null;

						if (lit.getExternalId() == 0) {
							externalId = String.valueOf(lit.getExternalId());
						} else {
							externalId = String
									.valueOf(lit.getLineItemNumber());
						}

						if ((externalId != null)
								&& (lit.getNotificationEvents() != null)) {
							events.put(externalId, lit.getNotificationEvents());
						}

					} catch (Exception e) {
						logger.warn("invalid line item:" + orderId);
					}
				}
			}
		}

		return events;
	}

	/**
	 * @param bean
	 *            SalesOrderBean weakly typed
	 * @return Strongly typed version of SalesOrderBean
	 */
	public SalesOrder getSalesOrder(final Object bean) {
		try {
			return (SalesOrder) bean;
		} catch (ClassCastException cce) {
			cce.printStackTrace();
			return null;
		}

	}
}
