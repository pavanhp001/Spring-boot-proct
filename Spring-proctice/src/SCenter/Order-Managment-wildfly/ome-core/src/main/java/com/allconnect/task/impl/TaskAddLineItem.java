package com.AL.task.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.activity.ActivitySalesContextEval;
import com.AL.activity.impl.ActivityPersistSalesContext;
import com.AL.customer.OmeCustomerService;
import com.AL.ome.OmePricingMessageSender;
import com.AL.ome.OmeProductCommunicator;
import com.AL.pricing.OrderPriceUtil;
import com.AL.task.LocalTaskAddLineItem;
import com.AL.task.TaskBase;
import com.AL.task.broadcast.AddProviderIdToContextForBroadcast;
import com.AL.task.builder.OrderChangeVOBuilder;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.ResponseItemOme;
import com.AL.task.response.ResponseBuilder;
import com.AL.task.strategy.LineItemManagementStrategy;
import com.AL.task.util.SalesContextUtil;
import com.AL.task.validate.ValidateAddLineItem;
import com.AL.util.audit.AuditService;
import com.AL.util.messaging.Broadcastable;
import com.AL.validation.impl.PricingValidationHelper;
import com.AL.V.beans.entity.Broadcast;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.OrderAudit;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.SalesOrderContext;
import com.AL.Vdao.broadcast.BroadcastMessage;
import com.AL.Vdao.broadcast.BroadcastService;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.Vdao.dao.SalesOrderContextDao;
import com.AL.vm.service.BroadcastManager;
import com.AL.vm.service.MarshallService;
import com.AL.vm.service.OrderManagementService;
import com.AL.vm.util.converter.marshall.MarshallLineItem;
import com.AL.vm.util.converter.unmarshall.UnmarshallLineItem;
import com.AL.vm.vo.OrderChangeValueObject;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.PricingRequestResponseDocument;
import com.AL.xml.v4.ProductEnterpriseRequestDocument;
import com.AL.xml.v4.ProductEnterpriseResponseDocument;

/**
 * @author ebthomas
 * 
 */

