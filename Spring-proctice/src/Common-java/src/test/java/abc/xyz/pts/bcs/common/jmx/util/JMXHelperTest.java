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

import java.lang.management.ManagementFactory;

import org.junit.Test;
import org.springframework.jmx.export.MBeanExporter;

import junit.framework.TestCase;

/**
 * This is to test the JMX registartion
 * 
 * @author Jagada Padhi
 */
public class JMXHelperTest extends TestCase {

    @Test
    public void testRegisterBean() {
        MBeanExporter exporter = new MBeanExporter();
        exporter.setServer(ManagementFactory.getPlatformMBeanServer());
        String domainName = "GSL.WI.monitor";
        String mBeanName = "messages.counts.sent";
        ManagedMutableInt mn = new ManagedMutableInt(1);

        try {
            JMXHelper.registerMBean(exporter, domainName, mBeanName, mn);

        } catch ( Exception e ) {
            assertTrue(false);
        }
    }

}
