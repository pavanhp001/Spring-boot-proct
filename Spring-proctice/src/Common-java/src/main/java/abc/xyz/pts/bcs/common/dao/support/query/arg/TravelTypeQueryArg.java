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

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;


/**
 *    Travel Type arg object
 */
public class TravelTypeQueryArg extends SimpleInQueryArg {


    private static final String TRAVEL_TYPE_TRANSFER = "TRANSFER";

    /**
     * Search for Travel Types
     * @param travellerTable
     * @param travelTypeField
     * @param manifestTable
     * @param transfer
     * @param unmatched
     * @param values
     */
    public TravelTypeQueryArg
        ( final Table travellerTable
        , final Field travelTypeField
        , final Table manifestTable
        , final String transfer
        , final String unmatched,
        final String... values)
    {

        if (hasNoNullValues(values, transfer, unmatched)) {
            return;
        }

        addToWhereClause(" ( ");

        if (StringUtils.isNotBlank(transfer) || StringUtils.isNotBlank(unmatched))
        {

            addToWhereClause(" ( ");
            addToWhereClause(manifestTable.getValue() + "." + travelTypeField + " = '" + TRAVEL_TYPE_TRANSFER + "'");

            if (StringUtils.isBlank(unmatched))
            {
                // return Transfers only
                addToWhereClause(" AND " + travellerTable + "." + Field.CONNECTING_FLIGHT_TRA_ID + " IS NOT NULL ");

            }
            else if (StringUtils.isBlank(transfer))
            {
                // return Unmatched only
                addToWhereClause(" AND " + travellerTable + "." + Field.CONNECTING_FLIGHT_TRA_ID + " IS NULL ");

            }
            addToWhereClause(" ) ");
            addToWhereClause(" OR "); // allow for other travel types

        }

        setInQuery(travelTypeField, manifestTable, (Object[]) values);

        addToWhereClause(" ) ");
    }

    /**
     * Variant that just uses the TRAVELLERS table - once all queries are updated
     * to reflect changes to TRAVELLERS as part of QAT-1327, we can remove the above
     * constructor
     */
    public TravelTypeQueryArg(final Table travellerTable,
            final Field travelTypeField,
            final String transfer,
            final String unmatched,
            final String... values) {

        if (hasNoNullValues(values, transfer, unmatched)) {
            return;
        }

        addToWhereClause(" ( ");

        if (StringUtils.isNotBlank(transfer) || StringUtils.isNotBlank(unmatched))
        {

            addToWhereClause(" ( ");
            addToWhereClause(travellerTable.getValue() + "." + travelTypeField + " = '" + TRAVEL_TYPE_TRANSFER + "'");

            if (StringUtils.isBlank(unmatched))
            {
                // return Transfers only
                addToWhereClause(" AND " + travellerTable + "." + Field.CONNECTING_FLIGHT_TRA_ID + " IS NOT NULL ");

            }
            else if (StringUtils.isBlank(transfer))
            {
                // return Unmatched only
                addToWhereClause(" AND " + travellerTable + "." + Field.CONNECTING_FLIGHT_TRA_ID + " IS NULL ");

            }
            addToWhereClause(" ) ");
            addToWhereClause(" OR "); // allow for other travel types

        }

        setInQuery(travelTypeField, travellerTable, (Object[]) values);

        addToWhereClause(" ) ");
    }
}
