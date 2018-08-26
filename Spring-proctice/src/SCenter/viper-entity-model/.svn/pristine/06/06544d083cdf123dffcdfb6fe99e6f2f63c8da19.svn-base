package com.A.V.beans.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author ebaugh
 * 
 */

@Entity
@Table( name = "market" )
public class MarketBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5463601405255880822L;

	@Id
    @GeneratedValue( generator = "marketBeanSequence" )
    @SequenceGenerator( name = "marketBeanSequence", sequenceName = "MARKET_BEAN_SEQ" )
    private long id;

    private String name;

    private String description;

    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<PostalCodeRangeBean> postalCodeRangeBeans;

    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<MarketItemBean> marketItemBeans;

    /**
     * {@inheritDoc}
     */
    @Override
    public final long getId()
    {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setId( final long id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( final String name )
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( final String description )
    {
        this.description = description;
    }

    public List<PostalCodeRangeBean> getPostalCodeRangeBeans()
    {
        return postalCodeRangeBeans;
    }

    public void setPostalCodeRangeBeans( final List<PostalCodeRangeBean> postalCodeRangeBeans )
    {
        this.postalCodeRangeBeans = postalCodeRangeBeans;
    }

    public final List<MarketItemBean> getMarketItems()
    {
        return marketItemBeans;
    }

    public final void setMarketItems( final List<MarketItemBean> marketItemBeans )
    {
        this.marketItemBeans = marketItemBeans;
    }

    /**
     * Function to return the list of providers for all the market items on this market.
     * 
     * @return list of providers.
     */
    public final List<BusinessParty> getProviders()
    {
        Set<BusinessParty> tmpProviders = new HashSet<BusinessParty>();

        // Get each of the marketItems in this market
        List<MarketItemBean> marketItems = getMarketItems();

        // Iterate of the list of marketItems, adding the Provider / BusinessParty to the set
        for ( MarketItemBean mbean : marketItems )
        {
            tmpProviders.add( mbean.getProvider() );
        }

        // Return a List composed of the members of the set that was just created.
        return new ArrayList<BusinessParty>( tmpProviders );
    }

}
