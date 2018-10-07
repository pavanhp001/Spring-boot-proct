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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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

import abc.xyz.pts.bcs.admin.business.UserStatus;
import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.admin.exception.InvalidAirportException;
import abc.xyz.pts.bcs.admin.exception.UserMissingException;
import abc.xyz.pts.bcs.admin.web.command.Action;
import abc.xyz.pts.bcs.admin.web.command.AdminUserUpdateCommand;
import abc.xyz.pts.bcs.admin.web.validator.AdminUserUpdateValidator;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableEvent;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.dao.utils.Constants;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

/**
 * @author
 * @version: $Id: AdminUserUpdateController.java 806 2011-09-22 15:48:27 $
 */
@Controller("adminUserUpdateController")
@RequestMapping("adminUserUpdate.form")
public class AdminUserUpdateController extends AdminUserCommonController {

    @Autowired
    protected DefaultBeanValidator commonsValidator;
    @Autowired
    private PropertyEditorRegistrar propertyEditorRegistrar;

    @Autowired
    private AdminUserUpdateValidator adminUserUpdateValidator;
    private static final String SUCCESS_VIEW = "adminUserUpdate.view";
    private static final String FORWARD_TO_USER_PROFILE = "forward:/adminUserProfile.cform";
    private static final String UPDATE_CONFLICT_MSG = "record.update.conflict";
    private static final String UPDATE_COMMAND = "adminUserUpdateCommand";
    private static final String TABLE_COMMAND = "updateUserTableCommand";
    private static final String USER = "user";
    private static final String EDIT_MODE = "editMode";
    private static final String PWD_RESET_ROLE = "PWDRESET";

