package templates;

import org.apache.log4j.Logger;



/**
 * 
 * @author rchapple
 *
 */
public final class VersionTracker
{
    private static final String REVISION_MAJOR_NUM = "@__PRODUCT_VERSION__@";
    private static final String REVISION_MINOR_NUM = "@__PRODUCT_VERSION__@";
    private static final String REVISION_PATCH_NUM = "2";
    private static final String REVISION_BUILD_NUM = "1";
    private static final String VERSION_NUMBER = 
        REVISION_MAJOR_NUM + "." + REVISION_MINOR_NUM + "." + REVISION_PATCH_NUM + ":" + REVISION_BUILD_NUM;

    
    private static final Logger log = Logger.getLogger(  VersionTracker.class );
    
    /**
     * Default constructor.
     */
    private VersionTracker()
    {
        log.info( "************************VERSION TRACKER*********************" );
        log.info( VERSION_NUMBER );
        log.info( "***********************************************************" );
         
    }
    
    /** This method gets the full version number, which is the only one 
     *  a user is probably interested in.
     * @return the VERSION_NUMBER
     */
    public static String getVersionNumber()
    {
        return VERSION_NUMBER;
    }   

    
    public static void main(String[] arg)
    {
        VersionTracker vt = new VersionTracker();
        System.out.println(vt.getVersionNumber());
    }
}
