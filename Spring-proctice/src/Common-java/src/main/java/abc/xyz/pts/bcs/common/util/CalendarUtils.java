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
package abc.xyz.pts.bcs.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.CalendarValidator;
import org.apache.log4j.Logger;

import abc.xyz.pts.bcs.common.dao.utils.Constants;

public final class CalendarUtils 
{
    private static final Logger LOG = Logger.getLogger(CalendarUtils.class);
    
    private CalendarUtils() {
        super();
    }
    
    /**
	 * 
	 */
	private static final String HH_MM = "HH:mm";
	private static final String HH_MM_SS = "HH:mm:ss";

	public static boolean isTheDiffLessThanTwoMonths(final Calendar fromDate, final Calendar toDate) {
        if ((fromDate == null) || (toDate == null) || fromDate.equals(toDate)) {
            return true;
        }
        return checkForDifference(fromDate, toDate, 2);
    }

    public static boolean isTheDiffLessThanThreeMonths(final Calendar fromDate, final Calendar toDate) {
        if ((fromDate == null) || (toDate == null) || fromDate.equals(toDate)) {
            return true;
        }
        return checkForDifference(fromDate, toDate, 3);
    }

    public static String getCalendarAsStringTimestamp(final Calendar calendar) {
        if (calendar != null) {
            DateFormat df = new SimpleDateFormat(Constants.FULL_DATE_FORMAT);
            return df.format(calendar.getTime());
        }

        return null;
    }

    public static String getCalendarAsString(final Calendar calendar, final String format) {
        if (calendar != null) {
            DateFormat df = new SimpleDateFormat(format);
            return df.format(calendar.getTime());
        }

        return null;
    }

