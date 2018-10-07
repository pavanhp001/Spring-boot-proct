/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral.dto;

import java.lang.reflect.Field;
import java.util.Calendar;

import org.apache.log4j.Logger;

/**
 * Used mainly by the ReferralSearchDao to get data in a flat structure.
 *
 *
 * @author MMotlib-Siddiqui
 *
 */

public class ReferralSearchRec
{
    private static final Logger LOG = Logger.getLogger(ReferralSearchRec.class);

    private Long refId; // referral reference
    private Integer referralLevel;
    private String referralStatus;
    private Calendar createdDatetime;
    private String referralRecActionCode;
    private Integer priorityReferral;
    private Long referralSeverity;
    private Integer priorityHit;
    private Integer totalReferralHits;
    private String movementType;
    private String movementStatus;
    private String typeOfTravel;
    private String travellerType;
    private Long travellerId;
    private String forename;
    private String lastName;
    private Calendar birthDate;
    private String priDocNo;
    private String priDocType;
    private String nationality;

    private Long flightSegId;
    private String operCarrierCode;
    private String operFlightNo;
    private String depAirportCode;
    private Calendar arrDatetime;
    private String arrAirportCode;
    private Calendar depDatetime;

    private Long hitId;
    private String hitType;
    private String wlWiWatlName;
    private Double hitScore;
    private Integer severityLevel;
    private String actionCode;
    private String qualificationStatus;
    private String wlRescCode;
    private Long wlTarwlId;

    private Long updateVersionNo;
    private Long referralStatusOrder;
    private String additionalInstructionCode;

    public Long getRefId()
    {
        return refId;
    }

    public void setRefId(final Long refId)
    {
        this.refId = refId;
    }

    public Integer getReferralLevel()
    {
        return referralLevel;
    }

    public void setReferralLevel(final Integer referralLevel)
    {
        this.referralLevel = referralLevel;
    }

    public String getReferralStatus()
    {
        return referralStatus;
    }

    public void setReferralStatus(final String referralStatus)
    {
        this.referralStatus = referralStatus;
    }

    public Calendar getCreatedDatetime()
    {
        return createdDatetime;
    }

    public void setCreatedDatetime(final Calendar createdDatetime)
    {
        this.createdDatetime = createdDatetime;
    }

    public String getReferralRecActionCode()
    {
        return referralRecActionCode;
    }

    public void setReferralRecActionCode(final String referralRecActionCode)
    {
        this.referralRecActionCode = referralRecActionCode;
    }


    public String getMovementType()
    {
        return movementType;
    }

    public void setMovementType(final String movementType)
    {
        this.movementType = movementType;
    }

    public String getMovementStatus() {
        return movementStatus;
    }

    public void setMovementStatus(final String movementStatus) {
        this.movementStatus = movementStatus;
    }

    public String getTypeOfTravel()
    {
        return typeOfTravel;
    }

    public void setTypeOfTravel(final String typeOfTravel)
    {
        this.typeOfTravel = typeOfTravel;
    }

    public String getTravellerType()
    {
        return travellerType;
    }

    public void setTravellerType(final String travellerType)
    {
        this.travellerType = travellerType;
    }

    public Long getTravellerId()
    {
        return travellerId;
    }

    public void setTravellerId(final Long travellerId)
    {
        this.travellerId = travellerId;
    }

