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

public class ReferralDetails {
    private Long id;
    private String status;
    private Long updateVersionNo;
    private String depAirportCode;
    private String arrAirportCode;
    private Calendar    depDatetime;
    private Calendar arrDatetime;
    private Calendar scheduledDepDatetime;
    private Calendar scheduledArrDatetime;
    private String operCarrierCode;
    private String operFlightNo;
    private String flightStatus;
    private String flightType;
    private Long flightSegId;
    private String notificationStatus;

    public Long getId() {
        return id;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(final String status) {
        this.status = status;
    }
    public Long getUpdateVersionNo() {
        return updateVersionNo;
    }
    public void setUpdateVersionNo(final Long updateVersionNo) {
        this.updateVersionNo = updateVersionNo;
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
    public Calendar getDepDatetime() {
        return depDatetime;
    }
    public void setDepDatetime(final Calendar depDatetime) {
        this.depDatetime = depDatetime;
    }
    public Calendar getArrDatetime() {
        return arrDatetime;
    }
    public void setArrDatetime(final Calendar arrDatetime) {
        this.arrDatetime = arrDatetime;
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
    public String getFlightStatus() {
        return flightStatus;
    }
    public void setFlightStatus(final String flightStatus) {
        this.flightStatus = flightStatus;
    }
    public String getFlightType() {
        return flightType;
    }
    public void setFlightType(final String flightType) {
        this.flightType = flightType;
    }
    public Long getFlightSegId() {
        return flightSegId;
    }
    public void setFlightSegId(final Long flightSegId) {
        this.flightSegId = flightSegId;
    }
    public String getNotificationStatus() {
        return notificationStatus;
    }
    public void setNotificationStatus(final String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }


}

