/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2012
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.util;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import abc.xyz.pts.bcs.common.bean.UserDetails;
import abc.xyz.pts.bcs.common.dao.utils.Constants;

public class UserRoleVerifierUtil {

    /** The Constant ALERT_OFFICER_ROLE. */
    public static final String ALERT_OFFICER_ROLE = "INT";
    /** The Constant TRAVEL_DATA_READ_PERMISSION_ROLES */
    public static final String TRAVEL_DATA_READ_PERMISSION_ROLES = "travel.data.read.permission.roles";

    /** ServletRequestAttributes instance to access HttpServletRequest */
    private static ServletRequestAttributes servletRequestAttributes;

    /**
     * Checks if is user has alert officer role.
     *
     * @return true, if is user has alert officer role
     */
    public static boolean isUserInRole(final String role){
        boolean outputFlag = false;
        servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes != null) {
            final HttpServletRequest request = servletRequestAttributes.getRequest();
            final UserDetails userDetails = (UserDetails) request.getAttribute(Constants.USER_PROFILE_KEY);
            final List<String> userRoles = userDetails.getRolesAsList();
            outputFlag = userRoles.contains(role);
            if(outputFlag == true){
                final String travelDataReadPermissionRoles = getTravelDataReadPermissionRoles();
                if(travelDataReadPermissionRoles!=null){
                    final String[] travelDataReadPermRoles = travelDataReadPermissionRoles.split(",");
                        for(final String tdrpRole : travelDataReadPermRoles){
                            if(userRoles.contains(tdrpRole)){
                                outputFlag = false;
                                break;
                            }
                        }
                    }
                }
            }
        return outputFlag;
    }

    /**
     * Fetch Travel Data Read Permission Roles from Environment.
     */
    public static String getTravelDataReadPermissionRoles(){
           return System.getProperty(TRAVEL_DATA_READ_PERMISSION_ROLES);
    }
}
