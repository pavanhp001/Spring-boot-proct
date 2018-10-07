/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral.dao;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import abc.xyz.pts.bcs.common.dao.support.CustomRowMapper;
import abc.xyz.pts.bcs.common.dao.support.query.AdvancedField;
import abc.xyz.pts.bcs.common.dao.support.query.ConfiguredCountBuilder;
import abc.xyz.pts.bcs.common.dao.support.query.CountBuilder;
import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Hints;
import abc.xyz.pts.bcs.common.dao.support.query.NullField;
import abc.xyz.pts.bcs.common.dao.support.query.QueryBuilder;
import abc.xyz.pts.bcs.common.dao.support.query.QueryBuilderAdvance;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.support.query.WithSelectQueryBuilder;
import abc.xyz.pts.bcs.common.dao.support.query.arg.DateRangeQueryArg;
import abc.xyz.pts.bcs.common.dao.support.query.arg.FlightRouteQueryArg;
import abc.xyz.pts.bcs.common.dao.support.query.arg.HitTypeQueryArg;
import abc.xyz.pts.bcs.common.dao.support.query.arg.OverrideTypeQueryArg;
import abc.xyz.pts.bcs.common.dao.support.query.arg.QualifiedStatusQueryArg;
import abc.xyz.pts.bcs.common.dao.support.query.arg.SimpleInQueryArg;
import abc.xyz.pts.bcs.common.dao.support.query.arg.SimpleQueryArg;
import abc.xyz.pts.bcs.common.dao.support.query.arg.TimeRangeQueryArg;
import abc.xyz.pts.bcs.common.dao.support.query.arg.TravelTypeQueryArg;
import abc.xyz.pts.bcs.common.dao.support.query.arg.WildCardQueryArg;
import abc.xyz.pts.bcs.common.dao.utils.JdbcUtil;
import abc.xyz.pts.bcs.common.exception.DatabaseException;
import abc.xyz.pts.bcs.common.referral.dto.ReferralHitTargetRec;
import abc.xyz.pts.bcs.common.referral.dto.ReferralHitTargetRecImpl;
import abc.xyz.pts.bcs.common.referral.dto.ReferralRec;
import abc.xyz.pts.bcs.common.referral.dto.ReferralSearchRec;
import abc.xyz.pts.bcs.common.referral.dto.ReferralTravellerRec;
import abc.xyz.pts.bcs.common.referral.dto.ReferralTravellerRecImpl;
import abc.xyz.pts.bcs.common.referral.dto.helper.ReferralRecHelper;
import abc.xyz.pts.bcs.common.referral.web.command.ReferralSearchCommand;
import abc.xyz.pts.bcs.common.util.WebStringUtils;
import abc.xyz.pts.bcs.common.web.command.DbActionCommand;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;
import abc.xyz.pts.bcs.wi.dao.PrRecommendedActionDao;
import abc.xyz.pts.bcs.wi.dao.PrSeverityReasonDao;
import abc.xyz.pts.bcs.wi.dao.PrWatchListNameDao;
import abc.xyz.pts.bcs.wi.dto.RecommendedAction;
import abc.xyz.pts.bcs.wi.dto.SeverityReason;
import abc.xyz.pts.bcs.wi.dto.WatchListName;

@Repository("referralSearchDao")
public class ReferralSearchDao {
    @Autowired
    private JdbcDaoSupport daoSupport;

    @Autowired
    private PrRecommendedActionDao prRecommendedActionDao;

    @Autowired
    private PrSeverityReasonDao prSeverityReasonDao;

    @Autowired
    private PrWatchListNameDao prWatchListNameDao;

    private CustomRowMapper<ReferralSearchRec> referralRowMapper = new CustomRowMapper<ReferralSearchRec>(ReferralSearchRec.class);
    private CustomRowMapper<ReferralHitTargetRecImpl> referralTargetRowMapper = new CustomRowMapper<ReferralHitTargetRecImpl>(ReferralHitTargetRecImpl.class);
    private CustomRowMapper<ReferralTravellerRecImpl> referralTravellerRowMapper = new CustomRowMapper<ReferralTravellerRecImpl>(ReferralTravellerRecImpl.class);

