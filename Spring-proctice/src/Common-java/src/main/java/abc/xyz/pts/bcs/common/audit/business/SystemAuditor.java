/**
 *  This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.audit.business;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import abc.xyz.das.dascore.model.generated.output.idetectdb.Api;
import abc.xyz.das.dascore.model.generated.output.idetectdb.DbData;
import abc.xyz.das.dascore.model.generated.output.idetectdb.FlightSegmentType;
import abc.xyz.das.dascore.model.generated.output.idetectdb.Pnr;
import abc.xyz.das.dascore.model.generated.output.idetectdb.pnrtypes.BookingNameListType;
import abc.xyz.das.dascore.model.generated.output.idetectdb.pnrtypes.BookingNameType;
import abc.xyz.das.dascore.model.generated. product.common.ApiTravelDocumentType;
import abc.xyz.pts.bcs.common.audit.annotation.Auditable;
import abc.xyz.pts.bcs.common.audit.bean.AuditContextHolder;
import abc.xyz.pts.bcs.common.dao.utils.DateStringUtils;
import abc.xyz.pts.bcs.common.dto.FlightSegment;
import abc.xyz.pts.bcs.common.dto.Referral;
import abc.xyz.pts.bcs.common.dto.ReferralHit;
import abc.xyz.pts.bcs.common.dto.Traveller;
import abc.xyz.pts.bcs.common.dto.TravellerSegmentSummary;
import abc.xyz.pts.bcs.common.util.CalendarUtils;
import abc.xyz.pts.bcs.irisk.mvo.riskmatch.Match;
import abc.xyz.pts.bcs.irisk.mvo.riskmatch.Response;
import abc.xyz.pts.bcs.scheduler.das.PreDepartureScanRequest;
import abc.xyz.pts.bcs.scheduler.das.PreDepartureScanResponse;

import com. .pts.bcs.irisk.common.FlightSegmentSummary;

/**
 *
 * @author Reddy.Yattapu
 *
 */
public class SystemAuditor {

    public static final String BOOKING = "BOOKING";
    public static final String CHECKIN = "CHECK-in";

	@Auditable(value = Event.REFERRAL_ADD_HIT)
    public void auditAddReferralHit(final Response response, final ReferralHit referralHit) {

        SystemAuditor.auditFlightSegmentData(response);
        SystemAuditor.auditTravellerDetails(response);

        AuditContextHolder.addParameterToDefaultEvent(Parameter.REFERRAL_NUMBER, String.valueOf(referralHit.getRefId()));
        AuditContextHolder.addParameterToDefaultEvent(Parameter.REFERRAL_HIT_ID, String.valueOf(referralHit.getId()));
    }

    @Auditable(value = Event.REFERRAL_ADD_HIT)
    public void auditAddReferralHit(final Traveller traveller, final ReferralHit newReferralHit, final FlightSegment flightSegment) {
        if (flightSegment != null) {
            auditFlightSegmentData(flightSegment);
        }
        auditTraveller(traveller);
        AuditContextHolder.addParameterToDefaultEvent(Parameter.REFERRAL_HIT_ID, String.valueOf(newReferralHit.getId()));
    }

    @Auditable(value=Event.AUTO_QUALIFIED_HIT)
    public void auditAutoQualifiedHit(final Response response, final Long referralId ,  final Long referralHitId )  {

        auditFlightSegmentData(response);
        auditTravellerDetails(response);

        AuditContextHolder.addParameterToDefaultEvent(Parameter.REFERRAL_NUMBER, String.valueOf(referralId));

        AuditContextHolder.addParameterToDefaultEvent(Parameter.REFERRAL_HIT_ID, String.valueOf(referralHitId));

    }

