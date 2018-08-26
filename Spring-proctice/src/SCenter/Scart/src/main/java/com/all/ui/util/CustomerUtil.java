package com.AL.ui.util;

import java.util.List;

import com.AL.xml.v4.Customer;

public enum CustomerUtil {
	
	INSTANCE;
	
	public com.AL.xml.v4.CustAddress getSelectedAddress(String addressid, Customer customer) {

    	List<com.AL.xml.v4.CustAddress> customerAddressList = null;

    	if ((customer != null) && (customer.getAddressList() != null) && (customer.getAddressList().getCustomerAddress() != null)) {

    		customerAddressList = customer.getAddressList()
    		.getCustomerAddress();
    	}
    	com.AL.xml.v4.CustAddress selectedAddress = null;

    	if ((!("0".equals(addressid))) && (customerAddressList != null)	&& (customerAddressList.size() > 0)) {

    		selectedAddress = customerAddressList.get(0);

    		for (com.AL.xml.v4.CustAddress addr : customerAddressList) {
    			if (addr.getAddress().getExternalId() == Long
    					.valueOf(addressid)) {
    				selectedAddress = addr;
    				break;
    			}
    		}
    	}

    	return selectedAddress;
    }
	
	
	
	/**
	 * @param customer
	 * @param key
	 * @return CustAddress
	 */
	public com.AL.xml.v4.CustAddress getAddress(Customer customer, final String key) {
		boolean isRole = Boolean.FALSE;
		if ((customer != null) && (customer.getAddressList() != null)) {
			List<com.AL.xml.v4.CustAddress> custAddressList = customer.getAddressList().getCustomerAddress();
			if (custAddressList != null) {
				for (com.AL.xml.v4.CustAddress custAddress : custAddressList) {
					if ((custAddress != null)
							&& (custAddress.getAddressRoles() != null)) {
						List<com.AL.xml.v4.RoleType> roleTypeList = custAddress.getAddressRoles().getRole();
						for (com.AL.xml.v4.RoleType roleType : roleTypeList) {
							if (key.equals(roleType.value())) {
								isRole = Boolean.TRUE;
								break;
							}
						}
						if (isRole) {
							return custAddress;
						}
					}
				}
			}
		}
		return new com.AL.xml.v4.CustAddress();
	}
	
}
