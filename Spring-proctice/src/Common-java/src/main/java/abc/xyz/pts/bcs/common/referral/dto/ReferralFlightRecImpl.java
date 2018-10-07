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

public final class ReferralFlightRecImpl implements Serializable, ReferralFlightRec
{
    private static final long serialVersionUID = 4817888763835822717L;

    private Long flightSegmentId;
    private String carrierCode;
    private String carrierNumber;
    private String depAirportCode;
    private String appAirportCode;
    private Calendar depTime;
    private Calendar arrTime;

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(final String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getCarrierNumber() {
        return carrierNumber;
    }

    public void setCarrierNumber(final String carrierNumber) {
        this.carrierNumber = carrierNumber;
    }

    public String getDepAirportCode() {
        return depAirportCode;
    }

    public void setDepAirportCode(final String depAirportCode) {
        this.depAirportCode = depAirportCode;
    }

    public String getAppAirportCode() {
        return appAirportCode;
    }

    public void setAppAirportCode(final String appAirportCode) {
        this.appAirportCode = appAirportCode;
    }

    public Calendar getDepTime() {
        return depTime;
    }

    public void setDepTime(final Calendar depTime) {
        this.depTime = depTime;
    }

    public Calendar getArrTime() {
        return arrTime;
    }

    public void setArrTime(final Calendar arrTime) {
        this.arrTime = arrTime;
    }

    public Long getFlightSegmentId() {
        return flightSegmentId;
    }

    public void setFlightSegmentId(final Long flightSegmentId) {
        this.flightSegmentId = flightSegmentId;
    }
}
