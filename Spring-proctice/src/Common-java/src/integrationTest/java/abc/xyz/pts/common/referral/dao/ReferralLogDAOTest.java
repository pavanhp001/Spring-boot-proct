package abc.xyz.pts.common.referral.dao;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import abc.xyz.pts.bcs.common.dto.Referral;
import abc.xyz.pts.bcs.common.dto.ReferralLog;
import abc.xyz.pts.bcs.common.dto.Traveller;
import abc.xyz.pts.bcs.common.irisk.dao.TravellerDAO;
import abc.xyz.pts.bcs.common.referral.dao.ReferralDAO;
import abc.xyz.pts.bcs.common.referral.dao.ReferralLogDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:DataSourceTest.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional

public class ReferralLogDAOTest {
	 private static final Logger log = Logger.getLogger(DAOTest.class);
	    @Autowired
	    private JdbcDaoSupport daoSupport;
	    @Autowired
	    private TravellerDAO  travellerDAO;
	    @Autowired
	    private ReferralLogDAO referralLogDAO;
	    @Autowired
	    private ReferralDAO referralDAO;

	    @Test
	    public void testReferralLogDAO()
	    {
	    	final Referral referral  = createReferral();

	    	final int startReferralLogRowCount = daoSupport.getJdbcTemplate().queryForInt("select count(*) as totalCount from referral_logs") ;

	    	// Tests for Insert
	    	final int referralId  = daoSupport.getJdbcTemplate().queryForInt("select max(id) from referrals") ;

	    	final ReferralLog referralLog  = getReferralLog(Long.valueOf(referralId));
	    	referralLogDAO.insert(referralLog);

	    	final int endReferralLogRowCount = daoSupport.getJdbcTemplate().queryForInt("select count(*) as totalCount from referral_logs") ;
	    	Assert.assertEquals(endReferralLogRowCount - startReferralLogRowCount, 1 ) ;

	    }

	    private ReferralLog getReferralLog(final Long referralId){
	    	final ReferralLog referralLog = new ReferralLog();
	    	referralLog.setRefId(referralId);
	    	referralLog.setEventType("REFCREATED");
	    	referralLog.setCreatedBy("kasi");
	    	referralLog.setHitType("WL");
	    	referralLog.setCreatedDatetime(getTodayDate());

	    	return referralLog;
	    }

	    private Calendar getTodayDate() {
	    	return Calendar.getInstance();
	    }


	    private Referral  createReferral()
	    {
	    	final Referral referral = new Referral() ;
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

	    private Long getTravellerId(){
	    	//Get Traveller
	    	Long id = null;
	    	final Traveller traveller  = referralDAO.findTravellerWithoutReferrals() ;
	    	if(traveller != null )
	    	{
	    		id = traveller.getId() ;
	    	}

	    	return id;
	    }

}
