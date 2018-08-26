/**
 *
 */
package com.AL.comm.message.handler;

import javax.jms.Message;
import org.apache.log4j.Logger;

public class HandlerImpl
{
    Logger logger = Logger.getLogger( HandlerImpl.class );

    /**
     * Private Constructor.
     */
    public HandlerImpl()
    {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public String execute( String doc )
    {
    	System.out.println( "invoke ----> public String execute( String doc )"+doc );

        return "response---->public String execute( String doc )";

    }

    /**
     * {@inheritDoc}
     */
    public String execute( Message doc )
    {

        System.out.println( "invoke ----> public String execute( Message doc )"+doc.toString() );

        return "response";
    }

}
