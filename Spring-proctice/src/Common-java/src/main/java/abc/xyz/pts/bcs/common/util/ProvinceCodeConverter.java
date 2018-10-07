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
 * An interface for converting a province code from a 2 character code to an ISTAT integer code.
 *
 * @version $Id: ProvinceCodeConverter.java 605 2008-07-16 16:41:49Z wgriffiths $
 */
public interface ProvinceCodeConverter {

    int UNKNOWN_PROVINCE_CODE = -1;

    /**
     * Converts given province code to its ISTAT equivalent.
     *
     * @param code
     *            Province code to convert.
     * @return The equivalent ISTAT code.
     */
    int convert(String code);
}
