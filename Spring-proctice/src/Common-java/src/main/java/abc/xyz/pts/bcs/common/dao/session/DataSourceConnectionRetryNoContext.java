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

import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.OracleConnection;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import abc.xyz.pts.bcs.common.dao.support.OracleConnectionCacheManagerHelper;

@Aspect
public class DataSourceConnectionRetryNoContext {

    private static final int MAX_NO_GET_CONNECTION_RETRY = 5;

    private static final Logger logger = Logger.getLogger(DataSourceConnectionRetryNoContext.class);

    private static final OracleConnectionCacheManagerHelper connectionCacheManagerHelper = OracleConnectionCacheManagerHelper
                    .getConnectionCacheManagerHelperInstance();

    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Connection getConnection(final ProceedingJoinPoint pjp) throws Throwable {
        boolean retry = false;
        int retries = 0;
        OracleConnection cnx = null;

        do {

            // Get a connection. It is assumed that Oracle's ValidateOnBorrow property is set to true
            try {
                retry = false;

                // Get the connection
                cnx = (OracleConnection) pjp.proceed();

                // Oracle will return null if it fails to create a connection in certain circumstances
                if (cnx == null) {
                    throw new SQLException("DataSource returned null");
                }
            } catch (SQLException e) {
                logger.error("Connection creation attempt threw exception", e);

                try {
                   if(cnx != null) {
                      cnx.close(OracleConnection.INVALID_CONNECTION); // Oracle specific extension - mark connection as invalid
                   }
                } catch (SQLException s) {
                   logger.warn("Exception thrown while closing connection", s);
                }

                // Only retry if we haven't reach max retries yet
                retry = (retries++ <= MAX_NO_GET_CONNECTION_RETRY); // && !connectionCacheManagerHelper.isValid(cnx, e);

                if (!retry) {
                    logger.error("Max retries exceeded, giving up");
                    throw e;
                } else {
                    // Clear down the cache if we are retrying
                    logger.warn("Attempt " + retries + " of " + MAX_NO_GET_CONNECTION_RETRY
                        + " failed to create database connection but will try again.");

                    connectionCacheManagerHelper.refreshConnectionPool();
                }
            }
        } while (retry);

        return cnx;
    }


}
