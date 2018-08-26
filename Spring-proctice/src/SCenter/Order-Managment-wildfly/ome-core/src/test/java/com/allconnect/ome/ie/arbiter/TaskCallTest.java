package com.AL.ome.ie.arbiter;

 



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.AL.ie.ws.impl.IntegratedEngineWSHandler;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.TransactionType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/spring/arbiterContext.xml"   })
@Configurable
public class TaskCallTest implements ApplicationContextAware {

	@Autowired
	IntegratedEngineWSHandler handler;
	 
	private ApplicationContext applicationContext;

	@Before
	public void setUp() {

		System.out.println("Test Setup");
	}

	@Test
	public void testHandler() throws Exception {
		TransactionType.Enum e = TransactionType.ORDER_QUALIFICATION;


		OrderManagementRequestResponseDocument doc = OrderManagementRequestResponseDocument.Factory.newInstance();
		OrderManagementRequestResponse reqRes = doc.addNewOrderManagementRequestResponse();
		reqRes.setTransactionType(e);
		Request req = reqRes.addNewRequest();
		
		handler.execute(doc);
		
		
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}