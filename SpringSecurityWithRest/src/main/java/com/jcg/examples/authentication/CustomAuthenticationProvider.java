package com.jcg.examples.authentication;


import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider
{
	
		Log logger = LogFactory.getLog(CustomAuthenticationProvider.class);
		@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException
		{
				String userName = authentication.getName();
				String password = authentication.getCredentials().toString();
				System.out.println("Username "+userName);
				System.out.println("password "+password);
				logger.info("userName: "+userName);
				logger.info("password: "+password);
				if (authorizedUser(userName, password))
				{
					logger.info("authorizedUser: ");
						List<GrantedAuthority> grantedAuths = new ArrayList<>();
						grantedAuths.add(()-> {return "AUTH_USER";});
						Authentication auth = new UsernamePasswordAuthenticationToken(userName, password, grantedAuths);
						System.out.println(auth.getAuthorities());
						logger.info("auth.getAuthorities(): "+auth.getAuthorities());
						return auth;
				}
				else
				{
					logger.info("Not authorizedUser");
						throw new AuthenticationCredentialsNotFoundException("Invalid Credentials!");
				}
		}

		private boolean authorizedUser(String userName, String password)
		{
			logger.info("In authorizedUser");
			logger.info("username is :" + userName+" and password is "+password );
				System.out.println("username is :" + userName+" and password is "+password );
				if("Chandan".equals(userName) && "Chandan".equals(password))
						return true;
				return false;
		}

		@Override
		public boolean supports(Class<?> authentication)
		{
			logger.info("In supports");
				return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
		}
}