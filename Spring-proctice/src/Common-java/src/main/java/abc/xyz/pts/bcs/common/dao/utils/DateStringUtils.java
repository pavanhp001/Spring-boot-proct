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
package abc.xyz.pts.bcs.common.dao.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Utilities to check and convert Date and String values.
 */
public final class DateStringUtils {

    private static final Logger LOG = Logger.getLogger(DateStringUtils.class);

    private DateStringUtils() {
    }
    /**
     * This method convert the date from XML Schema ( in message) into dd-MM-yyyy HH:mm format
     * @param schemaDate
     * @return
     */
    public static String getStringDateFromSchemaDate(final String schemaDate){
        if(schemaDate == null){
            return null;
        }
        final DateTime dateTime = new DateTime(schemaDate);
        final DateTimeFormatter fmt = DateTimeFormat.forPattern(Constants.DATE_FORMAT);
        return dateTime.toString(fmt);
    }

    /**
     * Return Date from a string without causing an Exception. null is returned for an invalid date.
     *
     * @param dateString
     * @return Date from dateString or null
     */
    public static Date getDateFromString(final String dateString, final String dateFormat) {
        if (isNullValue(dateString)) {
            return null;
        }
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        try {
            return simpleDateFormat.parse(dateString);

        } catch (final ParseException e) {
            // Should have been validated already
            LOG.debug(e);
            return null;
        }
    }

    /**
     * Return Date from a string without causing an Exception. null is returned for an invalid date.
     *
     * @param dateString
     * @return Date from dateString or null
     */
    public static Date getDateFromString(final String dateString) {
        return getDateFromString(dateString, Constants.SIMPLE_DATE_FORMAT);
    }

    /**
     * Return String representation of the date part of a Date.
     *
     * @param date
     *            to format
     * @return String or null
     */
    public static String getStringFromDate(final Date date) {
        return getStringFromDate(date, Constants.SIMPLE_DATE_FORMAT);
    }

    public static String getStringFromDate(final Date date, final String dateFormat) {
        if (date == null) {
            return "";
        }
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(date);
    }

    /**
     * Return String representation of the time part of a Date.
     *
     * @param date
     *            to format
     * @return String or null
     */
    public static String getStringFromTime(final Date date) {
        return getStringFromTime(date, Constants.TIME_FORMAT);
    }

    /**
     * Return String representation of the time part of a Date.
     *
     * @param date
     *            to format
     * @return String or null
     */
    public static String getStringFromTime(final Date date, final String timeFormat) {
        if (date == null) {
            return null;
        }
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
        return simpleDateFormat.format(date);
    }

    /**
     * Return String representation of the date and time parts of a Date.
     *
     * @param date
     *            to format
     * @return String or null
     */
    public static String getStringFromDateTime(final Date date) {
        final StringBuffer sb = new StringBuffer();
        final String dateString = getStringFromDate(date);
        final String timeString = getStringFromTime(date);
        if (dateString != null && dateString.length() > 0) {
            sb.append(dateString);
            sb.append(" - ");
        }
        if (timeString != null) {
            sb.append(timeString);
        }
        return sb.toString();
    }

