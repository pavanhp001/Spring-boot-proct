package com.AL.ome.marshall;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.ome.system.BaseALTestX;
import com.AL.V.beans.entity.CustomSelection;
import com.AL.vm.util.converter.unmarshall.UnmarshallCustomSelection;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderType;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class UnmarshallCustomSelectionTest
{

	OrderManagementRequestResponseDocument order = null;
	Request request = null;
	OrderType orderInfoType = null;

	@Before
	public void setUp() {
		String inputXml1 = BaseALTestX.getXMLFromFile("src\\test\\resources\\xml\\products_createOrder_refCustomer.xml");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	@Test
//	public void testCustomSelection(){
//		List<LineItemType> lineItemTypeList = orderInfoType.getLineItems().getLineItemList();
//		assertNotNull( lineItemTypeList );
//		for(LineItemType srcLIType : lineItemTypeList){
//			Set<CustomSelection> customSelectionSet = UnmarshallCustomSelection.buildCustomSelection( srcLIType );
//			assertNotNull( customSelectionSet );
//			assertTrue(!customSelectionSet.isEmpty());
//			for(CustomSelection cs : customSelectionSet){
//				System.out.println(" [ " + cs.getSelectionExtId() +" , "+ cs.getParentChoiceExtId() + "  , " + cs.getChoiceExtId() + "  ,  "+ cs.getChoiceDetail() + " ]");
//			}
//		}
//	}
}
