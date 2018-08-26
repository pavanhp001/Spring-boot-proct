package com.AL.ome.audit;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.ome.system.SimpleBaseALTestBase;
import com.AL.util.audit.AuditService;
import com.AL.V.beans.entity.OrderAudit;
import com.AL.Vdao.dao.AuditDao;

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class AuditOrderTest extends SimpleBaseALTestBase {
 
	 
	@Autowired
	private AuditDao<OrderAudit> dao;
	
	@Autowired
	private AuditService<OrderAudit> auditService;
	
	
	/**
	 * @throws Exception
	 */

	public void setUp() throws Exception {

	}
	
	
	@Test
	public void testServiceAudit() throws Exception {
	  
//		assertNotNull(auditService);
//		  
//		OrderAudit orderAudit =  createOrderAudit("testServiceAudit");
//		assertTrue(auditService.audit(  orderAudit));
		
	}

	
	
	@Test
	public void testDaoAudit() throws Exception {
 
		 
//		OrderAudit orderAudit =  createOrderAudit("testDao");
// 
//		assertTrue(dao.audit(  orderAudit));

	}
	 

	public OrderAudit createOrderAudit(final String description)
	{
		OrderAudit orderAudit = new OrderAudit();
		orderAudit.setAuditDate(Calendar.getInstance());
		//orderAudit.setAgentId(1);
		orderAudit.setDescription(description) ;
		orderAudit.setFromReasonCode("fromReasonCode");
		orderAudit.setFromStatusCode("fromStatusCode");
		orderAudit.setToStatusCode("toStatusCode");
		orderAudit.setToReasonCode("toReasonCode");
		orderAudit.setId( 0 );
		orderAudit.setDetail( "detail" );
		orderAudit.setLineItemNumber( 1 );
		orderAudit.setOrderId( "orderId" );
		 
		
		return orderAudit;
	}
 

	public AuditDao<OrderAudit> getDao() {
		return dao;
	}


	public void setDao(AuditDao<OrderAudit> dao) {
		this.dao = dao;
	}


	public AuditService<OrderAudit> getAuditService() {
		return auditService;
	}


	public void setAuditService(AuditService<OrderAudit> auditService) {
		this.auditService = auditService;
	}

 
	

}
