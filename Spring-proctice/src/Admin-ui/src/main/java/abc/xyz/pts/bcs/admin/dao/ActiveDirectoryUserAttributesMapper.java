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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.ldap.HasControls;

import org.apache.log4j.Logger;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DistinguishedName;

import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.common.bean.mapper.LocationCodeMapper;
import abc.xyz.pts.bcs.common.bean.mapper.RoleMapper;
import abc.xyz.pts.bcs.common.ldap.dao.LdapConstants;

public class ActiveDirectoryUserAttributesMapper extends UserAttributesMapper {
	private static final String EMPTY_STRING = "";
    private static final Logger LOGGER = Logger.getLogger(LdapUserAttributesMapper.class);
    private RoleMapper roleMapper;
    
    private LocationCodeMapper locCodeMapper;
    
	public void setRoleMapper(final RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	public void setLocCodeMapper(final LocationCodeMapper locCodeMapper) {
		this.locCodeMapper = locCodeMapper;
	}

	@SuppressWarnings("rawtypes")
	@Override
    public User mapFromContextWithControls(final Object ctx, final HasControls hasControls) {
        User user = new User();
        try {
            DirContextAdapter adapter = (DirContextAdapter) ctx;
            Attributes attrs = adapter.getAttributes();

            user.setEmail(getStringAttribute(attrs, AttributeName.EMAIL));
            user.setFaxNumber(getStringAttribute(attrs, AttributeName.FAX));
            user.setForename(getStringAttribute(attrs, AttributeName.FORENAME));
            user.setLastname(getStringAttribute(attrs, AttributeName.LASTNAME));
            user.setName(getStringAttribute(attrs, AttributeName.NAME));
            user.setLocation(extractLocationFromDept(getStringAttribute(attrs, AttributeName.LOCATION)));
            user.setMobileNumber(getStringAttribute(attrs, AttributeName.MOBILE));
            user.setUsername(getStringAttribute(attrs, AttributeName.USERNAME));
            user.setModifyTimestamp(getStringAttribute(attrs, AttributeName.MODIFY_TIMESTAMP));

            Attribute pwdChangedAttribute = attrs.get(attributeNames.get(AttributeName.PASSWORD_CHANGED.value()));

            if (pwdChangedAttribute != null) {
                Long dateStr = Long.parseLong((String)pwdChangedAttribute.get());
                Date pwdChangedDate = getTime(dateStr).getTime();
                user.setPasswordChangeDate(pwdChangedDate);
            }

            Attribute roleAttribute = attrs.get(attributeNames.get(AttributeName.ROLE.value()));
            if (roleAttribute != null) {
                List<String> roles = new ArrayList<String>();
				NamingEnumeration rolesEnum = roleAttribute.getAll();
                while (rolesEnum.hasMoreElements()) {
                    String roleStr = (String) rolesEnum.next();
                    DistinguishedName roleDn = new DistinguishedName(roleStr);
                    String roleName = roleDn.getValue(LdapConstants.CN.getValue()); 
                    if(roleMapper.getLdapRole(roleName) != null){
                    	roles.add( roleMapper.getLdapRole(roleName).toUpperCase());
                    }
                }
                user.setRoles(roles.toArray(new String[roles.size()]));
            }

        } catch (NamingException ne) {
            LOGGER.error("Unable to map role attribute", ne);
        }
        return user;
    }
    
    private static Calendar getTime(final long pwdLastSet)
    {
      long javaTime = pwdLastSet - 0x19db1ded53e8000L;
      javaTime /= 10000;

      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(javaTime);
      return cal;
    }   
    
    private String extractLocationFromDept(final String department) {	 
   	 if(null == department) {
   		 return EMPTY_STRING;
   	 }
   	 
   	 StringTokenizer stringTokenizer = new StringTokenizer(department, ",; ");	
        try{
       	 return locCodeMapper.getLocationCode(stringTokenizer.nextToken());
       	 
        }catch (NoSuchElementException nsee){
       	 LOGGER.info("Unable to extract the location from department " + department);
        }
       return EMPTY_STRING;
    }

  
}
