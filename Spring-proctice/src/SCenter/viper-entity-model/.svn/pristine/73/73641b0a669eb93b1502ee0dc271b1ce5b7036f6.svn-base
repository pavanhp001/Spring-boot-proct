package com.A.V.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.A.V.Constants;
import com.A.V.beans.entity.DescriptiveInfoBean;

/**
 * @author jgerhard
 * 
 */

@Embeddable
public class ProductBase extends CapabilitiesRecord implements Serializable
{
    private static final long serialVersionUID = 96579182678L;

    @Column( nullable = false )
    private String externalId;
    private String name;
    private String displayName;
    private String description;
    private String itemCategoryName;
    private String providerExternalId; 
    private boolean marketMembershipServiceable;
    private int channels;
    private boolean realTimeProduct = false;
    
    private PriceInfo price;
    
    private Boolean isEnergyPricing;
	private Integer energyunit;
	private Double energyRate1;
	private String energyRate1Tier;
	private Double energyRate2;
	private String energyRate2Tier;
	private Double energyRate3;
	private String energyRate3Tier;
	private Double energyRate4;
	private String energyRate4Tier;
    
    //long description 
    @Column ( length = 3000 )
    private String descriptiveInfo;
    
    //terms and conditions
    @Column ( length = 3000 )
    private String descriptiveInfo2;
    
    //disclosures
    @Column ( length = 3000 )
    private String descriptiveInfo3;
    
    //associated documents (urls)
    @Column ( length = 3000 )
    private String descriptiveInfo4;
    
    //long description augmented
    @Column ( length = 3000 )
    private String descriptiveInfoExt;
    
    //terms and conditions augmented
    @Column ( length = 3000 )
    private String descriptiveInfo2Ext;
    
    //disclosures augmented
    @Column ( length = 3000 )
    private String descriptiveInfo3Ext;
    
    //associated documents (urls) augmented
    @Column ( length = 3000 )
    private String descriptiveInfo4Ext;
    
    @Transient
    private transient List<DescriptiveInfoBean> descriptiveInfoBeanList;
    
    //pipe delimited
    @Column ( length = 3000 )
    private String marketingHighlights;
    
    @Transient
    private transient List<String> marketingHighlightsList;
    
    //pipe delimited
    @Column ( length = 1000 )
    private String metaData;
    
    @Transient
    private transient List<String> metaDataList;
    
    @Transient
    private transient List<String> itemCategoryNameList;
    
    private int offerType;
    
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

    public boolean isMarketMembershipServiceable()
    {
        return marketMembershipServiceable;
    }

