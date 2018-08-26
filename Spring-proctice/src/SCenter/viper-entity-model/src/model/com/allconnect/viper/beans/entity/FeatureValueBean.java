package com.A.V.beans.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.IndexColumn;

import com.A.V.beans.FeaturePriceInfo;
import com.A.V.beans.PointInTimeSuperClass;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author jgerhard
 * 
 */
@Entity
@org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
@Table( name = "featureValue" )

public class FeatureValueBean extends PointInTimeSuperClass implements CommonBeanInterface
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 2096243125680733438L;

	private static final int BATCH_SIZE = 10;

    @Id
    @GeneratedValue( generator = "featureValueBeanSequence" )
    @SequenceGenerator( name = "featureValueBeanSequence", sequenceName = "FEATURE_VALUE_BEAN_SEQ" )
    private long id;

    @Basic( optional = false )
    @Column( nullable = false )
    private String externalId;

    private DCValueBean dcValueBean;

    private FeaturePriceInfo priceInfo;
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    @org.hibernate.annotations.BatchSize( size = BATCH_SIZE )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private List<PriceTierBean> priceTiers;
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    @org.hibernate.annotations.BatchSize( size = BATCH_SIZE )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private List<FeatureDependencyBean> featureDependencies;
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    @org.hibernate.annotations.BatchSize( size = BATCH_SIZE )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private List<FeatureCapabilityBean> featureCapabilities;
    
    private Boolean included;
    private Boolean available;
    private Boolean display;
    private Boolean required;

    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    @org.hibernate.annotations.BatchSize( size = BATCH_SIZE )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    private List<String> tags;

    private Boolean hasTags;
    private Boolean hasCapabilities;
    private Boolean hasDependencies;
    private Boolean hasPriceTiers;
    
    public long getId()
    {
        return id;
    }

    public void setId( final long id )
    {
        this.id = id;
    }

    public final FeaturePriceInfo getFeaturePriceInfo()
    {
        return priceInfo;
    }

    public final void setFeaturePriceInfo( final FeaturePriceInfo priceInfo )
    {
        this.priceInfo = priceInfo;
    }

    public Boolean getDisplay()
    {
        return display;
    }

    public void setDisplay( final Boolean display )
    {
        this.display = display;
    }

    public Boolean getAvailable()
    {
        return available;
    }

    public void setAvailable( final Boolean available )
    {
        this.available = available;
    }

    public Boolean getIncluded()
    {
        return included;
    }

    public void setIncluded( final Boolean included )
    {
        this.included = included;
    }

    public final Boolean getRequired()
    {
        return required;
    }

    public final void setRequired( final Boolean required )
    {
        this.required = required;
    }

    public String getExternalId()
    {
        return externalId;
    }

    public void setExternalId( final String externalId )
    {
        this.externalId = externalId;
    }

    public List<String> getTags()
    {
        return tags;
    }

    public void setTags( final List<String> tags )
    {
        this.tags = tags;
    }

    public DCValueBean getDcValueBean()
    {
        return dcValueBean;
    }

    public void setDcValueBean( final DCValueBean dcValueBean )
    {
        this.dcValueBean = dcValueBean;
    }

    public List<PriceTierBean> getPriceTiers()
    {
        return priceTiers;
    }

    public void setPriceTiers( final List<PriceTierBean> priceTiers )
    {
        this.priceTiers = priceTiers;
    }

    public List<FeatureDependencyBean> getFeatureDependencies()
    {
        return featureDependencies;
    }

    public void setFeatureDependencies( final List<FeatureDependencyBean> featureDependencies )
    {
        this.featureDependencies = featureDependencies;
    }

    public List<FeatureCapabilityBean> getFeatureCapabilities()
    {
        return featureCapabilities;
    }

    public void setFeatureCapabilities( final List<FeatureCapabilityBean> featureCapabilities )
    {
        this.featureCapabilities = featureCapabilities;
    }

    public Boolean getHasTags()
    {
        return hasTags;
    }

    public void setHasTags( final Boolean hasTags )
    {
        this.hasTags = hasTags;
    }

    public Boolean getHasCapabilities()
    {
        return hasCapabilities;
    }

    public void setHasCapabilities( final Boolean hasCapabilities )
    {
        this.hasCapabilities = hasCapabilities;
    }

    public Boolean getHasDependencies()
    {
        return hasDependencies;
    }

    public void setHasDependencies( final Boolean hasDependencies )
    {
        this.hasDependencies = hasDependencies;
    }

    public Boolean getHasPriceTiers()
    {
        return hasPriceTiers;
    }

    public void setHasPriceTiers( final Boolean hasPriceTiers )
    {
        this.hasPriceTiers = hasPriceTiers;
    }

}
