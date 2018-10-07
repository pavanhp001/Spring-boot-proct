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
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.josso.gateway.SSONameValuePair;
import org.josso.tc60.agent.jaas.CatalinaSSOUser;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import abc.xyz.pts.bcs.common.bean.UserDetails;
import abc.xyz.pts.bcs.common.dao.session.PrivateUserProfile;
import abc.xyz.pts.bcs.common.dao.utils.Constants;

/**
 * Interceptor that stores the user name in the user profile manager and (if necessary) lets the session locale resolver
 * know which locale to use.
 */
public class SSOUserProfileInterceptor implements HandlerInterceptor {

    private PrivateUserProfile userProfile;
    private static Logger log = Logger.getLogger(SSOUserProfileInterceptor.class);

    /**
     * Set the user profile manager to use to store the security principal for the thread and to retrieve the user
     * locale.
     *
     * @param userProfile
     *            An instance of the user profile manager that provides thread local storage of the security principal.
     */
    public void setUserProfile(final PrivateUserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {

        Principal userPrincipal = request.getUserPrincipal();

        UserDetails userDetails = getUserDetails(request, userPrincipal);

        request.setAttribute(Constants.USER_PROFILE_KEY, userDetails);

        userProfile.setUserDetailsForThread(userDetails);

        return true; // continue as normal
    }

    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
            final ModelAndView modelAndView) throws Exception {
        userProfile.clearUserDetailsForThread();
    }

    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex)
            throws Exception {
        // Don't need to do anything.
    }

    public UserDetails getUserDetails(final HttpServletRequest request, final Principal p) {

        UserDetails provider = new UserDetails();
        if (p instanceof CatalinaSSOUser) {
            CatalinaSSOUser user = (CatalinaSSOUser) p;
            provider.setName(user.getName());

            provider.setRoles(user.getRoles());
            SSONameValuePair[] props = user.getProperties();
            for (SSONameValuePair nameValuePair : props) {
                if (nameValuePair.getName().equals("description")) {
                    provider.setDescription(nameValuePair.getValue());
                } else if (nameValuePair.getName().equals("locale")) {
                    provider.setLocale(new Locale(nameValuePair.getValue()));
                } else if (nameValuePair.getName().equals("location")) {
                    provider.setAirport(nameValuePair.getValue());
                } else if (nameValuePair.getName().equals("email")) {
                    provider.setEmailAddress(nameValuePair.getValue());
                } else if (nameValuePair.getName().equals("employeeName")) {
                    provider.setEmployeeName(nameValuePair.getValue());
                } else if (nameValuePair.getName().equals("entity")) {
                    provider.setEntity(nameValuePair.getValue());
                } else if (nameValuePair.getName().equals("office")) {
                    provider.setOffice(nameValuePair.getValue());
                } else if (nameValuePair.getName().equals("pwdlastauthtime")) {
                    try {
                        provider.setLastLogin(new Date(Long.parseLong(nameValuePair.getValue())));
                    } catch (NumberFormatException e) {
                        log.error("Invalid format for last login time: " + nameValuePair.getValue());
                    }
                }

            }
            // this should have been filled by ldap. But as a default lets
            // take it from the browser.
            if (provider.getLocale() == null) {
                provider.setLocale(LocaleContextHolder.getLocale());
            }
        }
        provider.setSessionId(request.getSession().getId());
        provider.setIpAddress(request.getRemoteAddr());
        return provider;

    }

}
