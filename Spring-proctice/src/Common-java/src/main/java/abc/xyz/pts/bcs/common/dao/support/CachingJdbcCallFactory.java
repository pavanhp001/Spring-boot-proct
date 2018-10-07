/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public class CachingJdbcCallFactory {

    private static final Logger logger = Logger.getLogger(CachingJdbcCallFactory.class);

    private static final ConcurrentMap<String, Future<? extends SimpleJdbcCall>> callCacheMap = new ConcurrentHashMap<String, Future<? extends SimpleJdbcCall>>();

    private static final Class<?>[] JDBCTEMPLATE_CONSTRUCTOR_ARGS = { JdbcTemplate.class };

    private CachingJdbcCallFactory() {
    }

    // Convenience methods..

    public static SimpleJdbcCall getSimpleJdbcCall(final JdbcTemplate jdbcTemplate, final String packageName,
            final String procedureName) {
        return getJdbcCall(SimpleJdbcCall.class, jdbcTemplate, packageName, procedureName, null, false);
    }

    public static CustomJdbcCall getCustomJdbcCall(final JdbcTemplate jdbcTemplate, final String packageName,
            final String procedureName) {
        return getJdbcCall(CustomJdbcCall.class, jdbcTemplate, packageName, procedureName, null, false);
    }

    public static SimpleJdbcCall getSimpleJdbcCall(final JdbcTemplate jdbcTemplate, final String packageName,
            final String procedureName, final boolean isFunction) {
        return getJdbcCall(SimpleJdbcCall.class, jdbcTemplate, packageName, procedureName, null, isFunction);
    }

    public static CustomJdbcCall getCustomJdbcCall(final JdbcTemplate jdbcTemplate, final String packageName,
            final String procedureName, final boolean isFunction) {
        return getJdbcCall(CustomJdbcCall.class, jdbcTemplate, packageName, procedureName, null, isFunction);
    }

    public static SimpleJdbcCall getSimpleJdbcCall(final JdbcTemplate jdbcTemplate, final String packageName,
            final String procedureName, final JdbcCallConfigurer<SimpleJdbcCall> configurer) {
        return getJdbcCall(SimpleJdbcCall.class, jdbcTemplate, packageName, procedureName, configurer, false);
    }

    public static CustomJdbcCall getCustomJdbcCall(final JdbcTemplate jdbcTemplate, final String packageName,
            final String procedureName, final JdbcCallConfigurer<CustomJdbcCall> configurer) {
        return getJdbcCall(CustomJdbcCall.class, jdbcTemplate, packageName, procedureName, configurer, false);
    }

    public static SimpleJdbcCall getSimpleJdbcCall(final JdbcTemplate jdbcTemplate, final String packageName,
            final String procedureName, final JdbcCallConfigurer<SimpleJdbcCall> configurer, final boolean isFunction) {
        return getJdbcCall(SimpleJdbcCall.class, jdbcTemplate, packageName, procedureName, configurer, isFunction);
    }

    public static CustomJdbcCall getCustomJdbcCall(final JdbcTemplate jdbcTemplate, final String packageName,
            final String procedureName, final JdbcCallConfigurer<CustomJdbcCall> configurer, final boolean isFunction) {
        return getJdbcCall(CustomJdbcCall.class, jdbcTemplate, packageName, procedureName, configurer, isFunction);
    }

    // Full form method

    public static <S extends SimpleJdbcCall> S getJdbcCall(final Class<S> clazz, final JdbcTemplate jdbcTemplate,
            final String packageName, final String procedureName, final JdbcCallConfigurer<S> configurer,
            final boolean isFunction) {
        String identity = (new StringBuffer(packageName)).append('.').append(procedureName).toString();
        Future<? extends SimpleJdbcCall> jdbcCallTask = callCacheMap.get(identity);
        if (jdbcCallTask == null) {
            Callable<S> callable = new Callable<S>() {
                public S call() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
                        InstantiationException, IllegalAccessException, InvocationTargetException {
                    Constructor<S> constructor = clazz.getConstructor(JDBCTEMPLATE_CONSTRUCTOR_ARGS);
                    Object[] theArgs = { jdbcTemplate };
                    S result;
                    if (!isFunction) {
                        @SuppressWarnings("unchecked")
                        S r = (S) constructor.newInstance(theArgs).withCatalogName(packageName).withProcedureName(
                                procedureName);
                        result = r;
                    } else {
                        @SuppressWarnings("unchecked")
                        S r = (S) constructor.newInstance(theArgs).withCatalogName(packageName).withFunctionName(
                                procedureName);
                        result = r;
                    }
                    if (configurer != null) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("configuring");
                        }
                        result = (S) configurer.configure((S) result);
                    }
                    return result;
                }
            };
            @SuppressWarnings("unchecked")
            FutureTask<S> newTask = new FutureTask(callable);
            jdbcCallTask = callCacheMap.putIfAbsent(identity, newTask);
            if (jdbcCallTask == null) {
                jdbcCallTask = newTask;
                newTask.run();
            }
        }
        try {
            @SuppressWarnings("unchecked")
            S result = (S) jdbcCallTask.get();
            if (logger.isTraceEnabled()) {
                logger.trace("result is: " + result);
            }
            return result;
        } catch (Exception ex) {
            logger.error("Unable to create SimpleJdbcCall for: " + identity, ex);
            return null;
        }
    }

    public static void report() {
        if (logger.isInfoEnabled()) {
            logger.info("cache contains: " + callCacheMap.size() + " elements");
        }
    }

}
