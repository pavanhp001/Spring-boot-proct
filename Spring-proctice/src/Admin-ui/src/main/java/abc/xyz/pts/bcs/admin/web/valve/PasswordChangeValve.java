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
package abc.xyz.pts.bcs.admin.web.valve;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import org.apache.catalina.Session;
import org.apache.catalina.authenticator.SavedRequest;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

/**
 * Valve to enable password change functionality with Josso This valve MUST appear before Josso's SSOAgentValve
 *
 * @author simona
 */
public class PasswordChangeValve extends ValveBase {

    private static final String PASSWORD_PREFIX = "/admin/pwdhandler/";
    private static final String PASSWORD_SCREEN = "/admin/adminChangePassword.form";
    private static final Pattern URL_PATTERN = Pattern.compile("/");

    @Override
    public void invoke(final Request request, final Response response) throws IOException, ServletException {

        String originalPathInfo = request.getRequestURI();
        while (originalPathInfo.contains("//")) {
            originalPathInfo = originalPathInfo.replaceAll("//", "/");
        }

        if (originalPathInfo.startsWith(PASSWORD_PREFIX)) {
            String newPathInfo = originalPathInfo.substring(PASSWORD_PREFIX.length());
            String[] pathElements = URL_PATTERN.split(newPathInfo, 0);

            // split() method doesn't return null
            if (pathElements.length > 0) {
                // Convert http://PASSWORD_PREFIX/a/b to http://PASSWORD_SCREEN?a=b
                String parameter = pathElements[0];
                String value = "true";

                if (pathElements.length > 1) {
                    value = pathElements[1];
                }

                // Create a query string from the paramters
                StringBuffer queryStringBuffer = new StringBuffer(parameter.length() + value.length() + 1);
                queryStringBuffer.append(parameter);
                queryStringBuffer.append('=');
                queryStringBuffer.append(value);

                // Add a 'saved request' containing the URL we want Josso to pick up
                // after completing the authentication process
                SavedRequest saved = new SavedRequest();

                // These are the only two parameters used by superclass's savedRequestURL
                // When upgrading Josso, check this.
                saved.setRequestURI(PASSWORD_SCREEN);
                saved.setQueryString(queryStringBuffer.toString());

                // Attach the saved request to the session
                Session session = request.getSessionInternal(true);
                session.setNote(org.apache.catalina.authenticator.Constants.FORM_REQUEST_NOTE, saved);
            }

        }
        // Allow Tomcat to invoke next valve in sequence
        getNext().invoke(request, response);
    }
}
