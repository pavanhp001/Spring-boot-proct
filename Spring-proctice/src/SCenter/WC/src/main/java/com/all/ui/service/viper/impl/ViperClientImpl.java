package com.A.ui.service.V.impl;

import java.util.*;

import org.springframework.stereotype.Component;

import com.A.ui.service.V.VClient;
import com.A.ui.transport.TransportConfig;
import com.A.ui.vo.CartError;
import com.A.ui.vo.ErrorList;
import com.A.ui.vo.ProductDetailVo;
import com.A.V.service.auth.AuthorizationFilter;
import com.A.xml.cm.v4.CustAddress;
import com.A.xml.cm.v4.CustomerManagementRequestResponse;
import com.A.xml.cm.v4.CustomerType;
import com.A.xml.v4.Customer;
import com.A.xml.v4.CustomerInformation;
import com.A.xml.v4.LineItemCollectionType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.NameValuePairType;
import com.A.xml.v4.ObjectFactory;
import com.A.xml.v4.OrderManagementRequestResponse;
import com.A.xml.v4.OrderStatusWithTypeType;
import com.A.xml.v4.OrderType;
import com.A.xml.v4.SalesContextEntityType;
import com.A.xml.v4.SalesContextType;

@Component
public class VClientImpl implements VClient {

	private static final Map<String, ProductDetailVo> map = new HashMap<String, ProductDetailVo>();

	public CustomerType get(final String agentId, final String id,
			List<String> credentials, ErrorList errors) {

		com.A.xml.cm.v4.ObjectFactory oFactoryCM = new com.A.xml.cm.v4.ObjectFactory();

		CustomerType ct = CustomerCacheService.INSTANCE.get(id);
		if (ct != null) {
			return ct;
		}

		CustomerManagementRequestResponse cmrr = createCMRR(oFactoryCM,
		"getCustomerById");

		if (id != null) {
			cmrr.getRequest().setCustomerId(id);
		}
		cmrr.getRequest().setAgentId(agentId);

		CustomerManagementRequestResponse cmrrR = TransportConfig.INSTANCE
		.getCustomerClient().send(cmrr);
		logCMRRResponse(cmrrR);

		CustomerType customer = null;

		if (cmrrR == null || cmrrR.getResponse() == null) {
			errors.add(new CartError("", "empty.cmrrr",
			"Null CustomerManagementRequestResponse"));
		} else {
			List<CustomerType> customerTypeList = cmrrR.getResponse()
			.getCustomerInfo();
			if (customerTypeList != null && customerTypeList.size() > 0) {
				customer = customerTypeList.get(0);
				CustomerCacheService.INSTANCE.store(customer, id);
			} else {
				errors.add(new CartError("", "empty.customer",
				"Customer doesn't exist"));
			}
		}

		return customer;
	}

	public List<OrderType> getOrderByCustomer(String agentId,
			String customerExtId, List<String> credentials, ErrorList errors) {

		com.A.xml.v4.ObjectFactory oFactoryOM = new com.A.xml.v4.ObjectFactory();

		OrderManagementRequestResponse omrr = createOMRR(oFactoryOM,
		"getOrderByCustomer");

		omrr.getRequest().setAgentId(agentId);
		omrr.getRequest().setCustomerId(customerExtId);

		OrderManagementRequestResponse omRR = TransportConfig.INSTANCE
		.getOrderClient().send(omrr);
		logOMRRResponse(omRR);

		if (omRR == null || omRR.getResponse() == null) {
			errors.add(new CartError("", "empty.omrr",
			"Null OrderManagementRequestResponse"));
		} else {
			List<OrderType> orderTypeList = omRR.getResponse().getOrderInfo();
			if (orderTypeList != null && orderTypeList.size() > 0) {
				if (orderTypeList.size() > 0) {
					return orderTypeList;
				}
			} else {
				errors.add(new CartError("", "empty.order",
				"Order doesn't exist"));
			}
		}

		return null;
	}

	public List<CustomerType> locate(final Map<String, String> criteria,
			List<String> credentials, ErrorList errors) {

		Set<String> keys = criteria.keySet();

		for (String key : keys) {
			String data = criteria.get(key);
			if ((data == null) || (data.length() == 0)) {
				criteria.put(key, "");
			}
		}

		List<CustomerType> customers = TransportConfig.INSTANCE
		.getCustomerClient().locateCustomer(criteria);
		if (customers != null && customers.size() > 0) {
			return customers;
		} else {
			errors.add(new CartError("", "empty.customers",
			"No Customers Exist"));
			return null;
		}

	}

