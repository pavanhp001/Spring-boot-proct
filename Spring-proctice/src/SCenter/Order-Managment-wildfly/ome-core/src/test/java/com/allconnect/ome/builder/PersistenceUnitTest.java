package com.AL.ome.builder;

import com.AL.ome.system.BaseALTestX;
import com.AL.task.Task;
import com.AL.task.TaskBase;
import com.AL.task.impl.TaskAddLineItem;
import com.AL.task.impl.TaskCreateOrder;
import com.AL.task.impl.TaskDelete;
import com.AL.task.impl.TaskGetOrder;
import com.AL.task.impl.TaskUpdateLineItemStatus;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
 * @author ebthomas
 * 
 */
public class PersistenceUnitTest extends BaseALTestX
{

    /**
     * test.
     */
    public void testDoAddLineItem()
    {
        try
        {
            execute( "resources\\xml\\ome-addlineitem-1.xml", TaskAddLineItem.class );
            execute( "resources\\xml\\ome-createOrder-1.xml", TaskCreateOrder.class );
            execute( "resources\\xml\\ome-delete-1.xml", TaskDelete.class );
            execute( "resources\\xml\\ome-getOrder-1.xml", TaskGetOrder.class );
            execute( "resources\\xml\\ome-updateLineItemStatus-1.xml", TaskUpdateLineItemStatus.class );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    /**
     * @param filename
     *            filename
     * @param clazz
     *            class
     * @throws Exception
     *             Exception
     */
    protected void execute( final String filename,
            final Class< ? extends TaskBase<OrderManagementRequestResponseDocument>> clazz ) throws Exception
    {
        //assertNotNull( getEntityManagerReference() );

        String inputXml = getXMLFromFile( filename );
        assertNotNull( inputXml );

        OrderManagementRequestResponseDocument orderDocument = OrderManagementRequestResponseDocument.Factory.parse( inputXml );
        assertNotNull( orderDocument );

        Task<OrderManagementRequestResponseDocument> task = clazz.newInstance();

        
        task.execute( orderDocument );
    }

}
