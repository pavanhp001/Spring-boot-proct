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

import org.apache.commons.lang.StringUtils;

public class DateUtil {
	
	private static DatatypeFactory df = null;
	static {
		try {
			df = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException dce) {
			throw new IllegalStateException(
					"Exception while obtaining DatatypeFactory instance", dce);
		}
	}

	public static XMLGregorianCalendar asXMLGregorianCalendar(
			java.util.Date date) {
		if (date == null) {
			return null;
		} else {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTimeInMillis(date.getTime());
			return df.newXMLGregorianCalendar(gc);
		}
	}
	

	public static Date toDate(XMLGregorianCalendar cal) {
		return cal.toGregorianCalendar().getTime();
	}

	public static XMLGregorianCalendar getCurrentXMLDate() {

		Date date = new Date();
		return XMLGregorianCalendarConverter.asXMLGregorianCalendar(date);
	}

	public static XMLGregorianCalendar setAMPM(XMLGregorianCalendar xmlcal,
			boolean isPM) {

		Date date = xmlcal.toGregorianCalendar().getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		if (isPM) {
 

			cal.set(Calendar.AM_PM, Calendar.PM);

		} else { 

			cal.set(Calendar.AM_PM, Calendar.AM);

		}

		return XMLGregorianCalendarConverter.asXMLGregorianCalendar(cal
				.getTime());
	}

	public static XMLGregorianCalendar fromCalendarToXML(Calendar cal) {

		return XMLGregorianCalendarConverter.asXMLGregorianCalendar(cal
				.getTime());
	}

	public static XMLGregorianCalendar fromDateToXML(Date date) {

		return XMLGregorianCalendarConverter.asXMLGregorianCalendar(date);
	}

	public static XMLGregorianCalendar fromStringToXMLDateTime(String dateString) {
		XMLGregorianCalendar xmlCal = fromDateToXML(fromString(dateString,
				"MM/dd/yyyy hh:mm a"));

		boolean containsPM = (dateString.toUpperCase().indexOf("PM") != -1);
		return setAMPM(xmlCal, containsPM);

	}

	public static Date fromStringToDateTime(String dateString) {
		return fromString(dateString, "MM/dd/yyyy hh:mm a");
	}

	public static XMLGregorianCalendar fromStringToXMLDate(String dateString) {
		return fromDateToXML(fromString(dateString, "MM/dd/yyyy"));
	}

	public static XMLGregorianCalendar fromStringToXMLTime(String dateString) {
		return fromDateToXML(fromString(dateString, "hh:mm a"));
	}

	public static Date fromStringToDate(String dateString) {
		return fromString(dateString, "MM/dd/yyyy");
	}

	public static Date fromStringToTime(String dateString) {
		return fromString(dateString, "hh:mm a");
	}
	
	public static String fromDate(Date date, String formatTo) {
		
		if ((date == null) || (formatTo == null)) {
			return null;
		}
		
		try {

			DateFormat formatter = new SimpleDateFormat(formatTo);
			
			String s = formatter.format(date);
			
			return s;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public static Date fromString(String dateString, String formatTo) {
		
		if ((dateString == null) || (formatTo == null)) {
			return null;
		}
		
		try {

			DateFormat formatter;
			Date date;
			formatter = new SimpleDateFormat(formatTo);
			date = (Date) formatter.parse(dateString);
			return date;

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;

	}

	
	public static String toDateString(Object obj) {
		
		if (obj == null) {
			return "";
		}
		 
		String parsed = "";
		if (obj instanceof XMLGregorianCalendar) {
			Date date = ((XMLGregorianCalendar)obj).toGregorianCalendar().getTime();

			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

			parsed = format.format(date);
		}
		return parsed;
	 

	}
	
	
	public static String toDateString(XMLGregorianCalendar cal) {
		if (cal == null) {
			return "";
		}
		Date date = cal.toGregorianCalendar().getTime();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		String parsed = format.format(date);
		return parsed;
	}

	public static String toTimeString(Object obj) {

		String parsed = "";
		if (obj instanceof XMLGregorianCalendar) {
			Date date = ((XMLGregorianCalendar)obj).toGregorianCalendar().getTime();

			SimpleDateFormat format = new SimpleDateFormat("hh:mm a");

			parsed = format.format(date);
		}
		return parsed;
	}

	public static String toTimeString(XMLGregorianCalendar cal) {
		
		if (cal == null) {
			return "";
		}
		Date date = cal.toGregorianCalendar().getTime();

		SimpleDateFormat format = new SimpleDateFormat("hh:mm a");

		String parsed = format.format(date);

		return parsed;
	}
	
	
	public static XMLGregorianCalendar asXMLGregorianCalendar(String strDate, String pattern) {
		XMLGregorianCalendar xmlgc = null;
		if(!StringUtils.isEmpty(strDate) && !StringUtils.isEmpty(pattern)) {
			SimpleDateFormat fmt = new SimpleDateFormat(pattern);
			try {
				Date date = fmt.parse(strDate);
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTimeInMillis(date.getTime());
				xmlgc = df.newXMLGregorianCalendar(gc);
			} catch(ParseException e) {
				e.printStackTrace();
			}
		}
		return xmlgc;
	}
	
	/**
	 * This is to just to return Date in XMLGregorianCalendar i.e. in yyyy-MM-dd format
	 * 
	 * @param String strDate
	 * @return XMLGregorianCalendar
	 */
	public static XMLGregorianCalendar asXMLGregorianCalendarDate(String strDate, String pattern) {
		XMLGregorianCalendar xmlgc = null;
		if(!StringUtils.isEmpty(strDate) && !StringUtils.isEmpty(pattern)) {
			SimpleDateFormat fmt = new SimpleDateFormat(pattern);
			try {
				Date date = fmt.parse(strDate);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				xmlgc = DatatypeFactory.newInstance().newXMLGregorianCalendar();
				xmlgc.setYear(cal.get(Calendar.YEAR));
				xmlgc.setDay(cal.get(Calendar.DAY_OF_MONTH));
				xmlgc.setMonth(cal.get(Calendar.MONTH)+1);

			} catch(ParseException e) {
				e.printStackTrace();
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
		}
		return xmlgc;
	}
	
	/**
	 * This is to just to return Date in XMLGregorianCalendar i.e. in yyyy-MM-dd format
	 * 
	 * @param date
	 * @return
	 */
	public static XMLGregorianCalendar asXMLGregorianCalendarDate(java.util.Date date) {
		if (date == null) {
			return null;
		} else {
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				
				XMLGregorianCalendar xmlgc = DatatypeFactory.newInstance().newXMLGregorianCalendar();
				xmlgc.setYear(cal.get(Calendar.YEAR));
				xmlgc.setDay(cal.get(Calendar.DAY_OF_MONTH));
				xmlgc.setMonth(cal.get(Calendar.MONTH)+1);
				return xmlgc;
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
