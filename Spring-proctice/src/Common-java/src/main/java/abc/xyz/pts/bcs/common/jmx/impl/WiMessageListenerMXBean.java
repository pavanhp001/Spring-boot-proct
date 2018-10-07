/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.jmx.impl;

import abc.xyz.pts.bcs.common.jmx.IWiMessageListenerMXBean;

/**
 * This class add WI Specific information information. This information would be used by JMX
 *
 * @author ryattapu.
 */
public final class WiMessageListenerMXBean extends AbstractMessageListenerMXBean implements IWiMessageListenerMXBean {
    private long noOfMatches;

    @Override
    public long getNoOfMatches() {
        return noOfMatches;
    }

    public void setNoOfMatches(final long noOfMatches) {
        this.noOfMatches = noOfMatches;
    }

}
