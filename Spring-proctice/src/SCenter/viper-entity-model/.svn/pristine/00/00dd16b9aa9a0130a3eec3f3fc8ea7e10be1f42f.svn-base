/**
 * 
 */
package com.A.V.utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author ebaugh
 * 
 */

public final class ReferenceData
{

    /**
     * Default Constructor.
     */

    private ReferenceData()
    {
        // Do nothing right now.
    }

    /**
     * Method for retrieving Integer reference data.
     * 
     * @param tableName
     *            name of table containing Integer reference data.
     * @return list of reference data.
     */
    public static List< ? > getIntegerReferenceData( final String tableName )
    {
        List< ? > results = getReferenceData( tableName );
        if ( results == null )
        {
            return new ArrayList<Integer>();
        }

        return results;
    }

    /**
     * Method for retrieving Float reference data.
     * 
     * @param tableName
     *            name of table containing Float reference data.
     * @return list of reference data.
     */
    public static List< ? > getFloatReferenceData( final String tableName )
    {
        List< ? > results = getReferenceData( tableName );
        if ( results == null )
        {
            return new ArrayList<Float>();
        }

        return results;
    }

    /**
     * Method for retrieving String reference data.
     * 
     * @param tableName
     *            name of table containing string reference data.
     * @return list of reference data.
     */
    public static List< ? > getStringReferenceData( final String tableName )
    {
        List< ? > results = getReferenceData( tableName );
        if ( results == null )
        {
            return new ArrayList<String>();
        }

        return results;
    }

    /**
     * Core method for retrieving data.
     * 
     * @param tableName
     *            name of table containing reference data.
     * @return list of reference data.
     */
    private static synchronized List< ? > getReferenceData( final String tableName )
    {
        Calendar now = Calendar.getInstance();
        String calendarDate;

        calendarDate = "'" + now.get( Calendar.DAY_OF_MONTH ) + "-" + now.get( Calendar.MONTH ) + "-" + now.get( Calendar.YEAR )
                + " " + now.get( Calendar.HOUR_OF_DAY ) + ":" + now.get( Calendar.MINUTE ) + "', 'DD-MM-YYYY HH24:MI'";

        String queryString = "select value from " + tableName + " where inEffect <= to_date( " + calendarDate
                + ") and expiration >= to_date( " + calendarDate + ")";

        List< ? > results;

        try
        {
            SessionFactory sf = null; //(SessionFactory) Component.getInstance( "hibernateSessionFactory" );
            Session session = sf.getCurrentSession();
            SQLQuery query = session.createSQLQuery( queryString );
            results = query.list();
            return results;
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This should be deleted Core method for retrieving data for zipcode.
     * 
     * @param tableName
     *            name of table containing reference data.
     * @param zipCode
     *            value of the zipCode entered.
     * @return String of reference data.
     */
    public static synchronized String getZipData( final String tableName, final String zipCode )
    {
        String zipCode1 = "'" + zipCode + "'";
        String queryString = "select description from " + tableName + " where value =" + zipCode1;

        String result = "InValid ZipCode";
        List< ? > results = null;

        try
        {
            SessionFactory sf = null; //(SessionFactory) Component.getInstance( "hibernateSessionFactory" );
            Session session = sf.getCurrentSession();
            SQLQuery query = session.createSQLQuery( queryString );
            
            results = query.list();
            
            for ( Iterator< ? > iter = results.iterator(); iter.hasNext();) 
            {
                
               result = (String) iter.next();
                
            }           

           
            return result;
        }
        catch ( HibernateException e )
        {
            
            e.printStackTrace();
           

        }

        return null;
    }

}
