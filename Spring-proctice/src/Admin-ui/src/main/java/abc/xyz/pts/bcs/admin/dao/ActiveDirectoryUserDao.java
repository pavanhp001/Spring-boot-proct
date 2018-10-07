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

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.common.ldap.dao.LdapConstants;
import abc.xyz.pts.bcs.common.ldap.support.OrderingSortControlDirContextProcessor;

public class ActiveDirectoryUserDao extends UserDao{
    
	private static final Logger LOGGER = Logger.getLogger(ActiveDirectoryUserDao.class);
	protected LdapTemplate ldapTemplate;
	
    public void setLdapTemplate(final LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }
    
    public User getUser(final String userName) {
    	if(LOGGER.isInfoEnabled()){
    		LOGGER.debug("Getting details from Active directory for the user " + userName);
    	}
        User user = null;

        AndFilter filter = createFilter(userName);
        
        OrderingSortControlDirContextProcessor sortProcessor =  new OrderingSortControlDirContextProcessor(LdapConstants.CN.getValue(), true);
        
        List<User> users = ldapTemplate.search(usersAbsoluteBaseName, filter.encode(), createSearchControls(), userAttributesMapper, sortProcessor);
      
        user = (User) users.get(0);
        
        if(LOGGER.isInfoEnabled()){
    		LOGGER.debug(" Active directory details for the username " + userName + " is " + user);
    	}
        
        return user;
    }

	private AndFilter createFilter(final String userName) {
		AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter(ActiveDirectoryLdapConstants.OBJECT_CLASS.getValue(), ActiveDirectoryLdapConstants.INET_ORG_PERSON.getValue()));
        filter.and(new EqualsFilter(userAttributesMapper.mapAttributeName(UserAttributesMapper.AttributeName.USERNAME), userName));
		return filter;
	}
}