    /* ****************************
     * * Get all matched referrals
     * ****************************
     */
    private WithSelectQueryBuilder getWithQB(final ReferralSearchCommand cmd, final TableActionCommand actionCmd) {
        WithSelectQueryBuilder qb = new WithSelectQueryBuilder(Table.MATCHED_RFRL.getValue(), actionCmd, Field.REF_ID);

        return (WithSelectQueryBuilder) getReferralSearchBuilder(cmd, qb);
    }


    /**
     * @param cmd
     * @param qb
     * @return
     */
    private QueryBuilder getReferralSearchBuilder(final ReferralSearchCommand cmd, final QueryBuilder qb) {
        if(WebStringUtils.containsWildCard(cmd.getDocumentNumber())){
            qb.addHint(Hints.TVLRS_DOC_NO_HINT.getHint());
        }

        // ***
        // * Build up the With Statement
        // ***
        qb.addSelect(Field.ID,                  Field.REF_ID,                       Table.RFRL);
        qb.addSelect(AdvancedField.VALUE_ZERO,  Field.REFERRAL_LEVEL);
        qb.addSelect(Field.STATUS,              Field.REFERRAL_STATUS,              Table.RFRL);
        qb.addSelect(Field.CREATED_DATETIME,                                        Table.RFRL);
        qb.addSelect(Field.ACTION_CODE,         Field.REFERRAL_REC_ACTION_CODE,     Table.RFRL);
        qb.addSelect(Field.PRIORITY,            Field.PRIORITY_REFERRAL,            Table.RFRL);
        qb.addSelect(Field.SEVERITY_LEVEL,      Field.REFERRAL_SEVERITY,            Table.RFRL);
        qb.addSelect(NullField.getInstance(Field.PRIORITY_HIT));
        qb.addSelect(AdvancedField.TOTAL_REFERRAL_HITS,  Field.TOTAL_REFERRAL_HITS);
        qb.addSelect(Field.MOVEMENT_TYPE,                                           Table.TRVLR);
        qb.addSelect(AdvancedField.MOVEMENT_STATUS_WITH_TBL_ALIAS);
        qb.addSelect(AdvancedField.TRAVEL_TYPE_TRVLR, Field.TYPE_OF_TRAVEL);
        qb.addSelect(Field.ID,                      Field.TRAVELLER_ID,             Table.TRVLR);
        qb.addSelect(Field.TRAVELLER_TYPE,                                          Table.TRVLR);
        qb.addSelect(Field.FORENAME,                                                Table.TRVLR);
        qb.addSelect(Field.LAST_NAME,                                               Table.TRVLR);
        qb.addSelect(Field.BIRTH_DATE,                                              Table.TRVLR);
        qb.addSelect(Field.PRI_DOC_TYPE,                                            Table.TRVLR);
        qb.addSelect(Field.PRI_DOC_NO,                                              Table.TRVLR);
        qb.addSelect(Field.NATIONALITY,                                             Table.TRVLR);
        qb.addSelect(Field.FLIGHT_SEG_ID,                                           Table.FLTS);
        qb.addSelect(Field.OPER_CARRIER_CODE,                                       Table.FLTS);
        qb.addSelect(Field.OPER_FLIGHT_NO,                                          Table.FLTS);
        qb.addSelect(Field.DEP_AIRPORT_CODE,                                        Table.FLTS);
        qb.addSelect(Field.DEP_DATETIME,                                            Table.FLTS);
        qb.addSelect(Field.ARR_AIRPORT_CODE,                                        Table.FLTS);
        qb.addSelect(Field.ARR_DATETIME,                                            Table.FLTS);
        qb.addSelect(Field.UPDATE_VERSION_NO,                                       Table.RFRL);
        qb.addSelect(Field.ADDITIONAL_INSTRUCTION_CODE,                             Table.RFRL);
        qb.addSelect(AdvancedField.REFERRAL_STATUS_ORDER, Field.REFERRAL_STATUS_ORDER);



        // * FROM
        qb.addTable(Table.REFERRALS,        Table.RFRL);
        qb.addTable(Table.TRAVELLERS,       Table.TRVLR);
        qb.addTable(Table.FLIGHT_SEGMENTS,  Table.FLTS);

        // **********
        // * WHERE
        // **********
        qb.addTableJoin( Table.TRVLR,   Field.FLTS_FLIGHT_SEG_ID
                       , Table.FLTS,    Field.FLIGHT_SEG_ID
                       );
        qb.addTableJoin( Table.TRVLR,   Field.ID
                       , Table.RFRL,    Field.TRA_ID
                       );

        // ** Referral
        qb.addWhereClause(new SimpleQueryArg(Field.ID, Table.RFRL, cmd.getReferralNumber()));
        if (cmd.getReferralStatusList() != null && cmd.getReferralStatusList().size() > 0) {
            qb.addWhereClause(new SimpleInQueryArg(Field.STATUS, Table.RFRL, cmd.getReferralStatusList().toArray()));
        }
        qb.addWhereClause(new HitTypeQueryArg(Field.ID, Table.RFRL, cmd.getHitTypeList()));
        qb.addWhereClause(new DateRangeQueryArg(cmd.getReferralGenDateFrom(), cmd.getReferralGenDateTo(), Field.CREATED_DATETIME, Table.RFRL));
        qb.addWhereClause(new QualifiedStatusQueryArg(Table.REFERRAL_HITS, Field.QUALIFICATION_STATUS, cmd.getHitStatus()));
        if (cmd.getSeverityLevel() != null && cmd.getSeverityLevel().size() > 0) {
            qb.addWhereClause(new SimpleInQueryArg(Field.SEVERITY_LEVEL, Table.RFRL, cmd.getSeverityLevel().toArray()));
        }


        // ** Flights
        qb.addWhereClause(new SimpleQueryArg(Field.OPER_CARRIER_CODE, Table.FLTS, cmd.getFlightCarrierCode()));
        qb.addWhereClause(new SimpleQueryArg(Field.OPER_FLIGHT_NO, Table.FLTS, cmd.getFlightNumber()));
        qb.addWhereClause(new SimpleQueryArg(Field.DEP_AIRPORT_CODE, Table.FLTS, cmd.getDepartureAirport()));
        qb.addWhereClause(new SimpleQueryArg(Field.ARR_AIRPORT_CODE, Table.FLTS, cmd.getArrivalAirport()));
        qb.addWhereClause(new FlightRouteQueryArg(Table.FLTS, cmd.getRoute()));
        qb.addWhereClause(new DateRangeQueryArg(cmd.getDepartureDateRangeFrom(), cmd.getDepartureDateRangeTo(),Field.DEP_DATE, Table.FLTS));
        qb.addWhereClause(new DateRangeQueryArg(cmd.getArrivalDateRangeFrom(), cmd.getArrivalDateRangeTo(),
                          Field.ARR_DATE, Table.FLTS));
        qb.addWhereClause(new TimeRangeQueryArg(cmd.getDepartureTimeRangeFrom(), cmd.getDepartureTimeRangeTo(),
                          Field.DEP_TIME, Table.FLTS));
        qb.addWhereClause(new TimeRangeQueryArg(cmd.getArrivalTimeRangeFrom(), cmd.getArrivalTimeRangeTo(),
                          Field.ARR_TIME, Table.FLTS));

        // ** Traveller
        qb.addWhereClause(new WildCardQueryArg(cmd.getForenames(), Field.FORENAME, Table.TRVLR));
        qb.addWhereClause(new WildCardQueryArg(cmd.getLastName(), Field.LAST_NAME, Table.TRVLR));
        qb.addWhereClause(new SimpleQueryArg(Field.GENDER, Table.TRVLR, cmd.getGender()));
        qb.addWhereClause(new SimpleQueryArg(Field.NATIONALITY, Table.TRVLR, cmd.getNationality()));
        qb.addWhereClause(new WildCardQueryArg(cmd.getDocumentNumber(), Field.PRI_DOC_NO, Table.TRVLR));
        qb.addWhereClause(new SimpleQueryArg(Field.BIRTH_DATE, Table.TRVLR, cmd.getDateOfBirth()));
        qb.addWhereClause(new SimpleQueryArg(Field.ID, Table.TRVLR, cmd.getTravellerId()));

        qb.addWhereClause(new SimpleInQueryArg(Field.MOVEMENT_TYPE, Table.TRVLR,
               cmd.getMovementStatusCancelled(),
               cmd.getMovementStatusDenied(),
               cmd.getMovementStatusExpected(), 
               cmd.getMovementStatusNoMovement()
               )
        );

        qb.addWhereClause(new TravelTypeQueryArg
                ( Table.TRVLR
                , Field.TYPE_OF_TRAVEL, Table.TRVLR
                , cmd.getTravelTypeTransfer()
                , cmd.getTravelTypeUnmatched()
                , cmd.getTravelTypeNormal()
                , cmd.getTravelTypeTransit()
                )
        );

        qb.addWhereClause(new OverrideTypeQueryArg(Table.TRVLR, Field.OVR_FLAG,
                cmd.getOverrideTypeNone(),
                cmd.getOverrideTypeAgent(),
                cmd.getOverrideTypeGovernment()));
        return qb;
    }


