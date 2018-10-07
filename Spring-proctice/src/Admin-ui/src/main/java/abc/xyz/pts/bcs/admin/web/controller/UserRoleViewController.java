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
package abc.xyz.pts.bcs.admin.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import abc.xyz.pts.bcs.admin.business.UserRoleService;
import abc.xyz.pts.bcs.admin.web.command.UserRoleDataCommand;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableEvent;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.enums.UserPermissionType;
import abc.xyz.pts.bcs.common.ldap.dto.UserRoleData;

@Controller("userRoleViewController")
@RequestMapping("viewUserRole.form")
public class UserRoleViewController extends AbstractUserRoleController
{
    private UserRoleService userRoleService;

    @ModelAttribute
    public void referenceData(final ModelMap model) {
        model.addAttribute("permissionList", Arrays.asList(UserPermissionType.values()));
    }
    
    @RequestMapping(method=RequestMethod.GET, params="locale")
    public String changeLocale(final HttpSession session, final ModelMap model) 
    {
        UserRoleDataCommand cmd = (UserRoleDataCommand)session.getAttribute(USER_ROLE_COMMAND);
        model.addAttribute(USER_ROLE_COMMAND, cmd);
        return VIEW_USER_ROLE;
   }
    
    @AuditableEvent(Event.VIEW_ROLE)
    @RequestMapping(value="/viewUserRole", method = RequestMethod.GET)
    public String setupForm
        ( @ModelAttribute(USER_ROLE_COMMAND) final UserRoleDataCommand cmd
        , final BindingResult bindingResult
        , final SessionStatus status
        , final HttpSession session
        , final ModelMap model
        , final HttpServletRequest request
        , final Locale locale
        ) throws IllegalAccessException, InvocationTargetException 
    {
        // Get it it from session or from parameter
        UserRoleDataCommand roleData = cmd;
        if (StringUtils.isBlank(cmd.getCode())) {
            roleData = (UserRoleDataCommand)session.getAttribute(USER_ROLE_COMMAND);
        }

        UserRoleData userRole =  userRoleService.findRoleByCode(roleData.getCode());
        roleData = new UserRoleDataCommand(userRole);
        
        roleData.setAction(ACTION_VIEW_USER_ROLE);
        roleData.setUpdateSuccess(cmd.isUpdateSuccess());
        BeanUtils.copyProperties(cmd, roleData);
        
        model.addAttribute(USER_ROLE_COMMAND, roleData);
        session.setAttribute(USER_ROLE_COMMAND, roleData);
        
        return VIEW_USER_ROLE;
    }
    
    @RequestMapping(value="/viewUserRole", method = RequestMethod.POST, params="close")
    public String processClose
        ( final ModelMap model
        , final SessionStatus status
        , final HttpSession session
        )
    {
        clearSession(session);
        
        status.setComplete();
        return REDIRECT_USER_ROLE_LIST;
    }
    

    public void setUserRoleService(final UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }
    
    
    @RequestMapping(value="/viewUserRole", method=RequestMethod.POST, params="editUserRole")
    public String processEditRole
        ( @ModelAttribute(USER_ROLE_COMMAND) final UserRoleDataCommand cmd
        , final BindingResult errors
        , final SessionStatus status
        , final HttpSession session
        , final ModelMap model
        , final HttpServletRequest request
        , final Locale locale)
    {
          return REDIRECT_EDIT_USER_ROLE;
    }

}
