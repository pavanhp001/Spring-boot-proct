/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2011
 * All rights reserved.
 */
package abc.xyz.pts.bcs.admin.web.command;

import java.util.List;

import abc.xyz.pts.bcs.common.audit.annotation.AuditableCommand;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.ldap.dto.UserRoleData;

@AuditableCommand(Event.USER_ROLE_LIST)
public class UserRoleListCommand implements java.io.Serializable 
{
    private static final long serialVersionUID = 1517574826358137307L;

    private List<UserRoleData> userRoleList;
    private String selectedRowCode;
    private String action;
    
    public List<UserRoleData> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(final List<UserRoleData> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public String getSelectedRowCode() {
        return selectedRowCode;
    }

    public void setSelectedRowCode(final String selectedRowCode) {
        this.selectedRowCode = selectedRowCode;
    }

    public String getAction() {
        return action;
    }

    public void setAction(final String action) {
        this.action = action;
    }
    
}
