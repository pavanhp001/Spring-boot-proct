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
public final class BillingInfoMapper
{
    public static final Map<String, String> billingInformationFields = new HashMap<String, String>();
     

    static
    {
        billingInformationFields.put( "billingMethod", "billingMethod" );
        billingInformationFields.put( "checkingAccountNumber", "checkingAccountNumber" );
        billingInformationFields.put( "creditCardNumber", "creditCardNumber" );
        billingInformationFields.put( "routingNumber", "routingNumber" );
        billingInformationFields.put( "verificationCode", "verificationCode" );
        billingInformationFields.put( "cardHolderName", "cardHolderName" );
        billingInformationFields.put( "status", "status" );
    }
}
 