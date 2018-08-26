package com.A.V.beans.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table( name = "PR_PR_CATALOG" )
public class Product implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 790063108112839250L;

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "productSequence" )
    @SequenceGenerator( name = "productSequence", sequenceName = "PR_PRODUCT_SEQ", allocationSize = 1)
    private long id;
   
    private String datasource;

    @NotNull
    private Calendar timestamp;
    
    private ProductBase productBase;
        
    @OneToMany
    @JoinTable(name = "PR_PR_CATALOG_PR_FEATURE", joinColumns = @JoinColumn(name = "PR_PR_CATALOG_ID"))
    private List<ProductFeature> productFeatures;
    
    @OneToMany
    @JoinTable(name = "PR_PR_CATALOG_PR_PROMOTION", joinColumns = @JoinColumn(name = "PR_PR_CATALOG_ID"))
    private List<ProductPromotion> promotions;
    
    @OneToMany
    @JoinTable(name = "PR_PR_CATALOG_PR_CUSTOMIZATION", joinColumns = @JoinColumn(name = "PR_PR_CATALOG_ID"))
    private List<ProductCustomization> customizations = new ArrayList<ProductCustomization>();
    
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
    
    public void setProductBase( final ProductBase productBase )
    {
        this.productBase = productBase;
    }

    public ProductBase getProductBase()
    {
        return productBase;
    }

    public List<ProductFeature> getProductFeatures()
    {
        return productFeatures;
    }

    public void setProductFeatures( final List<ProductFeature> productFeatures )
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

    public void setPromotions( final List<ProductPromotion> promotions )
    {
        this.promotions = promotions;
    }

    public List<ProductPromotion> getPromotions()
    {
        return promotions;
    }

    public Calendar getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp( final Calendar timestamp )
    {
        this.timestamp = timestamp;
    }

    public List<ProductCustomization> getCustomizations()
    {
        return customizations;
    }

    public void setCustomizations( final List<ProductCustomization> customizations )
    {
        this.customizations = customizations;
    }
    
    /**
     * Adds a customization to the product.
     * @param customization
     *              the customization to be added
     */
    public void addCustomization( final ProductCustomization customization)
    {
        this.customizations.add( customization );
    }
}
