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

import com.A.V.beans.ProductChoiceBase;

/**
 * @author gdesai 
 * 
 */
@Entity
@Table( name = "PR_CHOICE" )
public class ProductChoice  implements ProdChoice
{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1058082208797595917L;

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "productChoiceSequence" )
    @SequenceGenerator( name = "productChoiceSequence", sequenceName = "PR_CHOICE_SEQ", allocationSize = 1)
    private long id;

    private ProductChoiceBase productChoiceBase;

    @OneToMany
    @JoinTable(name = "PR_CHOICE_PR_CUSTOMIZATION", joinColumns = @JoinColumn(name = "PR_CHOICE_ID"))
    private List<ProductCustomization> customizations= new ArrayList<ProductCustomization>();

    public long getId()
    {
        return id;
    }

    public void setId( final long id )
    {
        this.id = id;
    }

    public ProductChoiceBase getProductChoiceBase()
    {
        return productChoiceBase;
    }

    public void setProductChoiceBase( final ProductChoiceBase productChoiceBase )
    {
        this.productChoiceBase = productChoiceBase;
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
     * Adds a customization to the choice.
     * @param customization
     *              the customization to be added
     */
    public void addCustomization( final ProductCustomization customization ) 
    {
       this.customizations.add( customization ); 
    }

   

}
