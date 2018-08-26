package com.AL.ome.functional;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskUpdateLineItemStatusLW;
import com.AL.util.OmeSpringUtil;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class TaskUpdateLineItemStatusLWTestF {

	@Autowired
	private ApplicationContext applicationContext;

	private static final OmeSpringUtil omeSpringUtil =  OmeSpringUtil.INSTANCE;

	@Before
	public void setUp() {
		
		 OmeSpringUtil.INSTANCE.setApplicationContext(applicationContext);
	}
	
	@Test
	public void testUpdateLineItemStatus(){
		/*OrderManagementRedefinedService orderService = (OrderManagementRedefinedService)SpringUtilTest.getBean(OrderManagementRedefinedService.class);
		
		OrchestrationContext params = OrchestrationContext.Factory.createOme();
		
		params.add(TaskContextParamEnum.orderId.name(),Long.valueOf(200048760));
		params.add(TaskContextParamEnum.lineItemExtId.name(),Long.valueOf(200037100));
		params.add(TaskContextParamEnum.status.name(),"hold_order");
		params.add(TaskContextParamEnum.source.name(),"V");
		params.add(TaskContextParamEnum.transactionType.name(), "updateLineItemStatusEP");
		params.add(TaskContextParamEnum.agentId.name(),"external");
		
		orderService.updateLineitemStatus(params);*/
		
    	String inputXml1 = BaseALTestX.getXMLFromFile( "src\\test\\resources\\xml\\91898.xml" );

        assertNotNull( inputXml1 );

        OrderManagementRequestResponseDocument doc = null;

        try{
            doc = doTask( OrderManagementRequestResponseDocument.Factory.parse( inputXml1 ) );
        }
        catch ( Exception e ){
            e.printStackTrace();
        }

        assertNotNull(doc);

        System.out.println(doc.toString());
	}
	
	 /**
     * @param orderDocument
     *            Order Management Request Response Document
     */
    public OrderManagementRequestResponseDocument doTask( final OrderManagementRequestResponseDocument orderDocument )
    {
        assertNotNull( orderDocument );

        assertNotNull( orderDocument );
        assertNotNull( orderDocument.getOrderManagementRequestResponse() );
        assertNotNull( orderDocument.getOrderManagementRequestResponse().getRequest() );

        TaskUpdateLineItemStatusLW task = (TaskUpdateLineItemStatusLW)omeSpringUtil.getBean(TaskUpdateLineItemStatusLW.class);


        OrderManagementRequestResponseDocument doc = task.execute( orderDocument );

        return doc;
    }
}
