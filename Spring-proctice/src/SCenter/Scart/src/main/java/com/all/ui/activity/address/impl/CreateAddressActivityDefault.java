package com.AL.ui.activity.address.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.ui.activity.address.CreateAddressActivity;
import com.AL.ui.dao.WebLookupDao;
import com.AL.ui.domain.WebLookupCollection;
import com.AL.ui.intent.Intent;
import com.AL.ui.service.V.UICartCustomerService;
import com.AL.ui.vo.ErrorList;
import com.AL.xml.cm.v4.CustAddress;
import com.AL.xml.cm.v4.CustomerType;

@Component
public class CreateAddressActivityDefault implements CreateAddressActivity{

	@Autowired
	private UICartCustomerService customerService;

	@Autowired
	private WebLookupDao lookupDao;

	public void startActivity(Intent intent) {
		String agentId = intent.getAsString("agentId");
		String id = intent.getAsString("id");
		String addressId = intent.getAsString("addressId");
		
		CustomerType customer = customerService.get(agentId, id, new ArrayList<String>(), new ErrorList());

		List<CustAddress> customerAddressList = null;
		if ((customer != null) && (customer.getAddressList() != null) && (customer.getAddressList().getCustomerAddress() != null)) {
			customerAddressList = customer.getAddressList().getCustomerAddress();
		}

		CustAddress selectedAddress = null;
		if ((!("0".equals(addressId))) && (customerAddressList != null) && (customerAddressList.size() > 0)) {
			selectedAddress = customerAddressList.get(0);
			for (CustAddress addr : customerAddressList) {
				if (addr.getAddress().getExternalId() == Long.valueOf(addressId)) {
					selectedAddress = addr;
					break;
				}
			}
		}
		WebLookupCollection countries = lookupDao.findCountries();
		WebLookupCollection states = lookupDao.findUSStates();
		
		intent.getExtras().put("customer", customer);
		intent.getExtras().put("selectedAddress", selectedAddress);
		intent.getExtras().put("countries", countries);
		intent.getExtras().put("states", states);
	}

}
