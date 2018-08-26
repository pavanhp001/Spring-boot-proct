package com.A.ui.service.config;
 

 

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.A.ui.domain.SystemConfig;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

 

/**
 * Utility class to retrieve system properties at runtime.
 * Properties are re-synchronized on a periodic basis and stored
 * in a static variable accessible only through a getter for now.
 * 
 * @author klyons
 *
 */
public class ConfigRepo
{
	private static final Logger logger = Logger.getLogger(ConfigRepo.class);
    public static HashMap<String, SystemConfig> systemProperties = 
                                                        new HashMap<String, SystemConfig>();

    public static SystemConfig getSystemProperty( final String name )
    {
        return systemProperties.get( name );
    }

    public static void setSystemConfig( HashMap<String, SystemConfig> propsMap )
    {
        // Clear the map cache and then store the new map values in it.
        systemProperties.clear();        
        systemProperties = propsMap;
    }
    
    public static void setSystemPropertyByName( final String name, final SystemConfig systemConfig  )
    {
        // Clear the map cache and then store the new map values in it.
    	systemProperties.put(name, systemConfig);
    }
    
    /**
     * Utility method to retrieve property value based on context.propertyName
     * @param propertyName
     * @return
     */
    public static int getInt(String propName) {
    	logger.debug("Retrieving SystemConfig for " + propName);
    	SystemConfig sysProp = ConfigRepo.getSystemProperty(propName);
    	if(sysProp != null) {
    		logger.debug(propName + " : " + sysProp.getValue());
    		return Integer.parseInt(sysProp.getValue());
    	}
		return 0;
	}
    
    public static boolean getBoolean(String propName) {
    	logger.debug("Retrieving SystemConfig for " + propName);
    	SystemConfig sysProp = ConfigRepo.getSystemProperty(propName);
    	if(sysProp != null) {
    		logger.debug(propName + " : " + sysProp.getValue());
    		return Boolean.parseBoolean(sysProp.getValue());
    	}
		return Boolean.FALSE;
	}
    
    /**
     * Utility method to retrieve property value based on context.propertyName
     * @param propertyName
     * @return
     */
    public static String getString(String propName) {
    	logger.debug("Retrieving SystemConfig for " + propName);
    	SystemConfig sysProp = ConfigRepo.getSystemProperty(propName);
    	if(sysProp != null) {
    		logger.debug(propName + " : " + sysProp.getValue());
    		return sysProp.getValue();
    	}
		return "";
	}
    
}
