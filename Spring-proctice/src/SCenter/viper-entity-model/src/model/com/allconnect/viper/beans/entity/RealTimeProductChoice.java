package com.A.V.beans.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.beans.ProductChoiceBase;

/**
 * @author gdesai
 * 
 */
@Entity
@Table( name = "RT_CHOICE" )
public class RealTimeProductChoice implements ProdChoice
{


    /**
	 * 
	 */
	private static final long serialVersionUID = -1192650643132516693L;

	@Id
    @GeneratedValue( generator = "rtProductChoiceSequence" )
    @SequenceGenerator( name = "rtProductChoiceSequence", sequenceName = "RT_CHOICE_SEQ" )
    private long id;

    private ProductChoiceBase productChoiceBase;

    @OneToMany
    private List<RealTimeProductCustomization> customizations;

    public long getId()
    {
        return id;
    }

    public void setId( final long id )
    {
        this.id = id;
    }

    @Override
	public ProductChoiceBase getProductChoiceBase()
    {
        return productChoiceBase;
    }


	public void setProductChoiceBase( final ProductChoiceBase productChoiceBase )
    {
        this.productChoiceBase = productChoiceBase;
    }

    @Override
	public List<RealTimeProductCustomization> getCustomizations()
    {
        return customizations;
    }


	public void setCustomizations( final List<RealTimeProductCustomization> customizations )
    {
        this.customizations = customizations;
    }

}
