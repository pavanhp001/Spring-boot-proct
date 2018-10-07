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

package abc.xyz.pts.bcs.common.audit.aspect.propertyResolver;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A simple singleton cache of methods that have been used in a class. The cache is backed by a concurrent hash map.
 *
 * @author tterry
 *
 */
public class MethodCache {

    private static ConcurrentMap<String, Method> cache;
    private static MethodCache thisRef = new MethodCache();

    private MethodCache() {
        cache = new ConcurrentHashMap<String, Method>();
    }

    /**
     * @return The singleton method cache instance.
     */
    public static MethodCache getInstance() {
        return thisRef;
    }

    /**
     * Clears the cache.
     *
     */
    public void clear() {
        cache.clear();
    }

    private String generateKey(final String methodName, final Class targetClass) {
        return (targetClass.getCanonicalName() + "." + methodName);
    }

    /**
     * Retrieves a method from the cache using the provided method name and class as the key.
     *
     * @param methodName
     * @param targetClass
     * @return
     */
    public Method getMethod(final String methodName, final Class targetClass) {
        final String key = generateKey(methodName, targetClass);
        return cache.get(key);
    }

    /**
     * Adds a new method to the cache using the provided method name and class as the key. If the cache entry already
     * exists then the cache is left unchanged.
     *
     * @param methodName
     * @param targetClass
     * @param method
     */
    public void putMethod(final Class targetClass, final Method method) {
        final String key = generateKey(method.getName(), targetClass);
        cache.putIfAbsent(key, method);
    }
}
