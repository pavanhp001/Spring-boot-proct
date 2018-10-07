/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */

package abc.xyz.pts.bcs.common.referral.dto;

/**
 *
 * @author mmotlib-siddiqui
 *
 */
public interface ReferralHitRec extends Comparable<ReferralHitRec>
{
    Integer getSeq();
    Long getHitId();
    Long getReferralNum();
    String getHitType();
    String getWatchListName();
    String getWatchListDesc();
    Double getScore();
    Integer getSeverity();
    Integer getPriority();
    String getRecommendedActionCode();
    String getRecommendedActionDesc();
    String getReasonDesc();
    String getReasonCode();
    String getQualificationStatus();
    Long getUpdateVersionNo();
    Long getTargetWatchlistId();

}
