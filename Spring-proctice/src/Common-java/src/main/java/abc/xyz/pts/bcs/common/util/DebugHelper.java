/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;

/**
 * Helper class for debug logging.
 *
 * @author Mohammed.Motlib-Siddiqui
 *
 */
public final class DebugHelper
{
    private DebugHelper()
    {
        super();
    }

    /**
     * Retun result of calling public/protected get methods.
     *
     * This ignores get method with parameters.
     *
     *
     * @param obj
     * @param LOG
     * @return
     */
    public static String debugGetMethods(final Object obj, final Logger log)
    {
        StringBuilder buf = new StringBuilder();

        try {
            Class cls = obj.getClass();
            buf.append("\n");
            buf.append(obj.getClass().getName() + " [\n");

            Method[] methodArray = cls.getMethods();
            for (Method method : methodArray)
            {
                if (method.getParameterTypes().length > 0)
                {
                    continue;
                }

                // is public / protected?
                boolean canDisplay = false;
                if (Modifier.isPublic(method.getModifiers()) || Modifier.isProtected(method.getModifiers()))
                {
                    canDisplay = true;
                }

                if (method.getName().startsWith("get") && canDisplay)
                {
                    buf.append("\t");
                    buf.append(method.getName());
                    buf.append("()");
                    buf.append(" = ");

                    Object rtnObj = method.invoke(obj);
                    buf.append(rtnObj);
                    if (rtnObj instanceof java.util.Calendar)
                    {
                        CalendarUtils.calToString((java.util.Calendar)rtnObj, "dd-MMM-yyyy HH:mm:ss") ;
                    }

                    buf.append("\n");
                }
            }

            buf.append("]\n");

        }
        catch (IllegalAccessException e)
        {
            log.warn("Could not format this instance!", e);
        }
        catch (InvocationTargetException e)
        {
            log.warn("Could not format this instance!", e);
        }

        return buf.toString();
    }
}
