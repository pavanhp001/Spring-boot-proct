/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dto;

import java.util.List;

/**
 * Wraps data intended to presented as table headers and rows by the UI and retrieved
 * via an AJAX call.
 *
 * @author andy taylor
 */
public class JSONTableData {

    /** Table header descriptions */
    private List<String> tableHeaders;

    /** List of table rows */
    private List<List<String>> tableRows;

    /** Id relating to this table data, if applicable */
    private Long id;

    public List<String> getTableHeaders() {
        return tableHeaders;
    }

    public void setTableHeaders(final List<String> tableHeaders) {
        this.tableHeaders = tableHeaders;
    }

    public List<List<String>> getTableRows() {
        return tableRows;
    }

    public void setTableRows(final List<List<String>> tableRows) {
        this.tableRows = tableRows;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((tableHeaders == null) ? 0 : tableHeaders.hashCode());
        result = prime * result + ((tableRows == null) ? 0 : tableRows.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JSONTableData other = (JSONTableData) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (tableHeaders == null) {
            if (other.tableHeaders != null) {
                return false;
            }
        } else if (!tableHeaders.equals(other.tableHeaders)) {
            return false;
        }
        if (tableRows == null) {
            if (other.tableRows != null) {
                return false;
            }
        } else if (!tableRows.equals(other.tableRows)) {
            return false;
        }
        return true;
    }



}