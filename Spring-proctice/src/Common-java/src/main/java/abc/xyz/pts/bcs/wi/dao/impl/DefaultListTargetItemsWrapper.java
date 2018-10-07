/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import abc.xyz.pts.bcs.common.enums.SortOrderType;
import abc.xyz.pts.bcs.common.util.SortColumnType;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;
import abc.xyz.pts.bcs.wi.business.TargetMatcher;
import abc.xyz.pts.bcs.wi.business.TargetMatcherResolver;
import abc.xyz.pts.bcs.wi.dao.ListTargetItemsWrapper;
import abc.xyz.pts.bcs.wi.dto.TargetItem;
import abc.xyz.pts.bcs.wi.iir.exception.IIRConnectException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchInvalidDataFormatException;
import abc.xyz.pts.bcs.wi.model.TargetMatchRequest;

public class DefaultListTargetItemsWrapper implements ListTargetItemsWrapper {
    private static final Logger LOG = Logger.getLogger(DefaultListTargetItemsWrapper.class);

    /*
     * Properties
     */
    private final TargetMatcherResolver targetMatcherResolver;

    /** Flag to indicate whether the IIR is enabled or disabled. */
    private final boolean iirEnabledFlag;

    public DefaultListTargetItemsWrapper(final TargetMatcherResolver targetMatcherResolver,
            final boolean iirEnabledFlag) {
        this.targetMatcherResolver = targetMatcherResolver;
        this.iirEnabledFlag = iirEnabledFlag;
    }

    /**
     * Return subset of data.
     *
     * @param tItemList
     * @param tableCommand
     * @return
     */
    public List<TargetItem> iirDataPaginate(final List<TargetItem> tItemList, final TableActionCommand tableCommand) {
        final int pageNumber = tableCommand.getPageNumber() - 1;
        final int pageSize = tableCommand.getPageSize();

        final int startIdx = pageNumber * pageSize;
        final int endIdx = Math.min(startIdx + pageSize, tItemList.size());

        final List<TargetItem> pageItems = tItemList.subList(startIdx, endIdx);

        // If we haven't reached the end of the list, show next/prev buttons
        if (endIdx < tItemList.size()) {
            tableCommand.setPagesAvailable(true);
        } else {
            tableCommand.setPagesAvailable(false);
        }

        tableCommand.setTotalResults(tItemList.size());
        tableCommand.setRowCountEstimated(false);

        return pageItems;
    }

    @Override
    public List<TargetItem> listTargetItemsWrapper(final TargetItem ti, final TableActionCommand tableCommand,
            final Locale locale)
    throws IIRSearchException, IIRConnectException, IIRSearchInvalidDataFormatException {

        return listTargetItemsWrapper(ti, tableCommand, false, locale);
    }

    @Override
    public int getTargetItemsCount(final TargetItem ti, final TableActionCommand tableCommand,
            final Locale locale) throws IIRSearchException, IIRConnectException, IIRSearchInvalidDataFormatException {
        final TargetMatchRequest targetMatchRequest =
                new TargetMatchRequest.TargetMatchRequestBuilder().iirEnabled(iirEnabledFlag).match(false).tableActionCommand(tableCommand).targetItemToBeMatched(ti).locale(locale).build();
        final List<TargetMatcher> targetMatchers = targetMatcherResolver.resolve(targetMatchRequest);

        int rowCount = 0;
        for(final TargetMatcher targetMatcher : targetMatchers) {
            rowCount += targetMatcher.getMatchesCount(targetMatchRequest);
        }

        return rowCount;
    }

    @Override
    public List<TargetItem> listTargetItemsWrapper(final TargetItem ti, final TableActionCommand tableCommand)
    throws IIRSearchException, IIRConnectException, IIRSearchInvalidDataFormatException {

        return listTargetItemsWrapper(ti, tableCommand, false, null);
    }

    /**
     * Search for target items using either the local database or the IIR
     * database when search data is provided.
     * <p>
     * The IIR database is used for the search when a name is provided as one of
     * the search parameters. Otherwise the local database is used for the
     * search.
     *
     * @param isMatch
     *            are we doing a match?
     * @param ti
     *            The DTO containing the search parameters.
     *
     * @return The updated DTO including any results.
     * @throws Exception
     */
    @Override
    public List<TargetItem> listTargetItemsWrapper(final TargetItem ti, final TableActionCommand tableCommand, final boolean isMatch,
            final Locale locale) throws IIRSearchException, IIRConnectException, IIRSearchInvalidDataFormatException {
        final long start = System.currentTimeMillis();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Inside the listTargetItemsWrapper");
        }

