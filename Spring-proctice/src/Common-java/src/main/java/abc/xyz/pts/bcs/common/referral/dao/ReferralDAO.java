/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import abc.xyz.pts.bcs.common.dao.support.CustomRowMapper;
import abc.xyz.pts.bcs.common.dto.Referral;
import abc.xyz.pts.bcs.common.dto.ReferralDetails;
import abc.xyz.pts.bcs.common.dto.ReferralHit;
import abc.xyz.pts.bcs.common.dto.ReferralLog;
import abc.xyz.pts.bcs.common.dto.Traveller;
import abc.xyz.pts.bcs.common.enums.QualificationStatusType;

/**
 *
 * Its time we centralised this stuff instead of having 2 copied (ui & core)
 *
 * @author mmotlib-siddiqui
 *
 */
@Repository("referralDAO")
public class ReferralDAO implements ReferralDAOInterface {
    private JdbcDaoSupport daoSupport;
    private static Logger logger = Logger.getLogger(ReferralDAO.class);
    private static final Long VERSION_NUMBER_INITIAL_VALUE = 0L;

    private static final String INSERT_REFERRAL_HITS_QUERY
                    ="        INSERT into  referral_hits "
                    +"                    (    id, ref_id, watlr_id, qualification_status, hit_type ,"
                    +"                        hit_score, priority, severity_level, action_code, update_version_no ,"
                    +"                        wl_source, wl_match_type, wl_forename, wl_last_name, wl_gender, wl_birth_date ,"
                    +"                        wl_birth_place, wl_birth_cntry_code, wl_nationality, wl_doc_type, wl_doc_no, wl_doc_issue_cntry_code ,"
                    +"                        wl_protocol_number, wl_wi_watl_name, wl_resc_code, app_hit_reason,  created_by ,"
                    +"                        created_datetime, wl_tarwl_id"
                    +"                    )"
                    +           "VALUES (REFH_ID_SEQ.nextval, ?,?,?,?, ?,?,?,?,?,?, ?,?,?,?,? ,?,?,?,?,?, ?,?,?,?,?, ?,?, ? ) " ;  //1+ 27

    private static final String INSERT_QUERY
                    =      "INSERT into  referrals "
                    +"                ( id, tra_id, action_code, status, update_version_no"
                    +"                , pri_actioning_airport_code, sec_actioning_airport_code"
                    +"                 , closure_code, modified_by, modified_datetime"
                    +"                , created_by, created_datetime , priority, severity_level )"
                    +"    VALUES (REF_ID_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?, ?) " ;


    private static final String SELECT_ALL_QUERY =
                    "SELECT           id, tra_id, action_code"
                    +"                 , status, pri_actioning_airport_code, sec_actioning_airport_code"
                    +"                 , closure_code, modified_by, modified_datetime"
                    +"                , created_by, created_datetime, priority "
                    +"                  from referrals" ;

    private static final String SELECT_BY_ID_QUERY =
                    "SELECT           id, tra_id , priority , action_code"
                    +"                , status, update_version_no , pri_actioning_airport_code, sec_actioning_airport_code"
                    +"                 , closure_code, modified_by, modified_datetime"
                    +"                , created_by, created_datetime  "
                    +"                  from referrals"
                    +"                   WHERE id = ? ";


    private static final String SELECT_BY_TRAVELLER_ID_QUERY
                    ="        SELECT    id, tra_id, action_code, status, update_version_no "
                    +"                , pri_actioning_airport_code, sec_actioning_airport_code "
                    +"                 , closure_code, modified_by, modified_datetime "
                    +"                , created_by, created_datetime , priority, update_version_no "
                    +"        from referrals"
                    +"         WHERE TRA_ID = ? ";

    private static final String TRAVELLER_WITHOUT_REFERRAL_QUERY =
        "SELECT id from travellers trav where trav.id not in ( select tra_id from referrals ) "  ;

