package com.A.ui.service.V;

import java.util.List;
import java.util.UUID;

import com.A.ui.builder.AddressBuilder;
import com.A.ui.service.V.impl.CustomerCacheService;
import com.A.ui.service.V.impl.CustomerInteractionCacheService;
import com.A.ui.transport.TransportConfig;
import com.A.ui.vo.CartError;
import com.A.ui.vo.ErrorList;
import com.A.V.factory.CustomerFactory;
import com.A.xml.cm.v4.AddressType;
import com.A.xml.cm.v4.Attributes;
import com.A.xml.cm.v4.CustAddress;
import com.A.xml.cm.v4.CustomerContextType;
import com.A.xml.cm.v4.CustomerInteractionType;
import com.A.xml.cm.v4.CustomerManagementRequestResponse;
import com.A.xml.cm.v4.CustomerType;
import com.A.xml.cm.v4.ObjectFactory;
import com.A.xml.cm.v4.ProcessingMessage;

public enum AddressService {

	INSTANCE;
	
	/**
	 * Add new address
	 * 
	 * @param agentId
	 * @param customerId
	 * @param roles
	 * @param addressId
	 * @param addressUniqueId
	 * @param addressType
	 * @param status
	 * @return
	 */
	@Deprecated
	public CustomerType saveNewAddress(final String agentId, final String customerId, String[] roles,
			final String addressId, final String addressUniqueId,
			final AddressType addressType, final String status){
		return saveNewAddress(agentId, customerId, roles, addressId, addressUniqueId,
				addressType, status, null);
	}

	/**
	 * Add new address
	 * 
	 * @param agentId
	 * @param customerId
	 * @param roles
	 * @param addressId
	 * @param addressUniqueId
	 * @param addressType
	 * @param status
	 * @param customerContext
	 * @return
	 */
	public CustomerType saveNewAddress(final String agentId, final String customerId, String[] roles,
			final String addressId, final String addressUniqueId,
			final AddressType addressType, final String status, CustomerContextType customerContext) {

		ObjectFactory oFactory = new ObjectFactory();

		final CustAddress address = AddressBuilder.INSTANCE
				.buildCustomerAddress(customerId, roles, addressId,
						addressUniqueId, addressType, status);

		CustomerManagementRequestResponse cmrr = oFactory.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("addAddress");
		 

		if(customerContext != null){
			cmrr.getRequest().setCustomerContext(customerContext);
		}
		
		if (customerId != null) {
			cmrr.getRequest().setCustomerId(customerId);
		}

		if (address != null) {
			cmrr.getRequest().setCustomerInfo(oFactory.createCustomerType());
			cmrr.getRequest().getCustomerInfo().setAddressList(oFactory.createAddressListType());
			cmrr.getRequest().getCustomerInfo().getAddressList().getCustomerAddress().add(address);
			
			//update agent information
			cmrr.getRequest().setAgentId(agentId);
			cmrr.getRequest().getCustomerInfo().setAgentId(agentId);
		}
		
		CustomerManagementRequestResponse cmrrR =TransportConfig.INSTANCE.getCustomerClient().send(cmrr);

		List<CustomerType> customerTypeList = cmrrR.getResponse().getCustomerInfo();

		CustomerType customerType = null;
		if ((customerTypeList != null) && (customerTypeList.size() > 0)) {
			customerType = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(customerTypeList.get(0));
			CustomerCacheService.INSTANCE.store(customerType, customerId);
		}

		return customerType;
	}
	
	/**
	 * Update address
	 * 
	 * @param agentId
	 * @param customerId
	 * @param address
	 * @return
	 */
	@Deprecated
	public CustomerType saveAddressUpdate(final String agentId, final String customerId, final CustAddress address) {
		return saveAddressUpdate(agentId, customerId, address, null);
	}
	
