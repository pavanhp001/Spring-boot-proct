/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2009
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.web.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;

/**
 * Helper class to define the mappings for functions to user roles, and to check the user roles for the current user.
 */
public final class CheckUserRole {

    private CheckUserRole () {
        
    }
    
    /**
     * Check that the current user has the required user roles
     *
     * @param checkRoles
     *            roles to check
     * @param userRoles
     *            roles for current user
     * @return result if any of the checkRoles match the userRoles
     */
    public static boolean checkUserRole(final String[] checkRoles, final HttpServletRequest request) {
        if (!ArrayUtils.isEmpty(checkRoles)) {
            // Loop through required roles and check against users' roles
            for (final String checkRole : checkRoles) {
                if (checkRole != null && request.isUserInRole(checkRole)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check that the current user has the required user roles
     *
     * @param checkRoles
     *            roles to check
     * @param userRoles
     *            roles for current user
     * @return result if any of the checkRoles match the userRoles
     */
    public static boolean checkUserRole(final String[] checkRoles, final String functionName) {
        if (!ArrayUtils.isEmpty(checkRoles) && functionName != null) {
            // Loop through required roles and check against users' roles
            final String[] functionRoles = getFunctionRoles(functionName);
            for (final String checkRole : checkRoles) {
                if (ArrayUtils.contains(functionRoles, checkRole)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get the mapping from function name to list of roles.
     *
     * @param function
     *            name
     * @return list of roles
     */
    public static String[] getFunctionRoles(final String function) {
        final List <String> permissionList = new ArrayList<String>();
        String [] result = null;
        if (function != null) {
            final String permissionCSV =System.getProperty(function);
            if (permissionCSV != null && !permissionCSV.isEmpty()) {
                final StringTokenizer tokenizedRoles = new StringTokenizer(permissionCSV , ",");
                       while (tokenizedRoles.hasMoreElements()) {
                           permissionList.add(tokenizedRoles.nextToken().trim().toUpperCase());
                       }
                    result = permissionList.toArray(new String[permissionList.size()]);
            }
        }
        return result;
    }

    /**
     * @param roleMap
     *            to set
     */
    public static void setFunctionalRoles(final Map<String, String[]> roleMap) {
        // TODO remove
    }


}
