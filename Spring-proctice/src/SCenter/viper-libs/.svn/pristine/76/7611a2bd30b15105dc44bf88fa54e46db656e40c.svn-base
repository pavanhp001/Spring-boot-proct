package com.A.util.convert;

import org.apache.log4j.Logger;


public class SafeConvert {
	
	private static final Logger logger = Logger.getLogger(SafeConvert.class);

 
	public static int convertInteger(final String value) {

		if (value != null) {

			try {
				 
					Integer result = (Integer.parseInt(value.trim()));

					return result;
				 
			} catch (NumberFormatException nfe) {
				//logger.error( MessageFormatter.format("NumberFormatException: %s",nfe.getMessage()));
			}
		}

		throw new IllegalArgumentException("conversion not supported:" + value);
	}
	
	
	public static Long convertLong(final String value) {

		if (value != null) {

			try {
				 
					Long result = (Long.parseLong(value.trim()));

					return result;
				 
			} catch (NumberFormatException nfe) {
				//logger.error( MessageFormatter.format("NumberFormatException: %s",nfe.getMessage()));
			}
		}

		throw new IllegalArgumentException("conversion not supported:" + value);
	}
	
	
	

}
