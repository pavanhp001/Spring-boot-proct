/**
 * 
 */
package abc.xyz.pts.bcs.common.referral;

import abc.xyz.pts.bcs.common.DBUnitTestSetup;
import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import abc.xyz.pts.bcs.common.referral.business.ReferralHitService;
import abc.xyz.pts.bcs.common.referral.dto.ReferralHitTargetRec;

/**
 * @author Sudheendra.Singh
 *
 */
public class ReferralHitServiceImplTest extends DBUnitTestSetup {
	private static final long HIT_ID = 1000L;
	@Autowired
	ReferralHitService referralHitService;
	
	@Test
	public void testGetReferralById() throws Exception {
	
		ReferralHitTargetRec referralHitTargetRec = referralHitService.getReferralHitTarget(HIT_ID);
		Assert.assertNotNull(referralHitTargetRec);
	}


}
