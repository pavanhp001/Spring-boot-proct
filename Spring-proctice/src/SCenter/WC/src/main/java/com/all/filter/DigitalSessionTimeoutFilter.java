package com.A.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.A.ui.repository.SessionCache;
import com.A.ui.service.config.ConfigRepo;

public class DigitalSessionTimeoutFilter implements javax.servlet.Filter {

	private String redirectPage;
	private ServletContext servletContext;
	private static final Logger logger = Logger.getLogger(DigitalSessionTimeoutFilter.class);
	private String xmlHttpRequest = "XMLHttpRequest";
	private String requestedWith = "X-Requested-With";
	private String sessionTimeOut = "sessionTimeOut";
	Pattern pattern;
	Pattern zipPattern;
	Pattern alphaPattern;
	
	
	public DigitalSessionTimeoutFilter() {
		super();
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		setServletContext(filterConfig.getServletContext());
		redirectPage = filterConfig.getInitParameter("Redirect-Page");
		final String weburlRegexPattern = filterConfig.getInitParameter("xssRegex");
		final String zipRegexPattern = filterConfig.getInitParameter("zipRegex");
		pattern = Pattern.compile(weburlRegexPattern);
		zipPattern = Pattern.compile(zipRegexPattern);
		alphaPattern = Pattern.compile("^[a-zA-Z ]*$");
        
		logger.info("RegexPattern - " + pattern.pattern());
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterChain) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpRes = (HttpServletResponse) res;

		HttpSession session = httpReq.getSession(false);
		logger.info("filter_execution_started");
		boolean isAuthenticated = false;
		
