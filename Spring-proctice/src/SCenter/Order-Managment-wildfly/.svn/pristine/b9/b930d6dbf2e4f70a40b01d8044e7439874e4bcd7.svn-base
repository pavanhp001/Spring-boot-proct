package com.AL.task.impl;

import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.activity.ActivitySalesContextEval;
import com.AL.activity.impl.ActivityCreateInitialStatus;
import com.AL.activity.impl.ActivityPersistSalesContext;
import com.AL.activity.impl.ActivitySubmitToProviderAfterValidation;
import com.AL.customer.OmeCustomerService;
import com.AL.enums.CustomerActionEnum;
import com.AL.ome.OmePricingMessageSender;
import com.AL.ome.OmeProductCommunicator;
import com.AL.pricing.OrderPriceUtil;
import com.AL.service.VOrderMappingService;
import com.AL.task.TaskBase;
import com.AL.task.broadcast.AddProviderIdToContextForBroadcast;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.ResponseItemOme;
import com.AL.task.response.ResponseBuilder;
import com.AL.util.messaging.Broadcastable;
import com.AL.validation.OMEValidator;
import com.AL.validation.impl.CustomerValidationHelper;
import com.AL.validation.impl.PricingValidationHelper;
import com.AL.V.beans.entity.Broadcast;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.User;
import com.AL.Vdao.broadcast.BroadcastMessage;
import com.AL.Vdao.broadcast.BroadcastService;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.Vdao.dao.UserDao;
import com.AL.vm.service.BroadcastManager;
import com.AL.vm.service.OrderManagementService;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.vm.util.converter.unmarshall.UnmarshallOrder;
import com.AL.vm.vo.AgentUtil;
import com.AL.xml.v4.CustomerManagementRequestResponseDocument;
import com.AL.xml.v4.CustomerType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.PricingRequestResponseDocument;
import com.AL.xml.v4.ProductEnterpriseRequestDocument;
import com.AL.xml.v4.ProductEnterpriseResponseDocument;

/**
 * @author ebthomas
 *
 */

