package com.A.V.utility;

/**
 * A utility class to handle all some string related functionality.
 * 
 * @author ssathiyanarayanan
 * 
 */
public final class StringUtils
{
    /**
     * Default Constructor.
     */
    private StringUtils()
    {
        // Do nothing special right now.
    }

    /**
     * 
     * @param str
     *            the String to check, may be null
     * @return true if the String is null, empty or whitespace
     * 
     */
    public static boolean isBlank( final String str )
    {
        if ( str == null || ( str.length() ) == 0 )
        {
            return true;
        }
        for ( int i = 0; i < str.length(); i++ )
        {
            if ( !( Character.isWhitespace( str.charAt( i ) ) ) )
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * @param str
     *            the String to check, may be null
     * @return true if the String is not empty and not null and not whitespace
     * 
     */
    public static boolean isNotBlank( final String str )
    {
        return !StringUtils.isBlank( str );
    }
    
    /**
     * This function checks whether the strings are the same or both empty/null.
     * 
     * @param arg1
     *            from address bean to be set
     * @param arg2
     *            from zipPlusFour list to be set
     * 
     * @return True if the values are equal, or both null/empty, else false.
     * 
     */
    public static boolean equalOrBothEmptyNull( final String arg1, final String arg2 )
    {
        if ( ( arg1 != null ) && ( arg2 != null ) )
        {
            if ( arg1.trim().equals( arg2.trim() ) )
            {
                return true;
            }
        }
        else if (  ( arg1 == null || arg1.length() == 0 ) 
                && ( arg2 == null || arg2.length() == 0 ) )
        {
            return true;
        }

        return false;

    }
    
    /**
     * This function checks whether the strings are both empty/null.
     * 
     * @param arg1
     *            from address bean to be set
     * @param arg2
     *            from zipPlusFour list to be set
     * 
     * @return True if the values are equal, or both null/empty, else false.
     * 
     */
    public static boolean bothEmptyNullOrPresent( final String arg1, final String arg2 )
    {
        if ( ( arg1 != null ) && ( arg2 != null ) )
        {
            if ( arg1.trim().length() > 0 && arg2.trim().length() > 0 )
            {
                return true;
            }
        }
        else if (  ( arg1 == null || arg1.length() == 0 ) 
                && ( arg2 == null || arg2.length() == 0 ) )
        {
            return true;
        }

        return false;

    }

}