        final TargetMatchRequest targetMatchRequest = new TargetMatchRequest.TargetMatchRequestBuilder().iirEnabled(iirEnabledFlag).match(isMatch).tableActionCommand(tableCommand).targetItemToBeMatched(ti).locale(locale).build();
        final List<TargetMatcher> targetMatchers = targetMatcherResolver.resolve(targetMatchRequest);
        List<TargetItem> rtnList = new ArrayList<TargetItem>();

        for(final TargetMatcher targetMatcher : targetMatchers) {
            final List<TargetItem> matches = targetMatcher.getMatches(targetMatchRequest);
            if(matches != null && !matches.isEmpty()) {
                rtnList.addAll(matches);
            }
        }

        // Translate JSP column name to IIRSearchField
        final SortColumnType sortByType = SortColumnType.getType(tableCommand.getSortBy());

        if (sortByType != null) {
            SortOrderType sortOrder = SortOrderType.ASCENDING;
            if (TableActionCommand.DESC.equalsIgnoreCase(tableCommand.getSortDirection())) {
                sortOrder = SortOrderType.DESCENDING;
            }

            // ** Sort the IIR results.
            rtnList = sortTargetItems(rtnList, sortByType, sortOrder);

            // ** Pagination details
            if (!tableCommand.isPrintAll()) {
                rtnList = iirDataPaginate(rtnList, tableCommand);
            }
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("Time taken to execute Searching of TargetItems : " + (System.currentTimeMillis() - start));
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Exiting the listTargetItemsWrapper");
        }

        return rtnList;
    }

    /**
     *
     * @param ti
     * @param sortColumnType
     * @param sortOrder
     * @return
     */
    private List<TargetItem> sortTargetItems(final List<TargetItem> ti, final SortColumnType sortColumnType,
            final SortOrderType sortOrder) {
        if (sortColumnType == null) {
            return ti;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Sorting IIR data on " + sortColumnType);
        }

        // Sort the data based on the column name provided
        switch (sortColumnType) {
            case FULL_NAME:
                Collections.sort(ti, new TargetItem.LastFirsNameComparator(sortOrder));
                break;

            case WATCH_LIST_NAME:
                Collections.sort(ti, new TargetItem.WatlNameComparator(sortOrder));
                break;

            case GENDER:
                Collections.sort(ti, new TargetItem.GenderComparator(sortOrder));
                break;

            case PLACE_OF_BIRTH:
                Collections.sort(ti, new TargetItem.BirthPlaceComparator(sortOrder));
                break;

            case COUNTRY_OF_BIRTH:
                Collections.sort(ti, new TargetItem.CountryOfBirthComparator(sortOrder));
                break;

            case BIRTH_DATE:
                Collections.sort(ti, new TargetItem.BirthDateComparator(sortOrder));
                break;

            case NATIONALITY:
                Collections.sort(ti, new TargetItem.NationalityComparator(sortOrder));
                break;

            case DOC_NUM:
                Collections.sort(ti, new TargetItem.DocNoComparator(sortOrder));
                break;

            case VALID_UNTIL_DATE:
                Collections.sort(ti, new TargetItem.ValidUntilDateComparator(sortOrder));
                break;

            case PROTOCOL:
                Collections.sort(ti, new TargetItem.ProtocolNumberComparator(sortOrder));
                break;

            case CREATED_BY:
                Collections.sort(ti, new TargetItem.CreatedByComparator(sortOrder));
                break;

            case REASON_CODE:
                Collections.sort(ti, new TargetItem.RescCodeDescComparator(sortOrder));
                break;

            case MATCH_SCORE:
                Collections.sort(ti, new TargetItem.MatchScoreComparator(sortOrder));
                break;

            case ACTION_CODE:
                Collections.sort(ti, new TargetItem.ActcCodeDescComparator(sortOrder));
                break;

            case SEVERITY_LEVEL:
                Collections.sort(ti, new TargetItem.SeverityComparator(sortOrder));
                break;

            case TARGET_STATUS:
                Collections.sort(ti, new TargetItem.TargetStatusComparator(sortOrder));
                break;

            case FILE_REFERENCE:
                Collections.sort(ti, new TargetItem.FileReferenceComparator(sortOrder));
                break;

            default:
                break;
        }

        return ti;
    }
}