		try{
			if (session != null){
				int session_timeout = ConfigRepo.getInt("*.session_timeout");
				if(session_timeout > 0 && session.getMaxInactiveInterval()!=session_timeout){
					session.setMaxInactiveInterval(session_timeout);
				}
				session.setAttribute("sessionActiveTime", session.getMaxInactiveInterval());
				logger.debug("Session Max Inactive Time ----->>>> "+session.getMaxInactiveInterval());
				if( session.getAttribute("isWebFlowStarted") != null )
				{
					isAuthenticated = true;
				}
			}
			final String url = ((HttpServletRequest) req).getRequestURL().toString();

			/*if (isUsaaLandingPage((HttpServletRequest) req))// "/landing/usaa"
			{
				chain.doFilter(req, res);
				return;
			}*/
			String queryString = ((HttpServletRequest) req).getQueryString();
			//		final URLDecoder urlDecoder = new URLDecoder();
			String urlQueryString = url;
			if (queryString != null && !queryString.isEmpty())
			{
				queryString = queryString.replaceAll("\t", "").replaceAll("\n", "").replace(System.getProperty("line.separator"), "")
						.trim();
				urlQueryString = urlQueryString + "?" + URLDecoder.decode(queryString, "UTF-8");

				final String upd_ddlState = req.getParameter("upd_ddlState");
				final String upd_txtCity = req.getParameter("upd_txtCity");
				final String upd_txtZip = req.getParameter("upd_txtZip");

				if (!isValidState(upd_ddlState) || !isValidCity(upd_txtCity) || !isValidZip(upd_txtZip))
				{
					logger.info("Rejecting Invalid zip - " + upd_txtZip + " , or state - " + upd_ddlState + " , or city - " + upd_txtCity);
					if (res instanceof HttpServletResponse)
					{
						final HttpServletResponse resp = (HttpServletResponse) res;
						resp.sendRedirect(resp.encodeRedirectURL("/rejectedURL"));
						return;
					}
				}
			}
			final Matcher matcher = pattern.matcher(urlQueryString);
			final boolean b = matcher.matches();

			if (!b)
			{
				logger.info("Rejecting Decoded urlQueryString - " + urlQueryString);
				if (res instanceof HttpServletResponse)
				{
					final HttpServletResponse resp = (HttpServletResponse) res;
					resp.sendRedirect(resp.encodeRedirectURL("/rejectedURL"));
					return;
				}

			}

			if (req instanceof HttpServletRequest && ((HttpServletRequest) req).getMethod() == "POST")
			{

				final Map<String, String[]> paramMap = req.getParameterMap();
				if (!paramMap.isEmpty())
				{
					final Iterator<String> iter = paramMap.keySet().iterator();
					while (iter.hasNext())
					{
						final String key = iter.next();
						final String[] vals = paramMap.get(key);
						if (vals[0] != null && !vals[0].isEmpty())
						{
							String urlParam = url + "?" + key + "=" + vals[0];
							urlParam = urlParam.replaceAll("\t", "").replaceAll("\n", "").replace(System.getProperty("line.separator"), "")
									.trim();

							//						LOG.info("Evaluating POST urlParam - " + urlParam);

							final Matcher postParamMatcher = pattern.matcher(urlParam);
							final boolean postValueValid = postParamMatcher.matches();

							if (!postValueValid)
							{
								logger.info("REJECTING POST urlParam - " + urlParam);
								if (res instanceof HttpServletResponse)
								{
									final HttpServletResponse resp = (HttpServletResponse) res;
									resp.sendRedirect(resp.encodeRedirectURL("/rejectedURL"));
									return;
								}

							}
						}

					}



				}

			}
			
			if ( (session != null) && (!session.isNew()) && isAuthenticated )
			{
				MDC.put("sessionId", session.getId()); 
				MDC.put("agentId", SessionCache.INSTANCE.getAgentId(session));
				String orderId = String.valueOf((Long)session.getAttribute("orderId"));
				MDC.put("orderId", orderId != null ? orderId : "" );
				String GUID = (String)session.getAttribute("GUID");
				MDC.put("GUID", GUID != null ? GUID : "" );
				filterChain.doFilter(req, res);
			} else {
				//Handling Ajax requests for session time out........
				 if(xmlHttpRequest.equals(httpReq.getHeader(requestedWith))){
					 logger.info("Ajax_call_detected_when_session_timeout");
					 httpRes.setContentType("text/html");
					 PrintWriter out = httpRes.getWriter();
					 out.write(sessionTimeOut);
					 out.flush();
					 out.close();
				 }else{
					String reqestUri = httpReq.getRequestURI();
					logger.info("reqestUri"+reqestUri);
					if( reqestUri.indexOf("cox") == -1 && reqestUri.indexOf("A") == -1 
						&& reqestUri.indexOf("clearCache") == -1 )
					{
						if (httpReq.getParameterValues("redirectme") != null)
						{
							httpRes.sendRedirect(redirectPage);
						} 
						else
						{
							logger.debug("path 1------------>"+httpReq.getContextPath()+"/digital/session_timeout");
							logger.debug("path 2 ------------>"+httpReq.getContextPath()+"/session_timeout");
							httpReq.getRequestDispatcher(httpReq.getContextPath()+"/session_timeout").forward(httpReq, httpRes);
						}
					} 
					else 
					{
						httpRes.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
						httpRes.setHeader("Pragma", "no-cache"); // HTTP 1.0.
						httpRes.setDateHeader("Expires", 0);
						filterChain.doFilter(req, res);
					}
				}
			}
		} catch(Exception e){
			e.printStackTrace();
			logger.warn("Error_in_doFilter",e);
		}
	}


	private boolean isValidState(final String state)
	{
		if (state != null && !state.isEmpty())
		{
			return (state.length() <= 3 && alphaPattern.matcher(state).matches());
		}
		return true;
	}

	private boolean isValidCity(final String city)
	{
		if (city != null && !city.isEmpty())
		{
			return alphaPattern.matcher(city).matches();

		}
		return true;
	}

	private boolean isValidZip(final String zip)
	{
		if (zip != null && !zip.isEmpty())
		{
			return zipPattern.matcher(zip).matches();
		}
		return true;
	}
	public void destroy() {
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}

