package com.A.V.beans;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author jgerhard
 *
 */
@Embeddable
public class ProductDCValue implements Serializable//implements CommonBeanInterface 
{
    private static final long serialVersionUID = 5391206346788L;
    
    //string, boolean, integer
    private String dataType;
    
    /*integer DC type attributes*/
    private Boolean integerUnlimited;
    
    //pipe delimited
    private String intValueList;
    private int integerValue;
    private int integerMinValue;
    private int integerMaxValue;
        
    public Boolean getIntegerUnlimited() 
    {
        return integerUnlimited;
    }

    public void setIntegerUnlimited( final Boolean unlimited ) 
    {
        this.integerUnlimited = unlimited;
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

    //pipe delimited
    private String strValueList;
    private String stringValue;
    private int stringLength;
    private double stringComparisonData;
    
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

    /*Boolean DC type attributes*/
    private Boolean booleanValue;
    
    public Boolean getBooleanValue() 
    {
        return booleanValue;
    }
    public void setBooleanValue( final Boolean value ) 
    {
        this.booleanValue = value;
    }

    public double getStringComparisonData() 
    {
        return stringComparisonData;
    }

    public void setStringComparisonData( final double stringComparisonData ) 
    {
        this.stringComparisonData = stringComparisonData;
    }

    public String getDataType()
    {
        return dataType;
    }

    public void setDataType( String dataType )
    {
        this.dataType = dataType;
    }
}
