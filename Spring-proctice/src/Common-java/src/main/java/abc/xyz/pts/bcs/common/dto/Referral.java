/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import abc.xyz.pts.bcs.common.enums.QualificationStatusType;
import abc.xyz.pts.bcs.common.enums.ReferralStatusType;
import abc.xyz.pts.bcs.common.exception.ApplicationException;
import abc.xyz.pts.bcs.common.referral.ReferralStateFactory;
import abc.xyz.pts.bcs.common.referral.state.ReferralState;

/**
 *
 * Value Object for the REFERRALS table
 *
 * since 14.4.2011
 *
 * @author Kasi.Subramaniam
 *
 */
public class Referral {

    private Long id;
    private Long traId;
    private String actionCode;
    private String status;
    private String priActioningAirportCode;
    private String secActioningAirportCode;
    private String closureCode;
    private String modifiedBy;
    private Calendar modifiedDatetime;
    private String createdBy;
    private Calendar createdDatetime;
    private Long priority;
    private Long severityLevel;
    private Long updateVersionNo;
    private ReferralState referralState;
    private final List<ReferralHit> referralHits;
    private String additionalInstructionCode;
    private String additionalInstructionDesc;

    // This is not from db
    // due to tightly coupling of decision making in this object, i.e. isAllHitRuledOut
    private Long totalHits;      // number of hits for this referral - we need to pass this value for UI


    public Referral() {
        this.referralHits = new ArrayList<ReferralHit>();
    }

    public List<ReferralHit> getReferralHits() {
        return referralHits;
    }

    public void addReferralHits(final List<ReferralHit> referralHits) {

        try {
            this.referralHits.addAll(referralHits);
            referralState.addHits();
        } catch (final ApplicationException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isOpen(){
        return getStatus().equals(ReferralStatusType.OPEN.name());
    }

    public boolean isNew() {
        return getStatus().equals(ReferralStatusType.NEW.name());
    }

    public boolean isUnqualified() {
        return getStatus().equals(ReferralStatusType.UNQ.name());
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getTraId() {
        return traId;
    }

    public void setTraId(final Long traId) {
        this.traId = traId;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(final String actionCode) {
        this.actionCode = actionCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
        this.referralState = ReferralStateFactory.getInstance(this);
    }

    public String getPriActioningAirportCode() {
        return priActioningAirportCode;
    }

    public void setPriActioningAirportCode(final String priActioningAirportCode) {
        this.priActioningAirportCode = priActioningAirportCode;
    }

    public String getSecActioningAirportCode() {
        return secActioningAirportCode;
    }

    public void setSecActioningAirportCode(final String secActioningAirportCode) {
        this.secActioningAirportCode = secActioningAirportCode;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(final String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Calendar getModifiedDatetime() {
        return modifiedDatetime;
    }

    public void setModifiedDatetime(final Calendar modifiedDatetime) {
        this.modifiedDatetime = modifiedDatetime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public Calendar getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(final Calendar createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public void setPriority(final Long priority) {
        this.priority = priority;
    }

    public Long getPriority() {
        return priority;
    }

    public Long getUpdateVersionNo() {
        return updateVersionNo;
    }

    public void setUpdateVersionNo(final Long updateVersionNo) {
        this.updateVersionNo = updateVersionNo;
    }

    public String getClosureCode() {
        return closureCode;
    }

    public void setClosureCode(final String closureCode) {
        this.closureCode = closureCode;
    }

    public boolean isAtLeastOneHitRuledIn() {
        for (final ReferralHit referralHit : referralHits) {
            if (QualificationStatusType.IN.name().equals(referralHit.getQualificationStatus())) {
                return true;
            }
        }
        return false;
    }

    private int totalReferralHits()
    {
        if (this.referralHits == null) {
            return 0;
        }

        return this.referralHits.size();
    }

    private boolean isTotalHitsEqual()
    {
        if (this.totalHits == null)
         {
            return true; // does not matter as we have nothing to compare to;
        }

        return this.totalHits.intValue() == totalReferralHits();
    }

    public boolean isAllHitRuledOut() {
        // only if we have access to all the hits in the referral
        return (!isAtLeastOneHitRuledIn() && !isHitRuledUnknown() && isTotalHitsEqual());
    }

    private boolean isHitRuledUnknown() {

        for (final ReferralHit referralHit : referralHits) {
            if (referralHit.isUnknown()) {
                return true;
            }
        }

        return false;
    }

    public ReferralState getReferralState() {
        return referralState;
    }

    public void save() throws ApplicationException {
        referralState.handleStateOnSave();
    }

    public void close() throws ApplicationException {
        referralState.handleStateOnClose();
    }

    public void acknowledge() throws ApplicationException {
        referralState.handleStateOnAcknoweledge();
    }

    public Long getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(final Long severityLevel) {
        this.severityLevel = severityLevel;
    }

    public Long getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(final Long totalHits) {
        this.totalHits = totalHits;
    }

    public String getAdditionalInstructionCode() {
        return additionalInstructionCode;
    }

    public void setAdditionalInstructionCode(final String additionalInstructionCode) {
        this.additionalInstructionCode = additionalInstructionCode;
    }

    public String getAdditionalInstructionDesc() {
        return additionalInstructionDesc;
    }

    public void setAdditionalInstructionDesc(final String additionalInstructionDesc) {
        this.additionalInstructionDesc = additionalInstructionDesc;
    }

    @Override
    public String toString() {
        return "Referral [actionCode=" + actionCode + ", closureCode="
                + closureCode + ", createdBy=" + createdBy
                + ", createdDatetime=" + createdDatetime + ", id=" + id
                + ", priority=" + priority + ", status=" + status
                + ", traId=" + traId
                + ", severityLevel=" + severityLevel
                + ", updateVersionNo=" + updateVersionNo
                + "]";
    }



}
