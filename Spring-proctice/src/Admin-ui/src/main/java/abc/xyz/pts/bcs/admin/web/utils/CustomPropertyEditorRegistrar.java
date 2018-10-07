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
package abc.xyz.pts.bcs.admin.web.utils;

import java.util.Date;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

import abc.xyz.pts.bcs.admin.business.UserStatus;
import abc.xyz.pts.bcs.admin.web.command.Action;

public class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar {

    private UserStatusEditor userStatusEditor;
    private StringArrayEditor stringArrayEditor;
    private ActionEditor actionEditor;
    private DateEditor dateEditor;

    /**
     * @param dateEditor
     *            the dateEditor to set
     */
    public void setDateEditor(final DateEditor dateEditor) {
        this.dateEditor = dateEditor;
    }

    /**
     * @param actionEditor
     *            the actionEditor to set
     */
    public void setActionEditor(final ActionEditor actionEditor) {
        this.actionEditor = actionEditor;
    }

    /**
     * @param stringArrayEditor
     *            the stringArrayEditor to set
     */
    public void setStringArrayEditor(final StringArrayEditor stringArrayEditor) {
        this.stringArrayEditor = stringArrayEditor;
    }

    public void registerCustomEditors(final PropertyEditorRegistry registry) {
        registry.registerCustomEditor(UserStatus.class, userStatusEditor);
        registry.registerCustomEditor(Action.class, actionEditor);
        registry.registerCustomEditor(String[].class, stringArrayEditor);
        registry.registerCustomEditor(Date.class, dateEditor);
    }

    public void setUserStatusEditor(final UserStatusEditor cce) {
        userStatusEditor = cce;
    }
}
