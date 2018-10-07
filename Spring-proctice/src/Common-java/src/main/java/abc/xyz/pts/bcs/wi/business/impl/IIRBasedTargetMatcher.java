/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.business.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import abc.xyz.pts.bcs.common.iir.IIRSearchFieldType;
import abc.xyz.pts.bcs.wi.business.IIRSearchRequestFactory;
import abc.xyz.pts.bcs.wi.business.TargetMatcher;
import abc.xyz.pts.bcs.wi.dao.PrRecommendedActionDao;
import abc.xyz.pts.bcs.wi.dao.PrSeverityReasonDao;
import abc.xyz.pts.bcs.wi.dao.PrWatchListNameDao;
import abc.xyz.pts.bcs.wi.dao.impl.ListTargetItemsWrapperImpl;
import abc.xyz.pts.bcs.wi.dto.RecommendedAction;
import abc.xyz.pts.bcs.wi.dto.SeverityReason;
import abc.xyz.pts.bcs.wi.dto.TargetItem;
import abc.xyz.pts.bcs.wi.dto.WatchListName;
import abc.xyz.pts.bcs.wi.iir.exception.IIRConnectException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchInvalidDataFormatException;
import abc.xyz.pts.bcs.wi.iir.search.IIRSearch;
import abc.xyz.pts.bcs.wi.iir.search.IIRSearchRequest;
import abc.xyz.pts.bcs.wi.iir.search.IIRSearchResponse;
import abc.xyz.pts.bcs.wi.model.TargetMatchRequest;

/**
 * <p>IIR Based Target Matcher implementation. This code was copied from {@link ListTargetItemsWrapperImpl} as part of refactoring</p>
 * @author sai.krishnamurthy
 */
public class IIRBasedTargetMatcher implements TargetMatcher {
    private static final Logger LOG = Logger.getLogger(IIRBasedTargetMatcher.class);

    private final IIRSearch iirSearch;
    private final PrWatchListNameDao prWatchListNameDao;
    private final PrSeverityReasonDao prSeverityReasonDao;
    private final PrRecommendedActionDao prRecommendedActionDao;
    public IIRSearchRequest test;
    private final IIRSearchRequestFactory iirSearchRequestFactory;

    private static final double NANOSECOND_TO_MILISECOND = 1000000.0;

    public IIRBasedTargetMatcher(final IIRSearch iirSearch, final PrWatchListNameDao prWatchListNameDao,
            final PrSeverityReasonDao prSeverityReasonDao, final PrRecommendedActionDao prRecommendedActionDao,
            final IIRSearchRequestFactory iirSearchRequestFactory) {
        this.iirSearch = iirSearch;
        this.prWatchListNameDao = prWatchListNameDao;
        this.prSeverityReasonDao = prSeverityReasonDao;
        this.prRecommendedActionDao = prRecommendedActionDao;
        this.iirSearchRequestFactory = iirSearchRequestFactory;
    }

