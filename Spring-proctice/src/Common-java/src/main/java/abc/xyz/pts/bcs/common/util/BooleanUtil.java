/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.util;

public final class BooleanUtil 
{
    private static final String YES = "Y";
    private static final String NO = "N";
    
    private BooleanUtil()
    {
        super();
    }
    
    /**
     * Converts Boolean value to Y or N
     * 
     * @param val
     * @return
     */
    public static String getYorN(final Boolean val)
    {
        if (val == null) {
            return null;
        }
        return (val ? YES : NO);
    }

}
