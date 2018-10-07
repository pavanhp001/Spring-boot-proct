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
import java.math.BigDecimal;

import org.apache.log4j.Logger;

/**
 * BigDecimal binder for Spring MVC
 *
 * @author ryattapu
 *
 */
public class CustomBigDecimalEditor extends PropertyEditorSupport {

    private static Logger log = Logger.getLogger(CustomBigDecimalEditor.class);

    public String getAsText() {
        log.info("Entering method .getAsText");

        if (this.getValue() == null) {
            return "";
        }

        try {
            BigDecimal bd = (BigDecimal) this.getValue();

            log.info("Exiting method .getAsText");
            return bd.toString();
        } catch (Exception e) {
            // no valid value stored so set to blank
            log.info("Exiting method .getAsText");
            return "";
        }

        // return super.getAsText();
    }

    public void setAsText(final String arg0) throws IllegalArgumentException {
        log.info("Entering method .setAsText");

        if ((arg0 == null) || arg0.equals("")) {
            log.debug("minAmount is not set.");
            setValue(null);
        } else {
            BigDecimal bd = new BigDecimal(arg0);
            setValue(bd);
        }

        log.info("Exiting method .setAsText");

        // super.setAsText(arg0);
    }
}