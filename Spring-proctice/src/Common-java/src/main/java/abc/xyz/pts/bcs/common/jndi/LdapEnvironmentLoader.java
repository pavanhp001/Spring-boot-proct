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


import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.log4j.Logger;

/**
 * Load System properties from LDAP
 */
public final class LdapEnvironmentLoader {

    private static final Logger LOG = Logger.getLogger(LdapEnvironmentLoader.class);

    /**
     * Load System properties from LDAP with given lookup name. Assumes jndi.properties file on classpath
     * @param lookup
      * @throws NamingException
     * @throws IOException
     */
    public static void loadConfig(final String lookup) throws NamingException, IOException {
        final Properties envProperties = new Properties();

          final DirContext ctx = new InitialDirContext();
        final JndiPropertiesWrapper ldapObject = (JndiPropertiesWrapper)ctx.lookup(lookup);
        ctx.close();

        envProperties.load(ldapObject.getConfigDataAsInputStream());

        for(final Map.Entry<Object, Object> element : envProperties.entrySet()) {

            final String key = (String)element.getKey();
            System.setProperty(key, String.valueOf(element.getValue()));

            if (LOG.isInfoEnabled()) {
                LOG.info("Config arg loaded: " + key + "=" + String.valueOf(envProperties.get(key)));
            }
        }
    }
 }
