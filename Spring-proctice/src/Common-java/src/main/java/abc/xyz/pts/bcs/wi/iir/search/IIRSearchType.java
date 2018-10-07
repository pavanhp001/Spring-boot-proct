/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
/**
 * @author MMotlib-Siddiqui
 */
package abc.xyz.pts.bcs.wi.iir.search;

public enum IIRSearchType
{
    INTERNATIONAL("search_international"),
    ARABIC_ROMANISED("search_arabic_romanised"),
    MIXED("search_mixed"),

    UNKNOWN("unknown-search")
    ;

    private String val;

    private IIRSearchType(final String val)
    {
        this.val = val;
    }

    public static IIRSearchType getInstance(final String val)
    {
        if (IIRSearchType.INTERNATIONAL.toString().equalsIgnoreCase(val)) {
            return IIRSearchType.INTERNATIONAL;
        }

        return IIRSearchType.UNKNOWN;
    }


    @Override
    public String toString() {
        return this.val;
    }

    public String getVal() {
        return val;
    }

//    public boolean equals(final String val)
//    {
//        return this.val.equalsIgnoreCase(val);
//    }

}
