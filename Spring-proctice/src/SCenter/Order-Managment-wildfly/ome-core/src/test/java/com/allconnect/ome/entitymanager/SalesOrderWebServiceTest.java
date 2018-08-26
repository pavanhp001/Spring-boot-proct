package com.AL.ome.entitymanager;

import org.junit.Test;
import com.AL.ome.system.BaseALTestX;
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
public class SalesOrderWebServiceTest extends BaseALTestX
{

    /**
     * @throws Exception
     *             Exception
     */
    @Test
    public void testServices() throws Exception
    {
        create();
        addlineItem();
        updateLineItemStatus();
        updateOrderStatus();
        getOrder();
        delete();
    }

    /**
     * @throws Exception
     *             Exception
     */
    public void delete() throws Exception
    {
        String inputXml = getXMLFromFile( "resources\\xml\\ome-delete-1.xml" );
        assertNotNull( inputXml );

        OrderManagementRequestResponseDocument document = OrderManagementRequestResponseDocument.Factory.parse( inputXml );
        TaskDelete deleteServiceTask = new TaskDelete();
       
        OrderManagementRequestResponseDocument response = deleteServiceTask.execute( document );

        assertNotNull( response );
    }

    /**
     * @throws Exception
     *             Exception
     */
    public void getOrder() throws Exception
    {
        String inputXml = getXMLFromFile( "resources\\xml\\ome-getOrder-1.xml" );
        assertNotNull( inputXml );

        OrderManagementRequestResponseDocument document = OrderManagementRequestResponseDocument.Factory.parse( inputXml );
        TaskGetOrder taskAddLineItem = new TaskGetOrder();
         
        OrderManagementRequestResponseDocument response = taskAddLineItem.execute( document );

        assertNotNull( response );
    }

    /**
     * @throws Exception
     *             Exception
     */
    public void updateOrderStatus() throws Exception
    {
        String inputXml = getXMLFromFile( "resources\\xml\\ome-updateOrderStatus-1.xml" );
        assertNotNull( inputXml );

        OrderManagementRequestResponseDocument document = OrderManagementRequestResponseDocument.Factory.parse( inputXml );
        TaskUpdateLineItemStatus taskAddLineItem = new TaskUpdateLineItemStatus();
        
        OrderManagementRequestResponseDocument response = taskAddLineItem.execute( document );

        assertNotNull( response );
    }

    /**
     * @throws Exception
     *             Exception
     */
    public void updateLineItemStatus() throws Exception
    {
        String inputXml = getXMLFromFile( "resources\\xml\\ome-updateLineItemStatus-1.xml" );
        assertNotNull( inputXml );

        OrderManagementRequestResponseDocument document = OrderManagementRequestResponseDocument.Factory.parse( inputXml );
        TaskUpdateLineItemStatus taskAddLineItem = new TaskUpdateLineItemStatus();
         
        OrderManagementRequestResponseDocument response = taskAddLineItem.execute( document );

        assertNotNull( response );
    }

    /**
     * @throws Exception
     *             Exception
     */
    public void addlineItem() throws Exception
    {
        String inputXml = getXMLFromFile( "resources\\xml\\ome-addlineitem-1.xml" );
        assertNotNull( inputXml );

        OrderManagementRequestResponseDocument document = OrderManagementRequestResponseDocument.Factory.parse( inputXml );
        TaskAddLineItem taskAddLineItem = new TaskAddLineItem();
         
        OrderManagementRequestResponseDocument response = taskAddLineItem.execute( document );

        assertNotNull( response );
    }

    /**
     * @throws Exception
     *             Exception
     */
    public void create() throws Exception
    {
        String inputXml = getXMLFromFile( "resources\\xml\\ome-createOrder-1.xml" );
        assertNotNull( inputXml );

        OrderManagementRequestResponseDocument document = OrderManagementRequestResponseDocument.Factory.parse( inputXml );
        TaskCreateOrder taskCreate = new TaskCreateOrder();
         
        OrderManagementRequestResponseDocument response = taskCreate.execute( document );

        assertNotNull( response );
    }

}
