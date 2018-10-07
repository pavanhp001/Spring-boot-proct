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
package abc.xyz.pts.bcs.common.dao.utils;

import java.math.BigInteger;

public final class Constants {

    /*
     * TODO Move to a properties file in the future
     */
    public static final String FULL_DATE_FORMAT = "dd-MM-yyyy HH:mm";
    public static final String TIME_STAMP_FORMAT = "dd-MM-yyyy HH:mm:ss";
    public static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";
    public static final String SIMPLE_DATE_FORMAT = "dd-MM-yyyy";
    public static final String ALT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String ALT_TIME_FORMAT = "HH:mm:ss";
    public static final String ORACLE_ERROR = "oracle.error.";
    public static final String DEFAULT_ORACLE_ERROR = "DB Error";
    public static final String USER_PROFILE_KEY = "userProfileKey";
    public static final String ERROR_MESSAGES_KEY = "errorMessages";
    public static final String ORACLE_ERROR_DUPLICATE_ROW = "oracle.error.20000.duplicate";
    public static final String REPORT_DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_FORMAT_dd_mm_yyyy = "dd-mm-yyyy";
    public static final String ORACLE_DATE_FORMAT_dd_mm_yyyy_hh24_mi = "dd-mm-yyyy hh24:mi";

    public static final BigInteger DEFAULT_RECORDS_PER_PAGE = BigInteger.valueOf(100);
    public static final String RECORD_UPDATE_CONFLICT = "record.update.conflict";
    public static final String TIME_23_59 = "23:59";

    public static final String HIT_OUT = "OUT";
    public static final String HIT_IN = "IN";
    public static int LENGTH_OF_RANDOM_STRING =16;
    public static final String CHARSET_UTF_8 = "UTF-8";
    public static final int PERF_GAIN_WITH_PARTITION_DEF_DEP_DATE_ADJUSTMENT = 3;
    public static final String FUZZY_TRAVELLER_SEARCH_COMMENT = "/*+ FIRST_ROWS(50) */";
    private Constants() {
    }
}
