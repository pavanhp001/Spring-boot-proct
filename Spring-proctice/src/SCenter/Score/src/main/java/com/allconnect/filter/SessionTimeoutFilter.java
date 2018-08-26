package com.AL.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import com.AL.ui.repository.SessionCache;

import java.util.logging.*;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

public class SessionTimeoutFilter implements javax.servlet.Filter {
	
	private String redirectPage;
	
	@SuppressWarnings("unused")
	private ServletContext servletContext;
	
	private Logger log;
	private String xmlHttpRequest = "XMLHttpRequest";
	private String requestedWith = "X-Requested-With";
	private String sessionTimeOut = "sessionTimeOut";

	public SessionTimeoutFilter() {
		super();
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		servletContext = filterConfig.getServletContext();
		redirectPage = filterConfig.getInitParameter("Redirect-Page");
		log = Logger.getLogger(SessionTimeoutFilter.class.getName());
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterChain) throws IOException, ServletException 
			{

		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpRes = (HttpServletResponse) res;

		HttpSession session = httpReq.getSession(false);

//		log.info("*************** filter execution ********************");

		boolean isAuthenticated = ((session != null) && (SessionCache.INSTANCE.isAuthenticated(session)));
		
//		log.info("*************** isAuthenticated ******************** "+isAuthenticated);

		if ((session != null) && (!session.isNew()) && (isAuthenticated))
		{
			filterChain.doFilter(req, res);
		} 
		else {
			//Handling Ajax requests for session time out........
			 if(xmlHttpRequest.equals(httpReq.getHeader(requestedWith))){
				 log.info("Ajax call detected when session timeout......");
				 httpRes.setContentType("text/html");
				 PrintWriter out = httpRes.getWriter();
				 out.write(sessionTimeOut);
				 out.flush();
				 out.close();
			 }else 
				{
					String reqestUri = httpReq.getRequestURI();
					if(reqestUri.indexOf("login") == -1)
					{
						if (httpReq.getParameterValues("redirectme") != null) 
						{
							httpRes.sendRedirect(redirectPage);
						} 
						else
						{
							httpRes.sendRedirect(httpReq.getContextPath()+"/rest/login");
						}
					}
					else
					{
						filterChain.doFilter(req, res);
					}
				}
		}
	}

	public void destroy() {
	}

}

class CharResponseWrapper extends HttpServletResponseWrapper {
	private CharArrayWriter output;

	public String toString() {
		return output.toString();
	}

	public CharResponseWrapper(HttpServletResponse response) {
		super(response);
		output = new CharArrayWriter();
	}

	public PrintWriter getWriter() {
		return new PrintWriter(output);
	}

}