	/**
	 * Update address
	 * 
	 * @param agentId
	 * @param customerId
	 * @param address
	 * @param customerContext
	 * @return
	 */
	public CustomerType saveAddressUpdate(final String agentId, final String customerId, final CustAddress address, CustomerContextType customerContext) {

		ObjectFactory oFactory = new ObjectFactory();

		CustomerCacheService.INSTANCE.clear(customerId);
		CustomerInteractionCacheService.INSTANCE.clearCachedCustomerInteraction(customerId);

		CustomerManagementRequestResponse cmrr = oFactory.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("updateCustomer");

		if(customerContext != null){
			cmrr.getRequest().setCustomerContext(customerContext);
		}
		cmrr.getRequest().setCustomerId(customerId);
		cmrr.getRequest().setAgentId(agentId);
		cmrr.getRequest().setCustomerInfo(oFactory.createCustomerType());
		cmrr.getRequest().getCustomerInfo().setAgentId(agentId);

		// *******************************
		// ** Set Customer Address List **
		// *******************************
		cmrr.getRequest().getCustomerInfo()
				.setAddressList(oFactory.createAddressListType());
		cmrr.getRequest().getCustomerInfo().getAddressList()
				.getCustomerAddress().add(address);

		CustomerManagementRequestResponse cmrrR = TransportConfig.INSTANCE.getCustomerClient().send(cmrr);

		List<CustomerType> customerTypeList = cmrrR.getResponse().getCustomerInfo();

		CustomerType customerType = null;
		if ((customerTypeList != null) && (customerTypeList.size() > 0)) {
			customerType = customerTypeList.get(0);
		}
		
		customerType = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(customerType);
		CustomerCacheService.INSTANCE.store(customerType, customerId);

		if ((customerType != null)
				&& (customerType.getCustomerInteractionList() != null)
				&& (customerType.getCustomerInteractionList()
						.getCustomerInteraction() != null)) {
			List<CustomerInteractionType> list = customerType
					.getCustomerInteractionList().getCustomerInteraction();

			CustomerInteractionCacheService.INSTANCE.storeCustomerInteraction(list, customerId,
					CustomerInteractionCacheService.CUSTOMER_INTERACTION_PREFIX);

		}
		return customerType;
	}
	
	/**
	 * Update address
	 * 
	 * @param agentId
	 * @param customerId
	 * @param roles
	 * @param addressId
	 * @param addressUniqueId
	 * @param addressType
	 * @param status
	 * @return
	 */
	@Deprecated
	public CustomerType saveAddressUpdate(final String agentId, final String customerId,
			String[] roles, final String addressId,
			final String addressUniqueId, final AddressType addressType,
			final String status) {
		return saveAddressUpdate(agentId, customerId, roles, addressId, addressUniqueId, 
				addressType, status, null);
	}

	/**
	 * Update address
	 * 
	 * @param agentId
	 * @param customerId
	 * @param roles
	 * @param addressId
	 * @param addressUniqueId
	 * @param addressType
	 * @param status
	 * @param customerContext
	 * @return
	 */
	public CustomerType saveAddressUpdate(final String agentId, final String customerId,
			String[] roles, final String addressId,
			final String addressUniqueId, final AddressType addressType,
			final String status, CustomerContextType customerContext) {

		ObjectFactory oFactory = new ObjectFactory();

		final CustAddress address = AddressBuilder.INSTANCE
				.buildCustomerAddress(customerId, roles, addressId,
						addressUniqueId, addressType, status);

		CustomerCacheService.INSTANCE.clear(customerId);
		CustomerInteractionCacheService.INSTANCE.clearCachedCustomerInteraction(customerId);

		CustomerManagementRequestResponse cmrr = oFactory
				.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory
				.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("updateCustomer");

		if(customerContext != null){
			cmrr.getRequest().setCustomerContext(customerContext);
		}
		
		cmrr.getRequest().setCustomerId(customerId);
		cmrr.getRequest().setAgentId(agentId);
		cmrr.getRequest().setCustomerInfo(oFactory.createCustomerType());
		cmrr.getRequest().getCustomerInfo().setAgentId(agentId);

		// *******************************
		// ** Set Customer Address List **
		// *******************************
		cmrr.getRequest().getCustomerInfo()
				.setAddressList(oFactory.createAddressListType());
		cmrr.getRequest().getCustomerInfo().getAddressList()
				.getCustomerAddress().add(address);

		CustomerManagementRequestResponse cmrrR = TransportConfig.INSTANCE.getCustomerClient().send(cmrr);

		List<CustomerType> customerTypeList = cmrrR.getResponse()
				.getCustomerInfo();

		CustomerType customerType = null;
		if ((customerTypeList != null) && (customerTypeList.size() > 0)) {
			customerType = customerTypeList.get(0);
		}

		customerType = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(customerType);
		CustomerCacheService.INSTANCE.store(customerType, customerId);

		if ((customerType != null)
				&& (customerType.getCustomerInteractionList() != null)
				&& (customerType.getCustomerInteractionList()
						.getCustomerInteraction() != null)) {
			List<CustomerInteractionType> list = customerType
					.getCustomerInteractionList().getCustomerInteraction();

			CustomerInteractionCacheService.INSTANCE.storeCustomerInteraction(list, customerId,
					CustomerInteractionCacheService.CUSTOMER_INTERACTION_PREFIX);

		}
		return customerType;
	}

