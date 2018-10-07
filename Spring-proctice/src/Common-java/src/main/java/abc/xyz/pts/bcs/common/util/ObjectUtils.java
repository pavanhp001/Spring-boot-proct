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
import java.util.Calendar;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.CalendarConverter;

/**
 * @author Deepesh.Rathore
 *
 */
public class ObjectUtils {

    static{
           registerDefaultCalendarConverter();
    }

    private static void registerDefaultCalendarConverter() {
        final Converter converter = new CalendarConverter(null);
        ConvertUtils.register(converter, Calendar.class);
    }

    public static void copyProperties(final Object dest, final Object orig){
        try {
            BeanUtils.copyProperties(dest, orig);
        } catch (final IllegalAccessException e) {
            thowCopyFailedException(dest, orig, e);
        } catch (final InvocationTargetException e) {
            thowCopyFailedException(dest, orig, e);
        } catch (final ConversionException e) {
            thowCopyFailedException(dest, orig, e);
        }
    }

    private static void thowCopyFailedException(final Object dest, final Object orig, final Exception e) {
        throw new CopyFailedException("Failed to copy properties from  "+orig + " into "+dest, e);
    }
}