    /**
     * Get Referral details.
     *
     * @param actionCmd
     * @return
     */
    private QueryBuilder getSelectReferralQb(final TableActionCommand actionCmd) {
        QueryBuilder qb = new QueryBuilder(actionCmd, Field.REF_ID, Table.MATCHED_RFRL);

        // * Select
        qb.addSelect(Field.REF_ID,                      Table.MATCHED_RFRL);
        qb.addSelect(AdvancedField.VALUE_ZERO,             Field.REFERRAL_LEVEL);
        qb.addSelect(Field.REFERRAL_STATUS,             Table.MATCHED_RFRL);
        qb.addSelect(Field.CREATED_DATETIME,            Table.MATCHED_RFRL);
        qb.addSelect(Field.REFERRAL_REC_ACTION_CODE,    Table.MATCHED_RFRL);
        qb.addSelect(Field.PRIORITY_REFERRAL,           Table.MATCHED_RFRL);
        qb.addSelect(Field.REFERRAL_SEVERITY,           Table.MATCHED_RFRL);
        qb.addSelect(Field.PRIORITY_HIT,                Table.MATCHED_RFRL);
        qb.addSelect(Field.TOTAL_REFERRAL_HITS,         Table.MATCHED_RFRL);
        qb.addSelect(Field.MOVEMENT_TYPE,               Table.MATCHED_RFRL);
        qb.addSelect(Field.MOVEMENT_STATUS,             Table.MATCHED_RFRL);
        qb.addSelect(Field.TYPE_OF_TRAVEL,              Table.MATCHED_RFRL);
        qb.addSelect(Field.TRAVELLER_TYPE,              Table.MATCHED_RFRL);
        qb.addSelect(Field.TRAVELLER_ID,                Table.MATCHED_RFRL);
        qb.addSelect(Field.FORENAME,                    Table.MATCHED_RFRL);
        qb.addSelect(Field.LAST_NAME,                   Table.MATCHED_RFRL);
        qb.addSelect(Field.BIRTH_DATE,                  Table.MATCHED_RFRL);
        qb.addSelect(Field.PRI_DOC_TYPE,                Table.MATCHED_RFRL);
        qb.addSelect(Field.PRI_DOC_NO,                  Table.MATCHED_RFRL);
        qb.addSelect(Field.NATIONALITY,                 Table.MATCHED_RFRL);
        qb.addSelect(Field.FLIGHT_SEG_ID,               Table.MATCHED_RFRL);
        qb.addSelect(Field.OPER_CARRIER_CODE,           Table.MATCHED_RFRL);
        qb.addSelect(Field.OPER_FLIGHT_NO,              Table.MATCHED_RFRL);
        qb.addSelect(Field.DEP_AIRPORT_CODE,            Table.MATCHED_RFRL);
        qb.addSelect(Field.DEP_DATETIME,                Table.MATCHED_RFRL);
        qb.addSelect(Field.ARR_AIRPORT_CODE,            Table.MATCHED_RFRL);
        qb.addSelect(Field.ARR_DATETIME,                Table.MATCHED_RFRL);

        qb.addSelect(NullField.getInstance(Field.HIT_ID));
        qb.addSelect(NullField.getInstance(Field.HIT_TYPE));
        qb.addSelect(NullField.getInstance(Field.WL_WI_WATL_NAME));
        qb.addSelect(NullField.getInstance(Field.HIT_SCORE));
        qb.addSelect(NullField.getInstance(Field.SEVERITY_LEVEL));
        qb.addSelect(NullField.getInstance(Field.ACTION_CODE));
        qb.addSelect(NullField.getInstance(Field.QUALIFICATION_STATUS));

        qb.addSelect(Field.UPDATE_VERSION_NO,           Table.MATCHED_RFRL);
        qb.addSelect(Field.ADDITIONAL_INSTRUCTION_CODE,    Table.MATCHED_RFRL);
        qb.addSelect(NullField.getInstance(Field.WL_RESC_CODE));
        qb.addSelect(NullField.getInstance(Field.WL_TARWL_ID));
        qb.addSelect(Field.REFERRAL_STATUS_ORDER,           Table.MATCHED_RFRL);

        // * FROM
        qb.addTable(Table.MATCHED_RFRL);

        return qb;
    }


