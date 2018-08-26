package com.AL.ui.service.V.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.AL.ui.builder.AddressBuilder;
import com.AL.ui.service.V.UICartCustomerService;
import com.AL.ui.transport.TransportConfig;
import com.AL.ui.vo.CartError;
import com.AL.ui.vo.ErrorList;
import com.AL.xml.cm.v4.AddressType;
import com.AL.xml.cm.v4.CustAddress;
import com.AL.xml.cm.v4.CustomerManagementRequestResponse;
import com.AL.xml.cm.v4.CustomerType;
import com.AL.xml.cm.v4.ObjectFactory;

@Component
public class UICartCustomerServiceDefault implements UICartCustomerService {
	
	public CustomerType saveIdentification(final String agentId, final String id, final CustomerType customer, final List<String> credentials, final ErrorList errors) {
		return updateCustomer(agentId, id, customer, credentials, errors);
	}
	
	public CustomerType saveName(final String agentId, final String id, final CustomerType customer, final List<String> credentials, final ErrorList errors) {
		return updateCustomer(agentId, id, customer, credentials, errors);
	}
	
	public CustomerType saveContactInfo(final String agentId, final String id, final CustomerType customer, final List<String> credentials, final ErrorList errors) {
		return updateCustomer(agentId, id, customer, credentials, errors);
	}

	public CustomerType saveFinancialInfo(final String agentId, final String id, final CustomerType customer, final List<String> credentials, final ErrorList errors) {
		return updateCustomer(agentId, id, customer, credentials, errors);
	}

	public CustomerType saveOptions(final String agentId, final String id, final CustomerType customer, final List<String> credentials, final ErrorList errors) {
		return updateCustomer(agentId, id, customer, credentials, errors);
	}
	
	public CustomerType saveBillingInfo(final String agentId, final String id, final CustomerType customer, final List<String> credentials, final ErrorList errors) {
		return updateCustomer(agentId, id, customer, credentials, errors);
	}
	
	public CustomerType savePaymentEvent(final String agentId, final String id, final CustomerType customer, final List<String> credentials, final ErrorList errors) {
		return updateCustomer(agentId, id, customer, credentials, errors);
	}
	
	public CustomerType get(final String agentId, final String id, List<String> credentials, ErrorList errors) {

		CustomerType ct = CustomerCacheService.INSTANCE.get(id);
		if (ct != null) {
			return ct;
		}

		ObjectFactory oFactory = new ObjectFactory();
		CustomerManagementRequestResponse cmrr = getCMRR(oFactory, "getCustomerById");
		
		if (id != null) {
			cmrr.getRequest().setCustomerId(id);
		}
		cmrr.getRequest().setAgentId(agentId);

		CustomerManagementRequestResponse cmrrR = TransportConfig.INSTANCE.getCustomerClient().send(cmrr);
		logCMRRResponse(cmrrR);

		CustomerType customer = null;

		if(cmrrR == null || cmrrR.getResponse() == null){
			errors.add(new CartError("", "empty.cmrrr", "Null CustomerManagementRequestResponse"));
		} else {
			List<CustomerType> customerTypeList = cmrrR.getResponse().getCustomerInfo();
			if (customerTypeList != null && customerTypeList.size() > 0) {
				customer = customerTypeList.get(0);
				CustomerCacheService.INSTANCE.store(customer, id);
			} else {
				errors.add(new CartError("", "empty.customer", "Customer doesn't exist"));
			}
		}

		return customer;
	}
	
	public CustomerType createCustomer(final String agentId, final CustomerType custInfo, final List<String> credentials,	final ErrorList errors) {

		ObjectFactory oFactory = new ObjectFactory();
		
		CustomerManagementRequestResponse cmrr = getCMRR(oFactory, "createCustomer");
		
		cmrr.getRequest().setCustomerId("0");
		cmrr.getRequest().setCustomerNumber("0");
		cmrr.getRequest().setCustomerInfo(custInfo);
		cmrr.getRequest().setAgentId(agentId);

		CustomerManagementRequestResponse cmrrR = TransportConfig.INSTANCE.getCustomerClient().send(cmrr);
		logCMRRResponse(cmrrR);

		CustomerType customer = null;

		if(cmrrR == null || cmrrR.getResponse() == null){
			errors.add(new CartError("", "empty.cmrrr", "Null CustomerManagementRequestResponse"));
		} else {
			List<CustomerType> customerTypeList = cmrrR.getResponse().getCustomerInfo();
			if (customerTypeList != null && customerTypeList.size() > 0) {
				customer = customerTypeList.get(0);
				CustomerCacheService.INSTANCE.store(customer, customer.getExternalId()+ "");
			} else {
				errors.add(new CartError("", "empty.customer", "Customer doesn't exist"));
			}
		}
		
		return customer;
	}
	
