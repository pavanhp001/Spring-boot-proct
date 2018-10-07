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
package abc.xyz.pts.bcs.admin.ldap.support;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.event.EventDirContext;
import javax.naming.event.NamingListener;

import org.springframework.ldap.core.ContextExecutor;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.LikeFilter;

import abc.xyz.pts.bcs.common.ldap.dao.LdapConstants;

public class CacheListenerContextExecutor implements ContextExecutor {

    private String relativeBaseName = null;
    private NamingListener listener = null;

    public CacheListenerContextExecutor(final String relativeBaseName, final NamingListener listener) {
        this.relativeBaseName = relativeBaseName;
        this.listener = listener;
    }

    @Override
    public Object executeWithContext(final DirContext ctx) throws NamingException {

        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setReturningObjFlag(true);
        EventDirContext resultContext = (EventDirContext) ctx.lookup(relativeBaseName);
        Filter likeFilter = new LikeFilter(LdapConstants.CN.getValue(), "*");

        resultContext.addNamingListener("", likeFilter.encode(), searchControls, listener);

        return null;

    }

}
