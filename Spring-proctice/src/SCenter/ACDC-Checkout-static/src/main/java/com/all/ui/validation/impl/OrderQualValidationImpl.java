package com.AL.ui.validation.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.AL.ui.service.V.ProductServiceUI;
import com.AL.ui.validation.ValidationProductType;
import com.AL.ui.validation.activity.AddressValidator;
import com.AL.ui.validation.activity.DemographicValidator;
import com.AL.ui.validation.activity.EMailAddressValidator;
import com.AL.ui.validation.activity.PhoneNumberValidator;
import com.AL.ui.validation.activity.SecurityVerificationValidator;
import com.AL.xml.pr.v4.ProductInfoType;
import com.AL.xml.v4.CustAddress;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.pr.v4.ProductType;

/**
 * @author rkiran
 *
 */
@ManagedResource
@Component
public class OrderQualValidationImpl {
	@Autowired
	private AddressValidator addressValidator;
	@Autowired
	private PhoneNumberValidator phoneNumberValidator;
	@Autowired
	private DemographicValidator demographicValidator;
	@Autowired
	private EMailAddressValidator eMailAddressValidator;
	@Autowired
	private SecurityVerificationValidator securityValidator;

	public void setAddressValidator(AddressValidator addressValidator) {
		this.addressValidator = addressValidator;
	}

	public void setPhoneNumberValidator(PhoneNumberValidator phoneNumberValidator) {
		this.phoneNumberValidator = phoneNumberValidator;
	}

	public void setDemographicValidator(DemographicValidator demographicValidator) {
		this.demographicValidator = demographicValidator;
	}

	public void seteMailAddressValidator(EMailAddressValidator eMailAddressValidator) {
		this.eMailAddressValidator = eMailAddressValidator;
	}

	public void setSecurityValidator(SecurityVerificationValidator securityValidator) {
		this.securityValidator = securityValidator;
	}

/*	public boolean supports(Class<?> clazz) {
		return OrderType.class.isAssignableFrom(clazz);
	}
*/
	public List<Errors> validateOrder(OrderType orderInfo, String gUID) {
		//OrderType orderInfo = (OrderType) target;
		List<Errors> errorsList = new ArrayList<Errors>();
		if(orderInfo == null){
			//errors.reject("orderInfo.null", "orderInfo object is null");
		}
		else{
			if (orderInfo.getLineItems().getLineItem() != null){
				for(LineItemType lineItemTypeInfo:orderInfo.getLineItems().getLineItem()){
					if (lineItemTypeInfo == null){
						//errors.reject("lineItemTypeInfo.null", "lineItemTypeInfo object is null");
					}
					else if(lineItemTypeInfo.getLineItemDetail() != null && lineItemTypeInfo.getLineItemDetail().getDetail() != null && lineItemTypeInfo.getLineItemDetail().getDetail().getProductLineItem() != null){
						
						//ProductType prodType = lineItemTypeInfo.getLineItemDetail().getDetail().getProductLineItem();
						
						//get the ProductInfoType object to get the product details
//						ProductInfoType prodDetails = ProductServiceUI.INSTANCE.getProduct(lineItemTypeInfo.getLineItemDetail().getDetail().getProductLineItem().getExternalId(), gUID);
						ProductInfoType prodDetails = null;
						String value = getSwitchCaseValue(prodDetails.getProduct());
						
						ValidationProductType prType = ValidationProductType.valueOf(value);
						
						switch (prType) {
						case uverse:
							validateUverseProduct(orderInfo,errorsList);
							break;
						case dsl:
							validateDslProduct(orderInfo,errorsList);
							break;
						case phone:
							validatePhoneProduct(orderInfo,errorsList);
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
			return "uverse";
		}
	}

	/**
	 * @param target
	 * @param errorsList 
	 */
	private void validatePhoneProduct(Object target, List<Errors> errorsList) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param target
	 * @param errorsList 
	 */
	private void validateDslProduct(Object target, List<Errors> errorsList) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param errorsList 
	 * @param target
	 */
	private void validateUverseProduct(OrderType order, List<Errors> errorsList) {
		//OrderType order = (OrderType) target;
		
		try{
			Errors addrErrors = null;
			//validate Customer Address
			for(CustAddress custAddr : order.getCustomerInformation().getCustomer().getAddressList().getCustomerAddress()){
				addrErrors = new BeanPropertyBindingResult(custAddr.getAddress(),"address");
				ValidationUtils.invokeValidator(addressValidator, custAddr.getAddress(), addrErrors);
			}
			
			//validate the demographic information of the customer
			Errors demoErrors = new BeanPropertyBindingResult(order.getCustomerInformation().getCustomer(),"customer");
			ValidationUtils.invokeValidator(demographicValidator, order.getCustomerInformation().getCustomer(), demoErrors);
			//validate the Phone numbers of the customer
			Errors phoneErrors = new BeanPropertyBindingResult(order.getCustomerInformation().getCustomer().getHomePhoneNumber(),"phoneNumber");
			ValidationUtils.invokeValidator(phoneNumberValidator, order.getCustomerInformation().getCustomer().getHomePhoneNumber(), phoneErrors);
			ValidationUtils.invokeValidator(phoneNumberValidator, order.getCustomerInformation().getCustomer().getWorkPhoneNumber(), phoneErrors);
			ValidationUtils.invokeValidator(phoneNumberValidator, order.getCustomerInformation().getCustomer().getCellPhoneNumber(), phoneErrors);
			
			Errors emailErrors = new BeanPropertyBindingResult(order.getCustomerInformation().getCustomer().getHomeEMail(),"email");
			ValidationUtils.invokeValidator(eMailAddressValidator, order.getCustomerInformation().getCustomer().getHomeEMail(), emailErrors);
			ValidationUtils.invokeValidator(eMailAddressValidator, order.getCustomerInformation().getCustomer().getWorkEMail(), emailErrors);
			
			Errors securityErrors = new BeanPropertyBindingResult(order.getCustomerInformation().getCustomer().getSecurityVerificationInfo(),"security");
			ValidationUtils.invokeValidator(securityValidator, order.getCustomerInformation().getCustomer().getSecurityVerificationInfo(), securityErrors);
			
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
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
