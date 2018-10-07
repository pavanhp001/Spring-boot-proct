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
package abc.xyz.pts.bcs.common.dao.session.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class SessionClearingConnectionWrapper implements InvocationHandler {

    private final Connection cnx;
    private final String sessionId;

    private static final Logger logger = Logger.getLogger(SessionClearingConnectionWrapper.class);
    private static final String clearConnStmt = "call pkg_user_session.pr_clear_connection(?)";

    public SessionClearingConnectionWrapper(final Connection cnx, final String sessionId) {
        this.cnx = cnx;
        this.sessionId = sessionId;
    }

    public Object invoke(final Object proxy, final Method m, final Object[] args) throws Throwable {
        if (logger.isTraceEnabled()) {
            logger.trace("Executing method '" + m.getName() + "' on connection " + cnx.toString());
        }
        if ("close".equals(m.getName())) {
            if (logger.isDebugEnabled()) {
                logger.debug("Clearing application context in db");
            }
            CallableStatement cs = null;

            try {
                cs = cnx.prepareCall(clearConnStmt);
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
        }
        return m.invoke(cnx, args);
    }

}
