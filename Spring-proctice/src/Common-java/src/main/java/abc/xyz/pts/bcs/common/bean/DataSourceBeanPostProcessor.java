/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.bean;

import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.jndi.JndiObjectFactoryBean;

public class DataSourceBeanPostProcessor implements DestructionAwareBeanPostProcessor{

    private static final Logger logger = Logger.getLogger(DataSourceBeanPostProcessor.class);

    @SuppressWarnings("deprecation")
    @Override
    public void postProcessBeforeDestruction(final Object bean, final String beanName) throws BeansException {
        if (JndiObjectFactoryBean.class.isAssignableFrom(bean.getClass())) {
            JndiObjectFactoryBean ofb = (JndiObjectFactoryBean) bean;
            if (OracleDataSource.class.isAssignableFrom(ofb.getObjectType())){
                try {
                    OracleDataSource ods = (OracleDataSource) ofb.getObject();
                    String name = ods.getDataSourceName();
                    String cacheName = ods.getConnectionCacheName();
                    logger.info("OracleDataSource: " + beanName + " (Name: " + name + ", CacheName: " + cacheName +")");
                    ods.close();
                    logger.info("Successfully closed the database connection: " + beanName);
                } catch (SQLException e) {
                    logger.info("Failed to close the database connection: " + beanName + " - "+ e.getMessage());
                }
            }
        }
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }

}
