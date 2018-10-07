/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.irisk.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import abc.xyz.pts.bcs.common.dao.support.CustomRowMapper;
import abc.xyz.pts.bcs.common.dto.Traveller;

/**
 * @author ryattapu
 *
 */

public class TravellerDAO {

    public static final String RISK_ASSESSMENT_STATUS_COMPLETED = "COMPLETED";
    public static final String RISK_ASSESSMENT_STATUS_INPROGRESS = "INPROGRESS";
    public static final String RISK_ASSESSMENT_STATUS_FAILED = "FAILED";
    public static final String RISK_ASSESSMENT_STATUS_NOTSTARTED = "NOTSTARTED";

    public void setDaoSupport(final JdbcDaoSupport daoSupport) {
        this.daoSupport = daoSupport;
    }

    private JdbcDaoSupport daoSupport;
    private final CustomRowMapper<Traveller> rowMapper = new CustomRowMapper<Traveller>(Traveller.class);

    private static final String QUERY_SELECT_ALL
                    = "    SELECT id, flts_flight_seg_id flight_seg_id, api_data_status, seq_no "
                    + "     , dcs_data_status, pnr_data_status, traveller_type "
                    + "     , pri_working_airport_code, middle_name, title "
                    + "     , created_datetime, boonl_id, boonl_id_has_pre_scan "
                    + "        , fltc_id flight_check_in_id, fltm_id flight_manifest_id "
                    + "     , birth_province_code, forename_3c fore_name_three_chars "
                    + "        , forename fore_name, last_name_3c last_name_three_chars, last_name"
                    + "     , update_version_no, origin_cntry_code "
                    + "     , pri_doc_type, dest_cntry_code, pri_doc_no, latest_update_type "
                    + "     , TRUNC(pri_doc_issue_date) pri_doc_issue_date, pnr_id "
                    + "        , TRUNC(pri_doc_expiry_date) pri_doc_expiry_date, name_id "
                    + "     , pri_doc_issue_cntry_code, dcs_id, visa_no, api_id "
                    + "     , visa_issue_cntry_code "
                    + "        , TRUNC(visa_issue_date) visa_issue_date, carpr_id "
                    + "     , TRUNC(visa_expiry_date) visa_expiry_date, visa_issue_place "
                    + "     , auth_flag, auth_username, gender, auth_datetime, nationality "
                    + "     , auth_full_name, bcs_username  "
                    + "        , TRUNC(birth_date) birth_date, birth_place "
                    + "     , birth_country_code, sdi_username, resident_cntry_code "
                    + "     , sdi_nsis_total_weighting, sdi_nsis_threshold "
                    + "     , origin_airport_code, movement_type, dest_airport_code "
                    + "     , birth_date_string, clearance_airport_code, full_name "
                    + "     , modified_datetime, addr_type, addr_name, addr_area "
                    + "     , addr_city, addr_cntry, addr_postcode, phone, email, seat_no "
                    + "     , bagtag_nos bag_tag_nos, booking_ref_no, api_dcs_perc, api_pnr_perc "
                    + "     , dcs_pnr_perc, ticket_no, ticket_type, ticket_status "
                    + "     , freq_flyer_flag, freq_flyer_carrier_code, freq_flyer_no"
                    + "     , sec_working_airport_code, re_routed_dep_airport_code "
                    + "     , re_routed_arr_airport_code, active_trav_status_datetime "
                    + "     , linkage_id, pre_scan_flag, tra_flts_at_risk_flag, at_risk_flag "
                    + "     , not_in_latest_api_flag, traa_id, flts_at_risk_flag  "
                    + "     , ma_hit_status, ma_hits_id, ma_hit_code, risk_assessment_status  "
                    + "     , dep_airport_code, arr_airport_code, oper_carrier_code, oper_flight_no "
                    + "     , arr_datetime, dep_datetime, arr_time, dep_time "                   
                    + "  FROM TRAVELLERS"
                    ;

    private static final String QUERY = QUERY_SELECT_ALL
    + "  WHERE id = ?"
    ;

    private static final String FIND_BY_FLIGHT_SEG_ID_AND_API_ID = QUERY_SELECT_ALL
    + "  WHERE flts_flight_seg_id = ? AND api_id = ?"
    ;

    private static final String UPDATE_AT_RISK_FLAG
        = "UPDATE travellers SET at_risk_flag = 'Y' WHERE id = ? ";

    /*
     * Basic Query to update the risk assessment status to one of the following values:
     * 'NOTSTARTED','INPROGRESS','FAILED','COMPLETED'
     */
    private static final String UPDATE_RISK_ASSESSMENT_STATUS = "UPDATE travellers SET risk_assessment_status = ? WHERE id = ? ";

    public Traveller findTravellerByFlightSegIdAndApiId(final Long flightSegId, final Long apiId){

        return daoSupport.getJdbcTemplate().queryForObject
        ( FIND_BY_FLIGHT_SEG_ID_AND_API_ID
        , new Object[]{ flightSegId,  apiId}
        , rowMapper
        );
    }

    public Traveller findTravellersById(final Long id){

        return daoSupport.getJdbcTemplate().queryForObject
        ( QUERY
        , new Object[]{ id }
        , rowMapper
        );
    }

    public List<Traveller> getTravellers(){

        return daoSupport.getJdbcTemplate().query
        ( QUERY_SELECT_ALL
        , rowMapper
        );
    }

     public int updateAtRiskFlag(final Long travellerId) {
        return daoSupport.getJdbcTemplate().update(UPDATE_AT_RISK_FLAG, new PreparedStatementSetter() {

            @Override
            public void setValues(final PreparedStatement ps) throws SQLException {
                ps.setLong(1, travellerId);
            }
        });
    }

     private int updateRiskAssessment(final Long travellerId, final String status) {
        return daoSupport.getJdbcTemplate().update(UPDATE_RISK_ASSESSMENT_STATUS, new PreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps) throws SQLException {
                ps.setString(1, status);
                ps.setLong(2, travellerId);
            }
        });
    }
     /**
      * Update the risk assessment status flag to COMPLETED
      */
     public int updateRiskAssessmentToCompleted(final Long travellerId) {
        return updateRiskAssessment(travellerId, RISK_ASSESSMENT_STATUS_COMPLETED);
    }
     /**
      * Update the risk assessment status flag to FAILED
      */
     public int updateRiskAssessmentToFailed(final Long travellerId) {
         return updateRiskAssessment(travellerId, RISK_ASSESSMENT_STATUS_FAILED);
     }
     /**
      * Update the risk assessment status flag to NOTSTARTED
      */
     public int updateRiskAssessmentToNotStarted(final Long travellerId) {
         return updateRiskAssessment(travellerId, RISK_ASSESSMENT_STATUS_NOTSTARTED);
     }
     /**
      * Update the risk assessment status flag to INPROGRESS
      */
     public int updateRiskAssessmentToInProgress(final Long travellerId) {
         return updateRiskAssessment(travellerId, RISK_ASSESSMENT_STATUS_INPROGRESS);
     }
}
