/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral.dao;

import java.util.List;

import abc.xyz.pts.bcs.common.dto.ReferralHit;
import abc.xyz.pts.bcs.common.referral.dto.ReferralHitTargetRec;
import abc.xyz.pts.bcs.common.referral.dto.ReferralTravellerRec;

public interface ReferralHitDAO {
    ReferralHitTargetRec getReferralHitTarget(Long referralHitId);
    ReferralTravellerRec getReferralHitTraveller(Long watchListRequestId);

    Long insert(final ReferralHit referralHit);
    int update(final ReferralHit referralHit);
    ReferralHit findById(Long id);
    List<ReferralHit>  findByReferralId(Long id);
    List<ReferralHit> getReferralHits();
}
