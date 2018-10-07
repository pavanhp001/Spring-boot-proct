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

/**
 * @author ryattapu
 *
 */
public class PriorityHitComparator implements Comparator<Match>, Serializable {
    private static final long serialVersionUID = -8239110866530044557L;
    private final SortOrderType sortOrder;


    public PriorityHitComparator(final SortOrderType order) {
        this.sortOrder = order;
    }

    @Override
    public int compare(final Match match1, final Match match2) {
        double score1 = 0;
        double score2 = 0;
        // QAT-609 For Match with Cleared Documents use 0 for Severity and
        // PercentageMatch values
        // If both Ruled Out
        if ( match1.getClearedDocumentsId() != null && match2.getClearedDocumentsId() != null) {
            score1 = match1.getPercentageMatch() * match1.getSeverityLevel();
            score2 = match2.getPercentageMatch() * match2.getSeverityLevel();
            // If match2 Ruled Out
        } else if (match1.getClearedDocumentsId() == null && match2.getClearedDocumentsId() != null) {
            score1 = match1.getPercentageMatch() * match1.getSeverityLevel();
            // If match1 Ruled Out
        } else if (match2.getClearedDocumentsId() == null && match1.getClearedDocumentsId() != null) {
            score2 = match2.getPercentageMatch() * match2.getSeverityLevel();
        } else {
            score1 = match1.getPercentageMatch() * match1.getSeverityLevel();
            score2 = match2.getPercentageMatch() * match2.getSeverityLevel();
        }

        int compareVal = 0;
        if (score1 > score2) {
            compareVal = 1;
        }
        if (score1 < score2) {
            compareVal = -1;
        }

        if (sortOrder == SortOrderType.DESCENDING) {
            compareVal *= -1;
        }
        return compareVal;
    }
}
