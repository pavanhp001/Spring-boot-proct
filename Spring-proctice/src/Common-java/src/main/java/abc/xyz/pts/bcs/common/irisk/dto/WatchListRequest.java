/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.irisk.dto;

import java.util.Calendar;

/**
 * @author ryattapu
 *
 */
public class WatchListRequest {
    private Long id;
    private Long traId;
    private String forename;
    private String lastName;
    private String gender;
    private Calendar birthDate;
    private String birthPlace;
    private String birthCntryCode;
    private String nationality;
    private String docType;
    private String docNo;
    private String docIssueCntryCode;
    private String travellerDataSource;
    private Calendar requestDatetime;;
    private Calendar responseDatetime;;
    private String errorMessage;
    private String responseStatus;
    private String watchListSource;
    private Long updateVersionNo;

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
    public String getForename() {
        return forename;
    }
    public void setForename(final String forename) {
        this.forename = forename;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(final String gender) {
        this.gender = gender;
    }
    public Calendar getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(final Calendar birthDate) {
        this.birthDate = birthDate;
    }
    public String getBirthPlace() {
        return birthPlace;
    }
    public void setBirthPlace(final String birthPlace) {
        this.birthPlace = birthPlace;
    }
    public String getBirthCntryCode() {
        return birthCntryCode;
    }
    public void setBirthCntryCode(final String birthCntryCode) {
        this.birthCntryCode = birthCntryCode;
    }
    public String getNationality() {
        return nationality;
    }
    public void setNationality(final String nationality) {
        this.nationality = nationality;
    }
    public String getDocType() {
        return docType;
    }
    public void setDocType(final String docType) {
        this.docType = docType;
    }
    public String getDocNo() {
        return docNo;
    }
    public void setDocNo(final String docNo) {
        this.docNo = docNo;
    }
    public String getDocIssueCntryCode() {
        return docIssueCntryCode;
    }
    public void setDocIssueCntryCode(final String docIssueCntryCode) {
        this.docIssueCntryCode = docIssueCntryCode;
    }
    public String getTravellerDataSource() {
        return travellerDataSource;
    }
    public void setTravellerDataSource(final String travellerDataSource) {
        this.travellerDataSource = travellerDataSource;
    }
    public Calendar getRequestDatetime() {
        return requestDatetime;
    }
    public void setRequestDatetime(final Calendar requestDatetime) {
        this.requestDatetime = requestDatetime;
    }
    public Calendar getResponseDatetime() {
        return responseDatetime;
    }
    public void setResponseDatetime(final Calendar responseDatetime) {
        this.responseDatetime = responseDatetime;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public String getResponseStatus() {
        return responseStatus;
    }
    public void setResponseStatus(final String responseStatus) {
        this.responseStatus = responseStatus;
    }
    public String getWatchListSource() {
        return watchListSource;
    }
    public void setWatchListSource(final String watchListSource) {
        this.watchListSource = watchListSource;
    }
    public Long getUpdateVersionNo() {
        return updateVersionNo;
    }
    public void setUpdateVersionNo(final Long updateVersionNo) {
        this.updateVersionNo = updateVersionNo;
    }


}
