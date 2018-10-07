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
 * <code>ScanType</code> contains the different scan type, such as New,
 * Marked, All
 *
 * @author Jpadhi
 *
 */
public enum ScanType {
    ALL("A"), NEW("N"), MARKED("M");

    private String scanType;

    /**
     * Constructor
     * @param statusString
     */
    private ScanType(final String scanTypeString) {
        this.scanType = scanTypeString;
    }

    public String getScanTypeValue() {
        return this.scanType;
    }
}
