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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FLIGHT_SEG_RC_REC", namespace = "FLIGHT_SEG_RC_REC")
public class FlightSegment {

    private Long flightSegId;
    private Long flightId;
    private String operCarrierCode;
    private String operFlightNo;
    private Calendar offblockDatetime;
    private String flightType;
    private String flightStatus;
    private String depAirportCode;
    private String arrAirportCode;
    private Calendar scheduledDepDatetime;
    private Calendar scheduledArrDatetime;
    private Calendar estimatedDepDatetime;
    private Calendar estimatedArrDatetime;
    private Calendar actualDepDatetime;
    private Calendar actualArrDatetime;
    private Calendar depDatetime;
    private Calendar arrDatetime;
    private String originAirportCode;
    private String destAirportCode;
    private String flightRoute;
    private String mktFlightNos;
    private String priWorkingAirportCode;
    private String secWorkingAirportCode;
    private String reRoutedDepAirportCode;
    private String reRoutedArrAirportCode;
    private Calendar createdDatetime;
    private Calendar modifiedDatetime;
    private Long seqNo;

    /**
     * Gets the value of the flightSegId property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getFlightSegId() {
        return flightSegId;
    }

    /**
     * Sets the value of the flightSegId property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setFlightSegId(final Long value) {
        this.flightSegId = value;
    }

    /**
     * Gets the value of the flightId property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getFlightId() {
        return flightId;
    }

    /**
     * Sets the value of the flightId property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setFlightId(final Long value) {
        this.flightId = value;
    }

    /**
     * Gets the value of the operCarrierCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOperCarrierCode() {
        return operCarrierCode;
    }

    /**
     * Sets the value of the operCarrierCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOperCarrierCode(final String value) {
        this.operCarrierCode = value;
    }

    /**
     * Gets the value of the operFlightNo property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOperFlightNo() {
        return operFlightNo;
    }

    /**
     * Sets the value of the operFlightNo property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOperFlightNo(final String value) {
        this.operFlightNo = value;
    }

    /**
     * Gets the value of the offblockDatetime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public Calendar getOffblockDatetime() {
        return offblockDatetime;
    }

    /**
     * Sets the value of the offblockDatetime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOffblockDatetime(final Calendar value) {
        this.offblockDatetime = value;
    }

    /**
     * Gets the value of the flightType property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFlightType() {
        return flightType;
    }

    /**
     * Sets the value of the flightType property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFlightType(final String value) {
        this.flightType = value;
    }

    /**
     * Gets the value of the flightStatus property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFlightStatus() {
        return flightStatus;
    }

    /**
     * Sets the value of the flightStatus property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFlightStatus(final String value) {
        this.flightStatus = value;
    }

    /**
     * Gets the value of the depAirportCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDepAirportCode() {
        return depAirportCode;
    }

    /**
     * Sets the value of the depAirportCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDepAirportCode(final String value) {
        this.depAirportCode = value;
    }

    /**
     * Gets the value of the arrAirportCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getArrAirportCode() {
        return arrAirportCode;
    }

    /**
     * Sets the value of the arrAirportCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setArrAirportCode(final String value) {
        this.arrAirportCode = value;
    }

    /**
     * Gets the value of the scheduledDepDatetime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public Calendar getScheduledDepDatetime() {
        return scheduledDepDatetime;
    }

    /**
     * Sets the value of the scheduledDepDatetime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setScheduledDepDatetime(final Calendar value) {
        this.scheduledDepDatetime = value;
    }

    /**
     * Gets the value of the scheduledArrDatetime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public Calendar getScheduledArrDatetime() {
        return scheduledArrDatetime;
    }

    /**
     * Sets the value of the scheduledArrDatetime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setScheduledArrDatetime(final Calendar value) {
        this.scheduledArrDatetime = value;
    }

    /**
     * Gets the value of the estimatedDepDatetime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public Calendar getEstimatedDepDatetime() {
        return estimatedDepDatetime;
    }

    /**
     * Sets the value of the estimatedDepDatetime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setEstimatedDepDatetime(final Calendar value) {
        this.estimatedDepDatetime = value;
    }

    /**
     * Gets the value of the estimatedArrDatetime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public Calendar getEstimatedArrDatetime() {
        return estimatedArrDatetime;
    }

    /**
     * Sets the value of the estimatedArrDatetime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setEstimatedArrDatetime(final Calendar value) {
        this.estimatedArrDatetime = value;
    }

    /**
     * Gets the value of the actualDepDatetime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public Calendar getActualDepDatetime() {
        return actualDepDatetime;
    }

    /**
     * Sets the value of the actualDepDatetime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setActualDepDatetime(final Calendar value) {
        this.actualDepDatetime = value;
    }

    /**
     * Gets the value of the actualArrDatetime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public Calendar getActualArrDatetime() {
        return actualArrDatetime;
    }

    /**
     * Sets the value of the actualArrDatetime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setActualArrDatetime(final Calendar value) {
        this.actualArrDatetime = value;
    }

    /**
     * Gets the value of the depDatetime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public Calendar getDepDatetime() {
        return depDatetime;
    }

    /**
     * Sets the value of the depDatetime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDepDatetime(final Calendar value) {
        this.depDatetime = value;
    }

    /**
     * Gets the value of the arrDatetime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public Calendar getArrDatetime() {
        return arrDatetime;
    }

    /**
     * Sets the value of the arrDatetime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setArrDatetime(final Calendar value) {
        this.arrDatetime = value;
    }

    /**
     * Gets the value of the originAirportCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOriginAirportCode() {
        return originAirportCode;
    }

    /**
     * Sets the value of the originAirportCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOriginAirportCode(final String value) {
        this.originAirportCode = value;
    }

    /**
     * Gets the value of the destAirportCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDestAirportCode() {
        return destAirportCode;
    }

    /**
     * Sets the value of the destAirportCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDestAirportCode(final String value) {
        this.destAirportCode = value;
    }

    /**
     * Gets the value of the flightRoute property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFlightRoute() {
        return flightRoute;
    }

    /**
     * Sets the value of the flightRoute property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFlightRoute(final String value) {
        this.flightRoute = value;
    }

    /**
     * Gets the value of the mktFlightNos property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMktFlightNos() {
        return mktFlightNos;
    }

    /**
     * Sets the value of the mktFlightNos property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMktFlightNos(final String value) {
        this.mktFlightNos = value;
    }

    /**
     * Gets the value of the priWorkingAirportCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPriWorkingAirportCode() {
        return priWorkingAirportCode;
    }

    /**
     * Sets the value of the priWorkingAirportCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPriWorkingAirportCode(final String value) {
        this.priWorkingAirportCode = value;
    }

    /**
     * Gets the value of the secWorkingAirportCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSecWorkingAirportCode() {
        return secWorkingAirportCode;
    }

    /**
     * Sets the value of the secWorkingAirportCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSecWorkingAirportCode(final String value) {
        this.secWorkingAirportCode = value;
    }

    /**
     * Gets the value of the reRoutedDepAirportCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getReRoutedDepAirportCode() {
        return reRoutedDepAirportCode;
    }

    /**
     * Sets the value of the reRoutedDepAirportCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setReRoutedDepAirportCode(final String value) {
        this.reRoutedDepAirportCode = value;
    }

    /**
     * Gets the value of the reRoutedArrAirportCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getReRoutedArrAirportCode() {
        return reRoutedArrAirportCode;
    }

    /**
     * Sets the value of the reRoutedArrAirportCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setReRoutedArrAirportCode(final String value) {
        this.reRoutedArrAirportCode = value;
    }

    /**
     * Gets the value of the createdDatetime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public Calendar getCreatedDatetime() {
        return createdDatetime;
    }

    /**
     * Sets the value of the createdDatetime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCreatedDatetime(final Calendar value) {
        this.createdDatetime = value;
    }

    /**
     * Gets the value of the modifiedDatetime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public Calendar getModifiedDatetime() {
        return modifiedDatetime;
    }

    /**
     * Sets the value of the modifiedDatetime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setModifiedDatetime(final Calendar value) {
        this.modifiedDatetime = value;
    }

    /**
     * Gets the value of the seqNo property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getSeqNo() {
        return seqNo;
    }

    /**
     * Sets the value of the seqNo property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setSeqNo(final Long value) {
        this.seqNo = value;
    }

}
