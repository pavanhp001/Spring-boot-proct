package abc.xyz.pts.bcs.common.business.lookup.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import abc.xyz.pts.bcs.common.business.lookup.LookupDataService;
import abc.xyz.pts.bcs.common.enums.WeightingType;
import abc.xyz.pts.bcs.common.util.WebConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-wi-test.xml" })
@TransactionConfiguration(defaultRollback = true)
public class LookupDataServiceImplTest extends AbstractJUnit4SpringContextTests{
	@Autowired
	private LookupDataService lookupDataService;

	@Test
	public void testGetNationalityList() throws Exception {
		lookupDataService.getNationalityList(WebConstants.BUNDLE_BASE_NAME);
		//List<NationalityBean> nationalityList = lookupDataService.getNationalityList(WebConstants.BUNDLE_BASE_NAME);
		//Assert.assertNotNull(nationalityList);
	}
	
	
	@Test
	public void testGetFlightStatusList() throws Exception {
		lookupDataService.getFlightStatusList(WebConstants.BUNDLE_BASE_NAME);
		//List<FlightStatusBean> flightStatusList = lookupDataService.getFlightStatusList(WebConstants.BUNDLE_BASE_NAME);
		//Assert.assertNotNull(flightStatusList);
	}
	
	@Test
	public void testGetGenderList() throws Exception {
		lookupDataService.getGenderList(WebConstants.BUNDLE_BASE_NAME);
		//List<GenderBean> genderList = lookupDataService.getGenderList(WebConstants.BUNDLE_BASE_NAME);
		//Assert.assertNotNull(genderList);
	}

	@Test
	public void testGetWatchlistWeightings() throws Exception {
		lookupDataService.getWatchlistWeightings(WeightingType.DOCUMENT );
		//WatchlistWeightings watchlistWeightings = lookupDataService.getWatchlistWeightings();
		//Assert.assertNotNull(watchlistWeightings);
	}
}
