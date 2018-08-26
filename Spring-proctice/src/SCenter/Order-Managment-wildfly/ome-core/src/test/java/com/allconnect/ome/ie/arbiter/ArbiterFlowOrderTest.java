package com.AL.ome.ie.arbiter;

import static org.junit.Assert.assertNotNull;
import java.util.Random;
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
import com.AL.ie.service.strategy.ArbiterFlow;
import com.AL.ie.service.strategy.ArbiterFlowManagerDefault;
import com.AL.ome.system.BaseALTestX;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml","classpath:**/arbiterContext.xml"  })
@Configurable
public class ArbiterFlowOrderTest implements ApplicationContextAware {

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

		 
		Runnable basic = new RunBasicThread();
		for (int i = 0; i < counter.get(); i++) {
			System.out.println("Launching Thread#"+i);
			new Thread(basic).start();
		}
		System.out.println("start processing task THREAD");

		 while (counter.getAndDecrement() > 0) {
			System.out.println("start sleep main THREAD...");
			Thread.sleep(600000);
			System.out.println("...");
	 	}
	}
	
 

	public void process() {
		Random random = new Random();
		int index = random.nextInt(123456789);
		String indexAsString = String.valueOf(index);
		ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> flow = arbiter
				.dispatch(getOMRRD(indexAsString));
		OrderManagementRequestResponseDocument doc = flow.getResponse();
		assertNotNull(doc);
		 
		
	}

	public OrderManagementRequestResponseDocument getOMRRD(final String number) {
		String inputXml1 = BaseALTestX
				.getXMLFromFile("src//main//resources//xml//arbiter//arbiter-1.xml");
		inputXml1 = inputXml1.replaceAll("ZZZZZZZZ", number);
		inputXml1 = inputXml1.replaceAll("YYYYYYYY", number);
		

		assertNotNull(inputXml1);

		try {
			OrderManagementRequestResponseDocument orderDocument1;
			orderDocument1 = OrderManagementRequestResponseDocument.Factory
					.parse(inputXml1);

			return orderDocument1;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	class RunBasicThread implements Runnable {

		RunBasicThread() {

		}

		// override run() method in interface
		public void run() {
			try {
				 
				process();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
