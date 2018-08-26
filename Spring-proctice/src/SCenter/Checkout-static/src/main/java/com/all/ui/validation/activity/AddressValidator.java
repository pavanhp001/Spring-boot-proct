package com.AL.ui.validation.activity;

import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.AL.ui.util.Utils;
import com.AL.xml.v4.AddressType;

@ManagedResource
@Component
public class AddressValidator implements Validator{

	public boolean supports(Class<?> classObj) {
		// TODO Auto-generated method stub
		return AddressType.class.isAssignableFrom(classObj);
	}

	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		AddressType addressInfo = (AddressType) target;
		
		if (addressInfo == null){
			errors.reject("adress.null", "Address object is null");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "streetName", "streetName.required", "StreetName required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "streetNumber", "streetNumber.required", "StreetNumber required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "city.required", "City required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "country.required", "Country required");
		if(!Utils.isAlphabetic(addressInfo.getCountry()))
		{
			errors.reject("adress.IsCountryAlphabetic", "Country shoud be alphebetic");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postalCode", "postalCode.required", "PostalCode required");
		if(!Utils.isValidZipCode(addressInfo.getPostalCode()))
		{
			errors.reject("adress.zipcode", "ZipCode invalid");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stateOrProvince", "stateOrProvince.required", "StateOrProvince required");
	}

}
