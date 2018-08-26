/**
 *
 */
package com.A.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.xmlbeans.XmlCalendar;


/**
 * @author ebthomas
 *
 */
public class DateUtil
{

	public static final String DATE_FORMAT = "MM/dd/yyyy";
    
	public static Calendar getCalendar( XmlCalendar xmlCalendar )
    {

//         TimeZone timeZone = xmlCalendar.getTimeZone( xmlCalendar.getTimezone() );
//
//        Calendar calendar = Calendar.getInstance( timeZone );
        Calendar calendar = Calendar.getInstance(   );
        calendar.set( Calendar.YEAR, xmlCalendar.get( Calendar.YEAR ) );
        calendar.set( Calendar.MONTH, xmlCalendar.get( Calendar.MONTH ) );
        calendar.set( Calendar.DATE, xmlCalendar.get( Calendar.DATE ) );
        calendar.set( Calendar.HOUR_OF_DAY, xmlCalendar.get( Calendar.HOUR ) );
        calendar.set( Calendar.MINUTE, xmlCalendar.get( Calendar.MINUTE ) );
        calendar.set( Calendar.SECOND, xmlCalendar.get( Calendar.SECOND ) );
        return calendar;
    }

    public static final Date startOfDay(final Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime( date );
        cal.set( Calendar.HOUR, 0 );
        cal.set( Calendar.MINUTE, 0 );
        cal.set( Calendar.SECOND, 0 );
        cal.set( Calendar.MILLISECOND, 0 );

        return cal.getTime();
    }

    public static final Date endOfDay(final Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime( date );
        cal.set( Calendar.HOUR, 23 );
        cal.set( Calendar.MINUTE, 59 );
        cal.set( Calendar.SECOND, 59 );
        cal.set( Calendar.MILLISECOND, 0 );
        return cal.getTime();
    }

    public static final String getCurrentDate() {
    	String format = "yyyy-MM-dd";
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
    	String dateAsString = simpleDateFormat.format(new Date());
    	return dateAsString;
    }

    public static void main(String[] args) {
		String dt = DateUtil.getCurrentDate();
		//System.out.println(dt);
	}
    
    public static XMLGregorianCalendar getXMLGregorianCalendar(Calendar cal) {
		GregorianCalendar gcal = (GregorianCalendar) GregorianCalendar.getInstance();
		XMLGregorianCalendar xgc = null;	    
		gcal.setTime( cal.getTime() );
		gcal.setTimeZone(cal.getTimeZone());
		try {
			xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException("Unable to instantiate an XML datatype factory", e);
		}	
		return xgc;
	}
    
    public static Calendar convertStringToDate(String dateStr) {
		Calendar cal = Calendar.getInstance();
		try {
			DateFormat df =  new SimpleDateFormat(DATE_FORMAT);
			cal.setTime( df.parse( dateStr ) );
		} catch ( ParseException e ) {
			throw new RuntimeException("Incorrect date format in OSN scheduling information.", e);
		}
		return ( cal );	
	}
    
    public static String convertDateToString(Calendar cal) {
    	cal.set(Calendar.HOUR, 0);
        cal.set( Calendar.MINUTE, 0);
        cal.set( Calendar.SECOND, 0);
        cal.set( Calendar.MILLISECOND, 0);
    	DateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String dob = sdf.format(cal.getTime());
		return dob;	
	}
}
