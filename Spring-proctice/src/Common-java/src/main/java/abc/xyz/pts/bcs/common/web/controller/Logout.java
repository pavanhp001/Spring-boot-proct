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

/**
 * The basic logout behaviour.
 */
public class Logout extends AbstractController {

    private String logoutView;

    /**
     * Set where the application should go after logout.
     *
     * @param logoutView
     */
    public void setLogoutView(final String logoutView) {
        this.logoutView = logoutView;
    }

    /**
     * Log the user out of the application.
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
        // Kill the session and forward to the login page.
        request.getSession().invalidate();
        return new ModelAndView(logoutView);
    }
}
