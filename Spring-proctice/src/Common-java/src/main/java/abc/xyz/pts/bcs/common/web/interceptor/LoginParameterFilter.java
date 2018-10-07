/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.web.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import abc.xyz.pts.bcs.common.web.userNavigation.UpdateParameterHttpServletRequestWrapper;

public class LoginParameterFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException,
            ServletException {

        String paramString = (String) ((HttpServletRequest) request).getSession().getAttribute("FORWARD_PARAM");
        if (!StringUtils.isBlank(paramString)) {
            Map<String, String[]> params = new HashMap<String, String[]>();
            StringTokenizer token = new StringTokenizer(paramString, "&");
            while (token.hasMoreElements()) {
                String param = token.nextToken();
                // find the equals, then the remainder are the list of values
                int equalPos = param.indexOf("=");
                if (equalPos >= 0) {
                    String key = param.substring(0, equalPos);
                    String values = "";
                    if (param.length() >= equalPos + 1) {
                        values = param.substring(equalPos + 1);
                    }
                    // loop through values
                    ArrayList<String> valueList = new ArrayList<String>();
                    StringTokenizer paramValues = new StringTokenizer(values, ",");
                    while (paramValues.hasMoreTokens()) {
                        valueList.add(paramValues.nextToken());
                    }
                    if (valueList.size() > 0) {
                        params.put(key, new String[] { valueList.get(0) });
                    }
                }
            }
            ((HttpServletRequest) request).getSession().removeAttribute("FORWARD_PARAM");
            HttpServletRequest newRequest = new UpdateParameterHttpServletRequestWrapper((HttpServletRequest) request,
                    params);
            chain.doFilter(newRequest, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }
}
