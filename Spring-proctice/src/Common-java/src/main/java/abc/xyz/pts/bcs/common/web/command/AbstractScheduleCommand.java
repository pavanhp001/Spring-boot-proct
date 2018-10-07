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
package abc.xyz.pts.bcs.common.web.command;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import abc.xyz.pts.bcs.common.audit.annotation.AuditableBeanProperty;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableExpressionBeanProperties;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableExpressionBeanProperty;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.audit.business.Parameter;
import abc.xyz.pts.bcs.common.dao.utils.DateStringUtils;
import abc.xyz.pts.bcs.common.dto.ScheduleRecord;

/**
 * @author M1001798
 *
 */
public abstract class AbstractScheduleCommand implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4197412587249045567L;

    @AuditableBeanProperty(key = Parameter.CARRIER_CODE, ignoreEvent = { Event.EDIT_SCHEDULE, Event.CANCEL_SCHEDULE })
    private String flightCarrierCode;
    @AuditableBeanProperty(key = Parameter.FLIGHT_NUMBER, ignoreEvent = { Event.EDIT_SCHEDULE, Event.CANCEL_SCHEDULE })
    private String flightNumber;
    @AuditableBeanProperty(key = Parameter.DEP_AIRPORT_CODE, ignoreEvent = { Event.EDIT_SCHEDULE, Event.CANCEL_SCHEDULE })
    private String departureAirport;
    @AuditableBeanProperty(key = Parameter.ARR_AIRPORT_CODE, ignoreEvent = { Event.EDIT_SCHEDULE, Event.CANCEL_SCHEDULE })
    private String arrivalAirport;
    @AuditableBeanProperty(key = Parameter.DEPARTURE_DATE, ignoreEvent = { Event.EDIT_SCHEDULE, Event.CANCEL_SCHEDULE })
    private Calendar departureDate;
    @AuditableBeanProperty(key = Parameter.SCHEDULE_TYPE, ignoreEvent = { Event.EDIT_SCHEDULE, Event.CANCEL_SCHEDULE })
    private String scheduledType;
    @AuditableBeanProperty(key = Parameter.SCHEDULE_DATE, ignoreEvent = { Event.EDIT_SCHEDULE, Event.CANCEL_SCHEDULE })
    private Calendar scheduledDate;
    @AuditableBeanProperty(key = Parameter.SCHEDULE_TIME, ignoreEvent = { Event.EDIT_SCHEDULE, Event.CANCEL_SCHEDULE })
    private String scheduledTime;
    private String requestType;

    /* Variables to support the Error Message in the Screen. */
    private String scheduledTimeLessThanCurrent;
    private String startTimeLessThanCurrent;
    private String scheduledTimeDepDateMoreThanConfiguredDay;
    private String depAptArrAptSame;

    /* Variables for Recurrence type schedules */
    private String recurrenceDepDate = "S";
    @AuditableBeanProperty(key = Parameter.RECURRENCE_TYPE, ignoreEvent = { Event.EDIT_SCHEDULE, Event.CANCEL_SCHEDULE })
    private String recurrenceType;
    @AuditableBeanProperty(key = Parameter.START_DATE, ignoreEvent = { Event.EDIT_SCHEDULE, Event.CANCEL_SCHEDULE })
    private Calendar startDate;

    @AuditableBeanProperty(key = Parameter.WEEK_DAYS, ignoreEvent = { Event.EDIT_SCHEDULE, Event.CANCEL_SCHEDULE })
    private String weekDays;
    private String rowClicked;
    private String scanType;
    /*
     * Indicators for Days
     */
    private boolean sundayFlag;
    private boolean mondayFlag;
    private boolean tuesdayFlag;
    private boolean wednesdayFlag;
    private boolean thursdayFlag;
    private boolean fridayFlag;
    private boolean saturdayFlag;

    private Long preScanId;

    private Long id;

    @AuditableExpressionBeanProperties(properties = {
            @AuditableExpressionBeanProperty(key = Parameter.CARRIER_CODE, expression = "selectedRecord.flightCode"),
            @AuditableExpressionBeanProperty(key = Parameter.FLIGHT_NUMBER, expression = "selectedRecord.flightNo"),
            @AuditableExpressionBeanProperty(key = Parameter.DEP_AIRPORT_CODE, expression = "selectedRecord.depAirportCode"),
            @AuditableExpressionBeanProperty(key = Parameter.ARR_AIRPORT_CODE, expression = "selectedRecord.arrAirportCode"),
            @AuditableExpressionBeanProperty(key = Parameter.DEPARTURE_DATE, expression = "selectedRecord.depDate"),
            @AuditableExpressionBeanProperty(key = Parameter.SCHEDULE_TYPE, expression = "selectedRecord.scheduledType"),
            @AuditableExpressionBeanProperty(key = Parameter.SCAN_DATE_TIME, expression = "selectedRecord.scanDatetime"),
            @AuditableExpressionBeanProperty(key = Parameter.SCAN_STATUS, expression = "selectedRecord.scheduleScanStatus"),
            @AuditableExpressionBeanProperty(key = Parameter.SCHEDULE_ERROR_REMARK, expression = "selectedRecord.errorCode")})
    private ScheduleRecord selectedRecord;

    private String cancelledInd;

    /** Indicator to specify, whether the repeat is for Specific flight or Series of Flight */
    private String repeatType;

    private List<String> selectedTimeInterval;

    private String depTime;

    /**
     * @return the flightCarrierCode
     */
    public String getFlightCarrierCode() {
        return flightCarrierCode;
    }

    /**
     * @param flightCarrierCode the flightCarrierCode to set
     */
    public void setFlightCarrierCode(final String flightCarrierCode) {
        this.flightCarrierCode = flightCarrierCode;
    }

    /**
     * @return the flightNumber
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * @param flightNumber the flightNumber to set
     */
    public void setFlightNumber(final String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * @return the departureAirport
     */
    public String getDepartureAirport() {
        return departureAirport;
    }

    /**
     * @param departureAirport the departureAirport to set
     */
    public void setDepartureAirport(final String departureAirport) {
        this.departureAirport = departureAirport;
    }

    /**
     * @return the arrivalAirport
     */
    public String getArrivalAirport() {
        return arrivalAirport;
    }

    /**
     * @param arrivalAirport the arrivalAirport to set
     */
    public void setArrivalAirport(final String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    /**
     * @return the departureDate
     */
    public Calendar getDepartureDate() {
        return departureDate;
    }

    /**
     * @param departureDate the departureDate to set
     */
    public void setDepartureDate(final Calendar departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * @return the scheduledType
     */
    public String getScheduledType() {
        return scheduledType;
    }

    /**
     * @param scheduledType the scheduledType to set
     */
    public void setScheduledType(final String scheduledType) {
        this.scheduledType = scheduledType;
    }

    /**
     * @return the scheduledDate
     */
    public Calendar getScheduledDate() {
        return scheduledDate;
    }

    /**
     * @param scheduledDate the scheduledDate to set
     */
    public void setScheduledDate(final Calendar scheduledDate) {
        this.scheduledDate = scheduledDate;
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

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(final String requestType) {
        this.requestType = requestType;
    }

    /**
     * @return the scheduledTimeLessThanCurrent
     */
    public String getScheduledTimeLessThanCurrent() {
        return scheduledTimeLessThanCurrent;
    }

    /**
     * @param scheduledTimeLessThanCurrent the scheduledTimeLessThanCurrent to set
     */
    public void setScheduledTimeLessThanCurrent(final String scheduledTimeLessThanCurrent) {
        this.scheduledTimeLessThanCurrent = scheduledTimeLessThanCurrent;
    }

    public String getScheduledTimeDepDateMoreThanConfiguredDay() {
        return scheduledTimeDepDateMoreThanConfiguredDay;
    }

    public void setScheduledTimeDepDateMoreThanConfiguredDay(final String scheduledTimeDepDateMoreThanConfiguredDay) {
        this.scheduledTimeDepDateMoreThanConfiguredDay = scheduledTimeDepDateMoreThanConfiguredDay;
    }

    /**
     * @return the depAptArrAptSame
     */
    public String getDepAptArrAptSame() {
        return depAptArrAptSame;
    }

    /**
     * @param depAptArrAptSame the depAptArrAptSame to set
     */
    public void setDepAptArrAptSame(final String depAptArrAptSame) {
        this.depAptArrAptSame = depAptArrAptSame;
    }

    /**
     * @return the recurrenceDepDate
     */
    public String getRecurrenceDepDate() {
        return recurrenceDepDate;
    }

    /**
     * @param recurrenceDepDate the recurrenceDepDate to set
     */
    public void setRecurrenceDepDate(final String recurrenceDepDate) {
        this.recurrenceDepDate = recurrenceDepDate;
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
     * @return the startDate
     */
    public Calendar getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(final Calendar startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the sundayFlag
     */
    public boolean isSundayFlag() {
        return sundayFlag;
    }

    /**
     * @param sundayFlag the sundayFlag to set
     */
    public void setSundayFlag(final boolean sundayFlag) {
        this.sundayFlag = sundayFlag;
    }

    /**
     * @return the mondayFlag
     */
    public boolean isMondayFlag() {
        return mondayFlag;
    }

    /**
     * @param mondayFlag the mondayFlag to set
     */
    public void setMondayFlag(final boolean mondayFlag) {
        this.mondayFlag = mondayFlag;
    }

    /**
     * @return the tuesdayFlag
     */
    public boolean isTuesdayFlag() {
        return tuesdayFlag;
    }

    /**
     * @param tuesdayFlag the tuesdayFlag to set
     */
    public void setTuesdayFlag(final boolean tuesdayFlag) {
        this.tuesdayFlag = tuesdayFlag;
    }

    /**
     * @return the wednesdayFlag
     */
    public boolean isWednesdayFlag() {
        return wednesdayFlag;
    }

    /**
     * @param wednesdayFlag the wednesdayFlag to set
     */
    public void setWednesdayFlag(final boolean wednesdayFlag) {
        this.wednesdayFlag = wednesdayFlag;
    }

    /**
     * @return the thursdayFlag
     */
    public boolean isThursdayFlag() {
        return thursdayFlag;
    }

    /**
     * @param thursdayFlag the thursdayFlag to set
     */
    public void setThursdayFlag(final boolean thursdayFlag) {
        this.thursdayFlag = thursdayFlag;
    }

    /**
     * @return the fridayFlag
     */
    public boolean isFridayFlag() {
        return fridayFlag;
    }

    /**
     * @param fridayFlag the fridayFlag to set
     */
    public void setFridayFlag(final boolean fridayFlag) {
        this.fridayFlag = fridayFlag;
    }

    /**
     * @return the saturdayFlag
     */
    public boolean isSaturdayFlag() {
        return saturdayFlag;
    }

    /**
     * @param saturdayFlag the saturdayFlag to set
     */
    public void setSaturdayFlag(final boolean saturdayFlag) {
        this.saturdayFlag = saturdayFlag;
    }

    public String getStartTimeLessThanCurrent() {
        return startTimeLessThanCurrent;
    }

    public void setStartTimeLessThanCurrent(final String startTimeLessThanCurrent) {
        this.startTimeLessThanCurrent = startTimeLessThanCurrent;
    }

    public Long getPreScanId() {
        return preScanId;
    }

    public void setPreScanId(final Long preScanId) {
        this.preScanId = preScanId;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public ScheduleRecord getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(final ScheduleRecord selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    /**
     *
     * @return
     */
    public String getWeekDays() {
        final StringBuilder builder = new StringBuilder();

        if (mondayFlag) {
            builder.append("MON");
        }

        if (tuesdayFlag) {
            if (builder.length() > 0) {
                builder.append(", ");
            }

            builder.append("TUE");
        }

        if (wednesdayFlag) {
            if (builder.length() > 0) {
                builder.append(", ");
            }

            builder.append("WED");
        }

        if (thursdayFlag) {
            if (builder.length() > 0) {
                builder.append(", ");
            }

            builder.append("THU");
        }

        if (fridayFlag) {
            if (builder.length() > 0) {
                builder.append(", ");
            }

            builder.append("FRI");
        }

        if (saturdayFlag) {
            if (builder.length() > 0) {
                builder.append(", ");
            }

            builder.append("SAT");
        }

        if (sundayFlag) {
            if (builder.length() > 0) {
                builder.append(", ");
            }

            builder.append("SUN");
        }

        return builder.toString();
    }

    public ScheduleRecord copyTo() {
        final ScheduleRecord scheduleRecord =  new ScheduleRecord();
        scheduleRecord.setId(getId());
        scheduleRecord.setFlightCode(getFlightCarrierCode());
        scheduleRecord.setFlightNo(getFlightNumber());
        scheduleRecord.setDepAirportCode(getDepartureAirport());
        scheduleRecord.setArrAirportCode(getArrivalAirport());
        if (getScheduledDate() != null) {
            scheduleRecord.setScheduledDatetime(
                    DateStringUtils.getCalendarFromDateTime(getScheduledDate(), getScheduledTime()));
        }
        scheduleRecord.setDepDate(getDepartureDate());
        scheduleRecord.setScheduledType(getScheduledType());
        scheduleRecord.setPreScanId(getPreScanId());
        scheduleRecord.setScanType(getScanType());
        return scheduleRecord;
    }

    public String getCancelledInd() {
        return cancelledInd;
    }

    public void setCancelledInd(final String cancelledInd) {
        this.cancelledInd = cancelledInd;
    }

    public String getRowClicked() {
        return rowClicked;
    }

    public void setRowClicked(final String rowClicked) {
        this.rowClicked = rowClicked;
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(final String scanType) {
        this.scanType = scanType;
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

    /**
     * @return the selectedTimeInterval
     */
    public List<String> getSelectedTimeInterval() {
        return selectedTimeInterval;
    }

    /**
     * @param selectedTimeInterval the selectedTimeInterval to set
     */
    public void setSelectedTimeInterval(final List<String> selectedTimeInterval) {
        this.selectedTimeInterval = selectedTimeInterval;
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
}
