package com.A.V.gateway;

import java.util.List;
import java.util.Map;

import com.A.xml.cm.v4.AccountHolderSearch;
import com.A.xml.cm.v4.CustomerManagementRequestResponse;
import com.A.xml.cm.v4.CustomerSearch;
import com.A.xml.cm.v4.CustomerType;

public interface CustomerClient {

	CustomerManagementRequestResponse send(CustomerManagementRequestResponse order);

	String extract(String orderRR);
	
	List<CustomerType> locateCustomer(Map<String, String> map);
	
	CustomerSearch searchCustomer(Map<String, String> map, int offset);
	
	void sendAsync(CustomerManagementRequestResponse cmrr);
	
	AccountHolderSearch searchAccntCustomerInfo(Map<String, String> map, int offset);
}
