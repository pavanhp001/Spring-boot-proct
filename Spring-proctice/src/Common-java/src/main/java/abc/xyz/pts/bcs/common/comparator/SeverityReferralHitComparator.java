/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.comparator;

import static abc.xyz.pts.bcs.common.enums.QualificationStatusType.OUT;

import java.io.Serializable;
import java.util.Comparator;

import abc.xyz.pts.bcs.common.dto.ReferralHit;
import abc.xyz.pts.bcs.common.enums.SortOrderType;

public class SeverityReferralHitComparator implements Comparator<ReferralHit>, Serializable {
    private static final long serialVersionUID = -31059598411383006L;
    private final SortOrderType sortOrder;

    public SeverityReferralHitComparator(final SortOrderType order) {
        this.sortOrder = order;
    }

    @Override
    public int compare(final ReferralHit match1, final ReferralHit match2) {
        long o1Severity = 0;
        long o2Severity = 0;
        // For Match with Cleared Documents use 0 for Severity and
        // PercentageMatch values
        // If both Ruled Out
        if ( OUT.name().equals(match1.getQualificationStatus()) && OUT.name().equals(match2.getQualificationStatus())) {
            o1Severity = match1.getSeverityLevel() == null ? 0 : match1.getSeverityLevel();
            o2Severity = match2.getSeverityLevel() == null ? 0 : match2.getSeverityLevel();
            // If match2 Ruled Out
        } else if (OUT.name().equals(match2.getQualificationStatus())) {
            o1Severity = match1.getSeverityLevel() == null ? 0 : match1.getSeverityLevel();
            // If match1 Ruled Out
        } else if (OUT.name().equals(match1.getQualificationStatus())) {
            o2Severity = match2.getSeverityLevel() == null ? 0 : match2.getSeverityLevel();
        // If match1 Ruled Out
        } else {
            o1Severity = match1.getSeverityLevel() == null ? 0 : match1.getSeverityLevel();
            o2Severity = match2.getSeverityLevel() == null ? 0 : match2.getSeverityLevel();
        }

        int compareVal = 0;
        if (o1Severity > o2Severity){
            compareVal = 1;
        }
        if (o1Severity < o2Severity) {
            compareVal = -1;
        }
        if (sortOrder == SortOrderType.DESCENDING) {
            compareVal *= -1;
        }

        return compareVal;
    }

}
