 

/**
*
*/
package com.AL.ome.functional;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskJob;
import com.AL.task.impl.TaskUpdateLineItemStatus;
import com.AL.util.OmeSpringUtil;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.JobType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

@RunWith(value = SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public final class JobFindTestF   {

	@Autowired
	private ApplicationContext applicationContext;
	
	private static final OmeSpringUtil omeSpringUtil =  OmeSpringUtil.INSTANCE;

	@Before
	public void setUp() {
		
		 OmeSpringUtil.INSTANCE.setApplicationContext(applicationContext);

	}

	@Test
	public void testJobFind() throws Exception {
		String inputXml1 = BaseALTestX
				.getXMLFromFile("src\\test\\resources\\xml\\2012-06-20-job.xml");

		assertNotNull(inputXml1);

		 
			//findByContextStatus(OrderManagementRequestResponseDocument.Factory.parse(inputXml1));
			//findByExternalId(OrderManagementRequestResponseDocument.Factory.parse(inputXml1));
			//findByOrderLineItem (OrderManagementRequestResponseDocument.Factory.parse(inputXml1));
			
			//findByNextOffer(OrderManagementRequestResponseDocument.Factory.parse(inputXml1));
		//findByNextProcess(OrderManagementRequestResponseDocument.Factory.parse(inputXml1));
		
		update(OrderManagementRequestResponseDocument.Factory.parse(inputXml1));
	}

	 
	
	public void update(
			final OrderManagementRequestResponseDocument orderDocument) {
		assertNotNull(orderDocument);
		 
		

		Request request = orderDocument.getOrderManagementRequestResponse().getRequest();
		JobType jt = request.addNewJobs().addNewJob();
		jt.setExternalId("494");
		jt.setTtl(-1);
		jt.setLockedAt(Calendar.getInstance());
		jt.setLocked(Boolean.FALSE);
		 
		request.setJobAction(Request.JobAction.UPDATE );
		 
	  
		TaskJob task = (TaskJob) omeSpringUtil
				.getBean(TaskJob.class);

		OrderManagementRequestResponseDocument o = task.execute(orderDocument);
		String extId = o.getOrderManagementRequestResponse().getResponse().getJobs().getJobList().get(0).getExternalId();
		
		assertEquals(extId,"494");
	}
	
	
	
	public void findByNextProcess(
			final OrderManagementRequestResponseDocument orderDocument) {
		assertNotNull(orderDocument);
		String status = "processing_schedule_pending,hold_order_pending_problem,processing_problem_ordered_vru_failed,"+
		"hold_customer_action,hold_authorization_pending,hold_provider,hold_order,submit_failed"; 
		
		

		Request request = orderDocument.getOrderManagementRequestResponse().getRequest();
		JobType jt = request.addNewJobs().addNewJob();
		request.setJobAction(Request.JobAction.FIND_NEXT_AND_PROCESS);
		jt.setStatus(1000);
		jt.setContext("2314635");
		jt.setExternalId("0");
		jt.setTtl(30);
		jt.setStatusQueued(status);
		TaskJob task = (TaskJob) omeSpringUtil
				.getBean(TaskJob.class);

		task.execute(orderDocument);
	}
	
	
	public void findByNextOffer(
			final OrderManagementRequestResponseDocument orderDocument) {
		assertNotNull(orderDocument);
		String status = "processing_schedule_pending,hold_order_pending_problem,processing_problem_ordered_vru_failed,"+
		"hold_customer_action,hold_authorization_pending,hold_provider,hold_order,submit_failed"; 
		
		

		Request request = orderDocument.getOrderManagementRequestResponse().getRequest();
		JobType jt = request.addNewJobs().addNewJob();
		request.setJobAction(Request.JobAction.FIND_NEXT_AND_OFFER );
		jt.setStatus(1000);
		jt.setContext("2314635");
		jt.setExternalId("0");
		jt.setTtl(1);
		jt.setStatusQueued(status);
		TaskJob task = (TaskJob) omeSpringUtil
				.getBean(TaskJob.class);

		task.execute(orderDocument);
	}
	
	
	public void findByOrderLineItem(
			final OrderManagementRequestResponseDocument orderDocument) {
		assertNotNull(orderDocument);
		 
		

		Request request = orderDocument.getOrderManagementRequestResponse().getRequest();
		JobType jt = request.addNewJobs().addNewJob();
		 
		jt.setContext("2314635");
		jt.setResourceExternalId("5913");
		jt.setParentExternalId("7385");
		
		 
		request.setJobAction(Request.JobAction.FIND_BY_ORDER_LINE_ITEM );
		 
	  
		TaskJob task = (TaskJob) omeSpringUtil
				.getBean(TaskJob.class);

		OrderManagementRequestResponseDocument o = task.execute(orderDocument);
		String extId = o.getOrderManagementRequestResponse().getResponse().getJobs().getJobList().get(0).getExternalId();
		
		assertEquals(extId,"494");
	}
	
	
	public void findByExternalId(
			final OrderManagementRequestResponseDocument orderDocument) {
		assertNotNull(orderDocument);
		 
		

		Request request = orderDocument.getOrderManagementRequestResponse().getRequest();
		request.addNewJobs().addNewJob().setExternalId("494");
		 
		request.setJobAction(Request.JobAction.FIND_BY_EXTERNAL_ID );
		 
	  
		TaskJob task = (TaskJob) omeSpringUtil
				.getBean(TaskJob.class);

		OrderManagementRequestResponseDocument o = task.execute(orderDocument);
		String extId = o.getOrderManagementRequestResponse().getResponse().getJobs().getJobList().get(0).getExternalId();
		
		assertEquals(extId,"494");
	}
	
	 
	public void findByContextStatus(
			final OrderManagementRequestResponseDocument orderDocument) {
		assertNotNull(orderDocument);
		String status = "processing_schedule_pending,hold_order_pending_problem,processing_problem_ordered_vru_failed,"+
		"hold_customer_action,hold_authorization_pending,hold_provider,hold_order,submit_failed"; 
		
		

		Request request = orderDocument.getOrderManagementRequestResponse().getRequest();
		JobType jt = request.addNewJobs().addNewJob();
		request.setJobAction(Request.JobAction.FIND_BY_CONTEXT_STATUS );
		jt.setStatus(1000);
		jt.setContext("2314635");
		jt.setExternalId("0");
		jt.setStatusQueued(status);
		TaskJob task = (TaskJob) omeSpringUtil
				.getBean(TaskJob.class);

		task.execute(orderDocument);
	}
}
