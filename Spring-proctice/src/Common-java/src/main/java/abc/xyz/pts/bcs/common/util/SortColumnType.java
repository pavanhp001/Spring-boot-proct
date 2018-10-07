/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.util;

import java.util.Arrays;
import java.util.List;

import abc.xyz.pts.bcs.common.dao.support.query.AdvancedField;
import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.iir.IIRSearchFieldType;
/**
 * User for mapping sort column from screen to IIR/SQL column name
 *
 * @author mmotlib-siddiqui
 *
 */
public enum SortColumnType {
    // *** RENAMING THE TYPE AFFECTS JSP PAGE - OBJECT IMPORTED INTO JSP PAGE
    // ****************************************************************************

    ACTION_CODE("actionCode",                                                   Field.CODE.toString()),
    ACTION_CODE_DESC("actionCodeDesc",      IIRSearchFieldType.ACTION_CODE,     Field.ACTC_CODE_DESC.toString()),
    ACTION_DATE("ACTION_DATE",                                                  Field.ACTION_DATE.toString()),
    ACTION_DATETIME("ACTION_DATETIME",                                          Field.ACTION_DATETIME.toString()),
    ACTIVE_COUNT("activeCount",                                                 AdvancedField.ACTIVE_COUNT.toString()),
    APP_ACTION_CODE("appActionCode",                                            Field.APP_ACTION_CODE.toString()),
    APP_BUSINESS_RULE("appBusinessRuleSort",                                    Field.APP_BUSINESS_RULE.toString()),
    APP_BILLING("appBillingSort", Field.EXPECTED_IATA_PORT_CODE.toString(),     Field.CARRIER_TYPE.toString()),
    APP_DIRECTIVE("appDirectiveSort",                                           Field.APP_DIRECTIVE.toString()),
    APPLN_CODE("APPLN_CODE",                                                    Field.APPLN_CODE.toString()),
    ARR_AIRPORT_CODE("arrSort",                                                 Field.ARR_AIRPORT_CODE.toString()),
    ARR_COUNRTY("arrCountrySort",                                               Field.ARR_CNTRY_CODE.toString()),
    ARR_DATE("arrDtSort",                                                       Field.SCHEDULED_ARR_DATETIME.toString()),
    ARR_LOCATION("arrLocationSort",                                             Field.ARR_LOCATION_CODE.toString()),
    AT_RISK("atRiskSort",                                                       Field.AT_RISK.toString()),
    AUTO_QUALIFY_IND("autoQualifyInd",                                          Field.AUTO_QUALIFY_IND.toString()),
    BIRTH_DATE("birthDateSort",             IIRSearchFieldType.BIRTH_DATE,      AdvancedField.BIRTH_DATE.getValue()),
    BIRTH_DATE_SIMPLE("birthDateSimpleSort",                                    Field.BIRTH_DATE.toString()),
    BOARD_POINT("boardPointSort",                                               Field.BOARD_POINT.toString()),
    CARRIER_TYPE("carrierTypeSort",                                             Field.CART_CODE.toString()),
    //CARRIER_TYPE_AIR("airSort",                                                 Field.CARRIER_TYPE_AIR.toString()),
    //CARRIER_TYPE_GEN("genSort",                                                 Field.CARRIER_TYPE_GENERAL_AVIATION.toString()),
    //CARRIER_TYPE_SEA("seaSort",                                                 Field.CARRIER_TYPE_SEA.toString()),
    //CARRIER_TYPE_BUS("busSort",                                                 Field.CARRIER_TYPE_BUS.toString()),
    CITIZENSHIP_CODE("citizenshipCodeSort",                                     Field.CITIZENSHIP_CODE.toString()),
    CLEARED_DOC_BIRTH_DATE("clearedBirthDateSort",                              Field.BIRTH_DATE.toString()),
    CREATED_DATETIME("createdDateSort",                                         Field.CREATED_DATETIME.toString()),
    CREATED_BY("createdBySort",                                                 Field.CREATED_BY.toString()),
    COUNTRY_OF_BIRTH("birthCountrySort",    IIRSearchFieldType.BIRTH_COUNTRY_CODE,  Field.COUNTRY_OF_BIRTH.toString()),
    CODE("code",                                                                Field.CODE.toString()),
    DAT_STATUS("dalStatusSort",                                                 Field.DAL_STATUS.toString()),
    DATE_OF_BIRTH("dateOfBirthSort",                                            Field.DATE_OF_BIRTH.toString()),
    DEP_AIRPORT_CODE("depSort",                                                 Field.DEP_AIRPORT_CODE.toString()),
    DEP_COUNTRY_("depCountrySort",                                              Field.DEP_CNTRY_CODE.toString()),
    DEP_DATE("depDtSort",                                                       Field.SCHEDULED_DEP_DATETIME.toString()),
    DEP_LOCATION("depLocationSort",                                             Field.DEP_LOCATION_CODE.toString()),
    DESCRIPTION("description",                                                  Field.DESCRIPTION.toString()),
    DESCRIPTION_LANG("descriptionLang",                                         Field.DESCRIPTION_LANG.toString()),
    DOC_NUM("docNumSort",                   IIRSearchFieldType.DOC_NUM,         Field.DOC_NO.toString()),
    DOC_TYPE("docTypeSort",                                                     Field.DOC_TYPE.toString()),
    DOCUMENT_NUMBER("docNumberSort",                                            Field.DOCUMENT_NUMBER.toString()),
    DUPLICATE_COUNT("duplicateCount",                                           AdvancedField.DUPLICATES_COUNT.toString()),
    ENABLED_ID("enabledInd",                                                    Field.ENABLED_IND.toString()),
    EMPLOYEE_NAME("EMPLOYEE_NAME",                                              Field.EMPLOYEE_NAME.toString()),
    ERROR_COUNT("errorCount",                                                   AdvancedField.ERRORS_COUNT.toString()),
    ERROR_REMARKS("errorRemarksSort",                                           Field.ERROR_CODE.toString()),
    EVENT_NAME("EVENT_NAME",                                                    Field.EVENT_NAME.toString()),
    EVENT_SEVERITY("EVENT_SEVERITY",                                            Field.EVENT_SEVERITY.toString()),
    EVENT_TYPE("EVENT_TYPE",                                                    Field.EVENT_TYPE.toString()),
    EVENT_TYPE_CODE("EVENT_TYPE_CODE",                                          Field.EVENT_TYPE_CODE.toString()),
    EXPECTED_DATE("expectedDateSort",                                           Field.EXPECTED_DATE.toString()),
    EXPECTED_DATE_TIME("expectedDateTimeSort",                                  Field.EXPECTED_DATE_TIME.toString()),
    EXPECTED_DIRECTION_CODE("expectedDirectionCodeSort",                        Field.EXPECTED_DIRECTION_CODE.toString()),
    EXPECTED_FLIGHT_CODE("expectedFlightCodeSort",                              Field.EXPECTED_FLIGHT_CODE.toString()),
    EXPECTED_MOVEMENT_TYPE_CODE("expectedMovementTypeCodeSort",                 Field.EXPECTED_MOVEMENT_TYPE_CODE.toString()),
    FAMILY_AND_GIVEN_NAME("familyAndGivenNameSort",                             Field.FAMILY_NAME.toString(), Field.GIVEN_NAMES.toString()),
    FILE_REFERENCE("fileRef",                                                   Field.FILE_REFERENCE_NUMBER.toString()),
    FILE_STATUS("fileStatus",                                                   AdvancedField.FILE_STATUS.toString()),
    FINAL_RESULT_CODE("finalResultCodeSort",                                    Field.FINAL_RESULT_CODE.toString()),
    FIRST_COLUMN("firstColumnSort", "1"),
    FLIGHT_ROUTE("routeSort",                                                   Field.FLIGHT_ROUTE.toString()),
    FLIGHT_SORT("flightSort",                                                   Field.FLIGHT_NUMBER.toString()),
    FULL_NAME("fullNameSort",                                                   Table.TRAVELLERS + "." + Field.LAST_NAME, Table.TRAVELLERS + "." + Field.FORENAME),
    GENDER("genderSort",                    IIRSearchFieldType.GENDER,          Field.GENDER.toString()),
    GOV_AUTHORITY_ID("govAuthorityIdSort",                                      Field.GOV_AUTHORITY_ID.toString()),
    GOV_OFFICER("govOfficerSort",                                               Field.GOV_OFFICER.toString()),
    GOV_OVERRIDE_PERMITTED_FLAG("govOverridePermittedFlagSort",                 Field.GOV_OVERRIDE_PERMITTED_FLAG.toString()),
    HIT_TYPE("hitTypeSort",                                                     Field.HIT_TYPE.toString()),
    ID("id",                                                                    Field.ID.toString()),
    IMPORTED_DATE_TIME("importedDateTime",                                      Field.CREATED_DATETIME.toString()),
    IMPORTED_USER_NAME("importedUserName",                                      Field.CREATED_BY.toString()),
    IMPORTED_TARGET_COUNT("importedTargetCount",                                Field.TARGET_COUNT.toString()),
    LASTNAME("lastNameSort",                                                    Field.LAST_NAME.toString()),
    LAST_MODIFIED_USER("lastModifiedUserSort",                                     Field.MODIFIED_BY.toString()),
    LAST_MODIFIED_DATE("lastModifiedDateSort",                                     Field.MODIFIED_DATETIME.toString()),
    MAL_STATUS("malStatusSort",                                                 Field.MAL_STATUS.toString()),

