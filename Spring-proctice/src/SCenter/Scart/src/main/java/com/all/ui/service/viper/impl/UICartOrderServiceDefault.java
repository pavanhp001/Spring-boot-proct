package com.AL.ui.service.V.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.AL.ui.service.V.UICartOrderService;
import com.AL.ui.transport.TransportConfig;
import com.AL.ui.vo.CartError;
import com.AL.ui.vo.ErrorList;
import com.AL.util.DateUtil;
import com.AL.xml.v4.CustomSelectionsType;
import com.AL.xml.v4.Customer;
import com.AL.xml.v4.CustomerInformation;
import com.AL.xml.v4.CustomerType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.LineItemStatusType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderStatusWithTypeType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.SchedulingInfoType;
import com.AL.xml.v4.SelectedDialogsType;
import com.AL.xml.v4.SelectedFeaturesType;

@Component
public class UICartOrderServiceDefault implements UICartOrderService {

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#get(java.lang.String, java.lang.String, java.util.Map, java.lang.String, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType get(final String agentId, final String externalId, Map<String, Object> roles, final String ACTIVE_PROVIDER, final List<String> credentials,
			ErrorList errors) {
		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, "getOrder");

		if (externalId != null) {
			omrr.getRequest().setOrderId(externalId);
		}
		
		if (agentId != null) {
			omrr.getRequest().setAgentId(agentId);
		}

		OrderManagementRequestResponse omRR = TransportConfig.INSTANCE.getOrderClient().send(omrr);
		logOMRRResponse(omRR);

		OrderType orderType = null;

		if(omRR ==  null || omRR.getResponse() == null){
			errors.add(new CartError("", "empty.omrr", "Null OrderManagementRequestResponse"));
		} else {
			List<OrderType> orderTypeList = omRR.getResponse().getOrderInfo();
			if (orderTypeList != null && orderTypeList.size() > 0) {
				if (orderTypeList.size() > 0) {
					orderType = orderTypeList.get(0);
				}
			} else {
				errors.add(new CartError("", "empty.order", "Order doesn't exist"));
			}
			if(orderType != null && orderType.getExternalId() != 0){
				OrderCacheService.INSTANCE.store(orderType, orderType.getExternalId() + "");
			}
		}

		if (orderType != null) {
			//AuthorizationFilter.INSTANCE.removeDeleted(orderType);
			//AuthorizationFilter.INSTANCE.filterShowAuthorizedView(orderType, roles,	ACTIVE_PROVIDER);

			// Sorting Status History
			Collections.sort(orderType.getOrderStatusHistory().getPreviousStatus(),
					new Comparator<OrderStatusWithTypeType>() {
				public int compare(OrderStatusWithTypeType o1,
						OrderStatusWithTypeType o2) {
					return o2.getDateTimeStamp().compare(
							o1.getDateTimeStamp());
				}
			});
		}

		return orderType;
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#createOrderAndCustomer(java.lang.String, com.AL.xml.v4.SalesContextType, com.AL.xml.v4.OrderType, com.AL.xml.v4.Customer, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType createOrderAndCustomer(final String agentId, final SalesContextType salesContextType, OrderType order, final Customer customer, List<String> credentials, ErrorList errors){

		ObjectFactory oFactoryOM = new ObjectFactory();

		CustomerInformation customerInformation = oFactoryOM.createCustomerInformation();
		customerInformation.setAction("referenceCustomer");
		customer.setExternalId(customer.getExternalId());
		customerInformation.setCustomer(customer);
		order.setCustomerInformation(customerInformation);

		OrderType orderType = createOrder(oFactoryOM, agentId, salesContextType, order, credentials, errors);

		return orderType;
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#createOrderAndCustomer(java.lang.String, com.AL.xml.v4.OrderType, com.AL.xml.v4.Customer, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType createOrderAndCustomer(final String agentId, OrderType order, final Customer customer, List<String> credentials, ErrorList errors){

		ObjectFactory oFactoryOM = new ObjectFactory();

		SalesContextType salesContextType = getDefaultSalesContextType(oFactoryOM);

		CustomerInformation customerInformation = oFactoryOM.createCustomerInformation();
		customerInformation.setAction("referenceCustomer");
		customer.setExternalId(customer.getExternalId());
		customerInformation.setCustomer(customer);
		order.setCustomerInformation(customerInformation);

		OrderType orderType = createOrder(oFactoryOM, agentId, salesContextType, order, credentials, errors);

		return orderType;
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#createOrderExistingCustomer(java.lang.String, com.AL.xml.v4.SalesContextType, com.AL.xml.v4.OrderType, com.AL.xml.v4.Customer, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType createOrderExistingCustomer(final String agentId, SalesContextType salesContextType, OrderType order, final Customer customer, List<String> credentials, ErrorList errors) {

		ObjectFactory oFactory = new ObjectFactory();
		CustomerInformation customerInformation = oFactory.createCustomerInformation();
		customerInformation.setAction("referenceCustomer");
		customer.setExternalId(customer.getExternalId());
		customerInformation.setCustomer(customer);
		order.setCustomerInformation(customerInformation);

		OrderType orderType = createOrder(oFactory, agentId, salesContextType, order, credentials, errors);

		return orderType;
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#addLineItem(java.lang.String, java.lang.String, com.AL.xml.v4.LineItemType, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType addLineItem(final String agentId, String orderExtId, LineItemType lineitem,
			List<String> credentials, ErrorList errors) {

		List<LineItemType> lineitems = new ArrayList<LineItemType>();
		lineitems.add(lineitem);

		OrderType order = addLineItem(agentId, orderExtId, lineitems, credentials, errors);
		return order;
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#addLineItem(java.lang.String, java.lang.String, java.util.List, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType addLineItem(final String agentId, String orderExtId, final List<LineItemType> lineitems, List<String> credentials, ErrorList errors) {
		OrderCacheService.INSTANCE.clear(orderExtId);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, "addLineItem");

