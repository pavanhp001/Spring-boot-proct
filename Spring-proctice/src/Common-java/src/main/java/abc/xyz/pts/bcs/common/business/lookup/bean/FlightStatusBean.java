/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2009
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.business.lookup.bean;

/**
 * This is DTO for flight status Code and Description. The sort order is determined by the FlightStatus workflow.
 */
public final class FlightStatusBean extends LookupItem {

    @Override
    public int compareTo(final LookupItem o) {
        // Order is determined by flight status workflow
        // Planned -> Approved -> In Flight -> Landing -> Completed -> Cancelled
        // -> Unknown

        return FlightStatus.valueOf(this.getCode()).code.compareTo(FlightStatus.valueOf(o.getCode()).code);
    }

    enum FlightStatus {
        P(0), C(1), D(2), L(3), T(4), E(5), U(6);
        private Integer code;

        FlightStatus(final Integer code) {
            this.code = code;
        }

    }
}
