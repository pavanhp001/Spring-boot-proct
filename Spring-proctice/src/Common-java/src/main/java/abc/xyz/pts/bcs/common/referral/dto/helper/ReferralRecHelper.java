/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.referral.dto.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import abc.xyz.pts.bcs.common.referral.dto.ReferralFlightRec;
import abc.xyz.pts.bcs.common.referral.dto.ReferralFlightRecImpl;
import abc.xyz.pts.bcs.common.referral.dto.ReferralHitRecImpl;
import abc.xyz.pts.bcs.common.referral.dto.ReferralRec;
import abc.xyz.pts.bcs.common.referral.dto.ReferralRecImpl;
import abc.xyz.pts.bcs.common.referral.dto.ReferralSearchRec;
import abc.xyz.pts.bcs.common.referral.dto.ReferralTravellerRec;
import abc.xyz.pts.bcs.common.referral.dto.ReferralTravellerRecImpl;
import abc.xyz.pts.bcs.wi.dto.RecommendedAction;
import abc.xyz.pts.bcs.wi.dto.SeverityReason;
import abc.xyz.pts.bcs.wi.dto.WatchListName;

public class ReferralRecHelper {

    public static List <ReferralRec> getReferralList(final List <ReferralSearchRec> recList, final List <RecommendedAction> recommendedActions,
            final Map <String, SeverityReason> severityReasons, final List <WatchListName> watchLists) {
        if (recList == null) {
            return Collections.emptyList();
        }

        // Prepare a HashMap of recommended action codes to descriptions
        final Map<String, String> recActionMap = mapRecommendedActionCodeToDescription(recommendedActions);

        // Prepare a HashMap of watch list codes to descriptions
        final Map<String, String> watchListMap = mapWatchListCodeToDescription(watchLists);

        final Iterator<ReferralSearchRec> rsrIterator = recList.iterator();

        // We are always looking one record ahead
        ReferralSearchRec referralSearchRec = null;
        if (rsrIterator.hasNext()) {
            referralSearchRec = rsrIterator.next();
        }

        final List<ReferralRec> referralList = new ArrayList<ReferralRec>();

        while (rsrIterator.hasNext() || referralSearchRec != null) {

            // ** Traveller Data
            final ReferralTravellerRecImpl referralTravellerRec = extractReferralTravellerData(referralSearchRec);

            // ** Flight
            final ReferralFlightRecImpl referralFlightRec = extractFlightData(referralSearchRec);

            // ** Referral Data
            final ReferralRecImpl referralRec = extractReferralData(referralSearchRec, referralTravellerRec,
                    referralFlightRec);

            // **
            // ** NO MORE DATE TO READ
            // **
            referralSearchRec = null;

            // ** Now get all the hits
            int seq = 0;
            while (rsrIterator.hasNext()) {
                final ReferralSearchRec rsrHit = rsrIterator.next();
                if (rsrHit.getRefId().equals(referralRec.getReferralNum())) {
                    // we have more hits to process
                    final ReferralHitRecImpl referralHitRec = new ReferralHitRecImpl();

                    referralHitRec.setReferralNum(referralRec.getReferralNum());
                    referralHitRec.setSeq(++seq);
                    referralHitRec.setHitId(rsrHit.getHitId());
                    referralHitRec.setWatchListName(watchListMap.get(rsrHit.getWlWiWatlName()));
                    referralHitRec.setHitType(rsrHit.getHitType());
                    referralHitRec.setScore(rsrHit.getHitScore());
                    referralHitRec.setSeverity(rsrHit.getSeverityLevel());
                    referralHitRec.setPriority(rsrHit.getPriorityHit());

                    referralHitRec.setRecommendedActionCode(rsrHit.getActionCode());
                    referralHitRec.setRecommendedActionDesc(recActionMap.get(rsrHit.getActionCode()));
                    referralHitRec.setQualificationStatus(rsrHit.getQualificationStatus());
                    referralHitRec.setReasonCode(rsrHit.getWlRescCode());

                    // Resolve Severity Reason Code
                    final SeverityReason reason = severityReasons.get(rsrHit.getWlRescCode());
                    referralHitRec.setReasonDesc(StringUtils.EMPTY);
                    if (reason != null) {
                        referralHitRec.setReasonDesc(reason.getDescription());
                    }

                    referralHitRec.setUpdateVersionNo(rsrHit.getUpdateVersionNo());
                    referralHitRec.setTargetWatchlistId(rsrHit.getWlTarwlId());

                    // Count this referral
                    referralRec.getReferralHits().add(referralHitRec);
                } else {
                    // prepare for the next referral
                    referralSearchRec = rsrHit;
                    break;
                }
            }

            referralList.add(referralRec);
        }

        return referralList;
    }

