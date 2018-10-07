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
package abc.xyz.pts.bcs.common.dao;

import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Common persistence utility class
 *
 * @deprecated 13/12/10
 */
public final class PersistenceUtil {

//    public static final String PKG_USER_SESSION_NAME = "PKG_USER_SESSION";

    public static final int NO_RESULTS = 0;
    public static final int UNKNOWN_DB_ERROR = -1;

    private static final int DB_NO_DATA_EXCEPTION = 1403;

    private static final Logger LOG = Logger.getLogger(PersistenceUtil.class);

    private PersistenceUtil() {
    }

    /**
     * Return an error code for the given exception - all error codes are negative. Logs ERROR.
     *
     * @param usqle
     *            Caught exception
     * @return NO_RESULTS, UNKNOWN_DB_ERROR or sql error code
     */
    public static int getErrorCode(final Exception usqle) {
        Throwable ex = usqle.getCause();

        if (ex instanceof SQLException) {
            SQLException sqlException = (SQLException) ex;
            if (sqlException.getErrorCode() == DB_NO_DATA_EXCEPTION || sqlException.getErrorCode() == NO_RESULTS) {
                LOG.debug(usqle);
                return NO_RESULTS;
            } else {
                LOG.error(usqle);
                return -1 * sqlException.getErrorCode();
            }
        } else {
            LOG.error(usqle);
            return UNKNOWN_DB_ERROR;
        }

    }

}
