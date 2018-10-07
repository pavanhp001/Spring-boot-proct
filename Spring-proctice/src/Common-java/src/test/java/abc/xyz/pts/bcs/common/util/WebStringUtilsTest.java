package abc.xyz.pts.bcs.common.util;

import junit.framework.TestCase;

import org.junit.Test;

public class WebStringUtilsTest extends TestCase {

    @Test
    public void testReplaceSearchCharacters(){
        assertNull(WebStringUtils.replaceSearchCharacters(null));
        assertEquals("", WebStringUtils.replaceSearchCharacters(""));
        assertEquals("Test", WebStringUtils.replaceSearchCharacters("Test"));
        assertEquals("Test_", WebStringUtils.replaceSearchCharacters("Test_"));
        assertEquals("Test__", WebStringUtils.replaceSearchCharacters("Test__"));
        assertEquals("Test%", WebStringUtils.replaceSearchCharacters("Test*"));
        assertEquals("Test_", WebStringUtils.replaceSearchCharacters("Test?"));
    }

    @Test
    public void testIsStringValidWildCardSearch(){
        assertTrue(WebStringUtils.isStringValidWildCardSearch(null));
        assertTrue(WebStringUtils.isStringValidWildCardSearch("Test*"));
        assertTrue(WebStringUtils.isStringValidWildCardSearch("Test?"));
        assertTrue(WebStringUtils.isStringValidWildCardSearch("Test*"));
        assertFalse(WebStringUtils.isStringValidWildCardSearch("Test**"));
        assertFalse(WebStringUtils.isStringValidWildCardSearch("Test??"));
        assertFalse(WebStringUtils.isStringValidWildCardSearch("Test*?"));
        assertFalse(WebStringUtils.isStringValidWildCardSearch("Test*a"));
        assertFalse(WebStringUtils.isStringValidWildCardSearch("Test?a"));
    }
    @Test
    public void testGetFullName(){
        assertEquals("JunitL, JunitF",WebStringUtils.getFullName("JunitF", "JunitL", null));
    }
    @Test
    public void testConvertColumNameToCamelCase(){
        assertEquals("ColumnName",WebStringUtils.convertColumNameToCamelCase("COLUMN_NAME"));
    }
    
    @Test
    public void testTimeLeftpadZeros(){
    	assertEquals("", WebStringUtils.timeLeftpadZeros(""));
        assertEquals("03:00", WebStringUtils.timeLeftpadZeros("3:0"));
        assertEquals("03:00", WebStringUtils.timeLeftpadZeros("3:00"));
        assertEquals("300", WebStringUtils.timeLeftpadZeros("300"));
    }
    
    @Test
    public void testFlightLeftpadZeros(){
    	assertEquals("", WebStringUtils.flightLeftpadZeros(""));
        assertEquals("0001", WebStringUtils.flightLeftpadZeros("1"));
        assertEquals("0011", WebStringUtils.flightLeftpadZeros("11"));
        assertEquals("0111", WebStringUtils.flightLeftpadZeros("111"));
        assertEquals("0000C", WebStringUtils.flightLeftpadZeros("C"));
        assertEquals("0001C", WebStringUtils.flightLeftpadZeros("1C"));
        assertEquals("0011C", WebStringUtils.flightLeftpadZeros("11C"));
        assertEquals("0111C", WebStringUtils.flightLeftpadZeros("111C"));
        assertEquals("1111C", WebStringUtils.flightLeftpadZeros("1111C"));
    }
}
