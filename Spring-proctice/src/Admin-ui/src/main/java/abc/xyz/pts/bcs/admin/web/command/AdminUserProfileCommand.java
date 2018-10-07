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
package abc.xyz.pts.bcs.admin.web.command;

import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableCommand;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableExpressionBeanProperties;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableExpressionBeanProperty;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.audit.business.Parameter;
import abc.xyz.pts.bcs.common.dto.AbstractRequeryableCommand;

/**
 *
 * @author DGroves
 * @version: $Id: AdminUserProfileCommand.java 822 2009-08-05 15:46:43Z tbarak $
 */
@AuditableCommand(Event.VIEW_USER)
public class AdminUserProfileCommand extends AbstractRequeryableCommand {
    /**
     *
     */
    private static final long serialVersionUID = 2186782274693757155L;
    private boolean userInAdminRole;
    @AuditableExpressionBeanProperties(properties = {
            @AuditableExpressionBeanProperty(key = Parameter.USERNAME, expression = "user.username"),
            @AuditableExpressionBeanProperty(key = Parameter.EMAIL_ADDRESS, expression = "user.email"),
            @AuditableExpressionBeanProperty(key = Parameter.USER_STATUS, expression = "user.userStatusAudit"),
            @AuditableExpressionBeanProperty(key = Parameter.USER_FULL_NAME, expression = "user.name") })
    private User user;
    private Action action;

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(final User user) {
        this.user = user;
    }

    /**
     * @return the userInAdminRole
     */
    public boolean isUserInAdminRole() {
        return userInAdminRole;
    }

    /**
     * @param userInAdminRole
     *            the userInAdminRole to set
     */
    public void setUserInAdminRole(final boolean userInAdminRole) {
        this.userInAdminRole = userInAdminRole;
    }

    /**
     * @return the action
     */
    public Action getAction() {
        return action;
    }

    /**
     * @param action
     *            the action to set
     */
    public void setAction(final Action action) {
        this.action = action;
    }
}
