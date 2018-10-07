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
package abc.xyz.pts.bcs.common.util;

import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * <p>
 * This utility is used for various display concerns in the Jasper Report.
 * For eg : If a list of values need to be displayed as a comma separated
 * internationalized string a function in this class is called from the jasper reports file(jrxml)
 * </p>
 *
 * @author Kasi.Subramaniam
 * @author Sai
 */
public final class ReportUtil {

    private static final String DOT = ".";
    private static final String COMMA = "comma";

    // prevent instantiation.
    private ReportUtil() {
    }

    /**
     * <p>Formats a {@link String} to it's value defined in the data dictionary to be called from the Jasper Reports.</p>
     * @param resourceKey - the resource key eg : the data dictionary key.
     * @param value - the value (the value which will be concatenated with the key and looked up in the resource bundle)
     * @param bundle - {@link ResourceBundle}
     * @return looked up value as {@link String}. If no resource is found in the {@link ResourceBundle} the passed in "value" is returned.
     */
    public static String formatString(final String resourceKey, final String value, final ResourceBundle bundle) {
        shouldNotBeNull(resourceKey , bundle);
        String resourceValue = "";
        if(value != null){
            resourceValue = lookupResource(bundle, resourceKey + DOT + value);
            if(resourceValue == null){
                return value;
            }
        }
        return resourceValue;
    }

    /**
     * <p>Formats a {@link List} of {@link String} to it's value defined in the data dictionary to be called from the Jasper Reports.
     * Each elements of  {@link List} will concatenated with the resource key and lookedup in the {@link ResourceBundle}. If missing, the value is used.
     * The resulting value is then formed as a comma delimited {@link String}.
     * </p>
     * @param resourceKey - the resource key eg : the data dictionary key.
     * @param value - the value (the value which will be concatenated with the key and looked up in the resource bundle)
     * @param bundle - {@link ResourceBundle}
     * @return looked up value as {@link String}. If no resource is found in the {@link ResourceBundle} the passed in "value" is returned.
     */
    public static String formatListOfStrings(final String resourceKey, final List<String> listOfValues, final ResourceBundle bundle) {
        return format(resourceKey, listOfValues, bundle, lookupResource(bundle, COMMA));
    }

    /**
     * <p>Formats a {@link List} of {@link String} to it's value defined in the data dictionary to be called from the Jasper Reports.
     * Each elements of  {@link List} will concatenated with the resource key and lookedup in the {@link ResourceBundle}. If missing, the value is used.
     * The resulting value is then formed delimited by the  CUSTOM DELIMITER  passed in {@link String}.
     * </p>
     * @param resourceKey - the resource key eg : the data dictionary key.
     * @param value - the value (the value which will be concatenated with the key and looked up in the resource bundle)
     * @param bundle - {@link ResourceBundle}
     * @param delimiter - the delimiter passed in from Jasper
     * @return looked up value as {@link String}. If no resource is found in the {@link ResourceBundle} the passed in "value" is returned.
     */
    public static String formatListOfStrings(final String resourceKey, final List<String> listOfValues, final ResourceBundle bundle, final String delimiter) {
        return format(resourceKey, listOfValues, bundle, delimiter);
    }

    private static String format(final String resourceKey,    final List<String> listOfValues, final ResourceBundle bundle , final String delimiter) {
        shouldNotBeNull(resourceKey , bundle);

        final StringBuilder result = new StringBuilder();
        if(listOfValues != null){
            int index = 0;
            final int collectionSize = listOfValues.size();
            for (final String value : listOfValues) {
                final String resourceValue = formatString(resourceKey, value  , bundle );
                result.append(resourceValue);

                addDelimiter(delimiter , result , index , collectionSize);
                index ++;
            }
        }
        return result.toString();
    }

    private static void addDelimiter(final String delimiter, final StringBuilder result, final int currentIndex, final int collectionSize) {
        if (collectionSize - 1 != currentIndex) {
            result.append(delimiter);
        }
    }

    private static String lookupResource(final ResourceBundle bundle, final String resourceKey) {
        try {
            if (bundle != null) {
                return bundle.getString(resourceKey);
            }
        } catch (final MissingResourceException e) {
            // Swallowed intentionally, because we want to gracefully default the resource value if not found.
        }
        return null;
    }

    private static void shouldNotBeNull(final Object ...params) {
        for(final Object param : params) {
            if(param == null){
                throw new IllegalArgumentException();
            }
        }
    }
}
