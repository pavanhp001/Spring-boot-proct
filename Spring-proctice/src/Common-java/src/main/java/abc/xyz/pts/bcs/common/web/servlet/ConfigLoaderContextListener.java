/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.web.servlet;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import abc.xyz.pts.bcs.common.jndi.LdapConfigLoader;

public class ConfigLoaderContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(ConfigLoaderContextListener.class);

    @Override
    public void contextDestroyed(final ServletContextEvent arg0) {
        // do nothing
    }

    @Override
    public void contextInitialized(final ServletContextEvent arg0) {

        try {
            LdapConfigLoader.loadConfig("cn=env");
            LdapConfigLoader.loadConfig("cn=amq");
            LdapConfigLoader.loadConfig("cn=permissions");
        } catch (NamingException e) {
            LOG.fatal("Failed to load BCS config from LDAP");
        } catch (IOException e) {
            LOG.fatal("Failed to load BCS config from LDAP");
        }
    }
}