//		SalesContextType salesContextType = new SalesContextType();
//
//		SalesContextEntityType salesContextEntityType = new SalesContextEntityType();
//		salesContextEntityType.setName("source");
//
//		NameValuePairType nameValuePairType = new NameValuePairType();
//		nameValuePairType.setName("source");
//		nameValuePairType.setValue("SYP");
//		salesContextEntityType.getAttribute().add(nameValuePairType);
//		salesContextType.getEntity().add(salesContextEntityType);
//
//		omrr.getRequest().setSalesContext(salesContextType);

		if (orderExtId != null) {
			omrr.getRequest().setOrderId(orderExtId);
		}
		omrr.getRequest().setAgentId(agentId);
		omrr.getRequest().setAfterLineItemNumber(9999);
		omrr.getRequest().setIsAppliesToLineItemIncluded(true);

		LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
		for(LineItemType li: lineitems){
			liCollection.getLineItem().add(li);
		}

		omrr.getRequest().setNewLineItems(liCollection);

		OrderManagementRequestResponse omRR = TransportConfig.INSTANCE.getOrderClient().send(omrr);
		logOMRRResponse(omRR);

		OrderType orderType = null;

		if(omRR ==  null || omRR.getResponse() == null){
			errors.add(new CartError("", "empty.omrr", "Null OrderManagementRequestResponse"));
		} else {
			List<OrderType> orderTypeList = omRR.getResponse().getOrderInfo();
			if (orderTypeList != null && orderTypeList.size() > 0) {
				if (orderTypeList.size() > 0) {
					orderType = orderTypeList.get(0);
				}
			} else {
				errors.add(new CartError("", "empty.order", "Order doesn't exist"));
			}
			if(orderType != null && orderType.getExternalId() != 0){
				OrderCacheService.INSTANCE.store(orderType, orderType.getExternalId() + "");
			}else{
				orderType = null;
			}
		}

		return orderType;
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#updateLineItemStatus(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType updateLineItemStatus(final String agentId, final String orderId, final String lineItemId, final String status, final List<Integer> reasons, List<String> credentials, ErrorList errors) {

		List<String> lineItemIdList = new ArrayList<String>();
		lineItemIdList.add(lineItemId);

		OrderType order = updateLineItemStatus(agentId, orderId, lineItemIdList, status, reasons, credentials, errors);

		return order;
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#updateLineItemStatus(java.lang.String, java.lang.String, java.util.List, java.lang.String, java.util.List, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType updateLineItemStatus(final String agentId, final String orderId, final List<String> lineItemIdList, final String status, final List<Integer> reasons,
			List<String> credentials, ErrorList errors) {

		OrderCacheService.INSTANCE.clear(orderId);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, "updateLineItemStatus");

		omrr.getRequest().setOrderId(orderId);
		for(String lineItemId : lineItemIdList){
			omrr.getRequest().getLineitemId().add(lineItemId);
		}
		omrr.getRequest().setAgentId(agentId);

		LineItemStatusType liStatusType = build(agentId, status, status, reasons);

		omrr.getRequest().setNewLineItemStatus(liStatusType);

		OrderManagementRequestResponse omRR = TransportConfig.INSTANCE.getOrderClient().send(omrr);
		logOMRRResponse(omRR);

		OrderType orderType = null;

		if(omRR ==  null || omRR.getResponse() == null){
			errors.add(new CartError("", "empty.omrr", "Null OrderManagementRequestResponse"));
		} else {
			List<OrderType> orderTypeList = omRR.getResponse().getOrderInfo();
			if (orderTypeList != null && orderTypeList.size() > 0) {
				if (orderTypeList.size() > 0) {
					orderType = orderTypeList.get(0);
				}
			} else {
				errors.add(new CartError("", "empty.order", "Order doesn't exist"));
			}
			
			if(orderType != null && orderType.getExternalId() != 0){
				OrderCacheService.INSTANCE.store(orderType, orderType.getExternalId() + "");
			}else{
				orderType = null;	
			}
		}

		return orderType;
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#submit(java.lang.String, java.lang.String, java.lang.String, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType submit(final String agentId, String orderId, String lineItemId, List<String> credentials, ErrorList errors) {

		List<String> lineItemIdList = new ArrayList<String>();
		lineItemIdList.add(lineItemId);

		OrderType order = submit(agentId, orderId, lineItemIdList, credentials, errors);

		return order;
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#submit(java.lang.String, java.lang.String, java.util.List, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType submit(final String agentId, String orderId, final List<String> lineItemIdList, List<String> credentials, ErrorList errors) {

		OrderCacheService.INSTANCE.clear(orderId);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = createOMRR(oFactory, "submit");

		omrr.getRequest().setOrderId(orderId);
		for(String lineItemId : lineItemIdList){
			omrr.getRequest().getLineitemId().add(lineItemId);
		}

		omrr.getRequest().setAgentId(agentId);

		OrderManagementRequestResponse omRR = TransportConfig.INSTANCE.getOrderClient().send(omrr);
		logOMRRResponse(omRR);

		OrderType orderType = null;

		if(omRR ==  null || omRR.getResponse() == null){
			errors.add(new CartError("", "empty.omrr", "Null OrderManagementRequestResponse"));
		} else {
			List<OrderType> orderTypeList = omRR.getResponse().getOrderInfo();
			if (orderTypeList != null && orderTypeList.size() > 0) {
				if (orderTypeList.size() > 0) {
					orderType = orderTypeList.get(0);
				}
			} else {
				errors.add(new CartError("", "empty.order", "Order doesn't exist"));
			}
			if(orderType != null && orderType.getExternalId() != 0){
				OrderCacheService.INSTANCE.store(orderType, orderId);
			}
		}

		return orderType;
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#removeLineItem(java.lang.String, java.lang.String, java.lang.String, java.util.List, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType removeLineItem(final String agentId, final String orderExtId, final String lineItemId, final List<Integer> reasons,
			List<String> credentials, ErrorList errors) {
		return updateLineItemStatus(agentId, orderExtId, lineItemId, "cancelled_removed", reasons, credentials, errors);
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#removeLineItem(java.lang.String, java.lang.String, java.util.List, java.util.List, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType removeLineItem(final String agentId, String orderExtId, List<String> lineItemIdList, final List<Integer> reasons,
			List<String> credentials, ErrorList errors) {
		return updateLineItemStatus(agentId, orderExtId, lineItemIdList, "cancelled_removed", reasons, credentials, errors);
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#deleteLineItem(java.lang.String, java.lang.String, java.lang.String, java.util.List, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType deleteLineItem(final String agentId, final String orderExtId, final String lineItemId, final List<Integer> reasons,
			List<String> credentials, ErrorList errors) {
		return updateLineItemStatus(agentId, orderExtId, lineItemId, "cancelled_removed", reasons, credentials, errors);
	}

	public OrderType deleteLineItem(final String agentId, String orderExtId, List<String> lineItemIdList, final List<Integer> reasons,
			List<String> credentials, ErrorList errors) {
		return updateLineItemStatus(agentId, orderExtId, lineItemIdList, "cancelled_removed", reasons, credentials, errors);
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#save(java.lang.String, java.lang.String, com.AL.xml.v4.OrderType)
	 */
	public OrderType save(String agentId, String lineitemExtId, OrderType order) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#saveSchedulingInfo(java.lang.String, long, java.lang.String, com.AL.xml.v4.SchedulingInfoType, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType saveSchedulingInfo(String agentId, long lineitemExtId,
			final String orderId,SchedulingInfoType schedInfo, List<String> credentials,
			ErrorList errors) {

		ObjectFactory oFactory = new ObjectFactory();
		LineItemType lit = oFactory.createLineItemType();

		lit.setExternalId(lineitemExtId);
		lit.setSchedulingInfo(schedInfo);
		return updateLineItem(agentId, orderId, lineitemExtId, "updateLineItem",
				lit, credentials, errors);
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#saveSelectedFeatures(java.lang.String, long, java.lang.String, com.AL.xml.v4.SelectedFeaturesType, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType saveSelectedFeatures(String agentId, long lineitemExtId,
			final String orderId,SelectedFeaturesType features, List<String> credentials,
			ErrorList errors) {
		ObjectFactory oFactory = new ObjectFactory();
		LineItemType lit = oFactory.createLineItemType();

		lit.setExternalId(lineitemExtId);
		lit.setSelectedFeatures(features);
		return updateLineItem(agentId, orderId, lineitemExtId, "updateLineItem",
				lit, credentials, errors);
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#saveSelectedDialogs(java.lang.String, long, java.lang.String, com.AL.xml.v4.SelectedDialogsType, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType saveSelectedDialogs(String agentId, long lineitemExtId,
			final String orderId,SelectedDialogsType selections, List<String> credentials,
			ErrorList errors) {
		ObjectFactory oFactory = new ObjectFactory();
		LineItemType lit = oFactory.createLineItemType();

		lit.setExternalId(lineitemExtId);
		lit.setActiveDialogs(selections);
		return updateLineItem(agentId, orderId, lineitemExtId, "updateLineItem",
				lit, credentials, errors);
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#saveCustomSelections(java.lang.String, long, java.lang.String, com.AL.xml.v4.CustomSelectionsType, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType saveCustomSelections(String agentId, long lineitemExtId,
			final String orderId,CustomSelectionsType selections, List<String> credentials,
			ErrorList errors) {
		ObjectFactory oFactory = new ObjectFactory();
		LineItemType lit = oFactory.createLineItemType();

		lit.setExternalId(lineitemExtId);
		lit.setCustomSelections(selections);
		return updateLineItem(agentId, orderId, lineitemExtId, "updateLineItem",
				lit, credentials, errors);
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#saveCustomer(java.lang.String, java.lang.String, com.AL.xml.v4.CustomerType, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType saveCustomer(String agentId, String lineitemExtId,
			CustomerType customerType, List<String> credentials,
			ErrorList errors) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#saveSchedulingInfo(java.lang.String, java.lang.String, java.util.List, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType saveSchedulingInfo(String agentId, String lineitemExtId,
			List<SchedulingInfoType> schedInfo, List<String> credentials,
			ErrorList errors) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#saveSelectedFeatures(java.lang.String, java.lang.String, java.util.List, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType saveSelectedFeatures(String agentId, String lineitemExtId,
			List<SelectedFeaturesType> features, List<String> credentials,
			ErrorList errors) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#saveSelectedDialogs(java.lang.String, java.lang.String, java.util.List, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType saveSelectedDialogs(String agentId, String lineitemExtId,
			List<SelectedDialogsType> selections, List<String> credentials,
			ErrorList errors) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#saveCustomSelections(java.lang.String, java.lang.String, java.util.List, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType saveCustomSelections(String agentId, String lineitemExtId,
			List<CustomSelectionsType> selections, List<String> credentials,
			ErrorList errors) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#saveCustomer(java.lang.String, java.lang.String, java.util.List, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType saveCustomer(String agentId, String lineitemExtId,
			List<CustomerType> customerType, List<String> credentials,
			ErrorList errors) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#CKO(java.lang.String, java.lang.String, java.util.List, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType CKO(String agentId, String orderId,
			List<String> lineitemExtId, List<String> credentials,
			ErrorList errors) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#updateLineItem(java.lang.String, java.lang.String, long, java.lang.String, com.AL.xml.v4.LineItemType, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType updateLineItem(final String agentId, final String orderId,
			final long lineItemId, final String transactionType,
			final LineItemType lit, List<String> credentials, ErrorList errors) {

		ObjectFactory oFactory = new ObjectFactory();
		
		lit.setExternalId(lineItemId);
		LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
		liCollection.getLineItem().add(lit);

		return updateLineItem(agentId, orderId, liCollection, credentials, errors);
	}
	
	/* (non-Javadoc)
	 * @see com.AL.ui.service.V.UICartOrderService#updateLineItem(java.lang.String, java.lang.String, com.AL.xml.v4.LineItemCollectionType, java.util.List, com.AL.ui.vo.ErrorList)
	 */
	public OrderType updateLineItem(final String agentId, final String orderId,
			final LineItemCollectionType liCollection, List<String> credentials, ErrorList errors) {

		OrderCacheService.INSTANCE.clear(orderId);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = oFactory
		.createOrderManagementRequestResponse();
		omrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		omrr.setCorrelationId(UUID.randomUUID().toString());
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.setTransactionType("updateLineItem");

		if (orderId != null) {
			omrr.getRequest().setOrderId(orderId);
		}

		omrr.getRequest().setAgentId(agentId);
		omrr.getRequest().setUpdatedLineItemInfo(liCollection);

		OrderManagementRequestResponse omrR = TransportConfig.INSTANCE
		.getOrderClient().send(omrr);

		OrderType orderR = null;

		if(omrR ==  null || omrR.getResponse() == null){
			errors.add(new CartError("", "empty.omrr", "Null OrderManagementRequestResponse"));
		} else {

			List<OrderType> orderTypeList = omrR.getResponse().getOrderInfo();

			if (orderTypeList != null && orderTypeList.size() > 0) {
				orderR = orderTypeList.get(0);
			}else {
				errors.add(new CartError("", "empty.order", "Order doesn't exist"));
			}

		}
		if(orderR != null && orderR.getExternalId() != 0){
			OrderCacheService.INSTANCE.store(orderR, orderId);
		}

		return orderR;

	}

	/**
	 * @param agentId
	 * @param statusCode
	 * @param description
	 * @param reasons
	 * @return
	 */
	public LineItemStatusType build(final String agentId, final String statusCode,
			final String description, final List<Integer> reasons) {
		ObjectFactory oFactory = new ObjectFactory();
		LineItemStatusType liStatusType = oFactory.createLineItemStatusType();
		liStatusType.setAgentId(agentId);
		liStatusType.setStatusCodeDescription(description);
		liStatusType.setStatusCode(LineItemStatusCodesType
				.fromValue(statusCode));
		liStatusType.setDateTimeStamp(DateUtil.getCurrentXMLDate());

		for (Integer reason : reasons) {
			liStatusType.getReason().add(reason);
		}

		return liStatusType;
	}

	/**
	 * @param omRR
	 */
	public void logOMRRResponse(OrderManagementRequestResponse omRR){
		//JaxbUtil<OrderManagementRequestResponse> utilOrder = new JaxbUtil<OrderManagementRequestResponse>();
	}

	/**
	 * @param oFactory
	 * @param transactionType
	 * @return
	 */
	public OrderManagementRequestResponse createOMRR(final ObjectFactory oFactory, final String transactionType) {

		OrderManagementRequestResponse cmrr = oFactory.createOrderManagementRequestResponse();
		cmrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType(transactionType);

		return cmrr;

	}

	/**
	 * @param oFactoryOM
	 * @param agentId
	 * @param salesContextType
	 * @param order
	 * @param credentials
	 * @param errors
	 * @return
	 */
	public OrderType createOrder(ObjectFactory oFactoryOM, final String agentId, SalesContextType salesContextType, OrderType order, List<String> credentials, ErrorList errors) {

		OrderManagementRequestResponse omrr = createOMRR(oFactoryOM, "createOrder");

		omrr.getRequest().setAgentId(agentId);
		omrr.getRequest().setOrderInfo(order);
		omrr.getRequest().setSalesContext(salesContextType);

		OrderManagementRequestResponse omRR = TransportConfig.INSTANCE.getOrderClient().send(omrr);
		logOMRRResponse(omRR);

		OrderType orderType = null;

		if(omRR ==  null || omRR.getResponse() == null){
			errors.add(new CartError("", "empty.omrr", "Null OrderManagementRequestResponse"));
		} else {
			List<OrderType> orderTypeList = omRR.getResponse().getOrderInfo();
			if (orderTypeList != null && orderTypeList.size() > 0) {
				if (orderTypeList.size() > 0) {
					orderType = orderTypeList.get(0);
				}
			} else {
				errors.add(new CartError("", "empty.order", "Order doesn't exist"));
			}
			if(orderType != null && orderType.getExternalId() != 0){
				OrderCacheService.INSTANCE.store(orderType, orderType.getExternalId() + "");
			}
		}

		return orderType;
	}

	/**
	 * @param oFactoryOM
	 * @return SalesContextType
	 */
	private SalesContextType getDefaultSalesContextType(ObjectFactory oFactoryOM){

		SalesContextType salesContextType = oFactoryOM.createSalesContextType();

		SalesContextEntityType salesContextEntityType = oFactoryOM.createSalesContextEntityType();
		salesContextEntityType.setName("source");

		NameValuePairType nameValuePairType = oFactoryOM.createNameValuePairType();
		nameValuePairType.setName("source");
		nameValuePairType.setValue("accord");		
		salesContextEntityType.getAttribute().add(nameValuePairType);
		salesContextType.getEntity().add(salesContextEntityType);

		return salesContextType;
	}
	
}
