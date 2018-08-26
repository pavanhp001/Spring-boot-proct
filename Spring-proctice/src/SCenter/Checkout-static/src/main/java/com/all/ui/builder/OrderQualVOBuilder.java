package com.AL.ui.builder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestUtils;

import com.AL.ui.vo.CKOInitialVo;
import com.AL.ui.vo.OrderQualVO;

public class OrderQualVOBuilder {
	
	
	public static OrderQualVO buildOrderInfo (CKOInitialVo CKOVo ) {
		
		long lineItemId = CKOVo.getFirstLineItemId();
		String orderId = CKOVo.getOrderId();

		OrderQualVO orderQualVO = new OrderQualVO();
		orderQualVO.setLineItemExternalId(lineItemId);
		orderQualVO.setOrderId(orderId);
		
		return orderQualVO;
	}


	public static OrderQualVO buildOrderQualVO(HttpServletRequest request) {
	
		long lineItemId = ServletRequestUtils.getLongParameter(request,
				"lineItemId", 14939L);
		String orderId = ServletRequestUtils.getStringParameter(request,
				"orderId", "14254");
		String customerId = ServletRequestUtils.getStringParameter(request,
				"customerId", "25362");
		OrderQualVO orderQualVO = new OrderQualVO();
		orderQualVO.setLineItemExternalId(lineItemId);
		orderQualVO.setOrderId(orderId);
		orderQualVO.setCustomerExternalId(customerId);
		
	
		return orderQualVO;
	}
	
