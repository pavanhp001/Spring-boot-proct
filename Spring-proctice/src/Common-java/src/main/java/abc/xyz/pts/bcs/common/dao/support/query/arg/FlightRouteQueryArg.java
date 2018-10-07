/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;


import org.apache.commons.lang.StringUtils;

import abc.xyz.pts.bcs.common.dao.support.query.Table;

public class FlightRouteQueryArg extends QueryArg {

    private static final int AIRPORT1 = 0;
    private static final int AIRPORT2 = 1;
    private static final int AIRPORT3 = 2;
    private static final int AIRPORT_LENGTH = 3;


    /**
     * Constructor
     * @param route Flight route
     */
    public FlightRouteQueryArg(final Table table, final String route) {

        if (StringUtils.isNotBlank(route)) {

            final String[] airports = StringUtils.split(route, "-");

            addTable(Table.ALIAS_FLIGHT_ROUTES1);

            if (airports.length == AIRPORT_LENGTH) {
            addToWhereClause(table.getValue() + ".flight_seg_id = fltr1.flts_flight_seg_id ");
            // This is to give Oracle the hint that flight seg id and dep_date are correlated
            addToWhereClause(" AND " + table.getValue() + ".scheduled_dep_datetime = fltr1.dep_datetime ");
            addToWhereClause(" AND " + table.getValue() + ".flight_seg_id = fltr2.flts_flight_seg_id ");
            // This is to give Oracle the hint that flight seg id and dep_date are correlated
            addToWhereClause(" AND " + table.getValue() + ".scheduled_dep_datetime = fltr2.dep_datetime ");
            }
            else
            {
                addToWhereClause(table.getValue() + ".flight_seg_id = fltr1.flts_flight_seg_id ");
                // This is to give Oracle the hint that flight seg id and dep_date are correlated
                addToWhereClause(" AND " + table.getValue() + ".scheduled_dep_datetime = fltr1.dep_datetime ");
            }

            if (airports.length == 1) {

                addSearchCriteria(airports[AIRPORT1]);
                addSearchCriteria(airports[AIRPORT1]);

                addToWhereClause(" AND (fltr1.dep_airport_code = ? OR fltr1.arr_airport_code = ? ) ");

            } else { // 2 or 3

                addSearchCriteria(airports[AIRPORT1]);
                addSearchCriteria(airports[AIRPORT2]);

                addToWhereClause(" AND fltr1.dep_airport_code = ? ");
                addToWhereClause(" AND fltr1.arr_airport_code = ? ");
            }

            if (airports.length == AIRPORT_LENGTH) {

                addSearchCriteria(airports[AIRPORT2]);
                addSearchCriteria(airports[AIRPORT3]);

                addTable(Table.ALIAS_FLIGHT_ROUTES2);

                addToWhereClause(" AND fltr2.dep_airport_code = ? ");
                addToWhereClause(" AND fltr2.arr_airport_code = ? ");

            }
        }
    }
}
