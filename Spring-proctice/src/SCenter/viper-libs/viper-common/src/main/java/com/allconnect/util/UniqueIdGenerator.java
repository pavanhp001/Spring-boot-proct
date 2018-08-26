package com.A.util;

import org.apache.log4j.Logger;

public final class UniqueIdGenerator {
	private static final Logger logger = Logger.getLogger(UniqueIdGenerator.class);
	private static long currentTime = System.currentTimeMillis();
	
	public static synchronized String getUniqueId(){
		String s = "AC-" + currentTime + 1;
		logger.info("New Id : " + s);
		return s;
	}
}
