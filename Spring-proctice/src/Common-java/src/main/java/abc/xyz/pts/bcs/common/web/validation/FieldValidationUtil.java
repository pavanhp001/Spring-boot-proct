/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.web.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

/**
 * User: vshiligin Date: 09-Jun-2008
 */
public final class FieldValidationUtil {

    private static final String ERRORS_NAME_WILDCARD = "errors.name.minchar.wildcard";
    private static final String ERRORS_ARR_DEP_AIRPORTS_ARE_SAME = "errors.depairport.arrairport.same";

    private FieldValidationUtil() {

    }

    // Time methods
    // Time format assumed: hh:mm
    public static boolean isEarlierTime(final String timeToValidate, final String timeToValidateAgainst) {
        Integer timeFromInt;
        Integer timeToInt;
        try {
            timeFromInt = new Integer(StringUtils.remove(timeToValidate, ':'));
            timeToInt = new Integer(StringUtils.remove(timeToValidateAgainst, ':'));
        } catch (final NumberFormatException e) {
            return false;
        }

        return timeFromInt.compareTo(timeToInt) < 0;
    }

    public static boolean isLaterTime(final String timeToValidate, final String timeToValidateAgainst) {
        Integer timeFromInt;
        Integer timeToInt;
        try {
            timeFromInt = new Integer(StringUtils.remove(timeToValidate, ':'));
            timeToInt = new Integer(StringUtils.remove(timeToValidateAgainst, ':'));
        } catch (final NumberFormatException e) {
            return false;
        }

        return timeFromInt.compareTo(timeToInt) > 0;
    }

    public static boolean isSameTime(final String timeToValidate, final String timeToValidateAgainst) {
        Integer timeFromInt;
        Integer timeToInt;
        try {
            timeFromInt = new Integer(StringUtils.remove(timeToValidate, ':'));
            timeToInt = new Integer(StringUtils.remove(timeToValidateAgainst, ':'));
        } catch (final NumberFormatException e) {
            return false;
        }

        return timeFromInt.compareTo(timeToInt) == 0;
    }

    private static int compareDates(final String dateToValidate, final String dateToValidateAgainst, final String datePattern)
        throws ParseException {

        final SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        dateFormat.setLenient(false);

        final Calendar date1 = Calendar.getInstance();
        date1.setTime(dateFormat.parse(dateToValidate.trim()));

        final Calendar date2 = Calendar.getInstance();
        date2.setTime(dateFormat.parse(dateToValidateAgainst.trim()));

        if (date1.before(date2)) {
            return -1;
        } else if (date1.equals(date2)) {
            return 0;
        } else {
            return 1;
        }
    }

    // date format assumed: dd-MM-yyyy
    public static boolean isEarlierDate(final String dateToValidate, final String dateToValidateAgainst)
        throws ParseException {
        if (compareDates(dateToValidate, dateToValidateAgainst, "dd-MM-yyyy") == -1) {
            return true;
        } else {
            return false;
        }
    }

    // date format assumed: dd-MM-yyyy
    public static boolean isEqualDate(final String dateToValidate, final String dateToValidateAgainst)
        throws ParseException {
        if (compareDates(dateToValidate, dateToValidateAgainst, "dd-MM-yyyy") == 0) {
            return true;
        } else {
            return false;
        }
    }

    // date format assumed: dd-MM-yyyy
    public static boolean isLaterDate(final String dateToValidate, final String dateToValidateAgainst)
        throws ParseException {
        if (compareDates(dateToValidate, dateToValidateAgainst, "dd-MM-yyyy") == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isLaterDate(final String dateToValidate, final String dateToValidateAgainst, final String datePattern)
            throws ParseException {
        if (compareDates(dateToValidate, dateToValidateAgainst, datePattern) == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validates the wild card character used in the provided last name field
     * <p>
     * This method verifies the position of the wild card in the entered string
     * and whether the correct number of characters appear before and after the
     * wild card. It is valid for last names only
     *
     * @param errors
     *            Errors to add to on failure of validation rules.
     * @param name
     *            The string that is entered into the name field
     */
    public static void checkValidLastNameWildcard(final Errors errors, final String name) {

        if (!StringUtils.isBlank(name)) {
            if (!Pattern.matches("(^[^*.]{3,80}\\*?$|^[^*.]{1,80}$)?", name)) {
                errors.rejectValue("lastName", ERRORS_NAME_WILDCARD);
            }
        }
    }

    /**
     * Validates the wild card character used in the provided given name field
     * <p>
     * This method verifies the position of the wild card in the entered string
     * and whether the correct number of characters appear before and after the
     * wild card. It is valid for given names only
     *
     * @param errors
     *            Errors to add to on failure of validation rules.
     * @param name
     *            The string that is entered into the name field
     */
    public static void checkValidForenameWildcard(final Errors errors, final String name) {

        if (!StringUtils.isBlank(name)) {
            if (!Pattern.matches("(^[^*.]{1,160}\\*?$|^[^*.]{1,160}$)?", name)) {
                errors.rejectValue("forenames", ERRORS_NAME_WILDCARD);
            }
        }
    }

    /**
     * This method checks both ArrivalAirport and Departure Airports are Same
     * @param errors
     * @param arrAirport
     * @param depAirport
     */

    public static void checkArrDepAirportCodeAreSame(final Errors errors, final String arrivalAirport, final String departureAirport) {
        if(StringUtils.isNotEmpty(departureAirport) && StringUtils.equals(arrivalAirport, departureAirport)){
            errors.rejectValue("departureAirport", ERRORS_ARR_DEP_AIRPORTS_ARE_SAME);
            errors.rejectValue("arrivalAirport", "","");
        }
    }
}
