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
package abc.xyz.pts.bcs.wi.business.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import abc.xyz.pts.bcs.common.iir.IIRSearchFieldType;
import abc.xyz.pts.bcs.wi.business.IIRSearchRequestFactory;
import abc.xyz.pts.bcs.wi.dao.PrRecommendedActionDao;
import abc.xyz.pts.bcs.wi.dao.PrSeverityReasonDao;
import abc.xyz.pts.bcs.wi.dao.PrWatchListNameDao;
import abc.xyz.pts.bcs.wi.dto.RecommendedAction;
import abc.xyz.pts.bcs.wi.dto.SeverityReason;
import abc.xyz.pts.bcs.wi.dto.TargetItem;
import abc.xyz.pts.bcs.wi.dto.WatchListName;
import abc.xyz.pts.bcs.wi.enums.TargetSearchStatus;
import abc.xyz.pts.bcs.wi.iir.search.IIRSearch;
import abc.xyz.pts.bcs.wi.iir.search.IIRSearchRequest;
import abc.xyz.pts.bcs.wi.iir.search.IIRSearchRequestImpl;
import abc.xyz.pts.bcs.wi.iir.search.IIRSearchResponse;
import abc.xyz.pts.bcs.wi.model.TargetMatchRequest;

@RunWith(MockitoJUnitRunner.class)
public class IIRBasedTargetMatcherTest {

	@Mock
	private IIRSearch iirSearch;
	@Mock
	private PrWatchListNameDao prWatchListNameDao;
	@Mock
	private PrSeverityReasonDao prSeverityReasonDao;
	@Mock
	private PrRecommendedActionDao prRecommendedActionDao;
	@Mock
	private IIRSearchRequestFactory iirSearchRequestFactory;

	private IIRBasedTargetMatcher iirBasedTargetMatcher;

	@Before
	public void setup() {
		iirBasedTargetMatcher = new IIRBasedTargetMatcher(iirSearch, prWatchListNameDao, prSeverityReasonDao, prRecommendedActionDao,
				iirSearchRequestFactory);

	}

