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

/**
 * @author ganesh
 *
 */
public class AddressValidatorTest {
	
	private AddressValidator addressValidator;
	
	@Before
	public void setup(){
		addressValidator = new AddressValidator();
	}
	
	@Test
	public void testAddressInfo(){
		AddressType address = new AddressType();
		address.setCity("Norcross");
		address.setStateOrProvince("GA");
		address.setCountry("US");
		address.setStreetName("Ivy Chase Ln");
		address.setStreetNumber("365");
		address.setPostalCode("35t467-4444");
		
		Errors errors = new BeanPropertyBindingResult(address, "address");
		
		addressValidator.validate(address, errors);
		
		assertTrue(errors.hasErrors() == false);
	}

}
