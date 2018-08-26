package com.A.V.beans.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.IndexColumn;

/**
 * @author jgerhard
 * 
 */
@Embeddable
public class DCValueBean implements Serializable// implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5853565410427658739L;

	/* integer DC type attributes */
    private Boolean integerUnlimited;

    @ElementCollection
    @Cascade( { org.hibernate.annotations.CascadeType.ALL } )
    @IndexColumn( name = "listOrder", base = 0 )
    private List<Integer> intValues;
    private int integerValue;
    private int integerMinValue;
    private int integerMaxValue;
    private Integer integerDefault;

    public Boolean getIntegerUnlimited()
    {
        return integerUnlimited;
    }

    public void setIntegerUnlimited( final Boolean unlimited )
    {
        this.integerUnlimited = unlimited;
    }

    public List<Integer> getIntValues()
    {
        return intValues;
    }

    public void setIntValues( final List<Integer> listOfValues )
    {
        this.intValues = listOfValues;
    }

    public int getIntegerValue()
    {
        return integerValue;
    }

    public void setIntegerValue( final int value )
    {
        this.integerValue = value;
    }

    public int getIntegerMinValue()
    {
        return integerMinValue;
    }

    public void setIntegerMinValue( final int minValue )
    {
        this.integerMinValue = minValue;
    }

    public int getIntegerMaxValue()
    {
        return integerMaxValue;
    }

    public void setIntegerMaxValue( final int maxValue )
    {
        this.integerMaxValue = maxValue;
    }

    public Integer getIntegerDefault()
    {
        return integerDefault;
    }

    public void setIntegerDefault( final Integer integerDefault )
    {
        this.integerDefault = integerDefault;
    }

    /* String DC type attributes */
    @ElementCollection
    @Cascade( { org.hibernate.annotations.CascadeType.ALL } )
    @IndexColumn( name = "listOrder", base = 0 )
    private List<String> strValues;
    private String stringValue;
    private int stringLength;
    private double stringComparisonData;
    private String stringDefault;

    public List<String> getStrValues()
    {
        return strValues;
    }

    public void setStrValues( final List<String> listOfValues )
    {
        this.strValues = listOfValues;
    }

    public String getStringValue()
    {
        return stringValue;
    }

    public void setStringValue( final String value )
    {
        this.stringValue = value;
    }

    public int getStringLength()
    {
        return stringLength;
    }

    public void setStringLength( final int length )
    {
        this.stringLength = length;
    }

    public double getStringComparisonData()
    {
        return stringComparisonData;
    }

    public void setStringComparisonData( final double stringComparisonData )
    {
        this.stringComparisonData = stringComparisonData;
    }

    public String getStringDefault()
    {
        return stringDefault;
    }

    public void setStringDefault( final String stringDefault )
    {
        this.stringDefault = stringDefault;
    }

    /* Boolean DC type attributes */
    private Boolean booleanValue;
    private Boolean booleanDefault;

    public Boolean getBooleanValue()
    {
        return booleanValue;
    }

    public void setBooleanValue( final Boolean value )
    {
        this.booleanValue = value;
    }

    public Boolean getBooleanDefault()
    {
        return booleanDefault;
    }

    public void setBooleanDefault( final Boolean booleanDefault )
    {
        this.booleanDefault = booleanDefault;
    }

}
