package com.AL.ui.validation;

import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.AL.ui.validation.utils.CoreValidationUtils;
import com.AL.xml.v4.AddressType;

  
	
	@ManagedResource
	@Component
	public class ValidateAddress  {

		public boolean supports(Class<?> classObj) {
			 
			return AddressType.class.isAssignableFrom(classObj);
		}

		public void validate(Object target, Errors errors) {
		 
			AddressType addressInfo = (AddressType) target;
			
			if (addressInfo == null){
				errors.reject("address.null", "Address object is null");
			}

			 
	 
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, addressInfo.getStreetName(), "streetname.required", "StreetName required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, addressInfo.getStreetNumber(), "streetnumber.required", "StreetNumber required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, addressInfo.getCity(), "city.required", "City required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, addressInfo.getCountry(), "country.required", "Country required");
			
			if(!CoreValidationUtils.INSTANCE.isAlphabetic(addressInfo.getCountry()))
			{
				errors.reject("address.country.not.alphabetic", "Country should be alphabetic");
			}
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, addressInfo.getPostalCode(), "postalcode.required", "PostalCode required");
			if(!CoreValidationUtils.INSTANCE.isValidZipCode(addressInfo.getPostalCode()))
			{
				errors.reject("address.zipcode", "ZipCode invalid");
			}
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, addressInfo.getStateOrProvince(), "stateorprovince.required", "StateOrProvince required");
		}
}
