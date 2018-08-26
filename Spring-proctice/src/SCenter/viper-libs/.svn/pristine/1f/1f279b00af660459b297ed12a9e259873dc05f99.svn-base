package com.A.spring.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * This class will read datasource file mentioned in ds-location.properties file and put
 * "JDBC URL, Username, Password, MinPoolSize, MaxPoolSize" into the properties object
 * which will be later used by DatasourcePostProcessor.java
 * @author ppatel
 *
 */
public class DatasourceParser {

	private static final String LOGICAL_DS_FILE_NAME = "logical.ds.file.name";
	private static final String TRANSACTIONAL_DS_FILE_NAME = "transactional.ds.file.name";
	private static final String LOCAL_TX_DATASOURCE = "local-tx-datasource";
	private static final String JNDI_NAME = "jndi-name";
	private static final String OS_NAME = "os.name";
	private static final String FILE_SEPARATOR = "file.separator";
	private static final String MIN_POOL_SIZE = "min-pool-size";
	private static final String MAX_POOL_SIZE = "max-pool-size";
	private static final String PASSWORD = "password";
	private static final String USER_NAME = "user-name";
	private static final String CONNECTION_URL = "connection-url";
	private static final String LOGICAL_PROPERTIES = "logicalProperties";
	private static final String LOGICAL = "logical";
	private static final String TRANSACTIONAL_PROPERTIES = "transactionalProperties";
	private static final String TRANSACTIONAL = "transactional";
	private static final Logger logger = Logger
			.getLogger(DatasourceParser.class);
	private static final String DS_LOCATION_FILE = "ds-location.properties";
	private static final String WINDOWS = "Windows";
	private static String earPath = "";
	private static Properties props = new Properties();
	private static final String DEPLOY_DIR = "deploy";

	private static Map<String, Properties> dsPropMap = new HashMap<String, Properties>();

	/**
	 * Helper method to load properties
	 */
	public static void loadProperties() {

		StringBuilder sb = new StringBuilder();
		sb.append(DS_LOCATION_FILE);
		ClassLoader clazzLoader = Thread.currentThread()
				.getContextClassLoader();
		boolean isLoaded = loadPropertiesFile(props, clazzLoader, sb.toString());
		if (!isLoaded) {
			isLoaded = loadPropertiesFile(props, clazzLoader.getParent(),
					sb.toString());
			if (isLoaded) {
				logger.info("Properties loaded from : " + DS_LOCATION_FILE);
			}
		}

	}

	/**
	 * Helper method to load properties from property file
	 * @param props
	 * @param clazzLoader
	 * @param filename
	 * @return
	 */
	private static boolean loadPropertiesFile(Properties props,
			ClassLoader clazzLoader, String filename) {
		try {
			logger.info("attempting.to.load:" + filename);
			earPath = clazzLoader.getResource(filename).getPath();
			logger.info("Path : " + earPath);
			props.load(clazzLoader.getResource(filename).openStream());
			logger.info("successfully.loaded:" + filename);
			return Boolean.TRUE;
		} catch (Exception e1) {
			logger.info("error.loading:" + filename);
			logger.error("Exception thrown while loading properties file",e1);
		}
		return Boolean.FALSE;
	}

	/**
	 * Helper method to get the file path
	 * @return
	 */
	private static String getFilePath() {
		String searchDir = DEPLOY_DIR;
		int indexOfSearchDir = earPath.indexOf(searchDir);
		String pathToSearchDir = earPath.substring(0, indexOfSearchDir);
		logger.info(pathToSearchDir + searchDir);
		return pathToSearchDir + searchDir;
	}

