/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2012
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.ldap.dao;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.apache.log4j.Logger;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.simple.ParameterizedContextMapper;

import abc.xyz.pts.bcs.common.ldap.dto.UserRoleData;

public class UserRoleAttributeMapper extends ConfigurableAttributesMapper implements ParameterizedContextMapper<UserRoleData> 
{
    private static final Logger LOG = Logger.getLogger(UserRoleAttributeMapper.class);
    
    @Override
    public UserRoleData mapFromContext(final Object ctx) 
    {
        UserRoleData data = new UserRoleData();
        DirContextAdapter adapter = (DirContextAdapter) ctx;
        
        Attributes attrs = adapter.getAttributes();
        data.setCode(getAttributeValue(attrs, LdapConstants.CN));
        data.setDescriptionInEnglish(getAttributeValue(attrs, LdapConstants.DESCRIPTION));
        data.setDescriptionInLang(getAttributeValue(attrs, LdapConstants.DESCRIPTION_LANG));
        data.setPermissionList(getAttributeValueList(attrs, LdapConstants.BUSINESS_CATEGORY));

        return data;
    }

    @Override
    protected String resolveAttributeName(final Enum attributeName) 
    {
        if (attributeName instanceof LdapConstants) {
            return ((LdapConstants) attributeName).getValue();
        } else {
            throw new IllegalArgumentException("Only instances of UserRoleAttributeType are allowed");
        }
    }
    
    
    protected String getAttributeValue(final Attributes attrs, final LdapConstants attributeName) 
    {
        String result = null;
        try {
            String ldapAttrName = attributeName.getValue();
            Attribute attr = attrs.get(ldapAttrName);
            if (attr != null) {
                result = (String) attr.get();
            }
        } catch (NamingException ne) {
            LOG.error("Unable to map attribute: " + attributeName, ne);
        }
        return result;
    }
    
    protected List<String> getAttributeValueList(final Attributes attrs, final LdapConstants attributeName) 
    {
        ArrayList<String> rtnList = new ArrayList<String>();
        try {
            String ldapAttrName = attributeName.getValue();
            Attribute attr = attrs.get(ldapAttrName);
            if (attr != null) {
                Enumeration result = attr.getAll();
                if (result != null) {
                   while (result.hasMoreElements()) {
                       rtnList.add((String)result.nextElement());
                   }
                }
            }
        } catch (NamingException ne) {
            LOG.error("Unable to map attribute: " + attributeName, ne);
        }
        
        return rtnList;
    }

}
