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

import org.apache.commons.lang.StringUtils;

public class WebStringUtils {
    private static final String WILDCARD_FROM = "*";
    private static final String WILDCARD_TO = "%";
    private static final String SINGLE_CHAR_FROM = "?";
    private static final String SINGLE_CHAR_TO = "_";


    public static boolean containsWildCard(final String searchString){
        return searchString == null ? false : searchString.contains(WILDCARD_FROM);
    }


    public static String replaceSearchCharacters(final String searchString) {
        if (searchString == null) {
            return searchString;
        }
        String retVal = searchString.replace(WILDCARD_FROM, WILDCARD_TO);
        retVal = retVal.replace(SINGLE_CHAR_FROM, SINGLE_CHAR_TO);
        return retVal;
    }

    public static String convertColumNameToCamelCase(final String columnName) {
        if (columnName != null) {
            final String[] words = columnName.toLowerCase().split("_");
            final StringBuilder builder = new StringBuilder();
            for (final String word : words) {
                builder.append(StringUtils.capitalize(word));
            }
            return builder.toString();
        }
        return null;
    }

    public static boolean isStringValidWildCardSearch(final String searchString) {
        if (searchString == null)
         {
            return true;// assume null is true
        }
        final int noOfStar = StringUtils.countMatches(searchString, WILDCARD_FROM);
        if (noOfStar > 1) {
            return false;
        }
        final int noOfQuestion = StringUtils.countMatches(searchString, SINGLE_CHAR_FROM);
        if (noOfQuestion > 1) {
            return false;
        }
        if ((noOfStar > 0) && (noOfQuestion > 0)) {
            return false;
        }
        final String reverse = StringUtils.reverse(searchString);
        final String last = "" + reverse.charAt(0);
        if ((noOfStar > 0) && (!WILDCARD_FROM.equals(last))) {
            return false;
        }
        if ((noOfQuestion > 0) && (!SINGLE_CHAR_FROM.equals(last))) {
            return false;
        }
        return true;
    }

    public static String timeLeftpadZeros(final String time) {
        if (StringUtils.isNotEmpty(time) && time.indexOf(":") != -1) {
            final String padWith = "0";
            String hours = time.substring(0, time.indexOf(":"));
            String minutes = time.substring(time.indexOf(":")+1);
            final int totalChars = 2;
            if (hours.length() < totalChars) {
                while (hours.length() < totalChars) {
                    hours = padWith + hours;
                }
            }
            if (minutes.length() < totalChars) {
                while (minutes.length() < totalChars) {
                    minutes = padWith + minutes;
                }
            }
            return hours+":"+minutes;
        }
        return time;
    }

    public static String flightLeftpadZeros(String flight) {
        if (StringUtils.isNotEmpty(flight)) {
            final String padWith = "0";
            int totalChars = 4;
            if(flight.charAt(flight.length()-1) == 'C'){
                totalChars = 5;
            }
            if (flight.length() < totalChars) {
                while (flight.length() < totalChars) {
                    flight = padWith + flight;
                }
            }
        }
        return flight;
    }

    public static String getFullName(final String firstName, final String lastName, final String middleName){
        final StringBuilder fullName = new StringBuilder();
        if(!StringUtils.isEmpty(lastName)){
            fullName.append(lastName);
        }
        if(!StringUtils.isEmpty(lastName) && (!StringUtils.isEmpty(firstName) || !StringUtils.isEmpty(middleName))){
            fullName.append(", ");
        }
        if(!StringUtils.isEmpty(firstName)){
            fullName.append(firstName).append(" ");
        }
        if(!StringUtils.isEmpty(middleName)){
            fullName.append(middleName);
        }
        return fullName.toString().trim();

    }

    public static String convertStringToDbWildcard(final String value){
        return StringUtils.isNotEmpty(value) ?    WILDCARD_TO+value+WILDCARD_TO : value;

    }
}
