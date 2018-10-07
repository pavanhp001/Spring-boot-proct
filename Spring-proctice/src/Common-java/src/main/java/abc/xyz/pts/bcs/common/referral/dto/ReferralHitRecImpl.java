/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral.dto;

import java.io.Serializable;

import abc.xyz.pts.bcs.common.dao.utils.Constants;

public final class ReferralHitRecImpl implements Serializable, ReferralHitRec
{
    private static final long serialVersionUID = -1935760126984609995L;

    private Integer seq;

    private Long hitId;
    private Long referralNum;
    private String hitType;

    private String watchListName;
    private String watchListDesc;

    private Double score;
    private Integer severity;
    private Integer priority;

    private String recommendedActionCode;
    private String recommendedActionDesc;
    private String reasonCode;
    private String reasonDesc;

    private String qualificationStatus;
    private Long updateVersionNo;

    private Long targetWatchlistId;


    @Override
    public Integer getSeq()
    {
        return seq;
    }

    public void setSeq(final Integer seq)
    {
        this.seq = seq;
    }

    @Override
    public Long getHitId()
    {
        return hitId;
    }

    public void setHitId(final Long hitId)
    {
        this.hitId = hitId;
    }

    @Override
    public Long getReferralNum()
    {
        return referralNum;
    }

    public void setReferralNum(final Long referralNum)
    {
        this.referralNum = referralNum;
    }

    @Override
    public String getHitType()
    {
        return hitType;
    }

    public void setHitType(final String hitType)
    {
        this.hitType = hitType;
    }

    @Override
    public String getWatchListName()
    {
        return watchListName;
    }

    public void setWatchListName(final String watchListName)
    {
        this.watchListName = watchListName;
    }

    @Override
    public String getWatchListDesc()
    {
        return watchListDesc;
    }

    public void setWatchListDesc(final String watchListDesc)
    {
        this.watchListDesc = watchListDesc;
    }

    @Override
    public Double getScore()
    {
        return score;
    }

    public void setScore(final Double score)
    {
        this.score = score;
    }

    @Override
    public Integer getSeverity()
    {
        return severity;
    }

    public void setSeverity(final Integer severity)
    {
        this.severity = severity;
    }

    @Override
    public Integer getPriority()
    {
        return priority;
    }

    public void setPriority(final Integer priority)
    {
        this.priority = priority;
    }

    @Override
    public String getRecommendedActionCode()
    {
        return recommendedActionCode;
    }

    public void setRecommendedActionCode(final String recommendedActionCode)
    {
        this.recommendedActionCode = recommendedActionCode;
    }

    @Override
    public String getRecommendedActionDesc()
    {
        return recommendedActionDesc;
    }

    public void setRecommendedActionDesc(final String recommendedActionDesc)
    {
        this.recommendedActionDesc = recommendedActionDesc;
    }

    @Override
    public String getQualificationStatus() {
        return qualificationStatus;
    }

    public void setQualificationStatus(final String qualificationStatus) {
        this.qualificationStatus = qualificationStatus;
    }

    @Override
    public Long getUpdateVersionNo()
    {
        return updateVersionNo;
    }

    public void setUpdateVersionNo(final Long updateVersionNo)
    {
        this.updateVersionNo = updateVersionNo;
    }

    @Override
    public String getReasonCode()
    {
        return reasonCode;
    }

    public void setReasonCode(final String reasonCode)
    {
        this.reasonCode = reasonCode;
    }

    @Override
    public String getReasonDesc()
    {
        return reasonDesc;
    }

    public void setReasonDesc(final String reasonDesc)
    {
        this.reasonDesc = reasonDesc;
    }

    @Override
    public Long getTargetWatchlistId()
    {
        return targetWatchlistId;
    }

    public void setTargetWatchlistId(final Long targetWatchlistId)
    {
        this.targetWatchlistId = targetWatchlistId;
    }

    @Override
    public int compareTo(final ReferralHitRec o) {
        if (this.getQualificationStatus().equals(Constants.HIT_OUT)) {
            return 1;
        } else if (o.getQualificationStatus().equals(Constants.HIT_OUT)) {
            return 0;
        } else {
            return o.getPriority().compareTo(this.getPriority());
        }
    }

      @Override
    public String toString() {
        return "ReferralHitRecImpl [seq=" + seq + ", hitId=" + hitId
                + ", referralNum=" + referralNum + ", hitType=" + hitType
                + ", watchListName=" + watchListName + ", watchListDesc="
                + watchListDesc + ", score=" + score + ", severity=" + severity
                + ", priority=" + priority + ", recommendedActionCode="
                + recommendedActionCode + ", recommendedActionDesc="
                + recommendedActionDesc + ", qualificationStatus="
                + qualificationStatus
                + ", reasonCode=" + reasonCode
                + ", updateVersionNo=" + updateVersionNo
                + ", targetWatchListId=" + targetWatchlistId
                + "]";
    }
}
