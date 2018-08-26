package com.A.V.beans.entity;

import java.util.ArrayList;
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

import com.A.V.beans.ProductCustomizationBase;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author gdesai
 * 
 */

@Entity
@Table( name = "PR_CUSTOMIZATION" )
public class ProductCustomization implements CommonBeanInterface, Customization
{


    /**
	 * 
	 */
	private static final long serialVersionUID = 8199804490712272581L;

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "productCustSequence" )
    @SequenceGenerator( name = "productCustSequence", sequenceName = "PR_CUSTOMIZATION_SEQ", allocationSize = 1)
    private long id;

    private ProductCustomizationBase productCustomizationBase;

    @OneToMany
    @JoinTable(name = "PR_CUSTOMIZATION_PR_CHOICE", joinColumns = @JoinColumn(name = "PR_CUSTOMIZATION_ID"))
    private List<ProductChoice> choices = new ArrayList<ProductChoice>();

    public long getId()
    {
        return id;
    }

    public void setId( final long id )
    {
        this.id = id;
    }

    public ProductCustomizationBase getProductCustomizationBase()
    {
        return productCustomizationBase;
    }

    public void setProductCustomizationBase( final ProductCustomizationBase productCustomizationBase )
    {
        this.productCustomizationBase = productCustomizationBase;
    }

    public List<ProductChoice> getChoices()
    {
        return choices;
    }

//    @Override
//    public void setChoices( final List<ProductChoice> choices )
//    {
//        this.choices = choices;
//    }
    
//    @Override
//	public void setChoices(List<? extends ProdChoice> choices) {
//		// TODO Auto-generated method stub
//		
//	}

	/**
     * Adds choice to customization.
     * @param choice
     *          the choice to be added
     */
    public void addChoice( final ProductChoice choice ) {
        choices.add( choice );
    }
 
}
