/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
/**
 *  @author MMotlib-Siddiqui
 */
package abc.xyz.pts.bcs.wi.iir.search;

import java.util.ArrayList;
import java.util.List;

import abc.xyz.pts.bcs.wi.dto.TargetItem;


final class IIRSearchResponseImpl
    implements IIRSearchResponse
{
    private final List<TargetItem> data;

    public IIRSearchResponseImpl() {
        this.data = new ArrayList<TargetItem>();
    }

    public IIRSearchResponseImpl(final List<TargetItem> data) {
        this.data = data;
    }

    @Override
    public String toString() {

        if (this.data == null) {
            return "";
        }

        final StringBuffer buf = new StringBuffer();
        buf.append("IIRSearchResponseImpl[");

        for (final TargetItem ti : data)
        {
            buf.append(System.getProperty("line.separator"));
            buf.append("\t");
            buf.append(ti.toString());
        }
        buf.append(System.getProperty("line.separator"));
        buf.append("]");

        return buf.toString();
    }


    @Override
    public List<TargetItem> getTargetList()
    {
        return data;
    }

}
