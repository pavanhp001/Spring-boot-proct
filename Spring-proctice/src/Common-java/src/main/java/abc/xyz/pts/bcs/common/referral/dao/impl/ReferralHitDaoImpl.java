/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import abc.xyz.pts.bcs.common.dao.support.CustomRowMapper;
import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.QueryBuilder;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.support.query.arg.SimpleQueryArg;
import abc.xyz.pts.bcs.common.dao.utils.JdbcUtil;
import abc.xyz.pts.bcs.common.dto.ReferralHit;
import abc.xyz.pts.bcs.common.referral.dao.ReferralHitDAO;
import abc.xyz.pts.bcs.common.referral.dto.ReferralHitTargetRec;
import abc.xyz.pts.bcs.common.referral.dto.ReferralHitTargetRecImpl;
import abc.xyz.pts.bcs.common.referral.dto.ReferralTravellerRec;
import abc.xyz.pts.bcs.common.referral.dto.ReferralTravellerRecImpl;
import abc.xyz.pts.bcs.common.web.command.DbActionCommand;

@Repository("referralHitDAO")
public class ReferralHitDaoImpl implements ReferralHitDAO {
    private static final Logger LOGGER = Logger.getLogger( ReferralHitDaoImpl.class );
    private static final int VERSION_NUMBER_INITIAL_VALUE = 0 ;

    @Autowired
    private JdbcDaoSupport daoSupport;

    private final CustomRowMapper<ReferralHitTargetRecImpl> referralTargetRowMapper = new CustomRowMapper<ReferralHitTargetRecImpl>(
            ReferralHitTargetRecImpl.class);
    private final CustomRowMapper<ReferralTravellerRecImpl> referralTravellerRowMapper = new CustomRowMapper<ReferralTravellerRecImpl>(
            ReferralTravellerRecImpl.class);
    private final CustomRowMapper<ReferralHit> rowMapper = new CustomRowMapper<ReferralHit>(ReferralHit.class);

    private static final String COLUMN_NAMES = " id, ref_id, watlr_id, qualification_status, hit_type ,"
        +"                        hit_score, priority, severity_level, action_code, update_version_no ,"
        +"                        wl_source, wl_match_type, wl_forename, wl_last_name, wl_gender, wl_birth_date ,"
        +"                        wl_birth_place, wl_birth_cntry_code, wl_nationality, wl_doc_type, wl_doc_no, wl_doc_issue_cntry_code ,"
        +"                        wl_protocol_number, wl_wi_watl_name, wl_resc_code, app_hit_reason,  created_by ,"
        +"                        created_datetime, wl_tarwl_id " ;   //29  - modified_by , modified_datetime not inclued as part of insert


    private static final String INSERT_QUERY
        ="        INSERT into  referral_hits " + "("  + COLUMN_NAMES + ")"
        +           "VALUES (REFH_ID_SEQ.nextval, ?,?,?,?, ?,?,?,?,?,?, ?,?,?,?,? ,?,?,?,?,?, ?,?,?,?,?, ?,?, ? ) " ;  //1+ 27


    private static final String QUERY_SELECT_ALL =     "SELECT " + COLUMN_NAMES     + " FROM referral_hits"  ;

    private static final String QUERY_BY_ID = "SELECT " + COLUMN_NAMES + " FROM referral_hits  WHERE id = ?"  ;

    private static final String QUERY_BY_REFERRAL_ID = "SELECT " + COLUMN_NAMES + " FROM referral_hits  WHERE ref_id = ?";

    private static final String UPDATE_QUERY =  " UPDATE referral_hits "
        + "    SET  modified_by = ?, modified_datetime = ? , update_version_no = ?  WHERE id = ? and update_version_no = ?"    ;


    @Override
    public Long insert(final ReferralHit referralHit){
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug(referralHit.toString());
        }
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        daoSupport.getJdbcTemplate().update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
                        final PreparedStatement ps =
                            connection.prepareStatement(INSERT_QUERY, new String[] {"id"});
                        ps.setLong(1, referralHit.getRefId());
                        if (referralHit.getWatlrId() != null)
                        {
                            ps.setLong(2, referralHit.getWatlrId() );
                        } else
                        {
                            ps.setNull(2, Types.BIGINT);
                        }
                        ps.setString(3, referralHit.getQualificationStatus());
                        ps.setString(4, referralHit.getHitType());
                        ps.setLong(5, referralHit.getHitScore());
                        ps.setLong(6, referralHit.getPriority()) ;
                        ps.setLong(7, referralHit.getSeverityLevel()) ;
                        ps.setString(8, referralHit.getActionCode());
                        ps.setLong(9, VERSION_NUMBER_INITIAL_VALUE ) ;
                        ps.setString(10, referralHit.getWlSource()) ;
                        ps.setString(11, referralHit.getWlMatchType());
                        ps.setString(12,  referralHit.getWlForename()  );
                        ps.setString(13, referralHit.getWlLastName());
                        ps.setString(14, referralHit.getWlGender());
                        ps.setDate(15, referralHit.getWlBirthDate() == null ? null : new Date( referralHit.getWlBirthDate().getTimeInMillis())) ;
                        ps.setString(16, referralHit.getWlBirthPlace());
                        ps.setString(17, referralHit.getWlBirthCntryCode());
                        ps.setString(18, referralHit.getWlNationality());
                        ps.setString(19, referralHit.getWlDocType());
                        ps.setString(20, referralHit.getWlDocNo()) ;
                        ps.setString(21, referralHit.getWlDocIssueCntryCode());
                        ps.setString(22, referralHit.getWlProtocolNumber());
                        ps.setString(23, referralHit.getWlWiWatlName());
                        ps.setString(24, referralHit.getWlRescCode()) ;
                        if (referralHit.getAppHitReason() != null)
                        {
                            ps.setLong(25, referralHit.getAppHitReason() );
                        } else  {
                            ps.setNull(25, Types.BIGINT);
                        }
                        ps.setString(26, referralHit.getCreatedBy()) ;
                        ps.setTimestamp(27, new Timestamp(Calendar.getInstance().getTimeInMillis())) ; // CREATED_DATETIME

