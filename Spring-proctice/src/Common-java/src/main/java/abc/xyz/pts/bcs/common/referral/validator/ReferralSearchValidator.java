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
package abc.xyz.pts.bcs.common.referral.validator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import abc.xyz.pts.bcs.common.dao.AirportValidationDAO;
import abc.xyz.pts.bcs.common.referral.web.command.ReferralSearchCommand;
import abc.xyz.pts.bcs.common.util.CalendarUtils;
import abc.xyz.pts.bcs.common.web.validation.FieldValidationUtil;

public class ReferralSearchValidator implements Validator {

    private AirportValidationDAO airportValidationDAO;
    private MessageSourceAccessor messageSourceAccessor;

    private static final String ARRIVAL_DATE_RANGE_FROM = "arrivalDateRangeFrom";
    private static final String ARRIVAL_TIME_RANGE_FROM = "arrivalTimeRangeFrom";
    private static final String DEPARTURE_DATE_RANGE_FROM = "departureDateRangeFrom";
    private static final String DEPARTURE_TIME_RANGE_FROM = "departureTimeRangeFrom";
    private static final String REFERRAL_GEN_DATE_FROM = "referralGenDateFrom";

    private static final String ERRORS_DATERANGE_ARRIVAL_CHRON_ORDER = "errors.daterange.arrival.chronologicalorder";
    private static final String ERRORS_DATERANGE_CHRON_ORDER = "errors.daterange.chronologicalorder";
    private static final String ERRORS_DATERANGE_ARRIVAL_EQUAL = "errors.daterange.arrival.equal";
    private static final String ERRORS_DATERANGE_ARRIVAL_FROM_REQD = "errors.daterange.arrival.from.required";
    private static final String ERRORS_DATERANGE_DEP_CHRON_ORDER = "errors.daterange.departure.chronologicalorder";
    private static final String ERRORS_DATERANGE_DEPARTURE_EQUAL = "errors.daterange.departure.equal";
    private static final String ERRORS_TIMERANGE_ARRIVAL_EQUAL = "errors.timerange.arrival.equal";
    private static final String ERRORS_TIMERANGE_DEPARTURE_EQUAL = "errors.timerange.departure.equal";
    private static final String ERRORS_DATERANGE_DEPARTURE_FROM_REQD = "errors.daterange.departure.from.required";
    private static final String ERRORS_DATERANGE_REFERRAL_CHRON_ORDER = "errors.daterange.referral.chronologicalorder";
    private static final String ERRORS_DATERANGE_REFERRAL_EQUAL = "errors.daterange.referral.equal";
    private static final String ERRORS_DATERANGE_REFERRAL_FROM_REQD = "errors.daterange.referral.from.required";
    private static final String DATE_OF_BIRTH = "dateOfBirth";
    private static final String ERRORS_DATE_DOB = "error.birth.date.too.high";

    /*
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    @SuppressWarnings("rawtypes")
    public boolean supports(final Class clazz) {
        return (clazz.equals(ReferralSearchValidator.class));
    }

    /*
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     * org.springframework.validation.Errors)
     */
    @Override
    public void validate(final Object target, final Errors errors) {

        final ReferralSearchCommand referralSearch = (ReferralSearchCommand) target;

        checkValidDateAndTimeRanges(errors, referralSearch);
        checkValidDateOfBirth(errors, referralSearch.getDateOfBirth());
        FieldValidationUtil.checkValidForenameWildcard(errors, referralSearch.getForenames());
        FieldValidationUtil.checkValidLastNameWildcard(errors, referralSearch.getLastName());
        final int departureAirportErrorCount = errors.getFieldErrorCount("departureAirport");
        if (departureAirportErrorCount == 0 && StringUtils.isNotBlank(referralSearch.getDepartureAirport())
                && !airportValidationDAO.isAirportValid(referralSearch.getDepartureAirport())) {
            errors.rejectValue("departureAirport", "errors.invalid.airport.code",
                    new Object[] { messageSourceAccessor.getMessage("airport.departure") }, "");
        }

        final int arrivalAirportErrorCount = errors.getFieldErrorCount("arrivalAirport");
        if (arrivalAirportErrorCount == 0 && StringUtils.isNotBlank(referralSearch.getArrivalAirport())
                && !airportValidationDAO.isAirportValid(referralSearch.getArrivalAirport())) {
            errors.rejectValue("arrivalAirport", "errors.invalid.airport.code",
                    new Object[] { messageSourceAccessor.getMessage("airport.arrival") }, "");
        }

        // only validate route airports, if the route is set and format is valid
        if (!errors.hasFieldErrors("route") && StringUtils.isNotBlank(referralSearch.getRoute())) {
            final String[] legs = referralSearch.getRoute().split("-");
            if (legs != null && legs.length > 0) {
                for (final String leg : legs) {
                    if (!airportValidationDAO.isAirportValid(leg)) {
                        errors.rejectValue("route", "errors.invalid.flight.route", null, "");
                        return;
                    }
                }
            }
        }
    }

