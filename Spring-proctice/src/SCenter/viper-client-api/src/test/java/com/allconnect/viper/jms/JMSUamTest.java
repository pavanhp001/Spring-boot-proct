package com.A.V.jms;


import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.A.ui.transport.TransportConfig;
import com.A.V.gateway.jms.UAMClientJMS;
import com.A.vo.UserAuthorization;
import com.A.xml.uam.v4.AuthenticateUserRequest;
import com.A.xml.uam.v4.AuthenticateUserRequestType;
import com.A.xml.uam.v4.Credential;
import com.A.xml.uam.v4.EnterpriseRequestDocumentType;
import com.A.xml.uam.v4.EnterpriseResponseDocumentType;
import com.A.xml.uam.v4.UamTransactionTypeType;

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/V-client-api-app-context.xml" })
public class JMSUamTest {
	

	private static final String END_POINT_NAME = "endpoint.uam.in";
	
	@Test
	public void testAuthenticationJMS() throws Exception {

		UAMClientJMS jmsClient = new UAMClientJMS();
		
		String template =   jmsClient.getAuthenticateRequest("ethomas1", "Password17");
		//String template =   jmsClient.getAuthenticateRequest("tgagguturu1", "Password16");
		
		assertNotNull(template);
		UserAuthorization userAuthorization = jmsClient.send("uam", END_POINT_NAME, template);

		assertNotNull("RESPONSE---->>>>>>" + userAuthorization.toString());

	}
	
	@Test
	public void testAuthentication() throws Exception {
		
		EnterpriseRequestDocumentType req = new EnterpriseRequestDocumentType();
		req.setGUID("smrOrder564");
		
		UamTransactionTypeType uamTransactionType = new UamTransactionTypeType();
		uamTransactionType.setUamTransactionType("authenticateUser");
		req.setTransactionType(uamTransactionType);
		
		AuthenticateUserRequestType request = new AuthenticateUserRequestType();
		AuthenticateUserRequest authenticateUserRequest = new AuthenticateUserRequest();
		
		authenticateUserRequest.setUserName("ethomas");
		
		Credential credential = new Credential();
		credential.setCredentialName("PasswordABCD");
		authenticateUserRequest.setCredentials(credential);
		
		Credential superCredential = new Credential();
		authenticateUserRequest.setSuperVisorCredentials(superCredential);
		request.setAuthenticateUserRequest(authenticateUserRequest);
		req.setRequest(request);

		EnterpriseResponseDocumentType response = TransportConfig.INSTANCE.getUAMClientJMS().send(req);
		
		assertNotNull(response);

	}
	
	
	

}
