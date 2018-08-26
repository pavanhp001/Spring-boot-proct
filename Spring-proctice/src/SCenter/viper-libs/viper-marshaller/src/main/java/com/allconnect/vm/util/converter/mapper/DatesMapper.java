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
public class DatesMapper
{
    public static final Map<String, String> datesFields = new HashMap<String, String>();
    
    static
    {
        datesFields.put( "DesiredStartDate", "DesiredStartDate" );
        datesFields.put( "ScheduledStartDate", "ScheduledStartDate" );
        datesFields.put( "ActualStartDate", "ActualStartDate" ); 
    }
}

 
