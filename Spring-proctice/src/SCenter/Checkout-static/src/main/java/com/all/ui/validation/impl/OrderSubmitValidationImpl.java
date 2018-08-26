package com.AL.ui.validation.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.AL.ui.validation.AbstractValidator;
import com.AL.ui.validation.ValidationProductType;
import com.AL.ui.validation.activity.AddressValidator;
import com.AL.ui.validation.activity.BillingInfoValidator;
import com.AL.ui.validation.activity.DemographicValidator;
import com.AL.ui.validation.activity.EMailAddressValidator;
import com.AL.ui.validation.activity.PhoneNumberValidator;
import com.AL.ui.validation.activity.SecurityVerificationValidator;
import com.AL.xml.v4.CustAddress;
import com.AL.xml.v4.CustBillingInfoType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.ProductType;

public class OrderSubmitValidationImpl {

	@Autowired
	private AddressValidator addressValidator;
	@Autowired
	private PhoneNumberValidator phoneNumberValidator;
	@Autowired
	private DemographicValidator demographicValidator;
	@Autowired
	private BillingInfoValidator billingInfoValidator;
	@Autowired
	private EMailAddressValidator eMailAddressValidator;
	@Autowired
	private SecurityVerificationValidator securityValidator;

	public void setAddressValidator(AddressValidator addressValidator) {
		this.addressValidator = addressValidator;
	}

	public void setPhoneNumberValidator(
			PhoneNumberValidator phoneNumberValidator) {
		this.phoneNumberValidator = phoneNumberValidator;
	}

	public void setDemographicValidator(
			DemographicValidator demographicValidator) {
		this.demographicValidator = demographicValidator;
	}

	public void setBillingInfoValidator(
			BillingInfoValidator billingInfoValidator) {
		this.billingInfoValidator = billingInfoValidator;
	}

	public void seteMailAddressValidator(
			EMailAddressValidator eMailAddressValidator) {
		this.eMailAddressValidator = eMailAddressValidator;
	}

	public void setSecurityValidator(SecurityVerificationValidator securityValidator) {
		this.securityValidator = securityValidator;
	}

	/*public boolean supports(Class<?> classObj) {
		return OrderType.class.isAssignableFrom(classObj);
	}*/

	public List<Errors> validateOrder(OrderType orderInfo) {

		List<Errors> errorsList = new ArrayList<Errors>();

		if (orderInfo == null) {
			//errors.reject("orderInfo.null", "orderInfo object is null");
		} else {
			if (orderInfo.getLineItems().getLineItem() != null) {
				for (LineItemType lineItemTypeInfo : orderInfo.getLineItems()
						.getLineItem()) {
					if (lineItemTypeInfo == null) {
						//errors.reject("lineItemTypeInfo.null", "lineItemTypeInfo object is null");
					}
					else if(lineItemTypeInfo.getLineItemDetail() != null && lineItemTypeInfo.getLineItemDetail().getDetail() != null && lineItemTypeInfo.getLineItemDetail().getDetail().getProductLineItem() != null)
					{
						ProductType prodType = lineItemTypeInfo.getLineItemDetail().getDetail().getProductLineItem();

						String value = getSwitchCaseValue(prodType);

						/*
						 * ProductCategoryListType productCategoryListTypeInfo =
						 * lineItemTypeInfo
						 * .getLineItemDetail().getDetail().getProductLineItem
						 * ().getProductCategoryList();
						 * 
						 * //how to get ProductType here? for(ItemCategory
						 * category:
						 * productCategoryListTypeInfo.getProductCategory()){
						 * String productType = category.getDisplayName();
						 * //Convert from String to ENUM ValidationProductType
						 * prType = ValidationProductType.valueOf(value);
						 * 
						 * switch (prType) { case uverse:
						 * validateUverseProduct(target, errors); break; case
						 * dsl: validateDslProduct(target, errors); break; case
						 * phone: validatePhoneProduct(target, errors); break;
						 * default: break; } }
						 */

						ValidationProductType prType = ValidationProductType.valueOf(value);

						switch (prType) {
							case uverse:
								validateUverseProduct(orderInfo, errorsList);
								break;
							case dsl:
								validateDslProduct(orderInfo, errorsList);
								break;
							case phone:
								validatePhoneProduct(orderInfo, errorsList);
								break;
							default:
								break;
						}
					}
				}

			}

		}
		return errorsList;
	}
	
