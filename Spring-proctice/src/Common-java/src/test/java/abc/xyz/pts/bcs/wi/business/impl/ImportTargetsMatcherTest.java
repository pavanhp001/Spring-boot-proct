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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import abc.xyz.pts.bcs.wi.dao.ImportTargetSearchDao;
import abc.xyz.pts.bcs.wi.model.TargetMatchRequest;

@RunWith(MockitoJUnitRunner.class)
public class ImportTargetsMatcherTest {

    @Mock
    private ImportTargetSearchDao importTargetSearchDao;
    private ImportTargetsMatcher importTargetMatcher;

    @Before
    public void setup() {
        importTargetMatcher = new ImportTargetsMatcher(importTargetSearchDao);
    }

    @Test
    public void getMatches_shouldCallTheDaoToGetTheMatches() {

        // Given
        final TargetMatchRequest targetMatchRequest = new TargetMatchRequest.TargetMatchRequestBuilder().build();

        // When
        importTargetMatcher.getMatches(targetMatchRequest);

        // Then
        verify(importTargetSearchDao, times(1)).getImportTargets(targetMatchRequest.getTargetItemToBeMatched(),
                targetMatchRequest.getTableActionCommand(), targetMatchRequest.getLocale());
    }

    @Test
    public void getMatches_shouldCallTheDaoToGetTheMatchesCount() {

        // Given
        final TargetMatchRequest targetMatchRequest = new TargetMatchRequest.TargetMatchRequestBuilder().build();

        // When
        importTargetMatcher.getMatchesCount(targetMatchRequest);

        // Then
        verify(importTargetSearchDao, times(1)).getImportTargetsCount(targetMatchRequest.getTargetItemToBeMatched(),
                targetMatchRequest.getTableActionCommand(), targetMatchRequest.getLocale());
    }
}
