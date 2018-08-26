package com.A.vm.util.converter.unmarshall;


import org.springframework.stereotype.Component;
import com.A.vm.vo.OrderChangeValueObject;
import com.A.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;

/**
 * @author ebthomas
 *
 */
@Component
public   class UnmarshallOrderStatus
{
    /**
     * Unmarshall Order Status.
     */
    public UnmarshallOrderStatus()
    {
        super();
    }
 
 

        /**
         * @param originalMessage Source Message
         * @return Boolean indicating valid status
         */
        private static Boolean isValid( final Request originalMessage )
        {
            if ( originalMessage == null )
            {
                throw new IllegalArgumentException( "null value not permitted." );
            }

            return Boolean.TRUE;
        }

        /**
         * @param originalMessage Source
         * @param entityManagerReference Entity Manager
          * @return Order Status
         */
        public static OrderChangeValueObject doBuildOrderStatus( final Request originalMessage )
        {
        	return OrderChangeValueObject.doBuildOrderStatus(originalMessage);
        }

        /**
         * @param request Source
         * @param entityManagerReference Entity Manager
         * @return Order Status
         */
        public  OrderChangeValueObject build( final Request request  )
        {
            if ( request == null )
            {
                throw new IllegalArgumentException( "null value not allowed in builder" );
            }

            if ( isValid( request ) )
            {
                return doBuildOrderStatus( request  );
            }

            throw new IllegalArgumentException( "invalid document.  unable to build" );

        }

     
}
