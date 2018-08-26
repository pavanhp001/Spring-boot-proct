package com.A.V.jms;

import org.junit.Test;

import com.A.V.gateway.jms.OrderProvisioningClientJMS;
import com.A.xml.v4.AuthenticateCustomerRequest;
import com.A.xml.v4.OrderProvisioningRequest;
import com.A.xml.v4.TransactionType;

public class OrderProvisioningClientJMSTest {

	@Test
	public void testSendOpRequest() {
		OrderProvisioningClientJMS opjms = new OrderProvisioningClientJMS();
		OrderProvisioningRequest request = new OrderProvisioningRequest();
		request.setProviderExternalId("");
		request.setCorrelationId("test2-test");
		request.setTransactionType(TransactionType.AUTHENTICATE_CUSTOMER);
		request.setAgentId("default");
		AuthenticateCustomerRequest authReq = new AuthenticateCustomerRequest();
		authReq.setAccountHolderName("jacob holl");
		authReq.setAccountNumber("34456456456");
		request.setRequest(authReq);
		opjms.send(request);
	}

}