	public CustomerType createCustomer(final String agentId,
			final CustomerType custInfo, List<String> credentials,
			ErrorList errors) {

		com.A.xml.cm.v4.ObjectFactory oFactoryCM = new com.A.xml.cm.v4.ObjectFactory();

		CustomerManagementRequestResponse cmrr = createCMRR(oFactoryCM,
		"createCustomer");

		cmrr.getRequest().setCustomerId("0");
		cmrr.getRequest().setCustomerNumber("0");
		cmrr.getRequest().setCustomerInfo(custInfo);
		cmrr.getRequest().setAgentId(agentId);

		CustomerManagementRequestResponse cmrrR = TransportConfig.INSTANCE
		.getCustomerClient().send(cmrr);
		logCMRRResponse(cmrrR);

		CustomerType customer = null;

		if (cmrrR == null || cmrrR.getResponse() == null) {
			errors.add(new CartError("", "empty.cmrrr",
			"Null CustomerManagementRequestResponse"));
		} else {
			List<CustomerType> customerTypeList = cmrrR.getResponse()
			.getCustomerInfo();
			if (customerTypeList != null && customerTypeList.size() > 0) {
				customer = customerTypeList.get(0);
				CustomerCacheService.INSTANCE.store(customer,
						customer.getExternalId() + "");
			} else {
				errors.add(new CartError("", "empty.customer",
				"Customer doesn't exist"));
			}
		}

		return customer;
	}

	public OrderType createOrderAndCustomer(final String agentId,
			final SalesContextType salesContextType, OrderType order,
			final Customer customer, List<String> credentials, ErrorList errors) {

		com.A.xml.v4.ObjectFactory oFactoryOM = new com.A.xml.v4.ObjectFactory();

		CustomerInformation customerInformation = oFactoryOM
		.createCustomerInformation();
		customerInformation.setAction("referenceCustomer");
		customer.setExternalId(customer.getExternalId());
		customerInformation.setCustomer(customer);
		order.setCustomerInformation(customerInformation);

		// order.setLineItems(oFactoryOM.createLineItemCollectionType());

		OrderType orderType = createOrder(oFactoryOM, agentId,
				salesContextType, order, credentials, errors);
		updateLineItemProductFromMap(orderType.getLineItems());
		return orderType;
	}

	public OrderType createOrderAndCustomer(final String agentId,
			OrderType order, final Customer customer, List<String> credentials,
			ErrorList errors) {

		com.A.xml.v4.ObjectFactory oFactoryOM = new com.A.xml.v4.ObjectFactory();

		SalesContextType salesContextType = getDefaultSalesContextType(oFactoryOM);

		CustomerInformation customerInformation = oFactoryOM
		.createCustomerInformation();
		customerInformation.setAction("referenceCustomer");
		customer.setExternalId(customer.getExternalId());
		customerInformation.setCustomer(customer);
		order.setCustomerInformation(customerInformation);

		OrderType orderType = createOrder(oFactoryOM, agentId,
				salesContextType, order, credentials, errors);
		updateLineItemProductFromMap(orderType.getLineItems());
		return orderType;
	}

