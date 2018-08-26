package com.A.V.beans.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

/**
 * @author ssathiyanarayanan
 * 
 */

@Entity
@Table( name = "ZIP_CODE_PLUS_FOUR" )
@org.hibernate.annotations.Table( appliesTo = "ZIP_CODE_PLUS_FOUR", 
  indexes = 
      { @Index( name = "ZIP_PLUS_FOUR_IX1", columnNames = {"zipCode", "stName" } ),
        @Index( 
             name = "ZIP_PLUS_FOUR_IX2", columnNames = {"stName", "addressPrimaryLowNumber", "addressPrimaryHighNumber" } ),
        @Index( 
             name = "ZIP_PLUS_FOUR_IX3", columnNames = {"state", "stName", "addressPrimaryLowNumber", "addressPrimaryHighNumber" } )
// @Index( 
//  name = "ZIP_PLUS_FOUR_IX4", columnNames = {"state", "SOUNDEX(stName)", "addressPrimaryLowNumber", "addressPrimaryHighNumber" } )
              } )
public class ZipPlusFour implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5068808272411199897L;
	
	@Id
    private String uniqueKey;
    private String zipCode;
    private String stPreDirAbbr;
    private String stName;
    private String stSuffixAbbr;
    private String stPostDirAbbr;
    private String addressPrimaryLowNumber;
    private String addressPrimaryHighNumber;
    private String addressPrimaryOddEven;
    private String addressSecondaryAbbr;
    private String addressSecondaryLowNumber;
    private String addressSecondaryHighNumber;
    private String addressSecondaryOddEven;
    private String state;
    private String plus4Low;
    private String plus4High;
    private String preferredLastLineCityStateKey;
    
    private transient Boolean isSoundexMatch;
    
    
    
    public String getUniqueKey()
    {
        return uniqueKey;
    }

    public void setUniqueKey( final String uniqueKey )
    {
        this.uniqueKey = uniqueKey;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode( final String zipCode )
    {
        this.zipCode = zipCode;
    }

    public String getStPreDirAbbr()
    {
        return stPreDirAbbr;
    }

    public void setStPreDirAbbr( final String stPreDirAbbr )
    {
        this.stPreDirAbbr = stPreDirAbbr;
    }

    public String getStName()
    {
        return stName;
    }

    public void setStName( final String stName )
    {
        this.stName = stName;
    }

    public String getStSuffixAbbr()
    {
        return stSuffixAbbr;
    }

    public void setStSuffixAbbr( final String stSuffixAbbr )
    {

        this.stSuffixAbbr = stSuffixAbbr;
    }

    public String getStPostDirAbbr()
    {

        return stPostDirAbbr;
    }

    public void setStPostDirAbbr( final String stPostDirAbbr )
    {

        this.stPostDirAbbr = stPostDirAbbr;
    }

    public String getAddressPrimaryLowNumber()
    {
        return addressPrimaryLowNumber;
    }

    public void setAddressPrimaryLowNumber( final String addressPrimaryLowNumber )
    {
        this.addressPrimaryLowNumber = addressPrimaryLowNumber;
    }

    public String getAddressPrimaryHighNumber()
    {
        return addressPrimaryHighNumber;
    }

    public void setAddressPrimaryHighNumber( final String addressPrimaryHighNumber )
    {
        this.addressPrimaryHighNumber = addressPrimaryHighNumber;
    }

    public String getAddressPrimaryOddEven()
    {

        return addressPrimaryOddEven;
    }

    public void setAddressPrimaryOddEven( final String addressPrimaryOddEven )
    {

        this.addressPrimaryOddEven = addressPrimaryOddEven;
    }

    public String getAddressSecondaryAbbr()
    {

        return addressSecondaryAbbr;
    }

    public void setAddressSecondaryAbbr( final String addressSecondaryAbbr )
    {

        this.addressSecondaryAbbr = addressSecondaryAbbr;
    }

    public String getPlus4Low()
    {

        return plus4Low;
    }

    public void setPlus4Low( final String plus4Low )
    {

        this.plus4Low = plus4Low;
    }

    public String getPlusFourHigh()
    {

        return plus4High;
    }

    public void setPlus4High( final String plus4High )
    {

        this.plus4High = plus4High;
    }

    public String getAddressSecondaryLowNumber()
    {
        return addressSecondaryLowNumber;
    }

    public void setAddressSecondaryLowNumber( final String addressSecondaryLowNumber )
    {
        this.addressSecondaryLowNumber = addressSecondaryLowNumber;
    }

    public String getAddressSecondaryHighNumber()
    {
        return addressSecondaryHighNumber;
    }

    public void setAddressSecondaryHighNumber( final String addressSecondaryHighNumber )
    {
        this.addressSecondaryHighNumber = addressSecondaryHighNumber;
    }

    public String getAddressSecondaryOddEven()
    {
        return addressSecondaryOddEven;
    }

    public void setAddressSecondaryOddEven( final String addressSecondaryOddEven )
    {
        this.addressSecondaryOddEven = addressSecondaryOddEven;
    }
    
    public String getState()
    {
        return state;
    }

    public void setState( final String state )
    {
        this.state = state;
    }

    public String getPlus4High()
    {
        return plus4High;
    }

    public String getPreferredLastLineCityStateKey()
    {
        return preferredLastLineCityStateKey;
    }

    public void setPreferredLastLineCityStateKey( final String preferredLastLineCityStateKey )
    {
        this.preferredLastLineCityStateKey = preferredLastLineCityStateKey;
    }
    
    /**
     * We're adding code to return false in the case nobody has set this, to allow for "tri-state" logic... 
     * since in some cases we have multiple references to this bean...
     * once it's set, we don't want to set it differently...per session.
     *  
     * @return true if match.
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
     * Setter for isSoundexMatch value.
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
        
        if ( preferredLastLineCityStateKey != null )
        {
            if ( !preferredLastLineCityStateKey.equals( "" ) )
            {
                retval.append( " preferred key=" );
                retval.append( preferredLastLineCityStateKey );
            }
        }
        
        if ( addressPrimaryLowNumber != null )
        {
            if ( !addressPrimaryLowNumber.equals( "" ) )
            {
                retval.append( " from=" );
                retval.append( addressPrimaryLowNumber );
            }
        }

        if ( addressPrimaryHighNumber != null )
        {
            if ( !addressPrimaryHighNumber.equals( "" ) )
            {
                retval.append( " to=" );
                retval.append( addressPrimaryHighNumber );
            }
        }

        if ( stPreDirAbbr != null )
        {
            if ( !stPreDirAbbr.equals( "" ) )
            {
                retval.append( stPreDirAbbr );
            }
        }
        
        if ( stName != null )
        {
            if ( !stName.equals( "" ) )
            {
                retval.append( " " );
                retval.append( stName );
            }
        }

        if ( stSuffixAbbr != null )
        {
            if ( !stSuffixAbbr.equals( "" ) )
            {
                retval.append( " " );
                retval.append( stSuffixAbbr );
            }
        }

        if ( stPostDirAbbr != null )
        {
            if ( !stPostDirAbbr.equals( "" ) )
            {
                retval.append( " " );
                retval.append( stPostDirAbbr );
            }
        }
        
        if ( stPostDirAbbr != null )
        {
            if ( !stPostDirAbbr.equals( "" ) )
            {
                retval.append( " " );
                retval.append( stPostDirAbbr );
            }
        }

        if ( addressPrimaryOddEven != null )
        {
            if ( !addressPrimaryOddEven.equals( "" ) )
            {
                retval.append( " Odd/Even/Both=" );
                retval.append( addressPrimaryOddEven );
            }
        }
        
        if ( addressSecondaryAbbr != null )
        {
            if ( !addressSecondaryAbbr.equals( "" ) )
            {
                retval.append( " " );
                retval.append( addressSecondaryAbbr );
            }
        }
        
        if ( addressSecondaryLowNumber != null )
        {
            if ( !addressSecondaryLowNumber.equals( "" ) )
            {
                retval.append( " from=" );
                retval.append( addressSecondaryLowNumber );
            }
        }
        
        if ( addressSecondaryHighNumber != null )
        {
            if ( !addressSecondaryHighNumber.equals( "" ) )
            {
                retval.append( " to=" );
                retval.append( addressSecondaryHighNumber );
            }
        }
        
        if ( addressSecondaryOddEven != null )
        {
            if ( !addressSecondaryOddEven.equals( "" ) )
            {
                retval.append( " Odd/Even/Both=" );
                retval.append( addressSecondaryOddEven );
            }
        }

        if ( zipCode != null )
        {
            if ( !zipCode.equals( "" ) )
            {
                retval.append( " " );
                retval.append( zipCode );
                retval.append( "-" );
            }
        }
        
        if ( plus4Low != null )
        {
            if ( !plus4Low.equals( "" ) )
            {
                retval.append( " from=" );
                retval.append( plus4Low );
            }
        }
        
        if ( plus4High != null )
        {
            if ( !plus4High.equals( "" ) )
            {
                retval.append(  " to=" );
                retval.append( plus4High );
            }
        }

        return retval.toString().trim();
    }

}
