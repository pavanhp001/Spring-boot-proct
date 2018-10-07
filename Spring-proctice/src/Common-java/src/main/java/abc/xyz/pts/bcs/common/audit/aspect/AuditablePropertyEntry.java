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

package abc.xyz.pts.bcs.common.audit.aspect;

class AuditablePropertyEntry {
    private String resourceKey;
    private boolean auditable;

    public AuditablePropertyEntry(final String resourceKey) {
        if (resourceKey == null) {
            this.auditable = false;
        } else {
            this.auditable = true;
        }
        this.resourceKey = resourceKey;
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public boolean isAuditable() {
        return auditable;
    }
}
