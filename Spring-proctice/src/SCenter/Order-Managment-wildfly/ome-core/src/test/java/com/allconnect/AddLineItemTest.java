/**
 *
 */
package com.AL;

import static org.junit.Assert.assertNotNull;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.AL.task.impl.TaskAddLineItem;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
 * @author ebthomas
 * 
 */
@RunWith( value = SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath:**/applicationContextTest.xml" } )
public class AddLineItemTest 
{

    @Autowired
    private TaskAddLineItem task;

    @Before
    public void setUp()
    {

    }

    @Test
    public void testLineItemAdd()
    {
    	OrderManagementRequestResponseDocument doc =   OrderManagementRequestResponseDocument.Factory.newInstance();
    	OrderManagementRequestResponse rrDoc = doc.addNewOrderManagementRequestResponse();
    	Request request = rrDoc.addNewRequest();
    	LineItemCollectionType newLineItemContainer = request.addNewNewLineItems();
    	LineItemType lit = newLineItemContainer.addNewLineItem();
    	lit.setLineItemNumber(1);
    	
    	System.out.println(doc.toString());
    	
        String inputXml1 = getXMLFromFile( "src\\main\\resources\\xml\\ome-addlineitem-1.xml" );

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
     * @param orderDocument
     *            Order Management Request Response Document
     */
    public void doTask( final OrderManagementRequestResponseDocument orderDocument )
    {

        assertNotNull( orderDocument );
        assertNotNull( orderDocument.getOrderManagementRequestResponse() );
        assertNotNull( orderDocument.getOrderManagementRequestResponse().getRequest() );

        task.execute( orderDocument );
    }
    
    
    /**
     * @param fileName name to get FileName
     * @return String contents
     */
    public static String getXMLFromFile( final String fileName )
    {
        File file = new File( fileName );
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;

        try
        {
            reader = new BufferedReader( new FileReader( file ) );
            String text = null;

            // repeat until all lines is read
            while ( ( text = reader.readLine() ) != null )
            {
                contents.append( text ).append( System.getProperty( "line.separator" ) );
            }

            return contents.toString();

        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if ( reader != null )
                {
                    reader.close();
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }

        return null;
    }

}
