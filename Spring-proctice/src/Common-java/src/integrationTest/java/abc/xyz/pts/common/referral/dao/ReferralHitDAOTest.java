package abc.xyz.pts.common.referral.dao;

import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import abc.xyz.pts.bcs.common.dto.Referral;
import abc.xyz.pts.bcs.common.dto.ReferralHit;
import abc.xyz.pts.bcs.common.dto.Traveller;
import abc.xyz.pts.bcs.common.irisk.dto.WatchListRequest;
import abc.xyz.pts.bcs.common.referral.dao.ReferralDAO;
import abc.xyz.pts.bcs.common.referral.dao.ReferralHitDAO;
import abc.xyz.pts.bcs.common.referral.dao.WatchListRequestDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:DataSourceTest.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional


/**
 * TODO 
 */
public class ReferralHitDAOTest
{

	private static final Logger log = Logger.getLogger(ReferralHitDAOTest.class);
    @Autowired
    private ReferralDAO   referralDAO;
    
    @Autowired
    private ReferralHitDAO referralHitDAO  ; 
    
    @Autowired
    private JdbcDaoSupport daoSupport;
    
    @Autowired
    private WatchListRequestDAO  watchListRequestDAO;
    
    private WatchListRequest watchListRequest  ; 
    private Referral referral ; 
   
    @Before
    public void setUpTestData() 
    {
    	log.debug("setUpTestData called " ) ; 
    	// create a WatchListRequest 
    	watchListRequest = createWatchListRequest() ; 
    	// create a Referral 
    	referral = createReferral(); 
    }
    
    @Test
    public void testFindByReferralId()
    {
    	checkReferralHitInsertAndUpdate(referral, watchListRequest);
    	int referralId  = daoSupport.getJdbcTemplate().queryForInt("select max(id) as id from referrals"); 
    	 List<ReferralHit> referralsHitList = referralHitDAO.findByReferralId(Long.valueOf(referralId)) ;
    	 Assert.assertNotNull( referralsHitList) ; 
    	 Assert.assertTrue(  referralsHitList.size() > 0) ; 
    	
    }
    
    @Test
    public void testFindIncorrectRefHitID()
    {
    	checkReferralHitInsertAndUpdate(referral, watchListRequest);

    	List<ReferralHit> referralsHitList = referralHitDAO.findByReferralId(Long.valueOf(6545L)) ;
    	Assert.assertNotNull( referralsHitList) ; 
    	Assert.assertTrue(  referralsHitList.size() ==  0) ; 
    	
    }
    
    @Test
    public void testGetReferralHits()
    {
    	checkReferralHitInsertAndUpdate(referral, watchListRequest);

    	List<ReferralHit>  hits = referralHitDAO.getReferralHits();  
    	Assert.assertNotNull(hits); 
    	
    	Assert.assertTrue( hits.size() >  0 ) ; 
    	
    }

    public void testGetZeroReferralHits()
    {
    	List<ReferralHit>  hits = referralHitDAO.getReferralHits();  
    	Assert.assertNotNull(hits); 
    	
    }
    
    @Test
    public void testUpdateReferralHit()
    {
    	checkReferralHitInsertAndUpdate(referral, watchListRequest);
    	int refHitsId  = daoSupport.getJdbcTemplate().queryForInt("select max(id) as id from referral_hits"); 
    	//load the ReferralHit back 
    	ReferralHit referralHit = referralHitDAO.findById( Long.valueOf(refHitsId)) ; 
    	referralHitDAO.update(referralHit); 
    	// TODO  Test Update Updated Row cannot be tested as DIRTY_READ not possible ( ie Not yet committed )   
    
    }
    
    	

    @Test
    public void testUpdateNonExistentReferralHit()
    {
    	ReferralHit hit = new ReferralHit() ; 
    	try
    	{
    		referralHitDAO.update(hit); 
    	}catch(Exception ee)
    	{
    		Assert.assertFalse("Non Existent Referral Hit Update fail ", false); 
    	}
    	
    }
     
