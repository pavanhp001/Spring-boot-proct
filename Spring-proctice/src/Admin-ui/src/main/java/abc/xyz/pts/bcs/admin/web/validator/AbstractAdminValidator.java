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

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import abc.xyz.pts.bcs.admin.dto.User;

public abstract class AbstractAdminValidator implements Validator {
    protected static final String EMPTY = "";
    private MessageSourceAccessor messageSourceAccessor;
    private static final String[] AIRPORT_ROLES = new String[] { "PPA", "HOS", "INT", "BAM", "ADR", "VTD" };

    public void validate(final Object target, final Errors errors) {
        User user = getUser(target);
        String[] userRoles = user.getRoles();
        // check that no invalid roles have been chosen. The list of
        // invalid roles is in the spring config.
        if (userRoles == null || userRoles.length == 0) {
            errors.rejectValue("user.roles", "errors.required",
                    new Object[] { messageSourceAccessor.getMessage("role") }, EMPTY);
        } else {
            // some roles must have a airport associated.
            if (userIsInRole(userRoles, AIRPORT_ROLES) && !StringUtils.isNotEmpty(user.getLocation())) {
                errors.rejectValue("user.location", "errors.required", new Object[] { messageSourceAccessor
                        .getMessage("airport") }, EMPTY);
            }
        }
    }

    private boolean userIsInRole(final String[] userRoles, final String[] requiredRoles) {
        boolean result = false;
        List<String> requiredRolesList = Arrays.asList(requiredRoles);
        for (String userRole : userRoles) {
            if (requiredRolesList.contains(userRole)) {
                result = true;
                break;
            }
        }
        return result;
    }

    protected abstract User getUser(Object command);

    public void setMessageSource(final MessageSource messageSource) {
        this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

}
