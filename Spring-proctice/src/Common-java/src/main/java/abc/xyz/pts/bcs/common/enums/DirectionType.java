/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.enums;

public enum DirectionType implements HasDataDictionaryKey, HasLegend
{
    INBOUND("inbound", "I"),
    OUTBOUND("outbound", "O"),
    TRANSFER_TRANSIT("ap.transit.transfer", "T")
    ;

    private String dataDictionaryKey;
    private String legend;

    private DirectionType(final String ddKey, final String legendKey)
    {
        this.dataDictionaryKey = ddKey;
        this.legend = legendKey;
    }

    @Override
    public String getDataDictionaryKey() {
        return this.dataDictionaryKey;
    }

    @Override
    public String getLegend() {
        return legend;
    }


}
