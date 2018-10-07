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

package abc.xyz.pts.bcs.common.business.impl;

import abc.xyz.pts.bcs.common.bean.UserDetails;
import abc.xyz.pts.bcs.common.dao.session.DbSessionParameterProvider;
import abc.xyz.pts.bcs.common.dao.session.PrivateUserProfile;

/**
 * Implementation of the PrivateUserProfile interface that sources the user profile information from an LDAP server.
 * This class should be thread safe except during construction.
 * <p>
 * It is expected that only one instance of this class be created in any container but not essential. More instances
 * will lead to greater resource utilisation.
 *
 * @author Tom Duncan.
 */
public final class SSOUserProfile implements PrivateUserProfile, DbSessionParameterProvider {

    /**
     * The threadlocal variable storing the User Details.
     */
    private ThreadLocal<UserDetails> userDetails = new ThreadLocal<UserDetails>() {
    };

    public void setUserDetailsForThread(final DbSessionParameterProvider userDetail) {
        this.userDetails.set((UserDetails) userDetail);
    }

    public void clearUserDetailsForThread() {
        userDetails.remove();
    }

    public DbSessionParameterProvider getUserDetails() {
        return userDetails.get();
    }

    public String getSessionId() {
        return (getUserDetails() != null ? getUserDetails().getSessionId() : null);
    }

    public String[] getRoles() {
        return (getUserDetails() != null ? getUserDetails().getRoles() : null);
    }

    public String getAirport() {
        return (getUserDetails() != null ? getUserDetails().getAirport() : null);
    }

    public String getUsername() {
        return (getUserDetails() != null ? getUserDetails().getUsername() : null);
    }

    public String toString() {
        return (getUserDetails() != null ? getUserDetails().toString() : null);
    }

}
