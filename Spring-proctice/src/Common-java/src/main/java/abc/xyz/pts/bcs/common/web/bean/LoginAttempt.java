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
package abc.xyz.pts.bcs.common.web.bean;

/**
 * This class represents a login attempt. Initially it will only be used to mediate the login error message on
 * Login.JSP. In future it may be used to support login audit functionality.
 */
public class LoginAttempt {
    private boolean retry = false;

    /**
     * Construct an instance definining whether it is a retry or not.
     *
     * @param retry
     *            true if a retry, false other wise.
     */
    public LoginAttempt(final boolean retry) {
        this.retry = retry;
    }

    /**
     * Returns true if the login attempt is a retry.
     *
     * @return
     */
    public boolean isRetry() {
        return retry;
    }
}
