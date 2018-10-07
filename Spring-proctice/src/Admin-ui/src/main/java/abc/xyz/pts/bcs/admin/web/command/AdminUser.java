/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */

package abc.xyz.pts.bcs.admin.web.command;

import java.io.Serializable;

import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableExpressionBeanProperties;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableExpressionBeanProperty;
import abc.xyz.pts.bcs.common.audit.business.Parameter;

/**
 * @author ryattapu
 *
 */
public class AdminUser implements Serializable {
    @AuditableExpressionBeanProperties(properties = {
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
