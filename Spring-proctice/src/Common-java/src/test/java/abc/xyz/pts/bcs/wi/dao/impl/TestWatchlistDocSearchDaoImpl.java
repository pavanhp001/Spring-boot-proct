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
package abc.xyz.pts.bcs.wi.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import abc.xyz.pts.bcs.common.business.lookup.LookupDataService;
import abc.xyz.pts.bcs.common.dao.support.CustomRowMapper;
import abc.xyz.pts.bcs.common.enums.WeightingType;
import abc.xyz.pts.bcs.wi.dto.TargetItem;
import abc.xyz.pts.bcs.wi.dto.WatchlistWeightings;

/**
 * @author ryattapu
 *
 */
public class TestWatchlistDocSearchDaoImpl extends TestCase {
	
    private final Mockery context = new JUnit4Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    
    private LookupDataService lookupDataService;
    private WatchlistDocSearchDaoImpl watchlistDocSearchDao;
    private CustomRowMapper<TargetItem> rowMapper = new CustomRowMapper<TargetItem>(TargetItem.class);
    private JdbcTemplate jdbcTemplate;
    private JdbcDaoSupport daoSupport;

	
    @Before
    public void setUp() throws Exception {
    	lookupDataService = context.mock(LookupDataService.class);
    	this.jdbcTemplate = context.mock(JdbcTemplate.class);
    	this.daoSupport = new JdbcDaoSupport() {
		};
        daoSupport.setJdbcTemplate(jdbcTemplate);
    	this.watchlistDocSearchDao = new WatchlistDocSearchDaoImpl();
        watchlistDocSearchDao.setDaoSupport(daoSupport);
    }

    @SuppressWarnings("unchecked")
	public void testCalculateTotalScore() throws Exception{
    	TargetItem targetItem = getTagetItem();
    	final WatchlistWeightings watchlistWeightings = new WatchlistWeightings();
    	watchlistWeightings.setFullName(10d);
    	watchlistWeightings.setBirthDate(10d);
    	watchlistWeightings.setGender(5d);
    	watchlistWeightings.setNationality(10d);
    	watchlistWeightings.setBirthCountry(10d);
    	watchlistWeightings.setDocNumber(50d);
    	watchlistWeightings.setDocType(5d);
    	
    	final List<TargetItem> matchItemsList = new ArrayList<TargetItem>();
    	matchItemsList.add(targetItem);
        watchlistDocSearchDao.setLookupDataService(lookupDataService);
        watchlistDocSearchDao.setDaoSupport(daoSupport);
        //Check for full match properties and by Date Of Birth
        context.checking(new Expectations() {
            {
                one(jdbcTemplate).query(with(any(String.class)), with(any(Object[].class)), with(any(CustomRowMapper.class)));
                will(returnValue(matchItemsList));
                one(lookupDataService).getWatchlistWeightings(WeightingType.DOCUMENT);
                will(returnValue(watchlistWeightings));
            }
        });      
        watchlistDocSearchDao.getMatches(targetItem, null);
        assertEquals(100d, matchItemsList.get(0).getMatchScore());
        
        //Check for full match properties and Date Of Birth Value in Range
        TargetItem matchItem = getTagetItem();
        matchItem.getBirthDate().add(Calendar.MONTH, 1);
        targetItem.setBirthDate(null);
        matchItemsList.clear();
        matchItemsList.add(targetItem);        
        
        context.checking(new Expectations() {
            {
                one(jdbcTemplate).query(with(any(String.class)), with(any(Object[].class)), with(any(CustomRowMapper.class)));
                will(returnValue(matchItemsList));
                one(lookupDataService).getWatchlistWeightings(WeightingType.DOCUMENT);
                will(returnValue(watchlistWeightings));
            }
        });      
        watchlistDocSearchDao.getMatches(matchItem, null);
        assertEquals(100d, matchItemsList.get(0).getMatchScore());
        
        context.assertIsSatisfied();
    }
    
    private Calendar getDateOfBirth(final int year, final int month, final int date){
        Calendar dateCal = Calendar.getInstance();
        dateCal.set(Calendar.YEAR, year);
        dateCal.set(Calendar.MONTH, month);
        dateCal.set(Calendar.DATE, date);
        dateCal.set(Calendar.HOUR_OF_DAY, 0);
        dateCal.set(Calendar.MINUTE, 0);
        dateCal.set(Calendar.SECOND, 0);
        dateCal.set(Calendar.MILLISECOND, 0);
        return dateCal;
    }
    
    private TargetItem getTagetItem(){
    	TargetItem targetItem = new TargetItem();
    	targetItem.setBirthDate(getDateOfBirth(2000,1,1));
    	targetItem.setBirthDateFrom(getDateOfBirth(2000,1,1));
    	targetItem.setBirthDateTo(getDateOfBirth(2000,3,1));
    	targetItem.setForename("REDDY");
    	targetItem.setLastName("YATTAPU");
    	targetItem.setNationality("GBR");
    	targetItem.setDocType("P");
    	targetItem.setDocNo("12345");
    	targetItem.setCountryOfBirth("IND");
    	targetItem.setGender("M");
    	targetItem.setMatchScore(0.0d);
    	return targetItem;
    }
}
