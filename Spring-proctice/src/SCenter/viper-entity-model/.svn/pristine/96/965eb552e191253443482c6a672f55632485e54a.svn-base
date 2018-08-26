package com.A.V.beans;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LineItemPriceInfo implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "PRICING_ON")
    private Calendar pricingDate;
    
    @Column(name = "PRICING_STATUS")
    private Long pricingStatus;
    
    @Column(name="ON_DELIVERY_PRICE")
    private double onDeliveryPrice;
    
    @Column(name = "NON_RECUR_PRICE")
    private double baseNonRecurringPrice;
    
    @Column(name = "RECUR_PRICE")
    private double baseRecurringPrice;
    
    @Column(name = "NON_RECUR_PRICE_U")
    private String baseNonRecurringPriceUnits;
    
    @Column(name = "RECUR_PRICE_U")
    private String baseRecurringPriceUnits;
    
    
    @Column(name = "INC_PRICE")
    private Boolean includePriceInTotal;
    
    

    @Column(name = "BASE_NON_RECUR_PRICE")
    public double getBaseNonRecurringPrice()
    {
        return baseNonRecurringPrice;
    }

    public void setBaseNonRecurringPrice( double baseNonRecurringPrice )
    {
        this.baseNonRecurringPrice = baseNonRecurringPrice;
    }

    public double getBaseRecurringPrice()
    {
        return baseRecurringPrice;
    }

    public void setBaseRecurringPrice( double baseRecurringPrice )
    {
        this.baseRecurringPrice = baseRecurringPrice;
    }

    public Calendar getPricingDate()
    {
        return pricingDate;
    }

    public void setPricingDate( Calendar pricingDate )
    {
        this.pricingDate = pricingDate;
    }

    public Long getPricingStatus()
    {
        return pricingStatus;
    }

    public void setPricingStatus( Long pricingStatus )
    {
        this.pricingStatus = pricingStatus;
    }

	public double getOnDeliveryPrice() {
		return onDeliveryPrice;
	}

	public void setOnDeliveryPrice(double onDeliveryPrice) {
		this.onDeliveryPrice = onDeliveryPrice;
	}

	public String getBaseNonRecurringPriceUnits() {
		return baseNonRecurringPriceUnits;
	}

	public void setBaseNonRecurringPriceUnits(String baseNonRecurringPriceUnits) {
		this.baseNonRecurringPriceUnits = baseNonRecurringPriceUnits;
	}

	public String getBaseRecurringPriceUnits() {
		return baseRecurringPriceUnits;
	}

	public void setBaseRecurringPriceUnits(String baseRecurringPriceUnits) {
		this.baseRecurringPriceUnits = baseRecurringPriceUnits;
	}

	public Boolean getIncludePriceInTotal() {
		return includePriceInTotal;
	}

	public void setIncludePriceInTotal(Boolean includePriceInTotal) {
		this.includePriceInTotal = includePriceInTotal;
	}

	
	
}
