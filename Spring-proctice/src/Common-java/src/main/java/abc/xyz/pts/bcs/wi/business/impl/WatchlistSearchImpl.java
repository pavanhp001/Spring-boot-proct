/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.business.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import abc.xyz.pts.bcs.common.audit.aspect.AbstractAuditAspect;
import abc.xyz.pts.bcs.common.audit.bean.AuditContextHolder;
import abc.xyz.pts.bcs.common.audit.business.Parameter;
import abc.xyz.pts.bcs.common.enums.SortOrderType;
import abc.xyz.pts.bcs.common.irisk.enums.AlertMatchTypes;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;
import abc.xyz.pts.bcs.irisk.mvo.wiinterface.TargetSearchErrors;
import abc.xyz.pts.bcs.irisk.mvo.wiinterface.WiErrorType;
import abc.xyz.pts.bcs.wi.business.WatchlistSearch;
import abc.xyz.pts.bcs.wi.dao.ClearedDocumentsSearchDao;
import abc.xyz.pts.bcs.wi.dao.ListTargetItemsWrapper;
import abc.xyz.pts.bcs.wi.dao.WatchlistDocSearchDao;
import abc.xyz.pts.bcs.wi.dto.TargetItem;
import abc.xyz.pts.bcs.wi.dto.TargetSearchResults;
import abc.xyz.pts.bcs.wi.iir.exception.IIRConnectException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchInvalidDataFormatException;


public class WatchlistSearchImpl implements WatchlistSearch
{
    @Autowired
    private ListTargetItemsWrapper listTargetItemsWrapper;

    @Autowired
    private WatchlistDocSearchDao watchlistDocSearchDao;

    @Autowired
    private ClearedDocumentsSearchDao clearedDocSearchDao;

    private int thresholdDocSearch;
    private int thresholdPersonSearch;

    private static final Logger LOGGER = Logger.getLogger(WatchlistSearchImpl.class);

