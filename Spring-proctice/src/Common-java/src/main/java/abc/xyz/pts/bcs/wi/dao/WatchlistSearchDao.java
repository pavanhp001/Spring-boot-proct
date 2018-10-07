/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.dao;

/**
 * @author MMotlib-Siddiqui
 */

import java.util.List;
import java.util.Locale;

import abc.xyz.pts.bcs.common.web.command.TableActionCommand;
import abc.xyz.pts.bcs.wi.dto.TargetItem;


public interface WatchlistSearchDao
{
    public List<TargetItem> search(TargetItem targetItem, TableActionCommand tableCommand);
    public List<TargetItem> search(TargetItem targetItem, TableActionCommand tableCommand, Locale locale);
    public List<TargetItem> searchAll(TargetItem targetItem, TableActionCommand tableCommand, Locale locale);

    /**
     * <code>getTargetCount</code> is used to get the count of the number of targets for the given
     * search criteria.
     * @param targetItem
     * @param tableCommand
     * @param locale
     * @return
     */
    int getTargetCount(TargetItem targetItem, TableActionCommand tableCommand, Locale locale);

}
