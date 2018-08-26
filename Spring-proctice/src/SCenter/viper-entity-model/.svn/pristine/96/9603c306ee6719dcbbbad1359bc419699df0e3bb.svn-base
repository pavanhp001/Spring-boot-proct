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
import com.A.V.beans.PointInTimeSuperClass;
import com.A.V.beans.PriceInfo;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author ebaugh
 * 
 */

@Entity
@Table( name = "marketItem" )
public class MarketItemBean extends PointInTimeSuperClass implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5873729661046361939L;

	@Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "marketItemBeanSequence" )
    @SequenceGenerator( name = "marketItemBeanSequence", sequenceName = "MARKET_ITEM_BEAN_SEQ" ,allocationSize = 1)
    private long id;

    private String externalId;
    private String description;
    private boolean marketMembershipServiceable;
    
    private int channels;
    
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

    @OneToOne
    @ForeignKey( name = "MARKET_ITEM_FK01" )
    private ItemBean itemBean;
    
    @OneToOne
    @ForeignKey( name = "MARKET_ITEM_FK02" )
    private BusinessParty provider; 

    private PriceInfo marketItemPrice;
   
    // This value is used to carry run-time information about how this marketItem
    // was determined to be serviceable.  Default to false.
    @Transient
    private boolean rtsDetermined = false;
    
    // These values are required by the filtering engine
    @Transient
    private Integer accordPlanId;
    @Transient
    private Long changeStamp;
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<String> marketItemFeatures;
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<DescriptiveInfoBean> marketItemDescriptiveInfo;

    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<MarketingHighlightBean> marketItemMarketingHighlights;

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

    public ItemBean getItem()
    {
        return itemBean;
    }

    public void setItem( final ItemBean itemBean )
    {
        this.itemBean = itemBean;
    }

    public BusinessParty getProvider()
    {
        return provider;
    }

    public void setProvider( final BusinessParty provider )
    {
        this.provider = provider;
    }

    /**
     * Getter for itemPrice.
     * @return item Price info from marketItem or item.
     */
    public final PriceInfo getMarketItemPrice()
    {
        // If the MarketItemPrice isn't present, or has both 0's, then
        // we'll return the item price info.
        if ( marketItemPrice == null  
             || ( marketItemPrice.getBaseNonRecurringPrice() == 0.0 
                     && marketItemPrice.getBaseRecurringPrice() == 0.0 ) )
        {
            return itemBean.getItemPrice();
        }
        return marketItemPrice;
    }

    public final void setMarketItemPrice( final PriceInfo marketItemPrice )
    {
        this.marketItemPrice = marketItemPrice;
    }

    public boolean isRtsDetermined()
    {
        return rtsDetermined;
    }

    public void setRtsDetermined( final boolean determined )
    {
        rtsDetermined = determined;
    }

    public ItemBean getItemBean()
    {
        return itemBean;
    }

    public void setItemBean( final ItemBean itemBean )
    {
        this.itemBean = itemBean;
    }

    public Integer getAccordPlanId()
    {
        return accordPlanId;
    }

    public void setAccordPlanId( final Integer accordPlanId )
    {
        this.accordPlanId = accordPlanId;
    }

    public Long getChangeStamp()
    {
        return changeStamp;
    }

    public void setChangeStamp( final Long changeStamp )
    {
        this.changeStamp = changeStamp;
    }

    public List<String> getMarketItemFeatures() 
    {
        return marketItemFeatures;
    }

    public void setMarketItemFeatures( final List<String> marketItemFeatures ) 
    {
        this.marketItemFeatures = marketItemFeatures;
    }

    public List<DescriptiveInfoBean> getMarketItemDescriptiveInfo() 
    {
        return marketItemDescriptiveInfo;
    }

    public void setMarketItemDescriptiveInfo(
            final List<DescriptiveInfoBean> marketItemDescriptiveInfo ) 
    {
        this.marketItemDescriptiveInfo = marketItemDescriptiveInfo;
    }

    public List<MarketingHighlightBean> getMarketItemMarketingHighlights() 
    {
        return marketItemMarketingHighlights;
    }

    public void setMarketItemMarketingHighlights( final List<MarketingHighlightBean> marketingHighlights ) 
    {
        this.marketItemMarketingHighlights = marketingHighlights;
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

	public void setMarketMembershipServiceable(boolean marketMembershipServiceable) {
		this.marketMembershipServiceable = marketMembershipServiceable;
	}
}
