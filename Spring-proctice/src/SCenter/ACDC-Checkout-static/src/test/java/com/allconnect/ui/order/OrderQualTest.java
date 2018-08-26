package com.AL.ui.order;

import java.util.Arrays;

import org.junit.Test;

import com.AL.ui.service.V.OrderService;
import com.AL.xml.v4.OrderType;

public class OrderQualTest {
	
	@Test
	public void testOrderQual() {
		OrderType orderType = OrderService.INSTANCE.getOrderByOrderNumber("17214");
		orderType.getCustomerInformation().getCustomer().setProviderCustomerType("NEW");
		orderType.getCustomerInformation().getCustomer().setSsn("234892345");
		System.out.println("************ "+orderType.getLineItems().getLineItem().get(0).getExternalId());
		OrderType updatedOrder = OrderService.INSTANCE.orderQualification("default", orderType, 
				Arrays.asList(17779L), "6fc5a5d8-0e51-11e2-99a3-ad952dddb39e");
		System.out.println(updatedOrder.getLineItems().getLineItem().get(0).getTransientResponseContainer().getTransientResponse().
				getProviderLineItemStatus().getReason().get(0));
	}

}