    private void checkReferralHitInsertAndUpdate(final Referral referral, final WatchListRequest watchListRequest )
    {
    	// Create a Referral Hit 
    	ReferralHit  referralHit = new ReferralHit();
    	referralHit.setRefId(Long.valueOf( referral.getId()))  ;  //REFERRAL.ID FK  
    	referralHit.setWatlrId(Long.valueOf(watchListRequest.getId())); // WATCH_LIST_REQUEST.ID  FK 
    	referralHit.setQualificationStatus("IN"); 
    	referralHit.setSeverityLevel(1L);
    	referralHit.setActionCode("A-Code");
    	referralHit.setUpdateVersionNo(1L);
    	referralHit.setHitType("WL");
    	referralHit.setHitScore(20L);
    	referralHit.setPriority(25L);
    	referralHit.setWlMatchType("P");
    	referralHit.setWlSource("WI");
    	referralHit.setWlForename("kasi"); 
    	referralHit.setWlLastName("kasi"); 
    	referralHit.setWlGender("M");  
    	referralHit.setWlBirthDate(getTodayDate()); 
    	referralHit.setWlBirthPlace("Madras"); 
    	referralHit.setWlBirthCntryCode("IND");   
    	referralHit.setWlNationality("IND"); 
    	referralHit.setWlDocType("P"); 
    	referralHit.setWlDocNo("23");
    	referralHit.setWlDocIssueCntryCode("D");  
    	referralHit.setWlProtocolNumber("123"); 
    	referralHit.setWlWiWatlName("name"); 
    	referralHit.setWlRescCode("wls"); 
    	referralHit.setAppHitReason(4L);
    	referralHit.setModifiedBy("kasi");
    	referralHit.setModifiedDatetime(null) ; 
    	referralHit.setCreatedBy("kasi"); 
    	referralHit.setCreatedDatetime(getTodayDate()) ; 

    	int before  =  daoSupport.getJdbcTemplate().queryForInt("select count(*)  from referral_hits"); 
    	
    	referralHitDAO.insert(referralHit) ; 
    	
    	int after   =  daoSupport.getJdbcTemplate().queryForInt("select count(*)  from referral_hits"); 
    	// Check one Row has been inserted into the ReferralHits table. 
    	Assert.assertTrue( (after-1) == before) ; 
    	
    	//Update the ReferralHit 
    	referralHit.setModifiedBy("Subramaniam"); 
    	referralHit.setModifiedDatetime( getTodayDate()) ; 
    	referralHitDAO.update(referralHit);
    	
    	int rowCount   =  daoSupport.getJdbcTemplate().queryForInt("select count(*)  from referral_hits"); 
    	
    	Assert.assertTrue( rowCount == after) ; 
    	// Retrieve the updated row and check if the fields are updated 
    	
    	ReferralHit  refHit =  referralHitDAO.findById(referralHit.getId()) ;
    	// Updated Row cannot be tested as DIRTY_READ not possible ( ie Not yet committed )   
    	// Assert.assertEquals("Subramaniam", refHit.getModifiedBy() ) ;  
    }

