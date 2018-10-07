/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import abc.xyz.pts.bcs.common.dao.support.CustomRowMapper;
import abc.xyz.pts.bcs.common.dto.FlightSegment;

public class FlightSegmentsDAO {
    private static final String DATE_TIME_FORMAT_STRING_ORACLE = "yyyy-MM-DD\"T\"HH24:MI:SS";
    private static final DateTimeFormatter DATE_TIME_FORMATTER_JAVA = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

    private JdbcDaoSupport daoSupport;

    private static final String UPDATE_AT_RISK_FLAG = "UPDATE flight_segments SET at_risk_flag = 'Y' WHERE flight_seg_id = ?";
    private final CustomRowMapper<FlightSegment> rowMapper = new CustomRowMapper<FlightSegment>(FlightSegment.class);

    private static final String QUERY_SELECT
                            = "    SELECT flight_seg_id, oper_carrier_code, oper_flight_no "
                            + "     , dep_airport_code, arr_airport_code "
                            + "     , scheduled_dep_datetime, scheduled_arr_datetime "
                            + "     , flight_id, das_seg_seq_no seq_no "
                            + "  FROM FLIGHT_SEGMENTS "
                            ;

    private static final String QUERY_FIND_BY_ID = QUERY_SELECT
                            + "  WHERE flight_seg_id = ?"
                            ;


    private static final String QUERY_FIND_BY_ROUTE_DETAILS = QUERY_SELECT +
            "WHERE dep_airport_code = ? AND arr_airport_code = ? AND TO_CHAR(scheduled_dep_datetime, '" + DATE_TIME_FORMAT_STRING_ORACLE + "') = ? AND " +
            "TO_CHAR(scheduled_arr_datetime, '" + DATE_TIME_FORMAT_STRING_ORACLE + "') = ? AND oper_carrier_code = ? AND oper_flight_no = ?";

    public int updateAtRiskFlag(final Long flightSegId) {
        return daoSupport.getJdbcTemplate().update(UPDATE_AT_RISK_FLAG, new PreparedStatementSetter() {

            @Override
            public void setValues(final PreparedStatement ps) throws SQLException {
                ps.setLong(1, flightSegId);
            }
        });
    }
    public void setDaoSupport(final JdbcDaoSupport daoSupport) {
        this.daoSupport = daoSupport;
    }

    public FlightSegment findById(final Long id){
        return daoSupport.getJdbcTemplate().queryForObject
        ( QUERY_FIND_BY_ID
        , new Object[]{ id }
        , rowMapper
        );
    }

    public List <FlightSegment> findByRouteCarrier(final FlightSegment flightSegment) {
        final CustomRowMapper<FlightSegment> rowMapper = new CustomRowMapper<FlightSegment>(FlightSegment.class);
        final List <String> paramList = new ArrayList<String>();
        paramList.add(flightSegment.getDepAirportCode().trim());
        paramList.add(flightSegment.getArrAirportCode().trim());
        paramList.add(DATE_TIME_FORMATTER_JAVA.print(flightSegment.getScheduledDepDatetime().getTimeInMillis()));
        paramList.add(DATE_TIME_FORMATTER_JAVA.print(flightSegment.getScheduledArrDatetime().getTimeInMillis()));
        paramList.add(flightSegment.getOperCarrierCode().trim());
        paramList.add(flightSegment.getOperFlightNo().trim());
        return daoSupport.getJdbcTemplate().query(QUERY_FIND_BY_ROUTE_DETAILS, paramList.toArray(),rowMapper);
    }
}