    @Override
    @ModelAttribute
    public void referenceData(final ModelMap model) {
        super.referenceData(model);
        model.addAttribute(EDIT_MODE, true);
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        this.propertyEditorRegistrar.registerCustomEditors(binder);
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute(UPDATE_COMMAND)
    public AdminUserUpdateCommand newCommand() {
        AdminUserUpdateCommand adminUpdateUser = new AdminUserUpdateCommand();
        adminUpdateUser.setAction(Action.VIEW);
        adminUpdateUser.setUser(new User());
        return adminUpdateUser;
    }

    @ModelAttribute(TABLE_COMMAND)
    public TableActionCommand newTableCommand() {
        return new TableActionCommand();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String setupForm(@ModelAttribute(TABLE_COMMAND) final TableActionCommand tableCommand
            , @ModelAttribute(UPDATE_COMMAND) final AdminUserUpdateCommand command
            , final BindingResult result
            , final SessionStatus status
            , final HttpSession session
            , final ModelMap model) throws Exception {
        removeSessionCommands(session);
        status.setComplete();
        // retrieve screen data to show
        retrieveScreenData(model, command, tableCommand, session);
        return SUCCESS_VIEW;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String setupFormGet(@ModelAttribute(TABLE_COMMAND) final TableActionCommand tableCommand
            , @ModelAttribute(UPDATE_COMMAND) final AdminUserUpdateCommand command
            , final BindingResult result
            , final SessionStatus status
            , final HttpSession session
            , final ModelMap model) throws Exception {
        return setupForm(tableCommand, command, result, status, session, model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "update")
    public String updateUser(@ModelAttribute(TABLE_COMMAND) final TableActionCommand tableCommand
            , @ModelAttribute(UPDATE_COMMAND) final AdminUserUpdateCommand command
            , final BindingResult errors
            , final HttpSession session
            , final ModelMap model) throws Exception {
        commonsValidator.validate(command, errors);
        adminUserUpdateValidator.validate(command, errors);
        if (!errors.hasErrors()) {
            // show popin view to confirm update
            command.setUpdateValidated(true);
        }
        removeSessionCommands(session);
        // set model and session attributes
        setCommandObjects(model, command, tableCommand, session);
        return SUCCESS_VIEW;
    }

    @AuditableEvent(Event.UPDATE_USER)
    @RequestMapping(method = RequestMethod.POST, params = "updateconfirm")
    public String updateConfirm(@ModelAttribute(TABLE_COMMAND) final TableActionCommand tableCommand
            , @ModelAttribute(UPDATE_COMMAND) final AdminUserUpdateCommand command
            , final BindingResult errors
            , final SessionStatus status
            , final HttpSession session
            , final ModelMap model) throws Exception {
        status.setComplete();
        command.setAction(Action.UPDATE);
        commonsValidator.validate(command, errors);
        adminUserUpdateValidator.validate(command, errors);
        if (errors.getAllErrors().isEmpty()) {
            try {
                getAdminService().updateUser(command.getUser());
                if (StringUtils.isNotBlank(command.getUser().getModifyTimestamp())) {
                    model.addAttribute("isUserDetailsUpdated", true);
                    model.addAttribute("updatedUserName", command.getUser().getUsername());
                } else {
                    errors.reject(Constants.RECORD_UPDATE_CONFLICT);
                }
            } catch (InvalidAirportException iae) {
                errors.rejectValue("user.location", "errors.invalid.airport.code", new Object[] { iae.getAirport() },
                        null);
            } catch (UserMissingException ume) {
                errors.reject(UPDATE_CONFLICT_MSG);
            }
        }
        removeSessionCommands(session);
        // set model and session attributes
        setCommandObjects(model, command, tableCommand, session);
     // remove PWDRESET Role from Audit -- QAT-1549
        List<String> RoleListPWDReset = new ArrayList<String>();
        RoleListPWDReset.add(PWD_RESET_ROLE);
        Collection<String> roleCollection = CollectionUtils.subtract(Arrays.asList(command.getUser().getRoles()), RoleListPWDReset);
        Arrays.copyOf(roleCollection.toArray(), roleCollection.toArray().length, String[].class);
        command.getUser().setRoles(Arrays.copyOf(roleCollection.toArray(), roleCollection.toArray().length, String[].class));
        // End remove PWDRESET Role from Audit -- QAT-1549
        
        return FORWARD_TO_USER_PROFILE;
    }

    @AuditableEvent(Event.RESET_PASSWORD)
    @RequestMapping(method = RequestMethod.POST, params = "resetPassword")
    public String resetUserPassword(@ModelAttribute(TABLE_COMMAND) final TableActionCommand tableCommand
            , @ModelAttribute(UPDATE_COMMAND) final AdminUserUpdateCommand command
            , final BindingResult result
            , final SessionStatus status
            , final HttpSession session
            , final ModelMap model) throws Exception {
        status.setComplete();
        command.setAction(Action.RESET_PASSWORD);
        // enable the user status if status is disabled
        if (UserStatus.DISABLED.equals(command.getUser().getUserStatus())) {
            User currentUser = command.getUser();
            currentUser.setUserStatus(UserStatus.ENABLED);
            getAdminService().updateUser(currentUser);
        }
        // reset password service call
        getAdminService().resetPassword(command.getUser().getUsername());
        // show popin view for resetpassword action
        command.setDisplayResetPasswordPopin("yes");
        removeSessionCommands(session);
        // retrieve screen data to show
        retrieveScreenData(model, command, tableCommand, session);
        return SUCCESS_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST, params = "reset")
    public String cancel(final ModelMap model
            , final SessionStatus status
            , final HttpSession session) throws Exception {
        AdminUserUpdateCommand command = (AdminUserUpdateCommand) session.getAttribute(UPDATE_COMMAND);
        if (command != null) {
            TableActionCommand tableCommand = (TableActionCommand) session.getAttribute(TABLE_COMMAND);
            // hide popin view
            command.setUpdateValidated(false);
            command.setDisplayResetPasswordPopin("");
            // retrieve screen data to show
            retrieveScreenData(model, command, tableCommand, session);
        }
        return SUCCESS_VIEW;
    }

    @RequestMapping(method = RequestMethod.GET, params = "locale")
    public String changeLocale(final HttpSession session,
            final ModelMap model) throws Exception {
        // Read command objects from session to ensure we see the original values
        AdminUserUpdateCommand command = (AdminUserUpdateCommand) session.getAttribute(UPDATE_COMMAND);
        if (command != null) {
            TableActionCommand tableCommand = (TableActionCommand) session.getAttribute(TABLE_COMMAND);
            // hide popin view
            command.setUpdateValidated(false);
            command.setDisplayResetPasswordPopin("");
            // retrieve screen data to show
            retrieveScreenData(model, command, tableCommand, session);
        }
        return SUCCESS_VIEW;
    }

    protected void retrieveScreenData(final ModelMap model, final AdminUserUpdateCommand adminUserUpdateCommand,
            final TableActionCommand updateUserTableCommand, final HttpSession session) throws Exception {
        User user = getAdminService().getUser(adminUserUpdateCommand.getUser().getUsername());
        adminUserUpdateCommand.setUser(user);
        // set model and session attributes
        setCommandObjects(model, adminUserUpdateCommand, updateUserTableCommand, session);
    }

    protected void setCommandObjects(final ModelMap model, final AdminUserUpdateCommand adminUserUpdateCommand,
            final TableActionCommand updateUserTableCommand, final HttpSession session) throws Exception {
        model.addAttribute(UPDATE_COMMAND, adminUserUpdateCommand);
        model.addAttribute(TABLE_COMMAND, updateUserTableCommand);
        model.addAttribute(USER, adminUserUpdateCommand.getUser());
        session.setAttribute(UPDATE_COMMAND, adminUserUpdateCommand);
        session.setAttribute(TABLE_COMMAND, updateUserTableCommand);
    }

    protected void removeSessionCommands(final HttpSession session) {
        session.removeAttribute(UPDATE_COMMAND);
        session.removeAttribute(TABLE_COMMAND);
    }

}
