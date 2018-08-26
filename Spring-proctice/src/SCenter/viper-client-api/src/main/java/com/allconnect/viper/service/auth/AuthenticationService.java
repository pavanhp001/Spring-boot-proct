package com.A.V.service.auth;

import java.util.Calendar;
import com.A.V.domain.User;
import com.A.V.gateway.jms.UAMClientJMS;
import com.A.vo.UserAuthorization;

public enum AuthenticationService {

	INSTANCE;
	
	private static final String END_POINT_NAME = "endpoint.uam.in";
	
	public UserAuthorization authenticate(String userid, String password){
		
		UAMClientJMS jmsClient = new UAMClientJMS();
		
		password = java.util.regex.Matcher.quoteReplacement(password);
		userid = java.util.regex.Matcher.quoteReplacement(userid);
		
		String template =   jmsClient.getAuthenticateRequest(userid, password);

		UserAuthorization userAuthorization = jmsClient.send("jms", END_POINT_NAME, template);
		
		return userAuthorization;
		

	}

	public User getUser(final String loginId) {

		User user = new User();
		user.setName("");
		user.setLoginId(loginId);
		user.setEnabled(Boolean.TRUE);
		user.setDateEffectiveFrom(Calendar.getInstance());
		user.setDateEffectiveTo(Calendar.getInstance());
		user.setId(System.currentTimeMillis());
		user.setLastLoginAt(Calendar.getInstance());
		user.setLoginAttempt(1);

		return user;

	}
	
	
	

}
