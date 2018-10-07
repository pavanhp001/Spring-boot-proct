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

package abc.xyz.pts.bcs.common.audit.aspect;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import abc.xyz.pts.bcs.common.audit.annotation.AuditableProperty;
import abc.xyz.pts.bcs.common.audit.util.ClassUtils;

public class AuditablePropertyCache {
    private static final ConcurrentMap<String, ConcurrentMap<String, AuditablePropertyEntry>> cache = new ConcurrentHashMap<String, ConcurrentMap<String, AuditablePropertyEntry>>();

    private static AuditablePropertyEntry put(final String className, final String propertyName,
            final String resourceKey) {
        ConcurrentMap<String, AuditablePropertyEntry> cacheEntries = new ConcurrentHashMap<String, AuditablePropertyEntry>(
                1);
        ConcurrentMap<String, AuditablePropertyEntry> existingEntry = cache.putIfAbsent(className, cacheEntries);
        if (existingEntry == null) {
            existingEntry = cacheEntries;
        }
        AuditablePropertyEntry ape = new AuditablePropertyEntry(resourceKey);
        existingEntry.putIfAbsent(propertyName, ape);
        return ape;
    }

    public static AuditablePropertyEntry getPropertyEntry(final Class clazz, final String propertyName) {
        AuditablePropertyEntry result = null;
        ConcurrentMap<String, AuditablePropertyEntry> cacheEntries = cache.get(clazz.getCanonicalName());
        if (cacheEntries != null) {
            result = cacheEntries.get(propertyName);
        }
        if (cacheEntries == null || result == null) {
            Class<?> tmpClass = clazz;
            if (org.springframework.aop.support.AopUtils.isCglibProxyClass(tmpClass)) {
                tmpClass = tmpClass.getSuperclass();
            }
            List<Field> fields = new ClassUtils().getFields(tmpClass);
            Field requiredField = null;
            for (Field field : fields) {
                if (field.getName().equals(propertyName)) {
                    requiredField = field;
                    break;
                }
            }
            if (requiredField != null) {
                if (requiredField.isAnnotationPresent(AuditableProperty.class)) {
                    AuditableProperty annotation = requiredField.getAnnotation(AuditableProperty.class);
                    result = AuditablePropertyCache.put(clazz.getCanonicalName(), propertyName, annotation.key()
                            .value());
                } else {
                    result = AuditablePropertyCache.put(clazz.getCanonicalName(), propertyName, null);
                }
            } else {
                result = AuditablePropertyCache.put(clazz.getCanonicalName(), propertyName, null);
            }

        }
        return result;
    }
}
