/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2011
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral.web.command;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import abc.xyz.pts.bcs.common.audit.annotation.AuditPropertyGroup;
import abc.xyz.pts.bcs.common.audit.annotation.AuditPropertyGroups;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableBeanProperty;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableCommand;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.audit.business.Parameter;
import abc.xyz.pts.bcs.common.db.dto.FlightSearch;
import abc.xyz.pts.bcs.common.enums.MovementStatusType;
import abc.xyz.pts.bcs.common.enums.OverrideType;
import abc.xyz.pts.bcs.common.enums.TravelType;

/**
 * @author cwalker
 */
@AuditableCommand(Event.SEARCH_REFERRAL_LIST)
@AuditPropertyGroups(auditWhenNoGroupMatch = false, value = {
    @AuditPropertyGroup(name = Event.SEARCH_REFERRAL_LIST, groupProperty = "searchType", propertyValue = ReferralSearchCommand.SEARCH_REFERRAL_LIST),
    @AuditPropertyGroup(name = Event.ACTIVE_REFERRALS, groupProperty = "searchType", propertyValue = ReferralSearchCommand.ACTIVE_REFERRALS),
    @AuditPropertyGroup(name = Event.UNKNOWN_HIT_REFERRALS, groupProperty = "searchType", propertyValue = ReferralSearchCommand.UNKNOWN_HIT_REFERRALS) })
public final class ReferralSearchCommand implements FlightSearch, Serializable {

    private static final long serialVersionUID = 225836282496815962L;

    private static final Logger LOG = Logger.getLogger(ReferralSearchCommand.class);
    public static final String SEARCH_REFERRAL_LIST = "Search Referral List";
    public static final String ACTIVE_REFERRALS = "Active Referrals";
    public static final String UNKNOWN_HIT_REFERRALS = "Unknown Hit Referrals";

    // Traveller Search criteria
    @SuppressWarnings("unused")
    @AuditableBeanProperty(key = Parameter.CARRIER_FLIGHT_NUMBER)
    private String carrierFlightNumber;


    @AuditableBeanProperty(key = Parameter.FORENAME)
    private String forenames;
    @AuditableBeanProperty(key = Parameter.LASTNAME)
    private String lastName;
    @AuditableBeanProperty(key = Parameter.DOCUMENT_NUMBER)
    private String documentNumber;
    @AuditableBeanProperty(key = Parameter.DATE_OF_BIRTH)
    private Calendar dateOfBirth;                                                // ?
    @AuditableBeanProperty(key = Parameter.NATIONALITY)
    private String nationality;
    @AuditableBeanProperty(key = Parameter.GENDER)
    private String gender;
    @AuditableBeanProperty(key = Parameter.SEVERITY)
    private List<Long> severityLevel;

    private Long travellerId;

    // Flight Search criteria

    @AuditableBeanProperty(key = Parameter.FLIGHT_NUMBER)
    private String flightNumber;
    @AuditableBeanProperty(key = Parameter.CARRIER_CODE)
    private String flightCarrierCode;
    @AuditableBeanProperty(key = Parameter.FLIGHT_ROUTE)
    private String route;
    @AuditableBeanProperty(key = Parameter.FLIGHT_STATUS)
    private String flightStatus;
    @AuditableBeanProperty(key = Parameter.ARR_AIRPORT_CODE)
    private String arrivalAirport;
    @AuditableBeanProperty(key = Parameter.DEP_AIRPORT_CODE)
    private String departureAirport;
    @AuditableBeanProperty(key = Parameter.DEP_START_DATE)
    private Calendar departureDateRangeFrom;                                    //
    @AuditableBeanProperty(key = Parameter.DEP_END_DATE)
    private Calendar departureDateRangeTo;                                        //
    @AuditableBeanProperty(key = Parameter.DEP_START_TIME)
    private String departureTimeRangeFrom;
    @AuditableBeanProperty(key = Parameter.DEP_END_TIME)
    private String departureTimeRangeTo;
    @AuditableBeanProperty(key = Parameter.ARR_START_DATE)
    private Calendar arrivalDateRangeFrom;                                        //
    @AuditableBeanProperty(key = Parameter.ARR_END_DATE)
    private Calendar arrivalDateRangeTo;                                          //
    @AuditableBeanProperty(key = Parameter.ARR_START_TIME)
    private String arrivalTimeRangeFrom;
    @AuditableBeanProperty(key = Parameter.ARR_END_TIME)
    private String arrivalTimeRangeTo;

    // Referral Search criteria

