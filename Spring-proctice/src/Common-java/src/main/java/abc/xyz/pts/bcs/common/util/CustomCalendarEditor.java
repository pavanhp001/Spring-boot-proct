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

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

import abc.xyz.pts.bcs.common.dao.utils.Constants;

public class CustomCalendarEditor extends PropertyEditorSupport {

    private final DateFormat dateFormat;
    private final Boolean allowEmpty;

    private final ThreadLocal<DateFormat> simpleDateFormat = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(Constants.DATE_FORMAT);
        }
    };

    public CustomCalendarEditor(final DateFormat dateFormat, final Boolean allowEmpty) {
        super();
        this.dateFormat = dateFormat;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public final String getAsText() {
        final Calendar value = (Calendar) getValue();
        if (value != null) {
            // if there is a time element then lets see it
            // otherwise show it in simple
            final int hour = value.get(Calendar.HOUR);
            final int minute = value.get(Calendar.MINUTE);
            if ((minute != 0) && (hour != 0)) {
                // dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
                return simpleDateFormat.get().format(value.getTime());
            }
        }

        return (value != null ? this.dateFormat.format(value.getTime()) : "");
    }

    @Override
    public final void setAsText(final String text) {
        if (this.allowEmpty && StringUtils.isEmpty(text)) {
            setValue(null);
        } else {
            try {
                final Calendar cal = Calendar.getInstance();
                this.dateFormat.setLenient(false);
                cal.setTime(this.dateFormat.parse(text));
                if (!isYearValid(cal)) {
                    throw new IllegalArgumentException();
                }
                setValue(cal);
            } catch (final ParseException ex) {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * This method is used to ensure that the year is the correct length.
     *
     * @param yearToValidate
     * @return boolean - flag to indicate whether the date is the correct length.
     */
    protected boolean isYearValid(final Calendar yearToValidate) {
        final Integer year = yearToValidate.get(Calendar.YEAR);
        if (year.toString().length() < 4) {
            return false;
        }

        return true;
    }

}