    private static final String SELECT_REF_DETAILS_BY_TRA_ID =
        "SELECT refs.id, refs.status, refs.notification_status, refs.update_version_no,  fltseg.dep_airport_code, fltseg.arr_airport_code"
        + "    ,fltseg.dep_datetime, fltseg.arr_datetime, fltseg.scheduled_dep_datetime, fltseg.scheduled_arr_datetime"
        + "    ,fltseg.oper_carrier_code, fltseg.oper_flight_no, fltseg.flight_status, fltseg.flight_type, fltseg.flight_seg_id "
        + "FROM referrals refs, flight_segments fltseg, travellers trv"
        + "    WHERE refs.tra_id = ? and trv.id =  refs.tra_id and trv.flts_flight_seg_id = fltseg.flight_seg_id";

    private static final String UPDATE_NOTIFICATION_STATUS_SQL = "UPDATE referrals SET notification_status = ?  WHERE id = ?";

    /**
     * update Referral record
     *
     * @param referral
     * @return number of rows updated
     */
    @Override
    public int update(final Referral referral) {
        if (logger.isDebugEnabled()) {
            logger.debug(referral.toString());
        }

        final String SQL =
            " UPDATE referrals "
          + "    SET modified_by = ? "
          + "      , modified_datetime = SYSDATE "
          + "      , status = ? "
          + "      , priority = ? "
          + "      , severity_level = ? "
          + "      , action_code = ? "
          + "      , closure_code = ? "
          + "      , additional_instruction_code = ? "
          + "      , additional_instruction_desc = ? "
          + "      , update_version_no = ? "
          + "  WHERE id = ? "
          + "    AND update_version_no = ? "
          ;


        final List<Object> values = new ArrayList<Object>();
        values.add(referral.getModifiedBy());
        values.add(referral.getStatus());
        values.add(referral.getPriority());
        values.add(referral.getSeverityLevel());
        values.add(referral.getActionCode());
        values.add(referral.getClosureCode());
        values.add(referral.getAdditionalInstructionCode());
        values.add(referral.getAdditionalInstructionDesc());
        values.add(referral.getUpdateVersionNo() + 1L);
        values.add(referral.getId());        
        values.add(referral.getUpdateVersionNo());

        return daoSupport.getJdbcTemplate().update(SQL, values.toArray());
    }


    /**
     * update Referral hit.
     *
     * @param referralHit
     * @return number of rows updated
     */
    @Override
    public int update(final ReferralHit referralHit) {
        final String SQL =
            " UPDATE referral_hits "
          + "    SET modified_by = ? "
          + "      , modified_datetime = SYSDATE "
          + "      , qualification_status = ? "
          + "      , update_version_no = ? "
          + "  WHERE id = ? "
          + "    AND update_version_no = ? "
          ;

        final List<Object> values = new ArrayList<Object>();
        values.add(referralHit.getModifiedBy());
        values.add(referralHit.getQualificationStatus());
        values.add(referralHit.getUpdateVersionNo() + 1);

        values.add(referralHit.getId());
        values.add(referralHit.getUpdateVersionNo());

        return daoSupport.getJdbcTemplate().update(SQL, values.toArray());
    }

    /**
     * Add a referral log
     *
     * @param referralLog
     * @return number of rows added
     */
    @Override
    public int insert(final ReferralLog referralLog) {
        final String SQL =
            " INSERT INTO referral_logs "
          + " ( id, ref_id, event_type, closure_code, remarks, hit_type, hit_id, NOTF_AIRPORT_CODE, "
          + "   NOTF_RECIPIENT_CATEGORY, NOTF_ROLES_NOTIFIED, NOTF_MEDIA_TYPE, NOTF_ACTION_STATUS, "
          + "   NOTF_ACTION_ERROR_MESSAGE, CREATED_BY, CREATED_DATETIME "
          + "  ) "
          + " VALUES (REFL_ID_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, "
          + "         ?, ?, ?, ?, "
          + "         ?, ?, SYSDATE) "
          ;



        final List<Object> values = new ArrayList<Object>();
        values.add(referralLog.getRefId());
        values.add(referralLog.getEventType());
        values.add(referralLog.getClosureCode());
        values.add(referralLog.getRemarks());
        values.add(referralLog.getHitType());
        values.add(referralLog.getHitId());
        values.add(referralLog.getNotfAirportCode());
        values.add(referralLog.getNotfRecipientCategory());
        values.add(referralLog.getNotfRolesNotified());
        values.add(referralLog.getNotfMediaType());
        values.add(referralLog.getNotfActionStatus());
        values.add(referralLog.getNotfActionErrorMessage());
        values.add(referralLog.getCreatedBy());

        return daoSupport.getJdbcTemplate().update(SQL, values.toArray());
    }

