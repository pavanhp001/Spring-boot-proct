package com.A.V.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;



/**
 * Contains all the status codes  
 * used throughout V Enterprise Services.
 * 
 * IMPORTANT: When adding values to this file, 
 * please APPEND to the end of the enum, and 
 * minimize enum name changes.
 * 
 * 
 * @author klyons
 *
 */
public class Status
{   
    
    private static Logger logger = Logger.getLogger( Status.class );
    
    private static String propFilelocationInJar = "META-INF/StatusCodes.properties";
    private static InputStream istream;
    private static Properties statusProperties;
    
    static 
    {
        try 
        {
            istream = Status.class.getClassLoader()
                        .getResourceAsStream( propFilelocationInJar );                        

            if ( istream != null )
            {
                statusProperties = new Properties();
                statusProperties.load( istream );
            }
            else
            {
                logger.error( propFilelocationInJar + " not found!" );
            }
        }
        catch( Exception e) 
        {
            logger.error( "Error Loading Properties File: " + propFilelocationInJar );
        }
        
        finally 
        {
            if ( istream != null )
            {
                try
                {
                    istream.close();
                }
                catch ( IOException e )
                {
                    logger.error( "Error Closing InputStream: " +  propFilelocationInJar );
                }
            }
        }        
    }
    
    /**
     * Pricing service status codes
     * 
     */ 
    public static enum Pricing
    {
        // Pricing General WS Statuses
        INFO_ORDER_PRICED_SUCCESSFULLY( getStatus("PRICING.INFO_ORDER_PRICED_SUCCESSFULLY") ),
        WARN_ORDER_PARTIALLY_PRICED( getStatus("PRICING.WARN_ORDER_PARTIALLY_PRICED") ),        
        WARN_ORDER_NONE_PRICED( getStatus("PRICING.WARN_ORDER_NONE_PRICED") ),
        ERROR_ORDERS_PARTIALLY_PRICED( getStatus("PRICING.WARN_ORDER_PARTIALLY_PRICED") ),     
        ERROR_ORDER_NONE_PRICED( getStatus("PRICING.FATAL_REQUEST_FAILED") ), 
        FATAL_REQUEST_FAILED( getStatus("PRICING.FATAL_REQUEST_FAILED") ),
        EMPTY_STATUS( getStatus("PRICING.EMPTY_STATUS") ),
        XML_REQUEST_VALID( getStatus("PRICING.EMPTY_STATUS") ),
                                
        // Pricing Status Codes    
        LINE_ITEM_VALID( getStatus("PRICING.LINE_ITEM_VALID") ), 
        LINE_ITEM_INVALID( getStatus("PRICING.LINE_ITEM_INVALID") ),
        ORDER_PRICED_SUCCESSFULLY( getStatus("PRICING.ORDER_PRICED_SUCCESSFULLY") ),
        ORDER_PRICED_UNSUCCESSFULLY( getStatus("PRICING.ORDER_PRICED_UNSUCCESSFULLY") );
            
        private String code;
        
        public String getCode() 
        {
            return code;
        }
        
        private Pricing( final String value ) 
        {
            code = value;
        }
        
    }
    
    /**
     * Pricing service status codes
     * 
     */ 
    public static enum CCP
    {
        // CCP General WS Statuses          
        FATAL_REQUEST_FAILED( getStatus("CCP.FATAL_REQUEST_FAILED") ),
        EMPTY_STATUS( getStatus("CCP.EMPTY_STATUS") ),
        XML_REQUEST_VALID( getStatus("CCP.EMPTY_STATUS") ),
        
        // CCP Status Codes    
        COMM_EVENT_SUCCESSFUL( getStatus("CCP.COMM_EVENT_SUCCESSFUL") ),
        COMM_EVENT_FAILED( getStatus("CCP.COMM_EVENT_FAILED") ),
        COMM_NOT_REQUIRED( getStatus("CCP.COMM_NOT_REQUIRED") ),
        FEEDBACK_PROCESSED_OK( getStatus("CCP.FEEDBACK_PROCESSED_OK") ),
        FEEDBACK_NOT_PROCESSED( getStatus("CCP.FEEDBACK_NOT_PROCESSED") ),
        EMAILCONTENT_PERSIST_ERROR( getStatus("CCP.EMAILCONTENT_PERSIST_ERROR") );
            
        private String code;
        
        public String getCode() 
        {
            return code;
        }

        private CCP( final String value ) 
        {
            code = value;
        }        
    }
        
    /**
     * The enumerated type to define the restricted set of 
     * values for the status code types in the
     * DB.
     *       */
    public enum Type 
    {
        V, OME, Pricing, Details
    }    
       
    private static String getStatus( final String statusName ) 
    {  
        try 
        {
            String value = null;            
            value = statusProperties.getProperty( statusName );
            
            if ( value != null ) 
            {
                return value.trim();
            }
        }
        catch( Exception e ) 
        {
            logger.error( "Error retrieving status property : " +  statusName );
        }
        
        return null;        
    }
    
}