    public void setMarketMemberShipServiceable( final boolean marketMembershipServiceable )
    {
        this.marketMembershipServiceable = marketMembershipServiceable;
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

    public void setName( final String name )
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setDisplayName( final String displayName )
    {
        this.displayName = displayName;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setPrice( final PriceInfo price )
    {
        this.price = price;
    }

    public PriceInfo getPrice()
    {
        return price;
    }

    public void setDescriptiveInfo( final String descriptiveInfo )
    {
        this.descriptiveInfo = descriptiveInfo;
    }

    public String getDescriptiveInfo()
    {
        return descriptiveInfo;
    }

    public void setItemCategoryName( final String itemCategoryName )
    {
        this.itemCategoryName = itemCategoryName;
    }

    public String getItemCategoryName()
    {
        return itemCategoryName;
    }

    public void setRealTimeProduct( final boolean realTimeProduct )
    {
        this.realTimeProduct = realTimeProduct;
    }

    public boolean isRealTimeProduct()
    {
        return realTimeProduct;
    }

    public void setProviderExternalId( final String providerExternalId )
    {
        this.providerExternalId = providerExternalId;
    }

    public String getProviderExternalId()
    {
        return providerExternalId;
    }
    
    public void setMarketingHighlights( final String marketingHighlights )
    {
        this.marketingHighlights = marketingHighlights;
    }

    public String getMarketingHighlights()
    {
        return marketingHighlights;
    }

    public void setMetaData( final String metaData )
    {
        this.metaData = metaData;
    }

    public String getMetaData()
    {
        return metaData;
    }
    
    /*
     * These 4 methods are convenience methods to access the data required and should be
     * used in place of the string methods for metaData, descriptiveInfo, and marketing highlights.
     */
    
    /**
     * Gets the meta data list.  This method unmarshalls the flattened data from its compressed format.
     *
     * @return the meta data list
     */
    public List<String> getItemCategoryNameList()
    {
        if ( itemCategoryName != null )
        {
            String[] temp = itemCategoryName.split( "\\|" );
            itemCategoryNameList = Arrays.asList( temp );
            return itemCategoryNameList;
        }
        else 
        {
            return null;
        }
    }
    
    /**
     * Gets the meta data list.  This method unmarshalls the flattened data from its compressed format.
     *
     * @return the meta data list
     */
    public List<String> getMetaDataList()
    {
        if ( metaData != null )
        {
            String[] temp = metaData.split( "\\|" );
            metaDataList = Arrays.asList( temp );
            return metaDataList;
        }
        else 
        {
            return null;
        }
    }
    
    /**
     * Gets the marketing highlights list.  This method unmarshalls the flattened data from its compressed format.
     *
     * @return the meta data list
     */
    public List<String> getMarketingHighlightsList()
    {
        if ( marketingHighlights != null )
        {
            String[] temp = marketingHighlights.split( "\\|" );
            marketingHighlightsList = Arrays.asList( temp );
            return marketingHighlightsList;
        }
        else 
        {
            return null;
        }
    }
    
    /**
     * Gets the DescriptiveInfoBean list.  
     * This method unmarshalls the flattened data from its compressed format.
     *
     * @return the meta data list
     */
    public List<DescriptiveInfoBean> getDescriptiveInfoList()
    {
        if ( descriptiveInfo != null )
        {
            //String[] tempDescInfo = descriptiveInfo.split( "\\|" );
            
            descriptiveInfoBeanList = new ArrayList<DescriptiveInfoBean>(); 
            if ( descriptiveInfo != null )
            {
                DescriptiveInfoBean di = new DescriptiveInfoBean();
                di.setType( "longDescription" );
                di.setDescription( descriptiveInfo );
                
                if ( descriptiveInfoExt != null )
                {
                    di.setDescription( di.getDescription() + " " + descriptiveInfoExt );
                }
                descriptiveInfoBeanList.add(  di );
            }
            
            if ( descriptiveInfo2 != null )
            {
                DescriptiveInfoBean di = new DescriptiveInfoBean();
                di.setType( "termsAndConditions" );
                di.setDescription( descriptiveInfo2 );
                
                if ( descriptiveInfo2Ext != null )
                {
                    di.setDescription( di.getDescription() + " " + descriptiveInfo2Ext );
                }
                
                descriptiveInfoBeanList.add(  di );
            }
            if ( descriptiveInfo3 != null )
            {
                DescriptiveInfoBean di = new DescriptiveInfoBean();
                di.setType( "disclosures" );
                di.setDescription( descriptiveInfo3 );
                
                if ( descriptiveInfo3Ext != null )
                {
                    di.setDescription( di.getDescription() + " " + descriptiveInfo3Ext );
                }
                descriptiveInfoBeanList.add(  di );
            }
            if ( descriptiveInfo4 != null )
            {
                DescriptiveInfoBean di = new DescriptiveInfoBean();
                di.setType( "associatedDocument" );
                di.setDescription( descriptiveInfo4 );
                
                if ( descriptiveInfo4Ext != null )
                {
                    di.setDescription( di.getDescription() + " " + descriptiveInfo4Ext );
                }
                descriptiveInfoBeanList.add(  di );
            }
            return descriptiveInfoBeanList;
        }
        else 
        {
            return null;
        }
    }

    public String getDescriptiveInfo2()
    {
        return descriptiveInfo2;
    }

    public void setDescriptiveInfo2( final String descriptiveInfo2 )
    {
        this.descriptiveInfo2 = descriptiveInfo2;
    }

    public String getDescriptiveInfo3()
    {
        return descriptiveInfo3;
    }

    public void setDescriptiveInfo3( final String descriptiveInfo3 )
    {
        this.descriptiveInfo3 = descriptiveInfo3;
    }

    public String getDescriptiveInfo4()
    {
        return descriptiveInfo4;
    }

    public void setDescriptiveInfo4( final String descriptiveInfo4 )
    {
        this.descriptiveInfo4 = descriptiveInfo4;
    }

    public String getDescriptiveInfoExt()
    {
        return descriptiveInfoExt;
    }

    public void setDescriptiveInfoExt( final String descriptiveInfoExt )
    {
        this.descriptiveInfoExt = descriptiveInfoExt;
    }

    public String getDescriptiveInfo2Ext()
    {
        return descriptiveInfo2Ext;
    }

    public void setDescriptiveInfo2Ext( final String descriptiveInfo2Ext )
    {
        this.descriptiveInfo2Ext = descriptiveInfo2Ext;
    }

    public String getDescriptiveInfo3Ext()
    {
        return descriptiveInfo3Ext;
    }

    public void setDescriptiveInfo3Ext( final String descriptiveInfo3Ext )
    {
        this.descriptiveInfo3Ext = descriptiveInfo3Ext;
    }

    public String getDescriptiveInfo4Ext()
    {
        return descriptiveInfo4Ext;
    }

    public void setDescriptiveInfo4Ext( final String descriptiveInfo4Ext )
    {
        this.descriptiveInfo4Ext = descriptiveInfo4Ext;
    }
    
    public OfferTypeEnum getOfferType() 
	{
		return OfferTypeEnum.getOfferType(this.offerType);
	}

	public void setOfferType(final OfferTypeEnum offerTypeEnum) 
	{
		this.offerType = offerTypeEnum.getValue();
	}

	public Integer getEnergyUnit() {
		return energyunit;
	}

	public void setEnergyUnit(Integer energyUnit) {
		this.energyunit = energyUnit;
	}

	public Boolean isEnergyPricing() {
		return isEnergyPricing;
	}

	public void setEnergyPricing(Boolean isEnergyPricing) {
		this.isEnergyPricing = isEnergyPricing;
	}

	public Boolean getIsEnergyPricing() {
		return isEnergyPricing;
	}

	public void setIsEnergyPricing(Boolean isEnergyPricing) {
		this.isEnergyPricing = isEnergyPricing;
	}

	public Integer getEnergyunit() {
		return energyunit;
	}

	public void setEnergyunit(Integer energyunit) {
		this.energyunit = energyunit;
	}

	public Double getEnergyRate1() {
		return energyRate1;
	}

	public void setEnergyRate1(Double energyRate1) {
		this.energyRate1 = energyRate1;
	}

	public String getEnergyRate1Tier() {
		return energyRate1Tier;
	}

	public void setEnergyRate1Tier(String energyRate1Tier) {
		this.energyRate1Tier = energyRate1Tier;
	}

	public Double getEnergyRate2() {
		return energyRate2;
	}

	public void setEnergyRate2(Double energyRate2) {
		this.energyRate2 = energyRate2;
	}

	public String getEnergyRate2Tier() {
		return energyRate2Tier;
	}

	public void setEnergyRate2Tier(String energyRate2Tier) {
		this.energyRate2Tier = energyRate2Tier;
	}

	public Double getEnergyRate3() {
		return energyRate3;
	}

	public void setEnergyRate3(Double energyRate3) {
		this.energyRate3 = energyRate3;
	}

	public String getEnergyRate3Tier() {
		return energyRate3Tier;
	}

	public void setEnergyRate3Tier(String energyRate3Tier) {
		this.energyRate3Tier = energyRate3Tier;
	}

	public Double getEnergyRate4() {
		return energyRate4;
	}

	public void setEnergyRate4(Double energyRate4) {
		this.energyRate4 = energyRate4;
	}

	public String getEnergyRate4Tier() {
		return energyRate4Tier;
	}

	public void setEnergyRate4Tier(String energyRate4Tier) {
		this.energyRate4Tier = energyRate4Tier;
	}

}