    /**
     * Get Hit details.
     *
     * @param actionCmd
     * @return
     */
    private QueryBuilder getSelectReferralTravelQb(final TableActionCommand actionCmd) {
        QueryBuilder qb = new QueryBuilder(actionCmd, Field.ID, Table.MATCHED_RFRL);

        qb.addSelect(Field.REF_ID,                                          Table.MRFRL);
        qb.addSelect(AdvancedField.HIT_TYPE_SEQ, Field.REFERRAL_LEVEL);
        qb.addSelect(Field.REFERRAL_STATUS,                                 Table.MRFRL);
        qb.addSelect(Field.CREATED_DATETIME,                                Table.MRFRL);
        qb.addSelect(Field.REFERRAL_REC_ACTION_CODE,                        Table.MRFRL);
        qb.addSelect(Field.PRIORITY_REFERRAL,                               Table.MRFRL);
        qb.addSelect(Field.REFERRAL_SEVERITY,                               Table.MRFRL);
        qb.addSelect(Field.PRIORITY,            Field.PRIORITY_HIT,         Table.RFRL_HIT);
        qb.addSelect(Field.TOTAL_REFERRAL_HITS,                             Table.MRFRL);
        qb.addSelect(Field.MOVEMENT_TYPE,                                   Table.MRFRL);
        qb.addSelect(Field.MOVEMENT_STATUS,                                 Table.MRFRL);
        qb.addSelect(Field.TYPE_OF_TRAVEL,                                  Table.MRFRL);
        qb.addSelect(Field.TRAVELLER_TYPE,                                  Table.MRFRL);
        qb.addSelect(Field.TRAVELLER_ID,                                    Table.MRFRL);
        qb.addSelect(Field.FORENAME,                                        Table.MRFRL);
        qb.addSelect(Field.LAST_NAME,                                       Table.MRFRL);
        qb.addSelect(Field.BIRTH_DATE,                                      Table.MRFRL);
        qb.addSelect(Field.PRI_DOC_TYPE,                                    Table.MRFRL);
        qb.addSelect(Field.PRI_DOC_NO,                                      Table.MRFRL);
        qb.addSelect(Field.NATIONALITY,                                     Table.MRFRL);
        qb.addSelect(Field.FLIGHT_SEG_ID,                                   Table.MRFRL);
        qb.addSelect(Field.OPER_CARRIER_CODE,                               Table.MRFRL);
        qb.addSelect(Field.OPER_FLIGHT_NO,                                  Table.MRFRL);
        qb.addSelect(Field.DEP_AIRPORT_CODE,                                Table.MRFRL);
        qb.addSelect(Field.DEP_DATETIME,                                    Table.MRFRL);
        qb.addSelect(Field.ARR_AIRPORT_CODE,                                Table.MRFRL);
        qb.addSelect(Field.ARR_DATETIME,                                    Table.MRFRL);

        qb.addSelect(Field.ID,                  Field.HIT_ID,               Table.RFRL_HIT);
        qb.addSelect(Field.HIT_TYPE,                                        Table.RFRL_HIT);
        qb.addSelect(Field.WL_WI_WATL_NAME,                                 Table.RFRL_HIT);
        qb.addSelect(Field.HIT_SCORE,                                       Table.RFRL_HIT);
        qb.addSelect(Field.SEVERITY_LEVEL,                                  Table.RFRL_HIT);
        qb.addSelect(Field.ACTION_CODE,                                     Table.RFRL_HIT);
        qb.addSelect(Field.QUALIFICATION_STATUS,                            Table.RFRL_HIT);
        qb.addSelect(Field.UPDATE_VERSION_NO,                               Table.RFRL_HIT);
        qb.addSelect(Field.ADDITIONAL_INSTRUCTION_CODE,                     Table.MRFRL);
        qb.addSelect(Field.WL_RESC_CODE,                                    Table.RFRL_HIT);
        qb.addSelect(Field.WL_TARWL_ID,                                     Table.RFRL_HIT);
        qb.addSelect(Field.REFERRAL_STATUS_ORDER,                           Table.MRFRL);


        // * FROM
        qb.addTable(Table.REFERRAL_HITS,    Table.RFRL_HIT);
        qb.addTable(Table.MATCHED_RFRL,     Table.MRFRL);

        // * WHERE
        qb.addTableJoin(Table.MRFRL, Field.REF_ID, Table.RFRL_HIT, Field.REF_ID);

        return qb;
    }


