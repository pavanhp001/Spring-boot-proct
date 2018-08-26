package com.A.V.beans;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author gdesai
 * 
 */

@Embeddable
public class ProductCustomizationBase
{
    @Basic( optional = false )
    @Column( nullable = false )
    private String externalId;

    private String name;

    private String shortDescription;

    private String longDescription;

    private int minValue;

    private int maxValue;

    private String type;

    private Boolean required;

    private String productType;

    private int displayOrder;
    
    public String getExternalId()
    {
        return externalId;
    }

    public void setExternalId( final String externalId )
    {
        this.externalId = externalId;
    }

    public String getName()
    {
        return name;
    }

    public void setName( final String name )
    {
        this.name = name;
    }

    public String getShortDescription()
    {
        return shortDescription;
    }

    public void setShortDescription( final String shortDescription )
    {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription()
    {
        return longDescription;
    }

    public void setLongDescription( final String longDescription )
    {
        this.longDescription = longDescription;
    }

    public int getMinValue()
    {
        return minValue;
    }

    public void setMinValue( final int minValue )
    {
        this.minValue = minValue;
    }

    public int getMaxValue()
    {
        return maxValue;
    }

    public void setMaxValue( final int maxValue )
    {
        this.maxValue = maxValue;
    }

    public String getType()
    {
        return type;
    }

    public void setType( final String type )
    {
        this.type = type;
    }

    public Boolean getRequired()
    {
        return required;
    }

    public void setRequired( final Boolean required )
    {
        this.required = required;
    }

    public String getProductType()
    {
        return productType;
    }

    public void setProductType( final String productType )
    {
        this.productType = productType;
    }

    public int getDisplayOrder()
    {
        return displayOrder;
    }

    public void setDisplayOrder( final int displayOrder )
    {
        this.displayOrder = displayOrder;
    }

}
