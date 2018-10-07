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
package abc.xyz.pts.bcs.common.dao.impl;

import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.SqlLobValue;

import abc.xyz.pts.bcs.common.dao.DownloadStatsReportDAO;
import abc.xyz.pts.bcs.common.dao.support.AbstractDAO;
import abc.xyz.pts.bcs.common.dao.support.CachingJdbcCallFactory;
import abc.xyz.pts.bcs.common.dao.support.CustomRowMapper;
import abc.xyz.pts.bcs.common.dao.support.JdbcCallConfigurer;
import abc.xyz.pts.bcs.common.dto.GeneratedReportRcRec;
import abc.xyz.pts.bcs.common.dto.PrAddGeneratedReport;
import abc.xyz.pts.bcs.common.dto.PrGetReport;
import abc.xyz.pts.bcs.common.dto.PrGetReportImage;
import abc.xyz.pts.bcs.common.dto.PrRequeryReport;
import abc.xyz.pts.bcs.common.dto.PrUpdateGeneratedReport;

/**
 * DAO for statistic reports downloads.
 */
public class DownloadStatsReportDAOImpl extends AbstractDAO implements DownloadStatsReportDAO {
    private SimpleJdbcCall getReportsCall;
    private SimpleJdbcCall requeryReportsCall;
    private SimpleJdbcCall addReportsCall;
    private SimpleJdbcCall getReportImageCall;
    private SimpleJdbcCall updateReportCall;

    public static class PrGetReportConfigurer extends JdbcCallConfigurer<SimpleJdbcCall> {
        @Override
        public SimpleJdbcCall configure(final SimpleJdbcCall simpleJdbcCall) {
            simpleJdbcCall.withoutProcedureColumnMetaDataAccess();
            simpleJdbcCall.declareParameters(new SqlParameter(P_USERNAME, Types.VARCHAR), new SqlParameter(
                    P_NUM_RECORDS, Types.BIGINT), new SqlOutParameter(P_QUERY_COUNT, Types.BIGINT),
                    new SqlOutParameter(P_GENERATED_REPORT_RC, OracleTypes.CURSOR,
                            new CustomRowMapper<GeneratedReportRcRec>(GeneratedReportRcRec.class)),
                    new SqlOutParameter(P_MESSAGE, Types.VARCHAR));
            simpleJdbcCall.compile();
            return simpleJdbcCall;
        }
    }

    public static class PrRequeryReportConfigurer extends JdbcCallConfigurer<SimpleJdbcCall> {
        @Override
        public SimpleJdbcCall configure(final SimpleJdbcCall simpleJdbcCall) {
            simpleJdbcCall.withoutProcedureColumnMetaDataAccess();
            simpleJdbcCall.declareParameters(new SqlParameter(P_START_RECORD, Types.BIGINT), new SqlParameter(
                    P_NUM_RECORDS, Types.BIGINT), new SqlParameter(P_ORDER_BY_COL, Types.VARCHAR), new SqlParameter(
                    P_ASC_DESC, Types.VARCHAR), new SqlOutParameter(P_GENERATED_REPORT_RC, OracleTypes.CURSOR,
                    new CustomRowMapper<GeneratedReportRcRec>(GeneratedReportRcRec.class)), new SqlOutParameter(
                    P_MESSAGE, Types.VARCHAR));
            simpleJdbcCall.compile();
            return simpleJdbcCall;
        }
    }

    public static class PrAddGeneratedReportConfigurer extends JdbcCallConfigurer<SimpleJdbcCall> {
        @Override
        public SimpleJdbcCall configure(final SimpleJdbcCall simpleJdbcCall) {
            simpleJdbcCall.withoutProcedureColumnMetaDataAccess();
            simpleJdbcCall.declareParameters(new SqlParameter(P_USERNAME, Types.VARCHAR), new SqlParameter(
                    P_REPORT_NAME, Types.VARCHAR), new SqlParameter(P_REPORT_PARAMETERS, Types.VARCHAR),
                    new SqlParameter(P_REPORT_FORMAT, Types.VARCHAR), new SqlParameter(P_STATUS, Types.VARCHAR),
                    new SqlOutParameter(P_GENERATED_REPORT_ID, Types.BIGINT), new SqlOutParameter(P_CREATED_DATETIME,
                            Types.TIMESTAMP), new SqlOutParameter(P_MESSAGE, Types.VARCHAR));
            simpleJdbcCall.compile();
            return simpleJdbcCall;
        }
    }

