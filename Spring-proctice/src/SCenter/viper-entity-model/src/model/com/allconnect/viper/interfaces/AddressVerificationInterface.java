package com.A.V.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.A.V.beans.entity.AddressBean;
import com.A.V.beans.entity.ZipCodesCityState;
import com.A.V.beans.entity.ZipPlusFour;

/**
 * 
 * @author ebaugh
 * 
 */
@Local
public interface AddressVerificationInterface extends Serializable
{
    /**
     * Searches the Zip Code City State table and returns the cities that match the city and state parameters.
     * 
     * @param city
     *            City to search for.
     * @param state
     *            State to search for.
     * @return list of matching zipCodeCityState records.
     */
    List<ZipCodesCityState> getCityStateNoZip( String city, String state );

    /**
     * Searches the Zip Code City State table and returns the cities that match the city,state and zip code parameters.
     * 
     * @param city
     *            City to search for.
     * @param state
     *            State to search for.
     * @param zipcode
     *            ZipCode to search for.
     * @return list of matching zipCodeCityState records.
     */
    List<ZipCodesCityState> getCityState( String city, String state, String zipcode );

    /**
     * Searches the Zip Code + 4 table and returns the matching streets based on the street number and street name.
     * 
     * @param streetNumber
     *            Number to search for.
     * @param streetName
     *            Name to search for.
     * @param unitNumber
     *            Unit number to search for (for RR addresses)
     * @param zipCode
     *            ZipCode to search for.
     * 
     * @return list of matching streets.
     */
    List<ZipPlusFour> getStreetsWithZip( String streetNumber, String streetName, String unitNumber, String zipCode );

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
    List<ZipPlusFour> getStreetsWithState( String streetNumber, String streetName, String unitNumber, String state );

    /**
     * Function that returns either a single best match, or a list of suggestions.
     * 
     * @param address
     *            to be set.
     * @param cityStates
     *            Cities and States to use for matching.
     * @param streets
     *            Streets to use for matching.
     * 
     * @return best address or list of suggestions.
     */
    List<AddressBean> calculateBestMatchOrSuggestions( AddressBean address, List<ZipCodesCityState> cityStates,
            List<ZipPlusFour> streets );

    /**
     * Function that returns either a single best match, or a list of suggestions.
     * 
     * @param rawAddress
     *            the parsed, but unstandardized address representation of address.
     * @param address
     *            the parsed and normalized address that we're checking.
     * @param statusCodes
     *            the map that holds status/processing messages generated during processing
     * @param trySoundex
     *            flag to indicate whether or not to try Soundex
     * @return best address or list of suggestions.
     */

    List<AddressBean> getBestMatchOrSuggestions( AddressBean rawAddress, AddressBean address, Map<Double, String> statusCodes,
            boolean trySoundex );

    /**
     * Function that looks for city State record based on a given zip code.
     * 
     * @param zipCode
     *            postal Code used for matching.
     * @param singleMatch
     *            boolean representing whether or not to return more that one row/match
     * @return list of matching zipCodeCityState records.
     */
    List<ZipCodesCityState> getByZipCodeNoCityState( final String zipCode, boolean singleMatch );

    /**
     * Function that gives information about any matches that were (or weren't) found.
     * 
     * @return List &lt String &gt a list of textual explanations of status codes associated with the address matching attempt.
     */
    Map<Double, String> getStatusCodes();

    /**
     * A method to clear the underlying EntityManager cache. Useful in those cases where the caller knows that the results that it
     * is creating do not have usefulness outside of the scope of execution.
     */
    void clearEMCache();
}
