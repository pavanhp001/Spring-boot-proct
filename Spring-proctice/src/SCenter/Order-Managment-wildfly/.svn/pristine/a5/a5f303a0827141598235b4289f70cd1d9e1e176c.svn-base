package com.AL.ie.ws;

import com.AL.xml.v4.OrderManagementRequestResponseDocument;

 
/**
 * @author ebthomas
 *
 * @param <T> input type
 */
public interface IeWSHandler<T>
{
 
    /**
     * @param doc request information
     * @return String with result
     */
	OrderManagementRequestResponseDocument execute( T doc );
    
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
        @SuppressWarnings("rawtypes")
		public static IeWSHandler  createIntegrationEngineHandler()
        {
        	 
            return null;
        }
    }

}
