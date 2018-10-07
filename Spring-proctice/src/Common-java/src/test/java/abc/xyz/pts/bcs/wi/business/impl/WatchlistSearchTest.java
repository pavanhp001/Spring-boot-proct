package abc.xyz.pts.bcs.wi.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.springframework.dao.DataAccessException;

import ssa.ssautil.SSAException;
import abc.xyz.pts.bcs.common.irisk.enums.AlertMatchTypes;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;
import abc.xyz.pts.bcs.irisk.mvo.wiinterface.TargetSearchErrors;
import abc.xyz.pts.bcs.irisk.mvo.wiinterface.WiErrorType;
import abc.xyz.pts.bcs.wi.dao.ListTargetItemsWrapper;
import abc.xyz.pts.bcs.wi.dao.WatchlistDocSearchDao;
import abc.xyz.pts.bcs.wi.dto.TargetItem;
import abc.xyz.pts.bcs.wi.dto.TargetSearchResults;
import abc.xyz.pts.bcs.wi.iir.exception.IIRConnectException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchInvalidDataFormatException;
import abc.xyz.pts.bcs.wi.iir.search.IIRSearch;

public class WatchlistSearchTest extends TestCase
{   
    private Mockery context;
    private WatchlistSearchImpl watchlistSearch;
    private WatchlistDocSearchDao watchlistDocSearchDao; 
    private ListTargetItemsWrapper listTargetItemsWrapper;
    private IIRSearch iirSearch;
    
    private TargetItem ti_1_P;
    private TargetItem ti_1_D;
    private TargetItem ti_11_P;
    private TargetItem ti_11_D;
    private TargetItem ti_2_P;
    private TargetItem ti_2_D;
    private TargetItem ti_22_P;
    private TargetItem ti_22_D;
    private TargetItem ti_3_P;
    private TargetItem ti_3_D;
    private TargetItem ti_33_P;
    private TargetItem ti_33_D;
    private TargetItem ti_4_P;
    private TargetItem ti_4_D;
    private TargetItem ti_5_P;
    private TargetItem ti_5_D;    
    private TargetItem ti_6_P;
    private TargetItem ti_6_D; 
    
