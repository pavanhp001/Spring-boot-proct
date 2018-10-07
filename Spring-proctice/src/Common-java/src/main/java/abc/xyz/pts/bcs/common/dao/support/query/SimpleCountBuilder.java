/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query;

import abc.xyz.pts.bcs.common.dao.support.query.arg.SimpleQueryArg;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

/**
 * Implement query for simple counts
 */
public class SimpleCountBuilder extends CountBuilder {

    /**
     *
     * @param tableCommand Error handle
     * @param field Value query field
     * @param table Query table
     * @param value Value to query for
     */
    public SimpleCountBuilder(final TableActionCommand tableCommand, final Field field, final Table table, final String value) {

        super(tableCommand);
        addTable(table);
        addWhereClause(new SimpleQueryArg(field, table, value));
    }
}
