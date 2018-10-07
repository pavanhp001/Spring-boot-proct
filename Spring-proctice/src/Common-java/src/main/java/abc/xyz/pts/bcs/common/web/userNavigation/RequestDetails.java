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
package abc.xyz.pts.bcs.common.web.userNavigation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import abc.xyz.pts.bcs.common.web.interceptor.UserNavigationInterceptor;

/**
 * Wrapper class to store a previous request from the user in their session.
 *
 * @author tterry
 *
 */
public class RequestDetails implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String url;
    private Map parameters;
    private String requestMethod;
    private String page;

    public RequestDetails(final String page, final String url, final String requestMethod) {
        this.page = page;
        this.url = url;
        this.requestMethod = requestMethod;
        parameters = new HashMap();
    }

    /**
     * Constructor.
     *
     * @param request
     *            A web request from the user.
     * @param pageName
     *            The spring mvc view name.
     */
    public RequestDetails(final HttpServletRequest request, final String pageName) {
        url = request.getRequestURI().toString();

        requestMethod = request.getMethod();
        parameters = new HashMap(request.getParameterMap().size());
        Iterator it = request.getParameterMap().keySet().iterator();
        List<String> ignoreList = getIgnorableParameters(request);
        // we must copy all the params as otherwise they are destroyed on the next request.
        while (it.hasNext()) {
            String key = (String) it.next();
            if (!ignoreList.contains(key)) {
                String value = (String) request.getParameter(key);
                parameters.put(key, value);
            }
        }
        this.page = pageName;
    }

    /**
     * This method copy ignorable parameters(comma delimeter) copy to list
     *
     * @return the List of fields to be not store in Session. *
     */
    private List<String> getIgnorableParameters(final HttpServletRequest request) {
        String[] ignoreParametersArray = null;
        List<String> ignoreList = new ArrayList<String>();
        String ignoreParameters = (String) request.getParameter(UserNavigationInterceptor.IGNORE_PARAMETERS_PARAM);
        if (ignoreParameters != null) {
            ignoreList = Arrays.asList(ignoreParameters.split(","));
        }
        return ignoreList;
    }

    /**
     * @return the URL associated with this request.
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the http method of this request, GET or POST
     */
    public String getRequestMethod() {
        return requestMethod;
    }

    /**
     * @return Any parameters that may have been supplied with the http request.
     */
    public Map getParameters() {
        return parameters;
    }

    /**
     * @return
     */
    public String getPage() {
        return page;
    }
}
