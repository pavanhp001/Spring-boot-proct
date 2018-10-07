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

/**
 *
 * @author mmotlib-siddiqui
 *
 */
public final class ReferralTravellerRecImpl implements Serializable,
        ReferralTravellerRec {
    private static final long serialVersionUID = 6009639000772655890L;

    private Long id;
    private Long travellerId;
    private String forename;
    private String lastName;
    private String fullname;
    private String gender;
    private Calendar birthDate;
    private String birthPlace;
    private String nationality;
    private String travellerType;
    private String movementType;
    private String movementStatus;
    private String docType;
    private String docNo;

    private String typeOfTravel; // Flight manifest table
    private String countryOfBirth;

    @Override
    public String getFullname() {
        if (lastName == null || forename == null) {
            return fullname;
        } else {
            return lastName + "," + forename;
        }
    }

    public void setFullname(final String fullname) {
        this.fullname = fullname;
    }

    @Override
    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final Calendar birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String getNationality() {
        return nationality;
    }

    public void setNationality(final String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String getTravellerType() {
        return travellerType;
    }

    public void setTravellerType(final String travellerType) {
        this.travellerType = travellerType;
    }

    @Override
    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(final String movementType) {
        this.movementType = movementType;
    }

    public String getMovementStatus() {
        return movementStatus;
    }

    public void setMovementStatus(final String movementStatus) {
        this.movementStatus = movementStatus;
    }

    @Override
    public String getTypeOfTravel() {
        return typeOfTravel;
    }

    public void setTypeOfTravel(final String typeOfTravel) {
        this.typeOfTravel = typeOfTravel;
    }

    @Override
    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Long getTravellerId() {
        return travellerId;
    }

    public void setTravellerId(final Long traId) {
        this.travellerId = traId;
    }

    @Override
    public String getForename() {
        return forename;
    }

    public void setForename(final String forename) {
        this.forename = forename;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(final String birthPlace) {
        this.birthPlace = birthPlace;
    }

    @Override
    public String getDocType() {
        return docType;
    }

    public void setDocType(final String docType) {
        this.docType = docType;
    }

    @Override
    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(final String docNo) {
        this.docNo = docNo;
    }

    @Override
    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(final String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    /**
     * Get pre-concatenated string for doc type/doc number.
     */
    @Override
    public String getDocData() {

        final StringBuilder builder = new StringBuilder();

        if (!StringUtils.isBlank(docType)) {
            builder.append(docType);
            builder.append("/");
        }
        builder.append(docNo);

        return builder.toString();
    }

    @Override
    public String toString() {
        final StringBuilder refTra = new StringBuilder();
        refTra.append("ReferralTravellerRecImpl [birthDate=");
        refTra.append(birthDate);
        refTra.append(", birthPlace=");
        refTra.append(birthPlace);
        refTra.append(", countryOfBirth=");
        refTra.append(countryOfBirth);
        refTra.append(", docNo=");
        refTra.append(docNo);
        refTra.append(", docType=");
        refTra.append(docType);
        refTra.append(", forename=");
        refTra.append(forename);
        refTra.append(", fullname=");
        refTra.append(fullname);
        refTra.append(", gender=");
        refTra.append(gender);
        refTra.append(", id=");
        refTra.append(id);
        refTra.append(", lastName=");
        refTra.append(lastName);
        refTra.append(", movementType=");
        refTra.append(movementType);
        refTra.append(", nationality=");
        refTra.append(nationality);
        refTra.append(", travellerId=");
        refTra.append(travellerId);
        refTra.append(", travellerType=");
        refTra.append(travellerType);
        refTra.append(", typeOfTravel=");
        refTra.append(typeOfTravel);
        refTra.append("]");
        return refTra.toString();
    }

}