    /**
     * Get all referral hits for the given referral id.
     *
     * @param referralId
     * @return list of referral hits
     */
    @Override
    public List <ReferralHit> getReferralHits(final Long referralId) {
        if (logger.isDebugEnabled()) {
            logger.debug("referralId=" + referralId);
        }

        final CustomRowMapper<ReferralHit> rowMapper = new CustomRowMapper<ReferralHit>(ReferralHit.class);

        // This may need extending to add more parameters as needed
        final String SQL =
              " SELECT id, ref_id, watlr_id, qualification_status, hit_type, hit_score,"
            + "        priority, severity_level, action_code, update_version_no,  "
            + "        wl_source, wl_match_type, wl_forename, wl_last_name, wl_gender, wl_birth_date, "
            + "        wl_birth_place, wl_birth_cntry_code, wl_nationality, wl_doc_type, wl_doc_no, "
            + "        wl_doc_issue_cntry_code, wl_protocol_number, wl_wi_watl_name, wl_resc_code, "
            + "        app_hit_reason, modified_by, modified_datetime, created_by, created_datetime, "
            + "        wl_tarwl_id "
            + "   FROM referral_hits "
            + "  WHERE ref_id = ? "
            ;

        final List<Object> values = new ArrayList<Object>();
        values.add(referralId);

        return daoSupport.getJdbcTemplate().query(SQL, values.toArray(), rowMapper);
    }


    /**
     * Get Referral hits in map using the referral hit id as the key.
     *
     * @param referralId
     * @return
     */
    @Override
    public Map <Long, ReferralHit> getReferralHitsMap(final Long referralId) {
        final List <ReferralHit> hitList = getReferralHits(referralId);
        final Map <Long, ReferralHit> hitMap = new HashMap <Long, ReferralHit>(hitList.size());

        for (final ReferralHit hit : hitList) {
            hitMap.put(hit.getId(), hit);
        }

        return hitMap;
    }


    /**
     * Get all referral logs for a referral.
     * THe referral logs retured are ordered so that the latest ones appear at the top.
     *
     * @param referralId
     * @return referral logs
     */
    @Override
    public List <ReferralLog> getReferralLogs(final Long referralId) {
        if (logger.isDebugEnabled()) {
            logger.debug("referralId=" + referralId);
        }

        final CustomRowMapper<ReferralLog> rowMapper = new CustomRowMapper<ReferralLog>(ReferralLog.class);

        final String SQL =
              " SELECT id, ref_id, event_type, closure_code, remarks, hit_type, hit_id, "
            + "        notf_airport_code, notf_recipient_category, notf_roles_notified, "
            + "        notf_media_type, notf_action_status, notf_action_error_message, "
            + "        created_by, created_datetime "
            + "   FROM referral_logs "
            + "  WHERE ref_id = ? "
            + "  ORDER BY created_datetime DESC, id DESC "
            ;

        final List<Object> values = new ArrayList<Object>();
        values.add(referralId);

        return daoSupport.getJdbcTemplate().query(SQL, values.toArray(), rowMapper);
    }

    /**
     * Get Referral data by Id.
     *
     *
     * @param referralId
     * @return
     */
    @Override
    public Referral getReferralById(final Long referralId) {
        if (logger.isDebugEnabled()) {
            logger.debug("referralId=" + referralId);
        }


        final CustomRowMapper<Referral> rowMapper = new CustomRowMapper<Referral>(Referral.class);

        final String SQL =
              " SELECT id, tra_id, priority, severity_level, action_code, status, update_version_no, "
            + "        pri_actioning_airport_code, sec_actioning_airport_code, closure_code,  "
            + "        modified_by, modified_datetime, created_by, created_datetime "
            + "   FROM referrals "
            + "  WHERE id = ? "
            ;

        final List<Object> values = new ArrayList<Object>();
        values.add(referralId);

        final List<Referral> referralList = daoSupport.getJdbcTemplate().query(SQL, values.toArray(), rowMapper);

        // We are expecting 1 row so fetch it
        Referral referral = null;
        if (referralList != null && !referralList.isEmpty()) {
           referral = referralList.get(0);
        }

        return referral;
    }