	/**
	 * @param agentId
	 * @param customerId
	 * @param roles
	 * @param addressId
	 * @param addressUniqueId
	 * @param addressType
	 * @param status
	 * @param customerContext
	 * @param attributes
	 * @return
	 */
	@Deprecated
	public CustomerType saveAddress(final String agentId, final String customerId, String[] roles,
			final String addressId, final String addressUniqueId,
			final AddressType addressType, final String status, CustomerContextType customerContext, Attributes attributes) {
		return saveAddress(agentId, customerId, roles, addressId, addressUniqueId, addressType, 
				status, customerContext, attributes, null);
	}
	
	/**
	 * Method to add new address
	 * 
	 * @param agentId
	 * @param customerId
	 * @param roles
	 * @param addressId
	 * @param addressUniqueId
	 * @param addressType
	 * @param status
	 * @param customerContext
	 * @param attributes
	 * @return
	 */
	public CustomerType saveAddress(final String agentId, final String customerId, String[] roles,
			final String addressId, final String addressUniqueId, final AddressType addressType, 
			final String status, CustomerContextType customerContext, 
			Attributes attributes, ErrorList errorList) {

		ObjectFactory oFactory = new ObjectFactory();

		final CustAddress address = AddressBuilder.INSTANCE
				.buildCustomerAddress(customerId, roles, addressId,
						addressUniqueId, addressType, status);

		CustomerManagementRequestResponse cmrr = oFactory.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("addAddress");
		 

		if(customerContext != null){
			cmrr.getRequest().setCustomerContext(customerContext);
		}
		
		if (customerId != null) {
			cmrr.getRequest().setCustomerId(customerId);
		}

		if (address != null) {
			cmrr.getRequest().setCustomerInfo(oFactory.createCustomerType());
			cmrr.getRequest().getCustomerInfo().setAddressList(oFactory.createAddressListType());
			cmrr.getRequest().getCustomerInfo().getAddressList().getCustomerAddress().add(address);
			
			//update agent information
			cmrr.getRequest().setAgentId(agentId);
			cmrr.getRequest().getCustomerInfo().setAgentId(agentId);
			cmrr.getRequest().getCustomerInfo().setAttributes(attributes);
		}
		
		CustomerManagementRequestResponse cmrrR =TransportConfig.INSTANCE.getCustomerClient().send(cmrr);

		List<CustomerType> customerTypeList = cmrrR.getResponse().getCustomerInfo();

		CustomerType customerType = null;
		if ((customerTypeList != null) && (customerTypeList.size() > 0)) {
			
			if(cmrrR.getStatus() != null && cmrrR.getStatus().getStatusCode() != null 
					&& cmrrR.getStatus().getStatusMsg() != null)
			{
				//If Error come the Status Code will be greater than 0
				if(cmrrR.getStatus().getStatusCode() > 0 && cmrrR.getStatus().getStatusMsg().equalsIgnoreCase("ERROR"))
				{
					if(cmrrR.getStatus().getProcessingMessages() != null &&
							cmrrR.getStatus().getProcessingMessages().getMessage() != null)
					{
						for(ProcessingMessage message : cmrrR.getStatus().getProcessingMessages().getMessage())
						{
							errorList.add(new CartError(String.valueOf(message.getCode()), message.getText()));
						}
					}
				}
				
			}
			
			customerType = customerTypeList.get(0);
			
			//To remove deleted billing info's and corresponding payments
			List<String> inActiveBillingIdList = CustomerService.INSTANCE.filterInActiveBillingInfo(customerType);
			CustomerService.INSTANCE.filterInActivePayments(customerType, inActiveBillingIdList);
			
			customerType = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(customerType);
			
			//Cache will be reset only if updateCustomer is successful
			if(errorList.size() == 0){
				CustomerInteractionCacheService.INSTANCE.clearCachedCustomerInteraction(customerId);
				CustomerCacheService.INSTANCE.clear(customerId);
				CustomerCacheService.INSTANCE.store(customerType, customerId);
			}
		}

		return customerType;
	}
	
