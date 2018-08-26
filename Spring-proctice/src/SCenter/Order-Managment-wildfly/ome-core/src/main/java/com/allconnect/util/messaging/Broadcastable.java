/**
 *
 */
package com.AL.util.messaging;

import java.util.Map;

 
/**
 * @author ebthomas
 *
 * @param <U> Item type to broadcast
 */
 
public interface Broadcastable 
{
        /**
         * @param objectToBroadcast broadcast
         */
        void broadcast( final String objectToBroadcast, final Map<String, String>  map);
}
