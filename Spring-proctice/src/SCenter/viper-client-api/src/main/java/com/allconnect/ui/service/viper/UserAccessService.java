package com.A.ui.service.V;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringEscapeUtils;

import com.A.ui.template.AuthenticateUserTemplateConstant;
import com.A.V.gateway.jms.UAMClientJMS;
import com.A.vo.UserAuthorization;
import com.A.xml.uam.v4.ValidatedUsers;

public enum UserAccessService {

	INSTANCE;

	private static final String END_POINT_NAME = "endpoint.uam.in";

	public UserAuthorization authenticate(String user,
			String credentials) {

		UAMClientJMS jmsClient = new UAMClientJMS();
		
		user = StringEscapeUtils.escapeXml(user);
		user = java.util.regex.Matcher.quoteReplacement(user);
		
		credentials = StringEscapeUtils.escapeXml(credentials);
		credentials = java.util.regex.Matcher.quoteReplacement(credentials);

		String template = getAuthenticateRequest(user, credentials);

		UserAuthorization userAuthorization = jmsClient.send("jms",
				END_POINT_NAME, template);

		return userAuthorization;

	}

	public static String getAuthenticateRequest(final String user,
			final String password) {

		String GUID = UUID.randomUUID().toString();

		String template = AuthenticateUserTemplateConstant.INSTANCE.UAM_JMS;

		template = template.replaceFirst("#!USERNAME!#", user)
				.replaceFirst("#!CREDENTIAL!#", password)
				.replaceFirst("#!GUID!#", GUID);

		return template;

	}
	
	public List<ValidatedUsers> validateUsers(Set<String> userLogins) {

		UAMClientJMS jmsClient = new UAMClientJMS();
		
		List<String> escapedXMLUserLoginsList = new ArrayList<String>();
		
		for(String user : userLogins){
			user = StringEscapeUtils.escapeXml(user);
			user = java.util.regex.Matcher.quoteReplacement(user);
			escapedXMLUserLoginsList.add(user);
		}

		String GUID = UUID.randomUUID().toString();
		String template = AuthenticateUserTemplateConstant.INSTANCE.getUAMJMSWithUserLoginList(escapedXMLUserLoginsList, GUID);
		
		System.out.println("ValidateUser::"+template);

		List<ValidatedUsers> validatedUsers = jmsClient.sendValidateUsers("jms",
				END_POINT_NAME, template);

		return validatedUsers;
	}

}
