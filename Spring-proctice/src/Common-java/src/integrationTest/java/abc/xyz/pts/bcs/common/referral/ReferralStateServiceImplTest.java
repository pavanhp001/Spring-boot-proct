package abc.xyz.pts.bcs.common.referral;


import abc.xyz.pts.bcs.common.DBUnitTestSetup;

import java.util.Locale;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import abc.xyz.pts.bcs.common.dto.Referral;
import abc.xyz.pts.bcs.common.enums.ReferralStatusType;
import abc.xyz.pts.bcs.common.exception.AlreadyAcknowledgedException;
import abc.xyz.pts.bcs.common.referral.ReferralService;
import abc.xyz.pts.bcs.common.referral.ReferralStateService;

public class ReferralStateServiceImplTest extends DBUnitTestSetup {
	private static final long REFERRAL_ID = 1000L;
	
	@Autowired
	//@Qualifier("referralsStateService")
	ReferralStateService referralsStateService;
	
	@Autowired
	ReferralService referralsService;
	
	//@Ignore
	@Test
	public void testGetReferralById() throws Exception {
	
		Referral referral = referralsService.getReferralById(REFERRAL_ID);
		Assert.assertNotNull(referral);
	}

	@Ignore
	@Test
	public void testGetReferralLogs() throws Exception {
		Referral referral = new Referral();
		referral.setStatus(ReferralStatusType.UNQ.name());
		try {
			referralsStateService.acknowledge(referral, Locale.ENGLISH);
			Assert.assertTrue(false);
		} catch (AlreadyAcknowledgedException aae) {
			Assert.assertTrue(true);
		}
	}
	
}
