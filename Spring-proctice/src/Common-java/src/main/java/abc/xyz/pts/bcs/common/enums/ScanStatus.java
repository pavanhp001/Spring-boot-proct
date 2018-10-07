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
 * <code>ScanStatus</code> contains the different scanned status, such as NOT_VALIDATED,
 * VALIDATING, SCHEDULED, REQUESTED, etc.
 *
 * @author Thiruvengadam.S
 *
 */
public enum ScanStatus {
    NOT_VALIDATED("N"), VALIDATING("V"), SCHEDULED("S"), REQUESTED("R"), PROCESSED("P"), ERROR("E"),
    CANCELLED("C");

    private String scanStatus;
    /**
     * Constructor
     * @param statusString
     */
    private ScanStatus(final String statusString) {
        scanStatus = statusString;
    }

    public String getScanValue() {
        return scanStatus;
    }
}
