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

import java.math.BigInteger;
import java.util.List;

import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.common.annotation.SensitiveProperty;
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
 * @version: $Id: AdminUserSearchCommand.java 803 2009-07-16 15:38:16Z tterry $
 */
@AuditableCommand(Event.SEARCH_USER)
@AuditPropertyGroups(@AuditPropertyGroup(name = Event.DELETE_USER, groupProperty = "action", propertyValue = "DELETE"))
public class AdminUserSearchCommand extends AbstractRequeryableCommand {
    /**
     *
     */
    private static final long serialVersionUID = -107651124252730158L;
    @AuditableExpressionBeanProperties(ignoreEvent = "DELETE.USER", properties = {
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

    @AuditableBeanProperty(group = "DELETE.USER", key = Parameter.USERNAME)
    private String selectedUsername;
    private Action action;
    private String pageChanged;
    @SensitiveProperty
    private List<User> records;

    public List<User> getRecords() {
        return records;
    }

    public void setRecords(final List<User> records) {
        this.records = records;
    }

    public void setStartRecordForRequery(final int pageNumber, final int recordsPerPage) {
        int startRecordForRequery = 1 + ((pageNumber - 1) * recordsPerPage);
        super.setStartRecordForRequery(new BigInteger(startRecordForRequery + ""));
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
     * @return the selectedUsername
     */
    public String getSelectedUsername() {
        return selectedUsername;
    }

    /**
     * @param selectedUsername
     *            the selectedUsername to set
     */
    public void setSelectedUsername(final String selectedUsername) {
        this.selectedUsername = selectedUsername;
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

    /**
     * @return the pageChanged
     */
    public String getPageChanged() {
        return pageChanged;
    }

    /**
     * @param pageChanged
     *            the pageChanged to set
     */
    public void setPageChanged(final String pageChanged) {
        this.pageChanged = pageChanged;
    }

}
