package com.AL.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum CustomerActionEnum {
	CREATECUSTOMER("createCustomer"),UPDATECUSTOMER("updateCustomer"),REFERENCECUSTOMER("referenceCusotmer"),
	ADDADDRESS("addAddress");
    public String desc;

    private static final Map<String, String> LOOKUP = new HashMap<String, String>();
    
    CustomerActionEnum( final String desc )
    {
        this.desc = desc;
    }

    static
    {
        for ( AddressRoleEnum s : EnumSet.allOf( AddressRoleEnum.class ) ) 
        {
            LOOKUP.put( s.getDesc(), s.name() );
        }
    }
    
    public String getDesc()
    {
        return desc;
    }
    public void setDesc( final String desc )
    {
        this.desc = desc;
    }
    public static String get( final String code )
    {
        return LOOKUP.get( code );
    }
}
