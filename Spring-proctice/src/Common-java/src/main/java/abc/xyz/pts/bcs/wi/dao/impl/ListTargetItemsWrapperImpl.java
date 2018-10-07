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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import abc.xyz.pts.bcs.common.enums.SortOrderType;
import abc.xyz.pts.bcs.common.iir.IIRSearchFieldType;
import abc.xyz.pts.bcs.common.util.SortColumnType;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;
import abc.xyz.pts.bcs.wi.dao.ImportTargetSearchDao;
import abc.xyz.pts.bcs.wi.dao.ListTargetItemsWrapper;
import abc.xyz.pts.bcs.wi.dao.PrRecommendedActionDao;
import abc.xyz.pts.bcs.wi.dao.PrSeverityReasonDao;
import abc.xyz.pts.bcs.wi.dao.PrWatchListNameDao;
import abc.xyz.pts.bcs.wi.dao.WatchlistSearchDao;
import abc.xyz.pts.bcs.wi.dto.RecommendedAction;
import abc.xyz.pts.bcs.wi.dto.SeverityReason;
import abc.xyz.pts.bcs.wi.dto.TargetItem;
import abc.xyz.pts.bcs.wi.dto.WatchListName;
import abc.xyz.pts.bcs.wi.enums.TargetSearchStatus;
import abc.xyz.pts.bcs.wi.iir.exception.IIRConnectException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchInvalidDataFormatException;
import abc.xyz.pts.bcs.wi.iir.search.IIRSearch;
import abc.xyz.pts.bcs.wi.iir.search.IIRSearchRequestImpl;
import abc.xyz.pts.bcs.wi.iir.search.IIRSearchResponse;


@Deprecated
public class ListTargetItemsWrapperImpl implements ListTargetItemsWrapper {
    private static final Logger LOG =
            Logger.getLogger(ListTargetItemsWrapperImpl.class);

    /*
     * Properties
     */
    private PrWatchListNameDao prWatchListNameDao;
    private PrSeverityReasonDao prSeverityReasonDao;
    private PrRecommendedActionDao prRecommendedActionDao;
    private WatchlistSearchDao watchlistSearchDao;
    private IIRSearch iirSearch;
    private ImportTargetSearchDao importTargetSearchDao;

    /** Flag to indicate whether the IIR is enabled or disabled. */
    private boolean iirEnabledFlag;

    public void setPrWatchListNameDao(
            final PrWatchListNameDao prWatchListNameDao) {
        this.prWatchListNameDao = prWatchListNameDao;
    }

    public void setPrSeverityReasonDao(
            final PrSeverityReasonDao prSeverityReasonDao) {
        this.prSeverityReasonDao = prSeverityReasonDao;
    }

    public void setPrRecommendedActionDao(
            final PrRecommendedActionDao prRecommendedActionDao) {
        this.prRecommendedActionDao = prRecommendedActionDao;
    }

    /**
     * @param iirEnabledFlag the iirEnabledFlag to set
     */
    public void setIirEnabledFlag(final boolean iirEnabledFlag) {
        this.iirEnabledFlag = iirEnabledFlag;
    }

    /**
     * Setter method for property iirSearch to allow Spring to inject
     * an instance of the class IIRSearchImpl
     *
     * @param iirSearch
     *                 instance of the class IirSearchImpl
     */
    public void setIirSearch(final IIRSearch iirSearch) {
        this.iirSearch = iirSearch;
    }

    public void setWatchlistSearchDao(
            final WatchlistSearchDao watchlistSearchDao) {
        this.watchlistSearchDao = watchlistSearchDao;
    }

    /**
     * @param importTargetSearchDao the importTargetSearchDao to set
     */
    public void setImportTargetSearchDao(
            final ImportTargetSearchDao importTargetSearchDao) {
        this.importTargetSearchDao = importTargetSearchDao;
    }

    /**
     * Return subset of data.
     *
     * @param tItemList
     * @param tableCommand
     * @return
     */
    @SuppressWarnings("static-method")
    public List<TargetItem> iirDataPaginate(final List<TargetItem> tItemList,
            final TableActionCommand tableCommand) {
        final int pageNumber = tableCommand.getPageNumber() - 1;
        final int pageSize = tableCommand.getPageSize();

        final int startIdx = pageNumber * pageSize;
        final int endIdx = Math.min(startIdx + pageSize, tItemList.size());

        final List<TargetItem> pageItems = tItemList.subList(startIdx, endIdx);

        // If we haven't reached the end of the list, show next/prev buttons
        if(endIdx < tItemList.size()) {
            tableCommand.setPagesAvailable(true);
        } else {
            tableCommand.setPagesAvailable(false);
        }

        tableCommand.setTotalResults(tItemList.size());
        tableCommand.setRowCountEstimated(false);

        return pageItems;
    }

