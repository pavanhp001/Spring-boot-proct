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
package abc.xyz.pts.bcs.admin.web.utils;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.Assert;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

import abc.xyz.pts.bcs.admin.AbstractTestCase;
import abc.xyz.pts.bcs.admin.web.utils.DateEditor;
import abc.xyz.pts.bcs.common.dao.utils.Constants;

public class DateEditorTest extends AbstractTestCase{
    private DateEditor customEditor;
    private Date date;
    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern(Constants.SIMPLE_DATE_FORMAT);

    @Before
    public void setUp() {
        customEditor = new DateEditor();
        date = new Date();
        customEditor.setValue(date);

    }

    @Test
    public void testGetAsTextNull() throws Exception {
        customEditor.setValue(null);
        String result = customEditor.getAsText();
        if(!result.equals("")){
        	Assert.fail();
        }
    }
    
    @Test
    public void testSetAsTextNull() throws Exception {
        customEditor.setAsText(null);
        Calendar result = (Calendar) customEditor.getValue();
        if(result != null){
            Assert.fail();
        }
    }
    
    @Test
    public void testGetAsTextDate() throws Exception {
    	String testDate = "18-05-1979";
    	Date expected = new Date();
    	expected.setTime(DATE_FORMATTER.parseDateTime(testDate).toDate().getTime());
        
        customEditor.setValue(expected);
        String result = customEditor.getAsText();
        if(!result.equals(testDate)){
        	Assert.fail();
        }
    }

    @Test
    public void testSetAsTextDate() throws Exception {
    	String testDate = "18-05-1979";
    	Date expected = new Date();
    	expected.setTime(DATE_FORMATTER.parseDateTime(testDate).toDate().getTime());
        customEditor.setAsText(testDate);
        Date result = (Date) customEditor.getValue();
        if(result == null || result.getTime() != expected.getTime()){
        	Assert.fail();
        }
    }
    
    @Test
    public void testGetAsTextDateTime() throws Exception {
        Calendar cal = new GregorianCalendar(1979,4,18,13,27,34);
        Date input = cal.getTime();
        String expected = "18-05-1979 13:27:34";
        
        customEditor.setValue(input);
        String result = customEditor.getAsText();
        assertTrue(expected.equals(result));
    }

    @Test
    public void testGetAsTextDateNoTime() throws Exception {
        Calendar cal = new GregorianCalendar(1979,4,18,0,0,0);
        Date input = cal.getTime();
        String expected = "18-05-1979";
        
        customEditor.setValue(input);
        String result = customEditor.getAsText();
        assertTrue(expected.equals(result));
    }

    @Test
    public void testSetAsTextInvalid() throws Exception {
        try{
        	customEditor.setAsText("abcd");
        	Assert.fail();
        }
        catch(IllegalArgumentException e){
        	// Expected Behaviour
        }
    }
}
