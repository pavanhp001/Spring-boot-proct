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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import abc.xyz.pts.bcs.common.audit.annotation.AuditableBeanProperty;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableCompositeBeanProperty;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableExpressionBeanProperties;
import abc.xyz.pts.bcs.common.audit.util.ClassUtils;

/**
 * Simple singleton cache for storing a list of fields that are auditable for a given class. The implementation for the
 * cache is a ConcurrentHashMap
 *
 * @author tterry
 *
 */
public final class AuditableBeanPropertyCache {

    private static ConcurrentMap<String, List<Field>> cache;
    private static AuditableBeanPropertyCache thisRef = new AuditableBeanPropertyCache();

    private AuditableBeanPropertyCache() {
        cache = new ConcurrentHashMap<String, List<Field>>();
    }

    /**
     * Clears the cache
     *
     */
    public void clear() {
        cache.clear();
    }

    /**
     * Retrieves the single cache instance.
     *
     * @return
     */
    public static AuditableBeanPropertyCache getInstance() {
        return thisRef;
    }

    /**
     * Adds the a list of fields to the cache for the given class.
     *
     * @param c
     * @param fields
     */
    private List<Field> cacheClass(final Class c) {
        List<Field> fields = new ClassUtils().getFields(c);
        List<Field> annotatedFields = new ArrayList<Field>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(AuditableExpressionBeanProperties.class)
                    || field.isAnnotationPresent(AuditableBeanProperty.class)
                    || field.isAnnotationPresent(AuditableCompositeBeanProperty.class)) {
                annotatedFields.add(field);
            }
        }
        cache.putIfAbsent(c.getCanonicalName(), annotatedFields);
        return fields;
    }

    /**
     * Retreives the fields for the given class from the cache.
     *
     * @param c
     * @return
     */
    public List<Field> getClassFields(final Class c) {
        List<Field> results = cache.get(c.getCanonicalName());
        if (results == null) {
            results = cacheClass(c);
        }
        return results;
    }
}
