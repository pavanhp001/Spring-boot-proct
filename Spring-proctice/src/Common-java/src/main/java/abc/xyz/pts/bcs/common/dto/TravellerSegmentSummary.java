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

public class TravellerSegmentSummary {
    // Data and Time When this information was retrieved
    private Calendar eventDatetime;
    // Flight Details
   private Long flightSegId;
   private String operCarrierCode;
   private String operFlightNo;
   private Calendar scheduledDepDatetime;
   private Calendar scheduledArrDatetime;
   private String depAirportCode;
   private String arrAirportCode;
    // Traveller Details
   private Long traId;
    private String forename;
    private String lastName;
    private String priDocNo;
    // Referral & Hits Details
   private Long refId;
   private String refStatus;
   private Long hitsCount;
    public Calendar getEventDatetime() {
        return eventDatetime;
    }
    public void setEventDatetime(final Calendar eventDatetime) {
        this.eventDatetime = eventDatetime;
    }
    public Long getFlightSegId() {
        return flightSegId;
    }
    public void setFlightSegId(final Long flightSegId) {
        this.flightSegId = flightSegId;
    }
    public String getOperCarrierCode() {
        return operCarrierCode;
    }
    public void setOperCarrierCode(final String operCarrierCode) {
        this.operCarrierCode = operCarrierCode;
    }
    public String getOperFlightNo() {
        return operFlightNo;
    }
    public void setOperFlightNo(final String operFlightNo) {
        this.operFlightNo = operFlightNo;
    }
    public Calendar getScheduledDepDatetime() {
        return scheduledDepDatetime;
    }
    public void setScheduledDepDatetime(final Calendar scheduledDepDatetime) {
        this.scheduledDepDatetime = scheduledDepDatetime;
    }
    public Calendar getScheduledArrDatetime() {
        return scheduledArrDatetime;
    }
    public void setScheduledArrDatetime(final Calendar scheduledArrDatetime) {
        this.scheduledArrDatetime = scheduledArrDatetime;
    }
    public String getDepAirportCode() {
        return depAirportCode;
    }
    public void setDepAirportCode(final String depAirportCode) {
        this.depAirportCode = depAirportCode;
    }
    public String getArrAirportCode() {
        return arrAirportCode;
    }
    public void setArrAirportCode(final String arrAirportCode) {
        this.arrAirportCode = arrAirportCode;
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
    public Long getRefId() {
        return refId;
    }
    public void setRefId(final Long refId) {
        this.refId = refId;
    }
    public String getRefStatus() {
        return refStatus;
    }
    public void setRefStatus(final String refStatus) {
        this.refStatus = refStatus;
    }
    public Long getHitsCount() {
        return hitsCount;
    }
    public void setHitsCount(final Long hitsCount) {
        this.hitsCount = hitsCount;
    }
    public String getPriDocNo() {
        return priDocNo;
    }
    public void setPriDocNo(final String priDocNo) {
        this.priDocNo = priDocNo;
    }
    public Long getTraId() {
        return traId;
    }
    public void setTraId(final Long traId) {
        this.traId = traId;
    }

}
