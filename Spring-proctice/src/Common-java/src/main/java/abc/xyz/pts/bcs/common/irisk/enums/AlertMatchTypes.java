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
package abc.xyz.pts.bcs.common.irisk.enums;

/**
 * @author ryattapu
 * This Enum Stores Watch List Match Types
 */
public enum AlertMatchTypes
{
    PERSON_MATCH("P"),
    DOCUMENT_MATCH("D")
    ;

    private String dbVal;

    private AlertMatchTypes(final String dbValue)
    {
        this.dbVal = dbValue;
    }

    public String getDbVal()
    {
        return dbVal;
    }
}
