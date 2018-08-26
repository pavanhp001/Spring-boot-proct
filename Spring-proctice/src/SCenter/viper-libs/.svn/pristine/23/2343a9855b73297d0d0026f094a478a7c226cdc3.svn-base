package com.A.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
import java.io.IOException;

/**
 * 
 * @author ssathiyanarayanan
 * 
 */
public final class AProperties
{
    private static Logger logger = Logger.getLogger( AProperties.class );
    private static String str;
    private static String file;

    /**
     * Default constructor.
     */
    private AProperties()
    {
        // Default Constructor
    }

    /**
     * Function to set name of property file.
     * 
     * @param fileName to be set
     */
    public static void setPropertyFile( final String fileName )
    {
        file = fileName + ".properties";

    }

    /**
     * Function returns property file name.
     * 
     * @return String file name.
     */
    public static String getPropertyFile()
    {

        return file;

    }

    /**
     * This function returns the property value for a given key.
     * 
     * @param key
     *            to be set
     * 
     * @return String of property value
     */
    public static String getProperty( final String key )
    {
        FileInputStream in = null;
        File f;
        try
        {
            f = new File( System.getProperty( "jboss.server.home.dir" ) 
                    + "/deploy/serviceability.ear/resources/AProperties.properties" );
            if ( f.exists() )
            {
                Properties pro = new Properties();
                in = new FileInputStream( f );
                pro.load( in );
                str = pro.getProperty( key );               
                in.close();
            }
            else
            {
                //LogUtil.log( LogLevelEnum.info, logger, "Properties File " + f.getAbsolutePath() + " not found!" );
                logger.error( "Properties File " + f.getAbsolutePath() + " not found!" );
            }

        }
        catch ( IOException e )
        {
            if ( in != null )
            {
                try
                {
                    in.close();
                }
                catch ( IOException e1 )
                {
                    logger.error( "Properties File error in exception handler" );
                }
            }
             
            logger.error( e.getMessage() + ":Error reading properties file", e );
        }
        return str;
    }

}