    /**
     * search for referrals, flights and traveller
     *
     * @param cmd
     * @param actionCmd
     * @return
     */
    public List<ReferralRec> searchReferral
        ( final ReferralSearchCommand cmd
        , final TableActionCommand actionCmd
        , final Locale locale
        )
    {
        // * appends the default order by
        QueryBuilderAdvance qba = new QueryBuilderAdvance(actionCmd, Field.REF_ID);

        //qba.getStaticOrderBy().add(Field.REF_ID.name());
        qba.getStaticOrderBy().add(Field.REFERRAL_LEVEL.name());
        qba.getStaticOrderBy().add(Field.PRIORITY_HIT.name() + " DESC ");

        // With SQL
        WithSelectQueryBuilder withQb = getWithQB(cmd, actionCmd);
        qba.setWithQb(withQb);
        qba.getCriteria().addAll(withQb.getCriteria());

        // Select statements for UNION
        qba.getQueryBuilderList().add(getSelectReferralQb(actionCmd));
        qba.getQueryBuilderList().add(getSelectReferralTravelQb(actionCmd));

        // * Ensure we estimate this differently
        qba.setEstimationSql(withQb);

        // * We are already restricted to page data in the With clause
        qba.setIgnoreRowNum(true);

        // Execute and prepare data (ensure we don't estimate at this stage)
        List<ReferralSearchRec> refSearchRecList =
            JdbcUtil.executeQuery(daoSupport.getJdbcTemplate(), referralRowMapper, qba, false);

        // Did we get ERROR from this query?
        if (qba.getTableCommand().getErrorNumber() != TableActionCommand.NO_ERROR) {
            throw new DatabaseException(qba.getTableCommand().getErrorNumber());
        }

        List <RecommendedAction> recommendedActions = prRecommendedActionDao.findAllRecommendedAction(locale);
        Map <String, SeverityReason> severityReason = prSeverityReasonDao.findAllSeverityReasonMap(locale);
        List <WatchListName> watchLists = prWatchListNameDao.findAllWatchListNames(locale);
        List <ReferralRec> referralList = ReferralRecHelper.getReferralList(refSearchRecList, recommendedActions, severityReason, watchLists);

        // * We need to adjust the paging after data is re-organised
        JdbcUtil.setNumberOfRows(daoSupport.getJdbcTemplate(), qba, referralList);

        // Did we get ERROR from this query?
        if (qba.getTableCommand().getErrorNumber() != TableActionCommand.NO_ERROR) {
            throw new DatabaseException(qba.getTableCommand().getErrorNumber());
        }

        return referralList;
    }