    /**
     * Get Referral by ID and UpdateVersionNo.
     *
     * @param referralId
     * @param versionNo
     * @return
     */
    @Override
    public Referral getReferralById(final Long referralId, final Long versionNo) {
        if (logger.isDebugEnabled()) {
            logger.debug("referralId=" + referralId + " versionNo=" + versionNo);
        }

        final CustomRowMapper<Referral> rowMapper = new CustomRowMapper<Referral>(Referral.class);

        final String SQL =
              " SELECT id, tra_id, priority, severity_level, action_code, status, update_version_no, "
            + "        pri_actioning_airport_code, sec_actioning_airport_code, closure_code,  "
            + "        modified_by, modified_datetime, created_by, created_datetime "
            + "   FROM referrals "
            + "  WHERE id = ? "
            + "    AND update_version_no = ? "
            ;

        final List<Object> values = new ArrayList<Object>();
        values.add(referralId);
        values.add(versionNo);

        final List<Referral> referralList = daoSupport.getJdbcTemplate().query(SQL, values.toArray(), rowMapper);

        // We are expecting 1 row so fetch it
        Referral referral = null;
        if (referralList != null && !referralList.isEmpty()) {
           referral = referralList.get(0);
        }

        if (logger.isDebugEnabled()) {
            logger.debug(" found data? " + (referral == null ? "NO" : "YES"));
        }

        return referral;
    }


    /**
     * Get the latest Referral Log.
     *
     * @param referralId
     * @return
     */
    @Override
    public ReferralLog getLatestReferralLog(final Long referralId) {
        final List <ReferralLog> refLogList = getReferralLogs(referralId);
        if (!refLogList.isEmpty()) {
            return refLogList.get(0); // return the top one which should be the latest
        }

        return null;
    }


    public void setDaoSupport(final JdbcDaoSupport daoSupport) {
        this.daoSupport = daoSupport;
    }


    @Override

