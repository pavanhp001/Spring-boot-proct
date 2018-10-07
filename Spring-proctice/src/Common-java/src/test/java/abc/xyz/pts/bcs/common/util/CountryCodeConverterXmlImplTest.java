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

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

/**
 * Tests for the ISO to ISTAT country code converter.
 *
 * @version $Id: CountryCodeConverterXmlImplTest.java 2096 2009-07-13 15:18:23Z mcunliffe $
 */
public class CountryCodeConverterXmlImplTest extends TestCase {

    private static final String VALID_COUNTRY_CODE_MAP =
        "test-sdinsis-country-code-map.xml";

    private static final String NON_EXISTENT_COUNTRY_CODE_MAP =
        "does-not-exist.xml";

    private static final String INVALID_COUNTRY_CODE_MAP =
        "test-sdinsis-country-code-map-invalid.xml";

    /**
     *<table>
     *  <tr>
     *      <td><b>Test Case ID</td>
     *      <td>ITIRK-2-CCCXIT01</td>
     *  </tr>
     *  <tr>
     *      <td><b>Description</b></td>
     *      <td>ISO Country Code Conversion Test</td>
     *  </tr>
     *  <tr>
     *      <td><b>Objective</b></td>
     *      <td>
     *          Tests whether an ISO country code is converted into its ISTAT
     *          equivalent or not.
     *      </td>
     *  </tr>
     *  <tr>
     *      <td><b>Requirement</b></td>
     *      <td></td>
     *  </tr>
     *  <tr>
     *      <td><b>Setup</b></td>
     *      <td></td>
     *  </tr>
     *  <tr>
     *      <td><b>Test Steps</b></td>
     *      <td>
     *          <ol>
     *              <li>
     *                  Checks that an ISO country code is mapped correctly to
     *                  its equivalent ISTAT country code
     *              </li>
     *              <li>
     *                  Checks that an ISO country code which maps to a unknown
     *                  ISTAT country code returns the unknown ISTAT country code
     *              </li>
     *              <li>
     *                  Checks that an ISO country code that does not map at all
     *                  to an ISTAT country code also returns the value for an
     *                  unknown ISTAT country code
     *              </li>
     *          </ol>
     *      </td>
     *  </tr>
     *  <tr>
     *      <td><b>Expected result</b></td>
     *      <td>The various ISO country code mappings return the correct ISTAT equivalent</td>
     *  </tr>
     *      <td><b>Cleanup</b></td>
     *      <td></td>
     *</table>
     */
    @Test
    public void testISOCountryCodeConversion() {

        CountryCodeConverterXmlImpl converter =
            new CountryCodeConverterXmlImpl(new ClassPathResource(VALID_COUNTRY_CODE_MAP));

        // First check an ISO code that has an ISTAT code mapped
        String testIsoCode1 = "GBR";
        final int expectedIstatCode1 = 219;

        assertEquals(expectedIstatCode1, converter.convert(testIsoCode1));

        // The next ISO code is in the mapping but there is no ISTAT code
        // so it has been mapped as unknown ISTAT code
        String testIsoCode2 = "ALA";

        assertEquals(CountryCodeConverter.UNKNOWN_COUNTRY_CODE,
                converter.convert(testIsoCode2));

        // Now we'll look for an ISO code that doesn't exist in our mapping
        // file and, again, we'll expect an unknown ISTAT code
        String testIsoCode3 = "BEN";

        assertEquals(CountryCodeConverter.UNKNOWN_COUNTRY_CODE,
                converter.convert(testIsoCode3));

    }

    /**
     *<table>
     *  <tr>
     *      <td><b>Test Case ID</td>
     *      <td>ITIRK-2-CCCXIT02</td>
     *  </tr>
     *  <tr>
     *      <td><b>Description</b></td>
     *      <td>ISO Country Code Conversion Exceptions Test</td>
     *  </tr>
     *  <tr>
     *      <td><b>Objective</b></td>
     *      <td>
     *          Tests the exception handling when converting from ISO to ISTAT code.
     *      </td>
     *  </tr>
     *  <tr>
     *      <td><b>Requirement</b></td>
     *      <td></td>
     *  </tr>
     *  <tr>
     *      <td><b>Setup</b></td>
     *      <td></td>
     *  </tr>
     *  <tr>
     *      <td><b>Test Steps</b></td>
     *      <td>
     *          <ol>
     *              <li>
     *                  Checks that a null ISO country code returns the value for an
     *                  unknown ISTAT country code
     *              </li>
     *              <li>
     *                  Checks that if the resource containing the country code
     *                  mappings could not be located then the value for an
     *                  unknown ISTAT country code is returned
     *              </li>
     *              <li>
     *                  Checks that if the resource containing the country code
     *                  mappings contains badly formed XML then the value for an
     *                  unknown ISTAT country code is returned
     *              </li>
     *          </ol>
     *      </td>
     *  </tr>
     *  <tr>
     *      <td><b>Expected result</b></td>
     *      <td>Various different exception scenarios are handled cleanly by the converter</td>
     *  </tr>
     *      <td><b>Cleanup</b></td>
     *      <td></td>
     *</table>
     */
    @Test
    public void testISOConversionExceptions() {

        CountryCodeConverterXmlImpl converter =
            new CountryCodeConverterXmlImpl(new ClassPathResource(VALID_COUNTRY_CODE_MAP));

        // If we pass in a null country code then we should get back an
        // unknown ISTAT code
        assertEquals(CountryCodeConverter.UNKNOWN_COUNTRY_CODE, converter.convert(null));

        // Pass a non-existent resource into the country code converter and we
        // should get an unknown ISTAT code returned when we run the conversion
        converter =
            new CountryCodeConverterXmlImpl(new ClassPathResource(NON_EXISTENT_COUNTRY_CODE_MAP));

        assertEquals(CountryCodeConverter.UNKNOWN_COUNTRY_CODE, converter.convert(null));

        // Pass a badly-formed XML resource into the country code converter and we
        // should get an unknown ISTAT code returned when we run the conversion
        converter =
            new CountryCodeConverterXmlImpl(new ClassPathResource(INVALID_COUNTRY_CODE_MAP));

        assertEquals(CountryCodeConverter.UNKNOWN_COUNTRY_CODE, converter.convert(null));
    }

