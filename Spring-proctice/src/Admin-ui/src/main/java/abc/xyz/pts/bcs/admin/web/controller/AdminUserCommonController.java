/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.admin.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.ModelMap;

import abc.xyz.pts.bcs.admin.business.AdminService;
import abc.xyz.pts.bcs.admin.business.UserRoleService;
import abc.xyz.pts.bcs.admin.dto.Airport;
import abc.xyz.pts.bcs.common.ldap.dto.UserRoleData;

/**
 *
 * @author John.Templer
 *
 *
 */
public abstract class AdminUserCommonController {

    protected static final String VALID_AIRPORTS = "validAirports";

    protected static final String ROLE_DESC_MAP = "roleMap";
    
    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRoleService userRoleService;
    
    protected AdminService getAdminService() {
        return adminService;
    }

    protected UserRoleService getUserRoleService() {
        return userRoleService;
    }

    protected void referenceData(final ModelMap model) {
        List<Airport> validAirports = getAdminService().getValidAirports();
        List<UserRoleData> userRoleList = getUserRoleService().findAllRoles();
        Map<String, String> roleDescriptionMap = createRoleDescriptionMap(userRoleList);
        model.addAttribute(VALID_AIRPORTS, validAirports);
        model.addAttribute(ROLE_DESC_MAP, roleDescriptionMap);
    }
    
    private Map<String, String> createRoleDescriptionMap(final List<UserRoleData> userRoleList) {
        Locale locale = LocaleContextHolder.getLocale();
        String localeLanguage = locale.getLanguage(); 
        Map<String, String> roleDescriptionMap = new HashMap<String, String>();
        if (userRoleList != null) {
            for (UserRoleData userRole : userRoleList) {                	
                if (userRole.getDescriptionInLang()!=null) {
                	roleDescriptionMap.put(userRole.getCode(), (Locale.ENGLISH.getLanguage().equals(localeLanguage) ? userRole.getDescriptionInEnglish() : userRole.getDescriptionInLang()));
                } else {
            		roleDescriptionMap.put(userRole.getCode(), userRole.getDescriptionInEnglish());
                }
            }
        }
        return roleDescriptionMap;
    }

}
