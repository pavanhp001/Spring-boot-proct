/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support.query;

import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

/**
 *    Implement query for counts
 */
public class CountBuilder extends QueryBuilder {

    /**
     *
     * @param tableCommand Error handle
     */
    public CountBuilder(final TableActionCommand tableCommand) {
        super(tableCommand);
        addSelect(AdvancedField.COUNT_RECORDS, Field.COUNT_RECORDS);
    }
    
    /**
     * 
     * @param tableCommand
     * @param primaryKey
     * @param table
     */
    public CountBuilder(final TableActionCommand tableCommand,
            final Field primaryKey, final Table table) {
        super(tableCommand,primaryKey,table);
    }

}
