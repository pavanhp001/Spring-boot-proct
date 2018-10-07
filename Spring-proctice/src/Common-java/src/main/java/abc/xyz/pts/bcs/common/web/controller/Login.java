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
package abc.xyz.pts.bcs.common.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import abc.xyz.pts.bcs.common.web.bean.LoginAttempt;

/**
 * Controller for login.
 *
 * @author Bob Boothby
 */
public class Login extends AbstractController {

    private String loginView;
    private String postLoginView;

    /**
     * Set the name of the view to use for the login form.
     *
     * @param loginView
     *            The login form.
     */
    public void setLoginView(final String loginView) {
        this.loginView = loginView;
    }

    /**
     * Set the name of the view to use if, after successful login, this controller is called again.
     *
     * @param postLoginView
     *            Where to go if, after login, this controller is called again.
     */
    public void setPostLoginView(final String postLoginView) {
        this.postLoginView = postLoginView;
    }

    /**
     * Direct user to page appropriate to whether the user is authenticated or not.
     *
     * @param request
     *            The request which may have valid credentials.
     * @param response
     *            The response.
     * @return ModelAndView Where to direct the user to.
     * @throws Exception
     *             If there is a problem.
     */
    protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

        ModelAndView mav = new ModelAndView();
        if (request.getUserPrincipal() != null) {
            /*
             * We can safely assume that a valid login has occurred.
             *
             * If we are here the user has explicitly requested access to this page and now validated we should send
             * them to an introductory page.
             */
            mav.setViewName(postLoginView);
        } else {
            /*
             * The user has not yet been authenticated and we must send the user to the login page.
             */
            mav.setViewName(loginView);
            /*
             * As we are coming to the login JSP through this form we will assume that it is not a retry.
             */
            mav.addObject("loginAttempt", new LoginAttempt(false));
        }
        return mav;
    }
}
