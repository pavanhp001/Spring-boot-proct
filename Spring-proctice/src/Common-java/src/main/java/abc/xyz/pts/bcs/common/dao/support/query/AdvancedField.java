/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query;

/**
 * Use for fields that have anything other than the DB field name
 */
public enum AdvancedField {

    // ALPHABETICAL
    ACTIVE_COUNT(" (SELECT COUNT(1) FROM " + Table.TARGET_WATCH_LISTS +" twl WHERE twl.FILE_REFERENCE_NUMBER = wlif.FILE_REFERENCE_NUMBER) active_count "),
    ALL("*"), // DON'T USE THIS!!!
    ARR_TIME("to_char(" + Table.FLIGHT_SEGMENTS.getValue() + ".arr_datetime, 'hh24:mi') arr_time"),
    APP_BUSINESS_RULE("DECODE(expected_movement_type_code, 'C', c_result_code, result_code)"),
    APP_CANCELLED("sum(decode(" + Table.TRANSACTION_DETAIL_LOG + ".summary_message_code,8505,1, 0))"),
    APP_CARRIER("decode(" + Table.ETA_USER + ".user_airline_crs_id,'99','PORTAL'," + Table.ETA_USER + ".user_airline_crs_id)"),
    APP_CITY("decode(" + Table.ETA_USER + ".user_airline_crs_id,'99','-'," + Table.ETA_USER + ".user_city_code)"),
    APP_CONTACT_GOVT("sum(decode(" + Table.TRANSACTION_DETAIL_LOG + ".summary_message_code,8590,1, 0))"),
    APP_COUNTRY("decode(" + Table.ETA_USER + ".user_airline_crs_id,'99','-'," + Table.ETA_USER + ".user_iata_country_code)"),
    APP_DIRECTIVE("DECODE(expected_movement_type_code, 'C', c_message_code, message_code)"),
    APP_DONOT_BRD("sum(decode(" + Table.TRANSACTION_DETAIL_LOG + ".summary_message_code,8502,1,0))"),
    APP_OK_BRD("sum(decode(" + Table.TRANSACTION_DETAIL_LOG + ".summary_message_code,8501,1,8503,1,8519,1,8517,1,0))"),
    APP_TRANSACTIONS("sum(decode(" + Table.TRANSACTION_DETAIL_LOG + ".summary_message_code,8501,1,8503,1,8505,1,8517,1,8519,1,8590,1,8502,1,0))"),
    AT_RISK_FLAG("MIN (" + Table.TRAVELLERS.getValue() + ".at_risk_flag) at_risk_flag"),
    BILLING_DIRECTION("decode(expected_direction_code,'T','Inbound','I','Inbound','O','Outbound',expected_direction_code)"),
    BILLING_EXPECTED("sum(decode(travel_type_code,'N',1,0))"),
    BILLING_TRANSACTION("sum(decode(travel_type_code,'N',1,'T',1,'X',1,0))"),
    BILLING_TRANSIT("sum(decode(travel_type_code,'T',1,'X',1,0))"),
    BIRTH_DATE("NVL(birth_date, birth_date_from)"),
    BIRTH_DATE_FORMATTED(" DECODE(SUBSTR(birth_date, 7, 2), '00', NULL, SUBSTR(birth_date, 7, 2)) ||'-'||DECODE(SUBSTR(birth_date, 5, 2), '00', NULL, SUBSTR(birth_date, 5, 2))||'-'||SUBSTR(birth_date, 1, 4) "),
    CODE_SHARE("DECODE(NVL(mkt_flight_nos, '0'), '0', '0', '1') code_share"),
    COUNT_RECORDS(" COUNT(*) "),
    DATE_OF_LAST_CHANGE("NVL(modified_datetime, created_datetime) date_of_last_change"),
    DEP_DATE(" TO_DATE(NVL(" + Table.SCHEDULES_MASTER.getValue() + ".dep_date," + Table.SCHEDULES_TRANSACTIONS.getValue() + ".dep_date)) "),
    DEP_TIME("to_char(" + Table.FLIGHT_SEGMENTS.getValue() + ".dep_datetime, 'hh24:mi') dep_time"),
    DOCUMENT_NUMBER(" DECODE(" + Table.TRAVELLERS.getValue() + ".pri_doc_type" +
            ",'P',(" + Table.TRAVELLERS.getValue() + ".pri_doc_type||'/'||" + Table.TRAVELLERS.getValue() + ".pri_doc_no) " +
            ",'O',(" + Table.TRAVELLERS.getValue() + ".pri_doc_type||'/'||" + Table.TRAVELLERS.getValue() + ".pri_doc_no) " +
            ",'') document_number"),
    DOCUMENT_NUMBER_FUZZY_SEARCH(" DECODE(" + Table.FUZZY_SEARCH_TRAVELLER.getValue() + ".pri_doc_type" +
            ",'P',(" + Table.FUZZY_SEARCH_TRAVELLER.getValue() + ".pri_doc_type||'/'||" + Table.FUZZY_SEARCH_TRAVELLER.getValue() + ".pri_doc_no) " +
            ",'O',(" + Table.FUZZY_SEARCH_TRAVELLER.getValue() + ".pri_doc_type||'/'||" + Table.FUZZY_SEARCH_TRAVELLER.getValue() + ".pri_doc_no) " +
            ",'') document_number"),
    DUPLICATES_COUNT(" (SELECT COUNT(1) FROM " + Table.IMPORT_TARGETS +" it WHERE it.WATLIF_ID = wlif.id and status = 'D') duplicates_count "),
    ERRORS_COUNT(" (SELECT COUNT(1) FROM " + Table.IMPORT_TARGETS +" it WHERE it.WATLIF_ID = wlif.id and status = 'E') errors_count "),
    EXPECTED_DATE_TIME(" TO_DATE(EXPECTED_DATE||EXPECTED_TIME, 'YYYYMMDDHH24MISS') "),
    FILE_STATUS(" decode((select count(1) from IMPORT_TARGETS it where it.WATLIF_ID = wlif.id and status = 'I') " +
            " ,0,decode((select count(1) from IMPORT_TARGETS it where it.WATLIF_ID = wlif.id and status = 'E') " +
            " ,0,decode((select count(1) from IMPORT_TARGETS it where it.WATLIF_ID = wlif.id and status = 'D') " +
            " ,0,decode((select count(1) from IMPORT_TARGETS it where it.WATLIF_ID = wlif.id and status = 'R') " +
            " ,0,decode((select count(1) from TARGET_WATCH_LISTS twl where twl.FILE_REFERENCE_NUMBER = wlif.FILE_REFERENCE_NUMBER) " +
            " ,0,'RM','A'),'R'),'D'),'E'),'I') file_status "),
    FLIGHT_NUMBER(Table.FLIGHT_SEGMENTS.getValue() + ".oper_carrier_code || " + Table.FLIGHT_SEGMENTS.getValue() + ".oper_flight_no flight_number"),
    FULL_NAME(Table.TRAVELLERS + "." + Field.LAST_NAME + ", " + Table.TRAVELLERS + "." + Field.GIVEN_NAMES),
    HIT_TYPE_SEQ(" CASE " + Table.RFRL_HIT + ".hit_type WHEN 'WL' THEN 1 ELSE 2 END "),
    IN_PROGRESS_COUNT(" (select count(1) from IMPORT_TARGETS it where it.WATLIF_ID = wlif.id and status = 'I') in_progress_count "),
    MOVEMENT_STATUS("DECODE("+ Table.FLIGHT_MANIFESTS + ".movement_type,'EXPECTED','E','CANCELLED','C','DENIED','D', 'N') movement_status"),
    MOVEMENT_STATUS_TRV("DECODE("+ Table.TRAVELLERS + ".movement_type,'EXPECTED','E','CANCELLED','C','DENIED','D', 'N') movement_status"),
    MOVEMENT_STATUS_FUZZY_SEARCH_TRV("DECODE("+ Table.FUZZY_SEARCH_TRAVELLER + ".movement_type,'EXPECTED','E','CANCELLED','C','DENIED','D', 'N') movement_status"),
    MOVEMENT_STATUS_WITH_TBL_ALIAS("DECODE("+ Table.TRVLR + ".movement_type,'EXPECTED','E','CANCELLED','C','DENIED','D', 'N') movement_status"),
    MOVEMENT_STATUS_ORDER_BY("DECODE("+ Table.TRAVELLERS + ".movement_type,'EXPECTED',1, 'CANCELLED',2, 'DENIED',3, 'N', 4)"),
    MOVEMENT_TYPE(" (SELECT movement_type FROM flight_manifests WHERE id = " + Table.TRVLR + ".fltm_id) "),
    NO_OF_CREW_MEMBERS("(SELECT COUNT(*) FROM " + Table.TRAVELLERS.getValue() + " tra WHERE tra.FLTS_FLIGHT_SEG_ID = " + Table.FLIGHT_SEGMENTS.getValue() + ".FLIGHT_SEG_ID AND tra.TRAVELLER_TYPE = 'C' AND tra.MOVEMENT_TYPE = 'EXPECTED') no_of_crew_members "),
    NO_OF_FLIGHTS("COUNT(*) no_of_flights"),
    NO_OF_PAXS("(SELECT COUNT(*) FROM " + Table.TRAVELLERS.getValue() + " tra WHERE tra.FLTS_FLIGHT_SEG_ID = " + Table.FLIGHT_SEGMENTS.getValue() + ".FLIGHT_SEG_ID AND tra.TRAVELLER_TYPE IN ('P', 'U') AND tra.MOVEMENT_TYPE = 'EXPECTED') no_of_paxs "),
    NOT_IN_LATEST_API_FLAG("MIN(" + Table.TRAVELLERS.getValue() + ".not_in_latest_api_flag) not_in_latest_api_flag"),
    NVL_SCHEDULES_DEPDATE_SYSDATE("NVL(" + Table.SCHEDULES_MASTER.getValue() + "." + Field.DEP_DATE + ", SYSDATE)"),
    OUTER_ID("id(+)"),
    PRE_SCAN_ID(Table.SCHEDULES_TRANSACTIONS.getValue() + "." + Field.ID + " pre_scan_id "),
    RES_BIRTH_DATE_FORMATTED(" DECODE(SUBSTR(r_birth_date, 7, 2), '00', NULL, SUBSTR(r_birth_date, 7, 2)) ||'-'||DECODE(SUBSTR(r_birth_date, 5, 2), '00', NULL, SUBSTR(r_birth_date, 5, 2))||'-'||SUBSTR(r_birth_date, 1, 4) "),
    READY_COUNT(" (SELECT COUNT(1) FROM " + Table.IMPORT_TARGETS +" it WHERE it.WATLIF_ID = wlif.id and status = 'R') ready_count "),
    RECURRENCE_DEP_DATE("( CASE "+Table.SCHEDULES_RECURRENCE_MASTER.getValue()+".DEP_DATE_TYPE WHEN 'S' THEN TO_DATE(SYSDATE) WHEN 'P' THEN TO_DATE(SYSDATE-1) ELSE TO_DATE(SYSDATE+1) END ) DEP_DATE"),
    RECURRENCE_DRVD_SCHEDULED_DATETIME("(" + Table.SCHEDULES_MASTER.getValue() + ".DEP_DATE - (" + Table.SCHEDULES_TIME_INTERVAL_MASTER.getValue() + ".TIME_INTERVAL/24))"),
    RECURRENCE_SCHEDULED_DATETIME(" TO_DATE((TO_CHAR(SYSDATE,'DD-MM-YYYY')||TO_CHAR("+Table.SCHEDULES_RECURRENCE_MASTER.getValue()+".START_DATETIME,' HH24:MI')),'DD-MM-YYYY  HH24:MI') SCHEDULED_DATETIME "),
    RECURRENCE_START_DATETIME(" (TO_CHAR("+Table.SCHEDULES_RECURRENCE_MASTER.getValue()+".START_DATETIME, 'HH24') * 3600 ) + (TO_CHAR("+Table.SCHEDULES_RECURRENCE_MASTER.getValue() + ".START_DATETIME,'MI') * 60 ) + (to_char("
    + Table.SCHEDULES_RECURRENCE_MASTER.getValue() + ".START_DATETIME, 'SS'))"),
    REFERRAL_STATUS_ORDER(" DECODE(" + Table.RFRL +".STATUS, 'UNQ', 1, 'NEW', 2, 'OPEN', 3, 'CLOSED', 4,  5) "),
    RES_TRAVEL_DOC_EXP_FORM(" DECODE(SUBSTR(r_travel_doc_expiry_date, 7, 2), '00', NULL, SUBSTR(r_travel_doc_expiry_date, 7, 2)) ||'-'||DECODE(SUBSTR(r_travel_doc_expiry_date, 5, 2), '00', NULL, SUBSTR(r_travel_doc_expiry_date, 5, 2))||'-'||SUBSTR(r_travel_doc_expiry_date, 1, 4) "),
    SCHEDULE_DATE_TIME("NVL(" + Table.SCHEDULES_MASTER.getValue() + "." + Field.SCHEDULED_DATETIME + ", "
            +  "TO_DATE((TO_CHAR(SYSDATE,'DD-MM-YYYY')||TO_CHAR("+Table.SCHEDULES_RECURRENCE_MASTER.getValue()+".START_DATETIME,' HH24:MI')),'DD-MM-YYYY  HH24:MI')) " + Field.SCHEDULED_DATETIME),
    SCHEDULE_DEP_DATETIME(" NVL(" + Table.SCHEDULES_MASTER.getValue() + ".dep_date,"
            + Table.SCHEDULES_TRANSACTIONS.getValue() + ".dep_date) "),
    SCHEDULE_SCAN_STATUS("NVL(" + Table.SCHEDULES_TRANSACTIONS.getValue() + "." + Field.SCAN_STATUS + ", decode("
            + Table.SCHEDULES_MASTER.getValue() + "." + Field.CANCELLED_IND + ", 'Y', 'C', 'N'))"),
    SCHEDULED_FLIGHT_NUMBER(Table.SCHEDULES_MASTER.getValue() + ".flight_code || " + Table.SCHEDULES_MASTER.getValue() + ".flight_no flight_no"),
    SCHEDULED_TIME_RANGE(" (to_char(" + Table.SCHEDULES_TRANSACTIONS + ".scan_datetime, 'hh24') * 3600 ) + (to_char(" + Table.SCHEDULES_TRANSACTIONS + ".scan_datetime,'mi') * 60 ) "),
    SERVICE(Table.CARRIER_ROUTES.getValue() + ".SERVICE_CARRIER_CODE || " + Table.CARRIER_ROUTES.getValue() + ".SERVICE_NUMBER service"),
    SOURCE("'API' source"),
    SUMMARY_MESSAGE_CODE(" DECODE( NVL("+Table.AUDIT_APP_VIEW +".SUMMARY_MESSAGE_CODE,0),0,'-',"+Table.AUDIT_APP_VIEW +".SUMMARY_MESSAGE_CODE)"),
    TARGET_STATUS_ACTIVE(" 'A' as TARGET_STATUS"),
    TARGET_STATUS(Table.IMPORT_TARGETS.getValue() + "." + Field.STATUS + " as TARGET_STATUS"),
    TIME_INTERVAL("(SELECT LISTAGG(" + Table.SCHEDULES_TIME_INTERVAL_MASTER.getValue() + "." + Field.TIME_INTERVAL
            + ", ',') WITHIN GROUP (ORDER BY " + Table.SCHEDULES_TIME_INTERVAL_MASTER.getValue() + "." + Field.TIME_INTERVAL
            + " DESC) FROM " + Table.SCHEDULES_TIME_INTERVAL_MASTER.getValue()
            + " WHERE " + Table.SCHEDULES_TIME_INTERVAL_MASTER.getValue() + "."  + Field.SCRM_ID + " = "
            + Table.SCHEDULES_RECURRENCE_MASTER.getValue() + "." + Field.ID + ")"),
    TOTAL_REFERRAL_HITS(" ( SELECT count(*) FROM referral_hits WHERE rfrl.id = ref_id )"),
    TRAVEL_DOC_EXP_FORM(" DECODE(SUBSTR(travel_doc_expiry_date, 7, 2), '00', NULL, SUBSTR(travel_doc_expiry_date, 7, 2)) ||'-'||DECODE(SUBSTR(travel_doc_expiry_date, 5, 2), '00', NULL, SUBSTR(travel_doc_expiry_date, 5, 2))||'-'||SUBSTR(travel_doc_expiry_date, 1, 4) "),
    TRAVEL_TYPE("(CASE FLIGHT_MANIFESTS.type_of_travel WHEN 'NORMAL' THEN 'N' WHEN 'TRANSIT' THEN 'T' WHEN 'TRANSFER' THEN CASE WHEN TRAVELLERS.CONNECTING_FLIGHT_TRA_ID IS NULL THEN 'U' ELSE 'X' END END) travel_type "),
    TRAVEL_TYPE_TRV("(CASE TRAVELLERS.type_of_travel WHEN 'NORMAL' THEN 'N' WHEN 'TRANSIT' THEN 'T' WHEN 'TRANSFER' THEN CASE WHEN TRAVELLERS.CONNECTING_FLIGHT_TRA_ID IS NULL THEN 'U' ELSE 'X' END END) travel_type "),
    TRAVEL_TYPE_TRVLR("(CASE TRVLR.type_of_travel WHEN 'NORMAL' THEN 'N' WHEN 'TRANSIT' THEN 'T' WHEN 'TRANSFER' THEN CASE WHEN TRVLR.CONNECTING_FLIGHT_TRA_ID IS NULL THEN 'U' ELSE 'X' END END) "),
    TRAVEL_TYPE_FUZZY_SEARCH_TRV("(CASE FUZZY_SEARCH_TRAVELLER.type_of_travel WHEN 'NORMAL' THEN 'N' WHEN 'TRANSIT' THEN 'T' WHEN 'TRANSFER' THEN CASE WHEN FUZZY_SEARCH_TRAVELLER.CONNECTING_FLIGHT_TRA_ID IS NULL THEN 'U' ELSE 'X' END END) travel_type "),  
    TRAVEL_TYPE_ORDER("DECODE(travel_type, 'N', 1, 'T', 2, 'X', 3, 'U', 4, 0) "), // uses result from TRAVEL_TYPE
    TYPE_OF_TRAVEL(" (SELECT type_of_travel FROM flight_manifests  WHERE id = " + Table.TRVLR +  ".fltm_id) "),
    VALUE_ZERO(" 0 "),
    WATCH_LIST_DESCRIPTION(Table.WATCH_LISTS + ".description || " + "decode(" + Table.WATCH_LISTS + ".enabled_ind" + ",'Y','',' #') " + Field.WATL_DESC),
    WATCH_LIST_LANG_DESCRIPTION(Table.WATCH_LISTS + ".description_lang || " + "decode(" + Table.WATCH_LISTS + ".enabled_ind" + ",'Y','',' #') " + Field.WATL_DESC),
    DEPARTURE_COUNTRY_NAME_SHORT_EN(" (SELECT COUNTRY_NAME_SHORT_EN || ' (' || CODE || ')' from country WHERE code=dep_cntry_code)"),
    DEPARTURE_COUNTRY_NAME_SHORT_AR(" (SELECT COUNTRY_NAME_SHORT_AR || ' (' || CODE || ')' from country WHERE code=dep_cntry_code)"),
    ARRIVAL_COUNTRY_NAME_SHORT_EN(" (SELECT COUNTRY_NAME_SHORT_EN || ' (' || CODE || ')' from country WHERE code=arr_cntry_code)"),
    ARRIVAL_COUNTRY_NAME_SHORT_AR(" (SELECT COUNTRY_NAME_SHORT_AR || ' (' || CODE || ')' from country WHERE code=arr_cntry_code)"),
    API_STATUS(" (SELECT ENABLED_IND FROM ROUTE_DATA_SOURCES WHERE CARR_ID ="+ Table.CARRIER_ROUTES+".id AND DATS_ID IN (SELECT ID FROM DATA_SOURCES WHERE DATA_SOURCE_NAME ='API'))"),
    PNR_STATUS(" (SELECT ENABLED_IND FROM ROUTE_DATA_SOURCES WHERE CARR_ID ="+ Table.CARRIER_ROUTES+".id AND DATS_ID IN (SELECT ID FROM DATA_SOURCES WHERE DATA_SOURCE_NAME ='PNR'))"),
    DCS_STATUS(" (SELECT ENABLED_IND FROM ROUTE_DATA_SOURCES WHERE CARR_ID ="+ Table.CARRIER_ROUTES+".id AND DATS_ID IN (SELECT ID FROM DATA_SOURCES WHERE DATA_SOURCE_NAME ='DCS'))"),
    API_RISK_ASSESSMENT_STATUS(" (SELECT DESTINATION_MODULE FROM ROUTE_DATA_SOURCES WHERE CARR_ID ="+ Table.CARRIER_ROUTES+".id AND DATS_ID IN (SELECT ID FROM DATA_SOURCES WHERE DATA_SOURCE_NAME ='API'))"),
    PNR_RISK_ASSESSMENT_STATUS(" (SELECT DESTINATION_MODULE FROM ROUTE_DATA_SOURCES WHERE CARR_ID ="+ Table.CARRIER_ROUTES+".id AND DATS_ID IN (SELECT ID FROM DATA_SOURCES WHERE DATA_SOURCE_NAME ='PNR'))"),
    DCS_RISK_ASSESSMENT_STATUS(" (SELECT DESTINATION_MODULE FROM ROUTE_DATA_SOURCES WHERE CARR_ID ="+ Table.CARRIER_ROUTES+".id AND DATS_ID IN (SELECT ID FROM DATA_SOURCES WHERE DATA_SOURCE_NAME ='DCS'))"),
    DATA_SOURCE_ROWS("(SELECT COUNT(1) FROM ROUTE_DATA_SOURCES where CARR_ID="+ Table.CARRIER_ROUTES+".id)"),
    FUZZY_TRAVELLER_SEARCH_SCORE("SCORE(1)")
    ;
    private String value;

    private AdvancedField(final String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
