package com.A.util;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public enum OmeSpringUtil {

	INSTANCE;
	private final Logger logger = Logger.getLogger(OmeSpringUtil.class);
	private ApplicationContext omeSpringContext = null;
	private static final Object lock = new Object();
	
	public ApplicationContext getContext() {

		if (omeSpringContext == null) {
			synchronized (lock) {
				if (omeSpringContext == null) {
					logger.info("Loading beans from : spring/omeContext.xml and spring/arbiterContext.xml");
					String[] contextFiles = new String[] {
							"spring/omeContext.xml",
							"spring/arbiterContext.xml" }; //

					omeSpringContext = new ClassPathXmlApplicationContext(
							contextFiles);
				}
			}
			
			logger.debug(omeSpringContext.getBeanDefinitionNames());
			logger.info("Loaded beans from Classpath.");
		}

		return omeSpringContext;
	}

	public void reset() {
		logger.info("Re-Initializing spring context : spring/omeContext.xml and spring/arbiterContext.xml");
		omeSpringContext = null;
		getContext();
	}

	public Object getBean(String beanName) {

		return getContext().getBean(beanName);
	}

	public Object getBean(Class<?> beanClass) {

		return getContext().getBean(beanClass);
	}

	public void setApplicationContext(ApplicationContext context) {
		this.omeSpringContext = context;
	}
}
