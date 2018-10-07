/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.comparator;

import java.io.Serializable;
import java.util.Comparator;

import abc.xyz.pts.bcs.common.enums.SortOrderType;
import abc.xyz.pts.bcs.irisk.mvo.riskmatch.Match;

public class SeverityComparator implements Comparator<Match>, Serializable {
    private static final long serialVersionUID = -31059598411383006L;
    private final SortOrderType sortOrder;

    public SeverityComparator(final SortOrderType order) {
        this.sortOrder = order;
    }

    @Override
    public int compare(final Match match1, final Match match2) {
        long o1Severity = 0;
        long o2Severity = 0;
        // For Match with Cleared Documents use 0 for Severity and
        // PercentageMatch values
        // If both Ruled Out
        if (match1.getClearedDocumentsId() != null && match2.getClearedDocumentsId() != null) {
            o1Severity = match1.getSeverityLevel() == null ? 0 : match1.getSeverityLevel();
            o2Severity = match2.getSeverityLevel() == null ? 0 : match2.getSeverityLevel();
            // If o2 Ruled Out
        } else if (match1.getClearedDocumentsId() == null && match2.getClearedDocumentsId() != null) {
            o1Severity = match1.getSeverityLevel() == null ? 0 : match1.getSeverityLevel();
            // If o1 Ruled Out
        } else if (match2.getClearedDocumentsId() == null && match1.getClearedDocumentsId() != null) {
            o2Severity = match2.getSeverityLevel() == null ? 0 : match2.getSeverityLevel();
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
