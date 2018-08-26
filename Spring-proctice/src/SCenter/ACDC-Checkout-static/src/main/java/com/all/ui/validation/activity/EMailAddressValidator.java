package com.AL.ui.validation.activity;

import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.AL.ui.util.Utils;
import com.AL.xml.v4.EMailAddressType;

@ManagedResource
@Component
public class EMailAddressValidator implements Validator{

	public boolean supports(Class<?> classObj) {
		// TODO Auto-generated method stub
		return EMailAddressType.class.isAssignableFrom(classObj);
	}

	public void validate(Object target, Errors errors) {

		EMailAddressType emailAddressInfo = (EMailAddressType) target;
		
		if (emailAddressInfo == null){
			errors.reject("eMailAddress.null", "EMailAddress object is null");
		}
		else
		{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "eMailAddress.required", "EMailAddress is required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "order", "eMailAddress.required", "EMailAddress order is required");
			if(!Utils.isValidEmail(emailAddressInfo.getValue()))
			{
				errors.reject("eMailAdress.invalid", "EmailId invalid");
			}
		}
	}

}
