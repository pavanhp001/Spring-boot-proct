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

import java.util.Arrays;
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
import abc.xyz.pts.bcs.admin.web.command.UserRoleDataCommand;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableEvent;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.enums.UserPermissionType;

@Controller("userRoleEditController")
@RequestMapping("editUserRole.form")
public class UserRoleEditController extends AbstractUserRoleController
{
    private UserRoleService userRoleService;
    
    @ModelAttribute
    public void referenceData(final ModelMap model) {
        model.addAttribute("permissionList", Arrays.asList(UserPermissionType.values()));
    }
    
    @RequestMapping(value="/editUserRole", method=RequestMethod.GET, params="locale")
    public String changeLocale(final HttpSession session, final ModelMap model) 
    {
        UserRoleDataCommand cmd = (UserRoleDataCommand)session.getAttribute(USER_ROLE_COMMAND);
        model.addAttribute(USER_ROLE_COMMAND, cmd);
        return EDIT_USER_ROLE;
   }
    
    @RequestMapping(value="/editUserRole", method = RequestMethod.GET)
    public String setupFormForEdit
        ( @ModelAttribute(USER_ROLE_COMMAND) final UserRoleDataCommand cmd
        , final BindingResult bindingResult
        , final SessionStatus status
        , final HttpSession session
        , final ModelMap model
        , final HttpServletRequest request
        , final Locale locale
        ) 
    {
        UserRoleDataCommand roleCmd = (UserRoleDataCommand)session.getAttribute(USER_ROLE_COMMAND);
        
        // Ensure we edit latest data
        UserRoleDataCommand newCmd = new UserRoleDataCommand(userRoleService.findRoleByCode(roleCmd.getCode()));
        newCmd.setAction(ACTION_EDIT_USER_ROLE);
        
        model.addAttribute(USER_ROLE_COMMAND, newCmd);
        session.setAttribute(USER_ROLE_COMMAND, newCmd);
        
        return EDIT_USER_ROLE;
    }
    
    @AuditableEvent(Event.UPDATE_ROLE)
    @RequestMapping(value="/editUserRole", method = RequestMethod.POST, params="saveUserRole")
    public String procesSaveRole
        ( @ModelAttribute(USER_ROLE_COMMAND) final UserRoleDataCommand cmd
        , final BindingResult errors
        , final SessionStatus status
        , final HttpSession session
        , final ModelMap model
        , final HttpServletRequest request
        , final Locale locale
        )
    {
        cmd.setCode(cmd.getSelectedCode());
        getCommonsValidator().validate(cmd, errors);
        if (errors.getAllErrors().isEmpty()) 
        {
            userRoleService.updateRole(cmd.toUserRoleData());
            cmd.setAction(ACTION_EDIT_USER_ROLE);
            return REDIRECT_VIEW_USER_ROLE + "?updateSuccess=true" + "&code=" + cmd.getCode();
        }
        
        return EDIT_USER_ROLE;
    }
    
    @RequestMapping(value="/editUserRole", method = RequestMethod.POST, params="cancel")
    public String processCancelFromEdit
        ( @ModelAttribute(USER_ROLE_COMMAND) final UserRoleDataCommand cmd
        , final BindingResult errors
        , final SessionStatus status
        , final HttpSession session
        , final ModelMap model
        , final HttpServletRequest request
        , final Locale locale
        )
    {
        status.setComplete();
        
        return REDIRECT_VIEW_USER_ROLE + "?code=" + cmd.getSelectedCode();
    }
    

    public void setUserRoleService(final UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

}
