/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query;

public enum Table {

    ACTION_CODES,
    APPLICATIONS,
    AUDIT_APP_VIEW,
    V_AUDIT_LOGS,
    V_AUDIT_LOG_PARAMETERS,
    CARRIER_PROCESSING_RULES,
    CARRIER_ROUTES,
    CARRIER_TYPES,
    CLEARED_DOCUMENTS,
    COUNTRY,
    DATA_SOURCES,
    ETA_USER,
    EVENT_PARAMETERS,
    EVENTS,
    EXPECTED_MOVEMENT,
    EXPECTED_MOVEMENT_VIEW,
    FLIGHT_CHECKINS,
    FLIGHT_CODE_SHARES,
    FLIGHT_MANIFESTS,
    FLIGHT_SEGMENTS,
    FLTM,                  // alias
    FLTS,                  // alias
    IMPORT_TARGETS,
    MATCHED_RFRL,          // alias
    MRFRL,                 // alias for MATCHED_RFRL
    OVERRIDE_AUTHORITY_VIEW,
    PARAMETERS,
    REASON_CODES,
    REFERRAL_HITS,
    REFERRAL_LOGS,
    REFERRALS,
    RFRL,                  // alias for REFERRALS
    RFRL_HIT,                  // alias for REFERRAL_HITS
    ROUTE_DATA_SOURCES,
    SCHEDULES_MASTER,
    SCHEDULES_RECURRENCE_MASTER,
    SCHEDULES_TIME_INTERVAL_MASTER,
    SCHEDULES_TRANSACTIONS,
    TRANSACTION_DETAIL_LOG,
    TRANSACTION_ORIGIN,
    TRAVELLERS,
    TRAVELLER_ALERTS,
    TRVLR,                 // alias for TRAVELLERS
    TARGET_WATCH_LISTS,
    V_TRAVELLER_FLIGHTS,
    WATCH_LISTS,
    WATCH_LIST_REQUESTS,
    WATCH_LIST_IMPORT_FILES,
    WLIF, // alias for WATCH_LIST_IMPORT_FILES


    // Aliased
    ALIAS_FLIGHT_ROUTES1("FLIGHT_ROUTES fltr1"),
    ALIAS_FLIGHT_ROUTES2("FLIGHT_ROUTES fltr2"),
    ALIAS_WATCH_LIST_IMPORT_FILES("WATCH_LIST_IMPORT_FILES wlif"),
    FUZZY_SEARCH_TRAVELLER,
    LOCATIONS;

    private String value;

    private Table() {
        this.value = name();
    }

    private Table(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