    // sai
    public Long insert(final Referral referral) {
        if(logger.isDebugEnabled()){
            logger.debug(referral.toString());
        }
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        daoSupport.getJdbcTemplate().update(
            new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
                    final PreparedStatement ps =
                        connection.prepareStatement(INSERT_QUERY, new String[] {"id"});
                        ps.setLong(1, referral.getTraId());
                        ps.setString(2, referral.getActionCode()  );
                        ps.setString(3, referral.getStatus());
                        ps.setLong(4, VERSION_NUMBER_INITIAL_VALUE );
                        ps.setString(5,  referral.getPriActioningAirportCode());
                        ps.setString(6, referral.getSecActioningAirportCode());
                        ps.setString(7, referral.getClosureCode());
                        ps.setString(8, referral.getModifiedBy());
                        ps.setTimestamp(9, referral.getModifiedDatetime() == null ? null : new Timestamp( referral.getModifiedDatetime().getTimeInMillis()))  ;
                        ps.setString(10, referral.getCreatedBy() ) ;
                        ps.setTimestamp(11, new Timestamp( Calendar.getInstance().getTimeInMillis())) ;
                        ps.setDouble(12, referral.getPriority() == null ? 0 : referral.getPriority() );
                        ps.setLong(13, referral.getSeverityLevel() == null ? 0 : referral.getSeverityLevel());
                        return ps;
                    }
                }, keyHolder);
        referral.setId(keyHolder.getKey().longValue());
        referral.setUpdateVersionNo(VERSION_NUMBER_INITIAL_VALUE);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Referral findByTravellerId(final Long travellerId) {
        Referral referral = null;
        final CustomRowMapper<Referral> rowMapper = new CustomRowMapper<Referral>(Referral.class);
        final List<Referral> referralList =
                        daoSupport.getJdbcTemplate().query
                        ( SELECT_BY_TRAVELLER_ID_QUERY
                        , new Object[]{ travellerId }
                        , rowMapper
                        );
        if(!referralList.isEmpty()){
            referral = referralList.get(0);
        }
        return referral;
    }

    @Override
    public Traveller findTravellerWithoutReferrals() {
        Traveller traveller  = null;
        final CustomRowMapper<Traveller> travellerRowMapper = new CustomRowMapper<Traveller>(Traveller.class);
        final List<Traveller> travellerList = daoSupport.getJdbcTemplate().query(TRAVELLER_WITHOUT_REFERRAL_QUERY, travellerRowMapper);

        // We just  need a traveller from the List who has nt got any referrals , so we pick up the first one.
        if(!travellerList.isEmpty())
        {
            traveller = travellerList.get(0);
        }
        return traveller;
    }


    @Override
    public Referral findById(final Long id) {
        Referral referral = null;
        final CustomRowMapper<Referral> rowMapper = new CustomRowMapper<Referral>(Referral.class);
        final List<Referral> referralList =
                        daoSupport.getJdbcTemplate().query
                        ( SELECT_BY_ID_QUERY
                        , new Object[]{ id }
                        , rowMapper
                        );
        if(!referralList.isEmpty()){
            referral = referralList.get(0);
        }
        return referral;
    }


    @Override
    public List <Referral> getReferrals() {
        final CustomRowMapper <Referral> rowMapper = new CustomRowMapper <Referral>(Referral.class);
        return daoSupport.getJdbcTemplate().query(SELECT_ALL_QUERY, rowMapper);
    }

    @Override
    public ReferralDetails findReferralDetailsByTravellerId(final Long traId) {
        ReferralDetails referralDetails = null;

        final CustomRowMapper<ReferralDetails> referralDetailsMapper = new CustomRowMapper<ReferralDetails>(ReferralDetails.class);

        final List<ReferralDetails> referralDetailsList =
            daoSupport.getJdbcTemplate().query
            ( SELECT_REF_DETAILS_BY_TRA_ID
            , new Object[]{ traId }
            , referralDetailsMapper
            );
        if(!referralDetailsList.isEmpty()){
            referralDetails = referralDetailsList.get(0);
        }
        return referralDetails;
    }

    @Override
    public int updateNotificationStatus(final Long id, final String alertActionType, final Long updateVersionNumber ) {
        return
        daoSupport.getJdbcTemplate().update(
                UPDATE_NOTIFICATION_STATUS_SQL, new PreparedStatementSetter() {
                    @Override
                    public void setValues(final PreparedStatement ps) throws SQLException {
                        if (alertActionType != null) {
                            ps.setString(1, alertActionType) ;
                        } else {
                            ps.setNull(1, Types.VARCHAR);
                        }
                        ps.setLong(2, id);
                    }
                });
    }

    @Override
    public Long insertReferralHit(final ReferralHit referralHit) {
        if(logger.isDebugEnabled()){
            logger.debug(referralHit.toString());
        }
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        daoSupport.getJdbcTemplate().update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
                        final PreparedStatement ps =
                            connection.prepareStatement(INSERT_REFERRAL_HITS_QUERY, new String[] {"id"});
                        ps.setLong(1, referralHit.getRefId());
                        if (referralHit.getWatlrId() != null) {
                            ps.setLong(2, referralHit.getWatlrId() );
                        } else {
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
                        if (referralHit.getAppHitReason() != null) {
                            ps.setLong(25, referralHit.getAppHitReason() );
                        } else {
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


    /**
     * Establish the highest severity for a referral - exclude hits that have been rules out
     *
     * @param referralId
     * @return severity
     */
    @Override
    public Long establishHighestReferralSeverity(final Long referralId) {
        final String SQL =
              " SELECT MAX(severity_level)"
            + "   FROM referral_hits "
            + "  WHERE ref_id = ? "
            + "    AND qualification_status NOT IN (?) "
            ;

        final List <Object> values = new ArrayList <Object>();
        values.add(referralId);
        values.add(QualificationStatusType.OUT.name());

        final Long severity = daoSupport.getJdbcTemplate().queryForLong(SQL, values.toArray());

        if (logger.isDebugEnabled()) {
            logger.debug("referralId=" + referralId + " highestSeverity=" + severity);
        }

        return severity;
    }
}
