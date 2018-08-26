package com.A.V.beans.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;
import org.hibernate.validator.NotNull;

import com.A.V.beans.PointInTimeSuperClass;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * 
 * @author ebaugh
 *
 */
@Entity
@Table( name = "promotion" )
public class PromotionBean extends PointInTimeSuperClass implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1835968907277988260L;

	@Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "promotionBeanSequence" )
    @SequenceGenerator( name = "promotionBeanSequence", sequenceName = "PROMO_BEAN_SEQ",allocationSize = 1 )
    private long id;

    private static final int MAX_LONG_STRING_LENGTH = 2000;
    private static final int MAX_SHORT_STRING_LENGTH = 1000;
    
    private String externalId;
    private String promotionCode;
    private String shortDescription;
    @Column( length = MAX_LONG_STRING_LENGTH )
    private String description;
    @Column( length = MAX_SHORT_STRING_LENGTH )
    private String qualification;
    @Column( length = MAX_LONG_STRING_LENGTH )
    private String conditions;

    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<String> relatedMarketItems = new ArrayList<String>();

    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<String> relatedItems = new ArrayList<String>();

    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<String> relatedBundles = new ArrayList<String>();
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<String> requiredFeatures = new ArrayList<String>();
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<String> promotionConflicts = new ArrayList<String>();
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<String> metaData = new ArrayList<String>();
    
    // This should contain one of the following strings...
    // informationalPromotion, baseMonthlyDiscount or oneTimeFeeDiscount
    private String type;

    // This should contain one of the following strings...if not null...
    // absolute, relative or relativePercentage
    private String priceValueType;
    private Float priceValue;
    private String promotionDuration;

    /**
    *
    */
    public PromotionBean()
    {
    }

    /**
     * 
     * @param id
     *            id of the promotion
     * @param promotionCode
     *            promotionCode of the promotion
     */
    public PromotionBean( final long id, final String promotionCode )
    {
        this.id = id;
        this.promotionCode = promotionCode;
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

    public String getExternalId()
    {
        return externalId;
    }

    public void setExternalId( final String externalId )
    {
        this.externalId = externalId;
    }

    public String getPromotionCode()
    {
        return promotionCode;
    }

    public void setPromotionCode( final String promotionCode )
    {
        this.promotionCode = promotionCode;
    }

    public String getShortDescription()
    {
        return shortDescription;
    }

    public void setShortDescription( final String shortDescription )
    {
        this.shortDescription = shortDescription;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( final String description )
    {
        this.description = description;
    }

    public String getQualification()
    {
        return qualification;
    }

    public void setQualification( final String qualification )
    {
        this.qualification = qualification;
    }

    public String getConditions()
    {
        return conditions;
    }

    public void setConditions( final String conditions )
    {
        this.conditions = conditions;
    }

    public List<String> getRelatedMarketItems()
    {
        return relatedMarketItems;
    }

    public void setRelatedMarketItems( final List<String> relatedMarketItems )
    {
        this.relatedMarketItems = relatedMarketItems;
    }

    public List<String> getRelatedItems()
    {
        return relatedItems;
    }

    public void setRelatedItems( final List<String> relatedItems )
    {
        this.relatedItems = relatedItems;
    }

    public String getType()
    {
        return type;
    }

    public void setType( final String type )
    {
        this.type = type;
    }

    public String getPriceValueType()
    {
        return priceValueType;
    }

    public void setPriceValueType( final String priveValueType )
    {
        this.priceValueType = priveValueType;
    }

    public Float getPriceValue()
    {
        return priceValue;
    }

    public void setPriceValue( final Float priceValue )
    {
        this.priceValue = priceValue;
    }

    public String getPromotionDuration()
    {
        return promotionDuration;
    }

    public void setPromotionDuration( final String promotionDuration )
    {
        this.promotionDuration = promotionDuration;
    }
    
    public Date getInEffectAsDate() 
    {
        return this.getInEffect().getTime();
    }
    public Date getExpirationAsDate() 
    {
        return this.getExpiration().getTime();
    }

    public List<String> getRequiredFeatures()
    {
        return requiredFeatures;
    }

    public void setRequiredFeatures( final List<String> requiredFeatures )
    {
        this.requiredFeatures = requiredFeatures;
    }

    public List<String> getConflictingPromotions()
    {
        return promotionConflicts;
    }

    public void setConflictingPromotions( final List<String> conflictingPromotions )
    {
        this.promotionConflicts = conflictingPromotions;
    }

    public List<String> getRelatedBundles()
    {
        return relatedBundles;
    }

    public void setRelatedBundles( final List<String> relatedBundles )
    {
        this.relatedBundles = relatedBundles;
    }

    public List<String> getMetaData()
    {
        return metaData;
    }

    public void setMetaData( final List<String> metaData )
    {
        this.metaData = metaData;
    }
    
}
