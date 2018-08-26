package com.AL.ie.service.strategy;



import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;


/**
 * @author ebaugh
 */
public final class ArbiterFlowKey
{

	private static final Logger logger = Logger.getLogger(ArbiterFlowKey.class);
    private static final AtomicInteger COUNTER = new AtomicInteger(1);


    /**
     * Default Constructor.
     **/
    private ArbiterFlowKey()
    {
        // Do nothing right now.
    }

    /**
     * Function used to get GUIDs.
     * It's called once for each GUID needed.
     * @return the next GUID value.
     */
    public static String nextVal()
    {
    	logger.debug("Generating uniqueId for arbiter flow");
//        long time = Calendar.getInstance( TimeZone.getTimeZone( "GMT" ) ).getTimeInMillis();
//        String uid = Long.toString( time );
//        return uid +"-"+ UUID.randomUUID().toString() + COUNTER.getAndIncrement();
    	return UUID.randomUUID().toString();

    }

}
