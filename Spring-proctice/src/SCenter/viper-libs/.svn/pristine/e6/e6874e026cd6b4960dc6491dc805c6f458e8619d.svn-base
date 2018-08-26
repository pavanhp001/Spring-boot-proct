package com.A.util;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public enum CustomerSpringUtil {

	INSTANCE;
	private final Logger logger = Logger.getLogger(CustomerSpringUtil.class);
	private ApplicationContext customerSpringContext = null;

	private static final Object lock = new Object();

	public ApplicationContext getContext() {
		String[] contextFiles = new String[] { "spring/customerContext.xml" };

		if (customerSpringContext == null) {
			synchronized (lock) {
				if(customerSpringContext == null){
					logger.info("Loading beans from customerContext.xml");
					customerSpringContext = new ClassPathXmlApplicationContext(contextFiles);
					logger.info("Loaded beans from Classpath.");
				}
			}

		}
		logger.trace(customerSpringContext.getBeanDefinitionNames());

		return customerSpringContext;
	}

	public void reset() {
		customerSpringContext = null;
		getContext();
	}

	public Object getBean(String beanName) {

		return getContext().getBean(beanName);
	}

	public Object getBean(Class<?> beanClass) {

		return getContext().getBean(beanClass);
	}
}