    /**
     *<table>
     *  <tr>
     *      <td><b>Test Case ID</td>
     *      <td>ITIRK-2-CCCXIT01</td>
     *  </tr>
     *  <tr>
     *      <td><b>Description</b></td>
     *      <td>ISTAT Country Code Conversion Test</td>
     *  </tr>
     *  <tr>
     *      <td><b>Objective</b></td>
     *      <td>
     *          Tests whether an ISTAT country code is converted into its ISO
     *          equivalent or not.
     *      </td>
     *  </tr>
     *  <tr>
     *      <td><b>Requirement</b></td>
     *      <td></td>
     *  </tr>
     *  <tr>
     *      <td><b>Setup</b></td>
     *      <td></td>
     *  </tr>
     *  <tr>
     *      <td><b>Test Steps</b></td>
     *      <td>
     *          <ol>
     *              <li>
     *                  Checks that an ISTAT country code is mapped correctly to
     *                  its equivalent ISO country code
     *              </li>
     *              <li>
     *                  Checks that an unknown ISTAT country code returns a null
     *                  ISO country code
     *              </li>
     *              <li>
     *                  Checks that an ISTAT country code that does not map at all
     *                  to an ISO country code also returns a null value
     *              </li>
     *          </ol>
     *      </td>
     *  </tr>
     *  <tr>
     *      <td><b>Expected result</b></td>
     *      <td>The various ISTAT country code mappings return the correct ISO equivalent</td>
     *  </tr>
     *      <td><b>Cleanup</b></td>
     *      <td></td>
     *</table>
     */
    @Test
    public void testISTATCountryCodeConversion() {

        CountryCodeConverterXmlImpl converter =
            new CountryCodeConverterXmlImpl(new ClassPathResource(VALID_COUNTRY_CODE_MAP));

        // First check an ISTAT code that has an ISO code mapped
        final int testIstatCode1 = 219;
        final String expectedIsoCode1 = "GBR";

        assertEquals(expectedIsoCode1, converter.convert(testIstatCode1));

        // The next ISTAT code is an unknown ISTAT code so it should return a null
        // ISO code
        int testIstatCode2 = CountryCodeConverter.UNKNOWN_COUNTRY_CODE;

        assertNull(converter.convert(testIstatCode2));

        // Now we'll look for an ISTAT code that doesn't exist in our mapping
        // file and, again, we'll expect a null ISO code to be returned
        final int testIstatCode3 = 837;

        assertNull(converter.convert(testIstatCode3));
    }

    /**
     *<table>
     *  <tr>
     *      <td><b>Test Case ID</td>
     *      <td>ITIRK-2-CCCXIT02</td>
     *  </tr>
     *  <tr>
     *      <td><b>Description</b></td>
     *      <td>ISTAT Country Code Conversion Exceptions Test</td>
     *  </tr>
     *  <tr>
     *      <td><b>Objective</b></td>
     *      <td>
     *          Tests the exception handling when converting from ISTAT to ISO code.
     *      </td>
     *  </tr>
     *  <tr>
     *      <td><b>Requirement</b></td>
     *      <td></td>
     *  </tr>
     *  <tr>
     *      <td><b>Setup</b></td>
     *      <td></td>
     *  </tr>
     *  <tr>
     *      <td><b>Test Steps</b></td>
     *      <td>
     *          <ol>
     *              <li>
     *                  Checks that if the resource containing the country code
     *                  mappings could not be located then a null ISO code is
     *                  returned
     *              </li>
     *              <li>
     *                  Checks that if the resource containing the country code
     *                  mappings contains badly formed XML then a null ISO code
     *                  is returned
     *              </li>
     *          </ol>
     *      </td>
     *  </tr>
     *  <tr>
     *      <td><b>Expected result</b></td>
     *      <td>Various different exception scenarios are handled cleanly by the converter</td>
     *  </tr>
     *      <td><b>Cleanup</b></td>
     *      <td></td>
     *</table>
     */
    @Test
    public void testExceptions() {

        // Pass a non-existent resource into the country code converter and we
        // should get a null ISO code returned when we run the conversion
        CountryCodeConverterXmlImpl converter =
            new CountryCodeConverterXmlImpl(new ClassPathResource(NON_EXISTENT_COUNTRY_CODE_MAP));

        assertNull(converter.convert(CountryCodeConverter.UNKNOWN_COUNTRY_CODE));

        // Pass a badly-formed XML resource into the country code converter and we
        // should get a null ISO code returned when we run the conversion
        converter =
            new CountryCodeConverterXmlImpl(new ClassPathResource(INVALID_COUNTRY_CODE_MAP));

        assertNull(converter.convert(CountryCodeConverter.UNKNOWN_COUNTRY_CODE));
    }
}
