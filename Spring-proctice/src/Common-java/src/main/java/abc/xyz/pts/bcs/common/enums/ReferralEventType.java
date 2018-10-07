/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.enums;

import abc.xyz.pts.bcs.irisk.mvo.riskmatch.AlertActionType;

public enum ReferralEventType {
    REFCREATED,
    HITADDED,
    HITINA,
    HITIN,
    HITOUT,
    REFACK,
    REFQUALA,
    REFQUALM,
    REFNOTF,
    REFRENOTF,
    REFOPENED,
    REFESCLTD,
    REFCLOSEDA,
    REFCLOSEDM,
    REFREOPEND,
    ADDNOTE,
    CLEAREDHIT;

    public static ReferralEventType getInstanceFromAlertActionType(final AlertActionType alertActionType ) {
        ReferralEventType result = null;
        switch (alertActionType){
        case NOTIFIED:
            result = REFNOTF;
            break;
        case RENOTIFIED:
            result = REFRENOTF;
            break;
        case ESCALATED:
            result = REFESCLTD;
            break;
        case HITADDED:
            result = HITADDED;
            break;
        }
        return result;
    }
}
