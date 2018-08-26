package com.AL.pricing;


import java.util.ArrayList;
import java.util.List;
import com.AL.codes.Reason;
import com.AL.codes.Status;


/**
 * Abstract class to be implemented by more concrete
 * implementations in the pricing service.
 * 
 * 
 * @author klyons
 *
 */
public abstract class BaseStatus
{
    private Status.Pricing status = Status.Pricing.EMPTY_STATUS;
    private Reason.Pricing reason = Reason.Pricing.EMPTY_REASON;    
    private List<String> messages = new ArrayList<String>();
    
    /**
     * @param status the status to set
     */
    public void setStatus( final Status.Pricing status )
    {
        this.status = status;
    }
    
    /**
     * @return the status
     */
    public Status.Pricing getStatus()
    {
        return status;
    }
    
    /**
     * @param reason the reason to set
     */
    public void setReason( final Reason.Pricing reason )
    {
        this.reason = reason;
    }
    
    /**
     * @return the reason
     */
    public Reason.Pricing getReason()
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

}

