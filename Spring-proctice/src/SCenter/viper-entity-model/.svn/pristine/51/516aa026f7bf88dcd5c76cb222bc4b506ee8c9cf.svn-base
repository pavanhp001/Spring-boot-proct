package com.A.V.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;

import org.apache.commons.codec.language.Soundex;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.A.V.beans.entity.AddressBean;
import com.A.V.beans.entity.ZipCodesCityState;
import com.A.V.beans.entity.ZipPlusFour;
import com.A.V.interfaces.AddressVerificationInterface;
import com.A.V.standard.AddressData;

import org.apache.log4j.Logger;

/**
 * @author ebaugh
 * 
 */
/*@Startup
@Scope( ScopeType.SESSION )
@Name( "addressVerification" )*/
public class AddressVerification implements AddressVerificationInterface
{
    private static final long serialVersionUID = 6346885324173L;

    private static Logger logger;

    private static final int PRIMARY_PAD_LENGTH = 10;
    private static final int SECONDARY_PAD_LENGTH = 8;

    private static final int PERFECT_MATCH_SCORE = 50;
    private static final int MATCH_STREET_BONUS = 20;
    private static final int MATCH_SCORE_14 = 14;
    private static final int MATCH_SCORE_13 = 13;
    private static final int MATCH_SCORE_12 = 12;
    private static final int MATCH_SCORE_11 = 11;
    private static final int MATCH_SCORE_10 = 10;
    private static final int MATCH_SCORE_9 = 9;
    private static final int MATCH_SCORE_8 = 8;
    private static final int MATCH_SCORE_7 = 7;
    private static final int MATCH_SCORE_6 = 6;
    private static final int MATCH_SCORE_5 = 5;
    private static final int MATCH_SCORE_4 = 4;
    private static final int MATCH_SCORE_3 = 3;
    private static final int MATCH_SCORE_2 = 2;
    private static final int PEE_WEE_SCORE = 1;

    private static final int MAX_RETURN_ADDRESSES = 5;

    private static final int MAX_DIGITS_OF_NUMBER = 8;

    // NOTE: We use the special reference data entity manager here, not the one for the logical
    // model. At this time we only pull from the ref data source...
    //@In
    private transient EntityManager refDataEntityManager;

    private Session session;

    private static final int MAX_SOUNDEX_PAIRS = 5;
    private static final int SOUNDEX_SET_SIZE = 11;
    private static final long MAX_SOUNDEX_ROWS = 150;

    private Map<Double, String> statusCodes;
    private boolean fullStreetCall;
    private boolean cityStateOnlyCall;
    
    private static final String STRING_DASH = "-";
    private static final String STRING_PERIOD = ".";

    /**
     * Default Constructor.
     */
    public AddressVerification()
    {
        // Nothing happens here    	
    }

    /**
     * This constructor is for use by users outside of a Seam context, since is doesn't rely on an injected entityManager.
     * 
     * @param session
     *            the Hibernate session to use
     */
    public AddressVerification( final Session session )
    {
        this.session = session;
    }

    /**
     * Searches the Zip Code City State table and returns the cities that match the given input. This query only returns records
     * where the City alias is greater than 3 characters, or where the City and the City alias are both less than or equal to 3
     * characters. This is a less than perfect solution for the moment.
     * 
     * @param city
     *            City to search for.
     * @param state
     *            State to search for.
     * @return list of matching zipCodeCityState records.
     */
    @SuppressWarnings( "unchecked" )
    public List<ZipCodesCityState> getCityStateNoZip( final String city, final String state )
    {
        List<ZipCodesCityState> cityStateList = null;

        Criteria citystateCriteria = getSession().createCriteria( ZipCodesCityState.class );

        if ( StringUtils.isNotBlank( city ) && StringUtils.isNotBlank( state ) )
        {
            citystateCriteria.add( Restrictions.or( Restrictions.eq( "city", city ), Restrictions.eq( "cityAliasName", city ) ) )
                    .add( Restrictions.eq( "state", state ) ).add( Restrictions.eq( "mailingName", 'Y' ) ).add(
                            Restrictions.or( Restrictions.sqlRestriction( "( Length(CityAliasName) > 3 " ), Restrictions
                                    .sqlRestriction( " ( Length( CityAliasName ) <= 3 AND Length( City) <= 3 ) )" ) ) );

            // Returns the first record from the result set ordered by cityType

            cityStateList = (List<ZipCodesCityState>) citystateCriteria.addOrder( Order.desc( "cityType" ) ).list();
        }

        if ( cityStateList != null && cityStateList.size() == 0 )
        {
            cityStateList = null;
        }

        if ( cityStateList != null )
        {
            // And flag them as soundex matches.
            for ( ZipCodesCityState tempCity : cityStateList )
            {
                tempCity.setSoundexMatch( false );
            }
        }

        return cityStateList;
    }

    /**
     * Searches the Zip Code City State table and returns the cities that match the state and zipCode parameters.
     * 
     * @param city
     *            City to search for.
     * @param state
     *            State to search for.
     * @param zipCode
     *            ZipCode to search for.
     * @return list of matching zipCodeCityState records.
     */
    @SuppressWarnings( "unchecked" )
    public List<ZipCodesCityState> getCityState( final String city, final String state, final String zipCode )
    {
        // Create and initialize an array list
        List<ZipCodesCityState> cityStateList = null;

        Criteria citystateCriteria = getSession().createCriteria( ZipCodesCityState.class );
        if ( StringUtils.isNotBlank( city ) && StringUtils.isNotBlank( state ) && StringUtils.isNotBlank( zipCode ) )
        {
            citystateCriteria.add( Restrictions.or( Restrictions.eq( "city", city ), Restrictions.eq( "cityAliasName", city ) ) )
                    .add( Restrictions.eq( "state", state ) ).add( ( Restrictions.eq( "zipCode", zipCode ) ) ).add(
                            Restrictions.eq( "mailingName", 'Y' ) ).add(
                            Restrictions.or( Restrictions.sqlRestriction( "( Length( CityAliasName ) > 3 " ), Restrictions
                                    .sqlRestriction( " ( Length( CityAliasName ) <= 3 AND Length( City ) <= 3 ) ) " ) ) );

            cityStateList = (List<ZipCodesCityState>) citystateCriteria.list();
        }

        if ( cityStateList != null && cityStateList.size() == 0 )
        {
            cityStateList = null;
        }

        if ( cityStateList != null )
        {
            // And flag them as not soundex matches.
            for ( ZipCodesCityState tempCity : cityStateList )
            {
                tempCity.setSoundexMatch( false );
            }
        }

        return cityStateList;
    }

    /**
     * Function that looks for city State record based on a given zip code. This query only returns records where the City alias is
     * greater than 3 characters, or where the City and the City alias are both less than or equal to 3 characters. This is a less
     * than perfect solution for the moment.
     * 
     * @param zipCode
     *            postal Code used for matching.
     * @param singleMatch
     *            boolean representing whether or not to return more that one row/match
     * @return list of matching zipCodeCityState records.
     */
    @SuppressWarnings( "unchecked" )
    public List<ZipCodesCityState> getByZipCodeNoCityState( final String zipCode, final boolean singleMatch )
    {
        List<ZipCodesCityState> cityStateList = null;

        Criteria citystateCriteria = getSession().createCriteria( ZipCodesCityState.class );

        if ( StringUtils.isNotBlank( zipCode ) )
        {
            citystateCriteria.add( Restrictions.eq( "zipCode", zipCode ) ).add( Restrictions.eq( "mailingName", 'Y' ) ).add(
                    Restrictions.or( Restrictions.sqlRestriction( "( Length(CityAliasName) > 3 " ), Restrictions
                            .sqlRestriction( " ( Length( CityAliasName ) <= 3 AND Length( City) <= 3 ) ) " ) ) );

            if ( singleMatch )
            {
                citystateCriteria.add( Restrictions.eq( "cityType", "P" ) );
            }

            // Returns the first record from the result set ordered by cityType
            // citystateCriteria.addOrder( Order.desc( "cityType" ) );
            cityStateList = (List<ZipCodesCityState>) citystateCriteria.list();
        }

        if ( cityStateList != null && cityStateList.size() == 0 )
        {
            cityStateList = null;
        }

        if ( cityStateList != null )
        {
            // And flag them as soundex matches.
            for ( ZipCodesCityState tempCity : cityStateList )
            {
                tempCity.setSoundexMatch( false );
            }
        }

        return cityStateList;

    }

    /**
     * Searches the Zip Code + 4 table and returns the matching streets.
     * 
     * @param streetNumber
     *            Number to search for.
     * @param streetName
     *            Name to search for.
     * @param unitNumber
     *            Unit number to use (for RR addresses )
     * @param zipCode
     *            ZipCode to search for.
     * @return list of matching streets.
     */
    public List<ZipPlusFour> getStreetsWithZip( final String streetNumber, final String streetName, final String unitNumber,
            final String zipCode )
    {
        DetachedCriteria streetCriteria;
        List<ZipPlusFour> streetList = null;

        // If the Zip isn't set, we're not going to find anything with this query...
        if ( StringUtils.isBlank( zipCode ) || StringUtils.isBlank( streetName )
                || ( StringUtils.isBlank( streetNumber ) && StringUtils.isBlank( unitNumber ) ) )
        {
            return null;
        }

        streetCriteria = buildStreetCoreCriteria( streetNumber, streetName, unitNumber );

        // If we couldn't build the criteria, then we can't do anything else, so return null.
        if ( streetCriteria == null )
        {
            return null;
        }

        // Conditions for ZIP Code..
        streetCriteria.add( Restrictions.eq( "zipCode", zipCode ) );

        // Now get the street matches..
        streetList = getStreetMatches( streetCriteria, null );

        if ( streetList != null )
        {	
            // And flag them as non-soundex matches.
            for ( ZipPlusFour street : streetList )
            {
                street.setSoundexMatch( false );
            }
        }

        return streetList;
    }

    /**
     * Searches the Zip Code + 4 table and returns the matching streets.
     * 
     * @param streetNumber
     *            Number to search for.
     * @param streetName
     *            Name to search for.
     * @param unitNumber
     *            Unit number to search for (for RR addresses)
     * @param state
     *            State to search for.
     * @return list of matching streets.
     */
    public List<ZipPlusFour> getStreetsWithState( final String streetNumber, final String streetName, final String unitNumber,
            final String state )
    {
        DetachedCriteria streetCriteria;
        List<ZipPlusFour> streetList = null;

        // If the street info isn't set, we're not going to find anything with this query...
        if ( StringUtils.isBlank( state ) || StringUtils.isBlank( streetName )
                || ( StringUtils.isBlank( streetNumber ) && StringUtils.isBlank( unitNumber ) ) )
        {
            return null;
        }

        streetCriteria = buildStreetCoreCriteria( streetNumber, streetName, unitNumber );

        // If we couldn't build the criteria, then we can't do anything else, so return null.
        if ( streetCriteria == null )
        {
            return null;
        }

        // Conditions for state..
        streetCriteria.add( Restrictions.eq( "state", state ) );

        // Now get the street matches..
        streetList = getStreetMatches( streetCriteria, null );

        if ( streetList != null )
        {
            // And flag them as non-soundex matches.
            for ( ZipPlusFour street : streetList )
            {
                street.setSoundexMatch( false );
            }
        }

        return streetList;
    }

    /**
     * Searches the Zip Code + 4 table and returns the matching streets by "soundex" matching.
     * 
     * @param streets
     *            Working set of streets so far. To use to prevent duplicates being added, and having the side-effect of being
     *            flagged incorrectly.
     * @param prefixDirectional
     *            Prefix directional to use in searching.
     * @param streetNumber
     *            Number to search for.
     * @param streetName
     *            Name to search for.
     * @param zips
     *            Zips to restrict search by.
     */
    public void updateStreetListBySoundex( final List<ZipPlusFour> streets, final String prefixDirectional,
            final String streetNumber, final String streetName, final List<String> zips )
    {
        // We go up and down Max values, and could do this potentially for
        // two values and also want a slot for the exact soundex value.
        List<String> soundExValues = new ArrayList<String>();

        // If the street info isn't set, we're not going to find anything with this query...
        if ( zips.size() < 1 || StringUtils.isBlank( streetName ) || StringUtils.isBlank( streetNumber ) )
        {
            return;
        }

        DetachedCriteria streetCriteria = buildStreetNumberCriteria( getSession(), streetNumber );

        // Conditions for state..
        StringBuilder zipSQL = new StringBuilder( " ( ZIPCODE IN ( " );
        boolean commaFlag = false;
        String streetName1;

        for ( int i = 0; i < zips.size(); i++ )
        {
            if ( commaFlag )
            {
                zipSQL.append( " , '" + zips.get( i ) + "'" );
            }
            else
            {
                zipSQL.append( " '" + zips.get( i ) + "'" );
            }
            commaFlag = true;
        }
        zipSQL.append( "  ) ) " );
        streetCriteria.add( Restrictions.sqlRestriction( zipSQL.toString() ) );
        boolean success = true;
        String fixedStreetName1 = fixSpecialAbbreviations( streetName );

        // This is odd exception #2 we have to handle here.
        // The database has both "COUNTY ROAD XXX" and "XXX" as street names
        // so we have to query for both if this is in the street name...
        if ( streetName.indexOf( "COUNTY ROAD" ) != -1 && !streetName.equals( "COUNTY ROAD" ) )
        {
            streetName1 = streetName.replace( "COUNTY ROAD", "" ).trim();
            success = success && buildSoundexStreetCriteria( prefixDirectional, streetName, soundExValues, streetName1 );
        }
        // This is odd exception #3 we have to handle here.
        // The database has both "SAINT XXX" and "ST XXX" as street names
        // so we have to query for both if this is in the street name...
        else if ( streetName.indexOf( "SAINT " ) != -1 )
        {
            streetName1 = streetName.replace( "SAINT ", "ST " ).trim();
            success = success && buildSoundexStreetCriteria( prefixDirectional, streetName, soundExValues, streetName1 );
        }
        // This handles a bunch of odd abbreviation exceptions, which can can do together...
        else if ( fixedStreetName1 != null )
        {
            streetName1 = fixedStreetName1.trim();
            success = success && buildSoundexStreetCriteria( prefixDirectional, streetName, soundExValues, streetName1 );
        }
        else
        {
            success = success && buildSoundexStreetCriteria( prefixDirectional, streetName, soundExValues, null );
        }

        // If we couldn't add at least some soundEx Values...
        if ( !success )
        {
            return;
        }

        executeStreetSoundexStrategy( streets, soundExValues, streetCriteria );
    }

