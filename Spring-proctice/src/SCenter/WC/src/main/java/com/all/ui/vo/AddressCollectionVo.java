package com.A.ui.vo;

import java.util.ArrayList;
import java.util.List;
import com.A.xml.v4.CustAddress;
import com.A.xml.v4.Customer;
import com.A.xml.v4.RoleType;

public class AddressCollectionVo {

	private Customer customer;

	public AddressCollectionVo(Customer customer) {
		this.customer = customer;
	}

	public List<CustAddress> getAddressList() {

		List<CustAddress> customerAddressList = null;

		if ((customer != null) && (customer.getAddressList() != null)
				&& (customer.getAddressList().getCustomerAddress() != null)) {

			customerAddressList = customer.getAddressList()
					.getCustomerAddress();
		}

		return customerAddressList;
	}

	public CustAddress getAddressByRole(final String filterRole) {

		List<CustAddress> customerAddressList = getAddressList();

		CustAddress selectedAddress = null;

		if ((customerAddressList != null) && (customerAddressList.size() > 0)) {

			// default address
			selectedAddress = customerAddressList.get(0);
			searchRole: for (CustAddress addr : customerAddressList) {
				for (RoleType role : addr.getAddressRoles().getRole()) {

					if (role.name().toString().equals(filterRole)) {
						selectedAddress = addr;
						break searchRole;
					}
				}
			}
		}

		return selectedAddress;
	}

	public static  CustAddress getAddressByRef(final List<CustAddress> customerAddressList, final String addressRef) {

		 
		CustAddress finalAddress = null;

		if ((addressRef != null) && (customerAddressList != null)
				&& (customerAddressList.size() > 0)) {

			searchLoop: for (CustAddress addr : customerAddressList) {

				if (addr.getAddressUniqueId().equals(addressRef)) {
					finalAddress = addr;
					break searchLoop;
				}

			}
		}

		return finalAddress;
	}

	public List<CustAddress> getAddressListByRole(final String filterRole) {

		List<CustAddress> customerAddressList = getAddressList();
		List<CustAddress> finalAddressList = new ArrayList<CustAddress>();

		if ((customerAddressList != null) && (customerAddressList.size() > 0)) {

			for (CustAddress addr : customerAddressList) {
				for (RoleType role : addr.getAddressRoles().getRole()) {

					if (role.name().toString().equals(filterRole)) {

						finalAddressList.add(addr);
					}
				}
			}
		}

		return finalAddressList;
	}

	public CustAddress getCurrentAddress() {
		return getAddressByRole("CurrentAddress");
	};

	public CustAddress getServiceAddress() {
		return getAddressByRole("ServiceAddress");
	};

	public CustAddress getBillingAddress() {
		return getAddressByRole("BillingAddress");
	};

	public CustAddress getPreviousAddress() {
		return getAddressByRole("PreviousAddress");
	};

	public CustAddress getMailingAddress() {
		return getAddressByRole("MailingAddress");
	};

	public CustAddress getHomeAddress() {
		return getAddressByRole("HomeAddress");
	};

	public CustAddress getShippingAddress() {
		return getAddressByRole("ShippingAddress");
	};

	public CustAddress getCorrectedAddress() {
		return getAddressByRole("CorrectedAddress");
	};

	public CustAddress getDTAddress() {
		return getAddressByRole("DTAddress");
	};

	public List<CustAddress> getCurrentAddressList() {
		return getAddressListByRole("CurrentAddress");
	};

	public List<CustAddress> getServiceAddressList() {
		return getAddressListByRole("ServiceAddress");
	};

	public List<CustAddress> getBillingAddressList() {
		return getAddressListByRole("BillingAddress");
	};

	public List<CustAddress> getPreviousAddressList() {
		return getAddressListByRole("PreviousAddress");
	};

	public List<CustAddress> getMailingAddressList() {
		return getAddressListByRole("MailingAddress");
	};

	public List<CustAddress> getHomeAddressList() {
		return getAddressListByRole("HomeAddress");
	};

	public List<CustAddress> getShippingAddressList() {
		return getAddressListByRole("ShippingAddress");
	};

	public List<CustAddress> getCorrectedAddressList() {
		return getAddressListByRole("CorrectedAddress");
	};

	public List<CustAddress> getDTAddressList() {
		return getAddressListByRole("DTAddress");
	};

}
