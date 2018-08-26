/**
 * 
 */
package com.AL.ui.validation.activity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.AL.xml.v4.AddressType;
import com.AL.xml.v4.SecurityVerificationType;

/**
 * @author rkiran
 *
 */
public class SecurityVerificationValidatorTest {
	
	private SecurityVerificationValidator securityVerificationValidator;
	
	@Before
	public void setup(){
		securityVerificationValidator = new SecurityVerificationValidator();
	}
	
	@Test
	public void testSecurityVerificationInfo(){
		SecurityVerificationType securityVerificationInfo = new SecurityVerificationType();
		securityVerificationInfo.setPin("1234");
		securityVerificationInfo.setSecurityQuestion("Quesiton1");
		securityVerificationInfo.setSecurityAnswer("Answer1");
		
		Errors errors = new BeanPropertyBindingResult(securityVerificationInfo, "securityVerificationInfo");
		
		securityVerificationValidator.validate(securityVerificationInfo, errors);
		
		assertTrue(errors.hasErrors() == false);
	}

}
