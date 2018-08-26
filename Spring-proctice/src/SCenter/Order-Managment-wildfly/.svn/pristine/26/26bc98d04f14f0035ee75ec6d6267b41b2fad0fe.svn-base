package com.AL.ome.builder;

import java.util.List;
import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskAddLineItem;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
 * @author ebthomas
 * 
 */
public class TaskAddLineItemTest extends BaseALTestX
{

    /**
     * @throws Exception
     */
    public void testUpdateLineItemStatus( )
    {
        String inputXml1 = getXMLFromFile( "resources\\xml\\ome-addlineitem-1.xml" );
    
        
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
        
        assertNotNull( orderDocument );
        assertNotNull( orderDocument.getOrderManagementRequestResponse() );
        assertNotNull( orderDocument.getOrderManagementRequestResponse().getRequest() );
        assertNotNull( orderDocument.getOrderManagementRequestResponse().getRequest().getNewLineItems() );
        //LineItemType lineItemType = orderDocument.getOrderManagementRequestResponse().getRequest().getNewLineItems();
        LineItemCollectionType liColl = orderDocument.getOrderManagementRequestResponse().getRequest().getNewLineItems();
        List<LineItemType> liList =liColl.getLineItemList();
        for ( LineItemType lineItemType : liList)
        {
            assertNotNull( lineItemType.getExternalId() );
            assertNotNull( lineItemType.getSvcAddressExtId() );
        }
        
        
        TaskAddLineItem task = new TaskAddLineItem();
        
        task.execute( orderDocument );
    }

}
