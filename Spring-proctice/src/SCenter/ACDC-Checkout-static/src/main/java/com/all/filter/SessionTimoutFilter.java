package com.AL.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import java.util.logging.*;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import org.apache.log4j.MDC;

import com.AL.ui.domain.SessionKeys;
import com.AL.ui.service.V.impl.CKOCacheService;
import com.AL.ui.vo.OrderQualVO;
import com.AL.ui.vo.SessionVO;

public class SessionTimoutFilter implements javax.servlet.Filter {
	private String redirectPage;
	private ServletContext servletContext;
	private Logger log;

	public SessionTimoutFilter() {
		super();
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		servletContext = filterConfig.getServletContext();
		redirectPage = filterConfig.getInitParameter("Redirect-Page");
		log = Logger.getLogger(SessionTimoutFilter.class.getName());
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpSession session = httpReq.getSession(true);
		if ((session != null)){
			MDC.put("sessionId", session.getId()); 
			SessionVO sessionVO = CKOCacheService.INSTANCE.get(session.getId());
			String agentId = "";
			String orderId = "";
			String GUID = "";
			if(sessionVO!=null){
				OrderQualVO orderQualVO = (OrderQualVO)sessionVO.get(SessionKeys.orderQualVo.name());
				agentId = orderQualVO.getAgentId() != null? orderQualVO.getAgentId(): "";
				orderId = orderQualVO.getOrderId() != null? orderQualVO.getOrderId(): "";
				GUID = orderQualVO.getGUID() !=null? orderQualVO.getGUID(): "";
			}
			MDC.put("agentId", agentId);
			MDC.put("orderId", orderId );
			MDC.put("GUID", GUID);
		}
		filterChain.doFilter(req, res);
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
