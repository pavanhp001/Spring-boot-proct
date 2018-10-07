/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2012
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.business.lookup.bean;

/**
 * @author Thiruvengadam.S
 *
 */
public class TimeIntervalBean extends LookupItem {
    @Override
    public int compareTo(final LookupItem o) {
        final Float f = Float.parseFloat(this.getCode());
        final Float argF = o.getCode() != null ? Float.parseFloat(o.getCode()) : 0;

        /* This comparison is done as follows for getting descending order List */
        return argF.compareTo(f);
    }
}
