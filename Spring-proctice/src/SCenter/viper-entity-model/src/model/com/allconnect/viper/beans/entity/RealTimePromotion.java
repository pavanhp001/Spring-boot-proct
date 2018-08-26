package com.A.V.beans.entity;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.NotNull;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * 
 * @author jgerhard
 *
 */
@Entity
@Table( name = "RT_PROMOTION" )
public class RealTimePromotion implements CommonBeanInterface, Promotion
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5980545973322306110L;

	@Id
    @GeneratedValue( generator = "realTimePromotionSequence" )
    @SequenceGenerator( name = "realTimePromotionSequence", sequenceName = "RT_PROMO_SEQ" )
    private long id;
        
    private static final int MAX_LONG_STRING_LENGTH = 3000;
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

    // This should contain one of the following strings...
    // informationalPromotion, baseMonthlyDiscount or oneTimeFeeDiscount
    private String type;

    // This should contain one of the following strings...if not null...
    // absolute, relative or relativePercentage
    private String priceValueType;
    private Float priceValue;
    private String promotionDuration;

    private String requiredFeatures;
    private String promotionConflicts;
    private String metaData;
    
    @Transient
    private List<String> requiredFeaturesList = null;
    
    @Transient
    private List<String> promotionConflictsList = null;
    
    @Transient
    private List<String> metaDataList = null;
    
    
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

    public String getRequiredFeatures()
    {
        return requiredFeatures;
    }

    public void setRequiredFeatures( final String requiredFeatures )
    {
        this.requiredFeatures = requiredFeatures;
    }

    public String getPromotionConflicts()
    {
        return promotionConflicts;
    }

    public void setPromotionConflicts( final String promotionConflicts )
    {
        this.promotionConflicts = promotionConflicts;
    }

    public String getMetaData()
    {
        return metaData;
    }

    public void setMetaData( final String metaData )
    {
        this.metaData = metaData;
    }

    /*
     * These 3 methods are convenience methods to access the data required and should be
     * used in place of the string methods for requiredFeatures, promotionConflicts, and metaData.
     */
    
    /**
     * Gets the required features list.  This method unmarshalls the flattened data from its compressed format.
     *
     * String encoding is FEATURE_EXT_ID1|FEATURE_EXT_ID2|FEATURE_EXT_ID3
     * @return the required features list
     */
    public List<String> getRequiredFeaturesList()
    {
        if ( requiredFeaturesList != null )
        {
            return requiredFeaturesList;
        }
        if ( requiredFeatures != null )
        {
            String[] temp = requiredFeatures.split( "\\|" );
            requiredFeaturesList = Arrays.asList( temp );
            return requiredFeaturesList;
        }
        else 
        {
            return null;
        }
    }

    /**
     * Gets the promotion conflicts list.  This method unmarshalls the flattened data from its compressed format.
     *
     * String encoding is PROMO_EXT_ID1|PROMO_EXT_ID2|PROMO_EXT_ID3
     * @return the promotion conflicts list
     */
    public List<String> getPromotionConflictsList()
    {
        if ( promotionConflictsList != null )
        {
            return promotionConflictsList;
        }
        if ( promotionConflicts != null )
        {
            String[] temp = promotionConflicts.split( "\\|" );
            promotionConflictsList = Arrays.asList( temp );
            return promotionConflictsList;
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
        if ( metaDataList != null )
        {
            return metaDataList;
        }
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
    
}