    @Override
    public void setUp()
    {
        context = new Mockery();
        watchlistDocSearchDao = context.mock(WatchlistDocSearchDao.class);
        listTargetItemsWrapper = context.mock(ListTargetItemsWrapper.class);
        iirSearch =  context.mock(IIRSearch.class);
        watchlistSearch = new WatchlistSearchImpl();
        watchlistSearch.setWatchlistDocSearchDao(watchlistDocSearchDao);
        watchlistSearch.setListTargetItemsWrapper(listTargetItemsWrapper);
        watchlistSearch.setThresholdDocSearch(40);
        watchlistSearch.setThresholdIIRSearch(40);        
        
        ti_1_P = new TargetItem();
        ti_1_P.setId(1L);
        ti_1_P.setMatchScore(50d);
        ti_1_P.setMatchType(AlertMatchTypes.PERSON_MATCH);

        ti_1_D = new TargetItem();
        ti_1_D.setId(1L);
        ti_1_D.setMatchScore(50d);
        ti_1_D.setMatchType(AlertMatchTypes.DOCUMENT_MATCH);
        
        ti_11_P = new TargetItem();
        ti_11_P.setId(1L);
        ti_11_P.setMatchScore(51d);
        ti_11_P.setMatchType(AlertMatchTypes.PERSON_MATCH);

        ti_11_D = new TargetItem();
        ti_11_D.setId(1L);
        ti_11_D.setMatchScore(51d);
        ti_11_D.setMatchType(AlertMatchTypes.DOCUMENT_MATCH);
        
        ti_2_P = new TargetItem();
        ti_2_P.setId(2L);
        ti_2_P.setMatchScore(75d);
        ti_2_P.setMatchType(AlertMatchTypes.PERSON_MATCH);

        ti_2_D = new TargetItem();
        ti_2_D.setId(2L);
        ti_2_D.setMatchScore(75d);
        ti_2_D.setMatchType(AlertMatchTypes.DOCUMENT_MATCH);
        
        ti_22_P = new TargetItem();
        ti_22_P.setId(2L);
        ti_22_P.setMatchScore(45d);
        ti_22_P.setMatchType(AlertMatchTypes.PERSON_MATCH);

        ti_22_D = new TargetItem();
        ti_22_D.setId(2L);
        ti_22_D.setMatchScore(45d);
        ti_22_D.setMatchType(AlertMatchTypes.DOCUMENT_MATCH);

        ti_3_P = new TargetItem();
        ti_3_P.setId(3L);
        ti_3_P.setMatchScore(100d);
        ti_3_P.setMatchType(AlertMatchTypes.PERSON_MATCH);
        
        ti_3_D = new TargetItem();
        ti_3_D.setId(3L);
        ti_3_D.setMatchScore(100d);
        ti_3_D.setMatchType(AlertMatchTypes.DOCUMENT_MATCH);

        ti_33_P = new TargetItem();
        ti_33_P.setId(3L);
        ti_33_P.setMatchScore(100d);
        ti_33_P.setMatchType(AlertMatchTypes.PERSON_MATCH);

        ti_33_D = new TargetItem();
        ti_33_D.setId(3L);
        ti_33_D.setMatchScore(100d);
        ti_33_D.setMatchType(AlertMatchTypes.DOCUMENT_MATCH);
        
        ti_4_P = new TargetItem();
        ti_4_P.setId(4L);
        ti_4_P.setMatchScore(10d);
        ti_4_P.setMatchType(AlertMatchTypes.PERSON_MATCH);

        ti_4_D = new TargetItem();
        ti_4_D.setId(4L);
        ti_4_D.setMatchScore(10d);
        ti_4_D.setMatchType(AlertMatchTypes.DOCUMENT_MATCH);
        
        ti_5_P = new TargetItem();
        ti_5_P.setId(4L);
        ti_5_P.setMatchScore(60d);
        ti_5_P.setMatchType(AlertMatchTypes.PERSON_MATCH);

        ti_5_D = new TargetItem();
        ti_5_D.setId(4L);
        ti_5_D.setMatchScore(70d);
        ti_5_D.setMatchType(AlertMatchTypes.DOCUMENT_MATCH);
        
        ti_6_P = new TargetItem();
        ti_6_P.setId(4L);
        ti_6_P.setMatchScore(75d);
        ti_6_P.setMatchType(AlertMatchTypes.PERSON_MATCH);

        ti_6_D = new TargetItem();
        ti_6_D.setId(4L);
        ti_6_D.setMatchScore(65d);
        ti_6_D.setMatchType(AlertMatchTypes.DOCUMENT_MATCH);
    }
    
    private boolean isMatch(final List<TargetItem> expected, final List<TargetItem>actual)
    {
        if (expected.size() != actual.size())
            return false;
        
        for (TargetItem tiExpected : expected)
        {
            boolean foundMatch = false;
            for (TargetItem tiActual : actual)
            {
                if ( tiActual.getId().longValue() == tiExpected.getId().longValue() 
                  && tiActual.getMatchScore().doubleValue() == tiExpected.getMatchScore().doubleValue())
                {
                    foundMatch = true;
                    break;
                }
            }
            
            if (foundMatch == false)
                return false;
        }
        
        return true;
    }
    
    public void testNoMatches() throws Exception
    {
        watchlistSearch.setThresholdDocSearch(40);
        watchlistSearch.setThresholdIIRSearch(40);
        
        final List<TargetItem> tiDocSearch = new ArrayList<TargetItem>();
        final List<TargetItem> expectedList = new ArrayList<TargetItem>();
        
        
        context.checking(new Expectations() {
            {
                one(watchlistDocSearchDao).getMatches(with(any(TargetItem.class)), with(any(Locale.class)));
                will(returnValue(tiDocSearch));
                
                one(listTargetItemsWrapper).listTargetItemsWrapper(
                        with(any(TargetItem.class)), 
                        with(any(TableActionCommand.class)), 
                        with(any(Boolean.class)), with(any(Locale.class))
                    );
                will(returnValue(tiDocSearch));
            }
        });
        
        TargetSearchResults resutls = watchlistSearch.getMatches(new TargetItem(), new TableActionCommand(), Locale.ENGLISH);
        List<TargetItem>tiList = resutls.getTargetMatches();
        
        assertNotNull(tiList);
        assertTrue(tiList.size() == expectedList.size());
        assertTrue(isMatch(expectedList, tiList));
    }

