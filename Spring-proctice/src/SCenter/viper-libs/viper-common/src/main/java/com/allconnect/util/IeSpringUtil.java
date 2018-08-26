package com.A.util;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IeSpringUtil extends SpringUtilBase {
    private static final Logger logger = Logger.getLogger(IeSpringUtil.class);
    @Override
    public ApplicationContext getContext() {
	logger.info("Loading beans from Classpath..............");
	String[] contextFiles = new String[] { "spring"+File.separatorChar+"arbiterContext.xml" };
	if (springContext == null) {
	    springContext = new ClassPathXmlApplicationContext(contextFiles);
	}
	logger.debug(springContext.getBeanDefinitionNames());
	logger.info("Loaded beans from Classpath..............");
	return springContext;
    }

}
