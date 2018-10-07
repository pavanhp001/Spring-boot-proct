/* **************************************************************************
 *                              - CONFIDENTIAL                           *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.wi.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import abc.xyz.pts.bcs.common.dao.support.CustomRowMapper;
import abc.xyz.pts.bcs.common.dao.support.query.AdvancedField;
import abc.xyz.pts.bcs.common.dao.support.query.CountBuilder;
import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Hints;
import abc.xyz.pts.bcs.common.dao.support.query.QueryBuilder;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.support.query.arg.DateRangeQueryArg;
import abc.xyz.pts.bcs.common.dao.support.query.arg.NotEqualsQueryArg;
import abc.xyz.pts.bcs.common.dao.support.query.arg.SimpleQueryArg;
import abc.xyz.pts.bcs.common.dao.support.query.arg.WildCardQueryArg;
import abc.xyz.pts.bcs.common.dao.utils.JdbcUtil;
import abc.xyz.pts.bcs.common.util.WebStringUtils;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;
import abc.xyz.pts.bcs.wi.dao.ImportTargetSearchDao;
import abc.xyz.pts.bcs.wi.dto.TargetItem;
import abc.xyz.pts.bcs.wi.enums.TargetSearchStatus;

/**
 *
 * @author Thiruvengadam.S
 */
public class ImportTargetSearchDaoImpl implements ImportTargetSearchDao {
    /** Logger */
    private static final Logger LOG =
            Logger.getLogger(ImportTargetSearchDaoImpl.class);
    /** DAO Support */
    private JdbcDaoSupport daoSupport;
    /** DB to Java Object mapper. */
    private final CustomRowMapper<TargetItem> rowMapper =
            new CustomRowMapper<TargetItem>(TargetItem.class);