    public void testNoMatchFromIIR()  throws Exception
    {
        watchlistSearch.setThresholdDocSearch(40);
        watchlistSearch.setThresholdIIRSearch(40);
        
        final List<TargetItem> tiIIRSearch = new ArrayList<TargetItem>();
        
        final List<TargetItem> tiDocSearch = new ArrayList<TargetItem>();
        tiDocSearch.add(ti_1_D);
        tiDocSearch.add(ti_2_D);
        tiDocSearch.add(ti_3_D);
        
        final List<TargetItem> expectedList = new ArrayList<TargetItem>();
        expectedList.add(ti_1_D);
        expectedList.add(ti_2_D);
        expectedList.add(ti_3_D); 
                
        context.checking(new Expectations() {
            {
                one(watchlistDocSearchDao).getMatches(with(any(TargetItem.class)), with(any(Locale.class)));
                will(returnValue(tiIIRSearch));
                
                one(listTargetItemsWrapper).listTargetItemsWrapper(
                        with(any(TargetItem.class)), 
                        with(any(TableActionCommand.class)), 
                        with(any(Boolean.class)), with(any(Locale.class))
                    );
                will(returnValue(tiDocSearch));
            }
        });
        
        TargetSearchResults resutls = watchlistSearch.getMatches(new TargetItem(), new TableActionCommand(), Locale.ENGLISH);
        List<TargetItem>tiList = resutls.getTargetMatches();
        assertNotNull(tiList);
        assertTrue(tiList.size() == expectedList.size());
        assertTrue(isMatch(expectedList, tiList));
    }
    
    public void testMatchesFromBoth() throws Exception
    {
        watchlistSearch.setThresholdDocSearch(40);
        watchlistSearch.setThresholdIIRSearch(40);
        
        final List<TargetItem> tiIIRSearch = new ArrayList<TargetItem>();
        tiIIRSearch.add(ti_11_P);
        tiIIRSearch.add(ti_22_P);
        tiIIRSearch.add(ti_33_P);
        
        final List<TargetItem> tiDocSearch = new ArrayList<TargetItem>();
        tiDocSearch.add(ti_1_D);
        tiDocSearch.add(ti_2_D);
        tiDocSearch.add(ti_3_D);
        tiDocSearch.add(ti_4_D);

        final List<TargetItem> expectedList = new ArrayList<TargetItem>();
        expectedList.add(ti_11_P);
        expectedList.add(ti_2_D);
        expectedList.add(ti_33_P);
        
        context.checking(new Expectations() {
            {
                one(watchlistDocSearchDao).getMatches(with(any(TargetItem.class)), with(any(Locale.class)));
                will(returnValue(tiIIRSearch));
                
                one(listTargetItemsWrapper).listTargetItemsWrapper(
                        with(any(TargetItem.class)), 
                        with(any(TableActionCommand.class)), 
                        with(any(Boolean.class)), with(any(Locale.class))
                    );
                will(returnValue(tiDocSearch));
            }
        });
        
        TargetSearchResults resutls = watchlistSearch.getMatches(new TargetItem(), new TableActionCommand(), Locale.ENGLISH);
        List<TargetItem>tiList = resutls.getTargetMatches();
        assertNotNull(tiList);
        assertTrue(tiList.size() == expectedList.size());
        assertTrue(isMatch(expectedList, tiList));
    }
    
