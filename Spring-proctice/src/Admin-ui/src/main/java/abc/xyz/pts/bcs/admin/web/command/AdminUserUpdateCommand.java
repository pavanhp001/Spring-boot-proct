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
import abc.xyz.pts.bcs.common.audit.annotation.AuditPropertyGroup;
import abc.xyz.pts.bcs.common.audit.annotation.AuditPropertyGroups;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableBeanProperty;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableCommand;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableExpressionBeanProperties;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableExpressionBeanProperty;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.audit.business.Parameter;
import abc.xyz.pts.bcs.common.dto.AbstractRequeryableCommand;

/**
 * @author DGroves
 * @version: $Id: AdminUserUpdateCommand.java 806 2009-07-17 15:48:27Z tterry $
 */
@AuditableCommand(Event.UPDATE_USER)
@AuditPropertyGroups(auditWhenNoGroupMatch = false, value = {
        @AuditPropertyGroup(name = Event.RESET_PASSWORD, groupProperty = "action", propertyValue = "RESET_PASSWORD"),
        @AuditPropertyGroup(name = Event.UPDATE_USER, groupProperty = "action", propertyValue = "UPDATE") })
public class AdminUserUpdateCommand extends AbstractRequeryableCommand {
    /**
     *
     */
    private static final long serialVersionUID = 6329714921272121827L;

    private Action action;

    @AuditableExpressionBeanProperties(ignoreEvent = "RESET.PASSWORD", properties = {
            @AuditableExpressionBeanProperty(key = Parameter.USERNAME, expression = "user.username"),
            @AuditableExpressionBeanProperty(key = Parameter.EMAIL_ADDRESS, expression = "user.email"),
            @AuditableExpressionBeanProperty(key = Parameter.FAX_NUMBER, expression = "user.faxNumber"),
            @AuditableExpressionBeanProperty(key = Parameter.FORENAME, expression = "user.forename"),
            @AuditableExpressionBeanProperty(key = Parameter.LASTNAME, expression = "user.lastname"),
            @AuditableExpressionBeanProperty(key = Parameter.MOBILE_NUMBER, expression = "user.mobileNumber"),
            @AuditableExpressionBeanProperty(key = Parameter.USER_ROLE, expression = "user.roles"),
            @AuditableExpressionBeanProperty(key = Parameter.USER_AIRPORT, expression = "user.location"),
            @AuditableExpressionBeanProperty(key = Parameter.USER_STATUS, expression = "user.userStatusAudit") })
    private User user;

    @AuditableBeanProperty(group = "RESET.PASSWORD", key = Parameter.USERNAME)
    private String auditUsername;
    private boolean updateValidated;

    private String displayResetPasswordPopin;

    /**
     * @return the updateValidated
     */
    public boolean isUpdateValidated() {
        return updateValidated;
    }

    /**
     * @param updateValidated
     *            the updateValidated to set
     */
    public void setUpdateValidated(final boolean updateValidated) {
        this.updateValidated = updateValidated;
    }

    public String getAuditUsername() {
        String result = null;
        if (getUser() != null) {
            result = getUser().getUsername();
        }
        return result;
    }

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

    public String getDisplayResetPasswordPopin() {
        return displayResetPasswordPopin;
    }

    public void setDisplayResetPasswordPopin(final String displayResetPasswordPopin) {
        this.displayResetPasswordPopin = displayResetPasswordPopin;
    }

}
