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
import java.util.Enumeration;
import java.util.Properties;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * Load System properties from LDAP
 */
public final class LdapConfigLoader extends AbtractLdapLoader {

    private static final Logger LOG = Logger.getLogger(LdapConfigLoader.class);

    /**
     * Load System properties from LDAP with given lookup name
     * @param lookup
      * @throws NamingException
     * @throws IOException
     */
    public static void loadConfig(final String lookup) throws NamingException, IOException {
        final Properties envProperties = new Properties();
        envProperties.load(getLdapData(lookup));

        final Enumeration<Object> propEnum = envProperties.keys();
        while (propEnum.hasMoreElements()) {

            final String key = (String)propEnum.nextElement();
            System.setProperty(key, String.valueOf(envProperties.get(key)));

            if (LOG.isInfoEnabled()) {
                LOG.info("Config arg loaded: " + key + "=" + String.valueOf(envProperties.get(key)));
            }
        }
    }
 }
