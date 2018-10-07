/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.enums;

/**
 * Flight Movement.
 *
 * This is the actual value stored in the database.
 *
 * @author mmotlib-siddiqui
 *
 */
public enum MovementStatusType implements HasDataDictionaryKey, HasLegend
{
    DENIED("movement.status.D","D"),
    EXPECTED("movement.status.E","E"),
    CANCELLED("movement.status.C","C"),
    N("movement.status.N","N")   // NO MOVEMENT
    ;

    private String dataDictionaryKey;
    private String legend;

    private MovementStatusType(final String ddKey, final String legendKey)
    {
        this.dataDictionaryKey = ddKey;
        this.legend = legendKey;
    }

    @Override
    public String getDataDictionaryKey()
    {
        return this.dataDictionaryKey;
    }

    @Override
    public String getLegend() {
        return legend;
    }
}
