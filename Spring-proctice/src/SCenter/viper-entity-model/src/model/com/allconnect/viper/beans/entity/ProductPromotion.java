package com.A.V.beans.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table( name = "PR_PROMOTION" )
public class ProductPromotion implements CommonBeanInterface, Promotion
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5586135374964534624L;

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "productPromotionSequence" )
    @SequenceGenerator( name = "productPromotionSequence", sequenceName = "PR_PRODUCT_PROMO_SEQ", allocationSize = 1)
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
    private List<String> requiredFeaturesList = new ArrayList<String>();
    
    @Transient
    private List<String> promotionConflictsList = new ArrayList<String>();
    
    @Transient
    private List<String> metaDataList = new ArrayList<String>();
    
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

    /* (non-Javadoc)
	 * @see com.A.V.beans.entity.Promotion#getExternalId()
	 */
    @Override
	public String getExternalId()
    {
        return externalId;
    }

    public void setExternalId( final String externalId )
    {
        this.externalId = externalId;
    }

    /* (non-Javadoc)
	 * @see com.A.V.beans.entity.Promotion#getPromotionCode()
	 */
    @Override
	public String getPromotionCode()
    {
        return promotionCode;
    }

    public void setPromotionCode( final String promotionCode )
    {
        this.promotionCode = promotionCode;
    }

    /* (non-Javadoc)
	 * @see com.A.V.beans.entity.Promotion#getShortDescription()
	 */
    @Override
	public String getShortDescription()
    {
        return shortDescription;
    }

    public void setShortDescription( final String shortDescription )
    {
        this.shortDescription = shortDescription;
    }

    /* (non-Javadoc)
	 * @see com.A.V.beans.entity.Promotion#getDescription()
	 */
    @Override
	public String getDescription()
    {
        return description;
    }

    public void setDescription( final String description )
    {
        this.description = description;
    }

    /* (non-Javadoc)
	 * @see com.A.V.beans.entity.Promotion#getQualification()
	 */
    @Override
	public String getQualification()
    {
        return qualification;
    }

    public void setQualification( final String qualification )
    {
        this.qualification = qualification;
    }

    /* (non-Javadoc)
	 * @see com.A.V.beans.entity.Promotion#getConditions()
	 */
    @Override
	public String getConditions()
    {
        return conditions;
    }

    public void setConditions( final String conditions )
    {
        this.conditions = conditions;
    }

    /* (non-Javadoc)
	 * @see com.A.V.beans.entity.Promotion#getType()
	 */
    @Override
	public String getType()
    {
        return type;
    }

    public void setType( final String type )
    {
        this.type = type;
    }

    /* (non-Javadoc)
	 * @see com.A.V.beans.entity.Promotion#getPriceValueType()
	 */
    @Override
	public String getPriceValueType()
    {
        return priceValueType;
    }

    public void setPriceValueType( final String priveValueType )
    {
        this.priceValueType = priveValueType;
    }

    /* (non-Javadoc)
	 * @see com.A.V.beans.entity.Promotion#getPriceValue()
	 */
    @Override
	public Float getPriceValue()
    {
        return priceValue;
    }

    public void setPriceValue( final Float priceValue )
    {
        this.priceValue = priceValue;
    }

    /* (non-Javadoc)
	 * @see com.A.V.beans.entity.Promotion#getPromotionDuration()
	 */
    @Override
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
    
    /* (non-Javadoc)
	 * @see com.A.V.beans.entity.Promotion#getRequiredFeaturesList()
	 */
    @Override
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

    /* (non-Javadoc)
	 * @see com.A.V.beans.entity.Promotion#getPromotionConflictsList()
	 */
    @Override
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

    /* (non-Javadoc)
	 * @see com.A.V.beans.entity.Promotion#getMetaDataList()
	 */
    @Override
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
