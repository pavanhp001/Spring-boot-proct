/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import abc.xyz.pts.bcs.common.bean.UserDetails;
import abc.xyz.pts.bcs.common.dao.support.query.AbstractBuilder;
import abc.xyz.pts.bcs.common.dao.support.query.CountBuilder;
import abc.xyz.pts.bcs.common.dao.support.query.InsertBuilder;
import abc.xyz.pts.bcs.common.dao.support.query.QueryBuilder;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

public class JdbcUtil {

    private static final Logger LOG = Logger.getLogger(JdbcUtil.class);

    private static final int UNKNOWN_DB_ERROR = -1;
    private static final String DB_DATE_FORMAT = "dd/MM/yyyy";
    private static final int STATEMENT_ID_MAX_LENGTH = 30;

    /**
     * Return tableCommand with actual or estimated row count set
     * If tableCommand.pageSize = -1, no paging is assumed
     *
     * @param query
     *            Query string with ? place holders
     * @param values
     *            Query values
     * @param template
     *            jdbc template
     * @param tableCommand
     *            table command to set total results
     * @return table command with total results set.
     */
    public static TableActionCommand getRowEstimatedRowCount(
            final String query, final Object[] values, final JdbcTemplate template, final TableActionCommand tableCommand) {

        if (!tableCommand.isTotalResultsAvailable()) { // only estimate for initial query

            final ServletRequestAttributes sessionAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            String sessionId = sessionAttributes.getSessionId();
            sessionId = sessionId.substring(0, STATEMENT_ID_MAX_LENGTH);

            String explainSql = "EXPLAIN PLAN SET statement_id = '" + sessionId + "' FOR SELECT * " + query;

            for (int i = 0; i < values.length; i++) {
                final Object value = values[i];
                String valueString = null;

                if (value instanceof String) {
                    valueString = "'" + StringEscapeUtils.escapeSql((String) value) + "'";

                } else if (value instanceof Date) {
                    valueString = "(to_date('"
                        + new SimpleDateFormat(DB_DATE_FORMAT).format((Date) value) + "', '" + DB_DATE_FORMAT + "'))";

                } else if (value instanceof Calendar) {

                    final Calendar cal = (Calendar) value;
                    final Date date = DateStringUtils.getDateFromCalendarOrNull(cal);
                    final String dateString = DateStringUtils.getStringFromDate(date);
                    valueString = "(to_date('"
                        + dateString + "', '" + DB_DATE_FORMAT + "'))";

                } else {
                    valueString = String.valueOf(value);
                }

                explainSql = explainSql.replaceFirst("\\?", valueString);

            }

            template.execute(explainSql);

            final String countQuery = " SELECT cardinality  " + " FROM plan_table "
                                + " WHERE statement_id = '" + sessionId + "' "
                                + " AND id = 0 ";

            final long estimate = template.queryForInt(countQuery);

            final String cleanupSgl = "DELETE FROM plan_table WHERE statement_id = '" + sessionId + "'";
            template.execute(cleanupSgl);

            tableCommand.setTotalResults(estimate);
            tableCommand.setRowCountEstimated(true);
        }

        return tableCommand;
    }

    /**
     * Exact number of results (page size * (page number -1) + number of results
     * in current batch)
     *
     * @param tableCommand
     * @param currentResults
     * @return
     */
    private static TableActionCommand getExactRowCount(
            final TableActionCommand tableCommand, final List<?> currentResults) {

        tableCommand.setTotalResults(
                tableCommand.getPageSize()
                * (tableCommand.getPageNumber() - 1)
                + currentResults.size());

        tableCommand.setRowCountEstimated(false);

        return tableCommand;
    }

