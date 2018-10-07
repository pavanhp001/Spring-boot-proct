/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.web.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import abc.xyz.pts.bcs.common.web.userNavigation.RequestDetails;
import abc.xyz.pts.bcs.common.web.userNavigation.UpdateParameterHttpServletRequestWrapper;
import abc.xyz.pts.bcs.common.web.userNavigation.UserNavigationTrail;

/**
 * Servlet filter to intercept the request and check if we are moving back between applications.
 *
 * If we are moving back between applications, restore the parameters from the last element in the history.
 */
public class BackFilter implements Filter {

    @Override
    public void destroy() {
    }

    /**
     * Check to update request parameters, forward the request to doFliter.
     */
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletRequest newRequest = null;
        // Check if we have moved between applications
        if (UserNavigationInterceptor.changeContext(req)) {
            // Check if we are moving back between applications
            if (request.getParameterMap().get("navigateBack") != null) {
                // As we have switched applications, the required page is the last
                UserNavigationTrail history = (UserNavigationTrail) req.getSession().getAttribute(
                        UserNavigationInterceptor.USER_HISTORY_LOCATION);
                if (history != null && history.getCurrentRequest() != null) {
                    RequestDetails swap = history.getCurrentRequest();
                    swap.getParameters().put("navigateBack", new String[] { "true" });
                    newRequest = new UpdateParameterHttpServletRequestWrapper(req, swap.getParameters());
                }
            }
        }
        if (newRequest != null) {
            filterChain.doFilter(newRequest, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void init(final FilterConfig arg0) throws ServletException {

    }
}
