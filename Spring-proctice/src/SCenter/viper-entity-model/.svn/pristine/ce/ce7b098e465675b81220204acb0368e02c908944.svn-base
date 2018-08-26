package com.A.V.utility;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a utility class to hold the definitions of the various
 * processing messages that could be returned from the Address Parsing.
 * 
 * @author rporumalla
 *
 */
public final class AddressProcessingCodes
{
    public static final Double PERFECT_MATCH = 1.0;
    public static final Double NON_EXACT_MATCH = 1.1;
    public static final Double NO_MATCHES_FOUND = 1.2;
    public static final Double ADDRESS_PARSE_ERROR = 2.0;
    public static final Double SYSTEM_ERROR = 3.0;
    public static final Double ADDRESS_NOT_FOUND = 4.0;
    public static final Double MULTIPLE_ADDRESSES_FOUND = 4.1;
    public static final Double ADDRESS_NOT_SERVICEABLE_PO_BOX = 4.2;
    public static final Double ADDRESS_NOT_SERVICEABLE_APO_BOX = 4.3;
    public static final Double ADDRESS_NOT_SERVICEABLE_FPO_BOX = 4.4;
    public static final Double STREET_NOT_FOUND = 5.0;
    //public static final Double RTI_SERVICE_INVOKED = 6.0;
    //public static final Double RTI_CACHE_HIT_FOR_TELCO_PROVIDER = 6.1;
    //public static final Double RTI_CACHE_HIT_FOR_CABLE_PROVIDER = 6.2;
    public static final Double RESULTS_NOT_FROM_FULL_ADDRESS = 7.0;
    public static final Double RESULTS_FROM_ZIP_PLUS_FOUR_ONLY = 7.1;
    public static final Double RESULTS_FROM_ZIP_5_ONLY = 7.2;
    public static final Double STREET_NUMBER_MISSING = 10.1;
    public static final Double PREDIRECTION_REMOVED = 11.1;
    public static final Double PREDIRECTION_ADDED = 11.2;
    public static final Double PREDIRECTION_CHANGED = 11.3;
    public static final Double STREET_NAME_MISSING = 12.1;
    public static final Double STREET_NAME_MATCHED_PHONETICALLY = 12.2;
    public static final Double STREET_TYPE_MISSING = 13.1;
    public static final Double POSTDIRECTION_REMOVED = 14.1;
    public static final Double POSTDIRECTION_ADDED = 14.2;
    public static final Double UNIT_MISSING = 15.1;
    public static final Double UNIT_NOT_FOUND = 15.2;
    public static final Double UNIT_INFORMATION_DROPPED = 15.3; // This isn't currently returned in any case.  EDB 02/06/09
    public static final Double UNIT_INFORMATION_ASSUMED_CORRECT = 15.4;
    public static final Double CITY_MISSING = 16.1;
    public static final Double CITY_DETERMINED_FROM_ZIP = 16.2;
    public static final Double CITY_COULD_NOT_BE_FOUND_IN_STATE = 16.3;
    public static final Double CITY_NAME_MATCHED_PHONETICALLY = 16.4;
    public static final Double STATE_MISSING = 17.1;
    public static final Double STATE_DETERMINED_FROM_ZIP = 17.2;
    public static final Double ZIP_CODE_MISSING = 18.1;
    public static final Double ZIP_NOT_FOUND = 18.2;
    public static final Double ZIP_CODE_CHANGED = 18.3;
    public static final Double PLUS4_CHANGED = 18.4;
    
    
    
