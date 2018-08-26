/**
 *
 */
package com.A.ome.system;


import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;



/**
 * @author ebthomas
 *
 */
public class ConvertXMLtoJSON extends BaseATestX
{
    public   void testIt() throws Exception {
        //InputStream is = 
        //        ConvertXMLtoJSON.class.getResourceAsStream("C:\\projects\\ome\\resources\\xml\\ome-createOrder-1.xml");
        //String xml = IOUtils.toString(is);
        
        String xml =  getXMLFromFile( "resources\\xml\\ome-createOrder-1.xml" );
        xml = xml.replaceAll( "\t", "" );
        xml = xml.replaceAll( "\n", "" );
        
        XMLSerializer xmlSerializer = new XMLSerializer(); 
        JSON json = xmlSerializer.read( xml );  
         
        System.out.println( json.toString(2) );
}

    
    

}

