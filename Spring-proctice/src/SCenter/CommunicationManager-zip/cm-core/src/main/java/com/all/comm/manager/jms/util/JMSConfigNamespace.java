/**
 *
 */
package com.AL.comm.manager.jms.util;

 
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.AL.property.management.cache.PropertyManager;

/**
 * @author ebthomas
 * 
 */
public class JMSConfigNamespace {
	private static Logger logger = Logger.getLogger(JMSConfigNamespace.class);
	private static String repositoryPath = System.getProperty("repository.home");
	private static final String QUEUE_LOGICAL_NAME_PREFIX = "endpoint.";
	private static final String INVALID_MISSING_VALUE = "INVALID.MISSING.VALUE";

	private String defaultMessageQueueName = "FOO.BAR";
	private String messageBrokerUrl = "tcp://localhost:61616";
	private String communicationType = "JMS";
	private String vendor = "ActiveMQ";
	private String jndiName = "";
	private String protocol = "tcp";
	private String query = "";
	private String transacted = "";
	private String persist = "";
	private int timeToLive = 1000;
	private String username = "username";
	private String password = "password";
	private int redeliveryMax = 5;
	private String jndiConnectionFactoryName;
	private Map<String, String> queueMapping = new HashMap<String, String>();

	public String getRepositoryPath() {
		if (repositoryPath == null) {
			repositoryPath = "";
		}

		return repositoryPath;
	}
	
	public Properties loadProperties(final String namespace)
	{
		if (namespace == null)
		{
			throw new IllegalArgumentException("null.invalid.namespace.cannot.load.properties");
		}
		
	    /*StringBuilder sb = new StringBuilder(getRepositoryPath());
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
    	}*/
    	
		logger.info("loading properties from S3 ="+namespace);
		Properties metadata = loadS3PropertiesFile(namespace);
        if(metadata == null){
        	throw new IllegalArgumentException("unable.to.load.properties.file.from.classloader.and.parent.classloader:"+namespace);
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
            
             
            if (key.trim().startsWith(QUEUE_LOGICAL_NAME_PREFIX))
               queueMapping.put(key.trim(), value.trim());
            
            logger.info(key + " -- " + value);
        }
        logger.info("*************************************************");
        logger.info("*************************************************");
        
        return metadata;
	}
	
	public Properties loadS3PropertiesFile(String appNamespace)
	{
		Properties metadata = null;
		try
        {
        	logger.info( "attempting to load:"+ appNamespace);
        	
        	metadata = PropertyManager.getPropertiesFile(appNamespace);
            
        	logger.info( "successfully loaded:"+ appNamespace);
            return metadata;
        }
        catch (  Exception e1 )
        {
        	e1.printStackTrace();
        	logger.info( "error.loading from S3 = "+ appNamespace); 
        }  
        
       return metadata;
	}
	
	public boolean loadPropertiesFile(Properties metadata , ClassLoader clazzLoader, String filename )
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
	
	
	 

	private String loadCleanProperty(final Properties metadata, final String key)
	{
		String value = metadata.getProperty(key);
		
		if ((value != null) && (value.length() > 0))
		{
			return value.trim();
		}
		
		return INVALID_MISSING_VALUE;
		
	}
	public JMSConfigNamespace(final String namespace) {

	    Properties metadata = loadProperties( namespace);
		
		defaultMessageQueueName = loadCleanProperty(metadata,"messageQueueName");
		String host = loadCleanProperty(metadata,"host");
		String port = loadCleanProperty(metadata,"port");
		messageBrokerUrl = loadCleanProperty(metadata,"url");
		protocol = loadCleanProperty(metadata,"protocol");
		query = loadCleanProperty(metadata,"query");
		
		if (INVALID_MISSING_VALUE.equals(protocol)) {
			protocol = "tcp";
		}  
		
		//If url is not present.  default to host and port.
		if (INVALID_MISSING_VALUE.equals(messageBrokerUrl)) {
			messageBrokerUrl = protocol+"://" + host + ":" + port;
		}  
		
		//If a query is present
		if (!INVALID_MISSING_VALUE.equals(query)) {
			messageBrokerUrl = messageBrokerUrl+"?"+query;
		} 
		
		
		communicationType = loadCleanProperty(metadata,"communicationType");
		vendor = loadCleanProperty(metadata,"vendor");
		jndiName = loadCleanProperty(metadata,"jndiName");
		transacted = loadCleanProperty(metadata,"transacted");
		persist = loadCleanProperty(metadata,"persist");

		String timeToLiveAsString = loadCleanProperty(metadata,"timeToLive");

		if (timeToLiveAsString != null) {
			try {
				timeToLive = Integer.valueOf(timeToLiveAsString.trim());
			} catch (Exception e) {
				logger.error("invalid time to live value in namespace:"
						+ namespace, e);
			}
		}

		username = loadCleanProperty(metadata,"username");
		password = loadCleanProperty(metadata,"password");
		
		try 
		{
		redeliveryMax = Integer.valueOf(loadCleanProperty(metadata,"redeliveryMax").trim());
		}catch(NumberFormatException nfe)
		{
			logger.error("unable.to.convert.redeliveryMax.string.to.integer."+nfe.getMessage());
		}
		
		jndiConnectionFactoryName = loadCleanProperty(metadata,"jndiConnectionFactoryName");
		
		
	}
	
	public String getQueue(final String logicalName)
	{
		if (queueMapping != null)
		{
			return queueMapping.get(logicalName);
		} else
		{
			return defaultMessageQueueName;
		}
	}

	public String getDefaultMessageQueueName() {
		return defaultMessageQueueName;
	}

	public void setDefaultMessageQueueName(String defaultMessageQueueName) {
		this.defaultMessageQueueName = defaultMessageQueueName;
	}

	public String getMessageBrokerUrl() {
		return messageBrokerUrl;
	}

	public void setMessageBrokerUrl(String messageBrokerUrl) {
		this.messageBrokerUrl = messageBrokerUrl;
	}

	public String getCommunicationType() {
		return communicationType;
	}

	public void setCommunicationType(String communicationType) {
		this.communicationType = communicationType;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public String getTransacted() {
		return transacted;
	}

	public void setTransacted(String transacted) {
		this.transacted = transacted;
	}

	public String getPersist() {
		return persist;
	}

	public void setPersist(String persist) {
		this.persist = persist;
	}

	public int getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(int timeToLive) {
		this.timeToLive = timeToLive;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRedeliveryMax() {
		return redeliveryMax;
	}

	public void setRedeliveryMax(int redeliveryMax) {
		this.redeliveryMax = redeliveryMax;
	}

	public String getJndiConnectionFactoryName() {
		return jndiConnectionFactoryName;
	}

	public void setJndiConnectionFactoryName(String jndiConnectionFactoryName) {
		this.jndiConnectionFactoryName = jndiConnectionFactoryName;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

}
