package com.A.V;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.v4.AuthenticateCustomerRequest;
import com.A.xml.v4.AuthenticateCustomerResponse;
import com.A.xml.v4.CreditQualificationRequest;
import com.A.xml.v4.CreditQualificationResponse;
import com.A.xml.v4.OrderManagementRequestResponse;
import com.A.xml.v4.OrderProvisioningRequest;
import com.A.xml.v4.OrderProvisioningResponse;
import com.A.xml.v4.TransactionType;

public class JaxBUtilTest {

	private static final String PAYLOAD_START = "<ac:payload>";
	private static final int PAYLOAD_START_LENGTH = PAYLOAD_START.length();
	private static final JaxbUtil<OrderProvisioningRequest> opRequestUtil = new JaxbUtil<OrderProvisioningRequest>();
	private static final JaxbUtil<OrderProvisioningResponse> opResponseUtil = new JaxbUtil<OrderProvisioningResponse>();
	private static final Class[] reqClassesToBeBound = { AuthenticateCustomerRequest.class,
			CreditQualificationRequest.class };
	private static final Class[] respClassesToBeBound = { AuthenticateCustomerResponse.class,
			CreditQualificationResponse.class };

	private static final JaxbUtil<OrderManagementRequestResponse> util = new JaxbUtil<OrderManagementRequestResponse>();

	public String extract(String orderRR) {
		int indexStart = orderRR.indexOf(PAYLOAD_START) + PAYLOAD_START_LENGTH;
		int indexEnd = orderRR.indexOf("</ac:payload>");

		if ((indexStart != -1) && (indexEnd != -1)) {
			return orderRR.substring(indexStart, indexEnd);
		}
		return orderRR;

	}

	@Test
	public void testReadOrder() {
		try {
			String s = FileUtils
					.readFileToString(new File(".\\src\\test\\resources\\xml\\orderResponse.xml"));
			s = extract(s);
			assertNotNull(s);
			OrderManagementRequestResponse response = util.toObject(s, OrderManagementRequestResponse.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testOpRequest() {
		JaxbUtil<OrderProvisioningRequest> oputil = new JaxbUtil<OrderProvisioningRequest>();
		OrderProvisioningRequest request = new OrderProvisioningRequest();
		request.setProviderExternalId("342256");
		AuthenticateCustomerRequest authReq = new AuthenticateCustomerRequest();
		authReq.setAccountHolderName("jacob holl");
		request.setRequest(authReq);
		String req = oputil.toStringWithBoundClass(request, OrderProvisioningRequest.class,
				AuthenticateCustomerRequest.class);
		System.out.println(req);
		OrderProvisioningRequest request2 = oputil.toObject(req, OrderProvisioningRequest.class);
		AuthenticateCustomerRequest authReq2 = (AuthenticateCustomerRequest) request2.getRequest();
		System.out.println(authReq2.getAccountHolderName());
		System.out.println(request2.getProviderExternalId());
	}

	@Test
	public void TestOpRequest2() {
		OrderProvisioningRequest opRequest = new OrderProvisioningRequest();
		opRequest.setProviderExternalId("24699452");
		opRequest.setCorrelationId("bc5c8e0f-e206-45a9-ad32-test");
		opRequest.setTransactionType(TransactionType.AUTHENTICATE_CUSTOMER);
		opRequest.setAgentId("default");
		AuthenticateCustomerRequest authReq = new AuthenticateCustomerRequest();
		authReq.setAccountHolderName("jacob holl");
		authReq.setAccountNumber("34456456456");
		opRequest.setRequest(authReq);
		String opRequestAsString = opRequestUtil.toStringWithBoundClass(opRequest,
				OrderProvisioningRequest.class, reqClassesToBeBound);
		System.out.println(opRequestAsString);
	}

	@Test
	public void testOpResponse() {
		try {
			String s = FileUtils.readFileToString(new File(
					".\\src\\test\\resources\\xml\\authenticateCustomerResponse.xml"));
			s = extract(s);
			System.out.println(s);
			OrderProvisioningResponse opResponse = opResponseUtil.toObjectWithBoundClass(s,
					OrderProvisioningResponse.class, respClassesToBeBound);
			System.out.println(opResponse.getResponseStatus().getStatus());
			AuthenticateCustomerResponse authResponse = (AuthenticateCustomerResponse) opResponse
					.getResponse();
			System.out.println(authResponse.getProviderAttributes().get(0).getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testOpResponse2() {
		try {
			String s = FileUtils.readFileToString(new File(
					".\\src\\test\\resources\\xml\\authenticateCustomerResponse2.xml"));
			s = extract(s);
			System.out.println(s);
			OrderProvisioningResponse opResponse = opResponseUtil.toObjectWithBoundClass(s,
					OrderProvisioningResponse.class, respClassesToBeBound);
			System.out.println(opResponse.getResponseStatus().getStatus());
			AuthenticateCustomerResponse authResponse = (AuthenticateCustomerResponse) opResponse
					.getResponse();
			if (authResponse != null) {
				System.out.println(authResponse.getProviderAttributes().get(0).getName());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