    public void testNoMatchFromDocSearch() throws Exception
    {
        watchlistSearch.setThresholdDocSearch(40);
        watchlistSearch.setThresholdIIRSearch(40);
        
        final List<TargetItem> tiIIRSearch = new ArrayList<TargetItem>();
        tiIIRSearch.add(ti_11_P);
        tiIIRSearch.add(ti_22_P);
        tiIIRSearch.add(ti_33_P);
        
        final List<TargetItem> tiDocSearch = new ArrayList<TargetItem>();


        final List<TargetItem> expectedList = new ArrayList<TargetItem>();
        expectedList.add(ti_11_P);
        expectedList.add(ti_22_P);
        expectedList.add(ti_33_P);

        

        context.checking(new Expectations() {
            {
                one(watchlistDocSearchDao).getMatches(with(any(TargetItem.class)), with(any(Locale.class)));
                will(returnValue(tiIIRSearch));
                
                one(listTargetItemsWrapper).listTargetItemsWrapper(
                        with(any(TargetItem.class)), 
                        with(any(TableActionCommand.class)), 
                        with(any(Boolean.class)), with(any(Locale.class))
                    );
                will(returnValue(tiDocSearch));
            }
        });

        
        TargetSearchResults resutls = watchlistSearch.getMatches(new TargetItem(), new TableActionCommand(), Locale.ENGLISH);
        List<TargetItem>tiList = resutls.getTargetMatches();
        assertNotNull(tiList);
        assertTrue(tiList.size() == expectedList.size());
        assertTrue(isMatch(expectedList, tiList));
    }
    
    public void testDocSearchThreshold() throws Exception
    {
        watchlistSearch.setThresholdDocSearch(101);
        watchlistSearch.setThresholdIIRSearch(40);
        
        final List<TargetItem> tiIIRSearch = new ArrayList<TargetItem>();
        tiIIRSearch.add(ti_11_P);
        tiIIRSearch.add(ti_22_P);
        tiIIRSearch.add(ti_33_P);
        
        final List<TargetItem> tiDocSearch = new ArrayList<TargetItem>();
        tiDocSearch.add(ti_1_D);
        tiDocSearch.add(ti_2_D);
        tiDocSearch.add(ti_3_D);
        tiDocSearch.add(ti_4_D);

        final List<TargetItem> expectedList = new ArrayList<TargetItem>();
        expectedList.add(ti_11_P);
        expectedList.add(ti_22_P);
        expectedList.add(ti_33_P);
        
        context.checking(new Expectations() {
            {
                one(watchlistDocSearchDao).getMatches(with(any(TargetItem.class)), with(any(Locale.class)));
                will(returnValue(tiIIRSearch));
                
                one(listTargetItemsWrapper).listTargetItemsWrapper(
                        with(any(TargetItem.class)), 
                        with(any(TableActionCommand.class)), 
                        with(any(Boolean.class)), with(any(Locale.class))
                    );
                will(returnValue(tiDocSearch));
            }
        });
        
        TargetSearchResults resutls = watchlistSearch.getMatches(new TargetItem(), new TableActionCommand(), Locale.ENGLISH);
        List<TargetItem>tiList = resutls.getTargetMatches();
        assertNotNull(tiList);
        assertTrue(tiList.size() == expectedList.size());
        assertTrue(isMatch(expectedList, tiList));
    }
    
    public void testPersonSearchThreshold() throws Exception
    {
        watchlistSearch.setThresholdDocSearch(40);
        watchlistSearch.setThresholdIIRSearch(101); // all PersonSearch gets rejected
        
        final List<TargetItem> tiIIRSearch = new ArrayList<TargetItem>();
        tiIIRSearch.add(ti_11_P);
        tiIIRSearch.add(ti_22_P);
        tiIIRSearch.add(ti_33_P);
        
        final List<TargetItem> tiDocSearch = new ArrayList<TargetItem>();
        tiDocSearch.add(ti_1_D);
        tiDocSearch.add(ti_2_D);
        tiDocSearch.add(ti_3_D);
        tiDocSearch.add(ti_4_D);

        final List<TargetItem> expectedList = new ArrayList<TargetItem>();
        expectedList.add(ti_1_D);
        expectedList.add(ti_2_D);
        expectedList.add(ti_3_D);
        
        context.checking(new Expectations() {
            {
                one(watchlistDocSearchDao).getMatches(with(any(TargetItem.class)), with(any(Locale.class)));
                will(returnValue(tiIIRSearch));
                
                one(listTargetItemsWrapper).listTargetItemsWrapper(
                        with(any(TargetItem.class)), 
                        with(any(TableActionCommand.class)), 
                        with(any(Boolean.class)), with(any(Locale.class))
                    );
                will(returnValue(tiDocSearch));
            }
        });
        
        TargetSearchResults resutls = watchlistSearch.getMatches(new TargetItem(), new TableActionCommand(), Locale.ENGLISH);
        List<TargetItem>tiList = resutls.getTargetMatches();
        assertNotNull(tiList);
        assertTrue(tiList.size() == expectedList.size());
        assertTrue(isMatch(expectedList, tiList));
    }
    
