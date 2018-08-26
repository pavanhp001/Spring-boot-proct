/**
 *
 */
package com.AL.comm.manager.jms.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ebthomas
 * 
 */
public class FileUtil
{

    private static String OS = null;
    private final static String configFilename = "src\\main\\resources\\config.properties";

    public static boolean createTextFile( String filename, String content )
    {

        try
        {
            BufferedWriter out = new BufferedWriter( new FileWriter( filename ) );
            out.write( content );
            out.close();
        }
        catch ( IOException e )
        {

            e.printStackTrace();
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public static boolean remove( final String filename )
    {
        File file = new File( filename );

        if ( file.exists() )
        {
            boolean success = file.delete();
            System.out.println( "successfully removed manual load file:" + success );
            return success;
        }

        return Boolean.FALSE;
    }

    public static String getOsName()
    {
        if ( OS == null )
        {
            OS = System.getProperty( "os.name" );
        }

        return OS.toLowerCase();
    }

    public static boolean isWindows()
    {

        return ( ( getOsName().indexOf( "nt" ) > -1 ) || ( getOsName().indexOf( "windows 2000" ) > -1 )
                || ( getOsName().indexOf( "windows" ) > -1 ) || ( getOsName().indexOf( "windows xp" ) > -1 ) );

    }

    public static boolean isUnix()
    {

        return !isWindows();
    }

    public static int readIntValue( File aFileName )
    {

        if ( !aFileName.exists() )
            return 0;

        StringBuffer sb = new StringBuffer();
        String line = null;

        try
        {
            FileReader inputFile = new FileReader( aFileName );

            BufferedReader inputBuffer = new BufferedReader( inputFile );

            while (( line = inputBuffer.readLine() ) != null)
                sb.append( line );

            inputBuffer.close();
            inputFile.close();

            return Integer.parseInt( sb.toString().trim() );

        }
        // Catches any error conditions
        catch ( IOException e )
        {
            e.printStackTrace();

            return 0;
        }

    }

    public static List<String> readListFromFile( String aFileName )
    {

        String line = null;
        List<String> list = new LinkedList<String>();

        File file = new File( aFileName );
        if ( !file.exists() )
        {
            return list;
        }

        try
        {
            FileReader inputFile = new FileReader( aFileName );

            BufferedReader inputBuffer = new BufferedReader( inputFile );

            while (( line = inputBuffer.readLine() ) != null)
                if ( ( line != null ) && ( line.trim().length() > 0 ) )
                    list.add( line.trim() );
            // sb.append(line);

            inputBuffer.close();
            inputFile.close();

        }
        catch ( java.io.FileNotFoundException fnfe )
        {
            fnfe.printStackTrace();

        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }

        return list;
    }

    public static List<String> getList( final String fileName, final String filter, int count )
    {

        List<String> filterList = StringUtil.splitString( filter, "|" );

        List<String> list = new ArrayList<String>();
        File file = new File( fileName );

        BufferedReader reader = null;

        int numRead = 0;

        try
        {
            reader = new BufferedReader( new FileReader( file ) );
            String text = null;

            // repeat until all lines is read
            while (( ( text = reader.readLine() ) != null ) && ( numRead < count ))
            {
                if ( filterList.size() > 0 )

                {
                    for ( String filterElement : filterList )

                    {
                        if ( text.trim().startsWith( filterElement ) )
                        {
                            list.add( StringUtil.camelCase( text.trim() ) );
                            numRead++;
                            break;
                        }
                    }
                }
                else
                {
                    list.add( StringUtil.camelCase( text.trim() ) );
                    numRead++;
                }

            }

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

        return list;
    }

    /**
     * @param fileName
     *            name to get FileName
     * @return String contents
     */
    public static String getStringContent( final String fileName )
    {
        File file = new File( fileName );
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;

        try
        {
            reader = new BufferedReader( new FileReader( file ) );
            String text = null;

            // repeat until all lines is read
            while (( text = reader.readLine() ) != null)
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

   
    /**
     * @return
     */
    public static String getPropertiesFileName()
    {
        return configFilename;
    }
}