    @AuditableBeanProperty(key = Parameter.REFERRAL_NUMBER)
    private String referralNumber;
    @AuditableBeanProperty(key = Parameter.REFERRAL_STATUS)
    private List<String> referralStatusList;
    @AuditableBeanProperty(key = Parameter.HIT_TYPE)
    private List<String> hitTypeList;
    @AuditableBeanProperty(key = Parameter.REFERRAL_GENERATION_START_DATE)
    private Calendar referralGenDateFrom;
    @AuditableBeanProperty(key = Parameter.REFERRAL_GENERATION_END_DATE)
    private Calendar referralGenDateTo;

    @AuditableBeanProperty(key = Parameter.HIT_STATUS)
    private String hitStatus;

    private String movementStatusCancelled;
    private String movementStatusDenied;
    private String movementStatusExpected;
    private String movementStatusNoMovement;

    private String travelTypeNormal;
    private String travelTypeTransfer;
    private String travelTypeTransit;
    private String travelTypeUnmatched;

    private String overrideTypeAgent;
    private String overrideTypeGovernment;
    private String overrideTypeNone;
    // UI elements - whether we're showing criteria DIVs

    private boolean showReferralCriteria;
    private boolean showFlightCriteria;
    private boolean showTravellerCriteria;

    // Params for incoming Ajax requests

    private Integer selectedHitId;

    // Param to store the processed Referral (Qualify button clicked)
    private Long qualifiedReferralId;

    private String closureReason;
    // Param to store the Closed Referral (Close button clicked)
    private Long closedReferralId;
    // Accessors

    private String searchType;

    private String defaultDateRangeFrom;
    private String defaultDateRangeTo;

    public ReferralSearchCommand()
    {
        // The Referral Search DAO requires all these to be set in order to get all data

        this.movementStatusCancelled = MovementStatusType.CANCELLED.name();
        this.movementStatusDenied = MovementStatusType.DENIED.name();
        this.movementStatusExpected = MovementStatusType.EXPECTED.name();
        this.movementStatusNoMovement = MovementStatusType.N.name();

        this.travelTypeNormal = TravelType.NORMAL.name();
        this.travelTypeTransfer = TravelType.TRANSFER.name();
        this.travelTypeTransit = TravelType.TRANSIT.name();
        this.travelTypeUnmatched = TravelType.UNMATCHED.name();

        this.overrideTypeAgent = OverrideType.AGENT.name();
        this.overrideTypeGovernment = OverrideType.GOVT.name();
        this.overrideTypeNone = OverrideType.NONE.name();

        this.referralStatusList = new ArrayList<String>();
    }

    public String getTravelType() {
        return (travelTypeNormal == null ? "" : travelTypeNormal + " ").concat(
                (travelTypeTransfer == null ? "" : travelTypeTransfer + "(X) ")).concat(
                (travelTypeUnmatched == null ? "" : travelTypeUnmatched + " ")).concat(
                (travelTypeTransit == null ? "" : travelTypeTransit + "(T)"));

    }

    public String getMovementStatus() {
        return (movementStatusCancelled == null ? "" : movementStatusCancelled + " ").concat(
               (movementStatusDenied == null ? "" : movementStatusDenied + " ")).concat(
               (movementStatusExpected == null ? "" : movementStatusExpected)).concat(
               (movementStatusNoMovement == null ? "" : movementStatusNoMovement));
    }

    public String getOverrideType() {
        return (overrideTypeAgent == null ? "" : overrideTypeAgent + " ").concat(
                (overrideTypeGovernment == null ? "" : overrideTypeGovernment + " ")).concat(
                    (overrideTypeNone == null ? "" : overrideTypeNone));
    }

    public String getHitStatus() {
        return hitStatus;
    }

    public void setHitStatus(final String hitStatus) {
        this.hitStatus = hitStatus;
    }

    public String getForenames() {
        return forenames;
    }

