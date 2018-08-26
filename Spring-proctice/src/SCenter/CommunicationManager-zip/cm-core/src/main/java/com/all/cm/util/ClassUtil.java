/**
 *
 */
package com.AL.cm.util;

/**
 * @author ebthomas
 * 
 */
public class ClassUtil
{

    public static Object getShortClassName( Object obj, String defaultReturn )
    {
        if ( obj == null )
        {
            return defaultReturn;
        }
        return obj.getClass().getSimpleName();

    }
}
