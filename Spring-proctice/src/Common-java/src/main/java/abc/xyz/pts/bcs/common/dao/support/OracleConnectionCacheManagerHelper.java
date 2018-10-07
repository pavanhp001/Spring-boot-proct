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

import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleConnectionCacheManager;

import org.apache.log4j.Logger;

public class OracleConnectionCacheManagerHelper {

    static final int[] errorCodes = { 17410, 17447, 17002, 17143 };

    private static final Logger logger = Logger.getLogger(OracleConnectionCacheMangerHelper.class);
    private static final OracleConnectionCacheManagerHelper helperInstance = new OracleConnectionCacheManagerHelper();
    private final OracleConnectionCacheManager connectionCacheManager;

    private OracleConnectionCacheManagerHelper() {
       try {
          connectionCacheManager = OracleConnectionCacheManager.getConnectionCacheManagerInstance();
          connectionCacheManager.setConnectionErrorCodes(errorCodes);
       } catch(SQLException e) {
          // This is a serious error!
          throw new RuntimeException("Unable to get Oracle Connection Cache Manager", e);
       }
    }

    public static OracleConnectionCacheManagerHelper getConnectionCacheManagerHelperInstance() {
        return helperInstance;
    }

    public void refreshConnectionPool() {
        try {
            for (String cacheName : connectionCacheManager.getCacheNameList()) {

                connectionCacheManager.refreshCache(cacheName, OracleConnectionCacheManager.REFRESH_INVALID_CONNECTIONS);

                if (logger.isDebugEnabled()) {
                    logger.debug("Number of active database connections: "
                            + connectionCacheManager.getNumberOfActiveConnections(cacheName));
                    logger.debug("Number of available database connections: "
                            + connectionCacheManager.getNumberOfAvailableConnections(cacheName));
                   logger.debug(cacheName + " cache invalid database connections were refreshed.");
                }
            }
        } catch (SQLException e) {
            logger.error("Couldn't reset connection cache, but continuing anyway", e);
        }
    }

    public boolean isValid(final Connection cnx, final SQLException e) {
       boolean result = false;
       try {
          result = cnx != null && !connectionCacheManager.isFatalConnectionError(e) && cnx.isValid(1);
       } catch (SQLException ex) {
          logger.error("Unable to check connection validity, assuming connection invalid",ex);
       }

       return result;
    }
}
