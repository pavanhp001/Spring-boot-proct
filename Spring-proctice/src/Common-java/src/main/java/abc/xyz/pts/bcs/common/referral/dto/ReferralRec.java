/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral.dto;

import java.util.Calendar;
import java.util.List;

import abc.xyz.pts.bcs.common.dto.ReferralLog;



/**
 *
 * @author mmotlib-siddiqui
 *
 * This is closely coupled with the Referral Search Screen.
 *
 */

public interface ReferralRec
{
    Long getReferralNum();
    String getReferralStatus();
    Integer getHighestHitPriority();
    Integer getTotalHits();
    Calendar getCreatedDatetime();
    String getRecommendedAction();
    List<ReferralHitRec> getReferralHits();
    ReferralTravellerRec getTraveller();
    ReferralFlightRec getFlight();
    ReferralLog getLatestReferralLog();
    Long getUpdateVersioNo();
    Long getReferralSeverity();
    void setLatestReferralLog(ReferralLog referralLogRec);
    void setRecommendedAction(String recommendedAction);
    String getAdditionalInstructionCode();
}