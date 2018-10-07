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
package abc.xyz.pts.bcs.common.ldap.dao;

import java.util.List;

import javax.naming.directory.SearchControls;

import org.apache.commons.lang.StringUtils;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.simple.SimpleLdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.ldap.filter.PresentFilter;

import abc.xyz.pts.bcs.common.enums.UserPermissionType;
import abc.xyz.pts.bcs.common.ldap.dto.UserRoleData;
import abc.xyz.pts.bcs.common.ldap.exception.RoleExistsException;
import abc.xyz.pts.bcs.common.ldap.support.OrderingSortControlDirContextProcessor;

public class UserRoleDao {

    private SimpleLdapTemplate ldapTemplate;
    private String roleAbsoluteBaseName;
    private UserRoleAttributeMapper roleAttributeMapper;

   
    /**
     * Return all roles from LDAP
     * 
     * @return
     */
    public List<UserRoleData> findAllRoles() 
    {
        final AndFilter andFilter = new AndFilter();
        Filter filter;

        filter = new EqualsFilter(LdapConstants.OBJECT_CLASS.getValue(), LdapConstants.GROUP_OF_URLS.getValue());
        andFilter.and(filter);

        filter = new PresentFilter(LdapConstants.CN.getValue());
        andFilter.and(filter);
        
        filter = new PresentFilter(LdapConstants.MEMBER_URL.getValue());
        andFilter.and(filter);
        
        // ignore items that are not marked to be nsRole
        filter = new LikeFilter(LdapConstants.MEMBER_URL.getValue(), "*nsRoleDN*");
        andFilter.and(filter);

        OrderingSortControlDirContextProcessor sortProcessor = 
                new OrderingSortControlDirContextProcessor(LdapConstants.CN.getValue(), true);
        
        return ldapTemplate.search(roleAbsoluteBaseName, andFilter.encode(), createSearchControls(), roleAttributeMapper, sortProcessor);
    }
    
    /**
     * Return a role by it's code.
     * 
     * @param roleCode
     * @return
     */
    public UserRoleData findByRoleCode(final String roleCode) 
    {
        // we only match the role code
        Filter filter = new EqualsFilter(LdapConstants.CN.getValue(), roleCode);
        
        return ldapTemplate.searchForObject(roleAbsoluteBaseName, filter.encode(), roleAttributeMapper);
    }
    
    
    /**
     * Add all permissions to the ctx for insert - ignoring blanks
     * 
     * @param ctx
     * @param roleData
     */
    private void addPermissions(final DirContextAdapter ctx, final List<String> permissionList)
    {
        if (permissionList == null) {
            return;
        }
        
        for (String permission : permissionList)
        {
            // business category is used for storing permissions
            if (StringUtils.isNotBlank(permission)) {
                ctx.addAttributeValue(LdapConstants.BUSINESS_CATEGORY.getValue(), permission);
            }
        }
    }
    
    /**
     * Add new role.
     * 
     * @param roleData
     */
    public void add(final UserRoleData roleData) throws RoleExistsException
    {
        try
        {
            final String memberURL = "ldap:///" + this.roleAbsoluteBaseName + "??sub?(nsRoleDN=cn=%1$s," + this.roleAbsoluteBaseName + ")";
            
            DistinguishedName dn = new DistinguishedName(roleAbsoluteBaseName);
            dn.append(LdapConstants.CN.getValue(), roleData.getCode());
    
            DirContextAdapter ctx = new DirContextAdapter(dn);
            ctx.setAttributeValue(LdapConstants.OBJECT_CLASS.getValue(), LdapConstants.GROUP_OF_URLS.getValue());
            ctx.addAttributeValue( LdapConstants.DESCRIPTION.getValue()
                                 , StringUtils.defaultIfBlank(roleData.getDescriptionInEnglish(), StringUtils.EMPTY)
                                 );
            ctx.addAttributeValue( LdapConstants.DESCRIPTION_LANG.getValue()
                                 , StringUtils.defaultIfBlank(roleData.getDescriptionInLang(), StringUtils.EMPTY)
                                 );
            ctx.addAttributeValue( LdapConstants.MEMBER_URL.getValue()
                                 , String.format(memberURL, roleData.getCode())
                                 );
    
            // allocate permissions
            addPermissions(ctx, roleData.getPermissionList());
            
            ldapTemplate.bind(ctx);
        } 
        catch (NameAlreadyBoundException nabe) 
        {
            throw new RoleExistsException(nabe);
        }
    }
    
    private void refreshPermissions(final DirContextOperations ctx, final List<String> permissionList)
    {        
        // ** Remove all permissions 
        // ****************************
        UserPermissionType[] allPermissions = UserPermissionType.values();
        for (UserPermissionType pType : allPermissions)
        {
            String permission = pType.getPermission();
            
            // business category is used for storing permissions
            if (StringUtils.isNotBlank(permission)) {
                ctx.removeAttributeValue(LdapConstants.BUSINESS_CATEGORY.getValue(), permission);
            }
        }
        
        if (permissionList == null) {
            return;
        }
        
        // ** Apply selected permissions
        // *****************************
        for (String permission : permissionList)
        {
            // business category is used for storing permissions
            if (StringUtils.isNotBlank(permission)) {
                ctx.addAttributeValue(LdapConstants.BUSINESS_CATEGORY.getValue(), permission);
            }
        }
    }
    
    
    /**
     * Update role and it's permissions.
     * 
     * @param roleData
     */
    public void update(final UserRoleData roleData) 
    {
        DistinguishedName dn = new DistinguishedName(roleAbsoluteBaseName);
        dn.append(LdapConstants.CN.getValue(), roleData.getCode());

        DirContextOperations ctx = ldapTemplate.lookupContext(dn);
        ctx.setAttributeValue( LdapConstants.DESCRIPTION.getValue()
                             , StringUtils.defaultIfBlank(roleData.getDescriptionInEnglish(), StringUtils.EMPTY)
                             );
        ctx.setAttributeValue( LdapConstants.DESCRIPTION_LANG.getValue()
                             , StringUtils.defaultIfBlank(roleData.getDescriptionInLang(), StringUtils.EMPTY)
                             );

        // we need to ensure that the permissions are refreshed
        refreshPermissions(ctx, roleData.getPermissionList());
        
        ldapTemplate.modifyAttributes(ctx);
    }
    
    
    private SearchControls createSearchControls() 
    {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        searchControls.setReturningAttributes(roleAttributeMapper.getAttributeNamesArray());
        searchControls.setReturningObjFlag(true);
        return searchControls;
    }
    
    // to wire from spring xml file
    public void setLdapTemplate(final SimpleLdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    // to wire from spring xml file
    public void setRoleAbsoluteBaseName(final String roleAbsoluteBaseName) {
        this.roleAbsoluteBaseName = roleAbsoluteBaseName;
    }
    
    // to wire from spring xml file
    public void setRoleAttributeMapper(final UserRoleAttributeMapper roleAttributeMapper) {
        this.roleAttributeMapper = roleAttributeMapper;
    }

    
}
