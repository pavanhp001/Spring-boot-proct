/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.core.main;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import abc.xyz.pts.bcs.common.jndi.LdapConfigLoader;

public abstract class BaseClassMain implements MainClassInterface{

    private static final String LDAP_CONFIG_LOOKUP_ENV = "cn=env";
    private static final String LDAP_CONFIG_LOOKUP_PERMISSIONS = "cn=permissions";
    public static final Logger LOG = Logger.getLogger(BaseClassMain.class);
    protected static final short ZERO = 0;
    protected static final short ONE = 1;
    protected static final short TWO = 2;
    protected static final String ERROR_FILE = "error.out";
    protected static final String HELP = "HELP";
    protected static final String LINE_BREAK = System.getProperty("line.separator");
    protected static final String USAGE = "Program arguments are: " + LINE_BREAK +
        "LdapContextName=The LDAP Context Name"  + LINE_BREAK +
        "Log4jPath=Full Path of Log4j file" + LINE_BREAK +
        "JndiPath=Full path of 'jndi.properties'" + LINE_BREAK +
        "All arguments are optional and their names are NOT case sensitive." + LINE_BREAK +
        "Arguments are delimter by space and '=' is used as argument/value delimiter";

     @Override
    public boolean initialise(final String [] args) throws Exception{
         boolean canContinue = true;
         try {
             if (args.length == ZERO) {
                 LOG.info("Starting " + getAppName() + " using default arguments ...");
             } else if ( args.length == ONE && args[ZERO].trim().equalsIgnoreCase(HELP)){
                 System.out.println( USAGE);
                 canContinue = false;
             } else {
                 LOG.info("Starting " + getAppName() + "using the provided arguments ...");
             }
             if (canContinue) {
                 Map <MainClassInterface.ArgumentOptions, String> argsMap = processArguments(args);
                 argsMap = buildStartupPropertiesMap(argsMap);
                 initializeLog4J(argsMap);
                 final Properties ldapProperties = new Properties();
                 try {
                    System.out.println(System.getProperty("user.dir"));
                    LOG.info("Default location is: " + System.getProperty("user.dir"));
                    LOG.info("JndiPath is " + argsMap.get(ArgumentOptions.JNDIPATH));
                    ldapProperties.load(new FileInputStream(new File(argsMap.get(ArgumentOptions.JNDIPATH))));
                    LOG.info("Loaded JNDI config file ...");
                    setPropertiesAsSystemProperties(ldapProperties);
                    LOG.info("Set JNDI properties as System properties ...");
                 } catch (final Throwable t){
                     LOG.fatal("Failed to load JNDI property - run/check the BSC Install to obtain jndi.properties file", t);
                     throw new Exception ("Failed to load JNDI property - run/check the BSC Install to obtain jndi.properties file", t);
                 }
                 LdapConfigLoader.loadConfig(argsMap.get(ArgumentOptions.LDAPCONTEXTNAME));
                 LdapConfigLoader.loadConfig(LDAP_CONFIG_LOOKUP_ENV);
                 LdapConfigLoader.loadConfig(LDAP_CONFIG_LOOKUP_PERMISSIONS);
                 LOG.info("Loaded LDAP config values ...");
             }
          } catch (final Exception e) {
            System.out.print(new Date() + " ERROR - ");
            e.printStackTrace(System.out);
            System.out.flush();
            throw e;
          }
          return canContinue;
    }

