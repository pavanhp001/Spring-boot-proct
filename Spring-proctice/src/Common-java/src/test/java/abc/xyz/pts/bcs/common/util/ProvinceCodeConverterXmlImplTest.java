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
 * Tests for the province code to ISTAT code converter.
 *
 * @version $Id: ProvinceCodeConverterXmlImplTest.java 2096 2009-07-13 15:18:23Z mcunliffe $
 */
public class ProvinceCodeConverterXmlImplTest extends TestCase {

    private static final String VALID_PROVINCE_CODE_MAP =
        "test-sdinsis-province-code-map.xml";

    private static final String NON_EXISTENT_PROVINCE_CODE_MAP =
        "does-not-exist.xml";

    private static final String INVALID_PROVINCE_CODE_MAP =
        "test-sdinsis-province-code-map-invalid.xml";

    /**
     *<table>
     *  <tr>
     *      <td><b>Test Case ID</td>
     *      <td>ITIRK-2-PCCXIT01</td>
     *  </tr>
     *  <tr>
     *      <td><b>Description</b></td>
     *      <td>Province Code Conversion Test</td>
     *  </tr>
     *  <tr>
     *      <td><b>Objective</b></td>
     *      <td>
     *          Tests whether a 2 character province code is converted into its ISTAT
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
     *                  Checks that a province code is mapped correctly to
     *                  its equivalent ISTAT province code
     *              </li>
     *              <li>
     *                  Checks that an province code that does not map at all
     *                  to an ISTAT province code will return the value for an
     *                  unknown ISTAT province code
     *              </li>
     *          </ol>
     *      </td>
     *  </tr>
     *  <tr>
     *      <td><b>Expected result</b></td>
     *      <td>The various province code mappings return the correct ISTAT equivalent</td>
     *  </tr>
     *      <td><b>Cleanup</b></td>
     *      <td></td>
     *</table>
     */
    @Test
    public void testProvinceCodeConversion() {

        ProvinceCodeConverterXmlImpl converter =
            new ProvinceCodeConverterXmlImpl(new ClassPathResource(VALID_PROVINCE_CODE_MAP));

        // First check a province code that has an ISTAT code mapped
        String testCode1 = "AT";
        final int expectedIstatCode1 = 5;

        assertEquals(expectedIstatCode1, converter.convert(testCode1));

        // Now we'll look for a province code that doesn't exist in our mapping
        // file and expect an unknown ISTAT code to be returned
        String testCode2 = "WG";

        assertEquals(ProvinceCodeConverter.UNKNOWN_PROVINCE_CODE,
                converter.convert(testCode2));
    }

    /**
     *<table>
     *  <tr>
     *      <td><b>Test Case ID</td>
     *      <td>ITIRK-2-PCCXIT02</td>
     *  </tr>
     *  <tr>
     *      <td><b>Description</b></td>
     *      <td>Province Code Conversion Exceptions Test</td>
     *  </tr>
     *  <tr>
     *      <td><b>Objective</b></td>
     *      <td>
     *          Tests the exception handling within the province code converter.
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
     *                  Checks that a null province code returns the value for an
     *                  unknown ISTAT province code
     *              </li>
     *              <li>
     *                  Checks that if the resource containing the province code
     *                  mappings could not be located then the value for an
     *                  unknown ISTAT province code is returned
     *              </li>
     *              <li>
     *                  Checks that if the resource containing the province code
     *                  mappings contains badly formed XML then the value for an
     *                  unknown ISTAT province code is returned
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

        ProvinceCodeConverterXmlImpl converter =
            new ProvinceCodeConverterXmlImpl(new ClassPathResource(VALID_PROVINCE_CODE_MAP));

        // If we pass in a null province code and we should get back an unknown
        // ISTAT code
        assertEquals(ProvinceCodeConverter.UNKNOWN_PROVINCE_CODE,
                converter.convert(null));

        // Pass a non-existent resource into the province code converter and we
        // should get an unknown ISTAT code returned when we run the conversion
        converter =
            new ProvinceCodeConverterXmlImpl(new ClassPathResource(NON_EXISTENT_PROVINCE_CODE_MAP));

        assertEquals(ProvinceCodeConverter.UNKNOWN_PROVINCE_CODE,
                converter.convert(null));

        // Pass a badly-formed XML resource into the province code converter and we
        // should get an unknown ISTAT code returned when we run the conversion
        converter =
            new ProvinceCodeConverterXmlImpl(new ClassPathResource(INVALID_PROVINCE_CODE_MAP));

        assertEquals(ProvinceCodeConverter.UNKNOWN_PROVINCE_CODE, converter.convert(null));
    }
}
