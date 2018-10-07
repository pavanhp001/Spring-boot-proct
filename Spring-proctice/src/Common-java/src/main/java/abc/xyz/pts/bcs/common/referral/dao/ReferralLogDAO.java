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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import abc.xyz.pts.bcs.common.dao.support.CustomRowMapper;
import abc.xyz.pts.bcs.common.dto.ReferralLog;

/**
 * @author ryattapu
 *
 */
public class ReferralLogDAO {
    @Autowired
    private JdbcDaoSupport daoSupport;
    private final CustomRowMapper<ReferralLog> rowMapper = new CustomRowMapper<ReferralLog>(ReferralLog.class);

    private static final Logger log = Logger.getLogger(ReferralLogDAO.class);

    private static final String COLUMN_NAMES =  "   id  ,ref_id  ,event_type  " +
                                                " , closure_code ,remarks ,hit_type  ,hit_id  ,notf_airport_code " +
                                                " , notf_recipient_category  ,notf_roles_notified  ,notf_media_type " +
                                                " , notf_action_status  , notf_action_error_message  ,created_by  ,created_datetime" ;

    private static final String INSERT_QUERY
                      = " INSERT into referral_logs"
                      + "        ( id, ref_id, event_type "
                      + "      , hit_type, hit_id, remarks, created_by, created_datetime "
                      + "      ) "
                      + " VALUES(REFL_ID_SEQ.nextVal, ?, ?, ?, ?, ?, ?, ?)"
                      ;

    private static final String SELECT_QUERY   = "SELECT "+ COLUMN_NAMES  + " FROM referral_logs  WHERE id = ? ";

    public ReferralLog findByReferralId(final Long id){
        return daoSupport.getJdbcTemplate().queryForObject
        ( SELECT_QUERY
        , new Object[]{ id }
        , rowMapper
        );
    }

    public Long insert(final ReferralLog referralLog){
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        daoSupport.getJdbcTemplate().update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
                        final PreparedStatement ps =
                        connection.prepareStatement(INSERT_QUERY, new String[] {"id"});
                        ps.setLong(1, referralLog.getRefId());
                        ps.setString(2, referralLog.getEventType());
                        ps.setString(3, referralLog.getHitType());
                        if (referralLog.getHitId() != null) {
                            ps.setLong(4, referralLog.getHitId() );
                        } else  {
                            ps.setNull(4, Types.BIGINT);
                        }
                        // QAT-609 Added new Remarks column
                        if (referralLog.getRemarks() != null) {
                            ps.setString(5, referralLog.getRemarks() );
                        } else  {
                            ps.setNull(5, Types.VARCHAR);
                        }
                        ps.setString(6, referralLog.getCreatedBy());
                        ps.setTimestamp(7, new Timestamp( Calendar.getInstance().getTimeInMillis()));
                        return ps;
                    }
                },keyHolder);
        return keyHolder.getKey().longValue();

    }


    public void setDaoSupport(final JdbcDaoSupport daoSupport) {
        this.daoSupport = daoSupport;
    }

}
