/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.wi.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import abc.xyz.pts.bcs.common.model.Rule;
import abc.xyz.pts.bcs.wi.business.impl.IIRBasedTargetMatcher;
import abc.xyz.pts.bcs.wi.business.impl.IIRBasedTargetMatcherRule;
import abc.xyz.pts.bcs.wi.business.impl.ImportTargetMatcherRule;
import abc.xyz.pts.bcs.wi.business.impl.ImportTargetsMatcher;
import abc.xyz.pts.bcs.wi.business.impl.SQLBasedTargetMatcher;
import abc.xyz.pts.bcs.wi.business.impl.SQLBasedTargetMatcherRule;
import abc.xyz.pts.bcs.wi.business.impl.WeightingsBasedPersonTargetMatcher;
import abc.xyz.pts.bcs.wi.business.impl.WeightingsBasedPersonTargetMatcherRule;
import abc.xyz.pts.bcs.wi.dto.TargetItem;
import abc.xyz.pts.bcs.wi.enums.TargetSearchStatus;
import abc.xyz.pts.bcs.wi.model.TargetMatchRequest;

public class TargetMatcherResolverTest {

    private TargetMatcherResolver resolver;


    private void injectRuleMappings(final boolean isImport) {
	Map<TargetMatcher, List<Rule<TargetMatchRequest>>> ruleMappings = new LinkedHashMap<TargetMatcher, List<Rule<TargetMatchRequest>>>();
	IIRBasedTargetMatcher iirBasedTargetMatcher = new IIRBasedTargetMatcher(null,null,null,null,null);
	List<Rule<TargetMatchRequest>> iirRulesList = new ArrayList<Rule<TargetMatchRequest>>();
	iirRulesList.add(new IIRBasedTargetMatcherRule());

	SQLBasedTargetMatcher sqlBasedTargetMatcher = new SQLBasedTargetMatcher(null);
	List<Rule<TargetMatchRequest>> sqlRulesList = new ArrayList<Rule<TargetMatchRequest>>();
	sqlRulesList.add(new SQLBasedTargetMatcherRule());
	
	WeightingsBasedPersonTargetMatcher weightingsBasedPersonTargetMatcher = new WeightingsBasedPersonTargetMatcher(null,null);
	List<Rule<TargetMatchRequest>> weightingsBasedTargetMatcherRulesList = new ArrayList<Rule<TargetMatchRequest>>();
	weightingsBasedTargetMatcherRulesList.add(new WeightingsBasedPersonTargetMatcherRule());

	ruleMappings.put(iirBasedTargetMatcher, iirRulesList);
	ruleMappings.put(sqlBasedTargetMatcher, sqlRulesList);
	ruleMappings.put(weightingsBasedPersonTargetMatcher, weightingsBasedTargetMatcherRulesList);
	
	if (isImport) {
	    ImportTargetsMatcher importTargetsMatcher = new ImportTargetsMatcher(null);
	    List<Rule<TargetMatchRequest>> importTargetsRuleList = new ArrayList<Rule<TargetMatchRequest>>();
	    importTargetsRuleList.add(new ImportTargetMatcherRule());
	    ruleMappings.put(importTargetsMatcher, importTargetsRuleList);
	}

	resolver = new TargetMatcherResolver(ruleMappings);
    }

    @Test
    public void resolve_shouldResolveToIIRBasedTargetMatchingImplementation_GivenNonNullFirstNameAndIIREnabled() {
	injectRuleMappings(false);
	TargetItem toMatch = new TargetItem();
	toMatch.setForename("MyForeName");
	toMatch.setTargetStatus(TargetSearchStatus.ALL.getStatus());
	TargetMatchRequest targetMatchRequest = new TargetMatchRequest.TargetMatchRequestBuilder().iirEnabled(true).targetItemToBeMatched(toMatch).build();
	List<TargetMatcher> targetMatcher = resolver.resolve(targetMatchRequest);
	assertEquals("Should resolve to IIR Target Matcher!", 1, targetMatcher.size());
	assertTrue("Should resolve to IIR Target Matcher!", targetMatcher.get(0) instanceof IIRBasedTargetMatcher);
    }

