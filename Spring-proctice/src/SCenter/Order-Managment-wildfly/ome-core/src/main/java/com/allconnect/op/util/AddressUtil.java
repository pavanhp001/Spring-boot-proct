package com.AL.op.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.AL.V.beans.entity.AddressBean;
import com.AL.xml.v4.AddressType;
import com.AL.xml.v4.CustAddress;
import com.AL.xml.v4.RoleType;


public class AddressUtil {
	
	private static final Logger logger = Logger.getLogger(AddressUtil.class);
	private static final String SPACE = " ";
	/**
	 * Convert address type to string.
	 *
	 * @param inputAddress the input address
	 * @return the string
	 */
	public static String convertAddressTypeToString(AddressType inputAddress) {
		logger.info("Creating address block");
		String streetName = inputAddress.getStreetName();
		String streetType = inputAddress.getStreetType();
		String streetDirection = inputAddress.getPrefixDirectional();
		String streetTrailingDirection = inputAddress.getPostfixDirectional();
		String streetNumber = inputAddress.getStreetNumber(); 
		String addressLine2 = inputAddress.getLine2();
		StringBuilder strAddress = new StringBuilder();
		if(!StringUtils.isEmpty(streetNumber)) {
			strAddress.append(streetNumber).append(SPACE);
		}

		if(!StringUtils.isEmpty(streetDirection)) {
			strAddress.append(streetDirection).append(SPACE);
		}

		if(!StringUtils.isEmpty(streetName)) {
			strAddress.append(streetName).append(SPACE);
		}

		if(!StringUtils.isEmpty(streetType)) {
			strAddress.append(streetType).append(SPACE);
		}

		if(!StringUtils.isEmpty(streetTrailingDirection)) {
			strAddress.append(streetTrailingDirection).append(SPACE);
		}

		if(!StringUtils.isEmpty(addressLine2)) {
			strAddress.append(addressLine2).append(SPACE);
		}
		strAddress.append(", ");
		strAddress.append(inputAddress.getCity()).append(SPACE);
		strAddress.append(inputAddress.getStateOrProvince()).append(SPACE);
		strAddress.append(inputAddress.getPostalCode());
		return strAddress.toString();
	}

	/**
	 * Convert address bean to string.
	 *
	 * @param inputAddress the input address
	 * @return the string
	 */
	public static String convertAddressBeanToString(AddressBean inputAddress) {
		logger.info("Creating address block");
		String streetName = inputAddress.getStreetName();
		String streetType = inputAddress.getStreetType();
		String streetDirection = inputAddress.getPrefixDirectional();
		String streetTrailingDirection = inputAddress.getPostfixDirectional();
		String streetNumber = inputAddress.getStreetNumber(); 
		String addressLine2 = inputAddress.getLine2();
		StringBuilder strAddress = new StringBuilder();
		if(!StringUtils.isEmpty(streetNumber)) {
			strAddress.append(streetNumber).append(SPACE);
		}

		if(!StringUtils.isEmpty(streetDirection)) {
			strAddress.append(streetDirection).append(SPACE);
		}

		if(!StringUtils.isEmpty(streetName)) {
			strAddress.append(streetName).append(SPACE);
		}

		if(!StringUtils.isEmpty(streetType)) {
			strAddress.append(streetType).append(SPACE);
		}

		if(!StringUtils.isEmpty(streetTrailingDirection)) {
			strAddress.append(streetTrailingDirection).append(SPACE);
		}

		if(!StringUtils.isEmpty(addressLine2)) {
			strAddress.append(addressLine2).append(SPACE);
		}
		strAddress.append(", ");
		strAddress.append(inputAddress.getCity()).append(SPACE);
		strAddress.append(inputAddress.getStateOrProvince()).append(SPACE);
		strAddress.append(inputAddress.getPostalCode());
		return strAddress.toString();
	}
	
	public static CustAddress getAddressByRole(List<CustAddress> custAddressList, RoleType.Enum role) {
		CustAddress billingAddress = null;
		for(CustAddress address : custAddressList) {
			if(address.getAddressRoles().getRoleList().contains(RoleType.BILLING_ADDRESS)) {
				billingAddress = address;
			}
		}
		return billingAddress;
	}

	

}
