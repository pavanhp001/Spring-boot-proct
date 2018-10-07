/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;


import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import abc.xyz.pts.bcs.common.dao.support.query.AdvancedField;
import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.utils.DateStringUtils;

public class DateRangeQueryArg extends QueryArg {



    public DateRangeQueryArg() {
        super();
    }

    /**
     * Constructor
     * @param fromDate From date range (or = if no to date)
     * @param toDate To date range
     * @param field [Field.DEP_DATE|Field.ARR_DATE]
     * @param table Table name of field
     * @param ignoreTrunc
     */
    public DateRangeQueryArg(final String from, final String to,
            final Field field, final Table table, final boolean ignoreTrunc) {
        setQueryArgs(from, to, field, table, ignoreTrunc);
    }

    /**
     * Constructor
     * @param fromDate From date range (or = if no to date)
     * @param toDate To date range
     * @param field [Field.DEP_DATE|Field.ARR_DATE]
     * @param table Table name of field
     */
    public DateRangeQueryArg(final String from, final String to,
            final Field field, final Table table) {
        setQueryArgs(from, to, field, table, false);

    }


    /**
     * Constructor to handle a valid until Calendar object
     *
     * @param expiresOnOrBefore
     *             The expiry date supplied in the GUI
     * @param field
     *             The DB field from where the info should be read
     * @param table
     *             The DB table from where the required info should be read
     */
    public DateRangeQueryArg(final Calendar expiresOnOrBefore,
            final Field field, final Table table) {
        this(expiresOnOrBefore, field, table, false);
    }

    /**
     * Constructor to handle a valid until Calendar object
     *
     * @param expiresOnOrBefore
     *             The expiry date supplied in the GUI
     * @param field
     *             The DB field from where the info should be read
     * @param table
     *             The DB table from where the required info should be read
     */
    public DateRangeQueryArg(final Calendar expiresOnOrBefore,
            final Field field, final Table table, final boolean ignoreTrunc) {
        if(expiresOnOrBefore != null) {
            if(ignoreTrunc){
                addToWhereClause(table.getValue() + "." + field + " >= ? ");
            }else{
                addToWhereClause("trunc(" + table.getValue() + "." + field + ") >= ? ");
            }
            addSearchCriteria(expiresOnOrBefore);
        }
    }

    /**
     * Constructor to handle a valid until Calendar object
     *
     * @param expiresOnOrBefore
     *             The expiry date supplied in the GUI
     * @param field
     *             The DB field from where the info should be read
     * @param table
     *             The DB table from where the required info should be read
     */
    public DateRangeQueryArg(final Calendar expiresOnOrBefore, final AdvancedField field, final boolean ignoreTrunc) {
        if(expiresOnOrBefore != null) {
            if(ignoreTrunc){
                addToWhereClause(field.getValue() + " >= ? ");
            }else{
                addToWhereClause("trunc(" + field.getValue() + ") >= ? ");
            }
            addSearchCriteria(expiresOnOrBefore);
        }
    }

    /**
     * Constructor to handle to and from dates
     *
     * @param from
     *             The start date of the search. If null then all dates prior to "to" date
     * @param to
     *             The end date of the search.
     * @param field
     *             The DB field from where the info should be read
     * @param table
     *             The DB table from where the required info should be read
     */
    public DateRangeQueryArg(final Date from, final Date to, final Field field,
            final Table table, final boolean ignoreTrunc) {
        setQueryArgs(DateStringUtils.getStringFromDate(from), DateStringUtils.getStringFromDate(to), field, table, ignoreTrunc);
    }

