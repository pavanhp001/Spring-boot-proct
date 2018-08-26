package com.A.util.spring;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;


/**
 * This class will be used to Post Process the datasource bean defined for OME
 * and Customer Service to inject db connection properties for Transactional DB
 * and Logical DB(Read Only). This class use the DatasourceParser.java to get
 * the properties from datasource xml file located in JBoss Deploy directory and
 * set them for each Datasource Bean.
 * 
 * @author ppatel
 * 
 */
public class DatasourcePostProcessor { //implements BeanPostProcessor {

	private static String repositoryPath = System
			.getProperty("repository.home");

	private static final String TRANSACTIONAL_PROPERTIES = "transactionalProperties";
	private static final String LOGICAL_PROPERTIES = "logicalProperties";
	private static final String GET_USER = "getUser";
	private static final String GET_JDBC_URL = "getJdbcUrl";
	private static final String SET_PASSWORD = "setPassword";
	private static final String SET_USER = "setUser";
	private static final String SET_JDBC_URL = "setJdbcUrl";
	private static final String LOGICAL = "logical";
	private static final String TRANSACTIONAL = "transactional";
	private static final String SET_MIN_POOL = "setMinPoolSize";
	private static final String SET_MAX_POOL = "setMaxPoolSize";
	private static int minPool = 1;
	private static int maxPool = 5;
	private static final Logger logger = Logger
			.getLogger(DatasourcePostProcessor.class);

	
	public static void main(String[] arg) {
		logger.info("This is it!!!");
		
		DatasourcePostProcessor dpp = new DatasourcePostProcessor();
		dpp.postProcessAfterInitialization(new Object(), "transactionalDatasource");
	}
	/**
	 * This method will be called immediately after the DatasourceBean is
	 * constructed by Spring container. It will check for bean name and based on
	 * the name, it will tell DatasourceParser.java to load properties from
	 * respected datasource xml file, which then ultimately be used to create db
	 * connection.
	 */
	 
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {

		if (beanName.equalsIgnoreCase("logicalDatasource") && bean != null) {

			Properties props = loadProperties(LOGICAL);
			if ((props != null) && (props.size() > 0)) {

				logger.info("Processing : " + beanName);
				props = getProperties(props.getProperty("logical.ds.file.name"));
			}

			overrideProperties(bean, props);
		} else if (beanName.equalsIgnoreCase("transactionalDatasource")
				&& bean != null) {

			Properties props = loadProperties("ds-location.properties");

			if ((props != null) && (props.size() > 0)) {
				logger.info("Processing : " + beanName);
				props = getProperties(props.getProperty("transactional.ds.file.name"));
				overrideProperties(bean, props);
			}
		}

		return bean;
	}

	public String getRepositoryPath() {
		if (repositoryPath == null) {
			repositoryPath = "";
		}

		return repositoryPath;
	}

	public Properties loadProperties(final String namespace) {
		if (namespace == null) {
			throw new IllegalArgumentException(
					"null.invalid.namespace.cannot.load.properties");
		}

		StringBuilder sb = new StringBuilder(getRepositoryPath());
		sb.append(namespace);

		Properties metadata = new Properties();

		ClassLoader clazzLoader = Thread.currentThread()
				.getContextClassLoader();

		boolean isLoaded = loadPropertiesFile(metadata, clazzLoader,
				sb.toString());

		if (!isLoaded) {
			isLoaded = loadPropertiesFile(metadata, clazzLoader.getParent(),
					sb.toString());

			if (!isLoaded) {
				throw new IllegalArgumentException(
						"unable.to.load.properties.file.from.classloader.and.parent.classloader:"
								+ sb.toString());
			}
		}

		return metadata;

	}

	public boolean loadPropertiesFile(Properties metadata,
			ClassLoader clazzLoader, String filename) {
		try {
			logger.debug("attempting to load:" + filename);
			metadata.load(clazzLoader.getResource(filename).openStream());
			logger.info("successfully loaded:" + filename);
			return Boolean.TRUE;
		} catch (Exception e1) {
			logger.info("error.loading:" + filename);

		}

		return Boolean.FALSE;
	}