    @Test
    public void resolve_shouldResolveToIIRBasedTargetMatchingImplementation_GivenNonNullLastNameAndIIREnabled() {
	injectRuleMappings(false);
	TargetItem toMatch = new TargetItem();
	toMatch.setLastName("MyForeName");
	toMatch.setTargetStatus(TargetSearchStatus.ALL.getStatus());
	TargetMatchRequest targetMatchRequest = new TargetMatchRequest.TargetMatchRequestBuilder().iirEnabled(true).targetItemToBeMatched(toMatch).build();
	List<TargetMatcher> targetMatcher = resolver.resolve(targetMatchRequest);
	assertEquals("Should resolve to IIR Target Matcher!", 1, targetMatcher.size());
	assertTrue("Should resolve to IIR Target Matcher!", targetMatcher.get(0) instanceof IIRBasedTargetMatcher);
    }

    @Test
    public void resolve_shouldResolveToSQLBasedTargetMatchingImplementation_GivenNullFirstAndNullLastNameAndIIRDisabled() {
	injectRuleMappings(false);
	TargetItem toMatch = new TargetItem();
	toMatch.setTargetStatus(TargetSearchStatus.ALL.getStatus());
	TargetMatchRequest targetMatchRequest = new TargetMatchRequest.TargetMatchRequestBuilder().targetItemToBeMatched(toMatch).build();
	List<TargetMatcher> targetMatcher = resolver.resolve(targetMatchRequest);
	assertEquals("Should resolve to SQL Target Matcher!", 1, targetMatcher.size());
	assertTrue("Should resolve to SQL Target Matcher!", targetMatcher.get(0) instanceof SQLBasedTargetMatcher);
    }
    
    @Test
    public void resolve_shouldResolveToSQLBasedTargetMatchingImplementation_GivenNonNullFileReferenceNumberAndIIREnabled() {
	injectRuleMappings(false);
	TargetItem toMatch = new TargetItem();
	toMatch.setForename("Forename");
	toMatch.setLastName("Lastname");
	toMatch.setFileReferenceNumber("FileRefNumber");
	toMatch.setTargetStatus(TargetSearchStatus.ALL.getStatus());
	TargetMatchRequest targetMatchRequest = new TargetMatchRequest.TargetMatchRequestBuilder().targetItemToBeMatched(toMatch).iirEnabled(true).build();
	List<TargetMatcher> targetMatcher = resolver.resolve(targetMatchRequest);
	assertEquals("Should resolve to SQL Target Matcher!", 1, targetMatcher.size());
	assertTrue("Should resolve to SQL Target Matcher!", targetMatcher.get(0) instanceof SQLBasedTargetMatcher);
    }

    @Test
    public void resolve_shouldResolveToImportTargetMatchingImplementation_GivenNonActiveSearch() {
	injectRuleMappings(true);
	TargetItem toMatch = new TargetItem();
	toMatch.setTargetStatus(TargetSearchStatus.ALL.getStatus());
	TargetMatchRequest targetMatchRequest = new TargetMatchRequest.TargetMatchRequestBuilder().targetItemToBeMatched(toMatch).build();
	List<TargetMatcher> targetMatcher = resolver.resolve(targetMatchRequest);
	assertEquals("Should resolve to SQL Target Matcher!", 2, targetMatcher.size());
	assertTrue("Should resolve to SQL Target Matcher!", targetMatcher.get(0) instanceof SQLBasedTargetMatcher);
	assertTrue("Should resolve to ImportTarget Matcher!", targetMatcher.get(1) instanceof ImportTargetsMatcher);
    }
    
    @Test
    public void resolve_shouldResolveToWeightingsBasedTargetMatchingImplementation_GivenNonNullLastNameAndIIRDisabled() {
	injectRuleMappings(false);
	TargetItem toMatch = new TargetItem();
	toMatch.setLastName("MyForeName");
	toMatch.setTargetStatus(TargetSearchStatus.ALL.getStatus());
	TargetMatchRequest targetMatchRequest = new TargetMatchRequest.TargetMatchRequestBuilder().iirEnabled(false).targetItemToBeMatched(toMatch).build();
	List<TargetMatcher> targetMatcher = resolver.resolve(targetMatchRequest);
	assertEquals("Should resolve to WeightingsBasedPersonTargetMatcher!", 1, targetMatcher.size());
	assertTrue("Should resolve to WeightingsBasedPersonTargetMatcher!", targetMatcher.get(0) instanceof WeightingsBasedPersonTargetMatcher);
    }
}
