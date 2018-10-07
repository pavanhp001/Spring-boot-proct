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

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.utils.DateStringUtils;

// Like DateRangeQueryArg, but doesn't truncate the date column
public class FullDateRangeQueryArg extends QueryArg {


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
    public FullDateRangeQueryArg(final Calendar expiresOnOrBefore, final Field field, final Table table) {
        this(null, DateStringUtils.getDateFromCalendarOrNull(expiresOnOrBefore), field, table);
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
    public FullDateRangeQueryArg(final Date from, final Date to, final Field field, final Table table) {

        if (from != null) {
            addSearchCriteria(from);
            addToWhereClause(table.getValue() + "." + field);
            if (to != null) {

                addSearchCriteria(to);
                addToWhereClause(" BETWEEN ? AND ? ");

            } else {
                addToWhereClause(" = ? ");
            }
        } else if (to != null) {
            addToWhereClause(table.getValue() + "." + field + " <= ? ");
            addSearchCriteria(to);
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
    public FullDateRangeQueryArg(final Date dtFrom, final Date dtTo, final Field fieldFrom, final Field fieldTo, final Table table)
    {
        if (dtFrom == null) {
            return;
        }

        addToWhereClause(" ( ");

        // fromDate is within the range in db
        addToWhereClause(" ? BETWEEN " + table.getValue() + "." + fieldFrom
                             + " AND NVL(" + table.getValue() + "." + fieldTo + ", " + table.getValue() + "." + fieldFrom + ") ") ;
        addSearchCriteria(dtFrom);

        if (dtTo != null)
        {
            addToWhereClause(" OR ");

            // toDate is within the range in db
            addToWhereClause(" ? BETWEEN " + table.getValue() + "." + fieldFrom
                    + " AND NVL(" + table.getValue() + "." + fieldTo + ", " + table.getValue() + "." + fieldFrom + ") ") ;
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
    public FullDateRangeQueryArg(final Calendar dtFrom, final Calendar dtTo, final Field fieldFrom, final Field fieldTo, final Table table)
    {
        this( (dtFrom == null ? null : dtFrom.getTime())
            , (dtTo == null ? null : dtTo.getTime())
            , fieldFrom
            , fieldTo
            , table
            );
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
    public FullDateRangeQueryArg(final Calendar dtFrom, final Calendar dtTo, final Field field, final Table table)
    {
        this( (dtFrom == null ? null : dtFrom.getTime())
            , (dtTo == null ? null : dtTo.getTime())
            , field
            , table
            );
    }

}
