package com.A.V.beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
@Table( name = "RT_PR_FEATURE" )
public class RealTimeProductFeature implements CommonBeanInterface, Feature
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7685257649875661517L;

	@Id
    @GeneratedValue( generator = "rtProductFtSequence" )
    @SequenceGenerator( name = "rtProductFtSequence", sequenceName = "RT_PRODUCT_FT_SEQ" )
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
