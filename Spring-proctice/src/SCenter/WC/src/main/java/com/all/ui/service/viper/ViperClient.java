package com.A.ui.service.V;

import java.util.*;

import com.A.ui.vo.ErrorList;
import com.A.xml.cm.v4.CustAddress;
import com.A.xml.cm.v4.CustomerType;
import com.A.xml.v4.LineItemCollectionType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.SalesContextEntityType;
import com.A.xml.v4.SalesContextType;
import com.A.xml.v4.OrderType;
import com.A.xml.v4.Customer;

public interface VClient {

	CustomerType get(final String agentId, final String customerExtId,
			List<String> credentials, ErrorList errors);
	
	/**
	 * @param agentId
	 * @param orderExtId
	 * @param filtered
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType getOrderById(final String agentId, final String orderExtId, final boolean filtered,
			List<String> credentials, ErrorList errors);
	
	/**
	 * Get OrderType with LineItem External Id
	 * 
	 * @param lineItemId
	 * @param agentId
	 * @return OrderType
	 */
	OrderType getOrderByLineItemNumber(final String lineItemId, final String agentId, List<String> credentials, ErrorList errors);

	List<OrderType> getOrderByCustomer(final String agentId,
			final String customerExtId, List<String> credentials,
			ErrorList errors);

	List<CustomerType> locate(final Map<String, String> criteria,
			List<String> credentials, ErrorList errors);

	CustomerType createCustomer(final String agentId, final CustomerType c,
			List<String> credentials, ErrorList errors);

	OrderType createOrderAndCustomer(final String agentId,
			final SalesContextType salesContextType, OrderType order,
			final Customer customer, List<String> credentials, ErrorList errors);

	OrderType createOrderAndCustomer(final String agentId, OrderType order,
			final Customer customer, List<String> credentials, ErrorList errors);

	OrderType createOrderExistingCustomer(final String agentId,
			final SalesContextType salesContextType, OrderType order,
			final Customer customer, List<String> credentials, ErrorList errors);

	OrderType createOrderExistingCustomer(final String agentId,
			OrderType order, final Customer customer, List<String> credentials,
			ErrorList errors);

	OrderType addLineItem(final String agentId, final String orderId,
			final LineItemCollectionType liCollection, SalesContextType updateSalesContext,String GUID);
	
	OrderType addPromtion(final String agentId, final String orderId,
			final LineItemCollectionType liCollection, SalesContextType updateSalesContext,String GUID);
	
	CustomerType saveAddress(final String agentId, final String customerId, final CustAddress address, final List<String> credentials, final ErrorList errors);

	void updateLineItemProductFromMap(final LineItemCollectionType liCollection);

	void addProductToMap(final LineItemCollectionType liCollection);
	
	void addProductToMap(final LineItemType lineItemType);

}
