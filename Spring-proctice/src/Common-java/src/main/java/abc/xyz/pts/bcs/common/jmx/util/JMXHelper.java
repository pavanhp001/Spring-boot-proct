/* **************************************************************************
 *                              - CONFIDENTIAL                           *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.jmx.util;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.apache.log4j.Logger;
import org.springframework.jmx.export.MBeanExporter;

/**
 * A helper class to register an mBean so that this code is not repeated
 * throughout the system.
 *
 * @author Arulkumar Kandasamy
 */
public final class JMXHelper {

    public static final Logger LOG = Logger.getLogger(JMXHelper.class);

    /**
     * Private default constructor.
     */
    private JMXHelper() {
        super();
    }

    /**
     * Registers an mBean.
     *
     * @param exporter The JMX Exporter.
     * @param domainName The domain name to use.
     * @param mBeanName The mBean name.
     * @param object The object to register.
     */
    public static void registerMBean(final MBeanExporter exporter,
            final String domainName, final String mBeanName,
            final Object object) {

        if (exporter != null) {
            try {
                final ObjectName objectName =
                        new ObjectName(domainName, "name", mBeanName);
                exporter.registerManagedResource(object, objectName);
            } catch (MalformedObjectNameException mone) {
                LOG.error("Cannot register JMX Object with domain name: "
                        + domainName + ", and mBeanName:" + mBeanName, mone);
            }
        }
    }
}