    public ReferralHitTargetRec getReferralHitTarget(final Long referralHitId) {
        // Just to ensure we trap db errors - not elegant but will do
        // for the moment
        // unit JdbcUtil gets updated.
        DbActionCommand dbCmd = new DbActionCommand();
        dbCmd.setAscDesc(DbActionCommand.DESC);

        QueryBuilder qb = new QueryBuilder(dbCmd, Field.REF_ID, Table.REFERRAL_HITS);

        // * SELECT
        qb.addSelect(Field.ID, Table.REFERRAL_HITS);
        qb.addSelect(Field.REF_ID, Table.REFERRAL_HITS);
        qb.addSelect(Field.HIT_TYPE, Table.REFERRAL_HITS);
        qb.addSelect(Field.HIT_SCORE, Table.REFERRAL_HITS);
        qb.addSelect(Field.WATLR_ID, Table.REFERRAL_HITS);
        qb.addSelect(Field.WL_FORENAME, Table.REFERRAL_HITS);
        qb.addSelect(Field.WL_LAST_NAME, Table.REFERRAL_HITS);
        qb.addSelect(Field.WL_GENDER, Table.REFERRAL_HITS);
        qb.addSelect(Field.WL_BIRTH_DATE, Table.REFERRAL_HITS);
        qb.addSelect(Field.WL_BIRTH_PLACE, Table.REFERRAL_HITS);
        qb.addSelect(Field.WL_BIRTH_CNTRY_CODE, Field.COUNTRY_OF_BIRTH, Table.REFERRAL_HITS);
        qb.addSelect(Field.WL_NATIONALITY, Table.REFERRAL_HITS);
        qb.addSelect(Field.WL_DOC_NO, Table.REFERRAL_HITS);
        qb.addSelect(Field.WL_DOC_TYPE, Table.REFERRAL_HITS);
        qb.addSelect(Field.WL_PROTOCOL_NUMBER, Table.REFERRAL_HITS);     // reference
        qb.addSelect(Field.UPDATE_VERSION_NO, Table.REFERRAL_HITS);
        qb.addSelect(Field.APP_HIT_REASON, Table.REFERRAL_HITS);
        qb.addSelect(Field.DESCRIPTION, Field.RESC_CODE_DESC, Table.REASON_CODES);
        qb.addSelect(Field.WL_TARWL_ID, Table.REFERRAL_HITS);

        // * FROM
        qb.addTable(Table.REFERRAL_HITS);
        qb.addTable(Table.REASON_CODES);

        // * FROM
        qb.addOuterTableJoin(Table.REFERRAL_HITS, Field.WL_RESC_CODE, Table.REASON_CODES, Field.CODE, Table.REASON_CODES, Field.CODE);

        // * WHERE
        qb.addWhereClause(new SimpleQueryArg(Field.ID, Table.REFERRAL_HITS, referralHitId));

        // ** Execute and prepare to return data
        ReferralHitTargetRec referralHitTargetRec = JdbcUtil.executeQueryById(daoSupport.getJdbcTemplate(), referralTargetRowMapper, qb);

        return referralHitTargetRec;
    }

