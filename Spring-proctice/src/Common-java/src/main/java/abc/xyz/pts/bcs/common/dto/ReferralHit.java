/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dto;

import java.util.Calendar;

import abc.xyz.pts.bcs.common.enums.QualificationStatusType;



/**
 *
 * Value object for the REFERRAL_HITS table
 * @since 13.4.2011
 * @author Kasi.Subramaniam
 *
 */
public class ReferralHit
{
    private Long id;
    private Long refId;
    private Long watlrId;
    private String qualificationStatus;
    private String hitType;
    private Long hitScore;
    private Long priority;
    private Long severityLevel;
    private String actionCode;
    private Long updateVersionNo;
    private String wlSource;
    private String wlMatchType;
    private String wlForename;
    private String wlLastName;
    private String wlGender;
    private Calendar wlBirthDate;
    private String wlBirthPlace;
    private String wlBirthCntryCode;
    private String wlNationality;
    private String wlDocType;
    private String wlDocNo;
    private String wlDocIssueCntryCode;
    private String wlProtocolNumber;
    private String wlWiWatlName;
    private String wlRescCode;
    private Long appHitReason;
    private String modifiedBy;
    private Calendar modifiedDatetime;
    private String createdBy;
    private Calendar createdDatetime;
    private Long wlTarwlId;


    protected Long clearedDocumentsId;