@Component("taskAddLineItem")
public class TaskAddLineItem extends
		TaskBase<OrderManagementRequestResponseDocument> implements
		LocalTaskAddLineItem<OrderManagementRequestResponseDocument>,
		Broadcastable {
	private static final String TRANS_ADD_LINE_ITEM = "addLineItem";

	private static final String NEW_LI_EXT_I_DS = "newLIExtIDs";

	private static final String CONCERT = "concert";

	private static final String ADD_LINEITEM_ACTION = "Lineitem Added";

	private Logger logger = Logger.getLogger(TaskAddLineItem.class);

	@Autowired
	private UnmarshallLineItem unmarshallLineItem;

	@Autowired
	private MarshallLineItem marshallLineItem;

	@Autowired
	private OrderManagementService orderManagementService;

	@Autowired
	private MarshallService marshallService;

	@Autowired
	private AuditService<OrderAudit> auditService;

	@Autowired
	private OmeCustomerService omeCustomerService;

	@Autowired
	private ResponseBuilder responseBuilder;

	@Autowired
	ActivityPersistSalesContext activityPersistSalesContext;

	@Autowired
	private OrderManagementDao orderManagementDao;

	@Autowired
	private SalesOrderContextDao socDao;

	@Autowired
	private BroadcastManager broadcastManager;

	/**
	 * {@inheritDoc}
	 */
	public OrchestrationContext processRequest(
			final OrderManagementRequestResponseDocument orderDocument) {

		logger.info("Processing addLineItem request");
		boolean isPricingRequired = SalesContextUtil.INSTANCE.isPricingRequired(orderDocument);

		OrchestrationContext params = createLoadContext(orderDocument);

		OrderChangeValueObject orderChangeValueObject = OrderChangeVOBuilder.INSTANCE.build(orderManagementService, params, orderDocument);
		orderChangeValueObject.setCorrelationId((String) params.get(TaskContextParamEnum.correlationId.name()));

		Boolean isProductExistInRequest = orderManagementService.isProductExist(orderChangeValueObject);
		ResponseItemOme rio = (ResponseItemOme) params;

		String agentId = "";
		if (orderDocument.getOrderManagementRequestResponse().getRequest().getAgentId() != null) {
			
			agentId = orderDocument.getOrderManagementRequestResponse().getRequest().getAgentId();
			params.add(TaskContextParamEnum.agentId.name(), agentId);
		}

		Map<String, String> validationMsgs = Collections.emptyMap();

		SalesOrder salesOrder = (SalesOrder) params.get(TaskContextParamEnum.salesOrder.name());
		// TODO:Uncomment this and the pricing also below
		try {
			if (isProductExistInRequest) {
				logger.info("Preparing for Product service communication");
				ProductEnterpriseRequestDocument productRequest = orderManagementService.prepareProductServiceRequest(orderChangeValueObject);
				ProductEnterpriseResponseDocument productResponse = OmeProductCommunicator.getProducts(productRequest.toString());
				Boolean hasErrors = orderManagementService.validateProductResponseForFatalError(productResponse);
				if (hasErrors) {
					rio.addInvalidProduct("Product Service failed with FATAL error!!!");
					return params;
				}
				validationMsgs = orderManagementService
						.copyProductUniqueIdFromProductToOrder(productResponse,
								orderDocument);
			}
		} catch (Exception e) {
			logger.warn("error communicating with the product service:"
					+ salesOrder.getExternalId());
		}

		// Populate PromotionLineitem unique id with unique id of Product where it applies to
		// So that later on during unmarshalling Promotion details can be retrieved
		// based on Product unique id
		orderManagementService.copyPromotionUniqueId(orderChangeValueObject,
				salesOrder);

		if (!validationMsgs.isEmpty()) {
			rio.addInvalidLineItems(validationMsgs);
			return params;
		}

		LineItemManagementStrategy.updateLineItemList(orderChangeValueObject,
				unmarshallLineItem);
		List<LineItem> updatedList = orderChangeValueObject.getLiList();

		salesOrder.setLineItems(updatedList);
		orderChangeValueObject.clear();

		params.add(TaskContextParamEnum.salesOrder.name(), salesOrder);
		params.add(TaskContextParamEnum.request.name(), orderDocument.getOrderManagementRequestResponse().getRequest());
		params.add(TaskContextParamEnum.isPricingRequired.name(), isPricingRequired);
		params.add(TaskContextParamEnum.source.name(),	ActivitySalesContextEval.INSTANCE.getSource(orderDocument));

		ValidateAddLineItem.INSTANCE.validate(params);
		return params;
	}

	public SalesOrder pricingUpdate(final OrchestrationContext params,
			SalesOrder salesOrder, final OrderType orderType) {

		ResponseItemOme rio = (ResponseItemOme) params;
		params.add(TaskContextParamEnum.orderExternalId.name(),	salesOrder.getExternalId());
		PricingRequestResponseDocument pricingRequest = OrderPriceUtil.copyOrderToPricing(orderType, rio);

		logger.info("send request to pricing service to calculate price on each lineitem");
		PricingRequestResponseDocument newPricingResponse = OmePricingMessageSender.getPricedOrder(pricingRequest.toString());

		boolean fatalError = PricingValidationHelper.isFatalErrorExist(newPricingResponse);

		if (fatalError) {

			logger.error("Pricing service response contains fatal/error message!!!");
			PricingValidationHelper.populateStatusMsg(newPricingResponse, params.getValidationReport());
		} 
		else {
			// Check for warning messages in Pricing response
			boolean warningError = PricingValidationHelper.isWarningErrorExist(newPricingResponse);

			// If warning message is present then update the status
			if (warningError) {
				rio.add(TaskContextParamEnum.pricingResponse.name(), newPricingResponse);
			}

			logger.debug("Update the lineitem price once it is processed by Pricing");
			if (newPricingResponse != null) {
				logger.debug("recieved from pricing:"
						+ newPricingResponse.toString());
				salesOrder = OrderPriceUtil.updateLineItemPrice(salesOrder,
						newPricingResponse);
			}
		}

		return salesOrder;

	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unused")
	@Override
	public OrchestrationContext processAgenda(final OrchestrationContext params) {
		SalesOrder salesOrder = (SalesOrder) params
				.get(TaskContextParamEnum.salesOrder.name());

		Object isPricingRequired = params.get(TaskContextParamEnum.isPricingRequired.name());
		boolean isPricingReq = ((isPricingRequired != null) && ((Boolean) isPricingRequired));
		logger.info("PricingRequired="+isPricingReq);
		
		OrderType orderType = marshallService.buildOrderType(salesOrder);

		try {
			if (isPricingReq) {
				salesOrder = pricingUpdate(params, salesOrder, orderType);
			}
		} catch (Exception e) {
			logger.warn("unable to calculate pricing update for addline item:"
					+ salesOrder.getExternalId());
		}

		List<LineItem> newLineItemList = new ArrayList<LineItem>();

		// new lineitem placed into list
		for (LineItem li : salesOrder.getLineItems()) {
			if (li.getExternalId() == 0) {
				newLineItemList.add(li);
			}
		}

		String agentId = (String) params.get(TaskContextParamEnum.agentId.name());
		orderManagementService.saveNewLineItems(agentId, salesOrder);

		String source = (String) params.get(TaskContextParamEnum.source.name());
		if (source == null || source.trim().equals("")) {
			source = salesOrder.getSource();
		}

		for (LineItem li : newLineItemList) {
			if (li != null) {
				omeCustomerService.addConsumerInteraction(agentId, salesOrder,
						source, li, "lineitem updated:" + li.getExternalId());
			}
		}

		params.add("DONT_SAVE_SALES_ORDER", new Boolean(true)); // Add this new flag to prevent the sales order object to persist again
		activityPersistSalesContext.process(params);
		orderManagementService.updateSalesOrderStatus(salesOrder); // New method to only save the status and status history

		// Adding consumer interaction about customer creation
		omeCustomerService.addConsumerInteraction(agentId, salesOrder, source,
				"new lineitem(s) added for an order");

		Request request = (Request) params.get(TaskContextParamEnum.request
				.name());
		boolean includeAccountHolders = false;
		if(request != null) {
			includeAccountHolders = request.getIncludeAccountHolders();
		} 
		
		salesOrder = orderManagementService.findOrderById(salesOrder.getExternalId(), includeAccountHolders);
		params.add(TaskContextParamEnum.salesOrder.name(), salesOrder);

		if (salesOrder != null) {
			logger.info("total lines item:" + salesOrder.getLineItems().size());

			AddProviderIdToContextForBroadcast.INSTANCE.execute(params,
					salesOrder);

			addConsumerToContext(params, salesOrder);
		}
		return params;
	}

	private SalesOrderContext generateSOC(String newLIExtIds) {
		// Generate new sales order context for newLIExtIds
		SalesOrderContext soc = new SalesOrderContext();
		soc.setEntityName(CONCERT);
		soc.setName(NEW_LI_EXT_I_DS);
		soc.setValue(newLIExtIds);
		return soc;
	}

	public void addConsumerToContext(final OrchestrationContext params,
			SalesOrder salesOrder) {
		logger.info("get customer info from db:"
				+ salesOrder.getConsumerExternalId());
		Consumer customer = omeCustomerService.getCustomer(salesOrder
				.getConsumerExternalId());

		if (customer != null) {
			params.add(TaskContextParamEnum.customer.name(), customer);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public OrderManagementRequestResponseDocument processResponse(
			final OrchestrationContext params) {

		OrderManagementRequestResponseDocument docResponse = responseBuilder
				.buildResponse(params);
		return docResponse;
	}

	/**
	 * {@inheritDoc}
	 */
	public OrderManagementRequestResponseDocument handleFault(
			final Exception e, final OrchestrationContext params,
			final OrderManagementRequestResponseDocument orderDocument) {
		logger.info(e.getMessage());
		return orderDocument;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void broadcast(String strToBroadcastOriginal, Map<String, String> map) {
		logger.info("Processing OME broadcast");
		
		Broadcast brd = broadcastManager.buildOrderBroadcast(strToBroadcastOriginal, map);
		String extId = String.valueOf(broadcastManager.saveBroadcastMessage(brd));
		map.put("broadcast_id", extId);

		BroadcastMessage broadcast = new BroadcastMessage(extId,
				strToBroadcastOriginal, map, TRANS_ADD_LINE_ITEM);
		BroadcastService.sendBroadcast(broadcast, Thread.currentThread()
				.getName());
	}

	public UnmarshallLineItem getUnmarshallLineItem() {
		return unmarshallLineItem;
	}

	public void setUnmarshallLineItem(UnmarshallLineItem unmarshallLineItem) {
		this.unmarshallLineItem = unmarshallLineItem;
	}

	public MarshallLineItem getMarshallLineItem() {
		return marshallLineItem;
	}

	public void setMarshallLineItem(MarshallLineItem marshallLineItem) {
		this.marshallLineItem = marshallLineItem;
	}

	public OrderManagementService getOrderManagementService() {
		return orderManagementService;
	}

	public void setOrderManagementService(
			OrderManagementService orderManagementService) {
		this.orderManagementService = orderManagementService;
	}

	public MarshallService getMarshallService() {
		return marshallService;
	}

	public void setMarshallService(MarshallService marshallService) {
		this.marshallService = marshallService;
	}

	public AuditService<OrderAudit> getAuditService() {
		return auditService;
	}

	public void setAuditService(AuditService<OrderAudit> auditService) {
		this.auditService = auditService;
	}

}
