package com.A.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AddressOwnershipEnum
{
	OWN("own"),RENT("rent");
    public String desc;

    private static final Map<String, String> LOOKUP = new HashMap<String, String>();
    
    AddressOwnershipEnum( final String desc )
    {
        this.desc = desc;
    }

    static
    {
        for ( AddressOwnershipEnum s : EnumSet.allOf( AddressOwnershipEnum.class ) ) 
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