    public void testThresholdRejectAll() throws Exception
    {
        watchlistSearch.setThresholdDocSearch(101);
        watchlistSearch.setThresholdIIRSearch(101); // all PersonSearch gets rejected
        
        final List<TargetItem> tiIIRSearch = new ArrayList<TargetItem>();
        tiIIRSearch.add(ti_11_P);
        tiIIRSearch.add(ti_22_P);
        tiIIRSearch.add(ti_33_P);
        
        final List<TargetItem> tiDocSearch = new ArrayList<TargetItem>();
        tiDocSearch.add(ti_1_D);
        tiDocSearch.add(ti_2_D);
        tiDocSearch.add(ti_3_D);
        tiDocSearch.add(ti_4_D);

        final List<TargetItem> expectedList = new ArrayList<TargetItem>();

        
        context.checking(new Expectations() {
            {
                one(watchlistDocSearchDao).getMatches(with(any(TargetItem.class)), with(any(Locale.class)));
                will(returnValue(tiIIRSearch));
                
                one(listTargetItemsWrapper).listTargetItemsWrapper(
                        with(any(TargetItem.class)), 
                        with(any(TableActionCommand.class)), 
                        with(any(Boolean.class)), with(any(Locale.class))
                    );
                will(returnValue(tiDocSearch));
            }
        });
        
        TargetSearchResults resutls = watchlistSearch.getMatches(new TargetItem(), new TableActionCommand(), Locale.ENGLISH);
        List<TargetItem>tiList = resutls.getTargetMatches();
        assertNotNull(tiList);
        assertTrue(tiList.size() == expectedList.size());
        assertTrue(isMatch(expectedList, tiList));
    }
    
    public void testThresholdEqual() throws Exception
    {
        watchlistSearch.setThresholdDocSearch(50);
        watchlistSearch.setThresholdIIRSearch(50);
        
        final List<TargetItem> tiIIRSearch = new ArrayList<TargetItem>();
        tiIIRSearch.add(ti_11_P);
        
        final List<TargetItem> tiDocSearch = new ArrayList<TargetItem>();
        tiDocSearch.add(ti_1_D);

        final List<TargetItem> expectedList = new ArrayList<TargetItem>();
        expectedList.add(ti_11_P);
        
        context.checking(new Expectations() {
            {
                one(watchlistDocSearchDao).getMatches(with(any(TargetItem.class)), with(any(Locale.class)));
                will(returnValue(tiIIRSearch));
                
                one(listTargetItemsWrapper).listTargetItemsWrapper(
                        with(any(TargetItem.class)), 
                        with(any(TableActionCommand.class)), 
                        with(any(Boolean.class)), with(any(Locale.class))
                    );
                will(returnValue(tiDocSearch));
            }
        });
        
        TargetSearchResults resutls = watchlistSearch.getMatches(new TargetItem(), new TableActionCommand(), Locale.ENGLISH);
        List<TargetItem>tiList = resutls.getTargetMatches();
        assertNotNull(tiList);
        assertTrue(tiList.size() == expectedList.size());
        assertTrue(isMatch(expectedList, tiList));
    }
    