	/*public static OrderQualVO buildOrderQualVO(Map<String, String> parameterMap, 
			OrderQualVO orderQualVO) {
		if(orderQualVO == null) {
			orderQualVO = new OrderQualVO();
		}
		if(parameterMap != null) {
			if(!StringUtils.isEmpty(parameterMap.get(SSN))) {
				orderQualVO.setSsn(parameterMap.get(SSN));
			}
			
			if(!StringUtils.isEmpty(parameterMap.get(DOB))) {
				orderQualVO.setDob(DateUtil.asXMLGregorianCalendar(parameterMap.get(DOB), DOB_DATE_FORMAT));
			}
			
			if(!StringUtils.isEmpty(parameterMap.get(SECOND_CONTACT))) {
				PhoneNumberType workPhoneNumber = new PhoneNumberType();
				workPhoneNumber.setValue(parameterMap.get(SECOND_CONTACT));
				orderQualVO.setSecondaryContactNumber(parameterMap.get(SECOND_CONTACT));
				if(!StringUtils.isEmpty(parameterMap.get(SECOND_CONTACT_EXT))) {
					workPhoneNumber.setExtension(parameterMap.get(SECOND_CONTACT_EXT));
					orderQualVO.setSecondaryContactNumberExt(parameterMap.get(SECOND_CONTACT_EXT));
				}
				orderQualVO.setWorkPhoneNumber(workPhoneNumber);
			}
			
			if(!StringUtils.isEmpty(parameterMap.get(SECURITY_PIN))) {
				orderQualVO.setSecurityPin(parameterMap.get(SECURITY_PIN));
			}
			
			if(!StringUtils.isEmpty(parameterMap.get(SECURITY_QUESTION))) {
				orderQualVO.setSecurityQuestion(parameterMap.get(SECURITY_QUESTION));
			}
			
			if(!StringUtils.isEmpty(parameterMap.get(SECURITY_ANSWER))) {
				orderQualVO.setSecurityAnswer(parameterMap.get(SECURITY_ANSWER));
			}
			
			if (!StringUtils.isEmpty(parameterMap.get(SAME_BILLING_ADDRESS_FLAG)))  {
				boolean sameBillingAddress = Boolean.valueOf(parameterMap.get(SAME_BILLING_ADDRESS_FLAG));
				orderQualVO.setBillingAddressSameAsService(sameBillingAddress);
				if(!sameBillingAddress) {
					CustAddress billingAddress = createCustomerAddress(parameterMap, orderQualVO);
					AddressRoles addressRoles = new AddressRoles();
					addressRoles.getRole().add(RoleType.BILLING_ADDRESS);
					billingAddress.setAddressRoles(addressRoles);
					billingAddress.setAddressUniqueId("BLG" + System.currentTimeMillis());
					orderQualVO.setBillingAddress(billingAddress);
				}
			} else {
				orderQualVO.setBillingAddressSameAsService(true);
			}
			
			if(!StringUtils.isEmpty(parameterMap.get(CARD_NUMBER)))
			{
				orderQualVO.setCreditCardNumber(parameterMap.get(CARD_NUMBER));
			}
			
			if(!StringUtils.isEmpty(parameterMap.get(CARD_HOLDER_NAME)))
			{
				orderQualVO.setCardHolderName(parameterMap.get(CARD_HOLDER_NAME));
			}
			
			if(!StringUtils.isEmpty(parameterMap.get(CVV)))
			{
				orderQualVO.setVerificationCode(parameterMap.get(CVV));
			}
			
			if(!StringUtils.isEmpty(parameterMap.get(CC_EXP_YEAR)))
			{
				orderQualVO.setExpirationYear(parameterMap.get(CC_EXP_YEAR));
			}
			
			if(!StringUtils.isEmpty(parameterMap.get(CC_EXP_MONTH)))
			{
				orderQualVO.setExpirationMonth(parameterMap.get(CC_EXP_MONTH));
			}
			
			if(!StringUtils.isEmpty(parameterMap.get(CARD_TYPE)))
			{
				orderQualVO.setCreditCardType(CreditCardTypeType.fromValue(parameterMap.get(CARD_TYPE)));
			}
			
		}
		return orderQualVO;
	}
	
	private static CustAddress createCustomerAddress(Map<String, String> parameterMap, OrderQualVO orderQualVO) {
		CustAddress custAddress = new CustAddress();
		AddressType addressType = new AddressType();
		StringBuilder sb = new StringBuilder();
		sb.append(parameterMap.get(BILL_STREET_ADDRESS)).append(", ");
		orderQualVO.setStreetNumber(parameterMap.get(BILL_STREET_ADDRESS));
		sb.append(parameterMap.get(BILL_CITY)).append(" ");
		orderQualVO.setCity(parameterMap.get(BILL_CITY));
		sb.append(parameterMap.get(BILL_STATE)).append(" ");
		orderQualVO.setStateOrProvince(parameterMap.get(BILL_STATE));
		sb.append(parameterMap.get(BILL_ZIP));
		orderQualVO.setPostalCode(parameterMap.get(BILL_ZIP));
		if(!StringUtils.isEmpty(parameterMap.get(BILL_ZIP4))) {
			sb.append(parameterMap.get(BILL_ZIP4));
			String zip = "";
			if(orderQualVO.getPostalCode() != null)
			{
				zip = orderQualVO.getPostalCode();
				orderQualVO.setPostalCode(zip.concat(parameterMap.get(BILL_ZIP4)));
			}
		}
		addressType.setAddressBlock(sb.toString());
		custAddress.setAddress(addressType);
		return custAddress;
	}*/
	