    private static Map<String, String> mapRecommendedActionCodeToDescription(final List<RecommendedAction> recommendedActions) {

        final Map<String, String> recActionMap = new HashMap<String, String>();

        for (final RecommendedAction ra : recommendedActions) {
            recActionMap.put(ra.getCode(), ra.getDescription());
        }

        return recActionMap;
    }

    private static Map<String, String> mapWatchListCodeToDescription(final List<WatchListName> watchLists) {

        final Map<String, String> watchListMap = new HashMap<String, String>();

        for (final WatchListName wl : watchLists) {
            watchListMap.put(wl.getCode(), wl.getDescription());
        }

        return watchListMap;
    }

    private static ReferralFlightRecImpl extractFlightData(final ReferralSearchRec referralSearchRec) {

        final ReferralFlightRecImpl referralFlightRec = new ReferralFlightRecImpl();

        referralFlightRec.setFlightSegmentId(referralSearchRec.getFlightSegId());
        referralFlightRec.setCarrierCode(referralSearchRec.getOperCarrierCode());
        referralFlightRec.setCarrierNumber(referralSearchRec.getOperFlightNo());
        referralFlightRec.setDepAirportCode(referralSearchRec.getDepAirportCode());
        referralFlightRec.setAppAirportCode(referralSearchRec.getArrAirportCode());
        referralFlightRec.setDepTime(referralSearchRec.getDepDatetime());
        referralFlightRec.setArrTime(referralSearchRec.getArrDatetime());

        return referralFlightRec;
    }

    private static ReferralTravellerRecImpl extractReferralTravellerData(final ReferralSearchRec referralSearchRec) {

        final ReferralTravellerRecImpl referralTravellerRec = new ReferralTravellerRecImpl();

        referralTravellerRec.setTravellerId(referralSearchRec.getTravellerId());
        referralTravellerRec.setMovementType(referralSearchRec.getMovementType());
        referralTravellerRec.setMovementStatus(referralSearchRec.getMovementStatus());
        referralTravellerRec.setTravellerType(referralSearchRec.getTravellerType());
        referralTravellerRec.setTypeOfTravel(referralSearchRec.getTypeOfTravel());
        referralTravellerRec.setFullname(referralSearchRec.getLastName() + ", " + referralSearchRec.getForename());
        referralTravellerRec.setBirthDate(referralSearchRec.getBirthDate());
        referralTravellerRec.setDocType(referralSearchRec.getPriDocType());
        referralTravellerRec.setDocNo(referralSearchRec.getPriDocNo());
        referralTravellerRec.setNationality(referralSearchRec.getNationality());

        return referralTravellerRec;
    }

    private static ReferralRecImpl extractReferralData(final ReferralSearchRec referralSearchRec,
            final ReferralTravellerRec referralTravellerRec, final ReferralFlightRec referralFlightRec) {

        final ReferralRecImpl referralRec = new ReferralRecImpl();
        referralRec.setReferralNum(referralSearchRec.getRefId());
        referralRec.setReferralStatus(referralSearchRec.getReferralStatus());
        referralRec.setHighestHitPriority(referralSearchRec.getPriorityReferral());
        referralRec.setReferralSeverity(referralSearchRec.getReferralSeverity());
        referralRec.setTotalHits(referralSearchRec.getTotalReferralHits());
        referralRec.setCreatedDatetime(referralSearchRec.getCreatedDatetime());
        referralRec.setRecommendedAction(referralSearchRec.getReferralRecActionCode());
        referralRec.setAdditionalInstructionCode(referralSearchRec.getAdditionalInstructionCode());
        referralRec.setTraveller(referralTravellerRec);
        referralRec.setFlight(referralFlightRec);
        referralRec.setUpdateVersioNo(referralSearchRec.getUpdateVersionNo());

        return referralRec;
    }

}
