package com.A.Vdao.util;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.A.V.beans.entity.SystemProperties;

/**
 * Utility class to retrieve system properties at runtime.
 * Properties are re-synchronized on a periodic basis and stored
 * in a static variable accessible only through a getter for now.
 *
 * @author klyons
 *
 */
public class SystemPropertiesRepo
{
	private static final Logger logger = Logger.getLogger(SystemPropertiesRepo.class);
    private static HashMap<String, SystemProperties> systemProperties =
                                                        new HashMap<String, SystemProperties>();
    private static final int DEFAULT_TIMEOUT = 10000;

    public static SystemProperties getSystemProperty( final String name )
    {
        return systemProperties.get( name );
    }

    public static void setSystemProperties( HashMap<String, SystemProperties> propsMap )
    {
        // Clear the map cache and then store the new map values in it.
        systemProperties.clear();
        systemProperties = propsMap;
    }

    /**
     * Utility method to retrieve property value based on context.propertyName
     * @param propertyName
     * @return
     */
    public static int getSystemPropertyValue(String propName) {
    	logger.info("Retrieving SystemProperties for " + propName);
    	SystemProperties sysProp = SystemPropertiesRepo.getSystemProperty(propName);
    	if(sysProp != null) {
    		logger.info(propName + " : " + sysProp.getValue());
    		return Integer.parseInt(sysProp.getValue());
    	}
		return DEFAULT_TIMEOUT;
	}

    /**
     * Utility method to retrieve property value based on context.propertyName
     * @param propertyName
     * @return
     */
    public static int getSystemPropertyValue(String propName, int defaultVal) {
    	logger.info("Retrieving SystemProperties for " + propName);
    	SystemProperties sysProp = SystemPropertiesRepo.getSystemProperty(propName);
    	if(sysProp != null) {
    		logger.info(propName + " : " + sysProp.getValue());
    		return Integer.parseInt(sysProp.getValue());
    	}
		return defaultVal;
	}

    /**
     * Utility method to retrieve property value based on context.propertyName
     * @param propertyName
     * @return
     */
    public static boolean getSystemPropertyValueAsBoolean(String propName) {
    	logger.info("Retrieving SystemProperties for " + propName);
    	SystemProperties sysProp = SystemPropertiesRepo.getSystemProperty(propName);
    	if(sysProp != null) {
    		logger.info(propName + " : " + sysProp.getValue());
    		return Boolean.parseBoolean(sysProp.getValue());
    	}
		return false;
	}
}
