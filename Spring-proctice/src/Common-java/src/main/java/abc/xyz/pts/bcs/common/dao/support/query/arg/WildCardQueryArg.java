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


public class WildCardQueryArg extends QueryArg {

    /**
     * Straight match or LIKE if wild card present
     * @param name
     * @param table
     */
    public WildCardQueryArg(String name, final Field field, final Table table) {

        if (StringUtils.isNotBlank(name)) {

            if (name.contains("*")) {
                name = DateStringUtils.replaceWildCards(name);
                addToWhereClause(table.getValue() + "." + field + " LIKE ? ");
            }else if (name.contains("%")) {
                addToWhereClause(table.getValue() + "." + field + " LIKE ? ");
            } else {
                addToWhereClause(table.getValue() + "." + field + " = ? ");
            }
            addSearchCriteria(name);
        }
    }

}
