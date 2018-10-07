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

import org.apache.commons.lang.StringUtils;

public class StringArrayEditor extends PropertyEditorSupport {

    public StringArrayEditor() {

    }

    @Override
    public String getAsText() {
        String result = "";
        String[] values = (String[]) getValue();

        if (values != null) {
            StringBuilder buff = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                if (i == 0) {
                    buff.append(values[i]);
                } else {
                    buff.append(',');
                    buff.append(values[i]);
                }
            }
            result = buff.toString();
        }
        return result;
    }

    @Override
    public void setAsText(final String text) {
        if (StringUtils.isEmpty(text)) {
            setValue(null);
        } else {
            String upperCase = text.toUpperCase();
            String[] values = upperCase.split(",");
            setValue(values);
        }
    }
}
