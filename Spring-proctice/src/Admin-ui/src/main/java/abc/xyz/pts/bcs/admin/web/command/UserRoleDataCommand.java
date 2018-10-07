/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2012
 * All rights reserved.
 */
package abc.xyz.pts.bcs.admin.web.command;

import java.util.List;

import abc.xyz.pts.bcs.common.audit.annotation.AuditPropertyGroup;
import abc.xyz.pts.bcs.common.audit.annotation.AuditPropertyGroups;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableBeanProperty;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableCommand;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.audit.business.Parameter;
import abc.xyz.pts.bcs.common.ldap.dto.UserRoleData;

@AuditableCommand(Event.ADD_ROLE)
@AuditPropertyGroups(auditWhenNoGroupMatch = false, value = {
	    @AuditPropertyGroup(name = Event.VIEW_ROLE, groupProperty = "action", propertyValue = "viewUserRole"),
	    @AuditPropertyGroup(name = Event.UPDATE_ROLE, groupProperty = "action", propertyValue = "editUserRole"),
	    @AuditPropertyGroup(name = Event.ADD_ROLE, groupProperty = "action", propertyValue = "addUserRole")
	    })
public class UserRoleDataCommand implements java.io.Serializable 
{
    private static final long serialVersionUID = 1517574826358137307L;

    private boolean userRoleAdded;
    private boolean updateSuccess;
    private String action;
    private String selectedCode;
    
    public UserRoleDataCommand() {
        super();
    }
    
    public UserRoleDataCommand(final String action) {
        this.action = action;
    }
    
	@AuditableBeanProperty(key = Parameter.CODE)
	private String code;

	@AuditableBeanProperty(key = Parameter.DESCRIPTION)
	private String description;
	
	@AuditableBeanProperty(key = Parameter.DESCRIPTION_LANG)
	private String descriptionLang;
	
	@AuditableBeanProperty(key = Parameter.PERMISSIONS)
	private List<String> permissionList;

    public UserRoleDataCommand(final UserRoleData data)
    {
        if (data != null) {
            this.code = data.getCode();
            this.description = data.getDescriptionInEnglish();
            this.descriptionLang = data.getDescriptionInLang();
            this.permissionList = data.getPermissionList();
        }
    }
    
    public String getCode() {
        return code;
    }
    public void setCode(final String code) {
        this.code = code;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(final String description) {
        this.description = description;
    }
    public String getDescriptionLang() {
        return descriptionLang;
    }
    public void setDescriptionLang(final String descriptionLang) {
        this.descriptionLang = descriptionLang;
    }
    public List<String> getPermissionList() {
        return permissionList;
    }
    public void setPermissionList(final List<String> permissionList) {
        this.permissionList = permissionList;
    }
	
    public UserRoleData toUserRoleData() {
        UserRoleData data = new UserRoleData();
        
        data.setCode(this.code);
        data.setDescriptionInEnglish(this.description);
        data.setDescriptionInLang(this.descriptionLang);
        data.setPermissionList(this.permissionList);
        
        return data;
    }

    public boolean isUserRoleAdded() {
        return userRoleAdded;
    }
    
    public void setUserRoleAdded(final boolean userRoleAdded) {
        this.userRoleAdded = userRoleAdded;
    }

    public void setUpdateSuccess(final boolean updateSuccess) {
        this.updateSuccess = updateSuccess;
    }

    public final boolean isUpdateSuccess() {
        return updateSuccess;
    }

    public String getAction() {
        return action;
    }
    
    public void setAction(final String action) {
        this.action = action;
    }

    public final String getSelectedCode() {
        return selectedCode;
    }

    public final void setSelectedCode(final String selectedCode) {
        this.selectedCode = selectedCode;
    }

}
