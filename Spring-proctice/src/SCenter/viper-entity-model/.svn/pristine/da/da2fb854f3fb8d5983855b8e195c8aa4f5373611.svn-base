package com.A.V.beans.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

/**
 * @author ssathiyanarayanan
 * 
 */

@Entity
@Table( name = "ZIP_CODE_DELUXE" )
@org.hibernate.annotations.Table( appliesTo = "ZIP_CODE_DELUXE", indexes = {
        @Index( name = "ZIP_CODES_DELUXEZIP_IX1", columnNames = { "state", "zipcode" } ),
        @Index( name = "ZIP_CODES_DELUXEZIP_IX2", columnNames = { "cityAliasName", "city" } )
// @Index( name = "ZIP_CODES_DELUXEZIP_IX3", columnNames = { "state", "SOUNDEX(city)", "SOUNDEX(cityAliasName"} )
} )
public class ZipCodesCityState implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4869275890196477299L;

	@Id
    @Column( name = "rowId" )
    private String rowId;

    @Index( name = "ZIP_CODES_DELUXEZIP_IX4", columnNames = { "zipCode" } )
    private String zipCode;
    private String city;
    private String state;
    private String county;
    private int population;
    private String cityType;

    @Index( name = "ZIP_CODES_DELUXEZIP_IX5", columnNames = { "cityAliasName" } )
    private String cityAliasName;

    private String cityStateKey;
    private char mailingName;
    private String preferredLastLineKey;

    private transient Boolean isSoundexMatch = null;

    public String getRowId()
    {
        return rowId;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode( final String zipCode )
    {
        this.zipCode = zipCode;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity( final String city )
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState( final String state )
    {
        this.state = state;
    }

    public String getCounty()
    {
        return county;
    }

    public void setCounty( final String county )
    {
        this.county = county;
    }

    public String getCityType()
    {
        return cityType;
    }

    public void setCityType( final String cityType )
    {
        this.cityType = cityType;
    }

    public String getCityAliasName()
    {
        return cityAliasName;
    }

    public void setCityAliasName( final String cityAliasName )
    {
        this.cityAliasName = cityAliasName;
    }

    public int getPopulation()
    {
        return population;
    }

    public void setPopulation( final int population )
    {
        this.population = population;
    }

    public String getCityStateKey()
    {
        return cityStateKey;
    }

    public void setCityStateKey( final String cityStateKey )
    {
        this.cityStateKey = cityStateKey;
    }

    public String getPreferredLastLineKey()
    {
        return preferredLastLineKey;
    }

    public char getMailingName()
    {
        return mailingName;
    }

    public void setMailingName( final char mailingName )
    {
        this.mailingName = mailingName;
    }

    public void setPreferredLastLineKey( final String preferredLastLineKey )
    {
        this.preferredLastLineKey = preferredLastLineKey;
    }

    /**
     * We're adding code to return false in the case nobody has set this, to allow for "tri-state" logic... 
     * since in some cases we have multiple references to this bean...
     * once it's set, we don't want to set it differently...per session.
     * 
     * @return true if is match.
     */
    public boolean isSoundexMatch()
    {
        if ( isSoundexMatch == null )
        {
            return false;
        }
        return isSoundexMatch;
    }

    /**
     * Default setter.
     * @param isSoundexMatch value to set.
     */
    public void setSoundexMatch( final boolean isSoundexMatch )
    {
        if ( this.isSoundexMatch == null )
        {
            this.isSoundexMatch = isSoundexMatch;
        }
    }

    /**
     * @return the string version of the address components
     */
    @Override
    public String toString()
    {
        StringBuffer retval = new StringBuffer();

        if ( preferredLastLineKey != null )
        {
            if ( !preferredLastLineKey.equals( "" ) )
            {
                retval.append( " preferred key=" );
                retval.append( preferredLastLineKey );
            }
        }

        if ( city != null )
        {
            if ( !city.equals( "" ) )
            {
                retval.append( city );
            }
        }

        if ( state != null )
        {
            if ( !state.equals( "" ) )
            {
                retval.append( " " );
                retval.append( state );
            }
        }

        if ( county != null )
        {
            if ( !county.equals( "" ) )
            {
                retval.append( " " );
                retval.append( county );
            }
        }

        if ( cityType != null )
        {
            if ( !cityType.equals( "" ) )
            {
                retval.append( " city type=" );
                retval.append( cityType );
            }
        }

        if ( cityAliasName != null )
        {
            if ( !cityAliasName.equals( "" ) )
            {
                retval.append( " alias=" );
                retval.append( cityAliasName );
            }
        }

        if ( cityStateKey != null )
        {
            if ( !cityStateKey.equals( "" ) )
            {
                retval.append( " cityStateKey=" );
                retval.append( cityStateKey );
            }
        }

        retval.append( " mailingName=" );
        retval.append( mailingName );

        if ( zipCode != null )
        {
            if ( !zipCode.equals( "" ) )
            {
                retval.append( " " );
                retval.append( zipCode );
            }
        }

        retval.append( " population=" );
        retval.append( population );

        return retval.toString().trim();
    }
}
