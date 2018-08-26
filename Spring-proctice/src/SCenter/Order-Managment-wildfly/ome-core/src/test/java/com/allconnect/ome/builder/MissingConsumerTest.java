package com.AL.ome.builder;

 
import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskCreateOrder;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
 * @author ebthomas
 * 
 */
public class MissingConsumerTest extends BaseALTestX
{

    /**
     * @throws Exception
     */
    public void testUpdateLineItemStatus( )
    {
        String inputXml1 = getXMLFromFile( "resources\\xml\\ome-createOrder-1.xml" );
         
        assertNotNull( inputXml1 );
        
        try
        {
            OrderManagementRequestResponseDocument orderDocument1;
            orderDocument1 = OrderManagementRequestResponseDocument.Factory.parse( inputXml1 );
             
            doTask( orderDocument1 );
            
           
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    
    /**
     * @param orderDocument Order Document
     */
    public void doTask( final OrderManagementRequestResponseDocument orderDocument ) 
    {
        assertNotNull( orderDocument );
        
        assertNotNull( orderDocument );
        assertNotNull( orderDocument.getOrderManagementRequestResponse() );
        assertNotNull( orderDocument.getOrderManagementRequestResponse().getRequest() );
         
        TaskCreateOrder task = new TaskCreateOrder(); 
        task.execute( orderDocument );
    }

}
