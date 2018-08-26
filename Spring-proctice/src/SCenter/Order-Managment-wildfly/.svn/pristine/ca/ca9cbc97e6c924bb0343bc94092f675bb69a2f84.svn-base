/**
 *
 */
package com.AL.ome.builder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskUpdateLineItem;
import com.AL.util.SpringUtilTest;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;

/**
 * @author ebthomas
 * 
 */
@RunWith( value = SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath:**/applicationContextTest.xml" } )
public class TaskUpdateLineItemTest extends BaseALTestX
{

    /**
     * @throws Exception
     */
	@Test
    public void testUpdateLineItem( )
    {
        String inputXml1 = getXMLFromFile( "src\\main\\resources\\xml\\ome-updateLineItem-1.xml" );
        
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
         
        
        TaskUpdateLineItem task = (TaskUpdateLineItem)SpringUtilTest.getBean(TaskUpdateLineItem.class);;
         
        OrderManagementRequestResponseDocument result = task.execute( orderDocument );
        Response response = result.getOrderManagementRequestResponse().getResponse();
        assertNotNull(response);
    }

}
