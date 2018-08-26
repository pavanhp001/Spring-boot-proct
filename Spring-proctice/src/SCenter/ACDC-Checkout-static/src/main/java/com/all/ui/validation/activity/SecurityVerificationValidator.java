package com.AL.ui.validation.activity;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.AL.ui.util.Utils;
import com.AL.xml.v4.SecurityVerificationType;
/**
 * @author rkiran
 *
 */
@Component
public class SecurityVerificationValidator implements Validator{

	public boolean supports(Class<?> classObj) {
		// TODO Auto-generated method stub
		return SecurityVerificationType.class.isAssignableFrom(classObj);
	}

	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		SecurityVerificationType securityVerificationInfo = (SecurityVerificationType) target;
		
		if (securityVerificationInfo == null){
			errors.reject("securityVerification.null", "securityVerification object is null");
		}
		else
		{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pin", "pin.required");
			if(!Utils.isNumeric(securityVerificationInfo.getPin()))
			{
				errors.reject("securityPin.invalid", "Security Pin invalid");
			}
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "securityQuestion", "securityQuestion.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "securityAnswer", "securityAnswer.required");
		}
	}

}
