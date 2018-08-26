package com.A.V.gateway;

import com.A.xml.v4.OrderProvisioningRequest;
import com.A.xml.v4.OrderProvisioningResponse;

public interface AuthenticateCustClient {
	
	String extract(String orderRR);


	OrderProvisioningResponse send(OrderProvisioningRequest omrr);
}
