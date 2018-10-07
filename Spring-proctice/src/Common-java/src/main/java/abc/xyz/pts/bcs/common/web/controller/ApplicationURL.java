/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.web.controller;

import javax.servlet.http.HttpServletRequest;


public final class ApplicationURL {

    private static final String PAGE_VIEW_UPDATE_TARGET = "/viewTarget.form";
    private static final String PAGE_TRAVELLER_SEARCH = "/travellerSearch.form";

    private static final String CONTEXT_WI = "/wi";
    private static final String CONTEXT_IDETECTDB = "/idetectdb";

    private ApplicationURL()
    {
        super();
    }

    /**
     * Get URL for View/Edit target.
     *
     * @param request
     * @return url
     */
    public static String getViewTarget(final HttpServletRequest request)
    {
        return getContext(request, CONTEXT_WI, PAGE_VIEW_UPDATE_TARGET);
    }

    /**
     * Get URL for Traveller Search.
     *
     * @param request
     * @return url
     */
    public static String getTravellerSearch(final HttpServletRequest request)
    {
        return getContext(request, CONTEXT_IDETECTDB, PAGE_TRAVELLER_SEARCH);
    }

    /**
     * Generic method to construct the url.
     *
     * @param request
     * @param appContext
     * @param page
     * @return
     */
    private static String getContext(final HttpServletRequest request, final String appContext, final String page)
    {
        StringBuffer url = request.getRequestURL();

        if (url.indexOf(request.getContextPath()) >= 0)
        {
            url.replace(url.indexOf(request.getContextPath()), url.length(), appContext);
            url.append(page);
        }

        return url.toString();
    }
}
