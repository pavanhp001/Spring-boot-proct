/**
 * 
 */
package com.AL.ui.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

//import com.AL.property.management.cache.PropertyManager;


/**
 * @author Parameshwar
 *
 */
public class ConfigProperties {

	private static final Logger logger = Logger.getLogger(ConfigProperties.class);
	public static String DIALOGECACHETIME = null;
	public static String PROVIDER_LOGO_LOCATION = null;
	public static Properties props = null;
	public static String DOMINION_URL = null;
	public static String DOMINION_PRODUCT_EXTID = null;
	public static String WARMTRANSFER_DIALOGUE_EXTIDS = null;
	
	public static InputStream output = null;


	static {
		
	try{
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
		//props = PropertyManager.getPropertiesFile("CKO-static/static-cko");

		//if(!props.isEmpty()) {
			DIALOGECACHETIME = "1"; //props.getProperty("dialogeCacheTime");
			PROVIDER_LOGO_LOCATION = "https://s3.amazonaws.com/AL-content/common/provider/logos/"; // props.getProperty("provider_logo_location");
			DOMINION_URL = "https://qa-enroll.dpspartnerportal.com/DPSPartnerPortal/Web/Enrollment"; // props.getProperty("provider_logo_location");
			DOMINION_PRODUCT_EXTID="DOMINION-SURGEHELP-CLOSINGOFFER";
			WARMTRANSFER_DIALOGUE_EXTIDS = "SoCalGas_GasLine_OptInSelection_Q=Customer decided to be transferred|";
		/*}else{
			logger.error("Unable to read properties file from the s3 bucket from path CKO-static/static-cko");
		}*/
	}

}