    public void testThresholdNotEqual() throws Exception
    {
        watchlistSearch.setThresholdDocSearch(50);
        watchlistSearch.setThresholdIIRSearch(50); 
        
        final List<TargetItem> tiIIRSearch = new ArrayList<TargetItem>();
        tiIIRSearch.add(ti_22_P);
        
        final List<TargetItem> tiDocSearch = new ArrayList<TargetItem>();
        tiDocSearch.add(ti_2_D);

        final List<TargetItem> expectedList = new ArrayList<TargetItem>();
        expectedList.add(ti_2_P);
        
        context.checking(new Expectations() {
            {
                one(watchlistDocSearchDao).getMatches(with(any(TargetItem.class)), with(any(Locale.class)));
                will(returnValue(tiIIRSearch));
                
                one(listTargetItemsWrapper).listTargetItemsWrapper(
                        with(any(TargetItem.class)), 
                        with(any(TableActionCommand.class)), 
                        with(any(Boolean.class)), with(any(Locale.class))
                    );
                will(returnValue(tiDocSearch));
            }
        });
        
        TargetSearchResults resutls = watchlistSearch.getMatches(new TargetItem(), new TableActionCommand(), Locale.ENGLISH);
        List<TargetItem>tiList = resutls.getTargetMatches();
        assertNotNull(tiList);
        assertTrue(tiList.size() == expectedList.size());
        assertTrue(isMatch(expectedList, tiList));
        
    }
    
    public void testThresholdDocHigherThanPerson() throws Exception
    {
        watchlistSearch.setThresholdDocSearch(50);
        watchlistSearch.setThresholdIIRSearch(50);
        
        final List<TargetItem> tiIIRSearch = new ArrayList<TargetItem>();
        tiIIRSearch.add(ti_5_P);
        
        final List<TargetItem> tiDocSearch = new ArrayList<TargetItem>();
        tiDocSearch.add(ti_5_D);

        final List<TargetItem> expectedList = new ArrayList<TargetItem>();
        expectedList.add(ti_5_D);
        
        context.checking(new Expectations() {
            {
                one(watchlistDocSearchDao).getMatches(with(any(TargetItem.class)), with(any(Locale.class)));
                will(returnValue(tiIIRSearch));
                
                one(listTargetItemsWrapper).listTargetItemsWrapper(
                        with(any(TargetItem.class)), 
                        with(any(TableActionCommand.class)), 
                        with(any(Boolean.class)), with(any(Locale.class))
                    );
                will(returnValue(tiDocSearch));
            }
        });
        
        TargetSearchResults resutls = watchlistSearch.getMatches(new TargetItem(), new TableActionCommand(), Locale.ENGLISH);
        List<TargetItem>tiList = resutls.getTargetMatches();
        assertNotNull(tiList);
        assertTrue(tiList.size() == expectedList.size());
        assertTrue(isMatch(expectedList, tiList));
        
    }
    
    public void testThresholdPersonHigherThanDoc() throws Exception
    {
        watchlistSearch.setThresholdDocSearch(50);
        watchlistSearch.setThresholdIIRSearch(50); 
        
        final List<TargetItem> tiIIRSearch = new ArrayList<TargetItem>();
        tiIIRSearch.add(ti_6_P);
        
        final List<TargetItem> tiDocSearch = new ArrayList<TargetItem>();
        tiDocSearch.add(ti_6_D);

        final List<TargetItem> expectedList = new ArrayList<TargetItem>();
        expectedList.add(ti_6_P);
        
        context.checking(new Expectations() {
            {
                one(watchlistDocSearchDao).getMatches(with(any(TargetItem.class)), with(any(Locale.class)));
                will(returnValue(tiIIRSearch));
                
                one(listTargetItemsWrapper).listTargetItemsWrapper(
                        with(any(TargetItem.class)), 
                        with(any(TableActionCommand.class)), 
                        with(any(Boolean.class)), with(any(Locale.class))
                    );
                will(returnValue(tiDocSearch));
            }
        });
        
        TargetSearchResults resutls = watchlistSearch.getMatches(new TargetItem(), new TableActionCommand(), Locale.ENGLISH);
        List<TargetItem>tiList = resutls.getTargetMatches();
        assertNotNull(tiList);
        assertTrue(tiList.size() == expectedList.size());
        assertTrue(isMatch(expectedList, tiList));
        
    }
    
