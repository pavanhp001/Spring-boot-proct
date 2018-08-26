package com.A.V.gateway;

import com.A.xml.v4.OrderManagementRequestResponse;

public interface OrderClient {

	OrderManagementRequestResponse send(OrderManagementRequestResponse order);

	String extract(String orderRR);
	void sendAsync(OrderManagementRequestResponse req);
}
