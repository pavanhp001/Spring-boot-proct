package com.AL.activity.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.activity.Activity;
import com.AL.ome.OmePricingMessageSender;
import com.AL.pricing.DummyPricingEngine;
import com.AL.pricing.OmePricingService;
import com.AL.pricing.OrderPriceUtil;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.validation.impl.PricingValidationHelper;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.vm.service.OrderManagementService;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.PricingRequestResponseDocument;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;

@Component("activityCalculateOrderPrice")
public class ActivityCalculateOrderPrice implements Activity {

	private static final boolean PERFORM_PRICING = true;
	private Logger logger = Logger.getLogger(ActivityCalculateOrderPrice.class);

	@Autowired
	private OrderManagementService omeService;

	@Autowired
	private MarshallOrder<SalesOrder> marshallOrder;
	
	@Autowired
	private OmePricingService omePricingService;


	public void process(OrchestrationContext params) {
		SalesOrder salesOrder = (SalesOrder) params
				.get(TaskContextParamEnum.salesOrder.name());
		if (salesOrder == null) {
			return;
		}
		OrderManagementRequestResponseDocument orderDocument = createDocumentWithOrder(
				params, salesOrder);
		PricingRequestResponseDocument pricingRequest = omeService
				.prepareOrderForPricing(orderDocument);
		//set salescontext
		SalesContextType scType = (SalesContextType) params.get(TaskContextParamEnum.salesContext.name());
		pricingRequest.getPricingRequestResponse().setSalesContext(scType);
		// If all lineitems are valid then only process the request further
		logger.debug("send request to pricing service to calculate price on each lineitem");
		PricingRequestResponseDocument pricingResponse = null;
		try {
			pricingResponse = omePricingService.processPricingRequest(pricingRequest);
		} catch (Exception e) {
			logger.warn("unable to access pricing service:" + e.getMessage());
		}
		boolean fatalError = PricingValidationHelper.isFatalErrorExist(pricingResponse);
		if (!fatalError) {
			logger.debug("Update the lineitem price once it is processed by Pricing");
			if (pricingResponse != null && salesOrder != null) {
				logger.debug("recieved from pricing:"
						+ pricingResponse.toString());
				salesOrder = OrderPriceUtil.updateLineItemPrice(salesOrder,
						pricingResponse);
			}
		} else {
			logger.error("Fatal error in Pricing");
			PricingValidationHelper.populateStatusMsg(pricingResponse,
					params.getValidationReport());
		}
		params.add(TaskContextParamEnum.salesOrder.name(), salesOrder);
	}
	
	public void processStatusUpdate(OrchestrationContext params) {
		SalesOrder salesOrder = (SalesOrder) params
				.get(TaskContextParamEnum.salesOrder.name());
		if (salesOrder == null) {
			return;
		}
		OrderManagementRequestResponseDocument orderDocument = createDocumentWithOrder(
				params, salesOrder);
		PricingRequestResponseDocument pricingRequest = omeService
				.prepareOrderForPricing(orderDocument);
		//set salescontext
		SalesContextType scType = (SalesContextType) params.get(TaskContextParamEnum.salesContext.name());
		pricingRequest.getPricingRequestResponse().setSalesContext(scType);
		// If all lineitems are valid then only process the request further
		logger.debug("send request to pricing service to calculate price on each lineitem");
		PricingRequestResponseDocument pricingResponse = null;
		try {
			pricingResponse = OmePricingMessageSender
					.getPricedOrder(pricingRequest.toString());
		} catch (Exception e) {
			logger.warn("unable to access pricing service:" + e.getMessage());
		}
		boolean fatalError = PricingValidationHelper
				.isFatalErrorExist(pricingResponse);
		if (!fatalError) {
			logger.debug("Update the lineitem price once it is processed by Pricing");
			if (pricingResponse != null && salesOrder != null) {
				logger.debug("recieved from pricing:"
						+ pricingResponse.toString());
				salesOrder = OrderPriceUtil.updateLineItemPrice(salesOrder,
						pricingResponse);
			}
		} else {
			logger.error("Fatal error in Pricing");
			PricingValidationHelper.populateStatusMsg(pricingResponse,
					params.getValidationReport());
		}
		params.add(TaskContextParamEnum.salesOrder.name(), salesOrder);
	}

	
	public OrderManagementRequestResponseDocument createDocumentWithOrder(
			OrchestrationContext params, SalesOrder salesOrder) {
		OrderManagementRequestResponseDocument orderDocument = OrderManagementRequestResponseDocument.Factory
				.newInstance();
		OrderManagementRequestResponse rr = orderDocument
				.addNewOrderManagementRequestResponse();

		rr.setRegion(params.getRegion());
		rr.setSalesCode(params.getSalesCode());
		rr.setAffiliateName(params.getAffiliateName());
		rr.setTransactionId(params.getTransactionId());
		rr.setCorrelationId(params.getCorrelationId());
	 
		Request r = rr.addNewRequest();
		 
		OrderType ot = r.addNewOrderInfo();

		marshallOrder.build(salesOrder, ot);
		String GUID = ActivitySalesContextEvaluate.INSTANCE.getGUID(salesOrder);
		
		//If Marshaller has not already set correlation Id
		if ((orderDocument.getOrderManagementRequestResponse().getCorrelationId() == null) || (orderDocument.getOrderManagementRequestResponse().getCorrelationId().length() ==0)) {
			
			if ((GUID != null)  && (GUID.length() > 0)) {
				orderDocument.getOrderManagementRequestResponse().setCorrelationId(GUID);
			} else {
				orderDocument.getOrderManagementRequestResponse().setCorrelationId(System.nanoTime()+"");
			}
		}
 

		return orderDocument;
	}
}
