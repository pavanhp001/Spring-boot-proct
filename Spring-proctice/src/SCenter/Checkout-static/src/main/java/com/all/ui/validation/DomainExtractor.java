package com.AL.ui.validation;

import java.util.List;
import com.AL.xml.v4.AccountType;
import com.AL.xml.v4.AddressType;
import com.AL.xml.v4.CustBillingInfoType;
import com.AL.xml.v4.CustomerFinancialInfoType;
import com.AL.xml.v4.CustomerType;
import com.AL.xml.v4.CustomizationType;
import com.AL.xml.v4.DriverLicenseType;
import com.AL.xml.v4.Order;
import com.AL.xml.v4.PaymentEventType;
import com.AL.xml.v4.StateIdType;

public class DomainExtractor {

	private Order order;
	private CustomerType customerType;

	public DomainExtractor(Order order) {

	}

	public DomainExtractor(CustomerType customer) {

	}

	public DomainExtractor(Order order, CustomerType customer) {

	}

	public CustomerType getCustomer() {

		if (customerType != null) {
			return customerType;
		}

		if ((order != null) && (order.getCustomerInformation() != null)) {
			return order.getCustomerInformation().getCustomer();
		}

		return null;
	}

	public AddressType getCurrentAddress() {
		// TODO: examine the role in the address; extract from the customer the
		// address with the required role.
		return null;
	}

	public AddressType getBillingAddress() {
		// TODO: examine the role in the address; extract from the customer the
		// address with the required role.
		return null;
	}

	public AddressType getPreviousAddress() {
		// TODO: examine the role in the address; extract from the customer the
		// address with the required role.
		return null;
	}

	public AddressType getMailingAddress() {
		// TODO: examine the role in the address; extract from the customer the
		// address with the required role.
		return null;
	}

	public AddressType getHomeAddress() {
		// TODO: examine the role in the address; extract from the customer the
		// address with the required role.
		return null;
	}

	public AddressType getShippingAddress() {
		// TODO: examine the role in the address; extract from the customer the
		// address with the required role.
		return null;
	}

	public AddressType getCorrectedAddress() {
		// TODO: examine the role in the address; extract from the customer the
		// address with the required role.
		return null;
	}

	public AddressType getDTAddress() {
		// TODO: examine the role in the address; extract from the customer the
		// address with the required role.
		return null;
	}

	public AddressType getServiceAddress() {
		return null;
	}

	public PaymentEventType getPaymentEvent() {
		return null;
	}
	
	public AccountType getAccountInfo(final String provider) {
		
		this.getCustomer().getAccounts().getAccount();
		
		return null;
	}

	public List<CustomizationType> getProductCustomizations(final String externalId) {
		return null;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	
	public DriverLicenseType getDriverLicense() {
		
		//CHECK FOR NULL
		//CHECK ORDER.
		
	    return getCustomer().getDriverLicense();
		
		//return null;
	}
	
	public StateIdType getStateId() {
		return null;
	}

	
	public CustBillingInfoType getBillingInformation() {
		return null;
	}
	
	public CustomerFinancialInfoType getFinancialInformation()
	{
		return null;
	}
	

}
