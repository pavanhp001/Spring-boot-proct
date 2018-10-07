/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.web.interceptor;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.SimpleFormController;

import abc.xyz.pts.bcs.common.web.userNavigation.RequestDetails;
import abc.xyz.pts.bcs.common.web.userNavigation.UserNavigationTrail;

/**
 * This class intercepts user web requests and stores them in the session. It allows the web requests to be replayed at
 * a later date using the application's back button. By including one of the following parameters in the request the
 * users navigation history can be modified. (No parameter supplied) The request is added to the user's request history.
 * (ignoreRequest) The current request is not stored. This is preferable is the request should not be able to be
 * replayed such as an insert action. (clearHistory) Clears all history for the user. (navigateBack) the user is
 * navigating backwards so do not store the current request and remove the previous request.
 *
 * Note: Only one navigation history item is allowed per mvc view.
 *
 * @author tterry
 *
 */
public class UserNavigationInterceptor extends HandlerInterceptorAdapter {
    public static final String USER_HISTORY_LOCATION = "userHistoryLocation";
    public static final String IGNORE_REQUEST_PARAM = "ignoreRequest";
    public static final String NAVIGATE_BACK_PARAM = "navigateBack";
    public static final String CLEAR_HISTORY_PARAM = "clearHistory";
    public static final String IGNORE_PARAMETERS_PARAM = "ignoreParameters";

    private static final Logger logger = Logger.getLogger(UserNavigationInterceptor.class);

    /**
     * Executes before each spring controller is called.
     *
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        if (isClassController(handler)) {
            HttpServletRequest newRequest = saveRequest(request, response, getControllerView(handler));
            return super.preHandle(newRequest, response, handler);
        }
        return super.preHandle(request, response, handler);
    }

    /**
     * Saves the request into the session. May ignore the request if the options above are supplied.
     *
     * @param request
     *            the user's current request
     * @param page
     *            The name of the spring mvc view
     */
    private HttpServletRequest saveRequest(final HttpServletRequest request, final HttpServletResponse response, final String page) {

        HttpSession session = request.getSession();
        UserNavigationTrail history = (UserNavigationTrail) session.getAttribute(USER_HISTORY_LOCATION);
        if (history == null) {
            history = new UserNavigationTrail();
        }
        // Check if we are moving from one application to another
        if (changeContext(request)) {
            // If we are going forwards, add a dummy entry to go back to the
            // "referer" of his request
            if (request.getParameter(NAVIGATE_BACK_PARAM) == null) {
                // Get the relative path for the referer URL
                try {
                   URL backUrl = new URL(request.getHeader("referer"));

                    RequestDetails requestDetails = new RequestDetails("dummyPage", backUrl.getPath(), request
                        .getMethod());
                    history.addRequestDetails(requestDetails);
                } catch (MalformedURLException e) {
                   logger.error("Malformed back URL received, ignoring: " + request.getHeader("referer"), e);
                }
            }
        }
        if (request.getParameter(CLEAR_HISTORY_PARAM) != null
                && request.getParameter(CLEAR_HISTORY_PARAM).equals("true")) {
            history.clear();
        } else if (request.getParameter(NAVIGATE_BACK_PARAM) != null
                && request.getParameter(NAVIGATE_BACK_PARAM).equals("true")) {
            // SWAP PARAMETERS FOR CHANGE OF CONTEXT DONE IN FILTER
            if (!UserNavigationInterceptor.changeContext(request)) {
                // remove last element
                history.removeLastRequest();
            }
        } else if (request.getParameter(IGNORE_REQUEST_PARAM) == null
                || request.getParameter(IGNORE_REQUEST_PARAM).equals("")) {
            if (request.getMethod().equals("GET")) {
                history.clear();
            }
            // Add this request to the history
            RequestDetails requestDetails = new RequestDetails(request, page);
            history.addRequestDetails(requestDetails);
        }
        request.getSession().setAttribute(USER_HISTORY_LOCATION, history);
        return request;
    }

    private boolean isClassController(final Object obj) {
        boolean result = false;
        try {
            Class c = obj.getClass();
            if (obj instanceof Proxy) {
                Method m = Object.class.getMethod("getClass", new Class[0]);
                c = (Class) Proxy.getInvocationHandler(obj).invoke(obj, m, new Object[0]);
            }
            if (SimpleFormController.class.isAssignableFrom(c)) {
                result = true;
            }
        } catch (Throwable t) {
            result = false;
        }
        return result;
    }

    private String getControllerView(final Object obj) {
        String result = null;
        try {
            if (obj instanceof Proxy) {
                Method m = SimpleFormController.class.getMethod("getFormView", new Class[0]);
                result = (String) Proxy.getInvocationHandler(obj).invoke(obj, m, new Object[0]);
            } else {
                result = ((SimpleFormController) obj).getFormView();
            }
        } catch (Throwable t) {
            result = null;
        }
        return result;
    }

    public static boolean changeContext(final HttpServletRequest request) {
        String sourceUrl = request.getHeader("referer");
        String targetUrl = request.getRequestURI();
        if (sourceUrl != null && !sourceUrl.endsWith("josso_security_check")) {
            // trim start of source to remove protocol and server
            int targetUrlSlash = targetUrl.lastIndexOf("/");
            if (targetUrlSlash >= 0) {
                targetUrl = targetUrl.substring(0, targetUrlSlash);
                if (targetUrl != null && !sourceUrl.contains(targetUrl)) {
                    return true;
                }
            }
        }
        return false;
    }
}