    public Calendar getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(final Calendar birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getPriDocNo()
    {
        return priDocNo;
    }

    public void setPriDocNo(final String priDocNo)
    {
        this.priDocNo = priDocNo;
    }

    public String getPriDocType()
    {
        return priDocType;
    }

    public void setPriDocType(final String priDocType)
    {
        this.priDocType = priDocType;
    }

    public String getNationality()
    {
        return nationality;
    }

    public void setNationality(final String nationality)
    {
        this.nationality = nationality;
    }

    public String getOperCarrierCode()
    {
        return operCarrierCode;
    }

    public void setOperCarrierCode(final String operCarrierCode)
    {
        this.operCarrierCode = operCarrierCode;
    }

    public String getOperFlightNo()
    {
        return operFlightNo;
    }

    public void setOperFlightNo(final String operFlightNo)
    {
        this.operFlightNo = operFlightNo;
    }

    public String getDepAirportCode()
    {
        return depAirportCode;
    }

    public void setDepAirportCode(final String depAirportCode)
    {
        this.depAirportCode = depAirportCode;
    }

    public Calendar getArrDatetime()
    {
        return arrDatetime;
    }

    public void setArrDatetime(final Calendar arrDatetime)
    {
        this.arrDatetime = arrDatetime;
    }

    public String getArrAirportCode()
    {
        return arrAirportCode;
    }

    public void setArrAirportCode(final String arrAirportCode)
    {
        this.arrAirportCode = arrAirportCode;
    }

    public Calendar getDepDatetime()
    {
        return depDatetime;
    }

    public void setDepDatetime(final Calendar depDatetime)
    {
        this.depDatetime = depDatetime;
    }

    public Long getHitId()
    {
        return hitId;
    }

    public void setHitId(final Long hitId)
    {
        this.hitId = hitId;
    }

    public String getHitType()
    {
        return hitType;
    }

    public void setHitType(final String hitType)
    {
        this.hitType = hitType;
    }

    public String getWlWiWatlName()
    {
        return wlWiWatlName;
    }

    public void setWlWiWatlName(final String wlWiWatlName)
    {
        this.wlWiWatlName = wlWiWatlName;
    }

    public Double getHitScore()
    {
        return hitScore;
    }

    public void setHitScore(final Double hitScore)
    {
        this.hitScore = hitScore;
    }

    public Integer getSeverityLevel()
    {
        return severityLevel;
    }

    public void setSeverityLevel(final Integer severityLevel)
    {
        this.severityLevel = severityLevel;
    }

    public String getActionCode()
    {
        return actionCode;
    }

    public void setActionCode(final String actionCode)
    {
        this.actionCode = actionCode;
    }

    public Long getFlightSegId()
    {
        return flightSegId;
    }

    public void setFlightSegId(final Long flightSegId)
    {
        this.flightSegId = flightSegId;
    }

    public String getForename()
    {
        return forename;
    }

    public void setForename(final String forename)
    {
        this.forename = forename;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(final String lastName)
    {
        this.lastName = lastName;
    }

    public Integer getTotalReferralHits()
    {
        return totalReferralHits;
    }

    public void setTotalReferralHits(final Integer totalReferralHits)
    {
        this.totalReferralHits = totalReferralHits;
    }

    public Integer getPriorityReferral()
    {
        return priorityReferral;
    }

    public void setPriorityReferral(final Integer priorityReferral)
    {
        this.priorityReferral = priorityReferral;
    }

    public Integer getPriorityHit()
    {
        return priorityHit;
    }

    public void setPriorityHit(final Integer priorityHit)
    {
        this.priorityHit = priorityHit;
    }

    public String getQualificationStatus() {
        return qualificationStatus;
    }

    public void setQualificationStatus(final String qualificationStatus) {
        this.qualificationStatus = qualificationStatus;
    }

    public Long getUpdateVersionNo()
    {
        return updateVersionNo;
    }

    public void setUpdateVersionNo(final Long updateVersionNo)
    {
        this.updateVersionNo = updateVersionNo;
    }

    public Long getReferralSeverity()
    {
        return referralSeverity;
    }

    public void setReferralSeverity(final Long referralSeverity)
    {
        this.referralSeverity = referralSeverity;
    }

    public String getWlRescCode()
    {
        return wlRescCode;
    }

    public void setWlRescCode(final String wlRescCode)
    {
        this.wlRescCode = wlRescCode;
    }

    public Long getWlTarwlId()
    {
        return wlTarwlId;
    }

    public void setWlTarwlId(final Long wlTarwlId)
    {
        this.wlTarwlId = wlTarwlId;
    }

    public Long getReferralStatusOrder()
    {
        return referralStatusOrder;
    }

    public void setReferralStatusOrder(final Long referralStatusOrder)
    {
        this.referralStatusOrder = referralStatusOrder;
    }

    public String getAdditionalInstructionCode() {
        return additionalInstructionCode;
    }

    public void setAdditionalInstructionCode(final String additionalInstructionCode) {
        this.additionalInstructionCode = additionalInstructionCode;
    }

    @Override
    public String toString() {

        final StringBuilder buf = new StringBuilder();

        try {
            final Class cls = this.getClass();
            buf.append("\n");
            buf.append(this.getClass().getName() + " [\n");

            final Field[] fieldList = cls.getDeclaredFields();
            for (final Field fld : fieldList) {
                buf.append("\t");
                buf.append(fld.getName());
                buf.append(": ");
                buf.append(fld.get(this) + ",\n");
            }
            buf.append("]\n");

        } catch (final Exception e) {
           LOG.warn("Could not format this instance!", e);
        }

        return buf.toString();
    }

}
