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
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import abc.xyz.pts.bcs.common.business.lookup.LookupDataService;
import abc.xyz.pts.bcs.common.enums.WeightingType;
import abc.xyz.pts.bcs.common.irisk.enums.AlertMatchTypes;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;
import abc.xyz.pts.bcs.wi.business.TargetMatcher;
import abc.xyz.pts.bcs.wi.dao.WatchlistSearchDao;
import abc.xyz.pts.bcs.wi.dto.TargetItem;
import abc.xyz.pts.bcs.wi.dto.WatchlistWeightings;
import abc.xyz.pts.bcs.wi.model.TargetMatchRequest;

@RunWith(MockitoJUnitRunner.class)
public class WeightingsBasedPersonMatcherTest {

	private TargetMatcher weightingsBasedPersonTargetMatcher;
	
	@Mock
	private WatchlistSearchDao watchlistSearchDao ; 
	
	@Mock
	private LookupDataService lookupDataService ; 
	
	@Before
	public void setup() {
		weightingsBasedPersonTargetMatcher = new WeightingsBasedPersonTargetMatcher(watchlistSearchDao,lookupDataService);
		
	}	
	
	@Test
	public void getMatches_shouldReturn_matches_AboveThreshold(){
		DateTime birthDate = new DateTime(1982, 1, 1, 0, 0, 0, 0);
		TargetItem traveller = new TargetItem(); 
		traveller.setForename("srikanth"); 
		traveller.setLastName("gone");
		traveller.setGender("M");
		traveller.setNationality("IND");
		traveller.setCountryOfBirth("IND");
		traveller.setDocType("P");
		traveller.setDocNo("12345678");
		traveller.setBirthDate(birthDate.toGregorianCalendar());
		
		TargetItem travellerToGetCandidates = new TargetItem(); 
		travellerToGetCandidates.setForename("srikanth"); 
		travellerToGetCandidates.setLastName("gone");
		
		List<TargetItem> candidatesTargetItem = createTargetItems(traveller); 
		
		// given 
		TargetMatchRequest targetMatchRequest = new TargetMatchRequest.TargetMatchRequestBuilder().targetItemToBeMatched(traveller).build();
		WatchlistWeightings expectedWatchlistWeightings = new WatchlistWeightings();
		expectedWatchlistWeightings.setFullName(60D);
		expectedWatchlistWeightings.setBirthDate(15D);
		expectedWatchlistWeightings.setGender(2.5D);
		expectedWatchlistWeightings.setNationality(2.5D);
		expectedWatchlistWeightings.setBirthCountry(2.5D);
		expectedWatchlistWeightings.setDocType(2.5D);
		expectedWatchlistWeightings.setDocNumber(15D);

		when(watchlistSearchDao.searchAll(eq(travellerToGetCandidates),any(TableActionCommand.class),any(Locale.class))).thenReturn(candidatesTargetItem); 
		when(lookupDataService.getWatchlistWeightings(WeightingType.PERSON)).thenReturn(expectedWatchlistWeightings);
		
		// When 
		List<TargetItem> actualTargetItems = weightingsBasedPersonTargetMatcher.getMatches(targetMatchRequest); 
		// Then 
		assertThat(actualTargetItems.size(), is(equalTo(3)));
		int index = 0 ; 
		for(TargetItem targetItem : actualTargetItems)
		{
			if( index == 0)
			{
				assertThat(targetItem.getMatchScore(), is(equalTo(75D)));
			}else if( index == 1){
				assertThat(targetItem.getMatchScore(), is(equalTo(80D)));
			}else if( index  == 2){
				assertThat(targetItem.getMatchScore(), is(equalTo(65D)));
			}
			index ++;
			assertThat(targetItem.getMatchType(), is(equalTo(AlertMatchTypes.PERSON_MATCH)));
		}
	}

	private List<TargetItem> createTargetItems(final TargetItem traveller) {
		List<TargetItem> candidatesTargetItem = new ArrayList<TargetItem>(); 
		
		for( int i=0;  i < 3 ; i ++)
		{
			TargetItem targetItem = new TargetItem(); 
			Calendar birthDate = null;
			String gender = null;
			String nationality = null;
			String countryOfBirth = null;
			String docType = null;
			String docNo = null;
			
			// More than threshold.
			if(i == 0) {
				birthDate = traveller.getBirthDate();
				gender = "F";
				nationality = "GBR";
				countryOfBirth = "GBR";
				docType="O";
				docNo="2345678";
			}
			
			// More than threshold
			if(i == 1) {
				birthDate = new DateTime(1983, 2, 2, 0, 0, 0, 0).toGregorianCalendar();
				gender = traveller.getGender();
				nationality = "GBR";
				countryOfBirth = "GBR";
				docType = traveller.getDocType();
				docNo = traveller.getDocNo();
			}

			// Less than threshold
			if(i == 2) {
				birthDate = new DateTime(1982, 2, 2, 0, 0, 0, 0).toGregorianCalendar();
				gender = "F";
				nationality = traveller.getNationality();
				countryOfBirth = "GBR";
				docType = traveller.getDocType();
				docNo = "4567889";
			}
			
			targetItem.setForename(traveller.getForename()); 
			targetItem.setLastName(traveller.getLastName());
			targetItem.setGender(gender);
			targetItem.setNationality(nationality);
			targetItem.setCountryOfBirth(countryOfBirth);
			targetItem.setDocType(docType);
			targetItem.setDocNo(docNo);
			targetItem.setBirthDate(birthDate);
			candidatesTargetItem.add(targetItem);
		}
		return candidatesTargetItem;
	}
	
}
