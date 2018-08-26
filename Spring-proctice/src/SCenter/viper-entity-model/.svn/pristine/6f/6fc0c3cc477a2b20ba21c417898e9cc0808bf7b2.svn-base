package com.A.V.beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.beans.FeaturePriceInfo;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author jgerhard
 * 
 */

@Entity
@Table( name = "priceTier" )
public class PriceTierBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8141220151044841349L;

	@Id
    @GeneratedValue( generator = "priceTierBeanSequence" )
    @SequenceGenerator( name = "priceTierBeanSequence", sequenceName = "PRICE_TIER_BEAN_SEQ" )
    private long id;

    private FeaturePriceInfo featurePriceInfo;
    
    private int tierStart = 0;
    
    public long getId()
    {
        return id;
    }

    public void setId( final long id )
    {
        this.id = id;
    }

    public void setFeaturePriceInfo( final FeaturePriceInfo featurePriceInfo )
    {
        this.featurePriceInfo = featurePriceInfo;
    }

    public FeaturePriceInfo getFeaturePriceInfo()
    {
        return featurePriceInfo;
    }

    public void setTierStart( final int tierStart )
    {
        this.tierStart = tierStart;
    }

    public int getTierStart()
    {
        return tierStart;
    }
    
    
    
}
