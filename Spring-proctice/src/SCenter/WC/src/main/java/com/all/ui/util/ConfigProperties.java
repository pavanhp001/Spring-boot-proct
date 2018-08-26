/**
 * 
 */
package com.A.ui.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

//import com.A.property.management.cache.PropertyManager;

/**
 * @author Parameshwar
 *
 */
public class ConfigProperties {

	private static final Logger logger = Logger.getLogger(ConfigProperties.class);

	public static String CYCLETIME = null;
	public static String DATAEXCHANGEREFERRERURL = null;
	public static String DATAEXCHANGEBYCALLINGADDRESS = null;
	public static String DATAEXCHANGEURL = null;
	public static String DATAEXCHANGESETCUSTOMERLINKANDMATCHURL = null;
	public static String ADVISORYPROMOTION = null;
	public static String PAUSEANDRESUMEURL = null;
	public static String SALESCENTER_IDLEPAGE_IMAGE_LOCATION = null;
	public static String SALESCENTER_IDLEPAGE_IMAGE_COUNT = null;
	public static String SALESCENTER_IDLEPAGE_IMAGE_DELAY = null;
	public static String EMAIL_FROM = null;
	public static String EMAIL_TO = null;
	public static String EMAIL_CC = null;
	public static String EMAIL_EMAILSUBJECT = null;
	public static String EMAIL_EMAILCONTENT = null;
	public static String EMAIL_ENABLED = null;
	public static String EMAIL_HOST = null;
	public static String EMAIL_PORT = null;
	public static String EMAIL_USERNAME = null;
	public static String EMAIL_PASSWORD = null;
	public static String EMAIL_PROTOCOL = null;
	public static String EMAIL_SECURECONNECTIONTYPE = null;
	public static String EMAIL_DEFAULTMIMETYPE = null;
	
	public static Properties props = null;
	public static InputStream output = null;

	static {

		//props = PropertyManager.getPropertiesFile("salescenter/salescenter");
		try {
			props = new Properties();
			output = new FileInputStream("./src/main/resources/jms.properties");
			if(output != null){
				props.load(output);
				logger.info("prop : "+props);
			}
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if(!props.isEmpty()) {
			logger.info("In props file : ");
			CYCLETIME = props.getProperty("cycletime");
			DATAEXCHANGEREFERRERURL = props.getProperty("dataexchangeReferrerUrl");
			DATAEXCHANGEURL = props.getProperty("dataexchangeUrl");
			DATAEXCHANGEBYCALLINGADDRESS = props.getProperty("dataExchangeByCallingAddressUrl");
			DATAEXCHANGESETCUSTOMERLINKANDMATCHURL = props.getProperty("dataexchangeSetCustomerLinkAndMatchUrl");
			ADVISORYPROMOTION = props.getProperty("advisoryPromotion");
			PAUSEANDRESUMEURL = props.getProperty("pauseAndResumeUrl");
			SALESCENTER_IDLEPAGE_IMAGE_LOCATION = props.getProperty("salescenter.idlepage.image.location");
			SALESCENTER_IDLEPAGE_IMAGE_COUNT = props.getProperty("salescenter.idlepage.image.count");
			SALESCENTER_IDLEPAGE_IMAGE_DELAY = props.getProperty("salescenter.idlepage.image.delay");
			
			EMAIL_FROM = "rnagisetty@A.com";
			EMAIL_TO = "rnagisetty@charterglobal.com";
			EMAIL_CC = "";
			EMAIL_EMAILSUBJECT = "";
			EMAIL_EMAILCONTENT = "";
			EMAIL_ENABLED = "";
			EMAIL_HOST = "";
			EMAIL_PORT ="25";
			EMAIL_USERNAME = "";
			EMAIL_PASSWORD = "";
			EMAIL_PROTOCOL ="";
			EMAIL_SECURECONNECTIONTYPE = "";
			EMAIL_DEFAULTMIMETYPE = "text/plain";
			
			
			
			logger.info("out props file : "+CYCLETIME+" - "+DATAEXCHANGEREFERRERURL+" - "+DATAEXCHANGEURL);
		}else{
			logger.error("Unable to read properties file from the s3 bucket from path salescenter/salescenter");
		}
	}
}