    /**
     * Can we execute the query?
     *
     * @param template
     * @param builder
     * @return
     */
    private static boolean isTooManyResultsToHandle(final JdbcTemplate template, final QueryBuilder builder) {

        final TableActionCommand tblCmd = builder.getTableCommand();

        final boolean needEstimatedRowCount = tblCmd.isMaxRowCountSet() && !tblCmd.isTotalResultsAvailable();

        if (needEstimatedRowCount) {
            getRowEstimatedRowCount(builder.getEstimationSql(), builder.getEstimationCriteriaValues(), template, tblCmd);
        }
        if (needEstimatedRowCount && tblCmd.getTotalResults() > tblCmd.getMaxRowCount()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return a page of results as set by tableCommand or an empty list on error
     *  (error number set in tableCommand)
     *
     * @param <T>
     * @param template
     * @param rowMapper
     * @param builder
     * @return Paged results
     */
    public static <T> List<T> executeQuery(
            final JdbcTemplate template, final RowMapper<T> rowMapper, final QueryBuilder builder) {

        return executeQuery(template, rowMapper, builder, true);
    }

    public static <T> List<T> executeQuery(
            final JdbcTemplate template, final RowMapper<T> rowMapper, final QueryBuilder builder, final boolean countRows) {

        final TableActionCommand tblCmd = builder.getTableCommand();

        template.setFetchSize(tblCmd.getPageSize() + 1);

        try {
            if (isTooManyResultsToHandle(template, builder)) {
                tblCmd.setErrorNumber(TableActionCommand.ERROR_TOO_MANY_RESULTS);
                return Collections.emptyList();
            }

            final List<T> results = template.query(builder.getPagedQuerySql(), builder.getCriteriaValues(), rowMapper);

            // Estimate/Count rows ?
            if (countRows == true) {
                setNumberOfRows(template, builder, results);
            }
            return results;

        } catch (final Exception usqle) {
            tblCmd.setErrorNumber(getErrorCodeAndLogException(usqle));
            return Collections.emptyList();
        }
    }

    /**
     * Estimate of Count the total rows.
     *
     * @param template
     * @param builder
     * @param results
     */
    public static void setNumberOfRows(
            final JdbcTemplate template, final QueryBuilder builder, final List<?> results) {

        TableActionCommand tableCommand = builder.getTableCommand();
        try {
            // We need to estimate if there is more than a page worth of data
            tableCommand = getExactRowCount(tableCommand, results);

            // Ensure we have page worth of data
            builder.keepPageData(results);

        } catch (final Exception usqle) {
            tableCommand.setErrorNumber(getErrorCodeAndLogException(usqle));
        }
    }

    /**
     * Return all results (non-paged) or an empty list on error (error number
     *  set in tableCommand)
     *
     * @param <T>
     * @param template
     * @param rowMapper
     * @param builder
     * @return All results
     */
    public static <T> List<T> executeAllQuery(
            final JdbcTemplate template, final RowMapper<T> rowMapper, final QueryBuilder builder) {

        try {
            final List<T> results = template.query(
                    builder.getSql(), builder.getCriteriaValues(), rowMapper);

            return results;

        } catch (final Exception usqle) {

            builder.getTableCommand().setErrorNumber(
                    getErrorCodeAndLogException(usqle));

            return new ArrayList<T>();
        }
    }

    /**
     * Return all results (non-paged) and set total results size to Table Command
     *  or an empty list on error (error number set in tableCommand)
     *
     * @param <T>
     * @param template
     * @param rowMapper
     * @param builder
     * @param tableCommand
     * @return
     */
    public static <T> List<T> executeAllQuery(
            final JdbcTemplate template, final RowMapper<T> rowMapper, final QueryBuilder builder, final TableActionCommand tableCommand) {

        final List<T> results = executeAllQuery(template, rowMapper, builder);

        tableCommand.setTotalResults(results.size());
        tableCommand.setPrintAll(true);

        return results;
    }

    /**
     * Return single result of id query or null on error (error number set in tableCommand)
     *
     * @param <T>
     * @param template
     * @param rowMapper
     * @param builder
     * @return Single result
     */
    public static <T> T executeQueryById(
            final JdbcTemplate template, final RowMapper<T> rowMapper, final QueryBuilder builder) {

        try {

            final List<T> results = template.query(
                    builder.getSingleResultQuerySql(), builder.getCriteriaValues(), rowMapper);

            if (results != null && !results.isEmpty()) {

                builder.getTableCommand().setTotalResults(results.size());
                return results.get(0);
            }

        } catch (final Exception usqle) {

            builder.getTableCommand().setErrorNumber(
                    getErrorCodeAndLogException(usqle));

        }
        return null;

    }

    /**
     * Update based on builder args. Number of update rows (totalResults) or
     *  error number (errorNumber) set in tableCommand
     *
     * @param template
     * @param builder
     */
    public static void executeUpdate(final JdbcTemplate template, final AbstractBuilder builder) {

        try {
            final int updatedRows = template.update(builder.getSql(), builder.getCriteriaValues());
            builder.getTableCommand().setTotalResults(updatedRows);

        } catch (final Exception usqle) {

            builder.getTableCommand().setErrorNumber(
                    getErrorCodeAndLogException(usqle));
        }
    }

    /**
     * Inserts the row and returns the generated id to the caller.
     *
     * @param template
     *            {@link JdbcTemplate}
     * @param builder
     *            {@link InsertBuilder}
     * @param idColumnName
     *            the name of the auto-generated column name eg : 'id'
     * @param objectConversionStrategies
     *            the conversion strategies for a specific type of the object.
     *            For example, the clients are free to pass a
     *            {@link ObjectConversionStrategy} implementation that converts
     *            from {@link Date} to {@link java.sql.Date}.
     * @return long generated id.
     */
    public static long insertAndReturnGeneratedKey(
            final JdbcTemplate template,
            final InsertBuilder builder,
            final String idColumnName,
            final ObjectConversionStrategy<? extends Object>... objectConversionStrategies) {

        final KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            template.update(
                    new PreparedStatementCreator() {

                        @Override
                        public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {

                            final PreparedStatement preparedStatement =
                                connection.prepareStatement(builder.getSql(), new String[] { idColumnName });

                            int index = 1;
                            for (final Object criteriaValue : builder.getCriteriaValues()) {

                                preparedStatement.setObject(
                                        index ++,
                                        convertObject(criteriaValue, objectConversionStrategies));
                            }
                            return preparedStatement;
                        }
                    }, keyHolder);

        } catch (final Exception usqle) {
            builder.getTableCommand().setErrorNumber(
                    getErrorCodeAndLogException(usqle));
        }
        return keyHolder.getKey().longValue();
    }

    /**
     * Returns the standard date conversion strategy that converts {@link Date} to {@link java.sql.Date}
     *
     * @return {@link ObjectConversionStrategy}
     */
    public static ObjectConversionStrategy<java.sql.Date> getDateConversionStrategy() {
        return new ObjectConversionStrategy<java.sql.Date>() {

            private Date date;

            @Override
            public java.sql.Date getTarget() {
                return new java.sql.Date(date.getTime());
            }

            @Override
            public boolean accept(final Object object) {
                if (object instanceof Date) {
                    date = (Date) object;
                    return true;
                }
                return false;
            }
        };
    }

    private static Object convertObject(
            final Object criteriaValue,
            final ObjectConversionStrategy<? extends Object>... objectConversionStrategies) {

        for (final ObjectConversionStrategy<? extends Object> conversionStrategy : objectConversionStrategies) {
            if (conversionStrategy.accept(criteriaValue)) {
                return conversionStrategy.getTarget();
            }
        }
        return criteriaValue;
    }

    /**
     * Return row count for query or 0 on error, error number set in tableCommand
     *
     * @param template
     * @param builder
     * @return
     */
    public static int executeQueryForCount(final JdbcTemplate template, final CountBuilder builder) {
        try {
            if (LOG.isInfoEnabled()) {
                LOG.info(" Count SQL - " + builder.getQueryForCountSql());
                LOG.info(" Count SQL Criteria - " + Arrays.toString(builder.getCriteriaValues()));
            }
            final int count = template.queryForInt(
                    builder.getQueryForCountSql(), builder.getCriteriaValues());
            return count;

        } catch (final Exception usqle) {

            builder.getTableCommand().setErrorNumber(
                    getErrorCodeAndLogException(usqle));

            return 0;
        }
    }

    /**
     * Return error code, log exception
     *
     * @param usqle
     * @return Oracle error code (as negative number) or UNKNOWN_DB_ERROR
     */
    public static int getErrorCodeAndLogException(final Exception usqle) {

        LOG.error(usqle, usqle);

        final Throwable ex = usqle.getCause();
        if (ex instanceof SQLException) {
            final SQLException sqlException = (SQLException) ex;

            return -1 * sqlException.getErrorCode();

        } else {
            return UNKNOWN_DB_ERROR;
        }

    }

    /**
     * <code>getUserDetails</code> is used to get the User Information from the Request Context.
     * @return
     */
    public static UserDetails getUserDetails() {
        /* Getting the User Name */
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        final UserDetails userDetails = (UserDetails) request.getAttribute(Constants.USER_PROFILE_KEY);

        return userDetails;
    }
}