    /**
     * Utility function to build the criteria for soundex searching.
     * 
     * @param prefixDirectional
     *            prefix directional to use to build soundex criteria.
     * @param streetName
     *            street name to use to build soundex criteria.
     * @param soundExValues
     *            list of computed soundex values.
     * @param streetName1
     *            additional formatted street name.
     * @return true if it can successfully build soundex criteria.
     */
    private boolean buildSoundexStreetCriteria( final String prefixDirectional, final String streetName,
            final List<String> soundExValues, final String streetName1 )
    {
        boolean success = addSoundExValues( soundExValues, streetName );
        if ( streetName1 != null )
        {
            success = success && addSoundExValues( soundExValues, streetName1 );
        }
        if ( prefixDirectional != null )
        {
            success = success && addSoundExValues( soundExValues, generateAltPrefixDirStreetName( prefixDirectional, streetName ) );

            if ( streetName1 != null )
            {
                success = success
                        && addSoundExValues( soundExValues, generateAltPrefixDirStreetName( prefixDirectional, streetName1 ) );
            }
        }
        return success;
    }

    /**
     * This function performs the soundEx strategy we've come up with for the street soundEx matching.
     * 
     * @param streets
     *            Result List of Streets.
     * @param soundExValues
     *            List of soundEx Values to check for
     * @param streetCriteria
     *            The criteria to use for querying.
     */
    private void executeStreetSoundexStrategy( final List<ZipPlusFour> streets, final List<String> soundExValues,
            final DetachedCriteria streetCriteria )
    {
        // Now build the query with the SoundEx values generated.
        List<ZipPlusFour> tempList = null;
        List<String> updatedSoundExValues = new ArrayList<String>();

        for ( String tempStr : soundExValues )
        {
            if ( !updatedSoundExValues.contains( tempStr ) )
            {
                updatedSoundExValues.add( tempStr );
            }
        }

        if ( updatedSoundExValues.size() > 0 )
        {
            Criteria clonedCriteria = streetCriteria.getExecutableCriteria( getSession() );
            RestrictedSoundexCriterion subExpression = new RestrictedSoundexCriterion();

            // Add the sub-expression...the part that's going to change...
            subExpression.setFieldName( "stname" );
            clonedCriteria.add( subExpression );

            // If this happens, it means we have multiple values to
            // search for via soundEx, i.e. COUNTY ROAD and COUNTY RD.
            if ( updatedSoundExValues.size() > SOUNDEX_SET_SIZE )
            {
                for ( int j = 0; j < SOUNDEX_SET_SIZE; j++ )
                {
                    // We should have some "multiple" of MAX_SOUNDEX_PAIRS, based on the number
                    // of street values we generated soundEx values for...
                    for ( int k = 0; k < ( updatedSoundExValues.size() / SOUNDEX_SET_SIZE ); k++ )
                    {
                        if ( MAX_SOUNDEX_ROWS - streets.size() <= 0 )
                        {
                            // We're done... return..
                            return;
                        }

                        // We want to take each N'th item, because the pattern is
                        // Best Value, Lesser, Lesser, Lesser, Best Value Lesser Lesser Lesser
                        // where this repeating pattern occurs every MAX_SOUNDEX_PAIRS. Each block
                        // is for the next street value we generated soundEx values for.
                        subExpression.setPhoneticMatchValue( updatedSoundExValues.get( ( k * SOUNDEX_SET_SIZE ) + j ) );
                        subExpression.setRowNum( MAX_SOUNDEX_ROWS - streets.size() );

                        tempList = getStreetMatchesWithCriteria( clonedCriteria );

                        if ( tempList != null )
                        {
                            // We need to make sure we're not adding duplicates here...
                            for ( ZipPlusFour street : tempList )
                            {
                                if ( !streets.contains( street ) )
                                {
                                    street.setSoundexMatch( true );
                                    streets.add( street );
                                }
                            }
                        }
                    }
                }
            }
            // In this case, we only have one street value we're soundex'ing on.
            else
            {
                for ( int i = 0; i < updatedSoundExValues.size(); i++ )
                {
                    if ( MAX_SOUNDEX_ROWS - streets.size() <= 0 )
                    {
                        // We're done... return..
                        return;
                    }

                    subExpression.setPhoneticMatchValue( updatedSoundExValues.get( i ) );
                    subExpression.setRowNum( MAX_SOUNDEX_ROWS - streets.size() );

                    tempList = getStreetMatchesWithCriteria( clonedCriteria );
                    if ( tempList != null )
                    {
                        // We need to make sure we're not adding duplicates here...
                        for ( ZipPlusFour street : tempList )
                        {
                            if ( !streets.contains( street ) )
                            {
                                street.setSoundexMatch( true );
                                streets.add( street );
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Searches the City State table and returns the matching streets by "soundex" matching.
     * 
     * @param cities
     *            Working set of cities so far. To use to prevent duplicates being added, and having the side-effect of being
     *            flagged incorrectly.
     * @param cityName
     *            Name to search for.
     * @param state
     *            State to restrict search by.
     */
    public void updateCitiesListBySoundex( final List<ZipCodesCityState> cities, final String cityName, final String state )
    {
        List<ZipCodesCityState> cityStateList = new ArrayList<ZipCodesCityState>();

        DetachedCriteria cityStateCriteria = DetachedCriteria.forClass( ZipCodesCityState.class );

        if ( cities == null )
        {
            throw new RuntimeException( "Had NULL Cities List in updateCitiesListBySoundex" );
        }

        // We go up and down Max values, and could do this potentially for
        // two values and also want a slot for the exact soundEx value.
        List<String> soundExValues = new ArrayList<String>();

        if ( StringUtils.isBlank( cityName ) && StringUtils.isBlank( state ) )
        {
            return;
        }

        cityStateCriteria.add( Restrictions.eq( "state", state ) ).add( Restrictions.eq( "mailingName", 'Y' ) ).add(
                Restrictions.or( Restrictions.sqlRestriction( "( Length(CityAliasName) > 3 " ), Restrictions
                        .sqlRestriction( " ( Length( CityAliasName ) <= 3 AND Length( City) <= 3 ) )" ) ) );

        boolean success = addSoundExValues( soundExValues, cityName );

        // If we couldn't add at least some soundEx Values...
        if ( !success )
        {
            return;
        }

        executeCitySoundexStrategy( cityStateList, soundExValues, cityStateCriteria );

        if ( cityStateList.size() != 0 )
        {
            cities.addAll( cityStateList );
        }
    }

    /**
     * This function performs the soundEx strategy we've come up with for the cities soundEx matching.
     * 
     * @param cities
     *            Result List of Cities.
     * @param soundExValues
     *            List of soundEx Values to check for
     * @param streetCriteria
     *            The criteria to use for querying.
     */
    private void executeCitySoundexStrategy( final List<ZipCodesCityState> cities, final List<String> soundExValues,
            final DetachedCriteria streetCriteria )
    {
        // Now build the query with the SoundEx values generated.
        List<ZipCodesCityState> tempList = null;

        if ( soundExValues.size() > 0 )
        {
            Criteria clonedCriteria = streetCriteria.getExecutableCriteria( getSession() );
            RestrictedSoundexCriterion subExpression1 = new RestrictedSoundexCriterion();
            RestrictedSoundexCriterion subExpression2 = new RestrictedSoundexCriterion();

            // Add the sub-expressions...the parts that are going to change...
            subExpression1.setFieldName( "cityAliasName" );
            subExpression2.setFieldName( "city" );
            clonedCriteria.add( subExpression1 );
            clonedCriteria.add( subExpression2 );
            clonedCriteria.addOrder( Order.desc( "cityType" ) );

            // If this happens, it means we have multiple values to
            // search for via soundEx, i.e. COUNTY ROAD and COUNTY RD.
            if ( soundExValues.size() > SOUNDEX_SET_SIZE )
            {
                for ( int j = 0; j < SOUNDEX_SET_SIZE; j++ )
                {
                    // We should have some "multiple" of MAX_SOUNDEX_PAIRS, based on the number
                    // of city values we generated soundEx values for...
                    for ( int k = 0; k < ( soundExValues.size() / SOUNDEX_SET_SIZE ); k++ )
                    {
                        if ( MAX_SOUNDEX_ROWS - cities.size() <= 0 )
                        {
                            // We're done... return..
                            return;
                        }

                        subExpression1.setPhoneticMatchValue( soundExValues.get( ( k * SOUNDEX_SET_SIZE ) + j ) );
                        subExpression2.setPhoneticMatchValue( soundExValues.get( ( k * SOUNDEX_SET_SIZE ) + j ) );
                        subExpression1.setRowNum( MAX_SOUNDEX_ROWS - cities.size() );
                        subExpression2.setRowNum( MAX_SOUNDEX_ROWS - cities.size() );

                        // We want to take each Nth item, because the pattern is
                        // Best Value, Lesser, Lesser, Lesser, Best Value Lesser Lesser Lesser
                        // where this repeating pattern occurs every MAX_SOUNDEX_PAIRS. Each block
                        // is for the next city value we generated soundEx values for.
                        tempList = getCityMatchesWithCriteria( clonedCriteria );
                        if ( tempList != null )
                        {
                            // We need to make sure we're not adding duplicates here...
                            for ( ZipCodesCityState city : tempList )
                            {
                                if ( !cities.contains( city ) )
                                {
                                    city.setSoundexMatch( true );
                                    cities.add( city );
                                }
                            }
                        }
                    }
                }
            }
            // In this case, we only have one city value we're soundex'ing on.
            else
            {
                for ( int i = 0; i < soundExValues.size(); i++ )
                {
                    if ( MAX_SOUNDEX_ROWS - cities.size() <= 0 )
                    {
                        // We're done... return..
                        return;
                    }

                    subExpression1.setPhoneticMatchValue( soundExValues.get( i ) );
                    subExpression2.setPhoneticMatchValue( soundExValues.get( i ) );
                    subExpression1.setRowNum( MAX_SOUNDEX_ROWS - cities.size() );
                    subExpression2.setRowNum( MAX_SOUNDEX_ROWS - cities.size() );

                    tempList = getCityMatchesWithCriteria( clonedCriteria );
                    if ( tempList != null )
                    {
                        // We need to make sure we're not adding duplicates here...
                        for ( ZipCodesCityState city : tempList )
                        {
                            if ( !cities.contains( city ) )
                            {
                                city.setSoundexMatch( true );
                                cities.add( city );
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Function to get city matches, given a city criteria.
     * 
     * @param cityStateCriteria
     *            the criteria with all values set.
     * @return the list of ZipCodesCityState records that match, and checked for possible perfect matches.
     */
    @SuppressWarnings( "unchecked" )
    private List<ZipCodesCityState> getCityMatchesWithCriteria( final Criteria cityStateCriteria )
    {
        List<ZipCodesCityState> cityStateList = null;
        if ( logger != null )
        {
            logger.trace( "Attempting to fetch Cities in getCityMatchesWithCriteria( final Criteria cityCriteria ) " );
        }

        cityStateList = (List<ZipCodesCityState>) cityStateCriteria.list();

        if ( logger != null )
        {
            logger.trace( "Completed query for getCityMatchesWithCriteria( final Criteria cityCriteria ) " );
        }

        if ( cityStateList != null && cityStateList.size() == 0 )
        {
            cityStateList = null;
        }

        return cityStateList;
    }

    /**
     * Function to generate and add the SoundExValues for querying that way.
     * 
     * @param soundexValues
     *            array to store values in.
     * @param stringToSoundex
     *            string to work with to generate additional values.
     * @return true if successful, false otherwise.
     */
    private boolean addSoundExValues( final List<String> soundexValues, final String stringToSoundex )
    {
        String value = new Soundex().soundex( stringToSoundex );
        // Make sure we didn't get empty data.
        if ( value == null || value.length() < 1 )
        {
            return false;
        }
        String header = value.substring( 0, 1 );
        int startingValue = Integer.valueOf( value.substring( 1 ) );

        // Add the best match value...
        soundexValues.add( value );

        // ... and add the standard "deviation" values...
        for ( int i = 1; i <= MAX_SOUNDEX_PAIRS; i++ )
        {
            soundexValues.add( header + String.valueOf( startingValue + i ) );
            soundexValues.add( header + String.valueOf( startingValue - i ) );
        }

        return true;
    }

    /**
     * Function to build the core criteria use for both get functions.
     * 
     * @param streetNumber
     *            the Street Number to use in the criteria.
     * @param streetName
     *            the Street Name to use in the criteria.
     * @param unitNumber
     *            the Unit Number to use in the criteria (for RR addresses)
     * @return the criteria with the street name and number added.
     */
    private DetachedCriteria buildStreetCoreCriteria( final String streetNumber, final String streetName, final String unitNumber )
    {
        DetachedCriteria streetCriteria;
        String streetName1 = null;
        String streetName2 = null;

        // If we have a rural route address, we need to check the line2 info, in the case where
        // the street number is blank...
        if ( streetName.contains( "RR " ) && StringUtils.isBlank( streetNumber ) )
        {
            streetCriteria = buildStreetNumberCriteria( getSession(), unitNumber );
        }
        else
        {
            streetCriteria = buildStreetNumberCriteria( getSession(), streetNumber );
        }

        // This is an odd exception #1 we have to handle here.
        // The database has both "U S HIGHWAY" and "US HIGHWAY" as street names
        // so we have to query for both if this is in the street name...
        // We also use this check to also try for US when we have just "HIGHWAY" entered..
        if ( streetName.indexOf( "HIGHWAY" ) != -1 )
        {
            if ( streetName.indexOf( "US HIGHWAY" ) != -1 )
            {
                streetName1 = streetName.replace( "US HIGHWAY", "U S HIGHWAY" ).trim();
                streetCriteria.add( Restrictions.or( Restrictions.eq( "stName", streetName ), Restrictions.eq( "stName",
                        streetName1 ) ) );
            }
            else
            {
                streetName1 = streetName.replace( "HIGHWAY", "U S HIGHWAY" ).trim();
                streetName2 = streetName.replace( "HIGHWAY", "US HIGHWAY" ).trim();
                streetCriteria.add( Restrictions.or( Restrictions.or( Restrictions.eq( "stName", streetName1 ), Restrictions.eq(
                        "stName", streetName2 ) ), Restrictions.eq( "stName", streetName ) ) );
            }
        }
        // This is odd exception #2 we have to handle here.
        // The database has both "COUNTY ROAD XXX" and "XXX" as street names
        // so we have to query for both if this is in the street name...
        else if ( streetName.indexOf( "COUNTY ROAD" ) != -1 && !streetName.equals( "COUNTY ROAD" ) )
        {
            streetName1 = streetName.replace( "COUNTY ROAD", "" ).trim();
            streetCriteria
                    .add( Restrictions.or( Restrictions.eq( "stName", streetName ), Restrictions.eq( "stName", streetName1 ) ) );
        }
        // This is odd exception #3 we have to handle here.
        // The database has both "SAINT XXX" and "ST XXX" as street names
        // so we have to query for both if this is in the street name...
        else if ( streetName.indexOf( "SAINT " ) != -1 )
        {
            streetName1 = streetName.replace( "SAINT ", "ST " ).trim();
            streetCriteria
                    .add( Restrictions.or( Restrictions.eq( "stName", streetName ), Restrictions.eq( "stName", streetName1 ) ) );
        }
        // This handles a bunch of odd abbreviation exceptions, which can can do together...
        else if ( hasPotentialSpecialAbbreviation( streetName ) )
        {
            streetName1 = fixSpecialAbbreviations( streetName ).trim();
            streetCriteria
                    .add( Restrictions.or( Restrictions.eq( "stName", streetName ), Restrictions.eq( "stName", streetName1 ) ) );
        }
        else
        {
            streetCriteria.add( Restrictions.eq( "stName", streetName ) );
        }

        return streetCriteria;
    }

    /**
     * Function to build the street number portion of the criteria.
     * 
     * @param session
     *            Session to work with.
     * @param streetNumber
     *            parsed street number to work with.
     * @return criteria built up from the number.
     */
    private DetachedCriteria buildStreetNumberCriteria( final Session session, final String streetNumber )
    {
        DetachedCriteria streetCriteria = DetachedCriteria.forClass( ZipPlusFour.class );
        // Convert the given street number into a '0' padded string
        String paddedAddressNum = padAddressNum( streetNumber );

        try
        {
            // If the number has a dash in it, we have to check to see if it's just a number without the dash..
            if ( paddedAddressNum.contains( "-" ) )
            {
                // Remove the dash, and try to re-pad the results...
                String addressNumNoDash = paddedAddressNum.replace( "-", "" );
                String paddedAddrNumNoDash = padAddressNum( addressNumNoDash );

                // Here we do stuff with the non-numeric version of the "street number", and since it's got a dash,
                // we need add the criteria with the value with the "-" in it as well...
                if ( !isNumeric( paddedAddrNumNoDash ) )
                {
                    streetCriteria.add( Restrictions.or( Restrictions.and( Restrictions.le( "addressPrimaryLowNumber",
                            paddedAddrNumNoDash ), Restrictions.ge( "addressPrimaryHighNumber", paddedAddrNumNoDash ) ),
                            Restrictions.and( Restrictions.le( "addressPrimaryLowNumber", paddedAddressNum ), Restrictions.ge(
                                    "addressPrimaryHighNumber", paddedAddressNum ) ) ) );
                }
                else
                { // Here we do some stuff with the numeric version of the "street number", and since it's got a dash,
                    // we need add the criteria with the value with the "-" in it in all cases..
                    if ( Integer.parseInt( paddedAddrNumNoDash ) % 2 == 0 )
                    {
                        streetCriteria.add( Restrictions.or( Restrictions.and( Restrictions.le( "addressPrimaryLowNumber",
                                paddedAddrNumNoDash ), Restrictions.and( Restrictions.ge( "addressPrimaryHighNumber",
                                paddedAddrNumNoDash ), Restrictions.in( "addressPrimaryOddEven", Arrays.asList( "E", "B" ) ) ) ),
                                Restrictions.and( Restrictions.le( "addressPrimaryLowNumber", paddedAddressNum ), Restrictions.ge(
                                        "addressPrimaryHighNumber", paddedAddressNum ) ) ) );
                    }
                    else
                    {
                        streetCriteria.add( Restrictions.or( Restrictions.and( Restrictions.le( "addressPrimaryLowNumber",
                                paddedAddrNumNoDash ), Restrictions.and( Restrictions.ge( "addressPrimaryHighNumber",
                                paddedAddrNumNoDash ), Restrictions.in( "addressPrimaryOddEven", Arrays.asList( "O", "B" ) ) ) ),
                                Restrictions.and( Restrictions.le( "addressPrimaryLowNumber", paddedAddressNum ), Restrictions.ge(
                                        "addressPrimaryHighNumber", paddedAddressNum ) ) ) );
                    }
                }
            }
            else
            {

                // If it doesn't have a dash... and
                // If it's not a numeric address... just check straight...
                if ( !isNumeric( paddedAddressNum ) )
                {
                    streetCriteria.add( Restrictions.le( "addressPrimaryLowNumber", paddedAddressNum ) ).add(
                            Restrictions.ge( "addressPrimaryHighNumber", paddedAddressNum ) );
                }
                // If it is numeric, then we have to check for odd or even address....
                else
                {
                    if ( Integer.parseInt( paddedAddressNum ) % 2 == 0 )
                    {
                        streetCriteria.add( Restrictions.le( "addressPrimaryLowNumber", paddedAddressNum ) ).add(
                                Restrictions.ge( "addressPrimaryHighNumber", paddedAddressNum ) ).add(
                                Restrictions.in( "addressPrimaryOddEven", Arrays.asList( "E", "B" ) ) );
                    }
                    else
                    {
                        streetCriteria.add( Restrictions.le( "addressPrimaryLowNumber", paddedAddressNum ) ).add(
                                Restrictions.ge( "addressPrimaryHighNumber", paddedAddressNum ) ).add(
                                Restrictions.in( "addressPrimaryOddEven", Arrays.asList( "O", "B" ) ) );
                    }
                }
            }

        }
        catch ( NumberFormatException e )
        {
            if ( logger != null )
            {
                logger.error( "Number Format Exception in Street Number " + e );
            }
            return null;
        }

        return streetCriteria;
    }

    /**
     * Function to get street matches, given a street criteria.
     * 
     * @param streetCriteria
     *            the criteria with all values set.
     * @param soundExValue
     *            the value for the soundEx parameter in the criteria.
     * @return the list of ZipPlusFour records that match, and checked for possible perfect matches.
     */
    @SuppressWarnings( "unchecked" )
    private List<ZipPlusFour> getStreetMatches( final DetachedCriteria streetCriteria, final String soundExValue )
    {
        List<ZipPlusFour> streetList = null;
        if ( logger != null )
        {
            logger.trace( "Attempting to fetch Streets in getStreetMatches( final Criteria streetCriteria ) " );
        }

        Criteria executableCriteria = streetCriteria.getExecutableCriteria( getSession() );

        streetList = (List<ZipPlusFour>) executableCriteria.list();

        if ( logger != null )
        {
            logger.trace( "Completed query for getStreetMatches( final Criteria streetCriteria ) " );
        }

        if ( streetList != null && streetList.size() == 0 )
        {
            streetList = null;
        }

        return streetList;
    }

    /**
     * Function to get street matches, given a street criteria.
     * 
     * @param streetCriteria
     *            the criteria with all values set.
     * @return the list of ZipPlusFour records that match, and checked for possible perfect matches.
     */
    @SuppressWarnings( "unchecked" )
    private List<ZipPlusFour> getStreetMatchesWithCriteria( final Criteria streetCriteria )
    {
        List<ZipPlusFour> streetList = null;
        if ( logger != null )
        {
            logger.trace( "Attempting to fetch Streets in getStreetMatchesWithCriteria( final Criteria streetCriteria ) " );
        }

        streetList = (List<ZipPlusFour>) streetCriteria.list();

        if ( logger != null )
        {
            logger.trace( "Completed query for getStreetMatchesWithCriteria( final Criteria streetCriteria ) " );
        }

        if ( streetList != null && streetList.size() == 0 )
        {
            streetList = null;
        }

        return streetList;
    }

    /**
     * The the appropriate session for this instance. If the instance is within a Seam context, it should return an
     * EntityManager-derived Session. If it's outside of Seam, then it will come from the value that was passed in the constructor.
     * 
     * @return the session
     */
    private Session getSession()
    {
        if ( refDataEntityManager != null )
        {
            return (Session) refDataEntityManager.getDelegate();
        }
        else
        {
            return session;
        }
    }

    /**
     * A method to clear the underlying EntityManager cache. Useful in those cases where the caller knows that the results that it
     * is creating do not have usefulness outside of the scope of execution.
     */
    public void clearEMCache()
    {
        refDataEntityManager.clear();
    }

    public boolean isFullStreetCall()
    {
        return fullStreetCall;
    }

    public void setFullStreetCall( final boolean fullStreetCall )
    {
        this.fullStreetCall = fullStreetCall;
    }

    public boolean isCityStateOnlyCall()
    {
        return cityStateOnlyCall;
    }

    public void setCityStateOnlyCall( final boolean cityStateOnlyCall )
    {
        this.cityStateOnlyCall = cityStateOnlyCall;
    }

    /**
     * Helper Function to take a ZipCodesCityState record and an existing AddressBean and build a new AddressBean based on the merge
     * from the two.
     * 
     * @param cityState
     *            ZipCodesCityState Info to use for the merge.
     * @param address
     *            Existing AddressBean to use for the merge.
     * @return merged AddressBean
     */
    private AddressBean createMergedAddressBean( final ZipCodesCityState cityState, final AddressBean address )
    {
        AddressBean temp = new AddressBean();
        temp.setCity( cityState.getCity() );
        temp.setCityAlias( cityState.getCityAliasName() );
        temp.setStateOrProvince( cityState.getState() );
        if ( StringUtils.isBlank( address.getPostalCode() ) )
        {
            temp.setPostalCode( cityState.getZipCode() );
        }
        // TODO -- We could check to see if we're given a correct plus 4, by doing a query
        // against the street table verify the zip5 and plus4 are assigned to just one row.
        else
        {
            temp.setPostalCode( address.getPostalCode() );
        }

        return temp;
    }

    /**
     * Function that returns either a single best match, or a list of suggestions.
     * 
     * @param rawAddress
     *            the parsed, but unstandardized address representation of address.
     * @param address
     *            the address that we're checking.
     * @param statusCodes
     *            the status code map for the calling NormalizedVerifiedAddress bean
     * @param trySoundex
     *            flag to indicate whether or not to try Soundex
     * 
     * @return best address or list of suggestions.
     */
    public List<AddressBean> getBestMatchOrSuggestions( final AddressBean rawAddress, final AddressBean address,
            final Map<Double, String> statusCodes, final boolean trySoundex )
    {
        List<AddressBean> results = new ArrayList<AddressBean>();
        AddressBean temp;
        this.statusCodes = statusCodes;

        // Get info about the address...
        boolean cityBlank = StringUtils.isBlank( address.getCity() );
        boolean stateBlank = StringUtils.isBlank( address.getStateOrProvince() );
        boolean zipBlank = StringUtils.isBlank( address.getZip5() );
        boolean streetNumberBlank = StringUtils.isBlank( address.getStreetNumber() );
        boolean streetNameBlank = StringUtils.isBlank( address.getStreetName() );
        List<ZipCodesCityState> cityStates = getCityRecords( address, cityBlank, stateBlank, zipBlank, trySoundex );

        // No need to continue if we couldn't find any city or states...
        if ( cityStates == null )
        {
            return null;
        }

        List<ZipPlusFour> streets = getStreetRecords( rawAddress, address, streetNameBlank, streetNumberBlank, cityStates,
                trySoundex );

        // If we got here, and have some streets and cities
        if ( streets != null && cityStates != null )
        {
        	// ### This is a good place to look at the initial ZipPlusFour (street) records, ###
        	// ### and eliminate any of them that don't need to make it through to scoring.  ###
        	
        	// Special processing for any Wisconsin-style grid addresses
        	streets = evaluateWisconsinAddresses( streets, address );
        	// Special processing for any Utah-style grid addresses
        	
        	// Take the remaining streets and cityStates, and combine and score them
            results = calculateBestMatchOrSuggestions( address, cityStates, streets );

            
            // Alternate Approaches : If we got no results, and we tried to get streets using street name, number
            // and zip, we can try changing to the state approach, and trying again...if we had already done this,
            // then no reason to do it again...
            if ( results.size() == 0 && isFullStreetCall() )
            {
                streets = getStreetsTwoStateTries( address, address.getStreetName(), cityStates );
                if ( streets == null )
                {
                    addStatusCode( ServiceabilityProcessingCodes.STREET_NOT_FOUND );
                }
                else if ( streets != null && cityStates != null )
                {
                    results = calculateBestMatchOrSuggestions( address, cityStates, streets );
                }
            }

            if ( results.size() == 0 && !isCityStateOnlyCall() )
            {
                cityStates = getCityStateNoZip( address.getCity(), address.getStateOrProvince() );

                // We're not putting the error codes here for City, because we must have gotten something with the
                // main query to even get here, & we're LOOSENING that criteria, and should AT LEAST get what we before.
                if ( streets != null && cityStates != null )
                {
                    results = calculateBestMatchOrSuggestions( address, cityStates, streets );
                }
            }
        }
        else if ( streets == null && cityStates != null && streetNumberBlank && streetNameBlank )
        {
            // Here we take the potential city state matches and build addressBeans to return...We follow similar
            // rules as below for determining if it's a zip only address... if that's the case then we only add
            // a result if it's a primary, and this is determined by checking the city and alias names being equal...
            // we don't want to come into here if we had some street info that ended up not giving us any streets...
            if ( address.isZipOnly() )
            {
                for ( ZipCodesCityState cityState : cityStates )
                {
                    if ( StringUtils.equalOrBothEmptyNull( cityState.getCity(), cityState.getCityAliasName() ) )
                    {
                        temp = createMergedAddressBean( cityState, address );
                        results.add( temp );
                    }
                }
            }
            else
            {
                ZipCodesCityState tempCityState = null;
                for ( ZipCodesCityState cityState : cityStates )
                {
                    if ( tempCityState == null )
                    {
                        tempCityState = cityState;
                    }
                    else
                    {
                        if ( tempCityState.getPopulation() < cityState.getPopulation() )
                        {
                            tempCityState = cityState;
                        }
                    }
                }
                temp = createMergedAddressBean( tempCityState, address );
                results.add( temp );
            }
        }

        // As as last step, look to see if any addresses had invalid +4 (i.e. 23ND -- which indicates that
        // the +4 is not defined fully for that address as of yet) and remove this info if present....
        for ( AddressBean addressResult : results )
        {
            if ( addressResult.getPlus4() != null && addressResult.getPlus4().contains( "ND" ) )
            {
                addressResult.setPostalCode( addressResult.getZip5() );
            }
        }

        if ( results != null )
        {
            if ( results.size() > 1 )
            {
                addStatusCode( ServiceabilityProcessingCodes.MULTIPLE_ADDRESSES_FOUND );
                removeStatusCode( ServiceabilityProcessingCodes.NO_MATCHES_FOUND );
            }
            else if ( results.size() == 1 )
            {
                removeStatusCode( ServiceabilityProcessingCodes.MULTIPLE_ADDRESSES_FOUND );

                if ( statusCodes.get( ServiceabilityProcessingCodes.PERFECT_MATCH ) == null )
                {
                    addStatusCode( ServiceabilityProcessingCodes.NON_EXACT_MATCH );
                }

                removeStatusCode( ServiceabilityProcessingCodes.NO_MATCHES_FOUND );
            }
            else
            {
                addStatusCode( ServiceabilityProcessingCodes.NO_MATCHES_FOUND );
                removeStatusCode( ServiceabilityProcessingCodes.MULTIPLE_ADDRESSES_FOUND );
            }
        }

        return results;
    }

    /**
     * Function that contains the logic for obtaining the cityState records that we will match against to try to determine the
     * correct address information.
     * 
     * @param address
     *            parsed input address information.
     * @param cityBlank
     *            flag to indicate whether or not the city is blank.
     * @param stateBlank
     *            flag to indicate whether or not the state is blank.
     * @param zipBlank
     *            flag to indicate whether or not the zipCode is blank.
     * @param trySoundex
     *            Whether or not to try a Soundex Match.
     * 
     * @return list of matching ZipCodesCityState records.
     */
    private List<ZipCodesCityState> getCityRecords( final AddressBean address, final boolean cityBlank, final boolean stateBlank,
            final boolean zipBlank, final boolean trySoundex )
    {
        List<ZipCodesCityState> cityStates = null;
        List<ZipCodesCityState> cityStatesTmp = null;

        // Now check the city stuff...
        if ( !cityBlank && !stateBlank && !zipBlank )
        {
            cityStates = getCityState( address.getCity(), address.getStateOrProvince(), address.getZip5() );
        }

        // Here, we care if the city and state aren't blank... and really don't care
        // whether the zip is or not...
        if ( !cityBlank && !stateBlank )
        {
            cityStatesTmp = getCityStateNoZip( address.getCity(), address.getStateOrProvince() );
            if ( cityStatesTmp != null )
            {
                if ( cityStates == null )
                {
                    cityStates = cityStatesTmp;
                }
                else
                {
                    cityStates.addAll( cityStatesTmp );
                }
            }
        }

        // We really don't care what the city or state is, just that the zip isn't blank...
        if ( !zipBlank )
        {
            cityStates = getByZipCodeNoCityState( address.getZip5(), false );
            if ( cityStates != null )
            {
                if ( cityBlank )
                {
                    addStatusCode( ServiceabilityProcessingCodes.CITY_DETERMINED_FROM_ZIP );
                }
                if ( stateBlank )
                {
                    addStatusCode( ServiceabilityProcessingCodes.STATE_DETERMINED_FROM_ZIP );
                }
            }
        }
        else
        {
            addStatusCode( ServiceabilityProcessingCodes.ZIP_CODE_MISSING );
        }

        // Here, we care if the city and state aren't blank... and really don't care
        // whether the zip is or not...
        if ( !cityBlank && !stateBlank )
        {
            if ( trySoundex )
            {
                cityStatesTmp = new ArrayList<ZipCodesCityState>();
                // Here we make one last check, via SoundEx...
                updateCitiesListBySoundex( cityStatesTmp, address.getCity(), address.getStateOrProvince() );
                if ( cityStatesTmp.size() > 0 )
                {
                    if ( cityStates == null )
                    {
                        cityStates = cityStatesTmp;
                    }
                    else
                    {
                        cityStates.addAll( cityStatesTmp );
                    }
                }
            }
            setCityStateOnlyCall( true );
        }

        // If we didn't get anything by now...put in the error codes...
        if ( cityStates == null )
        {
            if ( !zipBlank && !cityBlank && !stateBlank )
            {
                addStatusCode( ServiceabilityProcessingCodes.ZIP_NOT_FOUND );
            }
            else if ( !zipBlank && cityBlank && stateBlank )
            {
                removeStatusCode( ServiceabilityProcessingCodes.ZIP_CODE_MISSING );
                addStatusCode( ServiceabilityProcessingCodes.ZIP_NOT_FOUND );
                addStatusCode( ServiceabilityProcessingCodes.CITY_MISSING );
                addStatusCode( ServiceabilityProcessingCodes.STATE_MISSING );
            }
            else if ( zipBlank && !cityBlank && !stateBlank )
            {
                addStatusCode( ServiceabilityProcessingCodes.CITY_COULD_NOT_BE_FOUND_IN_STATE );
                addStatusCode( ServiceabilityProcessingCodes.ZIP_CODE_MISSING );
            }
        }

        return cityStates;
    }

    /**
     * Function that contains the logic for obtaining the street records that we will match against to try to determine the correct
     * address information.
     * 
     * @param rawAddress
     *            unnormalized, but parsed input address information.
     * @param address
     *            parsed and normalized input address information.
     * @param streetNameBlank
     *            flag to indicate whether the street name is blank.
     * @param streetNumberBlank
     *            flag to indicate whether the street number is blank.
     * @param cityStates
     *            List of potential cityStates.
     * @param trySoundex
     *            Whether or not to try a Soundex Match.
     * 
     * @return list of matching ZipPlusFour records.
     */
    private List<ZipPlusFour> getStreetRecords( final AddressBean rawAddress, final AddressBean address,
            final boolean streetNameBlank, final boolean streetNumberBlank, final List<ZipCodesCityState> cityStates,
            final boolean trySoundex )
    {
        List<ZipPlusFour> streets = null;
        List<ZipPlusFour> tempStreets = null;
        String tempStreetName;
        List<ZipPlusFour> workingStreetSet = new ArrayList<ZipPlusFour>();
        String tempStreetNumber;
        String tempLine2;

        // Now check the street stuff...
        if ( !streetNameBlank && ( !streetNumberBlank || ( !streetNameBlank && address.getStreetName().contains( "RR " ) ) ) )
        {
            // In the case that both the street name and number are present....
            streets = getStreetsWithZip( address.getStreetNumber(), address.getStreetName(), address.getLine2UnitNum(), address
                    .getZip5() );
            setFullStreetCall( true );

            if ( streets == null && address.getStreetNumber() != null && address.getStreetNumber().contains( "-" )
                    && StringUtils.isBlank( address.getLine2() ) )
            {
                tempStreetNumber = address.getStreetNumber().substring( 0, address.getStreetNumber().indexOf( '-' ) );
                tempLine2 = "APT " + address.getStreetNumber().substring( address.getStreetNumber().indexOf( '-' ) + 1 );
                streets = getStreetsWithZip( tempStreetNumber, address.getStreetName(), tempLine2, address.getZip5() );
                if ( streets != null )
                {
                    address.setStreetNumber( tempStreetNumber );
                    address.setLine2( tempLine2 );
                }
            }

            if ( streets == null )
            {

                addStatusCode( ServiceabilityProcessingCodes.STREET_NOT_FOUND );

                // Try to find streets based on given state, and one from list of city states...
                tempStreets = getStreetsTwoStateTries( address, address.getStreetName(), cityStates );
                if ( tempStreets != null )
                {
                    workingStreetSet.addAll( tempStreets );
                }

                // If we find a predirectional, we'll turn it into a word, and add it to the front
                // of the street name and try again...
                String optionalStreetName = 
                    generateAltPrefixDirStreetName( address.getPrefixDirectional(), address.getStreetName() );
                tempStreets = null;

                if ( optionalStreetName != null )
                {
                    tempStreets = getStreetsTwoStateTries( address, optionalStreetName, cityStates );
                    if ( tempStreets != null )
                    {
                        workingStreetSet.addAll( tempStreets );
                    }
                }

                setFullStreetCall( false );

                // If we have a raw address, we'll use that to build the tempStreetName...
                if ( rawAddress != null && rawAddress.getStreetType() != null )
                {
                    tempStreetName = address.getStreetName() + " " + rawAddress.getStreetTypeFromAbbr();

                    if ( StringUtils.isNotBlank( address.getStateOrProvince() ) )
                    {
                        tempStreets = getStreetsWithState( address.getStreetNumber(), tempStreetName, address.getLine2UnitNum(),
                                address.getStateOrProvince() );
                    }
                    else if ( StringUtils.isNotBlank( cityStates.get( 0 ).getState() ) )
                    {
                        tempStreets = getStreetsWithState( address.getStreetNumber(), tempStreetName, address.getLine2UnitNum(),
                                cityStates.get( 0 ).getState() );
                    }

                    if ( tempStreets != null )
                    {
                        workingStreetSet.addAll( tempStreets );
                    }
                }

                // Flag for trying different approaches...
                ArrayList<String> zips = new ArrayList<String>();

                // We also go look this up by Soundex...
                // First we need to grab the zips we'll need for the call...
                if ( StringUtils.isNotBlank( address.getZip5() ) )
                {
                    zips.add( address.getZip5() );
                }
                else if ( StringUtils.isNotBlank( cityStates.get( 0 ).getZipCode() ) )
                {
                    for ( int i = 0; i < cityStates.size(); i++ )
                    {
                        if ( StringUtils.isNotBlank( cityStates.get( i ).getZipCode() ) )
                        {
                            zips.add( cityStates.get( i ).getZipCode() );
                        }
                    }
                }

                tempStreets = exploreStreetTypeVariations( rawAddress, address, trySoundex, zips );

                // Now add whatever we've generated up til now...
                if ( tempStreets != null && tempStreets.size() > 0 )
                {
                    workingStreetSet.addAll( tempStreets );
                }

                // If we don't have a return set here, we'll make one.
                if ( streets == null )
                {
                    streets = new ArrayList<ZipPlusFour>();
                }

                streets.addAll( workingStreetSet );

                if ( streets.size() > 0 )
                {
                    removeStatusCode( ServiceabilityProcessingCodes.STREET_NOT_FOUND );
                }
                else
                {
                    streets = null;
                }
            }
        }
        else
        {
            // Set appropriate status messages for missing address components
            if ( streetNameBlank )
            {
                addStatusCode( ServiceabilityProcessingCodes.STREET_NAME_MISSING );
            }
            if ( streetNumberBlank )
            {
                addStatusCode( ServiceabilityProcessingCodes.STREET_NUMBER_MISSING );
            }
        }
        return streets;
    }

    /**
     * Utility function that explores different variations of street type.
     * 
     * @param rawAddress
     *            input raw address.
     * @param address
     *            address bean of parsed address.
     * @param trySoundex
     *            flag to indicate whether or not to try soundex.
     * @param zips
     *            zips to use in search.
     * @return list of Zip Plus 4 that might fit address.
     */
    private List<ZipPlusFour> exploreStreetTypeVariations( final AddressBean rawAddress, final AddressBean address,
            final boolean trySoundex, final ArrayList<String> zips )
    {
        List<ZipPlusFour> tempStreets = new ArrayList<ZipPlusFour>();
        List<ZipPlusFour> result = new ArrayList<ZipPlusFour>();
        String tempStreetName;

        if ( trySoundex )
        {
            updateStreetListBySoundex( tempStreets, address.getPrefixDirectional(), address.getStreetNumber(), address
                    .getStreetName(), zips );

            result.addAll( tempStreets );

            // Reset tempStreets...
            tempStreets.clear();

            // Let's try two variations...

            // Perhaps they gave us a street that has a "potential" street prefix,
            // but is actually MISSING the prefix... i.e. "Holly Hill" when it should be "Holly Hill Dr."
            // We'll use the raw address for one approach, and also try the SoundEx ...

            // If we have a raw address, we'll use that to build the tempStreetName...
            if ( rawAddress != null && rawAddress.getStreetType() != null )
            {
                tempStreetName = address.getStreetName() + " " + rawAddress.getStreetTypeFromAbbr();
            }
            else
            {
                tempStreetName = address.getStreetName() + " " + address.getStreetTypeFromAbbr();
            }

            if ( trySoundex )
            {
                // SoundEx approach which is used in either case...
                updateStreetListBySoundex( tempStreets, address.getStreetNumber(), address.getPrefixDirectional(), tempStreetName,
                        zips );

                // Add anything that came back..
                result.addAll( tempStreets );

                // Reset tempStreets...
                tempStreets.clear();
            }

            // // Or the opposite, perhaps they gave us an extra street prefix, when in fact it shouldn't have
            // // one...i.e. "Valley Glen Drive" rather than "Valley Glen"
            // // We'll use the raw address for one approach, and also try the SoundEx ...
            //
            // // If we have a raw address, we'll use that to build the tempStreetName...
            // if ( rawAddress != null && rawAddress.getStreetType() != null )
            // {
            // tempStreetName = address.getStreetName() + " " + rawAddress.getStreetTypeFromAbbr();
            // }
            // else
            // {
            // tempStreetName = address.getStreetName() + " " + address.getStreetTypeFromAbbr();
            // }
            //
            // // Reset tempStreets...
            // tempStreets = new ArrayList<ZipPlusFour>();
            //            
            // if ( trySoundex )
            // {
            // // SoundEx approach which is used in either case...
            // updateStreetListBySoundex( tempStreets, address.getStreetNumber(), address.getPrefixDirectional(),
            // tempStreetName, zips );
            // // Add anything that came back..
            // result.addAll( tempStreets );
            //                
            // // Reset tempStreets...
            // tempStreets.clear();
            // }

        }
        return result;
    }

    /**
     * Utility function to take an address and build an alternate street name where the prefix directional is actually part of the
     * street name...
     * 
     * @param prefixDirectional
     *            Prefix Directional data to work from.
     * 
     * @param streetName
     *            Street name data to work from.
     * @return optional name if possible to generate.
     */
    private String generateAltPrefixDirStreetName( final String prefixDirectional, final String streetName )
    {
        if ( prefixDirectional != null )
        {
            // We'll check to see if we need to expand the value, from it's abbreviation, if not we'll just
            // take the value in the address.
            String prefixDirName = AddressData.getInverseDirectionalMap().get( prefixDirectional );
            if ( prefixDirName == null )
            {
                prefixDirName = prefixDirectional;
            }
            return prefixDirName + streetName;
        }
        return null;
    }

    /**
     * Small helper function to get streets based on two different ways to get the state.
     * 
     * @param address
     *            Input address info.
     * @param streetName
     *            StreetName to use to check.
     * @param cityStates
     *            List of City States
     * 
     * @return returns the list of streets.
     */
    private List<ZipPlusFour> getStreetsTwoStateTries( final AddressBean address, final String streetName,
            final List<ZipCodesCityState> cityStates )
    {
        List<ZipPlusFour> streets = null;

        if ( StringUtils.isNotBlank( address.getStateOrProvince() ) )
        {
            streets = getStreetsWithState( address.getStreetNumber(), streetName, address.getLine2UnitNum(), address
                    .getStateOrProvince() );
        }
        else if ( StringUtils.isNotBlank( cityStates.get( 0 ).getState() ) )
        {
            streets = getStreetsWithState( address.getStreetNumber(), streetName, address.getLine2UnitNum(), cityStates.get( 0 )
                    .getState() );
        }
        return streets;
    }

    // Rules for CalculateBestMatchOrSuggestion.
    //
    // If this function doesn't find an exact match, it utilizes the following Match Rules.
    // 
    // In all the rules, we're going to assume the plus 4 is missing in the incoming address. If
    // an address had that much info, we're going to assume it SHOULD have matched exactly earlier.
    // Also, we only check these for one match... if we find two, then we still only have candidate suggestions,
    // not an assumed match.
    //   
    // 0) Assumptions to remember, the number and name are already validated, based on the queries that feed this function.
    // Either direct match, or matched by soundEx to a single result. Additionally, the city/state/zip info has been matched
    // either by direct match, or by zip (or potentially by soundEx... )
    //
    // 1) Regardless of whether the zip (the main portion) is valid, and if the prefix directional, street type and line 2 info
    // are valid then we'll assume it could be a match.
    // 2) If we have a valid zip (the main portion) and the prefix directional, and line 2 info is valid
    // then we'll assume it could be a match.
    // 3) If we have a valid zip (the main portion) and a street type and line 2 info that is valid
    // then we'll assume it could be a match.
    // 4) If we have a valid prefix directional, and line 2 information, then we're going to let it thru, but only if
    // the secondary info actually had something in it....(otherwise, it's a bit too loose of a match.... we're assuming
    // that apartment dwellers might know the apt, street name, but not street type or zip.
    //
    // Assumptions in this function.
    // 1) That we are given some street info and city/state info. May not be complete, but both aren't empty.
    // 2) The address bean contains "parsed" all upper case address values.

    /**
     * Function that returns either a single best match, or a list of suggestions.
     * 
     * @param address
     *            to be set.
     * @param cityStates
     *            Cities and States to use for matching.
     * @param streets
     *            Streets to use for matching.
     * @return best address or list of suggestions.
     */
    public List<AddressBean> calculateBestMatchOrSuggestions( final AddressBean address, final List<ZipCodesCityState> cityStates,
            final List<ZipPlusFour> streets )
    {
        AddressBean correctedAddress = null;
        AddressBean tempAddress;
        Map<Integer, List<AddressBean>> candidateAddresses = new HashMap<Integer, List<AddressBean>>();
        List<AddressBean> tempScoreList = null;
        Set<ZipCodesCityState> potentialCityStateMatches = new HashSet<ZipCodesCityState>();
        Set<ZipPlusFour> potentialStreetMatches = new HashSet<ZipPlusFour>();
        boolean zipValid = false;
        boolean plus4Valid = false;
        boolean preDirValid = false;
        boolean stTypeValid = false;
        boolean secLowNumValid = false;

        for ( ZipCodesCityState cityState : cityStates )
        {
            for ( ZipPlusFour street : streets )
            {
                if ( ( cityState.getCityStateKey().equals( street.getPreferredLastLineCityStateKey() )
                        || ( cityStates.size() == 1 && cityState.getPreferredLastLineKey().equals(
                                street.getPreferredLastLineCityStateKey() ) )
                        || ( cityState.getCityAliasName().equals( address.getCity() ) && cityState.getPreferredLastLineKey()
                                .equals( street.getPreferredLastLineCityStateKey() ) ) || ( cityState.isSoundexMatch() && cityState
                        .getPreferredLastLineKey().equals( street.getPreferredLastLineCityStateKey() ) ) )
                        && ( cityState.getZipCode().equals( street.getZipCode() ) ) )
                {
                    potentialCityStateMatches.add( cityState );
                    potentialStreetMatches.add( street );

                    zipValid = StringUtils.equalOrBothEmptyNull( address.getZip5(), street.getZipCode() );
                    plus4Valid = StringUtils.equalOrBothEmptyNull( address.getPlus4(), street.getPlus4Low() );
                    preDirValid = StringUtils.equalOrBothEmptyNull( address.getPrefixDirectional(), street.getStPreDirAbbr() );
                    stTypeValid = StringUtils.equalOrBothEmptyNull( address.getStreetType(), street.getStSuffixAbbr() );
                    secLowNumValid = isSecondaryNumInRange( address.getSecondoryAddressNumber(), street );

                    // An exact match...
                    if ( zipValid && plus4Valid && preDirValid && stTypeValid && secLowNumValid )
                    {
                        // Cast the address into an AddressBean
                        correctedAddress = new AddressBean( address );
                        if ( street.getStPostDirAbbr() != null )
                        {
                            // We'd return false if the both are different. So if the streetInfo isn't null (from above)
                            // and it doesn't match, then we want to store the streetInfo postfix directional data in the
                            // address... cause the address is either null, or doesn't have the most accurate info... assuming
                            // that the streetInfo has the more correct info...
                            if ( !StringUtils.equalOrBothEmptyNull( address.getPostfixDirectional(), street.getStPostDirAbbr() ) )
                            {
                                correctedAddress.setPostfixDirectional( street.getStPostDirAbbr() );
                            }
                        }

                        // Copy in the City and State information, in case they were left blank in the input
                        if ( correctedAddress.getCityOrAlias() == null )
                        {
                            correctedAddress.setCity( cityState.getCity() );
                            correctedAddress.setCityAlias( cityState.getCityAliasName() );
                        }
                        if ( correctedAddress.getStateOrProvince() == null )
                        {
                            correctedAddress.setStateOrProvince( cityState.getState() );
                        }

                        // Mark it as a soundexMatch if necessary...
                        if ( cityState.isSoundexMatch() )
                        {
                            correctedAddress.setCitySoundexMatch( true );
                        }

                        if ( street.isSoundexMatch() )
                        {
                            correctedAddress.setStreetSoundexMatch( true );
                        }
                    }
                }
            }
        }
        if ( correctedAddress != null && !( correctedAddress.isCitySoundexMatch() || correctedAddress.isStreetSoundexMatch() ) )
        {
            tempScoreList = (List<AddressBean>) candidateAddresses.get( PERFECT_MATCH_SCORE );
            if ( tempScoreList == null )
            {
                tempScoreList = new ArrayList<AddressBean>();
            }
            tempScoreList.add( correctedAddress );
            candidateAddresses.put( PERFECT_MATCH_SCORE, tempScoreList );
            clearStatusCodes();
            addStatusCode( ServiceabilityProcessingCodes.PERFECT_MATCH );
        }
        else
        {
            if ( potentialCityStateMatches.size() > 0 && potentialStreetMatches.size() > 0 )
            {
                // Here we take the potential matches and build addressBeans to return...
                for ( ZipCodesCityState cityState : potentialCityStateMatches )
                {
                    for ( ZipPlusFour streetInfo : potentialStreetMatches )
                    {
                        if ( !streetInfo.getZipCode().equals( cityState.getZipCode() ) )
                        {
                            continue;
                        }

                        int matchQuality = checkRules( address, cityState, streetInfo );
                        if ( matchQuality > 0 )
                        {
                            tempAddress = createCandidateAddress( address, streetInfo, cityState );

                            // Now add it to the potential address list
                            tempScoreList = (List<AddressBean>) candidateAddresses.get( matchQuality );
                            if ( tempScoreList == null )
                            {
                                tempScoreList = new ArrayList<AddressBean>();
                            }

                            // Now, if the list doesn't already contain this address,
                            // or if the input address is a zip-only address, then
                            // make sure you have the right primary city...
                            if ( !tempScoreList.contains( tempAddress )
                                    && ( !address.isZipOnly() || ( address.isZipOnly() && ( StringUtils.equalOrBothEmptyNull(
                                            tempAddress.getCity(), tempAddress.getCityAlias() ) || ( potentialCityStateMatches
                                            .size() == 1 ) ) ) ) )
                            {
                                tempScoreList.add( tempAddress );
                            }
                            candidateAddresses.put( matchQuality, tempScoreList );
                        }
                    }
                }
            }
        }
        return getTopCandidateAddresses( address, candidateAddresses, MAX_RETURN_ADDRESSES );
    }

    /**
     * This method assigns a "match quality" value to the given parameters.
     * 
     * @param address
     *            the address that we're attempting to verify/validate
     * @param cityState
     *            the CityState info for this match attempt
     * @param streetInfo
     *            the StreetInfo info for this match attempt.
     * @return int representing the scalar quality of the match
     */
    private int checkRules( final AddressBean address, final ZipCodesCityState cityState, final ZipPlusFour streetInfo )
    {
        boolean zipEqual = false;
        boolean zipValid = false;
        boolean plus4Valid = false;
        boolean preDirValid = false;
        boolean postDirValid = false;
        boolean stTypeValid = false;
        boolean secLowNumValid = false;
        boolean cityStateZipMatch = false;
        boolean cityMatch = false;
        boolean cityStringMatch = false;
        boolean cityStateKeyMatch = false;
        boolean streetNameExact = false;
        boolean streetNameStartsWith = false;
        boolean match = false;

        int value = 0;
        // We're re-doing this for the hopefully small set of potential matches...
        zipEqual = StringUtils.equalOrBothEmptyNull( address.getZip5(), streetInfo.getZipCode() );
        zipValid = zipEqual || ( address.getZip5() == null || address.getZip5().trim().length() == 0 );

        plus4Valid = StringUtils.equalOrBothEmptyNull( address.getPlus4(), streetInfo.getPlus4Low() );

        preDirValid = StringUtils.equalOrBothEmptyNull( address.getPrefixDirectional(), streetInfo.getStPreDirAbbr() );
        postDirValid = StringUtils.equalOrBothEmptyNull( address.getPostfixDirectional(), streetInfo.getStPostDirAbbr() );
        stTypeValid = StringUtils.equalOrBothEmptyNull( address.getStreetType(), streetInfo.getStSuffixAbbr() );
        secLowNumValid = isSecondaryNumInRange( address.getSecondoryAddressNumber(), streetInfo );
        cityStateZipMatch = StringUtils.equalOrBothEmptyNull( cityState.getZipCode(), streetInfo.getZipCode() );

        cityStringMatch = StringUtils.equalOrBothEmptyNull( cityState.getCityAliasName(), address.getCity() );
        cityStateKeyMatch = StringUtils.equalOrBothEmptyNull( cityState.getCityStateKey(), streetInfo
                .getPreferredLastLineCityStateKey() );

        // If we're not given a city name, then we'll use the key to indicate city match...otherwise we use the string.
        if ( address.getCity() == null || address.getCity().trim().length() == 0 )
        {
            cityMatch = cityStateKeyMatch;
        }
        else
        {
            cityMatch = cityStringMatch;
        }

        streetNameExact = StringUtils.equalOrBothEmptyNull( address.getStreetName(), streetInfo.getStName() );
        streetNameStartsWith = address.getStreetName().startsWith( streetInfo.getStName() )
                || streetInfo.getStName().startsWith( address.getStreetName() );

        // Match Rules --- See Above.
        if ( zipValid && plus4Valid && preDirValid && stTypeValid && secLowNumValid && postDirValid && cityMatch )
        {
            value = MATCH_SCORE_14;
            match = true;
        }
        if ( !match && zipValid && !plus4Valid && preDirValid && stTypeValid && secLowNumValid && postDirValid && cityMatch )
        {
            value = MATCH_SCORE_13;
            match = true;
        }
        if ( !match && zipValid && plus4Valid && !preDirValid && stTypeValid && secLowNumValid && postDirValid && cityMatch )
        {
            value = MATCH_SCORE_12;
            match = true;
        }
        if ( !match && zipValid && plus4Valid && preDirValid && stTypeValid && !secLowNumValid && postDirValid && cityMatch )
        {
            value = MATCH_SCORE_11;
            match = true;
        }
        if ( !match && zipValid && !plus4Valid && preDirValid && stTypeValid && secLowNumValid && cityMatch )
        {
            value = MATCH_SCORE_10;
            match = true;
        }
        if ( !match && zipValid && !plus4Valid && preDirValid && stTypeValid && secLowNumValid )
        {
            value = MATCH_SCORE_9;
            match = true;
        }
        if ( !match && !zipValid && cityStateZipMatch && !plus4Valid && preDirValid && stTypeValid && secLowNumValid )
        {
            value = MATCH_SCORE_8;
            match = true;
        }
        if ( !match && !plus4Valid && preDirValid && stTypeValid && secLowNumValid )
        {
            value = MATCH_SCORE_7;
            match = true;
        }
        if ( !match && zipValid && !plus4Valid && preDirValid && !stTypeValid && secLowNumValid )
        {
            value = MATCH_SCORE_6;
            match = true;
        }
        if ( !match && zipValid && !plus4Valid && !preDirValid && stTypeValid && secLowNumValid )
        {
            value = MATCH_SCORE_5;
            match = true;
        }
        if ( !match && !plus4Valid && preDirValid && !stTypeValid && secLowNumValid )
        {
            value = MATCH_SCORE_4;
            match = true;
        }
        if ( !match && ( zipValid && !plus4Valid && preDirValid && postDirValid && stTypeValid ) )
        {
            value = MATCH_SCORE_3;
            match = true;
        }
        if ( !match && zipValid && cityStateZipMatch && streetNameStartsWith )
        {
            value = MATCH_SCORE_2;
            match = true;
        }
        if ( !match && ( zipValid && !plus4Valid && stTypeValid ) )
        {
            value = MATCH_SCORE_2;
            match = true;
        }
        if ( !match
                && ( !zipValid && !plus4Valid && preDirValid && !stTypeValid && ( secLowNumValid && address
                        .getSecondoryAddressNumber() != null ) ) )
        {
            value = MATCH_SCORE_2;
            match = true;
        }

        // If they make it here at all, they'll fall into the PEE WEE League rule, you get a point for
        // showing up...
        if ( !match )
        {
            value = PEE_WEE_SCORE;
            match = true;
        }

        if ( streetNameExact )
        {
            value = value + MATCH_STREET_BONUS;
        }

        return value;
    }

    /**
     * This method employs the following algorithm: Take the highest single score you can find. If the highest score has multiple
     * entry, then you must return a list of length 'maxReturns'. This list will be comprised of the multiple scores and however
     * many more are needed to reach 'maxReturns'
     * 
     * @param address
     *            the passed in address info.
     * @param addresses
     *            the Map of Lists that contain the candidate addresses to be processed.
     * @param maxReturns
     *            the maximum number of candidate addresses that are to be returned.
     * @return a list of AddressBeans, still sorted by match score descending
     */
    public List<AddressBean> getTopCandidateAddresses( final AddressBean address, final Map<Integer, List<AddressBean>> addresses,
            final int maxReturns )
    {
        List<AddressBean> byScoreList = new ArrayList<AddressBean>(); // The return value list

        if ( addresses == null || addresses.size() == 0 ) // If we don't have any addresses, return..
        {
            return byScoreList;
        }

        // A boolean flag to show we're no longer looking for the highest score, but the group of 'higher' scores
        boolean getAll = false;
        boolean done = false;

        for ( int i = PERFECT_MATCH_SCORE; i >= 0 && !done; i-- ) // Look for the single highest score
        {
            List<AddressBean> matchScores = addresses.get( i );

            // matchScores SORT here. DOG DOG -- TO fix inconsistent suggestion order.

            // If we've dropped into "getAll" mode, then just go ahead and add all of the
            // entries for this score into the return (as long as that doesn't exceed the maxReturns value
            if ( matchScores != null && getAll )
            {
                for ( AddressBean candidateAddress : matchScores )
                {
                    if ( candidateAddress != null )
                    {
                        if ( !addSuggestions( byScoreList, maxReturns, candidateAddress ) ) // If we can't add any more, done...
                        {
                            done = true;
                            break;
                        }
                    }
                }
                continue;
            }

            if ( !getAll && matchScores != null && matchScores.size() == 1 ) // If we've found a score with a single entry
            {
                // If the best "one" is a phonetically matched address, then we're into getAll mode...
                // and attempt to return suggestions.
                if ( matchScores.get( 0 ).isCitySoundexMatch() )
                {
                    addStatusCode( ServiceabilityProcessingCodes.CITY_NAME_MATCHED_PHONETICALLY );
                    getAll = true;
                }
                if ( matchScores.get( 0 ).isStreetSoundexMatch() )
                {
                    addStatusCode( ServiceabilityProcessingCodes.STREET_NAME_MATCHED_PHONETICALLY );
                    getAll = true;
                }

                // To remove a potentially spurious status code... we MAY have actually used the ZIP query to find
                // some city names, however, we'll say we didn't if the city or city alias name of the match equals
                // the city or city alias name of the passed in address.
                if ( matchScores.get( 0 ).getCity().equals( address.getCity() )
                        || matchScores.get( 0 ).getCity().equals( address.getCityAlias() )
                        || matchScores.get( 0 ).getCityAlias().equals( address.getCityAlias() )
                        || matchScores.get( 0 ).getCityAlias().equals( address.getCity() ) )
                {
                    removeStatusCode( ServiceabilityProcessingCodes.CITY_DETERMINED_FROM_ZIP );
                }

                if ( getAll )
                {
                    byScoreList.add( matchScores.get( 0 ) );
                    break;
                }

                // Check to see if the input and match line up apt range wise,
                // or if that's really N/A, or if we can assume what we were given is correct.
                int rangeCheck = checkAptRange( matchScores.get( 0 ), address );

                // If the exact match falls into the N/A case, go into candidate mode, rather than give an exact answer.
                if ( rangeCheck == -1 )
                {
                    getAll = true;
                    byScoreList.add( matchScores.get( 0 ) );
                    addStatusCode( ServiceabilityProcessingCodes.UNIT_MISSING ); // Add status for Unit information
                }
                else // In this case, they weren't in the N/A case, but the apt may or may not have been within the range...
                {
                    if ( i != PERFECT_MATCH_SCORE )
                    {
                        addStatusCode( ServiceabilityProcessingCodes.NON_EXACT_MATCH );

                        // If they didn't fall within the range check...we really didn't match...
                        if ( rangeCheck == 0 )
                        {
                            addStatusCode( ServiceabilityProcessingCodes.ADDRESS_NOT_FOUND );
                            addStatusCode( ServiceabilityProcessingCodes.UNIT_NOT_FOUND );
                            removeStatusCode( ServiceabilityProcessingCodes.NON_EXACT_MATCH );
                            getAll = true;
                            byScoreList.add( matchScores.get( 0 ) );
                        }
                        else if ( rangeCheck == 1 )
                        {
                            // Best Single Non-Exact Match case...
                            matchScores.get( 0 ).setLine2( address.getLine2() );
                            byScoreList.add( matchScores.get( 0 ) );
                            break;
                        }
                        else if ( rangeCheck == 2 )
                        {
                            addStatusCode( ServiceabilityProcessingCodes.UNIT_INFORMATION_ASSUMED_CORRECT );
                            matchScores.get( 0 ).setLine2( address.getLine2() );
                            byScoreList.add( matchScores.get( 0 ) );
                            break;
                        }
                    }
                    else
                    {
                        addStatusCode( ServiceabilityProcessingCodes.PERFECT_MATCH );
                        byScoreList.add( matchScores.get( 0 ) );
                        done = true;
                        break;
                    }
                }
            }

            // If we've found an score with multiple entries, get all of them, then go into getAll mode...
            if ( !getAll && matchScores != null && matchScores.size() > 1 )
            {
                getAll = true; // Set the free-for-all flag to true.

                for ( AddressBean candidateAddress : matchScores ) // Begin adding...
                {
                    if ( candidateAddress != null )
                    {
                        // If we can't add any more, we're done...
                        if ( !addSuggestions( byScoreList, maxReturns, candidateAddress ) )
                        {
                            done = true;
                            break;
                        }
                    }
                }
            }
        }

        // Now to check to see if for all the suggestions, the given address ( if it has and apt range ),
        // whether or not it falls within the range of at least one suggestion...
        checkForNoSuggestionWithMatchingAptRange( byScoreList, address );

        if ( byScoreList.size() > 0 )
        {
            addStatusCode( ServiceabilityProcessingCodes.MULTIPLE_ADDRESSES_FOUND );
        }

        return byScoreList; // Return the candidate address(es)
    }

    /**
     * Utility method to check all the suggestions to see if any of them had an apt range that covered the apt info of the input
     * address.
     * 
     * @param suggestions
     *            list of suggestions to check.
     * @param initialAddress
     *            input address.
     */
    private void checkForNoSuggestionWithMatchingAptRange( final List<AddressBean> suggestions, final AddressBean initialAddress )
    {
        boolean flag = false;
        int checkValue;

        // Don't bother doing anything if we don't have any suggestions...
        if ( suggestions == null || suggestions.size() == 0 )
        {
            return;
        }

        for ( AddressBean check : suggestions )
        {
            checkValue = checkAptRange( check, initialAddress );
            if ( checkValue == 1 || checkValue == 2 )
            {
                flag = true;
                break;
            }
        }

        if ( !flag )
        {
            // If no suggestion had the apt range...
            addStatusCode( ServiceabilityProcessingCodes.UNIT_NOT_FOUND );
        }
    }

    /**
     * Utility Method to add an address to the suggestion list.
     * 
     * @param suggestions
     *            List of suggestions.
     * @param maxSuggestions
     *            maximum size of the suggestion list.
     * @param addressToAdd
     *            a suggestion to add
     * @return true if added, false if list greater or equal to maxSuggestions size.
     */
    private boolean addSuggestions( final List<AddressBean> suggestions, final int maxSuggestions, final AddressBean addressToAdd )
    {
        if ( suggestions.size() < maxSuggestions )
        {
            // Add the suggestion...
            suggestions.add( addressToAdd );

            // And check to see if we should add any status codes...
            if ( addressToAdd.isCitySoundexMatch() )
            {
                addStatusCode( ServiceabilityProcessingCodes.CITY_NAME_MATCHED_PHONETICALLY );
            }
            if ( addressToAdd.isStreetSoundexMatch() )
            {
                addStatusCode( ServiceabilityProcessingCodes.STREET_NAME_MATCHED_PHONETICALLY );
            }

            return true;
        }

        return false;
    }

    /**
     * Utility Method to compare an Address bean with an Apt Range in the 2nd Number to a given Address.
     * 
     * @param match
     *            The Matched address bean with the range.
     * @param address
     *            The Address to compare against.
     * @return -1 if NA, 0 if not in range, 1 if in range, 2 if no line2 in match, but one in address which we'll asssume to be
     *         correct.
     */
    private int checkAptRange( final AddressBean match, final AddressBean address )
    {
        String val1;
        String val2;

        // Check to see whether both agree on the Line2 (both have something, are empty or both null),
        // or if the address given has a line2, but the match doesn't.
        // In this last case, we're going to assume the line 2 info passed is correct,
        // since our service providers in some cases have apartment information
        // that doesn't agree with the post office.

        boolean line2Valid = StringUtils.bothEmptyNullOrPresent( address.getLine2(), match.getLine2() )
                || StringUtils.isBlank( match.getLine2() );

        // If they don't line up, it means we are in the N/A case ...
        // which is that the Address didn't have an apt, but the match did....
        if ( !line2Valid )
        {
            return -1;
        }

        // We don't have to check here if the address is null, because the above check would have
        // evaluated to false in that case...regardless of what the match is...
        if ( match.getSecondoryAddressNumber() != null )
        {
            // Normally, we get a range from the database for apartment numbers... but sometimes
            // (rather rarely) we can get just one value...i.e. it only has 1 apt... we also have
            // to be careful in case the range is of two numbers that HAVE DASHES as well!
            // The pattern has a space for those, because that's the "middle" dash...by convention...
            if ( match.getSecondoryAddressNumber().contains( " - " ) )
            {
                val1 = match.getSecondoryAddressNumber().split( " - " )[0].trim();
                val2 = match.getSecondoryAddressNumber().split( " - " )[1].trim();
            }
            else
            {
                val1 = match.getSecondoryAddressNumber().trim();
                val2 = match.getSecondoryAddressNumber().trim();
            }

            // Check to make sure that the secondary address number is within range...
            if ( isNumberInRange( address.getSecondoryAddressNumber(), val1, val2 ) )
            {
                return 1;
            }

            // Not within range..
            return 0;
        }

        if ( address.getSecondoryAddressNumber() == null )
        {
            // If we get here, the match.getSecondaryAddressNumber was null... and if the address
            // is null, then "they're in range".
            return 1;
        }
        // If the match has an empty or null line2, but the address that's our
        // "assume correct" case.
        return 2;
    }
    
    /**
     * A special-purpose method to look at grid-style addresses (Wisconsin in particular, although this
     * method is not state-aware) and to make sure that the addresses's odd/even-ness of the streets
     * match the odd/even-ness of the ZipPlusFours.
     * @param streets list of ZipPlusFours that could be potential street matches.
     * @param addr the Address that was supplied.
     * @return list of ZipPlusFours that passed the tests contained in this method.
     */
    private List<ZipPlusFour> evaluateWisconsinAddresses( final List<ZipPlusFour> streets, final AddressBean addr )
    {   	
    	// Loop over the passed streets and discard ones based on their odd/even status, as garnered from
    	// the last digit of their
    	ArrayList<ZipPlusFour> results = new ArrayList<ZipPlusFour>();
    	boolean addrIsEven = false;
    	boolean streetNumberMissing = (addr.getStreetNumber() == null);
    	if(!streetNumberMissing) {
    		addrIsEven = streetNumberIsEven( addr );
    	}
    	for ( ZipPlusFour zpf : streets )
    	{
    		if(streetNumberMissing) {
    			results.add( zpf );
    		}
    		// If the given address is 'even' and the ZipPlusFour we're looking is even (or 'both')
    		// then propagate it to the results/
    		else if ( addrIsEven )
    		{
    			if ( zpf.getAddressPrimaryOddEven().equals( "E" ) || zpf.getAddressPrimaryOddEven().equals( "B" ) )
    			{
    				results.add( zpf );
    			}
    		}
    		// If the given address is 'odd' and the ZipPlusFour we're looking is odd (or 'both')
    		// then propagate it to the results/
    		else
    		{
    			if ( zpf.getAddressPrimaryOddEven().equals( "O" ) || zpf.getAddressPrimaryOddEven().equals( "B" ) )
    			{
    				results.add( zpf );
    			}
    		}
    		
    	}
    	return results;
    }
    
    /**
     * Method that determines the even/odd-ness of the street number of the given AddressBean
     * There's a lot more to this routine than there should be, but we had to accommodate addresses
     *  of the form '128-B' and '128 1/2' or any combination thereof.
     * @param inAddr the AddressBean that is to be processed.
     * @return a boolean stating if the address is Even.
     */
    private boolean streetNumberIsEven( final AddressBean inAddr )
    {
		int delimIndex = -1;
		int spaceIndex = -1;
		boolean hasAlpha = false;
		boolean hasFraction = false;
		
		// The starting Street Number value
		String streetNbr = inAddr.getStreetNumber();
		// The 'usable' part of the street address
		String usableStreetNumber = new String();
		// The numeric value of the street number that we're trying to get
		int lastStrNumberDigit = -1;
		
		// Find the location of either '-' or ' ' characters 
		delimIndex = streetNbr.lastIndexOf( "-" );
		if ( delimIndex == -1 )
		{	
			spaceIndex = streetNbr.lastIndexOf( " " );
		}
		
		// Now look at the tokens that we've found, giving priority to the ones that we
		// found with the '-' as a delimiter
		if ( delimIndex != -1 )
		{
			// Look at the second token, if it contains a slash or an alpha character, then 
			// we'll ditch it (since we can't use it to calculate odd/even
			if ( streetNbr.substring( delimIndex + 1 ).trim().matches( "\\D*" ) )
			{
				hasAlpha = true;
			}
			if ( streetNbr.substring( delimIndex + 1 ).trim().matches( ".*/.*" ) )
			{
				hasFraction = true;
			}
		}
		// If we didn't have any '-' chars, maybe we have a ' ' delimiting the tokens in this street
		// number. Maybe we don't have ' ' characters, if not, skip this and get to the odd/even-ness already.
		if ( delimIndex == -1 && spaceIndex != -1 )
		{
			// Look at the second token, if it contains a slash or an alpha character, then 
			// we'll ditch it (since we can't use it to calculate odd/even
			if ( streetNbr.substring( spaceIndex ).trim().matches( ".*\\w*.*" ) )
			{
				hasAlpha = true;
			}
			if ( streetNbr.substring( spaceIndex ).trim().matches( ".*/.*" ) )
			{
				hasFraction = true;
			}
		}
		
		// Now, find which part of the street number we should process
		// First, figure out if (assuming there is one) the second part of the street number is usable 
		if ( delimIndex != -1 )
		{
			if ( hasAlpha || hasFraction )
			{
				// The only usable part is the first part, so get that
				usableStreetNumber = streetNbr.substring( 0, delimIndex ).trim();
			} 
			else
			{
				// Get the second part as the usable part
				usableStreetNumber = streetNbr.substring( delimIndex ).trim();
			}	
		} 
		// This is the case for street numbers without '-' or ' ' in them (which is the vast majority)
		else
		{
			usableStreetNumber = streetNbr.trim();
		}
		
		// Now, we have enough information to figure out how to determine the odd/even-ness
		// of the street number.
		
		// First, if neither a '-' or a ' ' was detected, then we can proceed as if this was a normal 
		// single part street number.
		if ( delimIndex == -1 )
		{
			// Remove any non-number characters that might be in the street number
			String temp = usableStreetNumber.replaceAll( "\\D*", "" );
			lastStrNumberDigit = Integer.parseInt( temp.substring( temp.length() - 1 ) );
		}
		// If we do have a 2-part street number, we have to figure out if we need to ditch the second part
		// just try to parse the first. This would mean that the second part is more "unit" information
		// than street address
		else
		{
			if ( hasAlpha || hasFraction )
			{
				// Remove any non-number characters that might be in the street number
				String temp = usableStreetNumber.replaceAll( "\\D*", "" );
				lastStrNumberDigit = Integer.parseInt( temp.substring( temp.length() - 1 ) );
			}
			else
			{
				// Remove any non-number characters that might be in the street number
				String temp = usableStreetNumber.replaceAll( "\\D*", "" );
				lastStrNumberDigit = Integer.parseInt( temp.substring( temp.length() - 1 ) );
			}
		}
    	
    	return ( lastStrNumberDigit % 2 == 0 );

    }

    /**
     * Build a candidate address, given the input Address, and potential street and city info.
     * 
     * @param address
     *            input address.
     * @param streetInfo
     *            potential street info.
     * @param cityState
     *            potential city info.
     * @return new addressBean with info populated.
     */
    private AddressBean createCandidateAddress( final AddressBean address, final ZipPlusFour streetInfo,
            final ZipCodesCityState cityState )
    {
        // Build the AddressBean
        AddressBean tempAddress = new AddressBean( streetInfo, cityState );
        tempAddress.setStreetNumber( address.getStreetNumber() );
        if ( !StringUtils.isBlank( streetInfo.getAddressSecondaryLowNumber() )
                && !StringUtils.isBlank( streetInfo.getAddressSecondaryHighNumber() ) )
        {
            tempAddress.setLine2( streetInfo.getAddressSecondaryAbbr() + " "
                    + unpadAddressNum( streetInfo.getAddressSecondaryLowNumber() ) );
            if ( !streetInfo.getAddressSecondaryLowNumber().equals( streetInfo.getAddressSecondaryHighNumber() ) )
            {
                // NOTE : It is IMPORTANT that this dash have spaces on either side, it allows us to distinguish between a range
                // and an apt number that has a dash IN IT...
                tempAddress
                        .setLine2( tempAddress.getLine2() + " - " + unpadAddressNum( streetInfo.getAddressSecondaryHighNumber() ) );
            }
        }

        // Mark it as a soundexMatch if necessary...
        if ( cityState.isSoundexMatch() )
        {
            tempAddress.setCitySoundexMatch( true );
        }

        if ( streetInfo.isSoundexMatch() )
        {
            tempAddress.setStreetSoundexMatch( true );
        }

        return tempAddress;
    }

    private static final Pattern SPECIAL_DIRECTION_ABBR = Pattern.compile( "(?:\\d*\\D*\\d*)\\b("
            + ")(?:\\D*\\d*)" );

    private static final Pattern SPECIAL_STREET_ABBR = Pattern.compile( "(?:\\d*\\D*\\d*)\\b(" + ")(?:\\D*\\d*)" );

    /**
     * Utility function to check if a street name has any potential special abbreviations.
     * 
     * @param streetName
     *            street name to check.
     * @return true of has potential special abbreviations.
     */
    private boolean hasPotentialSpecialAbbreviation( final String streetName )
    {
        Matcher m1 = SPECIAL_DIRECTION_ABBR.matcher( streetName );
        Matcher m2 = SPECIAL_STREET_ABBR.matcher( streetName );

        if ( m1.find() || m2.find() || streetName.indexOf( "MT " ) != -1 )
        {
            return true;
        }

        return false;
    }

    /**
     * Utility function to fix special abbreviations.
     * 
     * @param streetName
     *            street name to check.
     * @return fixed street name.
     */
    private String fixSpecialAbbreviations( final String streetName )
    {
        String resultStreetName = null;
        if ( !hasPotentialSpecialAbbreviation( streetName ) )
        {
            return resultStreetName;
        }
        else
        {
            resultStreetName = streetName;
            Matcher m1 = SPECIAL_DIRECTION_ABBR.matcher( resultStreetName );
            Matcher m2 = SPECIAL_STREET_ABBR.matcher( resultStreetName );

            if ( resultStreetName.indexOf( "MT " ) != -1 )
            {
                resultStreetName = resultStreetName.replace( "MT ", "MOUNT " ).trim();
            }

            if ( m1.find() )
            {
                // Here we just toggle the directional to full word.
                String temp1 = AddressData.getInverseDirectionalMap().get( m1.group( 1 ) );
                if ( temp1 != null )
                {
                    resultStreetName = streetName.substring( 0, m1.start( 1 ) ) + temp1 + streetName.substring( m1.end( 1 ) );
                }
            }
            if ( m2.find() )
            {
                // Here we toggle the Street Type to abbreviation or abbreviation to full word.
                String temp1 = AddressData.getStreetTypeMap().get( m2.group( 1 ) );
                String temp2 = AddressData.getStreetTypeAbbrMap().get( m2.group( 1 ) );
                if ( temp1 != null )
                {
                    resultStreetName = streetName.substring( 0, m2.start( 1 ) ) + temp1 + streetName.substring( m2.end( 1 ) );
                }
                else if ( temp2 != null )
                {
                    resultStreetName = streetName.substring( 0, m2.start( 1 ) ) + temp2 + streetName.substring( m2.end( 1 ) );
                }
            }
        }
        return resultStreetName;
    }

    /**
     * This function checks whether the address contains only digits.
     * 
     * @param streetNum
     *            The string to check.
     * @return True if it has only digits, else false.
     * 
     */
    public boolean isNumeric( final String streetNum )
    {
        if ( streetNum != null )
        {
            return streetNum.matches( "^[-+]?\\d+(\\.\\d+)?$" );
        }
        else
        {
            return false;
        }
    }

    /**
     * This function checks if the secondary address number is within the range.
     * 
     * @param secondaryNumber
     *            The address Secondary number to be set.
     * @param streetInfo
     *            The ZipPlusFour to be set.
     * 
     * @return True if the given number is in within the address range or return false.
     * 
     */
    public boolean isSecondaryNumInRange( final String secondaryNumber, final ZipPlusFour streetInfo )
    {
        return isNumberInRange( secondaryNumber, streetInfo.getAddressSecondaryLowNumber(), streetInfo
                .getAddressSecondaryHighNumber() );
    }

    /**
     * This function checks if the "number" which can be letters and number as would be used in either a Unit Number, or Address
     * Number is within the given range.
     * 
     * @param checkNumber
     *            The address number to be validated.
     * 
     * @param lowNum
     *            The low number for the range to check.
     * 
     * @param highNum
     *            The high number for the range to check.
     * 
     * @return True if the given number is in within the range, false otherwise.
     * 
     */
    public static boolean isNumberInRange( final String checkNumber, final String lowNum, final String highNum )
    {

        if ( checkNumber != null && lowNum != null && highNum != null )
        {
            // We do a basic comparison to the unit designator, to see if it lexically falls
            // into the pattern which would say it's between the two parts of the range.
            Integer lowCompare = letterNumberCompare( checkNumber, lowNum );
            Integer highCompare = letterNumberCompare( checkNumber, highNum );

            // We also check to see if both ends of the range end with the same alpha pattern,
            // for the special case of a floor that is 4C, 5C, 6C....
            boolean rangesEndSameLetter = hasSameTrailingAlpha( lowNum, highNum );

            if ( rangesEndSameLetter )
            {
                // Ok, both unit designators had the same trailing alpha... so see if this one
                // has the same... if so, check to see if it falls between...
                if ( hasSameTrailingAlpha( lowNum, checkNumber ) )
                {
                    return startingNumOnlyInRange( lowNum, highNum, checkNumber );
                }
                else
                {
                    // In this case, we had say 16J - 18J as the range, and 15E (or 30R) as the secondaryNumber,
                    // so we will say it doesn't fall between...
                    return false;
                }
            }
            else
            {
                if ( ( lowCompare != null && lowCompare >= 0 ) && ( highCompare != null && highCompare <= 0 ) )
                {
                    return true;
                }
            }
        }
        // If everything is null, then they're in range...
        else if ( checkNumber == null && lowNum == null && highNum == null )
        {
            return true;
        }
        return false;
    }

    /**
     * This function left pads the given primary street numbers .
     * 
     * @param streetNum
     *            to be set
     * @return the padded primary address number string
     */
    public String padAddressNum( final String streetNum )
    {
        String tempStreetNum = streetNum;
        String tempStreetNumPart2 = null;

        if ( streetNum == null )
        {
            return null;
        }

        if ( !isNumeric( streetNum ) )
        {
            // Check to see if it has a -.
            // These can come if you have a fractional address ( 216-1/2 ), or a
            // 2345-A type address. In either case, we want to strip off
            // everything before the - before we do the query, so we get the number
            // right...
            if ( streetNum.indexOf( '-' ) != -1 )
            {
                tempStreetNum = streetNum.substring( 0, streetNum.indexOf( '-' ) );
                tempStreetNumPart2 = streetNum.substring( streetNum.indexOf( '-' ) );
            }
            else if ( streetNum.indexOf( '/' ) != -1 && streetNum.indexOf( ' ' ) != -1 )
            {
                tempStreetNum = streetNum.substring( 0, streetNum.indexOf( ' ' ) );
            }

            // If the part before and after the dash are numeric, we can't strip and pad,
            // because it's a special case...
            if ( isNumeric( tempStreetNum ) && isNumeric( tempStreetNumPart2 ) )
            {
                return streetNum;
            }
            // otherwise, if what we cut before the dash isn't numeric, don't pad it,
            // and return what we started with...
            else if ( !isNumeric( tempStreetNum ) )
            {
                return streetNum;
            }
        }

        StringBuilder padding = new StringBuilder();
        char pad = '0';
        int len = Math.abs( PRIMARY_PAD_LENGTH ) - tempStreetNum.length();
        if ( len < 1 )
        {
            return tempStreetNum;
        }
        for ( int i = 0; i < len; ++i )
        {
            padding = padding.append( pad );
        }
        return padding.append( tempStreetNum ).toString();
    }

    /**
     * This function left pads the given secondary street numbers given.
     * 
     * @param streetNum
     *            to be set
     * @return the padded secondary address number string
     */
    public String padSecondaryNum( final String streetNum )
    {
        if ( streetNum == null )
        {
            return null;
        }
        StringBuilder padding = new StringBuilder();
        char pad = '0';
        int len = Math.abs( SECONDARY_PAD_LENGTH ) - streetNum.length();
        if ( len < 1 )
        {
            return streetNum;
        }
        for ( int i = 0; i < len; ++i )
        {
            padding = padding.append( pad );
        }
        return padding.append( streetNum ).toString();
    }

    /**
     * 
     * Checks to see if the Unit numbers are in the format of 123A - 234A.
     * 
     * @param val1
     *            String one to check.
     * @param val2
     *            String two to check.
     * 
     * @return boolean, true if have same trailing alpha pattern.
     */
    public static boolean hasSameTrailingAlpha( final String val1, final String val2 )
    {
        boolean val1EndsLetter = false;
        boolean val2EndsLetter = false;
        boolean val1StartsNumber = false;
        boolean val2StartsNumber = false;

        // If the strings contain "-" we'll remove them, to make it cleaner to compare..
        String value1 = val1.replaceAll( "-", "" );
        String value2 = val2.replaceAll( "-", "" );

        // Now to look to see if there no space before the first number...
        Pattern letterPattern = Pattern.compile( "[A-Za-z]+$" );
        Pattern numberPattern = Pattern.compile( "^[0-9]+" );
        Matcher val1Letter = letterPattern.matcher( value1 );
        Matcher val1Number = numberPattern.matcher( value1 );
        Matcher val2Letter = letterPattern.matcher( value2 );
        Matcher val2Number = numberPattern.matcher( value2 );

        // Alphabetic parts of the strings.
        val1EndsLetter = val1Letter.find();
        val2EndsLetter = val2Letter.find();
        val1StartsNumber = val1Number.find();
        val2StartsNumber = val2Number.find();

        // If the basic config is NumberLetter...
        if ( val1StartsNumber && val2StartsNumber && val1EndsLetter && val2EndsLetter )
        {
            // Then if they both end with the same letter group...
            if ( val1Letter.group( 0 ).equals( val2Letter.group( 0 ) ) )
            {
                return true;
            }
        }

        return false;

    }

    /**
     * 
     * Does a numerical range check, after stripping off any trailing alpha chars.
     * 
     * @param rangeStart
     *            String containing starting range to check.
     * @param rangeEnd
     *            String containing ending range to check.
     * @param value
     *            String to check.
     * 
     * @return true if the number is in range, inclusive.
     */
    public static boolean startingNumOnlyInRange( final String rangeStart, final String rangeEnd, final String value )
    {
        // If the strings contain "-" we'll remove them, to make it cleaner to compare..
        String start = rangeStart.replaceAll( "-", "" );
        String end = rangeEnd.replaceAll( "-", "" );
        String val = value.replaceAll( "-", "" );

        // Now to look to see if there no space before the first number...
        Pattern numberPattern = Pattern.compile( "^[0-9]+" );
        Matcher startNum = numberPattern.matcher( start );
        Matcher endNum = numberPattern.matcher( end );
        Matcher valNum = numberPattern.matcher( val );

        startNum.find();
        endNum.find();
        valNum.find();

        if ( startNum.group( 0 ) == null || startNum.group( 0 ).trim().length() == 0 )
        {
            return false;
        }

        if ( endNum.group( 0 ) == null || endNum.group( 0 ).trim().length() == 0 )
        {
            return false;
        }

        if ( valNum.group( 0 ) == null || valNum.group( 0 ).trim().length() == 0 )
        {
            return false;
        }

        int startNumber = Integer.valueOf( startNum.group( 0 ) );
        int endNumber = Integer.valueOf( endNum.group( 0 ) );
        int valNumber = Integer.valueOf( valNum.group( 0 ) );

        if ( startNumber <= valNumber && valNumber <= endNumber )
        {
            return true;
        }

        return false;

    }

    /**
     * 
     * Does an "alphabetic" comparison... so that C4 comes before C10...
     * 
     * @param val1
     *            String one to check.
     * @param val2
     *            String two to check.
     * @return 0, Positive or Negative number, indicating whether val1 is equal, greater than, or less than val2.
     */
    public static Integer letterNumberCompare( final String val1, final String val2 )
    {
    	int letterScore = -1;
        int numberScore = -1;

        boolean val1LetterExists = false;
        boolean val2LetterExists = false;
        
        boolean val1NumberExists = false;
        boolean val2NumberExists = false;
        
        boolean val1NumberDashExists = false;
        boolean val2NumberDashExists = false;
        
        boolean val1FractionExists = false;
        boolean val2FractionExists = false;
        
        boolean lettersExist = false;
        boolean numbersExist = false;

        // If the strings contain "-" we'll remove them, to make it cleaner to compare..
        String value1 = val1.replaceAll( STRING_DASH, "" );
        value1 = value1.trim();
        String value2 = val2.replaceAll( STRING_DASH, "" );
        value2 = value2.trim();

        // Now to look to see if there no space before the first number...
        Pattern letterPattern = Pattern.compile( "[A-Za-z]+" );
        Pattern numberPattern = Pattern.compile( "[0-9]+" );
        Pattern numberDashNumberPattern = Pattern.compile( "^[0-9.+-]{1}[0-9]{1,9}" );
        Pattern fractionPattern = Pattern.compile( "([0-9]*)\\s*(([0-9]+)\\s*[/]{1}\\s*([0-9]+))" );
           
        Matcher val1Letter = letterPattern.matcher( value1 );
        Matcher val2Letter = letterPattern.matcher( value2 );
        
        Matcher val1Number = numberPattern.matcher( value1 ); 
        Matcher val2Number = numberPattern.matcher( value2 );
         
        Matcher val1NumberDash = numberDashNumberPattern.matcher( value1.replaceAll( STRING_DASH, STRING_PERIOD ).trim() );
        Matcher val2NumberDash = numberDashNumberPattern.matcher( value2.replaceAll( STRING_DASH, STRING_PERIOD ).trim() );
        
        Matcher val1Fraction = fractionPattern.matcher( value1 ); 
        Matcher val2Fraction = fractionPattern.matcher( value2 );

        // Alphabetic parts of the strings.
        val1LetterExists = val1Letter.find();
        val2LetterExists = val2Letter.find();
        if ( val1LetterExists && val2LetterExists )
        {
            letterScore = val1Letter.group().compareTo( val2Letter.group() );
            lettersExist = true;

            // If one string starts with a letter and the other doesn't.... this
            // means you have some bogus data... so we'll return -1, so if you compare in a range,
            // you won't get working results...
            if ( ( value1.startsWith( val1Letter.group() ) && !value2.startsWith( val2Letter.group() ) )
                    || !value1.startsWith( val1Letter.group() ) && value2.startsWith( val2Letter.group() ) )
            {
                return null;
            }
        }

        // Numeric part of the compare.
        val1NumberExists = val1Number.find();
        val2NumberExists = val2Number.find();
        
        val1NumberDashExists = val1NumberDash.find();
        val2NumberDashExists = val2NumberDash.find();
        
        val1FractionExists = val1Fraction.find();
        val2FractionExists = val2Fraction.find();
        
        
        // This covers the case of anything with a '/' in it, which is typical of a fractional address.
        // This not 'terribly' common, but must be considered.
        if ( val1FractionExists || val2FractionExists )
        {
        	// If both aren't fractions, then we can't compare -- return null
        	if ( !( val1FractionExists && val2FractionExists ) )
        	{
        		return null;
        	}
        	else
        	{
                AddressCompareResult numberCompareResult = fractionCompare( val1Fraction, val2Fraction );
                numberScore = numberCompareResult.getValue();
                numbersExist = numberCompareResult.isNumber();
        	}
        }
        /*
         * for the number format XX-YY (without any letters in there)
         */
        else if ( val1NumberDashExists && val2NumberDashExists && !( val1LetterExists || val2LetterExists ) )
        {
            AddressCompareResult numberCompareResult = numberCompare( val1NumberDash, val2NumberDash );
            numberScore = numberCompareResult.getValue();
            numbersExist = numberCompareResult.isNumber();
        }
        else if ( val1NumberExists && val2NumberExists )
        {
            AddressCompareResult numberCompareResult = numberCompare( val1Number, val2Number );
            numberScore = numberCompareResult.getValue();
            numbersExist = numberCompareResult.isNumber();
        }

        if ( lettersExist )
        {
            if ( numbersExist )
            {
                // If the values are in agreement, then just send the numberScore,
                // since we'll return the comparison value...
                if ( ( letterScore == 0 && numberScore == 0 ) || ( letterScore < 0 && numberScore < 0 )
                        || ( letterScore > 0 && numberScore > 0 ) )
                {
                    return Integer.valueOf( numberScore );
                }
                if ( letterScore == 0 && numberScore != 0 )
                {
                    return Integer.valueOf( numberScore );
                }
                if ( letterScore != 0 && numberScore == 0 )
                {
                    return Integer.valueOf( letterScore );
                }

                // If the values disagree, then check to see whether
                // the strings start with letters or numbers
                if ( ( letterScore > 0 && numberScore < 0 ) || ( letterScore < 0 && numberScore > 0 ) )
                {
                    // If the strings start with letters...
                    if ( value1.startsWith( val1Letter.group() ) && value2.startsWith( val2Letter.group() ) )
                    {
                        return Integer.valueOf( letterScore );
                    }
                    return Integer.valueOf( numberScore );
                }
            }
            // If both numbers don't exist...then we can just return the letter difference.
            else if ( !val1NumberExists && !val2NumberExists )
            {
                return Integer.valueOf( letterScore );
            }
            else
            // One is letters and numbers the other is just numbers, so can't compare.
            {
                return null;
            }
        }

        // In this case, the letters don't exist...so make sure both don't have letters...
        // and both DO have numbers...
        if ( ( !val1LetterExists && !val2LetterExists ) && ( val1NumberExists && val2NumberExists ) )
        {
            return Integer.valueOf( numberScore );
        }
        // This case, either one has letters and the other doesn't, or
        // one has numbers and the other doesn't, or both... in all cases can't compare..
        return null;

    }

    /**
     * The fractional parts is added to the decimal portion after the decimal portion is multiplied by a multiplier. The multiplier
     * is selected based on the maximum expected fractional digit positions. currently 5 is the maximum expected digits in the
     * fractional part.
     * 
     * 14.8 eq 1400008 14.15 eq 1400015 1.48 eq 100048 11.99999 eq 1199999
     * 
     * @param numberToAdjust
     *            number to adjust
     * @return adjusted float number for compare
     */
    public static Float getAdjustedNumber( final String numberToAdjust )
    {

        final long multiplier = 100000L;

        double originalNumber = Float.valueOf( numberToAdjust );
        long integerPart = (long) originalNumber;
        long fractionalPart = 0;

        int fractionalBeginIndex = numberToAdjust.indexOf( "." );
        boolean hasFractionalPart = ( fractionalBeginIndex != -1 );

        if ( hasFractionalPart )
        {
            fractionalPart = Long.valueOf( numberToAdjust.substring( fractionalBeginIndex + 1 ) );
        }

        return new Float( ( multiplier * integerPart ) + fractionalPart );

    }

    /**
     * @param val1Number
     *            first value to compare
     * @param val2Number
     *            second value to compare
     * @return result of comparison
     */
    public static AddressCompareResult numberCompare( final Matcher val1Number, final Matcher val2Number )
    {

        float number1 = 0;
        float number2 = 0;
        int numberScore = -1;

        AddressCompareResult result = new AddressCompareResult();

        try
        {
            number1 = getAdjustedNumber( val1Number.group() );

            result.setNumber( true );
        }
        catch ( NumberFormatException e )
        {
            number1 = getAdjustedNumber( val1Number.group()
            		.substring( 0, (val1Number.group().length() < MAX_DIGITS_OF_NUMBER ? val1Number.group().length() : MAX_DIGITS_OF_NUMBER ) ) );
        }

        try
        {
            number2 = getAdjustedNumber( val2Number.group() );
            result.setNumber( true );
        }
        catch ( NumberFormatException e )
        {
            number2 = getAdjustedNumber( val2Number.group()
            		.substring( 0, (val2Number.group().length() < MAX_DIGITS_OF_NUMBER ? val2Number.group().length() : MAX_DIGITS_OF_NUMBER ) ) );
        }

        // ********************************************************
        // The negative value is what we initialized the score to.
        // *******************************************************
        if ( number1 == number2 )
        {
            numberScore = 0;
        }
        else if ( number1 > number2 )
        {
            numberScore = 1;
        }
        result.setValue( numberScore );

        return result;
    }
    
    /**
     * This method compares fractions.  Initially, it's just basic fractions, but we should be able to move on
     * to alpha-numeric fractions like 1/2A or A1/2.  We might even need to handle B7 1/2A, which, I grant, is insane
     * but possible.
     * @param val1Number
     *            first value to compare
     * @param val2Number
     *            second value to compare
     * @return result of comparison
     * @author rchapple
     */
    public static AddressCompareResult fractionCompare( final Matcher val1Number, final Matcher val2Number )
    {

        float number1 = 0;
        float number2 = 0;
        int numberScore = -1;

        // Because of the pattern that is used to parse, we know that the groups that contain the numerator and the
        // denominator are groups 1 and 3
        final int NUMERATOR_GROUP = 3;
        final int DENOMINATOR_GROUP = 4;
        
        
        AddressCompareResult result = new AddressCompareResult();

        try
        {
            number1 = Float.parseFloat( val1Number.group(NUMERATOR_GROUP) ) / Float.parseFloat( val1Number.group( DENOMINATOR_GROUP ) );
            result.setNumber( true );
        }
        catch ( NumberFormatException e )
        {

        }

        try
        {
            number2 = Float.parseFloat( val2Number.group(NUMERATOR_GROUP) ) / Float.parseFloat( val2Number.group( DENOMINATOR_GROUP ) );
            result.setNumber( true );
        }
        catch ( NumberFormatException e )
        {
        	
        }

        // ********************************************************
        // The negative value is what we initialized the score to.
        // *******************************************************
        if ( number1 == number2 )
        {
            numberScore = 0;
        }
        else if ( number1 > number2 )
        {
            numberScore = 1;
        }
        result.setValue( numberScore );

        return result;
    }
    
   
    /**
     * This function is the logical inverse of padAddressNum, in that it essentially finds the first non-zero character from the
     * left and returns it and all the characters to the right of it.
     * 
     * @param streetNum
     *            the street number or other value to zero-pad
     * @return the unpadded address number string
     */
    public String unpadAddressNum( final String streetNum )
    {

        /* If the past value is null, can't do anything, just return what you were given */
        if ( streetNum != null )
        {
            int cutFrom = -1;
            /* Loop looking for the first char that isn't a '0' */
            for ( int i = 0; i < streetNum.length(); i++ )
            {
                if ( streetNum.charAt( i ) != '0' )
                {
                    cutFrom = i;
                    break;
                }
            }

            if ( cutFrom != -1 || ( streetNum.charAt( streetNum.length() ) == '0' ) )
            {
                return streetNum.substring( cutFrom );
            }
            else
            {
                return "";
            }
        }
        return streetNum;
    }

    /**
     * This method adds a status code to the hashmap of status codes. There are no dupes here. You can set a duplicate status, but
     * obviously, we only use it once.
     * 
     * @param code
     *            the status code to add to the codes
     */
    private void addStatusCode( final Double code )
    {
        // Before we try to set the value, make sure that the code that was passed is
        // valid, and by that we mean present in the static ServiceabiltityProcessingCodes object
        this.statusCodes.put( code, ServiceabilityProcessingCodes.getCodes().get( code ) );
    }

    /**
     * This method removes a status code from the hashmap of status codes.
     * 
     * @param code
     *            the status code to remove from the codes
     */
    private void removeStatusCode( final Double code )
    {
        this.statusCodes.remove( code );
    }

    /**
     * This is a simple convenience method to clear out all the status codes.
     */
    public void clearStatusCodes()
    {
        this.statusCodes.clear();
    }

    /**
     * Function to report if results were perfect match.
     * 
     * @return true if the status says perfect match.
     */
    public boolean isPerfectMatch()
    {
        if ( this.statusCodes.containsKey( ServiceabilityProcessingCodes.PERFECT_MATCH ) )
        {
            return true;
        }

        return false;
    }

    /**
     * Function to report if results contained multiple candidate addresses.
     * 
     * @return true if the status says the results contained multiple candidate addresses.
     */
    public boolean hasMultipleCandidates()
    {
        if ( this.statusCodes.containsKey( ServiceabilityProcessingCodes.MULTIPLE_ADDRESSES_FOUND ) )
        {
            return true;
        }

        return false;
    }

    /**
     * Function to report if result contains a phonetic match.
     * 
     * @return true if has phonetic match.
     */
    public boolean hasPhoneticMatch()
    {
        if ( this.statusCodes.containsKey( ServiceabilityProcessingCodes.STREET_NAME_MATCHED_PHONETICALLY )
                || this.statusCodes.containsKey( ServiceabilityProcessingCodes.CITY_NAME_MATCHED_PHONETICALLY ) )
        {
            return true;
        }

        return false;
    }

    /**
     * Function to report if results were a non-exact match.
     * 
     * @return true if the status says non-exact match.
     */
    public boolean isNonExactMatch()
    {
        if ( this.statusCodes.containsKey( ServiceabilityProcessingCodes.NON_EXACT_MATCH ) )
        {
            return true;
        }

        return false;
    }

    /**
     * This method turns the bitmap of statuses that are created during processing into a list of textual descriptions of those
     * statuses.
     * 
     * @return list of strings that are textual descriptions of the status codes associated with the
     */
    public Map<Double, String> getStatusCodes()
    {
        return this.statusCodes;
    }

    @Produces
	public EntityManager getRefDataEntityManager() {
		return refDataEntityManager;
	}

	public void setRefDataEntityManager(EntityManager refDataEntityManager) {
		this.refDataEntityManager = refDataEntityManager;
	}
}
