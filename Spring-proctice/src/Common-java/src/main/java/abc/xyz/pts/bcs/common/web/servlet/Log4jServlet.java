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
package abc.xyz.pts.bcs.common.web.servlet;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Log4jServlet extends HttpServlet {

    private static final String INIT_PARAM = "log4j-init-file";
    private static final String URL_SEPARATOR = "/";

    public void init() {
        String prefix = getServletContext().getRealPath(URL_SEPARATOR);
        String file = getInitParameter(INIT_PARAM);
        // if the log4j-init-file is not set, then no point in trying
        if (file != null) {
            String fileName = prefix + file;
            DOMConfigurator.configure(fileName);
            Logger.getLogger(Log4jServlet.class).info("Log4j initialised from " + fileName);
        }
    }
}