    @Override
    public List<TargetItem> getMatches(final TargetMatchRequest targetMatchRequest) {

        try {
            final long startTime = System.nanoTime();

            // IIR search
            final IIRSearchRequest req = iirSearchRequestFactory.getIIRSearchRequest();
            req.setMatch(targetMatchRequest.isMatch());

            // Read the details from the UI
            addUserDetailsToIirSearchReq(req, targetMatchRequest.getTargetItemToBeMatched());

            // Process search results
            final IIRSearchResponse iirResp = iirSearch.search(req);
            List<TargetItem> rtnList = iirResp.getTargetList();

            if (LOG.isInfoEnabled()) {
                LOG.info("IIR List Size : " + (rtnList != null ? rtnList.size() : 0));
            }

            // ** Convert code to Description
            // TODO: Reading the 3 data tables every time there's a watchlist
            // lookup
            // is terrible!
            rtnList = resolveRefData(rtnList, targetMatchRequest.getLocale());

            // If sortByType is null, then it's a backend request, so don't
            // bother
            // sorting or paginating results.
            // Ideally, we probably shouldn't be using the TableCommand object
            // to
            // pass data in, as that's very UI-specific

            if (LOG.isInfoEnabled()) {
                if (targetMatchRequest.getTargetItemToBeMatched().getId() != null) {
                    LOG.info("IIR lookup for Traveller ID: " + targetMatchRequest.getTargetItemToBeMatched().getId() + " time taken: "
                            + ((System.nanoTime() - startTime) / NANOSECOND_TO_MILISECOND) + " ms");
                } else {
                    LOG.info("IIR lookup, time taken:" + ((System.nanoTime() - startTime) / NANOSECOND_TO_MILISECOND) + " ms");
                }
            }
            return rtnList;
        } catch (final IIRSearchException e) {
            throw new RuntimeException(e);
        } catch (final IIRConnectException e) {
            throw new RuntimeException(e);
        } catch (final IIRSearchInvalidDataFormatException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Get the information input by the user and add it to the IIR Search
     * Request that will be used by the IIR Search
     *
     * @param req
     *            IIRSearchRequest containing details to be sent to IIR Search
     * @param targetItem
     *            TargetItemList containing the details from the UI
     * @return IIRSearchRequestImpl
     */
    private IIRSearchRequest addUserDetailsToIirSearchReq(final IIRSearchRequest req, final TargetItem targetItem) {
        final Map<IIRSearchFieldType, Object> criteria = req.getCriteria();
        criteria.put(IIRSearchFieldType.ID, targetItem.getId()); // to prevent
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
     * Resolved the reference data.
     *
     * @param ti
     * @return
     */
    private List<TargetItem> resolveRefData(final List<TargetItem> ti, final Locale locale) {
        final List<RecommendedAction> recAction = prRecommendedActionDao.findAllRecommendedAction(locale);
        final List<SeverityReason> svReasonList = prSeverityReasonDao.findAllSeverityReason(locale);
        final List<WatchListName> wlList = prWatchListNameDao.findAllWatchListNames(locale);

        // *****
        // ** resolved reference data
        // *****

        // ** Action
        // ************************
        final HashMap<String, RecommendedAction> recActionMap = new HashMap<String, RecommendedAction>();
        for (final RecommendedAction ra : recAction) {
            recActionMap.put(ra.getCode(), ra);
        }

        // ** Severity & Reason
        // ************************
        final HashMap<String, SeverityReason> svReasonMap = new HashMap<String, SeverityReason>();
        for (final SeverityReason sr : svReasonList) {
            svReasonMap.put(sr.getCode(), sr);
        }

        // ** WatchList Map
        // *************************
        final HashMap<String, WatchListName> wlMap = new HashMap<String, WatchListName>();
        for (final WatchListName wl : wlList) {
            wlMap.put(wl.getCode(), wl);
        }

        // ** Map Code to Description
        // ***************************
        if (ti != null) {
            for (final TargetItem item : ti) {
                // Recommended Action
                if (item.getActcCode() != null) {
                    final RecommendedAction ra = recActionMap.get(item.getActcCode());
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
                    final SeverityReason sr = svReasonMap.get(item.getRescCode());
                    if (sr != null) {
                        item.setRescCodeDesc(sr.getDescription());
                        item.setSeverityLevel(sr.getSeverityLevel() != null ? sr.getSeverityLevel() : null);
                    } else {
                        item.setRescCodeDesc(item.getRescCode()); // show code
                        // instead
                    }
                }

                // ** Watch list name
                if (item.getWatlName() != null) {
                    final WatchListName wl = wlMap.get(item.getWatlName());
                    if (wl != null) {
                        item.setWatlDesc(wl.getDescription());
                    } else {
                        item.setWatlDesc(item.getWatlName()); // show code
                        // instead
                    }
                }
            }
        }

        return ti;
    }

    @Override
    public int getMatchesCount(final TargetMatchRequest targetMatchRequest) {
        int rowCount = 0;
        final List<TargetItem> items = getMatches(targetMatchRequest);
        if (items != null && !items.isEmpty()) {
            rowCount = items.size();
        }

        return rowCount;
    }
}
