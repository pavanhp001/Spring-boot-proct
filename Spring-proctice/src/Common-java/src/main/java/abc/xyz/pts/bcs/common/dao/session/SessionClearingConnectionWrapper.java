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

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleConnectionWrapper;

import org.apache.log4j.Logger;

public class SessionClearingConnectionWrapper extends OracleConnectionWrapper {

    private static final Logger logger = Logger.getLogger(SessionClearingConnectionWrapper.class);
    private static final String CLEAR_CONN_STMT = "call pkg_user_session.pr_clear_connection(?)";

    private final String sessionId;

    public SessionClearingConnectionWrapper(final OracleConnection cnx, final String sessionId) {
        super(cnx);
        this.sessionId = sessionId;
    }

    @Override
    public void close() throws SQLException {

        logger.debug("Clearing application context in db");

        CallableStatement cs = null;

        try {
            cs = connection.prepareCall(CLEAR_CONN_STMT);
            cs.setString(1, sessionId);
            cs.execute();
         } catch (SQLException e) {
            logger.error("Unable to clear database application context", e);
            // Not a lot we can do, fall through to finally clause to close the statement
         } finally {
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException s) {
                    logger.warn("Exception thrown while closing statement", s);
                }
            }

        }

        // Call close on the underlying connection
        super.close();
    }

}
