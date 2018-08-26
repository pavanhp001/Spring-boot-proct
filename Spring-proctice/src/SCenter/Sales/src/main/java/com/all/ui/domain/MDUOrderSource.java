package com.AL.ui.domain;



/**
 * 
 * @author 
 *
 */
public class MDUOrderSource {
	
	private MDUProperty[] mduProperties;

    private String ordersourceExternalId;

    public MDUProperty[] getMduProperties ()
    {
        return mduProperties;
    }

    public void setMduProperties (MDUProperty[] mduProperties)
    {
        this.mduProperties = mduProperties;
    }

    public String getOrdersourceExternalId ()
    {
        return ordersourceExternalId;
    }

    public void setOrdersourceExternalId (String ordersourceExternalId)
    {
        this.ordersourceExternalId = ordersourceExternalId;
    }

    @Override
    public String toString()
    {
        return "MDUOrderSource [mduProperties = "+mduProperties+", ordersourceExternalId = "+ordersourceExternalId+"]";
    }
}