	private void overrideProperties(Object bean, Properties props) {
		Class clazz = bean.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		try {
			if (methods != null && props != null) {
				for (Method method : methods) {

					String methodName = method.getName();

					if (methodName.equalsIgnoreCase(SET_JDBC_URL)) {
						String url = props.getProperty(DatasourceConstant.URL);
						logger.debug("Actual value of JDBC URL in DatasourceBean is : "
								+ clazz.getMethod(GET_JDBC_URL, null).invoke(
										bean, null));
						logger.debug("Url from Properties object : " + url);
						method.invoke(bean, url);
						logger.debug("Overriden value in DatasourceBean is : "
								+ clazz.getMethod(GET_JDBC_URL, null).invoke(
										bean, null));
					}

					if (methodName.equalsIgnoreCase(SET_USER)) {
						String username = props
								.getProperty(DatasourceConstant.USERNAME);
						logger.debug("Actual value of Username in DatasourceBean is : "
								+ clazz.getMethod(GET_USER, null).invoke(bean,
										null));
						logger.debug("Username from Properties object: "
								+ username);
						method.invoke(bean, username);
						logger.debug("Overriden value in DatasourceBean is : "
								+ clazz.getMethod(GET_USER, null).invoke(bean,
										null));
					}

					if (methodName.equalsIgnoreCase(SET_PASSWORD)) {
						String password = props
								.getProperty(DatasourceConstant.PASSWORD);
						// logger.info("Actual value of Password in DatasourceBean is : "
						// + clazz.getMethod("getPassword", null).invoke(bean,
						// null));
						// logger.info("Password from datasource file : "+
						// password);
						method.invoke(bean, password);
						// logger.info("Overriden value in DatasourceBean is : "
						// + clazz.getMethod("getPassword", null).invoke(bean,
						// null));
					}

					if (methodName.equalsIgnoreCase(SET_MIN_POOL)) {
						String minPoolSize = props
								.getProperty(DatasourceConstant.MIN_POOL);

						minPool = Integer.parseInt(minPoolSize);
						logger.debug("Actual value of MinPoolSize in DatasourceBean is : "
								+ clazz.getMethod("getMinPoolSize", null)
										.invoke(bean, null));
						logger.debug("MinPoolSize from Properties object : "
								+ minPool);
						method.invoke(bean, minPool);
						logger.debug("Overriden value in DatasourceBean is : "
								+ clazz.getMethod("getMinPoolSize", null)
										.invoke(bean, null));
					}

					if (methodName.equalsIgnoreCase(SET_MAX_POOL)) {
						String maxPoolSize = props
								.getProperty(DatasourceConstant.MAX_POOL);
						maxPool = Integer.parseInt(maxPoolSize);
						logger.debug("Actual value of MaxPoolSize in DatasourceBean is : "
								+ clazz.getMethod("getMaxPoolSize", null)
										.invoke(bean, null));
						logger.debug("MaxPoolSize from Properties object : "
								+ maxPool);
						method.invoke(bean, maxPool);
						logger.debug("Overriden value in DatasourceBean is : "
								+ clazz.getMethod("getMaxPoolSize", null)
										.invoke(bean, null));
					}
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error("IllegalArgument Exception thrown", e);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		// TODO Auto-generated method stub

		return bean;
	}

	private Properties getProperties(String datasourceType) {

		Map<String, Properties> dsPropMap = null; //DatasourceParser.parseDatasouceFile(datasourceType);
		if (dsPropMap != null && !dsPropMap.isEmpty()) {
			if (dsPropMap.containsKey(LOGICAL_PROPERTIES)
					&& datasourceType.equalsIgnoreCase("logical")) {
				Properties props = dsPropMap.get(LOGICAL_PROPERTIES);
				if (props != null && !props.isEmpty()) {
					return props;

				}
			} else if (dsPropMap.containsKey(TRANSACTIONAL_PROPERTIES)
					&& datasourceType.equalsIgnoreCase("transactional")) {
				Properties props = dsPropMap.get(TRANSACTIONAL_PROPERTIES);
				if (props != null && !props.isEmpty()) {
					return props;

				}
			}
		} else {
			logger.info("**************************************");
			logger.info("Properties from Datasource is emply!!!");
			logger.info("**************************************");
		}
		return null;
	}
}
