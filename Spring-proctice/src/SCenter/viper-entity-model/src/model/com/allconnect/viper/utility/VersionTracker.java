package com.A.V.utility;


/**
 * 
 * @author rchapple
 *
 */
public final class VersionTracker
{
    private static final String REVISION_MAJOR_NUM = "1";
    private static final String REVISION_MINOR_NUM = "7";
    private static final String REVISION_PATCH_NUM = "1";
    private static final String REVISION_BUILD_NUM = "1";
    private static final String VERSION_NUMBER = 
        REVISION_MAJOR_NUM + "." + REVISION_MINOR_NUM + "." + REVISION_PATCH_NUM + ":" + REVISION_BUILD_NUM;

    /**
     * Default constructor.
     */
    private VersionTracker()
    {
        // Do Nothing
    }
    
    /** This method gets the full version number, which is the only one 
     *  a user is probably interested in.
     * @return the VERSION_NUMBER
     */
    public static String getVersionNumber()
    {
        return VERSION_NUMBER;
    }   

}