@Component("taskCreateOrder")
public class TaskCreateOrder extends
		TaskBase<OrderManagementRequestResponseDocument> implements
		Broadcastable {

	private static final String TRANS_CREATE_ORDER = "createOrder";

	private Logger logger = Logger.getLogger(TaskCreateOrder.class);

	@Autowired
	ActivitySubmitToProviderAfterValidation orderStatusChange;

	@Autowired
	private UserDao userDao;

	@Autowired
	private OrderManagementService omeService;

	@Autowired
	private UnmarshallOrder unmarshallOrder;

	@Autowired
	private MarshallOrder<SalesOrder> marshallOrder;

	@Autowired
	private OrderManagementDao orderManagementDao;

	@Autowired
	private OmeCustomerService omeCustomerService;

	@Autowired
	private ResponseBuilder responseBuilder;

	@Autowired
	private OMEValidator omeValidator;

	@Autowired
	ActivityPersistSalesContext activityPersistSalesContext;

	@Autowired
	private VOrderMappingService orderMappingService;

	@Autowired
	private BroadcastManager broadcastManager;

	/**
	 * Constructor.
	 */
	public TaskCreateOrder() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	public OrchestrationContext processRequest(
			final OrderManagementRequestResponseDocument orderDocument) {

		logger.info("Processing createOrder request");
		if ((orderDocument == null)
				|| (orderDocument.getOrderManagementRequestResponse() == null)
				|| (orderDocument.getOrderManagementRequestResponse()
						.getRequest() == null)) {
			throw new IllegalArgumentException("invalid order document");
		}

		SalesOrder salesOrder = null;
		OrchestrationContext params = createLoadContext(orderDocument);
		ResponseItemOme rio = (ResponseItemOme) params;
		Request request = orderDocument.getOrderManagementRequestResponse()
				.getRequest();
		Consumer existingConsumer = null;

		boolean isValidCustBillingRef = Boolean.FALSE;
		boolean isValidLineItemRef = Boolean.FALSE;
		boolean isEmptyOrder = Boolean.FALSE;
		boolean isDuplicateIdExist = Boolean.FALSE;

		// Check for Customer Validity, if not valid then skip rest of the order
		// processing
		if (request.getOrderInfo() != null
				&& request.getOrderInfo().getCustomerInformation() != null) {

			// If no lineitems are provided then, create an empty order
			if (request.getOrderInfo().getLineItems() == null) {
				isEmptyOrder = Boolean.TRUE;
				rio.add(TaskContextParamEnum.isEmptyOrder.name(), isEmptyOrder);
			} else {
				LineItemCollectionType liColl = request.getOrderInfo()
						.getLineItems();
				if (liColl.getLineItemList() == null
						|| liColl.getLineItemList().isEmpty()) {
					logger.info("Create Order request is for empty order.");
					isEmptyOrder = Boolean.TRUE;
					rio.add(TaskContextParamEnum.isEmptyOrder.name(),
							isEmptyOrder);
				} else {
					rio.add(TaskContextParamEnum.isEmptyOrder.name(),
							isEmptyOrder);
				}
			}

			String customerAction = request.getOrderInfo()
					.getCustomerInformation().getAction().toString();
			logger.info("Customer action in createOrder request : " + customerAction);
			CustomerType customerSrc = request.getOrderInfo()
					.getCustomerInformation().getCustomer();
			CustomerManagementRequestResponseDocument customerResponse = null;

			// If customer action is CreateCustomer,UpdateCustomer, AddAddress,
			// then create customer,update it or add address and
			// Update the address ext id refered at line item level with one
			// generated by Customer service
			if (customerAction
					.equalsIgnoreCase(CustomerActionEnum.REFERENCECUSTOMER
							.name())) {
				existingConsumer = omeCustomerService.getExistingConsumer(customerSrc);
				if (existingConsumer != null) {
					ResponseItemOme omeParams = (ResponseItemOme) params;
					omeParams.add("customerExternalId",	existingConsumer.getExternalId());
				}
				// When order is ref. existing customer then we will not
				// validate unique id references
				isValidCustBillingRef = Boolean.TRUE;

				// Check external id referenced at lineitem level match actual
				// external id for consumer
				isValidLineItemRef = CustomerValidationHelper
						.isServiceAddressExist(existingConsumer,
								request.getOrderInfo());
				// isValidLineItemRef = Boolean.TRUE;
			} else {
				// validate address ref and billing ref provided at lineitem
				// level exist in customer info
				isValidLineItemRef = CustomerValidationHelper
						.validateLineItemRef(customerSrc,
								request.getOrderInfo(), customerAction);

				// For CreateCustomer,UpdateCustomer and AddAddress action
				// validate address references before proceeding for
				// creating/updating customer
				isValidCustBillingRef = CustomerValidationHelper
						.validateAddressRef(customerSrc, customerAction);

				// Check Unique ids for Billing Info and Addresses are unique
				// throughout the request
				isDuplicateIdExist = CustomerValidationHelper
						.checkDuplicateUniqueIdsExist(customerSrc);

				if (isValidCustBillingRef && isValidLineItemRef
						&& !isDuplicateIdExist) {
					customerResponse = omeCustomerService.processCustomer(
							params, customerSrc, customerAction);
					// Check for fatal error in customer response
					Boolean fatalResponse = CustomerValidationHelper
							.isFatalResponse(customerResponse, rio);
					if (customerResponse != null
							&& !customerAction
									.equalsIgnoreCase(CustomerActionEnum.UPDATECUSTOMER
											.name()) && !fatalResponse) {
						// Update address reference and billing info reference
						// at line item level
						omeCustomerService.updateLineitemInfo(customerResponse,
								orderDocument);
					} else {
						rio.addInvalidCustomerResponse(CUSTOMERSERVICE_FATAL_ERROR);
						return params;
					}
				}
			}

			// Processing Order only if customer data are valid and lineitem
			// references(Service Address Ref, Billing Ref)
			// exist in customer info
			if (isValidCustBillingRef && isValidLineItemRef) {
				salesOrder = processOrder(orderDocument, request, rio);
			}

			// If lineitem validation failed then we will add FATAL error
			// message in validation report
			if (!isValidLineItemRef) {
				rio.addInvalidLineitemData();
			}

			// If address ref used in BillingInfo does not exist in Customer
			// address list then add FATAL error message
			if (!isValidCustBillingRef) {
				// If address reference validation fail then we will not proceed
				// further
				rio.addInvalidCustomerData();
			}

			// If duplicate ids are used in billing info and address then retrun
			// fatal error
			if (isDuplicateIdExist) {
				rio.addInvalidCustomerData("Duplicate ids found in either BillingInformation or Address");
			}
		}

		params.add(TaskContextParamEnum.salesOrder.name(), salesOrder);
		params.add(TaskContextParamEnum.request.name(), request);
		params.add(TaskContextParamEnum.source.name(), ActivitySalesContextEval.INSTANCE.getSource(orderDocument));

		setupAgentParams(params, request);

		return params;
	}

	public void setupAgentParams(OrchestrationContext params, Request request) {

		String userLogin = AgentUtil.getAgentId(request);
		if (userLogin != null) {

			User user = userDao.findUserByUserLogin(userLogin);

			if (user != null) {
				params.add(TaskContextParamEnum.agent.name(), user);
			}

		}
	}

	public void addConsumerToContext(final OrchestrationContext params,
			SalesOrder salesOrder) {
		logger.debug("get customer info from db:"
				+ salesOrder.getConsumerExternalId());
		Consumer customer = omeCustomerService.getCustomer(salesOrder
				.getConsumerExternalId());

		if (customer != null) {
			params.add(TaskContextParamEnum.customer.name(), customer);
		}

	}

	private SalesOrder processOrder(
			final OrderManagementRequestResponseDocument orderDocument,
			Request request, ResponseItemOme rio) {

		logger.info("processing order information");
		SalesOrder salesOrder = null;
		PricingRequestResponseDocument pricingRequest = null;
		PricingRequestResponseDocument pricingResponse = null;
		Boolean isValidLineitems = Boolean.TRUE;
		Boolean isEmptyOrder = (Boolean) rio
				.get(TaskContextParamEnum.isEmptyOrder.name());
		if (!isEmptyOrder) {
			ProductEnterpriseRequestDocument productRequestDocument = omeService
					.prepareProductServiceRequest(orderDocument);

			ProductEnterpriseResponseDocument productResponse = OmeProductCommunicator
					.getProducts(productRequestDocument.toString());
			Boolean hasErrors = Boolean.TRUE;
			Map<String, String> validationMsgs = Collections.emptyMap();
			if (productResponse != null) {
				hasErrors = omeService
						.validateProductResponseForFatalError(productResponse);
				// Copy UniqueId returned by ProductService to Order Document so
				// that it can be used to
				// Uniquely retrieve product from prodct_catalog table.
				validationMsgs = omeService.copyLineItemInfoFromProductToOrder(
						productResponse, orderDocument);

				// Populate PromotionLineitem unique id with unique id of
				// Product where it applies to
				// So that later on during unmarshalling Promotion details can
				// be retrieved
				// based on Product unique id
				omeService
						.copyPromotionUniqueId(productResponse, orderDocument);
			} else {
				throw new IllegalArgumentException(
						"Error occured during product service communication!!!");
			}
			// if product response contains errors then order will not be
			// processed further
			if (hasErrors || !validationMsgs.isEmpty()) {
				logger.error("******Product service response contains fatal error or invalid products******");
				logger.error(productResponse.toString());
				isValidLineitems = Boolean.FALSE;
			}
			pricingRequest = omeService.prepareOrderForPricing(orderDocument);
			// If all lineitems are valid then only process the request further
			if (isValidLineitems) {
				logger.debug("Send request to pricing service to calculate price on each lineitem");
				pricingResponse = OmePricingMessageSender
						.getPricedOrder(pricingRequest.toString());
				// pricingResponse =
				// DummyPricingEngine.processOrderPrice(pricingRequest);
				// Code to check pricing response had any FATAL errors or not.
				boolean fatalError = PricingValidationHelper
						.isFatalErrorExist(pricingResponse);
				if (!fatalError) {

					logger.debug("unmarshall order and its line item");
					salesOrder = unmarshallOrder.build(request, Boolean.FALSE);

					// Check for warning messages in Pricing response
					boolean warningError = PricingValidationHelper
							.isWarningErrorExist(pricingResponse);

					// If warning message is present then update the status
					if (warningError) {
						rio.add(TaskContextParamEnum.pricingResponse.name(),
								pricingResponse);
					}

					logger.debug("Update the lineitem price from pricing response");
					if (pricingResponse != null && salesOrder != null) {
						logger.debug("recieved from pricing:"
								+ pricingResponse.toString());
						salesOrder = OrderPriceUtil.updateLineItemPrice(
								salesOrder, pricingResponse);
					}
				} else {
					logger.error("Pricing service response contains fatal/error message!!!");
					PricingValidationHelper.populateStatusMsg(pricingResponse,
							rio.getValidationReport());
				}
			} else {
				// rio.addInvalidProduct("PRODUCT SERVICE RETURNED WITH FATAL ERROR ");
				rio.addInvalidLineItems(validationMsgs);
			}
		} else {
			logger.info("Skipping interaction with pricing and product service. Possible accord order or empty order");
			salesOrder = unmarshallOrder.build(request, Boolean.FALSE);
		}
		return salesOrder;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrchestrationContext processValidation(OrchestrationContext params,
			SalesOrder salesOrder) {

		logger.info("Validating order");
//		Set<ConstraintViolation<SalesOrder>> violations = omeValidator
//				.validate(salesOrder);
//
//		if (violations != null) {
//			logger.debug("No of violation for SalesOrder are :"
//					+ violations.size());
//			populateViolation(params, violations);
//		} else {
//			logger.error("OmeValidator failed to validate SalesOrder entity.");
//		}
		return params;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrchestrationContext processAgenda(final OrchestrationContext params) {

		SalesOrder salesOrder = (SalesOrder) params
				.get(TaskContextParamEnum.salesOrder.name());
		User user = (User) params.get(TaskContextParamEnum.agent.name());

		if (salesOrder != null) {

			ActivityCreateInitialStatus.INSTANCE.execute(orderStatusChange,
						user, salesOrder, params);

			Long customerExtId = (Long) params.get("customerExternalId");
			Consumer consumer = omeCustomerService.getCustomer(customerExtId);
			params.add(TaskContextParamEnum.customer.name(), consumer);

			// attaching order with consumer
			salesOrder.setConsumerExternalId(customerExtId);
			logger.debug("saving new sales order");
			String guid = (String)params.get(TaskContextParamEnum.correlationId.name());
			salesOrder.setGuid(guid);
			orderManagementDao.save(salesOrder, customerExtId);
			salesOrder = orderManagementDao.findById(salesOrder.getExternalId());
			
			params.add(TaskContextParamEnum.salesOrder.name(), salesOrder);
			String source = (String)params.get(TaskContextParamEnum.source.name() );
			if(source == null || source.trim().equals("")){
				source = salesOrder.getSource();
			}

			if (salesOrder.getLineItems() != null && !salesOrder.getLineItems().isEmpty()) {
			    for (LineItem li : salesOrder.getLineItems()) {
				// Adding consumer interaction about customer creation
				omeCustomerService.addConsumerInteraction(user.getUserLogin(), salesOrder, source, li, "Order created with LineItem for customer : " + customerExtId);
			    }
			}else {
			    omeCustomerService.addConsumerInteraction(user.getUserLogin(), salesOrder, source, "Empty order created for customer : ." + customerExtId);
			}
			if (salesOrder != null) {
				((ResponseItemOme) params).addSalesOrderCreated(salesOrder);
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
		OrderManagementRequestResponseDocument response = responseBuilder
				.buildResponse(params);
		return response;
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
		logger.info("Processing order broadcast");
		orderMappingService.processOrderMapping(strToBroadcastOriginal);
		Broadcast brd = broadcastManager.buildOrderBroadcast(strToBroadcastOriginal, map);
		String extId = String.valueOf(broadcastManager.saveBroadcastMessage(brd));
		map.put("broadcast_id", extId);
//		Broadcastable broadcast = HttpBroadcastable.createDefault();
//		broadcast.broadcast(strToBroadcastOriginal, map);
		BroadcastMessage broadcast = new BroadcastMessage(extId,strToBroadcastOriginal,map,TRANS_CREATE_ORDER);
		BroadcastService.sendBroadcast(broadcast, Thread.currentThread().getName());
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
