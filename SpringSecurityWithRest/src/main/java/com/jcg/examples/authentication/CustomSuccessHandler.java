package com.jcg.examples.authentication;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;


public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{
	Log logger = LogFactory.getLog(CustomSuccessHandler.class);

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException
		{
			logger.info("onAuthenticationSuccess:");
				System.out.println("authentication successful!");
		}
}
