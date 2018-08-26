package com.A.V.beans.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.IndexColumn;

import com.A.V.Constants;
import com.A.V.beans.CapabilitiesRecord;
import com.A.V.beans.PointInTimeSuperClass;
import com.A.V.beans.PriceInfo;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author jgerhard
 * 
 */

@Entity
@Table( name = "bundle" )
public class BundleBean extends PointInTimeSuperClass implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3579306978719951489L;

	@Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "bundleBeanSequence" )
    @SequenceGenerator( name = "bundleBeanSequence", sequenceName = "BUNDLE_BEAN_SEQ",allocationSize = 1 )
    private long id;

    private String externalId;
    private String name;
    private String description;
     
    private int channels;

    private PriceInfo bundlePrice;
   
    @OneToOne
    @ForeignKey( name = "BUNDLE_FK01" )
    private BusinessParty provider; 
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<String> marketItems;
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<DescriptiveInfoBean> descriptiveInfo;

    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<MarketingHighlightBean> marketingHighlights;
    
    private String bundleCategory;
    
    @Transient
    private List<MarketItemBean> marketItemBeans = new ArrayList<MarketItemBean>();
    
    @Transient
    private List<ItemCategoryBean> bundleItemCategories = new ArrayList<ItemCategoryBean>();
    
    @Transient
    private CapabilitiesRecord bundleCapabilities = new CapabilitiesRecord();
    
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

    public String getExternalId()
    {
        return externalId;
    }

    public void setExternalId( final String externalId )
    {
        this.externalId = externalId;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( final String description )
    {
        this.description = description;
    }

    public int getChannels()
    {
        return channels;
    }

    public void setChannels( final int channels )
    {
        this.channels = channels;
    }
    
    /**
     * Utility Function to return string representation of the channels market item
     * is available in.
     * @return List of Strings representing channels.
     */
    public List<String> getChannelStrings()
    {
        List<String> result = new ArrayList<String>();
        
        result = Constants.getChannelStrings( channels );
        
        return result;
    }

    /**
     * Getter for itemPrice.
     * @return item Price info from marketItem or item.
     */
    public final PriceInfo getBundlePrice()
    {
        return bundlePrice;
    }

    public final void setBundlePrice( final PriceInfo bundlePrice )
    {
        this.bundlePrice = bundlePrice;
    }

    public String getName()
    {
        return name;
    }

    public void setName( final String name )
    {
        this.name = name;
    }

    public BusinessParty getProvider()
    {
        return provider;
    }

    public void setProvider( final BusinessParty provider )
    {
        this.provider = provider;
    }

    public List<String> getMarketItems()
    {
        return marketItems;
    }

    public void setMarketItems( final List<String> marketItems )
    {
        this.marketItems = marketItems;
    }

    public List<DescriptiveInfoBean> getDescriptiveInfo()
    {
        return descriptiveInfo;
    }

    public void setDescriptiveInfo( final List<DescriptiveInfoBean> descriptiveInfo )
    {
        this.descriptiveInfo = descriptiveInfo;
    }

    public List<MarketingHighlightBean> getMarketingHighlights()
    {
        return marketingHighlights;
    }

    public void setMarketingHighlights( final List<MarketingHighlightBean> marketingHighlights )
    {
        this.marketingHighlights = marketingHighlights;
    }

    public CapabilitiesRecord getBundleCapabilities()
    {
        return bundleCapabilities;
    }

    public void setBundleCapabilities( final CapabilitiesRecord bundleCapabilities )
    {
        this.bundleCapabilities = bundleCapabilities;
    }

    public String getBundleCategory()
    {
        return bundleCategory;
    }

    public void setBundleCategory( final String bundleCategory )
    {
        this.bundleCategory = bundleCategory;
    }

    public List<ItemCategoryBean> getBundleItemCategories()
    {
        return bundleItemCategories;
    }

    public void setBundleItemCategories( final List<ItemCategoryBean> bundleItemCategories )
    {
        this.bundleItemCategories = bundleItemCategories;
    }

    public List<MarketItemBean> getMarketItemBeans()
    {
        return marketItemBeans;
    }

    public void setMarketItemBeans( final List<MarketItemBean> marketItemBeans )
    {
        this.marketItemBeans = marketItemBeans;
    }
   
}
