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
import java.util.Map;

import abc.xyz.pts.bcs.common.dto.Referral;
import abc.xyz.pts.bcs.common.dto.ReferralDetails;
import abc.xyz.pts.bcs.common.dto.ReferralHit;
import abc.xyz.pts.bcs.common.dto.ReferralLog;
import abc.xyz.pts.bcs.common.dto.Traveller;

public interface ReferralDAOInterface {

    // Referral
    Long insert(final Referral referral);
    int update(final Referral referral);
    List<Referral> getReferrals();
    Referral getReferralById(final Long referralId);
    Referral getReferralById(final Long referralId, final Long versionNo);
    Referral findById(final Long id);
    Referral findByTravellerId(final Long travellerId);

    ReferralDetails findReferralDetailsByTravellerId(Long traId);

    // Referral Hits
    Long insertReferralHit(final ReferralHit referralHit);
    int update(final ReferralHit referralHit);
    List<ReferralHit> getReferralHits(final Long refId);
    Map<Long, ReferralHit> getReferralHitsMap(final Long refId);

    // Referral Log
    int insert(final ReferralLog referralLogRec);
    List<ReferralLog> getReferralLogs(final Long referralId);
    ReferralLog getLatestReferralLog(final Long referralId);


    Traveller findTravellerWithoutReferrals();

    int updateNotificationStatus(final Long id, final String alertActionType, final Long updateVersionNumber);
    Long establishHighestReferralSeverity(final Long referralId);
}
