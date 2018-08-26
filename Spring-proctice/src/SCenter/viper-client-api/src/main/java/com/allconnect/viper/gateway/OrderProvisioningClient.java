package com.A.V.gateway;

import com.A.xml.v4.OrderProvisioningRequest;
import com.A.xml.v4.OrderProvisioningResponse;

public interface OrderProvisioningClient {

	String extract(String opRequest);

	OrderProvisioningResponse send(OrderProvisioningRequest opRequest);

}
