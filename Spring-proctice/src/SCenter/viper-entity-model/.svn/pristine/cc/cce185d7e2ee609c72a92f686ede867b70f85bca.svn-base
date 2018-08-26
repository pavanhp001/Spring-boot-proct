package com.A.V.beans.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author ebaugh
 *
 */

@Entity
@Table( name = "itemCategory" )
public class ItemCategoryBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5507434214039491930L;

	@Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "itemCategoryBeanSequence" )
    @SequenceGenerator( name = "itemCategoryBeanSequence", sequenceName = "ITEM_CAT_BEAN_SEQ" ,allocationSize = 1)
    private long id;

    private String name;
    private String displayName;
    private String description;

    @ManyToMany
    private List<FeatureBean> features;
    
    /**
     * Default Constructor.
     */
    public ItemCategoryBean()
    {
    }

    /**
     * Constructor with full parameter setting.
     * @param id of item category to create.
     * @param name of item category to create.
     * @param displayName of item category to create.
     */
    public ItemCategoryBean( final long id, final String name, final String displayName )
    {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
    }

    @Override
    public final long getId()
    {
        return id;
    }

    @Override
    public final void setId( final long id )
    {
        this.id = id;
    }


    @NotNull
    // @Length(min = 4, max = 15)
    public final String getName()
    {
        return name;
    }


    public final void setName( final String name )
    {
        this.name = name;
    }


    @NotNull
    // @Length(min = 4, max = 15)
    public final String getDisplayName()
    {
        return displayName;
    }

    public final void setDisplayName( final String displayName )
    {
        this.displayName = displayName;
    }

    public List<FeatureBean> getFeatures() 
    {
        return features;
    }

    public void setFeatures( final List<FeatureBean> features ) 
    {
        this.features = features;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString()
    {
        return "ItemCategoryBean(" + id + "-" + name + "- NO Items )";
    }

    public String getDescription() 
    {
        return description;
    }

    public void setDescription( final String description ) 
    {
        this.description = description;
    }
}
