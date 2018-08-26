/**
 *
 */
package com.A.V.utility;

import java.io.Serializable;

/**
 * @author ebthomas
 * 
 */
public class AddressCompareResult implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1924174502071134503L;
    private int value;
    private boolean isNumber;

    /**
     * Default Constructor.
     */
    public AddressCompareResult()
    {
        super();
    }

    public int getValue()
    {
        return value;
    }

    public void setValue( final int value )
    {
        this.value = value;
    }

    public boolean isNumber()
    {
        return isNumber;
    }

    public void setNumber( final boolean isNumber )
    {
        this.isNumber = isNumber;
    }

}
