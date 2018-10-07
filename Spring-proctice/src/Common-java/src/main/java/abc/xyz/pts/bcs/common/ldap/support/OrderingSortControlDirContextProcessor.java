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
package abc.xyz.pts.bcs.common.ldap.support;

import javax.naming.ldap.Control;
import javax.naming.ldap.SortKey;

import org.springframework.ldap.control.AbstractFallbackRequestAndResponseControlDirContextProcessor;

public class OrderingSortControlDirContextProcessor extends
        AbstractFallbackRequestAndResponseControlDirContextProcessor {

    private static final String DEFAULT_REQUEST_CONTROL = "javax.naming.ldap.SortControl";

    private static final String FALLBACK_REQUEST_CONTROL = "com.sun.jndi.ldap.ctl.SortControl";

    private static final String DEFAULT_RESPONSE_CONTROL = "javax.naming.ldap.SortResponseControl";

    private static final String FALLBACK_RESPONSE_CONTROL = "com.sun.jndi.ldap.ctl.SortResponseControl";

    /**
     * What key to sort on.
     */
    private String sortKey;

    /**
     * Whether the search result actually was sorted.
     */
    private boolean sorted;

    /**
     * The result code of the supposedly sorted search.
     */
    private int resultCode;

    /**
     * Constructs a new instance using the supplied sort key.
     *
     * @param sortKey
     *            the sort key, i.e. the attribute name to sort on.
     */
    private boolean ascendingOrder;

    public OrderingSortControlDirContextProcessor(final String sortKey, final boolean ascendingOrder) {
        this.ascendingOrder = ascendingOrder;

        this.sortKey = sortKey;
        this.sorted = false;
        this.resultCode = -1;

        defaultRequestControl = DEFAULT_REQUEST_CONTROL;
        defaultResponseControl = DEFAULT_RESPONSE_CONTROL;

        fallbackRequestControl = FALLBACK_REQUEST_CONTROL;
        fallbackResponseControl = FALLBACK_RESPONSE_CONTROL;

        loadControlClasses();
    }

    /**
     * Check whether the returned values were actually sorted by the server.
     *
     * @return <code>true</code> if the result was sorted, <code>false</code> otherwise.
     */
    public boolean isSorted() {
        return sorted;
    }

    /**
     * Get the result code returned by the control.
     *
     * @return result code.
     */
    public int getResultCode() {
        return resultCode;
    }

    /**
     * Get the sort key.
     *
     * @return the sort key.
     */
    public String getSortKey() {
        return sortKey;
    }

    /*
     * @see org.springframework.ldap.control.AbstractRequestControlDirContextProcessor #createRequestControl()
     */
    public Control createRequestControl() {
        SortKey sortKeyObj = new SortKey(sortKey, this.ascendingOrder, null);
        return super.createRequestControl(new Class[] { SortKey[].class, boolean.class }, new Object[] {
                new SortKey[] { sortKeyObj }, Boolean.valueOf(critical) });
    }

    /*
     * @see org.springframework.ldap.control. AbstractFallbackRequestAndResponseControlDirContextProcessor
     * #handleResponse(java.lang.Object)
     */
    protected void handleResponse(final Object control) {
        Boolean result = (Boolean) invokeMethod("isSorted", responseControlClass, control);
        this.sorted = result.booleanValue();
        Integer code = (Integer) invokeMethod("getResultCode", responseControlClass, control);
        this.resultCode = code.intValue();
    }

}
