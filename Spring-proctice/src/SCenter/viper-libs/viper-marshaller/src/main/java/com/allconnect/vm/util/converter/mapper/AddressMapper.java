/**
 *
 */
package com.A.vm.util.converter.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

 

/**
 * @author ebthomas
 *
 */
public final class AddressMapper
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
        addressFields.put( "changeType", "changeType" );
        addressFields.put( "cityAlias", "cityAlias" );
        addressFields.put( "expiration", "expiration" );
        
 
        
        
        
         
    }
}
