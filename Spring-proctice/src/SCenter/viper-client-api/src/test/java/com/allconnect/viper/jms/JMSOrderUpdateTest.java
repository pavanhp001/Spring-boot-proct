package com.A.V.jms;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.A.ui.service.V.OrderService;
import com.A.xml.v4.NameValuePairType;
import com.A.xml.v4.ObjectFactory;
import com.A.xml.v4.OrderManagementRequestResponse.Response;
import com.A.xml.v4.SalesContextEntityType;
import com.A.xml.v4.SalesContextType;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/V-client-api-app-context.xml" })
public class JMSOrderUpdateTest {

	@Test
	public void testGetOrderJMS() throws Exception {

		//TODO: Create New Order or Use Existing Known Order.
		final String id = "26508";
		
		final String guid = "ABC123";
		final String agentId = "default";
		
		SalesContextType salesContext1 = OrderService.INSTANCE.getSalesContext(id);
		assertNotNull(salesContext1);
		
		 
		ObjectFactory oFactory = new ObjectFactory();

		SalesContextType salesContext = oFactory.createSalesContextType();
		SalesContextEntityType e = oFactory.createSalesContextEntityType();
		e.setName("CKO");
		NameValuePairType nvpt = oFactory.createNameValuePairType();
		nvpt.setName("GUID");
		nvpt.setValue("ABCZYZ987");

		e.getAttribute().add(nvpt);
		salesContext.getEntity().add(e);

		Response response2 = OrderService.INSTANCE.updateSalesContext(agentId,
				id, salesContext, guid);
		assertNotNull(response2);
		assertNotNull(response2.getSalesContext());
 
		assertEquals("CKO", response2.getSalesContext().getEntity().get(0)
				.getName());
		assertEquals("GUID", response2.getSalesContext().getEntity().get(0)
				.getAttribute().get(0).getName());
		assertEquals("ABCZYZ987", response2.getSalesContext().getEntity()
				.get(0).getAttribute().get(0).getValue());

	}

}