	public List<CustomerType> locate(final  Map<String, String> criteria, List<String> credentials, ErrorList errors){
		
		Set<String> keys = criteria.keySet();

		for (String key : keys) {
			String data = criteria.get(key);
			if ((data == null) || (data.length() == 0)) {
				criteria.put(key, "");
			}
		}

		List<CustomerType> customers = TransportConfig.INSTANCE.getCustomerClient().locateCustomer(criteria);
		if (customers != null && customers.size() > 0) {
			return customers;
		}else {
			errors.add(new CartError("", "empty.customers", "No Customers Exist"));
			return null;
		}
	}

	public CustomerType saveAddress(final String agentId, final String custId, final CustAddress address, final List<String> credentials, final ErrorList errors)  {

		ObjectFactory oFactory = new ObjectFactory();

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

	public CustomerType saveAddressUpdate(final String agentId, final String custId, String[] roles, final String addressId,
			final String addressUniqueId, final AddressType addressType, final String status, final List<String> credentials, final ErrorList errors) {

		ObjectFactory oFactory = new ObjectFactory();

		CustomerManagementRequestResponse cmrr = getCMRR(oFactory, "updateCustomer");

		final CustAddress address = AddressBuilder.INSTANCE.buildCustomerAddress(custId, roles, addressId,	addressUniqueId, addressType, status);

		CustomerCacheService.INSTANCE.clear(custId);

		if (custId != null) {
			cmrr.getRequest().setCustomerId(custId);
			cmrr.getRequest().setAgentId(agentId);
		}

		if(address != null){
			cmrr.getRequest().setCustomerInfo(oFactory.createCustomerType());
			cmrr.getRequest().getCustomerInfo().setAgentId(agentId);

			cmrr.getRequest().getCustomerInfo().setAddressList(oFactory.createAddressListType());
			cmrr.getRequest().getCustomerInfo().getAddressList().getCustomerAddress().add(address);
		}

		CustomerManagementRequestResponse cmrrR = TransportConfig.INSTANCE.getCustomerClient().send(cmrr);

		CustomerType customerType = null;
		if(cmrrR == null || cmrrR.getResponse() == null){
			errors.add(new CartError("", "empty.cmrrr", "Null CustomerManagementRequestResponse"));
		} else {
			List<CustomerType> customerTypeList = cmrrR.getResponse().getCustomerInfo();
			if (customerTypeList != null && customerTypeList.size() > 0) {
				customerType = customerTypeList.get(0);
				CustomerCacheService.INSTANCE.store(customerType, custId);
			} else {
				errors.add(new CartError("", "empty.customer", "Customer doesn't exist"));
			}
		}
		
		return customerType;
	}

	public CustomerType updateCustomer(final String agentId, final String id, final CustomerType customer, final List<String> credentials, final ErrorList errors) {

		CustomerCacheService.INSTANCE.clear(id);
		CustomerInteractionCacheService.INSTANCE.clearCachedCustomerInteraction(id);

		ObjectFactory oFactory = new ObjectFactory();
		CustomerManagementRequestResponse cmrr = getCMRR(oFactory, "updateCustomer");

		if (id != null) {
			cmrr.getRequest().setCustomerId(id);
		}

		if (customer != null) {
			customer.setAgentId(agentId);
			cmrr.getRequest().setCustomerInfo(customer);
		}

		CustomerManagementRequestResponse cmrrR = TransportConfig.INSTANCE.getCustomerClient().send(cmrr);
		logCMRRResponse(cmrrR);

		CustomerType customerType = null;

		if(cmrrR == null || cmrrR.getResponse() == null){
			errors.add(new CartError("", "empty.cmrrr", "Null CustomerManagementRequestResponse"));
		} else {
			List<CustomerType> customerTypeList = cmrrR.getResponse().getCustomerInfo();
			if (customerTypeList != null && customerTypeList.size() > 0) {
				customerType = customerTypeList.get(0);
				CustomerCacheService.INSTANCE.store(customerType, id);
			} else {
				errors.add(new CartError("", "empty.customer", "Customer doesn't exist"));
			}
		}
		
		return customerType;
	}
	
	public CustomerManagementRequestResponse getCMRR(ObjectFactory oFactory, String transactionType){
		CustomerManagementRequestResponse cmrr = oFactory.createCustomerManagementRequestResponse();
		cmrr.setRequest(oFactory.createCustomerManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType(transactionType);
		
		return cmrr;
	}
	
	public void logCMRRResponse(CustomerManagementRequestResponse cmmRR){
		//JaxbUtil<CustomerManagementRequestResponse> utilCust = new JaxbUtil<CustomerManagementRequestResponse>();
	}

}
