/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.business.impl;

import java.util.List;

import abc.xyz.pts.bcs.wi.business.TargetMatcher;
import abc.xyz.pts.bcs.wi.dao.WatchlistSearchDao;
import abc.xyz.pts.bcs.wi.dto.TargetItem;
import abc.xyz.pts.bcs.wi.model.TargetMatchRequest;

/**
 * <p>A concrete {@link TargetMatcher} strategy based on matching done by SQLs.</p>
 * @author sai.krishnamurthy
 */
public class SQLBasedTargetMatcher implements TargetMatcher {

    private final WatchlistSearchDao watchlistSearchDao;

    public SQLBasedTargetMatcher(final WatchlistSearchDao watchlistSearchDao) {
        this.watchlistSearchDao = watchlistSearchDao;
    }

    @Override
    public List<TargetItem> getMatches(final TargetMatchRequest targetMatchRequest) {
        return watchlistSearchDao.search(targetMatchRequest.getTargetItemToBeMatched(),
                targetMatchRequest.getTableActionCommand(), targetMatchRequest.getLocale());
    }

    @Override
    public int getMatchesCount(final TargetMatchRequest targetMatchRequest) {
        return watchlistSearchDao.getTargetCount(targetMatchRequest.getTargetItemToBeMatched(),
                targetMatchRequest.getTableActionCommand(), targetMatchRequest.getLocale());
    }
}
