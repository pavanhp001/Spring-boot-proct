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

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

//import abc.xyz.pts.bcs.irisk.exception.PersistenceProcedureException;

/*
 * Common code for DAOs
 * @author DG
 * @version: $Id: AbstractDAO.java 4373 2008-10-10 13:14:22Z sali $
 */
public abstract class AbstractDAO extends JdbcDaoSupport {

    public static final String PKG_USER_SESSION_NAME = "PKG_USER_SESSION";
    private static final String VERSION = "$Id: AbstractDAO.java 4373 2008-10-10 13:14:22Z sali $";

    protected static final Logger LOG = Logger.getLogger(AbstractDAO.class);

    private TransactionTemplate txTemplate;

    private String packageName;
    private String procedureName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(final String packageName) {
        this.packageName = packageName;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(final String procedureName) {
        this.procedureName = procedureName;
    }

    /**
     * Set by Spring for transaction support.
     *
     * @param txManager
     */
    public final void setTransactionManager(final PlatformTransactionManager txManager) {
        this.txTemplate = new TransactionTemplate(txManager);
        this.txTemplate.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
    }

    protected final TransactionTemplate getTransactionTemplate() {
        return this.txTemplate;
    }

    protected SimpleJdbcCall setProcedure(SimpleJdbcCall jdbcCall, final String pacName, final String procName) {
        jdbcCall = CachingJdbcCallFactory.getCustomJdbcCall(getJdbcTemplate(), pacName, procName);
        return jdbcCall;
    }

    protected SimpleJdbcCall setFunction(SimpleJdbcCall jdbcCall, final String pacName, final String procName) {
        jdbcCall = CachingJdbcCallFactory.getCustomJdbcCall(getJdbcTemplate(), pacName, procName, true);
        return jdbcCall;
    }

    public boolean getVarcharAsBool(final String charBool) {
        return ("Y".equals(charBool));
    }

    public Calendar getAsCalendar(final Object value) {
        Calendar valueToReturn = null;
        if (value != null) {
            if (value instanceof Date) {
                Date dateValue = (Date) value;
                valueToReturn = Calendar.getInstance();
                valueToReturn.setTime(dateValue);
            } else if (value instanceof Timestamp) {
                Timestamp timestampValue = (Timestamp) value;
                valueToReturn = Calendar.getInstance();
                Date dateValue = new Date(timestampValue.getTime());
                valueToReturn.setTime(dateValue);
            }
        }
        return valueToReturn;
    }

    protected <T extends Number> Long getAsLongOrZero(final T t) {
        if (t != null) {
            return Long.valueOf(t.longValue());
        }
        return 0L;
    }

    protected <T extends Number> Long getAsLong(final T t) {
        if (t != null) {
            return Long.valueOf(t.longValue());
        }
        return null;
    }

    protected <T> T getValueOrNull(final T t) {
        if (t != null) {
            return t;
        }
        return null;
    }
}
