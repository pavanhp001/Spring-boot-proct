
 


package com.A.util;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtilArbiter {

	static ApplicationContext springContext = null;

	public static ApplicationContext getContext() {
		if (springContext == null)
			springContext = new ClassPathXmlApplicationContext(
					"spring"+File.separator+"arbiterContext.xml");

		return springContext;
	}

	public static Object getBean(String beanName) {

		return getContext().getBean(beanName);
	}

	public static Object getBean(Class<?> beanClass) {

		return getContext().getBean(beanClass);
	}
}