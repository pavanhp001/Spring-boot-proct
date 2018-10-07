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
public enum RecurrenceDepDate {
    SAMEDAY("S"), NEXTDAY("N"), PREVOUSDAY("P");

    private String recurrenceDepDateStr;

    /**
     * Default Constructor
     * @param recDepDate
     */
    private RecurrenceDepDate(final String recDepDate) {
        this.recurrenceDepDateStr = recDepDate;
    }

    /**
     * get RecurrenceType
     * @return
     */
    public String getRecurrenceDepDate() {
        return this.recurrenceDepDateStr;
    }
}