	@Test
	public void getMatches_ShouldReturnTheMatchesFromIIR() throws Exception {

		// Given
		TargetItem toMatch = new TargetItem();
		toMatch.setForename("MyForeName");
		toMatch.setTargetStatus(TargetSearchStatus.ALL.getStatus());
		TargetMatchRequest targetMatchRequest = new TargetMatchRequest.TargetMatchRequestBuilder().iirEnabled(true).targetItemToBeMatched(toMatch).build();

		IIRSearchRequest expectedIIRSearchRequest = new IIRSearchRequestImpl();
		expectedIIRSearchRequest.setMatch(targetMatchRequest.isMatch());
		expectedIIRSearchRequest.getCriteria().put(IIRSearchFieldType.ID, targetMatchRequest.getTargetItemToBeMatched().getId());
		expectedIIRSearchRequest.getCriteria().put(IIRSearchFieldType.NATIONALITY, targetMatchRequest.getTargetItemToBeMatched().getNationality());
		expectedIIRSearchRequest.getCriteria().put(IIRSearchFieldType.FORENAMES, targetMatchRequest.getTargetItemToBeMatched().getForename());
		expectedIIRSearchRequest.getCriteria().put(IIRSearchFieldType.LAST_NAME, targetMatchRequest.getTargetItemToBeMatched().getLastName());
		expectedIIRSearchRequest.getCriteria().put(IIRSearchFieldType.DOC_NUM, targetMatchRequest.getTargetItemToBeMatched().getDocNo());
		expectedIIRSearchRequest.getCriteria().put(IIRSearchFieldType.DOC_TYPE, targetMatchRequest.getTargetItemToBeMatched().getDocType());
		expectedIIRSearchRequest.getCriteria().put(IIRSearchFieldType.ACTION_CODE,
				targetMatchRequest.getTargetItemToBeMatched().getRecommendedAction());
		expectedIIRSearchRequest.getCriteria().put(IIRSearchFieldType.PROTOCOL_NUMBER,
				targetMatchRequest.getTargetItemToBeMatched().getProtocolNumber());
		expectedIIRSearchRequest.getCriteria().put(IIRSearchFieldType.GENDER, targetMatchRequest.getTargetItemToBeMatched().getGender());
		expectedIIRSearchRequest.getCriteria().put(IIRSearchFieldType.BIRTH_PLACE, targetMatchRequest.getTargetItemToBeMatched().getBirthPlace());
		expectedIIRSearchRequest.getCriteria().put(IIRSearchFieldType.BIRTH_COUNTRY_CODE,
				targetMatchRequest.getTargetItemToBeMatched().getCountryOfBirth());
		expectedIIRSearchRequest.getCriteria().put(IIRSearchFieldType.BIRTH_DATE, targetMatchRequest.getTargetItemToBeMatched().getBirthDate());
		expectedIIRSearchRequest.getCriteria().put(IIRSearchFieldType.BIRTH_DATE_TO, targetMatchRequest.getTargetItemToBeMatched().getBirthDateTo());
		expectedIIRSearchRequest.getCriteria().put(IIRSearchFieldType.BIRTH_DATE_FROM,
				targetMatchRequest.getTargetItemToBeMatched().getBirthDateFrom());
		expectedIIRSearchRequest.getCriteria().put(IIRSearchFieldType.WATCHLIST_NAME, targetMatchRequest.getTargetItemToBeMatched().getWatlName());
		expectedIIRSearchRequest.getCriteria().put(IIRSearchFieldType.WATCHLIST_ENABLED_IND,
				targetMatchRequest.getTargetItemToBeMatched().getEnabledIndicator());
		expectedIIRSearchRequest.getCriteria().put(IIRSearchFieldType.SEVERITY_LEVEL,
				targetMatchRequest.getTargetItemToBeMatched().getSeverityLevel());
		expectedIIRSearchRequest.getCriteria().put(IIRSearchFieldType.REASON_CODE, targetMatchRequest.getTargetItemToBeMatched().getRescCode());
		expectedIIRSearchRequest.getCriteria().put(IIRSearchFieldType.VALID_UNTIL_DATE,
				targetMatchRequest.getTargetItemToBeMatched().getValidUntilDate());

		final List<RecommendedAction> expectedRecommendedActionsList = new ArrayList<RecommendedAction>();
		final List<SeverityReason> expectedSeverityReasonList = new ArrayList<SeverityReason>();
		final List<WatchListName> expectedWatchListNameList = new ArrayList<WatchListName>();

		RecommendedAction expectedRecommendedAction = new RecommendedAction();
		expectedRecommendedAction.setCode("ACTC-CODE-1");
		expectedRecommendedAction.setDescription("RECOMMENDED-ACTION");
		expectedRecommendedAction.setAppActionCode("APP-ACTION-CODE");
		expectedRecommendedAction.setAutoQualifyInd("AUTO-QUALIFY-IND");
		expectedRecommendedActionsList.add(expectedRecommendedAction);

		SeverityReason expectedSeverityReason = new SeverityReason();
		expectedSeverityReason.setDescription("SEVERITY-DESC");
		expectedSeverityReason.setCode("SEVERITY-CODE");
		expectedSeverityReason.setSeverityLevel(1L);
		expectedSeverityReasonList.add(expectedSeverityReason);

		WatchListName expectedWatchListName = new WatchListName();
		expectedWatchListName.setCode("WL-CODE");
		expectedWatchListName.setDescription("WL-DESC");
		expectedWatchListNameList.add(expectedWatchListName);

		IIRSearchResponse expectedIIRSearchResponse = new MockIIRSearchResponseImpl();
		when(iirSearchRequestFactory.getIIRSearchRequest()).thenReturn(expectedIIRSearchRequest);
		when(iirSearch.search(expectedIIRSearchRequest)).thenReturn(expectedIIRSearchResponse);
		when(prRecommendedActionDao.findAllRecommendedAction(targetMatchRequest.getLocale())).thenReturn(expectedRecommendedActionsList);
		when(prSeverityReasonDao.findAllSeverityReason(targetMatchRequest.getLocale())).thenReturn(expectedSeverityReasonList);
		when(prWatchListNameDao.findAllWatchListNames(targetMatchRequest.getLocale())).thenReturn(expectedWatchListNameList);

		// When
		List<TargetItem> actualTargetItems = iirBasedTargetMatcher.getMatches(targetMatchRequest);

		// Then
		assertThat(2, equalTo(actualTargetItems.size()));

		// Check the refdata mappings.
		for (TargetItem target : actualTargetItems) {
			assertThat(target.getActcCodeDesc(), equalTo("RECOMMENDED-ACTION"));
			assertThat(target.getWatlDesc(), equalTo("WL-DESC"));
			assertThat("SEVERITY-DESC", equalTo("SEVERITY-DESC"));
			assertThat(target.getSeverityLevel(), equalTo(1L));
			assertThat(target.getAutoQualify(), equalTo("AUTO-QUALIFY-IND"));

		}

	}
}
