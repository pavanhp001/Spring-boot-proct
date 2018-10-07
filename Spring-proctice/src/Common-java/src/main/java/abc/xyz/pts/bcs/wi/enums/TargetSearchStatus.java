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
package abc.xyz.pts.bcs.wi.enums;

/**
 * <code>TargetSearchStatus</code> contains the various status
 * available for the Target Search.
 * @author M1001798
 */
public enum TargetSearchStatus {
    ALL("ALL"), ACTIVE("A"), READY("R"), IN_PROGRESS("I"), ERROR("E"),
    DUPLICATE("D");

    /** Status String */
    private String status;

    /**
     *
     * @param argStatus
     */
    private TargetSearchStatus(final String argStatus) {
        this.status = argStatus;
    }

    public String getStatus() {
        return status;
    }
}
