/**
 *
 */
package com.AL.comm.manager.jms.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class StringUtil
{

    public static void main( String[] arg )
    {

        Formatter fmt = new Formatter();

        fmt.format( "No group separators: %d\n", 123456789 );
        fmt.format( "With group separators: %,d\n\n", 123456789 );
        System.out.println( fmt );

        System.out.println( StringUtil.randomAlphaNumeric( 6 ) );

    }

    public static String replaceLast( String data, String delimiter, String replaceWith )
    {
        StringBuilder b = new StringBuilder( data );
        b.replace( data.lastIndexOf( delimiter ), data.lastIndexOf( delimiter ) + delimiter.length(), replaceWith );
        return b.toString();

    }

    /**
     * Tokenize a String using the specified delimiter and return an array. This is a wrapper around
     * {@link java.util.StringTokenizer StringTokenizer}.
     * 
     * @param target
     *            The String to be tokenized.
     * @param delim
     *            The delimiter character(s).
     * @return The tokens.
     */
    public static String[] tokenize( String target, String delim )
    {
        if ( target == null )
        {
            return null;
        }
        else if ( isEmpty( delim ) )
        {
            return new String[] { target };
        }
        else
        {
            StringTokenizer st = new StringTokenizer( target, delim );
            String[] tokens = new String[st.countTokens()];
            for ( int i = 0; i < tokens.length; i++ )
            {
                tokens[i] = st.nextToken().trim();
            }
            return tokens;
        }
    }

    /**
     * @return true if s == null or s.equals("")
     */
    public static boolean isEmpty( String s )
    {
        return makeSafe( s ).length() == 0;
    }

    /**
     * Helper function for making null strings safe for comparisons, etc.
     * 
     * @return (s == null) ? "" : s;
     */
    public static String makeSafe( String s )
    {
        return ( s == null ) ? "" : s;
    }

    public static String splitAndGet( String input, int index, String delimiter )
    {

        if ( ( input == null ) || ( input.length() == 0 ) )
        {
            return null;
        }

        String[] stringValues = StringUtil.tokenize( input, delimiter );

        if ( index >= stringValues.length )
            index = stringValues.length - 1;

        String returnValue = stringValues[index];

        return returnValue;
    }

    public static ArrayList<String> splitString( String input, String delimiter )
    {
        ArrayList<String> output = new ArrayList<String>(); // only create the one target container list, not an additional
                                                            // intermediate array

        if ( ( input == null ) || ( input.length() == 0 ) )
        {
            return output;
        }

        String[] stringValues = StringUtil.tokenize( input, delimiter );

        for ( int i = 0; i < stringValues.length; i++ )
        {
            if ( ( stringValues[i] != null ) && ( stringValues[i].length() > 0 ) )
                output.add( stringValues[i].trim() );
        }

        return output;
    }

    public static String randomNumeric( Integer length )
    {
        Random randomGenerator = new Random();
        StringBuilder sb = new StringBuilder();

        while (sb.toString().length() <= length)
        {
            int randomInt = randomGenerator.nextInt( 100 );
            sb.append( randomInt );
        }

        return sb.toString().replace( '.', '0' ).substring( 0, length );

    }

    public static String randomAlpha( Integer length )
    {
        StringBuilder sb = new StringBuilder();

        while (sb.toString().length() <= length)
        {
            char p = (char) ( (int) 26 * Math.random() + (int) 'a' );

            sb.append( p );
        }

        return sb.toString().toUpperCase().substring( 0, length );

    }

    public static String randomAlphaNumeric( Integer length )
    {
        StringBuilder sb = new StringBuilder();
        String inplace = "";
        String numeric = "";
        String alpha = "";

        while (sb.toString().length() <= length)
        {
            numeric = StringUtil.randomNumeric( 4 );
            alpha = StringUtil.randomAlpha( 1 );
            sb.append( alpha + numeric + inplace.toUpperCase() );
        }

        return sb.toString().substring( 0, length );
    }

    public static String camelCase( String string )
    {

        String result = "";

        for ( int i = 0; i < string.length(); i++ )
        {
            String next = string.substring( i, i + 1 );
            if ( i == 0 )
            {
                result += next.toUpperCase();
            }
            else
            {
                result += next.toLowerCase();
            }
        }

        return result;
    }

    public static List<String> readListFromFile( String aFileName )
    {
        // StringBuffer sb = new StringBuffer();
        String line = null;
        List<String> list = new LinkedList<String>();

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

}