    public static void checkValidDateAndTimeRanges(final Errors errors, final ReferralSearchCommand referralSearch) {
        final String departureTimeRangeFrom = referralSearch.getDepartureTimeRangeFrom();
        final String departureTimeRangeTo = referralSearch.getDepartureTimeRangeTo();
        final Calendar departureDateRangeFrom = referralSearch.getDepartureDateRangeFrom();
        final Calendar departureDateRangeTo = referralSearch.getDepartureDateRangeTo();

        final String arrivalTimeRangeFrom = referralSearch.getArrivalTimeRangeFrom();
        final String arrivalTimeRangeTo = referralSearch.getArrivalTimeRangeTo();
        final Calendar arrivalDateRangeFrom = referralSearch.getArrivalDateRangeFrom();
        final Calendar arrivalDateRangeTo = referralSearch.getArrivalDateRangeTo();

        final Calendar referralGenDateRangeFrom = referralSearch.getReferralGenDateFrom();
        final Calendar referralGenDateRangeTo = referralSearch.getReferralGenDateTo();

        // ******
        // ** Departure
        // ******

        //Check Both arrival and Departure Airports are the same
        FieldValidationUtil.checkArrDepAirportCodeAreSame(errors, referralSearch.getArrivalAirport(),
                referralSearch.getDepartureAirport());

        // ** Date
        if (departureDateRangeFrom != null && departureDateRangeTo != null) {
            if (departureDateRangeFrom.compareTo(departureDateRangeTo) > 0) {
                errors.rejectValue(DEPARTURE_DATE_RANGE_FROM, ERRORS_DATERANGE_DEP_CHRON_ORDER);
            } else if (departureDateRangeFrom.compareTo(departureDateRangeTo) == 0) {
                errors.rejectValue(DEPARTURE_DATE_RANGE_FROM, ERRORS_DATERANGE_DEPARTURE_EQUAL);
            }
        } else if (departureDateRangeFrom == null && departureDateRangeTo != null) {
            errors.rejectValue(DEPARTURE_DATE_RANGE_FROM, ERRORS_DATERANGE_DEPARTURE_FROM_REQD);
        }

        // ** Time
        if (departureTimeRangeFrom != null && departureTimeRangeTo != null
                && departureTimeRangeFrom.equals(departureTimeRangeTo)) {
            errors.rejectValue(DEPARTURE_TIME_RANGE_FROM, ERRORS_TIMERANGE_DEPARTURE_EQUAL);
        }

        // ******
        // ** Arrival
        // ******

        // ** Date
        if (arrivalDateRangeFrom != null && arrivalDateRangeTo != null) {
            if (arrivalDateRangeFrom.compareTo(arrivalDateRangeTo) > 0) {
                errors.rejectValue(ARRIVAL_DATE_RANGE_FROM, ERRORS_DATERANGE_ARRIVAL_CHRON_ORDER);
            } else if (arrivalDateRangeFrom.compareTo(arrivalDateRangeTo) == 0) {
                errors.rejectValue(ARRIVAL_DATE_RANGE_FROM, ERRORS_DATERANGE_ARRIVAL_EQUAL);
            }
        } else if (arrivalDateRangeFrom == null && arrivalDateRangeTo != null) {
            errors.rejectValue(ARRIVAL_DATE_RANGE_FROM, ERRORS_DATERANGE_ARRIVAL_FROM_REQD);
        }

        if (departureDateRangeFrom != null && arrivalDateRangeFrom != null
                && !CalendarUtils.isDiffChronological(departureDateRangeFrom, arrivalDateRangeFrom)) {
            errors.rejectValue(ARRIVAL_DATE_RANGE_FROM, ERRORS_DATERANGE_CHRON_ORDER);
         }


        // ** Time
        if (arrivalTimeRangeFrom != null && arrivalTimeRangeTo != null
                && arrivalTimeRangeFrom.equals(arrivalTimeRangeTo)) {
            errors.rejectValue(ARRIVAL_TIME_RANGE_FROM, ERRORS_TIMERANGE_ARRIVAL_EQUAL);
        }


        // ******
        // * Referral
        // ******

        // ** Date
        if (referralGenDateRangeFrom != null && referralGenDateRangeTo != null) {
            if (referralGenDateRangeFrom.compareTo(referralGenDateRangeTo) > 0) {
                errors.rejectValue(REFERRAL_GEN_DATE_FROM, ERRORS_DATERANGE_REFERRAL_CHRON_ORDER);
            } else if (referralGenDateRangeFrom.compareTo(referralGenDateRangeTo) == 0) {
                errors.rejectValue(REFERRAL_GEN_DATE_FROM, ERRORS_DATERANGE_REFERRAL_EQUAL);
            }
        } else if (referralGenDateRangeFrom == null && referralGenDateRangeTo != null) {
            errors.rejectValue(REFERRAL_GEN_DATE_FROM, ERRORS_DATERANGE_REFERRAL_FROM_REQD);
        }

    }

    public static void checkValidDateOfBirth(final Errors errors, final Calendar dateOfBirth) {
        if (dateOfBirth != null) {
            final Calendar currentDate = new GregorianCalendar();
            currentDate.setTime(new Date());

            if (dateOfBirth.compareTo(currentDate) > 0) {
                errors.rejectValue(DATE_OF_BIRTH, ERRORS_DATE_DOB);
            }
        }
    }

    public AirportValidationDAO getAirportValidationDAO() {
        return airportValidationDAO;
    }

    public void setAirportValidationDAO(final AirportValidationDAO airportValidationDAO) {
        this.airportValidationDAO = airportValidationDAO;
    }

    public void setMessageSource(final MessageSource messageSource) {
        this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

}
