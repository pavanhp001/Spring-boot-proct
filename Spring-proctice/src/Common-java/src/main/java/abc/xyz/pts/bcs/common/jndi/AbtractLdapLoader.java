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
package abc.xyz.pts.bcs.common.jndi;

import java.io.InputStream;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * Performs LDAP lookup for system and application properties.
 *
 */
public abstract class AbtractLdapLoader {

    private static final String LDAP_JNDI_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    private static final String LDAP_CREDENTIALS = "ldap.properties.credentials";

    /**
     * Load input stream of data from LDAP with the given lookup
     * @param lookupEntry Lookup name
     * @param ldapProperties Property file containing LDAP settings
     * @return LDAP data
     * @throws NamingException
     */
    protected static InputStream getLdapData(final String lookupEntry) throws NamingException {

        final Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_JNDI_FACTORY);

        // Used by core apps and assumes properties are already loaded from jndi.properties into System Properties
        if (System.getProperty(LDAP_CREDENTIALS) == null){
            env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL));
            env.put(Context.SECURITY_PRINCIPAL, System.getProperty(Context.SECURITY_PRINCIPAL));
            env.put(Context.SECURITY_CREDENTIALS, System.getProperty(Context.SECURITY_CREDENTIALS));
        } else {
            // Assumed properties loaded from jndi.properties used by UI Applications
            env.put(Context.SECURITY_CREDENTIALS, System.getProperty(LDAP_CREDENTIALS));
        }

        final DirContext ctx = new InitialDirContext(env);
        final JndiPropertiesWrapper ldapObject = (JndiPropertiesWrapper)ctx.lookup(lookupEntry);

        return ldapObject.getConfigDataAsInputStream();
    }

}
