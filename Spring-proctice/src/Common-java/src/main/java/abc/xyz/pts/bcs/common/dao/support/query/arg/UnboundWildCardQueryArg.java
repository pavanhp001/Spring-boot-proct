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
import org.apache.commons.lang.StringEscapeUtils;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;
import abc.xyz.pts.bcs.common.dao.utils.DateStringUtils;

/**
 * Creates a wild card query arg without a bind variable (?)
 * @author Kasi.Subramaniam
 */
public class UnboundWildCardQueryArg extends QueryArg {


    public UnboundWildCardQueryArg(final String str, final Field field, final Table table) {
       // If bind variables aren't being used, escape the input!

        if (StringUtils.isNotBlank(str)) {

            if (str.contains("*")) {
                String name = StringEscapeUtils.escapeSql(str);
                name = DateStringUtils.replaceWildCards(name);
                addToWhereClause(table.getValue() + "." + field + " LIKE '"+name+"'");
            }else if (str.contains("%")) {
                String name = StringEscapeUtils.escapeSql(str);
                addToWhereClause(table.getValue() + "." + field + " LIKE '"+name+"'");
            } else {
                addToWhereClause(table.getValue() + "." + field + " = ? ");
                addSearchCriteria(str);
            }
        }
    }

}