	public OrderType createOrderExistingCustomer(String agentId,
			SalesContextType salesContextType, OrderType order,
			final Customer customer, List<String> credentials, ErrorList errors) {

		com.A.xml.v4.ObjectFactory oFactoryOM = new com.A.xml.v4.ObjectFactory();

		CustomerInformation customerInformation = oFactoryOM
		.createCustomerInformation();
		customerInformation.setAction("referenceCustomer");
		customer.setExternalId(customer.getExternalId());
		customerInformation.setCustomer(customer);
		order.setCustomerInformation(customerInformation);

		OrderType orderType = createOrder(oFactoryOM, agentId,
				salesContextType, order, credentials, errors);
		updateLineItemProductFromMap(orderType.getLineItems());
		return orderType;
	}
	public CustomerType saveAddress(final String agentId, final String custId, final CustAddress address, final List<String> credentials, final ErrorList errors)  {

		com.A.xml.cm.v4.ObjectFactory oFactory = new com.A.xml.cm.v4.ObjectFactory();

		CustomerManagementRequestResponse cmrr = getCMRR(oFactory, "addAddress");

		if (custId != null) {
			cmrr.getRequest().setCustomerId(custId);
			cmrr.getRequest().setAgentId(agentId);
		}

		if (address != null) {
			cmrr.getRequest().setCustomerInfo(oFactory.createCustomerType());
			cmrr.getRequest().getCustomerInfo().setAddressList(oFactory.createAddressListType());
			cmrr.getRequest().getCustomerInfo().getAddressList().getCustomerAddress().add(address);

			cmrr.getRequest().getCustomerInfo().setAgentId(agentId);
		}

		CustomerManagementRequestResponse cmrrR = TransportConfig.INSTANCE.getCustomerClient().send(cmrr);
		logCMRRResponse(cmrrR);

		CustomerType customer = null;

		if(cmrrR == null || cmrrR.getResponse() == null){
			errors.add(new CartError("", "empty.cmrrr", "Null CustomerManagementRequestResponse"));
		} else {
			List<CustomerType> customerTypeList = cmrrR.getResponse().getCustomerInfo();
			if (customerTypeList != null && customerTypeList.size() > 0) {
				customer = customerTypeList.get(0);
				CustomerCacheService.INSTANCE.store(customer, custId);
			} else {
				errors.add(new CartError("", "empty.customer", "Customer doesn't exist"));
			}
		}

		return customer;
	}
	public CustomerManagementRequestResponse getCMRR(com.A.xml.cm.v4.ObjectFactory oFactory, String transactionType){
		CustomerManagementRequestResponse cmrr = oFactory.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType(transactionType);

		return cmrr;
	}
	public OrderType createOrderExistingCustomer(final String agentId,
			OrderType order, final Customer customer, List<String> credentials,
			ErrorList errors) {

		com.A.xml.v4.ObjectFactory oFactoryOM = new com.A.xml.v4.ObjectFactory();

		SalesContextType salesContextType = getDefaultSalesContextType(oFactoryOM);

		CustomerInformation customerInformation = oFactoryOM
		.createCustomerInformation();
		customerInformation.setAction("referenceCustomer");
		customer.setExternalId(customer.getExternalId());
		customerInformation.setCustomer(customer);
		order.setCustomerInformation(customerInformation);

		addProductToMap(order.getLineItems());

		OrderType orderType = createOrder(oFactoryOM, agentId,
				salesContextType, order, credentials, errors);

		return orderType;
	}

	public void logCMRRResponse(CustomerManagementRequestResponse cmmRR) {
		// JaxbUtil<CustomerManagementRequestResponse> utilCust = new
		// JaxbUtil<CustomerManagementRequestResponse>();
		// utilCust.toString(cmmRR, CustomerManagementRequestResponse.class));
	}

	public void logOMRRResponse(OrderManagementRequestResponse omRR) {
		// JaxbUtil<OrderManagementRequestResponse> utilOrder = new
		// JaxbUtil<OrderManagementRequestResponse>();
		// utilOrder.toString(omRR, OrderManagementRequestResponse.class));
	}

	public OrderManagementRequestResponse createOMRR(
			final com.A.xml.v4.ObjectFactory oFactory,
			final String transactionType) {

		OrderManagementRequestResponse cmrr = oFactory
		.createOrderManagementRequestResponse();
		cmrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType(transactionType);

		return cmrr;
	}

	public CustomerManagementRequestResponse createCMRR(
			com.A.xml.cm.v4.ObjectFactory oFactory,
			String transactionType) {
		CustomerManagementRequestResponse cmrr = oFactory
		.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory
				.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType(transactionType);

		return cmrr;
	}

	public OrderType createOrder(
			com.A.xml.v4.ObjectFactory oFactoryOM,
			final String agentId, SalesContextType salesContextType,
			OrderType order, List<String> credentials, ErrorList errors) {

		addProductToMap(order.getLineItems());
		OrderManagementRequestResponse omrr = createOMRR(oFactoryOM,
		"createOrder");

		omrr.getRequest().setOrderInfo(order);
		omrr.getRequest().setSalesContext(salesContextType);
		omrr.getRequest().setAgentId(agentId);

		OrderManagementRequestResponse omRR = TransportConfig.INSTANCE
		.getOrderClient().send(omrr);
		logOMRRResponse(omRR);

		OrderType orderType = null;

		if (omRR == null || omRR.getResponse() == null) {
			errors.add(new CartError("", "empty.omrr",
			"Null OrderManagementRequestResponse"));
		} else {
			List<OrderType> orderTypeList = omRR.getResponse().getOrderInfo();
			if (orderTypeList != null && orderTypeList.size() > 0) {
				if (orderTypeList.size() > 0) {
					orderType = orderTypeList.get(0);
				}
			} else {
				errors.add(new CartError("", "empty.order",
				"Order doesn't exist"));
			}

			updateLineItemProductFromMap(orderType.getLineItems());
			OrderCacheService.INSTANCE.store(orderType,
					orderType.getExternalId() + "");
		}

		return orderType;
	}

