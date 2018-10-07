/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query.arg;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;

import abc.xyz.pts.bcs.common.dao.support.query.Field;
import abc.xyz.pts.bcs.common.dao.support.query.Table;


/**
 *    Value list arg object for IN WHERE clauses (e.g. table1.field1 IN (?) )
 */
public class UnboundStringInQueryArg extends QueryArg {


    public UnboundStringInQueryArg() {

    }

    public UnboundStringInQueryArg(final Field searchField, final Table table, final List<String> values) {

        if(!CollectionUtils.isEmpty(values)){
            setInQuery(searchField, table, values);
        }

    }

    public void setInQuery(final Field searchField, final Table table, final List<String> values) {

        final StringBuilder inList = new StringBuilder();

        for (final String value : values) {
            if (value != null && !"".equals(value)) {
                if (inList.length() > 0) {
                    inList.append(",");
                }
                inList.append('\'').append(StringEscapeUtils.escapeSql(value)).append('\'');
            }
        }
        if (inList.length() == 0) {
            // return no results
            addToWhereClause(" 1=2 ");
        } else {
            addToWhereClause(table.getValue() + "." + searchField + " IN (" + inList + ")");
        }
    }


 

}
