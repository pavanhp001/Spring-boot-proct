/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2010
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.ListResourceBundle;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

/**
 * @see {@link ReportUtil}
 * @author Sai
 *
 */
public class ReportUtilTest {
	
	private static final String DOT = ".";
//	private static final String COMMA = ",";
	private static final String DELIMITER = ", ";
	private static final String INVALID_VALUE = "INVALID_VALUE";
	private static final String TRANSLATED_VALUE_1 = "translatedValue1";
	private static final String TRANSLATED_VALUE_2 = "translatedValue2";
	private static final String MY_VALUE_1 = "myValue1";
	private static final String MY_VALUE_2 = "myValue2";
	private static final String MY_VALID_RESOURCE_KEY = "myValidResourceKey";
	private static final String MY_INVALID_RESOURCE_KEY = "myInvalidResourceKey";
	private static final String COMMA_KEY = "comma";
	private static final String COMMA_VALUE = ",";
	
	private ResourceBundle resourceBundle;
	
	@Before
	public void setup() {
		resourceBundle = getMockResourceBundle();
	}

	@Test
	public void formatString_shouldFormatString_GivenAValidResourceKey() {
		String formattedString = ReportUtil.formatString(MY_VALID_RESOURCE_KEY, MY_VALUE_1, resourceBundle);
		assertEquals("Unexpected formatted string", TRANSLATED_VALUE_1, formattedString);
	}
	
	@Test
	public void formatString_shouldReturnTheSameValue_GivenAnInvalidResourceKey() {
		String formattedString = ReportUtil.formatString(MY_INVALID_RESOURCE_KEY, MY_VALUE_1, resourceBundle);
		assertEquals("Unexpected returned string", MY_VALUE_1, formattedString);
	}
	
	@Test
	public void formatString_shouldReturnBlankString_GivenANullValue() {
		String formattedString = ReportUtil.formatString(MY_INVALID_RESOURCE_KEY, null, resourceBundle);
		assertEquals("Unexpected returned string", "", formattedString);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void formatString_shouldError_GivenMandatoryParametersMissing() {
		ReportUtil.formatString(null, null, null);
	}
	
	@Test
	public void formatListOfStrings_shouldFormatListOfStrings_GivenAValidResourceKey() {
		String formattedString = ReportUtil.formatListOfStrings(MY_VALID_RESOURCE_KEY, Arrays.asList(MY_VALUE_1,MY_VALUE_2), resourceBundle);
		assertEquals("Unexpected formatted string", TRANSLATED_VALUE_1+COMMA_VALUE+TRANSLATED_VALUE_2, formattedString);
	}
	
	@Test
	public void formatListOfStrings_shouldFormatListOfStrings_GivenOneMissingResource() {
		String formattedString = ReportUtil.formatListOfStrings(MY_VALID_RESOURCE_KEY, Arrays.asList(MY_VALUE_1,INVALID_VALUE), resourceBundle);
		assertEquals("Unexpected formatted string", TRANSLATED_VALUE_1+COMMA_VALUE+INVALID_VALUE, formattedString);
	}
	
	@Test
	public void formatListOfStrings_shouldFormatListOfStrings_GivenAllMissingResources() {
		String formattedString = ReportUtil.formatListOfStrings(MY_VALID_RESOURCE_KEY, Arrays.asList(INVALID_VALUE,INVALID_VALUE), resourceBundle);
		assertEquals("Unexpected formatted string", INVALID_VALUE+COMMA_VALUE+INVALID_VALUE, formattedString);
	}
	
	@Test
	public void formatListOfStrings_shouldReturnBlankString_GivenNullValues() {
		String formattedString = ReportUtil.formatListOfStrings(MY_INVALID_RESOURCE_KEY, null, resourceBundle);
		assertEquals("Unexpected returned string", "", formattedString);
	}
	
	@Test
	public void formatListOfStrings_shouldFormatListOfStrings_GivenAValidResourceKeyAndDelimiter() {
		String formattedString = ReportUtil.formatListOfStrings(MY_VALID_RESOURCE_KEY, Arrays.asList(MY_VALUE_1,MY_VALUE_2), resourceBundle, DELIMITER);
		assertEquals("Unexpected formatted string", TRANSLATED_VALUE_1+DELIMITER +TRANSLATED_VALUE_2, formattedString);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void formatListOfStrings_shouldError_GivenMandatoryParametersMissing() {
		ReportUtil.formatListOfStrings(null, null, null);
	}
	
	/**
	 * Note : {@link ResourceBundle} cannot be mocked with JMock because the {@link ResourceBundle#getString(String)} is declared as final and JMock doesn't support it.
	 * @return {@link ResourceBundle} mocked implementation.
	 */
	private ResourceBundle getMockResourceBundle() {
		  return new ListResourceBundle() {
			  private Object[][] contents = new Object[][]{
                      {MY_VALID_RESOURCE_KEY+DOT+MY_VALUE_1,TRANSLATED_VALUE_1},
                      {MY_VALID_RESOURCE_KEY+DOT+MY_VALUE_2,TRANSLATED_VALUE_2},
                      {COMMA_KEY,COMMA_VALUE},
			  };
			  
			@Override
			protected Object[][] getContents() {
				return contents;
			}
		  };
	}

}
