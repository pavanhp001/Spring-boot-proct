package com.AL.ui.validation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import org.springframework.format.datetime.DateFormatter;

import com.AL.ui.exception.InvalidDataException;
import com.AL.ui.exception.InvalidFormatException;
public class DateValidator {

	protected Calendar currentDate;
    protected Calendar mLowerBoundary = null;
    protected Calendar mUpperBoundary = null;
    private Set<Calendar> mIncludedDays;
    private Set<Calendar> mExcludedDays;
    
    public DateValidator() {
    }

    public DateValidator(Calendar current, Calendar lowerBoundary, Calendar upperBoundary) {
       this( current, lowerBoundary, upperBoundary, null, null );
    }
    public DateValidator(Calendar current, Calendar lowerBoundary, Calendar upperBoundary, Set <Calendar> includedDays, Set <Calendar> excludedDays ) {
        currentDate = current;
        mLowerBoundary = lowerBoundary;
        mUpperBoundary = upperBoundary;
        mIncludedDays = includedDays;
        mExcludedDays = excludedDays;
    }

    
    // use cases
    // 1. Is this an actual date
    // 2. Within a range
    public boolean validate(Object obj) throws InvalidDataException
    {
		if (obj == null)
			throw new InvalidDataException("Date not entered.");

		if (obj instanceof String) {
			String date = (String)obj;

			if (date.length() == 0)
				throw new InvalidDataException("Date not valid.");

			// test for obfuscated dates
			if (date.trim().startsWith("*"))
				return true;

            // check if it's a real date
           /* Calendar cal = getCalendar(date);
            if (cal == null)
                throw new InvalidDataException("Date not valid.");*/

/*
			// check that year is 4 digets long
			StringBuffer tmp = new StringBuffer(date);
			String tmpstr = tmp.toString();
			String dtmp = tmp.reverse().toString();
			if (tmpstr.indexOf('/') != 4 && dtmp.indexOf('/') != 4)
				throw new InvalidDataException("Year must have 4 digit format.");
*/
            // are there boundries, if so does the date fall b/w them
            /*if ( mExcludedDays != null && mExcludedDays.contains( cal ) ) {
                throw new InvalidDataException("Date is excluded." + CommonHelper.renderFullDate( cal ) );
            } else if ( mIncludedDays != null && !mIncludedDays.contains( cal ) ) {
                throw new InvalidDataException("Date is not included." + CommonHelper.renderFullDate( cal ) );
            } else if (mLowerBoundary != null && mUpperBoundary != null) {
                boolean lowb = mLowerBoundary.before(cal);
                boolean upb = mUpperBoundary.after(cal);
                if (!lowb || !upb) {
                    throw new InvalidDataException("Date out of range.");
                }
            }*/
        }

        return true;
    }

    public void setBoundaries( Calendar lowerBoundary, Calendar upperBoundary ) {
        mLowerBoundary = lowerBoundary;
        mUpperBoundary = upperBoundary;
    }

  /*  protected Calendar getCalendar(String date) {
        try {
//            DateFormatter format = new DateFormatter(date);
        	DateFormat format = new SimpleDateFormat();
//            format.toFullString();
            return format.getCalendar();
        } catch (Exception e) {}

        return null;
    }*/

    protected Calendar getNormalizedCalendar() {

        Calendar temp = Calendar.getInstance();
        return new GregorianCalendar( temp.get( Calendar.YEAR ), temp.get( Calendar.MONTH ), temp.get( Calendar.DATE ), 0, 0, 0 );
    }

    public Object normalizeFormat(Object obj) throws InvalidDataException, InvalidFormatException
    {
        if (obj instanceof String) {
            String str = ((String)obj);
//            if(!(str.indexOf("-") <= 0)){
            	try {
            		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            		Date sys = format.parse(str);
            		String d1 = format.format(sys);

            		return format.format(d1);

            	} catch (Exception e) {
            		throw new InvalidDataException("Date string could not be formatted: " + str);
            	}
//            }
            /*else {
            	throw new InvalidFormatException("Date string could not be formatted: " + str);
            }*/
        } else {
            throw new InvalidDataException("Expected date string, but found: " + obj);
        }

        // return not reachable
    }
}
