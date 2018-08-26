package com.AL.ui.service.V;

import java.util.List;
import java.util.Map;
import com.AL.ui.vo.ErrorList;
import com.AL.xml.cm.v4.AddressType;
import com.AL.xml.cm.v4.CustAddress;
import com.AL.xml.cm.v4.CustomerType;

public interface UICartCustomerService {

	/**
	 * @param agentId
	 * @param customer
	 * @param credentials
	 * @param errors
	 * @return
	 */
	CustomerType createCustomer(final String agentId, final CustomerType customer, final List<String> credentials,ErrorList errors);
	
	/**
	 * @param agentId
	 * @param customerExtId
	 * @param credentials
	 * @param errors
	 * @return
	 */
	CustomerType get(final String agentId, final String customerExtId, final List<String> credentials,ErrorList errors);

	/**
	 * @param criteria
	 * @param credentials
	 * @param errors
	 * @return
	 */
	List<CustomerType> locate(final  Map<String, String> criteria,  final List<String> credentials,ErrorList errors);

	/**
	 * @param agentId
	 * @param id
	 * @param customer
	 * @param credentials
	 * @param errors
	 * @return
	 */
	CustomerType saveName(final String agentId, final String id, final CustomerType customer, final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param customerId
	 * @param address
	 * @param credentials
	 * @param errors
	 * @return
	 */
	CustomerType saveAddress(final String agentId, final String customerId, final CustAddress address, final List<String> credentials, final ErrorList errors);

	/**
	 * @param agentId
	 * @param custId
	 * @param roles
	 * @param addressId
	 * @param addressUniqueId
	 * @param addressType
	 * @param status
	 * @param credentials
	 * @param errors
	 * @return
	 */
	CustomerType saveAddressUpdate(final String agentId, final String custId, String[] roles, final String addressId,
			final String addressUniqueId, final AddressType addressType, final String status, final List<String> credentials, final ErrorList errors);

	/**
	 * @param agentId
	 * @param id
	 * @param customer
	 * @param credentials
	 * @param errors
	 * @return
	 */
	CustomerType saveBillingInfo(final String agentId, final String id, final CustomerType customer, final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param id
	 * @param customer
	 * @param credentials
	 * @param errors
	 * @return
	 */
	CustomerType savePaymentEvent(final String agentId, final String id, final CustomerType customer, final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param id
	 * @param customer
	 * @param credentials
	 * @param errors
	 * @return
	 */
	CustomerType saveIdentification(final String agentId, final String id, final CustomerType customer, final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param id
	 * @param customer
	 * @param credentials
	 * @param errors
	 * @return
	 */
	CustomerType saveContactInfo(final String agentId, final String id, final CustomerType customer, final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param id
	 * @param customer
	 * @param credentials
	 * @param errors
	 * @return
	 */
	CustomerType saveFinancialInfo(final String agentId, final String id, final CustomerType customer, final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param id
	 * @param customer
	 * @param credentials
	 * @param errors
	 * @return
	 */
	CustomerType saveOptions(final String agentId, final String id, final CustomerType customer, final List<String> credentials, final ErrorList errors);
	
	/**
	 * @param agentId
	 * @param id
	 * @param customer
	 * @param credentials
	 * @param errors
	 * @return
	 */
	CustomerType updateCustomer(final String agentId, final String id, final CustomerType customer, final List<String> credentials, final ErrorList errors);

}
