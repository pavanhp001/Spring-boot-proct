package com.AL.ome.functional;

 

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskRules;
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
public class ValidateTestF {

	// @Autowired
	// private EntityManagerFactory entityManagerFactory;

	@Before
	public void setUp() {

	}

	@Test
	public void testRead() {
		String inputXml1 = BaseALTestX
				.getXMLFromFile("src\\test\\resources\\xml\\ome-validate-rules.xml");
		//inputXml1 = inputXml1.replaceAll("ZZZZZZZZ", "22");

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

		TaskRules task = (TaskRules) SpringUtilTest
				.getBean(TaskRules.class);

		OrderManagementRequestResponseDocument doc = task
				.execute(orderDocument);
		System.out.println(doc.toString());

	}

}