    /**
     * This method check date values and create data range query
     * @param from
     *             The start date of the search. If null then all dates prior to "to" date
     * @param to
     *             The end date of the search.
     * @param field
     *             The DB field from where the info should be read
     * @param table
     *             The DB table from where the required info should be read
     */
    protected void setQueryArgs(final String from, final String to,
            final Field field, final Table table, final boolean ignoreTrunc) {
        if (StringUtils.isNotEmpty(from)) {
            if(ignoreTrunc){
                addToWhereClause(table.getValue() + "." + field);
            }else{
                addToWhereClause("trunc(" + table.getValue() + "." + field +")"); // strip time
            }
            if (StringUtils.isNotEmpty(to)) {
                addToWhereClause(" BETWEEN TO_DATE('"+ from +"', 'dd-MM-yyyy') AND TO_DATE('"+ to +"', 'dd-MM-yyyy') ");

            } else {
                addToWhereClause(" = TO_DATE('"+ from +"', 'dd-MM-yyyy') ");
            }
        } else if (StringUtils.isNotEmpty(to)) {
            if(ignoreTrunc){
                addToWhereClause(table.getValue() + "." + field + " <= TO_DATE('"+ to +"', 'dd-MM-yyyy') ");
            }else{
                addToWhereClause("trunc(" + table.getValue() + "." + field + ") <= TO_DATE('"+ to +"', 'dd-MM-yyyy') ");
            }
        }
    }


    /**
     * To handle a date or a range of date against 2 columns in db.
     *
     * @param dtFrom
     * @param dtTo
     * @param fieldFrom
     * @param fieldTo
     * @param table
     */
    public DateRangeQueryArg(final Date dtFrom, final Date dtTo,
            final Field fieldFrom, final Field fieldTo, final Table table) {
        if (dtFrom == null) {
            return;
        }

        addToWhereClause(" ( ");

        // fromDate is within the range in db
        addToWhereClause(" ? BETWEEN " + table.getValue() + "." + fieldFrom
                             + " AND NVL(" + table.getValue() + "." + fieldTo + ", " + table.getValue() + "." + fieldFrom + ") ");
        addSearchCriteria(dtFrom);

        if (dtTo != null) {
            addToWhereClause(" OR ");

            // toDate is within the range in db
            addToWhereClause(" ? BETWEEN " + table.getValue() + "." + fieldFrom
                    + " AND NVL(" + table.getValue() + "." + fieldTo + ", " + table.getValue() + "." + fieldFrom + ") ");
            addSearchCriteria(dtTo);

            addToWhereClause(" OR ");

            //
            addToWhereClause(table.getValue() + "." + fieldFrom + " BETWEEN " + " ? AND ? ");
            addSearchCriteria(dtFrom);
            addSearchCriteria(dtTo);
        }

        addToWhereClause(" ) ");
    }

    /**
     * Accept Calendar object.
     *
     * @param dtFrom
     * @param dtTo
     * @param fieldFrom
     * @param fieldTo
     * @param table
     */
    public DateRangeQueryArg(final Calendar dtFrom, final Calendar dtTo,
            final Field fieldFrom, final Field fieldTo, final Table table) {
        this((dtFrom == null ? null : dtFrom.getTime())
                , (dtTo == null ? null : dtTo.getTime())
                , fieldFrom
                , fieldTo
                , table);
    }

    /**
     * Accept Calendar object.
     *
     * @param dtFrom
     * @param dtTo
     * @param fieldFrom
     * @param fieldTo
     * @param table
     * @param ignoreTrunc
     */
    public DateRangeQueryArg(final Calendar dtFrom, final Calendar dtTo,
            final Field fieldFrom, final Field fieldTo, final Table table, final boolean ignoreTrunc) {
        this((dtFrom == null ? null : dtFrom.getTime())
                , (dtTo == null ? null : dtTo.getTime())
                , fieldFrom
                , fieldTo
                , table);
    }

    /**
     * Accept Calendar object.
     *
     * @param dtFrom
     * @param dtTo
     * @param fieldFrom
     * @param fieldTo
     * @param table
     */
    public DateRangeQueryArg(final Calendar dtFrom, final Calendar dtTo,
            final Field field, final Table table) {
        this((dtFrom == null ? null : dtFrom.getTime())
                , (dtTo == null ? null : dtTo.getTime())
                , field
                , table, false);
    }

    /**
     * Accept Calendar object.
     *
     * @param dtFrom
     * @param dtTo
     * @param fieldFrom
     * @param fieldTo
     * @param table
     * @param ignoreTrunc
     */
    public DateRangeQueryArg(final Calendar dtFrom, final Calendar dtTo,
            final Field field, final Table table, final boolean ignoreTrunc) {
        this((dtFrom == null ? null : dtFrom.getTime())
                , (dtTo == null ? null : dtTo.getTime())
                , field
                , table, ignoreTrunc);
    }
}