	private SalesContextType getDefaultSalesContextType(
			com.A.xml.v4.ObjectFactory oFactoryOM) {

		SalesContextType salesContextType = oFactoryOM.createSalesContextType();

		SalesContextEntityType salesContextEntityType = oFactoryOM
		.createSalesContextEntityType();
		salesContextEntityType.setName("source");

		NameValuePairType nameValuePairType = oFactoryOM
		.createNameValuePairType();
		nameValuePairType.setName("source");
		nameValuePairType.setValue("SYP");
		salesContextEntityType.getAttribute().add(nameValuePairType);
		salesContextType.getEntity().add(salesContextEntityType);

		return salesContextType;
	}

	public void updateLineItemProductFromMap(
			final LineItemCollectionType liCollection) {

		for (LineItemType lit : liCollection.getLineItem()) {
			//Only products
			if(lit.getLineItemDetail().getDetail().getProductLineItem()!= null){

				String productExtId = lit.getLineItemDetail().getDetail()
				.getProductLineItem().getExternalId();

				if (map.containsKey(productExtId)) {

					ProductDetailVo vo = map.get(productExtId);

					if ((lit.getPartnerExternalId() == null)
							|| (lit.getPartnerExternalId().length() == 0)) {
						lit.setPartnerExternalId(vo.getPartnerExternalId());
					}

					if ((lit.getPartnerName() == null)
							|| (lit.getPartnerName().length() == 0)) {
						lit.setPartnerName(vo.getPartnerName());
					}

					ObjectFactory oFactory = new ObjectFactory();
					if (lit.getLineItemDetail() == null) {
						lit.setLineItemDetail(oFactory.createLineItemDetailType());
					}

					if (lit.getLineItemDetail().getDetail() == null) {
						lit.getLineItemDetail().setDetail(
								oFactory.createOrderLineItemDetailTypeType());
					}

					if (lit.getLineItemDetail().getDetail().getProductLineItem() == null) {
						lit.getLineItemDetail().getDetail()
						.setProductLineItem(oFactory.createProductType());
					}

					if (lit.getLineItemDetail().getDetail().getProductLineItem()
							.getProvider() == null) {
						lit.getLineItemDetail().getDetail().getProductLineItem()
						.setProvider(oFactory.createProviderType());
					}

					lit.getLineItemDetail().getDetail().getProductLineItem()
					.getProvider().setName(vo.getPartnerName());
					lit.getLineItemDetail().getDetail().getProductLineItem()
					.setName(vo.getProductName());

				}
			}
		}
	}

	public void addProductToMap(final LineItemType lineItemType) {

		if(lineItemType.getLineItemDetail().getDetailType().equals("product")){
			String partnerExtId = lineItemType.getPartnerExternalId();
			String partnerName = lineItemType.getPartnerName();
			String productName = lineItemType.getLineItemDetail().getDetail()
			.getProductLineItem().getName();
			String productExtId = lineItemType.getLineItemDetail().getDetail()
			.getProductLineItem().getExternalId();

			if (!map.containsKey(productExtId)) {
				ProductDetailVo vo = new ProductDetailVo(partnerExtId, partnerName,
						productName, productExtId);
				map.put(productExtId, vo);
			}
		}
	}

	public void addProductToMap(final LineItemCollectionType liCollection) {

		for (LineItemType lit : liCollection.getLineItem()) {

			addProductToMap(lit);
		}

	}

	public OrderType addLineItem(final String agentId, final String orderId,
			final LineItemCollectionType liCollection, SalesContextType updateSalesContext,String GUID) {

		addProductToMap(liCollection);

		OrderCacheService.INSTANCE.clear(orderId);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = oFactory
		.createOrderManagementRequestResponse();
		omrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		if(GUID != null && !GUID.isEmpty()){
			omrr.setCorrelationId(GUID);
		}else{
			omrr.setCorrelationId(UUID.randomUUID().toString());
		}
		
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.setTransactionType("addLineItem");

		if (orderId != null) {
			omrr.getRequest().setOrderId(orderId);
		}
		omrr.getRequest().setAgentId(agentId);
		omrr.getRequest().setAfterLineItemNumber(9999);
		omrr.getRequest().setIsAppliesToLineItemIncluded(true);
		if(updateSalesContext != null){
			omrr.getRequest().setSalesContext(updateSalesContext);
		}
		
	
		omrr.getRequest().setNewLineItems(liCollection);

		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE
		.getOrderClient().send(omrr);

		OrderType orderR = null;

		if ((cmrrR != null) && (cmrrR.getResponse() != null)
				&& (cmrrR.getResponse().getOrderInfo() != null)) {
			List<OrderType> orderTypeList = cmrrR.getResponse().getOrderInfo();

			if (orderTypeList.size() > 0) {
				orderR = orderTypeList.get(0);
			}
		}

		updateLineItemProductFromMap(orderR.getLineItems());

		OrderCacheService.INSTANCE.store(orderR, orderId);

		return orderR;

	}
	