    private static final double NANOSECOND_TO_MILISECOND = 1000000.0;
    /**
     * Is the item within the threshold.
     *
     * @param ti
     * @return
     */
    private boolean isWithinThreshold(final TargetItem ti)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("targetId=" + ti.getId()
                    + " matchScore=" + ti.getMatchScore()
                    + " matchType=" + ti.getMatchType()
                    + " docThreshold=" + thresholdDocSearch
                    + " personThreshold=" + thresholdPersonSearch
                    );
        }

        if (ti.getMatchType() == AlertMatchTypes.DOCUMENT_MATCH) {
            return (ti.getMatchScore() >= thresholdDocSearch);
        } else if (ti.getMatchType() == AlertMatchTypes.PERSON_MATCH) {
            return (ti.getMatchScore() >= thresholdPersonSearch);
        }

        return false;
    }

    /**
     * Remove duplicate target.
     *
     * @param inList
     * @return
     */
    private List<TargetItem> getTargetWithHigestScore(final List<TargetItem> inList)
    {
//        final long startTime = System.nanoTime();

        // ** Sort them by their Id so that we can remove the duplicate
        Collections.sort(inList, new TargetItem.IdComparator(SortOrderType.ASCENDING));

        TargetItem prevTi = null;
        final Map<Long, TargetItem> outTargetMap = new HashMap<Long, TargetItem>(inList.size());
        for (final TargetItem ti : inList)
        {
            if (isWithinThreshold(ti) == false) {
                continue;
            }

            // for duplicate match pick the highest and equal match consider Person Match and ignore Document Match
            if (prevTi != null && prevTi.getId().equals(ti.getId()))
            {
                final int result = ti.getMatchScore().compareTo(prevTi.getMatchScore());

                if (result > 0)
                {
                    outTargetMap.put(ti.getId(), ti);
                }
                else if (result == 0)
                {
                    final TargetItem addTi = (ti.getMatchType() == AlertMatchTypes.PERSON_MATCH ? ti : prevTi);
                    outTargetMap.put(addTi.getId(), addTi);
                }
                else
                {
                    outTargetMap.put(ti.getId(), prevTi);
                }
            }
            else
            {
                outTargetMap.put(ti.getId(), ti);
            }

            prevTi = ti;
        }

        return new ArrayList<TargetItem>(outTargetMap.values());
    }
    /**
     * This method calculate No of Document Search and No of Name Search matched targets
     * @param finalMatches
     */
    private void calculateNoOfDocAndNameHits(final List<TargetItem> finalMatches){
        int noOfDocSearchHits = 0;
        int noOfNameSearchHits = 0;
        for(final TargetItem targetItem : finalMatches){
             if (targetItem.getMatchType() == AlertMatchTypes.DOCUMENT_MATCH){
                 noOfDocSearchHits++;
             }else if(targetItem.getMatchType() == AlertMatchTypes.PERSON_MATCH){
                 noOfNameSearchHits++;
             }
        }


        //Set NoOfNameHits retained to Audit Parameter
        AuditContextHolder.addParameter(Parameter.NO_OF_HITS_RETAINED_NAME
                                        , String.valueOf(noOfNameSearchHits)
                                       );

        //Set NoOfDocHits retained to Audit Parameter
        AuditContextHolder.addParameter(  Parameter.NO_OF_HITS_RETAINED_DOCUMENT
                                        , String.valueOf(noOfDocSearchHits)
                                       );


    }

    private List<TargetItem> getPersonAndDocMatches
            ( final TargetItem travellerItem
            , final TableActionCommand tableCommand
            , final TargetSearchErrors docSearchErrors
            , final TargetSearchErrors irrSearchErrors
            , final Locale locale
            ) throws Exception
        {

        List<TargetItem> personSearchList = new ArrayList<TargetItem>();
        List<TargetItem> docSearchList = new ArrayList<TargetItem>();

        try {

            personSearchList = listTargetItemsWrapper.listTargetItemsWrapper(
                    travellerItem, tableCommand, true, locale);

        } catch (final IIRConnectException connExe) {
            irrSearchErrors.setErrorCode(WiErrorType.IIR_CONN_TIME_OUT);
            irrSearchErrors.setErrorMessage(connExe.getMessage());
            LOGGER.error("ErrorCode ("+ WiErrorType.IIR_CONN_TIME_OUT +") :"+ connExe.getMessage());


        } catch (final IIRSearchInvalidDataFormatException dateExe) {
            irrSearchErrors.setErrorCode(WiErrorType.IIR_DATE_VALIDATION);
            irrSearchErrors.setErrorMessage(dateExe.getMessage());
            LOGGER.error("ErrorCode ("+ WiErrorType.IIR_DATE_VALIDATION +") :"+ dateExe.getMessage());

        } catch (final IIRSearchException searchExe) {
            irrSearchErrors.setErrorCode(WiErrorType.IIR_SEARCH_PROBLEM);
            irrSearchErrors.setErrorMessage(searchExe.getMessage());
            LOGGER.error("ErrorCode ("+ WiErrorType.IIR_SEARCH_PROBLEM +") :"+ searchExe.getMessage());

        } catch (final Exception exe) {
            irrSearchErrors.setErrorCode(WiErrorType.IIR_SEARCH_PROBLEM);
            irrSearchErrors.setErrorMessage(exe.getMessage());
            LOGGER.error("ErrorCode ("+ WiErrorType.IIR_SEARCH_PROBLEM +") :"+ exe.getMessage());
        }

        try {

            docSearchList = watchlistDocSearchDao.getMatches(travellerItem, locale);

        } catch (final DataAccessException searchExe){
            docSearchErrors.setErrorCode(WiErrorType.DOC_DATA_ACCESS_PROBLEM);
            docSearchErrors.setErrorMessage(searchExe.getMessage());
            LOGGER.error("ErrorCode ("+ WiErrorType.DOC_DATA_ACCESS_PROBLEM +") :"+ searchExe.getMessage());

        } catch (final Exception exe) {
            docSearchErrors.setErrorCode(WiErrorType.DOC_SEARCH_PROBLEM);
            docSearchErrors.setErrorMessage(exe.getMessage());
            LOGGER.error("ErrorCode ("+ WiErrorType.DOC_SEARCH_PROBLEM +") :"+ exe.getMessage());
        }

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("item founds for name Search(" + personSearchList.size() + ")"
                    + " document Search(" + docSearchList.size() + ")");
        }

        // ** Merge both data set
        personSearchList.addAll(docSearchList);

        return personSearchList;
    }

    @Override
    /**
     * targetItem contains the traveller details for this service call.
     */
    public TargetSearchResults getMatches
        ( final TargetItem travellerItem
        , final TableActionCommand tableCommand
        , final Locale locale
        ) throws Exception
    {
        final long startTime = System.nanoTime();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Inside the TargetSearchResults");
        }
        final TargetSearchResults resutls = new TargetSearchResults();
        final TargetSearchErrors irrSearchErrors = new TargetSearchErrors();
        final TargetSearchErrors docSearchErrors = new TargetSearchErrors();
        //Get Person And Doc Matches for this target
        final List<TargetItem> personSearchList = getPersonAndDocMatches(travellerItem, tableCommand, docSearchErrors, irrSearchErrors, locale);

        //Filter matches and remove duplicate and matches under threshold limit
        final List<TargetItem> mergedMatches = getTargetWithHigestScore(personSearchList);

        //Set No of Doc Matches and Name Search Matches to Audit Parameters
        calculateNoOfDocAndNameHits(mergedMatches);

        //Calculate numberOfHitsDiscarded Audit Parameter and set to AuditContextHolder
        final int numberOfHitsDiscarded = personSearchList.size() - mergedMatches.size();
        AuditContextHolder.addParameter(  Parameter.NO_OF_HITS_DISCARDED
                                        , String.valueOf(numberOfHitsDiscarded)
                                       );
        // Check for Any Cleared Documents Associated With these MAtches and set id of cleared Documents if any
        checkForAssociatedClearedDocuments(travellerItem, mergedMatches);

        //Add Correlated Matches to Results
        resutls.getTargetMatches().addAll(mergedMatches);

        //check any IRR Search errors
        if(irrSearchErrors.getErrorCode() != null){
            irrSearchErrors.setSearchType( WiErrorType.IIR_SEARCH);
            resutls.getErrors().add(irrSearchErrors);

            //Set Name Search Status to FAILURE if any IRR Search errors
            AuditContextHolder.setEventStatusSuccess(false);
            AuditContextHolder.addParameter(
                      Parameter.NAME_SEARCH_STATUS
                    , AbstractAuditAspect.RESPONSE_STATUS_FAILURE
                   );
        }else{
            //Set Name Search Status to SUCCESS if any IRR Search errors
            AuditContextHolder.addParameter(
                    Parameter.NAME_SEARCH_STATUS
                    , AbstractAuditAspect.RESPONSE_STATUS_SUCCESS
                   );
        }

        //check any DOC Search errors
        if(docSearchErrors.getErrorCode() != null){
            docSearchErrors.setSearchType( WiErrorType.DOC_SEARCH);
            resutls.getErrors().add(docSearchErrors);

            //Set Doc Search Status to FAILURE if any Doc Search errors
            AuditContextHolder.setEventStatusSuccess(false);
            AuditContextHolder.addParameter(
                      Parameter.DOCUMENT_SEARCH_STATUS
                    , AbstractAuditAspect.RESPONSE_STATUS_FAILURE
                   );
        }else{
            //Set Name Search Status to SUCCESS if any IRR Search errors
            AuditContextHolder.addParameter(
                    Parameter.DOCUMENT_SEARCH_STATUS
                    , AbstractAuditAspect.RESPONSE_STATUS_SUCCESS
                   );
        }
        if(LOGGER.isInfoEnabled()) {
            if(travellerItem.getId() != null){
                LOGGER.info("Watchlist search for message id: " + travellerItem.getId() + " took: " + ((System.nanoTime() - startTime)/NANOSECOND_TO_MILISECOND) + " ms");
            }else{
                LOGGER.info("Time taken to execute TargetSearchResults: " + ((System.nanoTime() - startTime)/NANOSECOND_TO_MILISECOND) + " ms");
            }
        }


        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Exiting the TargetSearchResults");
        }
        return resutls;
    }

    /**
     * Now check for cleared Document matches.  targetItem (passed into method) contains the traveller details,
     * so we pass in targetItem and also the id of each target returned
     * @param travellerItem
     * @param mergedMatches
     */
    private void checkForAssociatedClearedDocuments(
            final TargetItem travellerItem, final List<TargetItem> mergedMatches) {
        int totalClearedDocumentsMatches = 0;
        try {
            if (mergedMatches != null && !mergedMatches.isEmpty() ) {
                for (final TargetItem item : mergedMatches) {
                    final Long id = clearedDocSearchDao.getClearedDocId(item.getId(),travellerItem);
                    item.setClearedDocumentsId(id);
                    if(id != null){
                        totalClearedDocumentsMatches++;
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("Cleared doc " + id + " found for traveller " + travellerItem.getForename() + " " + travellerItem.getLastName() + " and target id " + item.getId());
                        }
                    } else {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("No cleared document found for traveller " + travellerItem.getForename() + " " + travellerItem.getLastName() + " and target id " + item.getId());
                        }
                    }
                }
            } else {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("No target matches returned, and so no cleared hit checking performed.");
                }
            }
        } catch (final Exception e) {
            LOGGER.warn("An error occurred trying to check targets against cleared documents lists.",e);
        }

        //Set No Of Cleared Document Matched Hits
        AuditContextHolder.addParameter(
                  Parameter.NO_OF_CLEARED_DOCUMENT_HITS
                , String.valueOf(totalClearedDocumentsMatches)
               );

    }


    public void setWatchlistDocSearchDao(final WatchlistDocSearchDao watchlistDocSearchDao)
    {
        this.watchlistDocSearchDao = watchlistDocSearchDao;
    }

    public void setListTargetItemsWrapper(final ListTargetItemsWrapper listTargetItemsWrapper) {
        this.listTargetItemsWrapper = listTargetItemsWrapper;
    }

    public void setThresholdDocSearch(final int thresholdDocSearch) {
        this.thresholdDocSearch = thresholdDocSearch;
    }

    public void setThresholdIIRSearch(final int thresholdIIRSearch) {
        this.thresholdPersonSearch = thresholdIIRSearch;
    }

    @Override
    public TargetSearchResults getMatches(final TargetItem targetItem,
            final TableActionCommand tableCommand) throws Exception {
        return  getMatches(targetItem, tableCommand, Locale.ENGLISH);
    }

}

