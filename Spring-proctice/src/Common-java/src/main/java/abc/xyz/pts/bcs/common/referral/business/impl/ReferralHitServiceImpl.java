/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2011
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import abc.xyz.pts.bcs.common.dto.ReferralHit;
import abc.xyz.pts.bcs.common.referral.business.ReferralHitService;
import abc.xyz.pts.bcs.common.referral.dao.ReferralHitDAO;
import abc.xyz.pts.bcs.common.referral.dto.ReferralHitTargetRec;
import abc.xyz.pts.bcs.common.referral.dto.ReferralTravellerRec;

@Service("referralHitService")
public class ReferralHitServiceImpl implements ReferralHitService {

    @Autowired
    ReferralHitDAO referralHitDAO;

    @Override
    public ReferralHitTargetRec getReferralHitTarget(final Long hitId)
    {
        return referralHitDAO.getReferralHitTarget(hitId);
    }

    @Override
    public List<ReferralHitTargetRec> getAllReferralHitTargets(final Long refId)
    {
        // Get all hits for this referral
        final List<ReferralHit> allHitsForReferral = referralHitDAO.findByReferralId(refId);

        // Get a ReferralHitTargetRec for each hit
        final List<ReferralHitTargetRec> hitsForReferral = new ArrayList<ReferralHitTargetRec>();

        for (final ReferralHit referralHit:allHitsForReferral){
            hitsForReferral.add(referralHitDAO.getReferralHitTarget(referralHit.getId()));
        }
        return hitsForReferral;
    }

    @Override
    public ReferralTravellerRec getReferralHitTraveller(final Long watchListRequestId)
    {
        return referralHitDAO.getReferralHitTraveller(watchListRequestId);
    }


    @Override
    public ReferralHit findById(final Long referralHitId)
    {
        return referralHitDAO.findById(referralHitId);
    }


}

