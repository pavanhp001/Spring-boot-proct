package com.AL.ui.factory;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.AL.controller.ajax.CartFeedbackOnStartController;
import com.AL.xml.cm.v4.AddressType;
import com.AL.xml.cm.v4.CustAddress;
import com.AL.xml.cm.v4.ObjectFactory;
import com.AL.xml.cm.v4.RoleType;
import com.AL.xml.cm.v4.CustAddress.AddressRoles;

/**
 * @author 
 *
 */
public enum CartAddressFactory {

	INSTANCE;
	
	private static final Logger logger = Logger.getLogger(CartAddressFactory.class);

	private ObjectFactory oFactory = new ObjectFactory();

	/**
	 * @param request
	 * @return AddressType
	 */
	public AddressType addAddress(HttpServletRequest request){

		String streetNumber = request.getParameter("streetNumber");
		String stateOrProvince = request.getParameter("stateOrProvince");
		String streetName = request.getParameter("streetName");
		String city = request.getParameter("city");
		String line2 = request.getParameter("line2");
		String postalCode = request.getParameter("postalCode");
		String country = request.getParameter("country");

		AddressType address = oFactory.createAddressType();
		address.setStreetNumber(streetNumber);
		address.setStateOrProvince(stateOrProvince);
		address.setStreetName(streetName);
		address.setCity(city);
		address.setLine2(line2);
		address.setPostalCode(postalCode);
		address.setCountry(country);

		return address;
	}
	
	/**
	 * @param status
	 * @param addressUniqueId
	 * @param roles
	 * @param address
	 * @return CustAddress
	 */
	public CustAddress addCustAddress(String status, String addressUniqueId, String[] roles, AddressType address){

		CustAddress custAddress = oFactory.createCustAddress();
		AddressRoles addrRoles = oFactory.createCustAddressAddressRoles();
		
		for (String role : roles) {
			if ((role != null) && (role.length() > 0)) {
				String cleanRole = resolveRole(role);
				RoleType rt = RoleType.fromValue(cleanRole);
				addrRoles.getRole().add(rt);
			}
		}
		custAddress.setAddressRoles(addrRoles);
		custAddress.setStatus(status);
		custAddress.setAddressUniqueId(addressUniqueId);
		custAddress.setAddress(address);

		return custAddress;
	}
	
	/**
	 * @param customerId
	 * @param roles
	 * @param addressId
	 * @param addressUniqueId
	 * @param addressType
	 * @param status
	 * @return CustAddress
	 */
	public CustAddress buildCustomerAddress(final String customerId,
			String[] roles, final String addressId,
			final String addressUniqueId, final AddressType addressType,
			final String status) {

		ObjectFactory oFactory = new ObjectFactory();

		final CustAddress custAddr = oFactory.createCustAddress();
		final AddressRoles addrRoles = oFactory.createCustAddressAddressRoles();

		custAddr.setAddress(addressType);
		custAddr.setAddressUniqueId(addressUniqueId);
		custAddr.setStatus(status);

		for (String role : roles) {

			custAddr.setAddressRoles(addrRoles);
			if ((role != null) && (role.length() > 0)) {

				String cleanRole = resolveRole(role);
				logger.debug(cleanRole);
				RoleType rt = RoleType.fromValue(cleanRole);

				addrRoles.getRole().add(rt);
			}
		}

		return custAddr;
	}
	
	 /**
	 * @param rawRole
	 * @return String
	 */
	private String resolveRole(final String rawRole) {

		if ((rawRole == null) || (rawRole.length() == 0)) {
			return rawRole;
		}

		if ("CURRENT_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "CurrentAddress";
		else if ("SERVICE_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "ServiceAddress";
		else if ("BILLING_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "BillingAddress";
		else if ("PREVIOUS_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "PreviousAddress";
		else if ("MAILING_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "MailingAddress";
		else if ("HOME_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "HomeAddress";
		else if ("SHIPPING_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "ShippingAddress";
		else if ("CORRECTED_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "CorrectedAddress";
		else if ("DT_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "DTAddress";

		return rawRole;

	}

}
