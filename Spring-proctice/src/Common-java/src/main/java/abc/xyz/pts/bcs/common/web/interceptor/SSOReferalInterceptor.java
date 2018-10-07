/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.web.interceptor;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.josso.gateway.SSONameValuePair;
import org.josso.gateway.identity.SSOUser;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SSOReferalInterceptor extends HandlerInterceptorAdapter {

    private String changePasswordView;

    private static final Logger LOGGER = Logger.getLogger(SSOReferalInterceptor.class);
    private static final String REFERER_HEADER_NAME = "Referer";
    private static final String LOGIN_REFERER = "josso";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss'Z'");
    private static final String REDIRECT_QUERY_STR = "homePage=true";

    public SSOReferalInterceptor() {
        super();
    }

    /**
     * @param changePasswordView
     *            the changePasswordView to set
     */
    public void setChangePasswordView(final String changePasswordView) {
        this.changePasswordView = changePasswordView;
    }

    private boolean isPasswordAboutToExpire(final Principal p) {
        boolean result = false;
        try {
            if (SSOUser.class.isAssignableFrom(p.getClass())) {
                final SSONameValuePair[] props = ((SSOUser) p).getProperties();
                String pwdChangedTimeStr = null;
                String secondsBeforeStr = null;
                String passwordReset = null;
                for (final SSONameValuePair nameValuePair : props) {
                    if (nameValuePair.getName().equals("pwdChangedTime")) {
                        pwdChangedTimeStr = nameValuePair.getValue();
                    } else if (nameValuePair.getName().equals("secondsBeforePasswordExpiryWarning")) {
                        secondsBeforeStr = nameValuePair.getValue();
                    } else if (nameValuePair.getName().equals("passwordReset")) {
                        passwordReset = nameValuePair.getValue();
                    }
                }
                if (passwordReset != null && passwordReset.equals("TRUE")) {
                    result = true;
                } else if (pwdChangedTimeStr != null && secondsBeforeStr != null) {
                    Date passwordChangedDate;
                    synchronized (sdf) { // TODO: replace with Joda Time
                        passwordChangedDate = sdf.parse(pwdChangedTimeStr);
                    }

                    final long millisUntilExpireWarning = Long.parseLong(secondsBeforeStr) * 1000;
                    final long currentTime = System.currentTimeMillis();
                    if ((passwordChangedDate.getTime() + millisUntilExpireWarning) < currentTime) {
                        result = true;
                    } else {
                        result = false;
                    }
                } else {
                    result = false;
                }
            }
        } catch (final Exception e) {
            LOGGER.error("Unable to determine whether user's (" + (p != null ? p.getName() : "null") + ") password is about to expire", e);
        }
        return result;
    }

    /**
     * We check for the existence of a query string param as we get into a infinate loop. This is due to the referer
     * from the previous request not being removed when we redirect.
     *
     * @param request
     * @return
     */
    private boolean isPreviousRedirect(final HttpServletRequest request) {
        boolean result = false;
        final String queryStr = request.getQueryString();
        if (queryStr != null && queryStr.length() > 0 && queryStr.contains(REDIRECT_QUERY_STR)) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {

        final String referer = request.getHeader(REFERER_HEADER_NAME);

        // Redirect if password is due to expire, and we've come from the login screen
        if (referer != null && referer.contains(LOGIN_REFERER) && !isPreviousRedirect(request)) {
            final Principal userPrincipal = request.getUserPrincipal();

            if (isPasswordAboutToExpire(userPrincipal)) {
                response.sendRedirect(changePasswordView);
                return false;
            }
        }

        return true; // continue as normal
    }

}
