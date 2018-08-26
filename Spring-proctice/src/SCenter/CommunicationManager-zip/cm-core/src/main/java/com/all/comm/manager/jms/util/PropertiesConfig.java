/**
 *
 */
package com.AL.comm.manager.jms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;

public enum PropertiesConfig
{

    INSTANCE;

    Logger logger = Logger.getLogger( JMSConfigNamespace.class );

    public static String getProperty( final Properties prop, final String key )
    {

        if ( prop == null )
            loadProperties( FileUtil.getPropertiesFileName() );

        if ( prop != null )
        {
            String str = prop.getProperty( key );

            return str;
        }

        return null;
    }

    public static List<String> getPropertyInfo()
    {
        List<String> value = FileUtil.readListFromFile( FileUtil.getPropertiesFileName() );

        return value;
    }

    public static Properties loadProperties( String filename )
    {
        Properties prop = new Properties();

        FileInputStream fis = null;
        File file = new File( filename );

        if ( !file.exists() )
        {
            prop = null;
            return prop;
        }

        try
        {
            fis = new FileInputStream( file );

            prop.load( fis );
            // You can do something here like getting the value of a key. Example

        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( null != fis )
                try
                {
                    fis.close();
                }
                catch ( IOException e )
                { /* .... */
                }
        }

        return prop;

    }

    public void updateProperties( String filename, String key, String value, String comment )
    {
        Properties prop = new Properties();

        FileOutputStream fos = null;
        try
        {

            // Setting a key=value pair
            prop.setProperty( key, value );
            prop.store( ( fos = new FileOutputStream( filename ) ), comment );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( null != fos )
                try
                {
                    fos.close();
                }
                catch ( IOException e )
                { /* .... */
                }
        }

    }

}
