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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import abc.xyz.pts.bcs.common.dto.ReferralLog;

/**
 *
 * @author mmotlib-siddiqui
 *
 */
public final class ReferralRecImpl implements Serializable, ReferralRec {
    private static final long serialVersionUID = 2438084060004897509L;

    private Long referralNum; // reference number
    private String referralStatus;
    private Integer highestHitPriority;
    private Integer totalHits; // do we need this?
    private Calendar createdDatetime;
    private String recommendedAction; // initially is the highest
    private List<ReferralHitRec> referralHits;
    private ReferralTravellerRec traveller;
    private ReferralFlightRec flight;
    private ReferralLog latestReferralLog;
    private Long updateVersioNo;
    private Long referralSeverity;
    private String additionalInstructionCode;

    public ReferralRecImpl() {
        traveller = new ReferralTravellerRecImpl();
        flight = new ReferralFlightRecImpl();
        referralHits = new ArrayList<ReferralHitRec>();
    }

    @Override
    public Long getReferralNum() {
        return referralNum;
    }

    public void setReferralNum(final Long referralNum) {
        this.referralNum = referralNum;
    }
    @Override
    public String getReferralStatus() {
        return referralStatus;
    }

    public void setReferralStatus(final String referralStatus) {
        this.referralStatus = referralStatus;
    }
    @Override
    public Integer getHighestHitPriority() {
        return highestHitPriority;
    }

    public void setHighestHitPriority(final Integer highestHitPriority) {
        this.highestHitPriority = highestHitPriority;
    }
    @Override
    public Integer getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(final Integer totalHits) {
        this.totalHits = totalHits;
    }
    @Override
    public Calendar getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(final Calendar createdDatetime) {
        this.createdDatetime = createdDatetime;
    }
    @Override
    public String getRecommendedAction() {
        return recommendedAction;
    }

    public void setRecommendedAction(final String recommendedAction) {
        this.recommendedAction = recommendedAction;
    }
    @Override
    public List<ReferralHitRec> getReferralHits() {
        return referralHits;
    }

    public void setReferralHits(final List<ReferralHitRec> referralHits) {
        this.referralHits = referralHits;
    }
    @Override
    public ReferralTravellerRec getTraveller() {
        return traveller;
    }

    public void setTraveller(final ReferralTravellerRec traveller) {
        this.traveller = traveller;
    }
    @Override
    public ReferralFlightRec getFlight() {
        return flight;
    }

    public void setFlight(final ReferralFlightRec flight) {
        this.flight = flight;
    }
    @Override
    public ReferralLog getLatestReferralLog() {
        return latestReferralLog;
    }

    public void setLatestReferralLog(final ReferralLog latestReferralLog) {
        this.latestReferralLog = latestReferralLog;
    }

    @Override
    public Long getUpdateVersioNo() {
        return updateVersioNo;
    }

    public void setUpdateVersioNo(final Long updateVersioNo) {
        this.updateVersioNo = updateVersioNo;
    }

    @Override
    public Long getReferralSeverity() {
        return this.referralSeverity;
    }

    public void setReferralSeverity(final Long referralSeverity) {
        this.referralSeverity = referralSeverity;
    }
    
    @Override
    public String getAdditionalInstructionCode() {
        return additionalInstructionCode;
    }

    public void setAdditionalInstructionCode(final String additionalInstructionCode) {
        this.additionalInstructionCode = additionalInstructionCode;
    }

    @Override
    public String toString() {
        StringBuilder referralRec = new StringBuilder();
        referralRec.append("ReferralRecImpl [referralNum=");
        referralRec.append(referralNum);
        referralRec.append(", referralStatus=");
        referralRec.append(referralStatus);
        referralRec.append(", highestHitPriority=");
        referralRec.append(highestHitPriority);
        referralRec.append(", referralSeverity=");
        referralRec.append(referralSeverity);
        referralRec.append(", totalHits=");
        referralRec.append(totalHits);
        referralRec.append(", createdDatetime=");
        referralRec.append(createdDatetime);
        referralRec.append(", recommendedAction=");
        referralRec.append(recommendedAction);
        referralRec.append(", referralHits=");
        referralRec.append(referralHits);
        referralRec.append(", traveller=");
        referralRec.append(traveller);
        referralRec.append(", flight=");
        referralRec.append(flight);
        referralRec.append(", latestReferralLog=");
        referralRec.append(latestReferralLog);
        referralRec.append(", updateVersionNp=");
        referralRec.append(updateVersioNo);
        referralRec.append("]");
        return referralRec.toString();
    }




}
