package com.jcg.examples.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomEntryPoint implements AuthenticationEntryPoint
{
	Log logger = LogFactory.getLog(CustomEntryPoint.class);

		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException
		{
			logger.info("Unauthorized ");
					System.out.println("Entering commence");
				 response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
		}

}
