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

public class ExpressionResolver {
    private static final String GETTER_PREFIX = "get";
    private static final String EXPRESSION_SEPARATOR = ".";

    /**
     * A recursive method that resolves a chain of java bean properties to a method and invokes it.
     *
     * @param startPoint
     * @param expression
     * @return
     * @throws Exception
     */

    public static Object resolve(final Object startPoint, final String expression) throws Exception {
        Object result = null;
        if (startPoint != null) {
            int dotIndex = expression.indexOf(EXPRESSION_SEPARATOR);
            if (dotIndex > -1) {
                // recurse down the object property tree...
                String currentObjPropertyName = expression.substring(0, dotIndex);
                String expTmp = expression.substring(dotIndex + 1);
                Object tmpResult = callPropertyGetter(currentObjPropertyName, startPoint);
                result = resolve(tmpResult, expTmp);
            } else {
                result = callPropertyGetter(expression, startPoint);
            }
        }
        return result;
    }

    /**
     * Finds a getter for a property on a target class and invokes it.
     *
     * @param propertyName
     * @param target
     * @return
     * @throws Exception
     *             if the getter is not found.
     */
    public static Object callPropertyGetter(final String propertyName, final Object target) throws Exception {
        Object result = null;
        Class targetClass = target.getClass();
        String methodName = GETTER_PREFIX + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        MethodCache methodCache = MethodCache.getInstance();
        Method getterMethod = methodCache.getMethod(methodName, targetClass);
        if (getterMethod == null) {
            Method[] methods = targetClass.getMethods();
            // we have to loop as the method might be declared in a super class.
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    getterMethod = method;
                    break;
                }
            }
            if (getterMethod == null) {
                throw new Exception("Unable to find method '" + methodName + "' on class '" + targetClass.getName()
                        + "'");
            } else {
                methodCache.putMethod(targetClass, getterMethod);
            }
        }
        result = getterMethod.invoke(target, new Object[0]);
        return result;
    }
}