    public void setForenames(final String forenames) {
        this.forenames = forenames;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(final String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Calendar getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(final Calendar dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(final String nationality) {
        this.nationality = nationality;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(final String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightCarrierCode() {
        return flightCarrierCode;
    }

    public void setFlightCarrierCode(final String flightCarrierCode) {
        this.flightCarrierCode = flightCarrierCode;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(final String route) {
        this.route = route;
    }

    public String getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(final String flightStatus) {
        this.flightStatus = flightStatus;
    }

    @Override
    public String getArrivalAirport() {
        return arrivalAirport;
    }

    @Override
    public void setArrivalAirport(final String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    @Override
    public String getDepartureAirport() {
        return departureAirport;
    }

    @Override
    public void setDepartureAirport(final String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Calendar getDepartureDateRangeFrom() {
        return departureDateRangeFrom;
    }

    public void setDepartureDateRangeFrom(final Calendar dateFrom) {
        this.departureDateRangeFrom = dateFrom;
    }

    public Calendar getDepartureDateRangeTo() {
        return departureDateRangeTo;
    }

    public void setDepartureDateRangeTo(final Calendar dateTo) {
        this.departureDateRangeTo = dateTo;
    }

    public String getReferralNumber() {
        return referralNumber;
    }

    public void setReferralNumber(final String referralNumber) {
        this.referralNumber = referralNumber;
    }

    public List<String> getReferralStatusList() {
        return referralStatusList;
    }

    public void setReferralStatusList(final List<String> referralStatusList) {
        this.referralStatusList = referralStatusList;
    }

    public List<String> getHitTypeList() {
        return hitTypeList;
    }

    public void setHitTypeList(final List<String> hitTypeList) {
        this.hitTypeList = hitTypeList;
    }

    public Calendar getReferralGenDateFrom() {
        return referralGenDateFrom;
    }

    public void setReferralGenDateFrom(final Calendar referralStartDate) {
        this.referralGenDateFrom = referralStartDate;
    }

    public Calendar getReferralGenDateTo() {
        return referralGenDateTo;
    }

    public void setReferralGenDateTo(final Calendar referralEndDate) {
        this.referralGenDateTo = referralEndDate;
    }

    public Calendar getArrivalDateRangeFrom() {
        return arrivalDateRangeFrom;
    }

    public void setArrivalDateRangeFrom(final Calendar arrivalDateRangeFrom) {
        this.arrivalDateRangeFrom = arrivalDateRangeFrom;
    }

    public Calendar getArrivalDateRangeTo() {
        return arrivalDateRangeTo;
    }

    public void setArrivalDateRangeTo(final Calendar arrivalDateRangeTo) {
        this.arrivalDateRangeTo = arrivalDateRangeTo;
    }

    public String getDepartureTimeRangeFrom() {
        return departureTimeRangeFrom;
    }

    public void setDepartureTimeRangeFrom(final String departureTimeRangeFrom) {
        this.departureTimeRangeFrom = departureTimeRangeFrom;
    }

    public String getDepartureTimeRangeTo() {
        return departureTimeRangeTo;
    }

    public void setDepartureTimeRangeTo(final String departureTimeRangeTo) {
        this.departureTimeRangeTo = departureTimeRangeTo;
    }

    public String getArrivalTimeRangeFrom() {
        return arrivalTimeRangeFrom;
    }

    public void setArrivalTimeRangeFrom(final String arrivalTimeRangeFrom) {
        this.arrivalTimeRangeFrom = arrivalTimeRangeFrom;
    }

    public String getArrivalTimeRangeTo() {
        return arrivalTimeRangeTo;
    }

    public void setArrivalTimeRangeTo(final String arrivalTimeRangeTo) {
        this.arrivalTimeRangeTo = arrivalTimeRangeTo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public boolean getShowReferralCriteria() {
        return showReferralCriteria;
    }

    public void setShowReferralCriteria(final boolean showReferralCriteria) {
        this.showReferralCriteria = showReferralCriteria;
    }

    public boolean getShowFlightCriteria() {
        return showFlightCriteria;
    }

    public void setShowFlightCriteria(final boolean showFlightCriteria) {
        this.showFlightCriteria = showFlightCriteria;
    }

    public boolean getShowTravellerCriteria() {
        return showTravellerCriteria;
    }

    public void setShowTravellerCriteria(final boolean showTravellerCriteria) {
        this.showTravellerCriteria = showTravellerCriteria;
    }

    public String getMovementStatusCancelled() {
        return movementStatusCancelled;
    }

    public void setMovementStatusCancelled(final String movementStatusCancelled) {
        this.movementStatusCancelled = movementStatusCancelled;
    }

    public String getMovementStatusDenied() {
        return movementStatusDenied;
    }

    public void setMovementStatusDenied(final String movementStatusDenied) {
        this.movementStatusDenied = movementStatusDenied;
    }

    public String getMovementStatusExpected() {
        return movementStatusExpected;
    }

    public void setMovementStatusExpected(final String movementStatusExpected) {
        this.movementStatusExpected = movementStatusExpected;
    }

    public String getMovementStatusNoMovement() {
        return movementStatusNoMovement;
    }

    public void setMovementStatusNoMovement(final String movementStatusNoMovement) {
        this.movementStatusNoMovement = movementStatusNoMovement;
    }

    public String getTravelTypeNormal() {
        return travelTypeNormal;
    }

    public void setTravelTypeNormal(final String travelTypeNormal) {
        this.travelTypeNormal = travelTypeNormal;
    }

    public String getTravelTypeTransfer() {
        return travelTypeTransfer;
    }

    public void setTravelTypeTransfer(final String travelTypeTransfer) {
        this.travelTypeTransfer = travelTypeTransfer;
    }

    public String getTravelTypeTransit() {
        return travelTypeTransit;
    }

    public void setTravelTypeTransit(final String travelTypeTransit) {
        this.travelTypeTransit = travelTypeTransit;
    }

    public String getTravelTypeUnmatched() {
        return travelTypeUnmatched;
    }

    public void setTravelTypeUnmatched(final String travelTypeUnmatched) {
        this.travelTypeUnmatched = travelTypeUnmatched;
    }

    public boolean hasMovementStatus()
    {
        if (StringUtils.isNotBlank(movementStatusCancelled)) {
            return true;
        }
        if (StringUtils.isNotBlank(movementStatusDenied)) {
            return true;
        }
        if (StringUtils.isNotBlank(movementStatusExpected)) {
            return true;
        }

        return false;
    }

    public boolean hasTravelType()
    {
        if (StringUtils.isNotBlank(travelTypeTransfer)) {
            return true;
        }
        if (StringUtils.isNotBlank(travelTypeUnmatched)) {
            return true;
        }
        if (StringUtils.isNotBlank(travelTypeNormal)) {
            return true;
        }
        if (StringUtils.isNotBlank(travelTypeTransit)) {
            return true;
        }

        return false;
    }

    public Integer getSelectedHitId() {
        return selectedHitId;
    }

    public void setSelectedHitId(final Integer selectedHitId) {
        this.selectedHitId = selectedHitId;
    }

    public Long getQualifiedReferralId() {
        return qualifiedReferralId;
    }

    public void setQualifiedReferralId(final Long qualifiedReferralId) {
        this.qualifiedReferralId = qualifiedReferralId;
    }

    public String getClosureReason() {
        return closureReason;
    }

    public void setClosureReason(final String closureReason) {
        this.closureReason = closureReason;
    }

    public Long getClosedReferralId() {
        return closedReferralId;
    }

    public void setClosedReferralId(final Long closedReferralId) {
        this.closedReferralId = closedReferralId;
    }

    public List<Long> getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(final List<Long> severityLevel) {
        this.severityLevel = severityLevel;
    }

    public Long getTravellerId() {
        return travellerId;
    }

    public void setTravellerId(final Long travellerId) {
        this.travellerId = travellerId;
    }

    public String getOverrideTypeAgent() {
        return overrideTypeAgent;
    }

    public void setOverrideTypeAgent(final String overrideTypeAgent) {
        this.overrideTypeAgent = overrideTypeAgent;
    }

    public String getOverrideTypeGovernment() {
        return overrideTypeGovernment;
    }

    public void setOverrideTypeGovernment(final String overrideTypeGovernment) {
        this.overrideTypeGovernment = overrideTypeGovernment;
    }

    public String getOverrideTypeNone() {
        return overrideTypeNone;
    }

    public void setOverrideTypeNone(final String overrideTypeNone) {
        this.overrideTypeNone = overrideTypeNone;
    }

    public String getCarrierFlightNumber() {
        if (StringUtils.isNotBlank(flightCarrierCode) && StringUtils.isNotBlank(flightNumber))
        {
            return flightCarrierCode + flightNumber;
        }
        return "";
    }

    @SuppressWarnings("unchecked")
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
           LOG.warn("Could not format this ReferralSearchCommand instance!", e);
        }

        return buf.toString();
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(final String searchType) {
        this.searchType = searchType;
    }

    @Override
    public String getDefaultDateRangeFrom() {
        return defaultDateRangeFrom;
    }

    @Override
    public void setDefaultDateRangeFrom(final String defaultDateRangeFrom) {
        this.defaultDateRangeFrom = defaultDateRangeFrom;
    }

    @Override
    public String getDefaultDateRangeTo() {
        return defaultDateRangeTo;
    }

    @Override
    public void setDefaultDateRangeTo(final String defaultDateRangeTo) {
        this.defaultDateRangeTo = defaultDateRangeTo;
    }


}
