/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.enums;

public enum ReferralHitsType {

    WL("Watch List Hit"), APP("App Rules Hit"), TRAVP("Traveller Pattern Hit");

    private final String description;

    ReferralHitsType(final String value) {
        description = value;
    }

    public String getDescription() {
        return description;
    }

}
