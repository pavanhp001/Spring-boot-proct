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

/**
 * This is a common class and it is going to be used across the application It
 * can be used to monitor the counters
 *
 * @author Jpadhi
 *
 */

import org.apache.commons.lang.mutable.MutableInt;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource
public class ManagedMutableInt {

    /** The mutable int count. */
    private final MutableInt iCount;

    /**
     * Constructor.
     *
     * @param initialValue The initial value.
     */
    public ManagedMutableInt(final int initialValue) {
        iCount = new MutableInt(initialValue);
    }

    /**
     * Constructor.
     *
     * @param initialValue The initial value.
     */
    public ManagedMutableInt(final ManagedMutableInt initialValue) {
        iCount = new MutableInt(initialValue.getCount());
    }

    /**
     * Increments the count.
     */
    public void increment() {
        iCount.increment();
    }

    /**
     * Gets the current count.
     *
     * @return The current count.
     */
    public int getCount() {
        return iCount.intValue();
    }

    /**
     * This value is used to rest the counters
     *
     * @return The current count.
     */
    public MutableInt getCountValue() {
        return iCount;
    }
}
