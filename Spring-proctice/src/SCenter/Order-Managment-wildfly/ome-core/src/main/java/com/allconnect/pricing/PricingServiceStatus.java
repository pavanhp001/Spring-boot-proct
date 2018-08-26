package com.AL.pricing;

import java.util.ArrayList;
import java.util.List;
import com.AL.codes.Reason;
import com.AL.codes.Status;


/**
 * Utility class to store the response
 * from the call to the pricing service.
 * 
 * It contains an overall status of the call to
 * the pricing service and a list of all priced orders.
 * 
 * @author klyons
 *
 */
public class PricingServiceStatus extends WSBaseStatus<Status.Pricing, Reason.Pricing>
{
    // GUID
    private String guid = null;
    
    
    // List of priced order responses. These have their own individual statuses.
    private List<PricedOrderStatus> pricedOrderStatuses = new ArrayList<PricedOrderStatus>();
            
    
    public PricingServiceStatus()
    {
        setStatus( Status.Pricing.EMPTY_STATUS );
        setReason( Reason.Pricing.EMPTY_REASON );
    }

    /**
     * Determines the overall status of the service call based on
     * the ratio of successfully priced line items to unsuccessfully
     * priced line items.
     * 
     * 
     * @param serviceStatus
     */
    public void determineResponseMessages()
    {
        // For now there should only be ONE order in this list.
        for ( PricedOrderStatus orderStatus : getPricedOrderStatuses() ) 
        {
            if (orderStatus.getStatus() == Status.Pricing.ERROR_ORDER_NONE_PRICED) 
            {
                getMessages().add( "ORDER GUID : " + this.getGuid()  + " FAILED. SEE LINE ITEM ERRORS.");
            }
            else if ( orderStatus.getStatus() == Status.Pricing.WARNING_NONE_PRICED ) 
            {
                getMessages().add( "ORDER GUID : " + this.getGuid()  + " WARNING. LINE ITEMS CANCELLED OR REMOVED.");
            }
            else if ( orderStatus.getStatus() == Status.Pricing.INFO_ORDER_PRICED_SUCCESSFULLY ) 
            {
                getMessages().add( "ORDER GUID : " + this.getGuid()  + " SUCCESSFUL");
            }
            else if (orderStatus.getStatus() == Status.Pricing.FATAL_REQUEST_FAILED) 
            {
                String msg = "";
                if (orderStatus.getMessages().size() > 0) 
                {
                    msg = orderStatus.getMessages().get(0);
                }
                
                getMessages().add( "ORDER GUID: " + this.getGuid()  + " " + msg);
            }
            
            setStatus( orderStatus.getStatus() );
        }
    }

    /**
     * @param guid the guid to set
     */
    public void setGuid( final String guid )
    {
        this.guid = guid;
    }

    /**
     * @return the guid
     */
    public String getGuid()
    {
        return guid;
    }
    
    /**
     * @param pricedOrders the pricedOrders to set
     */
    public void setPricedOrderStatuses( final List<PricedOrderStatus> pricedOrders )
    {
        this.pricedOrderStatuses = pricedOrders;
    }
    
    /**
     * @return the pricedOrders
     */
    public List<PricedOrderStatus> getPricedOrderStatuses()
    {
        return pricedOrderStatuses;
    }
    
}