                        if (referralHit.getWlTarwlId() == null) {
                            ps.setNull(28, Types.BIGINT);
                        } else {
                            ps.setLong(28, referralHit.getWlTarwlId());
                        }
                        return ps;
                    }
                }, keyHolder);
        referralHit.setId( keyHolder.getKey().longValue());
        return keyHolder.getKey().longValue();
    }

    @Override
    public int update(final ReferralHit referralHit)
    {
        return
        daoSupport.getJdbcTemplate().update(
                UPDATE_QUERY, new PreparedStatementSetter() {
                    @Override
                    public void setValues(
                            final PreparedStatement ps)
                    throws SQLException {
                        ps.setString(1, referralHit.getModifiedBy() == null ? null : referralHit.getModifiedBy()) ;
                        ps.setTimestamp(2, referralHit.getModifiedDatetime() == null ? null : new Timestamp( referralHit.getModifiedDatetime().getTimeInMillis()));
                        ps.setLong(3, referralHit.getUpdateVersionNo() + 1L);
                        ps.setLong(4, referralHit.getId());
                        ps.setLong(5, referralHit.getUpdateVersionNo() );
                    }
                });
    }


    @Override
    public ReferralHit findById(final Long id){

            return daoSupport.getJdbcTemplate().queryForObject
            ( QUERY_BY_ID
            , new Object[]{ id }
            , rowMapper
            );
        }

    @Override
    public List<ReferralHit>  findByReferralId(final Long id){

        return daoSupport.getJdbcTemplate().query
        ( QUERY_BY_REFERRAL_ID
        , new Object[]{ id }
        , rowMapper
        );
    }

    @Override
    public List<ReferralHit> getReferralHits(){

        return daoSupport.getJdbcTemplate().query(QUERY_SELECT_ALL,rowMapper);

    }

    public void setDaoSupport(final JdbcDaoSupport daoSupport) {
        this.daoSupport = daoSupport;
    }


    @Override
    public ReferralHitTargetRec getReferralHitTarget(final Long referralHitId) {
        // Just to ensure we trap db errors - not elegant but will do for the
        // moment
        // unit JdbcUtil gets updated.
        final DbActionCommand dbCmd = new DbActionCommand();
        dbCmd.setAscDesc(DbActionCommand.DESC);

        final QueryBuilder qb = new QueryBuilder(dbCmd, Field.REF_ID, Table.REFERRAL_HITS);

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
        qb.addSelect(Field.WL_PROTOCOL_NUMBER, Table.REFERRAL_HITS); // reference
        qb.addSelect(Field.UPDATE_VERSION_NO, Table.REFERRAL_HITS);
        qb.addSelect(Field.APP_HIT_REASON, Table.REFERRAL_HITS);
        qb.addSelect(Field.DESCRIPTION, Field.RESC_CODE_DESC, Table.REASON_CODES);
        qb.addSelect(Field.WL_TARWL_ID, Table.REFERRAL_HITS);

        // * FROM
        qb.addTable(Table.REFERRAL_HITS);
        qb.addTable(Table.REASON_CODES);

        // * FROM
        qb.addOuterTableJoin(Table.REFERRAL_HITS, Field.WL_RESC_CODE, Table.REASON_CODES, Field.CODE,
                Table.REASON_CODES, Field.CODE);

        // * WHERE
        qb.addWhereClause(new SimpleQueryArg(Field.ID, Table.REFERRAL_HITS, referralHitId));

        // ** Execute and prepare to return data
        final ReferralHitTargetRec referralHitTargetRec = JdbcUtil.executeQueryById(daoSupport.getJdbcTemplate(),
                referralTargetRowMapper, qb);

        return referralHitTargetRec;
    }

    @Override
    public ReferralTravellerRec getReferralHitTraveller(final Long watchListRequestId) {
        // Just to ensure we trap db errors - not elegant but will do for the
        // moment
        // unit JdbcUtil gets updated.
        final DbActionCommand dbCmd = new DbActionCommand();
        dbCmd.setAscDesc(DbActionCommand.DESC);

        final QueryBuilder qb = new QueryBuilder(dbCmd, Field.ID, Table.WATCH_LIST_REQUESTS);

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
        final ReferralTravellerRec referralTravellerRec = JdbcUtil.executeQueryById(daoSupport.getJdbcTemplate(),
                referralTravellerRowMapper, qb);

        return referralTravellerRec;
    }

}
