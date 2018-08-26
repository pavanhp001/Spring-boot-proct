package com.A.ui.builder;

import com.A.xml.cm.v4.AddressType;
import com.A.xml.cm.v4.CustAddress;
import com.A.xml.cm.v4.ObjectFactory;
import com.A.xml.cm.v4.RoleType;
import com.A.xml.cm.v4.CustAddress.AddressRoles;

public enum AddressBuilder {

	INSTANCE;
	
	

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

			//custAddr.setAddressRoles(addrRoles);
			if ((role != null) && (role.length() > 0) && (role != "")) {

				String cleanRole = resolveRole(role);
				 
				RoleType rt = RoleType.fromValue(cleanRole);

				addrRoles.getRole().add(rt);
			}
			custAddr.setAddressRoles(addrRoles);
		}

		return custAddr;
	}

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