    public ReferralTravellerRec getReferralHitTraveller(final Long watchListRequestId) {
        // Just to ensure we trap db errors - not elegant but will do for the moment
        // unit JdbcUtil gets updated.
        DbActionCommand dbCmd = new DbActionCommand();
        dbCmd.setAscDesc(DbActionCommand.DESC);


        QueryBuilder qb = new QueryBuilder(dbCmd, Field.ID, Table.WATCH_LIST_REQUESTS);

        // * SELECT
        qb.addSelect(Field.ID, Table.WATCH_LIST_REQUESTS);
        qb.addSelect(Field.TRA_ID, Field.TRAVELLER_ID, Table.WATCH_LIST_REQUESTS);
        qb.addSelect(Field.FORENAME, Table.WATCH_LIST_REQUESTS);
        qb.addSelect(Field.LAST_NAME, Table.WATCH_LIST_REQUESTS);
        qb.addSelect(Field.GENDER, Table.WATCH_LIST_REQUESTS);
        qb.addSelect(Field.BIRTH_DATE, Table.WATCH_LIST_REQUESTS);
        qb.addSelect(Field.BIRTH_PLACE, Table.WATCH_LIST_REQUESTS);
        qb.addSelect(Field.BIRTH_CNTRY_CODE, Field.COUNTRY_OF_BIRTH, Table.WATCH_LIST_REQUESTS);
        qb.addSelect(Field.NATIONALITY, Table.WATCH_LIST_REQUESTS);
        qb.addSelect(Field.DOC_NO, Table.WATCH_LIST_REQUESTS);
        qb.addSelect(Field.DOC_TYPE, Table.WATCH_LIST_REQUESTS);

        // * FROM
        qb.addTable(Table.WATCH_LIST_REQUESTS);

        // * WHERE
        qb.addWhereClause(new SimpleQueryArg(Field.ID, Table.WATCH_LIST_REQUESTS, watchListRequestId));

        // ** Execute and prepare to return data
        ReferralTravellerRec referralTravellerRec = JdbcUtil.executeQueryById(daoSupport.getJdbcTemplate(), referralTravellerRowMapper, qb);

        return referralTravellerRec;
    }


