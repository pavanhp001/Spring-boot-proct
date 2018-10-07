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

import abc.xyz.pts.bcs.common.annotation.SensitiveProperty;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableBeanProperty;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableCommand;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.audit.business.Parameter;

/**
 *
 * @author DGroves
 * @version: $Id: AdminChangePasswordCommand.java 560 2009-05-15 15:42:51Z tterry $
 */
@AuditableCommand(Event.CHANGE_PASSWORD)
public class AdminChangePasswordCommand {

    @AuditableBeanProperty(key = Parameter.USERNAME)
    private String userName;
    @SensitiveProperty
    private String oldPassword;
    @SensitiveProperty
    private String newPassword;
    @SensitiveProperty
    private String confirmPassword;
    private String userType;
    private String displayUpdatePopin;

    public String getDisplayUpdatePopin() {
        return displayUpdatePopin;
    }

    public void setDisplayUpdatePopin(final String displayUpdatePopin) {
        this.displayUpdatePopin = displayUpdatePopin;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(final String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(final String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(final String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public final Object copyProperties(final Object dest) throws Exception {
        /*
         * PropertyDescriptor[] properties = PropertyUtils.getPropertyDescriptors(this); Class srcClass =
         * this.getClass(); for (PropertyDescriptor propDescriptor : properties) { Field field = null; try { field =
         * srcClass.getDeclaredField(propDescriptor.getName()); } catch (NoSuchFieldException nsfe) { //field is
         * inherited, we can't check for annotation } if ((field == null ||
         * !field.isAnnotationPresent(SensitiveProperty.class)) && !"class".equals(propDescriptor.getName()) &&
         * propDescriptor.getWriteMethod() != null) {
         *
         * try { Object copiedValue = PropertyUtils.getProperty(this, propDescriptor.getName());
         * PropertyUtils.setProperty(dest, propDescriptor.getName(), copiedValue); } catch (NoSuchMethodException nsme)
         * { // ignore hidden methods } } }
         */
        return dest;
    }
}
