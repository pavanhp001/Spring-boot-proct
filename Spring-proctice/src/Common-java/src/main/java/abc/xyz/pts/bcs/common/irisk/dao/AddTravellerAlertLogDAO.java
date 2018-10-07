/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.irisk.dao;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;

import abc.xyz.pts.bcs.common.dao.exception.UniqueKeyViolationException;
import abc.xyz.pts.bcs.common.irisk.dto.PrAddTravellerAlertLog;

public class AddTravellerAlertLogDAO {
    private SimpleJdbcInsert insertTravellerAlertLog;
    private OracleSequenceMaxValueIncrementer oracleSequenceMaxValueIncrementer;
    private GetUserNameDAO getUserNameDAO;

    public GetUserNameDAO getGetUserNameDAO() {
        return getUserNameDAO;
    }
    public void setGetUserNameDAO(final GetUserNameDAO getUserNameDAO) {
        this.getUserNameDAO = getUserNameDAO;
    }
    public void setDataSource(final DataSource dataSource) {
//        simpleJdbcCall = new SimpleJdbcCall(dataSource).withCatalogName("pkg_user_session").withFunctionName("f_get_username").withoutProcedureColumnMetaDataAccess();
//        this.funcGetUserName = new SimpleJdbcCall(dataSource).withCatalogName("pkg_user_session").withFunctionName("f_get_username").withoutProcedureColumnMetaDataAccess();
        this.insertTravellerAlertLog = new SimpleJdbcInsert(dataSource).withTableName("TRAVELLER_ALERT_LOGS");
        this.insertTravellerAlertLog.setOverrideIncludeSynonymsDefault(true);
        this.oracleSequenceMaxValueIncrementer = new OracleSequenceMaxValueIncrementer(dataSource, "TRAAL_ID_SEQ");
    }
    public PrAddTravellerAlertLog add(final PrAddTravellerAlertLog prAddTravellerAlertLog ) {
        final Map <String, Object> parameters = new HashMap<String, Object>(14);
        parameters.put("ID", oracleSequenceMaxValueIncrementer.nextLongValue());
        parameters.put("TRAA_ID", prAddTravellerAlertLog.getTravellerAlertId());
        parameters.put("REC_USERNAME", getUserNameDAO.execute());
        parameters.put("ACTION_DATETIME", Calendar.getInstance());
        parameters.put("ACTION_TYPE", (prAddTravellerAlertLog.getActionType() != null ?  prAddTravellerAlertLog.getActionType().toUpperCase() : null));
        parameters.put("REMARKS", prAddTravellerAlertLog.getRemark());
        parameters.put("REC_FULL_NAME", prAddTravellerAlertLog.getRecFullName());
        parameters.put("AIRPORT_CODE", prAddTravellerAlertLog.getAirportCode());
        parameters.put("RE_ROUTED_AIRPORT_CODE", prAddTravellerAlertLog.getReRoutedAirportCode());
        parameters.put("ROLES_NOTIFIED", prAddTravellerAlertLog.getRolesNotified());
        parameters.put("MEDIA_TYPE", prAddTravellerAlertLog.getMediaType());
        parameters.put("ACTION_STATUS", prAddTravellerAlertLog.getActionStatus());
        parameters.put("ACTION_ERROR_MESSAGE", prAddTravellerAlertLog.getActionErrorMessage());
        parameters.put("RECIPIENT_CATEGORY", prAddTravellerAlertLog.getRecipientCategory());
        try {
            insertTravellerAlertLog.execute(parameters);
        } catch (final DataIntegrityViolationException e) {
            final SQLException exception = (SQLException) e.getCause();
            throw new UniqueKeyViolationException(exception);
        }
        return prAddTravellerAlertLog;
    }




}
