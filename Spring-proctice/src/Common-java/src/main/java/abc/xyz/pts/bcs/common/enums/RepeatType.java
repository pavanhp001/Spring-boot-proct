/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2012
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.enums;

/**
 * @author Thiruvengadam.S
 *
 */
public enum RepeatType {
    SERIES_OF_FLIGHT("S"), SPECIFIC_FLIGHT("F");

    String repeatType;

    /**
     *
     * @param repeatString
     */
    private RepeatType(final String repeatString) {
        this.repeatType = repeatString;
    }

    /**
     * <code>getRepeatType</code> is used to get the identifier for the given Repeat Type.
     * @return
     */
    public String getRepeatType() {
        return this.repeatType;
    }
}