	private String getSwitchCaseValue(ProductType prodType) {
		if(prodType.getName() != null && prodType.getName().toLowerCase().contains("verse") == true){
			return "uverse";
		}
		else if(prodType.getName() != null && prodType.getName().toLowerCase().contains("dsl") == true){
			return "dsl";
		}
		else{
			return "phone";
		}
	}	

	public void validateUverseProduct(OrderType order, List<Errors> errorsList) {
		//OrderType order = (OrderType) target;

		try {
			Errors addrErrors = null;
			// validate Customer Address
			for (CustAddress custAddr : order.getCustomerInformation().getCustomer().getAddressList().getCustomerAddress()) {
				addrErrors = new BeanPropertyBindingResult(custAddr.getAddress(),"address");
				ValidationUtils.invokeValidator(addressValidator,custAddr.getAddress(), addrErrors);
			}

			// validate the demographic information of the customer
			Errors demoErrors = new BeanPropertyBindingResult(order.getCustomerInformation().getCustomer(),"customer");
			ValidationUtils.invokeValidator(demographicValidator, order.getCustomerInformation().getCustomer(), demoErrors);
			// validate the Phone numbers of the customer
			Errors phoneErrors = new BeanPropertyBindingResult(order.getCustomerInformation().getCustomer().getHomePhoneNumber(),"phoneNumber");
			ValidationUtils.invokeValidator(phoneNumberValidator, order.getCustomerInformation().getCustomer().getHomePhoneNumber(), phoneErrors);
			ValidationUtils.invokeValidator(phoneNumberValidator, order.getCustomerInformation().getCustomer().getWorkPhoneNumber(), phoneErrors);
			ValidationUtils.invokeValidator(phoneNumberValidator, order.getCustomerInformation().getCustomer().getCellPhoneNumber(), phoneErrors);

			Errors emailErrors = new BeanPropertyBindingResult(order.getCustomerInformation().getCustomer().getHomeEMail(),"email");
			ValidationUtils.invokeValidator(eMailAddressValidator, order.getCustomerInformation().getCustomer().getHomeEMail(),emailErrors);
			ValidationUtils.invokeValidator(eMailAddressValidator, order.getCustomerInformation().getCustomer().getWorkEMail(),emailErrors);
			Errors securityErrors = new BeanPropertyBindingResult(order.getCustomerInformation().getCustomer().getSecurityVerificationInfo(),"security");
			ValidationUtils.invokeValidator(securityValidator, order.getCustomerInformation().getCustomer().getSecurityVerificationInfo(), securityErrors);

			// validate the billingInfo of the customer
			Errors billingErrors = null;
			for (CustBillingInfoType custBillingInfo : order.getCustomerInformation().getCustomer().getBillingInfoList().getBillingInfo()) {
				billingErrors = new BeanPropertyBindingResult(order.getCustomerInformation().getCustomer().getSecurityVerificationInfo(),"billing");
				ValidationUtils.invokeValidator(billingInfoValidator,custBillingInfo, billingErrors);
			}
			
			if(demoErrors.hasErrors() == true){
				errorsList.add(demoErrors);
			}
			
			if(addrErrors.hasErrors() == true){
				errorsList.add(addrErrors);
			}
			
			if(emailErrors.hasErrors() == true){
				errorsList.add(emailErrors);
			}
			
			if(securityErrors.hasErrors() == true){
				errorsList.add(securityErrors);
			}
			
			if(billingErrors.hasErrors() == true){
				errorsList.add(billingErrors);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void validateDslProduct(Object target, List<Errors> errorsList) {

	}

	public void validatePhoneProduct(Object target, List<Errors> errorsList) {

	}

}
