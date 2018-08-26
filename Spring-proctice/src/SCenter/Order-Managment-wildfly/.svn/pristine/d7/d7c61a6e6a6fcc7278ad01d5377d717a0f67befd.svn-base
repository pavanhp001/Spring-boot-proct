package com.AL.pricing;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class which tracks status and reason
 * code information for any service. Class is designed
 * to be used by any implementation of the Status & 
 * Reason classes through generic input.
 * 
 * Status and Reason types are defined at compile time
 * 
 * S - Status Type eg: Status.Pricing, Status.OME
 * R - Reason Type eg: Reason.Pricing, Reason.OME 
 * 
 * @author klyons
 *
 */
public abstract class WSBaseStatus<S,R>
{
    private S status;
    private R reason;    
    private List<String> messages = new ArrayList<String>();
    
    // Transaction ID
    private int transID = 0;
    
    // Transaction type
    private int transTypeEnumVal = 0;
    
    /**
     * @param status the status to set
     */
    public void setStatus( final S status )
    {
        this.status = status;
    }
    
    /**
     * @return the status
     */
    public S getStatus()
    {
        return status;
    }
    
    /**
     * @param reason the reason to set
     */
    public void setReason( final R reason )
    {
        this.reason = reason;
    }
    
    /**
     * @return the reason
     */
    public R getReason()
    {
        return reason;
    }
    
    /**
     * @param messages the messages to set
     */
    public void setMessages( final List<String> messages )
    {
        this.messages = messages;
    }
    
    /**
     * @return the messages
     */
    public List<String> getMessages()
    {
        return messages;
    }

    /**
     * @param transID the transID to set
     */
    public void setTransID( int transID )
    {
        this.transID = transID;
    }

    /**
     * @return the transID
     */
    public int getTransID()
    {
        return transID;
    }

    /**
     * @param transTypeEnumVal the transTypeEnumVal to set
     */
    public void setTransTypeEnumVal( int transTypeEnumVal )
    {
        this.transTypeEnumVal = transTypeEnumVal;
    }

    /**
     * @return the transTypeEnumVal
     */
    public int getTransTypeEnumVal()
    {
        return transTypeEnumVal;
    }

}
