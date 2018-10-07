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

import org.junit.Test;
import junit.framework.TestCase;

/**
 * Testcase to determine the count of message
 * 
 * @author Jagada Padhi
 */
public class ManagedMutableIntTest extends TestCase {

    private ManagedMutableInt managedMutableInt;

    @Override
    protected void setUp() throws Exception {
        managedMutableInt = new ManagedMutableInt(5);
    }

    @Test
    public void testGetCount() {
        int msgCount = managedMutableInt.getCount();
        assertEquals(5, msgCount);

    }

    @Test
    public void testIncrement() {
        managedMutableInt.increment();
        int msgCountAfterIncrement = managedMutableInt.getCount();
        assertEquals(6, msgCountAfterIncrement);
    }
}
