package com.AL.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.AL.xml.v4.AddressType;
import com.AL.xml.v4.Customer;

public enum OrderUtil 
{
	
	INSTANCE;
	
	/**
	 * 
	 */
	private static final Logger logger = Logger.getLogger(OrderUtil.class);
	
	/**
	 * @param customer
	 * @param key
	 * @return CustAddress
	 */
	private com.AL.xml.v4.AddressType getAddress(Customer customer, final String key) 
	{
		boolean isRole = Boolean.FALSE;
		if ((customer != null) && (customer.getAddressList() != null)) {
			List<com.AL.xml.v4.CustAddress> custAddressList = customer.getAddressList().getCustomerAddress();
			if (custAddressList != null) {
				for (com.AL.xml.v4.CustAddress custAddress : custAddressList) {
					if ((custAddress != null)
							&& (custAddress.getAddressRoles() != null)) {
						List<com.AL.xml.v4.RoleType> roleTypeList = custAddress.getAddressRoles().getRole();
						for (com.AL.xml.v4.RoleType roleType : roleTypeList) {
							if (key.equals(roleType.name())) {
								isRole = Boolean.TRUE;
								break;
							}
						}
						if (isRole) {
							return custAddress.getAddress();
						}
					}
				}
			}
		}
		return new com.AL.xml.v4.CustAddress().getAddress();
	}
	
	public String getAddress(Customer customer)
	{
		
		AddressType address = getAddress(customer,
				com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());
		
		logger.info("---------------- Building Formated Address ----------------");

		return  escapeNull(address.getStreetNumber(), false, true) +
				escapeNull(address.getStreetName(), true, false) +
				escapeNull(address.getCity(), true, false) +
				escapeNull(address.getStateOrProvince(), false, true) +
				escapeNull(address.getPostalCode(), false, false);
	}
	
	private String escapeNull(String value, boolean isCommaDelimeter, boolean isSpaceDelimeter)
	{
		String result = StringUtils.isBlank(value) ? "" : value.trim().toUpperCase();
		
		if(result.length() > 0)
		{
			if(isCommaDelimeter)
			{
				return result + ", ";
			}
			if(isSpaceDelimeter)
			{
				return result + " ";
			}
		}
		
		return result;
	}

}