     protected void setPropertiesAsSystemProperties(final Properties properties) {
        for(final Map.Entry <Object, Object>  keyValuePair : properties.entrySet()) {
            System.setProperty(keyValuePair.getKey().toString(), keyValuePair.getValue().toString());
            LOG.info("Added " + keyValuePair.getKey().toString() + "=" + keyValuePair.getValue().toString() + "to System property");
        }
     }
     public Map <MainClassInterface.ArgumentOptions, String> processArguments(final String [] args) {
             final Map <MainClassInterface.ArgumentOptions, String> argsMap =  new HashMap <MainClassInterface.ArgumentOptions, String> ();
             String[] argNameValuePair = null;
             MainClassInterface.ArgumentOptions argName = null;
             String argValue = null;
            for (final String i: args) {
                argNameValuePair = i.split(MainClassInterface.ARG_FIELD_SEPERATOR);
                if (argNameValuePair.length != TWO) {
                    System.out.println(new Date() + " INFO - Argument(" + i + ") is a JVM argument");
                }
                else {
                    argName = MainClassInterface.ArgumentOptions.getInstance(argNameValuePair[ZERO]);
                    argValue = argNameValuePair[ONE];
                    if (argName != null && argValue != null) {
                        System.out.println(new Date() + " INFO - Argument name(" + argNameValuePair[ZERO] + ") value(" + argNameValuePair[ONE] + ") is used.") ;
                        argsMap.put(argName, argValue.trim());
                    } else {
                        System.out.println(new Date() + " WARN - Argument name(" + argNameValuePair[ZERO] + ") is invalid and argument(" + i + ") will be ignored.") ;
                    }
                }
            }
            return argsMap;
     }

     protected Map <MainClassInterface.ArgumentOptions, String> buildStartupPropertiesMap(Map <MainClassInterface.ArgumentOptions, String> argumentPassedIn) throws Exception {
        if ( argumentPassedIn != null ) {

            if (argumentPassedIn.get(ArgumentOptions.JNDIPATH) == null){
                argumentPassedIn.put(ArgumentOptions.JNDIPATH,checkValueForNullOrEmpty(argumentPassedIn.get(ArgumentOptions.JNDIPATH), getJndiPath()));
            }
            if (argumentPassedIn.get(ArgumentOptions.LOG4JPATH) == null){
                argumentPassedIn.put(ArgumentOptions.LOG4JPATH,checkValueForNullOrEmpty(argumentPassedIn.get(ArgumentOptions.LOG4JPATH), getLog4jPath()));
            }
            if (argumentPassedIn.get(MainClassInterface.ArgumentOptions.LDAPCONTEXTNAME) == null){
                argumentPassedIn.put(MainClassInterface.ArgumentOptions.LDAPCONTEXTNAME,checkValueForNullOrEmpty(argumentPassedIn.get(ArgumentOptions.LDAPCONTEXTNAME), getLdapContextName()));
            }
        } else {
            argumentPassedIn = new HashMap<MainClassInterface.ArgumentOptions, String>();
            argumentPassedIn.put(ArgumentOptions.JNDIPATH,checkValueForNullOrEmpty(getJndiPath(),"ERROR"));
            argumentPassedIn.put(MainClassInterface.ArgumentOptions.LOG4JPATH,checkValueForNullOrEmpty( getLog4jPath(), "ERROR"));
            argumentPassedIn.put(MainClassInterface.ArgumentOptions.LDAPCONTEXTNAME,checkValueForNullOrEmpty(getLdapContextName(), "ERROR"));
        }

         return argumentPassedIn;
     }

     protected String checkValueForNullOrEmpty(final String valueToCheck, final String defaultValue) throws Exception {
         if (valueToCheck == null || valueToCheck.trim().isEmpty()) {
                return defaultValue;
             } else {
             return valueToCheck.trim();
         }
     }

     protected void initializeLog4J(final Map <MainClassInterface.ArgumentOptions, String> argsMap) throws Exception {
         try {
             System.out.println("Default location startup location for log4j is: " + System.getProperty("user.dir"));
             DOMConfigurator.configure(argsMap.get(ArgumentOptions.LOG4JPATH));
         } catch (final Exception e) {
             System.out.println("Failed to locate the log4j file at: " + argsMap.get(ArgumentOptions.LOG4JPATH) + e);
             throw new Exception("Failed to locate the log4j file at: " + argsMap.get(ArgumentOptions.LOG4JPATH) + e);
         }

     }
}
