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
import abc.xyz.pts.bcs.common.dao.utils.DateStringUtils;

/**
 * @author Deepesh.Rathore
 *
 */
public class UnboundWildCardReverseIndexQueryArg extends QueryArg {


    /**
     *
     * Constructor that Prefixes a oracle REVERSE keyword to the column name as well as the
     * argument  being used in the Query
     *
     * @param valueArg
     * @param field
     * @param table
     */
    public UnboundWildCardReverseIndexQueryArg(final String valueArg, final Field field, final Table table) {

            String value = null;

            if (StringUtils.isNotBlank(valueArg)) {

                value = escapeApostrophe(valueArg);
                
                if (value.startsWith("*") && value.endsWith("*")) {
                    value = DateStringUtils.replaceWildCards(value);
                    addToWhereClause( table.getValue() + "." + field +  " LIKE ("+ "'"+value+"'"+")");
                }else if (value.startsWith("*")) {
                    value = DateStringUtils.replaceWildCards(value);
                    addToWhereClause("REVERSE("  + table.getValue() + "." + field + ")" + " LIKE REVERSE("+ "'"+value+"'"+")");
                }else if (value.endsWith("*") ){
                    value = DateStringUtils.replaceWildCards(value);
                    addToWhereClause( table.getValue() + "." + field +  " LIKE ("+ "'"+value+"'"+")");
                }
                else
                {
                    addToWhereClause(table.getValue() + "." + field + " = " + "'"+value+"'" );
                }
            }
    }

    private String escapeApostrophe(final String name) {
        return name.replace("'", "''");
    }
}
