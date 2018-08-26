package com.AL.util.messaging.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.xmlbeans.XmlOptions;
import com.AL.util.messaging.Broadcastable;

/**
 * @author ebthomas
 *
 * @param <T>
 *            Input Type
 */
public   class HttpBroadcastable   implements Broadcastable
{
    private static final String MULE_BROADCAST_URL = "http://sgdv1vm3:8802/services/orderManagement/broadcast";

    public static Broadcastable createDefault()
    {
        return new JMSBroadcastable();
    }

    public void broadcast( final String strToBroadcastOriginal, final Map<String, String> map )
    {

        if ( strToBroadcastOriginal == null )
        {
            return;
        }

    }

    /**
     * @param strToBroadcastOriginal
     *            Broadcast
     */
    public void broadcast( final String strToBroadcastOriginal  )
    {

        if ( strToBroadcastOriginal == null )
        {
            return;
        }

        String strToBroadcast = cleanBroadcastMessage( strToBroadcastOriginal );

        // Make the call out to the filtering servlet
        try
        {
            URL url = new URL( MULE_BROADCAST_URL );
            URLConnection conn = (URLConnection) url.openConnection();
            conn.setAllowUserInteraction( false );
            conn.setDoOutput( true );

            // Write the request as a URL-encoded page parameter
            StringBuilder sb = new StringBuilder( strToBroadcast );
            StringWriter sw = new StringWriter();
            XmlOptions options = new XmlOptions();
            options.setSavePrettyPrint();

            sb.append( "xml=" );
            sb.append( URLEncoder.encode( sw.toString(), "UTF-8" ) );
            conn.setRequestProperty( "Content-type", "application/x-www-form-urlencoded" );
            conn.setRequestProperty( "Content-length", Integer.toString( sb.length() ) );
            PrintWriter pw = new PrintWriter( conn.getOutputStream() );
            pw.print( sb.toString() );
            pw.flush();
            pw.close();
        }

        catch ( ConnectException ce )
        {
            System.out.println( MULE_BROADCAST_URL + " unable to connect: " + ce.getMessage() );
        }
        catch ( MalformedURLException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        catch ( Exception exc )
        {
            exc.printStackTrace();
        }

    }

    /**
     * @param data
     *            String to be cleaned
     * @return cleaned String
     */
    private String cleanBroadcastMessage( final String data )
    {
        StringBuilder sb = new StringBuilder();

        sb.append( " <?xml version=\"1.0\" encoding=\"UTF-8\"?> " );
        sb.append( " <v3:acMessage " );
        sb.append( " xmlns:v3=\"http://xml.AL.com/v3\" " );
        sb.append( " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " );
        sb.append( " xsi:schemaLocation=\"http://xml.AL.com/v3 " );
        sb.append( " ../xml-library/schemas/v3/acMessageWrapper.xsd \"> " );
        sb.append( " <msgType> request </msgType> " );
        sb.append( " <actionType> query </actionType> " );
        sb.append( data.replaceFirst( "<xml-fragment>", "" ).replaceFirst( "</xml-fragment>", "" ) );
        sb.append( " </v3:acMessage>" );

        return sb.toString();
    }

}
