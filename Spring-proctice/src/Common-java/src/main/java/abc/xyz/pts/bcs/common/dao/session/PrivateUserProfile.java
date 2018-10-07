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
package abc.xyz.pts.bcs.common.dao.session;

public interface PrivateUserProfile {

    /**
     * Store the user details in a thread local variable for the duration of the call.
     *
     * @param userDetails
     *            the security user details to store.
     */
    void setUserDetailsForThread(DbSessionParameterProvider userDetails);

    /**
     * Clear the stored user details from the thread local variable.
     */
    void clearUserDetailsForThread();

    DbSessionParameterProvider getUserDetails();

}