    @Override
    public List<TargetItem> listTargetItemsWrapper(final TargetItem ti,
            final TableActionCommand tableCommand,
            final Locale locale) throws IIRSearchException,
            IIRConnectException, IIRSearchInvalidDataFormatException {

        return listTargetItemsWrapper(ti, tableCommand, false, locale);
    }

    @Override
    public List<TargetItem> listTargetItemsWrapper(final TargetItem ti,
            final TableActionCommand tableCommand) throws IIRSearchException,
            IIRConnectException, IIRSearchInvalidDataFormatException {

        return listTargetItemsWrapper(ti, tableCommand, false, null);
    }

    /**
     * Search for target items using either the local database or the IIR database
     * when search data is provided.
     * <p>
     * The IIR database is used for the search when a name is provided as one of the
     * search parameters. Otherwise the local database is used for the search.
     * @param isMatch are we doing a match?
     * @param ti
     *                 The DTO containing the search parameters.
     *
     * @return The updated DTO including any results.
     * @throws Exception
     */
    @Override
    public List<TargetItem> listTargetItemsWrapper(final TargetItem ti,
            final TableActionCommand tableCommand, final boolean isMatch,
            final Locale locale) throws IIRSearchException,
            IIRConnectException, IIRSearchInvalidDataFormatException {
        final long start = System.currentTimeMillis();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Inside the listTargetItemsWrapper");
        }

        List<TargetItem> rtnList = new ArrayList<TargetItem>();

        final boolean isActiveSearch =
                (TargetSearchStatus.ALL.getStatus().equals(
                        ti.getTargetStatus())
                        || TargetSearchStatus.ACTIVE.getStatus().equals(
                                ti.getTargetStatus()));

        // IIR or DB Search?
        if (isActiveSearch) {

            // IIR search will be ignored in case of any of the 2 scenarios
            // 1. if both forename and lastname are empty or
            // 2. File reference number is provided.
            final boolean isDBSearch = (StringUtils.isEmpty(ti.getForename())
                    && StringUtils.isEmpty(ti.getLastName()))
                    || StringUtils.isNotBlank(ti.getFileReferenceNumber());

            if (isDBSearch || (iirEnabledFlag == false)) {
                rtnList = watchlistSearchDao.search(ti, tableCommand, locale);
            } else {
                final long startTime = System.nanoTime();

                // IIR search
                final IIRSearchRequestImpl req = new IIRSearchRequestImpl();
                req.setMatch(isMatch);

                // Read the details from the UI
                addUserDetailsToIirSearchReq(req, ti);

                // Process search results
                final IIRSearchResponse iirResp = iirSearch.search(req);
                rtnList = iirResp.getTargetList();

                if (LOG.isInfoEnabled()) {
                    LOG.info("IIR List Size : "
                            + (rtnList != null? rtnList.size() : 0));
                }

                // ** Convert code to Description
                // TODO: Reading the 3 data tables every time there's a watchlist lookup is terrible!
                rtnList = resolveRefData(rtnList, locale);

                // If sortByType is null, then it's a backend request, so don't bother sorting or paginating results.
                // Ideally, we probably shouldn't be using the TableCommand object to pass data in, as that's very UI-specific



                if(LOG.isInfoEnabled()) {
                    if(ti.getId() != null) {
                        LOG.info("IIR lookup for Traveller ID: "
                                + ti.getId() + " time taken: "
                                + ((System.nanoTime() - startTime)/1000000.0) +" ms");
                    } else {
                        LOG.info("IIR lookup, time taken:"
                                + ((System.nanoTime() - startTime)/1000000.0) +" ms");
                    }
                }
            }
        }

        /* If the Status in the Target item is ALL or Error or
         * Duplicate or In_Progress, then Search the Imported Targets. */
        final boolean isImportTargetSearch =
                TargetSearchStatus.ALL.getStatus().equals(
                        ti.getTargetStatus())
                        || TargetSearchStatus.ERROR.getStatus().equals(
                                ti.getTargetStatus())
                                || TargetSearchStatus.READY.getStatus().equals(
                                        ti.getTargetStatus())
                                        || TargetSearchStatus.DUPLICATE.getStatus().equals(
                                                ti.getTargetStatus());
        if (isImportTargetSearch) {
            final List<TargetItem> lst = importTargetSearchDao.getImportTargets(
                    ti, tableCommand, locale);
            if (lst != null && lst.size() > 0) {
                rtnList.addAll(lst);
            }
        }

