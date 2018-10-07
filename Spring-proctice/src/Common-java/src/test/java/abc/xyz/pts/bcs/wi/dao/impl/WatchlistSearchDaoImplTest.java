/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.dao.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import abc.xyz.pts.bcs.common.dao.delegate.JdbcUtilDelegate;
import abc.xyz.pts.bcs.common.dao.support.CustomRowMapper;
import abc.xyz.pts.bcs.common.dao.support.query.AdvancedField;
import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Hints;
import abc.xyz.pts.bcs.common.dao.support.query.QueryBuilder;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.support.query.arg.BirthDateRangeQuery;
import abc.xyz.pts.bcs.common.dao.support.query.arg.DateRangeQueryArg;
import abc.xyz.pts.bcs.common.dao.support.query.arg.NotEqualsQueryArg;
import abc.xyz.pts.bcs.common.dao.support.query.arg.SimpleQueryArg;
import abc.xyz.pts.bcs.common.dao.support.query.arg.WildCardQueryArg;
import abc.xyz.pts.bcs.common.util.WebStringUtils;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;
import abc.xyz.pts.bcs.wi.dto.TargetItem;

@RunWith(MockitoJUnitRunner.class)
public class WatchlistSearchDaoImplTest {

    @Mock
    private JdbcUtilDelegate jdbcUtilDelegate;

    @Mock
    private JdbcTemplate jdbcTemplate;

    private WatchlistSearchDaoImpl watchlistSearchDaoImpl;

