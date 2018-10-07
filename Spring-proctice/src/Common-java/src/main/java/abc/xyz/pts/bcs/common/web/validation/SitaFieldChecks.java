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
package abc.xyz.pts.bcs.common.web.validation;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.springframework.validation.Errors;
import org.springmodules.validation.commons.MessageUtils;

/**
 * Validation for field of type time, e.g. 12:30 used by spring validator. Referenced in validation-rules and
 * validation.xml
 *
 * @author Vlad Shiligin
 */
public class  FieldChecks implements Serializable {

    private static final Log log = LogFactory.getLog( FieldChecks.class);

    public void validateTime(final Object target, final ValidatorAction va, final Field field, final Errors errors) {
        final String timeFormat = field.getVarValue("timeFormat");
        final String value =  FieldChecks.extractValue(target, field);
        String timeRegex = "[0-2][0-9][:][0-5][0-9]";

        if (timeFormat != null && !timeFormat.isEmpty() && timeFormat.toUpperCase().equals("AM_PM")) {
            timeRegex = "[0-1][0-9][:][0-5][0-9]\\s[A|a,P|p][M|m]";
        }
        if (!GenericValidator.isBlankOrNull(value)) {
            if (!value.matches(timeRegex)) {
                 FieldChecks.rejectValue(errors, field, va);
            } else {

                final int hours = Integer.parseInt(value.substring(0, value.indexOf(":")));
                final int minutes = Integer.parseInt(value.substring(value.indexOf(":") + 1));

                if (timeFormat != null && timeFormat.trim().length() > 0 && timeFormat.equals("AM_PM")
                        && (hours > 12 || minutes > 59)) {
                     FieldChecks.rejectValue(errors, field, va);
                } else if ((hours > 23 || minutes > 59)) {
                     FieldChecks.rejectValue(errors, field, va);
                }
            }
        }
    }

    /**
     * Checks if the field matches the regular expression in the field's mask attribute.
     *
     * @param bean
     *            The bean validation is being performed on.
     * @param va
     *            The <code>ValidatorAction</code> that is currently being performed.
     * @param field
     *            The <code>Field</code> object associated with the current field being validated.
     * @param errors
     *            The <code>Errors</code> object to add errors to if any validation errors occur. -param request Current
     *            request object.
     * @return true if field matches mask, false otherwise.
     */
    public static boolean validateRegEx(final Object bean, final ValidatorAction va, final Field field, final Errors errors) {
        final String mask = field.getVarValue("regex");
        final String value =  FieldChecks.extractValue(bean, field);
        try {
            if (value != null && value.trim().length() > 0 && !value.matches(mask)) {
                 FieldChecks.rejectValue(errors, field, va);
                return false;
            } else {
                return true;
            }
        } catch (final Exception e) {
             FieldChecks.log.error(e.getMessage(), e);
        }
        return true;
    }

    /**
     * Extracts the value of the given bean. If the bean is <code>null</code>, the returned value is also
     * <code>null</code>. If the bean is a <code>String</code> then the bean itself is returned. In all other cases, the
     * <code>ValidatorUtils</code> class is used to extract the bean value using the <code>Field</code> object supplied.
     *
     * @see ValidatorUtils#getValueAsString(Object, String)
     */
    protected static String extractValue(final Object bean, final Field field) {
        String value = null;

        if (bean == null) {
            return null;
        } else if (bean instanceof String) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }

        return value;
    }

    /**
     * Convinience method to perform the work of rejecting a field's value.
     *
     * @param errors
     *            the errors
     * @param field
     *            the field that was rejected
     * @param va
     *            the validator action
     */
    public static void rejectValue(final Errors errors, final Field field, final ValidatorAction va) {
        String fieldCode = field.getKey();

        // this is required to translate the mapping access used by commons validator (delegated to commons beanutils)
        // which uses '(' and ')' to the notation used by spring validator (delegated to PropertyAccessor) which
        // uses '[' and ']'.
        fieldCode = fieldCode.replace('(', '[').replace(')', ']');

        final String errorCode = MessageUtils.getMessageKey(va, field);
        final Object[] args = MessageUtils.getArgs(va, field);

        if ( FieldChecks.log.isDebugEnabled()) {
             FieldChecks.log.debug("Rejecting value [field='" + fieldCode + "', errorCode='" + errorCode + "']");
        }

        errors.rejectValue(fieldCode, errorCode, args, errorCode);
    }
    /**
     * Convinience method to perform the work of rejecting a field's value.
     *
     * @param errors
     *            the errors
     * @param field
     *            the field that was rejected
     * @param va
     *            the validator action
     * @param preArg
     *               an argument to add in before the other args are added in
     */
    public static void rejectValue(final Errors errors, final Field field, final ValidatorAction va, final Object preArg) {
        String fieldCode = field.getKey();

        // this is required to translate the mapping access used by commons validator (delegated to commons beanutils)
        // which uses '(' and ')' to the notation used by spring validator (delegated to PropertyAccessor) which
        // uses '[' and ']'.
        fieldCode = fieldCode.replace('(', '[').replace(')', ']');

        final String errorCode = MessageUtils.getMessageKey(va, field);
        final int alength = MessageUtils.getArgs(va, field).length;
        final Object[] args = new Object[alength+1];
        args[0] = preArg;
        int index = 1;
        for (final Object a : MessageUtils.getArgs(va, field)) {
            args[index++] = a;
        }

        if ( FieldChecks.log.isDebugEnabled()) {
             FieldChecks.log.debug("Rejecting value [field='" + fieldCode + "', errorCode='" + errorCode + "']");
        }

        errors.rejectValue(fieldCode, errorCode, args, errorCode);
    }
}