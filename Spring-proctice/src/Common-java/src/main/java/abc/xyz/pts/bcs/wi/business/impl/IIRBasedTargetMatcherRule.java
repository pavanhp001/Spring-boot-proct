/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.business.impl;

import org.apache.commons.lang.StringUtils;

import abc.xyz.pts.bcs.common.model.Rule;
import abc.xyz.pts.bcs.wi.business.TargetMatcher;
import abc.xyz.pts.bcs.wi.enums.TargetSearchStatus;
import abc.xyz.pts.bcs.wi.model.TargetMatchRequest;

/**
 * <p>
 * This concrete {@link Rule} implementation decides if the
 * {@link TargetMatcher} strategy should be IIR
 * </p>
 * 
 * @author sai.krishnamurthy
 */
public class IIRBasedTargetMatcherRule implements Rule<TargetMatchRequest> {

	@Override
	public boolean evaluate(final TargetMatchRequest input) {
		return (isActiveSearch(input) && input.isIirEnabled() && isNameFieldPresent(input) && StringUtils.isBlank(input.getTargetItemToBeMatched().getFileReferenceNumber()));
	}

	/**
	 * @param input
	 * @return
	 */
	private boolean isActiveSearch(final TargetMatchRequest input) {
		return TargetSearchStatus.ALL.getStatus().equals(input.getTargetItemToBeMatched().getTargetStatus())
				|| TargetSearchStatus.ACTIVE.getStatus().equals(input.getTargetItemToBeMatched().getTargetStatus());
	}

	/**
	 * @param input
	 * @return
	 */
	private boolean isNameFieldPresent(final TargetMatchRequest input) {
		return StringUtils.isNotBlank(input.getTargetItemToBeMatched().getForename())
				|| StringUtils.isNotBlank(input.getTargetItemToBeMatched().getLastName());
	}
}
