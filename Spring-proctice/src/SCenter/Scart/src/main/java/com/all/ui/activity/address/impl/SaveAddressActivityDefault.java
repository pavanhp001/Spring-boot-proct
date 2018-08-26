package com.AL.ui.activity.address.impl;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.ui.activity.address.SaveAddressActivity;
import com.AL.ui.factory.CartAddressFactory;
import com.AL.ui.intent.Intent;
import com.AL.ui.service.V.AddressService;
import com.AL.ui.service.V.UICartCustomerService;
import com.AL.ui.vo.ErrorList;
import com.AL.xml.cm.v4.AddressType;
import com.AL.xml.cm.v4.CustAddress;
import com.AL.xml.cm.v4.CustomerType;

@Component
public class SaveAddressActivityDefault implements SaveAddressActivity {

	@Autowired
	private UICartCustomerService custService;
	
	public void startActivity(Intent intent) {

		String agentId =  intent.getAsString("agentId");
		String addressId =  intent.getAsString("addressId");
		String status =  intent.getAsString("status");
		String addressUniqueId =  intent.getAsString("addressUniqueId");
		String customerId =  intent.getAsString("customerId");
		String[] roles =  intent.getAsStringArray("roles");
		AddressType address = intent.getAsAddressType("address");
		
		CustomerType customer = null;
		if ("0".equals(addressId)) {
			CustAddress custAddress = CartAddressFactory.INSTANCE.addCustAddress(status, addressUniqueId, roles, address);

			customer = custService.saveAddress(agentId, customerId, custAddress, new ArrayList<String>(), new ErrorList());
		} else {
			customer = AddressService.INSTANCE.saveAddressUpdate(agentId, customerId, roles, addressId, addressUniqueId, address, "active");
		}
		
		intent.getExtras().put("customer", customer);
		
	}
	
}
