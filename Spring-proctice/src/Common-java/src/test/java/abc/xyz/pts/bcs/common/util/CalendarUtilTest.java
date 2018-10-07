/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2012
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class CalendarUtilTest extends TestCase {

    @Test
    public void testIsTheDiffLessThanTwoMonths(){
        Calendar fromDate = new GregorianCalendar(2008,10,2);
        Calendar toDate = (Calendar)fromDate.clone();
        toDate.add(Calendar.MONTH, 2);
        toDate.add(Calendar.DAY_OF_WEEK, 1);
        assertFalse(CalendarUtils.isTheDiffLessThanTwoMonths(fromDate, toDate));
        toDate.add(Calendar.DAY_OF_WEEK, -2);
        assertTrue(CalendarUtils.isTheDiffLessThanTwoMonths(fromDate, toDate));
        toDate = (Calendar)fromDate.clone();
        assertTrue(CalendarUtils.isTheDiffLessThanTwoMonths(fromDate, toDate));
        toDate.add(Calendar.DAY_OF_WEEK, -1);
        assertFalse(CalendarUtils.isTheDiffLessThanTwoMonths(fromDate, toDate));
        assertTrue(CalendarUtils.isTheDiffLessThanTwoMonths(null, toDate));
        assertTrue(CalendarUtils.isTheDiffLessThanTwoMonths(fromDate, null));
    }

    @Test
    public void testIsTheDiffLessThanThreeMonths(){
        Calendar fromDate = new GregorianCalendar(2008,10,2);
        Calendar toDate = (Calendar)fromDate.clone();
        toDate.add(Calendar.MONTH, 3);
        toDate.add(Calendar.DAY_OF_WEEK, 1);
        assertFalse(CalendarUtils.isTheDiffLessThanThreeMonths(fromDate, toDate));
        toDate.add(Calendar.DAY_OF_WEEK, -2);
        assertTrue(CalendarUtils.isTheDiffLessThanThreeMonths(fromDate, toDate));
        toDate = (Calendar)fromDate.clone();
        assertTrue(CalendarUtils.isTheDiffLessThanThreeMonths(fromDate, toDate));
        toDate.add(Calendar.DAY_OF_WEEK, -1);
        assertFalse(CalendarUtils.isTheDiffLessThanThreeMonths(fromDate, toDate));
        assertTrue(CalendarUtils.isTheDiffLessThanThreeMonths(null, toDate));
        assertTrue(CalendarUtils.isTheDiffLessThanThreeMonths(fromDate, null));
    }

    @Test
    public void testIsDiffChronological(){
        Calendar fromDate = new GregorianCalendar(2008,10,2);
        Calendar toDate = (Calendar)fromDate.clone();
        assertTrue(CalendarUtils.isDiffChronological(fromDate, toDate));
        toDate.add(Calendar.DAY_OF_WEEK, 1);
        assertTrue(CalendarUtils.isDiffChronological(fromDate, toDate));
        toDate.add(Calendar.DAY_OF_WEEK, -2);
        assertFalse(CalendarUtils.isDiffChronological(fromDate, toDate));
        assertFalse(CalendarUtils.isDiffChronological(null, toDate));
        assertFalse(CalendarUtils.isDiffChronological(fromDate, null));
        assertTrue(CalendarUtils.isDiffChronological(null, null));
    }

    // dd-MM-yyyy HH:mm
    @Test
    public void testGetCalendarAsStringTimestamp(){
        Calendar date = new GregorianCalendar(2008,10,2);
        assertEquals(CalendarUtils.getCalendarAsStringTimestamp(date), "02-11-2008 00:00");
        assertNull(CalendarUtils.getCalendarAsStringTimestamp(null));
    }
    
    @Test
    public void testCombine_1() {
        //
        // TEST:    valid date & time
        // EXPECT:  returned with date & time combined
        //
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 15);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 20);
        cal.set(Calendar.MONTH, 10);
        cal.set(Calendar.YEAR, 2011);
        
        Calendar dateOnly = new GregorianCalendar();
        dateOnly.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        dateOnly.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        dateOnly.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        
        String timePart = "13:15";
        Calendar result = CalendarUtils.combine(dateOnly, timePart); 
        
        assertEquals(cal.getTimeInMillis(), result.getTimeInMillis());
    }
    
    @Test
    public void testCombine_2() {
        //
        // TEST:    date & time are both null
        // EXPECT:  returned null
        //
        Calendar result = CalendarUtils.combine(null, null); 
        
        assertNull(result);
    }
    
    @Test
    public void testCombine_3() {
        //
        // TEST:    date is valid, time is null
        // EXPECT:  returned date with 00:00:00 hours
        //
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 20);
        cal.set(Calendar.MONTH, 10);
        cal.set(Calendar.YEAR, 2011);
        
        Calendar dateOnly = new GregorianCalendar();
        dateOnly.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        dateOnly.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        dateOnly.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        
        Calendar result = CalendarUtils.combine(dateOnly, null); 
        
        assertEquals(cal.getTimeInMillis(), result.getTimeInMillis());
    }
    
    @Test
    public void testCombine_4() {
        //
        // TEST:    valid date & time in incorrect format
        // EXPECT:  returned with date with time set to 00:00:00 hours
        //
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 20);
        cal.set(Calendar.MONTH, 10);
        cal.set(Calendar.YEAR, 2011);
        
        Calendar dateOnly = new GregorianCalendar();
        dateOnly.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        dateOnly.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        dateOnly.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        
        String timePart = "1315:"; // incorrect format
        Calendar result = CalendarUtils.combine(dateOnly, timePart); 
        
        assertEquals(cal.getTimeInMillis(), result.getTimeInMillis());
    }
    
    @Test
    public void testCombine_5() {
        //
        // TEST:    valid date & time with few character
        // EXPECT:  returned with date with time set to 00:00:00 hours
        //
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 20);
        cal.set(Calendar.MONTH, 10);
        cal.set(Calendar.YEAR, 2011);
        
        Calendar dateOnly = new GregorianCalendar();
        dateOnly.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        dateOnly.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        dateOnly.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        
        String timePart = "13:5"; // incorrect format
        Calendar result = CalendarUtils.combine(dateOnly, timePart); 
        
        assertEquals(cal.getTimeInMillis(), result.getTimeInMillis());
    }
    
    @Test
    public void testCombine_6() {
        //
        // TEST:    valid date & time with seconds
        // EXPECT:  returned with date with time without seconds
        //
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 50);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 20);
        cal.set(Calendar.MONTH, 10);
        cal.set(Calendar.YEAR, 2011);
        
        Calendar dateOnly = new GregorianCalendar();
        dateOnly.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        dateOnly.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        dateOnly.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        
        String timePart = "13:50:29"; // incorrect format
        Calendar result = CalendarUtils.combine(dateOnly, timePart); 
        
        assertEquals(cal.getTimeInMillis(), result.getTimeInMillis());
    }
    
    @Test
    public void testCombine_7() {
        //
        // TEST:    valid date & time
        // EXPECT:  returned with date & time combined
        //
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 15);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 20);
        cal.set(Calendar.MONTH, 10);
        cal.set(Calendar.YEAR, 2011);
        
        Calendar dateOnly = new GregorianCalendar();
        dateOnly.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        dateOnly.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        dateOnly.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        
        String timePart = "25:15"; // expecting hours to be set to 0
        Calendar result = CalendarUtils.combine(dateOnly, timePart); 
        
        assertEquals(cal.getTimeInMillis(), result.getTimeInMillis());
    }
    
    @Test
    public void testToStringHHMMSS() {
    	
    	Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE, 11);
        cal.set(Calendar.SECOND, 12);
    	
    	String stringHHMMSS = CalendarUtils.toStringHHMMSS(cal);
    	assertThat(stringHHMMSS, is("10:11:12"));
    }
    
    @Test
    public void testToStringHHMMSS_ReturnsEmptyStringOnNullInput() {
    	assertThat(CalendarUtils.toStringHHMMSS(null), is(StringUtils.EMPTY));
    }
    
    @Test 
    public void test_convertToSqlDate() 
    {
        final String DATE19_TYPE = "yyyy'-'MM'-'dd'T'HH':'mm':'ss" ;
        String dateStr;
        java.sql.Date result;
        java.sql.Date expectedDate;
        Calendar cal;
        
        // ** Valid Data
        dateStr = "2012-06-18T08:27:11";
        cal = new GregorianCalendar(2012, 5, 18, 8, 27, 11); // remember 0 based month
        expectedDate = new java.sql.Date(cal.getTimeInMillis());
        result = CalendarUtils.convertToSqlDate(dateStr, DATE19_TYPE);
        assertThat(result, is(expectedDate));
        
        // ** Invalid Data
        dateStr = "2012-06-1808:27:11";
        expectedDate = null;
        result = CalendarUtils.convertToSqlDate(dateStr, DATE19_TYPE);
        assertThat(result, is(expectedDate));
        
        // ** Invalid Data
        dateStr = "1233445";
        expectedDate = null;
        result = CalendarUtils.convertToSqlDate(dateStr, DATE19_TYPE);
        assertThat(result, is(expectedDate));
        
        // ** Empty Data
        dateStr = "";
        expectedDate = null;
        result = CalendarUtils.convertToSqlDate(dateStr, DATE19_TYPE);
        assertThat(result, is(expectedDate));
        
        // ** Null Data
        dateStr = null;
        expectedDate = null;
        result = CalendarUtils.convertToSqlDate(dateStr, DATE19_TYPE);
        assertThat(result, is(expectedDate));
    }

}
