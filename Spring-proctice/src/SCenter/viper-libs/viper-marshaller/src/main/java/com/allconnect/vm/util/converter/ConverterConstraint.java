/**
 *
 */
package com.A.vm.util.converter;

/**
 * @author ebthomas
 * 
 */
public enum ConverterConstraint
{
    mandatory( Boolean.FALSE ), optional( Boolean.TRUE );

    private Boolean isRequired = Boolean.FALSE;

    /**
     * @param value value
     */
    private ConverterConstraint( final Boolean value )
    {
        this.isRequired = value;
    }

    /**
     * @return boolean indicating is required field
     */
    public Boolean getIsRequired()
    {
        return isRequired;
    }

    /**
     * @param isRequired required
     */
    public void setIsRequired( final Boolean isRequired )
    {
        this.isRequired = isRequired;
    }

}
