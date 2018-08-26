/**
 * 
 */
package com.AL.ui.validation.impl;

import static com.AL.ui.constants.Constants.GUID;
import static com.AL.ui.constants.Constants.SALES_CONTEXT_ENTITY;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.validation.activity.DemographicValidator;
import com.AL.ui.validation.activity.EMailAddressValidator;
import com.AL.ui.validation.activity.PhoneNumberValidator;
import com.AL.ui.validation.activity.SecurityVerificationValidator;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;

/**
 * @author ganesh
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-CKO-att-sti-app-context.xml" })
public class OrderQualValidationImplTest {
	@Autowired
	private OrderQualValidationImpl orderQualValidationImpl = null;
	
	@Before
	public void setup(){
		//orderQualValidationImpl = new OrderQualValidationImpl();
	}
	
	public OrderManagementRequestResponse getOrderByOrderId(String orderId){
		OrderManagementRequestResponse orderRequestResponse = OrderService.INSTANCE
				.getOrderManagementRequestResponseByOrderNumber(orderId);
		return orderRequestResponse;
	}
	
	@Test
	public void testOrderQualValidation(){
		OrderManagementRequestResponse response = getOrderByOrderId("17602");
		//Errors errors = new BeanPropertyBindingResult(order, "order");
		String GUID = getGUID(response.getResponse().getSalesContext());
		List<Errors> errors = orderQualValidationImpl.validateOrder(response.getResponse().getOrderInfo().get(0),GUID);
		
		assertTrue(errors.size()  > 0 == false);
	}
	
	public String getGUID(SalesContextType salesContext) {
		String guid = "";
		if(salesContext != null) {
			for(SalesContextEntityType entity : salesContext.getEntity()) {
				if(SALES_CONTEXT_ENTITY.equalsIgnoreCase(entity.getName())) {
					for(NameValuePairType pair : entity.getAttribute()) {
						if(GUID.equalsIgnoreCase(pair.getName())) {
							guid = pair.getValue();
						}
					}	
				}
			}
		}
		return guid;
	}

}
