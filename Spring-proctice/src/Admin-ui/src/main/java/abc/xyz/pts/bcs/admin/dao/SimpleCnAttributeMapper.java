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

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.apache.log4j.Logger;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.simple.ParameterizedContextMapper;

import abc.xyz.pts.bcs.common.ldap.dao.LdapConstants;

public class SimpleCnAttributeMapper implements ParameterizedContextMapper<String> {

    private static final Logger LOG = Logger.getLogger(SimpleCnAttributeMapper.class);

    @Override
    public String mapFromContext(final Object ctx) {
        String result = null;
        try {
            DirContextAdapter adapter = (DirContextAdapter) ctx;
            Attributes attrs = adapter.getAttributes();
            Attribute cnAttr = attrs.get(LdapConstants.CN.getValue());
            result = (String) cnAttr.get();
        } catch (NamingException ne) {
            LOG.error("Unable to map attribute: " + LdapConstants.CN.getValue(), ne);
        }
        return result;
    }

}
