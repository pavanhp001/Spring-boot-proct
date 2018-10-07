/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;


import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;


/**
 *    Override Type arg object
 */
public class OverrideTypeQueryArg extends SimpleInQueryArg {



    /**
     * Search for Travel Types
     * @param travellerTable
     * @param travelTypeField
     * @param manifestTable
     * @param noOverride
     * @param values
     */
    public OverrideTypeQueryArg
        ( final Table table
        , final Field field
        , final String noOverride,
          final String... values) {


        if (hasNoNullValues(values, noOverride)) {
            return;
        }

        addToWhereClause(" ( ");

        if (noOverride != null) {
            addToWhereClause(table.name() + "." + field.name() + " IS NULL OR ");
        }

        setInQuery(field, table, (Object[]) values);

        addToWhereClause(" ) ");
    }


}
