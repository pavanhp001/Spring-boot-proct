package com.AL.ui.activity.order.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.ui.activity.order.SaveOrderAndCustomerActivity;
import com.AL.ui.intent.Intent;
import com.AL.ui.service.V.UICartCustomerService;
import com.AL.ui.service.V.impl.UICartOrderServiceDefault;
import com.AL.ui.vo.ErrorList;
import com.AL.xml.cm.v4.AddressListType;
import com.AL.xml.cm.v4.AddressType;
import com.AL.xml.cm.v4.BillingInfoList;
import com.AL.xml.cm.v4.CustAddress;
import com.AL.xml.cm.v4.CustBillingInfoType;
import com.AL.xml.cm.v4.CustomerType;
import com.AL.xml.cm.v4.ObjectFactory;
import com.AL.xml.cm.v4.RoleType;
import com.AL.xml.cm.v4.CustAddress.AddressRoles;
import com.AL.xml.v4.Customer;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;

@Component
public class SaveOrderAndCustomerActivityDefault  implements SaveOrderAndCustomerActivity{

	@Autowired
	private UICartCustomerService custService;

	@Autowired
	private UICartOrderServiceDefault orderService;

	public void startActivity(Intent intent) {

		String agentId =  intent.getStringExtra("agentId");
		String[] roles =   intent.getStringArrayExtra("roles");

		AddressType address = (AddressType)intent.getExtras().get("address");
		CustomerType customerType = (CustomerType) intent.getExtras().get("customerType");

		String customerExtId = customerType.getExternalId().longValue() + "";
		ObjectFactory oFactory = new ObjectFactory();
		CustomerType custType=oFactory.createCustomerType();
		if(customerExtId.equals("0")){
			custType = createCustomer(agentId,customerType, address, roles);
		}else{
			custType =	custService.get(agentId,customerExtId, new ArrayList<String>(), new ErrorList());
		}

		SalesContextType salesContextType = new SalesContextType();

		SalesContextEntityType salesContextEntityType = new SalesContextEntityType();
		salesContextEntityType.setName("source");

		NameValuePairType nameValuePairType = new NameValuePairType();
		nameValuePairType.setName("source");
		nameValuePairType.setValue("accord");		
		salesContextEntityType.getAttribute().add(nameValuePairType);
		salesContextType.getEntity().add(salesContextEntityType);

		OrderType orderType = new OrderType();
		orderType.setExternalId(0L);
		orderType.setSource("Web");
		orderType.setReferrerId("108");
		orderType.setAgentId(agentId);
		Customer customer = new Customer();
		customer.setExternalId(custType.getExternalId());

		OrderType order = orderService.createOrderAndCustomer(agentId, salesContextType, orderType, customer, new ArrayList<String>(), new ErrorList());

		intent.getExtras().put("order", order);
	}
	private CustomerType createCustomer(String agentId, CustomerType customerType, AddressType address, String[] roles){
		String addressUniqueId = customerType.getLastName()+"-addr";
		String billingUniqueId = customerType.getLastName()+"-payinfo";

		ObjectFactory oFactory = new ObjectFactory();
		BillingInfoList billingInfoList = oFactory.createBillingInfoList();
		CustBillingInfoType custBillingInfoType = oFactory.createCustBillingInfoType();

		customerType.setReferrerId(1123L);
		customerType.setAgentId(agentId);

		custBillingInfoType.setBillingUniqueId(billingUniqueId);
		custBillingInfoType.setAddressRef(addressUniqueId);

		billingInfoList.getBillingInfo().add(custBillingInfoType);

		customerType.setExternalId(0L);

		AddressListType addressList = oFactory.createAddressListType();
		CustAddress custAddress = oFactory.createCustAddress();
		AddressRoles addrRoles = oFactory.createCustAddressAddressRoles();
		for (String role : roles) {
			custAddress.setAddressRoles(addrRoles);
			addrRoles.getRole().add(RoleType.valueOf(role));
		}

		custAddress.setStatus("active");
		custAddress.setAddressUniqueId(addressUniqueId);
		custAddress.setAddress(address);
		addressList.getCustomerAddress().add(custAddress);

		customerType.setBillingInfoList(billingInfoList);
		customerType.setAddressList(addressList);

		CustomerType customer = custService.createCustomer(agentId,customerType,  new ArrayList<String>(), new ErrorList());
		return customer;
	}

}