    @SuppressWarnings("serial")
	public void testGetMatches() 
    {
    	
        final List<TargetItem> tiIIRSearch = new ArrayList<TargetItem>();        
        final List<TargetItem> tiDocSearch = new ArrayList<TargetItem>();
    	TargetItem targetSearch = new TargetItem();
    	targetSearch.setForename("REDDY");
    	targetSearch.setLastName("YATTAPU");
        TargetSearchResults resutls = null;
        final SSAException saaExc = new SSAException() {
			
			@Override
			public boolean getFatal() {
				// TODO Auto-generated method stub
				return false;
			}
		};

		final IIRSearchException iirExe = new IIRSearchException("search", saaExc, "Search Failed");
		final IIRConnectException iirConn = new IIRConnectException(iirExe);
		final DataAccessException dataAccExc = new DataAccessException("Search Exception"){	
		};
		/*************************
		 * IIR SEARCH TEST CASES *
		 * ***********************
		 */
        //IIR Search Expected to throw IIRSearchException 
        try {
			context.checking(new Expectations() {
			    {
	                one(listTargetItemsWrapper).listTargetItemsWrapper(
	                        with(any(TargetItem.class)), 
	                        with(any(TableActionCommand.class)), 
	                        with(any(Boolean.class)), with(any(Locale.class))
	                    );					
	                will(throwException(iirExe));
			        
			        one(watchlistDocSearchDao).getMatches(with(any(TargetItem.class)), with(any(Locale.class)));
			        will(returnValue(tiIIRSearch));

			    }
			});
			
			watchlistSearch.setListTargetItemsWrapper(listTargetItemsWrapper);
	        resutls = watchlistSearch.getMatches(targetSearch, new TableActionCommand(), Locale.ENGLISH);
			assertNotNull(resutls);
			TargetSearchErrors errors = resutls.getErrors().get(0);
			assertEquals(WiErrorType.IIR_SEARCH_PROBLEM, errors.getErrorCode());
			assertEquals(WiErrorType.IIR_SEARCH, errors.getSearchType());	       
		} catch (Exception e) {
			e.printStackTrace();
		}

        //IIR Search Expected to throw IIRConnectException 
        try {
			context.checking(new Expectations() {
			    {
	                one(listTargetItemsWrapper).listTargetItemsWrapper(
	                        with(any(TargetItem.class)), 
	                        with(any(TableActionCommand.class)), 
	                        with(any(Boolean.class)), null
	                    );					
	                will(throwException(iirConn));
			        
			        one(watchlistDocSearchDao).getMatches(with(any(TargetItem.class)), with(any(Locale.class)));
			        will(returnValue(tiIIRSearch));

			    }
			});
			
			watchlistSearch.setListTargetItemsWrapper(listTargetItemsWrapper);
	        resutls = watchlistSearch.getMatches(targetSearch, new TableActionCommand());
			assertNotNull(resutls);
			TargetSearchErrors errors = resutls.getErrors().get(0);
			assertEquals(WiErrorType.IIR_CONN_TIME_OUT, errors.getErrorCode());
			assertEquals(WiErrorType.IIR_SEARCH, errors.getSearchType());
	       
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		//IIR Search Expected to throw IIRSearchInvalidDataFormatException 
        try {
			context.checking(new Expectations() {
			    {
	                one(listTargetItemsWrapper).listTargetItemsWrapper(
	                        with(any(TargetItem.class)), 
	                        with(any(TableActionCommand.class)), 
	                        with(any(Boolean.class)), with(any(Locale.class))
	                    );						
	                will(throwException(new IIRSearchInvalidDataFormatException(new Exception("Invalid Date Exception"))));			        
			        one(watchlistDocSearchDao).getMatches(with(any(TargetItem.class)), with(any(Locale.class)));
			        will(returnValue(tiIIRSearch));

			    }
			});
			
			watchlistSearch.setListTargetItemsWrapper(listTargetItemsWrapper);
	        resutls = watchlistSearch.getMatches(new TargetItem(), new TableActionCommand(), Locale.ENGLISH);
			assertNotNull(resutls);
			TargetSearchErrors errors = resutls.getErrors().get(0);
			assertEquals(WiErrorType.IIR_DATE_VALIDATION, errors.getErrorCode());
			assertEquals(WiErrorType.IIR_SEARCH, errors.getSearchType());
	       
		} catch (Exception e) {
			e.printStackTrace();
		}       
		
		//IIR Search Expected to throw Exception 
        try {
			context.checking(new Expectations() {
			    {
	                one(listTargetItemsWrapper).listTargetItemsWrapper(
	                        with(any(TargetItem.class)), 
	                        with(any(TableActionCommand.class)), 
	                        with(any(Boolean.class)), with(any(Locale.class))
	                    );					
	                will(throwException(new Exception("Search Exception")));
			        
			        one(watchlistDocSearchDao).getMatches(with(any(TargetItem.class)), with(any(Locale.class)));
			        will(returnValue(tiIIRSearch));

			    }
			});
			
			watchlistSearch.setListTargetItemsWrapper(listTargetItemsWrapper);
	        resutls = watchlistSearch.getMatches(targetSearch, new TableActionCommand(), Locale.ENGLISH);
			assertNotNull(resutls);
			TargetSearchErrors errors = resutls.getErrors().get(0);
			assertEquals(WiErrorType.IIR_SEARCH_PROBLEM, errors.getErrorCode());
			assertEquals(WiErrorType.IIR_SEARCH, errors.getSearchType());
	       
		} catch (Exception e) {
			e.printStackTrace();
		} 
		/*************************
		 * DOC SEARCH TEST CASES *
		 * ***********************
		 */
		
		//Doc Search Expected to throw DataAccessException 
        try {
			context.checking(new Expectations() {
			    {
	                one(listTargetItemsWrapper).listTargetItemsWrapper(
	                        with(any(TargetItem.class)), 
	                        with(any(TableActionCommand.class)), 
	                        with(any(Boolean.class)), with(any(Locale.class))
	                    );	
	                will(returnValue(tiDocSearch));	                
			        
			        one(watchlistDocSearchDao).getMatches(with(any(TargetItem.class)), with(any(Locale.class)));
	                will(throwException(dataAccExc));

			    }
			});
			
			watchlistSearch.setListTargetItemsWrapper(listTargetItemsWrapper);
	        resutls = watchlistSearch.getMatches(targetSearch, new TableActionCommand(), Locale.ENGLISH);
			assertNotNull(resutls);
			TargetSearchErrors errors = resutls.getErrors().get(0);
			assertEquals(WiErrorType.DOC_DATA_ACCESS_PROBLEM, errors.getErrorCode());
			assertEquals(WiErrorType.DOC_SEARCH, errors.getSearchType());
	       
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		//Doc Search Expected to throw Exception 
        try {
			context.checking(new Expectations() {
			    {
	                one(listTargetItemsWrapper).listTargetItemsWrapper(
	                        with(any(TargetItem.class)), 
	                        with(any(TableActionCommand.class)), 
	                        with(any(Boolean.class)), with(any(Locale.class))
	                    );	
	                will(returnValue(tiDocSearch));	                
			        
			        one(watchlistDocSearchDao).getMatches(with(any(TargetItem.class)), with(any(Locale.class)));
	                will(throwException(new Exception("Doc Search Problems")));

			    }
			});
			
			watchlistSearch.setListTargetItemsWrapper(listTargetItemsWrapper);
	        resutls = watchlistSearch.getMatches(targetSearch, new TableActionCommand(), Locale.ENGLISH);
			assertNotNull(resutls);
			TargetSearchErrors errors = resutls.getErrors().get(0);
			assertEquals(WiErrorType.DOC_SEARCH_PROBLEM, errors.getErrorCode());
			assertEquals(WiErrorType.DOC_SEARCH, errors.getSearchType());
	       
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
}
