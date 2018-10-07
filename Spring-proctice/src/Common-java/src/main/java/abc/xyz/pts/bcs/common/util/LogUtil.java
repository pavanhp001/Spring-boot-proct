/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.util;

/**
 * @author Deepesh.Rathore
 *
 */
public class LogUtil {

	private static final Float ONE_MILLION_NANOSECONDS = 1000000.0F;
	
    public static float getMilliSecondsSince(final long nanos) {
        return (System.nanoTime() - nanos) / ONE_MILLION_NANOSECONDS;
    }

}
