/**
 *
 */
package com.AL.ome.builder;

import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskUpdateLineItemStatus;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
 * @author ebthomas
 * 
 */
public class TaskUpdateOrderStatusTest extends BaseALTestX
{

    /**
     * @throws Exception
     */
    public void testUpdateLineItemStatus( )
    {
        String inputXml1 = getXMLFromFile( "resources\\xml\\ome-updateOrderStatus-1.xml" );
        
        assertNotNull( inputXml1 );
        

        try
        { 
            doTask( OrderManagementRequestResponseDocument.Factory.parse( inputXml1 ) ); 
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    
    /**
     * @param orderDocument Order Management Request Response Document
     */
    public void doTask( final OrderManagementRequestResponseDocument orderDocument ) 
    {
         
        assertNotNull( orderDocument );
        assertNotNull( orderDocument.getOrderManagementRequestResponse() );
        assertNotNull( orderDocument.getOrderManagementRequestResponse().getRequest() );
          
        
        
        TaskUpdateLineItemStatus  task = new TaskUpdateLineItemStatus ();
         
        task.execute( orderDocument );
    }

}
