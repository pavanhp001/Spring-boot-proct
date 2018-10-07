/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2010
 * All rights reserved.
 */

package abc.xyz.pts.bcs.common.web.command;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import abc.xyz.pts.bcs.common.util.SortColumnType;


public class TableActionCommand implements Serializable {

    private static final String EMPTY_STRING = "";
    private static final long serialVersionUID = 7826064804181539040L;
    private static final Logger LOG = Logger.getLogger(TableActionCommand.class);

    public static final String ASC = "ASC";
    public static final String DESC = "DESC";
    public static final int ERROR_TOO_MANY_RESULTS = 1;
    public static final int NO_ERROR = 0;
    public static final int NO_MAX_ROW_COUNT = 0;
    public static final int RESULT_COUNT_NOT_SET = -1;
    public static final int DEFAULT_MAX_ROW_COUNT = 200000;

    private static final String DISABLED = "disabled";
    private static final int DEFAULT_PAGE_SIZE = 50;
    private static final int DEFAULT_PAGE_NUMNER = 1;
    public static final int MAX_REPORT_PRINT_SIZE = 2000;
    public static final int DEFAULT_CONFIGURED_ROW_COUNT = 2000;

    /*Fake Sort By Column Name to avoid exposing Actual Database Table Column Name to UI
     * for Security reasons
     */
    private String sortBy;

    //Actual Database Table Sort By Column Name
    private List<String> sortByColumns;
    private String previousSortBy;
    private boolean descending;
    private int pageSize;
    private int pageNumber = DEFAULT_PAGE_NUMNER;
    private boolean pagesAvailable;
    public long totalResults = RESULT_COUNT_NOT_SET;
    private long errorNumber = NO_ERROR;
    private boolean rowCountEstimated;
    private int maxRowCount = NO_MAX_ROW_COUNT;
    private String selectedId;
    
    // to store the configured maximum result count
    private int configuredRowCount = DEFAULT_CONFIGURED_ROW_COUNT;

    // Show advance search options
    protected boolean showAdvanced;

    //This flag for Printing Reports for All Matches found
    private boolean printAll;


    public boolean getShowAdvanced() {
        return showAdvanced;
    }

    public void setShowAdvanced(final boolean showAdvanced) {
        this.showAdvanced = showAdvanced;
    }

    public boolean isRowCountEstimated() {
        return rowCountEstimated;
    }

    public boolean getRowCountEstimated() {
        return rowCountEstimated;
    }

    public int getMaxRowCount() {
        return maxRowCount;
    }

    public void setMaxRowCount(final int maxRowCount) {
        this.maxRowCount = maxRowCount;
    }

    public void setRowCountEstimated(final boolean rowCountEstimated) {
        this.rowCountEstimated = rowCountEstimated;
    }

    public void setDefaultTableStatus(final String sortBy,
            final String ascDesc) {
        setSortBy(sortBy);
        setAscDesc(ascDesc);
        setDefaultTableStatus();
    }

    public void setDefaultTableStatus(){
        setTotalResults(RESULT_COUNT_NOT_SET);
        setErrorNumber(NO_ERROR);
        setPageNumber(DEFAULT_PAGE_NUMNER);
        setRowCountEstimated(false);
        setPrintAll(false);
    }

    public long getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(final long errorNumber) {
        this.errorNumber = errorNumber;
    }

    /**
     * @return the sortBy
     */
    public String getSortBy() {
        return sortBy;
    }

    /**
     * @param sortBy
     *            the sortBy to set
     */
    public void setSortBy(final String sortBy) {
        this.sortBy = sortBy;
        this.sortByColumns = null;
        if (!StringUtils.isBlank(sortBy)) {
            final SortColumnType sct = SortColumnType.getType(sortBy);
            if (sct != null) {
                this.sortByColumns = sct.getSortByColumns();
            } else {
                LOG.warn("unrecognised sort column value '" + sortBy + "'");
            }
        }
    }

    /**
     * @return the sortOrder
     */
    public boolean isDescending() {
        return descending;
    }

    /**
     * @param sortOrder
     *            the sortOrder to set
     */
    public void setDescending(final boolean descending) {
        this.descending = descending;
    }

    public void setAscDesc(final String ascDesc) {
        setDescending(DESC.equalsIgnoreCase(ascDesc) ? true : false);
    }

    public String getSortDirection() {
        return isDescending() ? DESC : ASC;
    }

    public void calculateSortDirection(final TableActionCommand oldTableCommand) {
        if (getSortBy().equals(oldTableCommand.getPreviousSortBy())) {
            setDescending(!oldTableCommand.isDescending()); // TOGGLE
        } else {
            setDescending(false);
        }

        // Reset page number to 1
        setPageNumber(DEFAULT_PAGE_NUMNER);
        setTotalResults(oldTableCommand.getTotalResults());
        setRowCountEstimated(oldTableCommand.isRowCountEstimated());
        setPrintAll(false);
    }

    public int getPageSize() {
        return  pageSize == 0 ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(final int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(final long totalResults) {
        this.totalResults = totalResults;
    }

    public void nextPage() {
        setPrintAll(false);
        pageNumber = pageNumber + 1;
    }

    public void previousPage() {
        setPrintAll(false);
        if(pageNumber > 1) {
            pageNumber = pageNumber - 1;
        }
    }

    public final int getStartIndex() {
        final int lastRecordNumber = isPrintAll() ? 1 : ((pageNumber -1)* getPageSize())+1;
        return lastRecordNumber;
    }

    public final int getEndIndex() {
        final int endRecordNumber = isPrintAll() ? MAX_REPORT_PRINT_SIZE : ((pageNumber -1)* getPageSize()) + getPageSize() + 1;
        return endRecordNumber;
    }

    public final String getPreviousDisabled() {
        if (pageNumber <= 1) {
            return DISABLED;
        }
        return EMPTY_STRING;
    }

    public final String getNextDisabled() {
        if (!isPagesAvailable()) {
            return DISABLED;
        }
        return EMPTY_STRING;
    }

    public final boolean showPrintPopin(){
        return ((getTotalResults() > getPageSize() || isRowCountEstimated()));
    }

    public boolean isPagesAvailable() {
        return pagesAvailable;
    }

    public void setPagesAvailable(final boolean pagesAvailable) {
        this.pagesAvailable = pagesAvailable;
    }

    public String getPreviousSortBy() {
        return previousSortBy;
    }

    public void setPreviousSortBy() {
        this.previousSortBy = sortBy;
    }

    public List<String> getSortByColumns() {
        return sortByColumns;
    }

    public void setSortByColumns(final List<String> sortByColumns) {
        this.sortByColumns = sortByColumns;
    }

    public boolean isPrintAll() {
        return printAll;
    }

    public boolean getPrintAll() {
        return printAll;
    }

    public void setPrintAll(final boolean printAll) {
        this.printAll = printAll;
    }

    public void setMaxResultCountToDefault() {
        this.maxRowCount = DEFAULT_MAX_ROW_COUNT;
    }

    public String getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(final String selectedId) {
        this.selectedId = selectedId;
    }

    public boolean isTotalResultsAvailable() {
        return this.totalResults != RESULT_COUNT_NOT_SET;
    }

    public boolean isMaxRowCountSet() {
        return this.maxRowCount != NO_MAX_ROW_COUNT;
    }

    public int getConfiguredRowCount() {
        return configuredRowCount;
    }

    public void setConfiguredRowCount(final int configuredRowCount) {
        this.configuredRowCount = configuredRowCount;
    }
    
}
