/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.audit.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassUtils {

    /**
     * Returns all the public, private and protected fields for the given class. Note this may return duplicate fields,
     * as subclasses can define member variables with the same name. We could do extra checking to see if accessors and
     * mutators exist.
     *
     * @param c
     * @return A list of fields
     */
    public List<Field> getFields(final Class<?> c) {
        List<Field> fields = new ArrayList<Field>();
        Class<?> clazz = c;
        do {
            Field[] declaredFields = clazz.getDeclaredFields();
            fields.addAll(Arrays.asList(declaredFields));
            clazz = clazz.getSuperclass();
        } while (clazz != null);
        return fields;
    }
}
