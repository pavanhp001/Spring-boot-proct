/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.enums;

/**
 * @author Thiruvengadam.S
 *
 */
public enum ScheduledType {
    SCHEDULED("S"), IMMEDIATE("I"), RECURRENCE("R");

    private String scheduleTypeStr;

    /**
     * Default Constructor
     * @param schStr
     */
    private ScheduledType(final String schStr) {
        this.scheduleTypeStr = schStr;
    }

    /**
     * ScheduledType
     * @return
     */
    public String getScheduledType() {
        return this.scheduleTypeStr;
    }
}