    public static final String PERFECT_MATCH_STRING = "Perfect Match";
    public static final String NON_EXACT_MATCH_STRING = "Non-Exact Match";
    public static final String NO_MATCHES_FOUND_STRING = "No Matches Found";
    // These two aren't used right now, since these errors are only reported as a Status, and not a processing message.
    // The three above are used for both status and processing messages.
    //public static final String ADDRESS_PARSE_ERROR_STRING = "Address Parse Error";
    //public static final String SYSTEM_ERROR_STRING = "System Error";
    public static final String ADDRESS_NOT_FOUND_STRING = "Address not found";
    public static final String MULTIPLE_ADDRESSES_FOUND_STRING = "Multiple addresses found";
    public static final String ADDRESS_NOT_SERVICEABLE_PO_BOX_STRING = "Address not serviceable - PO Box";
    public static final String ADDRESS_NOT_SERVICEABLE_APO_BOX_STRING = "Address not serviceable - APO Box";
    public static final String ADDRESS_NOT_SERVICEABLE_FPO_BOX_STRING = "Address not serviceable - FPO Box";
    public static final String STREET_NOT_FOUND_STRING = "Street not found";
    public static final String RTI_SERVICE_INVOKED_STRING = "The address caused at least one Real Time Integration to be invoked";
    //public static final String RTI_CACHE_HIT_FOR_TELCO_PROVIDER_STRING 
    //                        = "The address matched a result in the Real Time Integration cache for a Telco Provider";
    //public static final String RTI_CACHE_HIT_FOR_CABLE_PROVIDER_STRING 
    //                        = "The address matched a result in the Real Time Integration cache for a Cable Provider";
    public static final String RESULTS_NOT_FROM_FULL_ADDRESS_STRING = "Results not derived from full address";
    public static final String RESULTS_FROM_ZIP_PLUS_FOUR_ONLY_STRING = "Results derived from ZIP5 and PLUS4";
    public static final String RESULTS_FROM_ZIP_5_ONLY_STRING = "Results derived from ZIP5"; 
    public static final String STREET_NUMBER_MISSING_STRING = "Street Number missing";
    public static final String PREDIRECTION_REMOVED_STRING = "Predirection removed";
    public static final String PREDIRECTION_ADDED_STRING = "Predirection added";
    public static final String PREDIRECTION_CHANGED_STRING = "Predirection changed";
    public static final String STREET_NAME_MISSING_STRING = "Street name missing";
    public static final String STREET_NAME_MATCHED_PHONETICALLY_STRING = "Street name matched phonetically";
    public static final String STREET_TYPE_MISSING_STRING = "Street Type Missing";
    public static final String POSTDIRECTION_REMOVED_STRING = "Postdirection removed";
    public static final String POSTDIRECTION_ADDED_STRING = "Postdirection added";
    public static final String UNIT_MISSING_STRING = "Unit Missing";
    public static final String UNIT_NOT_FOUND_STRING = "Unit Not Found";
    public static final String UNIT_INFORMATION_DROPPED_STRING 
                            = "Unit information dropped"; // This isn't currently returned in any case.  EDB 02/06/09
    public static final String UNIT_INFORMATION_ASSUMED_CORRECT_STRING = "Unit information assumed correct";
    public static final String CITY_MISSING_STRING = "City Missing";
    public static final String CITY_DETERMINED_FROM_ZIP_STRING = "City Determined from ZIP";
    public static final String CITY_COULD_NOT_BE_FOUND_IN_STATE_STRING = "City could not be found in State";
    public static final String CITY_NAME_MATCHED_PHONETICALLY_STRING = "City name matched phonetically";
    public static final String STATE_MISSING_STRING = "State Missing";
    public static final String STATE_DETERMINED_FROM_ZIP_STRING = "State Determined from ZIP";
    public static final String ZIP_CODE_MISSING_STRING = "ZIP code Missing";
    public static final String ZIP_NOT_FOUND_STRING = "ZIP not found";
    public static final String ZIP_CODE_CHANGED_STRING = "ZIP code changed";
    public static final String PLUS4_CHANGED_STRING = "+4 changed"; 
    
    private static Map<Double, String> codes;
       
    static 
    {
        setupCodes();
    }
    
    /** 
     *  Default Constructor.
     */
    private AddressProcessingCodes()
    {
        // Do nothing right now.
    }
    
    public static Map<Double, String> getCodes()
    {
        return codes;
    }

    public static void setCODES( final Map<Double, String> newCodes )
    {
        codes = newCodes;
    }