    /**
     * @param daoSupport the daoSupport to set
     */
    public void setDaoSupport(final JdbcDaoSupport daoSupport) {
        this.daoSupport = daoSupport;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TargetItem> getImportTargets(final TargetItem targetItem,
            final TableActionCommand tableCommand, final Locale locale) {
        if (LOG.isInfoEnabled()) {
            LOG.info("Inside the getImportTargets");
        }

        final QueryBuilder builder = new QueryBuilder(tableCommand, Field.ID, Table.IMPORT_TARGETS);
        getImportTargetQueryBuilder(targetItem, tableCommand, locale, builder);
        if (LOG.isInfoEnabled()) {
            LOG.info("Generated Query : " + builder.getSql());
            LOG.info("Paged Query Criteria : "
                    + Arrays.asList(builder.getCriteriaValues()));
        }

        final List<TargetItem> targetItems = JdbcUtil.executeAllQuery(
                daoSupport.getJdbcTemplate(), rowMapper, builder);

        return targetItems;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getImportTargetsCount(final TargetItem targetItem,
            final TableActionCommand tableCommand, final Locale locale) {
        if (LOG.isInfoEnabled()) {
            LOG.info("Inside the getImportTargetsCount");
        }

        final CountBuilder builder = new CountBuilder(tableCommand);
        getImportTargetQueryBuilder(targetItem, tableCommand, locale, builder);

        return JdbcUtil.executeQueryForCount(daoSupport.getJdbcTemplate(), builder);
    }

    /**
     *
     * @param targetItem
     * @param tableCommand
     * @param locale
     * @return
     */
    public QueryBuilder getImportTargetQueryBuilder(final TargetItem targetItem,
            final TableActionCommand tableCommand, final Locale locale, final QueryBuilder queryBuilder) {

        // Add hint if its Wild char search for doc number
        if(WebStringUtils.containsWildCard(targetItem.getDocNo())) {
            queryBuilder.addHint(Hints.TARGET_WL_DOC_NO_HINT.getHint());
        }

        /* SELECT Clause*/
        queryBuilder.addSelect(Field.ID, Table.IMPORT_TARGETS);
        queryBuilder.addSelect(AdvancedField.TARGET_STATUS);
        queryBuilder.addSelect(Field.WATL_NAME, Table.IMPORT_TARGETS);
        queryBuilder.addSelect(Field.RESC_CODE, Table.IMPORT_TARGETS);
        queryBuilder.addSelect(Field.ACTC_CODE, Table.IMPORT_TARGETS);
        queryBuilder.addSelect(Field.FORENAME, Table.IMPORT_TARGETS);
        queryBuilder.addSelect(Field.LAST_NAME, Table.IMPORT_TARGETS);
        queryBuilder.addSelect(Field.DOC_TYPE, Table.IMPORT_TARGETS);
        queryBuilder.addSelect(Field.DOC_NO, Table.IMPORT_TARGETS);
        queryBuilder.addSelect(Field.NATIONALITY, Table.IMPORT_TARGETS);
        queryBuilder.addSelect(Field.BIRTH_DATE, Table.IMPORT_TARGETS);
        queryBuilder.addSelect(Field.BIRTH_PLACE, Table.IMPORT_TARGETS);
        queryBuilder.addSelect(Field.GENDER, Table.IMPORT_TARGETS);
        queryBuilder.addSelect(Field.PROTOCOL_NUMBER, Table.IMPORT_TARGETS);
        queryBuilder.addSelect(Field.BIRTH_CNTRY_CODE, Field.COUNTRY_OF_BIRTH,
                Table.IMPORT_TARGETS);
        queryBuilder.addSelect(Field.VALID_UNTIL_DATE, Table.IMPORT_TARGETS);

        // Change the field the description is read from, depending on locale
        if(locale == null || "en".equals(locale.getLanguage())) {
            queryBuilder.addSelect(AdvancedField.WATCH_LIST_DESCRIPTION);
            queryBuilder.addSelect(Field.DESCRIPTION, Field.ACTC_CODE_DESC, Table.ACTION_CODES);  // Recommeded action
            queryBuilder.addSelect(Field.DESCRIPTION, Field.RESC_CODE_DESC, Table.REASON_CODES);        // reason
        } else {
            queryBuilder.addSelect(AdvancedField.WATCH_LIST_LANG_DESCRIPTION);
            queryBuilder.addSelect(Field.DESCRIPTION_LANG, Field.ACTC_CODE_DESC, Table.ACTION_CODES);
            queryBuilder.addSelect(Field.DESCRIPTION_LANG, Field.RESC_CODE_DESC, Table.REASON_CODES);
        }

        queryBuilder.addSelect(Field.SEVERITY_LEVEL, Table.REASON_CODES); // severity
        queryBuilder.addSelect(Field.STATUS, Table.IMPORT_TARGETS);
        queryBuilder.addSelect(Field.FILE_REFERENCE_NUMBER,
                Table.WATCH_LIST_IMPORT_FILES);

        /* FROM Clause */
        queryBuilder.addTable(Table.IMPORT_TARGETS);
        queryBuilder.addTable(Table.ACTION_CODES);
        queryBuilder.addTable(Table.REASON_CODES);
        queryBuilder.addTable(Table.WATCH_LISTS);
        queryBuilder.addTable(Table.WATCH_LIST_IMPORT_FILES);

        /* WHERE Clause */
        /* Table Joins */
        queryBuilder.addTableJoin(Table.IMPORT_TARGETS, Field.WATLIF_ID,
                Table.WATCH_LIST_IMPORT_FILES, Field.ID);
        queryBuilder.addOuterTableJoin(Table.IMPORT_TARGETS, Field.ACTC_CODE,
                Table.ACTION_CODES, Field.CODE, Table.ACTION_CODES, Field.CODE);
        queryBuilder.addOuterTableJoin(Table.IMPORT_TARGETS, Field.RESC_CODE,
                Table.REASON_CODES, Field.CODE, Table.REASON_CODES, Field.CODE);
        queryBuilder.addOuterTableJoin(Table.IMPORT_TARGETS, Field.WATL_NAME,
                Table.WATCH_LISTS, Field.NAME, Table.WATCH_LISTS, Field.NAME);

        /* remaining where clause */
        queryBuilder.addWhereClause(new SimpleQueryArg(Field.FORENAME,
                Table.IMPORT_TARGETS, targetItem.getForename()));
        queryBuilder.addWhereClause(new SimpleQueryArg(Field.LAST_NAME,
                Table.IMPORT_TARGETS, targetItem.getLastName()));
        queryBuilder.addWhereClause(new SimpleQueryArg(Field.WATL_NAME,
                Table.IMPORT_TARGETS, targetItem.getWatlName()));
        queryBuilder.addWhereClause(new SimpleQueryArg(
                Field.GENDER, Table.IMPORT_TARGETS, targetItem.getGender()));
        queryBuilder.addWhereClause(new SimpleQueryArg(
                Field.BIRTH_CNTRY_CODE, Table.IMPORT_TARGETS,
                targetItem.getCountryOfBirth()));
        queryBuilder.addWhereClause(new SimpleQueryArg(
                Field.BIRTH_PLACE, Table.IMPORT_TARGETS,
                targetItem.getBirthPlace()));
        queryBuilder.addWhereClause(new SimpleQueryArg(
                Field.BIRTH_DATE, Table.IMPORT_TARGETS,
                targetItem.getBirthDate()));
        queryBuilder.addWhereClause(new SimpleQueryArg(Field.NATIONALITY,
                Table.IMPORT_TARGETS, targetItem.getNationality()));
        queryBuilder.addWhereClause(new WildCardQueryArg(targetItem.getDocNo(),
                Field.DOC_NO, Table.IMPORT_TARGETS));
        queryBuilder.addWhereClause(new SimpleQueryArg(Field.DOC_TYPE,
                Table.IMPORT_TARGETS, targetItem.getDocType()));
        queryBuilder.addWhereClause(new SimpleQueryArg(Field.RESC_CODE,
                Table.IMPORT_TARGETS, targetItem.getRescCode()));
        queryBuilder.addWhereClause(new SimpleQueryArg(Field.ACTC_CODE,
                Table.IMPORT_TARGETS, targetItem.getRecommendedAction()));
        queryBuilder.addWhereClause(
                new DateRangeQueryArg(null, targetItem.getValidUntilDate(),
                        Field.VALID_UNTIL_DATE, Table.IMPORT_TARGETS));
        queryBuilder.addWhereClause(new SimpleQueryArg(Field.PROTOCOL_NUMBER,
                Table.IMPORT_TARGETS, targetItem.getProtocolNumber()));
        queryBuilder.addWhereClause(new SimpleQueryArg(Field.SEVERITY_LEVEL,
                Table.REASON_CODES, targetItem.getSeverityLevel()));
        if (TargetSearchStatus.ALL.getStatus().equals(
                targetItem.getTargetStatus()) == false) {
            queryBuilder.addWhereClause(new SimpleQueryArg(Field.STATUS,
                    Table.IMPORT_TARGETS, targetItem.getTargetStatus()));
        }

        /* Excluding the In-Progress records. */
        queryBuilder.addWhereClause(new NotEqualsQueryArg(Field.STATUS,
                Table.IMPORT_TARGETS,
                TargetSearchStatus.IN_PROGRESS.getStatus()));

        queryBuilder.addWhereClause(new SimpleQueryArg(
                Field.FILE_REFERENCE_NUMBER, Table.WATCH_LIST_IMPORT_FILES,
                targetItem.getFileReferenceNumber()));

        // ** Exclude itself if targetId is specified.
        if (targetItem.getId() != null
                && StringUtils.isNotEmpty(targetItem.getId().toString())) {
            queryBuilder.addWhereClause(new NotEqualsQueryArg(Field.ID,
                    Table.IMPORT_TARGETS, targetItem.getId()));
        }

        // ** Execute and prepare to return data
        return queryBuilder;
    }
}
