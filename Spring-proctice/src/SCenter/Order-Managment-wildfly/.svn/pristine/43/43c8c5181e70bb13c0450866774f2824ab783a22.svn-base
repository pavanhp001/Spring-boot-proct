package com.AL.ome.ws;

 

import javax.xml.ws.Endpoint;
import com.AL.enums.LogLevelEnum;
import com.AL.ie.ws.impl.IeWSImpl;
import com.AL.util.LogUtil;
 

/**
 * @author ebthomas
 *
 */
public final class StartIntegrationWS
{

    
    
    /**
     * Static Constructor.
     */
    private StartIntegrationWS() 
    {
        super();
    }
    
    public static final String URI = "http://localhost:9999/OmeWS";

    /**
     * @param args input
     */
    public static void main( final String[] args )
    {
        
        
       // LogUtil.log( LogLevelEnum.system, LogUtil.NULL_LOG4J_LOGGER, "starting web service" );

        IeWSImpl impl = new IeWSImpl();

        Endpoint endpoint = Endpoint.publish( URI, impl );

        boolean status = endpoint.isPublished();

        //LogUtil.log( LogLevelEnum.system, LogUtil.NULL_LOG4J_LOGGER, "init process completed" );
        //LogUtil.log( LogLevelEnum.system, LogUtil.NULL_LOG4J_LOGGER, "active:" + status );
    }

}
