/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query;

public final class NullField
{
    private String val;

    private NullField(final Field fl)
    {
        val = " NULL AS " + fl;
    }

    private NullField(final Field fl, final Table tbl)
    {
        val = " NULL AS " + tbl + "." +  fl;
    }


    public static NullField getInstance(final Field fl)
    {
        return new NullField(fl);
    }

    public static NullField getInstance(final Field fl, final Table tbl)
    {
        return new NullField(fl);
    }

    public String getVal()
    {
        return val;
    }

}
