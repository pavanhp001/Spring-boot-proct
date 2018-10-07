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
package abc.xyz.pts.bcs.common.dao.support;

import java.sql.SQLException;

import oracle.jdbc.pool.OracleConnectionCacheManager;

import org.apache.log4j.Logger;

public class OracleConnectionCacheMangerHelper {

    static final int[] errorCodes = { 17410, 17447, 17002, 17143 };

    private static final Logger LOGGER = Logger.getLogger(OracleConnectionCacheMangerHelper.class);

    public static OracleConnectionCacheManager getConnectionCacheManagerInstance() {
        OracleConnectionCacheManager connectionCachemanager = null;
        try {
            connectionCachemanager = OracleConnectionCacheManager.getConnectionCacheManagerInstance();
            connectionCachemanager.setConnectionErrorCodes(errorCodes);
        } catch (SQLException e) {
            LOGGER.error("Failed to get OracleConnectionCacheManger. " + e.getErrorCode() + " " + e.getCause() + " "
                    + e.getMessage());
        }
        return connectionCachemanager;
    }
}
