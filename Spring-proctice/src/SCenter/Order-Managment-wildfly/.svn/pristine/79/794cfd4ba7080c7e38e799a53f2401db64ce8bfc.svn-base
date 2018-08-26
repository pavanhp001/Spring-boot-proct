/**
 *
 */
package com.AL.ome.functional;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskUpdateLineItemStatus;
import com.AL.util.OmeSpringUtil;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

@RunWith( value = SpringJUnit4ClassRunner.class )
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ContextConfiguration( locations = { "classpath:**/applicationContextTest.xml" } )
public final class TaskUpdateLineItemStatusTestF
{

	@Autowired
	private ApplicationContext applicationContext;

	private static final OmeSpringUtil omeSpringUtil =  OmeSpringUtil.INSTANCE;

	@Before
	public void setUp() {

		 OmeSpringUtil.INSTANCE.setApplicationContext(applicationContext);

	}

    @Test
    public void testUpdateOrderStatus()
    {
        //String inputXml1 = BaseALTestX.getXMLFromFile( "src\\test\\resources\\xml\\ome-updateLineItemStatus-1.xml" );
    	String inputXml1 = BaseALTestX.getXMLFromFile( "src\\test\\resources\\xml\\updatelistatus.xml" );


//        OrderManagementRequestResponseDocument d = OrderManagementRequestResponseDocument.Factory.newInstance();
//        Request r = d.addNewOrderManagementRequestResponse().addNewRequest();
//        //r.addNewLineitemId();
//        r.setLineItemId("123");

        //System.out.println(d.xmlText());

        assertNotNull( inputXml1 );

        OrderManagementRequestResponseDocument doc = null;

        try
        {
            doc = doTask( OrderManagementRequestResponseDocument.Factory.parse( inputXml1 ) );
        }
        catch ( Exception e )
        {
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

        TaskUpdateLineItemStatus task = (TaskUpdateLineItemStatus)omeSpringUtil.getBean(TaskUpdateLineItemStatus.class);


        OrderManagementRequestResponseDocument doc = task.execute( orderDocument );

        return doc;
    }
}
