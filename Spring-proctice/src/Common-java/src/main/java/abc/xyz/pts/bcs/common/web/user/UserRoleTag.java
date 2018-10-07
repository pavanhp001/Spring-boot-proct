/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2009
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * Custom JSTL tag to check if an element should be displayed based on the user role.
 * <P>
 * The <CODE>hide</CODE> attribute is not required, and defaults to "false". If <CODE>hide="true"</CODE> attribute is
 * set, the element will be hidden if the users' role matches the required user role, otherwise it will be displayed.
 * <P>
 * If the <CODE>not="true"</CODE> attribute is set, the element will be displayed if the users' role does NOT match the
 * required user role. This cannot be used in conjunction with <CODE>hide="true"</CODE>.
 * <P>
 * The required user roles can be specified explicitly in the <CODE>roles</CODE>, or by specifying a
 * <CODE>roleGroup</CODE> declared in CheckUserRole.java.
 * <P>
 * If a <CODE>role</CODE> is set, the <CODE>function</CODE> is not checked.
 * <P>
 * If a <CODE>workingAirport</CODE> is set, it must match the <CODE>UserDetails.airport</CODE>/
 */
public class UserRoleTag extends TagSupport {

    private String[] rolesList;
    private String roles;
    private String roleGroup;
    private String workingAirport;

    private boolean hide;
    private boolean not;

    @Override
    /**
     * Main function to get required roles to display an element,
     * then check against the role(s) for the current user.
     */
    public int doStartTag() throws JspException {
        final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        if (request.getUserPrincipal() != null) {
            boolean validRole = false;
           String[] checkRoles;
            if (rolesList != null && CheckUserRole.checkUserRole(rolesList, request)) {
                validRole = true;
            } else {
                checkRoles = CheckUserRole.getFunctionRoles(roleGroup);
                if (CheckUserRole.checkUserRole(checkRoles, request)) {
                    validRole = true;

                }
            }
            if (((validRole && !not) || (!validRole && not) && !hide)) {
                return EVAL_BODY_INCLUDE;
            }
        }
        return SKIP_BODY;
    }

    /**
     * @return role(s) required to display an element
     */
    public String getRoles() {
        return roles;
    }

    /**
     * @param role
     *            (s) required to display an element
     */
    public void setRoles(final String roles) {
        this.roles = roles;
        this.rolesList = (roles != null) ? StringUtils.split(roles, ',') : null;
    }

    /**
     * @return roleGroup required to display an element
     */
    public String getRoleGroup() {
        return roleGroup;
    }

    /**
     * @param roleGroup
     *            required to display an element
     */
    public void setRoleGroup(final String roleGroup) {
        this.roleGroup = roleGroup;
    }

    /**
     * @return if the check should hide an element
     */
    public boolean getHide() {
        return hide;
    }

    /**
     * @param hide
     *            if the check should hide an element
     */
    public void setHide(final boolean hide) {
        this.hide = hide;
    }

    /**
     * @return the working airport code
     */
    public String getWorkingAirport() {
        return workingAirport;
    }

    /**
     * @param workingAirport
     *            to check
     */
    public void setWorkingAirport(final String workingAirport) {
        this.workingAirport = workingAirport;
    }

    /**
     * @return not flag
     */
    public boolean getNot() {
        return not;
    }

    /**
     * @param not
     *            flag to set
     */
    public void setNot(final boolean not) {
        this.not = not;
    }
}