	/*public List<Errors> validateOrderQualVO(OrderQualVO orderQualVO) {
		// TODO Auto-generated method stub
		List<Errors> errorsList = new ArrayList<Errors>();
		Errors orderQualVOErrors = new BeanPropertyBindingResult(orderQualVO, "orderQualVO");
		ValidationUtils.rejectIfEmptyOrWhitespace(orderQualVOErrors, "secondaryContactNumber", "secondaryContactNumber.required", "Secondary Contact Number is required");
		if(orderQualVO.getSecondaryContactNumber()!=null && !orderQualVO.getSecondaryContactNumber().equals(""))
		{
			if(!StringUtils.isNumeric(orderQualVO.getSecondaryContactNumber()))
			{
				orderQualVOErrors.reject("SecondaryContactNumber.non-numeric", "Secondary Contact Number should be numeric");
			}
		}
		if(orderQualVO.getSecondaryContactNumberExt()!=null && !orderQualVO.getSecondaryContactNumberExt().equals(""))
		{
			if(!StringUtils.isNumeric(orderQualVO.getSecondaryContactNumberExt()))
			{
				orderQualVOErrors.reject("SecondaryContactNumberExt.non-numeric", "Secondary contact number extension should be numeric");
			}
		}
		//ValidationUtils.rejectIfEmptyOrWhitespace(orderQualVOErrors, "secondaryContactNumberExt", "secondaryContactNumberExt.required", "Secondary contact number extension is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(orderQualVOErrors, "ssn", "ssn.required", "SSN is required");
		if(orderQualVO.getSsn()!=null && !orderQualVO.getSsn().equals(""))
		{
			if(!StringUtils.isNumeric(orderQualVO.getSsn()))
			{
				orderQualVOErrors.reject("SSN.non-numeric", "Social Security Number should be numeric");
			}
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(orderQualVOErrors, "dob", "dob.required", "Date of Birth is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(orderQualVOErrors, "securityQuestion", "securityQuestion.required", "Security Question is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(orderQualVOErrors, "securityAnswer", "securityAnswer.required", "Security Answer is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(orderQualVOErrors, "securityPin", "securityPin.required", "Security Pin is required");
		if(orderQualVO.getSecurityPin() !=null && !orderQualVO.getSecurityPin().equals(""))
		{
			if(!StringUtils.isNumeric(orderQualVO.getSecurityPin()))
			{
				orderQualVOErrors.reject("SecurityPin.non-numeric", "Security Pin should be numeric");
			}
		}
		
		if(orderQualVO.isBillingAddressSameAsService() != true)
		{
			ValidationUtils.rejectIfEmptyOrWhitespace(orderQualVOErrors, "streetNumber", "streetNumber.required", "StreetNumber is required");
			ValidationUtils.rejectIfEmptyOrWhitespace(orderQualVOErrors, "city", "city.required", "City is required");
			ValidationUtils.rejectIfEmptyOrWhitespace(orderQualVOErrors, "stateOrProvince", "stateOrProvince.required", "StateOrProvince is required");
			ValidationUtils.rejectIfEmptyOrWhitespace(orderQualVOErrors, "postalCode", "postalCode.required", "PostalCode is required");
			if(orderQualVO.getPostalCode() !=null && !orderQualVO.getPostalCode().equals(""))
			{
				if(!StringUtils.isNumeric(orderQualVO.getPostalCode()))
				{
					orderQualVOErrors.reject("PostalCode.non-numeric", "Postal Code should be numeric");
				}
			}
		}
		errorsList.add(orderQualVOErrors);
		return errorsList;
	}
	
	public List<Errors> validateOrderQualVOBillingInfo(OrderQualVO orderQualVO) {
		// TODO Auto-generated method stub
		List<Errors> errorsList = new ArrayList<Errors>();
		Errors orderQualVOErrors = new BeanPropertyBindingResult(orderQualVO, "orderQualVO");
		//ValidationUtils.rejectIfEmptyOrWhitespace(orderQualVOErrors, "billingAccountType", "billingAccountType.required", "SSN is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(orderQualVOErrors, "creditCardNumber", "creditCardNumber.required", "CreditCard Number is required");
		if(orderQualVO.getCreditCardNumber()!=null && !orderQualVO.getCreditCardNumber().equals(""))
		{
			if(!StringUtils.isNumeric(orderQualVO.getCreditCardNumber()))
			{
				orderQualVOErrors.reject("creditCardNumber.non-numeric", "CreditCard Number should be numeric");
			}
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(orderQualVOErrors, "creditCardType", "creditCardType.required", "CreditCard Type is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(orderQualVOErrors, "cardHolderName", "cardHolderName.required", "Card HolderName is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(orderQualVOErrors, "verificationCode", "verificationCode.required", "Verification Code is required");
		if(orderQualVO.getVerificationCode()!=null && !orderQualVO.getVerificationCode().equals(""))
		{
			if(!StringUtils.isNumeric(orderQualVO.getVerificationCode()))
			{
				orderQualVOErrors.reject("verificationCode.non-numeric", "CVV should be numeric");
			}
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(orderQualVOErrors, "expirationMonth", "expirationMonth.required", "Expiration Month is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(orderQualVOErrors, "expirationYear", "expirationYear.required", "Expiration Year is required");
		errorsList.add(orderQualVOErrors);
		return errorsList;
	}*/
}
