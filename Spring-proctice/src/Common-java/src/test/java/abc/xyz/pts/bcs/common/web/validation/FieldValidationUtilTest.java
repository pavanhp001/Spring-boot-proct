/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.web.validation;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import abc.xyz.pts.bcs.common.referral.web.command.ReferralSearchCommand;

/**
 * @author Reddy.Yattapu
 *
 */
@RunWith(JUnit4.class)
public class FieldValidationUtilTest {
	 @Test
	    public void testCheckArrDepAirportCodeAreSame() {
		 ReferralSearchCommand search = new ReferralSearchCommand();
	     Errors errors = new BindException(search, "arrivalAirport");
	     //Check for Both Airport have same value
		 FieldValidationUtil.checkArrDepAirportCodeAreSame(errors, "LHR", "LHR");
		 assertTrue(errors.hasErrors());
		 
	     //Check for Both Airport have different value
		 errors = new BindException(search, "arrivalAirport");
		 FieldValidationUtil.checkArrDepAirportCodeAreSame(errors, "LGW", "LHR");
		 assertFalse(errors.hasErrors());
		  
	     //Check for Both Airport have null value
		 errors = new BindException(search, "arrivalAirport");
		 FieldValidationUtil.checkArrDepAirportCodeAreSame(errors, null, null);
		 assertFalse(errors.hasErrors());
		 
	     //Check for Arraval Airport have value and Departure is null
		 errors = new BindException(search, "arrivalAirport");
		 FieldValidationUtil.checkArrDepAirportCodeAreSame(errors, "LHR", null);
		 assertFalse(errors.hasErrors());

	 }

}
