package abc.xyz.pts.common.referral.dao;


import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import abc.xyz.pts.bcs.common.dto.Traveller;
import abc.xyz.pts.bcs.common.irisk.dao.TravellerDAO;
import abc.xyz.pts.bcs.common.irisk.dto.WatchListRequest;
import abc.xyz.pts.bcs.common.referral.dao.ReferralDAO;
import abc.xyz.pts.bcs.common.referral.dao.ReferralLogDAO;
import abc.xyz.pts.bcs.common.referral.dao.WatchListRequestDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:DataSourceTest.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional

public class DAOTest{
	private static final Logger log = Logger.getLogger(DAOTest.class);
	@Autowired
	private JdbcDaoSupport daoSupport;
	@Autowired
	TravellerDAO  travellerDAO;
	@Autowired
	WatchListRequestDAO  watchListRequestDAO;
	@Autowired
	ReferralLogDAO referralLogDAO;
	@Autowired
	ReferralDAO referralDAO;
	
	@Test
	public void testWatchListRequestDAOInsertAndUpdate() 
	{
		log.debug("within the testWatchListRequestDAOInsertAndUpdate( ) ") ; 
		int startRowCount = daoSupport.getJdbcTemplate().queryForInt("select count(*) as totalCount from watch_list_requests") ; 
		
		// Insert and Select  Test 
		createWatchListRequest();
		int  pk = daoSupport.getJdbcTemplate().queryForInt("select max(id)  from watch_list_requests");
		
		//Update Test 
		WatchListRequest watchListRequestOut = watchListRequestDAO.findByMessageId(Long.valueOf(pk));
		watchListRequestOut.setResponseStatus("SUCCESS");
		Calendar responseDatetime = getTodayDate();
		watchListRequestOut.setUpdateVersionNo(1L); 
		watchListRequestOut.setResponseDatetime(responseDatetime);
		watchListRequestDAO.update(watchListRequestOut);
		watchListRequestOut = watchListRequestDAO.findByMessageId(Long.valueOf(pk));
		assertEquals("SUCCESS", watchListRequestOut.getResponseStatus());

		// Ensure Only Updated has taken place ie., No rows are added  
		int endRowCount = daoSupport.getJdbcTemplate().queryForInt("select count(*) as totalCount from watch_list_requests") ; 
		Assert.assertEquals(1, endRowCount- startRowCount); 
	}

	private WatchListRequest  createWatchListRequest()
	{
		WatchListRequest watchListRequest = new WatchListRequest();
		watchListRequest.setTraId(getTravellerId() ) ; 
		watchListRequest.setForename("LAKSHMI");
		watchListRequest.setLastName("REDDY");
		watchListRequest.setBirthCntryCode("GBR");
		watchListRequest.setBirthDate(getDob());
		watchListRequest.setBirthPlace("LONDON");
		watchListRequest.setDocIssueCntryCode("GBR");
		watchListRequest.setDocNo("12345");
		watchListRequest.setDocType("P");
		watchListRequest.setGender("M");
		watchListRequest.setNationality("GBR");
		watchListRequest.setRequestDatetime(getTodayDate());
		watchListRequest.setResponseDatetime(getTodayDate()) ; 
		watchListRequest.setResponseStatus("SUCCESS");
		watchListRequest.setTravellerDataSource("API");
		watchListRequest.setWatchListSource("WI");

		watchListRequestDAO.insert(watchListRequest);    	
		
		return watchListRequest ;
	}

	private Long getTravellerId()
	{
		//Get Traveller
		Long id = null;
		Traveller traveller = referralDAO.findTravellerWithoutReferrals()  ;  	
		if(traveller != null ){
			id = traveller.getId() ; 
		}
		return id;
	}

	private Calendar getTodayDate()
	{
		return Calendar.getInstance();
	}

	private Calendar getDob() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1971);
		cal.set(Calendar.MONTH, 9);
		cal.set(Calendar.DATE, 15);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}
}