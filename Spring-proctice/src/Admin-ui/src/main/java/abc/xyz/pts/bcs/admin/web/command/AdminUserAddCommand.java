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

import abc.xyz.pts.bcs.common.audit.annotation.AuditableCommand;
import abc.xyz.pts.bcs.common.audit.business.Event;

/**
 * @author DGroves
 * @version: $Id: AdminUserAddCommand.java 803 2009-07-16 15:38:16Z tterry $
 */
@AuditableCommand(Event.CREATE_NEW_USER)
public class AdminUserAddCommand extends AdminUser {
    private boolean userAdded = false;

    /**
     * @return the userAdded
     */
    public boolean isUserAdded() {
        return userAdded;
    }

    /**
     * @param userAdded
     *            the userAdded to set
     */
    public void setUserAdded(final boolean userAdded) {
        this.userAdded = userAdded;
    }

}
