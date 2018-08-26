package com.AL.ui.validation.activity;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.AL.xml.v4.CustomerType;

/**
 * @author rkiran
 *
 */
public class DemographicValidatorTest {
	
	private DemographicValidator demographicValidator;
	
	@Before
	public void setup(){
		demographicValidator = new DemographicValidator();
	}
	
	@Test
	public void testDemographicInfo() throws DatatypeConfigurationException, ParseException{
		CustomerType customerType = new CustomerType();
		
		QName pQName = new QName("http://xml.AL.com/v4", "dob");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse("1979-06-28");
		Calendar cal = Calendar.getInstance();
		XMLGregorianCalendar gregCreationDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();
		cal.setTime(date);
		gregCreationDate.setYear(cal.get(Calendar.YEAR));
		gregCreationDate.setDay(cal.get(Calendar.DAY_OF_MONTH));
		gregCreationDate.setMonth(cal.get(Calendar.MONTH) + 1);
		
		JAXBElement<XMLGregorianCalendar> element = new JAXBElement<XMLGregorianCalendar>(pQName, XMLGregorianCalendar.class, gregCreationDate);
		customerType.setDob(element);
		customerType.setGender("M");
		customerType.setSsn("999060606");
		
		Errors errors = new BeanPropertyBindingResult(customerType, "customerType");
		demographicValidator.validate(customerType, errors);
		assertTrue(errors.hasErrors() == false);
	}

}
