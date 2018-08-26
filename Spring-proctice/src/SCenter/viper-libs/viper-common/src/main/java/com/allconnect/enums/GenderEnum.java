package com.A.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ebthomas
 * 
 */
public enum GenderEnum
{
    Female( "F", 0 ), Male( "M", 1 );

    private String desc;
    private Integer id;

    /**
     * @param desc gender description
     * @param id unique key for the id
     */
    GenderEnum( final String desc, final Integer id )
    {
        this.desc = desc;
        this.id = id;
        
        
    }

    private static final Map<String, String> LOOKUP = new HashMap<String, String>();

    static
    {
        for ( GenderEnum s : EnumSet.allOf( GenderEnum.class ) ) 
        {
            LOOKUP.put( s.getDesc(), s.name() );
        }
    }

    /**
     * @param code unique code for the gender
     * @return lookup value
     */
    public static String get( final String code )
    {
        return LOOKUP.get( code );
    }

    public String getDesc()
    {
        return desc;
    }

    /**
     * @param desc description 
     */
    public void setDesc( final String desc )
    {
        this.desc = desc;
    }

    public Integer getId()
    {
        return id;
    }

    /**
     * @param id unique identification
     */
    public void setId( final Integer id )
    {
        this.id = id;
    }

}
