package com.A.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtilTest {

	static ApplicationContext springContext = null;

	public static ApplicationContext getContext() {
		if (springContext == null)
			springContext = new ClassPathXmlApplicationContext(
					"applicationContextTest.xml");

		return springContext;
	}

	public static Object getBean(String beanName) {

		return getContext().getBean(beanName);
	}

	public static Object getBean(Class<?> beanClass) {

		return getContext().getBean(beanClass);
	}
}