	/**
	 * @param agentId
	 * @param customerId
	 * @param roles
	 * @param addressId
	 * @param addressUniqueId
	 * @param addressType
	 * @param status
	 * @param customerContext
	 * @param attributes
	 * @return
	 */
	@Deprecated
	public CustomerType updateAddress(final String agentId, final String customerId,
			String[] roles, final String addressId,
			final String addressUniqueId, final AddressType addressType,
			final String status, CustomerContextType customerContext, Attributes attributes) {
		return updateAddress(agentId, customerId, roles, addressId, addressUniqueId, addressType, 
				status, customerContext, attributes, null);
	}
	
	/**
	 * Method to update Address
	 * 
	 * @param agentId
	 * @param customerId
	 * @param roles
	 * @param addressId
	 * @param addressUniqueId
	 * @param addressType
	 * @param status
	 * @param customerContext
	 * @param attributes
	 * @return
	 */
	public CustomerType updateAddress(final String agentId, final String customerId,
			String[] roles, final String addressId, final String addressUniqueId, 
			final AddressType addressType, final String status, CustomerContextType customerContext, 
			Attributes attributes, ErrorList errorList) {

		ObjectFactory oFactory = new ObjectFactory();

		final CustAddress address = AddressBuilder.INSTANCE
				.buildCustomerAddress(customerId, roles, addressId,
						addressUniqueId, addressType, status);

		CustomerManagementRequestResponse cmrr = oFactory
				.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory
				.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("updateCustomer");

		if(customerContext != null){
			cmrr.getRequest().setCustomerContext(customerContext);
		}
		
		cmrr.getRequest().setCustomerId(customerId);
		cmrr.getRequest().setAgentId(agentId);
		cmrr.getRequest().setCustomerInfo(oFactory.createCustomerType());
		cmrr.getRequest().getCustomerInfo().setAgentId(agentId);
		cmrr.getRequest().getCustomerInfo().setAttributes(attributes);

		// *******************************
		// ** Set Customer Address List **
		// *******************************
		cmrr.getRequest().getCustomerInfo()
				.setAddressList(oFactory.createAddressListType());
		cmrr.getRequest().getCustomerInfo().getAddressList()
				.getCustomerAddress().add(address);

		CustomerManagementRequestResponse cmrrR = TransportConfig.INSTANCE.getCustomerClient().send(cmrr);

		List<CustomerType> customerTypeList = cmrrR.getResponse()
				.getCustomerInfo();

		CustomerType customerType = null;
		if ((customerTypeList != null) && (customerTypeList.size() > 0)) {
			if(cmrrR.getStatus() != null && cmrrR.getStatus().getStatusCode() != null 
					&& cmrrR.getStatus().getStatusMsg() != null)
			{
				//If Error come the Status Code will be greater than 0
				if(cmrrR.getStatus().getStatusCode() > 0 && cmrrR.getStatus().getStatusMsg().equalsIgnoreCase("ERROR"))
				{
					if(cmrrR.getStatus().getProcessingMessages() != null &&
							cmrrR.getStatus().getProcessingMessages().getMessage() != null)
					{
						for(ProcessingMessage message : cmrrR.getStatus().getProcessingMessages().getMessage())
						{
							errorList.add(new CartError(String.valueOf(message.getCode()), message.getText()));
						}
					}
				}
				
			}
			
			customerType = customerTypeList.get(0);
			
			//To remove deleted billing info's and corresponding payments
			List<String> inActiveBillingIdList = CustomerService.INSTANCE.filterInActiveBillingInfo(customerType);
			CustomerService.INSTANCE.filterInActivePayments(customerType, inActiveBillingIdList);
			
			customerType = CustomerFactory.INSTANCE.checkTitleCaseForCustomer(customerType);
			//Cache will be reset only if updateCustomer is successful
			if(errorList.size() == 0){
				CustomerInteractionCacheService.INSTANCE.clearCachedCustomerInteraction(customerId);
				CustomerCacheService.INSTANCE.clear(customerId);
				CustomerCacheService.INSTANCE.store(customerType, customerId);
			}
		}
		

		if ((customerType != null)
				&& (customerType.getCustomerInteractionList() != null)
				&& (customerType.getCustomerInteractionList()
						.getCustomerInteraction() != null)) {
			List<CustomerInteractionType> list = customerType
					.getCustomerInteractionList().getCustomerInteraction();

			CustomerInteractionCacheService.INSTANCE.storeCustomerInteraction(list, customerId,
					CustomerInteractionCacheService.CUSTOMER_INTERACTION_PREFIX);

		}
		return customerType;
	}

}
