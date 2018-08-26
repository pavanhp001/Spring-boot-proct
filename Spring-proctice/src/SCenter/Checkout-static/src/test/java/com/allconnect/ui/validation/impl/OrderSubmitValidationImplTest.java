/**
 * 
 */
package com.AL.ui.validation.impl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.AL.ui.service.V.OrderService;
import com.AL.xml.v4.OrderType;

/**
 * @author spatil
 *
 */
public class OrderSubmitValidationImplTest {
	private OrderSubmitValidationImpl orderSubmitValidationImpl = null;
	
	public OrderType getOrderByOrderId(String orderId){
		return OrderService.INSTANCE.getOrderByOrderNumber(orderId);
	}	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		orderSubmitValidationImpl = new OrderSubmitValidationImpl();		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void testOrderSubmitValidation(){
		OrderType order = getOrderByOrderId("13021");
		Errors errors = new BeanPropertyBindingResult(order, "order");
		
		orderSubmitValidationImpl.validateOrder(order);
		
		assertTrue(errors.hasErrors() == false);
		
		
	}	
}
