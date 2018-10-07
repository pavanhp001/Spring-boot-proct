/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2011
 * All rights reserved.
 */
package abc.xyz.pts.bcs.admin.web.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import abc.xyz.pts.bcs.admin.business.UserRoleService;
import abc.xyz.pts.bcs.admin.web.command.UserRoleListCommand;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableEvent;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.ldap.dto.UserRoleData;

@Controller("userRoleListController")
@RequestMapping("userRoleList.form")
public class UserRoleListViewController extends AbstractUserRoleController
{
    private UserRoleService userRoleService;
    

    /**
     * On initial display of the page get all roles from LDAP.
     * 
     * @param userRoleListCmd
     * @param bindingResult
     * @param status
     * @param session
     * @param model
     * @param request
     * @param locale
     * @return
     */
    @AuditableEvent(Event.MENU_SELECTION)
    @RequestMapping(value="/userRoleList", method=RequestMethod.GET)
    public String setupForm
        ( @ModelAttribute(USER_ROLE_LIST_COMMAND) final UserRoleListCommand userRoleListCmd
        , final BindingResult bindingResult
        , final SessionStatus status
        , final HttpSession session
        , final ModelMap model
        , final HttpServletRequest request
        , final Locale locale
        ) 
    {
        List<UserRoleData> userRoleList = userRoleService.findAllRoles();
        
        // prepare to return roles
        userRoleListCmd.setUserRoleList(userRoleList);
        model.addAttribute(USER_ROLE_LIST_COMMAND, userRoleListCmd);
        
        return LIST_USER_ROLE;
    }
    
    @RequestMapping(value="/userRoleList", method=RequestMethod.POST, params=ACTION_ADD_USER_ROLE)
    public String processAddUserRole 
        ( @ModelAttribute(USER_ROLE_LIST_COMMAND) final UserRoleListCommand userRoleListCmd
        , final BindingResult bindingResult
        , final SessionStatus status
        , final HttpSession session
        , final ModelMap model
        , final HttpServletRequest request
        , final Locale locale
        )
    {
        return REDIRECT_ADD_USER_ROLE;
    }
    
    @RequestMapping(value="/userRoleList", method=RequestMethod.POST, params="selectedRowCode")
    public String processViewUserRole
        ( @ModelAttribute(USER_ROLE_LIST_COMMAND) final UserRoleListCommand userRoleListCmd
        , final BindingResult errors
        , final HttpSession session
        , final ModelMap model
        , final HttpServletRequest request
        , final Locale locale
        ) 
    {
        return REDIRECT_VIEW_USER_ROLE + "?code=" + userRoleListCmd.getSelectedRowCode();
    }
    
    public void setUserRoleService(final UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

}
