package com.A.ome.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import junit.framework.TestCase;

/**
 * @author ebthomas
 *
 */
public class BaseATestX extends TestCase
{
public void defaultTest() {
		
	}

    /**
     * @param fileName name to get FileName
     * @return String contents
     */
    public static String getXMLFromFile( final String fileName )
    {
        
        
        File file = new File( fileName );
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;

        try
        {
            reader = new BufferedReader( new FileReader( file ) );
            String text = null;

            // repeat until all lines is read
            while ( ( text = reader.readLine() ) != null )
            {
                contents.append( text ).append( System.getProperty( "line.separator" ) );
            }

            return contents.toString();

        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if ( reader != null )
                {
                    reader.close();
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }

        return null;
    }

}
