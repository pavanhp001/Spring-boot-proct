package abc.xyz.pts.bcs.wi.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import abc.xyz.pts.bcs.common.web.command.TableActionCommand;
import abc.xyz.pts.bcs.wi.dao.impl.ListTargetItemsWrapperImpl;
import abc.xyz.pts.bcs.wi.dto.RecommendedAction;
import abc.xyz.pts.bcs.wi.dto.SeverityReason;
import abc.xyz.pts.bcs.wi.dto.TargetItem;
import abc.xyz.pts.bcs.wi.dto.WatchListName;
import abc.xyz.pts.bcs.wi.iir.exception.IIRConnectException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchInvalidDataFormatException;
import abc.xyz.pts.bcs.wi.iir.search.IIRSearch;
import abc.xyz.pts.bcs.wi.iir.search.IIRSearchRequest;
import abc.xyz.pts.bcs.wi.iir.search.IIRSearchResponse;

public class ListTargetItemsWrapperTest extends TestCase  {
	
    private Mockery context;
    
    public void setUp() {
        context = new Mockery();
    }
    
	/**
	 * Test that the an IIR Search is handled properly by the wrapper when
	 * the first name is received from the UI.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testListTargetItemsWrapperIIRSearchForename() throws Exception {
		
		/*
		 * Create an instance of TargetItemList and populate it with the necessary
		 * properties for the test case
		 */		
		TargetItem targetItemForename = new TargetItem();
		targetItemForename.setForename("mohamed");
		
		/*
		 * Set the IIR Search property to be a mock IIR search
		 * Call the method under test targetItemList created above
		 */
		List<TargetItem> targetItemListForename = mockSearch(targetItemForename);

		/*
		 * Verify the contents of the TargetItemList are as expected
		 */
		// not elegant but this needs to be revisited - see previous version in SVN
		assertTrue(targetItemListForename.size() > 0);
	}
		
	
	/**
	 * Get IIR Search Results using the Mock Search object
	 * 
	 * @param targetItemList
	 * @return
	 * @throws IIRSearchException
	 * @throws IIRConnectException
	 * @throws IIRSearchInvalidDataFormatException
	 */
	private List<TargetItem> mockSearch(final TargetItem targetItem) 
		throws IIRSearchException, 
				IIRConnectException, 
				IIRSearchInvalidDataFormatException {
		
		IIRSearch mockIirSearch = new MockIIRSearchImpl();
	
		final PrRecommendedActionDao prRecommendedActionDao = context.mock(PrRecommendedActionDao.class);
		final PrSeverityReasonDao prSeverityReasonDao = context.mock(PrSeverityReasonDao.class);
		final PrWatchListNameDao prWatchListNameDao = context.mock(PrWatchListNameDao.class);
		
		ListTargetItemsWrapperImpl listTargetItemsWrapper = new ListTargetItemsWrapperImpl();
		listTargetItemsWrapper.setIirSearch(mockIirSearch);
		listTargetItemsWrapper.setPrRecommendedActionDao(prRecommendedActionDao);
		listTargetItemsWrapper.setPrSeverityReasonDao(prSeverityReasonDao);
		listTargetItemsWrapper.setPrWatchListNameDao(prWatchListNameDao);
		listTargetItemsWrapper.setIirEnabledFlag(true);
		
        context.checking(new Expectations() {
            {
            oneOf(prRecommendedActionDao).findAllRecommendedAction(null);
            will(returnValue(new ArrayList<RecommendedAction>()));
            
            oneOf(prSeverityReasonDao).findAllSeverityReason(null);
            will(returnValue(new ArrayList<SeverityReason>()));
            
            oneOf(prWatchListNameDao).findAllWatchListNames(null);
            will(returnValue(new ArrayList<WatchListName>()));            
            }
        });

		return listTargetItemsWrapper.listTargetItemsWrapper(targetItem, new TableActionCommand());
	}
	
	
	/**
	 * Mock the IIR Search Impl class.
	 * 
	 * @author gerard.mchale
	 */
	private class MockIIRSearchImpl implements IIRSearch {
		
		/**
		 * Return a mock IIRSearchResponse object without accessing the IIR 
		 * database
		 * 
		 * @param req
		 * 			The search criteria used for the search
		 * @return	MockIIRSearchImpl
		 */
		public MockIIRSearchResponseImpl search(final IIRSearchRequest req) {
			
			MockIIRSearchResponseImpl mockIirSearchResponse = new MockIIRSearchResponseImpl();
			mockIirSearchResponse.getList();
			return mockIirSearchResponse;
		}
	}
	
    
	
	/**
	 * Create a Mock IIRSearchResponseImpl object that can be injected into the function
	 * under test
	 * 
	 * @author gerard.mchale
	 *
	 */
	private class MockIIRSearchResponseImpl implements IIRSearchResponse 
	{
		private List<TargetItem> list = new ArrayList<TargetItem>();
		
		@Override
		public List<TargetItem> getTargetList() 
		{
			setList();
			setList(getList());
			return list;
		}
		
		private void setList(final List<TargetItem> list) 
		{	
			this.list.addAll(list);
		}
		
		private void setList() 
		{
		    TargetItem ti = new TargetItem();
		    ti.setId(264L);
		    ti.setForename("MOHAMED");
		    ti.setLastName("RAJI");
		    ti.setDocNo("IJ0NS1GT");
		    ti.setGender("M");
		    ti.setDocType("P");
		    ti.setBirthDate(Calendar.getInstance());
		    ti.setMatchScore(58d);
		    
		    list.add(ti);
		}
		
		private List<TargetItem> getList() 
		{	
			return list;
		}
	}
}