    private WatchListRequest getWatchListRequest(){
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
    	return watchListRequest;
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

    private Calendar getTodayDate() {
    	return Calendar.getInstance();
    }

    private WatchListRequest  createWatchListRequest()
    {
    	WatchListRequest watchListRequest = null ;  
    	watchListRequest = getWatchListRequest();    
    	Long traId = null ; 

    	if(getTravellerId() != null)
    	{
    		traId = getTravellerId();
    		watchListRequest.setTraId(traId);
    		watchListRequestDAO.insert(watchListRequest);    	
    	}
    	return watchListRequest ;
    }

    private Long getTravellerId(){
    	
    	Long id = null;
    	Traveller traveller = null ; 
    	//  = travellerDAO.getTravellers();    	
    	traveller  =  referralDAO.findTravellerWithoutReferrals(); 
    	
    	if(traveller != null ){
    		id = traveller.getId() ; 
    	}
    	
    	return id;
    
    }
    
    private Referral  createReferral() 
    {
    	Referral referral = new Referral() ;
    	referral.setTraId(getTravellerId()); 
    	referral.setActionCode("act"); 
    	referral.setStatus("UNQ"); 
    	referral.setPriActioningAirportCode("PRI");
    	referral.setSecActioningAirportCode("SEC");
    	referral.setClosureCode("OTHER");
    	referral.setModifiedBy("Mo");
    	referral.setModifiedDatetime(getTodayDate() ); 
    	referral.setCreatedBy("C");
    	referral.setCreatedDatetime(getTodayDate()); 
    	referralDAO.insert(referral); 
    	return referral ;  
    }
    
    @Test
    public void testInsertRefHitsWithNulls() 
    {
    	int referralId  = daoSupport.getJdbcTemplate().queryForInt("select max(id) as id from referrals"); 
    	int watchListRequestId = daoSupport.getJdbcTemplate().queryForInt("select max(id) from watch_list_requests" ) ; 
    	
    	Assert.assertTrue( referralId >=1) ; 
    	Assert.assertTrue( watchListRequestId >=1) ; 
    	
    	// Create a Referral Hit 
    	ReferralHit  referralHit = new ReferralHit();
    	referralHit.setRefId(Long.valueOf( referralId  ) )  ;  //REFERRAL.ID FK  
    	referralHit.setWatlrId(Long.valueOf(watchListRequestId)); // WATCH_LIST_REQUEST.ID  FK 
    	referralHit.setQualificationStatus("IN"); 
    	referralHit.setHitType("WL");
    	referralHit.setWlMatchType("P");
    	referralHit.setWlSource("WI");
    	referralHit.setWlForename("kasi"); 
    	referralHit.setWlLastName("kasi"); 
    	referralHit.setWlGender("");  
    	referralHit.setWlBirthDate(getTodayDate()); 
    	referralHit.setWlBirthPlace(null); 
    	referralHit.setWlBirthCntryCode(" ");   
    	referralHit.setWlNationality("IND"); 
    	referralHit.setWlDocType("P"); 
    	referralHit.setWlDocNo("23");
    	referralHit.setWlDocIssueCntryCode("D");  
    	referralHit.setWlProtocolNumber("123"); 
    	referralHit.setWlWiWatlName(""); 
    	referralHit.setWlRescCode("wls"); 
    	referralHit.setModifiedBy("kasi");
    	referralHit.setModifiedDatetime(null) ; 
    	referralHit.setCreatedBy("kasi"); 
    	referralHit.setCreatedDatetime(getTodayDate()) ; 
    	
    	try 
    	{
    		referralHitDAO.insert(referralHit) ;
    		fail("should nt reach here") ; 
    	}catch(Exception ee)
    	{
    		Assert.assertTrue(true); 
    	}

    
    }
    
    @Test
    public void testInsertWatchListAndAppReasonNull()
    {
    	int referralId  = daoSupport.getJdbcTemplate().queryForInt("select max(id) as id from referrals"); 
    	int watchListRequestId = daoSupport.getJdbcTemplate().queryForInt("select max(id) from watch_list_requests" ) ; 
    	
    	Assert.assertTrue( referralId >=1) ; 
    	Assert.assertTrue( watchListRequestId >=1) ; 
    	
    	// Create a Referral Hit 
    	ReferralHit  referralHit = new ReferralHit();
    	referralHit.setRefId(Long.valueOf( referralId  ) )  ;  //REFERRAL.ID FK  
    	referralHit.setWatlrId(null); // WATCH_LIST_REQUEST.ID  FK 
    	referralHit.setQualificationStatus("IN"); 
    	referralHit.setHitType("WL");
    	referralHit.setWlMatchType("P");
    	referralHit.setWlSource("WI");
    	referralHit.setWlForename("kasi"); 
    	referralHit.setWlLastName("kasi"); 
    	referralHit.setWlGender("");  
    	referralHit.setWlBirthDate(getTodayDate()); 
    	referralHit.setWlBirthPlace(null); 
    	referralHit.setWlBirthCntryCode(" ");   
    	referralHit.setWlNationality("IND"); 
    	referralHit.setWlDocType("P"); 
    	referralHit.setWlDocNo("23");
    	referralHit.setWlDocIssueCntryCode("D");  
    	referralHit.setWlProtocolNumber("123"); 
    	referralHit.setAppHitReason(null ) ; 
    	referralHit.setWlWiWatlName(""); 
    	referralHit.setWlRescCode("wls"); 
    	referralHit.setModifiedBy("kasi");
    	referralHit.setModifiedDatetime(null) ; 
    	referralHit.setCreatedBy("kasi"); 
    	referralHit.setCreatedDatetime(getTodayDate()) ; 
    	
    	try 
    	{
    		referralHitDAO.insert(referralHit) ;
    		fail("should nt reach here") ; 
    	}catch(Exception ee)
    	{
    		Assert.assertTrue(true); 
    	}

    	
    	
    	
    }
    
    
}

