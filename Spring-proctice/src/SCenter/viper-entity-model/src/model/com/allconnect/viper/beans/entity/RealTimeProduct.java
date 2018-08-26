package com.A.V.beans.entity;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.A.V.beans.ProductBase;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author jgerhard
 * 
 */

@Entity
@Table( name = "RT_PRODUCT" )
public class RealTimeProduct implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3392158405592339345L;

	@Id
    @GeneratedValue( generator = "rtProductSequence" )
    @SequenceGenerator( name = "rtProductSequence", sequenceName = "RT_PRODUCT_SEQ" )
    private long id;
   
    private String guid;
    private String datasource;
    private ProductBase productBase;
    
    @NotNull
    private Calendar timestamp;
    
    @ManyToMany
    private List<RealTimeProductFeature> productFeatures;
    
    @ManyToMany
    private List<RealTimePromotion> promotions;
       
    @OneToMany
    private List<RealTimeProductCustomization> customizations;
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

    public void setGuid( final String guid )
    {
        this.guid = guid;
    }

    public String getGuid()
    {
        return guid;
    }
    
    public void setProductBase( final ProductBase productBase )
    {
        this.productBase = productBase;
    }

    public ProductBase getProductBase()
    {
        return productBase;
    }

    public List<RealTimeProductFeature> getProductFeatures()
    {
        return productFeatures;
    }

    public void setProductFeatures( final List<RealTimeProductFeature> productFeatures )
    {
        this.productFeatures = productFeatures;
    }

    public String getDatasource()
    {
        return datasource;
    }

    public void setDatasource( final String datasource )
    {
        this.datasource = datasource;
    }

    public void setPromotions( final List<RealTimePromotion> promotions )
    {
        this.promotions = promotions;
    }

    public List<RealTimePromotion> getPromotions()
    {
        return promotions;
    }

    public List<RealTimeProductCustomization> getCustomizations()
    {
        return customizations;
    }

    public void setCustomizations( final List<RealTimeProductCustomization> customizations )
    {
        this.customizations = customizations;
    }

    

   
}