    @Before
    public void setup() {
        watchlistSearchDaoImpl = new WatchlistSearchDaoImpl();
        watchlistSearchDaoImpl.setJdbcUtilDelegate(jdbcUtilDelegate);
        watchlistSearchDaoImpl.setJdbcTemplate(jdbcTemplate);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void search_shouldGenerateCorrectSQL_Given_EN_Locale_And_NonNull_TargetId_And_Contains_Wildcard() {

        // Given
        final TargetItem targetItem = getTargetItem();
        final TableActionCommand tableActionCommand = new TableActionCommand();
        targetItem.setId(1L);
        targetItem.setMessageIndicator(true);
        targetItem.setDocNo("1098*");
        final Locale locale = new Locale("en");
        final QueryBuilder expectedQueryBuilder = getExpectedQueryBuilder(targetItem, tableActionCommand, locale.getLanguage().equals("en"));

        // When
        watchlistSearchDaoImpl.search(targetItem, tableActionCommand, locale);

        // Then
        verify(jdbcUtilDelegate, times(1)).executeAllQuery(any(JdbcTemplate.class), any(CustomRowMapper.class), eq(expectedQueryBuilder));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void search_shouldGenerateCorrectSQL_Given_Non_EN_Locale_And_NonNull_TargetId_And_Contains_Wildcard() {

        // Given
        final TargetItem targetItem = getTargetItem();
        final TableActionCommand tableActionCommand = new TableActionCommand();
        targetItem.setId(1L);
        targetItem.setMessageIndicator(true);
        targetItem.setDocNo("1098*");
        final Locale locale = new Locale("ae");
        final QueryBuilder expectedQueryBuilder = getExpectedQueryBuilder(targetItem, tableActionCommand, locale.getLanguage().equals("en"));

        // When
        watchlistSearchDaoImpl.search(targetItem, tableActionCommand, locale);

        // Then
        verify(jdbcUtilDelegate, times(1)).executeAllQuery(any(JdbcTemplate.class), any(CustomRowMapper.class), eq(expectedQueryBuilder));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void searchAll_shouldGenerateCorrectSQL_WhenRequestedForForenameAndLastname() {

        // Given
        final TargetItem targetItem = new TargetItem();
        targetItem.setForename("Forename");
        targetItem.setLastName("Lastname");
        final TableActionCommand tableActionCommand = new TableActionCommand();
        targetItem.setId(1L);
        targetItem.setMessageIndicator(true);
        final Locale locale = new Locale("en");
        final QueryBuilder expectedQueryBuilder = getExpectedQueryBuilder(targetItem, tableActionCommand, locale.getLanguage().equals("en"));

        // When
        watchlistSearchDaoImpl.searchAll(targetItem, tableActionCommand, locale);

        // Then
        verify(jdbcUtilDelegate, times(1)).executeAllQuery(any(JdbcTemplate.class), any(CustomRowMapper.class), eq(expectedQueryBuilder));
    }

    private TargetItem getTargetItem() {
        final TargetItem targetItem = new TargetItem();
        targetItem.setActcCode("ACTCCODE");
        targetItem.setActcCodeDesc("ACTCCODE DESC");
        targetItem.setAppActionCode("APP ACTION CODE");
        targetItem.setAutoQualify("Y");
        targetItem.setBirthDate(Calendar.getInstance());
        targetItem.setBirthPlace("BPLACE");
        targetItem.setCountryOfBirth("COBIRTH");
        targetItem.setCreatedBy("CREATED BY");
        targetItem.setDocNo("DOC NO");
        targetItem.setDocType("DOCTYPE");
        targetItem.setDocumentIssueCountry("DOC ISSUE COUNTRY");
        targetItem.setEnabledIndicator(true);
        targetItem.setForename("FORENAME");
        targetItem.setGender("GENDER");
        targetItem.setId(1L);
        targetItem.setLastName("LAST NAME");
        targetItem.setNationality("NATIONALITY");
        return targetItem;
    }

    private QueryBuilder getExpectedQueryBuilder(final TargetItem targetItem, final TableActionCommand tableActionCommand, final boolean isEnglishLocale) {
        final QueryBuilder qb = new QueryBuilder(tableActionCommand, Field.ID, Table.TARGET_WATCH_LISTS);
        if (WebStringUtils.containsWildCard(targetItem.getDocNo())) {
            qb.addHint(Hints.TARGET_WL_DOC_NO_HINT.getHint());
        }
        // * SELECT
        qb.addSelect(Field.ID, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.FORENAME, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.LAST_NAME, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.WATL_NAME, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.GENDER, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.BIRTH_DATE, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.BIRTH_DATE_FROM, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.BIRTH_DATE_TO, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.BIRTH_PLACE, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.BIRTH_CNTRY_CODE, Field.COUNTRY_OF_BIRTH, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.NATIONALITY, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.DOC_NO, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.DOC_TYPE, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.VALID_UNTIL_DATE, Table.TARGET_WATCH_LISTS);
        qb.addSelect(Field.PROTOCOL_NUMBER, Table.TARGET_WATCH_LISTS); // reference
        qb.addSelect(Field.CREATED_BY, Table.TARGET_WATCH_LISTS);
        qb.addSelect(AdvancedField.DATE_OF_LAST_CHANGE);
        qb.addSelect(Field.UPDATE_VERSION_NO, Table.TARGET_WATCH_LISTS);
        if (isEnglishLocale) {
            qb.addSelect(AdvancedField.WATCH_LIST_DESCRIPTION);
            qb.addSelect(Field.DESCRIPTION, Field.ACTC_CODE_DESC, Table.ACTION_CODES);
            qb.addSelect(Field.CODE, Field.ACTC_CODE, Table.ACTION_CODES);
            qb.addSelect(Field.DESCRIPTION, Field.RESC_CODE_DESC, Table.REASON_CODES);
        } else {
            qb.addSelect(AdvancedField.WATCH_LIST_LANG_DESCRIPTION);
            qb.addSelect(Field.DESCRIPTION_LANG, Field.ACTC_CODE_DESC, Table.ACTION_CODES);
            qb.addSelect(Field.CODE, Field.ACTC_CODE, Table.ACTION_CODES);
            qb.addSelect(Field.DESCRIPTION_LANG, Field.RESC_CODE_DESC, Table.REASON_CODES);
        }
        qb.addSelect(Field.AUTO_QUALIFY_IND, Field.AUTO_QUALIFY, Table.ACTION_CODES);
        qb.addSelect(Field.SEVERITY_LEVEL, Table.REASON_CODES); // severity
        qb.addSelect(AdvancedField.TARGET_STATUS_ACTIVE);
        qb.addSelect(Field.FILE_REFERENCE_NUMBER, Table.TARGET_WATCH_LISTS);

        // * FROM
        qb.addTable(Table.TARGET_WATCH_LISTS);
        qb.addTable(Table.ACTION_CODES);
        qb.addTable(Table.REASON_CODES);
        qb.addTable(Table.WATCH_LISTS);

        // WHERE
        qb.addTableJoin(Table.TARGET_WATCH_LISTS, Field.ACTC_CODE, Table.ACTION_CODES, Field.CODE);
        qb.addTableJoin(Table.TARGET_WATCH_LISTS, Field.RESC_CODE, Table.REASON_CODES, Field.CODE);
        qb.addTableJoin(Table.TARGET_WATCH_LISTS, Field.WATL_NAME, Table.WATCH_LISTS, Field.NAME);

        qb.addWhereClause(new SimpleQueryArg(Field.FORENAME, Table.TARGET_WATCH_LISTS, targetItem.getForename()));
        qb.addWhereClause(new SimpleQueryArg(Field.LAST_NAME, Table.TARGET_WATCH_LISTS, targetItem.getLastName()));


        qb.addWhereClause(new SimpleQueryArg(Field.WATL_NAME, Table.TARGET_WATCH_LISTS, targetItem.getWatlName()));
        qb.addWhereClause(new SimpleQueryArg(Field.GENDER, Table.TARGET_WATCH_LISTS, targetItem.getGender()));
        qb.addWhereClause(new SimpleQueryArg(Field.BIRTH_CNTRY_CODE, Table.TARGET_WATCH_LISTS, targetItem.getCountryOfBirth()));
        qb.addWhereClause(new SimpleQueryArg(Field.BIRTH_PLACE, Table.TARGET_WATCH_LISTS, targetItem.getBirthPlace()));
        qb.addWhereClause(new BirthDateRangeQuery(targetItem.getBirthDate(), Field.BIRTH_DATE, Field.BIRTH_DATE_FROM, Field.BIRTH_DATE_TO, Table.TARGET_WATCH_LISTS));
        qb.addWhereClause(new SimpleQueryArg(Field.NATIONALITY, Table.TARGET_WATCH_LISTS, targetItem.getNationality()));
        qb.addWhereClause(new WildCardQueryArg(targetItem.getDocNo(), Field.DOC_NO, Table.TARGET_WATCH_LISTS));
        qb.addWhereClause(new SimpleQueryArg(Field.DOC_TYPE, Table.TARGET_WATCH_LISTS, targetItem.getDocType()));
        qb.addWhereClause(new SimpleQueryArg(Field.RESC_CODE, Table.TARGET_WATCH_LISTS, targetItem.getRescCode()));
        qb.addWhereClause(new SimpleQueryArg(Field.ACTC_CODE, Table.TARGET_WATCH_LISTS, targetItem.getRecommendedAction()));
        qb.addWhereClause(new SimpleQueryArg(Field.FILE_REFERENCE_NUMBER, Table.TARGET_WATCH_LISTS, targetItem.getFileReferenceNumber()));

        if (targetItem.isMessageIndicator()) {
            qb.addWhereClause(new DateRangeQueryArg(targetItem.getValidUntilDate(), Field.VALID_UNTIL_DATE, Table.TARGET_WATCH_LISTS));
        } else {
            qb.addWhereClause(new DateRangeQueryArg(null, targetItem.getValidUntilDate(), Field.VALID_UNTIL_DATE, Table.TARGET_WATCH_LISTS));
        }
        qb.addWhereClause(new SimpleQueryArg(Field.PROTOCOL_NUMBER, Table.TARGET_WATCH_LISTS, targetItem.getProtocolNumber()));
        qb.addWhereClause(new SimpleQueryArg(Field.SEVERITY_LEVEL, Table.REASON_CODES, targetItem.getSeverityLevel()));

        // ** Exclude itself if targetId is specified.
        if (targetItem.getId() != null && StringUtils.isNotEmpty(targetItem.getId().toString())) {
            qb.addWhereClause(new NotEqualsQueryArg(Field.ID, Table.TARGET_WATCH_LISTS, targetItem.getId()));
        }
        return qb;
    }
}
