package com.A.util;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

public abstract class SpringUtilBase {

    private static final Logger logger = Logger.getLogger(SpringUtilBase.class);
    ApplicationContext springContext = null;

    private static void sync() {
	// SyncService ts = (SyncService)getBean(SyncServiceImpl.class);
	// if (ts != null)
	// {
	// ts.sync();
	// }
    }

    public void reset() {
	springContext = null;
	getContext();
    }

    public abstract ApplicationContext getContext() ;

    public Object getBean(String beanName) {

	return getContext().getBean(beanName);
    }

    public Object getBean(Class<?> beanClass) {

	return getContext().getBean(beanClass);
    }

}