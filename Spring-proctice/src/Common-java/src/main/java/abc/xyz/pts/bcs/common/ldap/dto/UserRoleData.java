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
package abc.xyz.pts.bcs.common.ldap.dto;

import java.util.ArrayList;
import java.util.List;

import abc.xyz.pts.bcs.common.audit.annotation.AuditableBeanProperty;
import abc.xyz.pts.bcs.common.audit.business.Parameter;

public final class UserRoleData implements java.io.Serializable
{
    private static final long serialVersionUID = -81684388575317959L; 
    
    @AuditableBeanProperty(key = Parameter.CODE)
    private String code;
    
    @AuditableBeanProperty(key = Parameter.DESCRIPTION)
    private String descriptionInEnglish;
    
    @AuditableBeanProperty(key = Parameter.DESCRIPTION_LANG)
    private String descriptionInLang;
    
    @AuditableBeanProperty(key = Parameter.PERMISSIONS)
    private List<String> permissionList;
    
    public UserRoleData()
    {
        this.permissionList = new ArrayList<String>();
    }
    
    public String getCode() {
        return code;
    }
    public void setCode(final String code) {
        this.code = code;
    }
    public String getDescriptionInEnglish() {
        return descriptionInEnglish;
    }
    public void setDescriptionInEnglish(final String descriptionInEnglish) {
        this.descriptionInEnglish = descriptionInEnglish;
    }
    public String getDescriptionInLang() {
        return descriptionInLang;
    }
    public void setDescriptionInLang(final String descriptionInLang) {
        this.descriptionInLang = descriptionInLang;
    }
    public List<String> getPermissionList() {
        return permissionList;
    }
    public void setPermissionList(final List<String> permissionList) {
        this.permissionList = permissionList;
    }
    
    /**
     * Add a permission to our list.
     * 
     * @param permission
     */
    public void addPermission(final String permission) {
        this.permissionList.add(permission);
    }

}