    public ReferralSearchRec getReferralById(final Long referralId) {
        // Just to ensure we trap db errors - not elegant but will do for the moment
        // unit JdbcUtil gets updated.
        DbActionCommand dbCmd = new DbActionCommand();
        dbCmd.setAscDesc(DbActionCommand.DESC);

        WithSelectQueryBuilder qb =
            new WithSelectQueryBuilder(Table.MATCHED_RFRL.getValue(), dbCmd, Field.REF_ID);

        // ***
        // * Build up the With Statement
        // ***
        qb.addSelect(Field.ID,                  Field.REF_ID,                       Table.RFRL);
        qb.addSelect(AdvancedField.VALUE_ZERO,  Field.REFERRAL_LEVEL);
        qb.addSelect(Field.STATUS,              Field.REFERRAL_STATUS,              Table.RFRL);
        qb.addSelect(Field.CREATED_DATETIME,                                        Table.RFRL);
        qb.addSelect(Field.ACTION_CODE,         Field.REFERRAL_REC_ACTION_CODE,     Table.RFRL);
        qb.addSelect(Field.PRIORITY,            Field.PRIORITY_REFERRAL,            Table.RFRL);
        qb.addSelect(Field.ADDITIONAL_INSTRUCTION_CODE,                             Table.RFRL);
        qb.addSelect(NullField.getInstance(Field.PRIORITY_HIT));
        qb.addSelect(AdvancedField.TOTAL_REFERRAL_HITS,  Field.TOTAL_REFERRAL_HITS);
        qb.addSelect(Field.MOVEMENT_TYPE,                                           Table.TRVLR);
        qb.addSelect(AdvancedField.MOVEMENT_STATUS_WITH_TBL_ALIAS);
        qb.addSelect(Field.TYPE_OF_TRAVEL,                                          Table.TRVLR);
        qb.addSelect(Field.ID,                      Field.TRAVELLER_ID,             Table.TRVLR);
        qb.addSelect(Field.TRAVELLER_TYPE,                                          Table.TRVLR);
        qb.addSelect(Field.FORENAME,                                                Table.TRVLR);
        qb.addSelect(Field.LAST_NAME,                                               Table.TRVLR);
        qb.addSelect(Field.BIRTH_DATE,                                              Table.TRVLR);
        qb.addSelect(Field.PRI_DOC_TYPE,                                            Table.TRVLR);
        qb.addSelect(Field.PRI_DOC_NO,                                              Table.TRVLR);
        qb.addSelect(Field.NATIONALITY,                                             Table.TRVLR);
        qb.addSelect(Field.FLIGHT_SEG_ID,                                           Table.FLTS);
        qb.addSelect(Field.OPER_CARRIER_CODE,                                       Table.FLTS);
        qb.addSelect(Field.OPER_FLIGHT_NO,                                          Table.FLTS);
        qb.addSelect(Field.DEP_AIRPORT_CODE,                                        Table.FLTS);
        qb.addSelect(Field.DEP_DATETIME,                                            Table.FLTS);
        qb.addSelect(Field.ARR_AIRPORT_CODE,                                        Table.FLTS);
        qb.addSelect(Field.ARR_DATETIME,                                            Table.FLTS);


        // * FROM
        qb.addTable(Table.REFERRALS,        Table.RFRL);
        qb.addTable(Table.TRAVELLERS,       Table.TRVLR);
        qb.addTable(Table.FLIGHT_SEGMENTS,  Table.FLTS);

        // **********
        // * WHERE
        // **********
        qb.addTableJoin( Table.TRVLR,   Field.FLTS_FLIGHT_SEG_ID
                       , Table.FLTS,    Field.FLIGHT_SEG_ID
                       );
        qb.addTableJoin( Table.TRVLR,   Field.ID
                       , Table.RFRL,    Field.TRA_ID
                       );

        // ** Referral
        qb.addWhereClause(new SimpleQueryArg(Field.ID, Table.RFRL, referralId));
        ReferralSearchRec refSearchRec = JdbcUtil.executeQueryById(daoSupport.getJdbcTemplate(), referralRowMapper, qb);

        return refSearchRec;
    }


    public int getReferralSearchCount(final ReferralSearchCommand search, final TableActionCommand tableCommand) {
        CountBuilder builder = new ConfiguredCountBuilder(tableCommand);
        getReferralSearchBuilder(search, builder);
        return JdbcUtil.executeQueryForCount(daoSupport.getJdbcTemplate(), builder);
    }
}
