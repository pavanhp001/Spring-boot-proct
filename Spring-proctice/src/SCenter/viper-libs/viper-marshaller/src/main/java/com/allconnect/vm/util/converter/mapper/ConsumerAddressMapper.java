/**
 *
 */
package com.A.vm.util.converter.mapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ebthomas
 *
 */
public final class ConsumerAddressMapper
{
    public static final Map<String, String> addressFields = new HashMap<String, String>();

    static
    {
        addressFields.put( "prefixDirectional", "prefixDirectional" );
        addressFields.put( "streetNumber", "streetNumber" );
        addressFields.put( "streetName", "streetName" );
        addressFields.put( "streetType", "streetType" );
        addressFields.put( "line2", "line2" );
        addressFields.put( "postfixDirectional", "postfixDirectional" ); 
        addressFields.put( "city", "city" );
        addressFields.put( "stateOrProvince", "stateOrProvince" );
        addressFields.put( "postalCode", "postalCode" );
        addressFields.put( "country", "country" );
        addressFields.put( "countyParishBorough", "countyParishBorough" );
        addressFields.put( "addressBlock", "inputAddress" );
    }
}
