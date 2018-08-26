package com.A.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AddressStatusEnum
{
	ACTIVE("active"),INACTIVE("inactive"),PROSPECT("prospect");
    public String desc;

    private static final Map<String, String> LOOKUP = new HashMap<String, String>();
    
    AddressStatusEnum( final String desc )
    {
        this.desc = desc;
    }

    static
    {
        for ( AddressStatusEnum s : EnumSet.allOf( AddressStatusEnum.class ) ) 
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