    /**
     * Function to set up the codes mapping.
     */
    public static void setupCodes()
    {
        // Populate the "codes" HashMap with the appropriate values
        codes = new HashMap<Double, String>();
        
        codes.put( PERFECT_MATCH, PERFECT_MATCH_STRING );
        codes.put( NON_EXACT_MATCH, NON_EXACT_MATCH_STRING );
        codes.put( NO_MATCHES_FOUND, NO_MATCHES_FOUND_STRING );
        
        codes.put( ADDRESS_NOT_FOUND, ADDRESS_NOT_FOUND_STRING );
        codes.put( MULTIPLE_ADDRESSES_FOUND, MULTIPLE_ADDRESSES_FOUND_STRING );
        codes.put( ADDRESS_NOT_SERVICEABLE_PO_BOX, ADDRESS_NOT_SERVICEABLE_PO_BOX_STRING );
        codes.put( ADDRESS_NOT_SERVICEABLE_APO_BOX, ADDRESS_NOT_SERVICEABLE_APO_BOX_STRING );
        codes.put( ADDRESS_NOT_SERVICEABLE_FPO_BOX, ADDRESS_NOT_SERVICEABLE_FPO_BOX_STRING );
        
        codes.put( STREET_NOT_FOUND, STREET_NOT_FOUND_STRING );
        
        //codes.put( RTI_SERVICE_INVOKED, RTI_SERVICE_INVOKED_STRING );
        //codes.put( RTI_CACHE_HIT_FOR_TELCO_PROVIDER, RTI_CACHE_HIT_FOR_TELCO_PROVIDER_STRING );
        
        codes.put( PREDIRECTION_REMOVED, PREDIRECTION_REMOVED_STRING );
        codes.put( PREDIRECTION_ADDED, PREDIRECTION_ADDED_STRING );
        codes.put( PREDIRECTION_CHANGED, PREDIRECTION_CHANGED_STRING  );

        codes.put( STREET_NUMBER_MISSING, STREET_NUMBER_MISSING_STRING );

        codes.put( RESULTS_NOT_FROM_FULL_ADDRESS, RESULTS_NOT_FROM_FULL_ADDRESS_STRING );
        codes.put( RESULTS_FROM_ZIP_PLUS_FOUR_ONLY, RESULTS_FROM_ZIP_PLUS_FOUR_ONLY_STRING );
        codes.put( RESULTS_FROM_ZIP_5_ONLY, RESULTS_FROM_ZIP_5_ONLY_STRING  );

        codes.put( STREET_NAME_MISSING, STREET_NAME_MISSING_STRING );
        codes.put( STREET_NAME_MATCHED_PHONETICALLY, STREET_NAME_MATCHED_PHONETICALLY_STRING );

        codes.put( STREET_TYPE_MISSING, STREET_TYPE_MISSING_STRING );

        codes.put( POSTDIRECTION_REMOVED, POSTDIRECTION_REMOVED_STRING );
        codes.put( POSTDIRECTION_ADDED, POSTDIRECTION_ADDED_STRING );

        codes.put( UNIT_MISSING, UNIT_MISSING_STRING );
        codes.put( UNIT_NOT_FOUND, UNIT_NOT_FOUND_STRING );
        codes.put( UNIT_INFORMATION_DROPPED, UNIT_INFORMATION_DROPPED_STRING );
        codes.put( UNIT_INFORMATION_ASSUMED_CORRECT, UNIT_INFORMATION_ASSUMED_CORRECT_STRING );

        codes.put( CITY_MISSING, CITY_MISSING_STRING );
        codes.put( CITY_DETERMINED_FROM_ZIP, CITY_DETERMINED_FROM_ZIP_STRING );
        codes.put( CITY_COULD_NOT_BE_FOUND_IN_STATE, CITY_COULD_NOT_BE_FOUND_IN_STATE_STRING );
        codes.put( CITY_NAME_MATCHED_PHONETICALLY, CITY_NAME_MATCHED_PHONETICALLY_STRING );

        codes.put( STATE_MISSING, STATE_MISSING_STRING );
        codes.put( STATE_DETERMINED_FROM_ZIP, STATE_DETERMINED_FROM_ZIP_STRING );

        codes.put( ZIP_CODE_MISSING, ZIP_CODE_MISSING_STRING );
        codes.put( ZIP_NOT_FOUND, ZIP_NOT_FOUND_STRING );
        codes.put( ZIP_CODE_CHANGED, ZIP_CODE_CHANGED_STRING );
        codes.put( PLUS4_CHANGED, PLUS4_CHANGED_STRING );
    }
}