	/**
	 * A method to parse datasource file and put all the properties read from datasource into the 
	 * properties object
	 * @param datasourceType
	 * @return
	 */
	public static Map<String, Properties> parseDatasouceFile(
			String datasourceType) {
		loadProperties();
		try {
			String fileName = "";
			String path = getFilePath();
			path = reformatPath(path);

			String datasourceFileName = "";
			//Prepare the complete path for Transactional Datasource file
			if (datasourceType.equalsIgnoreCase(TRANSACTIONAL)) {
				datasourceFileName = (String) props.get(TRANSACTIONAL_DS_FILE_NAME);
				fileName = path + System.getProperty(FILE_SEPARATOR)
						+ datasourceFileName;
				logger.info("Parsing datasource file : " + fileName);
			//Prepare the complete path for Logical(Read Only) datasource file	
			} else if (datasourceType.equalsIgnoreCase(LOGICAL)) {
				datasourceFileName = (String) props.get(LOGICAL_DS_FILE_NAME);
				fileName = path + System.getProperty(FILE_SEPARATOR)
						+ datasourceFileName;
				logger.info("Parsing datasource file : " + fileName);
			}
			
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(fileName));

			NodeList listOfDatasources = doc.getElementsByTagName(LOCAL_TX_DATASOURCE);
			int totalDS = listOfDatasources.getLength();
			logger.info("Total no of datasource : " + totalDS);
			for (int i = 0; i < listOfDatasources.getLength(); i++) {
				Node firstDSNode = listOfDatasources.item(i);
				if (firstDSNode.getNodeType() == Node.ELEMENT_NODE) {

					// local-tx-datasource element
					Element dsElmt = (Element) firstDSNode;

					// jndi element list
					NodeList jndiEleList = dsElmt
							.getElementsByTagName(JNDI_NAME);

					// first element of local-tx-datasource ie. jndi
					Element jndiElmt = (Element) jndiEleList.item(0);

					// Getting child of the jndi element
					NodeList fstNm = jndiElmt.getChildNodes();

					// Getting value
					String eleValue = ((Node) fstNm.item(0)).getNodeValue();
					logger.info("*****************************************************************");
					logger.info("JNDI Name of the datasource in "+ datasourceFileName +" : " + eleValue);
					logger.info("*****************************************************************");
					if (eleValue.startsWith(TRANSACTIONAL)) {
						//Read ds properties for transactional db
						Properties prop = parseProperties(dsElmt);
						dsPropMap.put(TRANSACTIONAL_PROPERTIES, prop);
					} else if (eleValue.startsWith(LOGICAL)) {
						//Read ds properties for readonly db
						Properties prop = parseProperties(dsElmt);
						dsPropMap.put(LOGICAL_PROPERTIES, prop);
					}
				}
			}

		} catch (ParserConfigurationException e) {
			logger.error("ParserConfigurationException thrown while parsing datasource file :", e);
			return Collections.EMPTY_MAP;
		} catch (SAXException e) {
			logger.error("SAXException thrown while parsing datasource file :", e);
			return Collections.EMPTY_MAP;
		} catch (IOException e) {
			logger.error("IOException thrown while parsing datasource file :", e);
			return Collections.EMPTY_MAP;
		}
		return dsPropMap;
	}

	/**
	 * This method will reformat the path according to host operating system.
	 * @param path
	 * @return
	 */
	private static String reformatPath(String path) {
		String os = System.getProperty(OS_NAME);
		logger.info("Operating System : " + os);
		if (os.startsWith(WINDOWS) && path.startsWith("/")) {
			path = path.replaceFirst("/", "");
		}
		path = StringUtils.replace(path, "/", System.getProperty(FILE_SEPARATOR));
		return path;
	}

	/**
	 * Helper method to read properties from Datasource file 
	 * @param dsElmt
	 * @return
	 */
	private static Properties parseProperties(Element dsElmt) {
		Properties prop = new Properties();

		// Connection url
		NodeList urlEleList = dsElmt.getElementsByTagName(CONNECTION_URL);
		Element urlElmt = (Element) urlEleList.item(0);
		NodeList urlNode = urlElmt.getChildNodes();
		String urlValue = ((Node) urlNode.item(0)).getNodeValue();
		logger.info("URL from ds file : " + urlValue);
		prop.put(DatasourceConstant.URL, urlValue);

		// Username element
		NodeList uNameList = dsElmt.getElementsByTagName(USER_NAME);
		Element uNameElmt = (Element) uNameList.item(0);
		NodeList uName = uNameElmt.getChildNodes();
		String uNameValue = ((Node) uName.item(0)).getNodeValue();
		logger.info("username from ds file: " + uNameValue);
		prop.put(DatasourceConstant.USERNAME, uNameValue);

		// Password Element
		NodeList passList = dsElmt.getElementsByTagName(PASSWORD);
		Element passElmt = (Element) passList.item(0);
		NodeList passName = passElmt.getChildNodes();
		String passValue = ((Node) passName.item(0)).getNodeValue();
		//logger.info("password from ds file: " + passValue);
		prop.put(DatasourceConstant.PASSWORD, passValue);
		
		// Min Pool Size element 
		NodeList minPoolList = dsElmt.getElementsByTagName(MIN_POOL_SIZE);
		Element minPoolElnt = (Element) minPoolList.item(0);
		NodeList minPool = minPoolElnt.getChildNodes();
		String minPoolValue = ((Node) minPool.item(0)).getNodeValue();
		logger.info("MinPool Size from ds file: " + minPoolValue);
		prop.put(DatasourceConstant.MIN_POOL, minPoolValue.trim());
		
		//Max Pool Size
		NodeList maxPoolSize = dsElmt.getElementsByTagName(MAX_POOL_SIZE);
		Element maxPoolElmt = (Element) maxPoolSize.item(0);
		NodeList maxPool = maxPoolElmt.getChildNodes();
		String maxPoolValue = ((Node) maxPool.item(0)).getNodeValue();
		logger.info("MaxPool Size from ds file: " + maxPoolValue);
		prop.put(DatasourceConstant.MAX_POOL, maxPoolValue.trim());
		
		return prop;
	}

	public static void main(String[] args) {
		parseDatasouceFile(LOGICAL);
	}
}
