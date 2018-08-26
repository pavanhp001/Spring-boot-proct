package com.A.V.beans;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
/**
 * @author Earl Baugh
 *
 */

@Embeddable
public class PriceInfo implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3522263118155856935L;
	
	private double baseNonRecurringPrice;
    private double baseRecurringPrice;
    
//    @Column(name = "NON_RECUR_PRICE_U")
    @Transient
    private String baseNonRecurringPriceUnits;
    
//    @Column(name = "RECUR_PRICE_U")
  @Transient
    private String baseRecurringPriceUnits;
    
    

    /**
     * Default Constructor.
     */
    public PriceInfo()
    {
        // Do nothing right now.
    }
    
    
    public final double getBaseNonRecurringPrice()
    {
        return baseNonRecurringPrice;
    }

    public final void setBaseNonRecurringPrice( final double baseNonRecurringPrice )
    {
        this.baseNonRecurringPrice = baseNonRecurringPrice;
    }


    public final double getBaseRecurringPrice()
    {
        return baseRecurringPrice;
    }


    public final void setBaseRecurringPrice( final double baseRecurringPrice )
    {
        this.baseRecurringPrice = baseRecurringPrice;
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
    
    
}
