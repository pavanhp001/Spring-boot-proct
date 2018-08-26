package com.AL.ome.ie.arbiter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import org.apache.xmlbeans.XmlException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.AL.ie.service.strategy.SplitStrategy;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderType;

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextEmpty.xml" })
@Configurable
public class ArbiterFlowSplitterTest implements ApplicationContextAware {

	public SplitStrategy splitStrategy = SplitStrategy.INSTANCE;

	private ApplicationContext applicationContext;

	@Before
	public void setUp() {

		System.out.println("Test Setup");
	}

	@Test
	public void testFlow() throws Exception {
		
 
		
		 
	}

	public OrderType getOrder()
	{
		String inputXml1 = getXMLFromFile("src\\test\\java\\com\\AL\\ome\\ie\\arbiter\\orderManagement.xml");
		 
		OrderManagementRequestResponseDocument oRRD = getOrderManagementRequestResponseDocument(inputXml1);
		 
		OrderManagementRequestResponse oRR = oRRD
				.getOrderManagementRequestResponse();
		Request oReq = oRR.getRequest();
		
		return oReq.getOrderInfo();
	}
	public void validateSplitting(Map<String, OrderType> map)
	{assertEquals(3, map.size());
	assertNotNull(map.get("VERIZON"));
	assertNotNull(map.get("ATT"));
	assertNotNull(map.get("DISH"));
	assertNull(map.get("abc-wrong"));
	
	assertEquals(2,map.get("VERIZON").getLineItems().getLineItemList().size());
	assertEquals(1,map.get("DISH").getLineItems().getLineItemList().size());
	assertEquals(2,map.get("ATT").getLineItems().getLineItemList().size());
	
//	assertEquals("ATT",map.get("ATT").getLineItems().getLineItemList().get(0).getProvider().getName());
//	assertEquals("VERIZON",map.get("VERIZON").getLineItems().getLineItemList().get(0).getProvider().getName());
//	assertEquals("DISH",map.get("DISH").getLineItems().getLineItemList().get(0).getProvider().getName());
}
	
	public OrderType  createAggregateTemplate(Map<String, OrderType> map) {
		
		if (map.values().size() == 0)
		{
			return null;
		}
		
		 OrderType order = map.values().iterator().next();
	 	
		/*OrderType resultOrder = ArbiterMarshaller.INSTANCE.clearLineItems(order);
	 

		Collection<OrderType> orderTypesWithLineItems = map.values();

		for (OrderType ot : orderTypesWithLineItems) {
			
			List<LineItemType> lineItemsToAggregate = ot.getLineItems().getLineItemList();
			
			resultOrder.getLineItems().getLineItemList().addAll(lineItemsToAggregate); 
		}*/

		return null;
	}
	
	

	/**
	 * @param inputXml
	 *            input xml received from the request
	 * @return RequestResponseDocument for OrderManagement
	 */
	public static OrderManagementRequestResponseDocument getOrderManagementRequestResponseDocument(
			final String inputXml) {
		OrderManagementRequestResponseDocument doc = null;

		try {
			doc = OrderManagementRequestResponseDocument.Factory
					.parse(inputXml);
		} catch (XmlException e) {
			e.printStackTrace();
		}
		return doc;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * @param fileName
	 *            name to get FileName
	 * @return String contents
	 */
	public static String getXMLFromFile(final String fileName) {
		File file = new File(fileName);
		StringBuffer contents = new StringBuffer();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;

			// repeat until all lines is read
			while ((text = reader.readLine()) != null) {
				contents.append(text).append(
						System.getProperty("line.separator"));
			}

			return contents.toString();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
