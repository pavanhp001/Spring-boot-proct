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

import java.security.Principal;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.josso.tc60.agent.jaas.CatalinaSSOUser;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * This page is called after the initial login attempt if the user did not explicitly request a specific page. It uses
 * the user's mapeed roles to determine where to send the user. It is non-deterministic if the user has more than one
 * role mapped with a start page. If deterministic behaviour in this instance is required then the class should be
 * reimplemented.
 *
 * @author Bob Boothby
 */
public class CheckLogin extends AbstractController {
    private Map<String, String> roleStartPages;
    private Set<String> validRoles;
    private String defaultView;

    /**
     * Redirect to the appropriate start page depending on a user's role.
     *
     * @param request
     *            The initial request.
     * @param response
     *            The initial response.
     * @return ModelAndView to direct the user to.
     * @throws Exception
     *             If there is a problem.
     */
    protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        String[] roles = null;
        String nextView = defaultView;
        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal != null) {
            roles = ((CatalinaSSOUser) userPrincipal).getRoles();
        }

        if ((roles != null) && (roles.length == 1) && (roleStartPages.get(roles[0]) != null)) {
            nextView = roleStartPages.get(roles[0]);
        }

        return new ModelAndView(nextView);
    }

    /**
     * Set the map of start pages for roles.
     *
     * @param roleStartPages
     *            The map of start pages for roles.
     */
    public void setRoleStartPages(final Map<String, String> roleStartPages) {
        this.roleStartPages = roleStartPages;
        setValidRoles(roleStartPages.keySet());
    }

    /**
     * Set a default view to direct the user to after login if none is defined for any of their roles.
     *
     * @param defaultView
     *            The default view to direct the user to.
     */
    public void setDefaultView(final String defaultView) {
        this.defaultView = defaultView;
    }

    public Set<String> getValidRoles() {
        return validRoles;
    }

    public void setValidRoles(final Set<String> validRoles) {
        this.validRoles = validRoles;
    }

}
