/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.jmx.impl;

import abc.xyz.pts.bcs.common.jmx.IAuditMessageListenerMXBean;

/**
 * This class add Audit Specific information information. This information would be used by JMX
 *
 * @author ryattapu.
 */
public final class AuditMessageListenerMXBean extends AbstractMessageListenerMXBean implements
        IAuditMessageListenerMXBean {
    private String invalidEventReason;

    @Override
    public String getInvalidEventReason() {
        return invalidEventReason;
    }

    public void setInvalidEventReason(final String invalidEvent) {
        invalidEventReason = invalidEvent;
    }

}
