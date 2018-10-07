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
package abc.xyz.pts.bcs.admin.dao;

import javax.naming.directory.Attributes;

import org.springframework.ldap.core.DirContextAdapter;

import abc.xyz.pts.bcs.common.bean.mapper.RoleMapper;
import abc.xyz.pts.bcs.common.ldap.dao.LdapConstants;
import abc.xyz.pts.bcs.common.ldap.dao.UserRoleAttributeMapper;
import abc.xyz.pts.bcs.common.ldap.dto.UserRoleData;

public class ActiveDirectoryUserRoleAttributeMapper extends	UserRoleAttributeMapper {
	
	private RoleMapper roleMapper;

	public void setRoleMapper(final RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}


	@Override
	public UserRoleData mapFromContext(final Object ctx) 
	    {
	        UserRoleData data = new UserRoleData();
	        
	        DirContextAdapter adapter = (DirContextAdapter) ctx;
	        
	        Attributes attrs = adapter.getAttributes();
	        
	        String roleName = getAttributeValue(attrs, LdapConstants.CN);                   
               
	        if(roleMapper.getLdapRole(roleName) != null){
       	    	data.setCode(roleMapper.getLdapRole(roleName).toUpperCase());
	        }
	        data.setDescriptionInEnglish(getAttributeValue(attrs, LdapConstants.NAME));
	        
	        return data;
	    }

}
