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
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import abc.xyz.pts.bcs.common.dao.support.CustomRowMapper;
import abc.xyz.pts.bcs.common.irisk.dto.WatchListRequest;

/**
 * @author ryattapu
 *
 */
public class WatchListRequestDAO {
    @Autowired
    private JdbcDaoSupport daoSupport;
    private final CustomRowMapper<WatchListRequest> rowMapper = new CustomRowMapper<WatchListRequest>(WatchListRequest.class);

    private static final Logger log = Logger.getLogger(WatchListRequestDAO.class);

    private static final String INSERT_QUERY
                      = " INSERT into watch_list_requests "
                      + "        ( id, tra_id, forename, watch_list_source, last_name, gender "
                      + "      , birth_date, birth_place, birth_cntry_code, nationality "
                      + "      , doc_type, doc_no, doc_issue_cntry_code, traveller_data_source "
                      + "      , request_datetime "
                      + "      ) "
                      + " VALUES(WATLR_ID_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
                      ;

    private static final String SELECT_QUERY
                     = " SELECT id, tra_id, forename, watch_list_source, last_name, gender "
                     + "      , birth_date, birth_place, birth_cntry_code, nationality "
                     + "      , doc_type, doc_no, doc_issue_cntry_code, traveller_data_source "
                     + "      , request_datetime, response_datetime, response_status, error_message "
                     + "  FROM watch_list_requests "
                     + " WHERE id = ? "

                      ;
    private static final String UPDATE_QUERY
                    = " UPDATE watch_list_requests "
                    + "    SET  response_datetime = ?, response_status = ?, error_message =? "
                    + " WHERE id = ? "     ;


    public WatchListRequest findByMessageId(final Long id){
        WatchListRequest request = null;
        final List<WatchListRequest>  list = daoSupport.getJdbcTemplate().query
                                        ( SELECT_QUERY
                                        , new Object[]{ id }
                                        , rowMapper
                                        );
        if(list.size() > 0){
            request = list.get(0);
        }
        return request;

    }

    public int update(final WatchListRequest request){
        return
        daoSupport.getJdbcTemplate().update(
                UPDATE_QUERY, new PreparedStatementSetter() {
                    @Override
                    public void setValues(
                            final PreparedStatement ps)
                            throws SQLException {
                        ps.setTimestamp(1, request.getResponseDatetime() == null ? null : new Timestamp(request.getResponseDatetime().getTimeInMillis()));
                        ps.setString(2, request.getResponseStatus());
                        ps.setString(3, request.getErrorMessage());
                        ps.setLong(4, request.getId());
                    }
                });
    }

    public Long insert(final WatchListRequest request){

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        daoSupport.getJdbcTemplate().update(
            new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
                    final PreparedStatement ps =
                    connection.prepareStatement(INSERT_QUERY, new String[] {"id"});
                    ps.setLong(1, request.getTraId());
                    ps.setString(2, request.getForename());
                    ps.setString(3, request.getWatchListSource());
                    ps.setString(4, request.getLastName());
                    ps.setString(5, request.getGender());
                    ps.setDate(6, request.getBirthDate() == null ? null : new Date(request.getBirthDate().getTimeInMillis()));
                    ps.setString(7, request.getBirthPlace());
                    ps.setString(8, request.getBirthCntryCode());
                    ps.setString(9, request.getNationality());
                    ps.setString(10, request.getDocType());
                    ps.setString(11, request.getDocNo());
                    ps.setString(12, request.getDocIssueCntryCode());
                    ps.setString(13, request.getTravellerDataSource());
                    ps.setTimestamp(14, new Timestamp( Calendar.getInstance().getTimeInMillis() )) ;
                    return ps;
                }
            },
            keyHolder);
        request.setId(keyHolder.getKey().longValue());
        return keyHolder.getKey().longValue();

    }

    public void setDaoSupport(final JdbcDaoSupport daoSupport) {
        this.daoSupport = daoSupport;
    }
}
