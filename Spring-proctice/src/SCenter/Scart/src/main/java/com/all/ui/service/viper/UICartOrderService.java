package com.AL.ui.service.V;

import java.util.List;
import java.util.Map;

import com.AL.ui.vo.ErrorList;
import com.AL.xml.v4.CustomSelectionsType;
import com.AL.xml.v4.Customer;
import com.AL.xml.v4.CustomerType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.SchedulingInfoType;
import com.AL.xml.v4.SelectedDialogsType;
import com.AL.xml.v4.SelectedFeaturesType;

public interface UICartOrderService {
	 
	/**
	 * @param agentId
	 * @param lineitemExtId
	 * @param order
	 * @return
	 */
	OrderType save(final String agentId, final String lineitemExtId, final OrderType order);
	
	/**
	 * @param agentId
	 * @param lineitemExtId
	 * @param orderId
	 * @param schedInfo
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType saveSchedulingInfo(final String agentId, final long lineitemExtId, final String orderId, final SchedulingInfoType schedInfo, final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param lineitemExtId
	 * @param orderId
	 * @param features
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType saveSelectedFeatures(final String agentId, final long lineitemExtId, final String orderId, final SelectedFeaturesType features, final List<String> credentials, final ErrorList errors); 
	
	/**
	 * @param agentId
	 * @param lineitemExtId
	 * @param orderId
	 * @param selections
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType saveSelectedDialogs(final String agentId, final long lineitemExtId, final String orderId, final SelectedDialogsType selections, final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param lineitemExtId
	 * @param orderId
	 * @param selections
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType saveCustomSelections(final String agentId, final long lineitemExtId, final String orderId, final CustomSelectionsType selections, final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param lineitemExtId
	 * @param customerType
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType saveCustomer(final String agentId, final String lineitemExtId, final CustomerType customerType, final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param lineitemExtId
	 * @param schedInfo
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType saveSchedulingInfo(final String agentId, final String lineitemExtId, final List<SchedulingInfoType> schedInfo, final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param lineitemExtId
	 * @param features
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType saveSelectedFeatures(final String agentId, final String lineitemExtId, final List<SelectedFeaturesType> features, final List<String> credentials, final ErrorList errors); 
	
	/**
	 * @param agentId
	 * @param lineitemExtId
	 * @param selections
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType saveSelectedDialogs(final String agentId, final String lineitemExtId, final List<SelectedDialogsType> selections, final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param lineitemExtId
	 * @param selections
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType saveCustomSelections(final String agentId, final String lineitemExtId, final List<CustomSelectionsType> selections, final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param lineitemExtId
	 * @param customerType
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType saveCustomer(final String agentId, final String lineitemExtId, final List<CustomerType> customerType, final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param orderId
	 * @param lineitemExtId
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType CKO(final String agentId, final String orderId, final List<String> lineitemExtId,  final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param externalId
	 * @param roles
	 * @param ACTIVE_PROVIDER
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType get(final String agentId, final String externalId, Map<String, Object> roles, final String ACTIVE_PROVIDER, final List<String> credentials, ErrorList errors);
	
	/**
	 * @param agentId
	 * @param salesContextType
	 * @param order
	 * @param customer
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType createOrderAndCustomer(final String agentId, final SalesContextType salesContextType, OrderType order, final Customer customer, List<String> credentials, ErrorList errors);
	
	/**
	 * @param agentId
	 * @param order
	 * @param customer
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType createOrderAndCustomer(final String agentId, OrderType order, final Customer customer, List<String> credentials, ErrorList errors);
	
	/**
	 * @param agentId
	 * @param salesContextType
	 * @param order
	 * @param customer
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType createOrderExistingCustomer(final String agentId, SalesContextType salesContextType, OrderType order, final Customer customer, List<String> credentials, ErrorList errors);
	
	/**
	 * @param agentId
	 * @param orderExtId
	 * @param lineitem
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType addLineItem(final String agentId, final String orderExtId, final LineItemType lineitem, final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param orderExtId
	 * @param lineitems
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType addLineItem(final String agentId, final String orderExtId, final List<LineItemType> lineitems, final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param orderExtId
	 * @param lineItemId
	 * @param reasons
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType removeLineItem(final String agentId, final String orderExtId, final String lineItemId, final List<Integer> reasons, List<String> credentials, ErrorList errors);
	
	/**
	 * @param agentId
	 * @param orderExtId
	 * @param lineItemIdList
	 * @param reasons
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType removeLineItem(final String agentId, final String orderExtId, final List<String> lineItemIdList, final List<Integer> reasons, final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param orderExtId
	 * @param lineItemId
	 * @param reasons
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType deleteLineItem(final String agentId, final String orderExtId, final String lineItemId, final List<Integer> reasons, List<String> credentials, ErrorList errors);
	
	/**
	 * @param agentId
	 * @param orderExtId
	 * @param lineItemIdList
	 * @param reasons
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType deleteLineItem(final String agentId, final String orderExtId, final List<String> lineItemIdList, final List<Integer> reasons, final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param orderExtId
	 * @param lineItemId
	 * @param status
	 * @param reasons
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType updateLineItemStatus(final String agentId, final String orderExtId, final String lineItemId, final String status, final List<Integer> reasons, List<String> credentials, ErrorList errors);
	
	/**
	 * @param agentId
	 * @param orderExtId
	 * @param lineItemIdList
	 * @param status
	 * @param reasons
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType updateLineItemStatus(final String agentId, final String orderExtId, final List<String> lineItemIdList, final String status, final List<Integer> reasons, List<String> credentials, ErrorList errors);
	
	/**
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @param transactionType
	 * @param lit
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType updateLineItem(final String agentId, final String orderId, final long lineItemId, final String transactionType, final LineItemType lit, List<String> credentials, ErrorList errors);
	
	/**
	 * @param agentId
	 * @param orderId
	 * @param liCollection
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType updateLineItem(final String agentId, final String orderId, final LineItemCollectionType liCollection, List<String> credentials, ErrorList errors);
	
	/**
	 * @param agentId
	 * @param orderExtId
	 * @param lineItemId
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType submit(final String agentId, String orderExtId, String lineItemId, List<String> credentials, ErrorList errors);
	
	/**
	 * @param agentId
	 * @param orderExtId
	 * @param lineItemIdList
	 * @param credentials
	 * @param errors
	 * @return
	 */
	OrderType submit(final String agentId, String orderExtId, List<String> lineItemIdList, List<String> credentials, ErrorList errors);
	
}
