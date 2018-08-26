/**
 *
 */
package com.A.util;

//import org.jboss.seam.log.Log;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.slf4j.helpers.MessageFormatter;



/**
 * @author ebthomas
 * 
 */
public final class LogUtil
{

    public static final org.apache.log4j.Logger NULL_LOG4J_LOGGER = null;
   // public static final Log NULL_JBOSS_LOGGER = null;
    
    /**
     * Default Constructor for utility class.
     */
    private LogUtil()
    {
        super();
    }
    
    
    public static void error(final Logger logger, final String parameterizedMsg,
    		                        final Object param1,
    		                        final Object param2) {
    		          if (logger.isEnabledFor(Level.ERROR)) {
    		              logger.error(MessageFormatter.format(
    		                      parameterizedMsg.toString(), param1, param2));
    		          }
    		      }
    
    
    
    /**
     * @param logLevelEnum
     *            level of logging.
     * @param logger
     *            Log to write to.
     * @param outputString
     *            String output.
     */
   /* public static void log( final LogLevelEnum logLevelEnum, final   org.apache.log4j.Logger  logger, final String outputString )
    {
        if ( logger != null )
        {
            switch ( logLevelEnum )
            {
                case info:
                    logger.info( outputString );
                    break;
                case debug:
                    logger.debug( outputString );
                    break;
                case warning:
                    logger.warn( outputString );
                    break;
                case fatal:
                    logger.fatal( outputString );
                    break;
                case system:
                    System.out.println( outputString );
                    break;
                default:
                    logger.debug( outputString );
            }

        }
        else
        {
            System.out.println( outputString );
        }

    }*/
    
    

    /**
     * @param logLevelEnum
     *            level of logging.
     * @param logger
     *            Log to write to.
     * @param outputString
     *            String output.
     */
   /* public static void log( final LogLevelEnum logLevelEnum, final Log logger, final String outputString )
    {
        if ( logger != null )
        {
            switch ( logLevelEnum )
            {
                case info:
                    logger.info( outputString );
                    break;
                case debug:
                    logger.debug( outputString );
                    break;
                case warning:
                    logger.warn( outputString );
                    break;
                case fatal:
                    logger.fatal( outputString );
                    break;
                case system:
                    System.out.println( outputString );
                    break;
                default:
                    logger.debug( outputString );
            }

        }
        else
        {
            System.out.println( outputString );
        }

    }*/


    
 

}