    /**
     * Return Date\time from a string. null is returned for an invalid date\time.
     *
     * @param dateString
     *            containing the date
     * @param timeString
     *            containing the time
     * @return Date from dateString or null
     */
    public static Date getDateTimeFromStrings(final String dateString, final String timeString) {
        final Date date = getDateFromString(dateString);
        if (date == null || timeString == null || "".equals(timeString.trim())) {
            return null;
        }
        // convert the time to string
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.TIME_FORMAT);
        try {
            final Calendar dateTime = new GregorianCalendar();
            dateTime.clear();
            dateTime.setTime(date);
            final Date time = simpleDateFormat.parse(timeString);
            final Calendar timeCal = new GregorianCalendar();
            timeCal.setTime(time);
            dateTime.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
            dateTime.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
            return dateTime.getTime();
        } catch (final ParseException e) {
            // Should have been validated already
            LOG.debug(e);
        }
        return null;
    }

    /**
     * Replaces * and ? characters to % and _ for db queries. Returns null if value is empty string
     *
     * @param value
     *            to check
     * @return String or null
     */
    public static String replaceWildCards(final String value) {
        if (isNullValue(value)) {
            return null;
        }
        String ret = value.replaceAll("\\?", "_");
        ret = ret.replaceAll("\\*", "%");

        return ret;
    }

    /**
     * Return null or value. Values just contaning spaces or '-' return null.
     *
     * @param value
     *            to check
     * @return null or value
     */
    public static String getStringValue(final String value) {
        if (isNullValue(value)) {
            return null;
        }
        return value;
    }

    /**
     * Return true if value is null value. Values just containing spaces or a '-' return null.
     *
     * @param value
     *            to check
     * @return true if null value
     */
    private static boolean isNullValue(final String value) {
        if (value == null || "".equals(value.trim()) || "-".equals(value)) {
            return true;
        }
        return false;
    }

    /**
     * Return null or new instance of the Calendar.
     *
     * @param value
     *            to check
     * @return null or value
     */
    public static Calendar getCalendarFromDateOrNull(final Object val) {
        if (val == null) {
            return null;
        }

        final Calendar cal = Calendar.getInstance();
        cal.setTime((Date) val);

        return cal;
    }

    /**
     * Return null or new instance of the date value as a Date.
     *
     * @param value
     *            to check
     * @return null or value
     */
    public static Date getDateFromCalendarOrNull(final Calendar cal) {
        if (cal == null) {
            return null;
        }
        return cal.getTime();
    }


    /**
     *
     * @param date
     * @return
     */
    public static boolean isValidDate(final String date, final String format)
    {
         final SimpleDateFormat sdf = new SimpleDateFormat(format);
        // declare and initialize testDate variable, this is what will hold  our converted string
        Date testDate = null;
        // we will now try to parse the string into date form
        try{
            testDate = sdf.parse(date);
        }
        // if the format of the string provided doesn't match the format we
        // declared in SimpleDateFormat() we will get an exception
        catch (final ParseException e){
            LOG.warn(e);
            return false;
        }

        //  dateformat.parse will accept any date as long as it's in the format you defined, it simply rolls dates over, for example,
        //  december 32  becomes jan 1 and december 0 becomes november 30 , This statement will make sure that once the string has been checked for proper formatting that the date is
        //  still the  date that was entered, if it's not, we assume that the date is invalid

        if (!sdf.format(testDate).equals(date)){
            return false;
        }
        // if we make it to here without getting an error it is assumed that the date was a valid one and that it's in the proper format
    return true;
    }

    /**
     * Convienience method for Checking Date Chronological order.
     *
     * @param fromDate
     * @param toDate
     * @return boolean
     */
    public static boolean isFromDateEarlierThanToDate(final String  pFromDate, final String pToDate)
    {
        boolean result = false;
        final Date frmDate = DateStringUtils.getDateFromString(pFromDate) ;
        final Date toDate = DateStringUtils.getDateFromString(pToDate) ;

        if ( frmDate.compareTo(toDate) <= 0 )
        {
            result = true ;
        }
        return result;

    }

    /**
     * Add one day to String date
     * @param date String in "dd-MM-yyyy" format
     * @return New date
     */
    public static Date addOneDayToDate(final String date) {
        if (StringUtils.isNotBlank(date)) {
            final Calendar calcDate = new GregorianCalendar();
            calcDate.setTime(DateStringUtils.getDateFromString(date));
            calcDate.add(Calendar.DATE, 1);
            return calcDate.getTime();
        }
        return null;
    }

    public static String getStringFromCalendar(final Calendar cal)
    {
        final DateFormat df = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT);
        return  df.format(cal.getTime());

    }

    /**
     * Converts Calendar to TimeSatmp
     * @param cal
     * @return
     */
    public static Timestamp getTimeStampFromDate(final Date date) {

        return new Timestamp(date.getTime());
    }

    /**
     * Return Date\time from a string. null is returned for an invalid date\time.
     *
     * @param cal
     *            containing the Calendar
     * @param timeString
     *            containing the time
     * @return dateTime
     */
    public static Calendar getCalendarFromDateTime(final Calendar cal, final String timeString) {
        final Date date = cal.getTime();
        if (date == null || timeString == null || "".equals(timeString.trim())) {
            return null;
        }
        // convert the time to string
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.TIME_FORMAT);
        try {
            final Calendar dateTime = new GregorianCalendar();
            dateTime.clear();
            dateTime.setTime(date);
            final Date time = simpleDateFormat.parse(timeString);
            final Calendar timeCal = new GregorianCalendar();
            timeCal.setTime(time);
            dateTime.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
            dateTime.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
            return dateTime;
        } catch (final ParseException e) {
            // Should have been validated already
            LOG.debug(e);
        }
        return null;
    }

}
