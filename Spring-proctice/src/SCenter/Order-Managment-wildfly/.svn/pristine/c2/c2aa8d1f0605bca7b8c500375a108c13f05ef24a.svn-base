package com.AL.ome.ie.arbiter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.ie.service.strategy.ArbiterFlowManagerDefault;
import com.AL.ie.service.strategy.MotherObjectArbiter;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.TransientResponseContainerType;
import com.AL.xml.v4.TransientResponseType;
import com.AL.xml.v4.orderFulfillment.OrderFulfillmentResponseDocument;

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml",
		"classpath:**/arbiterContext.xml" })
@Configurable
public class GetTransientFromResponseTest implements ApplicationContextAware {

	private AtomicInteger counter = new AtomicInteger(10);

	@Autowired
	ArbiterFlowManagerDefault arbiter;

	MarshallOrder<SalesOrder> marshallOrder;

	@Autowired
	private ApplicationContext applicationContext;

	@Before
	public void setUp() {

		System.out.println("Start Arbiter Service Test");
	}

	@Test
	public void testFlow() throws Exception {

		String path = MotherObjectArbiter.path;
		String arbiterResponse = getXMLFromFile(path
				+ "src//main//resources//xml//arbiter//arbiter-1-response.xml");
		OrderFulfillmentResponseDocument doc = OrderFulfillmentResponseDocument.Factory
				.parse(arbiterResponse);
		assertNotNull(doc);
		List<OrderType> otList = doc.getOrderFulfillmentResponse()
				.getOrderManagementRequestResponse().getResponse()
				.getOrderInfoList();
		assertNotNull(otList);
		assertEquals(1, otList.size());
		// ********************************************************************************************************
		OrderType ot = otList.get(0);
		List<LineItemType> lineItemsToAggregate = ot.getLineItems()
				.getLineItemList();
		assertNotNull(lineItemsToAggregate);
		assertEquals(1,lineItemsToAggregate.size());
		

		LineItemType lit = lineItemsToAggregate.get(0);

		TransientResponseContainerType trct = lit.getTransientResponseContainer();
		assertNotNull(trct);
		
		TransientResponseType transientResponseType = trct.getTransientResponse();
	 
		assertNotNull(transientResponseType);
		
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
