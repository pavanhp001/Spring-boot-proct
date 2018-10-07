/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.util;

public class CustomFormatter {
    public static String parseString(final String stringToTrim) {
        return trimValue(stringToTrim);
    }

    public static String printString(final String stringToTrim) {
        return trimValue(stringToTrim);
    }

    static private String trimValue(final String stringToTrim) {
        if (stringToTrim != null) {
            return stringToTrim.trim();
        } else {
            return stringToTrim;
        }
    }
}
