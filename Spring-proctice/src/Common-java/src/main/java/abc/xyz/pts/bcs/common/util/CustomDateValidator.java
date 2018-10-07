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
package abc.xyz.pts.bcs.common.util;

import java.util.Date;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericTypeValidator;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springmodules.validation.commons.FieldChecks;

/**
 * Custom Date Validator to validate the locale specific date patterns
 * @author AKandasamy
 *
 */
public final class CustomDateValidator {
    private static final String LOCALE_SPEC_DATE_PATTERN = "localeSpecDatePattern";
    private static final Logger LOG = Logger.getLogger(CustomDateValidator.class);

    private CustomDateValidator() {

    }

    public static Date validateDate(final Object bean, final ValidatorAction va, final Field field, final Errors errors) {

        Date result = null;
        String value = extractValue(bean, field);
        String datePatternStrict = ValidatorUtils.getValueAsString(bean, LOCALE_SPEC_DATE_PATTERN);

        if (!GenericValidator.isBlankOrNull(value)) {
            try {
                if (datePatternStrict != null && datePatternStrict.length() > 0) {
                    result = GenericTypeValidator.formatDate(value, datePatternStrict, true);
                }
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }

            if (result == null) {
                FieldChecks.rejectValue(errors, field, va);
            }
        }

        return result;
    }

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
}
