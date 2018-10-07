/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.business;

import java.util.List;

import abc.xyz.pts.bcs.wi.dto.TargetItem;
import abc.xyz.pts.bcs.wi.model.TargetMatchRequest;

/**
 * <p>The core contract that the concrete Target matching strategies should use.
 * This will match the targets based on a the {@link TargetMatchRequest} passed.
 * </p>
 * @author sai.krishnamurthy
 */
public interface TargetMatcher {
    List<TargetItem> getMatches(TargetMatchRequest targetMatchRequest);

    int getMatchesCount(TargetMatchRequest targetMatchRequest);
}
