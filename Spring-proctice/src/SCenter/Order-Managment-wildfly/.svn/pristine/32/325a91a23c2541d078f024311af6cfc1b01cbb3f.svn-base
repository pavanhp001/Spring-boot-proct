package com.AL.ome.builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.AL.ome.system.BaseALTestX;
import com.AL.task.impl.TaskGetOrderByConfirmationNumber;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import org.springframework.beans.factory.annotation.Autowired;
 
@RunWith( value = SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath:**/applicationContext.xml" } )
public class TaskGetOrderByConfirmationNumberTest extends BaseALTestX
{
	
 
	@Autowired
    private EntityManagerFactory em;
	
    /**
     * @throws Exception
     */
    
	@Test
    public void testRead( )
    {
        String inputXml1 = getXMLFromFile( "src\\test\\resources\\xml\\ome-getOrderbyConfirmNumber-1.xml" );
         
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
    
   
    public void doTask( final OrderManagementRequestResponseDocument orderDocument ) 
    {
        assertNotNull( orderDocument );
        
        assertNotNull( orderDocument );
        assertNotNull( orderDocument.getOrderManagementRequestResponse() );
        assertNotNull( orderDocument.getOrderManagementRequestResponse().getRequest() );
         
        
        TaskGetOrderByConfirmationNumber  task = new TaskGetOrderByConfirmationNumber();
         
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


	public EntityManagerFactory getEm() {
		return em;
	}


	public void setEm(EntityManagerFactory em) {
		this.em = em;
	}

}
