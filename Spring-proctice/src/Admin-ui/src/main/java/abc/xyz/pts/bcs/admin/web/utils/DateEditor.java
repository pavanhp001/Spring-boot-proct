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

package abc.xyz.pts.bcs.admin.web.utils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import abc.xyz.pts.bcs.common.dao.utils.Constants;

public class DateEditor extends PropertyEditorSupport {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern(Constants.SIMPLE_DATE_FORMAT);

    public DateEditor() {

    }

    // Extend this class to allow time only calendars.
    @Override
    public String getAsText() {
        String result = "";
        Date value = (Date) getValue();
        if (value != null) {
            DateTime dt = new DateTime(value.getTime());
            int hour = dt.getHourOfDay();
            int minute = dt.getMinuteOfHour();
            int second = dt.getSecondOfMinute();
            if ((hour == 0) && (minute == 0) && (second == 0)) {
                result = DATE_FORMATTER.print(dt);
            } else {
                result = DATE_TIME_FORMATTER.print(dt);
            }
        }
        return result;
    }

    @Override
    public void setAsText(final String text) {
        if (StringUtils.isEmpty(text)) {
            setValue(null);
        } else {
            try {
                SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT);
                dateFormatter.setLenient(false);
                Date value = dateFormatter.parse(text);
                setValue(value);
            } catch (ParseException ex) {
                throw new IllegalArgumentException();
            }
        }
    }

}
