package com.A.V.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Contains all the reason codes used
 * throughout V Enterprise Services.
 * 
 * @author klyons
 *
 */
public class Reason
{
    private static Logger logger = Logger.getLogger( Reason.class );
    
    private static String propFilelocationInJar = "META-INF/ReasonCodes.properties";
    private static InputStream istream;
    private static Properties reasonProperties;
    
    static 
    {
        try 
        {
            istream = Reason.class.getClassLoader()
                        .getResourceAsStream( propFilelocationInJar );                        

            if ( istream != null )
            {
                reasonProperties = new Properties();
                reasonProperties.load( istream );
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
     * Pricing Reason Codes
     * 
     */ 
    public static enum Pricing 
    {       
        // Pricing Reason Codes - General Issues
        GUID_MISSING( getReason("PRICING.GUID_MISSING") ),
        UNKNOWN_TRANSACTION_TYPE( getReason("PRICING.UNKNOWN_TRANSACTION_TYPE") ),
        INVALID_XML_REQUEST( getReason("PRICING.INVALID_XML_REQUEST") ),        
        DETAIL_SERVICE_UNAVAILABLE( getReason("PRICING.DETAIL_SERVICE_UNAVAILABLE") ),      
        DB_CONNECTION_FAILURE( getReason("PRICING.DB_CONNECTION_FAILURE") ),
        EMPTY_REASON( getReason("PRICING.EMPTY_REASON") ),
        UNKNOWN_REASON( getReason("PRICING.UNKNOWN_REASON") ),       
        
        // Pricing Reason Codes - LineItem Issues    
        LI_DELETED_BY_OME( getReason("PRICING.LINE_ITEM_DELETED_BY_OME") ),

        LI_MARKETITEM_NOT_ACTIVE( getReason("PRICING.LI_MARKETITEM_NOT_ACTIVE") ),
        LI_MARKETITEM_MULTIPLE_ACTIVE( getReason("PRICING.LI_MARKETITEM_MULTIPLE_ACTIVE") ), 
        LI_MARKETITEM_NOT_FND( getReason("PRICING.LI_MARKETITEM_NOT_FND") ), 
        LI_MARKETITEM_EMPTY_OR_NULL( getReason("PRICING.LI_MARKETITEM_EMPTY_OR_NULL") ),
        LI_MARKETITEM_REQ_FEAT_MISSING( getReason("PRICING.LI_MARKETITEM_REQ_FEAT_MISSING") ),
        LI_MARKETITEM_VALID( getReason("PRICING.LI_MARKETITEM_VALID") ),                 
        
        LI_PRODUCT_NOT_ACTIVE( getReason("PRICING.LI_PRODUCT_NOT_ACTIVE") ),
        LI_PRODUCT_MULTIPLE_ACTIVE( getReason("PRICING.LI_PRODUCT_MULTIPLE_ACTIVE") ), 
        LI_PRODUCT_NOT_FND( getReason("PRICING.LI_PRODUCT_NOT_FND") ), 
        LI_PRODUCT_EMPTY_OR_NULL( getReason("PRICING.LI_PRODUCT_EMPTY_OR_NULL") ),
        LI_PRODUCT_REQ_FEAT_MISSING( getReason("PRICING.LI_PRODUCT_REQ_FEAT_MISSING") ),
        LI_PRODUCT_VALID( getReason("PRICING.LI_PRODUCT_VALID") ),         
        
        // Pricing Reason Codes - Promotion Issues
        LI_PROMOTION_NOT_ACTIVE( getReason("PRICING.LI_PROMOTION_NOT_ACTIVE") ),
        LI_PROMOTION_MULTIPLE_ACTIVE( getReason("PRICING.LI_PROMOTION_MULTIPLE_ACTIVE") ), 
        LI_PROMOTION_NOT_FND( getReason("PRICING.LI_PROMOTION_NOT_FND") ),
        LI_PROMOTION_EXPIRED( getReason("PRICING.LI_PROMOTION_EXPIRED") ), 
        LI_PROMOTION_CONFLICT( getReason("PRICING.LI_PROMOTION_CONFLICT") ), 
        LI_PROMOTION_REQ_FEAT_MISSING( getReason("PRICING.LI_PROMOTION_REQ_FEAT_MISSING") ), 
        LI_PROMOTION_VALID( getReason("PRICING.LI_PROMOTION_VALID") ), 
        LI_PROMOTION_NOT_APPLICABLE( getReason("PRICING.LI_PROMOTION_NOT_APPLICABLE") ),
        
        // Pricing Reason Codes - RealTime Promotion Issues
        LI_RT_PROMOTION_NOT_ACTIVE( getReason("PRICING.LI_RT_PROMOTION_NOT_ACTIVE") ),
        LI_RT_PROMOTION_MULTIPLE_ACTIVE( getReason("PRICING.LI_RT_PROMOTION_MULTIPLE_ACTIVE") ), 
        LI_RT_PROMOTION_NOT_FND( getReason("PRICING.LI_RT_PROMOTION_NOT_FND") ),
        LI_RT_PROMOTION_EXPIRED( getReason("PRICING.LI_RT_PROMOTION_EXPIRED") ), 
        LI_RT_PROMOTION_CONFLICT( getReason("PRICING.LI_RT_PROMOTION_CONFLICT") ), 
        LI_RT_PROMOTION_REQ_FEAT_MISSING( getReason("PRICING.LI_RT_PROMOTION_REQ_FEAT_MISSING") ), 
        LI_RT_PROMOTION_VALID( getReason("PRICING.LI_RT_PROMOTION_VALID") ), 
        
        // Pricing Reason Codes - Bundle Issues
        LI_BUNDLE_NOT_ACTIVE( getReason("PRICING.LI_BUNDLE_NOT_ACTIVE") ),
        LI_BUNDLE_MULTIPLE_ACTIVE( getReason("PRICING.LI_BUNDLE_MULTIPLE_ACTIVE") ), 
        LI_BUNDLE_NOT_FND( getReason("PRICING.LI_BUNDLE_NOT_FND") ), 
        LI_BUNDLE_EXPIRED( getReason("PRICING.LI_BUNDLE_EXPIRED") ),
        LI_BUNDLE_VALID( getReason("PRICING.LI_BUNDLE_VALID") ), 
        
        // Pricing Reason Codes - Feature Constraint Issues
        FEATURE_UNAVAILABLE( getReason("PRICING.FEATURE_UNAVAILABLE") ),
        FEATURE_MIN_VIOLATION( getReason("PRICING.FEATURE_MIN_VIOLATION") ), 
        FEATURE_MAX_VIOLATION( getReason("PRICING.FEATURE_MAX_VIOLATION") ), 
        FEATURE_STRING_CONSTRAINT_INVALID( getReason("PRICING.FEATURE_STRING_CONSTRAINT_INVALID") ),
        FEATURE_INTEGER_CONSTRAINT_INVALID( getReason("PRICING.FEATURE_INTEGER_CONSTRAINT_INVALID") ),
        FEATURE_BOOLEAN_CONSTRAINT_INVALID( getReason("PRICING.FEATURE_BOOLEAN_CONSTRAINT_INVALID") ),
        FEATURE_TYPE_UNKNOWN( getReason("PRICING.FEATURE_TYPE_UNKNOWN") ),
        FEATURE_GROUP_VIOLATION(getReason("PRICING.FEATURE_GROUP_VIOLATION") ),
        FEATURE_VALIDATED(getReason("PRICING.FEATURE_VALIDATED") ),
        REQUIRED_FEAURE_NOT_SELECTED(getReason("PRICING.REQUIRED_FEAURE_NOT_SELECTED") );        
        
            
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
     * CCP Reason Codes
     * 
     */ 
    public static enum CCP 
    {       
        // CCP Reason Codes - General Issues
        UNKNOWN_TRANSACTION_TYPE( getReason("CCP.UNKNOWN_TRANSACTION_TYPE") ),
        INVALID_XML_REQUEST( getReason("CCP.INVALID_XML_REQUEST") ),        
        DB_CONNECTION_FAILURE( getReason("CCP.DB_CONNECTION_FAILURE") ),
        NO_VALID_EMAIL_PROVIDED( getReason("CCP.NO_VALID_EMAIL_PROVIDED") ),
        EMPTY_REASON( getReason("CCP.EMPTY_REASON") ),
        UNKNOWN_REASON( getReason("CCP.UNKNOWN_REASON") ),
        
        // CCP Reason Codes
        CAMPAIGN_ID_ZERO( getReason("CCP.CAMPAIGN_ID_ZERO") ),
        CAMPAIGN_ID_ERROR( getReason("CCP.CAMPAIGN_ID_ERROR") ),
        EMAIL_ID_NOT_FOUND( getReason("CCP.EMAIL_ID_NOT_FOUND") ),
        ORDER_TYPE_UNKNOWN( getReason("CCP.ORDER_TYPE_UNKNOWN") );
        
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
     * OME Reason Codes
     * 
     */ 
    public static enum OME 
    {       
        ORDER_STATUS( getReason("OME.ORDER_STATUS") ),
        ORDER_SUMMARY( getReason("OME.ORDER_SUMMARY") ),
        ORDER_ORP( getReason("OME.ORDER_PENDING_PROBLEM") ),
        EVT_CALL_A( getReason("OME.EVT_CALL_A") ),
        EVT_CALL_PROVIDER( getReason("OME.EVT_CALL_PROVIDER") );
        
        private String code;
        
        public String getCode() 
        {
            return code;
        }

        private OME( final String value ) 
        {
            code = value;
        }        
    }
    
   
    
    
    private static String getReason( final String reasonName ) 
    {  
        try 
        {            
            String value = null;            
            value = reasonProperties.getProperty( reasonName );
            
            if ( value != null ) 
            {
                return value.trim();
            }
            else return "-500";
        }
        catch( Exception e ) 
        {
            logger.error( "Error retrieving reason property : " +  reasonName );
        }
        
        return null;        
    }

    
}
