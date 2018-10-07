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
package abc.xyz.pts.bcs.common.controller.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import abc.xyz.pts.bcs.common.bean.UserDetails;
import abc.xyz.pts.bcs.common.business.lookup.LookupDataService;
import abc.xyz.pts.bcs.common.dao.utils.Constants;
import abc.xyz.pts.bcs.common.db.dto.FlightSearch;
import abc.xyz.pts.bcs.common.enums.UserPermissionType;
import abc.xyz.pts.bcs.common.util.UserRoleVerifierUtil;

/**
 * The Class ControllerUtil.
 */
public final class ControllerUtil
{
    private ControllerUtil()
    {
        super();
    }

    /** ServletRequestAttributes instance to access HttpServletRequest */
    private static ServletRequestAttributes servletRequestAttributes;

    /**
     * Checks if is user has view travel data role.
     *
     * @return true, if is user has view travel data role
     */
    public static boolean isUserHasViewTravelDataRole(){
        boolean outputFlag = false;
        servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes != null) {
            final HttpServletRequest request = servletRequestAttributes.getRequest();
            final UserDetails userDetails = (UserDetails) request.getAttribute(Constants.USER_PROFILE_KEY);
            final List<String> roles = userDetails.getRolesAsList();
            outputFlag = !(roles.contains(UserPermissionType.REFERRAL_READ.getPermission()) || roles.contains(UserPermissionType.REFERRAL_UNQUALIFIED_READ.getPermission()));
        }
        return outputFlag;
    }

    /**
     * If workingAirport set then if one airport is set, set other to working airport
     * @param searchCommand
     * @param workingAirport
     */
    public static void setWorkingAirport(
            final FlightSearch searchCommand,
            final String workingAirport) {

        if (StringUtils.isEmpty(workingAirport)){
            return;
        }

        if ((StringUtils.isNotEmpty(searchCommand.getArrivalAirport()))
                || StringUtils.isNotEmpty(searchCommand.getDepartureAirport())){ // arr or dep airport entered

            if (StringUtils.isEmpty(searchCommand.getArrivalAirport())
                    && !workingAirport.equals(searchCommand.getDepartureAirport())){ // no arr airport entered, dep not working airport
                searchCommand.setArrivalAirport(workingAirport);

            } else if (StringUtils.isEmpty(searchCommand.getDepartureAirport()) // no dep airport entered, arr not working airport
                    && !workingAirport.equals(searchCommand.getArrivalAirport())) {
                searchCommand.setDepartureAirport(workingAirport);
            }
        }

    }

    /**
     * This method set the default date range values for flight search
     * @param searchCommand
     * @param lookupDataService
     */

    public static void setDefaultDateRange(final FlightSearch searchCommand, final LookupDataService lookupDataService){
        if(UserRoleVerifierUtil.isUserInRole(UserRoleVerifierUtil.ALERT_OFFICER_ROLE)){
            searchCommand.setDefaultDateRangeFrom(lookupDataService.getStringDateRangeDefaultFromDateForAlertOfficer());
        }
        else{
            searchCommand.setDefaultDateRangeFrom(lookupDataService.getStringDateRangeDefaultFromDate());
        }
        searchCommand.setDefaultDateRangeTo(lookupDataService.getStringDateRangeDefaultToDate());
    }
}
