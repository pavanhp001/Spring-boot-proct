package com.AL.ws;

import com.AL.ws.impl.OrderManagementWSHandler;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
 * @author ebthomas
 *
 * @param <T> input type
 */
public interface WSHandler<T,R>
{
	
	 
	
    /**
     * @param doc request information
     * @return String with result
     */
    R execute( T doc );
    
    /**
     * @author ebthomas
     * 
     *         Factory that will create Order Management Tasks
     * 
     */
    public static final class FACTORY
    {

        /**
         * Factory Constructor.
         */
        private FACTORY()
        {
            super();
        }

        /**
         * @return OrderManagementTaskExecutor
         */
        public static OrderManagementWSHandler createOmeHandler()
        {
        	 
            return new OrderManagementWSHandler();
        }
    }

}
