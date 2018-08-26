/**
 *
 */
package com.A.util;

/**
 * @author ebaugh
 * 
 */
public final class Constants
{
    // Database / Field Max and Min Sizes.
    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 10;

    public static final int USERNAME_MIN_LENGTH = 4;
    public static final int USERNAME_MAX_LENGTH = 15;

    public static final int NAME_MIN_LENGTH = 0;
    public static final int NAME_MAX_LENGTH = 100;

    // Allocation sizes.
    public static final int DEFAULT_LOG_BUFFER_SIZE = 2000;

    public static final int DEFAULT_MAX_RESULT_SIZE = 25;

 
    public static final String PROMOTION = "promotion";
    public static final String MARKETITEM = "marketitem";
    public static final String BUNDLE = "bundle";
    /**
     * Default Constructor.
     */
    private Constants()
    {
        // We do nothing special for the constants constructor currently.
    }
}
