package com.AL.pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.log4j.Logger;

import com.AL.task.util.SalesContextUtil;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.PriceInfoType;
import com.AL.xml.v4.SalesContextType;

public enum PricingCalculator {
	
	INSTANCE;
	
	private static int DECIMALS = 2;
    private static final String EMPTY_STRING = "";
    private static final String UPDATED_BY_VALUE_EXT = "ext_process";

    private static final Logger logger = Logger.getLogger( PricingCalculator.class );

    /**
     * Final method which simply tabulates the final total
     * order price by summing the collective line item 
     * prices for an order.
     * 
     * @param order completed, validated and fully priced order.
     */
    public void calculateTotalOrderPrice( final OrderType order, String updatedByVal, SalesContextType salesContext) 
    {
        PriceInfoType totalOrderPrice = null;
        PriceInfoType lineItemPrice = null;
        double baseNonRecurringPrice = 0d;
        double baseRecurringPrice = 0d;
        
        if ( order.getTotalPriceInfo() == null ) 
        {
            totalOrderPrice = order.addNewTotalPriceInfo();
        }
        else
        {
            totalOrderPrice = order.getTotalPriceInfo();
        }        
        
        // Extract each line item from the order to be priced.
        List<LineItemType> orderLineItems = order.getLineItems().getLineItemList();        

        for ( LineItemType lineItem : orderLineItems ) 
        {
            if(skipStatus(lineItem, updatedByVal, salesContext)){
            	continue;
            }
        	lineItemPrice = lineItem.getLineItemPriceInfo();
            baseNonRecurringPrice = baseNonRecurringPrice + lineItemPrice.getBaseNonRecurringPrice();
            baseRecurringPrice = baseRecurringPrice + lineItemPrice.getBaseRecurringPrice();
        }
        
        totalOrderPrice.setBaseNonRecurringPrice( round( baseNonRecurringPrice ) );
        totalOrderPrice.setBaseRecurringPrice( round( baseRecurringPrice ) );
    }
    
    /**
     * Method to skip order price calc if status is cancelled_removed,
     * 	sales_new_order or processing_cancelled
     * 
     * @param orderExtId
     * @param lineItem
     * @param updatedByValue
     * @return Boolean
     */
    private Boolean skipStatus(LineItemType lineItem, 
    		String updatedByVal, SalesContextType salesContext) {
    	if(updatedByVal.equals(EMPTY_STRING)){
    		return Boolean.FALSE;
    	}
    	
    	logger.info( "Inside skipStatus, LineItem Status : "+lineItem.getLineItemStatus().getStatusCode());
    	if(null != lineItem.getLineItemStatus() && null !=lineItem.getLineItemStatus().getStatusCode()){ // FIX for AM-1201 NULL check has to be in here since Hybris orders are failing
    		if(lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.CANCELLED_REMOVED) ||
    				lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.PROCESSING_CANCELLED) ||
    				lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.PROCESSING_DISCONNECTED)){ // For PR-35 : added one filter
    			return Boolean.TRUE;
    		} else if(lineItem.getLineItemStatus().getStatusCode().equals(LineItemStatusCodesType.SALES_NEW_ORDER)){
    			if(updatedByVal.equals(UPDATED_BY_VALUE_EXT)){
    				SalesContextUtil.INSTANCE.isSessionStatusCompleted(salesContext);
    			} else {
    				return Boolean.TRUE;
    			}
    		}
    	}
		
		return Boolean.FALSE;
	}
    
    
    /**
     * Utility method to round a double to a decimal
     * precision of 2 spaces.
     * 
     * @param price
     * @return
     */
    private static double round( final double price ) 
    {   
        BigDecimal rounded = new BigDecimal( price );
        
        return rounded.setScale( DECIMALS, RoundingMode.HALF_EVEN ).doubleValue();
    }
}
