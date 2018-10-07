/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.wi.business.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import abc.xyz.pts.bcs.common.business.lookup.LookupDataService;
import abc.xyz.pts.bcs.common.enums.WeightingType;
import abc.xyz.pts.bcs.common.irisk.enums.AlertMatchTypes;
import abc.xyz.pts.bcs.wi.business.TargetMatcher;
import abc.xyz.pts.bcs.wi.dao.WatchlistSearchDao;
import abc.xyz.pts.bcs.wi.dto.TargetItem;
import abc.xyz.pts.bcs.wi.dto.WatchlistWeightings;
import abc.xyz.pts.bcs.wi.model.TargetMatchRequest;

public class WeightingsBasedPersonTargetMatcher implements TargetMatcher {

    private static final Logger LOG = Logger.getLogger(WeightingsBasedPersonTargetMatcher.class);
    private final WatchlistSearchDao watchlistSearchDao;
    private final LookupDataService lookupDataService;

    public WeightingsBasedPersonTargetMatcher(final WatchlistSearchDao watchlistSearchDao,
            final LookupDataService lookupDataService) {
        this.watchlistSearchDao = watchlistSearchDao;
        this.lookupDataService = lookupDataService;
    }

    @Override
    public List<TargetItem> getMatches(final TargetMatchRequest targetMatchRequest) {

        final TargetItem targetToBeMatchedForName = new TargetItem();
        targetToBeMatchedForName.setForename(targetMatchRequest.getTargetItemToBeMatched().getForename());
        targetToBeMatchedForName.setLastName(targetMatchRequest.getTargetItemToBeMatched().getLastName());
        targetToBeMatchedForName.setId(targetMatchRequest.getTargetItemToBeMatched().getId());

        LOG.debug("INCOMING TRAVELLER INFORMATION TO BE MATCHED --- "+targetMatchRequest.getTargetItemToBeMatched());

        // Get the candidate matches.
        final List<TargetItem> candidateMatches =
                watchlistSearchDao.searchAll(targetToBeMatchedForName, targetMatchRequest.getTableActionCommand(),
                        targetMatchRequest.getLocale());

        // Get weightings.
        final WatchlistWeightings personWeightings = lookupDataService.getWatchlistWeightings(WeightingType.PERSON);

        LOG.debug("~~~~~~~ Weightings is ... " + personWeightings);
        for (final TargetItem candidate  : candidateMatches) {
            candidate.setMatchScore(
                    calculateTotalScore(personWeightings, targetMatchRequest.getTargetItemToBeMatched(), candidate));
            candidate.setMatchType(AlertMatchTypes.PERSON_MATCH);
        }

        return candidateMatches;
    }

    private Double calculateTotalScore(final WatchlistWeightings personWeightings,
            final TargetItem traveller, final TargetItem candidate) {

        Double baseScore = 0.0;

        if (personWeightings != null) {

            baseScore += personWeightings.getFullName();

            // ** Doc Number
            if (!StringUtils.isEmpty(traveller.getDocNo()) && traveller.getDocNo().equals(candidate.getDocNo())) {
                baseScore += personWeightings.getDocNumber();
            }

            // ** Nationality
            if (!StringUtils.isEmpty(traveller.getNationality())
                    && traveller.getNationality().equals(candidate.getNationality())) {
                baseScore += personWeightings.getNationality();
            }

            // ** Country Of Birth
            if (!StringUtils.isEmpty(traveller.getCountryOfBirth())
                    && traveller.getCountryOfBirth().equals(candidate.getCountryOfBirth())) {
                baseScore += personWeightings.getBirthCountry();
            }

            // ** Gender
            if (!StringUtils.isEmpty(traveller.getGender()) && traveller.getGender().equals(candidate.getGender())) {
                baseScore += personWeightings.getGender();
            }

            // ** Doc Type
            if (!StringUtils.isEmpty(traveller.getDocType())
                    && traveller.getDocType().equals(candidate.getDocType())) {
                baseScore += personWeightings.getDocType();
            }


            // ** BirthDate
            if (traveller.getBirthDate() != null) {
                final long tBirthDate = traveller.getBirthDate().getTimeInMillis();
                if (candidate.getBirthDate() != null) {
                    final long mBirthDate = candidate.getBirthDate().getTimeInMillis();

                    if (tBirthDate == mBirthDate) {
                        baseScore += personWeightings.getBirthDate();
                    }
                } else if (candidate.getBirthDateFrom() != null && candidate.getBirthDateTo() != null) {
                    final long mBirthDateFrom = candidate.getBirthDateFrom().getTimeInMillis();
                    final long mBirthDateTo = candidate.getBirthDateTo().getTimeInMillis();

                    if (tBirthDate >= mBirthDateFrom && tBirthDate <= mBirthDateTo) {
                        baseScore += personWeightings.getBirthDate();
                    }
                }
            }
        }
        LOG.debug("~~~~~~~ Weighting score being returned for Target Item "+candidate + " is : " + baseScore);
        return baseScore;
    }

    @Override
    public int getMatchesCount(final TargetMatchRequest targetMatchRequest) {
        final TargetItem targetToBeMatchedForName = new TargetItem();
        targetToBeMatchedForName.setForename(targetMatchRequest.getTargetItemToBeMatched().getForename());
        targetToBeMatchedForName.setLastName(targetMatchRequest.getTargetItemToBeMatched().getLastName());

        // Get the candidate matches.
        return watchlistSearchDao.getTargetCount(targetToBeMatchedForName,
                targetMatchRequest.getTableActionCommand(), targetMatchRequest.getLocale());
    }
}
