/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.enums;

public enum OverrideType implements HasDataDictionaryKey, HasLegend
{
    AGENT("override.type.AGENT", "A"),
    GOVT("override.type.GOVT", "G"),
    NONE("override.type.NONE", "")
    ;

    private String dataDictionaryKey;
    private String legend;

    private OverrideType(final String ddKey, final String legendKey) {
        this.dataDictionaryKey = ddKey;
        this.legend = legendKey;
    }

    @Override
    public String getLegend() {
        return legend;
    }

    @Override
    public String getDataDictionaryKey() {
        return this.dataDictionaryKey;
    }
}
