package com.AL.ome.builder;

import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskCreateOrder;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
/**
 * @author ebthomas
 * 
 */
public class TaskCreateTest extends BaseALTestX
{
    /**
     * @throws Exception
     */
    public void testOrderIdPresent()
    {
        String inputXml1 = getXMLFromFile( "resources\\xml\\ome-createOrder-1.xml" );

        assertNotNull( inputXml1 );

        try
        {
            OrderManagementRequestResponseDocument orderDocument1;
            orderDocument1 = OrderManagementRequestResponseDocument.Factory.parse( inputXml1 );

            OrderManagementRequestResponseDocument response = doTask( orderDocument1 );

            assertNotNull( response );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    /**
     * @param orderDocument
     *            Order Document
     * @return
     */
    public OrderManagementRequestResponseDocument doTask( final OrderManagementRequestResponseDocument orderDocument )
    {
        assertNotNull( orderDocument );

        assertNotNull( orderDocument );
        assertNotNull( orderDocument.getOrderManagementRequestResponse() );
        assertNotNull( orderDocument.getOrderManagementRequestResponse().getRequest() );

        TaskCreateOrder task = new TaskCreateOrder();

        
        return task.execute( orderDocument );
    }
}