    public Long getClearedDocumentsId() {
        return clearedDocumentsId;
    }
    public void setClearedDocumentsId(final Long clearedDocumentsId) {
        this.clearedDocumentsId = clearedDocumentsId;
    }
    public Long getId() {
        return id;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public Long getRefId() {
        return refId;
    }
    public void setRefId(final Long refId) {
        this.refId = refId;
    }
    public Long getWatlrId() {
        return watlrId;
    }
    public void setWatlrId(final Long watlrId) {
        this.watlrId = watlrId;
    }
    public String getQualificationStatus() {
        return qualificationStatus;
    }
    public void setQualificationStatus(final String qualificationStatus) {
        this.qualificationStatus = qualificationStatus;
    }
    public String getHitType() {
        return hitType;
    }
    public void setHitType(final String hitType) {
        this.hitType = hitType;
    }
    public Long getHitScore() {
        return hitScore;
    }
    public void setHitScore(final Long hitScore) {
        this.hitScore = hitScore;
    }
    public Long getPriority() {
        return priority;
    }
    public void setPriority(final Long priority) {
        this.priority = priority;
    }
    public Long getSeverityLevel() {
        return severityLevel;
    }
    public void setSeverityLevel(final Long severityLevel) {
        this.severityLevel = severityLevel;
    }
    public String getActionCode() {
        return actionCode;
    }
    public void setActionCode(final String actionCode) {
        this.actionCode = actionCode;
    }
    public Long getUpdateVersionNo() {
        return updateVersionNo;
    }
    public void setUpdateVersionNo(final Long updateVersionNo) {
        this.updateVersionNo = updateVersionNo;
    }
    public String getWlSource() {
        return wlSource;
    }
    public void setWlSource(final String wlSource) {
        this.wlSource = wlSource;
    }
    public String getWlMatchType() {
        return wlMatchType;
    }
    public void setWlMatchType(final String wlMatchType) {
        this.wlMatchType = wlMatchType;
    }
    public String getWlForename() {
        return wlForename;
    }
    public void setWlForename(final String wlForename) {
        this.wlForename = wlForename;
    }
    public String getWlLastName() {
        return wlLastName;
    }
    public void setWlLastName(final String wlLastName) {
        this.wlLastName = wlLastName;
    }
    public String getWlGender() {
        return wlGender;
    }
    public void setWlGender(final String wlGender) {
        this.wlGender = wlGender;
    }
    public String getWlBirthPlace() {
        return wlBirthPlace;
    }
    public void setWlBirthPlace(final String wlBirthPlace) {
        this.wlBirthPlace = wlBirthPlace;
    }
    public String getWlBirthCntryCode() {
        return wlBirthCntryCode;
    }
    public void setWlBirthCntryCode(final String wlBirthCntryCode) {
        this.wlBirthCntryCode = wlBirthCntryCode;
    }
    public String getWlNationality() {
        return wlNationality;
    }
    public void setWlNationality(final String wlNationality) {
        this.wlNationality = wlNationality;
    }
    public String getWlDocType() {
        return wlDocType;
    }
    public void setWlDocType(final String wlDocType) {
        this.wlDocType = wlDocType;
    }
    public String getWlDocNo() {
        return wlDocNo;
    }
    public void setWlDocNo(final String wlDocNo) {
        this.wlDocNo = wlDocNo;
    }
    public String getWlDocIssueCntryCode() {
        return wlDocIssueCntryCode;
    }
    public void setWlDocIssueCntryCode(final String wlDocIssueCntryCode) {
        this.wlDocIssueCntryCode = wlDocIssueCntryCode;
    }
    public String getWlProtocolNumber() {
        return wlProtocolNumber;
    }
    public void setWlProtocolNumber(final String wlProtocolNumber) {
        this.wlProtocolNumber = wlProtocolNumber;
    }
    public String getWlWiWatlName() {
        return wlWiWatlName;
    }
    public void setWlWiWatlName(final String wlWiWatlName) {
        this.wlWiWatlName = wlWiWatlName;
    }
    public String getWlRescCode() {
        return wlRescCode;
    }
    public void setWlRescCode(final String wlRescCode) {
        this.wlRescCode = wlRescCode;
    }
    public Long getAppHitReason() {
        return appHitReason;
    }
    public void setAppHitReason(final Long appHitReason) {
        this.appHitReason = appHitReason;
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
    public Calendar getWlBirthDate() {
        return wlBirthDate;
    }
    public void setWlBirthDate(final Calendar wlBirthDate) {
        this.wlBirthDate = wlBirthDate;
    }
    public Long getWlTarwlId() {
        return wlTarwlId;
    }
    public void setWlTarwlId(final Long wlTarwlId) {
        this.wlTarwlId = wlTarwlId;
    }

    public boolean isRuledOut() {
        return QualificationStatusType.OUT.name().equals(getQualificationStatus());
    }

    public boolean isRuledIn() {
        return QualificationStatusType.IN.name().equals(getQualificationStatus());
    }

    public boolean isUnknown() {
        return QualificationStatusType.UNKNOWN.name().equals(getQualificationStatus());
    }


    @Override
    public String toString() {
        return "ReferralHit [actionCode=" + actionCode + ", appHitReason="
                + appHitReason + ", createdBy=" + createdBy
                + ", createdDatetime=" + createdDatetime + ", hitScore="
                + hitScore + ", hitType=" + hitType + ", id=" + id
                + ", modifiedBy=" + modifiedBy + ", modifiedDatetime="
                + modifiedDatetime + ", priority=" + priority
                + ", qualificationStatus=" + qualificationStatus + ", refId="
                + refId + ", severityLevel=" + severityLevel
                + ", updateVersionNo=" + updateVersionNo + ", watlrId="
                + watlrId + ", wlBirthCntryCode=" + wlBirthCntryCode
                + ", wlBirthDate=" + wlBirthDate + ", wlBirthPlace="
                + wlBirthPlace + ", wlDocIssueCntryCode=" + wlDocIssueCntryCode
                + ", wlDocNo=" + wlDocNo + ", wlDocType=" + wlDocType
                + ", wlForename=" + wlForename + ", wlGender=" + wlGender
                + ", wlLastName=" + wlLastName + ", wlMatchType=" + wlMatchType
                + ", wlNationality=" + wlNationality + ", wlProtocolNumber="
                + wlProtocolNumber + ", wlRescCode=" + wlRescCode
                + ", wlSource=" + wlSource + ", wlWiWatlName=" + wlWiWatlName
                + ", wlTarwlId=" + wlTarwlId
                + "]";
    }



}