    // * we don't score data from DB so set the sort to be the LastName (default)
    MATCH_SCORE("matchScoreSort",           IIRSearchFieldType.SCORE,           Field.LAST_NAME.toString()),

    MESSAGE_CODE("messageCodeSort",                                             Field.MESSAGE_CODE.toString()),
    MOVEMENT_STATUS("mmStatusSort",                                             AdvancedField.MOVEMENT_STATUS_ORDER_BY.getValue()),
    NATIONALITY("nationalitySort",          IIRSearchFieldType.NATIONALITY,     Field.NATIONALITY.toString()),
    NATIONALITY_CODE("nationalityCodeSort",                                     Field.NATIONALITY_CODE.toString()),
    NAME("nameSort",                                                            Field.NAME.toString()),
    NO_OF_CREW_MEMBERS("crewSort",                                              Field.NO_OF_CREW_MEMBERS.toString()),
    NO_OF_PAXS("paxSort",                                                       Field.NO_OF_PAXS.toString()),
    NOT_IN_LATEST_API_FLAG("latestApiSort",                                     Field.NOT_IN_LATEST_API_FLAG.toString()),
    NOTIFY_STATUS_FLAG("notifyStatusFlagSort",                                  Field.NOTIFY_STATUS_FLAG.toString()),
    OFF_POINT("offPointSort",                                                   Field.OFF_POINT.toString()),
    OVERRIDE_FLAG("overrideFlagSort",                                           Field.OVERRIDE_FLAG.toString()),
    OVERRIDE_TYPE("overTypeSort",                                               Field.OVERRIDE_TYPE.toString()),
    PAL_STATUS("palStatusSort",                                                 Field.PAL_STATUS.toString()),
    PAX_CREW_INDICATOR_CODE("paxCrewIndicatorCodeSort",                         Field.PAX_CREW_INDICATOR_CODE.toString()),
    PAX_STATUS_FLAG("paxStatusFlagSort",                                        Field.PAX_STATUS_FLAG.toString()),
    PLACE_OF_BIRTH("birthPlaceSort",        IIRSearchFieldType.BIRTH_PLACE,     Field.BIRTH_PLACE.toString()),
    PORT_OF_DESTINATION("portOfDestinationSort",                                Field.PORT_OF_DESTINATION.toString()),
    PORT_OF_ORIGIN("portOfOriginSort",                                          Field.PORT_OF_ORIGIN.toString()),
    PRIORITY_CODE("priorityCodeSort",                                           Field.PRIORITY_CODE.toString()),
    PROTOCOL("protocolSort",                IIRSearchFieldType.PROTOCOL_NUMBER, Field.PROTOCOL_NUMBER.toString()),
    REASON_CODE("reasonCode",                                                   Field.CODE.toString()),
    REASON_CODE_DESC("reasonCodeDesc",      IIRSearchFieldType.REASON_CODE,     Field.RESC_CODE_DESC.toString()),
    READY_COUNT("readyCount",                                                   Field.READY_COUNT.toString()),
    REFERRAL_ARRIVAL_TIME("arrTimeSort",                                        Field.ARR_DATETIME.toString()),
    REFERRAL_CARRIER_CODE_NUM("carrierCodeNumSort",                             Field.OPER_CARRIER_CODE.toString(), Field.OPER_FLIGHT_NO.toString()),
    REFERRAL_DATE_OF_BIRTH("referralDobSort",                                   Field.BIRTH_DATE.toString()),
    REFERRAL_DEPARTURE_TIME("depTimeSort",                                      Field.DEP_DATETIME.toString()),
    REFERRAL_DOC_TYPE_NUM("referralDocTypeNumSort" ,                            Field.PRI_DOC_TYPE.toString(), Field.PRI_DOC_NO.toString()),
    REFERRAL_GENERATED_ON("referralGeneratedSort",                              Field.CREATED_DATETIME.toString()),
    REFERRAL_HITS_TOTAL("referralHitsTotalSort",                                Field.TOTAL_REFERRAL_HITS.toString()),
    REFERRAL_ID("referralId",                                                   Field.REF_ID.toString()),
    REFERRAL_IND("referralSort",                                                Field.REFERRAL_STATUS.toString()),
    RISK_PROGRESS_IND("riskProgressSort",                                       Field.RISK_ASSESSMENT_STATUS.toString()),
    REFERRAL_MVT_TYPE("referralMvtTypeSort",                                    Field.MOVEMENT_TYPE.toString()),
    REFERRAL_PRIORITY("referralPrioritySort",                                   Field.PRIORITY_REFERRAL.toString()),
    REFERRAL_REC_ACTION_CODE("refRecActionCode",                                Field.REFERRAL_REC_ACTION_CODE.toString()),
    REFERRAL_SEVERITY("refSeverity",                                            Field.REFERRAL_SEVERITY.toString()),
    REFERRAL_STATUS("referralStatusSort",                                       AdvancedField.REFERRAL_STATUS_ORDER.toString()),
    REFERRAL_TRAVELLER_TYPE("referralTravellerTypeSort",                        Field.TRAVELLER_TYPE.toString()),
    REFERRAL_TYPE_OF_TRAVEL("referralTypeOfTravelSort",                         Field.TYPE_OF_TRAVEL.toString()),
    RESPONSE_STATUS("RESPONSE_STATUS",                                          Field.RESPONSE_STATUS.toString()),
    RULE_ENABLED("ruleEnabledSort",                                             Field.ENABLED_IND.toString()),
    SCAN_TYPE("scanTypeSort",                                                   Field.SCAN_TYPE.toString()),
    SEVERITY_LEVEL("severitySort",          IIRSearchFieldType.SEVERITY_LEVEL,  Field.SEVERITY_LEVEL.toString()),
    SCHEDULED_DEP_DATETIME("schedDepSort",                                      Field.SCHEDULED_DEP_DATETIME.toString()),
    SCHEDULED_ARR_DATETIME("schedArrSort",                                      Field.SCHEDULED_ARR_DATETIME.toString()),
    SCHEDULED_DEP_DATE("depDateSort",                                           Field.DEP_DATE.toString()),
    SCHEDULED_TYPE_SORT("schTypeSort",                                          Field.SCHEDULED_TYPE.toString()),
    SCHEDULED_SCAN_STATUS_SORT("scanStatusSort",                                AdvancedField.SCHEDULE_SCAN_STATUS.toString()),
    SCHEDULED_SCAN_DATE("scanDtSort",                                           Field.SCAN_DATETIME.toString()),
    SCHEDULED_DATETIME("scheduledDtTmSort",                                     Field.SCHEDULED_DATETIME.toString()),
    SCHEDULED_FLIGHT_SORT("scheduleFlightSort",                                 Field.FLIGHT_CODE.toString(), Field.FLIGHT_NO.toString()),
    SERVICE("serviceSort",                                                        Field.SERVICE.toString()),
    SEX_CODE("sexCodeSort",                                                     Field.SEX_CODE.toString()),
    STATUS("statusSort",                                                        Field.STATUS.toString()),
    SUMMARY_MESSAGE_CODE("summaryMessageCodeSort",                              Field.SUMMARY_MESSAGE_CODE.toString()),
    SURNAME_FORENAME("surnameForenameSort", IIRSearchFieldType.FULL_NAME,       Field.LAST_NAME.toString(), Field.FORENAME.toString()),
    TARGET_STATUS("targetStatus",                                               Field.TARGET_STATUS.toString()),
    TRANSACTION_DATE("transactionDateSort",                                     Field.TRANSACTION_DATE.toString()),
    TRAVEL_DOC_NUMBER("travelDocNumberSort",                                    Field.TRAVEL_DOC_NUMBER.toString()),
    TRAVELLER_ALERT_ID("allertIdSort",                                          Field.TRAVELLER_ALERT_ID.toString()),
    TRAVELLER_TYPE("travellerTypeSort",                                         Field.TRAVELLER_TYPE.toString()),
    TRAVEL_DATETIME("travellerDateSort",                                        Field.TRAVEL_DATETIME.toString()),
    TRAVEL_TYPE_CODE("travelTypeCodeSort",                                      Field.TRAVEL_TYPE_CODE.toString()),
    TRAVEL_TYPE_DECODED("travelTypeDecodedSort",                                AdvancedField.TRAVEL_TYPE_ORDER.getValue()),
    TRAVELLERS_DEP_DATE("travellersDepDtSort",                                  Table.TRAVELLERS.toString()+"."+Field.DEP_DATETIME.toString()),
    TRAVELLERS_ARR_DATE("travellersArrDtSort",                                  Table.TRAVELLERS.toString()+"."+Field.ARR_DATETIME.toString()),
    USERNAME("USERNAME",                                                        Field.USERNAME.toString()),
    VALID_UNTIL_DATE("validUntilSort",      IIRSearchFieldType.VALID_UNTIL_DATE, Field.VALID_UNTIL_DATE.toString()),
    WATCH_LIST_NAME("targetListNameSort",   IIRSearchFieldType.WATCHLIST_NAME, Field.WATL_NAME.toString()),
    ;

