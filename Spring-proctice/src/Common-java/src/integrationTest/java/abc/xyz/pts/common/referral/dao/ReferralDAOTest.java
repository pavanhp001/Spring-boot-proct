package abc.xyz.pts.common.referral.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.List;

import org.junit.After;
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
import abc.xyz.pts.bcs.common.dto.Traveller;
import abc.xyz.pts.bcs.common.irisk.dao.TravellerDAO;
import abc.xyz.pts.bcs.common.referral.dao.ReferralDAO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:DataSourceTest.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional

public class ReferralDAOTest
{

	@Autowired
	private ReferralDAO   referralDAO;
	@Autowired
	private TravellerDAO travellerDAO; 
	@Autowired
	private JdbcDaoSupport daoSupport;
	
	@Before
	public void setup() {
		// TODO Auto-generated method stub
		System.out.println();
	}
	
	@After
	public void post() {
		System.out.println();

	}

	@Test
	// Insert into REFERRAL 
	public void testInsertRowInReferrallTable() 
	{
		int initialRowCount = daoSupport.getJdbcTemplate().queryForInt("select count(*) as totalCount from referrals") ; 

		createReferral(); 

		int updatedrowCount = daoSupport.getJdbcTemplate().queryForInt("select count(*) as totalCount from referrals") ; 

		Assert.assertEquals(1, updatedrowCount -  initialRowCount); 
	}

	@Test 
	public void testFindByTravellerId() 
	{
		createReferral();
		int referralId = daoSupport.getJdbcTemplate().queryForInt("select max(id) as id from referrals"); 
		int travellerId = daoSupport.getJdbcTemplate().queryForInt("select tra_id from referrals where id = " + referralId ) ; 

		Referral ref  = referralDAO.findByTravellerId(Long.valueOf(travellerId) )  ; 

		Assert.assertNotNull(ref) ;  

	}

	@Test 
	public void testFindByid()  
	{
		createReferral();
		int referralId  = daoSupport.getJdbcTemplate().queryForInt("select max(id) as id from referrals"); 
		int travellerId = daoSupport.getJdbcTemplate().queryForInt("select tra_id from referrals where id = " + referralId ) ; 

		Referral ref = referralDAO.findById(Long.valueOf(referralId))  ;  
		Assert.assertNotNull(ref) ;  
		Assert.assertTrue( referralId == ref.getId() ) ; 

	}
	
	@Test 
	public void testFindReferralUsingInvalidId()  
	{
		Referral referral = referralDAO.findById(Long.valueOf(9565L))  ;  
		Assert.assertNull(referral); 
	}
	
	@Test 
	public void testReferralUsingInvalidTravellerId()  
	{
		Referral referral = referralDAO.findByTravellerId(9565L);   
		Assert.assertNull(referral); 
	}
	
	@Test
	public void testGetReferrals()
	{
		Referral ecpectedReferral = createReferral(); 
//		List<Referral> listReferrals  = referralDAO.getReferrals();
		Referral referralFromDB = referralDAO.findByTravellerId(ecpectedReferral.getTraId());
		assertThat(referralFromDB.getId(), is(ecpectedReferral.getId()));
	}
	
	@Test 
	public void testInsertRowWithNullValues()  
	{
		createReferralWithNullAndWhitespaceValues();
		int referralId  = daoSupport.getJdbcTemplate().queryForInt("select max(id) as id from referrals"); 
		
		Referral ref = referralDAO.findById(Long.valueOf(referralId))  ;  
		Assert.assertNotNull(ref) ;  
		Assert.assertTrue( referralId == ref.getId() ) ; 

	}
	
	@Test 
	public void testUpdateReferral()  
	{
		createReferral(); 
		int referralId  = daoSupport.getJdbcTemplate().queryForInt("select max(id) as id from referrals"); 
		Referral ref = referralDAO.findById(Long.valueOf(referralId))  ;
		Referral ref1 = new Referral();
		ref1.setId(305L);
		ref1.setUpdateVersionNo(95L); 
		referralDAO.update(ref1); 
		// Check Updation of version Number
		
	}
	

	private Long getTravellerId(){
		//Get Traveller
		Long id = null;
		List<Traveller> travellerList = travellerDAO.getTravellers();    	
		if(travellerList.size() > 0){
			id = travellerList.get(0).getId();
		}
		return id;
	}
	
	/**
	 * 
	 * @return
	 */
	
	private Long getTravellerIdForReferral(){
		//Get Traveller
		Long id = null;
		Traveller traveller  = referralDAO.findTravellerWithoutReferrals();   	
		
		if(traveller != null )
		{
			id = traveller.getId();
		}
		
		return id;
	}
	

	private Calendar getTodayDate() 
	{
		return Calendar.getInstance();
	}

	private Referral  createReferral() 
	{
		Referral referral = new Referral() ;
		referral.setTraId( getTravellerIdForReferral()) ;  
		referral.setActionCode("act"); 
		referral.setStatus("UNQ"); 
		referral.setPriActioningAirportCode("PRI");
		referral.setSecActioningAirportCode("SEC");
		referral.setClosureCode("OTHER");
		referral.setModifiedBy("Mo");
		referral.setModifiedDatetime(getTodayDate() ); 
		referral.setCreatedBy("C");
		referral.setPriority(20L); 
		referral.setCreatedDatetime(getTodayDate()); 

		referralDAO.insert(referral); 

		return referral ;  

	}

	private Referral  createReferralWithNullAndWhitespaceValues() 
	{
		Referral referral = new Referral() ;
		referral.setTraId(getTravellerIdForReferral()); 
		referral.setActionCode("S"); 
		referral.setStatus("UNQ"); 
		referral.setPriActioningAirportCode(null);
		referral.setSecActioningAirportCode(" ");
		referral.setClosureCode("OTHER");
		referral.setModifiedBy("Mo");
		referral.setPriority(null); 
		referral.setModifiedDatetime(getTodayDate() ); 
		referral.setCreatedBy("C");
		referral.setCreatedDatetime(getTodayDate()); 

		referralDAO.insert(referral); 

		return referral ;  

	}

}