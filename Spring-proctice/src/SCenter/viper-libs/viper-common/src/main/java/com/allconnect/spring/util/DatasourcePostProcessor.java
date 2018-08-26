package com.A.spring.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
/**
 * This class will be used to Post Process the datasource bean defined for OME and Customer
 * Service to inject db connection properties for Transactional DB and Logical DB(Read Only).
 * This class use the DatasourceParser.java to get the properties from datasource xml file
 * located in JBoss Deploy directory and set them for each Datasource Bean.
 * @author ppatel
 *
 */
public class DatasourcePostProcessor implements BeanPostProcessor {
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

	/**
	 * This method will be called immediately after the DatasourceBean is constructed by
	 * Spring container. It will check for bean name and based on the name, it will tell
	 * DatasourceParser.java to load properties from respected datasource xml file, which
	 * then ultimately be used to create db connection.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {

//		if (beanName.equalsIgnoreCase("logicalDatasource") && bean != null) {
//			logger.info("Processing : " + beanName);
//			Properties props = getProperties(LOGICAL);
//
//			overrideProperties(bean, props);
//		}else if(beanName.equalsIgnoreCase("transactionalDatasource") && bean != null){
//			logger.info("Processing : " + beanName);
//			Properties props = getProperties(TRANSACTIONAL);
//			overrideProperties(bean, props);
//
//		}

		return bean;
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
						logger.info("Actual value of JDBC URL in DatasourceBean is : " + clazz.getMethod(GET_JDBC_URL, null).invoke(bean, null));
						logger.info("Url from Properties object : " + url);
						method.invoke(bean, url);
						logger.info("Overriden value in DatasourceBean is : " + clazz.getMethod(GET_JDBC_URL, null).invoke(bean, null));
					}

					if (methodName.equalsIgnoreCase(SET_USER)) {
						String username = props.getProperty(DatasourceConstant.USERNAME);
						logger.info("Actual value of Username in DatasourceBean is : " + clazz.getMethod(GET_USER, null).invoke(bean, null));
						logger.info("Username from Properties object: "+ username);
						method.invoke(bean, username);
						logger.info("Overriden value in DatasourceBean is : " + clazz.getMethod(GET_USER, null).invoke(bean, null));
					}

					if (methodName.equalsIgnoreCase(SET_PASSWORD)) {
						String password = props.getProperty(DatasourceConstant.PASSWORD);
						//logger.info("Actual value of Password in DatasourceBean is : " + clazz.getMethod("getPassword", null).invoke(bean, null));
						//logger.info("Password from datasource file : "+ password);
						method.invoke(bean, password);
						//logger.info("Overriden value in DatasourceBean is : " + clazz.getMethod("getPassword", null).invoke(bean, null));
					}

					if (methodName.equalsIgnoreCase(SET_MIN_POOL)) {
						String minPoolSize = props.getProperty(DatasourceConstant.MIN_POOL);

						minPool = Integer.parseInt(minPoolSize);
						logger.info("Actual value of MinPoolSize in DatasourceBean is : " + clazz.getMethod("getMinPoolSize", null).invoke(bean, null));
						logger.info("MinPoolSize from Properties object : "+ minPool);
						method.invoke(bean, minPool);
						logger.info("Overriden value in DatasourceBean is : " + clazz.getMethod("getMinPoolSize", null).invoke(bean, null));
					}

					if (methodName.equalsIgnoreCase(SET_MAX_POOL)) {
						String maxPoolSize = props.getProperty(DatasourceConstant.MAX_POOL);
						maxPool = Integer.parseInt(maxPoolSize);
						logger.info("Actual value of MaxPoolSize in DatasourceBean is : " + clazz.getMethod("getMaxPoolSize", null).invoke(bean, null));
						logger.info("MaxPoolSize from Properties object : "+ maxPool);
						method.invoke(bean, maxPool);
						logger.info("Overriden value in DatasourceBean is : " + clazz.getMethod("getMaxPoolSize", null).invoke(bean, null));
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

		Map<String, Properties> dsPropMap = DatasourceParser
				.parseDatasouceFile(datasourceType);
		if (dsPropMap != null && !dsPropMap.isEmpty()) {
			if (dsPropMap.containsKey(LOGICAL_PROPERTIES) && datasourceType.equalsIgnoreCase("logical")) {
				Properties props = dsPropMap.get(LOGICAL_PROPERTIES);
				if (props != null && !props.isEmpty()) {
					return props;

				}
			}else if(dsPropMap.containsKey(TRANSACTIONAL_PROPERTIES) && datasourceType.equalsIgnoreCase("transactional")){
				Properties props = dsPropMap.get(TRANSACTIONAL_PROPERTIES);
				if (props != null && !props.isEmpty()) {
					return props;

				}
			}
		}else{
			logger.info("**************************************");
			logger.info("Properties from Datasource is emply!!!");
			logger.info("**************************************");
		}
		return null;
	}
}
