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

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.utils.DateStringUtils;


/**
 * Test class for DateStringUtils.
 */
public class DateStringUtilsTest extends TestCase {

    private static final String DATE = "11-10-2008";
    private static final String TIME = "12:13";

    /**
     *Test parsing of String as Date using the getDateFromString method.
     *<p>
     *<table>
     *<tr><td><b>Test Case ID</td><td>ITIDB-4-PER-004</td></tr>
     *<tr><td><b>Description</b></td><td>Test parsing of String as Date using the getDateFromString method.</td></tr>
     *<tr><td><b>Objective</b></td><td>Confirm Date or NULL is returned.</td></tr>
     *<tr><td><b>Requirement</b></td><td>Valid String converted to Date, or return NULL.</td></tr>
     *<tr><td><b>Setup</b></td><td>None</td></tr>
     *<tr><td><b>Test Steps:</b></td>
     *<td><ol><li>Pass empty String as parameter to the getDateFromString method</li>
     *<li>Pass NULL as parameter to the getDateFromString method</li>
     *<li>Pass invalid "abc" String as parameter to the getDateFromString method</li>
     *<li>Pass String of a Date as parameter to the getDateFromString method</li></ol></td></tr>
     *<tr><td><b>Expected result</b></td>
     *<td><ol><li>Return NULL</li>
     *<li>Return NULL</li>
     *<li>Return NULL</li>
     *<li>Return non NULL Date <code>Date</code></li>
     *<li><code>Date</code> is correct year</li>
     *<li><code>Date</code> is correct month</li>
     *<li><code>Date</code> is correct day</li></ol></td></tr>
     *<tr><td><b>Cleanup</b></td><td>None</td></tr>
     *</table>
     */
    @Test
    public void testGetDateFromString() {

        assertNull(DateStringUtils.getDateFromString(""));
        assertNull(DateStringUtils.getDateFromString(null));
        assertNull(DateStringUtils.getDateFromString("abc"));
        Date date = DateStringUtils.getDateFromString("01-01-2001");
        assertNotNull(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        assertEquals(2001, cal.get(Calendar.YEAR));
        assertEquals(Calendar.JANUARY, cal.get(Calendar.MONTH));
        assertEquals(01, cal.get(Calendar.DAY_OF_MONTH));
    }

    /**
     *Test formatting Date as String using the getStringFromDate method.
     *<p>
     *<table>
     *<tr><td><b>Test Case ID</td><td>ITIDB-4-PER-005</td></tr>
     *<tr><td><b>Description</b></td><td>Test formatting Date as String using the getStringFromDate method.</td></tr>
     *<tr><td><b>Objective</b></td><td>Confirm String or NULL is returned.</td></tr>
     *<tr><td><b>Requirement</b></td><td>Valid Date converted to String, or return NULL.</td></tr>
     *<tr><td><b>Setup</b></td><td>None</td></tr>
     *<tr><td><b>Test Steps:</b></td>
     *<td><ol><li>Pass NULL as parameter to the getStringFromDate method</li>
     *<li>Pass valid Date as String for "01-01-201" as parameter to the getStringFromDate method</li></ol></td></tr>
     *<tr><td><b>Expected result</b></td>
     *<td><ol><li>Return NULL</li>
     *<li>Return non NULL <code>String</code></li>
     *<li><code>String</code> matches formatted Date</li>
     *</ol></td></tr>
     *<tr><td><b>Cleanup</b></td><td>None</td></tr>
     *</table>
     */
    @Test
    public void testGetStringFromDate() {
        assertEquals("", DateStringUtils.getStringFromDate(null));
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2008, Calendar.OCTOBER, 11);

        String date = DateStringUtils.getStringFromDate(cal.getTime());
        assertNotNull(date);
        assertEquals(DATE, date);
    }