    public static class PrGetReportImageConfigurer extends JdbcCallConfigurer<SimpleJdbcCall> {
        @Override
        public SimpleJdbcCall configure(final SimpleJdbcCall simpleJdbcCall) {
            simpleJdbcCall.withoutProcedureColumnMetaDataAccess();
            simpleJdbcCall.declareParameters(new SqlParameter(P_GENERATED_REPORT_ID, Types.BIGINT),
                    new SqlOutParameter(P_REPORT_NAME, Types.VARCHAR), new SqlOutParameter(P_CREATED_DATETIME,
                            Types.TIMESTAMP), new SqlOutParameter(P_REPORT_FORMAT, Types.VARCHAR), new SqlOutParameter(
                            P_REPORT_IMAGE, Types.BLOB), new SqlOutParameter(P_MESSAGE, Types.VARCHAR));
            simpleJdbcCall.compile();
            return simpleJdbcCall;
        }
    }

    public static class PrUpdateGeneratedReportDAOConfigurer extends JdbcCallConfigurer<SimpleJdbcCall> {
        @Override
        public SimpleJdbcCall configure(final SimpleJdbcCall simpleJdbcCall) {
            simpleJdbcCall.withoutProcedureColumnMetaDataAccess();
            simpleJdbcCall.declareParameters(new SqlParameter(P_GENERATED_REPORT_ID, Types.BIGINT), new SqlParameter(
                    P_STATUS, Types.VARCHAR), new SqlParameter(P_REPORT_IMAGE, Types.BLOB), new SqlOutParameter(
                    P_MESSAGE, Types.VARCHAR));
            simpleJdbcCall.compile();
            return simpleJdbcCall;
        }
    }

    public PrGetReport getReports(final PrGetReport prGetReport) {
        getReportsCall = CachingJdbcCallFactory.getSimpleJdbcCall(getJdbcTemplate(), PKG_GENERATED_REPORT,
                PR_GET_REPORTS, new PrGetReportConfigurer());

        // IN PARAMETERS
        SqlParameterSource in = new MapSqlParameterSource().addValue(P_USERNAME, prGetReport.getUsername()).addValue(
                P_NUM_RECORDS, prGetReport.getNumRecords());
        Map<String, Object> out = getReportsCall.execute(in);

        // OUT PARAMETERS
        List<GeneratedReportRcRec> list = (List<GeneratedReportRcRec>) out.get(P_GENERATED_REPORT_RC);
        prGetReport.getPGeneratedReportRc().addAll(list);

        prGetReport.setQueryCount(getAsLongOrZero((Number) out.get("P_QUERY_COUNT")));
        return prGetReport;
    }

    public PrRequeryReport getReports(final PrRequeryReport prRequeryReport) {
        requeryReportsCall = CachingJdbcCallFactory.getSimpleJdbcCall(getJdbcTemplate(), PKG_GENERATED_REPORT,
                PR_REQUERY_REPORTS, new PrRequeryReportConfigurer());
        SqlParameterSource in = new MapSqlParameterSource().addValue(P_START_RECORD, prRequeryReport.getStartRecord())
                .addValue(P_NUM_RECORDS, prRequeryReport.getNumRecords()).addValue(P_ORDER_BY_COL,
                        prRequeryReport.getOrderByCol()).addValue(P_ASC_DESC, prRequeryReport.getAscDesc());
        Map<String, Object> out = requeryReportsCall.execute(in);

        List<GeneratedReportRcRec> list = (List<GeneratedReportRcRec>) out.get(P_GENERATED_REPORT_RC);
        prRequeryReport.getPGeneratedReportRc().addAll(list);
        // IS THIS CORRECT???
        // prRequeryReport.setNumRecords(Long.valueOf(prRequeryReport.getPGeneratedReportRc().size()));
        return prRequeryReport;
    }

