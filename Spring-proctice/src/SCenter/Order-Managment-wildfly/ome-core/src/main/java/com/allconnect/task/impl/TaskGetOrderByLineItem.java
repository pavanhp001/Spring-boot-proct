package com.AL.task.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.task.LocalTaskGetOrderByLineItem;
import com.AL.task.TaskBase;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.ResponseItemOme;
import com.AL.task.response.ResponseBuilder;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.Vdao.dao.CustomerDao;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.vm.util.converter.unmarshall.UnmarshallOrder;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;
@Component("getOrderByLineItem")
public class TaskGetOrderByLineItem extends TaskBase<OrderManagementRequestResponseDocument> implements
		LocalTaskGetOrderByLineItem<OrderManagementRequestResponseDocument> {

	private static final Logger logger = Logger
			.getLogger(TaskGetOrderByLineItem.class);

	@Autowired
	private UnmarshallOrder unmarshallOrder;

	@Autowired
	private OrderManagementDao orderManagementDao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private ResponseBuilder responseBuilder;

	private static final Long DEFAULT_ORDER_EXTERNAL_ID = -1L;
	private static final Long DEFAULT_LINEITEM_EXTERNAL_ID = -1L;
	private static final String SEARCH_CRITERIA = "WHOLE_ORDER_BY_LINE_ITEM";

	@Override
	public OrchestrationContext processRequest(
			OrderManagementRequestResponseDocument orderDocument) {

		logger.info("Processing getOrderByLineItem request");
		Request request = orderDocument.getOrderManagementRequestResponse()
				.getRequest();

		SalesContextType salesContext = request.getSalesContext();
		String searchBy = "";

		if (salesContext != null) {
			for(SalesContextEntityType entity : salesContext.getEntityList()) {
				if("source".equalsIgnoreCase(entity.getName())) {
					for(NameValuePairType pair : entity.getAttributeList()) {
						if("searchBy".equalsIgnoreCase(pair.getName())) {
							if (pair.getValue() != null && !pair.getValue().equals("")) {
								searchBy = pair.getValue();
							}
						}
					}
				}
			}
		}

		OrchestrationContext params = createLoadContext(orderDocument);
		Long orderExtId = getOrderExternalId(request);
		Long liExtId = getLineItemExtId(request);
		String providerExtId = getProviderExtId(request);
		String criteria = request.getCriteria();

		logger.info("Requested order ext. id [" + orderExtId + "] and lineitem ext. id [" + liExtId +"]");
		try {
			params.add(TaskContextParamEnum.salesOrderId.name(), orderExtId);
			params.add(TaskContextParamEnum.request.name(), request);
			params.add(TaskContextParamEnum.lineItemExtId.name(), liExtId);
			params.add(TaskContextParamEnum.searchBy.name(), searchBy);
			params.add(TaskContextParamEnum.providerExtId.name(), providerExtId );
			params.add(TaskContextParamEnum.criteria.name(), criteria );
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception thrown while processing GetOrderByLineItem processRequest : " , e);
			return OrchestrationContext.Factory.createOme(e);
		}
		return params;
	}

	@Override
	public OrchestrationContext processAgenda(final OrchestrationContext params) {
		Long salesOrderId = (Long) params.get(TaskContextParamEnum.salesOrderId
				.name());
		Long liExtId =  (Long) params.get(TaskContextParamEnum.lineItemExtId.name());
		String searchBy = (String) params.get(TaskContextParamEnum.searchBy.name());
		
		String criteria = (String) params.get(TaskContextParamEnum.criteria.name());
		
		SalesOrder salesOrderBean = null;
		Consumer consumer = null;
		
		Request request = (Request) params.get(TaskContextParamEnum.request
				.name());		
		// ============================= END- ABOVE CODE IS USED FOR ACCORD, NOW ITS absolete ===============================================
		if (searchBy != null && !searchBy.equals("")) {
			if (searchBy.equalsIgnoreCase("SRVC_SLCTN_ID")) {
				salesOrderBean = orderManagementDao.findOrderByServiceSelectionId("SRVC_SLCTN_ID", liExtId, request.getIncludeAccountHolders());
				if (salesOrderBean != null) {
					consumer = customerDao
							.findCustomerByExternalId(salesOrderBean
									.getConsumerExternalId());
					if (consumer != null) {
						params.add(TaskContextParamEnum.customer.name(), consumer);
					}
				params.addSuccessful(salesOrderBean);
				} else {
					((ResponseItemOme) params).addOrderNotFound("Accord Order Number not found for the respective Service Selection Id - " + String.valueOf(liExtId.longValue()));
				}
			} else {
				((ResponseItemOme) params).addSalesOrderNotFound(String
						.valueOf(salesOrderId));
			}
		} // ============================= END- ABOVE CODE IS USED FOR ACCORD, NOW ITS absolete ===============================================
		else 
		{ 
			logger.debug("Criteria  ::: "+criteria);
			if(null != criteria && criteria.equalsIgnoreCase(SEARCH_CRITERIA)){
				//This will get all the lineitems, for Encore search ticket #114887
				salesOrderBean = orderManagementDao.findOrderByLineItemExtId(liExtId, request.getIncludeAccountHolders());
			} else {
				// This block is for normal search
				salesOrderBean = orderManagementDao.findOrderByLineItemExtId(salesOrderId, liExtId, request.getIncludeAccountHolders());
			}

			if (salesOrderBean != null) {
				consumer = customerDao
						.findCustomerByExternalId(salesOrderBean
								.getConsumerExternalId());
				if (consumer != null) {
					params.add(TaskContextParamEnum.customer.name(), consumer);
				}
			params.addSuccessful(salesOrderBean);
			} else {
				((ResponseItemOme) params).addSalesOrderNotFound(String
					.valueOf(salesOrderId));
			}
		}
		
		return params;
	}

	@Override
	public OrderManagementRequestResponseDocument processResponse(
			OrchestrationContext params) {
		return responseBuilder.buildResponse(params);
	}

	@Override
	public OrderManagementRequestResponseDocument handleFault(Exception e,
			OrchestrationContext taskResponse,
			OrderManagementRequestResponseDocument orderDocument) {

		logger.debug(e);
		return orderDocument;
	}

	@Override
	public void broadcast(String strToBroadcastOriginal,
			Map<String, String> header) {
		//Gets api do not need to broadcast
	}

	/**
	 * Retrieves order external id from request document
	 * @param request
	 * @return
	 */
	private Long getOrderExternalId(Request request) {
		String orderId = unmarshallOrder.buildOrderId(request);
		Long orderExtId = DEFAULT_ORDER_EXTERNAL_ID;
		if (orderId != null && orderId.trim().length() > 0) {
			orderExtId = Long.valueOf(orderId);
		}

		return orderExtId;
	}

	/**
	 * Retrieves lineitem external id from request document
	 * @param request
	 * @return
	 */
	private Long getLineItemExtId(Request request) {
		String sLineItemExtId = unmarshallOrder.buildLineItemExternalId(request);
		Long liExtId = DEFAULT_LINEITEM_EXTERNAL_ID;
		if (sLineItemExtId != null && sLineItemExtId.trim().length() > 0) {
			liExtId = Long.valueOf(sLineItemExtId);
		}else{
			throw new IllegalArgumentException("LineItemId cannot be null. Pass correct lineitem external id.");
		}

		return liExtId;
	}

	/**
	 * Retrieves provider external id from request document
	 * @param request
	 * @return
	 */
	private String getProviderExtId(Request request) {
		String sProviderExtId = unmarshallOrder.buildProviderExternalId(request);

		if (sProviderExtId != null && sProviderExtId.trim().length() > 0) {
			//
		}

		return sProviderExtId;
	}
}