        // Translate JSP column name to IIRSearchField
        final SortColumnType sortByType =
                SortColumnType.getType(tableCommand.getSortBy());

        if(sortByType != null) {
            SortOrderType sortOrder = SortOrderType.ASCENDING;
            if (TableActionCommand.DESC.equalsIgnoreCase(
                    tableCommand.getSortDirection())) {
                sortOrder = SortOrderType.DESCENDING;
            }

            // ** Sort the IIR results.
            rtnList = sortTargetItems(rtnList, sortByType, sortOrder);

            // ** Pagination details
            if (!tableCommand.isPrintAll()) {
                rtnList = iirDataPaginate(rtnList, tableCommand);
            }
        }

        if(LOG.isInfoEnabled()) {
            LOG.info("Time taken to execute Searching of TargetItems : "
                    + (System.currentTimeMillis() - start));
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Exiting the listTargetItemsWrapper");
        }

        return rtnList;
    }

    /**
     * Resolved the reference data.
     *
     * @param ti
     * @return
     */
    private List<TargetItem> resolveRefData(final List<TargetItem> ti,
            final Locale locale) {
        final List<RecommendedAction> recAction =
                prRecommendedActionDao.findAllRecommendedAction(locale);
        final List<SeverityReason> svReasonList =
                prSeverityReasonDao.findAllSeverityReason(locale);
        final List<WatchListName> wlList =
                prWatchListNameDao.findAllWatchListNames(locale);

        // *****
        // ** resolved reference data
        // *****

        // ** Action
        // ************************
        final HashMap<String, RecommendedAction> recActionMap =
                new HashMap<String, RecommendedAction>();
        for (final RecommendedAction ra : recAction) {
            recActionMap.put(ra.getCode(), ra);
        }

        // ** Severity & Reason
        // ************************
        final HashMap<String, SeverityReason> svReasonMap =
                new HashMap<String, SeverityReason>();
        for (final SeverityReason sr : svReasonList) {
            svReasonMap.put(sr.getCode(), sr);
        }

        // ** WatchList Map
        // *************************
        final HashMap<String, WatchListName> wlMap =
                new HashMap<String, WatchListName>();
        for (final WatchListName wl : wlList) {
            wlMap.put(wl.getCode(), wl);
        }

        // ** Map Code to Description
        // ***************************
        if (ti != null) {
            for (final TargetItem item : ti) {
                // Recommended Action
                if (item.getActcCode() != null) {
                    final RecommendedAction ra =
                            recActionMap.get(item.getActcCode());
                    if (ra != null) {
                        item.setActcCodeDesc(ra.getDescription());
                        item.setAppActionCode(ra.getAppActionCode());
                        item.setAutoQualify(ra.getAutoQualifyInd());
                    } else {
                        item.setActcCodeDesc(item.getActcCode());
                    }
                }

                // ** Reason
                if (item.getRescCode() != null) {
                    final SeverityReason sr =
                            svReasonMap.get(item.getRescCode());
                    if (sr != null) {
                        item.setRescCodeDesc(sr.getDescription());
                        item.setSeverityLevel(sr.getSeverityLevel() != null ? sr.getSeverityLevel() : null);
                    } else {
                        item.setRescCodeDesc(item.getRescCode());     // show code instead
                    }
                }

                // ** Watch list name
                if (item.getWatlName() != null) {
                    final WatchListName wl = wlMap.get(item.getWatlName());
                    if (wl != null) {
                        item.setWatlDesc(wl.getDescription());
                    } else {
                        item.setWatlDesc(item.getWatlName()); // show code instead
                    }
                }
            }
        }

        return ti;
    }


    /**
     * Get the information input by the user and add it to the IIR Search Request
     * that will be used by the IIR Search
     *
     * @param req
     *             IIRSearchRequest containing details to be sent to IIR Search
     * @param targetItem
     *             TargetItemList containing the details from the UI
     * @return IIRSearchRequestImpl
     */
    @SuppressWarnings("static-method")
    private IIRSearchRequestImpl addUserDetailsToIirSearchReq(
            final IIRSearchRequestImpl req, final TargetItem targetItem) {

        /*
         * Extract info from TargetItemList and add to IIRSearchRequestImpl
         */
        final Map<IIRSearchFieldType, Object> criteria = req.getCriteria();

        criteria.put(IIRSearchFieldType.ID, targetItem.getId());  // to prevent itself being selected
        criteria.put(IIRSearchFieldType.NATIONALITY, targetItem.getNationality());
        criteria.put(IIRSearchFieldType.FORENAMES, targetItem.getForename());
        criteria.put(IIRSearchFieldType.LAST_NAME, targetItem.getLastName());
        criteria.put(IIRSearchFieldType.DOC_NUM, targetItem.getDocNo());
        criteria.put(IIRSearchFieldType.DOC_TYPE, targetItem.getDocType());
        criteria.put(IIRSearchFieldType.ACTION_CODE, targetItem.getRecommendedAction());
        criteria.put(IIRSearchFieldType.PROTOCOL_NUMBER, targetItem.getProtocolNumber());
        criteria.put(IIRSearchFieldType.GENDER, targetItem.getGender());
        criteria.put(IIRSearchFieldType.BIRTH_PLACE, targetItem.getBirthPlace());
        criteria.put(IIRSearchFieldType.BIRTH_COUNTRY_CODE, targetItem.getCountryOfBirth());
        criteria.put(IIRSearchFieldType.BIRTH_DATE, targetItem.getBirthDate());
        criteria.put(IIRSearchFieldType.BIRTH_DATE_TO, targetItem.getBirthDateTo());
        criteria.put(IIRSearchFieldType.BIRTH_DATE_FROM, targetItem.getBirthDateFrom());
        criteria.put(IIRSearchFieldType.WATCHLIST_NAME, targetItem.getWatlName());
        criteria.put(IIRSearchFieldType.WATCHLIST_ENABLED_IND, targetItem.getEnabledIndicator());
        criteria.put(IIRSearchFieldType.SEVERITY_LEVEL, targetItem.getSeverityLevel());
        criteria.put(IIRSearchFieldType.REASON_CODE, targetItem.getRescCode());
        criteria.put(IIRSearchFieldType.VALID_UNTIL_DATE, targetItem.getValidUntilDate());

        return req;
    }


    /**
     * Sort a targetItemList based on the column field provided
     *
     * @param ti
     *                 the TargetItemList containing the list of targetItems to be sorted
     * @param sortType
     *                 the search field type used to determine the field that the sorting
     *                 should be based on
     * @return TargetItemList containing the sorted data
     */
    /*@SuppressWarnings("static-method")
    private List<TargetItem> sortTargetItems(final List<TargetItem> ti,
            final IIRSearchFieldType iirSortField,
            final SortOrderType sortOrder) {
        if (iirSortField == null) {
            return ti;
        }

        if(LOG.isDebugEnabled()) {
            LOG.debug("Sorting IIR data on " + iirSortField.getVal());
        }

        // Sort the data based on the column name provided
        switch (iirSortField) {
            case FULL_NAME:
                Collections.sort(ti, new TargetItem.LastFirsNameComparator(sortOrder));
                break;

            case WATCHLIST_NAME:
                Collections.sort(ti, new TargetItem.WatlNameComparator(sortOrder));
                break;

            case GENDER:
                Collections.sort(ti, new TargetItem.GenderComparator(sortOrder));
                break;

            case BIRTH_PLACE:
                Collections.sort(ti, new TargetItem.BirthPlaceComparator(sortOrder));
                break;

            case BIRTH_COUNTRY_CODE:
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

            case PROTOCOL_NUMBER:
                Collections.sort(ti, new TargetItem.ProtocolNumberComparator(sortOrder));
                break;

            case CREATE_BY:
                Collections.sort(ti, new TargetItem.CreatedByComparator(sortOrder));
                break;

            case REASON_CODE:
                Collections.sort(ti, new TargetItem.RescCodeDescComparator(sortOrder));
                break;

            case SCORE:
                Collections.sort(ti, new TargetItem.MatchScoreComparator(sortOrder));
                break;

            case ACTION_CODE:
                Collections.sort(ti, new TargetItem.ActcCodeDescComparator(sortOrder));
                break;

            case SEVERITY_LEVEL:
                Collections.sort(ti, new TargetItem.SeverityComparator(sortOrder));
                break;

            default:
                break;
        }

        return ti;
    }*/

    /**
     *
     * @param ti
     * @param sortColumnType
     * @param sortOrder
     * @return
     */
    @SuppressWarnings("static-method")
    private List<TargetItem> sortTargetItems(final List<TargetItem> ti,
            final SortColumnType sortColumnType,
            final SortOrderType sortOrder) {
        if (sortColumnType == null) {
            return ti;
        }

        if(LOG.isDebugEnabled()) {
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

    @Override
    public int getTargetItemsCount(final TargetItem ti, final TableActionCommand tableCommand, final Locale locale)
            throws IIRSearchException, IIRConnectException, IIRSearchInvalidDataFormatException {
        return 0;
    }
}
