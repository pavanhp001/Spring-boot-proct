package com.A.V.jms;

 
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.A.ui.builder.TransientResponseBuilder;
import com.A.ui.service.V.OrderService;
import com.A.xml.v4.OrderType;
import com.A.xml.v4.ProviderLineItemStatusType;
 

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/V-client-api-app-context.xml" })
public class JMSOrderQualificationTest {
	
	private String ORDER_EXT_ID = "6524";
	private Long LINE_ITEM_EXT_ID = 5919L;

	@Test
	public void testOrderQualJMS() throws Exception {

		OrderType order = OrderService.INSTANCE.getOrderByOrderNumber(ORDER_EXT_ID);

		assertNotNull(order);
		List<Long> liExtIdList = new ArrayList<Long>();
		liExtIdList.add(LINE_ITEM_EXT_ID);
		
		order = OrderService.INSTANCE.orderQualification("default",order,liExtIdList);
		 
		assertNotNull(order);
		assertEquals(Long.valueOf(ORDER_EXT_ID).longValue(),order.getExternalId());
		
		
		boolean isError = TransientResponseBuilder.INSTANCE.isError(order, order.getLineItems().getLineItem().get(0).getExternalId());
		assertEquals(Boolean.TRUE, isError);
		
		
		ProviderLineItemStatusType status = TransientResponseBuilder.INSTANCE.getTransientResponseStatus(order, order.getLineItems().getLineItem().get(0).getExternalId());
		assertEquals("ERROR", status.getProcessingStatusCode());
		
		for (String reason:status.getReason()) {
			assertNotNull(reason);
		}
	}
	
	

	 
}
 