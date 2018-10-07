/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.iir;

/**
 * @author MMotlib-Siddiqui
 */


public enum IIRSearchFieldType {

    // input/output
    ACTION_CODE("actc_code"),
    BIRTH_COUNTRY_CODE("birth_cntry_code"),
    BIRTH_DATE("birth_date"),
    BIRTH_DATE_FROM("birth_date_from"),
    BIRTH_DATE_TO("birth_date_to"),
    BIRTH_PLACE("birth_place"),
    CL_ID("cl_id"),
    CREATE_BY("created_by"),
    CREATED_DATETIME("created_datetime"),
    DOC_NUM("doc_no"),
    DOC_TYPE("doc_type"),
    DOC_TYPE_NO("doc_type_no"),
    FORENAMES("forename"),
    FULL_NAME("full_name"),
    GENDER("gender"),
    ID("id"),
    LAST_NAME("last_name"),
    MODIFIED_DATETIME("modified_datetime"),
    MODIFIED_BY("modified_by"),
    NATIONALITY("nationality"),
    PROTOCOL_NUMBER("protocol_number"),
    REASON_CODE("resc_code"),
    SEVERITY_LEVEL("severity"),
    VALID_UNTIL_DATE("valid_until_date"),
    WATCHLIST_NAME("watl_name"),
    WATCHLIST_ENABLED_IND("enabled_ind"),

    @Deprecated // replaced by ACTION_CODE
    RECOMMENDED_ACTION("recommended_action"),

    // output
    SCORE("score"),
    ;

    private String val;

    private IIRSearchFieldType(final String val) {
        this.val = val;
    }


    @Override
    public String toString() {
        return this.val;
    }

    public String getVal() {
        return val;
    }

    public static IIRSearchFieldType getInstance(final String val)
    {
        if (val == null) {
            return null;
        }

        for (final IIRSearchFieldType t : IIRSearchFieldType.values())
        {
            if (t.getVal().equalsIgnoreCase(val)) {
                return t;
            }
        }

        return null;
    }

}

