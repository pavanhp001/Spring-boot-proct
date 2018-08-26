package com.A.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AddressRoleEnum {
    CURRENTADDRESS("currentAddress"),PREVIOUSADDRESS("previousAddress"),BILLINGADDRESS("billingAddress"),SERVICEADDRESS("serviceAddress");
    public String desc;

    private static final Map<String, String> LOOKUP = new HashMap<String, String>();
    
    AddressRoleEnum( final String desc )
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
