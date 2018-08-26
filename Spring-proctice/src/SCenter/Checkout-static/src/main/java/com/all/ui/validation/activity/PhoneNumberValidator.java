package com.AL.ui.validation.activity;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import com.AL.ui.util.Utils;
import com.AL.xml.v4.AddressType;
import com.AL.xml.v4.PhoneNumberType;

@Component
public class PhoneNumberValidator implements Validator{

	public boolean supports(Class<?> classObj) {
		// TODO Auto-generated method stub
		return PhoneNumberType.class.isAssignableFrom(classObj);
	}

	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		//homePhoneNumber ???
		//cellPhoneNumber ???
		//workPhoneNumber ???
		PhoneNumberType phoneNumberInfo = (PhoneNumberType) target;
		
		if (phoneNumberInfo == null){
			errors.reject("phoneNumber.null", "PhoneNumberInfo object is null");
		}
		else
		{	
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "phoneNumber.required", "Phone is required");
			if(Utils.isValidPhoneNumber(phoneNumberInfo.getValue()))
			{
				errors.reject("phoneNumber.invalid", "PhoneNumber invalid");
			}
			//if condition need to be added for extension
			if(phoneNumberInfo.getExtension()!=null)
			{
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "extension", "phoneNumberExt.required","PhoneNumberExt is required");
			}
		}
	}

}
