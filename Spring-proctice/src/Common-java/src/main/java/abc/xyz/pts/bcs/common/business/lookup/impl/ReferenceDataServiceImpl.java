/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.business.lookup.impl;

import java.util.List;
import java.util.Locale;

import abc.xyz.pts.bcs.common.business.lookup.ReferenceDataService;
import abc.xyz.pts.bcs.common.business.lookup.ReferenceDataServiceType;
import abc.xyz.pts.bcs.wi.dao.PrRecommendedActionDao;
import abc.xyz.pts.bcs.wi.dto.RecommendedAction;

public final class ReferenceDataServiceImpl implements ReferenceDataService
{
    private PrRecommendedActionDao prRecommendedActionDao;

    public void setPrRecommendedActionDao(final PrRecommendedActionDao prRecommendedActionDao)
    {
        this.prRecommendedActionDao = prRecommendedActionDao;
    }

    @Override
    public List<RecommendedAction> getRecommendedActionList(final Locale locale, final ReferenceDataServiceType dataType)
    {
        if (ReferenceDataServiceType.VISIBLE == dataType)
        {
            return prRecommendedActionDao.findVisibleRecommendedAction(locale);
        }
        else
        {
            return prRecommendedActionDao.findAllRecommendedAction(locale);
        }
    }
}