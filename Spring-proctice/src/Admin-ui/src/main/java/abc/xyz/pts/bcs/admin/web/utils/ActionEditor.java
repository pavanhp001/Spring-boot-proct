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

import org.apache.commons.lang.StringUtils;

import abc.xyz.pts.bcs.admin.web.command.Action;

public class ActionEditor extends AbstractEnumEditor {

    public ActionEditor() {

    }

    @Override
    public String getAsText() {
        String result = "";
        Action value = (Action) getValue();

        if (value != null) {
            result = value.getValue();
        }
        return result;
    }

    @Override
    public void setAsText(final String text) {
        if (StringUtils.isEmpty(text)) {
            setValue(null);
        } else {
            String name = convertName(text);
            Action us = Action.valueOf(name);
            setValue(us);
        }
    }

}
