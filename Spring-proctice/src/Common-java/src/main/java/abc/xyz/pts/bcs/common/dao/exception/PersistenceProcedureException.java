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
package abc.xyz.pts.bcs.common.dao.exception;

import java.sql.SQLException;

/*
 * @version: $Id: PersistenceProcedureException.java 6808 2009-03-27 16:34:01Z jbenson $
 */
public class PersistenceProcedureException extends RuntimeException {

    private static final long serialVersionUID = -7170183825917837773L;

    public PersistenceProcedureException() {
        super();
    }

    public PersistenceProcedureException(final String message) {
        super(message);
    }

    public PersistenceProcedureException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PersistenceProcedureException(final Throwable cause) {
        super(cause);
    }

    public PersistenceProcedureException(final SQLException sqle) {
        super(sqle);
    }

    public String getOraErr() {
        // set a default error code
        String oraErr = "99999";
        if (this.getCause() instanceof SQLException) {
            // The error code is also known as vendorCode.
            oraErr = String.valueOf(((SQLException) this.getCause()).getErrorCode());
            // String sqlState = String.valueOf(((SQLException)this.getCause()).getSQLState());
        }
        return oraErr;
    }
}
