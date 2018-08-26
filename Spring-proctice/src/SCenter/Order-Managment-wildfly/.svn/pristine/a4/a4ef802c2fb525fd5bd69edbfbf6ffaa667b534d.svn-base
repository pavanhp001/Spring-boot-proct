package com.AL.ome.functional;

 
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskCreateOrder;
import com.AL.task.impl.TaskSubmit;
import com.AL.util.SpringUtilTest;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
* @author ebthomas
* 
*/
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
@Transactional(value = "transactional")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class CreateSubmitOrderTestF {

	// @Autowired
	// private EntityManagerFactory entityManagerFactory;

	@Before
	public void setUp() {

	}
	
 

	@Test
	public void testRead() {
		String inputXml1 = BaseALTestX
				.getXMLFromFile("/Users/ethomas/Documents/workspace-sts-3.0.0.RELEASE/OrderManagement/ome-core/src/test/resources/xml/customer/2012-10-03-create-customer.xml");
		 

		assertNotNull(inputXml1);

		try {
			OrderManagementRequestResponseDocument orderDocument1;
			orderDocument1 = OrderManagementRequestResponseDocument.Factory
					.parse(inputXml1);

			doTask(orderDocument1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param orderDocument
	 *            Order Document
	 */
	public void doTask(
			final OrderManagementRequestResponseDocument orderDocument) {
		assertNotNull(orderDocument);

		assertNotNull(orderDocument);
		assertNotNull(orderDocument.getOrderManagementRequestResponse());
		assertNotNull(orderDocument.getOrderManagementRequestResponse()
				.getRequest());

		TaskCreateOrder task = (TaskCreateOrder) SpringUtilTest
				.getBean(TaskCreateOrder.class);

		OrderManagementRequestResponseDocument doc = task
				.execute(orderDocument);
		System.out.println(doc.toString());

	}

}
