package com.A.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public enum CalendarUtil {

	INSTANCE;
	
	public Calendar getGMTCalendar() {
		Calendar c = Calendar.getInstance();
	   
	    TimeZone z = c.getTimeZone();
	    int offset = z.getRawOffset();
	    if(z.inDaylightTime(new Date())){
	        offset = offset + z.getDSTSavings();
	    }
	    int offsetHrs = offset / 1000 / 60 / 60;
	    int offsetMins = offset / 1000 / 60 % 60;

	    
	    c.add(Calendar.HOUR_OF_DAY, (-offsetHrs));
	    c.add(Calendar.MINUTE, (-offsetMins));

	    return c;
	}
}
