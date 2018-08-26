package com.A.V.beans.entity;

import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.beans.PointInTimeSuperClass;
import com.A.V.interfaces.CommonBeanInterface;
import com.A.V.standard.AddressData;
import com.A.V.utility.StringUtils;

/**
 * @author ebaugh
 * 
 */

@Entity
@Table( name = "BP_ADDRESS")
public class BusinessPartyAddress extends PointInTimeSuperClass implements CommonBeanInterface
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 6149344597495938251L;
	
	public static final int ZIP5_LENGTH = 5;
    public static final int PLUS4_LENGTH = 4;
    
 
    @Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "bpAddressBeanSequence" )
    @SequenceGenerator( name = "bpAddressBeanSequence", sequenceName = "BP_ADDRESS_SEQ" ,allocationSize = 1)
    @Column(name="ADDRESS_ID")
    private long id;

    @Column(name="EXTERNAL_ID", unique=true,nullable=false)
    private Long externalId;
      
    @Column(name = "PREFIX_DIR")
    private String prefixDirectional;
    
    @Column(name = "STREET_NUM")
    private String streetNumber;
    
    @Column(name = "STREET_NAME")
    private String streetName;
    
    @Column(name = "LINE2")
    private String line2;
    
    @Column(name = "STREET_TYPE")
    private String streetType;
    
    @Column(name = "POST_DIR")
    private String postfixDirectional;
    
    @Column(name = "CITY_ALIAS")
    private String cityAlias;
    
    @Column(name = "CITY")
    private String city;
    
    @Column(name = "STATE_OR_PROV")
    private String stateOrProvince;
    
    @Column(name = "POSTAL_CODE")
    private String postalCode;
    
    @Column(name = "COUNTY")
    private String countyParishBorough;
    
    @Column(name = "COUNTRY")
    private String country;
    
    @Column(name = "INPUT_ADDR")
    private String inputAddress;

    @Column(name = "IS_PREV_SERVICE")
    private Boolean hasHadServicePreviously;
    
 
    private transient boolean isCitySoundexMatch;
    private transient boolean isStreetSoundexMatch;
    
     
   
    /**
     * Constructor.
     */
    public BusinessPartyAddress()
    {
        isCitySoundexMatch = true;
        isStreetSoundexMatch = false;
    }

    /**
     * Constructor takes an address bean.
     * 
     * @param address
     *            bean to be set
     * 
     */
    public BusinessPartyAddress( final BusinessPartyAddress address )
    {
        this.streetNumber = address.streetNumber;
        this.streetName = address.streetName;
        this.prefixDirectional = address.prefixDirectional;
        this.postfixDirectional = address.postfixDirectional;
        this.streetType = address.streetType;
        this.countyParishBorough = address.countyParishBorough;
        this.line2 = address.line2;
        this.postalCode = address.postalCode;
        this.city = address.city;
        this.cityAlias = address.cityAlias;
        this.stateOrProvince = address.stateOrProvince;
    }
    
    /**
     * Constructor.
     * 
     * @param streetInfo
     *            street info to use to set.
     * @param cityState
     *            cityState info to use to set.
     */
    public BusinessPartyAddress( final ZipPlusFour streetInfo, final ZipCodesCityState cityState )
    {
        this.streetName = streetInfo.getStName();
        this.prefixDirectional = streetInfo.getStPreDirAbbr();
        this.streetType = streetInfo.getStSuffixAbbr();
        this.postfixDirectional = streetInfo.getStPostDirAbbr();
        this.postalCode = streetInfo.getZipCode() + "-" + streetInfo.getPlus4Low();
        this.city = cityState.getCity();
        this.cityAlias = cityState.getCityAliasName();
        this.stateOrProvince = cityState.getState();
    }
    
    /**
     * Function to determine whether this address is a "zip-only" one.
     * @return true if addressBean is a zip-only one.
     */
    public boolean isZipOnly()
    {
        if ( ( StringUtils.isBlank( city ) && StringUtils.isBlank( cityAlias ) )
                &&  StringUtils.isBlank( stateOrProvince ) 
                && !StringUtils.isBlank( postalCode ) )
        {
            return true;
        }
        return false;
    }
    
    /** 
     * {@inheritDoc}
     */
    public boolean equals( final Object op )
    {
        if ( op instanceof AddressBean )
        {
            return equals( (AddressBean) op );
        }
        return false;
    }
    
    /**
     * Equal function for this class.   
     * @param op address to compare to this.
     * @return true if the equal rules all pass.
     */
    public boolean equals( final AddressBean op )
    {
        if ( !StringUtils.equalOrBothEmptyNull( this.streetName, op.getStreetName() ) )
        {
            return false;
        }
        
        if ( !StringUtils.equalOrBothEmptyNull(  this.streetNumber, op.getStreetNumber() ) )
        {
            return false;
        }
        
        if ( !StringUtils.equalOrBothEmptyNull( this.prefixDirectional, op.getPrefixDirectional() ) )
        {
            return false;
        }
        
        if ( !StringUtils.equalOrBothEmptyNull( this.streetType, op.getStreetType() ) )
        {
            return false;
        }
        
        if ( !StringUtils.equalOrBothEmptyNull( this.line2, op.getLine2() ) )
        {
            return false;
        }
        
        if ( !StringUtils.equalOrBothEmptyNull( this.postalCode, op.getPostalCode() ) )
        {
            return false;
        }
        
        if ( !StringUtils.equalOrBothEmptyNull( this.city, op.getCity() ) )
        {
            return false;
        }
        
        if ( !StringUtils.equalOrBothEmptyNull( this.cityAlias, op.getCityAlias() ) )
        {
            return false;
        }
        
        if ( !StringUtils.equalOrBothEmptyNull( this.postfixDirectional, op.getPostfixDirectional() ) )
        {
            return false;
        }
        
        if ( !StringUtils.equalOrBothEmptyNull( this.stateOrProvince, op.getStateOrProvince() ) )
        {
            return false;
        }
        
        if ( !StringUtils.equalOrBothEmptyNull( this.countyParishBorough, op.getCountyParishBorough() ) )
        {
            return false;
        }
        
     
        
        return true;
    }
    
    /**
     * Equal function for this class.   
     * @param op address to compare to this.
     * @return true if the equal rules all pass.
     */
    public boolean equalsCityOrAlias( final AddressBean op )
    {
        if ( !StringUtils.equalOrBothEmptyNull( this.streetName, op.getStreetName() ) )
        {
            return false;
        }
        
        if ( !StringUtils.equalOrBothEmptyNull(  this.streetNumber, op.getStreetNumber() ) )
        {
            return false;
        }
        
        if ( !StringUtils.equalOrBothEmptyNull( this.prefixDirectional, op.getPrefixDirectional() ) )
        {
            return false;
        }
        
        if ( !StringUtils.equalOrBothEmptyNull( this.streetType, op.getStreetType() ) )
        {
            return false;
        }
        
        if ( !StringUtils.equalOrBothEmptyNull( this.line2, op.getLine2() ) )
        {
            return false;
        }
        
        if ( !StringUtils.equalOrBothEmptyNull( this.postalCode, op.getPostalCode() ) )
        {
            return false;
        }
        
        if ( !StringUtils.equalOrBothEmptyNull( this.getCityOrAlias(), op.getCityOrAlias() ) )
        {
            return false;
        }
                
        if ( !StringUtils.equalOrBothEmptyNull( this.postfixDirectional, op.getPostfixDirectional() ) )
        {
            return false;
        }
        
        if ( !StringUtils.equalOrBothEmptyNull( this.stateOrProvince, op.getStateOrProvince() ) )
        {
            return false;
        }
        
        if ( !StringUtils.equalOrBothEmptyNull( this.countyParishBorough, op.getCountyParishBorough() ) )
        {
            return false;
        }
        
      
        
        return true;
    }
    
    /**
     * {@inheritDoc}
     */
    public int hashCode()
    {
        int value = 0;
        if (  this.streetName != null )
        {
            value += this.streetName.hashCode();
        }
        
        if ( this.streetNumber != null  )
        {
            value += this.streetNumber.hashCode();
        }
        
        if ( this.prefixDirectional != null  )
        {
            value += this.prefixDirectional.hashCode();
        }
        
        if (  this.streetType != null  )
        {
            value += this.streetType.hashCode();
        }
        
        if (  this.line2 != null  )
        {
            value += this.line2.hashCode();
        }
        
        if (  this.postalCode != null  )
        {
            value += this.postalCode.hashCode();
        }
        
        if ( this.city != null  )
        {
            value += this.city.hashCode();
        }
        
        if ( this.cityAlias != null  )
        {
            value += this.cityAlias.hashCode();
        }
        
        if (  this.postfixDirectional != null  )
        {
            value += this.postfixDirectional.hashCode();
        }
        
        if ( this.stateOrProvince != null  )
        {
            value += this.stateOrProvince.hashCode();
        }
        
        if ( this.countyParishBorough != null  )
        {
            value += this.countyParishBorough.hashCode();
        }
        
        
        
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long getId()
    {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setId( final long id )
    {
        this.id = id;
    }

    public String getInputAddress()
    {
        return inputAddress;
    }

    public void setInputAddress( final String inputAddress )
    {
        this.inputAddress = inputAddress;
    }

    

    public String getPrefixDirectional()
    {
        return prefixDirectional;
    }

    /**
     * For some reason this needs a java doc, even though we are suppose to not require it for getters an setters.
     * 
     * @param prefixDirectional
     *            data to store.
     */
    public void setPrefixDirectional( final String prefixDirectional )
    {
        if ( prefixDirectional != null )
        {
            this.prefixDirectional = prefixDirectional.trim();
        }
        else
        {
            this.prefixDirectional = null;
        }
    }

    public String getStreetNumber()
    {
        return streetNumber;
    }

    /**
     * To get Checkstyle to stop whining about this file.
     * @param streetNumber streetnumber.
     */
    public void setStreetNumber( final String streetNumber )
    {
        if ( streetNumber != null )
        {
            this.streetNumber = streetNumber.trim();
        }
        else
        {
            this.streetNumber = null;
        }
    }

    public final String getStreetName()
    {
        return streetName;
    }

    /**
     * To get Checkstyle to stop whining about this file.
     * @param streetName streetName
     */
    public final void setStreetName( final String streetName )
    {
        if ( streetName != null )
        {
            this.streetName = streetName.trim();
        }
        else
        {
            this.streetName = null;
        }
    }

    public String getLine2()
    {
        return line2;
    }

    /**
     * To get Checkstyle to stop whining about this file.
     * @param line2 line2
     */
    public void setLine2( final String line2 )
    {
        if ( line2 != null )
        {
            this.line2 = line2.trim();
        }
        else
        {
            this.line2 = null;
        }
    }
    
    /**
     * Function split apart a line2 into Unit Designator and Unit Number.
     * 
     * @return String[] with 0 being Unit Designator, 1 being Unit Number
     */
    private String[] splitLine2()
    {
        String firstPart = null;
        String secondPart = null;
        String[] result = new String[2];
        
        StringTokenizer st = new StringTokenizer( this.line2, " " );
        List<String> unitDesignators = AddressData.getUnitList();
        
        if ( this.line2 != null )
        {
            firstPart = st.nextToken();
        }
        
        if ( firstPart != null )
        {
            secondPart = this.line2.replace( firstPart, "" ).trim();
        }
        
        if ( firstPart != null )
        {
            // Some Unit Designators contain trailing spaces...because of parsing issues.
            if ( unitDesignators.contains(  firstPart ) || unitDesignators.contains(  firstPart + " " ) )
            {
                result[0] = firstPart;
                
                if ( secondPart != null )
                {
                    result[1] = secondPart;
                }
            }
            else if ( secondPart == null || secondPart.length() == 0 )
            {
                result[1] = firstPart;
            }
            else
            {
                result[1] = this.line2;
            }
        }   
        
        return result;
    }
    
    /**
     * Utility function to return full string of line2.
     * @return string containing unit and number from line2.
     */
    public String getLine2UnitFull()
    {
        String[] value = splitLine2();
        String expansion = AddressData.getUnitAbbr().get( value[0] );
        if ( expansion == null )
        {
            return value[0];
        }
        return expansion;
    }
    
    /**
     * Utility function to return just the unit designator.
     * @return returns the abbreviated unit designator.
     */
    public String getLine2UnitAbbr()
    {
        if ( line2 != null )
        {
            String[] value = splitLine2();
            return value[0];
        }
        
        return null;
    }
    
    /**
     * Utility function to return just the unit number.
     * @return returns the abbreviated unit number.
     */
    public String getLine2UnitNum()
    {
        if ( line2 != null )
        {
            String[] value = splitLine2();
            return value[1];
        }
        
        return null;
    }

    public String getStreetType()
    {
        return streetType;
    }
    
    /** Function to reverse the street type abbreviation into the expanded string version.
     * 
     * @return Expanded abbreviation.
     */
    public String getStreetTypeFromAbbr()
    {
        String result;      
        result = AddressData.getStreetTypeAbbrMap().get( streetType );
        if ( result == null )
        {
            return streetType;
        }
        return result;
    }

    /**
     * To get Checkstyle to stop whining about this file.
     * @param streetType streetType
     */
    public void setStreetType( final String streetType )
    {
        if ( streetType != null )
        {
            this.streetType = streetType.trim();
        }
        else
        {
            this.streetType = null;
        }
    }

    public String getPostfixDirectional()
    {
        return postfixDirectional;
    }

    /**
     * To get Checkstyle to stop whining about this file.
     * @param postfixDirectional postfixDirectional
     */
    public void setPostfixDirectional( final String postfixDirectional )
    {
        if ( postfixDirectional != null )
        {
            this.postfixDirectional = postfixDirectional.trim();
        }
        else
        {
            this.postfixDirectional = null;
        }
    }
    
    public final String getCityAlias()
    {
        return cityAlias;
    }

    /**
     * To get Checkstyle to stop whining about this file.
     * @param cityAlias cityAlias
     */
    public final void setCityAlias( final String cityAlias )
    {
        if ( cityAlias  != null )
        {
            // Do this here to make the toString easier to code
            // for city and cityAlias...
            if ( !cityAlias.trim().equals( "" ) )
            {
                this.cityAlias = cityAlias.trim();
            }
            else
            {
                this.cityAlias = null;
            }
        }
        else
        {
            this.cityAlias = null;
        }
    }

    public final String getCity()
    {
        return city;
    }

    /**
     * To get Checkstyle to stop whining about this file.
     * @param city city
     */
    public final void setCity( final String city )
    {
        if ( city != null )
        {
            this.city = city.trim();
        }
        else
        {
            this.city = null;
        }
    }

    /** 
     * Function to get city or alias, depending on which is set.
     * @return value of city name or city alias..with preference to alias.
     */
    public final String getCityOrAlias()
    {
        if ( cityAlias != null )
        {
            return getCityAlias();
        }
        else
        {
            return getCity();
        }
    }
    
    public final String getStateOrProvince()
    {
        return stateOrProvince;
    }

    /**
     * To get Checkstyle to stop whining about this file.
     * @param stateOrProvince stateOrProvince
     */
    public final void setStateOrProvince( final String stateOrProvince )
    {
        if ( stateOrProvince != null )
        {
            this.stateOrProvince = stateOrProvince.trim();
        }
        else
        {
            this.stateOrProvince = null;
        }
    }

    public final String getPostalCode()
    {
        return postalCode;
    }

    /**
     * Set the postalcode to the given value.  The value should be of the form: #####-####.
     * If the ZIP5 portion is not of the proper length, then we must throw the whole thing away.
     * If the Plus4 portion is not of the proper length, then we drop just the Plus4 portion.
     * @param postalCodeValue postalCodeValue
     */
    public final void setPostalCode( final String postalCodeValue )
    {
        String pcv = null;
        String zip5 = "";
        String plus4 = "";
        
        // Remove any trailing or internal whitespace
        pcv = postalCodeValue.trim();
        pcv.replaceAll( " ", "" );
        
        // If the postalcode has a '-' in it, we can tokenize it.
        if ( pcv.indexOf( "-" ) != -1 )
        {
            StringTokenizer st = new StringTokenizer( pcv , "-" );
            zip5 = st.nextToken();
            plus4 = st.nextToken();   
        }
        
        // In case there is no '-' separating the PostalCode components,
        // we have to tokenize it by character count.        
        else
        {
            if ( pcv.length() < ZIP5_LENGTH )
            {
                zip5 = pcv.substring( 0, pcv.length() );
            }
            else
            {
                zip5 = pcv.substring( 0, ZIP5_LENGTH  );
            }
                        
            if ( pcv.length() > ZIP5_LENGTH )
            {
                plus4 = pcv.substring( ZIP5_LENGTH, pcv.length() );   
            }
        }
        
        // Now, figure out what to set as the PostalCode, from the data that was given
        
        // If the ZIP5 portion isn't 5 digits, we can't except this data.
        if ( zip5.length() != ZIP5_LENGTH )
        {
            this.postalCode = null;
        }
        // If the ZIP5 portion is good, but the Plus4 is not, just set the ZIP5.
        else if ( zip5.length() == ZIP5_LENGTH && plus4.length() != PLUS4_LENGTH )
        {
            this.postalCode = zip5;
        }
        // If the ZIP5 portion is good, and so is the Plus4, then set them together,
        // with a '-' between them.
        else if ( zip5.length() == ZIP5_LENGTH && plus4.length() == PLUS4_LENGTH )
        {
            this.postalCode = zip5 + "-" + plus4;
        }
        // Catch-all case.  Shouldn't get here, but just in 'case'. 
        else
        {
            this.postalCode = null;
        }
        
    }

    /**
     * Function to return just the main "5" portion of a zip code.
     * @return zip5 value.
     */
    public final String getZip5()
    {
        String zip5 = null;

        // If the postal code isn't even set, then skip all of this 
        // logic and just return null
        if ( this.getPostalCode() != null )
        {    
            // Tokenize the Postal Code on the '-'. If there isn't one, then we assume that
            // if there are any numbers to be found, that they are the Zip5
            StringTokenizer st = new StringTokenizer( this.getPostalCode(), "-" );
            zip5 = st.nextToken();
    
            // If the length is less than 5, we have a problem :0
            // No sense in trying to recover -- fail.
            if ( zip5.length() < ZIP5_LENGTH )
            {
                throw new RuntimeException();
            }
        }
        
        return zip5;
    }

    /**
     * Function to return just the plus 4 portion of a zip code.
     * @return plus 4 value.
     */
    public final String getPlus4()
    {
        String plus4 = null;

        // If the postal code isn't even set, then skip all of this 
        // logic and just return null
        if ( this.getPostalCode() != null )
        {
            // Tokenize the Postal Code on the '-'. If there isn't one, then we assume that
            // if there are any numbers to be found, that they are the Zip5, and therefore there
            // isn't a Plus4 to return, so return null
            StringTokenizer st = new StringTokenizer( this.getPostalCode(), "-" );
            if ( st.countTokens() == 2 )
            {
                // Iterate past the first token, which is the Zip5
                st.nextToken();
                // Then get the Plus4
                plus4 = st.nextToken();
            }
    
            // If the length is less than 4, we have a problem :0
            // No sense in trying to recover -- fail.
            if ( plus4 != null && plus4.length() < PLUS4_LENGTH )
            {
                throw new RuntimeException();
            }
        }
        return plus4;
    }

    public final String getCountyParishBorough()
    {
        return countyParishBorough;
    }

    /**
     * To get Checkstyle to stop whining about this file.
     * @param countyParishBorough countyParishBorough
     */
    public final void setCountyParishBorough( final String countyParishBorough )
    {
        if ( countyParishBorough != null )
        {
            this.countyParishBorough = countyParishBorough.trim();
        }
        else
        {
            this.countyParishBorough = null;
        }
    }

    public final String getCountry()
    {
        return country;
    }

    /**
     * 
     * @param country
     *            -- checkstyle issue..
     */
    public final void setCountry( final String country )
    {
        if ( country != null )
        {
            this.country = country.trim();
        }
        else
        {
            this.country = null;
        }
    }

    public Boolean getHasHadServicePreviously()
    {
        return hasHadServicePreviously;
    }

    public void setHasHadServicePreviously( final Boolean hasHadServicePreviously )
    {
        this.hasHadServicePreviously = hasHadServicePreviously;
    }

    public boolean isStreetSoundexMatch()
    {
        return isStreetSoundexMatch;
    }

    public void setStreetSoundexMatch( final boolean isStreetSoundexMatch )
    {
        this.isStreetSoundexMatch = isStreetSoundexMatch;
    }
    
    public boolean isCitySoundexMatch()
    {
        return isCitySoundexMatch;
    }

    public void setCitySoundexMatch( final boolean isCitySoundexMatch )
    {
        this.isCitySoundexMatch = isCitySoundexMatch;
    }

    /**
     * @return the string version of the address components
     */
    @Override
    public String toString()
    {
        StringBuffer retval = new StringBuffer();

        if ( streetNumber != null )
        {
            if ( !streetNumber.equals( "" ) )
            {
                retval.append( streetNumber );
            }
        }

        if ( prefixDirectional != null )
        {
            if ( !prefixDirectional.equals( "" ) )
            {
                retval.append( " " );
                retval.append( prefixDirectional );
            }
        }

        if ( streetName != null )
        {
            if ( !streetName.equals( "" ) )
            {
                retval.append( " " );
                retval.append( streetName );
            }
        }

        if ( streetType != null )
        {
            if ( !streetType.equals( "" ) )
            {
                retval.append( " " );
                retval.append( streetType );
            }
        }

        if ( postfixDirectional != null )
        {
            if ( !postfixDirectional.equals( "" ) )
            {
                retval.append( " " );
                retval.append( postfixDirectional );
            }
        }

        if ( line2 != null )
        {
            if ( !line2.equals( "" ) )
            {
                retval.append( " " );
                retval.append( line2 );
            }
        }
        
        if ( retval.length() != 0 )
        {
            retval.append( ", " + getCityOrAlias() );
        }
        else 
        {
            retval.append( getCityOrAlias() );
        }
        
        
        if ( stateOrProvince != null )
        {
            if ( !stateOrProvince.equals( "" ) )
            {
                retval.append( " " );
                retval.append( stateOrProvince );
            }
        }

        if ( postalCode != null )
        {
            if ( !postalCode.equals( "" ) )
            {
                retval.append( " " );
                retval.append( postalCode );
            }
        }

        if ( countyParishBorough != null )
        {
            if ( !countyParishBorough.equals( "" ) )
            {
                retval.append( " " );
                retval.append( countyParishBorough );
            }
        }

        if ( country != null )
        {
            if ( !country.equals( "" ) )
            {
                retval.append( " " );
                retval.append( country );
            }
        }

        return retval.toString().trim();
    }

    
    
    
	public Long getExternalId() {
		return externalId;
	}

	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}
	 

	/**
     * This function strips of Secondary Suffix Abbreviation and returns only the Secondary Number from Line2.
     * 
     * @return unitNumber,just the unit Number portion from secondary Address
     * 
     */
    public String getSecondoryAddressNumber()
    {
        String[] searchFor = {"#", "APT", "SUITE", "STE", "UNIT", "UNT", "LOT", "TRLR", "SPC", "BLDG", "FL", "DEPT", "OFC", "BSMT", 
               "FRNT", "LBBY", "HDGR", "LOWR", "PIER", "PH", "REAR", "RM", "SIDE", "SLIP", "SPC", "STOP", "UPPR" };
        String unitNumber = this.getLine2();
        if ( unitNumber != null )
        {
            for ( int i = 0; i < searchFor.length; i++ )
            {
                unitNumber = unitNumber.replace( searchFor[i], "" ).trim();
            }
        }
        return unitNumber;
    }
}
