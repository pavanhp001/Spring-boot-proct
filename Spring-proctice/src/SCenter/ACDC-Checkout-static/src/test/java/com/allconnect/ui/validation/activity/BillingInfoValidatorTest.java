/**
 * 
 */
package com.AL.ui.validation.activity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.AL.xml.v4.CustBillingInfoType;

/**
 * @author ganesh
 *
 */
public class BillingInfoValidatorTest {
	
	public BillingInfoValidator billingValidator;
	
	@Before
	public void setup(){
		billingValidator = new BillingInfoValidator();
	}
	
	@Test
	public void testBillingInfoDetails(){
		CustBillingInfoType custBilling = new CustBillingInfoType();
		custBilling.setCardHolderName("Ganesh Kumar");
		custBilling.setVerificationCode("124");
		custBilling.setCreditCardNumber("1111444455552222");
		
		Errors errors = new BeanPropertyBindingResult(custBilling, "custBilling");
		
		billingValidator.validate(custBilling, errors);
		
		assertTrue(errors.hasErrors() == false);
	}

}
