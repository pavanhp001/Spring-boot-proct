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

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.UUID;

import oracle.jdbc.OracleConnection;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StringUtils;

import abc.xyz.pts.bcs.common.dao.support.OracleConnectionCacheManagerHelper;

@Aspect
public class DataSourceSessionSettingAspect {

    private static final int PARAM_SESSION_ID = 1;
    private static final int PARAM_USERNAME = 2;
    private static final int PARAM_AIRPORT = 3;
    private static final int PARAM_ROLES = 4;

    private static final int MAX_NO_GET_CONNECTION_RETRY = 5;
    private DbSessionParameterProvider provider;
    private boolean sessionContextShared = false;

    private static final String PR_SET_SESSION_PARAMETERS = "{call pkg_user_session.pr_set_session_parameters(?, ?, ?, ?)}";
    private static final Logger LOGGER = Logger.getLogger(DataSourceSessionSettingAspect.class);

    private static final OracleConnectionCacheManagerHelper connectionCacheManagerHelper = OracleConnectionCacheManagerHelper
                    .getConnectionCacheManagerHelperInstance();

    public void setProvider(final DbSessionParameterProvider dbSessionParameterProvider) {
        this.provider = dbSessionParameterProvider;
    }

    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Object initialiseCnx(final ProceedingJoinPoint pjp) throws Throwable {
        // The contract of getConnection is to return a valid Connection object, or throw a exception,
        // so no need to check cnx for null
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Creating connection with User Details: " + provider);
        }

        boolean retry;
        int retries = 0;
        OracleConnection cnx = null;

        do {
           try {
              retry = false;

              cnx = (OracleConnection) pjp.proceed();

              if(cnx == null)
             {
                throw new SQLException("Null connection returned by driver"); // This gets caught immediately below
            }

              // Set the appliocation context up
              return setSessionContext(cnx);
           } catch(final SQLException e) {
               LOGGER.error("Connection creation and session setting attempt threw exception", e);

               if(cnx != null) {
                   try {
                      cnx.close(OracleConnection.INVALID_CONNECTION); // Oracle specific - mark cnx as invalid to force physical cnx closure
                   } catch (final SQLException se) {
                      LOGGER.warn("Unable to close connection", se);
                   }
               }


               // Only retry if we're less than max attempts
               retry = (retries++ < MAX_NO_GET_CONNECTION_RETRY);      // && !connectionCacheManagerHelper.isValid(cnx, e);

               if (!retry) {
                   LOGGER.error("Max connection retries exceeded");
                   throw e;
               } else {
                  // Attempt to clear any invalid connections (including the one we've just used)
                  connectionCacheManagerHelper.refreshConnectionPool();

                  LOGGER.warn("Failed to acquire database connection, about to make attempt " + retries + " of "
                        + MAX_NO_GET_CONNECTION_RETRY);
               }
           }
        } while(retry);

        return null;
    }

    public Object setSessionContext(final OracleConnection cnx) throws SQLException  {
        LOGGER.debug("Setting up application context in db");

        CallableStatement cs = null;
        final String sessionId = getSessionId();

        try {
          cs = cnx.prepareCall(PR_SET_SESSION_PARAMETERS);

          // Just use numeric indexes - life is too short to have to query database metadata
          cs.setString(PARAM_SESSION_ID, sessionId);
          cs.setString(PARAM_USERNAME, provider.getUsername());
          cs.setString(PARAM_AIRPORT, provider.getAirport());
          cs.setString(PARAM_ROLES, StringUtils.arrayToCommaDelimitedString(provider.getRoles()));

          cs.execute();
        } finally {
          if(cs != null) {
              try {
                 cs.close();
              } catch (final SQLException e) {
                 LOGGER.warn("Failed to close statement but continuing", e);
              }
          }
        }

        // Wrap the real Connection object in a proxy that will reset the db session params before closing

        if (sessionContextShared) {
            LOGGER.debug("Application context set successfully, shared connection is not being proxied.");
            return cnx;
        } else {
            LOGGER.debug("Application context set successfully, connection is proxied.");
            return new SessionClearingConnectionWrapper(cnx, sessionId);
        }
    }

    public boolean getSessionContextShared() {
        return sessionContextShared;
    }

    public void setSessionContextShared(final boolean sessionContextShared) {
        this.sessionContextShared = sessionContextShared;
    }

    private String getSessionId() {
        if     ( provider.getSessionId() == null && provider.getUsername() != null && provider.getRoles() != null ) {
                return UUID.randomUUID().toString();
        } else {
            return provider.getSessionId();
        }
    }
}
