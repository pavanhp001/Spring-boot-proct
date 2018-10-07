/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral;

import java.util.List;
import java.util.Locale;

import abc.xyz.pts.bcs.common.dto.FlightSegment;
import abc.xyz.pts.bcs.common.dto.Referral;
import abc.xyz.pts.bcs.common.dto.ReferralDetails;
import abc.xyz.pts.bcs.common.dto.ReferralHit;
import abc.xyz.pts.bcs.common.dto.ReferralLog;
import abc.xyz.pts.bcs.common.dto.Traveller;
import abc.xyz.pts.bcs.common.enums.ReferralEventType;
import abc.xyz.pts.bcs.common.exception.ApplicationException;
import abc.xyz.pts.bcs.irisk.mvo.riskmatch.Response;

public interface ReferralService
{
    List<ReferralLog> getReferralLogs(Long referralId);
    void updateReferral(Referral referral, final String oldActionCode, String eventType, Locale locale) throws ApplicationException;
    void insert(ReferralLog referralLog);
    Referral getReferralById(Long referralId);
    Referral getReferralById(Long referralId, Long versionNo) throws ApplicationException;
    Long calculatePriority(final Double hitScore, final Long severityLevel);
    Long calculateAppPriority();
    Referral addReferralAndLog(Referral newReferral);
    ReferralHit saveReferralHitAndLog(ReferralHit newReferralHit, String remarks, Response response);
    ReferralHit saveReferralHitAndLog(Traveller traveller, ReferralHit newReferralHit, FlightSegment  flightSegment);
    ReferralDetails findReferralDetailsByTravellerId(Long traId);
    String getAppRecommendedActionCode();
    Long getAppHitScore();
    Long getAppSeverityLevel();
    void closeReferral(Referral referral, final String eventType, final String closureNote, final Locale locale, final boolean closeOnSave) throws ApplicationException ;
    void acknowledgeReferral(Referral referral, final Locale locale);
    Referral findReferralByTravellerId(Long travellerId);
     void createReferralLog(Referral referral, ReferralEventType eventType);
      void updateReferralStatus(Referral referral);

}
