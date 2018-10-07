/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.business.lookup;

import java.util.List;
import java.util.Locale;

import abc.xyz.pts.bcs.wi.dto.RecommendedAction;

public interface ReferenceDataService
{
    String RECOMMENDED_ACTION_LIST = "recommendedActionList";
    String RECOMMENDED_ACTION_LIST_VISIBLE = "recommendedActionListVisible";

    /**
     * Get a list of Recommended Actions.
     *
     *
     * @param locale
     * @param dataType all/visible
     * @return
     */
    List<RecommendedAction> getRecommendedActionList(final Locale locale, final ReferenceDataServiceType dataType);

}
