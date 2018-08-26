package com.A.util.log4j;
 

/**
 * @author ebthomas
 *
 */
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;


public class PatternLayoutEx extends PatternLayout {

	private boolean ignoresThrowable;

	public void setIgnoresThrowable( boolean ignoresThrowable ) {
		this.ignoresThrowable = ignoresThrowable;
	}


	public boolean ignoresThrowable() {
		return ignoresThrowable;
	}
	private static final String MASK = "...................";
	private static final Pattern CCPATTERN = Pattern.compile("creditCardNumber>(.*?)creditCardNumber>");
	Pattern SSNPATTERN = Pattern.compile("ssn>(.*?)ssn>");

	@Override
	public String format(LoggingEvent event) {
		if (event.getMessage() instanceof String) {
			if(SSNPATTERN.matcher(event.getRenderedMessage()).find()){
				return setMaskedEvent(event,SSNPATTERN.matcher(event.getRenderedMessage()).replaceAll(MASK));

			}else if(CCPATTERN.matcher(event.getRenderedMessage()).find()){
				return setMaskedEvent(event,CCPATTERN.matcher(event.getRenderedMessage()).replaceAll(MASK));
			}
		}
		return super.format(event);
	}

	private String setMaskedEvent(LoggingEvent event,String maskText){
		Throwable throwable = event.getThrowableInformation() != null ? 
				event.getThrowableInformation().getThrowable() : null;
				LoggingEvent maskedEvent = new LoggingEvent(event.fqnOfCategoryClass,
						Logger.getLogger(event.getLoggerName()), event.timeStamp, 
						event.getLevel(), maskText, throwable);
				return super.format(maskedEvent);
	}

}
