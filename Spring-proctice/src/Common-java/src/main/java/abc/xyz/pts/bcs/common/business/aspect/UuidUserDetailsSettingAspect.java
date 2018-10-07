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
package abc.xyz.pts.bcs.common.business.aspect;

import java.util.UUID;

import org.aspectj.lang.JoinPoint;

import abc.xyz.pts.bcs.common.bean.UserDetails;
import abc.xyz.pts.bcs.common.dao.session.PrivateUserProfile;

/**
 * An aspect that sets the user details for a given thread. In addition it will create a unique session id for the user
 * details. This can be used by backend systems as a similar mechanism to using the jsession id in the web frontends for
 * session ids.
 *
 * @author tterry
 *
 */
public class UuidUserDetailsSettingAspect {

    private PrivateUserProfile userProfile = null;
    private UserDetails userDetails = null;

    /**
     * @param userProfile
     *            the userProfile to set
     */
    public void setUserProfile(final PrivateUserProfile userProfile) {
        this.userProfile = userProfile;
    }

    /**
     * @param userDetails
     *            the userDetails to set
     */
    public void setUserDetails(final UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public void setSession(final JoinPoint jp) {
        // quite a slow call, we could use JUG for a performance increase
        // but expected throughput is not enough to warrant it.
        UUID uuid = UUID.randomUUID();
        userDetails.setSessionId(uuid.toString());
        userProfile.setUserDetailsForThread(userDetails);
    }
}
