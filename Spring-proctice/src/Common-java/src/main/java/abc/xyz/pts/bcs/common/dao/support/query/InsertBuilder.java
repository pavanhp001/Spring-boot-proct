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
 * Class to generate the SQL needed to add a target to the Warnings
 * Index
 *
 * @author gerard.mchale
 */
public class InsertBuilder extends AbstractBuilder {

    private final StringBuilder insertColumnList = new StringBuilder(128);
    private final StringBuilder insertValueList = new StringBuilder(128);

    /**
     * Constructor
     *
     * @param tableCommand
     */
    public InsertBuilder(final TableActionCommand tableCommand) {
        this(tableCommand, null);
    }

    /**
     * Constructor
     *
     * @param tableCommand
     * @param autoId Add auto ID
     */
    public InsertBuilder(final TableActionCommand tableCommand,
            final String sequence) {
        super();
        this.tableCommand = tableCommand;
        if (sequence != null) {
            insertColumnList.append("ID");
            insertValueList.append(sequence + ".nextval");
        }
    }
    /**
     * Add Create item to query, prepending comma if not first item to be inserted
     * Add a corresponding "?" to the values list
     * Add the corresponding value to the list of values to be inserted
     *
     * @param insert
     *             Column name in the database table
     * @param value
     *             Value to be inserted into the database table
     */
    public void addInsert(final Field insert, final Object value) {
        checkAddSeparator(insertColumnList, ",");
        insertColumnList.append(insert);
        checkAddSeparator(insertValueList, ",");
        insertValueList.append("?");
        criteriaValues.add(value);
    }

    /**
     * Generate the actual SQL statement and return it to the calling
     * method
     *
     * @return the SQL statement
     */
    @Override
    public String getSql() {
        final StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO " + tableList.toString());
        sql.append(" (" + insertColumnList.toString() + " )");
        sql.append(" VALUES (" + insertValueList.toString() + ")");
        return sql.toString();
    }
}
