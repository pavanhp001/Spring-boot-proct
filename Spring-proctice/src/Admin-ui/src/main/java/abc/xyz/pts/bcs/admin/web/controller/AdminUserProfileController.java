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
package abc.xyz.pts.bcs.admin.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springmodules.validation.commons.DefaultBeanValidator;

import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.admin.web.command.Action;
import abc.xyz.pts.bcs.admin.web.command.AdminUserProfileCommand;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableEvent;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.bean.UserDetails;
import abc.xyz.pts.bcs.common.dao.utils.Constants;
import abc.xyz.pts.bcs.common.enums.UserPermissionType;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

@Controller("adminUserProfileController")
@RequestMapping(value = { "adminUserProfile.cform" })
public class AdminUserProfileController extends AdminUserCommonController {

    @Autowired
    protected DefaultBeanValidator commonsValidator;
    @Autowired
    private PropertyEditorRegistrar propertyEditorRegistrar;
    @Autowired(required = true)
    private HttpServletRequest request;

    private static final String VIEW_COMMAND = "adminUserProfileCommand";
    private static final String TABLE_COMMAND = "viewUserTableCommand";
    private static final String USER = "user";
    private static final String SUCCESS_VIEW = "adminUserProfile.view";

    private static final String HIDE_UNALLOCATED_ROLES = "hideUnallocatedRoles";
    private static final String DISPLAY_MODE = "displayMode";

    @Override
    @ModelAttribute
    public void referenceData(final ModelMap model) {
        super.referenceData(model);
        model.addAttribute(HIDE_UNALLOCATED_ROLES, true);
        model.addAttribute(DISPLAY_MODE, true);
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        this.propertyEditorRegistrar.registerCustomEditors(binder);
    }

    @ModelAttribute(VIEW_COMMAND)
    public AdminUserProfileCommand newCommand() {
        AdminUserProfileCommand adminVewUser = new AdminUserProfileCommand();
        adminVewUser.setAction(Action.VIEW);
        adminVewUser.setUser(new User());
        return adminVewUser;
    }

    @ModelAttribute(TABLE_COMMAND)
    public TableActionCommand newTableCommand() {
        return new TableActionCommand();
    }

    @AuditableEvent(Event.VIEW_USER)
    @RequestMapping(method = RequestMethod.POST)
    public String setupForm(@ModelAttribute(TABLE_COMMAND) final TableActionCommand tableCommand
            , @ModelAttribute(VIEW_COMMAND) final AdminUserProfileCommand command
            , final BindingResult result
            , final SessionStatus status
            , final HttpSession session
            , final ModelMap model) throws Exception {
        status.setComplete();
        removeSessionCommands(session);
        retrieveScreenData(model, command, tableCommand, session);
        return SUCCESS_VIEW;
    }

    @AuditableEvent(Event.MENU_SELECTION)
    @RequestMapping(method = RequestMethod.GET)
    public String showUserProfile(@ModelAttribute(TABLE_COMMAND) final TableActionCommand tableCommand
            , @ModelAttribute(VIEW_COMMAND) final AdminUserProfileCommand command
            , final BindingResult result
            , final SessionStatus status
            , final HttpSession session
            , final ModelMap model) throws Exception {
        status.setComplete();
        removeSessionCommands(session);
        retrieveScreenData(model, command, tableCommand, session);
        return SUCCESS_VIEW;
    }

    @RequestMapping(method = RequestMethod.GET, params = "locale")
    public String changeLocale(final HttpSession session,
            final ModelMap model) throws Exception {
        // Read command objects from session to ensure we see the original values
        AdminUserProfileCommand command = (AdminUserProfileCommand) session.getAttribute(VIEW_COMMAND);
        if (command != null) {
            TableActionCommand tableCommand = (TableActionCommand) session.getAttribute(TABLE_COMMAND);
            retrieveScreenData(model, command, tableCommand, session);
        }

        return SUCCESS_VIEW;
    }

    protected void retrieveScreenData(final ModelMap model, final AdminUserProfileCommand adminUserProfileCommand,
            final TableActionCommand viewUserTableCommand, final HttpSession session) throws Exception {
        User user = null;
        String username = null;

        // Below is a condition to check, which user information need to display like searched user OR logged in user
        if (adminUserProfileCommand.getUser() != null && StringUtils.isNotEmpty(adminUserProfileCommand.getUser().getUsername())) {
            user = getAdminService().getUser(adminUserProfileCommand.getUser().getUsername());
        } else {
            username = request.getUserPrincipal().getName();
            adminUserProfileCommand.getUser().setUsername(username);
            user = getAdminService().getUser(adminUserProfileCommand.getUser().getUsername());
        }

        adminUserProfileCommand.setUser(user);
        // check for SUP or ADM role
        adminUserProfileCommand.setUserInAdminRole(currentUserIsInAdminRole(request));
        // Add command to model which are required for JSP
        model.addAttribute(VIEW_COMMAND, adminUserProfileCommand);
        model.addAttribute(TABLE_COMMAND, viewUserTableCommand);
        model.addAttribute(USER, adminUserProfileCommand.getUser());

        session.setAttribute(VIEW_COMMAND, adminUserProfileCommand);
        session.setAttribute(TABLE_COMMAND, viewUserTableCommand);

    }

    private boolean currentUserIsInAdminRole(final HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) request.getAttribute(Constants.USER_PROFILE_KEY);
        List<String> roles = userDetails.getRolesAsList();
        return roles.contains(UserPermissionType.USER_WRITE.getPermission());
    }

    private void removeSessionCommands(final HttpSession session) {
        session.removeAttribute(VIEW_COMMAND);
        session.removeAttribute(TABLE_COMMAND);
    }

}
