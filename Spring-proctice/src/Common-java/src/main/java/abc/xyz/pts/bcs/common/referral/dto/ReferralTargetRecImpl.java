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

/**
 *
 * @author mmotlib-siddiqui
 *
 */
public final class ReferralTargetRecImpl implements Serializable,
        ReferralTargetRec {
    private static final long serialVersionUID = 6009639000772655891L;

    private Long travellerId;
    private Long referralId;
    private Long watchListRequestId;
    private String fullname;
    private String gender;
    private Calendar birthDate;
    private String countryOfBirth;
    private String placeOfBirth;
    private String nationality;

    private String docType;
    private String docNumber;

    private String ownersRef;
    private String reason;

    @Override
    public Long getTravellerId() {
        return travellerId;
    }

    public void setTravellerId(final Long travellerId) {
        this.travellerId = travellerId;
    }

    @Override
    public Long getReferralId() {
        return referralId;
    }

    public void setReferralId(final Long referralId) {
        this.referralId = referralId;
    }

    @Override
    public Long getWatchListRequestId() {
        return watchListRequestId;
    }

    public void setWatchListRequestId(final Long watchListRequestId) {
        this.watchListRequestId = watchListRequestId;
    }

    @Override
    public String getFullname() {
        return fullname;
    }

    public void setFullname(final String fullname) {
        this.fullname = fullname;
    }

    @Override
    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    @Override
    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final Calendar birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(final String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    @Override
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(final String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    @Override
    public String getNationality() {
        return nationality;
    }

    public void setNationality(final String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String getDocType() {
        return docType;
    }

    public void setDocType(final String docType) {
        this.docType = docType;
    }

    @Override
    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(final String docNumber) {
        this.docNumber = docNumber;
    }

    @Override
    public String getOwnersRef() {
        return ownersRef;
    }

    public void setOwnersRef(final String ownersRef) {
        this.ownersRef = ownersRef;
    }

    @Override
    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

}
