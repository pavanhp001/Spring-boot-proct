/* **************************************************************************
 *                              - CONFIDENTIAL                           *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.wi.dao.impl;

/**
 * Constants class for the Warnings Index DAO's.
 *
 * @version $Id: WarningsIndexDaoConstants.java 1323 2009-07-03 11:44:03Z ryattapu $
 */
public final class WarningsIndexDaoConstants {

    private WarningsIndexDaoConstants() {
        // Prevent instantiation
    }

    // Input parameter names
    public static final String TARGET_ID = "P_ID";
    public static final String FORENAME_PARAM = "P_FORENAME";
    public static final String LASTNAME_PARAM = "P_LAST_NAME";
    public static final String DOB_PARAM = "P_BIRTH_DATE";
    public static final String BIRTH_PLACE = "P_BIRTH_PLACE";
    public static final String COUNTRY_OF_BIRTH = "P_COUNTRY_OF_BIRTH";
    public static final String WATCH_LIST_NAME = "P_WATCH_LIST_NAME";
    public static final String REASON_CODE = "P_REASON_CODE";
    public static final String ACTION_CODE = "P_ACTION_CODE";

    public static final String GENDER_PARAM = "P_GENDER";
    public static final String NATIONALITY_PARAM = "P_NATIONALITY";
    public static final String DOC_TYPE_PARAM = "P_DOC_TYPE";
    public static final String DOC_NUMBER_PARAM = "P_DOC_NUMBER";
    public static final String RECOMMENDED_ACTION = "P_RECOMMENDED_ACTION";
    public static final String PROTOCOL_NUMBER = "P_PROTOCOL_NUMBER";
    public static final String NUM_RECORDS = "P_NUM_RECORDS";
    public static final String VALID_UNTIL_DATE = "P_VALID_UNTIL_DATE";
    public static final String SESSION_ID = "P_SESSION_ID";
    public static final String START_RECORD = "P_START_RECORD";
    public static final String UPDATE_VERSION_NO = "P_UPDATE_VERSION_NO";
    public static final String P_UPDATE_COUNT = "P_UPDATE_COUNT";
    public static final String ORDER_BY_COL = "P_ORDER_BY_COL";
    public static final String ASC_DESC = "P_ASC_DESC";
    public static final String P_QUERY_COUNT = "P_QUERY_COUNT";

    // Output parameter names
    public static final String CLEARED_MATCH_REF_CURSOR = "P_CLEARED_WL_RC";
    public static final String TARGET_MATCH_REF_CURSOR = "P_TARGET_WL_RC";
    public static final String P_ALL_MATCHES_WL_RC = "P_ALL_MATCHES_WL_RC";

    // REF CURSORS
    public static final String TARGET_LIST_REF_CURSOR = "P_TARGET_ITEM_RC";
    public static final String CLEARED_LIST_REF_CURSOR = "P_CLEARED_ITEM_RC";

}
