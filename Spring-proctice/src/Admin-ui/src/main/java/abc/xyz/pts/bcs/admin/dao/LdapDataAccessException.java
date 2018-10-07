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
package abc.xyz.pts.bcs.admin.dao;

import org.springframework.dao.DataAccessException;

public class LdapDataAccessException extends DataAccessException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public LdapDataAccessException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

}
