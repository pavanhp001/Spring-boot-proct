/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.business.impl;

import abc.xyz.pts.bcs.common.model.Rule;
import abc.xyz.pts.bcs.wi.enums.TargetSearchStatus;
import abc.xyz.pts.bcs.wi.model.TargetMatchRequest;

/**
 * <p>The concrete {@link Rule} implementation to choose {@link ImportTargetsMatcher}</p>
 * @author sai.krishnamurthy
 */
public class ImportTargetMatcherRule implements Rule<TargetMatchRequest> {

    @Override
    public boolean evaluate(final TargetMatchRequest input) {
	return (TargetSearchStatus.ALL.getStatus().equals(input.getTargetItemToBeMatched().getTargetStatus())
		|| TargetSearchStatus.ERROR.getStatus().equals(input.getTargetItemToBeMatched().getTargetStatus())
		|| TargetSearchStatus.READY.getStatus().equals(input.getTargetItemToBeMatched().getTargetStatus()) || 
		TargetSearchStatus.DUPLICATE.getStatus().equals(input.getTargetItemToBeMatched().getTargetStatus()));

    }

}
