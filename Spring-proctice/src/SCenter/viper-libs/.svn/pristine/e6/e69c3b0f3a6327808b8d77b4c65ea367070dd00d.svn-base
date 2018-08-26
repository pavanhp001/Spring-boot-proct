package com.A.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum DwellingEnum {
    APARTMENT("apartment"),HOUSE("house"),NEW_CONSTRUCTION("new_construction"),CONDO("condo/townhouse"),BUSINESS("business");
    public String desc;

    private static final Map<String, String> LOOKUP = new HashMap<String, String>();
    
    DwellingEnum( final String desc )
    {
        this.desc = desc;
    }

    static
    {
     
    	
        for ( DwellingEnum s : EnumSet.allOf( DwellingEnum.class ) ) 
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
