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

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.josso.tc60.agent.jaas.CatalinaSSOUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springmodules.validation.commons.DefaultBeanValidator;

import abc.xyz.pts.bcs.admin.business.AdminService;
import abc.xyz.pts.bcs.admin.dao.PasswordChangeResult;
import abc.xyz.pts.bcs.admin.web.command.AdminChangePasswordCommand;
import abc.xyz.pts.bcs.admin.web.validator.AdminChangePasswordValidator;
import abc.xyz.pts.bcs.common.audit.annotation.AuditableEvent;
import abc.xyz.pts.bcs.common.audit.business.Event;
import abc.xyz.pts.bcs.common.util.CustomCalendarEditor;

/**
 * @author DGroves
 * @version: $Id: AdminChangePasswordController.java 811 2009-07-24 12:17:26Z tterry $
 */

// ** this is wired through xml as one service needs to be be included in an untransactional mode
// @Controller("adminChangePasswordController")
// @RequestMapping(value={"adminChangePassword.form"})
public class AdminChangePasswordController {
    private static final String PASSWORD_RESET = "password.must.be.reset";
    private static final String PASSWORD_WILL_EXPIRE = "password.due.to.expire";
    private static final String PASSWORD_WILL_EXPIRE_DAYS = "password.due.to.expire.in.days";
    private static final String PASSWORD_TO0_SHORT = "password.too.short";
    private static final String PASSWORD_TOO_SIMPLE = "password.too.simple";
    private static final String ERRORS_CHANGE_PASSWORD = "errors.change.password";
    private static final String PASSWORD_CHANGED = "password.changed";
    private static final String PASSWORD_CHANGE_PERIOD_NOT_REACHED = "password.change.period.not.reached";
    private static final String INVALID_OLD_PASSWORD = "invalid.old.password";
    private static final String PASSWORD_MUST_LOGOUT = "password.changed.must.logout";
    private static final String PWD_RESET_ROLE = "PWDRESET";
    private static final String MANADATORY_CHANGE_PASSWORD = "mandatoryChangePassword";

    private static final String INFO_MESSAGES_KEY = "infoMessages";

    private AdminService adminService;

    @Autowired
    private DefaultBeanValidator commonsValidator;

    @Autowired
    private AdminChangePasswordValidator changePasswordValidator;

    // ** Pages
    private static final String PAGE_ADMIN_CHANGE_PWD_COMMAND = "adminChangePasswordCommand";
    private static final String PAGE_ADMIN_CHANGE_PWD_VIEW = "adminChangePassword.view";

    private static final int SECONDS_IN_A_DAY = 60 * 60 * 24;

    /**
     * Get the actual user id from Principle.
     *
     * @param request
     * @return userId
     */
    private String getUserId(final HttpServletRequest request) {

        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal instanceof CatalinaSSOUser) {
            CatalinaSSOUser user = (CatalinaSSOUser) userPrincipal;
            return user.getName();
        }

        return "";
    }

    @AuditableEvent(Event.CHANGE_PASSWORD)
    @RequestMapping(method = RequestMethod.POST, params = "saveNewPassword")
    public String processUpdatePassword(@ModelAttribute(PAGE_ADMIN_CHANGE_PWD_COMMAND) final AdminChangePasswordCommand command
            , final BindingResult errors
            , final HttpSession session
            , final HttpServletRequest request
            , final ModelMap model) throws Exception {
        // ** Prevent security hole as we can't trust the username that is supplied the command
        command.setUserName(getUserId(request));

        commonsValidator.validate(command, errors);
        if (errors.hasErrors()) {
            return PAGE_ADMIN_CHANGE_PWD_VIEW;
        }

        changePasswordValidator.validate(command, errors);
        if (errors.hasErrors()) {
            return PAGE_ADMIN_CHANGE_PWD_VIEW;
        }

        ArrayList<String> infoMessagesList = new ArrayList<String>();

        String oldPassword = command.getOldPassword();
        String newPassword = command.getNewPassword();
        String userId = command.getUserName();
        PasswordChangeResult pwdResult = adminService.changePassword(userId, oldPassword, newPassword);

        String result;

        switch (pwdResult) {
            case SUCCESS:
                result = PASSWORD_CHANGED;
                break;
            case TOO_YOUNG:
                result = PASSWORD_CHANGE_PERIOD_NOT_REACHED;
                break;
            case INVALID_OLD_PWD:
                result = INVALID_OLD_PASSWORD;
                break;
            case TOO_SHORT:
                result = PASSWORD_TO0_SHORT;
                break;
            case NOT_COMPLEX:
                result = PASSWORD_TOO_SIMPLE;
                break;
            default:
                result = ERRORS_CHANGE_PASSWORD;
                break;
        }

        if (!result.equals(PASSWORD_CHANGED)) {
            errors.rejectValue("", result);
        }

        // If the password was successfully changed with the user in the PWDRESET role, give them an extra
        // warning message to inform them they need to log out
        if (PASSWORD_CHANGED.equals(result) && request.isUserInRole(PWD_RESET_ROLE)) {
            infoMessagesList.add(PASSWORD_MUST_LOGOUT);
        }

        // reset data
        command.setConfirmPassword(null);
        command.setOldPassword(null);
        command.setNewPassword(null);

        model.addAttribute(PAGE_ADMIN_CHANGE_PWD_COMMAND, command);
        model.addAttribute(INFO_MESSAGES_KEY, infoMessagesList);

        if (errors.hasErrors()) {
            return PAGE_ADMIN_CHANGE_PWD_VIEW;
        } else {
            command.setDisplayUpdatePopin("yes");
            return PAGE_ADMIN_CHANGE_PWD_VIEW;
        }
    }

    @AuditableEvent(Event.MENU_SELECTION)
    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(@ModelAttribute(PAGE_ADMIN_CHANGE_PWD_COMMAND) final AdminChangePasswordCommand command
            , final BindingResult errors
            , final SessionStatus status
            , final HttpSession session
            , final HttpServletRequest request
            , final ModelMap model) {
        String expiryStr = request.getParameter("expiry");

        command.setUserName(getUserId(request));

        if (expiryStr != null) {
            try {
                int expirySeconds = Integer.parseInt(expiryStr);
                if (expirySeconds <= 0) {
                    errors.reject(PASSWORD_RESET);
                } else {
                    Integer[] expiryDays = new Integer[1];
                    expiryDays[0] = Integer.valueOf((int) (expirySeconds / SECONDS_IN_A_DAY));
                    errors.reject(PASSWORD_WILL_EXPIRE_DAYS, expiryDays, null);
                }
            } catch (NumberFormatException e) {
                // If the expiry time can't be parsed, default to standard warning
                errors.reject(PASSWORD_WILL_EXPIRE);
            }
        }

        if (request.isUserInRole(PWD_RESET_ROLE)) {
            request.setAttribute(MANADATORY_CHANGE_PASSWORD, true);
        }

        model.addAttribute(PAGE_ADMIN_CHANGE_PWD_COMMAND, command);
        return PAGE_ADMIN_CHANGE_PWD_VIEW;
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(java.util.Calendar.class, new CustomCalendarEditor(dateFormat, true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));

        // ** Special Case - we want DON'T want to trim
        // binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }

    // Spring beans
    public final void setAdminService(final AdminService adminService) {
        this.adminService = adminService;
    }

}
