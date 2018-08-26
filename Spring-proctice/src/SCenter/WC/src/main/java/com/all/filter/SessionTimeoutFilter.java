package com.A.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.A.managers.ApplicationContextProvider;
import com.A.ui.dao.CustomerTrackerDao;
import com.A.ui.repository.SessionCache;
import com.A.ui.service.config.ConfigRepo;
import com.A.ui.service.V.OrderService;
import com.A.ui.util.Utils;
import com.A.xml.v4.OrderManagementRequestResponse;
import com.A.xml.v4.OrderType;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

public class SessionTimeoutFilter implements javax.servlet.Filter {

	private String redirectPage;
	private ServletContext servletContext;
	private static final Logger logger = Logger.getLogger(SessionTimeoutFilter.class);
	private String xmlHttpRequest = "XMLHttpRequest";
	private String requestedWith = "X-Requested-With";
	private String sessionTimeOut = "sessionTimeOut";

	public SessionTimeoutFilter() {
		super();
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		servletContext = filterConfig.getServletContext();
		redirectPage = filterConfig.getInitParameter("Redirect-Page");
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterChain) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpRes = (HttpServletResponse) res;

		HttpSession session = httpReq.getSession(false);

		logger.info("filter_execution_started");
		logger.info("requestURL="+httpReq.getRequestURL());
		boolean isAuthenticated = ((session != null) && (SessionCache.INSTANCE.isAuthenticated(session)));

		try{
			if (session != null){
				int session_timeout = ConfigRepo.getInt("*.session_timeout");
				if(session_timeout > 0 && session.getMaxInactiveInterval()!=session_timeout){
					session.setMaxInactiveInterval(session_timeout);
				}
				session.setAttribute("sessionActiveTime", session.getMaxInactiveInterval());
				logger.debug("Session Max Inactive Time ----->>>> "+session.getMaxInactiveInterval());
				
			}

			//TODO: Have to check Authentication
			if ((session != null) && (!session.isNew()) && isAuthenticated) {
				//To put SessionId in logs
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
					//TODO: skip login page by some other way
					String reqestUri = httpReq.getRequestURI();
					if(reqestUri.indexOf("login.htm") == -1 && reqestUri.indexOf("vdnShow") == -1
							&& reqestUri.indexOf("login") == -1 && reqestUri.indexOf("login_process") == -1 
							&& reqestUri.indexOf("clearCache") == -1 && reqestUri.indexOf("clearMDUCache") == -1 && reqestUri.indexOf("session_timeout") == -1
							&&  reqestUri.indexOf("clearThoughtSpotCache") == -1){

						if (httpReq.getParameterValues("redirectme") != null) {
							httpRes.sendRedirect(redirectPage);
						} else {
							logger.info("path 1------------>"+httpReq.getContextPath()+"/salescenter/session_timeout");
							logger.info("path 2 ------------>"+httpReq.getContextPath()+"/session_timeout");
							httpReq.getRequestDispatcher(httpReq.getContextPath()+"/session_timeout").forward(httpReq, httpRes);
							//ActivitySafeRedirect.INSTANCE.execute(httpReq, httpRes);
						}
					} else {
						httpRes.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
						httpRes.setHeader("Pragma", "no-cache"); // HTTP 1.0.
						httpRes.setDateHeader("Expires", 0);
						filterChain.doFilter(req, res);
					}
				}	
			}
		
		} catch(Exception e){
			logger.warn("Error_in_doFilter",e);
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
