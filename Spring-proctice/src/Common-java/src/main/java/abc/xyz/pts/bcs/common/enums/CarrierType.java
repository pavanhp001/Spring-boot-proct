/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.enums;

public enum CarrierType {
    AIR,
    GENA,
    SEA,
    BUS;

    public static String[] getAllTypes() {
        final String[] allTypes= new String[values().length];
        int counter = 0;
        for (final CarrierType carrierType : values()) {
            allTypes[counter++] = carrierType.name();
        }

        return allTypes;
    }
}
