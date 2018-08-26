package com.A.V.beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.beans.ProductFeatureBase;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author jgerhard
 * 
 */

@Entity
@Table( name = "PR_FEATURE" )
public class ProductFeature implements CommonBeanInterface, Feature
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3075228581091786459L;

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "productFtSequence" )
    @SequenceGenerator( name = "productFtSequence", sequenceName = "PR_PRODUCT_FT_SEQ", allocationSize = 1)
    private long id;
   
    private ProductFeatureBase productFeatureBase;
    
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

    public ProductFeatureBase getProductFeatureBase()
    {
        return productFeatureBase;
    }

    public void setProductFeatureBase( final ProductFeatureBase productFeatureBase )
    {
        this.productFeatureBase = productFeatureBase;
    }
}
