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
import abc.xyz.pts.bcs.common.exception.ApplicationException;

@Controller("userRoleAddController")
@RequestMapping("addUserRole.form")
public class UserRoleAddController extends AbstractUserRoleController
{
    private UserRoleService userRoleService;

    @ModelAttribute
    public void referenceData(final ModelMap model) {
        model.addAttribute("permissionList", Arrays.asList(UserPermissionType.values()));
    }
    
    @AuditableEvent(Event.MENU_SELECTION)
    @RequestMapping(value="/addUserRole", method = RequestMethod.GET)
    public String setupForm
        ( @ModelAttribute(USER_ROLE_COMMAND) final UserRoleDataCommand userRoleDataCommand
        , final BindingResult bindingResult
        , final SessionStatus status
        , final HttpSession session
        , final ModelMap model
        , final HttpServletRequest request
        , final Locale locale
        ) 
    {
        model.addAttribute(USER_ROLE_COMMAND, new UserRoleDataCommand(ACTION_ADD_USER_ROLE));
        return ADD_USER_ROLE;
    }

    
    @RequestMapping(value="/addUserRole", method = RequestMethod.POST, params="cancel")
    public String processCancelFromAdd
        ( final ModelMap model
        , final SessionStatus status
        , final HttpSession session
        )
    {
        status.setComplete();
        return REDIRECT_USER_ROLE_LIST;
    }
    
    @AuditableEvent(Event.ADD_ROLE)
    @RequestMapping(value="/addUserRole", method = RequestMethod.POST, params=ACTION_ADD_USER_ROLE)
    public String processAddUserRole
            ( @ModelAttribute(USER_ROLE_COMMAND) final UserRoleDataCommand userRoleDataCommand
            , final BindingResult errors
            , final HttpSession session
            , final ModelMap model
            ) 
    {
        getCommonsValidator().validate(userRoleDataCommand, errors);
        if (errors.getAllErrors().isEmpty()) {
            try{
                userRoleService.addRole(userRoleDataCommand.toUserRoleData());
                userRoleDataCommand.setUserRoleAdded(true);
                userRoleDataCommand.setAction(ACTION_ADD_USER_ROLE);
            } catch (ApplicationException e) {
                // pass the warning back to the screen.
                // the error message is in e.getErrorCode() - refers to data dictionary item
                errors.rejectValue("permissionList", e.getErrorCode());
            }
        }
        model.addAttribute(USER_ROLE_COMMAND, userRoleDataCommand);

        // return back to the role list
        return ADD_USER_ROLE;
    }
    
    @RequestMapping(value="/addUserRole", method = RequestMethod.POST, params = "reset")
    public String processReset
            ( final ModelMap model
            , final SessionStatus status
            , final HttpSession session
            )
    {
        status.setComplete();

        model.addAttribute(USER_ROLE_COMMAND, new UserRoleDataCommand(ACTION_ADD_USER_ROLE));
        return ADD_USER_ROLE;
    }

    public void setUserRoleService(final UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

}
