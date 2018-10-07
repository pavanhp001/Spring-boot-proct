/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */

package abc.xyz.pts.bcs.admin.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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
import abc.xyz.pts.bcs.admin.exception.InvalidAirportException;
import abc.xyz.pts.bcs.admin.exception.UserExistsException;
import abc.xyz.pts.bcs.admin.web.command.Action;
import abc.xyz.pts.bcs.admin.web.command.AdminUser;
import abc.xyz.pts.bcs.admin.web.command.AdminUserAddCommand;
import abc.xyz.pts.bcs.admin.web.validator.AdminUserAddValidator;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableEvent;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

/**
 * Controller that allows a user to add a new User
 */

@Controller("adminUserAddController")
@RequestMapping(value = { "adminUserAdd.form" })
public class AdminUserAddController extends AdminUserCommonController {

    private static final String TABLE_COMMAND = "addUserTableCommand";
    private static final String ADD_COMMAND = "addUserCommand";
    private static final String USER = "user";

    private static final String HIDE_STATUS = "hideStatus";
    private static final String SUCCESS_VIEW = "adminUserAdd.view";
    private static final String SEARCH_FORM = "redirect:/adminUserSearch.form";

    @Autowired
    protected DefaultBeanValidator commonsValidator;

    @Autowired
    private PropertyEditorRegistrar customPropertyEditorRegistrar;
    @Autowired
    private AdminUserAddValidator adminUserAddValidator;

    @Override
    @ModelAttribute
    public void referenceData(final ModelMap model) {
        super.referenceData(model);
        model.addAttribute(HIDE_STATUS, true);
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        this.customPropertyEditorRegistrar.registerCustomEditors(binder);
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute(ADD_COMMAND)
    public AdminUserAddCommand newCommand() {
        AdminUserAddCommand adminAddUser = new AdminUserAddCommand();
        adminAddUser.setAction(Action.VIEW);
        adminAddUser.setUser(new User());
        return adminAddUser;
    }

    @ModelAttribute(TABLE_COMMAND)
    public TableActionCommand newTableCommand() {
        return new TableActionCommand();
    }

    @AuditableEvent(Event.MENU_SELECTION)
    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(@ModelAttribute(TABLE_COMMAND) final TableActionCommand tableCommand
            , @ModelAttribute(ADD_COMMAND) final AdminUser command
            , final BindingResult result
            , final SessionStatus status
            , final HttpSession session
            , final ModelMap model) throws Exception {
        removeSessionCommands(session);
        status.setComplete();
        model.addAttribute(ADD_COMMAND, command);
        model.addAttribute(TABLE_COMMAND, tableCommand);

        return SUCCESS_VIEW;
    }

    @AuditableEvent(Event.CREATE_NEW_USER)
    @RequestMapping(method = RequestMethod.POST, params = "add")
    public String processAdd(@ModelAttribute(TABLE_COMMAND) final TableActionCommand tableCommand
            , @ModelAttribute(ADD_COMMAND) final AdminUserAddCommand command
            , final BindingResult errors
            , final HttpSession session
            , final ModelMap model) throws Exception {
        commonsValidator.validate(command, errors);
        adminUserAddValidator.validate(command, errors);
        if (errors.getAllErrors().isEmpty()) {
            try {
                getAdminService().addUser(command.getUser());
                command.setUserAdded(true);
            } catch (UserExistsException uee) {
                errors.rejectValue("user.username", "user.already.exists");
            } catch (InvalidAirportException iae) {
                errors.rejectValue("user.location", "errors.invalid.airport.code", new Object[] { iae.getAirport() },
                        null);
            }
        }
        model.addAttribute(ADD_COMMAND, command);
        model.addAttribute(TABLE_COMMAND, tableCommand);
        model.addAttribute(USER, command.getUser());

        return SUCCESS_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST, params = "reset")
    public String cancel(final ModelMap model
            , final SessionStatus status
            , final HttpSession session) {
        removeSessionCommands(session);
        status.setComplete();
        // ** set up default value
        AdminUserAddCommand cmd = new AdminUserAddCommand();
        model.addAttribute(ADD_COMMAND, cmd);
        return SUCCESS_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST, params = "search")
    public String search(final ModelMap model
            , final SessionStatus status
            , final HttpSession session) {
        removeSessionCommands(session);
        status.setComplete();
        // ** set up default value
        return SEARCH_FORM;
    }

    private void removeSessionCommands(final HttpSession session) {
        session.removeAttribute(TABLE_COMMAND);
        session.removeAttribute(ADD_COMMAND);
    }
}
