package com.A.V.gateway.file;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.A.V.gateway.soap.OrderClientSoap;
import com.A.xml.v4.OrderManagementRequestResponse;
import com.A.xml.v4.OrderManagementRequestResponse.Request;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class VOmeSearchTest {

	@Test
	public void testOmeGetOrderByNumber() {
		
		String uuid = UUID.randomUUID().toString();

		OrderManagementRequestResponse omRR = new OrderManagementRequestResponse();
		omRR.setRequest(new Request());
		omRR.getRequest().setOrderId("6524");
		omRR.setCorrelationId(uuid);
		omRR.setTransactionType("getOrder");

		OrderClientSoap orderClientSoap = new OrderClientSoap();
		OrderManagementRequestResponse document = orderClientSoap.send(omRR);

		assertEquals(6524, document.getResponse().getOrderInfo().get(0).getExternalId());

	}
	
	@Test
	public void testOmeGetOrderByConfirmation() {
		
//		String uuid = UUID.randomUUID().toString();
//
//		OrderManagementRequestResponse omRR = new OrderManagementRequestResponse();
//		omRR.setRequest(new Request());
//		omRR.getRequest().setConfirmationNumber("AC-1312806755603156");
//		omRR.setCorrelationId(uuid);
//		omRR.setTransactionType("getOrderByConfirmationNumber");
//
//		OrderClientSoap orderClientSoap = new OrderClientSoap();
//		OrderManagementRequestResponse response = orderClientSoap.send(omRR);
//
//		assertEquals(uuid, response.getCorrelationId());

	}
	
	@Test
	public void testOmeGetOrderByStatusReason() {
		
//		String uuid = UUID.randomUUID().toString();
//
//		OrderManagementRequestResponse omRR = new OrderManagementRequestResponse();
//		omRR.setRequest(new Request());
//		omRR.getRequest().setConfirmationNumber("AC-1312806755603156");
//		omRR.setCorrelationId(uuid);
//		omRR.setTransactionType("getOrderByStatus");
//		
//		omRR.getRequest().getReason().add("5001");
//		omRR.getRequest().getStatus().add("status");
//		
//
//		OrderClientSoap orderClientSoap = new OrderClientSoap();
//		OrderManagementRequestResponse response = orderClientSoap.send(omRR);
//
//		assertEquals(uuid, response.getCorrelationId());

	}
	
	 

}