	public OrderType addPromtion(final String agentId, final String orderId,
			final LineItemCollectionType liCollection, SalesContextType updateSalesContext,String GUID) {

		addProductToMap(liCollection);

		OrderCacheService.INSTANCE.clear(orderId);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse omrr = oFactory
		.createOrderManagementRequestResponse();
		omrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		if(GUID != null && !GUID.isEmpty()){
			omrr.setCorrelationId(GUID);
		}else{
			omrr.setCorrelationId(UUID.randomUUID().toString());
		}
		
		omrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		omrr.setTransactionType("addLineItem");

		if (orderId != null) {
			omrr.getRequest().setOrderId(orderId);
		}
		omrr.getRequest().setAgentId(agentId);
		omrr.getRequest().setAfterLineItemNumber(9999);
		omrr.getRequest().setIsAppliesToLineItemIncluded(false);
		if(updateSalesContext != null){
			omrr.getRequest().setSalesContext(updateSalesContext);
		}
		
	
		omrr.getRequest().setNewLineItems(liCollection);

		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE
		.getOrderClient().send(omrr);

		OrderType orderR = null;

		if ((cmrrR != null) && (cmrrR.getResponse() != null)
				&& (cmrrR.getResponse().getOrderInfo() != null)) {
			List<OrderType> orderTypeList = cmrrR.getResponse().getOrderInfo();

			if (orderTypeList.size() > 0) {
				orderR = orderTypeList.get(0);
			}
		}

		updateLineItemProductFromMap(orderR.getLineItems());

		OrderCacheService.INSTANCE.store(orderR, orderId);

		return orderR;

	}

	/* (non-Javadoc)
	 * @see com.A.ui.service.V.VClient#getOrderById(java.lang.String, java.lang.String, boolean, java.util.List, com.A.ui.vo.ErrorList)
	 */
	public OrderType getOrderById(String agentId, String id,
			boolean filtered, List<String> credentials, ErrorList errors) {
		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse cmrr = createOMRR(oFactory, "getOrder");

		if (id != null) {
			cmrr.getRequest().setOrderId(id);
		}

		if(agentId != null){
			cmrr.getRequest().setAgentId(agentId);
		}

		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE
		.getOrderClient().send(cmrr);

		OrderType order = null;

		if ((cmrrR != null) && (cmrrR.getResponse() != null)) {

			List<OrderType> orderTypeList = cmrrR.getResponse().getOrderInfo();

			if ((orderTypeList != null) && (orderTypeList.size() > 0)) {
				order = orderTypeList.get(0);
			}
		}

		if (order != null) {
			if (filtered) {
				AuthorizationFilter.INSTANCE.removeDeleted(order);
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
		}

		return order;
	}

	/* (non-Javadoc)
	 * @see com.A.ui.service.V.VClient#getOrderByLineItemNumber(java.lang.String, java.lang.String, java.util.List, com.A.ui.vo.ErrorList)
	 */
	public OrderType getOrderByLineItemNumber(String lineItemId, String agentId, List<String> credentials, ErrorList errors) {
		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse cmrr = createOMRR(oFactory, "getOrderByLineItem");

		if (lineItemId != null) {
			cmrr.getRequest().setLineItemId(lineItemId);
		}
		
		if(agentId != null){
			cmrr.getRequest().setAgentId(agentId);
		}

		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE.getOrderClient().send(cmrr);

		OrderType order = null;

		if ((cmrrR != null) && (cmrrR.getResponse() != null)) {
			List<OrderType> orderTypeList = cmrrR.getResponse().getOrderInfo();
			if ((orderTypeList != null) && (orderTypeList.size() > 0)) {
				order = orderTypeList.get(0);
			}
		}

		if (order != null) {
			// Sorting Status History
			Collections.sort(order.getOrderStatusHistory().getPreviousStatus(),
					new Comparator<OrderStatusWithTypeType>() {
				public int compare(OrderStatusWithTypeType o1,
						OrderStatusWithTypeType o2) {
					return o2.getDateTimeStamp().compare(
							o1.getDateTimeStamp());
				}
			});
		}

		return order;
	}

}
