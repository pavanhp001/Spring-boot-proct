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
package abc.xyz.pts.bcs.admin.web.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import abc.xyz.pts.bcs.admin.business.AdminService;
import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.admin.web.command.AdminChangePasswordCommand;
import abc.xyz.pts.bcs.common.enums.UserPermissionType;

public class AdminChangePasswordValidator implements Validator {
    private static final int THREE = 3;
    private static final String EMPTY = "";

    @Autowired
    private AdminService adminService;

    private MessageSource messageSource;
    private MessageSourceAccessor messageSourceAccessor = null;

    public void setMessageSource(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public MessageSourceAccessor getMessageSourceAccessor() {
        if (messageSourceAccessor == null) {
            messageSourceAccessor = new MessageSourceAccessor(messageSource);
        }
        return messageSourceAccessor;
    }

    @Override
    public boolean supports(final Class clazz) {
        return clazz.equals(AdminChangePasswordCommand.class);
    }

    @Override
    public void validate(final Object command, final Errors errors) {
        final AdminChangePasswordCommand adminChangePasswordCommand = (AdminChangePasswordCommand) command;
        final String newPassword = adminChangePasswordCommand.getNewPassword();
        final String userName = adminChangePasswordCommand.getUserName();
        final String confirmPassword = adminChangePasswordCommand.getConfirmPassword();

        if (newPassword == null) {
            errors.rejectValue("newPassword", "password.too.simple", new Object[] {}, EMPTY);
            return;
        } else if (!newPassword.equals(confirmPassword)) {
            errors.rejectValue("confirmPassword", "invalid.confirm.password", new Object[] {}, EMPTY);
            return;
        }

        // Check for 3 different char types
        int charsMask = 0;

        for (final char c : newPassword.toCharArray()) {
            if (Character.isLowerCase(c)) {
                charsMask |= 0x1;
            } else if (Character.isUpperCase(c)) {
                charsMask |= 0x2;
            } else if (Character.isDigit(c)) {
                charsMask |= 0x4;
            } else {
                charsMask |= 0x8;
            }
        }

        if (Integer.bitCount(charsMask) < 3) {
            errors.rejectValue("newPassword", "password.too.simple", new Object[] {}, EMPTY);
        }

        if (userName != null) {
            final int lenght = userName.length();
            final List<String> threeConsecutiveCharsList = new ArrayList<String>();
            for (int i = 0; i < lenght; i++) {
                if (i + THREE <= lenght) {
                    threeConsecutiveCharsList.add(userName.substring(i, i + THREE));
                }
            }
            boolean isThreeConsecutiveCharsExists = false;
            for (final String threeConsecutiveChars : threeConsecutiveCharsList) {
                if (newPassword.indexOf(threeConsecutiveChars) != -1) {
                    isThreeConsecutiveCharsExists = true;
                    break;
                }
            }
            if (isThreeConsecutiveCharsExists) {
                errors.rejectValue("newPassword", "invalid.password.contains.three.consecutive.chars.from.username",
                        new Object[] {}, EMPTY);
            }

        }

        if (currentUserIsInAdminRole(userName) && newPassword.length() < 8) {
            errors.rejectValue("newPassword", "password.too.short");
        }
    }

    private boolean currentUserIsInAdminRole(final String userName) {
        final User user = adminService.getUser(userName);
        final List<String> roles = Arrays.asList(user.getRoles());
        return roles.contains(UserPermissionType.USER_WRITE.getPermission());
    }
}