    public PrAddGeneratedReport addReport(final PrAddGeneratedReport prAddGeneratedReport) {
        addReportsCall = CachingJdbcCallFactory.getSimpleJdbcCall(getJdbcTemplate(), PKG_GENERATED_REPORT,
                PR_ADD_GENERATED_REPORT, new PrAddGeneratedReportConfigurer());

        SqlParameterSource in = new MapSqlParameterSource().addValue(P_USERNAME, prAddGeneratedReport.getUsername())
                .addValue(P_REPORT_NAME, prAddGeneratedReport.getReportName()).addValue(P_REPORT_PARAMETERS,
                        prAddGeneratedReport.getReportParameters()).addValue(P_REPORT_FORMAT,
                        prAddGeneratedReport.getReportFormat()).addValue(P_STATUS, prAddGeneratedReport.getStatus());

        Map<String, Object> out = addReportsCall.execute(in);

        prAddGeneratedReport.setGeneratedReportId(getAsLongOrZero((Number) out.get("P_GENERATED_REPORT_ID")));

        return prAddGeneratedReport;
    }

    public PrGetReportImage findReportById(final PrGetReportImage prGetReportImage) {
        getReportImageCall = CachingJdbcCallFactory.getSimpleJdbcCall(getJdbcTemplate(), PKG_GENERATED_REPORT,
                PR_GET_REPORT_IMAGE, new PrGetReportImageConfigurer());

        // IN PARAMETERS
        SqlParameterSource in = new MapSqlParameterSource().addValue(P_GENERATED_REPORT_ID, prGetReportImage
                .getGeneratedReportId());
        Map<String, Object> out = getReportImageCall.execute(in);

        // OUT PARAMETERS
        prGetReportImage.setReportFormat((String) out.get(P_REPORT_FORMAT));
        prGetReportImage.setReportName((String) out.get(P_REPORT_NAME));
        Calendar cal = Calendar.getInstance();
        Date createTime = (Date) out.get(P_CREATED_DATETIME);
        if (createTime != null) {
            cal.setTime(createTime);
        }
        prGetReportImage.setCreatedDatetime(cal);
        if (out.get(P_REPORT_IMAGE) != null) {
            Blob report = (Blob) out.get(P_REPORT_IMAGE);
            long startIndex = 1;
            try {
                int length = Long.valueOf(report.length()).intValue();
                prGetReportImage.setReportImage(report.getBytes(startIndex, length));
            } catch (SQLException sqlex) {
                LOG.error(sqlex);
            }
        }
        return prGetReportImage;
    }

    public PrUpdateGeneratedReport updateReport(final PrUpdateGeneratedReport prUpdateGeneratedReport) {
        updateReportCall = CachingJdbcCallFactory.getSimpleJdbcCall(getJdbcTemplate(), PKG_GENERATED_REPORT,
                PR_UPDATE_GENERATED_REPORT, new PrUpdateGeneratedReportDAOConfigurer());
        // IN PARAMETERS
        SqlLobValue sqlLobValue = new SqlLobValue(prUpdateGeneratedReport.getReportImage());
        SqlParameterSource in = new MapSqlParameterSource().addValue(P_GENERATED_REPORT_ID,
                prUpdateGeneratedReport.getGeneratedReportId()).addValue(P_STATUS, prUpdateGeneratedReport.getStatus())
                .addValue(P_REPORT_IMAGE, sqlLobValue);

        // CALL PROC
        updateReportCall.execute(in);
        return prUpdateGeneratedReport;
    }
}