    /**
     *Test formatting Time part of a Date as String using the getStringFromTime method.
     *<p>
     *<table>
     *<tr><td><b>Test Case ID</td><td>ITIDB-4-PER-006</td></tr>
     *<tr><td><b>Description</b></td><td>Test formatting Time part of a Date as String using
     *                                   the getStringFromTime method.</td></tr>
     *<tr><td><b>Objective</b></td><td>Confirm String or NULL is returned.</td></tr>
     *<tr><td><b>Requirement</b></td><td>Extract Time from valid Date converted to String, or return NULL.</td></tr>
     *<tr><td><b>Setup</b></td><td>None</td></tr>
     *<tr><td><b>Test Steps:</b></td>
     *<td><ol><li>Pass NULL as parameter to the getStringFromTime method</li>
     *<li>Pass valid Date for "01-01-201 12:13:14" as parameter to the getStringFromTime method</li></ol></td></tr>
     *<tr><td><b>Expected result</b></td>
     *<td><ol><li>Return NULL</li>
     *<li>Return non NULL <code>String</code></li>
     *<li><code>String</code> matches formatted Time without seconds (hh:mm)</li></ol></td></tr>
     *<tr><td><b>Cleanup</b></td><td>None</td></tr>
     *</table>
     */
    @Test
    public void testGetStringFromTime() {
        assertNull(DateStringUtils.getStringFromTime(null));
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2008, Calendar.OCTOBER, 11, 12, 13, 14);
        String time = DateStringUtils.getStringFromTime(cal.getTime());
        assertNotNull(time);
        assertEquals(TIME, time);
    }

    /**
     *Test parsing of String as Date using the getDateTimeFromStrings method.
     *<p>
     *<table>
     *<tr><td><b>Test Case ID</td><td>ITIDB-4-PER-007</td></tr>
     *<tr><td><b>Description</b></td><td>Test parsing of String as Date/Time using the
     *                                   getDateTimeFromStrings method.</td></tr>
     *<tr><td><b>Objective</b></td><td>Confirm Date or NULL is returned.</td></tr>
     *<tr><td><b>Requirement</b></td><td>Valid String converted to Date/Time, or return NULL.</td></tr>
     *<tr><td><b>Setup</b></td><td>None</td></tr>
     *<tr><td><b>Test Steps:</b></td>
     *<td><ol><li>Pass valid Date String and NULL Time String getDateTimeFromStrings method</li>
     *<li>Pass NULL Date String and valid Time String getDateTimeFromStrings method</li>
     *<li>Pass valid Date String and NULL Time String getDateTimeFromStrings method</li>
     *<li>Pass NULL Date String and NULL Time String getDateTimeFromStrings method</li>
     *<li>Pass valid Date String and empty Time String getDateTimeFromStrings method</li>
     *<li>Pass empty Date String and valid Time String getDateTimeFromStrings method</li>
     *<li>Pass empty Date String and empty Time String getDateTimeFromStrings method</li>
     *<li>Pass invalid Date String and invalid Time String getDateTimeFromStrings method</li>
     *<li>Pass valid Date String and invalid Time String getDateTimeFromStrings method</li>
     *<li>Pass invalid Date String and valid Time String getDateTimeFromStrings method</li>
     *<li>Pass valid Date String and valid Time String getDateTimeFromStrings method</li></ol></td></tr>
     *<tr><td><b>Expected result</b></td>
     *<td><ol><li>Return NULL</li>
     *<li>Return NULL</li>
     *<li>Return NULL</li>
     *<li>Return NULL</li>
     *<li>Return NULL</li>
     *<li>Return NULL</li>
     *<li>Return NULL</li>
     *<li>Return NULL</li>
     *<li>Return NULL</li>
     *<li>Return non NULL Date <code>Date</code></li>
     *<li><code>Date</code> is correct year</li>
     *<li><code>Date</code> is correct month</li>
     *<li><code>Date</code> is correct day</li>
     *<li><code>Date</code> is correct hour</li>
     *<li><code>Date</code> is correct minute</li></ol></td></tr>
     *<tr><td><b>Cleanup</b></td><td>None</td></tr>
     *</table>
     */
    @Test
    public void testGetDateTimeFromStrings() {

        int year = 2008;
        int month = 11;
        int day = 10;
        int hour = 12;
        int min = 13;
        String dateString = day + "-" + month + "-" + year;
        String timeString = hour + ":" + min;

        // try null values
        assertNull(DateStringUtils.getDateTimeFromStrings(dateString, null));
        assertNull(DateStringUtils.getDateTimeFromStrings(null, timeString));
        assertNull(DateStringUtils.getDateTimeFromStrings(null, null));
        assertNull(DateStringUtils.getDateTimeFromStrings(dateString, ""));
        assertNull(DateStringUtils.getDateTimeFromStrings("", timeString));
        assertNull(DateStringUtils.getDateTimeFromStrings("", ""));

        // try invalid values
        assertNull(DateStringUtils.getDateTimeFromStrings("1111", "2222"));
        assertNull(DateStringUtils.getDateTimeFromStrings(dateString, "2222"));
        assertNull(DateStringUtils.getDateTimeFromStrings("1111", timeString));

        // set valid values
        Date test1 = DateStringUtils.getDateTimeFromStrings(dateString, timeString);
        assertNotNull(test1);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(test1);
        assertEquals(year, cal1.get(Calendar.YEAR));
        assertEquals(month - 1, cal1.get(Calendar.MONTH));
        assertEquals(day, cal1.get(Calendar.DAY_OF_MONTH));
        assertEquals(hour, cal1.get(Calendar.HOUR_OF_DAY));
        assertEquals(min, cal1.get(Calendar.MINUTE));
    }

    /**
     *Test replacing wildcards in strings using the replaceWildCards method.
     *<p>
     *<table>
     *<tr><td><b>Test Case ID</td><td>ITIDB-4-PER-008</td></tr>
     *<tr><td><b>Description</b></td><td>Test replacing wildcards in string using the replaceWildCards method</td></tr>
     *<tr><td><b>Objective</b></td><td>Confirm updated String is returned.</td></tr>
     *<tr><td><b>Requirement</b></td><td>Passed String is updated to convert wildcards.</td></tr>
     *<tr><td><b>Setup</b></td><td>None</td></tr>
     *<tr><td><b>Test Steps:</b></td>
     *<td><ol><li>Pass NULL as parameter to the replaceWildCards method</li>
     *<li>Pass empty String as parameter to the replaceWildCards method</li>
     *<li>Pass String without any wildcards as parameter to the replaceWildCards method</li>
     *<li>Pass String with single wildcard * as parameter to the replaceWildCards method</li>
     *<li>Pass String with just wildcards ** as parameter to the replaceWildCards method</li>
     *<li>Pass String with wildcard * at the start of String as parameter to the replaceWildCards method</li>
     *<li>Pass String with wildcard * at the end of String as parameter to the replaceWildCards method</li>
     *<li>Pass String with single wildcard ? as parameter to the replaceWildCards method</li>
     *<li>Pass String with just wildcards ?? as parameter to the replaceWildCards method</li>
     *<li>Pass String with wildcard ? at the start of String as parameter to the replaceWildCards method</li>
     *<li>Pass String with wildcard ? at the end of String as parameter to the replaceWildCards method</li>
     *<li>Pass String ?12*34 as parameter to the replaceWildCards method</li></ol></td></tr>
     *<tr><td><b>Expected result</b></td>
     *<td><ol><li>Returns NULL</li>
     *<li>Returns NULL</li>
     *<li>Returns original String</li>
     *<li>Return String %</li>
     *<li>Return String %%</li>
     *<li>Return String %1</li>
     *<li>Return String 1%</li>
     *<li>Return String _</li>
     *<li>Return String __</li>
     *<li>Return String _1</li>
     *<li>Return String 1_</li>
     *<li>Return String _12%34</li>
     *</ol></td></tr>
     *<tr><td><b>Cleanup</b></td><td>None</td></tr>
     *</table>
     */
    public void testReplateWildCards() {
        assertNull(DateStringUtils.replaceWildCards(null));
        assertNull(DateStringUtils.replaceWildCards(""));
        assertEquals("1234", DateStringUtils.replaceWildCards("1234"));

        assertEquals("%", DateStringUtils.replaceWildCards("*"));
        assertEquals("%%", DateStringUtils.replaceWildCards("**"));
        assertEquals("%1", DateStringUtils.replaceWildCards("*1"));
        assertEquals("1%", DateStringUtils.replaceWildCards("1*"));

        assertEquals("_", DateStringUtils.replaceWildCards("?"));
        assertEquals("__", DateStringUtils.replaceWildCards("??"));
        assertEquals("_1", DateStringUtils.replaceWildCards("?1"));
        assertEquals("1_", DateStringUtils.replaceWildCards("1?"));

        assertEquals("_12%34", DateStringUtils.replaceWildCards("?12*34"));
    }

    /**
     *Test evaluation of String to check not NULL or empty String from getStringValue method.
     *<p>
     *<table>
     *<tr><td><b>Test Case ID</td><td>ITIDB-4-PER-009</td></tr>
     *<tr><td><b>Description</b></td><td>Test evaluation of String to check not NULL or empty
     *                                   String from getStringValue method.</td></tr>
     *<tr><td><b>Objective</b></td><td>Confirm String or NULL is returned.</td></tr>
     *<tr><td><b>Requirement</b></td><td>Null or empty String or "-" will return null/</td></tr>
     *<tr><td><b>Setup</b></td><td>None</td></tr>
     *<tr><td><b>Test Steps:</b></td>
     *<td><ol><li>Pass NULL as parameter to the getStringValue method</li>
     *<li>Pass empty String as parameter to the getStringValue method</li>
     *<li>Pass "-" String as parameter to the getStringValue method</li>
     *<li>Pass "test" String as parameter to the getStringValue method</li>
     *<li>Pass "test " String as parameter to the getStringValue method</li></ol></td></tr>
     *<tr><td><b>Expected result</b></td>
     *<td><ol><li>Return NULL</li>
     *<li>Return NULL</li>
     *<li>Return NULL</li>
     *<li>Return non NULL String "test"</li>
     *<li>Return non NULL String "test "</li></ol></td></tr>
     *<tr><td><b>Cleanup</b></td><td>None</td></tr>
     *</table>
     */
    @Test
    public void testGetStringValue() {
        assertNull(DateStringUtils.getStringValue(null));
        assertNull(DateStringUtils.getStringValue(""));
        assertNull(DateStringUtils.getStringValue("-"));
        assertEquals("test", DateStringUtils.getStringValue("test"));
        assertEquals("test ", DateStringUtils.getStringValue("test "));
    }

    /**
     *Test evaluation of Date to check not NULL and return NULL or Calendar using getCalendarFromDateOrNull method.
     *<p>
     *<table>
     *<tr><td><b>Test Case ID</td><td>ITIDB-4-PER-010</td></tr>
     *<tr><td><b>Description</b></td><td>Test evaluation of Date to check not NULL and return NULL
     *                                   or Calendar using getCalendarFromDateOrNull method.</td></tr>
     *<tr><td><b>Objective</b></td><td>Confirm Calendar is not null, or convert to Date.</td></tr>
     *<tr><td><b>Requirement</b></td><td>Date is converted to Calendar, or return null/</td></tr>
     *<tr><td><b>Setup</b></td><td>None</td></tr>
     *<tr><td><b>Test Steps:</b></td>
     *<td><ol><li>Pass NULL as parameter to the getCalendarFromDateOrNull method</li>
     *<li>Pass String as parameter to the getCalendarFromDateOrNull method</li>
     *<li>Pass Calendar as parameter to the getCalendarFromDateOrNull method</li></ol></td></tr>
     *<tr><td><b>Expected result</b></td>
     *<td><ol><li>Return NULL</li>
     *<li>Throws ClassCastException</li>
     *<li>Return non NULL <code>Calendar</code></li>
     *<li><code>Calendar</code> is equal to the passed Date</li></ol></td></tr>
     *<tr><td><b>Cleanup</b></td><td>None</td></tr>
     *</table>
     */
    @Test
    public void testGetCalendarFromDateOrNull() {
        assertNull(DateStringUtils.getCalendarFromDateOrNull(null));
        try {
            DateStringUtils.getCalendarFromDateOrNull("test");
        } catch (ClassCastException cce) {
            // ignore expected exception
        }
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2008, Calendar.OCTOBER, 11, 12, 13, 14);
        Calendar test = DateStringUtils.getCalendarFromDateOrNull(cal.getTime());
        assertNotNull(test);
        assertEquals(cal, test);
    }

    /**
     *Test evaluation of Calendar to check not NULL and return NULL or Date using getDateFromCalendarOrNull method.
     *<p>
     *<table>
     *<tr><td><b>Test Case ID</td><td>ITIDB-4-PER-011</td></tr>
     *<tr><td><b>Description</b></td><td>Test evaluation of Calendar to check not NULL and return NULL
     *                                    or Date using getDateFromCalendarOrNull method.</td></tr>
     *<tr><td><b>Objective</b></td><td>Confirm Date is not null, or convert to Calendar.</td></tr>
     *<tr><td><b>Requirement</b></td><td>Calendar is converted to Date, or return null/</td></tr>
     *<tr><td><b>Setup</b></td><td>None</td></tr>
     *<tr><td><b>Test Steps:</b></td>
     *<td><ol><li>Pass NULL as parameter to the getDateFromCalendarOrNull method</li>
     *<li>Pass String as parameter to the getDateFromCalendarOrNull method</li>
     *<li>Pass Calendar as parameter to the getDateFromCalendarOrNull method</li></ol></td></tr>
     *<tr><td><b>Expected result</b></td>
     *<td><ol><li>Return NULL</li>
     *<li>Return non NULL <code>Date</code></li>
     *<li><code>Date</code> is equal to the passed Calendar</li></ol></td></tr>
     *<tr><td><b>Cleanup</b></td><td>None</td></tr>
     *</table>
     */
    @Test
    public void testGetDateFromCalendarOrNull() {
        assertNull(DateStringUtils.getDateFromCalendarOrNull(null));

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2008, Calendar.OCTOBER, 11, 12, 13, 14);
        Date test = DateStringUtils.getDateFromCalendarOrNull(cal);
        assertEquals(cal.getTime(), test);
    }


    @Test
    public void testGetStringDateFromSchemaDate() {    	
    	assertEquals("18-08-2011 13:02", DateStringUtils.getStringDateFromSchemaDate("2011-08-18T13:02:30"));
    }
    
    @Test
    public void testAddOneDayToDate(){
    	assertEquals("19-08-2011", DateStringUtils.getStringFromDate(DateStringUtils.addOneDayToDate("18-08-2011")));
    }
    @Test
    public void testAddOneDayToNullDate(){
    	assertEquals(null, DateStringUtils.addOneDayToDate(null));
    }
    
    @Test
    public void testAddOneDayToEmptyDate(){
    	assertEquals(null, DateStringUtils.addOneDayToDate(""));
    }
}
