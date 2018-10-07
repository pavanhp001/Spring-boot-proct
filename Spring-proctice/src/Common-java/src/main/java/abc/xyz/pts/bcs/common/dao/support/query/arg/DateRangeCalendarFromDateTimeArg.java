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

public class DateRangeCalendarFromDateTimeArg extends DateRangeQueryArg {

    /**
     * Constructor - Deal with Date Range queries on Date/Time fields
     *
     * @param fromDate From date range (or = if no to date)
     * @param toDate To date range
     * @param field Query field
     * @param table Table name of field
     */
    public DateRangeCalendarFromDateTimeArg(final Calendar from, final Calendar to, final Field field, final Table table) {
        Date dateFrom = null;
        if (from != null) {
            dateFrom = from.getTime();
        }
        Date dateTo = null;

        if (to != null) {
            dateTo = to.getTime();
        }
        setQueryArgs( DateStringUtils.getStringFromDate(dateFrom)
                    , DateStringUtils.getStringFromDate(dateTo)
                    , field
                    , table
                    , false);
    }
}