    @Auditable(value = Event.AUTO_CLOSED_REFERRAL)
    public void auditAutoClosure(final TravellerSegmentSummary travellerSegmentSummary) {
        // Flight Details
        AuditContextHolder.addParameterToDefaultEvent(Parameter.FLIGHT_SEG_ID, travellerSegmentSummary.getFlightSegId().toString());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.CARRIER_CODE, travellerSegmentSummary.getOperCarrierCode());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.FLIGHT_NUMBER, travellerSegmentSummary.getOperFlightNo());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.CARRIER_FLIGHT_NUMBER, travellerSegmentSummary.getOperCarrierCode() + travellerSegmentSummary.getOperFlightNo());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.DEP_AIRPORT_CODE, travellerSegmentSummary.getDepAirportCode());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.ARR_AIRPORT_CODE, travellerSegmentSummary.getArrAirportCode());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.SCHEDULED_DEP_DATE, CalendarUtils.getCalendarAsStringTimestamp(travellerSegmentSummary.getScheduledDepDatetime()));
        AuditContextHolder.addParameterToDefaultEvent(Parameter.SCHEDULED_ARR_DATE, CalendarUtils.getCalendarAsStringTimestamp(travellerSegmentSummary.getScheduledArrDatetime()));

        // Traveller Details
        AuditContextHolder.addParameterToDefaultEvent(Parameter.TRAVELLER_ID, travellerSegmentSummary.getTraId().toString());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.FORENAME, travellerSegmentSummary.getForename());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.LASTNAME, travellerSegmentSummary.getLastName());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.DOCUMENT_NUMBER, travellerSegmentSummary.getPriDocNo());

        // Referral Details
        AuditContextHolder.addParameterToDefaultEvent(Parameter.REFERRAL_NUMBER, travellerSegmentSummary.getRefId().toString());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.NO_OF_HITS, travellerSegmentSummary.getHitsCount().toString());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.REFERRAL_PREV_STATUS, travellerSegmentSummary.getRefStatus());

    }

    @Auditable(value = Event.REFERRAL_CREATED)
    public void auditReferralCreated(final Traveller traveller, final Referral newReferral, final FlightSegment flightSegment, final long noOfHits) {

        SystemAuditor.auditTravellersData(traveller, flightSegment);

        AuditContextHolder.addParameterToDefaultEvent(Parameter.REFERRAL_NUMBER, String.valueOf(newReferral.getId()));
        AuditContextHolder.addParameterToDefaultEvent(Parameter.NO_OF_HITS, String.valueOf(noOfHits));

    }

    @Auditable(value = Event.REFERRAL_CREATED)
    public void auditReferralCreated(final Response response, final long referralId, final long noOfHits) {

        SystemAuditor.auditFlightSegmentData(response);
        SystemAuditor.auditTravellerDetails(response);

        AuditContextHolder.addParameterToDefaultEvent(Parameter.REFERRAL_NUMBER, String.valueOf(referralId));
        AuditContextHolder.addParameterToDefaultEvent(Parameter.NO_OF_HITS, String.valueOf(noOfHits));

    }

    @Auditable(value = Event.AUTO_CLEAR_HIT)
    public void auditClearedDocumentHit(final Response response, final Match match, final long referralId) {

        SystemAuditor.auditFlightSegmentData(response);
        SystemAuditor.auditTravellerDetails(response);

        AuditContextHolder.addParameterToDefaultEvent(Parameter.REFERRAL_NUMBER, String.valueOf(referralId));
        AuditContextHolder.addParameterToDefaultEvent(Parameter.CLEARED_HIT_ID, String.valueOf(match.getClearedDocumentsId()));

    }

    public static void auditTravellerDetails(final Response response) {
        if (response.getRequest() != null) {
            final abc.xyz.pts.bcs.irisk.mvo.riskmatch.Traveller t = response.getRequest().get(0);
            if (t != null) {
                AuditContextHolder.addParameterToDefaultEvent(Parameter.TRAVELLER_ID, String.valueOf(t.getTraId()));
                AuditContextHolder.addParameterToDefaultEvent(Parameter.FORENAME, t.getForeName());
                AuditContextHolder.addParameterToDefaultEvent(Parameter.LASTNAME, t.getSurname());
                if (t.getTravelDocuments() != null && t.getTravelDocuments().size() > 0) {
                    AuditContextHolder.addParameterToDefaultEvent(Parameter.DOCUMENT_NO, t.getTravelDocuments().get(0).getDocumentNo());
                }
            }
        }
    }

    public static void auditFlightSegmentData(final Response response) {
        final FlightSegmentSummary flightSegmentSummary = response.getFlightSegmentSummary();
        auditFlightSegmentDataToAnnotatedEvent(flightSegmentSummary);
    }

    public static void auditFlightSegmentData(final FlightSegmentSummary flightSegmentSummary) {
        auditFlightSegmentDataToAnnotatedEvent(flightSegmentSummary);
    }

    private static void auditFlightSegmentDataToAnnotatedEvent(final FlightSegmentSummary flightSegmentSummary) {
        if (flightSegmentSummary != null) {
            if (flightSegmentSummary.getFlightSegId() != 0) {
                AuditContextHolder.addParameterToDefaultEvent(Parameter.FLIGHT_SEG_ID, String.valueOf(flightSegmentSummary.getFlightSegId()));
            }
            AuditContextHolder.addParameterToDefaultEvent(Parameter.FLIGHT_NUMBER, flightSegmentSummary.getOperFlightNo());
            AuditContextHolder.addParameterToDefaultEvent(Parameter.CARRIER_CODE, flightSegmentSummary.getOperCarrierCode());
            AuditContextHolder.addParameterToDefaultEvent(Parameter.CARRIER_FLIGHT_NUMBER, flightSegmentSummary.getOperCarrierCode() + flightSegmentSummary.getOperFlightNo());
            if (flightSegmentSummary.getDepAirportCode() != null) {
                AuditContextHolder.addParameterToDefaultEvent(Parameter.DEP_AIRPORT_CODE, flightSegmentSummary.getDepAirportCode());
            }
            if (flightSegmentSummary.getArrAirportCode() != null) {
                AuditContextHolder.addParameterToDefaultEvent(Parameter.ARR_AIRPORT_CODE, flightSegmentSummary.getArrAirportCode());
            }
            if (flightSegmentSummary.getScheduledDepDateTime() != null) {
                AuditContextHolder.addParameterToDefaultEvent(Parameter.SCHEDULED_DEP_DATE, flightSegmentSummary.getScheduledDepDateTime());
            }
            if ( flightSegmentSummary.getScheduledArrDateTime() != null) {
                AuditContextHolder.addParameterToDefaultEvent(Parameter.SCHEDULED_ARR_DATE, flightSegmentSummary.getScheduledArrDateTime());
            }
        }
    }

    public static void auditFlightSegmentDataToAnnotatedEvent(final String flightSegId, final String operCarrierCode, final String operFlightNo, final String depAirportCode, final String arrAirportCode, final String depDateTime, final String arrDateTime) {

        AuditContextHolder.addParameterToDefaultEvent(Parameter.FLIGHT_SEG_ID, flightSegId);
        AuditContextHolder.addParameterToDefaultEvent(Parameter.FLIGHT_NUMBER, operFlightNo);
        AuditContextHolder.addParameterToDefaultEvent(Parameter.CARRIER_CODE, operCarrierCode);
        AuditContextHolder.addParameterToDefaultEvent(Parameter.CARRIER_FLIGHT_NUMBER, operCarrierCode + operFlightNo);
        AuditContextHolder.addParameterToDefaultEvent(Parameter.DEP_AIRPORT_CODE, depAirportCode);
        AuditContextHolder.addParameterToDefaultEvent(Parameter.ARR_AIRPORT_CODE, arrAirportCode);
        AuditContextHolder.addParameterToDefaultEvent(Parameter.SCHEDULED_DEP_DATE, depDateTime);
        AuditContextHolder.addParameterToDefaultEvent(Parameter.SCHEDULED_ARR_DATE, arrDateTime);

    }

    public static void auditTraveller(final Traveller traveller) {
        if (traveller.getId() != null && traveller.getId() != 0) {
            AuditContextHolder.addParameterToDefaultEvent(Parameter.TRAVELLER_ID, traveller.getId().toString());
        }
        AuditContextHolder.addParameterToDefaultEvent(Parameter.FORENAME, traveller.getForeName());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.LASTNAME, traveller.getLastName());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.DOCUMENT_NO, traveller.getPriDocNo());
    }

    private static void auditFlightSegmentData(final FlightSegment flightSegment) {

        AuditContextHolder.addParameterToDefaultEvent(Parameter.CARRIER_CODE, flightSegment.getOperCarrierCode());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.FLIGHT_NUMBER, flightSegment.getOperFlightNo());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.CARRIER_FLIGHT_NUMBER, flightSegment.getOperCarrierCode() + flightSegment.getOperFlightNo());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.DEP_AIRPORT_CODE, flightSegment.getDepAirportCode());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.ARR_AIRPORT_CODE, flightSegment.getArrAirportCode());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.SCHEDULED_DEP_DATE, CalendarUtils.getCalendarAsStringTimestamp(flightSegment.getScheduledDepDatetime()));
        AuditContextHolder.addParameterToDefaultEvent(Parameter.SCHEDULED_ARR_DATE, CalendarUtils.getCalendarAsStringTimestamp(flightSegment.getScheduledArrDatetime()));
    }

    public static void auditTravellersData(final Traveller traveller, final FlightSegment flightSegment) {
        if (flightSegment != null) {
            auditFlightSegmentData(flightSegment);
        }
        auditTraveller(traveller);
    }

    public static void auditFlightSegmentData(final DbData data, final Event event) {
        final FlightSegmentType flightSegment = data.getFlightSegment();
        final Event eventForAudit = (event == null)?Event.ANNOTATED_EVENT:event;

        AuditContextHolder.addParameter(eventForAudit, Parameter.FLIGHT_NUMBER, flightSegment.getOperFlightNo());
        AuditContextHolder.addParameter(eventForAudit, Parameter.CARRIER_CODE, flightSegment.getOperCarrierCode());
        AuditContextHolder.addParameter(eventForAudit, Parameter.CARRIER_FLIGHT_NUMBER, flightSegment.getOperCarrierCode() + flightSegment.getOperFlightNo());
        AuditContextHolder.addParameter(eventForAudit, Parameter.DEP_AIRPORT_CODE, flightSegment.getDepAirportCode());
        AuditContextHolder.addParameter(eventForAudit, Parameter.ARR_AIRPORT_CODE, flightSegment.getArrAirportCode());
        AuditContextHolder.addParameter(eventForAudit, Parameter.SCHEDULED_DEP_DATE, DateStringUtils.getStringDateFromSchemaDate(flightSegment.getScheduledDepDateTime()));
        AuditContextHolder.addParameter(eventForAudit, Parameter.SCHEDULED_ARR_DATE, DateStringUtils.getStringDateFromSchemaDate(flightSegment.getScheduledArrDateTime()));

    }

    public static void auditFlightSegmentData(final FlightSegmentType flightSegment, final Event event) {
    	 final Event eventForAudit = (event == null)?Event.ANNOTATED_EVENT:event;

        AuditContextHolder.addParameter(eventForAudit, Parameter.FLIGHT_NUMBER, flightSegment.getOperFlightNo());
        AuditContextHolder.addParameter(eventForAudit, Parameter.CARRIER_CODE, flightSegment.getOperCarrierCode());
        AuditContextHolder.addParameter(eventForAudit, Parameter.CARRIER_FLIGHT_NUMBER, flightSegment.getOperCarrierCode() + flightSegment.getOperFlightNo());
        AuditContextHolder.addParameter(eventForAudit, Parameter.DEP_AIRPORT_CODE, flightSegment.getDepAirportCode());
        AuditContextHolder.addParameter(eventForAudit, Parameter.ARR_AIRPORT_CODE, flightSegment.getArrAirportCode());
        AuditContextHolder.addParameter(eventForAudit, Parameter.SCHEDULED_DEP_DATE, DateStringUtils.getStringDateFromSchemaDate(flightSegment.getScheduledDepDateTime()));
        AuditContextHolder.addParameter(eventForAudit, Parameter.SCHEDULED_ARR_DATE, DateStringUtils.getStringDateFromSchemaDate(flightSegment.getScheduledArrDateTime()));
    }

    public static void auditTravellerData(final DbData data, final Api api, final Event event) {
        auditTravellerData(data, api, event, null);
    }

    public static void auditTravellerData(final FlightSegmentType flightSegment, final Api api, final Event event){
    	auditTravellerData(flightSegment, api, event, null);
    }

    public static void auditTravellerData(final DbData data, final Api api, final Event event, final Long travellerId) {
    	 final Event eventForAudit = (event == null)?Event.ANNOTATED_EVENT:event;
        auditFlightSegmentData(data, eventForAudit);
        if (travellerId != null) {
            AuditContextHolder.addParameter(eventForAudit, Parameter.TRAVELLER_ID, travellerId.toString());
        }
        AuditContextHolder.addParameter(eventForAudit, Parameter.FORENAME, api.getForename());
        AuditContextHolder.addParameter(eventForAudit, Parameter.LASTNAME, api.getLastname());
        if (api.getTravelDocumentList() != null) {
            final List<ApiTravelDocumentType> docList = api.getTravelDocumentList().getTravelDocument();
            if (CollectionUtils.isNotEmpty(docList) && docList.size() > 0) {
                final ApiTravelDocumentType offlDoc = docList.get(0);
                AuditContextHolder.addParameter(eventForAudit, Parameter.DOCUMENT_NO, offlDoc.getDocumentNo());

            }
        }

    }

    public static void auditTravellerData(final FlightSegmentType flightSegment, final Api api, final Event event, final Long travellerId){
    	 final Event eventForAudit = (event == null)?Event.ANNOTATED_EVENT:event;

        auditFlightSegmentData(flightSegment, eventForAudit);
        if (travellerId != null) {
            AuditContextHolder.addParameter(eventForAudit, Parameter.TRAVELLER_ID, travellerId.toString());
        }
        AuditContextHolder.addParameter(eventForAudit, Parameter.FORENAME, api.getForename());
        AuditContextHolder.addParameter(eventForAudit, Parameter.LASTNAME, api.getLastname());
        if (api.getTravelDocumentList() != null) {
            final List<ApiTravelDocumentType> docList = api.getTravelDocumentList().getTravelDocument();
            if (docList.size() > 0) {
                final ApiTravelDocumentType offlDoc = docList.get(0);
                AuditContextHolder.addParameter(eventForAudit, Parameter.DOCUMENT_NO, offlDoc.getDocumentNo());

            }
        }
    }
    public static void auditApTravellerData(final Traveller traveller, final FlightSegment flightSegment) {
        // Flight Details
        AuditContextHolder.addParameterToDefaultEvent(Parameter.FLIGHT_NUMBER, flightSegment.getOperFlightNo());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.CARRIER_CODE, flightSegment.getOperCarrierCode());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.CARRIER_FLIGHT_NUMBER, flightSegment.getOperCarrierCode() + flightSegment.getOperFlightNo());
        // Traveller Details
        AuditContextHolder.addParameterToDefaultEvent(Parameter.FORENAME, traveller.getForeName());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.LASTNAME, traveller.getLastName());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.DOCUMENT_NO, traveller.getPriDocNo());

    }

    public static void auditScheduleRequest(final PreDepartureScanRequest pdsRequest) {

        AuditContextHolder.addParameterToDefaultEvent(Parameter.PRE_SCAN_ID, String.valueOf(pdsRequest.getPreScanId()));
        AuditContextHolder.addParameterToDefaultEvent(Parameter.CARRIER_CODE, pdsRequest.getCarrierCode());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.FLIGHT_NUMBER, pdsRequest.getFlightNumber());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.DEP_AIRPORT_CODE, pdsRequest.getDepartureAirportCode());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.ARR_AIRPORT_CODE, pdsRequest.getArrivalAirportCode());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.DEPARTURE_DATE, DateStringUtils.getStringFromCalendar(pdsRequest.getDepartureDate()));
        AuditContextHolder.addParameterToDefaultEvent(Parameter.REQUEST_TYPE, pdsRequest.getRequestType().value());
        AuditContextHolder.addParameterToDefaultEvent(Parameter.CREATED_DATE_TIME, DateStringUtils.getStringFromDateTime(pdsRequest.getCreatedDateTime().getTime()));
        AuditContextHolder.addParameterToDefaultEvent(Parameter.SCAN_TYPE, pdsRequest.getScanType().value());

    }

    public static void auditScheduleResponse(final PreDepartureScanResponse pdsResponse) {
        AuditContextHolder.addParameterToDefaultEvent(Parameter.PRE_SCAN_ID, String.valueOf(pdsResponse.getPreScanId()));
        AuditContextHolder.addParameterToDefaultEvent(Parameter.CREATED_DATE_TIME, DateStringUtils.getStringFromDateTime(pdsResponse.getCreatedDateTime().getTime()));
        AuditContextHolder.addParameterToDefaultEvent(Parameter.STATUS_CODE, pdsResponse.getStatusCode().value());

    }

    // QAT-2646 PNR-Audit
    public static void auditFlightSegDetail(final FlightSegmentType flightSegment, final Event event,final String dataType){
    	//TODO: Refactor the method name while doing DCS audit since this method can serve the purpose for DCS as well - Venu/amit
    	// common set of flight-segment data for multiple PNRs

        AuditContextHolder.addParameter(event, Parameter.DATA_TYPE, dataType);
        AuditContextHolder.addParameter(event, Parameter.CARRIER_CODE,flightSegment.getOperCarrierCode());
        AuditContextHolder.addParameter(event, Parameter.FLIGHT_NUMBER, flightSegment.getOperFlightNo());
        AuditContextHolder.addParameter(event, Parameter.CARRIER_FLIGHT_NUMBER,composeCarrierCodeFlightNo(flightSegment));
        AuditContextHolder.addParameter(event, Parameter.DEP_AIRPORT_CODE, flightSegment.getDepAirportCode());
        AuditContextHolder.addParameter(event, Parameter.ARR_AIRPORT_CODE, flightSegment.getArrAirportCode());
        AuditContextHolder.addParameter(event, Parameter.SCHEDULED_DEP_DATE, DateStringUtils.getStringDateFromSchemaDate(flightSegment.getScheduledDepDateTime()));
        AuditContextHolder.addParameter(event, Parameter.SCHEDULED_ARR_DATE, DateStringUtils.getStringDateFromSchemaDate(flightSegment.getScheduledArrDateTime()));
    }



    // QAT-2646 PNR-Audit
    public static void auditPnrDetails(final Pnr pnr, final Event event){

    	AuditContextHolder.addParameter(event, Parameter.PNRID, Long.valueOf(pnr.getPnrId()).toString());
    	final String numberOfTravellers = getNumberOfTravellers(pnr);
    	if(StringUtils.isNotEmpty(numberOfTravellers)){
    		 AuditContextHolder.addParameter(event, Parameter.NUMBER_OF_TRAVELLERS_ON_BOOKING, numberOfTravellers);
    	}

    }
    /**
     * Method to set the travellers in the DCS message
     * @param dcsListSize - number of entries in the DCS message
     * @param event - event that needs to be audited.
     */
    public static void updateTravellersinCheckin(final int dcsListSize, final Event event){
    	AuditContextHolder.addParameter(event, Parameter.TRAVELLER_COUNT_IN_THE_MESSAGE, String.valueOf(dcsListSize));
    }

    //method to null check and get number of travellers in the PNR
    private static String getNumberOfTravellers(final Pnr pnr){
    	String pnrNameCount=null;
    	if(pnr!=null){
    		final BookingNameListType bookingNameList = pnr.getBookingNameList();
    		if(bookingNameList!=null){
    			final List<BookingNameType> bookingName = bookingNameList.getBookingName();
    			if(bookingName!=null){
    				final BookingNameType bookingNameType = bookingName.get(0);
    				if(bookingNameType!=null){
    					 final Integer pnrNameCnt = bookingNameType.getPnrNameCount();
    					 if(pnrNameCnt!=null){
    						 pnrNameCount =  pnrNameCnt.toString();
    					 }
    				}
    			}
    		}
    	}

    	return  pnrNameCount;
    }
    //method to concatenate carriercode and flightnumber
    private static String composeCarrierCodeFlightNo(
			final FlightSegmentType flightSegment) {
		final StringBuilder carrierCodeFlightNumber = new StringBuilder();

    	if(StringUtils.isNotEmpty(flightSegment.getOperCarrierCode())){
    		carrierCodeFlightNumber.append(flightSegment.getOperCarrierCode());
    	}
    	if(StringUtils.isNotEmpty(flightSegment.getOperFlightNo())){
    		carrierCodeFlightNumber.append(flightSegment.getOperFlightNo());
    	}
    	return carrierCodeFlightNumber.toString();
	}
}
