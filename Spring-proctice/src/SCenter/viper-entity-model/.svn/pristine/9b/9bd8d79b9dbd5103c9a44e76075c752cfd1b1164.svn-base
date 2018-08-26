package com.A.V.beans.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.validator.NotNull;

import com.A.V.beans.CapabilitiesRecord;
import com.A.V.beans.PriceInfo;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author ebaugh
 *
 */

@Entity
@Table( name = "item" )
public class ItemBean extends CapabilitiesRecord implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7312879311237036752L;

	@Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "itemBeanSequence" )
    @SequenceGenerator( name = "itemBeanSequence", sequenceName = "ITEM_BEAN_SEQ",allocationSize = 1 )
    private long id;

    private String externalId;
    private String name;
    private String displayName;
    
    @OneToOne
    @ForeignKey( name = "ITEM_FK01" )
    private BusinessParty provider;
    
    @OneToOne
    @ForeignKey( name = "ITEM_FK02" )
    private ItemCategoryBean itemCategory;
    
    private PriceInfo itemPrice;
  
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<String> features;
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<DescriptiveInfoBean> descriptiveInfo;

    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<String> marketingHighlights;

    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<String> metaData;
    
    /**
     *
     */
    public ItemBean()
    {
    }

    /**
     *
     * @param id id of the item
     * @param name name of the item
     * @param displayName name to display for the item
     */
    public ItemBean( final long id, final String name, final String displayName )
    {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
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

    @NotNull
    // @Length(min = 4, max = 15)
    public final String getName()
    {
        return name;
    }

    public final void setName( final String name )
    {
        this.name = name;
    }

    @NotNull
    // @Length(min = 4, max = 15)
    public final String getDisplayName()
    {
        return displayName;
    }

    public final void setDisplayName( final String displayName )
    {
        this.displayName = displayName;
    }

    public BusinessParty getProvider()
    {
        return provider;
    }

    public void setProvider( final BusinessParty provider )
    {
        this.provider = provider;
    }

    
    public ItemCategoryBean getItemCategory()
    {
        return itemCategory;
    }

    public void setItemCategory( final ItemCategoryBean itemCategory )
    {
        this.itemCategory = itemCategory;
    }

    public PriceInfo getItemPrice()
    {
        return itemPrice;
    }

    public void setItemPrice( final PriceInfo itemPrice )
    {
        this.itemPrice = itemPrice;
    }

    public final List<String> getFeatures()
    {
        return features;
    }

    public final void setFeatures( final List<String> features )
    {
        this.features = features;
    }

    public List<DescriptiveInfoBean> getDescriptiveInfo()
    {
        return descriptiveInfo;
    }

    public void setDescriptiveInfo( final List<DescriptiveInfoBean> descriptiveInfo )
    {
        this.descriptiveInfo = descriptiveInfo;
    }
        
    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString()
    {
        return "ItemBean(" + id + "-" + name + ")";
    }

    public String getExternalId() 
    {
        return externalId;
    }

    public void setExternalId( final String externalId ) 
    {
        this.externalId = externalId;
    }
    public List<String> getMarketingHighlights()
    {
        return marketingHighlights;
    }

    public void setMarketingHighlights( final List<String> marketingHighlights )
    {
        this.marketingHighlights = marketingHighlights;
    }

    public void setMetaData( final List<String> metaData )
    {
        this.metaData = metaData;
    }

    public List<String> getMetaData()
    {
        return metaData;
    }
}
