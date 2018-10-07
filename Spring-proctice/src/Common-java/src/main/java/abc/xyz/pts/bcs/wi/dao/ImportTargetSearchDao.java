/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.dao;

import java.util.List;
import java.util.Locale;

import abc.xyz.pts.bcs.common.web.command.TableActionCommand;
import abc.xyz.pts.bcs.wi.dto.TargetItem;

/**
 * <code>ImportTargetSearchDao</code> is used to get the list of
 * imported targets based on the search criteria.
 *
 * @author Thiruvengadam.S
 *
 */
public interface ImportTargetSearchDao {
    /**
     * <code>getImportTargets</code> is used to get the list of
     * target items based on the criteria.
     * @param targetItem
     * @param tableCommand
     * @param locale
     * @return
     */
    List<TargetItem> getImportTargets(final TargetItem targetItem,
            final TableActionCommand tableCommand, final Locale locale);

    /**
     * <code>getImportTargetsCount</code> is used to get the total count of the imported targets
     * for the given search criteria.
     * @param targetItem
     * @param tableCommand
     * @param locale
     * @return
     */
    int getImportTargetsCount(TargetItem targetItem, TableActionCommand tableCommand, Locale locale);
}
