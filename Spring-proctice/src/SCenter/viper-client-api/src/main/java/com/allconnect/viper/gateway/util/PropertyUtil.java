package com.A.V.gateway.util;

import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

public enum PropertyUtil {

	INSTANCE;
	
	
 
	private static final Logger logger = Logger.getLogger(PropertyUtil.class);
	
	public static Properties load(final String namespace)
	{
		
		if (namespace == null)
		{
			throw new IllegalArgumentException("null.invalid.namespace.cannot.load.properties");
		}
		
	    StringBuilder sb = new StringBuilder();
        sb.append(namespace).append(".properties");
        
        Properties metadata=new Properties();  
        
        ClassLoader clazzLoader = Thread.currentThread().getContextClassLoader();
        
    	boolean isLoaded = loadPropertiesFile(  metadata , clazzLoader, sb.toString() );
    	
    	if (!isLoaded)
    	{
    		isLoaded = loadPropertiesFile(  metadata , clazzLoader.getParent(), sb.toString() );
    		
    		if (!isLoaded)
    		{
    			throw new IllegalArgumentException("unable.to.load.properties.file.from.classloader.and.parent.classloader:"+sb.toString());
    		}
    	}
    	
        
        
        Enumeration<?> en = metadata.propertyNames();

        logger.info("*************************************************");
        logger.info("**** properties from "+namespace+".properties ***");
        logger.info("*************************************************");
        while (en.hasMoreElements()) {
            String key = ((String) en.nextElement());
            String value = metadata.getProperty(key);
            
            if ((key == null) || (value == null) || (key.length() == 0) || (value.length() == 0))
            {
            	logger.info("incomplete property key:"+key+" or value "+value+" not processing");
            	continue;
            }
             
            logger.info(key + " -- " + value);
        }
        logger.info("*************************************************");
        logger.info("*************************************************");
        
        return metadata;
	}
	
	
	public static boolean loadPropertiesFile(Properties metadata , ClassLoader clazzLoader, String filename )
	{
		try
        {
        	logger.info( "attempting to load:"+ filename);
            metadata.load(clazzLoader.getResource(filename).openStream());
            logger.info( "successfully loaded:"+ filename);
            return Boolean.TRUE;
        }
        catch (  Exception e1 )
        {
        	logger.info( "error.loading:"+ filename); 
        	
        }  
        
       return Boolean.FALSE;
	}
	
	
	 

	 
}
