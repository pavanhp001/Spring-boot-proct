package com.A.V.beans.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.beans.ProductCustomizationBase;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author gdesai
 * 
 */

@Entity
@Table( name = "RT_CUSTOMIZATION" )
public class RealTimeProductCustomization implements CommonBeanInterface, Customization
{


    /**
	 * 
	 */
	private static final long serialVersionUID = 7586499023742191975L;

	@Id
    @GeneratedValue( generator = "rtProductCustSequence" )
    @SequenceGenerator( name = "rtProductCustSequence", sequenceName = "RT_CUSTOMIZATION_SEQ" )
    private long id;

    private ProductCustomizationBase productCustomizationBase;

    @OneToMany
    private List<RealTimeProductChoice> choices;

    public long getId()
    {
        return id;
    }

    public void setId( final long id )
    {
        this.id = id;
    }

    @Override
	public ProductCustomizationBase getProductCustomizationBase()
    {
        return productCustomizationBase;
    }

	public void setProductCustomizationBase( final ProductCustomizationBase productCustomizationBase )
    {
        this.productCustomizationBase = productCustomizationBase;
    }

	@Override
	public List<RealTimeProductChoice> getChoices()
    {
        return choices;
    }

	public void setChoices( final List<RealTimeProductChoice> choices )
    {
        this.choices = choices;
    }

}
