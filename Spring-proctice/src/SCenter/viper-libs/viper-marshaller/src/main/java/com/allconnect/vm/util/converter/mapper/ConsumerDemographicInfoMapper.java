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
public final class ConsumerDemographicInfoMapper
{
    public static final Map<String, String> contact = new HashMap<String, String>();

    static
    {
        contact.put( "Title", "Title" );
        contact.put( "FirstName", "FirstName" );
        contact.put( "LastName", "LastName" );
        contact.put( "MiddleName", "MiddleName" );
        contact.put( "NameSuffix", "NameSuffix" );
        //contact.put( "Dob", "Dob" );
        contact.put( "Ssn", "Ssn" );
        //A customer no is now system generated as in the Accord 6
        //contact.put( "ACustomerNumber", "ACustomerNumber" );
        contact.put( "EMailOptIn", "EMailOptIn" );
        contact.put( "directMailOptIn", "directMailOptIn" );
        contact.put( "region", "region" );
        contact.put( "nonBuyerWebOptIn", "nonBuyerWebOptIn" );
        contact.put( "marketingOptIn", "marketingOptIn" );
        //contact.put( "PrimaryLanguage", "PrimaryLanguage" );
    }
}


