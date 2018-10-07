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
package abc.xyz.pts.bcs.wi.dao;

import java.util.List;
import java.util.Locale;

import abc.xyz.pts.bcs.common.web.command.TableActionCommand;
import abc.xyz.pts.bcs.wi.dto.TargetItem;
import abc.xyz.pts.bcs.wi.iir.exception.IIRConnectException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchInvalidDataFormatException;

public interface ListTargetItemsWrapper {

    /**
     * Call the stored procedure to find all matches in the Target and cleared
     * traveller List stored in either the IIR or local database.
     * The search is done using the supplied parameters.
     * @param locale TODO
     * @param targetItemList
     *                 The DTO containing the search parameters.
     *
     * @return The updated DTO including any results.
     */
    List<TargetItem> listTargetItemsWrapper(
            final TargetItem targetItem
            , final TableActionCommand tableCommand
            , final boolean isMatch
            , final Locale locale) throws Exception;

    List<TargetItem> listTargetItemsWrapper(
            final TargetItem targetItem
            , final TableActionCommand tableCommand) throws Exception;

    List<TargetItem> listTargetItemsWrapper(
            final TargetItem targetItem
            , final TableActionCommand tableCommand
            , final Locale locale) throws Exception;

    /**
     * <code>getTargetItemsCount</code> is used to get the count of the number targets for the given
     * search criteria.
     * @param ti
     * @param tableCommand
     * @param locale
     * @return
     * @throws IIRSearchException
     * @throws IIRConnectException
     * @throws IIRSearchInvalidDataFormatException
     */
    int getTargetItemsCount(TargetItem ti, TableActionCommand tableCommand, Locale locale) throws IIRSearchException,
    IIRConnectException, IIRSearchInvalidDataFormatException;
}
