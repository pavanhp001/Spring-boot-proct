package com.AL.ome.ws;

import javax.xml.ws.Endpoint;
import com.AL.enums.LogLevelEnum;
import com.AL.util.LogUtil;
import com.AL.ws.OrderManagementWSRemote;
import com.AL.ws.impl.OrderManagementWSImpl;

/**
 * @author ebthomas
 *
 */
public final class StartOmeWS
{

    
    
    /**
     * Static Constructor.
     */
    private StartOmeWS() 
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

        OrderManagementWSRemote impl = new OrderManagementWSImpl();

        Endpoint endpoint = Endpoint.publish( URI, impl );

        boolean status = endpoint.isPublished();

        //LogUtil.log( LogLevelEnum.system, LogUtil.NULL_LOG4J_LOGGER, "init process completed" );
       // LogUtil.log( LogLevelEnum.system, LogUtil.NULL_LOG4J_LOGGER, "active:" + status );
    }

}
