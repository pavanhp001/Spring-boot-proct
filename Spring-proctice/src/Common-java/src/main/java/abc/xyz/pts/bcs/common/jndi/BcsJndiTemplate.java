/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.jndi;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.springframework.jndi.JndiTemplate;

public class BcsJndiTemplate extends JndiTemplate {

    private static final String LDAP_JNDI_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";

    public BcsJndiTemplate() throws NamingException {

        DirContext jndiContext = new InitialDirContext();
        Properties ctxProperties = new Properties();
        Hashtable ctxEnvironment = jndiContext.getEnvironment();

        for (Enumeration<String> ctxEnum = ctxEnvironment.keys(); ctxEnum.hasMoreElements();) {
            String key = (ctxEnum.nextElement());
            ctxProperties.setProperty(key, (String) ctxEnvironment.get(key));
        }
        ctxProperties.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_JNDI_FACTORY);

        super.setEnvironment(ctxProperties);
    }
}
