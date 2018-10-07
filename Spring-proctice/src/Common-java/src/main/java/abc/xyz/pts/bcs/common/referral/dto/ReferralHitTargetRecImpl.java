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
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

import abc.xyz.pts.bcs.common.enums.QualificationStatusType;

/*
 *
 * @author mmotlib-siddiqui
 *
 */
public final class ReferralHitTargetRecImpl implements Serializable, ReferralHitTargetRec {
    private static final long serialVersionUID = 6009639000772655892L;

    private Long id;
    private Long refId;
    private String hitType;
    private Long hitScore;
    private Long watlrId;
    private String wlForename;
    private String wlLastName;
    private String wlGender;
    private Calendar wlBirthDate;
    private String countryOfBirth;
    private String wlBirthPlace;
    private String wlNationality;
    private String wlDocType;
    private String wlDocNo;
    private String wlProtocolNumber;
    private String wlRescCode;
    private Integer updateVersionNo;
    private String rescCode;
    private String rescCodeDesc;
    private String appHitReason;
    private String appHitReasonDescription;
    private Long wlTarwlId;

    private QualificationStatusType qualificationStatus;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Long getRefId() {
        return refId;
    }

    public void setRefId(final Long refId) {
        this.refId = refId;
    }

    @Override
    public String getHitType() {
        return hitType;
    }

    public void setHitType(final String hitType) {
        this.hitType = hitType;
    }

    @Override
    public Long getHitScore() {
        return hitScore;
    }

    public void setHitScore(final Long hitScore) {
        this.hitScore = hitScore;
    }

    @Override
    public Long getWatlrId() {
        return watlrId;
    }

    public void setWatlrId(final Long watlrId) {
        this.watlrId = watlrId;
    }

    @Override
    public String getWlForename() {
        return wlForename;
    }

    public void setWlForename(final String wlForename) {
        this.wlForename = wlForename;
    }

    @Override
    public String getWlLastName() {
        return wlLastName;
    }

    public void setWlLastName(final String wlLastName) {
        this.wlLastName = wlLastName;
    }

    @Override
    public String getWlGender() {
        return wlGender;
    }

    public void setWlGender(final String wlGender) {
        this.wlGender = wlGender;
    }

    @Override
    public Calendar getWlBirthDate() {
        return wlBirthDate;
    }

    public void setWlBirthDate(final Calendar wlBirthDate) {
        this.wlBirthDate = wlBirthDate;
    }

    @Override
    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(final String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    @Override
    public String getWlBirthPlace() {
        return wlBirthPlace;
    }

    public void setWlBirthPlace(final String wlBirthPlace) {
        this.wlBirthPlace = wlBirthPlace;
    }

    @Override
    public String getWlNationality() {
        return wlNationality;
    }

    public void setWlNationality(final String wlNationality) {
        this.wlNationality = wlNationality;
    }

    @Override
    public String getWlDocType() {
        return wlDocType;
    }

    public void setWlDocType(final String wlDocType) {
        this.wlDocType = wlDocType;
    }

    @Override
    public String getWlDocNo() {
        return wlDocNo;
    }

    public void setWlDocNo(final String wlDocNumber) {
        this.wlDocNo = wlDocNumber;
    }

    @Override
    public String getWlProtocolNumber() {
        return wlProtocolNumber;
    }

    public void setWlProtocolNumber(final String wlProtocolNumber) {
        this.wlProtocolNumber = wlProtocolNumber;
    }

    @Override
    public String getWlRescCode() {
        return wlRescCode;
    }

    public void setWlRescCode(final String wlRescCode) {
        this.wlRescCode = wlRescCode;
    }

    @Override
    public String getWlFullname() {

        StringBuilder buf = new StringBuilder();

        if (wlLastName != null) {
            buf.append(wlLastName);
        }

        if (buf.length() > 0 && wlForename != null) {
            buf.append("," + wlForename);
        }

        return buf.toString();
    }

    @Override
    public Integer getUpdateVersionNo() {
        return updateVersionNo;
    }

    public void setUpdateVersionNo(final Integer updateVersionNo) {
        this.updateVersionNo = updateVersionNo;
    }

    @Override
    public String getRescCode() {
        return rescCode;
    }

    public void setRescCode(final String rescCode) {
        this.rescCode = rescCode;
    }

    @Override
    public String getRescCodeDesc() {
        return rescCodeDesc;
    }

    public void setRescCodeDesc(final String rescCodeDesc) {
        this.rescCodeDesc = rescCodeDesc;
    }

    @Override
    public String getAppHitReason() {
        return appHitReason;
    }

    public void setAppHitReason(final String appHitReason) {
        this.appHitReason = appHitReason;
    }

    @Override
    public String getAppHitReasonDescription() {
        return appHitReasonDescription;
    }

    public void setAppHitReasonDescription(final String appHitReasonDescription) {
        this.appHitReasonDescription = appHitReasonDescription;
    }

    @Override
    public String getDocData() {
        StringBuilder builder = new StringBuilder();

        if (StringUtils.isBlank(this.wlDocType) == false) {
            builder.append(this.wlDocType);
            builder.append("/");
        }

        if (this.wlDocNo != null) {
            builder.append(this.wlDocNo);
        }

        return builder.toString();
    }

    @Override
    public QualificationStatusType getQualificationStatus() {
        return qualificationStatus;
    }

    public void setQualificationStatus(final QualificationStatusType qualificationStatus) {
        this.qualificationStatus = qualificationStatus;
    }

    @Override
    public Long getWlTarwlId() {
        return wlTarwlId;
    }

    public void setWlTarwlId(final Long wlTarwlId) {
        this.wlTarwlId = wlTarwlId;
    }


    @Override
    public String toString() {
        StringBuilder rec = new StringBuilder();
        rec.append("ReferralHitTargetRecImpl [id=");
        rec.append(id);
        rec.append(", refId=");
        rec.append(refId);
        rec.append(", hitType=");
        rec.append(hitType);
        rec.append(", hitScore=");
        rec.append(hitScore);
        rec.append(", watlrId=");
        rec.append(watlrId);
        rec.append(", wlForename=");
        rec.append(wlForename);
        rec.append(", wlLastName=");
        rec.append(wlLastName);
        rec.append(", wlGender=");
        rec.append(wlGender);
        rec.append(", wlBirthDate=");
        rec.append(wlBirthDate);
        rec.append(", countryOfBirth=");
        rec.append(countryOfBirth);
        rec.append(", wlBirthPlace=");
        rec.append(wlBirthPlace);
        rec.append(", wlNationality=");
        rec.append(wlNationality);
        rec.append(", wlDocType=");
        rec.append(wlDocType);
        rec.append(", wlDocNo=");
        rec.append(wlDocNo);
        rec.append(", wlProtocolNumber=");
        rec.append(wlProtocolNumber);
        rec.append(", wlRescCode=");
        rec.append(wlRescCode);
        rec.append(", updateVersionNo=");
        rec.append(updateVersionNo);
        rec.append(", rescCode=");
        rec.append(rescCode);
        rec.append(", rescCodeDesc=");
        rec.append(rescCodeDesc);
        rec.append(", appHitReason=");
        rec.append(appHitReason);
        rec.append(", appHitReasonDescription=");
        rec.append(appHitReasonDescription);
        rec.append(", qualificationStatus=");
        rec.append(qualificationStatus);
        rec.append(", wlTarwlId=");
        rec.append(wlTarwlId);
        rec.append("]");
        return rec.toString();
    }


}
