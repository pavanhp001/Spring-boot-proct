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

package abc.xyz.pts.bcs.common.audit.util;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class PropertyFormatter {

    public static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";
    public static final String SIMPLE_DATE_FORMAT = "dd-MM-yyyy";
    public static final String TIME_FORMAT = "HH:mm";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern(SIMPLE_DATE_FORMAT);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern(DATE_FORMAT);

    public static String format(final Object obj) {
        String result = "";
        if (obj != null) {
            if (obj instanceof Date) {
                result = formatDate((Date) obj);
            } else if (obj instanceof Calendar) {
                result = formatCalendar((Calendar) obj);
            }
            /*
             * TODO: Add support for many other types. such as better date handling. else if (obj instanceof LocalDate){
             * //format as date only } else if (obj instanceof LocalTime){ //format as time only } else if( obj
             * instanceof LocalDateTime){ //format as date and time }
             */else {
                result = obj.toString();
            }
        }
        return result;
    }

    private static String formatDate(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return formatCalendar(cal);
    }

    private static String formatCalendar(final Calendar value) {
        String result = "";
        int hour = value.get(Calendar.HOUR);
        int minute = value.get(Calendar.MINUTE);
        int second = value.get(Calendar.SECOND);
        int milli = value.get(Calendar.MILLISECOND);
        if ((hour == 0) && (minute == 0) && (second == 0) && (milli == 0)) {
            result = DATE_FORMATTER.print(value.getTimeInMillis());
        } else {
            result = DATE_TIME_FORMATTER.print(value.getTimeInMillis());
        }
        return result;
    }
}