package com.AL.ui.util;

import java.util.Properties;

import org.apache.log4j.Logger;
import com.AL.property.management.cache.PropertyManager;

public class ConfigProperties {
	private static final Logger logger = Logger.getLogger(ConfigProperties.class);
	public static String DIALOGECACHETIME = null;
	public static String PROVIDER_LOGO_LOCATION = null;
	public static Properties props = null;

	static {
		props = PropertyManager.getPropertiesFile("CKO-static/static-cko");

		if(!props.isEmpty()) {
			DIALOGECACHETIME = props.getProperty("dialogeCacheTime");
			PROVIDER_LOGO_LOCATION = props.getProperty("provider_logo_location");
		}else{
			logger.error("Unable to read properties file from the s3 bucket from path CKO-static/static-cko");
		}
	}
}
