package com.AL.ome.functional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.ome.system.BaseALTestX;
import com.AL.V.beans.entity.SelectedDialogue;
import com.AL.vm.util.converter.unmarshall.UnmarshallSelectedDialogue;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderType;

/**
 * JUnit testcase to test unmarshalling functionality of active dialogue 
 *
*/

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class UnmarshallActiveDialogueTestF
{

	OrderManagementRequestResponseDocument order = null;
	Request request = null;
	OrderType orderInfoType = null;
	
	private UnmarshallSelectedDialogue unmarshallActiveDialogue;
	
	@Before
	public void setUp() {
		unmarshallActiveDialogue = new UnmarshallSelectedDialogue();
		String inputXml1 = BaseALTestX.getXMLFromFile("src\\main\\resources\\xml\\products_createOrder_refCustomer.xml");
		assertNotNull(inputXml1);

		try {
			order = OrderManagementRequestResponseDocument.Factory
					.parse(inputXml1);
			
			request = order.getOrderManagementRequestResponse().getRequest();
			orderInfoType = request.getOrderInfo();
			assertNotNull(order);
			assertNotNull(request);
			assertNotNull(orderInfoType);
			assertNotNull(orderInfoType.getLineItems());
			assertNotNull(unmarshallActiveDialogue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void testUnmarshallDialogue(){
//		List<LineItemType> lineItemTypeList = orderInfoType.getLineItems().getLineItemList();
//		assertNotNull( lineItemTypeList );
//		for(LineItemType srcLIType : lineItemTypeList){
//			Set<SelectedDialogue> activeDialoguesSet = unmarshallActiveDialogue.buildSelectedDialogue( srcLIType );
//			assertNotNull( activeDialoguesSet );
//			assertTrue(!activeDialoguesSet.isEmpty());
//		}
//	}
}
