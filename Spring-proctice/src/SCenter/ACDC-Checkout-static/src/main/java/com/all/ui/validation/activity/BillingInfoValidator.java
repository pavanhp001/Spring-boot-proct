package com.AL.ui.validation.activity;

import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.AL.xml.v4.CustBillingInfoType;

@ManagedResource
@Component
public class BillingInfoValidator implements Validator{

	public boolean supports(Class<?> classObj) {
		// TODO Auto-generated method stub
		return CustBillingInfoType.class.isAssignableFrom(classObj);
	}

	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		CustBillingInfoType billingInfoTypeInfo = (CustBillingInfoType) target;
		
		if (billingInfoTypeInfo == null){
			errors.reject("billingInfoTypeInfo.null", "BillingInfoTypeInfo object is null");
		}

/*		ValidationUtils.rejectIfEmptyOrWhitespace(errors, billingInfoTypeInfo.getCardHolderName(), "CardHolderName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, billingInfoTypeInfo.getCreditCardNumber(), "CreditCardNumber.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, billingInfoTypeInfo.getVerificationCode(), "VerificationCode.required");*/
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cardHolderName", "cardHolderName.required","CardHolderName is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCardNumber", "creditCardNumber.required","CreditCardNumaber is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "verificationCode", "verificationCode.required","VerificationCode is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expirationYearMonth", "ExpirationYearMonth.required", "ExpirationYearMonth is required");
	}

}