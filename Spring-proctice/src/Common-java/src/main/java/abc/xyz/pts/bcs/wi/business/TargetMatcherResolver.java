/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import abc.xyz.pts.bcs.common.model.Rule;
import abc.xyz.pts.bcs.wi.model.TargetMatchRequest;

public class TargetMatcherResolver {
	 private static final Logger LOG = Logger.getLogger(TargetMatcherResolver.class);
    
    private final Map<TargetMatcher, List<Rule<TargetMatchRequest>>> matcherRulesMapping;
    
    public TargetMatcherResolver(final Map<TargetMatcher, List<Rule<TargetMatchRequest>>> matcherRulesMapping) {
	this.matcherRulesMapping = matcherRulesMapping;
    }

    public List<TargetMatcher> resolve(final TargetMatchRequest targetMatchRequest) {
	List<TargetMatcher> matchers = new ArrayList<TargetMatcher>(); 
	for(Map.Entry<TargetMatcher, List<Rule<TargetMatchRequest>>> entry   : matcherRulesMapping.entrySet()) {
	    boolean allMatched = true;
	    for(Rule<TargetMatchRequest> rule : entry.getValue()) {
		if(!rule.evaluate(targetMatchRequest)) {
		    allMatched = false;
		    break;
		}
	    }
	    if(allMatched) {
		matchers.add(entry.getKey());
	    }
	}
	
	LOG.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Returning TargetMatchers : " + matchers); 
	return matchers ; 
    }
}
