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

/**
 * An interface for converting a country code from ISO 3166-1 alpha-3 code to an ISTAT integer code.
 *
 * @version $Id: CountryCodeConverter.java 605 2008-07-16 16:41:49Z wgriffiths $
 */
public interface CountryCodeConverter {

    String ITALIAN_ISO_CODE = "ITA";

    int UNKNOWN_COUNTRY_CODE = -1;

    /**
     * Converts given ISO country code to its ISTAT equivalent.
     *
     * @param isoCode
     *            ISO country code to convert.
     * @return The equivalent ISTAT code.
     */
    int convert(String isoCode);

    /**
     * Converts given ISTAT country code to its ISO equivalent.
     *
     * @param istatCode
     *            ISTAT country code to convert.
     * @return The equivalent ISO code.
     */
    String convert(int istatCode);
}
