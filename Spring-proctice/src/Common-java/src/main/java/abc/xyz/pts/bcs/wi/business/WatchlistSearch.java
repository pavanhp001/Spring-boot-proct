/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.business;

import java.util.Locale;

import abc.xyz.pts.bcs.common.web.command.TableActionCommand;
import abc.xyz.pts.bcs.wi.dto.TargetItem;
import abc.xyz.pts.bcs.wi.dto.TargetSearchResults;

public interface WatchlistSearch
{
    /**
     * Get matches for the target.
     *
     * Date from IIR and Document search is combined to produce a unique result set.
     * In the case where we get same target from both sources, we keep the one with the highest score.
     *
     * @param targetItem
     * @param tableCommand
     * @return unique targets
     * @throws Exception
     */
    TargetSearchResults getMatches(final TargetItem targetItem, TableActionCommand tableCommand
        ) throws Exception;
    TargetSearchResults getMatches(final TargetItem targetItem, TableActionCommand tableCommand,
            Locale locale) throws Exception;
}
