/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.dao;

import abc.xyz.pts.bcs.wi.dto.TargetItem;

public interface ClearedDocumentsSearchDao
{
    /**
     *  QAT-609 Match target against Cleared Document using:
     *    Given name(s)
     *    Family name
     *     Doc Nationality
     *    Doc Type
     *    Doc Number
     *    Date of Birth
     *
     *  Also, the current date is compared against the expiry date of the
     *  Cleared doc to ensure it is valid.
     *
     *  All the fields MUST match for a cleared document to be counted.
     *  If there is not 100% match across all fields, then there no cleared
     *  doc id is returned.  If the date has expired then no cleared doc is returned.
     *
     * @param traveller - contains the matching fields.
     * @param targetId - the id of the target we are processing.
     * @return the ID of a cleared document or null if no match.
     */
    public Long getClearedDocId(final Long targetId, final TargetItem traveller);

}
