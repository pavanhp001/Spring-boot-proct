package abc.xyz.pts.bcs.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

import org.junit.Test;

import abc.xyz.pts.bcs.common.dao.utils.Constants;

/**
 * Test class for CustomCalendarEditor.
 */
public class CustomCalendarEditorTest extends TestCase {

    private CustomCalendarEditor cceEmpty;
    private CustomCalendarEditor cceNotEmpty;
    private static String DATE = "02-11-2008 00:00";
    private static Calendar date = new GregorianCalendar(2008,10,2);

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        cceEmpty = new CustomCalendarEditor(new SimpleDateFormat(Constants.FULL_DATE_FORMAT), true);
        cceNotEmpty = new CustomCalendarEditor(new SimpleDateFormat(Constants.FULL_DATE_FORMAT), false);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        cceEmpty = null;
        cceNotEmpty = null;
    }

    @Test
    public void testGetAsText(){
        cceEmpty.setValue(date);
        assertEquals(DATE, cceEmpty.getAsText());
        cceEmpty.setValue(null);
        assertEquals("", cceEmpty.getAsText());
    }

    @Test
    public void testSetAsText(){
        cceEmpty.setAsText("");
        assertNull(cceEmpty.getValue());
        try {
            cceNotEmpty.setAsText("");
            fail();
        } catch (IllegalArgumentException e) {
            // ignore expected exception
        }
        try {
            cceNotEmpty.setAsText("02-11-28 00:00");
            fail();
        } catch (IllegalArgumentException e) {
            // ignore expected exception
        }
        try {
            cceEmpty.setAsText("02-11-28 00:00");
            fail();
        } catch (IllegalArgumentException e) {
            // ignore expected exception
        }
        cceEmpty.setAsText(DATE);
        assertEquals(date, cceEmpty.getValue());
        cceNotEmpty.setAsText(DATE);
        assertEquals(date, cceNotEmpty.getValue());
    }

    @Test
    public void testIsYearValid(){
        Calendar date = new GregorianCalendar(2008,10,2);
        assertTrue(cceEmpty.isYearValid(date));
        date = new GregorianCalendar(8,10,2);
        assertFalse(cceEmpty.isYearValid(date));
    }
}
