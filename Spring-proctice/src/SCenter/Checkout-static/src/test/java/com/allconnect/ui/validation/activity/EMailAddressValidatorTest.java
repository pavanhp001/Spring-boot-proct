package com.AL.ui.validation.activity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.AL.xml.v4.EMailAddressType;
/**
 * @author rkiran
 *
 */
public class EMailAddressValidatorTest {
	
	private EMailAddressValidator eMailAddressValidator;
	
	@Before
	public void setup(){
		eMailAddressValidator = new EMailAddressValidator();
	}
	
	@Test
	public void testEMailAddress(){
		EMailAddressType eMailAddress = new EMailAddressType();
		eMailAddress.setValue("sachin@home.com");
		eMailAddress.setDesc("Home eMail");
		//eMailAddress.setOrder(1);
		
		Errors errors = new BeanPropertyBindingResult(eMailAddress, "emailAddress");
		eMailAddressValidator.validate(eMailAddress, errors);
		assertTrue(errors.hasErrors() == false);
	}

}
