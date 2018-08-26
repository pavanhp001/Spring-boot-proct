package com.AL.ui.validation.activity;

import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.AL.xml.v4.CustomerType;

@ManagedResource
@Component
public class DemographicValidator implements Validator {

	public boolean supports(Class<?> classObj) {
		// TODO Auto-generated method stub
		return CustomerType.class.isAssignableFrom(classObj);
	}

	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		//homePhoneNumber ???
		//cellPhoneNumber ???
		//workPhoneNumber ???
		CustomerType custInfo = (CustomerType) target;
		
		if (custInfo == null){
			errors.reject("custInfo.null", "CustInfo object is null");
		}
		else{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "gender.required","Gender is required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dob", "dob.required","DOB is required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ssn", "ssn.required","SSN is required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "field.required","Firstname is required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "field.required","LastName is required");
		}
	}
}
