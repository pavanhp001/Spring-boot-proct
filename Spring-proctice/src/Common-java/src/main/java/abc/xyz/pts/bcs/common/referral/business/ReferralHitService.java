/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral.business;

import java.util.List;

import abc.xyz.pts.bcs.common.dto.ReferralHit;
import abc.xyz.pts.bcs.common.referral.dto.ReferralHitTargetRec;
import abc.xyz.pts.bcs.common.referral.dto.ReferralTravellerRec;

/**
 * @author Sudheendra.Singh
 *
 */
public interface ReferralHitService {

    ReferralHitTargetRec getReferralHitTarget(Long hitId);
    List<ReferralHitTargetRec> getAllReferralHitTargets(Long refId);
    ReferralTravellerRec getReferralHitTraveller(Long watchListRequestId);

    /**
     * Find referral hit record by its id.
     *
     * @param referralHitId
     * @return referralHit
     */
    ReferralHit findById(Long referralHitId);
}
