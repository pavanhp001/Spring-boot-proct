/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;

import java.util.Date;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.utils.DateStringUtils;

public class DateRangeFromDateTimeArg extends DateRangeQueryArg {


    /**
     * Constructor - Deal with Date Range queries on Date/Time fields
     *
     * @param fromDate From date range (or = if no to date)
     * @param toDate To date range
     * @param field Query field
     * @param table Table name of field
     */
    public DateRangeFromDateTimeArg(final String from, final String to,  final Field field, final Table table) {

        final Date dateFrom = DateStringUtils.getDateFromString(from);
        Date dateTo = null;

        // If both dates supplied, add 1 day to 'to' date
        // If only 'from' date supplied, set 'to' date as 'from' date + 1 day
        if (dateFrom != null && to == null) {
            dateTo = DateStringUtils.addOneDayToDate(from);
        } else {
            dateTo = DateStringUtils.addOneDayToDate(to);
        }
            setQueryArgs( DateStringUtils.getStringFromDate(dateFrom)
                        , DateStringUtils.getStringFromDate(dateTo)
                        , field, table
                        , true
                        );
        }
}
