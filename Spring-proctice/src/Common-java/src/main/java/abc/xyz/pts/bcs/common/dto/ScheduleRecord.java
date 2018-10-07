/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.w3._2001.xmlschema.Adapter1;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleRecord")
@XmlRootElement(name = "ScheduleRecord", namespace = "scheduleRecord")
public class ScheduleRecord implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -2626329092939939080L;
    /** Date Format */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm");

    @XmlAttribute(name = "ID")
    private Long id;

    @XmlAttribute(name = "FLIGHT_CODE")
    private String flightCode;

    @XmlAttribute(name = "FLIGHT_NO")
    private String flightNo;

    @XmlAttribute(name = "DEP_AIRPORT_CODE")
    private String depAirportCode;

    @XmlAttribute(name = "ARR_AIRPORT_CODE")
    private String arrAirportCode;

    @XmlAttribute(name = "DEP_DATE")
    @XmlJavaTypeAdapter(Adapter1.class)
    @XmlSchemaType(name = "dateTime")
    private Calendar depDate;

    @XmlAttribute(name = "SCHEDULED_TYPE")
    private String scheduledType;

    @XmlAttribute(name = "SCAN_TYPE")
    private String scanType;

    @XmlAttribute(name = "SCHEDULE_SCAN_STATUS")
    private String scheduleScanStatus;

    @XmlAttribute(name = "SCHEDULED_DATETIME")
    @XmlJavaTypeAdapter(Adapter1.class)
    @XmlSchemaType(name = "dateTime")
    private Calendar scheduledDatetime;

    @XmlAttribute(name = "PRE_SCAN_ID")
    private Long preScanId;

    @XmlAttribute(name = "SCAN_DATETIME")
    @XmlJavaTypeAdapter(Adapter1.class)
    @XmlSchemaType(name = "dateTime")
    private Calendar scanDatetime;

    @XmlAttribute(name = "ERROR_CODE")
    private String errorCode;

    @XmlAttribute(name = "CANCELLED_IND")
    private String cancelledInd;

    private String recurrenceType;
    private Calendar startDatetime;
    private String scheduledTime;
    private String depDateType;
    private boolean mondayInd;
    private boolean tuesdayInd;
    private boolean wednesdayInd;
    private boolean thursdayInd;
    private boolean fridayInd;
    private boolean saturdayInd;
    private boolean sundayInd;

    private Long fltsFlightSegId;

    private String depTime;

    private List<String> timeIntervals;

    /** Indicator to specify, whether the repeat is for Specific flight or Series of Flight */
    private String repeatType;

    @XmlAttribute(name = "BOOKING_REF_NO_LIST")
    private String bookingRefNoList;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(final String flightCode) {
        this.flightCode = flightCode;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(final String flightNo) {
        this.flightNo = flightNo;
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

    public Calendar getDepDate() {
        return depDate;
    }

    public void setDepDate(final Calendar depDate) {
        this.depDate = depDate;
        if (depDate != null) {
            synchronized (DATE_FORMAT) {
                this.setDepTime(DATE_FORMAT.format(depDate.getTime()));
            }
        }
    }

    public String getScheduledType() {
        return scheduledType;
    }

    public void setScheduledType(final String scheduledType) {
        this.scheduledType = scheduledType;
    }

    public String getScheduleScanStatus() {
        return scheduleScanStatus;
    }

    public void setScheduleScanStatus(final String scheduleScanStatus) {
        this.scheduleScanStatus = scheduleScanStatus;
    }

    public Calendar getScheduledDatetime() {
        return scheduledDatetime;
    }

    public void setScheduledDatetime(final Calendar scheduledDatetime) {
        this.scheduledDatetime = scheduledDatetime;
    }

    public Long getPreScanId() {
        return preScanId;
    }

    public void setPreScanId(final Long preScanId) {
        this.preScanId = preScanId;
    }

    public Calendar getScanDatetime() {
        return scanDatetime;
    }

    public void setScanDatetime(final Calendar scanDatetime) {
        this.scanDatetime = scanDatetime;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the recurrenceType
     */
    public String getRecurrenceType() {
        return recurrenceType;
    }

    /**
     * @param recurrenceType the recurrenceType to set
     */
    public void setRecurrenceType(final String recurrenceType) {
        this.recurrenceType = recurrenceType;
    }

    /**
     * @return the startDatetime
     */
    public Calendar getStartDatetime() {
        return startDatetime;
    }

    /**
     * @param startDatetime the startDatetime to set
     */
    public void setStartDatetime(final Calendar startDatetime) {
        this.startDatetime = startDatetime;
        if (startDatetime != null) {
            synchronized (DATE_FORMAT) {
                this.setScheduledTime(DATE_FORMAT.format(startDatetime.getTime()));
            }
        }
    }

    /**
     * @return the scheduledTime
     */
    public String getScheduledTime() {
        return scheduledTime;
    }

    /**
     * @param scheduledTime the scheduledTime to set
     */
    public void setScheduledTime(final String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    /**
     * @return the depDateType
     */
    public String getDepDateType() {
        return depDateType;
    }

    /**
     * @param depDateType the depDateType to set
     */
    public void setDepDateType(final String depDateType) {
        this.depDateType = depDateType;
    }

    /**
     * @return the mondayInd
     */
    public boolean isMondayInd() {
        return mondayInd;
    }

    /**
     * @param mondayInd the mondayInd to set
     */
    public void setMondayInd(final String mondayInd) {
        this.mondayInd = getFlag(mondayInd);
    }

    /**
     * @return the tuesdayInd
     */
    public boolean isTuesdayInd() {
        return tuesdayInd;
    }

    /**
     * @param tuesdayInd the tuesdayInd to set
     */
    public void setTuesdayInd(final String tuesdayInd) {
        this.tuesdayInd = getFlag(tuesdayInd);
    }

    /**
     * @return the wednesdayInd
     */
    public boolean isWednesdayInd() {
        return wednesdayInd;
    }

    /**
     * @param wednesdayInd the wednesdayInd to set
     */
    public void setWednesdayInd(final String wednesdayInd) {
        this.wednesdayInd = getFlag(wednesdayInd);
    }

    /**
     * @return the thursdayInd
     */
    public boolean isThursdayInd() {
        return thursdayInd;
    }

    /**
     * @param thursdayInd the thursdayInd to set
     */
    public void setThursdayInd(final String thursdayInd) {
        this.thursdayInd = getFlag(thursdayInd);
    }

    /**
     * @return the fridayInd
     */
    public boolean isFridayInd() {
        return fridayInd;
    }

    /**
     * @param fridayInd the fridayInd to set
     */
    public void setFridayInd(final String fridayInd) {
        this.fridayInd = getFlag(fridayInd);
    }

    /**
     * @return the saturdayInd
     */
    public boolean isSaturdayInd() {
        return saturdayInd;
    }

    /**
     * @param saturdayInd the saturdayInd to set
     */
    public void setSaturdayInd(final String saturdayInd) {
        this.saturdayInd = getFlag(saturdayInd);
    }

    /**
     * @return the sundayInd
     */
    public boolean isSundayInd() {
        return sundayInd;
    }

    /**
     * @param sundayInd the sundayInd to set
     */
    public void setSundayInd(final String sundayInd) {
        this.sundayInd = getFlag(sundayInd);
    }

    /**
     *
     * @param booleanString
     * @return
     */
    private boolean getFlag(final String booleanString) {
        return "Y".equals(booleanString);
    }

    /**
     * @return the fltsFlightSegId
     */
    public Long getFltsFlightSegId() {
        return fltsFlightSegId;
    }

    /**
     * @param fltsFlightSegId the fltsFlightSegId to set
     */
    public void setFltsFlightSegId(final Long fltsFlightSegId) {
        this.fltsFlightSegId = fltsFlightSegId;
    }

    /**
     * @return the repeatType
     */
    public String getRepeatType() {
        return repeatType;
    }

    /**
     * @param repeatType the repeatType to set
     */
    public void setRepeatType(final String repeatType) {
        this.repeatType = repeatType;
    }

    public String getCancelledInd() {
        return cancelledInd;
    }

    public void setCancelledInd(final String cancelledInd) {
        this.cancelledInd = cancelledInd;
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(final String scanType) {
        this.scanType = scanType;
    }

    /**
     * @return the bookingRefNoList
     */
    public String getBookingRefNoList() {
        return bookingRefNoList;
    }

    /**
     * @param bookingRefNoList the bookingRefNoList to set
     */
    public void setBookingRefNoList(final String bookingRefNoList) {
        this.bookingRefNoList = bookingRefNoList;
    }

    /**
     * @return the depTime
     */
    public String getDepTime() {
        return depTime;
    }

    /**
     * @param depTime the depTime to set
     */
    public void setDepTime(final String depTime) {
        this.depTime = depTime;
    }

    /**
     * @return the timeIntervals
     */
    public void setTimeInterval(final String timeInterval) {
        if (timeInterval != null) {
            final String[] timeIntervalArr = timeInterval.split(",");

            timeIntervals = new ArrayList<String>();
            for (final String string : timeIntervalArr) {
                timeIntervals.add(string);
            }
        }
    }

    /**
     * @return the timeIntervals
     */
    public List<String> getTimeIntervals() {
        return timeIntervals;
    }

    /**
     * @param timeIntervals the timeIntervals to set
     */
    public void setTimeIntervals(final List<String> timeIntervals) {
        this.timeIntervals = timeIntervals;
    }

    /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation
     * of this object.
     */
    @Override
    public String toString() {
        final StringBuilder retValue = new StringBuilder();

        retValue.append("ScheduleRecord [ ");
        retValue.append("\nid = ").append(this.id);
        retValue.append("\nflightCode = ").append(this.flightCode);
        retValue.append("\nflightNo = ").append(this.flightNo);
        retValue.append("\ndepAirportCode = ").append(this.depAirportCode);
        retValue.append("\narrAirportCode = ").append(this.arrAirportCode);
        retValue.append("\ndepDate = ").append(this.depDate);
        retValue.append("\nscheduledType = ").append(this.scheduledType);
        retValue.append("\nscanType = ").append(this.scanType);
        retValue.append("\nscheduleScanStatus = ").append(this.scheduleScanStatus);
        retValue.append("\nscheduledDatetime = ").append(this.scheduledDatetime);
        retValue.append("\npreScanId = ").append(this.preScanId);
        retValue.append("\nscanDatetime = ").append(this.scanDatetime);
        retValue.append("\nerrorCode = ").append(this.errorCode);
        retValue.append("\ncancelledInd = ").append(this.cancelledInd);
        retValue.append("\nrecurrenceType = ").append(this.recurrenceType);
        retValue.append("\nstartDatetime = ").append(this.startDatetime);
        retValue.append("\nscheduledTime = ").append(this.scheduledTime);
        retValue.append("\ndepDateType = ").append(this.depDateType);
        retValue.append("\nmondayInd = ").append(this.mondayInd);
        retValue.append("\ntuesdayInd = ").append(this.tuesdayInd);
        retValue.append("\nwednesdayInd = ").append(this.wednesdayInd);
        retValue.append("\nthursdayInd = ").append(this.thursdayInd);
        retValue.append("\nfridayInd = ").append(this.fridayInd);
        retValue.append("\nsaturdayInd = ").append(this.saturdayInd);
        retValue.append("\nsundayInd = ").append(this.sundayInd);
        retValue.append("\nfltsFlightSegId = ").append(this.fltsFlightSegId);
        retValue.append("\ndepTime = ").append(this.depTime);
        retValue.append("\ntimeIntervals = ").append(this.timeIntervals);
        retValue.append("\nrepeatType = ").append(this.repeatType);
        retValue.append("\nbookingRefNoList = ").append(this.bookingRefNoList);
        retValue.append("\n]");

        return retValue.toString();
    }

}