    /**
     * Return date with combined date and time.
     *
     * @param cal
     * @param timeStr in HH:mm format  (24 hr)
     * @return
     */
    public static Calendar combine(final Calendar cal, final String timeStr)
    {
        if (cal == null) {
            return cal;
        }

        // If time format is incorrect then set the time to 00:00 hours
        Integer hour = 0;
        Integer minute = 0;
        if (StringUtils.isNotBlank(timeStr))
        {
            Pattern p = Pattern.compile("^[0-9]{2}:[0-9]{2}");  // hh:mm
            Matcher m = p.matcher(timeStr);
            if (m.find()) 
            {
                int idx = timeStr.indexOf(':');
                int numCharToGrab = 2;          // minutes and hours represented by 2 characters
                hour = Integer.parseInt(timeStr.substring(0, numCharToGrab));
                idx++;      // skip ':' char
                minute = Integer.parseInt(timeStr.substring(idx, idx + numCharToGrab)); // only grab minutes part 
            }
        }

        return new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 
                (hour > 23 ? 0 : hour), (minute > 59 ? 0 : minute), 0);
    }

    /*
     * private
     */
    private static boolean checkForDifference(final Calendar fromDate, final Calendar toDate, final int months) {
        Calendar cloneFromDate = (Calendar) fromDate.clone();
        if (cloneFromDate.getTime().getTime() > toDate.getTime().getTime()) {
            return false;
        }
        cloneFromDate.add(Calendar.MONTH, months);
        if (cloneFromDate.getTime().getTime() >= toDate.getTime().getTime()) {
            return true;
        }

        return false;
    }

    public static boolean isDiffChronological(final Calendar fromDate, final Calendar toDate)
    {
        // cant check if they are both null
        if (fromDate == null && toDate == null) {
            return true;
        }

        if (fromDate == null || toDate == null) {
            return false;
        }

        return !(fromDate.getTime().getTime() > toDate.getTime().getTime());
    }

    /*
     * This method assumes supplied Calendars milli seconds, seconds, minutes, hours are zero
     * Supplied Calendars contians only Date, Month and Year values and rest are zero
     */
    public static boolean isTwoDateAreEquals(final Calendar dateOne, final Calendar dateTwo)
    {
        if (dateTwo == null && dateOne == null) {
            return true;
        }

        if (dateTwo == null || dateOne == null) {
            return false;
        }

        return (dateOne.getTime().getTime() == dateTwo.getTime().getTime());
    }

    public static boolean isDateWithInRangeOfDates(final Calendar fromDate, final Calendar toDate, final Calendar suppliedDate) {
        // cant check if they are both null
        if (fromDate == null && toDate == null) {
            return true;
        }

        if (fromDate == null || toDate == null) {
            return false;
        }

        return !(suppliedDate.before(fromDate) || suppliedDate.after(toDate));
    }

    /**
     * convert Calendar to String format
     * @param dt
     * @return date in DD-MMM-YYYY
     */
    public static String calToString(final java.util.Calendar cal)
    {
        return calToString(cal,"dd-MMM-yyyy");
    }

    /**
     * convert Calendar to String format
     * @param dt
     * @return date in dd-MM-yyyy
     */
   public static String toStringDDMMYYYY(final java.util.Calendar cal)
   {
       return calToString(cal,"dd-MM-yyyy");
   }

   /**
    * convert Calendar to String time format
    * @param dt
    * @return date in HH:mm  (24 hour)
    */
    public static String toStringHHMM(final java.util.Calendar cal)
    {
        return calToString(cal, HH_MM);
    }
    
    /**
     * convert Calendar to String time format
     * @param dt
     * @return date in HH:mm  (24 hour)
     */
     public static String toStringHHMMSS(final java.util.Calendar cal)
     {
         return calToString(cal, HH_MM_SS);
     }

   /**
    * convert Calendar to String format
    * @param dt
    * @param pattern
    * @return date formatted using specified pattern
    */
   public static String calToString(final java.util.Calendar cal, final String pattern)
   {
       if (cal == null || pattern == null) {
           return StringUtils.EMPTY;
       }

       java.util.Date dt = cal.getTime();

       try{
           DateFormat formatter = new SimpleDateFormat(pattern);
           return formatter.format(dt);
       } catch (IllegalArgumentException e){
           // Return an empty string if the pattern is not valid
           return StringUtils.EMPTY;
       }
   }
   
   /**
    * Converts String(YYYY-MM-DD'T'HH:mm:SS) to java.sql.date Expected format is
    * DATE19_TYPE --> YYYY-MM-DD'T'HH:mm:SS
    * 
    * 
    * @return Calendar pnrCreationDate
    */
   public static java.sql.Date convertToSqlDate(final String str, final String fmt) {
       java.sql.Date val = null;
       try {
           Calendar creationDate = CalendarUtils.stringToCal(str, fmt);
           if (creationDate != null) {
               val = new java.sql.Date(creationDate.getTime().getTime());
           }
       } catch (ParseException e) {
           LOG.warn(e, e); // not fatal so no need to panic!
       }

       return val;
   }

   /**
    * convert String to Calendar format
    * @param dt
    * @param pattern
    * @return date formatted using specified pattern
 * @throws ParseException
    */
   public static Calendar stringToCal(final String strDate, final String pattern) throws ParseException
   {
       Calendar cal = null;
       if(strDate != null){
           DateFormat formatter = new SimpleDateFormat(pattern);
           Date date = null;
           date = formatter.parse(strDate);
           cal=Calendar.getInstance();
           cal.setTime(date);
       }
       return  cal;
   }

   /**
    * convert String to Calendar format with date pattern derived from the date string
    * @param dt
    * @param pattern
    * @return date formatted using specified pattern
    */
   public static Calendar stringToCal(final String strDate){
       Calendar cal = null;
       if(strDate != null) {
           String datePattern = getDatePatternFromDateString(strDate);
           CalendarValidator validator = CalendarValidator.getInstance();
           if (validator.isValid(strDate, datePattern)) {
               cal = validator.validate(strDate, datePattern);
           }
       }

       return  cal;
   }

   private static String getDatePatternFromDateString(final String strDate){
       String datePattern = null;
       if(strDate.indexOf(WebConstants.SLASH) != -1){
           datePattern = getDatePatternFromDateStringWithSlash(strDate);
       }else if(strDate.indexOf(WebConstants.EIPHEN) != -1 ){
           datePattern = getDatePatternFromDateStringWithEiphen(strDate);
       }
       return datePattern;

   }

   private static String getDatePatternFromDateStringWithSlash(final String strDate){
       String datePattern = null;
       StringTokenizer st = new StringTokenizer(strDate, WebConstants.SLASH);
       String strToken = null;
       while(st.hasMoreTokens()){
           strToken = st.nextToken();
           if(strToken != null){
               if (strToken.length()== 1 || strToken.length()== 2){
                   datePattern = WebConstants.CSV_EN_DATE_FORMAT_WITH_SLASH;
                   break;
               }else if(strToken.length()== 4){
                   datePattern = WebConstants.CSV_AR_DATE_FORMAT_WITH_SLASH;
                   break;
               }
           }
       }

       return datePattern;
   }

   private static String getDatePatternFromDateStringWithEiphen(final String strDate){
       String datePattern = null;
       StringTokenizer st = new StringTokenizer(strDate, WebConstants.EIPHEN);
       String strToken = null;
       while(st.hasMoreTokens()){
           strToken = st.nextToken();
           if(strToken != null){
               if (strToken.length()== 1 || strToken.length()== 2){
                   datePattern = WebConstants.CSV_EN_DATE_FORMAT_WITH_EIPHEN;
                   break;
               }else if(strToken.length()== 4){
                   datePattern = WebConstants.CSV_AR_DATE_FORMAT_WITH_EIPHEN;
                   break;
               }
           }
       }
       return datePattern;
   }

   public static Calendar getDefaultDateFromYear(final String strYear, final String appendString) throws ParseException{
       StringBuffer sbDate = new StringBuffer();
       sbDate.append(appendString).append(strYear);
       Calendar cal = null;
       if(sbDate != null){
           DateFormat formatter = new SimpleDateFormat(WebConstants.CSV_EN_DATE_FORMAT_WITH_EIPHEN);
           Date date = formatter.parse(sbDate.toString());
           cal=Calendar.getInstance();
           cal.setTime(date);
       }
       return  cal;
   }

    public static double getTimeDiffInHours(final Calendar dateOne, final Calendar dateTwo) {

        if (dateOne == null || dateTwo == null) {
            return 0;
        }
        double milis1 = dateOne.getTimeInMillis();
        double milis2 = dateTwo.getTimeInMillis();
        return (milis2 - milis1) / (60 * 60 * 1000);

    }
    
    
    /**
     * This is to get the gain from the partitioning of the database when a user just supplies the
     * Arrival date. It takes date as string and number of days to adjust to the date.
     * @param date
     * @param adjustedDays
     * @return
     */
    public static String adjustDateByGivenDays(final String date, final int adjustedDays){
    	Calendar cal = CalendarUtils.stringToCal(date);
		if (cal != null) {
			cal.add(Calendar.DATE, adjustedDays);
		}
		return CalendarUtils.calToString(cal, "dd-MM-yyyy");
    }
    
}
