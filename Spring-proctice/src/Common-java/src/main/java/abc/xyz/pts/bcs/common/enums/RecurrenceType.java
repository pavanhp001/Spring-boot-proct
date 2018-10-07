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
 * @author Arulkumar.K
 *
 */
public enum RecurrenceType {
    DAILY("DAILY"), WEEKLY("WEEKLY"), TIMEINTERVAL("TIMEINTERVAL");

    private String recurrenceTypeStr;

    /**
     * Default Constructor
     * @param recType
     */
    private RecurrenceType(final String recType) {
        this.recurrenceTypeStr = recType;
    }

    /**
     * get RecurrenceType
     * @return
     */
    public String getRecurrenceType() {
        return this.recurrenceTypeStr;
    }
}
