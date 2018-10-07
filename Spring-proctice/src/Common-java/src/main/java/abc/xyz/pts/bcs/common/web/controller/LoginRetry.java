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
 * Controller for login retry.
 *
 * @author Bob Boothby.
 */
public class LoginRetry extends AbstractController {

    private String loginView;

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
        /*
         * The user has not yet been authenticated and we must send the user to the login page.
         */
        mav.setViewName(loginView);
        /*
         * As we are coming to the login JSP through this form we will assume that it is not a retry.
         */
        mav.addObject("loginAttempt", new LoginAttempt(true));
        return mav;
    }

}
