package com.AL.ome.ws;

import org.junit.Test;
import com.AL.ome.system.BaseALTestX;
import com.AL.xml.v4.OrderDocument;
import com.AL.xml.v4.OrderType;

/**
 * @author ebthomas
 * 
 */
public class ProcessRequestTest extends BaseALTestX
{

    /**
     * @throws Exception
     */
    @Test
    public void testAbilityToConvertXML()
    {

        try
        {
            String inputXml = getXMLFromFile( "C:\\projects\\ome\\resources\\xml\\sample_order_1.xml" );

            OrderDocument orderDocument = OrderDocument.Factory.parse( inputXml );
            assertNotNull( orderDocument );

            OrderType order = orderDocument.getOrder();
            assertNotNull( order );

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

}