    private String jspColName;
    private IIRSearchFieldType iirSearchField;
    private List<String> sortByColumns;

    private SortColumnType(final String jspColName, final String...sortByColumns) {
        this.jspColName = jspColName;
        this.sortByColumns = Arrays.asList(sortByColumns);

    }

    private SortColumnType(final String jspColName,
            final IIRSearchFieldType iirSearchField, final String...sortByColumns) {
        this.jspColName = jspColName;
        this.sortByColumns = Arrays.asList(sortByColumns);
        this.iirSearchField = iirSearchField;
    }

    /**
     * Instance based on the jsp column name value.
     *
     * @param jspColName
     * @return WISortColumnType
     */
    public static SortColumnType getType(final String jspColName) {
        if (jspColName == null) {
            return null;
        }

        for (final SortColumnType t : SortColumnType.values()) {
            if (t.getJspColName().equalsIgnoreCase(jspColName)) {
                return t;
            }
        }

        return null;
    }


    public String getJspColName() {
        return jspColName;
    }

    public List<String> getSortByColumns() {
        return sortByColumns;
    }

    public IIRSearchFieldType getIirSearchField() {
        return iirSearchField;
    }

    public void setSortByColumns(final List<String> sortByColumns) {
        this.sortByColumns = sortByColumns;
    }
}
