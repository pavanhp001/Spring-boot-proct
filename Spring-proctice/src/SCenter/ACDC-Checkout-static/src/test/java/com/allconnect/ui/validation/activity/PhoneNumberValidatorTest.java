package com.AL.ui.validation.activity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.AL.xml.v4.PhoneNumberType;

/**
 * @author rvallabhaneeni
 *
 */
public class PhoneNumberValidatorTest {
	
	private PhoneNumberValidator phoneNumberValidator;
	
	@Before
	public void setup(){
		phoneNumberValidator = new PhoneNumberValidator();
	}
	
	@Test
	public void testPhoneNumber(){
		PhoneNumberType phoneNumber = new PhoneNumberType();
		phoneNumber.setValue("408-7190-218");
		phoneNumber.setExtension("123");
		phoneNumber.setDesc("Home Phone");
		phoneNumber.setOrder(1);
		
		Errors errors = new BeanPropertyBindingResult(phoneNumber, "phoneNumber");
		phoneNumberValidator.validate(phoneNumber, errors);
		assertTrue(errors.hasErrors() == false);
	}

}
