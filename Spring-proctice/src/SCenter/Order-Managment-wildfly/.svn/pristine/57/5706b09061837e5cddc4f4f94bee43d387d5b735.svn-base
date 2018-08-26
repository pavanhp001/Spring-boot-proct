package com.AL.ome.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.AL.BaseALTest;
import com.AL.service.VOrderMappingService;
import com.AL.V.beans.entity.VOrderMapping;
import com.AL.Vdao.dao.VOrderMappingDao;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

@RunWith(value = SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
@Transactional
/**
 * JUnit test to verify order submit response is being processed by VOrderMappingService correctly or not.
 * As this service is being used to insert V order mapping in om_rtim_order_mapping table. This service
 * should process order submit response(A7) for diff realtime provider like, ATT-STI, G2B etc.
 * @author PPatel
 *
 */
public class VOrderMappingServiceImplTestF {

	@Autowired
	private VOrderMappingService service;

	@Autowired
	private VOrderMappingDao mappingDao;

	private OrderManagementRequestResponseDocument successOrderDoc = null;

	private final String VOrderNo = "AC"+System.currentTimeMillis();

	private Long orderExtId = -1L;

	private Long liExtId = -1L;

	@Before
	public void setup(){
		//String successXml = BaseALTest.getXMLFromFile("src\\test\\resources\\xml\\att_order_submit_response_success.xml");
		//String successXml = BaseALTest.getXMLFromFile("src\\test\\resources\\xml\\harmony_dish_create_order_response.xml");
		//String successXml = BaseALTest.getXMLFromFile("src\\test\\resources\\xml\\harmony_g2b_submit_response.xml");
		String successXml = BaseALTest.getXMLFromFile("src\\test\\resources\\xml\\SYP_dish_submit_response.xml");

		assertNotNull(successXml);
		successXml = successXml.replaceAll("VORDERNO", VOrderNo);

		try {
			successOrderDoc = OrderManagementRequestResponseDocument.Factory.parse(successXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSaveMapping(){
		assertNotNull(successOrderDoc);
		assertNotNull(service);
		VOrderMapping savedMappingBean = service.processOrderMapping(successOrderDoc.xmlText());
		assertNotNull(savedMappingBean);
		VOrderMapping foundMappingBean = mappingDao.findByVOrderNoAndOrderExtIdAndLIExtId(savedMappingBean.getVOrderNo(), savedMappingBean.getOrderExtId(), savedMappingBean.getLiExtId());
		assertNotNull(foundMappingBean);
	}

//	@Test
//	public void testFindVOrderMapping(){
//		assertNotNull(successOrderDoc);
//		assertNotNull(service);
//		VOrderMapping mapping = mappingDao.findByVOrderNoAndOrderExtIdAndLIExtId("AC56798885", 15883L, 14929L);
//		assertNotNull(mapping);
//		assertTrue(mapping.getVOrderNo().equalsIgnoreCase("AC56798885"));
//	}
}
